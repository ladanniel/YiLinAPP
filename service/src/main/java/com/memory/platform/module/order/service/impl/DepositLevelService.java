package com.memory.platform.module.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.order.DepositLevel;
import com.memory.platform.module.order.dao.DepositLevelDao;
import com.memory.platform.module.order.dao.OrderAutLogDao;
import com.memory.platform.module.order.service.IDepositLevelService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;

@Service
@Transactional
public class DepositLevelService implements IDepositLevelService {


	@Autowired
	private DepositLevelDao depositLevelDao;

	
	@Override
	public List<DepositLevel> getDepositLevelList() {
		// TODO Auto-generated method stub
		return depositLevelDao.getList();
	}
	
}
