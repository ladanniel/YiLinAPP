package com.memory.platform.module.solr.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.module.global.dao.BaseDao;
@Repository
public class SolrRobOrderRecordDao extends BaseDao<RobOrderRecord> {

	public List<RobOrderRecord> getRobOrderRecordListWithTimestamp(Date timeStamp) {
		String strSql = " SELECT id,rob_order_no,add_user_id,create_time,update_time,update_user_id,audit_person,audit_person_id,"
				+ "audit_time,companyName,robbed_company_id,robbed_account_id,goods_types,remark,status,total_price,"
				+ "unit_price,version,weight,account_id,goods_baice_id,cancel_user_id,cancel_remark,cancel_date,actualWeight,"
				+ "deposit_unit_price,time_stamp FROM rob_order_record where time_stamp > :timeStamp order by create_time desc ";
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", timeStamp);
		return this.getListBySql(strSql, map, RobOrderRecord.class);
	}

	public List<Map<String, Object>> getSolrRobOrderRecordByTimeStamp(Date timeStamp) {
		String sql = "SELECT a.id,a.rob_order_no,b.delivery_area_name,b.delivery_coordinate,b.consignee_area_name,"
				+ "b.consignee_coordinate,b.is_long_time,b.finite_time,b.company_name,c. NAME AS goods_type_name,"
				+ "a.weight,a.unit_price,a.total_price,a.status status,b.delivery_name,b.consignee_name,b.total_weight "
				+ "goods_total_weight,b.map_kilometer,a.create_time rob_order_time,b.create_time goods_basic_create_time,"
				+" a.time_stamp,"
				+ "b.unit_price goods_unit_price,a.account_id FROM rob_order_record AS a LEFT JOIN goods_basic AS b ON "
				+ "a.goods_baice_id = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id WHERE "
				+ "a.time_stamp >:timeStamp   ";
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", timeStamp);
		return this.getListBySQLMap(sql, map);
	}
}