package com.memory.platform.module.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.module.system.dao.CompanyTypeDao;
import com.memory.platform.module.system.service.ICompanyTypeService;

/**
 * 
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月23日 下午4:21:58 
* 修 改 人： 
* 日   期： 
* 描   述： 商户业务接口实现类
* 版 本 号：  V1.0
 */
@Service
public class CompanyTypeService implements ICompanyTypeService{
	
	@Autowired
	private CompanyTypeDao companyTypeDao;

	/*  
	 *  getCompanyTypeById
	 */
	@Override
	public CompanyType getCompanyTypeById(String id) {
		// TODO Auto-generated method stub
		return companyTypeDao.getCompanyTypeById(id);
	}

	/*  
	 *  updateCompanyType
	 */
	@Override
	public void updateCompanyType(CompanyType CompanyType) {
		// TODO Auto-generated method stub
		companyTypeDao.updateCompanyType(CompanyType);
	}

	/*  
	 *  getPage
	 */
	@Override
	public Map<String, Object> getPage(CompanyType CompanyType, int start, int limit) {
		// TODO Auto-generated method stub
		return companyTypeDao.getPage(CompanyType, start, limit);
	}

	/*  
	 *  saveCompanyType
	 */
	@Override
	public void saveCompanyType(CompanyType CompanyType) {
		// TODO Auto-generated method stub
		companyTypeDao.saveCompanyType(CompanyType);
	}

	/*  
	 *  removeCompanyType
	 */
	@Override
	public void removeCompanyType(String id) {
		// TODO Auto-generated method stub
		companyTypeDao.removeCompanyType(id);
	}

	/*  
	 *  getCompanyTypeList
	 */
	@Override
	public List<CompanyType> getCompanyTypeList() {
		// TODO Auto-generated method stub
		return companyTypeDao.getCompanyTypeList();
	}

	/*  
	 *  getCompanyTypeByName
	 */
	@Override
	public boolean getCompanyTypeByName(String name,String companyTypeId) {
		// TODO Auto-generated method stub
		return companyTypeDao.getCompanyTypeByName(name,companyTypeId);
	}

	/*  
	 *  getCompanyTypeIsRegisterList
	 */
	@Override
	public List<CompanyType> getCompanyTypeIsRegisterList() {
		// TODO Auto-generated method stub
		return companyTypeDao.getCompanyTypeIsRegisterList();
	} 

}
