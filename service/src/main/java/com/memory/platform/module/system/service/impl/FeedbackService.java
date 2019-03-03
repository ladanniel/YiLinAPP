package com.memory.platform.module.system.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.sys.Feedback;
import com.memory.platform.module.system.dao.FeedbackDao;
import com.memory.platform.module.system.service.IFeedbackService;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月25日 上午10:01:49 
* 修 改 人： 
* 日   期： 
* 描   述：意见反馈服务类接口 
* 版 本 号：  V1.0
 */
@Service
public class FeedbackService implements IFeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;
	
	@Override
	public void saveFeedback(Feedback feedback) {
		feedbackDao.save(feedback);
	}

	@Override
	public void updateFeedback(Feedback feedback) {
		feedbackDao.update(feedback);
	}

	@Override
	public Feedback getFeedbackById(String id) {
		return feedbackDao.getObjectById(Feedback.class, id);
	}

	@Override
	public Map<String, Object> getPage(Feedback feedback, int start, int limit) {
		return feedbackDao.getPage(feedback, start, limit);
	}

}
