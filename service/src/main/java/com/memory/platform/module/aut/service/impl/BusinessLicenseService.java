package com.memory.platform.module.aut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.module.aut.dao.BusinessLicenseDao;
import com.memory.platform.module.aut.service.IBusinessLicenseService;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 下午4:54:32 
* 修 改 人： 
* 日   期： 
* 描   述：驾驶证业务接口实现类
* 版 本 号：  V1.0
 */
@Service
public class BusinessLicenseService implements IBusinessLicenseService {
	@Autowired
	private BusinessLicenseDao businessLicenseDao;
	
	/*  
	 *  getBusinessLicenseByNo
	 */
	@Override
	public BusinessLicense getBusinessLicenseByNo(String no,String id) {
		// TODO Auto-generated method stub
		return businessLicenseDao.getBusinessLicenseByNo(no,id);
	}

	/*  
	 *  get
	 */
	@Override
	public BusinessLicense get(String id) {
		// TODO Auto-generated method stub
		return businessLicenseDao.getObjectById(BusinessLicense.class, id);
	}

	 

	 
}
