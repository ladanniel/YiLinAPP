package com.memory.platform.module.aut.service;

import com.memory.platform.entity.aut.DrivingLicense;

/**
* 创 建 人：杨佳桥
* 日    期： 2016年4月30日 下午9:09:59 
* 修 改 人： 
* 日   期： 
* 描   述：驾驶证业务接口类
* 版 本 号：  V1.0
 */
public interface IDrivingLicenseService {

	/**
	 * 
	* 功能描述： 根据ID查询驾驶证认证表
	* 输入参数:  @param driving_license_id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年5月2日下午12:40:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：DrivingLicense
	 */
	DrivingLicense get(String driving_license_id);
	
	/**
	* 功能描述： 通过驾驶证编号查询驾驶证信息
	* 输入参数:  @param no
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:55:19
	* 修 改 人: 
	* 日    期: 
	* 返    回：DrivingLicense
	 */
	DrivingLicense getDrivingLicenseByNo(String no,String id);
}
