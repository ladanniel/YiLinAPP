package com.memory.platform.module.aut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.base.DrivingLicenseType;
import com.memory.platform.module.aut.dao.DrivingLicenseTypeDao;
import com.memory.platform.module.aut.service.IDrivingLicenseTypeService;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月9日 下午4:42:10 
* 修 改 人： 
* 日   期： 
* 描   述： 准驾车型业务接口实现类
* 版 本 号：  V1.0
 */
@Service
public class DrivingLicenseTypeService implements IDrivingLicenseTypeService {

	
	@Autowired
	private DrivingLicenseTypeDao drivingLicenseTypeDao;

	/*  
	 *  getDrivingLicenseTypeList
	 */
	@Override
	public List<DrivingLicenseType> getDrivingLicenseTypeList() {
		// TODO Auto-generated method stub
		return drivingLicenseTypeDao.getDrivingLicenseTypeList();
	}

	/*  
	 *  get
	 */
	@Override
	public DrivingLicenseType get(String id) {
		// TODO Auto-generated method stub
		return drivingLicenseTypeDao.getObjectById(DrivingLicenseType.class, id);
	}
	 

	 
}
