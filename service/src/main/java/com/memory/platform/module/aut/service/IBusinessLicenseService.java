package com.memory.platform.module.aut.service;

import com.memory.platform.entity.aut.BusinessLicense;

/**
* 创 建 人：杨佳桥
* 日    期： 2016年4月30日 下午9:09:59 
* 修 改 人： 
* 日   期： 
* 描   述：营业执照业务接口类
* 版 本 号：  V1.0
 */
public interface IBusinessLicenseService {
	
	/**
	* 功能描述： 通过ID获取营业执照业对象
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月9日下午4:10:14
	* 修 改 人: 
	* 日    期: 
	* 返    回：BusinessLicense
	 */
	public BusinessLicense get(String id);
	
	/**
	* 功能描述： 通过营业执照编号查询营业执照信息
	* 输入参数:  @param no 营业执照编号
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午8:06:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：BusinessLicense
	 */
	public BusinessLicense getBusinessLicenseByNo(String no,String id);
}
