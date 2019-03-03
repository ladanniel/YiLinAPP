package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.memory.platform.entity.global.UpdateColume;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.PhonePlatform;

public interface IAccountService {

	/**
	 * 返回用户实体信息
	 * 
	 * @param userId
	 * @return
	 */
	Account getAccount(String accountId);

	/**
	 * 返回用户实体信息
	 * 
	 * @param userId
	 * @return
	 */
	void updateAccount(Account account);

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pass
	 * @return
	 */
	Account accountLogin(String account, String pass);

	/**
	 * 验证用户是否存在
	 * 
	 * @param account
	 * @param pass
	 * @return
	 */
	Account checkAccount(String account);

	/**
	 * 验证手机惟一性
	 * 
	 * @param account
	 * @param pass
	 * @return
	 */
	Account checkAccountByPhone(String phone);

	Map<String, Object> checkAccountCountByPhone(String phone);

	/**
	 * 
	 * 功能描述：修改用户的名称 输入参数: @param id 输入参数: @param name 输入参数: @return 异 常： 创 建 人:
	 * yico-cj 日 期: 2016年4月29日上午11:37:17 修 改 人: 日 期: 返 回：Map<String,Object>
	 * 
	 * @param request
	 */
	Map<String, Object> editName(String id, String name, HttpServletRequest request);

	/**
	 * 功能描述： 分页用户账号 输入参数: @param account 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月29日下午4:31:38 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	Map<String, Object> getPage(Account account, int start, int limit);

	/**
	 * 
	 * 功能描述： 修改电话号码 输入参数: @param id 输入参数: @param phone 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: yico-cj 日 期: 2016年5月2日下午1:14:12 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	Map<String, Object> editPhone(String id, String phone, String code, String codeType, HttpServletRequest request);

	/**
	 * 
	 * 功能描述： 输入参数: @param id 输入参数: @param password 输入参数: @return 异 常： 创 建 人:
	 * yico-cj 日 期: 2016年5月3日下午3:43:45 修 改 人: 日 期: 返 回：String
	 */
	Map<String, Object> editPassword(String id, String password);

	/**
	 * 功能描述： 保存帐户 输入参数: @param account 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午2:03:16 修 改 人: 日 期: 返 回：void
	 */
	void saveUser(Account account);

	/**
	 * 功能描述： 更新帐户 输入参数: @param account 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午2:04:06 修 改 人: 日 期: 返 回：void
	 */
	void updateUser(Account account);

	/**
	 * 功能描述： 根据账号获取用户对象 输入参数: @param account 输入参数: @return 异 常： 创 建 人: longqibo
	 * 日 期: 2016年5月10日下午1:09:36 修 改 人: 日 期: 返 回：Account
	 */
	Account getAccountByAccount(String account);

	/**
	 * 
	 * 功能描述： 根据验证码验证电话号码是否本人操作 输入参数: @param phone 当前登陆用户手机号 输入参数: @param code
	 * 验证码 输入参数: @param codeType 验证码类型 输入参数: @return 异 常： 创 建 人: yico-cj 日 期:
	 * 2016年5月15日上午11:38:10 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> validPhone(String phone, String code, String codeType);

	/**
	 * 功能描述： 分页查询用户认证信息 输入参数: @param account 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年5月17日下午3:07:24 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getPageAccountAut(Account account, int start, int limit);

	/**
	 * 功能描述： 审核用户信息 输入参数: @param accountId 用户ID 输入参数: @param isfalse
	 * 输入参数: @param failed 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年5月17日下午4:16:18 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> saveAutUser(String accountId, boolean isfalse, String failed);

	/**
	 * 功能描述： 查询平台的所有用户 输入参数: @param account 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月2日上午9:38:23 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getUserAllPage(Account account, int start, int limit);

	/**
	 * 功能描述： 修改用户状态 输入参数: @param updateColume 输入参数: @throws Exception 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月2日下午2:02:14 修 改 人: 日 期: 返 回：void
	 */
	public void updateAccountFiled(UpdateColume updateColume) throws Exception;

	/**
	 * 功能描述： 重置用户密码 输入参数: @param updateColume 输入参数: @throws Exception 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月2日下午2:49:59 修 改 人: 日 期: 返 回：void
	 */
	public Map<String, Object> updateResetPassWord(String id) throws Exception;

	/**
	 * 
	 * 功能描述：获取该商户下没有用户详细信息的所有用户 输入参数: @param companyId 商户ID 输入参数: @return 异 常： 创
	 * 建 人: yico-cj 日 期: 2016年6月7日下午12:56:47 修 改 人: 日 期: 返 回：List<Account>
	 */
	List<Account> getListByCompanyId(String companyId);

	/**
	 * 功能描述： 查询是否存在该角色的用户 输入参数: @param roleId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年6月20日下午2:22:47 修 改 人: 日 期: 返 回：int
	 */
	int getAccountRoleList(String roleId);

	/**
	 * 
	 * 功能描述： 根据token获取用户对象 输入参数: @param token 输入参数: @return 异 常： 创 建 人:
	 * Administrator 日 期: 2016年6月26日上午11:15:43 修 改 人: 日 期: 返 回：Account
	 */
	Account getAccountByToken(String token);

	List<Map<String, Object>> getRoleAppTooleList(String roleId);

	List<Map<String, Object>> getListForMap(String companyId, String keyword);

	List<Map<String, Object>> getListForMapById(String companySectionId);

	Map<String, Object> updateResetPassWord(Account account);

	Map<String, Object> editPhone(String accountId, String phone);

	Map<String, Object> getAccountForMap(String account);

	Map<PhonePlatform, List<Account>> getAccountListWithPhonePlatform(List<String> accountIDS);
	
	/**
	 * aiqiwu 2017-09-01
	 * 获取员工列表
	 * */
	
	List<Map<String,Object>> getEmployeeByCompanyIdAndSectionIdForMap(String companyId,String companySectionId);
}
