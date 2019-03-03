package com.memory.platform.module.goods.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 创 建 人： longqibo 日 期： 2016年6月2日 上午10:59:04 修 改 人： 日 期： 描 述： 货物类型 － Dao 版 本 号：
 * V1.0
 */
@Repository
public class GoodsTypeDao extends BaseDao<GoodsType> {

	/**
	 * 功能描述： 查询所有的货物类型数据 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月6日上午11:49:34 修 改 人: 日 期: 返 回：List<GoodsType>
	 */
	public List<GoodsType> getAllList() {
		return this.getListByHql(" from GoodsType  order by create_time asc ", null);
	}

	public List<Map<String, Object>> getGoodsType() {
		String sql = " SELECT a.id as value,a.name as text FROM goods_type a where a.level = 1 order by a.create_time asc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 功能描述： 查询第一级货物类型数据 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月6日上午11:49:50 修 改 人: 日 期: 返 回：List<GoodsType>
	 */
	public List<GoodsType> getListByLeav() {
		return this.getListByHql(
				" from GoodsType goodsType where goodsType.level = 1 order by goodsType.create_time asc ", null);
	}

	/**
	 * 功能描述： 通过名称查询是否存在相同的货物类型数据 输入参数: @param name 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年6月6日上午11:52:50 修 改 人: 日 期: 返 回：GoodsType
	 */
	public GoodsType getGoodsTypeByName(String name, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from GoodsType goodsType where goodsType.name = :name ";
		if (!StringUtils.isEmpty(id)) {
			hql += " and  goodsType.id != :id";
			map.put("id", id);
		}
		map.put("name", name);
		return this.getObjectByColumn(hql, map);
	}

	public List<GoodsType> getGoodsTypeByPrentId(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prentId", id);
		return this.getListByHql(" from GoodsType goodsType where goodsType.prentId = :prentId", map);
	}

}
