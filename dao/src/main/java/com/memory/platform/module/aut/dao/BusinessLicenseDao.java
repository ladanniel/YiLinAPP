package com.memory.platform.module.aut.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年5月4日 下午5:09:18 修 改 人： 日 期： 描 述： 驾驶证信息DAO类 版 本 号：
 * V1.0
 */
@Repository
public class BusinessLicenseDao extends BaseDao<BusinessLicense> {

	/**
	* 功能描述： 通过营业执照编号查询营业执照信息
	* 输入参数:  @param no 营业执照编号
	* 输入参数:  @return 
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午8:04:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：BusinessLicense
	 */
	public BusinessLicense getBusinessLicenseByNo(String no,String id) {
		String hql = " from BusinessLicense businessLicense where businessLicense.company_no=:company_no ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company_no", no);
		if(!StringUtils.isEmpty(id)){
			hql += " and  businessLicense.id != :id";
			map.put("id", id);
		} 
		BusinessLicense businessLicense = this.getObjectByColumn(hql, map);
		return businessLicense;
	}
}
