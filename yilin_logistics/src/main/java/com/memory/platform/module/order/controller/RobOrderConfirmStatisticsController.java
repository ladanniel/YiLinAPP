package com.memory.platform.module.order.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.order.service.IRobOrderConfirmStatisticsService;
import com.memory.platform.module.system.service.IAccountService;
/**
* 创 建 人： 武国庆
* 日    期： 2016年6月17日 上午10:45:32 
* 修 改 人： 
* 日   期： 
* 描   述：订单统计
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/order/statistics")
public class RobOrderConfirmStatisticsController extends BaseController {

	@Autowired
	private IRobOrderConfirmStatisticsService robOrderConfirmStatisticsService;
	

	@Autowired
	private IAccountService accountService;
	
	/**
	 * 订单详细信息
	* 功能描述： 
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: Administrator
	* 日    期: 2016年7月13日下午1:55:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/robOrderconfirm", method = RequestMethod.GET)
	public String robOrderconfirm(Model model,HttpServletRequest request){
		Account account = UserUtil.getUser();
		if((account.getCompany().getCompanyType().getName().equals("车队"))&&account.getUserType().equals(UserType.company)){//车队管理员
			List<Account> userList = accountService.getListByCompanyId(account.getCompany().getId());
			model.addAttribute("userList", userList);
		}
		if((account.getCompany().getCompanyType().getName().equals("货主"))&&account.getUserType().equals(UserType.company)){//货主管理员
			List<Account> userList = accountService.getListByCompanyId(account.getCompany().getId());
			model.addAttribute("comUserList", userList);
		}
		
		model.addAttribute("years", DateUtil.getYearQian(3));
		model.addAttribute("yearDay", DateUtil.getYear());
		return "/order/statistics/roborderconfirm";
	}
	
	
	/**
	 * 订单详细信息
	* 功能描述： 
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: Administrator
	* 日    期: 2016年7月13日下午1:55:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/robOrderconfirm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> orderconfirm(String turckUserId,Date starDate,Date endDate,Model model,HttpServletRequest request){
		if(starDate==null||endDate==null){
			endDate = DateUtil.getbeforeDate();
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.DATE, -7);
			starDate = calendar.getTime();
		}
		Account account = UserUtil.getUser();
		RobOrderConfirm robOrderConfirm = new RobOrderConfirm();
		robOrderConfirm.setTurckUserId(turckUserId);
		
		if((account.getCompany().getCompanyType().getName().equals("货主"))&&account.getUserType().equals(UserType.company)){//货主管理员
			robOrderConfirm.setRobbedCompanyId(account.getCompany().getId());
		}else if((account.getCompany().getCompanyType().getName().equals("车队"))&&account.getUserType().equals(UserType.company)){//车队管理员
			robOrderConfirm.setCompanyName(account.getCompany().getName());
		}else if((account.getCompany().getCompanyType().getName().equals("货主"))&&account.getUserType().equals(UserType.user)){//货主员工
			robOrderConfirm.setRobbedAccountId(account.getId());
		}else if((account.getCompany().getCompanyType().getName().equals("车队"))&&account.getUserType().equals(UserType.user)){//车队员工
			robOrderConfirm.setTurckUserId(account.getId());
		}
		
		Map dataMap = robOrderConfirmStatisticsService.getRobOrderConfirm(starDate, endDate, robOrderConfirm,null);
		List<Object[]> dataList = (List<Object[]>) dataMap.get("list");
		List<String> xAxis = (List<String>) dataMap.get("dateList");
		
		//重量
		Map weightMap = robOrderConfirmStatisticsService.getRobOrderConfirmWeight(starDate, endDate, robOrderConfirm, null);
		List<Object[]> weightList = (List<Object[]>) weightMap.get("list");
		List<String> xAxisWeight = (List<String>) weightMap.get("dateList");
		
		Map<String, Object>  map = new HashMap<String, Object>();
		map.put("dataList", dataList.get(0));
		map.put("xAxis", xAxis);
		
		map.put("weightList", weightList.get(0));
		map.put("xAxisWeight",xAxisWeight);
		
		return map;
	}
	
	/**
	* 功能描述： 货物统计，月统计
	* 输入参数:  @param year 统计年份
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日上午11:48:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/confirmMonthStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String,Object> getConfirmMonthStatistics(RobOrderRecord robOrderRecord,String year,Model model,HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		
		Map<String, Object> month_map = DateUtil.getMonths(year);
		List<String> months = (List<String>)month_map.get("months");
		List counts = robOrderConfirmStatisticsService.getAllConfirmMonthCount(months, account, "months",robOrderRecord);
		List orderCompletioncounts = robOrderConfirmStatisticsService.getConfirmCompletionMonthCount(months, account, "months",robOrderRecord);
		List weight = robOrderConfirmStatisticsService.getAllConfirmMonthWeight(months, account, "months",robOrderRecord);
		List endWeight = robOrderConfirmStatisticsService.getConfirmCompletionMonthWeight(months, account, "months",robOrderRecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts.get(0));
		map.put("orderCompletioncounts", orderCompletioncounts.get(0));
		map.put("weight", weight.get(0));
		map.put("endWeight", endWeight.get(0));
		map.put("xAxis", month_map.get("months_v"));
		map.put("success", true);
		map.put("msg", "统计数据成功！");
		return map;
	}
	
	
	/**
	* 功能描述： 平台商户发货排名统计
	* 输入参数:  @param year
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午2:56:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/confirmRankingStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String,Object> getConfirmRankingStatistics(String ranking,String type,Model model,HttpServletRequest request) {
		Map<String, Object> month_map = robOrderConfirmStatisticsService.getConfirmRankingStatistics(ranking == null ?0:Integer.parseInt(ranking), type);
		return month_map;
	}

}
