package com.memory.platform.module.capital.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.memory.platform.core.AppUtil;
import com.memory.platform.core.BeanKVO;
import com.memory.platform.core.BeanKVO.EnumPropertyCallBack;
import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.UnionPay;
import com.memory.platform.entity.capital.WeiXinPay;
import com.memory.platform.entity.capital.WeiXinPayOrder;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.Config;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.global.sdk.LogUtil;
import com.memory.platform.global.sdk.SDKConfig;

/**
 * 创建人：xiaolong 创建时间：2016年12月23日 上午10:41:23 修改人： 修改时间： 描述： 版本号：V1.0
 */
public class RechargeUtil {
 

	/**
	 * 功能描述： 查询开通状态
	 * 
	 * @param @return
	 *            异 常： 创 建 人: xiaolong 日 期: 2016年12月23日 上午10:34:25 修 改 人: 日 期: 返
	 *            回：Map<String,Object>
	 */
	public static Map<String, Object> queryOpen(String orderId, String txnTime, String accNo) {
		Map<String, String> contentData = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "78"); // 交易类型 78-开通查询
		contentData.put("txnSubType", "00"); // 交易子类型 00-根据账号accNo查询(默认）
		contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
		String accNo1 = AcpService.encryptData(accNo, "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo1);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下

		///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		// contentData.put("accNo", accNo); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				String respCode = rspData.get("respCode");
				map.put("code", respCode);
				if (("00").equals(respCode)) {
					map.put("success", true);
					map.put("msg", "已开通银联支付。");
				} else {
					// 其他应答码为失败请排查原因或做失败处理
					map.put("success", false);
					map.put("msg", "查询开通失败。");
				}
			} else {
				map.put("code", "");
				map.put("success", false);
				map.put("msg", "验证签名失败。");
			}
		} else {
			map.put("code", "");
			map.put("success", false);
			map.put("msg", "未返回正确的http状态。");
		}
		map.put("orderId", orderId);
		map.put("txnTime", txnTime);
		map.put("accNo", accNo);
		return map;
	}

	/**
	 * 功能描述： 开通发送验证码
	 * 
	 * @param @param
	 *            orderId
	 * @param @param
	 *            txnTime
	 * @param @param
	 *            phoneNo
	 * @param @param
	 *            accNo
	 * @param @return
	 *            异 常： 创 建 人: xiaolong 日 期: 2016年12月23日 上午11:23:46 修 改 人: 日 期: 返
	 *            回：Map<String,Object>
	 */
	public static Map<String, Object> openSendMsg(String orderId, String txnTime, String phoneNo, String accNo) {
		Map<String, String> contentData = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "77"); // 交易类型 77-发送短信
		contentData.put("txnSubType", "00"); // 交易子类型 00-开通短信
		contentData.put("bizType", "000301"); // 业务类型 无跳转
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01"); // 账号类型

		Map<String, String> customerInfoMap = new HashMap<String, String>();
		customerInfoMap.put("phoneNo", phoneNo); // 手机号

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
		String accNo1 = AcpService.encryptData(accNo, "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo1);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, accNo, BaseSdk.encoding_UTF8);

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				// LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				map.put("code", respCode);
				if (("00").equals(respCode)) {
					map.put("success", true);
					map.put("msg", "开通支付成功发送验证码");
				} else {
					map.put("success", false);
					map.put("msg", "开通支付发送验证码失败");
				}
			} else {
				map.put("code", "");
				map.put("success", false);
				map.put("msg", "验证签证失败。");
			}
		} else {
			map.put("code", "");
			map.put("success", false);
			map.put("msg", "未获取到返回报文或返回http状态码非200。");
		}
		map.put("orderId", orderId);
		map.put("txnTime", txnTime);
		map.put("phoneNo", phoneNo);
		map.put("accNo", accNo);
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------");
		System.out.println(rspData);
		return map;
	}

	/**
	 * 功能描述： 交易发送短信验证码
	 * 
	 * @param @param
	 *            orderId
	 * @param @param
	 *            txnTime
	 * @param @param
	 *            txnAmt
	 * @param @param
	 *            phoneNo
	 * @param @param
	 *            accNo
	 * @param @return
	 *            异 常： 创 建 人: xiaolong 日 期: 2016年12月23日 上午11:11:07 修 改 人: 日 期: 返
	 *            回：Map<String,Object>
	 */
	public static Map<String, Object> sendMsg(String orderId, String txnTime, String txnAmt, String phoneNo,
			String accNo) {
		Map<String, String> contentData = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "77"); // 交易类型 11-代收
		contentData.put("txnSubType", "02"); // 交易子类型 02-消费短信
		contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("currencyCode", "156"); // 交易币种（境内商户一般是156 人民币）
		contentData.put("txnAmt", txnAmt); // 交易金额，单位分，不要带小数点
		contentData.put("accType", "01"); // 账号类型

		// 必送手机号
		Map<String, String> customerInfoMap = new HashMap<String, String>();
		customerInfoMap.put("phoneNo", phoneNo);

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
		String accNo1 = AcpService.encryptData(accNo, "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo1);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, accNo, BaseSdk.encoding_UTF8);

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》

		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				// LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				map.put("code", respCode);
				if (("00").equals(respCode)) {
					map.put("success", true);
					map.put("msg", "成功发送短信验证码");
				} else {
					map.put("success", false);
					map.put("msg", "短信验证码发送失败。");
				}
			} else {
				map.put("code", "");
				map.put("success", false);
				map.put("msg", "验证签名失败");
			}
		} else {
			map.put("code", "");
			map.put("success", false);
			map.put("msg", "未获取到返回报文或返回http状态码非200");
		}
		map.put("orderId", orderId);
		map.put("txnTime", txnTime);
		map.put("phoneNo", phoneNo);
		map.put("accNo", accNo);
		map.put("txnAmt", txnAmt);
		return map;
	}

	/**
	 * 功能描述： 交易充值
	 * 
	 * @param @param
	 *            orderId
	 * @param @param
	 *            txnTime
	 * @param @param
	 *            txnAmt
	 * @param @param
	 *            smsCode
	 * @param @return
	 *            异 常： 创 建 人: xiaolong 日 期: 2016年12月23日 上午11:26:27 修 改 人: 日 期: 返
	 *            回：Map<String,Object>
	 */
	public static Map<String, Object> consume(String orderId, String txnTime, String txnAmt, String smsCode,
			String accNo) {
		Map<String, String> contentData = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "01"); // 交易类型 01-消费
		contentData.put("txnSubType", "01"); // 交易子类型 01-消费
		contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("currencyCode", "156"); // 交易币种（境内商户一般是156 人民币）
		contentData.put("txnAmt", txnAmt); // 交易金额，单位分，不要带小数点
		contentData.put("accType", "01"); // 账号类型

		// 消费：交易要素卡号+验证码看业务配置(默认要短信验证码)。
		Map<String, String> customerInfoMap = new HashMap<String, String>();
		customerInfoMap.put("smsCode", smsCode); // 短信验证码,测试环境不会真实收到短信，固定填111111

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对
		//////////// accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		contentData.put("accNo", AcpService.encryptData(accNo, BaseSdk.encoding_UTF8));
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, accNo, BaseSdk.encoding_UTF8);
		//////////

		///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		// contentData.put("accNo", "6216261000000000018");
		///////// //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		// String customerInfoStr =
		///////// AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018",AcpService.encoding_UTF8);
		////////

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

		// 后台通知地址（需设置为【外网】能访问 http
		// https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		// 后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 代收产品接口规范 代收交易 商户通知
		// 注意:1.需设置为外网能访问，否则收不到通知 2.http https均可 3.收单后台通知后需要10秒内返回http200或302状态码
		// 4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		// 5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d
		// 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", BaseSdk.backUrl);
		// 分期付款用法（商户自行设计分期付款展示界面）：
		// 修改txnSubType=03，增加instalTransInfo域
		// 【测试环境】固定使用测试卡号6221558812340000，测试金额固定在100-1000元之间，分期数固定填06
		// 【生产环境】支持的银行列表清单请联系银联业务运营接口人索要
		// contentData.put("txnSubType", "03"); //交易子类型 03-分期付款
		// contentData.put("instalTransInfo","{numberOfInstallments=06}");//分期付款信息域，numberOfInstallments期数

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				// LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				map.put("code", respCode);
				if (("00").equals(respCode)) {
					// 交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
					// TODO
					// 如果是配置了敏感信息加密，如果需要获取卡号的铭文，可以按以下方法解密卡号
					// String accNo1 = rspData.get("accNo");
					// String accNo2 = AcpService.decryptData(accNo1, "UTF-8");
					// //解密卡号使用的证书是商户签名私钥证书acpsdk.signCert.path
					// LogUtil.writeLog("解密后的卡号："+accNo2);
					map.put("success", true);
					map.put("msg", "充值成功");
				} else if (("03").equals(respCode) || ("04").equals(respCode) || ("05").equals(respCode)) {
					map.put("success", true);
					map.put("msg", rspData.get("respMsg"));
				} else {
					map.put("code", "");
					map.put("success", false);
					map.put("msg", rspData.get("respMsg"));
				}
			} else {
				map.put("code", "");
				map.put("success", false);
				map.put("msg", rspData.get("respMsg"));
				// LogUtil.writeErrorLog("验证签名失败");
				// TODO 检查验证签名失败的原因
			}
		} else {
			map.put("code", "");
			map.put("success", false);
			map.put("msg", rspData.get("respMsg"));
			// 未返回正确的http状态
			// LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		map.put("orderId", orderId);
		map.put("txnTime", txnTime);
		map.put("txnAmt", txnAmt);
		map.put("smsCode", smsCode);
		map.put("accNo", accNo);
		return map;
	}

	/**
	 * I 功能描述： 开通银联支付
	 * 
	 * @param @param
	 *            orderId
	 * @param @param
	 *            txnTime
	 * @param @param
	 *            accNo
	 * @param @return
	 *            异 常： 创 建 人: xiaolong 日 期: 2016年12月23日 下午5:03:15 修 改 人: 日 期: 返
	 *            回：Map<String,Object>
	 */
	public static Map<String, Object> openBack(String orderId, String txnTime, String accNo, String cardType,
			String phoneNo, String smsCode) {
		Map<String, String> contentData = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "79"); // 交易类型 11-代收
		contentData.put("txnSubType", "00"); // 交易子类型 00-默认开通
		contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01"); // 账号类型

		// 贷记卡 必送：卡号、手机号、CVN2、有效期；验证码看业务配置（默认不要短信验证码）。
		// 借记卡 必送：卡号、手机号；选送：证件类型+证件号、姓名；验证码看业务配置（默认不要短信验证码）。
		// 此测试商户号777290058110097 后台开通业务只支持 贷记卡
		Map<String, String> customerInfoMap = new HashMap<String, String>();
		// customerInfoMap.put("certifTp", "01"); //证件类型
		// customerInfoMap.put("certifId", "341126197709218366"); //证件号码
		// customerInfoMap.put("customerNm", "全渠道"); //姓名
		customerInfoMap.put("phoneNo", phoneNo); // 手机号
		// customerInfoMap.put("pin", "123456"); //密码【这里如果送密码 商户号必须配置 ”商户允许采集密码“】
		// customerInfoMap.put("cvn2", "123"); //卡背面的cvn2三位数字
		// customerInfoMap.put("expired", "1711"); //有效期 年在前月在后
		customerInfoMap.put("smsCode", smsCode); // 短信验证码

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对
		//////////// accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		String accNo1 = AcpService.encryptData(accNo, BaseSdk.encoding_UTF8); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo1);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, accNo, BaseSdk.encoding_UTF8);

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				// LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				map.put("code", respCode);
				map.put("msg", rspData.get("respMsg"));
				if (("00").equals(respCode)) {
					map.put("success", true);
				} else {
					map.put("success", false);
				}
			} else {
				map.put("code", "");
				map.put("success", false);
				map.put("msg", "验证签名失败");
				// LogUtil.writeErrorLog("验证签名失败");
				// TODO 检查验证签名失败的原因
			}
		} else {
			map.put("code", "");
			map.put("success", false);
			map.put("msg", "未获取到返回报文或返回http状态码非200");
			// 未返回正确的http状态
			// LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		return map;
	}

	/**
	 * 功能描述： 手机生成订单 输入参数: @param money 输入参数: @return 交易流水号 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月6日下午14:50:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> generateOrder(String txnTime, String orderId, String txnAmt) {
		Map<String, String> contentData = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();
		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号 全渠道默认值
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法
		contentData.put("txnType", "01"); // 交易类型 01:消费
		contentData.put("txnSubType", "01"); // 交易子类 01：消费
		contentData.put("bizType", "000201"); // 填写000201
		contentData.put("channelType", "08"); // 渠道类型 08手机

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		contentData.put("accessType", "0"); // 接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构
											// 2：平台商户）
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01"); // 账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
		contentData.put("txnAmt", txnAmt); // 交易金额 单位为分，不能带小数点
		contentData.put("currencyCode", "156"); // 境内商户固定 156 人民币

		// 请求方保留域，
		// 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
		// 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
		// 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
		// contentData.put("reqReserved", "透传信息1|透传信息2|透传信息3");
		// 2. 内容可能出现&={}[]"'符号时：
		// 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
		// 2) 如果对账文件没有显示要求，可做一下base64（如下）。
		// 注意控制数据长度，实际传输的数据长度不能超过1024位。
		// 查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved),
		// DemoBase.encoding);解base64后再对数据做后续解析。
		// contentData.put("reqReserved",
		// Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));

		// 后台通知地址（需设置为外网能访问 http
		// https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		// 后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 网关支付产品接口规范 消费交易 商户通知
		// 注意:1.需设置为外网能访问，否则收不到通知 2.http https均可 3.收单后台通知后需要10秒内返回http200或302状态码
		// 4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5
		// 分钟后会再次通知。
		// 5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d
		// 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", BaseSdk.bankOpenUrl);
		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestAppUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		String tn = "";
		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				if (("00").equals(respCode)) {
					// 成功,获取tn号
					tn = rspData.get("tn");
					map.put("success", true);
					map.put("tn", tn);
					map.put("txnTime", rspData.get("txnTime"));
					map.put("rspData", rspData);
					// TODO
				} else {
					// 其他应答码为失败请排查原因或做失败处理
					map.put("success", false);
					map.put("msg", rspData.get("respMsg"));
					// TODO
				}
			} else {
				LogUtil.writeErrorLog("验证签名失败");
				// TODO 检查验证签名失败的原因
				map.put("code", "");
				map.put("success", false);
				map.put("msg", "验证签名失败");
			}
		} else {
			// 未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			map.put("code", "");
			map.put("success", false);
			map.put("msg", "未获取到返回报文或返回http状态码非200");
		}
		return map;
	}

	/**
	 * 功能描述： 充值 输入参数: @param 银联对象 异 常： 创 建 人: aiqiwu 日 期:2017年4月19日下午17:06:23 修 改 人:
	 * 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> rechargeConsume(UnionPay unionPay) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String txnAmt = ((unionPay.getMoney() * 100) + "").substring(0,
				((unionPay.getMoney() * 100) + "").indexOf("."));
		Map<String, String> contentData = new HashMap<String, String>();
		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法
		contentData.put("txnType", "01"); // 交易类型 01-消费
		contentData.put("txnSubType", "01"); // 交易子类型 01-消费
		contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", unionPay.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", unionPay.getTxnTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("currencyCode", "156"); // 交易币种（境内商户一般是156 人民币）
		contentData.put("txnAmt", txnAmt); // 交易金额，单位分，不要带小数点
		contentData.put("accType", "01"); // 账号类型

		// 消费：交易要素卡号+验证码看业务配置(默认要短信验证码)。
		Map<String, String> customerInfoMap = new HashMap<String, String>();
		customerInfoMap.put("smsCode", unionPay.getSmsCode()); // 短信验证码,测试环境不会真实收到短信，固定填111111

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对
		//////////// accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		String accNo = AcpService.encryptData(unionPay.getCardNo(), BaseSdk.encoding_UTF8); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, unionPay.getCardNo(),
				BaseSdk.encoding_UTF8);
		//////////

		///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		// contentData.put("accNo", "6216261000000000018");
		///////// //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		// String customerInfoStr =
		///////// AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018",AcpService.encoding_UTF8);
		////////

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

		// 后台通知地址（需设置为【外网】能访问 http
		// https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		// 后台通知参数详见open.unionpay.com帮助中心 下载 产品接口规范 代收产品接口规范 代收交易 商户通知
		// 注意:1.需设置为外网能访问，否则收不到通知 2.http https均可 3.收单后台通知后需要10秒内返回http200或302状态码
		// 4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		// 5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d
		// 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", BaseSdk.backUrl);

		// 分期付款用法（商户自行设计分期付款展示界面）：
		// 修改txnSubType=03，增加instalTransInfo域
		// 【测试环境】固定使用测试卡号6221558812340000，测试金额固定在100-1000元之间，分期数固定填06
		// 【生产环境】支持的银行列表清单请联系银联业务运营接口人索要
		// contentData.put("txnSubType", "03"); //交易子类型 03-分期付款
		// contentData.put("instalTransInfo","{numberOfInstallments=06}");//分期付款信息域，numberOfInstallments期数

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		StringBuffer parseStr = new StringBuffer("");
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				String respCode = rspData.get("respCode");
				if (("00").equals(respCode)) {
					// 交易已受理(不代表交易已成功），等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
					// TODO
					// 如果是配置了敏感信息加密，如果需要获取卡号的铭文，可以按以下方法解密卡号
					// String accNo1 = rspData.get("accNo");
					// String accNo2 = AcpService.decryptData(accNo1, "UTF-8");
					// //解密卡号使用的证书是商户签名私钥证书acpsdk.signCert.path
					// LogUtil.writeLog("解密后的卡号："+accNo2);
					// parseStr.append("解密后的卡号："+accNo2);
					ret.put("success", true);
					ret.put("msg", "充值成功");
					ret.put("data", rspData);
				} else if (("03").equals(respCode) || ("04").equals(respCode) || ("05").equals(respCode)) {
					// 后续需发起交易状态查询交易确定交易状态
					// TODO
					ret.put("success", true);
					ret.put("msg", rspData.get("respMsg"));
				} else {
					// 其他应答码为失败请排查原因
					// TODO
					ret.put("success", false);
					ret.put("msg", rspData.get("respMsg"));
				}
			} else {
				// TODO 检查验证签名失败的原因
				ret.put("success", false);
				ret.put("msg", rspData.get("respMsg"));
			}
		} else {
			// 未返回正确的http状态
			ret.put("success", false);
			ret.put("msg", rspData.get("respMsg"));
		}
		return ret;
	}

	/**
	 * 功能描述： 充值获取验证码 输入参数: @param 充值金额 输入参数: @param 卡号 异 常： 创 建 人: aiqiwu
	 * 日期:2017年4月19日下午15:57:11 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> sendConsumeSMSCode(String cardNo, String phoneNo, double money) {
		Map<String, Object> ret = new HashMap<String, Object>();

		String txnTime = com.memory.platform.global.DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
		String txnAmt = ((money * 100) + "").substring(0, ((money * 100) + "").indexOf("."));
		Map<String, String> contentData = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法
		contentData.put("txnType", "77"); // 交易类型 11-代收
		contentData.put("txnSubType", "02"); // 交易子类型 02-消费短信
		contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("currencyCode", "156"); // 交易币种（境内商户一般是156 人民币）
		contentData.put("txnAmt", txnAmt); // 交易金额，单位分，不要带小数点
		contentData.put("accType", "01"); // 账号类型

		// 必送手机号
		Map<String, String> customerInfoMap = new HashMap<String, String>();
		customerInfoMap.put("phoneNo", phoneNo);

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
		String accNo = AcpService.encryptData(cardNo, "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, cardNo, BaseSdk.encoding_UTF8);

		///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		// contentData.put("accNo", "6216261000000000018");
		///////// //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		// String customerInfoStr =
		///////// DemoBase.getCustomerInfo(customerInfoMap,"6216261000000000018",DemoBase.encoding_UTF8);
		////////

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》

		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				String respCode = rspData.get("respCode");
				if (("00").equals(respCode)) {
					// 成功
					// TODO
					ret.put("cardNo", cardNo);
					ret.put("txnTime", rspData.get("txnTime"));
					ret.put("orderId", rspData.get("orderId"));
					ret.put("money", txnAmt);
					ret.put("success", true);
				} else {
					ret.put("success", false);
					ret.put("msg", "短信验证码发送失败。");
				}
			} else {
				ret.put("success", false);
				ret.put("msg", "验证签名失败");
			}
		} else {
			ret.put("success", false);
			ret.put("msg", "未获取到返回报文或返回http状态码非200");
		}
		return ret;
	}

	/**
	 * 功能描述：获取开通银行卡短信 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月20日上午10:29:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> getSMSCode(String cardNo, String phoneNo) {
		Map<String, Object> ret = null;
		String txnTime = com.memory.platform.global.DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
		/**
		 * 组装请求报文
		 */
		Map<String, String> contentData = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); // 签名方法
		contentData.put("txnType", "77"); // 交易类型 77-发送短信
		contentData.put("txnSubType", "00"); // 交易子类型 00-开通短信
		contentData.put("bizType", "000301"); // 业务类型 无跳转
		contentData.put("channelType", "07"); // 渠道类型07-PC

		/*** 商户接入参数 ***/
		contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
		contentData.put("orderId", orderId); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
		contentData.put("txnTime", txnTime); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01"); // 账号类型

		Map<String, String> customerInfoMap = new HashMap<String, String>();
		customerInfoMap.put("phoneNo", phoneNo); // 手机号

		//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
		String accNo1 = AcpService.encryptData(cardNo, BaseSdk.encoding_UTF8); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo1);
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, cardNo, BaseSdk.encoding_UTF8);

		///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		// contentData.put("accNo", "6216261000000000018");
		///////// //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		// String customerInfoStr =
		///////// AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018",DemoBase.encoding_UTF8);
		////////

		contentData.put("customerInfo", customerInfoStr);
		// contentData.put("reqReserved", "透传字段");
		// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

		/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																			// acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

		/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
		// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
		if (!rspData.isEmpty()) {
			if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
				String respCode = rspData.get("respCode");
				if (("00").equals(respCode)) {
					// 成功
					// TODO
					ret = new HashMap<String, Object>();
					ret.put("cardNo", cardNo);
					ret.put("phoneNo", phoneNo);
					ret.put("txnTime", rspData.get("txnTime"));
					ret.put("orderId", rspData.get("orderId"));
				}
			}
		}
		return ret;
	}

	/**
	 * 功能描述：新增绑定银行卡 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月10日上午11:36:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static boolean openQueryCard(UnionPay unionPay) {
		boolean ret = false;
		do {
			Map<String, String> contentData = new HashMap<String, String>();

			/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", BaseSdk.version); // 版本号
			contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
																// 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", "01"); // 签名方法
			contentData.put("txnType", "78"); // 交易类型 78-开通查询
			contentData.put("txnSubType", "00"); // 交易子类型 00-根据账号accNo查询(默认）
			contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
			contentData.put("channelType", "07"); // 渠道类型07-PC

			/*** 商户接入参数 ***/
			contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
			contentData.put("orderId", unionPay.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			contentData.put("txnTime", unionPay.getTxnTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			// contentData.put("reqReserved", "透传字段");
			// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

			//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
			String accNo1 = AcpService.encryptData(unionPay.getCardNo(), "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
			contentData.put("accNo", accNo1);
			contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																				// acpsdk.encryptCert.path属性下

			///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
			// contentData.put("accNo", accNo); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号

			/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
			Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																				// acpsdk.backTransUrl
			Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

			/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
			// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
			if (!rspData.isEmpty()) {
				if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
					String respCode = rspData.get("respCode");
					if (("00").equals(respCode))
						ret = true;
				}
			}
		} while (false);
		return ret;
	}

	public static boolean openCard(UnionPay unionPay) {
		boolean ret = false;
		do {
			/**
			 * 组装请求报文
			 */
			Map<String, String> contentData = new HashMap<String, String>();
			/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", BaseSdk.version); // 版本号
			contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
																// 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", "01"); // 签名方法
			contentData.put("txnType", "77"); // 交易类型 77-发送短信
			contentData.put("txnSubType", "00"); // 交易子类型 00-开通短信
			contentData.put("bizType", "000301"); // 业务类型 无跳转
			contentData.put("channelType", "07"); // 渠道类型07-PC

			/*** 商户接入参数 ***/
			contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
			contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			contentData.put("orderId", unionPay.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			contentData.put("txnTime", unionPay.getTxnTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			contentData.put("accType", "01"); // 账号类型

			Map<String, String> customerInfoMap = new HashMap<String, String>();
			customerInfoMap.put("phoneNo", unionPay.getPhoneNo()); // 手机号

			//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo,phoneNo加密使用：
			String accNo1 = AcpService.encryptData(unionPay.getCardNo(), "UTF-8"); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
			contentData.put("accNo", accNo1);
			contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																				// acpsdk.encryptCert.path属性下
			String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, accNo1,
					BaseSdk.encoding_UTF8);

			///////// 如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
			// contentData.put("accNo", "6216261000000000018");
			///////// //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
			// String customerInfoStr =
			///////// AcpService.getCustomerInfo(customerInfoMap,"6216261000000000018",DemoBase.encoding_UTF8);
			////////

			contentData.put("customerInfo", customerInfoStr);
			// contentData.put("reqReserved", "透传字段");
			// //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

			/** 对请求参数进行签名并发送http post请求，接收同步应答报文 **/
			Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl(); // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的
																				// acpsdk.backTransUrl
			Map<String, String> rspData = AcpService.post(reqData, requestBackUrl, BaseSdk.encoding_UTF8); // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

			/** 对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考-------------> **/
			// 应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
			if (!rspData.isEmpty()) {
				if (AcpService.validate(rspData, BaseSdk.encoding_UTF8)) {
					String respCode = rspData.get("respCode");
					if (("00").equals(respCode)) {
						// 成功
						// TODO
						ret = true;
					}
				}
			}
		} while (false);
		return ret;
	}

}
