package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.base.Area;


public interface IPersonnelAreaService {
	/**
	 * 获取所有区域信息
	 * @param menuId
	 * @return
	 */
	List<Map<String, Object>> getAreaList();
	List<Area> getAreaAll();
}
