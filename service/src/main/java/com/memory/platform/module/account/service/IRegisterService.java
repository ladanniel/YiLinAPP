package com.memory.platform.module.account.service;

import com.memory.platform.entity.member.Account;

/**
 * 
* 创 建 人： 武国庆
* 日    期： 2016年4月30日 下午9:09:59 
* 修 改 人： 
* 日   期： 
* 描   述：账户注册
* 版 本 号：  V1.0
 */
public interface IRegisterService {

	/**
	 * 
	 * 功能描述： 账户注册保存
	* 输入参数:  @param account
	* 异    常： 
	* 创 建 人: 武国庆
	* 日    期: 2016年4月30日下午9:10:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveCompanyReg(Account account);
}
