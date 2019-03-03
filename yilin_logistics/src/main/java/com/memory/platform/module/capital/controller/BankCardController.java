package com.memory.platform.module.capital.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.BankCard.BandStatus;
import com.memory.platform.entity.capital.UnionPay;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.AES;
import com.memory.platform.global.Config;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.global.sdk.SDKConfig;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.capital.service.IUnionPayService;
import com.memory.platform.module.capital.util.RechargeUtil;
import com.memory.platform.module.global.controller.BaseController;

import net.sf.json.spring.web.servlet.view.JsonView;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午6:19:01 修 改 人： 日 期： 描 述： 银行卡管理控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/capital/bankcard")
public class BankCardController extends BaseController {

	@Autowired
	private IBankCardService bankCardService;
	@Autowired
	private IUnionPayService unionPayService;

	/**
	 * 功能描述： 绑定银行卡列表 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: longqibo 日 期: 2016年4月26日下午9:57:21 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		model.addAttribute("list", bankCardService.getAll(account.getId()));
		model.addAttribute("auth",
				StringUtil.isEmpty(account.getIdcard_id()) && StringUtil.isEmpty(account.getCompany().getIdcard_id())
						? "no" : "yes");
		return "/capital/bank/index";
	}

	/**
	 * 功能描述： 绑定银行卡页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: longqibo 日 期: 2016年4月26日下午9:59:14 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		model.addAttribute("user", UserUtil.getUser(request));
		return "/capital/bank/add";
	}

	/**
	 * 功能描述： 绑定银行卡 输入参数: @param bankCard 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月26日下午10:35:21 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(BankCard bankCard, HttpServletRequest request, String orderId, String txnTime,
			String accNo, String smsCode) {
		// bankCard.setAccount(UserUtil.getUser(request));
		// bankCard.setCreate_time(new Date());
		// this.bankCardService.saveBankCard(bankCard);
		// return jsonView(true, SAVE_SUCCESS);
		return RechargeUtil.openBack(orderId, txnTime, accNo, null, bankCard.getMobile(), smsCode);
	}

	@RequestMapping(value = "backBank")
	public void backBank(HttpServletRequest request, HttpServletResponse resp) throws UnsupportedEncodingException {
		String encoding = request.getParameter(com.memory.platform.global.sdk.SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				valideData.put(key, value);
			}
		}

		// 签证成功
		if (AcpService.validate(valideData, encoding)) {
			String orderId = valideData.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
			String respCode = valideData.get("respCode"); // 获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
			System.out.println("----------------------------------------------orderId:" + orderId);
			BankCard bankCard = bankCardService.getBankCardByOrderId(orderId);
			if (respCode.equals("00")) {
				// 开通成功
				bankCard.setBandStatus(BankCard.BandStatus.success);
				bankCardService.updateBankCard(bankCard);
			} else {
				bankCardService.removeBankCard(bankCard);
			}
		}
	}

	@RequestMapping(value = "bandCard")
	public void bandCard(HttpServletRequest req, HttpServletResponse resp, BankCard bankCard) throws IOException {
		String accNo = bankCard.getBankCard();
		String txnTime = DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();

		bankCard.setAccount(UserUtil.getUser(req));
		bankCard.setBandStatus(BankCard.BandStatus.defalut);
		bankCard.setOrderNo(orderId);
		bankCard.setCreate_time(new Date());
		bankCardService.saveBankCard(bankCard);

		Map<String, String> contentData = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		contentData.put("version", BaseSdk.version); // 版本号
		contentData.put("encoding", BaseSdk.encoding_UTF8); // 字符集编码
															// 可以使用UTF-8,GBK两种方式
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

		contentData.put("accNo", AcpService.encryptData(accNo, BaseSdk.encoding_UTF8));
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下

		contentData.put("frontUrl", BaseSdk.frontUrl);

		contentData.put("backUrl", BaseSdk.bankOpenUrl);

		/** 请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面 **/
		Map<String, String> reqData = AcpService.sign(contentData, BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl(); // 获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, reqData, BaseSdk.encoding_UTF8); // 生成自动跳转的Html表单

		resp.getWriter().write(html); // 将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过

	}

	/**
	 * 功能描述：新增绑定银行卡 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月10日上午11:36:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "openQueryCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> openQueryCard(HttpServletRequest req, HttpServletResponse resp,
			@RequestBody UnionPay unionPay) throws IOException {
		boolean isOpen = unionPayService.openQueryCard(unionPay);
		Map<String, Object> map = null;
		if (isOpen) {
			// 写库
			BankCard bankCard = new BankCard();
			bankCard.setAccount(UserUtil.getUser(req));
			bankCard.setBandStatus(BankCard.BandStatus.defalut);
			bankCard.setOrderNo(unionPay.getOrderId());
			bankCard.setOpenBank(unionPay.getOpenBank());
			bankCard.setBankCard(unionPay.getCardNo());
			bankCard.setCreate_time(new Date());
			bankCard.setBandStatus(BandStatus.success);
			bankCard.setMobile(unionPay.getPhoneNo());
			map = bankCardService.saveBankCard(bankCard);
		} else {
			// 处理未开通逻辑
			boolean isSuccess = unionPayService.openCard(unionPay);
			if (isSuccess) {
				// 写库
				BankCard bankCard = new BankCard();
				bankCard.setAccount(UserUtil.getUser(req));
				bankCard.setBandStatus(BankCard.BandStatus.defalut);
				bankCard.setOrderNo(unionPay.getOrderId());
				bankCard.setOpenBank(unionPay.getOpenBank());
				bankCard.setBankCard(unionPay.getCardNo());
				bankCard.setCreate_time(new Date());
				bankCard.setBandStatus(BandStatus.success);
				bankCard.setMobile(unionPay.getPhoneNo());
				map = bankCardService.saveBankCard(bankCard);
			} else {
				// 绑定卡失败
				return jsonView(false, "绑定失败");
			}
		}
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		String msg = map.get("msg").toString();
		return jsonView(success, msg);
	}

	@RequestMapping(value = "directOpenCard", method = RequestMethod.POST)
	@ResponseBody
	public void directOpenCard(HttpServletRequest req, HttpServletResponse resp, String bankCard, String userName,
			String userId, String phoneNo, String cnName) throws IOException {
		if (StringUtil.isEmpty(bankCard) || StringUtil.isEmpty(userName) || StringUtil.isEmpty(userId)
				|| StringUtil.isEmpty(phoneNo) || StringUtil.isEmpty(cnName))
			return;
		UnionPay unionPay = new UnionPay();
		unionPay.setCardNo(bankCard);
		unionPay.setUserName(userName);
		unionPay.setUserId(userId);
		unionPay.setPhoneNo(phoneNo);
		BankCard card = new BankCard();
		card.setAccount(UserUtil.getUser(req));
		card.setOrderNo(unionPay.getOrderId());
		card.setOpenBank(cnName);
		card.setBankCard(unionPay.getCardNo());
		card.setCreate_time(new Date());
		card.setBandStatus(BandStatus.defalut);
		card.setMobile(unionPay.getPhoneNo());
		bankCardService.saveBankCard(card);
		String html = unionPayService.directOpenCard(unionPay);
		resp.setContentType("text/html; charset=" + BaseSdk.encoding_UTF8);
		resp.getWriter().write(html);
	}

	/**
	 * 功能描述：获取开通银行卡短信 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月10日上午11:36:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getSMSCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSMSCode(HttpServletRequest req, HttpServletResponse resp, String cardNo,
			String phoneNo) throws IOException {
		Map<String, Object> map = unionPayService.sendOpenCardSMSCode(cardNo, phoneNo);
		Map<String, Object> ret = new HashMap<>();
		boolean success = map == null ? false : true;
		if (success)
			ret.put("data", map);
		else
			ret.put("msg", "获取验证码失败!");
		ret.put("success", success);
		return ret;
	}

	/**
	 * 功能描述： 删除绑定银行卡 输入参数: @param id 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月26日下午10:02:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> remove(String cardNo) {
		BankCard card = null;
		try {
			card = bankCardService.getBankCardByBankCard(AES.Encrypt(cardNo));
		} catch (Exception e1) {
			return jsonView(false, REMOVE_FAIL);
		}
		if (card == null)
			return jsonView(false, "卡号信息不存在!");
		this.bankCardService.removeBankCard(card);
		return jsonView(true, REMOVE_SUCCESS);
	}

	/**
	 * 功能描述： 检测银行卡信息 输入参数: @param number 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月27日下午12:16:03 修 改 人: 日 期: 返 回：void
	 */
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	@ResponseBody
	public BankCard verify(String number) {
		number = number.replaceAll(" ", "");
		BankCard b = bankCardService.getBankCardByBankCard(number);
		if (null != b) {
			throw new DataBaseException("银行卡已被绑定，请更换新银行卡。");
		}
		BankCard bankCard = bankCardService.verify(number);
		return bankCard;
	}

	@RequestMapping(value = "checkBankCard", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkBankCard(String bankCard) {
		bankCard = bankCard.replaceAll(" ", "");
		BankCard b = null;
		try {
			b = bankCardService.getBankCardByBankCard(AES.Encrypt(bankCard));
		} catch (Exception e1) {
			return false;
		}
		if (null != b) {
			return false;
		}
		try {
			bankCardService.verify(bankCard);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 功能描述： 开通银联支付发送短信
	 * 
	 * @param @param
	 *            phoneNo
	 * @param @param
	 *            accNo
	 * @param @return
	 *            异 常： 创 建 人: xiaolong 日 期: 2016年12月23日 下午4:50:49 修 改 人: 日 期: 返
	 *            回：Map<String,Object>
	 */
	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public Map<String, Object> sendMsg(String phoneNo, String accNo) {
		String txnTime = DateUtil.timeNo();
		String orderId = "RN" + txnTime + AppUtil.random(4).toString();
		return RechargeUtil.openSendMsg(orderId, txnTime, phoneNo, accNo);
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				// System.out.println("ServletUtil类247行 temp数据的键=="+en+"
				// 值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
