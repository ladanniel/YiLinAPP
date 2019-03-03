package com.memory.platform.module.system.service;

import java.util.Map;

import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月23日 下午3:09:14 
* 修 改 人： 
* 日   期： 
* 描   述： 参数设置服务接口
* 版 本 号：  V1.0
 */
public interface IParameterManageService {

	Map<String, Object> getPage(ParameterManage parameterManage,int start, int limit);
	
	void saveInfo(ParameterManage parameterManage);
	
	void updateInfo(ParameterManage parameterManage);
	
	ParameterManage getInfo(String id);
	
	void removeInfo(ParameterManage parameterManage);
	
	ParameterManage getTypeInfo(ParaType type);
}
