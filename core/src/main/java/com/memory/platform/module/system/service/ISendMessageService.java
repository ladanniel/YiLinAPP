package com.memory.platform.module.system.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.memory.platform.entity.sys.SendMessage;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午2:35:09 
* 修 改 人： 
* 日   期： 
* 描   述： 短信接口 － 服务接口
* 版 本 号：  V1.0
 */
public interface ISendMessageService {

	/**
	* 功能描述： 保存接口信息
	* 输入参数:  @param sendMessage
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午2:27:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveMessage(SendMessage sendMessage);
	
	/**
	* 功能描述： 更新接口信息
	* 输入参数:  @param sendMessage
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午2:28:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateMessage(SendMessage sendMessage);
	
	/**
	* 功能描述： 短信接口列表
	* 输入参数:  @param sendMessage
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午2:33:10
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(SendMessage sendMessage,int start, int limit);
	
	/**
	* 功能描述： 根据id返回信息
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午3:01:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：SendMessage
	 */
	SendMessage getSendMessage(String id);
	
	/**
	* 功能描述： 发送短信接口
	* 输入参数:  @param mobiles   手机串
	* 输入参数:  @param content   发送文本
	* 输入参数:  @param sendPoint  短信切入点全路径
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午9:36:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	Map<String, Object> saveRecordAndSendMessage(String mobiles,String content,String sendPoint);
	
	/**
	* 功能描述： 获取启用接口
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月31日下午9:40:26
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<SendMessage>
	 */
	List<SendMessage> getEnabledSendMessage();
}
