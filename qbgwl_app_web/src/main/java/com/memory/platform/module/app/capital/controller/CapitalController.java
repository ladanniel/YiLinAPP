package com.memory.platform.module.app.capital.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.python.antlr.PythonParser.break_stmt_return;
import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.core.AppUtil;
import com.memory.platform.core.BeanKVO;
import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.CashApplication;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.entity.capital.Transfer;
import com.memory.platform.entity.capital.TransferType;
import com.memory.platform.entity.capital.UnionPay;
import com.memory.platform.entity.capital.WeiXinPay;
import com.memory.platform.entity.capital.WeiXinPayCallBack;
import com.memory.platform.entity.capital.BankCard.BandStatus;
import com.memory.platform.entity.capital.BankCard.BankType;
import com.memory.platform.entity.capital.CashApplication.Status;
import com.memory.platform.entity.capital.RechargeDirect;
import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.entity.sys.Bank;
import com.memory.platform.entity.sys.Paymentinterfac;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.AES;
import com.memory.platform.global.Config;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.DoubleHelper;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.global.sdk.SDKConfig;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.capital.dto.BankCardDTO;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.capital.service.ICashApplicationService;
import com.memory.platform.module.capital.service.IMoneyRecordService;
import com.memory.platform.module.capital.service.IRechargeDirectService;
import com.memory.platform.module.capital.service.IRechargeRecordService;
import com.memory.platform.module.capital.service.ITradeSequenceService;
import com.memory.platform.module.capital.service.ITransferService;
import com.memory.platform.module.capital.service.IWeiXinPayCallBackService;
import com.memory.platform.module.capital.util.RechargeUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.solr.service.ISolrSearchService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.IBankService;
import com.memory.platform.module.system.service.IPaymentinterfacService;
import com.memory.platform.module.system.service.ITransferTypeService;
import com.memory.platform.rocketMQ.RocketUtils;
import com.memory.platform.rocketMQ.RocketUtils.TagTopic;
import com.memory.platform.rocketMQ.RocketUtils.onConsumerListener;
import com.memory.platform.rocketMQ.broadcasting.BroadcastProducer;
import com.memory.platform.rocketMQ.broadcasting.BroadcastProducerSurpport;
import com.memory.platform.security.annotation.LoginValidate;

/**
 * 创 建 人： longqibo 日 期： 2016年7月8日 上午10:10:56 修 改 人： 日 期： 描 述： 资金模块app接口 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/app/capital")
public class CapitalController extends BaseController {
	Logger log = Logger.getLogger(CapitalController.class);
	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IBankCardService bankCardService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ICashApplicationService cashApplicationService;
	@Autowired
	private ITradeSequenceService tradeSequenceService;
	@Autowired
	private IRechargeRecordService rechargeRecordService;
	@Autowired
	private ITransferService transferService;
	@Autowired
	private ITransferTypeService transferTypeService;
	@Autowired
	private IMoneyRecordService moneyRecordService;
	@Autowired
	private IBankService bankService;
	@Autowired
	private IRechargeDirectService rechargeDirectService;
	@Resource(name ="rocketMQProucer")
	private BroadcastProducer broadCastProducer;

	/**
	 * 功能描述： 获取用户账户信息 输入参数: @param accountId 输入参数: @param headers 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年7月8日上午10:12:50 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "capitalDetail")
	@ResponseBody
	public Map<String, Object> capitalDetail(@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = capitalAccountService
				.getCapitalForMap(account.getId());
		return jsonView(true, "成功获取用户帐户信息。", map);
	}

	/**
	 * 功能描述： 获取我的银行卡列表 输入参数: @param accountId 输入参数: @param headers 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年7月8日上午10:13:07 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "bankCardDetail")
	@ResponseBody
	public Map<String, Object> bankCardDetail(@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<Map<String, Object>> list = bankCardService.getAllForMap(account
				.getId());
		return jsonView(true, "成功获取银行卡信息。", list);
	}

	/**
	 * 功能描述： 监测银行卡信息 输入参数: @param cardNo 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年7月8日上午10:13:20 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getCardInfo")
	@ResponseBody
	public Map<String, Object> getCardInfo(String cardNo) {
		Map<String, Object> map = null;
		BankCard b = bankCardService.getBankCardByBankCard(cardNo);
		if (null != b) {
			return jsonView(false, "银行卡已被绑定，请更换新银行卡。");
		}
		try {
			map = bankCardService.verifyApp(cardNo);
		} catch (Exception e) {
			return jsonView(false, e.getMessage(), null);
		}
		Map<String, Object> data = (Map<String, Object>) map.get("data");
		if (null != data) {
			Bank bank = bankService.getBankByShortName(data.get("bankName")
					.toString());
			data.put("bank", bank);
		} else {
			return jsonView(false, map.get("msg").toString());
		}
		return jsonView(true, "成功获取银行卡信息。", data);
	}

	/**
	 * 功能描述： 添加银行卡信息 输入参数: @param bankCardNo 输入参数: @param accountId 输入参数: @param
	 * openBank 输入参数: @param mobile 输入参数: @param headers 输入参数: @return 异 常： 创 建
	 * 人: longqibo 日 期: 2016年7月8日上午10:13:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "addBankCard")
	@ResponseBody
	public Map<String, Object> addBankCard(String bankCardNo, String openBank,
			String mobile, @RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);

		String txnTime = DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();

		System.out.println("++++++++++++++++++++++++++" + bankCardNo);
		BankCard bankCard = new BankCard();
		bankCard.setAccount(account);
		bankCard.setBandStatus(BankCard.BandStatus.defalut);
		bankCard.setOrderNo(orderId);
		bankCard.setOpenBank(openBank);
		bankCard.setCreate_time(new Date());
		bankCard.setMobile(mobile);
		bankCard.setBankCard(bankCardNo);
		Map<String, Object> map = bankCardService.saveBankCard(bankCard);
		if (!(boolean) map.get("success")) {
			return map;
		}

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

		contentData.put("accNo", AcpService.encryptData(bankCardNo, "UTF-8"));
		contentData.put("encryptCertId", AcpService.getEncryptCertId()); // 加密证书的certId，配置在acp_sdk.properties文件
																			// acpsdk.encryptCert.path属性下

		contentData.put("frontUrl", BaseSdk.frontUrl);

		contentData.put("backUrl", BaseSdk.bankOpenUrl);

		/** 请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面 **/
		Map<String, String> reqData = AcpService.sign(contentData,
				BaseSdk.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl(); // 获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, reqData,
				BaseSdk.encoding_UTF8); // 生成自动跳转的Html表单

		return jsonView(true, html);
	}

	/**
	 * 功能描述： 移除绑定银行卡 输入参数: @param bankCardId 输入参数: @param accountId 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年7月7日下午4:06:50 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "delBankCrad")
	@ResponseBody
	public Map<String, Object> delBankCrad(String bankCardId,
			@RequestHeader HttpHeaders headers) {
		BankCard bankCard = bankCardService.getBankCard(bankCardId);
		bankCardService.removeBankCard(bankCard);
		return jsonView(true, "成功移除绑定银行卡。");
	}

	/**
	 * 功能描述： 设置交易顺序 输入参数: @param sort 输入参数: @param id 输入参数: @param accountId
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年6月6日下午3:57:57 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "tradeSequence")
	@ResponseBody
	public Map<String, Object> tradeSequence(String sort, String id,
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (StringUtil.isEmpty(sort) || StringUtil.isEmpty(id)) {
			return jsonView(false, "参数错误。");
		}
		TradeSequence tradeSequence = new TradeSequence();
		tradeSequence.setAccount(account);
		if (sort.equals("up")) {
			TradeSequence tradeSequence1 = tradeSequenceService
					.getTradeSequence(id);
			tradeSequence.setSequenceNo(tradeSequence1.getSequenceNo() - 1);
			TradeSequence tradeSequence2 = tradeSequenceService
					.getTradeSequenceByNo(tradeSequence);
			tradeSequence1.setSequenceNo(tradeSequence1.getSequenceNo() - 1);
			tradeSequence2.setSequenceNo(tradeSequence2.getSequenceNo() + 1);
			tradeSequenceService.updateSequence(tradeSequence1, tradeSequence2);
		} else {
			TradeSequence tradeSequence1 = tradeSequenceService
					.getTradeSequence(id);
			tradeSequence.setSequenceNo(tradeSequence1.getSequenceNo() + 1);
			TradeSequence tradeSequence2 = tradeSequenceService
					.getTradeSequenceByNo(tradeSequence);
			tradeSequence1.setSequenceNo(tradeSequence1.getSequenceNo() + 1);
			tradeSequence2.setSequenceNo(tradeSequence2.getSequenceNo() - 1);
			tradeSequenceService.updateSequence(tradeSequence1, tradeSequence2);
		}
		return jsonView(true, "设置成功。");
	}

	/**
	 * 功能描述： 充值 输入参数: @param bankCardId 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年6月6日下午2:32:42 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "topUp")
	@ResponseBody
	public Map<String, Object> topUp(String source, double money,
			String mobile, @RequestHeader HttpHeaders headers, String orderId,
			String txnTime, String accNo, String smsCode) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (money <= 0) {
			return jsonView(false, "请输入正确的充值金额。");
		}
		if (money > 20000) {
			return jsonView(false, "充值金额不能超过2万。");
		}
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMoney(money);
		String arr[] = source.split("-");
		rechargeRecord.setSourceAccount(arr[0] + "-" + arr[1]);
		rechargeRecord.setSourceType(arr[2]);
		rechargeRecord.setTradeAccount(arr[1]);
		rechargeRecord.setTradeName(arr[0]);
		rechargeRecord.setBankName(arr[0]);
		String txnAmt = ((money * 100) + "").substring(0,
				((money * 100) + "").indexOf("."));
		Map<String, Object> map = RechargeUtil.consume(orderId, txnTime,
				txnAmt, smsCode, accNo);
		rechargeRecord.setCreate_time(new Date());
		rechargeRecord.setAccount(account);
		if (map.get("success").toString().equals("true")) {
			rechargeRecord.setStatus(RechargeRecord.Status.waitProcess);
		} else {
			rechargeRecord.setStatus(RechargeRecord.Status.failed);
		}
		rechargeRecord.setTradeNo(orderId);
		Paymentinterfac paymentinterfac = new Paymentinterfac();
		paymentinterfac.setId("1");
		this.rechargeRecordService.savePay(rechargeRecord);
		boolean sucess = (boolean) map.get("success");
		return jsonView(sucess, map.get("msg").toString());
	}

	/**
	 * 功能描述： 提现 输入参数: @param accountId 输入参数: @param money 输入参数: @param
	 * payPassword 输入参数: @param bankCard 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年6月6日下午2:44:56 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "withdraw")
	@ResponseBody
	public Map<String, Object> withdraw(double money, String payPassword,
			String cardNo, @RequestHeader HttpHeaders headers) {
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if (StringUtil.isEmpty(payPassword)) {
			return jsonView(false, "请输入支付密码。");
		}
		if (!account.getPaypassword().equals(AppUtil.md5(payPassword))) {
			return jsonView(false, "请输入正确的支付密码。");
		}
		if (money <= 0) {
			return jsonView(false, "请输入正确的金额。");
		}
		BankCard bankCard;
		try {
			bankCard = bankCardService.getBankCardByBankCard(AES
					.Encrypt(cardNo));
		} catch (Exception e) {
			throw new DataBaseException("银行卡加密失败。");
		}
		if (null == bankCard) {
			return jsonView(false, "请绑定银行卡后再提现。");
		}
		CashApplication cashApplication = new CashApplication();
		cashApplication.setAccount(account);
		cashApplication.setBankcard(bankCard.getBankCard());
		cashApplication.setBankname(bankCard.getCnName());
		cashApplication.setCreate_time(new Date());
		cashApplication.setOpenbank(bankCard.getOpenBank());
		cashApplication.setStatus(Status.waitProcess);
		cashApplication.setTradeNo("CN" + DateUtil.dateNo());
		cashApplication.setMoney(money);
		try {
			cashApplicationService.saveCashApplication(cashApplication);
		} catch (Exception e) {
			return jsonView(false, e.getMessage());
		}

		return jsonView(true, "提现成功。");
	}

	/**
	 * 功能描述： 开通资金账户 输入参数: @param accountId 账号ID 输入参数: @param payPassword 支付密码
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年7月7日上午10:42:23 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "openPayPassword")
	@ResponseBody
	public Map<String, Object> openPayPassword(String payPassword,
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (StringUtil.isEmpty(account.getIdcard_id())) {
			return jsonView(false, "请实名认证成功后，才能设置支付密码。");
		}
		if (StringUtil.isEmpty(payPassword)) {
			return jsonView(false, "支付密码不能为空。");
		}
		if (!StringUtil.regExp(payPassword, "^[0-9]{6}$")) {
			return jsonView(false, "支付密码必须为6为数字。");
		}
		if (account.getPassword().equals(AppUtil.md5(payPassword))) {
			return jsonView(false, "支付密码不能和账号密码相同，请重新设置。");
		}
		if (account.getCapitalStatus().equals(Account.CapitalStatus.open)) {
			return jsonView(false, "已开通资金账户。");
		}
		account.setPaypassword(AppUtil.md5(payPassword));
		account.setCapitalStatus(CapitalStatus.open);
		capitalAccountService.savePayPassword(account);
		request.getSession().setAttribute("USER", account);
		return jsonView(true, "设置成功");
	}

	/**
	 * 功能描述： 获取我的充值记录 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人: longqibo
	 * 日 期: 2016年7月7日下午3:34:33 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getRechargeRecord")
	@ResponseBody
	public Map<String, Object> getRechargeRecord(int start, int size,
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		start = start == 1 ? 0 : size * (start - 1);
		Map<String, Object> map = rechargeRecordService.getListForMap(
				account.getId(), start, size);
		return jsonView(true, "成功获取我的充值记录。", map);
	}

	/**
	 * 功能描述： 获取我的提现记录 输入参数: @param accountId 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年7月7日下午5:47:42 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getCashRecord")
	@ResponseBody
	public Map<String, Object> getCashRecord(int start, int size,
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		start = start == 1 ? 0 : size * (start - 1);
		Map<String, Object> map = cashApplicationService.getListForMap(
				account.getId(), start, size);
		return jsonView(true, "成功获取我的提现记录。", map);
	}

	public static class Model {
		int type;

		/**
		 * @return the type
		 */
		public int getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 */
		public void setType(int type) {
			this.type = type;
		}

	}

	/**
	 * 功能描述： 获取银行卡 输入参数: @param accountId 输入参数: @param type 输入参数: @param headers
	 * 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期: 2017年4月19日下午16:50:04 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "getTradeRechargeList")
	@ResponseBody
	public Map<String, Object> getTradeRechargeList(Model m,
			@RequestHeader HttpHeaders headers) {
		int type = m.type;
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<BankCard> list = bankCardService.getAll(account.getId());
		List<BankCardDTO> dtos = new ArrayList<>();
		for (BankCard cardInfo : list) {
			BankCardDTO cardDTO = new BankCardDTO();
			BeanKVO kvo = new BeanKVO(cardDTO);
			kvo.setValueWithObject(cardInfo);
			dtos.add(cardDTO);
		}
		return jsonView(true, "成功获取我的银行卡列表", dtos);

		// aiqiwu 2017-04-18 注释，不获取账号交易序列，而是绑定的银行卡
		// List<Map<String, Object>> list =
		// tradeSequenceService.getListForMap(account.getId(), type);
		// for (Map<String, Object> map : list) {
		// if (null != map.get("source_account")) {
		// try {
		// map.put("source_account",
		// AES.Decrypt(map.get("source_account").toString()));
		// } catch (Exception e) { // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// Map<String, Object> capital =
		// capitalAccountService.getCapitalForMap(account.getId());
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("data", list);
		// map.put("avaiable", capital.get("avaiable").toString());
		// return jsonView(true, "成功获取我的交易列表。", map);

	}

	/**
	 * 功能描述： 转账业务 输入参数: @param accountId 输入参数: @param payPassword 输入参数: @param
	 * money 输入参数: @param source 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年7月13日上午10:05:44 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "transfer")
	@ResponseBody
	public Map<String, Object> transfer(Transfer transfer, String toaccountId,
			String payPassword, double money, String source,
			String transferTypeId, @RequestHeader HttpHeaders headers) {
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		Account toAccount = null;
		if (money <= 0) {
			return jsonView(false, "请输入正确的金额。");
		}
		transfer.setMoney(money);
		if (StringUtil.isNotEmpty(toaccountId)) {
			toAccount = accountService.getAccount(toaccountId);
			if (null == toAccount) {
				return jsonView(false, "请输入正确的收款帐户。");
			} else if (toAccount.getId().equals(account.getId())) {
				return jsonView(false, "不能给自己帐户转账。");
			}
			if (null == capitalAccountService.getCapitalForMap(toaccountId)) {
				return jsonView(false, "对方账号未开启资金功能，不能转账。");
			}
		} else {
			return jsonView(false, "请输入正确的收款帐户。");
		}
		if (StringUtil.isEmpty(account.getPaypassword())) {
			return jsonView(false, "请先设置支付密码。");
		}
		if (StringUtil.isEmpty(payPassword)) {
			return jsonView(false, "请输入支付密码。");
		}
		if (!account.getPaypassword().equals(AppUtil.md5(payPassword))) {
			return jsonView(false, "请输入正确的支付密码。");
		}
		if (transfer.getMoney() == 0) {
			return jsonView(false, "请输入正确的金额。");
		}
		transfer.setAccount(account);
		transfer.setToAccount(toAccount);
		String[] array = source.split("-");
		transfer.setSourceAccount(array[0] + "-" + array[1]);
		transfer.setSourcType(array[2]);
		transfer.setTradeAccount(array[1]);
		transfer.setTradeName(array[0]);
		if (StringUtil.isEmpty(transferTypeId)) {
			return jsonView(false, "请联系管理员，初始化转账类型。");
		}
		TransferType transferType = new TransferType();
		transferType.setId(transferTypeId);
		transfer.setTransferType(transferType);

		try {
			if (array[2].equals("余额")) {
				// 直接转账
				this.transferService.saveTransfer(transfer);
			} else {
				// 充值后转账
				this.transferService.saveRechargeToTransfer(transfer);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return jsonView(false, e.getMessage());
		}

		return jsonView(true, "转账成功");
	}

	/**
	 * 功能描述： 获取商户转账类型 输入参数: @param accountId 输入参数: @param headers 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年7月13日上午10:38:53 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "getTransferTypes")
	@ResponseBody
	public Map<String, Object> getTransferTypes(
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<Map<String, Object>> list = transferTypeService
				.getTransferTypeForMap(account.getCompany().getCompanyType()
						.getId());
		return jsonView(true, "成功获取商户转账类型。", list);
	}

	/**
	 * 功能描述： 我的转账列表 输入参数: @param accountId 输入参数: @param headers 输入参数: @return 异
	 * 常： 创 建 人: longqibo 日 期: 2016年7月13日下午5:30:38 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "getTransferRecord")
	@ResponseBody
	public Map<String, Object> getTransferRecord(int start, int size,
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		start = start == 1 ? 0 : size * (start - 1);
		Map<String, Object> map = transferService.getListForMap(
				account.getId(), start, size);
		return jsonView(true, "成功获取我的转账记录。", map);
	}

	/**
	 * 功能描述：检测账户信息 输入参数: @param account 输入参数: @param accountId 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年7月13日下午5:56:36 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/getAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccount(String account,
			HttpServletRequest request, @RequestHeader HttpHeaders headers) {
		Account user = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = accountService.getAccountForMap(account);
		if (null == map) {
			return jsonView(false, "账号不存在。");
		}
		if (null == capitalAccountService.getCapitalForMap(map.get("id")
				.toString())) {
			return jsonView(false, "该收款帐户还未开启资金功能，不能转账。");
		}
		if (map.get("id").toString().equals(user.getId())) {
			return jsonView(false, "不能给自己帐户转账。");
		}
		return jsonView(true, "成功获取账户信息。", map);
	}

	@RequestMapping(value = "/getTradeRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getTradeRecord(HttpServletRequest request,
			String type, int start, int size,
			@RequestHeader HttpHeaders headers, Date startTime, Date endTime) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		start = start == 1 ? 0 : size * (start - 1);
		Map<String, Object> map = moneyRecordService.getListForMap(
				account.getId(), type, start, size, startTime, endTime);
		return jsonView(true, "成功获取交易记录。", map);
	}

	@RequestMapping(value = "/setPayPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPayPassword(
			@RequestHeader HttpHeaders headers, String code, String payPassword) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (StringUtil.isEmpty(payPassword)) {
			return jsonView(false, "支付密码不能为空。");
		}
		if (!StringUtil.regExp(payPassword, "^[0-9]{6}$")) {
			return jsonView(false, "支付密码必须为6为数字。");
		}
		Map<String, Object> map = SMSUtil.checkPhoneCode(account.getPhone(),
				code, "2");
		Boolean flag = (Boolean) map.get("success");
		if (!flag) {
			return jsonView(false, "验证码不正确。");
		}
		if (!account.getCapitalStatus().equals(Account.CapitalStatus.open)) {
			return jsonView(true, "您还未开启资金账户功能。");
		}
		account.setPaypassword(AppUtil.md5(payPassword));
		accountService.updateAccount(account);
		return jsonView(true, "成功修改支付密码。");
	}

	/**
	 * 后台银联充值回调地址
	 * 
	 * @param request
	 * @param response
	 * @param rechargeRecord
	 * @throws IOException
	 */
	@RequestMapping(value = "reSuccess")
	@UnInterceptor
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String encoding = request
				.getParameter(com.memory.platform.global.sdk.SDKConstants.param_encoding);
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
			String respMsg = valideData.get("respMsg");
			RechargeRecord rechargeRecord = rechargeRecordService
					.getRechargeRecordByOrderId(orderId);
			if (rechargeRecord.getStatus() == RechargeRecord.Status.success)
				return;
			if (respCode.equals("00")) {
				// 充值成功
				rechargeRecord.setStatus(RechargeRecord.Status.success);
			} else {
				// 充值失败
				rechargeRecord.setStatus(RechargeRecord.Status.failed);
			}
			rechargeRecord.setRemark(respMsg);
			rechargeRecordService.saveRecharge(rechargeRecord);
		}
	}

	@RequestMapping(value = "backBank")
	@UnInterceptor
	public void backBank(HttpServletRequest request, HttpServletResponse resp)
			throws UnsupportedEncodingException {
		String encoding = request
				.getParameter(com.memory.platform.global.sdk.SDKConstants.param_encoding);
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

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, String> getAllRequestParam(HttpServletRequest request) {
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

	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public Map<String, Object> sendMsg(HttpServletRequest request,
			double money, String phoneNo, String accNo) {
		String txnTime = DateUtil.timeNo();
		String orderId = "RN" + txnTime + AppUtil.random(4).toString();
		String txnAmt = ((money * 100) + "").substring(0,
				((money * 100) + "").indexOf("."));
		Map<String, Object> map = RechargeUtil.sendMsg(orderId, txnTime,
				txnAmt, phoneNo, accNo);
		return map;
	}

	@RequestMapping(value = "/success")
	@UnInterceptor
	public String success(Model model, HttpServletRequest request) {
		return "/capital/success";
	}

	/**
	 * 功能描述： 生成订单 输入参数: @param money 输入参数: @return 交易流水号 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月6日下午14:50:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getTranscationNumber")
	@ResponseBody
	public Map<String, Object> getTranscationNumber(double money,
			@RequestHeader HttpHeaders headers) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (money <= 0) {
			return jsonView(false, "请输入正确的充值金额。");
		}
		if (money > 20000) {
			return jsonView(false, "充值金额不能超过2万。");
		}
		String txnTime = DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
		String txnAmt = ((money * 100) + "").substring(0,
				((money * 100) + "").indexOf("."));
		RechargeDirect rd = new RechargeDirect();
		rd.setMoney(money);
		rd.setCreate_time(new Date());
		rd.setAccount(account);
		// 请求银联
		Map<String, Object> map = RechargeUtil.generateOrder(txnTime, orderId,
				txnAmt);
		boolean success = (boolean) map.get("success");
		String msg = "";
		String tn = "";
		if (!success) {
			rd.setStatus(RechargeDirect.Status.failed);
			msg = map.get("msg").toString();
			rd.setRemark("生成金额为(" + txnAmt + ")的订单失败,失败原因:" + msg);
		} else {
			rd.setStatus(RechargeDirect.Status.waitProcess);
			// return jsonView(false, map.get("msg").toString());
			tn = map.get("tn").toString();
			String tt = map.get("txnTime").toString();
			rd.setTradeNo(tn);
			rd.setTxnTime(tt);
			rd.setResponseData(map.get("rspData").toString());
			rd.setRemark("生成金额为(" + txnAmt + ")的订单成功!");
		}

		rd.setOrderId(orderId);
		this.rechargeDirectService.saveRechargeDirect(rd);
		return jsonView(success, success ? "生成订单成功!" : msg, success ? tn : null);
	}

	/**
	 * 功能描述： 充值获取验证码 输入参数: @param 充值金额 输入参数: @param 卡号 异 常： 创 建 人: aiqiwu
	 * 日期:2017年4月19日下午15:57:11 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/sendConsumeSMSCode")
	@ResponseBody
	public Map<String, Object> sendConsumeSMSCode(HttpServletRequest request,
			double money, String accNo) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (account == null) {
			return jsonView(false, "用户尚未登录!");
		}
		if (StringUtil.isEmpty(accNo))
			return jsonView(false, "卡号不能为空!");
		if (money <= 0) {
			return jsonView(false, "请输入正确的充值金额。");
		}
		if (money > 20000) {
			return jsonView(false, "充值金额不能超过2万。");
		}
		BankCard bankCard;
		try {
			bankCard = bankCardService
					.getBankCardByBankCard(AES.Encrypt(accNo));
		} catch (Exception e) {
			throw new DataBaseException("银行卡加密失败。");
		}
		if (!account.getId().equals(bankCard.getAccount().getId())) {
			return jsonView(false, "非法数据。");
		}
		Map<String, Object> map = RechargeUtil.sendConsumeSMSCode(accNo,
				bankCard.getMobile(), money);
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		UnionPay unionPay = null;
		if (success) {
			unionPay = new UnionPay();
			unionPay.setCardNo(map.get("cardNo").toString());
			unionPay.setOrderId(map.get("orderId").toString());
			unionPay.setTxnTime(map.get("txnTime").toString());
			unionPay.setMoney(money);
		}
		return jsonView(success, success ? "成功获取短信验证" : map.get("msg")
				.toString(), unionPay);
	}

	/**
	 * 功能描述： 充值 输入参数: @param 银联对象 异 常： 创 建 人: aiqiwu 日 期:2017年4月19日下午17:06:23 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/rechargeConsume")
	@ResponseBody
	public Map<String, Object> rechargeConsume(HttpServletRequest request,
			String cardNo, String orderId, String smsCode, String txnTime,
			String money) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (account == null) {
			return jsonView(false, "用户尚未登录!");
		}
		if (StringUtil.isEmpty(cardNo))
			return jsonView(false, "卡号不能为空!");
		if (StringUtil.isEmpty(orderId))
			return jsonView(false, "订单号不能为空!");
		if (StringUtil.isEmpty(smsCode))
			return jsonView(false, "验证码不能为空!");
		if (StringUtil.isEmpty(txnTime))
			return jsonView(false, "订单时间不能为空!");
		if (StringUtil.isEmpty(money))
			return jsonView(false, "充值金额不能为空!");
		double my = Double.valueOf(money).doubleValue();
		if (my <= 0) {
			return jsonView(false, "请输入正确的充值金额。");
		}
		if (my > 20000) {
			return jsonView(false, "充值金额不能超过2万。");
		}
		UnionPay unionPay = new UnionPay();
		unionPay.setCardNo(cardNo);
		unionPay.setOrderId(orderId);
		unionPay.setSmsCode(smsCode);
		unionPay.setTxnTime(txnTime);
		unionPay.setMoney(my);
		Map<String, Object> map = RechargeUtil.rechargeConsume(unionPay);
		Map<String, Object> ret = new HashMap<>();
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		if (success) {
			ret.put("data", map.get("data"));
			BankCard bankCard;
			try {
				bankCard = bankCardService.getBankCardByBankCard(AES
						.Encrypt(unionPay.getCardNo()));
			} catch (Exception e) {
				throw new DataBaseException("银行卡加密失败。");
			}
			RechargeRecord rechargeRecord = new RechargeRecord();
			// 处理交易逻辑
			rechargeRecord.setSourceAccount(account.getId());
			rechargeRecord
					.setSourceType(bankCard.getBankType() == BankType.creditCard ? "信用卡"
							: "储蓄卡");
			rechargeRecord.setTradeAccount(unionPay.getCardNo());
			rechargeRecord.setTradeName(bankCard.getCnName());
			rechargeRecord.setBankName(bankCard.getBankName());
			rechargeRecord.setCreate_time(new Date());
			rechargeRecord.setAccount(account);
			if (map.get("success").toString().equals("true")) {
				rechargeRecord
						.setStatus(com.memory.platform.entity.capital.RechargeRecord.Status.waitProcess);
			} else {
				rechargeRecord
						.setStatus(com.memory.platform.entity.capital.RechargeRecord.Status.failed);
			}
			rechargeRecord.setTradeNo(unionPay.getOrderId());
			double txnAmt = unionPay.getMoney();
			// rechargeRecord.setRemark("充值了" + txnAmt);
			rechargeRecord.setMoney(txnAmt);
			this.rechargeRecordService.savePay(rechargeRecord);
		} else
			ret.put("msg", map.get("msg"));
		ret.put("success", success);
		return ret;
	}

	/**
	 * 功能描述：获取开通银行卡短信 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月20日上午10:29:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getSMSCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSMSCode(HttpServletRequest req,
			HttpServletResponse resp, String cardNo, String phoneNo)
			throws IOException {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (account == null) {
			return jsonView(false, "用户尚未登录!");
		}
		if (StringUtil.isEmpty(cardNo))
			return jsonView(false, "卡号不能为空!");
		if (StringUtil.isEmpty(phoneNo))
			return jsonView(false, "手机号不能为空!");
		Map<String, Object> map = RechargeUtil.getSMSCode(cardNo, phoneNo);
		Map<String, Object> ret = new HashMap<>();
		boolean success = map == null ? false : true;
		UnionPay unionPay = null;
		if (success) {
			unionPay = new UnionPay();
			unionPay.setCardNo(map.get("cardNo").toString());
			unionPay.setOrderId(map.get("orderId").toString());
			unionPay.setTxnTime(map.get("txnTime").toString());
			unionPay.setPhoneNo(map.get("phoneNo").toString());
		}
		return jsonView(success, success ? "成功获取短信验证!" : "获取验证码失败!", unionPay);
	}

	/**
	 * 功能描述：新增绑定银行卡 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月10日上午11:36:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "openQueryCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> openQueryCard(HttpServletRequest req,
			HttpServletResponse resp, String cardNo, String orderId,
			String smsCode, String txnTime, String phoneNo) throws IOException {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (account == null) {
			return jsonView(false, "用户尚未登录!");
		}
		if (StringUtil.isEmpty(cardNo))
			return jsonView(false, "卡号不能为空!");
		if (StringUtil.isEmpty(orderId))
			return jsonView(false, "订单号不能为空!");
		if (StringUtil.isEmpty(smsCode))
			return jsonView(false, "验证码不能为空!");
		if (StringUtil.isEmpty(txnTime))
			return jsonView(false, "订单时间不能为空!");
		if (StringUtil.isEmpty(phoneNo))
			return jsonView(false, "手机号不能为空!");
		UnionPay unionPay = new UnionPay();
		unionPay.setCardNo(cardNo);
		unionPay.setOrderId(orderId);
		unionPay.setSmsCode(smsCode);
		unionPay.setTxnTime(txnTime);
		unionPay.setPhoneNo(phoneNo);
		boolean isOpen = RechargeUtil.openQueryCard(unionPay);
		BankCardDTO dto = null;
		if (isOpen) {
			// 写库
			BankCard bankCard = new BankCard();
			bankCard.setAccount((Account) request.getAttribute(ACCOUNT));
			bankCard.setBandStatus(BankCard.BandStatus.defalut);
			bankCard.setOrderNo(unionPay.getOrderId());
			bankCard.setOpenBank(unionPay.getOpenBank());
			bankCard.setBankCard(unionPay.getCardNo());
			bankCard.setCreate_time(new Date());
			bankCard.setBandStatus(BandStatus.success);
			bankCard.setMobile(unionPay.getPhoneNo());
			bankCardService.saveBankCard(bankCard);
			dto = new BankCardDTO();
			BeanKVO kvo = new BeanKVO(dto);
			kvo.setValueWithObject(bankCard);
		} else {
			// 处理未开通逻辑
			boolean isSuccess = RechargeUtil.openCard(unionPay);
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
				bankCardService.saveBankCard(bankCard);
				dto = new BankCardDTO();
				BeanKVO kvo = new BeanKVO(dto);
				kvo.setValueWithObject(bankCard);
			} else {
				// 绑定卡失败
				return jsonView(false, "绑定失败");
			}
		}
		return jsonView(true, "绑定成功", dto);
	}

	/**
	 * aiqiwu 2017-09-18 获取账号资金记录
	 */
	@RequestMapping(value = "getCapitalRecordPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCapitalRecordPage(MoneyRecord moneyRecord,
			int start, int limit) {
		moneyRecord.setAccount(UserUtil.getAccount());
		Map<String, Object> map = this.moneyRecordService.getCapitalRecordPage(
				moneyRecord, start, limit);
		return jsonView(true, "获取资金记录成功", map);
	}

	/**
	 * aiqiwu 2018-03-14 生成微信支付订单
	 */
	@RequestMapping(value = "generatorWeiXinPayOrder", method = RequestMethod.POST)
	@AuthInterceptor(capitalValid=true)
	@ResponseBody
	public Map<String, Object> generatorWeiXinPayOrder(WeiXinPay payInfo) {
		Account account = UserUtil.getAccount();
		if (account == null) {
			return jsonView(false, "用户尚未登录!");
		}
		if (payInfo == null) {
			return jsonView(false, "参数不能为空!");
		}
		if (StringUtils.isEmpty(payInfo.getAppid())) {
			return jsonView(false, "appId不能为空!");
		}
		if (payInfo.getTotal_fee() <= 0) {
			return jsonView(false, "充值金额必须大于0!");
		}
		
		
		if (StringUtils.isEmpty(payInfo.getSpbill_create_ip())) {
			return jsonView(false, "ip地址不能为空");
		}
		if(payInfo.getSpbill_create_ip().equals("an error occurred when obtaining ip address")) {
			payInfo.setSpbill_create_ip(account.getLogin_ip());
		}
		Map<String, Object> map = rechargeRecordService
				.generatorWeiXinOrder(payInfo);
		boolean parseBoolean = Boolean.parseBoolean(map.get("success")
				.toString());
		if(parseBoolean == false ) {
			String msg =(String) map.get("msg");
			
			System.out.println("创建订单失败" + msg);
		}

		return jsonView(parseBoolean, parseBoolean ? "创建订单成功" : "创建订单失败",
				map.get("data"));
	}
	 /*
	  * 微信提现到微信账户上面
	  * */
	@RequestMapping(value = "withdrawWeiXin", method = RequestMethod.POST)
	@AuthInterceptor(capitalValid=true)
	@ResponseBody
	public Map<String, Object> withdrawWeiXin(String weiXinAuthCode,Integer money,String payPassword) {
		 Account account = UserUtil.getAccount();
		 if(account==null) {
			 throw new  BusinessException("账户认证错误");
		 }
		 if(money==null || money==0) {
			 throw new BusinessException("提现金额必须大于0");
		 }
		 double d = money;
		 if(d<=0) {
			 throw new BusinessException("提现金额必须大于0");
		 }
		 Map<String, Object> map = rechargeRecordService
					.withdrawWeiXin(weiXinAuthCode,d,payPassword);
		 
		 
		 return map;
	}
	/*
	 * 获取微信用户信息
	 * */
	 @RequestMapping(value= "getWeiXinUserInfo" ,method=RequestMethod.POST)
	 @ResponseBody
	public Map<String, Object> getWeiXinUserInfo(String weiXinAuthCode) {
		 if(StringUtil.isEmpty(weiXinAuthCode)) {
			 throw new BusinessException("认证码不能为空");
		 }
		return  rechargeRecordService.getWeiXinUserInfo(weiXinAuthCode);
	}
	/**
	 * aiqiwu 2018-03-14 微信支付回调地址
	 * 
	 * @throws InterruptedException
	 * @throws MQBrokerException
	 * @throws RemotingException
	 * @throws MQClientException
	 */
	@RequestMapping(value = "weixinpaycallback")
	@ResponseBody
	@LoginValidate(false)
	@UnInterceptor
	public void weixinpaycallback(@RequestBody WeiXinPayCallBack callBack,
			HttpServletRequest req, HttpServletResponse resp)
			throws MQClientException, RemotingException, MQBrokerException,
			InterruptedException {
		String suc = "SUCCESS";
		String msgResult = "OK";
		do {
			try {
				if (callBack == null) {
					suc = "FAIL";
					msgResult = "format error";
					break;
				}
				String json = JsonPluginsUtil.beanToJson(callBack);
				log.info(json);
				// 判断是否转换xml成功，成功则发送消息并通知消费者

				MessageExt msg = new MessageExt();
				msg.setTopic("recharge");
				msg.setTags("weiXinPay");
				msg.setBody(json.getBytes());

				broadCastProducer.sendMsg(msg);

			} catch (Exception e) {
				suc = "FAIL";
				msgResult = "recve error";
				log.info(e);
			}
		} while (false);
		String rep = String
				.format("<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>",
						suc, msgResult);

		try {
			resp.getOutputStream().write(rep.getBytes("utf8"));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	
}
