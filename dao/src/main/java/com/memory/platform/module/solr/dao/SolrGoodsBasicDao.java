package com.memory.platform.module.solr.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.module.global.dao.BaseDao;

public class SolrGoodsBasicDao extends BaseDao<GoodsBasic> {
	public List<GoodsBasic> getGoodsBasicListWithTimestamp(Date timeStamp) {
		String strSql = " SELECT b.id,b.delivery_no,b.account_id,b.company_id,b.add_user_id,b.create_time,b.update_time,"
				+ "b.update_user_id,b.audit_person_id,b.company_name,b.consignee_address,b.consignee_email,"
				+ "b.consignee_mobile,b.consignee_name,b.remark,b.`status`,b.on_line,b.stock_type_names,"
				+ "b.total_price,b.total_weight,b.unit_price,b.version,b.audit_cause,b.consignee_coordinate,"
				+ "b.delivery_address,b.delivery_coordinate,b.delivery_email,b.delivery_mobile,b.delivery_name,"
				+ "b.finite_time,b.is_long_time,b.release_time,b.consignee_area_id,b.consignee_area_name,"
				+ "b.delivery_area_id,b.delivery_area_name,b.goods_type_id,b.auditPerson,audit_time,b.hasLock,"
				+ "b.embark_weight,b.map_kilometer,b.stockTypes,b.time_stamp FROM goods_basic b "
				+ "where   b.time_stamp > :timeStamp order by b.create_time desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeStamp", timeStamp);
		
		return this.getListBySql(strSql, map, GoodsBasic.class);
	}
}
