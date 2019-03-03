package com.memory.platform.module.aut.service;

import com.memory.platform.entity.aut.Idcard;

/**
* 创 建 人：杨佳桥
* 日    期： 2016年4月30日 下午9:09:59 
* 修 改 人： 
* 日   期： 
* 描   述：身份证业务接口类
* 版 本 号：  V1.0
 */
public interface IIdcardService {

	/**
	 * 
	* 功能描述： 根据ID查找身份证表
	* 输入参数:  @param idcard_id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年5月2日下午12:07:53
	* 修 改 人: 
	* 日    期: 
	* 返    回：Idcard
	 */
	Idcard get(String idcard_id);
	
	/**
	* 功能描述： 通过身份证号码查询身份证信息
	* 输入参数:  @param no 身份证编号
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:44:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：Idcard
	 */
	Idcard getNo(String no,String id);
	
	/**
	* 功能描述:通过用户ID，查询身份证信息 
	* 输入参数:  @param accountId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: Aug 27, 201611:27:43 AM
	* 修 改 人: 
	* 日    期: 
	* 返    回：Idcard
	 */
	Idcard getAccountId(String accountId);
}
