package com.memory.platform.module.app.account.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.Status;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanySectionService;
import com.memory.platform.module.system.service.IRoleService;
import com.memory.platform.module.system.service.ISendMessageService;

/**
* 创 建 人： longqibo
* 日    期： 2016年7月8日 上午10:11:44 
* 修 改 人： 
* 日   期： 
* 描   述： 员工管理模块APP接口
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/app/staff")
public class StaffManageController extends BaseController {

	@Autowired
	private IAccountService accountService;
	@Autowired
	private ICompanySectionService companySectionService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private ISendMessageService sendMessageService;
	
	/**
	* 功能描述： 获取员工列表
	* 输入参数:  @param accountId
	* 输入参数:  @param start
	* 输入参数:  @param size
	* 输入参数:  @param headers
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月8日上午11:04:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "queryStaffs")
	@ResponseBody
	public Map<String, Object> queryStaffs(String keyword,@RequestHeader HttpHeaders headers,HttpServletRequest request){
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<Map<String, Object>>  list = accountService.getListForMap(account.getCompany().getId(), keyword);
		return jsonView(true, "成功获取员工列表",list);
	}
	
	
	@RequestMapping(value = "getStaffs")
	@ResponseBody
	public Map<String, Object> getStaffs(@RequestHeader HttpHeaders headers,HttpServletRequest request){
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<CompanySection> list = companySectionService.getList(account.getCompany().getId());
		for (CompanySection companySection : list) {
			companySection.setStaffs(accountService.getListForMapById(companySection.getId()));
		}
		return jsonView(true, "成功获取员工列表。",list);
	}
	
	/**
	* 功能描述： 保存员工信息
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月8日上午11:06:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "saveStaffs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveStaffs(Account account,String companySectionId,String roleId,@RequestHeader HttpHeaders headers) {
		Account user = (Account) request.getAttribute(ACCOUNT);
		if(StringUtil.isEmpty(account.getAccount())){
			return jsonView(false, "用户名不能为空！");
		}
		if(StringUtil.isEmpty(companySectionId)){
			return jsonView(false, "请选择组织机构！");
		}
		if(!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)){
			return jsonView(false, "用户名由字母开头及数字或字母组成并且长度为6到16位");
		}
//		if(StringUtil.isEmpty(account.getPassword())){
//			return jsonView(false, "密码不能为空！");
//		}
//		if(!StringUtil.regExp(account.getPassword(), StringUtil.PASSWORD_REG)){
//			return jsonView(false, "密码由字母数字混合组成并且在6到16位之间！");
//		}
		if(StringUtil.isEmpty(roleId)){
			return jsonView(false, "请选择员工角色！");
		}
		if(StringUtil.isEmpty(account.getName())){
			return jsonView(false, "姓名不能为空！");
		}
		if(StringUtil.isEmpty(account.getPhone())){
			return jsonView(false, "手机号码不能为空！");
		}
		if(!StringUtil.regExp(account.getPhone(), StringUtil.MOBILE_REG)){
			return jsonView(false, "手机号码错误！");
		}
		if(account.getStatus() == null){
			return jsonView(false, "状态不能为空！");
		}
		if(null != accountService.checkAccount(account.getAccount())){
			return jsonView(false, "用户名存在！");
		}
		if(null != accountService.checkAccountByPhone(account.getPhone())){
			return jsonView(false, "手机号码已存在！");
		}
		Map<String,Object> map = SMSUtil.checkPhoneCode(account.getPhone(),account.getCode(), "1");
		Boolean flag = (Boolean) map.get("success");
		if(!flag) {
			return jsonView(false, "验证码不正确！");
		}
		account.setCompany(user.getCompany());
		Role role = new Role();
		role.setId(roleId);
		account.setRole(role);
		account.setAdd_user_id(user.getId());
		CompanySection companySection = new CompanySection();
		companySection.setId(companySectionId);
		account.setCompanySection(companySection);
		String password = AppUtil.random(8).toString();
		account.setPassword(password);
		this.accountService.saveUser(account);
		
		String inPoint = "com.memory.platform.module.system.service.impl.AccountService.saveUser";
		String sendMsg = "尊敬的用户您好，您的管理员"+user.getName()+"已为您成功注册易林物流平台账号。您的账号为："+account.getPhone()+"，密码为："+password+"，请及时登录www.ylwuliu.cn修改您的密码。如忘记密码可请管理员重置或登录平台使用密码找回功能找回您的密码。";
		Map<String, Object> map_v = sendMessageService.saveRecordAndSendMessage(account.getPhone(), sendMsg, inPoint);
		if (!Boolean.valueOf(map_v.get("success").toString())) {
			return jsonView(false, "短信提示异常，请联系管理员,添加员工失败。");
		}
		return jsonView(true, "成功添加员工。");
	}
	
	/**
	* 功能描述： 获取商户下的组织机构
	* 输入参数:  @param accountId
	* 输入参数:  @param headers
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月8日上午11:19:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CompanySection>
	 */
	@RequestMapping(value = "getCompanySectionList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCompanySectionList(@RequestHeader HttpHeaders headers){
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<CompanySection> list = companySectionService.getListByCompany(account.getCompany().getId());
		return jsonView(true, "成功获取商户下组织机构列表",list);
	}
	
	/**
	* 功能描述： 删除员工信息
	* 输入参数:  @param accountId
	* 输入参数:  @param id
	* 输入参数:  @param headers
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月8日上午11:25:30
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "removeStaff", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeStaff(String id,@RequestHeader HttpHeaders headers){
		Account account = (Account) request.getAttribute(ACCOUNT);
		Account user = accountService.getAccount(id);
		if(null == user){
			return jsonView(false, "参数错误，没有该员工。");
		}
		if(!account.getCompany().getId().equals(user.getCompany().getId())){
			return jsonView(false, "不能删除，操作错误。");
		}
		account.setStatus(Status.delete);
		this.accountService.updateAccount(account);
		return jsonView(true, "成功删除员工");
	}
	
	/**
	* 功能描述： 编辑员工信息
	* 输入参数:  @param account
	* 输入参数:  @param headers
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月8日上午11:28:33
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Account account,String companySectionId,String roleId,@RequestHeader HttpHeaders headers) {
		Account user = (Account) request.getAttribute(ACCOUNT);
		if(StringUtil.isEmpty(account.getAccount())){
			throw new DataBaseException("用户名不能为空！");
		}
		if(StringUtil.isEmpty(companySectionId)){
			throw new DataBaseException("请选择组织机构！");
		}
		if(!StringUtil.regExp(account.getAccount(), StringUtil.ACCOUNT_REG)){
			throw new DataBaseException("用户名由小写英文字母开头数字或小写英文字母组成并且长度为6到16位！");
		}
		if(StringUtil.isEmpty(roleId)){
			throw new DataBaseException("请选择员工角色！");
		}
		if(StringUtil.isEmpty(account.getName())){
			throw new DataBaseException("姓名不能为空！");
		}
		if(StringUtil.isEmpty(account.getPhone())){
			throw new DataBaseException("手机号码不能为空！");
		}
		if(!StringUtil.regExp(account.getPhone(), StringUtil.MOBILE_REG)){
			throw new DataBaseException("手机号码错误！");
		}
		if(account.getStatus() == null){
			throw new DataBaseException("状态不能为空！");
		}
		Account prent = this.accountService.getAccount(account.getId());
		if(!account.getPhone().equals(prent.getPhone())){
			Map<String,Object> map = SMSUtil.checkPhoneCode(account.getPhone(),account.getCode(), "1");
			Boolean flag = (Boolean) map.get("success");
			if(!flag) {
				throw new DataBaseException("验证码不正确！");
			}
		}
		prent.setUpdate_time(new Date());
		prent.setUpdate_user_id(user.getId());
		prent.setAccount(account.getAccount());
		prent.setName(account.getName());
		prent.setPhone(account.getPhone());
		prent.setStatus(account.getStatus());
		Role role = new Role();
		role.setId(roleId);
		account.setRole(role);
		prent.setRole(account.getRole());
		CompanySection companySection = new CompanySection();
		companySection.setId(companySectionId);
		prent.setCompanySection(companySection);
		this.accountService.updateUser(prent);
		return jsonView(true, "编辑员工信息成功。");
	}
	
	/**
	* 功能描述： 获取商户下角色列表
	* 输入参数:  @param accountId
	* 输入参数:  @param headers
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月8日上午11:46:27
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getRoleList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRoleList(@RequestHeader HttpHeaders headers){
		Account account = (Account) request.getAttribute(ACCOUNT);
		List<Role> list = roleService.getList(account.getCompany().getCompanyType().getId());
		return jsonView(true, "成功获取商户下角色列表",list);
	}
	
	/**
	* 功能描述： 商户重置密码
	* 输入参数:  @param accountId
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年7月22日下午3:13:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping(value = "resetPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPassWord(String id,@RequestHeader HttpHeaders headers) throws Exception{
		Account account = (Account) request.getAttribute(ACCOUNT);
		Account resetAccount = accountService.getAccount(id);
		if(!account.getCompany().getId().equals(resetAccount.getCompany().getId())){
			throw new BusinessException("参数错误，不能重置密码。");
		}
		return accountService.updateResetPassWord(resetAccount);
	}
	
	@RequestMapping(value = "getCompanySection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCompanySection(String id){
		return jsonView(true, "成功获取组织机构。",companySectionService.getCompanySection(id));
	}
}
