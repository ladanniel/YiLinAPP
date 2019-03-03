package com.memory.platform.module.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月17日 上午10:36:10 
* 修 改 人： 
* 日   期： 
* 描   述： 订单详情dao
* 版 本 号：  V1.0
 */
@Repository
public class RobOrderRecordInfoDao extends BaseDao<RobOrderRecordInfo> {

	public List<Map<String, Object>> getInfoForList(String orderId){
		String sql = "select * from rob_order_record_info as info where info.rob_order_record_id = :orderId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		return this.getListBySQLMap(sql, map);
	}
	
	public List<RobOrderRecordInfo> getList(String orderId){
		StringBuffer hql = new StringBuffer(" from RobOrderRecordInfo info where info.robOrderRecord.id = :orderId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		hql.append(" order by info.create_time desc");
		return this.getListByHql(hql.toString(), map);
	}
	
	/**
	* 功能描述： 查询抢单的货物信息
	* 输入参数:  @param robOrderId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午2:26:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	public List<RobOrderRecordInfo> getListByRobOrderRecordId(String robOrderId){
		StringBuffer hql = new StringBuffer(" from RobOrderRecordInfo info where info.robOrderRecord.id = :robOrderId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("robOrderId", robOrderId);
		hql.append(" order by info.goodsDetail.id,info.create_time ASC");
		return this.getListByHql(hql.toString(), map);
	}
	
	/**
	* 功能描述： 查询详细货物已拆分的货物信息
	* 输入参数:  @param pid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午2:27:18
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	public List<Map<String, Object>> getListByRobOrderRecordInfoByPid(String pid,String robOrderId){
		StringBuffer hql = new StringBuffer(" from RobOrderRecordInfo info where info.parentId = :pid and info.robOrderRecord.id = :robOrderId");
		Map<String, Object> map = new HashMap<String,Object>();
		
		hql.append(" order by info.create_time desc");
		StringBuffer sql = new StringBuffer(" SELECT a.id,a.parent_id as parentId,a.goods_detail_id AS goodsDetailId,c.name as goodsTypeName,b.goods_name as goodsName,a.weight "
				+ "FROM rob_order_record_info AS a LEFT JOIN goods_detail AS b ON a.goods_detail_id  = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id "
				+ "WHERE a.parent_id = :pid AND a.rob_order_record_id = :robOrderId");
		map.put("pid", pid);
		map.put("robOrderId", robOrderId);
		return this.getListBySQLMap(sql.toString(), map);
	}
	
	
	
	/**
	* 功能描述： 保存抢单详细信息
	* 输入参数:  @param list
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月15日上午9:56:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void saveRobOrderRecordInfo(List<RobOrderRecordInfo> list){
		for (RobOrderRecordInfo orderRecordInfo : list) {
			this.save(orderRecordInfo);		
		}
	}
	
	/**
	* 功能描述： 删除货物信息
	* 输入参数:  @param robOrderRecordId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月28日上午11:22:07
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void deleteRobOrderRecordInfo(String robOrderRecordId){
		String sql = "delete from rob_order_record_info where rob_order_record_id=?";
		Object[] args = new Object[] { robOrderRecordId };
		this.updateSQL(sql, args);
	}
	
	/**
	* 功能描述： 删除被拆分的货物信息
	* 输入参数:  @param robOrderRecordId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月10日上午11:05:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void deleteRobOrderRecordInfoSplit(String robOrderRecordId){
		String sql = "delete from rob_order_record_info where parent_id=?";
		Object[] args = new Object[] { robOrderRecordId };
		this.updateSQL(sql, args);
	}
	
 
	
	/**
	* 功能描述： 更新抢单详细货物信息，更新装货车辆
	* 输入参数:  @param id
	* 输入参数:  @param stockId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月2日下午4:26:27
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public void updateRobOrderRecordInfo(String id,String stockId,String accountId,String rob_order_record_id){
		String sql = "update rob_order_record_info set stock_id=?,update_time = ? ,update_user_id = ?"+
				"where id=? and add_user_id = ? and rob_order_record_id = ?";
		Date day = new Date();
		Object[] objects = {stockId,day,accountId,id,accountId,rob_order_record_id};
		this.updateSQL(sql, objects);
	}
	
	/**
	* 功能描述： 获取详细货物信息
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午3:27:33
	* 修 改 人: 
	* 日    期: 
	* 返    回：RobOrderRecordInfo
	 */
	 public RobOrderRecordInfo getRobOrderRecordInfoById(String id){
		return this.getObjectById(RobOrderRecordInfo.class, id);
	}
	
	/**
	* 功能描述： 通过抢单ID,查询抢单详细列表
	* 输入参数:  @param orderId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日下午12:24:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	 public List<RobOrderRecordInfo> getListByRobOrderRecord(String robOrderId, String truckID){
		StringBuffer hql = new StringBuffer(" from RobOrderRecordInfo info where info.robOrderRecord.id = :robOrderId and info.stockId=:stockId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("robOrderId", robOrderId);
		map.put("stockId", truckID);
		hql.append(" order by info.goodsDetail.id,info.create_time ASC");
		return this.getListByHql(hql.toString(), map);
	}
		public double getInfoActualWeightt(String infoId){
			String sql = "SELECT a.actual_weight FROM rob_order_record_info AS a WHERE a.id = :infoId";
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("infoId", infoId);
			return Double.parseDouble(this.getSqlMap(sql, map).get("actual_weight").toString());
		}
		
		/**
		* 功能描述： 查询抢单详细的货物信息
		* 输入参数:  @param robOrderId  抢单ID
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月11日下午12:47:48
		* 修 改 人: 
		* 日    期: 
		* 返    回：List<Map<String,Object>>
		 */
		public List<Map<String, Object>> getRobOrderRecordInfoList(String robOrderId){
			StringBuffer sql = new StringBuffer("SELECT "
					+ "a.id,c.name AS goods_type_name,b.goods_name,(b.weight - b.embark_weight) as surplus_weight,a.weight,b.height,b.length,b.diameter,b.wing_width,a.is_parent,a.is_split,a.parent_id AS parentId,b.id AS goodsDetailId, " 
					+"  b.weight goods_detail_weight,b.specifica "
					+ "FROM  rob_order_record_info AS a LEFT JOIN goods_detail AS b ON a.goods_detail_id = b.id  LEFT JOIN goods_type AS c ON b.goods_type_id = c.id  "
					+ "WHERE a.rob_order_record_id = :robOrderId");
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("robOrderId",robOrderId);
			return this.getListBySQLMap(sql.toString(), map);
		}
		/**
		* 功能描述： 货物抢单详细货物信息
		* 输入参数:  @param id
		* 输入参数:  @param account
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月13日下午2:58:12
		* 修 改 人: 
		* 日    期: 
		* 返    回：Map<String,Object>
		 */
		public Map<String, Object> getRobOrderRecordInfoById(String id,Account account){
			StringBuffer sql = new StringBuffer("SELECT "
					+ "a.id,a.weight,c.name AS goodsTypeName,b.goods_name AS goodsName,a.parent_id AS parentId,b.id AS goodsDetailId,a.rob_order_record_id AS robOrderId "
					+ " FROM rob_order_record_info AS a LEFT JOIN goods_detail AS b ON a.goods_detail_id = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id"
					+ " WHERE a.id = :id AND  a.add_user_id = :add_user_id");
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id",id);
			map.put("add_user_id",account.getId());
			return this.getSqlMap(sql.toString(), map);
		}
		
		/**
		* 功能描述： 获取被拆分货物的列表
		* 输入参数:  @param robOrderId
		* 输入参数:  @return
		* 异    常： 
		* 创 建 人: yangjiaqiao
		* 日    期: 2016年8月13日下午3:34:42
		* 修 改 人: 
		* 日    期: 
		* 返    回：List<Map<String,Object>>
		 */
		public List<Map<String, Object>> getRobOrderRecordInfoSplitList(String robOrderInfoId){
			StringBuffer sql = new StringBuffer("SELECT "
					+ "a.id,a.weight,c.name AS goodsTypeName,b.goods_name AS goodsName,a.parent_id AS parentId,b.id AS goodsDetailId "
					+ " FROM rob_order_record_info AS a LEFT JOIN goods_detail AS b ON a.goods_detail_id = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id"
					+ " WHERE a.parent_id = :robOrderInfoId");
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("robOrderInfoId",robOrderInfoId);
			return this.getListBySQLMap(sql.toString(), map);
		}

		 
		
}
