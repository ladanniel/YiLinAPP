package com.memory.platform.module.system.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.module.system.dao.ParameterManageDao;
import com.memory.platform.module.system.service.IParameterManageService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月23日 下午3:13:31 
* 修 改 人： 
* 日   期： 
* 描   述： 参数配置服务实现接口
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class ParameterManageService implements IParameterManageService {

	@Autowired
	private ParameterManageDao parameterManageDao;
	
	@Override
	public Map<String, Object> getPage(ParameterManage parameterManage, int start, int limit) {
		return parameterManageDao.getPage(parameterManage, start, limit);
	}

	@Override
	public void saveInfo(ParameterManage parameterManage) {
		parameterManageDao.save(parameterManage);
	}

	@Override
	public void updateInfo(ParameterManage parameterManage) {
		parameterManageDao.update(parameterManage);
	}

	@Override
	public ParameterManage getInfo(String id) {
		return parameterManageDao.getObjectById(ParameterManage.class, id);
	}

	@Override
	public void removeInfo(ParameterManage parameterManage) {
		parameterManageDao.delete(parameterManage);
	}

	@Override
	public ParameterManage getTypeInfo(ParaType type) {
		return parameterManageDao.getTypeInfo(type);
	}


}
