package com.memory.platform.module.aut.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.base.DrivingLicenseType;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月9日 下午4:36:27 
* 修 改 人： 
* 日   期： 
* 描   述： 驾驶证准驾查询DAO类
* 版 本 号：  V1.0
 */
@Repository
public class DrivingLicenseTypeDao extends BaseDao<DrivingLicenseType> {
    
	/**
	* 功能描述： 查询所有准驾车型数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月9日下午4:38:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<DrivingLicenseType>
	 */
	public List<DrivingLicenseType> getDrivingLicenseTypeList(){
		String hql = " from DrivingLicenseType  ";
		return this.getListByHql(hql);
	}
}
