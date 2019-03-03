package com.memory.platform.module.system.controller;

import java.util.Date;
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
import com.memory.platform.entity.member.Staff;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.IStaffService;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年5月28日 下午4:59:02 
* 修 改 人： 
* 日   期： 
* 描   述： 易林员工详情
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/staff")
public class StaffController extends BaseController {
	
	private static String [] national = {
	                                         "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
	                                         "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族",
	                                         "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
	                                         "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"
	};
	
	@Autowired
	private IStaffService staffService;
	@Autowired
	private IAccountService accountService;
	
	/**
	 * 
	* 功能描述： 用户详情查询
	* 输入参数:  @param staff
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月2日下午1:16:34
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Staff staff,HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		staff.setCompanyId(account.getCompany().getId());
		return this.staffService.getPageStaff(staff, getStart(request), getLimit(request));
	}
	
	/**
	 * 
	* 功能描述： 跳转至用户详情添加页面
	* 输入参数:  @param request
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月2日下午1:34:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(HttpServletRequest request,Model model) {
		Account user = UserUtil.getUser(request);
		List<Account> accounts = accountService.getListByCompanyId(user.getCompany().getId());
		model.addAttribute("accounts", accounts);
		model.addAttribute("edus", staffService.getEduList());
		model.addAttribute("users", staffService.getEduList());
		model.addAttribute("national",national);
		return "/system/staff/add";
	}
	
	/**
	 * 
	* 功能描述： 添加用户详情
	* 输入参数:  @param request
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月2日下午1:34:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String,Object> addStaff(HttpServletRequest request,Staff staff,String accountId) {
		Account user = UserUtil.getUser(request);
		Account curUser = accountService.getAccount(accountId);
		staff.setAdd_user_id(user.getId());
		staff.setCreate_time(new Date());
		staff.setAge(DateUtil.getAge(staff.getBirthday()));
		staff.setName(curUser.getName());
		staff.setAccount(accountService.getAccount(accountId));
		return staffService.save(staff);
	}
	
	/**
	 * 
	* 功能描述： 根据ID跳转到对应的用户详情修改界面
	* 输入参数:  @param request
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月13日上午11:16:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/update", method = RequestMethod.GET)
	protected String viewupdate(HttpServletRequest request,Model model,String id) {
		Staff staff = staffService.getById(id);
		model.addAttribute("staff", staff);
		model.addAttribute("edus", staffService.getEduList());
		model.addAttribute("users", staffService.getEduList());
		model.addAttribute("national",national);
		return "/system/staff/update";
	}
	/**
	 * 
	 * 功能描述： 查询用功详细信息
	 * 输入参数:  @param request
	 * 输入参数:  @param model
	 * 输入参数:  @param id
	 * 输入参数:  @return
	 * 异    常： 
	 * 创 建 人: yico-cj
	 * 日    期: 2016年6月13日上午11:16:24
	 * 修 改 人: 
	 * 日    期: 
	 * 返    回：String
	 */
	@RequestMapping(value = "/view/infodetail", method = RequestMethod.GET)
	protected String infodetail(HttpServletRequest request,Model model,String id) {
		Staff staff = staffService.getById(id);
		model.addAttribute("staff", staff);
		return "/system/staff/infodetail";
	}
	
	/**
	 * 
	* 功能描述： 修改用户详情 
	* 输入参数:  @param request
	* 输入参数:  @param staff
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月14日上午9:06:30
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String,Object> updateStaff(HttpServletRequest request,Staff staff) {
		Map<String,Object> ret = staffService.updateStaff(staff);
		return ret;
	}
}
