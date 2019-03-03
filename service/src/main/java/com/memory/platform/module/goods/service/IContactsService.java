package com.memory.platform.module.goods.service;

import java.util.Map;

import com.memory.platform.entity.goods.Contacts;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 下午2:12:07 
* 修 改 人： 
* 日   期： 
* 描   述： 联系人服务 － 接口
* 版 本 号：  V1.0
 */
public interface IContactsService {

	/**
	* 功能描述： 保存联系人
	* 输入参数:  @param contacts
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午2:18:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveContacts(Contacts contacts);
	
	/**
	* 功能描述： 更新联系人
	* 输入参数:  @param contacts
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午2:19:53
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateContacts(Contacts contacts);
	
	/**
	* 功能描述： 联系人列表
	* 输入参数:  @param contacts
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午2:21:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(Contacts contacts,int start,int limit);
	
	/**
	* 功能描述： 根据ID获取联系人信息
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午2:23:25
	* 修 改 人: 
	* 日    期: 
	* 返    回：Contacts
	 */
	Contacts getContactsById(String id);
	
	/**
	* 功能描述： 删除联系人信息
	* 输入参数:  @param id
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午3:08:10
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void removeContacts(String id);
}
