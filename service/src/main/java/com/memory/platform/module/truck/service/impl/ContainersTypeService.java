package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.ContainersType;
import com.memory.platform.module.truck.dao.ContainersTypeDao;
import com.memory.platform.module.truck.service.IContainersTypeService;


/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：货箱形式业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class ContainersTypeService implements IContainersTypeService {
	@Autowired
	private ContainersTypeDao containersTypeDao;
	/**
	* 功 能 描 述：分页查询货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(ContainersType containersType, int start, int limit) {
		return containersTypeDao.getPage(containersType, start, limit);
	}
	/**
	* 功 能 描 述：根据id查询货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：ContainersType
	 */
	@Override
	public ContainersType getContainersTypeById(String id) {
		return containersTypeDao.getContainersTypeById(id);
	}
	/**
	* 功 能 描 述：保存货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveContainersType(ContainersType containersType) {
		containersTypeDao.saveContainersType(containersType);

	}
	/**
	* 功 能 描 述：修改货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateContainersType(ContainersType containersType) {
		containersTypeDao.updateContainersType(containersType);
	}
	/**
	* 功 能 描 述：检查货箱形式是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getContainersTypeByName(String name) {
		return containersTypeDao.getContainersTypeByName(name);
	}
	/**
	* 功 能 描 述：检查货箱形式是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getContainersTypeByName(String name, String containersTypeId) {
		return containersTypeDao.getContainersTypeByName(name, containersTypeId);
	}
	/**
	* 功 能 描 述：删除货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeContainersType(String id) {
		containersTypeDao.removeContainersType(id);
	}
	/**
	* 功 能 描 述：查询所有货箱形式
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<ContainersType> getContainersTypeList() {
		return containersTypeDao.getContainersTypeList();
	}

	
	@Override
	public List<Map<String, Object>> getContainersTypeListForMap() {
		return containersTypeDao.getContainersTypeListForMap();
	}
}
