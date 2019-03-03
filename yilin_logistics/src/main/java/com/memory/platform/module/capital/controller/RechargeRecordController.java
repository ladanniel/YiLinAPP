package com.memory.platform.module.capital.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.BankCard.BandStatus;
import com.memory.platform.entity.capital.BankCard.BankType;
import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.capital.RechargeRecord.Status;
import com.memory.platform.entity.capital.UnionPay;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.User;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.AES;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.ExportExcelUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.capital.service.IRechargeRecordService;
import com.memory.platform.module.capital.service.IUnionPayService;
import com.memory.platform.module.capital.util.RechargeUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IPaymentinterfacService;
import com.memory.platform.security.annotation.LoginValidate;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午6:23:54 修 改 人： 日 期： 描 述： 充值记录控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/capital/rechargerecord")
public class RechargeRecordController extends BaseController {

	@Autowired
	private IRechargeRecordService rechargeRecordService;
	@Autowired
	private IPaymentinterfacService paymentinterfacService;
	@Autowired
	private IUnionPayService unionPayService;
	@Autowired
	private IBankCardService bankCardService;

	/**
	 * 功能描述： 充值记录列表 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: longqibo 日 期: 2016年4月28日下午2:57:22 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/capital/recharge/index";
	}

	/**
	 * 功能描述： 列表分页 输入参数: @param rechargeRecord 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年4月28日下午3:11:19 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(RechargeRecord rechargeRecord, HttpServletRequest request, String tradeAccount) {
		if (StringUtil.isEmpty(tradeAccount)) {
			rechargeRecord.setTradeAccount(null);
		}
		rechargeRecord.setAccount(UserUtil.getUser(request));
		return this.rechargeRecordService.getPage(rechargeRecord, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 充值页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: longqibo 日 期: 2016年4月28日下午3:53:17 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		return "/capital/recharge/add";
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
	@LoginValidate(value = false)
	public void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			String respMsg = valideData.get("respMsg");
			if (reqParam.get("txnType").equals("79")) {
				String cardNo = AcpService.decryptData(reqParam.get("accNo"), "UTF-8");
				BankCard bankCard = bankCardService.getBankCardByBankCard(cardNo);
				if (bankCard.getBandStatus() == BandStatus.success)
					return;
				if (respCode.equals("00"))
					bankCard.setBandStatus(BandStatus.success);
				else
					bankCard.setBandStatus(BandStatus.fail);
				bankCardService.updateBankCard(bankCard);
			} else {
				RechargeRecord rechargeRecord = rechargeRecordService.getRechargeRecordByOrderId(orderId);
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

	/**
	 * 在线充值
	 * 
	 * @param request
	 * @param response
	 * @param rechargeRecord
	 * @param bankCard
	 * @param bankName
	 * @param source
	 * @param payPassword
	 * @param cardType
	 * @throws IOException
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, HttpServletResponse response,
			RechargeRecord rechargeRecord, String source, String orderId, String phoneNo, String txnAmt, String txnTime,
			String accNo, String smsCode) throws IOException {
		Account account = UserUtil.getUser(request);

		if (StringUtil.isEmpty(rechargeRecord.getMoney().toString()) || rechargeRecord.getMoney() == 0) {
			throw new BusinessException("请输入金额。");
		}
		if (rechargeRecord.getMoney() > 20000) {
			throw new BusinessException("充值金额不能超过2万。");
		}
		if (rechargeRecord.getMoney() <= 0) {
			throw new BusinessException("请输入正确的金额。");
		}
		String arr[] = source.split("-");
		rechargeRecord.setSourceAccount(arr[0] + "-" + arr[1]);
		rechargeRecord.setSourceType(arr[2]);
		rechargeRecord.setTradeAccount(arr[1]);
		rechargeRecord.setTradeName(arr[0]);
		rechargeRecord.setBankName(arr[0]);

		Map<String, Object> map = RechargeUtil.consume(orderId, txnTime, txnAmt, smsCode, accNo);
		rechargeRecord.setCreate_time(new Date());
		rechargeRecord.setAccount(account);
		if (map.get("success").toString().equals("true")) {
			rechargeRecord.setStatus(Status.waitProcess);
		} else {
			rechargeRecord.setStatus(Status.failed);
		}
		rechargeRecord.setTradeNo(orderId);
		//rechargeRecord.setPaymentinterfac(paymentinterfacService.getPaymentinterfac("1"));
		this.rechargeRecordService.savePay(rechargeRecord);
		return map;
	}

	/**
	 * 功能描述： 易林财务充值视图 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: longqibo 日 期: 2016年5月25日下午1:19:07 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/osindex", method = RequestMethod.GET)
	protected String osindex(Model model, HttpServletRequest request) {
		return "/capital/recharge/osindex";
	}

	/**
	 * 功能描述：易林财务充值分页 输入参数: @param rechargeRecord 输入参数: @param request
	 * 输入参数: @param tradeAccount 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月25日下午1:18:47 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/getOsPage")
	@ResponseBody
	public Map<String, Object> getOsPage(RechargeRecord rechargeRecord, HttpServletRequest request,
			String tradeAccount) {
		return this.rechargeRecordService.getPage(rechargeRecord, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 查看充值详情 输入参数: @param model 输入参数: @param id 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月25日下午1:50:22 修 改 人: 日 期: 返
	 * 回：String
	 */
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model, String id, HttpServletRequest request) {
		model.addAttribute("vo", rechargeRecordService.getRechargeRecordById(id));
		return "/capital/recharge/look";
	}

	/**
	 * 功能描述： 导出Excel文件 输入参数: @param request 输入参数: @param response 输入参数: @param
	 * rechargeRecord 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午5:25:50 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String exportexcel(HttpServletRequest request, HttpServletResponse response, RechargeRecord rechargeRecord) {
		String fileName = System.currentTimeMillis() + "充值记录";
		String columnNames[] = { "交易流水号", "充值用户账号", "充值用户手机号", "充值用户姓名", "充值类型", "充值来源名称(银行或支付平台)", "充值来源账号(卡号或支付账号)",
				"充值金额", "充值日期", "状态" };
		String keys[] = { "tradeNo", "account", "phone", "name", "sourceType", "tradeName", "tradeAccount", "money",
				"create_time", "status" };

		List<RechargeRecord> list = rechargeRecordService.getList(rechargeRecord);
		List<Map<String, Object>> listmap = createExcelRecord(list);

		ExportExcelUtil.createExcel(request, response, fileName, list, listmap, columnNames, keys);
		return null;
	}

	/**
	 * 功能描述： list转换map 输入参数: @param list 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午5:26:11 修 改 人: 日 期: 返 回：List<Map<String,Object>>
	 */
	private List<Map<String, Object>> createExcelRecord(List<RechargeRecord> list) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);

		for (RechargeRecord vo : list) {
			Map<String, Object> exceMap = new HashMap<String, Object>();
			exceMap.put("tradeNo", vo.getTradeNo());
			exceMap.put("account", vo.getAccount().getAccount());
			exceMap.put("phone", vo.getAccount().getPhone());
			exceMap.put("name", vo.getAccount().getName());
			exceMap.put("sourceType", vo.getSourceType());
			exceMap.put("tradeName", vo.getTradeName());
			exceMap.put("tradeAccount", vo.getTradeAccount());
			exceMap.put("money", vo.getMoney());
			exceMap.put("create_time", vo.getCreate_time());
			String status = "";
			if (vo.getStatus().equals(Status.success)) {
				status = "成功";
			} else if (vo.getStatus().equals(Status.failed)) {
				status = "失败";
			} else {
				status = "等待处理";
			}
			exceMap.put("status", status);
			listmap.add(exceMap);
		}
		return listmap;
	}

	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public Map<String, Object> sendMsg(HttpServletRequest request, double money, String phoneNo, String accNo) {
		String txnTime = DateUtil.timeNo();
		String orderId = "RN" + txnTime + AppUtil.random(4).toString();
		String txnAmt = ((money * 100) + "").substring(0, ((money * 100) + "").indexOf("."));
		return RechargeUtil.sendMsg(orderId, txnTime, txnAmt, phoneNo, accNo);
	}

	/**
	 * 功能描述： 充值获取验证码 输入参数: @param 充值金额 输入参数: @param 卡号 异 常： 创 建 人: aiqiwu 日
	 * 期:2017年4月13日下午14:35:11 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/sendConsumeSMSCode")
	@ResponseBody
	public Map<String, Object> sendConsumeSMSCode(HttpServletRequest request, double money, String accNo) {
		BankCard bankCard;
		try {
			bankCard = bankCardService.getBankCardByBankCard(AES.Encrypt(accNo));
		} catch (Exception e) {
			throw new DataBaseException("银行卡加密失败。");
		}
		Account account = UserUtil.getUser(request);
		if (account == null) {
			throw new DataBaseException("用户未登录。");
		}
		if (!account.getId().equals(bankCard.getAccount().getId())) {
			throw new DataBaseException("非法数据");
		}
		// if(bankCard.getAccount())
		Map<String, Object> map = unionPayService.sendConsumeSMSCode(accNo, bankCard.getMobile(), money);
		Map<String, Object> ret = new HashMap<>();
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		if (success)
			ret.put("data", map);
		else
			ret.put("msg", map.get("msg"));
		ret.put("success", success);
		return ret;
	}

	/**
	 * 功能描述： 充值 输入参数: @param 银联对象 异 常： 创 建 人: aiqiwu 日 期:2017年4月13日下午14:36:23 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/rechargeConsume")
	@ResponseBody
	public Map<String, Object> rechargeConsume(HttpServletRequest request, @RequestBody UnionPay unionPay) {
		Map<String, Object> map = unionPayService.rechargeConsume(unionPay);
		Map<String, Object> ret = new HashMap<>();
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		if (success) {
			Map<String, String> rspData = (Map<String, String>) map.get("data");
			if (rspData == null)
				throw new DataBaseException("充值返回参数异常!");
			ret.put("data", rspData);
			String queryId = rspData.get("queryId");
			Account account = UserUtil.getUser(request);
			BankCard bankCard;
			try {
				bankCard = bankCardService.getBankCardByBankCard(AES.Encrypt(unionPay.getCardNo()));
			} catch (Exception e) {
				throw new DataBaseException("银行卡加密失败。");
			}
			RechargeRecord rechargeRecord = new RechargeRecord();
			// 处理交易逻辑
			rechargeRecord.setSourceAccount(account.getId());
			rechargeRecord.setSourceType(bankCard.getBankType() == BankType.creditCard ? "信用卡" : "储蓄卡");
			rechargeRecord.setTradeAccount(unionPay.getCardNo());
			rechargeRecord.setTradeName(bankCard.getCnName());
			rechargeRecord.setBankName(bankCard.getBankName());
			rechargeRecord.setCreate_time(new Date());
			rechargeRecord.setAccount(account);
			if (map.get("success").toString().equals("true")) {
				rechargeRecord.setStatus(Status.waitProcess);
			} else {
				rechargeRecord.setStatus(Status.failed);
			}
			rechargeRecord.setTradeNo(unionPay.getOrderId());
			//rechargeRecord.setPaymentinterfac(paymentinterfacService.getPaymentinterfac("1"));
			double txnAmt = unionPay.getMoney() / 100;
			// rechargeRecord.setRemark("充值了" + txnAmt);
			rechargeRecord.setMoney(txnAmt);
			rechargeRecord.setQuery_id(queryId.isEmpty() ? "" : queryId);
			this.rechargeRecordService.savePay(rechargeRecord);
		} else
			ret.put("msg", map.get("msg"));
		ret.put("success", success);
		return ret;
	}
}
