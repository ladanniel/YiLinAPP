package com.memory.platform.module.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.order.OrderWaybillLog;
import com.memory.platform.module.order.dao.OrderWaybillLogDao;
import com.memory.platform.module.order.service.IOrderWaybillLogService;

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
public class OrderWaybillLogService implements IOrderWaybillLogService {

	@Autowired
	private OrderWaybillLogDao orderWaybillLogDao;

	@Override
	public OrderWaybillLog getOrderWaybillLog(OrderWaybillLog orderWaybillLog) {
		return orderWaybillLogDao.getOrderWaybillLog(orderWaybillLog);
	}

	@Override
	public List<OrderWaybillLog> getOrderWaybillLogList(String orderSpecialApplyId) {
		return orderWaybillLogDao.getOrderWaybillLogList(orderSpecialApplyId);
	}

}
