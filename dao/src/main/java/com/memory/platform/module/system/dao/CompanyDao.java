package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.Company;
import com.memory.platform.global.Auth;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 
 * 创 建 人： 武国庆 日 期： 2016年5月2日 下午12:38:35 修 改 人： 日 期： 描 述：商户 DAO类 版 本 号： V1.0
 */

@Repository
public class CompanyDao extends BaseDao<Company> {

	/**
	 * 功能描述： 商户信息分页查询 输入参数: @param company 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年5月10日下午4:40:59 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getPage(Company company, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Company company where company.status != :status ";
		map.put("status", Company.Status.admin);
		if (null != company.getSearch() && !"".equals(company.getSearch())) {
			hql += " and ( company.name like :name or company.contactName like :contactName or company.contactTel like :contactTel )";
			map.put("name", "%" + company.getSearch() + "%");
			map.put("contactName", "%" + company.getSearch() + "%");
			map.put("contactTel", "%" + company.getSearch() + "%");
		}
		if (null != company.getCompanyAuths() && company.getCompanyAuths().length > 0) {
			hql += " and company.audit in ( :audit )";
			map.put("audit", company.getCompanyAuths());
		} else {
			Auth[] auths = { Auth.notapply };
			hql += " and company.audit not in ( :audit )";
			map.put("audit", auths);
		}
		if (null != company.getCompanyTypes() && company.getCompanyTypes().length > 0) {
			hql += " and company.companyType.id in ( :companyTypeId )";
			map.put("companyTypeId", company.getCompanyTypes());
		}
		if (!StringUtils.isEmpty(company.getAreaFullName())) {
			hql += " and company.areaFullName like :areaFullName ";
			map.put("areaFullName", company.getAreaFullName() + "%");
		}
		if (company.getStatus_serch() != null && company.getStatus_serch().length > 0) {
			hql += " and company.status in (:statuss) ";
			map.put("statuss", company.getStatus_serch());
		}
		if (company.getSource_serch() != null && company.getSource_serch().length > 0) {
			hql += " and company.source in ( :source )";
			map.put("source", company.getSource_serch());
		}
		hql += " order by  company.update_time desc";
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 保存商户
	 * 
	 * @param user
	 */
	public void saveCompany(Company company) {
		this.save(company);
	}

	/**
	 * 功能描述： 商户信息修改 输入参数: @param company 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年5月4日下午5:20:04 修 改 人: 日 期: 返 回：void
	 */
	public void updateCompany(Company company) {
		this.update(company);
	}

	/**
	 * 功能描述： 通过名称查询商户信息 输入参数: @param name //商户名称 输入参数: @param id //商户ID
	 * ,用户在验证商户信息的时候，查询不等于该商户的ID名称 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年5月5日下午7:17:23 修 改 人: 日 期: 返 回：Company
	 */
	public Company getCompanyByName(String name, String id) {
		String hql = " from Company company where company.name=:name ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (!StringUtils.isEmpty(id)) {
			hql += " and  company.id != :id";
			map.put("id", id);
		}
		Company company1 = this.getObjectByColumn(hql, map);
		return company1;
	}

	/**
	 * 功 能 描 述：查询所有商户 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修 改
	 * 日 期： 返 回：list
	 */
	public List<Company> getCompanyList() {
		String hql = "from Company";
		return this.getListByHql(hql);
	}

	/**
	 * 功能描述： 商户信息分页查询 输入参数: @param company 商户信息对象 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年5月10日下午4:42:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getPageCompanyByName(String name, String conpanyTypeName, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				" SELECT c.id,c.`name` FROM sys_company c LEFT JOIN sys_company_type AS b ON c.company_type_id = b.id  WHERE c.`status` =1 and audit !=0 and audit!=2 and audit!=3");
		StringBuffer where = new StringBuffer();
		if (StringUtils.isNotBlank(name)) {
			where.append(" AND c.name like :name");
			map.put("name", "%" + name + "%");
		}
		if (StringUtils.isNotBlank(conpanyTypeName)) {
			where.append(" AND b.name=:conpanyTypeName");
			map.put("conpanyTypeName", conpanyTypeName);
		}
		return this.getPageSQLMap(sql.append(where).toString(), map, start, limit);
	}

	/**
	 * 功能描述： 获取商户信息 输入参数: @param id 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年9月22日下午3:47:58 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getCompanyByAccountId(String id) {
		String sql = "SELECT vo.`id` ,vo.`name` ,vo.`area_id` ,vo.`audit` ,vo.`area_full_name` ,vo.`status` ,vo.`address` ,vo.`contact_name` ,vo.`contact_tel` ,vo.`source` ,vo.`account_id`,vo.`idcard_id` ,vo.`driving_license_id` ,vo.`business_license_id`  FROM `sys_company` AS vo WHERE `account_id` = :accountId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", id);
		return this.getSqlMap(sql, map);
	}

	public Map<String, Object> getCompanyId(String id) {
		String sql = "SELECT vo.`id` ,vo.`name` ,vo.`area_id` ,vo.`audit` ,vo.`area_full_name` ,vo.`status` ,vo.`address` ,vo.`contact_name` ,vo.`contact_tel` ,vo.`source` ,vo.`account_id`,vo.`idcard_id` ,vo.`driving_license_id` ,vo.`business_license_id`  FROM `sys_company` AS vo WHERE `id` = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.getSqlMap(sql, map);
	}

	/*
	 * aiqiwu 2017-06-05
	 * */
	public List<Company> getCompanyListByTypeId(String typeId) {
		// TODO Auto-generated method stub
		String hql = " from Company company where company.companyType.id = :typeId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		return this.getListByHql(hql, map);
	}
}
