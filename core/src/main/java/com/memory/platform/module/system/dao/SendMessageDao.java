package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.SendMessage;
import com.memory.platform.entity.sys.SendMessage.Status;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午2:20:52 
* 修 改 人： 
* 日   期： 
* 描   述： 短信发送接口dao
* 版 本 号：  V1.0
 */
@Repository
public class SendMessageDao extends BaseDao<SendMessage> {

	/**
	* 功能描述： 接口信息列表
	* 输入参数:  @param sendMessage
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午2:30:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(SendMessage sendMessage,int start, int limit){
		StringBuffer hql = new StringBuffer(" from SendMessage sendMessage where 1 = 1");
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer where = new StringBuffer();
		where.append(" order by sendMessage.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	public List<SendMessage> getEnabledSendMessage(){
		StringBuffer hql = new StringBuffer(" from SendMessage sendMessage where 1 = 1");
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer where = new StringBuffer();
		where.append(" and sendMessage.status = :status");
		map.put("status", Status.enabled);
		where.append(" order by sendMessage.create_time desc");
		return this.getListByHql(hql.append(where).toString(), map);
	}
}
