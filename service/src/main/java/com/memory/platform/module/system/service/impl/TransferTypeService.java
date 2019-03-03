package com.memory.platform.module.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.TransferType;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.module.system.dao.TransferTypeDao;
import com.memory.platform.module.system.service.ITransferTypeService;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月10日 下午2:46:00 
* 修 改 人： 
* 日   期： 
* 描   述： 转账类型数据字典 － 业务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class TransferTypeService implements ITransferTypeService {

	@Autowired
	private TransferTypeDao transferTypeDao;
	
	@Override
	public void save(TransferType transferType) {
		transferTypeDao.save(transferType);
	}

	@Override
	public void update(TransferType transferType) {
		transferTypeDao.update(transferType);
	}

	@Override
	public void remove(String id) {
		try {
			transferTypeDao.delete(this.getTransferType(id));
		} catch (Exception e) {
			throw new BusinessException("已删除数据或没有这条数据。");
		}
	}

	@Override
	public TransferType getTransferType(String id) {
		return transferTypeDao.getObjectById(TransferType.class, id);
	}

	@Override
	public Map<String, Object> getPage(TransferType transferType, int start, int limit) {
		return transferTypeDao.getPage(transferType, start, limit);
	}

	@Override
	public TransferType checkName(String name,String typeId) {
		return transferTypeDao.checkName(name,typeId);
	}

	@Override
	public List<TransferType> getAll(String companyTypeId) {
		return transferTypeDao.getAll(companyTypeId);
	}
	@Override
	public List<Map<String, Object>> getTransferTypeForMap(String companyTypeId) {
		return transferTypeDao.getTransferTypeForMap(companyTypeId);
	}
}
