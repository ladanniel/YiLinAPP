package com.memory.platform.module.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.order.OrderSpecialApply;
import com.memory.platform.module.order.dao.OrderSpecialApplyDao;
import com.memory.platform.module.order.service.IOrderSpecialApplyService;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月16日 下午8:21:36 
* 修 改 人： 
* 日   期： 
* 描   述： 订单操作记录服务接口实现类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class OrderSpecialApplyService implements IOrderSpecialApplyService {

	@Autowired
	private OrderSpecialApplyDao orderSpecialApplyDao;

	@Override
	public OrderSpecialApply getOrderSpecialApply(
			OrderSpecialApply orderSpecialApply) {
		return orderSpecialApplyDao.getOrderSpecialApply(orderSpecialApply);
	}
	
	


}
