package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.member.Staff;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年6月1日 下午3:50:35 
* 修 改 人： 
* 日   期： 
* 描   述： 用户详情dao层
* 版 本 号：  V1.0
 */
@Repository
public class StaffDao extends BaseDao<Staff> {

	/**
	 * 
	* 功能描述： 用户详情分页查询
	* 输入参数:  @param staff
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月3日下午1:32:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPageStaff(Staff staff, int start, int limit) {
		String hql = " from Staff where account.company.id=:companyId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", staff.getCompanyId());
		if(!StringUtils.isEmpty(staff.getSearch()) ) {
			hql += " and name like :name ";
			map.put("name","%"+staff.getSearch()+"%");
		}
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 
	* 功能描述： 取得学历字列表
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月3日下午1:32:12
	* 修 改 人: 
	* 日    期: 
	* 返    回：Object
	 */
	public Object getEduList() {
		return this.getListByHql(" from Education ");
	}


}
