package com.memory.platform.module.system.service;

import java.util.Map;

import com.memory.platform.entity.sys.Feedback;

/**
* 创 建 人： longqibo
* 日    期： 2016年7月25日 上午9:45:13 
* 修 改 人： 
* 日   期： 
* 描   述： 	意见反馈服务接口
* 版 本 号：  V1.0
 */
public interface IFeedbackService {

	void saveFeedback(Feedback feedback);
	
	void updateFeedback(Feedback feedback);
	
	Feedback getFeedbackById(String id);
	
	Map<String, Object> getPage(Feedback feedback,int start,int limit);
}
