package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.member.Staff;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年6月1日 下午3:49:00 
* 修 改 人： 
* 日   期： 
* 描   述： 用户详情业务层接口
* 版 本 号：  V1.0
 */
public interface IStaffService {

	/**
	 * 
	* 功能描述： 用户详情分页查询
	* 输入参数:  @param staff 用户详情对象
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月1日下午3:52:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPageStaff(Staff staff, int start, int limit);

	/**
	 * 
	* 功能描述： 取得学历字列表
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月3日下午1:30:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：Object
	 */
	Object getEduList();

	/**
	 * 
	* 功能描述： 添加用户详情
	* 输入参数:  @param staff
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月3日下午4:22:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> save(Staff staff);

	/**
	 * 
	* 功能描述： 取得所有人员的信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月12日下午12:09:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Staff>
	 */
	List<Staff> getStaffList();

	/**
	 * 
	* 功能描述： 根据ID查询用户详情 
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年6月13日上午11:15:15
	* 修 改 人: 
	* 日    期: 
	* 返    回：Staff
	 */
	Staff getById(String id);
	
    Map<String, Object> updateStaff(Staff staff);
}
