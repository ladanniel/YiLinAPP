package com.memory.platform.module.truck.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.truck.AxleType;
import com.memory.platform.module.truck.dao.AxleTypeDao;
import com.memory.platform.module.truck.service.IAxleTypeService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：拖挂轮轴类型业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class AxleTypeService implements IAxleTypeService {
	@Autowired
	private AxleTypeDao axleTypeDao;
	/**
	 * 
	* 功 能 描 述：分页查询所有轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	@Override
	public Map<String, Object> getPage(AxleType axleType, int start, int limit) {
		return axleTypeDao.getPage(axleType, start, limit);
	}
	/**
	 * 
	* 功 能 描 述：根据编号查询轮轴类型实体
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：AxleType
	 */
	@Override
	public AxleType getAxleTypeById(String id) {
		return axleTypeDao.getAxleTypeById(id);
	}
	/**
	 * 
	* 功 能 描 述：保存轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void saveAxleType(AxleType axleType) {
		axleTypeDao.saveAxleType(axleType);

	}
	/**
	 * 
	* 功 能 描 述：修改轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateAxleType(AxleType axleType) {
		axleTypeDao.updateAxleType(axleType);
	}
	/**
	 * 
	* 功 能 描 述：检测轮轴类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getAxleTypeByName(String name) {
		return axleTypeDao.getAxleTypeByName(name);
	}
	/**
	 * 
	* 功 能 描 述：检测轮轴类型是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：boolean
	 */
	@Override
	public boolean getAxleTypeByName(String name, String axleTypeId) {
		return axleTypeDao.getAxleTypeByName(name, axleTypeId);
	}
	/**
	 * 
	* 功 能 描 述：删除轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeAxleType(String id) {
		axleTypeDao.removeAxleType(id);
	}
	/**
	 * 
	* 功 能 描 述：查询所有轮轴类型
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<AxleType> getAxleTypeList() {
		return axleTypeDao.getAxleTypeList();
	}
	/**
	 * 
	* 功 能 描 述：查询所有轮轴类型
	* 输 入 参 数： 
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：list
	 */
	@Override
	public List<Map<String, Object>> getAxleTypeListForMap() {
		return axleTypeDao.getAxleTypeListForMap();
	}
}
