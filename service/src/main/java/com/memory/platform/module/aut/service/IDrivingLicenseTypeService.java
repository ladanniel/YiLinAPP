package com.memory.platform.module.aut.service;

import java.util.List;

import com.memory.platform.entity.base.DrivingLicenseType;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月9日 下午4:39:25 
* 修 改 人： 
* 日   期： 
* 描   述： 准驾车型业务接口类
* 版 本 号：  V1.0
 */
public interface IDrivingLicenseTypeService {
	
	/**
	* 功能描述： 通过ID查询准驾车型数据
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月10日下午2:08:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：DrivingLicenseType
	 */
	DrivingLicenseType get(String id);
	/**
	* 功能描述： 查询所有准驾车型数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月9日下午4:40:43
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<DrivingLicenseType>
	 */
	 List<DrivingLicenseType> getDrivingLicenseTypeList();
}
