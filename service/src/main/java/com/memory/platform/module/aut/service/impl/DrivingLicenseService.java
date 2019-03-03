package com.memory.platform.module.aut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.module.aut.dao.DrivingLicenseDao;
import com.memory.platform.module.aut.service.IDrivingLicenseService;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 下午4:54:32 
* 修 改 人： 
* 日   期： 
* 描   述：驾驶证业务接口实现类
* 版 本 号：  V1.0
 */
@Service
public class DrivingLicenseService implements IDrivingLicenseService {

	
	@Autowired
	private DrivingLicenseDao drivingLicenseDao;
	/*  
	 *  get
	 */
	@Override
	public DrivingLicense get(String driving_license_id) {
		// TODO Auto-generated method stub
		return drivingLicenseDao.getObjectById(DrivingLicense.class, driving_license_id);
	}

	/*  
	 *  getDrivingLicenseByNo
	 */
	@Override
	public DrivingLicense getDrivingLicenseByNo(String no,String id) {
		// TODO Auto-generated method stub
		return drivingLicenseDao.getDrivingLicenseByNo(no,id);
	}

	 
	 
}
