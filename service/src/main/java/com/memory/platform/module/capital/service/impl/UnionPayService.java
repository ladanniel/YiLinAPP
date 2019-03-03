package com.memory.platform.module.capital.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.UnionPay;
import com.memory.platform.global.Config;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.global.sdk.SDKConfig;
import com.memory.platform.module.capital.service.IUnionPayService;

@Service
@Transactional
public class UnionPayService implements IUnionPayService {

	@Override
	public boolean openQueryCard(UnionPay unionPay) {
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

	@Override
	public boolean openCard(UnionPay unionPay) {
		boolean ret = false;
		do {
			Map<String, String> contentData = new HashMap<String, String>();
			/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
			contentData.put("version", BaseSdk.version); // 版本号
			contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
																// 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", "01"); // 签名方法
			contentData.put("txnType", "79"); // 交易类型 11-代收
			contentData.put("txnSubType", "00"); // 交易子类型 00-默认开通
			contentData.put("bizType", "000301"); // 业务类型 认证支付2.0
			contentData.put("channelType", "07"); // 渠道类型07-PC

			/*** 商户接入参数 ***/
			contentData.put("merId", Config.merId); // 商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
			contentData.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			contentData.put("orderId", unionPay.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			contentData.put("txnTime", unionPay.getTxnTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			contentData.put("accType", "01"); // 账号类型

			// 贷记卡 必送：卡号、手机号、CVN2、有效期；验证码看业务配置（默认不要短信验证码）。
			// 借记卡 必送：卡号、手机号；选送：证件类型+证件号、姓名；验证码看业务配置（默认不要短信验证码）。
			// 此测试商户号777290058110097 后台开通业务只支持 贷记卡
			Map<String, String> customerInfoMap = new HashMap<String, String>();
			// customerInfoMap.put("certifTp", "01"); //证件类型
			// customerInfoMap.put("certifId", "341126197709218366"); //证件号码
			// customerInfoMap.put("customerNm", "全渠道"); //姓名
			customerInfoMap.put("phoneNo", unionPay.getPhoneNo()); // 手机号
			// customerInfoMap.put("pin", "123456"); //密码【这里如果送密码 商户号必须配置
			// ”商户允许采集密码“】
			// customerInfoMap.put("cvn2", "123"); //卡背面的cvn2三位数字
			// customerInfoMap.put("expired", "1711"); //有效期 年在前月在后
			customerInfoMap.put("smsCode", unionPay.getSmsCode()); // 短信验证码

			//////////// 如果商户号开通了【商户对敏感信息加密】的权限那么需要对
			//////////// accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
			String accNo1 = AcpService.encryptData(unionPay.getCardNo(), BaseSdk.encoding_UTF8); // 这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
			contentData.put("accNo", accNo1);
			contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																				// acpsdk.encryptCert.path属性下
			String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap, null,
					BaseSdk.encoding_UTF8);
			//////////

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
						ret = true;
					}
				}
			}
		} while (false);
		return ret;
	}

	@Override
	public Map<String, Object> sendOpenCardSMSCode(String cardNo, String phoneNo) {
		Map<String, Object> ret = null;
		String txnTime = DateUtil.timeNo();
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

	@Override
	public Map<String, Object> sendConsumeSMSCode(String cardNo, String phoneNo, double money) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String txnTime = DateUtil.timeNo();
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

	@Override
	public Map<String, Object> rechargeConsume(UnionPay unionPay) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String txnAmt = (unionPay.getMoney() + "").substring(0, (unionPay.getMoney() + "").indexOf('.'));
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
	
	@Override
	public String directOpenCard(UnionPay unionPay){
		String txnTime = DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
		/**
		 * 组装请求报文
		 */
		Map<String, String> contentData = new HashMap<String, String>();

		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", BaseSdk.version);                  //版本号
		contentData.put("encoding",BaseSdk.encoding_UTF8);                //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01"); //签名方法
		contentData.put("txnType", "79");                              //交易类型 11-代收
		contentData.put("txnSubType", "00");                           //交易子类型 00-默认开通
		contentData.put("bizType", "000301");                          //业务类型 认证支付2.0
		contentData.put("channelType", "07");                          //渠道类型07-PC
		
		/***商户接入参数***/
		contentData.put("merId", Config.merId);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01");                              //账号类型
		
		//选送卡号、手机号、证件类型+证件号、姓名 
		//也可以都不送,在界面输入这些要素
		//此测试商户号777290058110097 后台开通业务只支持 贷记卡
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		customerInfoMap.put("certifTp", "01");						//证件类型
		customerInfoMap.put("certifId", unionPay.getUserId());		//证件号码
		customerInfoMap.put("customerNm", unionPay.getUserName());					//姓名
		customerInfoMap.put("phoneNo", unionPay.getPhoneNo());			    //手机号

		////////////如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		String accNo = AcpService.encryptData(unionPay.getCardNo(), "UTF-8");  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNo);
		contentData.put("encryptCertId",AcpService.getEncryptCertId());       //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap,null,BaseSdk.encoding_UTF8);
		contentData.put("customerInfo", customerInfoStr);
		//////////
		
		/////////如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		//contentData.put("accNo", "6216261000000000018");
		//String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding_UTF8);   //前台实名认证送支付验证要素 customerInfo中要素不要加密  
		//contentData.put("customerInfo", customerInfoStr);
		////////
		
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//注：如果开通失败的“返回商户”按钮也是触发frontUrl地址，点击时是按照get方法返回的，没有通知数据返回商户
		contentData.put("frontUrl", BaseSdk.frontUrl);
		
		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", BaseSdk.backUrl);
		
		//contentData.put("reqReserved", "透传字段");         				//请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。		
		//contentData.put("reserved", "{customPage=true}");         	//如果开通页面需要使用嵌入页面的话，请上送此用法		
		
		// 订单超时时间。
		// 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
		// 此时间建议取支付时的北京时间加15分钟。
		// 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
		contentData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime() + 15 * 60 * 1000));
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> reqData = AcpService.sign(contentData,BaseSdk.encoding_UTF8);            		 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();   								 //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl,reqData,BaseSdk.encoding_UTF8);
		return html;									    //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
	}
}
