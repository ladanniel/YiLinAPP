package com.memory.platform.module.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.order.OrderSpecialApply;
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
public class OrderSpecialApplyDao extends BaseDao<OrderSpecialApply> {

	/**
	 * 根据实体信息获取求助信息
	 * @param orderWaybillLog
	 * @return
	 */
	public OrderSpecialApply getOrderSpecialApply(OrderSpecialApply orderSpecialApply){
		StringBuffer hql = new StringBuffer(" from OrderSpecialApply log where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isNoneBlank(orderSpecialApply.getRobOrderConfirmId())){
			where.append(" and log.robOrderConfirmId = :robOrderConfirmId");
			map.put("robOrderConfirmId",orderSpecialApply.getRobOrderConfirmId());
		}
		
		/*if(orderSpecialApply.getConfirmStatus()!=null){
			where.append(" and log.confirmStatus = :confirmStatus");
			map.put("confirmStatus", orderSpecialApply.getConfirmStatus());
		}*/
		
		if(orderSpecialApply.getSpecialType()!=null){
			where.append(" and log.specialType = :specialType");
			map.put("specialType", orderSpecialApply.getSpecialType());
		}
		
		if(orderSpecialApply.getSpecialStatus()!=null){
			where.append(" and log.specialStatus = :specialStatus");
			map.put("specialStatus", orderSpecialApply.getSpecialStatus());
		}
		
		if(StringUtils.isNoneBlank(orderSpecialApply.getApplypersonid())){
			where.append(" and log.applypersonid = :applypersonid");
			map.put("applypersonid", orderSpecialApply.getApplypersonid());
		}
		
		where.append(" order by log.create_time desc");
		
		List<OrderSpecialApply> list = this.getListByHql(hql.append(where).toString(), map);
		
		return list.get(0);
		//return this.getObjectByColumn(hql.append(where).toString(), map);
	}
}
