package com.memory.platform.module.order.service;

import java.util.List;

import com.memory.platform.entity.order.OrderWaybillLog;


/**
* 创 建 人： longqibo
* 日    期： 2016年6月16日 下午8:18:23 
* 修 改 人： 
* 日   期： 
* 描   述： 订单操作记录服务接口
* 版 本 号：  V1.0
 */
public interface IOrderWaybillLogService {
	
	/**
	 * 根据实体信息获取求助信息
	 * @param orderWaybillLog
	 * @return
	 */
	OrderWaybillLog getOrderWaybillLog(OrderWaybillLog orderWaybillLog);
	
	
	/**
	 * 获取处理信息
	 * @param robConfirmId
	 * @return
	 */
	List<OrderWaybillLog> getOrderWaybillLogList(String orderSpecialApplyId);
}
