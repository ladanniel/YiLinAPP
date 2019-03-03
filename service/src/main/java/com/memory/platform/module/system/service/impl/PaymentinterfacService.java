package com.memory.platform.module.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.sys.Paymentinterfac;
import com.memory.platform.module.system.dao.PaymentinterfacDao;
import com.memory.platform.module.system.service.IPaymentinterfacService;

@Service
@Transactional
public class PaymentinterfacService implements IPaymentinterfacService {

	@Autowired
	private PaymentinterfacDao paymentinterfacDao;
	
	@Override
	public Paymentinterfac getPaymentinterfac(String id) {
		// TODO Auto-generated method stub
		return paymentinterfacDao.getObjectById(Paymentinterfac.class, id);
	}

}
