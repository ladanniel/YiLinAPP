package com.memory.platform.module.aut.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 下午5:09:18 
* 修 改 人： 
* 日   期： 
* 描   述： 驾驶证信息DAO类
* 版 本 号：  V1.0
 */
@Repository
public class DrivingLicenseDao extends BaseDao<DrivingLicense> {

	/**
	* 功能描述： 通过驾驶证编号查询驾驶证信息
	* 输入参数:  @param no 驾驶证编号
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:53:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：DrivingLicense
	 */
	public DrivingLicense getDrivingLicenseByNo(String no,String id){
		String hql = " from DrivingLicense drivingLicense where drivingLicense.driving_license_no=:driving_license_no ";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("driving_license_no", no);
		if(!StringUtils.isEmpty(id)){
			hql += " and  drivingLicense.id != :id";
			map.put("id", id);
		} 
		DrivingLicense drivingLicense = this.getObjectByColumn(hql, map);
		return drivingLicense;
	}
}
