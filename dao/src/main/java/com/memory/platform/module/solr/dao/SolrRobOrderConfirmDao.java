package com.memory.platform.module.solr.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.core.ArrayUtil;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.module.global.dao.BaseDao;

@Repository
public class SolrRobOrderConfirmDao extends BaseDao<RobOrderConfirm> {

	public List<RobOrderConfirm> getRobOrderConfirmListWithTimestamp(Date timeStamp) {
		String strSql = " SELECT c.id,c.transport_no,c.turck_id,c.rob_order_id,c.rob_order_no,c.unit_price,c.total_weight,"
				+ "c.transportation_cost,c.`status`,c.turck_user_id,c.add_user_id,c.create_time,c.update_time,c.update_user_id,"
				+ "c.confirm_data,c.lgistics_code,c.lgistics_num,c.lgistics_name,c.account_id,c.receipt_img,c.actual_weight,"
				+ "c.transportation_deposit,c.turck_cost,c.turck_deposit,c.original_receipt_img,c.end_data,c.special_status,"
				+ "c.lock_status,c.indemnity,c.rocessing_result,c.actualtransportation_cost,c.confirm_receipted_date,c.auto_payment_err,"
				+ "c.additional_cost,c.total_cost,c.time_stamp,c.special_type FROM rob_order_confirm c"
				+ " where c.time_stamp > :timeStamp order by c.create_time desc ";
		Map<String, Object> map = new HashMap<>();
		map.put("timeStamp", timeStamp);
		return this.getListBySql(strSql, map, RobOrderConfirm.class);
	}
	public List< Map<String,Object>> getSolrRoborderConfirmListByTimeStamp(Date timeStamp) {
		String sql = "SELECT rob_order_confirm.id,rob_order_confirm.status,goods_basic.id AS goods_basic_id,"
				+ "goods_basic.delivery_address,goods_basic.delivery_area_name,goods_basic.delivery_mobile,"
				+ "goods_basic.delivery_name,goods_basic.delivery_coordinate,goods_basic.consignee_address,"
				+ "goods_basic.consignee_area_name,goods_basic.consignee_coordinate,goods_basic.consignee_name,"
				+ "goods_basic.consignee_mobile,goods_basic.map_kilometer,goods_basic.create_time AS goods_basic_create_time,"
				+ "goods_basic.update_time AS goods_basic_update_time,goods_basic.unit_price AS goods_basic_unit_price,"
				+ "goods_basic.total_price AS goods_basic_total_price,goods_basic.total_weight,"
				+ "goods_basic.company_name AS goods_basic_company_name,goods_basic.remark AS goods_basic_remark,"
				+ "rob_order_confirm.create_time AS rob_order_create_time,rob_order_record.unit_price AS rob_order_unit_price,"
				+"IFNULL(( SELECT 2 FROM order_special_apply WHERE rob_order_confirm.id = order_special_apply.rob_orderconfirm_id AND order_special_apply.special_status = 3 ), 0 ) special_status_success," 
				 +"rob_order_confirm.lock_status,rob_order_confirm.special_status,"
				+ "goods_type.`name` AS goods_type_name,SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN "
				+ "rob_order_confirm.transportation_cost WHEN rob_order_confirm. STATUS = 1 THEN myRound "
				+ "(rob_order_confirm.actual_weight * rob_order_confirm.unit_price) WHEN rob_order_confirm.`status` > 1 "
				+ "AND rob_order_confirm.`status` <> 7 THEN rob_order_confirm.actualtransportation_cost END ) AS "
				+ "transportation_cost,SUM(CASE WHEN rob_order_confirm.`status` = 0 OR rob_order_confirm.`status` = 1 "
				+ "THEN rob_order_confirm.transportation_deposit WHEN rob_order_confirm.`status` = 7 THEN 0 ELSE "
				+ "rob_order_confirm.total_cost END) AS payment,SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN "
				+ "rob_order_confirm.total_weight WHEN rob_order_confirm.`status` > 0 AND rob_order_confirm.`status` <> 7 "
				+ "THEN rob_order_confirm.actual_weight ELSE 0 END ) AS actual_weight,sum(rob_order_confirm.additional_cost) "
				+ "additional_cost,sum(rob_order_confirm.total_cost) total_cost,SUM(1) AS count,rob_order_record.robbed_account_id,"
				+ "rob_order_record.account_id,rob_order_confirm.turck_user_id FROM goods_basic JOIN rob_order_record ON "
				+ "goods_basic.id = rob_order_record.goods_baice_id JOIN goods_type ON goods_basic.goods_type_id = goods_type.id "
				+ "JOIN rob_order_confirm ON rob_order_confirm.rob_order_id = rob_order_record.id JOIN mem_account ON mem_account.id = "
				+ "rob_order_record.account_id JOIN sys_company ON sys_company.id = mem_account.company_id WHERE 1 = 1   "
				+ " and  rob_order_confirm.time_stamp > :timeStamp GROUP BY goods_basic.id ";
			 
			Map<String, Object> params = new HashMap<>();
			params.put("timeStamp", timeStamp);
		 
//			final Map<String,Object> params = new HashMap<String, Object>();
//			if(idsList != null && idsList.size() > 0) {
//				where  += "rob_order_confirm.id in (" ;
//				int idx = 0;
//				for (String idInfo : idsList) {
//					String paramKey = ":rob_order_confirmID" + idx;
//					where  += paramKey;
//					if(idx !=idsList.size() - 1 ) {
//						where += ",";
//					}
//					params.put(paramKey,   idInfo);
//					idx ++;
//				}
//				where += ")";	 
//			}
		return 	this.getListBySQLMap(sql,params);
	}
	public Map<String, Object> getSolrRobOrderConfirmById(String id) {
		String sql = "SELECT rob_order_confirm.id,rob_order_confirm.status,goods_basic.id AS goods_basic_id,"
				+ "goods_basic.delivery_address,goods_basic.delivery_area_name,goods_basic.delivery_mobile,"
				+ "goods_basic.delivery_name,goods_basic.delivery_coordinate,goods_basic.consignee_address,"
				+ "goods_basic.consignee_area_name,goods_basic.consignee_coordinate,goods_basic.consignee_name,"
				+ "goods_basic.consignee_mobile,goods_basic.map_kilometer,goods_basic.create_time AS goods_basic_create_time,"
				+ "goods_basic.update_time AS goods_basic_update_time,goods_basic.unit_price AS goods_basic_unit_price,"
				+ "goods_basic.total_price AS goods_basic_total_price,goods_basic.total_weight,"
				+ "goods_basic.company_name AS goods_basic_company_name,goods_basic.remark AS goods_basic_remark,"
				+ "rob_order_confirm.create_time AS rob_order_create_time,rob_order_record.unit_price AS rob_order_unit_price,"
				+"IFNULL(( SELECT 1 FROM order_special_apply WHERE rob_order_confirm.id = order_special_apply.rob_orderconfirm_id AND order_special_apply.special_status = 3 ), 0 ) special_status,rob_order_confirm.lock_status,"
				+ "goods_type.`name` AS goods_type_name,SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN "
				+ "rob_order_confirm.transportation_cost WHEN rob_order_confirm. STATUS = 1 THEN myRound "
				+ "(rob_order_confirm.actual_weight * rob_order_confirm.unit_price) WHEN rob_order_confirm.`status` > 1 "
				+ "AND rob_order_confirm.`status` <> 7 THEN rob_order_confirm.actualtransportation_cost END ) AS "
				+ "transportation_cost,SUM(CASE WHEN rob_order_confirm.`status` = 0 OR rob_order_confirm.`status` = 1 "
				+ "THEN rob_order_confirm.transportation_deposit WHEN rob_order_confirm.`status` = 7 THEN 0 ELSE "
				+ "rob_order_confirm.total_cost END) AS payment,SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN "
				+ "rob_order_confirm.total_weight WHEN rob_order_confirm.`status` > 0 AND rob_order_confirm.`status` <> 7 "
				+ "THEN rob_order_confirm.actual_weight ELSE 0 END ) AS actual_weight,sum(rob_order_confirm.additional_cost) "
				+ "additional_cost,sum(rob_order_confirm.total_cost) total_cost,SUM(1) AS count,rob_order_record.robbed_account_id,"
				+ "rob_order_record.account_id,rob_order_confirm.turck_user_id FROM goods_basic JOIN rob_order_record ON "
				+ "goods_basic.id = rob_order_record.goods_baice_id JOIN goods_type ON goods_basic.goods_type_id = goods_type.id "
				+ "JOIN rob_order_confirm ON rob_order_confirm.rob_order_id = rob_order_record.id JOIN mem_account ON mem_account.id = "
				+ "rob_order_record.account_id JOIN sys_company ON sys_company.id = mem_account.company_id WHERE 1 = 1 AND "
				+ "rob_order_confirm.id = :robOrderConfirmId GROUP BY goods_basic.id ORDER BY "
				+ "rob_order_confirm.create_time DESC";
		Map<String, Object> map = new HashMap<>();
		map.put("robOrderConfirmId", id);
		return this.getSqlMap(sql, map);
	}
	
}
