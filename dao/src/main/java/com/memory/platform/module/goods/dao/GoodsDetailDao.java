package com.memory.platform.module.goods.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 创 建 人： longqibo 日 期： 2016年6月14日 下午3:39:44 修 改 人： 日 期： 描 述： 货物详情 － dao 版 本 号：
 * V1.0
 */
@Repository
public class GoodsDetailDao extends BaseDao<GoodsDetail> {

	public List<Map<String, Object>> getListForMap(String goodsBasicId) {
		String sql = " SELECT vo.weight,vo.length,vo.height,vo.diameter,vo.wing_width AS wingWidth,type.NAME as goodsName,"
				+ "(vo.weight - vo.embark_weight) AS surplusWeight,(select t.name from goods_type t where t.id = type.prent_id) "
				+ "as goodsType FROM goods_detail AS vo LEFT JOIN goods_type AS type ON vo.goods_type_id = type.id WHERE vo.goods_baice_id = ?";
		String[] array = { goodsBasicId };
		return this.getListBySQLMap(sql, array);
	}

	/**
	 * 功能描述： 通过货物基本信息ID,查询货物的详细信息 输入参数: @param goodsBasicId 输入参数: @return 异 常： 创
	 * 建 人: yangjiaqiao 日 期: 2016年6月16日下午2:28:27 修 改 人: 日 期: 返
	 * 回：List<GoodsDetail>
	 */
	public List<GoodsDetail> getListGoodsDetail(String goodsBasicId) {
		String hql = "from GoodsDetail goodsDetail where goodsDetail.goodsBasic.id = :goodsBasicId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsBasicId", goodsBasicId);
		return this.getListByHql(hql, map);
	}

	public List<Map<String, Object>> getListMapGoodsDetail(String goodsBasicId) {
		String sqlStr = " SELECT d.id,d.add_user_id,d.create_time,d.update_time,d.update_user_id,d.height AS height,d.length AS length,"
				+ "d.version,d.weight AS weight,d.goods_baice_id AS goodsBaiceId,d.goods_type_id AS goodsTypeId,d.diameter AS diameter,"
				+ "d.wing_width AS wingWidth,d.embark_weight,d.specifica AS specifica,(SELECT tmpt.id FROM goods_type tmpt WHERE "
				+ "tmpt.id = t.prent_id ) AS goodsType,t.`name` AS goodsName,(SELECT tmpt. NAME FROM goods_type tmpt WHERE "
				+ "tmpt.id = t.prent_id ) AS goodsTypeName,d.weight - d.embark_weight as surplusWeight FROM goods_detail d LEFT JOIN "
				+ "goods_type t ON d.goods_type_id = t.id WHERE d.goods_baice_id = :goodsBasicId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsBasicId", goodsBasicId);
		return this.getListBySQLMap(sqlStr, map);
	}

	/**
	 * 功能描述： 保存货物详细信息 输入参数: @param list 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月15日上午9:56:49 修 改 人: 日 期: 返 回：void
	 */
	public void saveGoodsDetailList(List<GoodsDetail> list) {
		for (GoodsDetail goodsDetail : list) {
			this.save(goodsDetail);
		}
	}

	/**
	 * 功能描述： 修改货物详细信息 输入参数: @param list 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月15日上午9:57:11 修 改 人: 日 期: 返 回：void
	 */
	public void updateGoodsDetailList(List<GoodsDetail> list) {
		for (GoodsDetail goodsDetail : list) {
			this.merge(goodsDetail);
		}
	}

	/**
	 * 功能描述： 删除货物详情数据 输入参数: @param goodsBasicId 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月16日下午4:02:28 修 改 人: 日 期: 返 回：void
	 */
	public void deleteGoodsDetailList(String goodsBasicId) {
		String sql = "delete from goods_detail where goods_baice_id=?";
		Object[] args = new Object[] { goodsBasicId };
		this.updateSQL(sql, args);
	}

	public List<Map<String, Object>> getGoodsDetailByGoodsBasicId(String goodsBasicId) {
		StringBuffer sql = new StringBuffer(
				" SELECT detail.goods_baice_id,detail.goods_name,detail.weight,detail.length,detail.specifica,detail.embark_weight "
						+ "FROM goods_detail detail WHERE detail.goods_baice_id = :goodsBasicId ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsBasicId", goodsBasicId);
		return this.getListBySQLMap(sql.toString(), map);
	}
}
