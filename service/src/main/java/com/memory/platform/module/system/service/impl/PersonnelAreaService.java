package com.memory.platform.module.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.base.Area;
import com.memory.platform.module.system.dao.AreaDao;
import com.memory.platform.module.system.dao.PersonnelAreaDao;
import com.memory.platform.module.system.service.IPersonnelAreaService;

@Service
public class PersonnelAreaService implements IPersonnelAreaService{

	@Autowired
	private PersonnelAreaDao personnelAreaDao;
	@Autowired
	AreaDao areaDao;
	
	/**
	 * 查询所有区域信息
	 */
	public List<Map<String, Object>> getAreaList() {
		// TODO Auto-generated method stub
		return personnelAreaDao.getAreaList();
	}
	
	public List<Area> getAreaAll() {
		return areaDao.getAll();
	}
}
