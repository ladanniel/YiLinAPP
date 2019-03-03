package com.memory.platform.module.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.order.DepositLevel;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class DepositLevelDao extends BaseDao<DepositLevel> {
	
	//获取
	public List<DepositLevel> getList(){
		
		StringBuffer hql = new StringBuffer(" from DepositLevel");
		
		return  this.getListByHql(hql.toString());
		
	}
}
