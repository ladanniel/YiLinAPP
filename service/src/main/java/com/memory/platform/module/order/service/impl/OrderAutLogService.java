package com.memory.platform.module.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.order.dao.OrderAutLogDao;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;

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
public class OrderAutLogService implements IOrderAutLogService {

	@Autowired
	private OrderAutLogDao orderAutLogDao;
	@Autowired
	IRobOrderConfirmService robOrderConfrimService ; 
	
	@Override
	public Map<String, Object> getPage(OrderAutLog log, int start, int limit) {
		return orderAutLogDao.getPage(log, start, limit);
	}

	@Override
	public void saveLog(OrderAutLog log) {
		orderAutLogDao.save(log);
	}

	@Override
	public OrderAutLog getLogById(String id) {
		return orderAutLogDao.getObjectById(OrderAutLog.class, id);
	}

	@Override
	public List<Map<String, Object>> getListForMap(String orderId) {
		return orderAutLogDao.getListForMap(orderId);
	}

	/*  
	 *  getListForMap
	 */
	@Override
	public List<Map<String, Object>> getListForMap(String orderId, String order) {
		return orderAutLogDao.getListForMap(orderId, order);
	}

	/*  
	 *  getLog
	 */
	@Override
	public OrderAutLog getLog(OrderAutLog log) {
		return orderAutLogDao.getLog(log);
	}

	@Override
	public List<OrderAutLog> getOrderlog(String robConfirmId) {
		return orderAutLogDao.getOrderlog(robConfirmId);
	}
	
	@Override
	public List<Map<String, Object>> getOrderLog(String orderId) {
		return orderAutLogDao.getOrderLog(orderId);
	}

	@Override
	public List<Map<String, Object>> getOrderlogListMap(String robConfirmId) {
		
		return orderAutLogDao.getOrderlogListMap(robConfirmId);
	}
	
	@Override
	public List<Map<String, Object>> getOrderAndConfirmLogListMap(String robOrderID, String robConfirmId) {
		if(StringUtil.isEmpty(robOrderID) ) {
			if(StringUtil.isNotEmpty(robConfirmId)) {
				RobOrderConfirm confirm =  robOrderConfrimService.getRobOrderConfirmById(robConfirmId);
				if(confirm!=null) {
					
					robOrderID = confirm.getRobOrderId();
				}
				
			}
			
		}
		ArrayList<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
		 if(StringUtil.isNotEmpty(robOrderID)) {
			 
			 ret = (ArrayList<Map<String,Object>>)this.getOrderLog(robOrderID);
		 }
		 if(StringUtil.isNotEmpty(robConfirmId)) {
			 
			 ArrayList<Map<String,Object>> confirmLogs = (ArrayList<Map<String,Object>>)this.orderAutLogDao.getOrderConfirmLogListMap(robConfirmId) ;
			 for (int i = confirmLogs.size()-1; i >-1; i--) {
				 ret.add(0, confirmLogs.get(i));
			}
		 }
		return ret;
	}
}
