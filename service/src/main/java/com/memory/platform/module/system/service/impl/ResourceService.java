package com.memory.platform.module.system.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.sys.Resource;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.system.dao.ResourceDao;
import com.memory.platform.module.system.service.IResourceService;


@Service
@Transactional
public class ResourceService implements IResourceService{
	
	@Autowired
	private ResourceDao resourceDao;

	public Map<String, Object> getPage(Resource resource,int start, int limit) {
		return this.resourceDao.getPage(resource,start, limit);
	}

	public void saveResource(Resource resource) {
		resource.setAdd_user_id(UserUtil.getUser().getId());
		resource.setCreate_time(new Date());
		this.resourceDao.saveResource(resource);
	}

	public void removeResource(String resourceId) {
		this.resourceDao.removeResource(resourceId);
	}

	public Resource getResource(String resourceId) {
		return this.resourceDao.getResource(resourceId);
	}
	
	public Resource getResourceByUrl(String url) {
		return this.resourceDao.getResourceByUrl(url);
	}

	public List<Resource> getResourceList() {
		return this.resourceDao.getResourceList();
	}


	public void updateResource(Resource resource) {
		resource.setUpdate_user_id(UserUtil.getUser().getId());
		resource.setCreate_time(new Date());
		this.resourceDao.updateResource(resource);
	}

	/*  
	 *  getResourceByUrlName
	 */
	@Override
	public boolean getResourceByName(String name,String companyTypeId) {
		// TODO Auto-generated method stub
		return resourceDao.getResourceByName(name,companyTypeId);
	}

	 

}
