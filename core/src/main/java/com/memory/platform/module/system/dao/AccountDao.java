package com.memory.platform.module.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.Status;
import com.memory.platform.global.Auth;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class AccountDao extends BaseDao<Account> {

	/**
	 * 返回用户实体信息
	 * 
	 * @param userId
	 * @return
	 */
	public Account getAccount(String userId) {
		return this.getObjectById(Account.class, userId);
	}

	public List<Account> getListAccounts() {
		return this.getListByHql(" from Account user");
	}

	/**
	 * 根据帐号返回用户实体信息
	 * 
	 * @param account
	 * @return
	 */
	public Account getAccountByAccount(String account) {
		String sql = " from Account user where (user.account=:account )";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		//map.put("phone", account);
		Account account2 = this.getObjectByColumn(sql, map);
		return account2;
	}

	/**
	 * 根据手机号码返回用户实体信息
	 * 
	 * @param phone
	 * @return
	 */
	public Account checkAccountByPhone(String phone) {
		String sql = " from Account user where user.phone=:phone";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		Account account2 = this.getObjectByColumn(sql, map);
		return account2;
	}

	/**
	 * 功能描述： 用户列表 输入参数: @param account 输入参数: @param list 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午8:13:33 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getPage(Account account, List<String> list, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();

		StringBuffer sql = new StringBuffer();
		sql.append(" from Account account ");
		StringBuffer where = new StringBuffer();
		where.append("where account.company.id = :companyId and account.status <> :status ");

		map.put("companyId", account.getCompany().getId());
		map.put("status", Status.delete);
		if (list.size() > 0) {
			where.append("and account.companySection.id in (:ids) ");
			map.put("ids", list);
		}
		if (StringUtil.isNotEmpty(account.getSearch())) {
			where.append(" and (account.account like :account or " + "account.name like :name or "
					+ "account.phone like :phone)");
			map.put("account", "%" + account.getSearch() + "%");
			map.put("name", "%" + account.getSearch() + "%");
			map.put("phone", "%" + account.getSearch() + "%");
		}
		sql.append(where).append(" order by account.create_time desc");
		return this.getPage(sql.toString(), map, start, limit);
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	public void saveAccount(Account account) {
		this.save(account);
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	public void removeAccount(String accountId) {
		Account account = new Account();
		account.setId(accountId);
		this.delete(account);
	}

	/**
	 * 返回用户角色对应资源列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getAccountResourceList(String accountId) {
		String sql = "select re.* from s_user u, s_role r, s_user_role ur, s_role_menu rm, s_menu m, s_resource re";
		sql += " where u.id=? and u.id=ur.userId and r.id=ur.roleId and m.id=rm.menuId and r.id=rm.roleId";
		sql += " and m.resourceid=re.id";
		return this.getListBySQLMap(sql, new Object[] { accountId });
	}

	/**
	 * 删除用户对应角色
	 * 
	 * @param userId
	 */
	public void removeAccountRole(String accountId) {
		String sql = "delete from s_user_role where userId=?";
		Object[] args = new Object[] { accountId };
		this.updateSQL(sql, args);
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 */
	public void updateAccount(Account account) {
		StringBuffer sql = new StringBuffer();
		StringBuffer field = new StringBuffer();
		field.append(" account=?,name=?, age=?,phone=?,utime=?,status=?");
		List<Object> list = new ArrayList<Object>();
		this.updateSQL(sql.toString(), list.toArray());
	}

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param pass
	 */
	public void updateAccountPass(String accountId, String pass) {
		String sql = "update mem_account set password=? where id=?";
		Object[] args = new Object[] { AppUtil.md5(pass), accountId };
		this.updateSQL(sql, args);
	}

	/**
	 * 功能描述： 查询需要认证用户信息 输入参数: @param account 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年5月17日下午3:10:42 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getPageAccountAut(Account account, int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Account account where account.company.audit = :auditcompany";
		map.put("auditcompany", Auth.auth);
		if (null != account.getSearch() && !"".equals(account.getSearch())) {
			hql += " and account.account like :account or account.phone like :phone or account.name like :name";
			map.put("account", "%" + account.getSearch() + "%");
			map.put("phone", "%" + account.getSearch() + "%");
			map.put("name", "%" + account.getSearch() + "%");
		}
		if (null != account.getAccountAuths() && account.getAccountAuths().length > 0) {
			hql += " and account.authentication in ( :audit )";
			map.put("audit", account.getAccountAuths());
		}
		if (!StringUtil.isEmpty(account.getCompanyName())) {
			hql += " and account.company.name like :companyName";
			map.put("companyName", "%" + account.getCompanyName() + "%");
		}
		hql += " order by account.update_time desc";
		return this.getPage(hql, map, start, limit);
	}

	public Map<String, Object> getUserAllPage(Account account, int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Account account where account.userType != :userType";
		map.put("userType", Account.UserType.admin);
		if (null != account.getSearch() && !"".equals(account.getSearch())) {
			hql += " and account.account like :account or account.phone like :phone or account.name like :name";
			map.put("account", "%" + account.getSearch() + "%");
			map.put("phone", "%" + account.getSearch() + "%");
			map.put("name", "%" + account.getSearch() + "%");
		}
		if (null != account.getAccountAuths() && account.getAccountAuths().length > 0) {
			hql += " and account.authentication in ( :audit )";
			map.put("audit", account.getAccountAuths());
		}
		if (!StringUtil.isEmpty(account.getCompanyName())) {
			hql += " and account.company.name like :companyName";
			map.put("companyName", "%" + account.getCompanyName() + "%");
		}
		hql += " order by account.update_time desc";
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 
	 * 功能描述： 输入参数: @param companyId 输入参数: @return 异 常： 创 建 人: yico-cj 日 期:
	 * 2016年6月7日下午12:58:34 修 改 人: 日 期: 返 回：List<Account>
	 */
	public List<Account> getListByCompanyId(String companyId, List<String> ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		if (ids.size() > 0) {
			StringBuffer str = new StringBuffer();
			for (String id : ids) {
				str.append("'").append(id).append("'").append(",");
			}
			str.delete(str.length() - 1, str.length());
			return this.getListByHql(
					"from Account where company.id = :companyId and id not in (" + str.toString() + ") ", map);
		} else {
			return this.getListByHql("from Account where company.id = :companyId  ", map);
		}

	}

	public int getAccountRoleList(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select count(*) FROM mem_account a where a.role_id = :roleId";
		map.put("roleId", roleId);
		return this.getCountSql(sql, map);

	}

	/**
	 * 
	 * 功能描述： 根据token获取用户对象 输入参数: @param token 输入参数: @return 异 常： 创 建 人:
	 * Administrator 日 期: 2016年6月26日上午11:15:43 修 改 人: 日 期: 返 回：Account
	 */
	public Account getAccountByToken(String token) {
		String sql = " from Account user where user.token=:token";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		Account account2 = this.getObjectByColumn(sql, map);
		return account2;
	}

	public List<Map<String, Object>> getListForMap(String companyId, String keyword) {
		StringBuffer sql = new StringBuffer(
				"select vo.account,vo.id,vo.phone,vo.name,vo.status,vo.authentication,vo.company_id,vo.idcard_id,vo.driving_license_id,vo.login_count,vo.company_section_id,vo.last_login_time,vo.capitalStatus,vo.role_id from mem_account as vo");
		StringBuffer where = new StringBuffer(" where vo.company_id = :companyId and vo.status != 3");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		if (StringUtil.isNotEmpty(keyword)) {
			where.append(" and (vo.phone like :phone " + "or vo.name like name)");
			map.put("phone", "%" + keyword + "%");
			map.put("name", "%" + keyword + "%");
		}
		where.append(" order by vo.create_time desc");
		return this.getListBySQLMap(sql.append(where).toString(), map);
	}

	public Map<String, Object> getAccountForMap(String account) {
		String sql = "select vo.id,vo.account,vo.phone,vo.name from mem_account as vo where vo.account = '" + account
				+ "' or vo.phone = '" + account + "'";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getListBySQLMap(sql, map);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public List<Map<String, Object>> getListForMapById(String companySectionId) {
		StringBuffer sql = new StringBuffer(
				"select vo.account,vo.id,vo.phone,vo.name,vo.status,vo.authentication,vo.company_id,vo.idcard_id,vo.driving_license_id,vo.login_count,vo.company_section_id,vo.last_login_time,vo.capitalStatus,vo.role_id from mem_account as vo where vo.company_section_id = :companySectionId and vo.status != 3  order by vo.create_time desc");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companySectionId", companySectionId);
		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 获取当前登录用户的APP功能 输入参数: @param roleId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月18日下午4:06:56 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getRoleAppTooleList(String roleId) {
		String sql = "SELECT a.name,a.type,a.iconcls,a.iconcls_color,a.path,a.app_type "
				+ " FROM sys_menu_app AS a WHERE a.id IN ( SELECT b.menu_app_id FROM sys_role_menu_app AS b WHERE b.role_id = :roleId )";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 获取所有易林用户
	 * 
	 * @return
	 */
	public List<Account> getAdminAccount() {
		String hql = " from Account user where user.company.companyType.name='管理'";
		return this.getListByHql(hql);
	}

	/**
	 * 功能描述： 获取账户人联系信息 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人: longqibo
	 * 日 期: 2016年8月14日上午11:51:37 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getAccountInfo(String accountId) {
		String sql = "SELECT account.`name` ,account.`phone`,company.`name` AS company_name,company.`address`  "
				+ " FROM `mem_account` AS  account LEFT JOIN `sys_company` AS company ON company.`id`  = account.`company_id` "
				+ " WHERE account.`id` = :accountId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		return this.getSqlMap(sql, map);
	}

	/**
	 * 根据手机号码返回用户实体信息
	 * 
	 * @param phone
	 * @return
	 */
	public Map<String, Object> checkAccountCountByPhone(String phone) {
		String sql = " SELECT count(phone) AS count FROM mem_account WHERE phone = :phone ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		return this.getSqlMap(sql, map);
	}

	/**
	 * aiqiwu 2017-09-01 获取员工列表
	 */
	public List<Map<String, Object>> getEmployeeByCompanyIdAndSectionIdForMap(String companyId, String companySectionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT account.id as account_id,account.account,account.phone,account.`status`,account.authentication,account.`name`,"
				+ "account.company_id,account.company_section_id,truck.id AS truck_id,truck.track_no,"
				+ "truck.truck_status,truck.track_long,truck.capacity as truck_capacity,truckType.`name` truck_type_name FROM mem_account account LEFT JOIN sys_track truck ON "
				+ "account.id = truck.account_id LEFT JOIN bas_truck_type truckType ON truckType.id = truck.card_type_id ");
		StringBuffer where = new StringBuffer();
		where.append(" where account.company_id = :companyId and account.status <> :status ");
		map.put("companyId", companyId);
		map.put("status", Status.delete);
		where.append(" and account.company_section_id in (:ids) ");
		map.put("ids", companySectionId);
		sql.append(where).append(" order by account.create_time desc ");
		return this.getListBySQLMap(sql.toString(), map);
	}

}