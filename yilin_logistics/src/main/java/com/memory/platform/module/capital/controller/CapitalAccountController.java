package com.memory.platform.module.capital.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.ExportExcelUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.capital.service.IMoneyRecordService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;

/**
 * 创 建 人： longqibo 日 期： 2016年4月29日 上午9:54:24 修 改 人： 日 期： 描 述： 资金账户控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/capital/account")
public class CapitalAccountController extends BaseController {

	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IMoneyRecordService moneyRecordService;
	@Autowired
	private IAccountService accountService;

	/**
	 * 功能描述： 资金概括 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: longqibo 日 期: 2016年4月29日上午9:56:23 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		model.addAttribute("account", capitalAccountService.getCapitalAccount(UserUtil.getUser().getId()));
		return "/capital/account/index";
	}

	/**
	 * 功能描述： 开启资金管理 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: longqibo 日 期: 2016年5月13日下午5:01:37 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/opencapital", method = RequestMethod.GET)
	protected String openCapital(Model model, HttpServletRequest request) {
		model.addAttribute("user", UserUtil.getUser(request));
		return "/capital/account/open";
	}

	/**
	 * 功能描述： 分页资金记录 输入参数: @param moneyRecord 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年4月29日上午11:05:28 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(MoneyRecord moneyRecord, HttpServletRequest request) {
		moneyRecord.setAccount(UserUtil.getUser(request));
		return this.moneyRecordService.getPage(moneyRecord, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 设置支付密码 输入参数: @param payPassword 输入参数: @return 异 常： 创 建 人: longqibo
	 * 日 期: 2016年5月13日下午5:08:27 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/setPayPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPayPassword(String payPassword, HttpServletRequest request) {
		Account account = accountService.getAccount(UserUtil.getUser(request).getId());
		if (!account.getAuthentication().equals(Auth.auth)) {
			throw new DataBaseException("请实名认证成功后，才能设置支付密码。");
		}
		if (StringUtil.isEmpty(payPassword)) {
			throw new DataBaseException("支付密码不能为空。");
		}
		if (payPassword.length() != 6) {
			throw new DataBaseException("支付密码长度为6位。");
		}
		if (account.getPassword().equals(AppUtil.md5(payPassword))) {
			throw new DataBaseException("支付密码不能和账号密码相同，请重新设置。");
		}
		if (null != capitalAccountService.getCapitalAccount(account.getId())) {
			throw new DataBaseException("请重新登录。");
		}
		account.setPaypassword(AppUtil.md5(payPassword));
		account.setCapitalStatus(CapitalStatus.open);
		capitalAccountService.savePayPassword(account);
		request.getSession().setAttribute("USER", account);
		return jsonView(true, "设置成功");
	}

	/**
	 * 功能描述： 易林财务用户资金管理 输入参数: @param model 输入参数: @param request 输入参数: @return 异
	 * 常： 创 建 人: longqibo 日 期: 2016年5月26日上午9:52:19 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/osindex", method = RequestMethod.GET)
	protected String osindex(Model model, HttpServletRequest request) {
		return "/capital/account/osindex";
	}

	/**
	 * 功能描述： 易林财务用户资金分页 输入参数: @param moneyRecord 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月26日上午9:53:23 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getOsPage")
	@ResponseBody
	public Map<String, Object> getOsPage(CapitalAccount capitalAccount, HttpServletRequest request) {
		return this.capitalAccountService.getPage(capitalAccount, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 查看用户资金详情 输入参数: @param model 输入参数: @param id 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月26日上午10:51:08 修 改 人: 日 期:
	 * 返 回：String
	 */
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model, String id, HttpServletRequest request) {
		model.addAttribute("vo", capitalAccountService.getCapitalAccountById(id));
		return "/capital/account/look";
	}

	/**
	 * 功能描述： excel导出 输入参数: @param request 输入参数: @param response 输入参数: @param
	 * capitalAccount 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午6:05:00 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String exportexcel(HttpServletRequest request, HttpServletResponse response, CapitalAccount capitalAccount) {
		String fileName = System.currentTimeMillis() + "用户资金";
		String columnNames[] = { "用户帐号", "用户手机号", "用户姓名", "总金额", "可用余额", "冻结资金", "总充值", "总提现", "创建日期", "状态" };
		String keys[] = { "account", "phone", "name", "total", "avaiable", "frozen", "totalrecharge", "totalcash",
				"create_time", "status" };

		List<CapitalAccount> list = capitalAccountService.getList(capitalAccount);
		List<Map<String, Object>> listmap = createExcelRecord(list);

		ExportExcelUtil.createExcel(request, response, fileName, list, listmap, columnNames, keys);
		return null;
	}

	/**
	 * 功能描述： list转换map 输入参数: @param list 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午5:26:11 修 改 人: 日 期: 返 回：List<Map<String,Object>>
	 */
	private List<Map<String, Object>> createExcelRecord(List<CapitalAccount> list) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);

		for (CapitalAccount vo : list) {
			Map<String, Object> exceMap = new HashMap<String, Object>();
			exceMap.put("account", vo.getAccount().getAccount());
			exceMap.put("phone", vo.getAccount().getPhone());
			exceMap.put("name", vo.getAccount().getName());
			exceMap.put("total", vo.getTotal());
			exceMap.put("avaiable", vo.getAvaiable());
			exceMap.put("frozen", vo.getFrozen());
			exceMap.put("totalrecharge", vo.getTotalrecharge());
			exceMap.put("totalcash", vo.getTotalcash());
			exceMap.put("create_time", vo.getCreate_time());
			String status = "";
			if (vo.getAccount().getCapitalStatus().equals(CapitalStatus.open)) {
				status = "正常";
			} else {
				status = "冻结";
			}
			exceMap.put("status", status);
			listmap.add(exceMap);
		}
		return listmap;
	};

	/**
	 * 功能描述： 资金统计页面 输入参数: @param model 输入参数: @param id 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月21日下午2:35:13 修 改 人: 日
	 * 期: 返 回：String
	 */
	@RequestMapping(value = "/view/capitalStatistics", method = RequestMethod.GET)
	protected String getCapitalStatistics(Model model, String id, HttpServletRequest request) {
		Account account = UserUtil.getUser();
		if (account.getCompany().getCompanyType().getName().equals("系统")
				|| account.getCompany().getCompanyType().getName().equals("管理")) {
			model.addAttribute("capital", capitalAccountService.getTotalCapital());
			model.addAttribute("type", "system");
		} else {
			model.addAttribute("capital", capitalAccountService.getTotalCapital(account));
			model.addAttribute("type", "member");
		}
		model.addAttribute("years", DateUtil.getYearQian(3));
		model.addAttribute("yearDay", DateUtil.getYear());
		return "/capital/account/capitalStatistics";
	}

	/**
	 * 功能描述： 资金统计，月统计 输入参数: @param year 统计年份 输入参数: @param model 输入参数: @param id
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月24日上午11:48:09 修 改 人: 日 期: 返 回：String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/capitalMonthStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String, Object> getGoodsMonthStatistics(String year, Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		String accountId = null;
		if (!account.getCompany().getCompanyType().getName().equals("系统")
				&& !account.getCompany().getCompanyType().getName().equals("管理")) {
			accountId = account.getId();
		}
		Map<String, Object> month_map = DateUtil.getMonths(year);
		List<String> months = (List<String>) month_map.get("months");
		Map<String, Object> recharge = moneyRecordService.getAllMoneyRecord(months, accountId, "month", "0");
		Map<String, Object> cash = moneyRecordService.getAllMoneyRecord(months, accountId, "month", "1");
		Map<String, Object> transfer = moneyRecordService.getAllMoneyRecord(months, accountId, "month", "2");
		Map<String, Object> income = moneyRecordService.getAllMoneyRecord(months, accountId, "month", "6");
		Map<String, Object> paid = moneyRecordService.getAllMoneyRecord(months, accountId, "month", "8");
		Map<String, Object> transportSection = moneyRecordService.getAllMoneyRecord(months, accountId, "month", "10");
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> r = new ArrayList<Object>(recharge.values());
		List<Object> c = new ArrayList<Object>(cash.values());
		List<Object> t = new ArrayList<Object>(transfer.values());
		int count = 0;
		for (Object object : t) {
			if (null == object) {
				t.set(count, 0);
			} else {
				t.set(count, 0 - Double.parseDouble(object.toString()));
			}
			count++;
		}
		List<Object> i = new ArrayList<Object>(income.values());
		List<Object> p = new ArrayList<Object>(paid.values());
		List<Object> s = new ArrayList<Object>(transportSection.values());
		Collections.reverse(r);
		Collections.reverse(c);
		Collections.reverse(t);
		Collections.reverse(i);
		Collections.reverse(p);
		Collections.reverse(s);
		List<Object> tp = new ArrayList<Object>();
		for (Object object : p) {
			if (null == object) {
				object = 0;
			}
			object = 0 - Double.parseDouble(object.toString());
			tp.add(object);
		}
		map.put("recharge", r);
		map.put("cash", c);
		map.put("transfer", t);
		map.put("income", i);
		map.put("paid", tp);
		map.put("transportSection", s);
		map.put("xAxis", month_map.get("months_v"));
		map.put("success", true);
		map.put("msg", "统计数据成功！");
		return map;
	}

}
