package com.memory.platform.module.capital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.RechargeDirect;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Auth;
import com.memory.platform.module.capital.dao.RechargeDirectDao;
import com.memory.platform.module.capital.service.IRechargeDirectService;

@Service
@Transactional
public class RechargeDirectService implements IRechargeDirectService {
	@Autowired
	private RechargeDirectDao rechargeDirectDao;

	@Override
	public void saveRechargeDirect(RechargeDirect rechargeDirect) {
		if (rechargeDirect.getAccount().getAuthentication().equals(Auth.notAuth)) {
			throw new BusinessException("请先认证后再操作。");
		}
		this.rechargeDirectDao.save(rechargeDirect);
	}
}
