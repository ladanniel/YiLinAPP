package com.memory.platform.module.capital.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.CashApplication;
import com.memory.platform.entity.capital.CashApplication.Status;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.ExportExcelUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.capital.service.ICashApplicationService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IParameterManageService;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午6:21:23 修 改 人： 日 期： 描 述： 提现申请控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/capital/cashapplication")
public class CashApplicationController extends BaseController {

	@Autowired
	private ICashApplicationService cashApplicationService;
	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IBankCardService bankCardService;
	@Autowired
	private IParameterManageService parameterManageService;

	/**
	 * 功能描述：提现记录列表 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: longqibo 日 期: 2016年4月28日下午5:44:37 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/capital/cash/index";
	}

	/**
	 * 功能描述： 提现申请页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: longqibo 日 期: 2016年4月28日下午5:45:50 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		model.addAttribute("capitalAccount", capitalAccountService.getCapitalAccount(UserUtil.getUser().getId()));
		List<BankCard> lstBankCard = bankCardService.getAll(UserUtil.getUser().getId());
		model.addAttribute("lstBankCard", lstBankCard);
		return "/capital/cash/add";
	}

	/**
	 * 功能描述： 列表分页 输入参数: @param rechargeRecord 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年4月28日下午3:11:19 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(CashApplication cashApplication, HttpServletRequest request) {
		cashApplication.setAccount(UserUtil.getUser(request));
		Map<String, Object> ret = this.cashApplicationService.getPage(cashApplication, getStart(request),
				getLimit(request));
		return ret;
	}

	/**
	 * 功能描述： 添加提现申请 输入参数: @param request 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月28日下午8:54:33 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(CashApplication cashApplication, HttpServletRequest request, String payPassword) {
		Account account = UserUtil.getUser(request);
		if (StringUtil.isEmpty(payPassword)) {
			throw new BusinessException("请输入支付密码。");
		}
		if (!account.getPaypassword().equals(AppUtil.md5(payPassword))) {
			throw new BusinessException("请输入正确的支付密码。");
		}
		if (cashApplication.getMoney() == 0 || StringUtil.isEmpty(cashApplication.getMoney().toString())) {
			throw new BusinessException("请输入正确的金额。");
		}
		if (cashApplication.getBankcard().equals("no")) {
			throw new BusinessException("请绑定银行卡后再申请提现。");
		}
		if (cashApplication.getMoney() <= 0) {
			throw new BusinessException("请输入正确的金额。");
		}
		BankCard bankcard = bankCardService.getBankCard(cashApplication.getBankcard());
		if (null == bankcard) {
			throw new BusinessException("请绑定银行卡后再申请提现。");
		}
		cashApplication.setAccount(account);
		cashApplication.setBankcard(bankcard.getBankCard());
		cashApplication.setBankname(bankcard.getCnName());
		cashApplication.setCreate_time(new Date());
		cashApplication.setOpenbank(bankcard.getOpenBank());
		cashApplication.setStatus(Status.waitProcess);
		cashApplication.setTradeNo("CN" + DateUtil.dateNo());
		cashApplicationService.saveCashApplication(cashApplication);
		return jsonView(true, "提现申请提交成功");

	}

	/**
	 * 功能描述： 管理员查看提现申请页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异
	 * 常： 创 建 人: longqibo 日 期: 2016年5月24日上午9:50:39 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/osindex", method = RequestMethod.GET)
	protected String osIndex(Model model, HttpServletRequest request) {
		return "/capital/cash/osindex";
	}

	/**
	 * 功能描述：提现记录日志 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: longqibo 日 期: 2016年8月31日下午5:18:17 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/osindexlog", method = RequestMethod.GET)
	protected String osLogIndex(Model model, HttpServletRequest request) {
		return "/capital/cash/osindexlog";
	}

	/**
	 * 功能描述： 管理员查看提现申请分页列表 输入参数: @param cashApplication 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月24日上午9:51:43 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getOsPage")
	@ResponseBody
	public Map<String, Object> getOsPage(CashApplication cashApplication, HttpServletRequest request) {
		Integer day = (int) parameterManageService.getTypeInfo(ParameterManage.ParaType.withdrawal).getValue();
		cashApplication.setDay(day);
		return this.cashApplicationService.getOsPage(cashApplication, getStart(request), getLimit(request));
	}

	@RequestMapping("/getOsLogPage")
	@ResponseBody
	public Map<String, Object> getOsLogPage(CashApplication cashApplication, HttpServletRequest request) {
		return this.cashApplicationService.getOsLogPage(cashApplication, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 审核信息 输入参数: @param model 输入参数: @param cashId 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月24日上午10:43:59 修 改 人: 日 期:
	 * 返 回：String
	 */
	@RequestMapping(value = "/view/verify", method = RequestMethod.GET)
	protected String verify(Model model, String cashId, HttpServletRequest request) {
		model.addAttribute("cash", cashApplicationService.getCashById(cashId));
		return "/capital/cash/verify";
	}

	/**
	 * 功能描述： 查看详情 输入参数: @param model 输入参数: @param cashId 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月24日下午3:25:21 修 改 人: 日 期: 返
	 * 回：String
	 */
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model, String cashId, HttpServletRequest request) {
		model.addAttribute("cash", cashApplicationService.getCashById(cashId));
		return "/capital/cash/look";
	}

	/**
	 * 功能描述： 提现审核 输入参数: @param model 输入参数: @param cashId 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月24日上午10:44:27 修 改 人: 日 期:
	 * 返 回：String
	 */
	@RequestMapping(value = "/verify")
	@ResponseBody
	protected Map<String, Object> verify(Model model, CashApplication cashApplication, HttpServletRequest request) {
		CashApplication application = cashApplicationService.getCashById(cashApplication.getId());
		if (!application.getStatus().equals(Status.lock)) {
			throw new BusinessException("请选择要审核的数据进行锁定后操作。");
		}
		application.setStatus(cashApplication.getStatus());
		application.setVerifytime(new Date());
		application.setVerifytPeopson(UserUtil.getUser(request).getName());
		application.setUpdate_user_id(UserUtil.getUser(request).getId());
		application.setRemark(cashApplication.getRemark());
		boolean success = true;
		String msg = "审核成功";
		try {
			cashApplicationService.updateCashApplication(application);
		} catch (Exception e) {
			// TODO: handle exception
			success =false;
			msg = e.getMessage();
		}
		
		return jsonView(success, msg);
	}

	/**
	 * 功能描述： 信息锁定 输入参数: @param model 输入参数: @param cashApplication 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月24日上午11:45:44 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/lock")
	@ResponseBody
	protected Map<String, Object> lock(Model model, CashApplication cashApplication, HttpServletRequest request) {
		CashApplication application = cashApplicationService.getCashById(cashApplication.getId());
		if (!application.getStatus().equals(Status.waitProcess)) {
			throw new BusinessException("请选择等待处理中的数据进行锁定，其它状态下的数据无法锁定。");
		}
		application.setStatus(Status.lock);
		cashApplicationService.updateLock(application);
		return jsonView(true, "锁定成功");
	}

	/**
	 * 功能描述： 导出Excel文件 输入参数: @param request 输入参数: @param response 输入参数: @param
	 * rechargeRecord 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午5:25:50 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String exportexcel(HttpServletRequest request, HttpServletResponse response, CashApplication cashApplication,
			String type) {
		if (type.equals("verify")) {
			Integer day = (int) parameterManageService.getTypeInfo(ParameterManage.ParaType.withdrawal).getValue();
			cashApplication.setDay(day);
		}
		String fileName = System.currentTimeMillis() + "提现记录";
		String columnNames[] = { "交易流水号", "提现用户账号", "提现用户手机号", "提现用户姓名", "银行卡号", "开户行名称", "提现金额", "实际到账金额", "审核人",
				"提现申请时间", "提现处理时间", "状态", "备注" };
		String keys[] = { "tradeNo", "account", "phone", "name", "bankCard", "openBank", "money", "actualMoney",
				"persion", "create_time", "verifytime", "status", "remark" };

		List<CashApplication> list = cashApplicationService.getList(cashApplication, type);
		List<Map<String, Object>> listmap = createExcelRecord(list);

		ExportExcelUtil.createExcel(request, response, fileName, list, listmap, columnNames, keys);
		return null;
	}

	/**
	 * 功能描述： list转换map 输入参数: @param list 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午5:26:11 修 改 人: 日 期: 返 回：List<Map<String,Object>>
	 */
	private List<Map<String, Object>> createExcelRecord(List<CashApplication> list) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);

		for (CashApplication vo : list) {
			Map<String, Object> exceMap = new HashMap<String, Object>();
			exceMap.put("tradeNo", vo.getTradeNo());
			exceMap.put("account", vo.getAccount().getAccount());
			exceMap.put("phone", vo.getAccount().getPhone());
			exceMap.put("name", vo.getAccount().getName());
			exceMap.put("bankCard", vo.getBankcard());
			exceMap.put("openBank", vo.getOpenbank());
			exceMap.put("money", vo.getMoney());
			exceMap.put("actualMoney", vo.getActualMoney());
			exceMap.put("persion", vo.getVerifytPeopson());
			exceMap.put("create_time", vo.getCreate_time());
			exceMap.put("verifytime", vo.getVerifytime());
			String status = "";
			if (vo.getStatus().equals(Status.success)) {
				status = "通过";
			} else if (vo.getStatus().equals(Status.failed)) {
				status = "未通过";
			} else {
				status = "待处理";
			}
			exceMap.put("status", status);
			exceMap.put("remark", vo.getRemark());
			listmap.add(exceMap);
		}
		return listmap;
	}

}
