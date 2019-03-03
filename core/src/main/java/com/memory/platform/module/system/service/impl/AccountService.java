package com.memory.platform.module.system.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.core.AppUtil;

import com.memory.platform.entity.global.UpdateColume;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.PhonePlatform;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.member.Staff;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.dao.AccountDao;
import com.memory.platform.module.system.dao.CompanySectionDao;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.system.service.IStaffService;

@Service
@Transactional
public class AccountService implements IAccountService {
	@Autowired
	private ISendMessageService sendMessageService;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CompanySectionDao companySectionDao;
	@Autowired
	private IStaffService staffService;

	public Account userLogin(String account, String pass) {
		Account account2 = this.accountDao.getAccountByAccount(account);
		return account2;
	}

	public Account getAccount(String accountId) {
		return this.accountDao.getAccount(accountId);
	}

	/*
	 * accountLogin
	 */
	@Override
	public Account accountLogin(String account, String pass) {
		// TODO Auto-generated method stub
		Account account2 = this.accountDao.getAccountByAccount(account);
		return account2;
	}

	/*
	 * checkAccount
	 */
	@Override
	public Account checkAccount(String account) {
		// TODO Auto-generated method stub
		Account account2 = this.accountDao.getAccountByAccount(account);
		return account2;
	}

	@Override
	public Map<String, Object> editName(String id, String name, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Account account = accountDao.getAccount(id);
			if (account.getName() != null && !"".equals(account.getName())) {
				map.put("success", false);
				map.put("msg", "抱歉！您已经修改过名称不能在修改。");
				return map;
			}
			account.setName(name);
			accountDao.update(account);
			request.getSession().setAttribute("USER", account);
			map.put("success", true);
			map.put("msg", "恭喜！名称修改成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "抱歉！名称修改失败。请联系管理员");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> getPage(Account account, int start, int limit) {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(account.getCompanySection().getId())) {
			list = companySectionDao.getIds(account.getCompanySection().getId());
		}
		return this.accountDao.getPage(account, list, start, limit);
	}

	@Override
	public Account checkAccountByPhone(String phone) {
		// TODO Auto-generated method stub
		Account account2 = this.accountDao.checkAccountByPhone(phone);
		return account2;
	}

	@Override
	public void updateAccount(Account account) {
		this.accountDao.update(account);
	}

	@Override
	public Map<String, Object> editPhone(String id, String phone, String code, String codeType,
			HttpServletRequest request) {
		Map<String, Object> map = null;

		try {
			map = SMSUtil.checkPhoneCode(phone, code, codeType);
			Boolean flag = (Boolean) map.get("success");
			if (!flag) {
				return map;
			}
			Account account = accountDao.getAccount(id);
			if (phone == null || "".equals(phone)) {
				map.put("success", false);
				map.put("msg", "请输入手机号！");
				return map;
			}
			if (phone.equals(account.getPhone())) {
				map.put("success", false);
				map.put("msg", "绑定手机号不能于原手机号相同！");
				return map;
			}
			if (checkAccountByPhone(phone) != null) {
				map.put("success", false);
				map.put("msg", "抱歉！此手机号已被使用！");
				return map;
			}
			account.setPhone(phone);
			accountDao.update(account);
			request.getSession().setAttribute("USER", account);
			map.put("success", true);
			map.put("msg", "恭喜！手机号修改成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "抱歉！修改失败");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public void saveUser(Account account) {
		account.setLogin_count(0);
		account.setCreate_time(new Date());
		account.setPassword(AppUtil.md5(account.getPassword()));
		account.setAuthentication(Auth.notapply);
		this.accountDao.save(account);
	}

	@Override
	public void updateUser(Account account) {
		this.accountDao.update(account);
	}

	@Override
	public Map<String, Object> editPassword(String id, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Account account = accountDao.getAccount(id);
			// if(account.getPassword().equals(AppUtil.md5(password))) {
			// map.put("success", false);
			// map.put("msg", "抱歉！您的新密码与旧密码一致！");
			// return map;
			// }
			account.setPassword(AppUtil.md5(password));
			accountDao.update(account);
			map.put("success", true);
			map.put("msg", "恭喜！密码修改成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "存在子集组织机构");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Account getAccountByAccount(String account) {
		return accountDao.getAccountByAccount(account);
	}

	@Override
	public Map<String, Object> validPhone(String phone, String code, String codeType) {
		return SMSUtil.checkPhoneCode(phone, code, codeType);
	}

	/*
	 * getPageAccountAut
	 */
	@Override
	public Map<String, Object> getPageAccountAut(Account account, int start, int limit) {
		// TODO Auto-generated method stub
		return accountDao.getPageAccountAut(account, start, limit);
	}

	/*
	 * saveAutUser
	 */
	@Override
	public Map<String, Object> saveAutUser(String accountId, boolean isfalse, String failed) {
		String inPoint = "com.memory.platform.module.system.service.impl.AccountService.saveAutUser";
		Account autAccount = UserUtil.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = accountDao.getObjectById(Account.class, accountId);
		if (account.getAuthentication().equals(Auth.auth)) {
			map.put("success", false);
			map.put("msg", "【" + account.getName() + "】用户已经审核通过，无需重复审核!");
			return map;
		} else if (account.getAuthentication().equals(Auth.supplement)) {
			map.put("success", false);
			map.put("msg", "【" + account.getName() + "】用户正在补录认证信息，还不能进行审核!");
			return map;
		} else if (account.getAuthentication().equals(Auth.notAuth)) {
			map.put("success", false);
			map.put("msg", "【" + account.getName() + "】用户已经审核[未通过]，无需重复审核!");
			return map;
		} else if (account.getAuthentication().equals(Auth.notapply)) {
			map.put("success", false);
			map.put("msg", "【" + account.getName() + "】用户为提交申请，还不能进行审核!");
			return map;
		}
		if (isfalse) {
			account.setAuthentication(Auth.auth);
			account.setFailed_msg(null);
			account.setAut_time(new Date());
			account.setAut_user_id(autAccount.getId());
			accountDao.update(account);
			String sendMsg = "尊敬的用户您好！您所提交的用户认证信息已通过审核，畅享我们的服务吧！";
			Map<String, Object> map_v = sendMessageService.saveRecordAndSendMessage(account.getPhone(), sendMsg,
					inPoint);
			if (!Boolean.valueOf(map_v.get("success").toString())) {
				throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
			}
			map.put("success", true);
			map.put("msg", "【" + account.getName() + "】用户信息审核成功!");
			//刷新当前人员登录信息
			AppUtil.setLoginUser(account);
			
		} else {
			if (StringUtils.isBlank(failed)) {
				map.put("success", false);
				map.put("msg", "请输入未通过的原因!");
			} else {
				String sendMsg = "";
				if (account.getAuthentication().equals(Auth.waitProcessSupplement)) {
					account.setAuthentication(Auth.supplement);
					account.setFailed_msg(
							"<span style='color: red'>您所补录的认证信息，管理员审核【未通过】原因为:</span><span style='color: #1ab394;'>"
									+ failed + "</span>");
					sendMsg = "尊敬的用户您好！您所补录的用户认证信息未通过审核，原因为:" + failed;
				} else {
					sendMsg = "尊敬的用户您好！您所提交的用户认证信息未通过审核，原因为:" + failed;
					account.setAuthentication(Auth.notAuth);
					account.setFailed_msg(failed);
				}
				account.setAut_time(new Date());
				account.setAut_user_id(autAccount.getId());
				accountDao.update(account);
				Map<String, Object> map_v = sendMessageService.saveRecordAndSendMessage(account.getPhone(), sendMsg,
						inPoint);
				if (!Boolean.valueOf(map_v.get("success").toString())) {
					throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
				}
				map.put("success", true);
				map.put("msg", "【" + account.getName() + "】用户信息审核成功!");
				AppUtil.setLoginUser(account);
			}
		}
		return map;
	}

	/*
	 * getUserAllPage
	 */
	@Override
	public Map<String, Object> getUserAllPage(Account account, int start, int limit) {
		// TODO Auto-generated method stub
		return accountDao.getUserAllPage(account, start, limit);
	}

	/*
	 * updateCompanyFiled
	 */
	@Override
	public void updateAccountFiled(UpdateColume updateColume) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String updateHql = " update Account account set account." + updateColume.getField()
				+ " = :filedValue where account.id = :id";
		if (updateColume.getField().equals("status")) {
			Account.Status mytype = Enum.valueOf(Account.Status.class, updateColume.getField_value());
			map.put("filedValue", mytype);
		} else {
			map.put("filedValue", updateColume.getField_value());
		}
		map.put("id", updateColume.getId());
		accountDao.updateHQL(updateHql, map);
	}

	/*
	 * updateResetPassWord
	 */
	@Override
	public Map<String, Object> updateResetPassWord(String id) throws Exception {
		Map<String, Object> value = new HashMap<String, Object>();
		Account account = accountDao.getObjectById(Account.class, id);
		Calendar c = Calendar.getInstance();
		String ps = AppUtil.getrandom(c).toString();
		Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(account.getPhone(),
				"尊敬的用户您好，你的用户为：[" + account.getPhone() + "]，的密码已重置为：" + ps + ",为了你的账户安全，请尽快登陆修改你的密码！",
				"com.memory.platform.module.system.service.impl.AccountService.updateResetPassWord");
		if (Boolean.valueOf(map.get("success").toString())) {
			account.setPassword(AppUtil.md5(ps));
			accountDao.update(account);
			value.put("success", true);
			value.put("msg", "重置密码成功，系统已经把重置的密码，发送在用户的手机上！");
		} else {
			value.put("success", false);
			value.put("msg", "短信发送失败！");
		}
		return value;
	}

	@Override
	public List<Account> getListByCompanyId(String companyId) {
		List<Staff> lists = staffService.getStaffList();
		List<String> ids = new ArrayList<String>();
		for (Staff s : lists) {
			ids.add(s.getAccount().getId());
		}
		return accountDao.getListByCompanyId(companyId, ids);
	}

	/*
	 * getAccountROleList
	 */
	@Override
	public int getAccountRoleList(String roleId) {
		// TODO Auto-generated method stub
		return accountDao.getAccountRoleList(roleId);
	}

	/*
	 * getAccountByToken
	 */
	@Override
	public Account getAccountByToken(String token) {
		Account account =  AppUtil.getAccountWithMemstoreByToken(token);
		if(account!=null) return account;
		return accountDao.getAccountByToken(token);
	}

	/*
	 * getRoleAppTooleList
	 */
	@Override
	public List<Map<String, Object>> getRoleAppTooleList(String roleId) {
		// TODO Auto-generated method stub
		return accountDao.getRoleAppTooleList(roleId);
	}

	@Override
	public List<Map<String, Object>> getListForMap(String companyId, String keyword) {
		return accountDao.getListForMap(companyId, keyword);
	}

	@Override
	public List<Map<String, Object>> getListForMapById(String companySectionId) {
		return accountDao.getListForMapById(companySectionId);
	}

	@Override
	public Map<String, Object> updateResetPassWord(Account account) {
		Map<String, Object> value = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();
		String ps = AppUtil.getrandom(c).toString();
		Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(account.getPhone(),
				"尊敬的用户您好，你的用户为：[" + account.getPhone() + "]，的密码已重置为：" + ps + ",为了你的账户安全，请尽快登陆修改你的密码！",
				"com.memory.platform.module.system.service.impl.AccountService.updateResetPassWord");
		if (Boolean.valueOf(map.get("success").toString())) {
			account.setPassword(AppUtil.md5(ps));
			accountDao.update(account);
			value.put("success", true);
			value.put("msg", "重置密码成功，系统已经把重置的密码，发送在用户的手机上！");
		} else {
			value.put("success", false);
			value.put("msg", "短信发送失败！");
		}
		return value;
	}

	@Override
	public Map<String, Object> editPhone(String accountId, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 验证码判断 测试不用
			/*
			 * CacheManager cacheManager =
			 * CacheManager.create("/config/ehcache.xml"); Cache cacheCode =
			 * cacheManager.getCache("phoneCode"); Element result =
			 * cacheCode.get(account.getPhone());
			 * if(result==null||!account.getCode().equals(result.getObjectValue(
			 * ).toString())){ return jsonView(false, "验证码不正确"); }
			 */

			Account account = accountDao.getAccount(accountId);
			if (account == null || "".equals(account)) {
				map.put("success", false);
				map.put("msg", "用户ID不正确");
				return map;
			}
			if (phone == null || "".equals(phone)) {
				map.put("success", false);
				map.put("msg", "请输入手机号！");
				return map;
			}
			if (phone.equals(account.getPhone())) {
				map.put("success", false);
				map.put("msg", "绑定手机号不能于原手机号相同！");
				return map;
			}
			if (checkAccountByPhone(phone) != null) {
				map.put("success", false);
				map.put("msg", "抱歉！此手机号已被使用！");
				return map;
			}
			account.setPhone(phone);
			accountDao.update(account);
			map.put("success", true);
			map.put("msg", "成功");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "失败");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> getAccountForMap(String account) {
		return accountDao.getAccountForMap(account);
	}

	@Override
	public Map<PhonePlatform, List<Account>> getAccountListWithPhonePlatform(List<String> accountIDS) {

		Map<String, Object> map = new HashMap<>();
		map.put("ids", accountIDS);
		List<Account> accounts = accountDao.getListByHql(" from Account where   id in (:ids)   ", map);
		Map<PhonePlatform, List<Account>> map2 = new HashMap<>();
		for (Account account : accounts) {
			if (map2.containsKey(account.getPhone_platform()) == false) {
				map2.put(account.getPhone_platform(), new ArrayList<Account>());
			}
			List<Account> arr = map2.get(account.getPhone_platform());
			arr.add(account);
		}
		return map2;
	}

	@Override
	public Map<String, Object> checkAccountCountByPhone(String phone) {
		// TODO Auto-generated method stub
		Map<String, Object> map = this.accountDao.checkAccountCountByPhone(phone);
		return map;
	}

	/**
	 * aiqiwu 2017-09-01 获取员工列表
	 */
	@Override
	public List<Map<String,Object>> getEmployeeByCompanyIdAndSectionIdForMap(String  companyId,String companySectionId) {
		return this.accountDao.getEmployeeByCompanyIdAndSectionIdForMap(companyId, companySectionId);
	}
}
