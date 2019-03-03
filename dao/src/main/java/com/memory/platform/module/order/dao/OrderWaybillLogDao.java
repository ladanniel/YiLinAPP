package com.memory.platform.module.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.order.OrderWaybillLog;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月16日 下午8:14:49 
* 修 改 人： 
* 日   期： 
* 描   述：  物殊操作记录 － dao
* 版 本 号：  V1.0
 */
@Repository
public class OrderWaybillLogDao extends BaseDao<OrderWaybillLog> {

	/**
	 * 根据实体信息获取求助信息
	 * @param orderWaybillLog
	 * @return
	 */
	public OrderWaybillLog getOrderWaybillLog(OrderWaybillLog orderWaybillLog){
		StringBuffer hql = new StringBuffer(" from OrderWaybillLog log where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(orderWaybillLog.getRobOrderConfirm()!=null&&StringUtils.isNoneBlank(orderWaybillLog.getRobOrderConfirm().getId())){
			where.append(" and log.robOrderConfirm.id = :robOrderConfirmId");
			map.put("robOrderConfirmId", orderWaybillLog.getRobOrderConfirm().getId());
		}
		
		if(orderWaybillLog.getConfirmStatus()!=null){
			where.append(" and log.confirmStatus = :confirmStatus");
			map.put("confirmStatus", orderWaybillLog.getConfirmStatus());
		}
		
		if(orderWaybillLog.getSpecialType()!=null){
			where.append(" and log.specialType = :specialType");
			map.put("specialType", orderWaybillLog.getSpecialType());
		}
		
		if(orderWaybillLog.getSpecialStatus()!=null){
			where.append(" and log.specialStatus = :specialStatus");
			map.put("specialStatus", orderWaybillLog.getSpecialType());
		}
		
		if(StringUtils.isNoneBlank(orderWaybillLog.getApplypersonid())){
			where.append(" and log.applypersonid = :applypersonid");
			map.put("applypersonid", orderWaybillLog.getApplypersonid());
		}
		
		
		return this.getObjectByColumn(hql.append(where).toString(), map);
	}
	

	/**
	 * 获取处理信息
	 * @param robConfirmId
	 * @return
	 */
	public List<OrderWaybillLog> getOrderWaybillLogList(RobOrderConfirm robOrderConfirm,String applypersonid){
		StringBuffer hql = new StringBuffer(" from OrderWaybillLog log where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(robOrderConfirm!=null&&StringUtils.isNoneBlank(robOrderConfirm.getId())){
			where.append(" and log.robOrderConfirm.id = :robOrderConfirmId");
			map.put("robOrderConfirmId", robOrderConfirm.getId());
		}
		
		if(robOrderConfirm.getStatus()!=null){
			where.append(" and log.confirmStatus = :confirmStatus");
			map.put("robOrderConfirmId", robOrderConfirm.getStatus());
		}
		
		where.append(" and log.applypersonid = :applypersonid");
		map.put("applypersonid", applypersonid);
		
		return this.getListByHql(hql.append(where).toString(), map);
	}
	/**
	 * 获取处理信息
	 * @param robConfirmId
	 * @return
	 */
	public List<OrderWaybillLog> getOrderWaybillLogList(String orderSpecialApplyId){
		StringBuffer hql = new StringBuffer(" from OrderWaybillLog log where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		
		where.append(" and log.orderSpecialApplyId = :orderSpecialApplyId");
		map.put("orderSpecialApplyId", orderSpecialApplyId);
		
		return this.getListByHql(hql.append(where).toString(), map);
	}
	
	
	
	
}
