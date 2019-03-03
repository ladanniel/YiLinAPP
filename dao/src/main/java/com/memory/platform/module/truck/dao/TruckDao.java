package com.memory.platform.module.truck.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.Margin;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.global.StringFromateTemplate;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 
 * 创 建 人：liyanzhang 日 期： 2016年5月30日 19:10:47 修 改 人： 修 改 日 期： 描 述：车辆基本信息DAO层 版 本
 * 号： V1.0
 */
@Repository
public class TruckDao extends BaseDao<Truck> {

	/**
	 *
	 * 功 能 描 述：通过车辆ID获取实体 输 入 参 数： @param id 输 入 参 数： @return 异 常： 创 建 人：
	 * liyanzhang 日 期： 2016年5月30日 19:11:54 修 改 人： 修 改 日 期： 返 回：Truck
	 */
	public Truck checkTruckByNo(String no) {
		String sql = " from Truck truck where truck.track_no=:track_no";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("track_no", no);
		Truck truck = this.getObjectByColumn(sql, map);
		return truck;
	}

	/**
	 *
	 * 功 能 描 述：通过驾驶员ID获取实体 输 入 参 数： @param id 输 入 参 数： @return 异 常： 创 建 人：
	 * liyanzhang 日 期： 2016年5月30日 19:11:54 修 改 人： 修 改 日 期： 返 回：Truck
	 */
	public Truck getTruckByAcountNo(String accountid) {
		String sql = " from Truck truck where truck.account.id=:accountid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountid", accountid);
		Truck truck = this.getObjectByColumn(sql, map);
		return truck;
	}

	/**
	 *
	 * 功 能 描 述：通过车牌号获取实体 输 入 参 数： @param id 输 入 参 数： @return 异 常： 创 建 人：
	 * liyanzhang 日 期： 2016年5月30日 19:11:54 修 改 人： 修 改 日 期： 返 回：Truck
	 */
	public Truck getTruckById(String id) {
		return this.getObjectById(Truck.class, id);
	}

	/**
	 * 
	 * 功 能 描 述：分页查询车辆列表 输 入 参 数： @param Truck 输 入 参 数： @param start 输 入 参
	 * 数： @param limit 输 入 参 数： @return 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日
	 * 19:13:01 修 改 人： 修 改 日 期： 返 回：Map<String,Object>
	 */
	public Map<String, Object> getPage(Truck truck, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Truck truck where 1=1 ";
		if (truck.getSearch() != null && !"".equals(truck.getSearch())) {
			hql += " and (truck.track_no like :name or truck.track_read_no=:track_read_no or truck.engine_no=:engine_no)";
			map.put("name", "%" + truck.getSearch() + "%");
			map.put("track_read_no", truck.getSearch());
			map.put("engine_no", truck.getSearch());
		}
		hql += " order by truck.track_no";
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 
	 * 功 能 描 述：多条件分页查询车辆列表 输 入 参 数： @param Truck 输 入 参 数： @param start 输 入 参
	 * 数： @param limit 输 入 参 数： @return 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日
	 * 19:13:01 修 改 人： 修 改 日 期： 返 回：Map<String,Object>
	 */
	public Map<String, Object> getTruckPage(Truck truck, int start, int limit) {
		Account user = UserUtil.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Truck truck where truck.company.id=:companyId ";
		map.put("companyId", user.getCompany().getId());
		if (truck.getSearch() != null && !"".equals(truck.getSearch())) {
			hql += " and (truck.track_no like :name or truck.track_read_no=:track_read_no or truck.engine_no=:engine_no)";
			map.put("name", "%" + truck.getSearch() + "%");
			map.put("track_read_no", truck.getSearch());
			map.put("engine_no", truck.getSearch());
		}
		if (!StringUtil.isEmpty(truck.getTrack_no())) {
			hql += " and truck.track_no like :track_no";
			map.put("track_no", truck.getTrack_no() + "%");
		}
		if (truck.getTruckTypeIds().length > 0) {
			hql += " and truck.truckType.id in(:truckType)";
			map.put("truckType", truck.getTruckTypeIds());
		}
		if (truck.getTruckPlateIds().length > 0) {
			hql += " and truck.truckPlate.id in(:truckPlate)";
			map.put("truckPlate", truck.getTruckPlateIds());
		}
		if (truck.getTruckBrandIds().length > 0) {
			hql += " and truck.truckBrand.id in(:truckBrand)";
			map.put("truckBrand", truck.getTruckBrandIds());
		}
		if (!StringUtil.isEmpty(truck.getTrack_read_no())) {
			hql += " and truck.track_read_no=:track_read_no";
			map.put("track_read_no", truck.getTrack_read_no());
		}
		if (!StringUtil.isEmpty(truck.getEngine_no())) {
			hql += " and truck.engine_no=:engine_no";
			map.put("engine_no", truck.getEngine_no());
		}
		hql += " order by truck.track_no";
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 
	 * 功 能 描 述：易林多条件分页查询车辆列表 输 入 参 数： @param Truck 输 入 参 数： @param start 输 入 参
	 * 数： @param limit 输 入 参 数： @return 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日
	 * 19:13:01 修 改 人： 修 改 日 期： 返 回：Map<String,Object>
	 */
	public Map<String, Object> getTruckPageByYilin(Truck truck, int start, int limit) {
		Account user = UserUtil.getUser();
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = " from Truck truck where 1=1 ";
		if (truck.getSearch() != null && !"".equals(truck.getSearch())) {
			hql += " and (truck.track_no like :name or truck.track_read_no=:track_read_no or truck.engine_no=:engine_no)";
			map.put("name", "%" + truck.getSearch() + "%");
			map.put("track_read_no", truck.getSearch());
			map.put("engine_no", truck.getSearch());
		}
		if (!StringUtil.isEmpty(truck.getTrack_no())) {
			hql += " and truck.track_no like :track_no";
			map.put("track_no", truck.getTrack_no() + "%");
		}
		if (!StringUtil.isEmpty(truck.getCompanyName())) {
			hql += " and truck.company.name like :companyName";
			map.put("companyName", truck.getCompanyName() + "%");
		}
		if (truck.getTruckTypeIds().length > 0) {
			hql += " and truck.truckType.id in(:truckType)";
			map.put("truckType", truck.getTruckTypeIds());
		}
		if (truck.getTruckPlateIds().length > 0) {
			hql += " and truck.truckPlate.id in(:truckPlate)";
			map.put("truckPlate", truck.getTruckPlateIds());
		}
		if (truck.getTruckBrandIds().length > 0) {
			hql += " and truck.truckBrand.id in(:truckBrand)";
			map.put("truckBrand", truck.getTruckBrandIds());
		}
		if (!StringUtil.isEmpty(truck.getTrack_read_no())) {
			hql += " and truck.track_read_no=:track_read_no";
			map.put("track_read_no", truck.getTrack_read_no());
		}
		if (!StringUtil.isEmpty(truck.getEngine_no())) {
			hql += " and truck.engine_no=:engine_no";
			map.put("engine_no", truck.getEngine_no());
		}
		hql += " order by truck.track_no";
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 
	 * 功 能 描 述：保存车辆 输 入 参 数： @param Truck 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日
	 * 19:15:13 修 改 人： 修 改 日 期： 返 回：void
	 */
	public void saveTruck(Truck truck) {
		this.save(truck);
	}

	/**
	 * 
	 * 功 能 描 述：更新车辆 输 入 参 数： @param Truck 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日
	 * 19:16:35 修 改 人： 修 改 日 期： 返 回：void
	 */
	public void updateTruck(Truck truck) {
		this.update(truck);
	}

	/**
	 * 
	 * 功 能 描 述：判断同名车辆是否存在 输 入 参 数： @param name 输 入 参 数： @return 异 常： 创 建 人：
	 * liyanzhang 日 期： 2016年5月30日 19:18:11 修 改 人： 修 改 日 期： 返 回：boolean
	 */
	public boolean getTruckByName(String name) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String sql = "SELECT COUNT(*) FROM sys_track as truck WHERE truck.track_no=:name";
		resMap.put("name", name);
		return this.getCountSqlIs(sql, resMap);
	}

	/**
	 * 
	 * 功 能 描 述：通过车牌和ID查询，判断数据库中是否存在 输 入 参 数： @param name 输 入 参 数： @param Truck 输
	 * 入 参 数： @return 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日 19:19:51 修 改 人： 修 改
	 * 日 期： 返 回：boolean--存在返回false，不存在返回true
	 */
	public boolean getTruckByName(String name, String truckId) {
		String hql = " from Truck truck where truck.track_no=:name";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		if (null != truckId) {
			hql += " and truck.id != :truckId";
			map.put("truckId", truckId);
		}
		return this.getListByHql(hql, map).size() > 0 ? false : true;
	}

	/**
	 * 
	 * 功 能 描 述：删除车辆 输 入 参 数： @param id 异 常： 创 建 人： liyanzhang 日 期： 2016年5月30日
	 * 19:21:53 修 改 人： 修 改 日 期： 返 回：void
	 */
	public void removeTruck(String id) {
		Truck truck = this.loadObjectById(Truck.class, id);
		this.delete(truck);
	}

	public List<Map<String, Object>> getCompanyTrucks(String companyId) {
		String sql = "SELECT a.id as value,concat_ws('--',a.track_no,b.`name`,b.phone) AS text FROM sys_track as a LEFT JOIN mem_account AS b ON a.account_id  = b.id where a.company_id = :company_id  AND a.account_id is not null";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company_id", companyId);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 功能描述： 修改车辆状态 输入参数: @param id 车辆ID 输入参数: @param truckStatus 车辆状态 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年7月15日上午11:18:01 修 改 人: 日 期: 返 回：void
	 */
	public void updateTruckStatus(String id, TruckStatus truckStatus) {
		String sql = "update sys_track set truck_status=? where id=?";
		Object[] objects = { truckStatus.ordinal(), id };
		this.updateSQL(sql, objects);
	}

	/**
	 * 功能描述： 修改车辆司机人员 输入参数: @param trackId 输入参数: @param accountId 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年7月15日上午11:20:22 修 改 人: 日 期: 返 回：void
	 */
	public void updateTruckUserId(String trackId, String accountId, String companyId) {
		String sql = "update sys_track set account_id=? where id=? and company_id = ?";
		Object[] objects = { accountId, trackId, companyId };
		this.updateSQL(sql, objects);
	}

	/**
	 * 功能描述： 修改车辆司机人员状态 输入参数: @param trackId 输入参数: @param accountId 异 常： 创 建 人:
	 * liyanzhang 日 期: 2016年7月15日上午11:20:22 修 改 人: 日 期: 返 回：void
	 */
	public void updateTruckUserId(String trackId, TruckStatus truckStatus, String accountId, String companyId) {
		String sql = "update sys_track set account_id=? where id=? and company_id = ?";
		Object[] objects = { accountId, truckStatus.ordinal(), trackId, companyId };
		this.updateSQL(sql, objects);
	}

	public List<Map<String, Object>> getCompantAccountTruckList(String companyId) {
		String sql = "SELECT a.id, a.name,  b.track_no, b.id as trackId,b.truck_status FROM mem_account AS a LEFT JOIN sys_track AS b ON a.id = b.account_id WHERE a.company_id = :company_id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company_id", companyId);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 功能描述： 查询是否有车辆装货，判断依据是：1、车辆不在运势中的状态 2、已分配司机的车辆 输入参数: @param companyId
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月30日下午10:23:30 修 改 人: 日
	 * 期: 返 回：int
	 */
	public int getChekcTruckNum(String companyId) {
		String sql = "SELECT count(a.id) FROM sys_track AS a  WHERE a.company_id = :company_id AND a.truck_status = 0 AND a.account_id IS NOT NULL";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company_id", companyId);
		return this.getCountSql(sql, map);
	}

	public List<Truck> getTruckByCompanyID(String companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("company_id", companyId);
		String hql = "from Truck where company_id=:company_id";
		return this.getListByHql(hql, map);
	}

	public List<Map<String, Object>> getTraceCarInfomation(String campanyID) {
		String strSql = "SELECT sys_track.track_no,sys_track.track_long,bas_truck_type.`name` AS truck_type_name,sys_track.capacity,"
				+ "sys_track.truck_status,sys_track.sim_card_id,sys_track.gps_device_id,sys_track.yy_vehicel_id,sys_track.yy_vehicel_key,"
				+ "IFNULL(mem_account.`name`,'未分配') AS track_user_name,goods_basic.consignee_coordinate,goods_basic.consignee_area_name,"
				+ "goods_basic.consignee_address,goods_basic.delivery_coordinate,goods_basic.delivery_area_name,goods_basic.delivery_address,"
				+ "sys_track_img.track_ahead_img FROM sys_track LEFT JOIN mem_account ON sys_track.account_id = mem_account.id "
				+ "JOIN bas_truck_type ON sys_track.card_type_id = bas_truck_type.id JOIN sys_company ON"
				+ " sys_track.company_id = sys_company.id AND sys_company.id = :campanyID LEFT JOIN rob_order_confirm ON "
				+ "sys_track.id = rob_order_confirm.turck_id AND rob_order_confirm.`status` < 4 LEFT JOIN rob_order_record ON rob_order_record.id"
				+ " = rob_order_confirm.rob_order_id LEFT JOIN goods_basic ON goods_basic.id = rob_order_record.goods_baice_id LEFT JOIN "
				+ "sys_track_img ON sys_track.id = sys_track_img.track_id where sys_track.yy_vehicel_id is not null and sys_track.yy_vehicel_key is not null";
		Map<String, Object> map = new HashMap<>();
		map.put("campanyID", campanyID);
		return super.getListBySQLMap(strSql, map);
	}

	/**
	 * 
	 * 功 能 描 述：根据商户查询车辆及绑定驾驶员 输 入 参 数： @param id 异 常： 创 建 人： liyanzhang 日 期：
	 * 2016年5月30日 19:21:53 修 改 人： 修 改 日 期： 返 回：list
	 */
	public List<Map<String, Object>> getTruckAndAccountListWithMap(String companyId) {
		String sql = "SELECT a.id,a.track_no,a.capacity,a.truck_status,b.id as accountId,b.name FROM sys_track AS a left join mem_account AS b ON a.account_id=b.id WHERE a.company_id=:company_id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company_id", companyId);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 功能描述： 查询车队车辆列表 输入参数: @param companyid 输入参数: @param 异 常： 创 建 人:liyanzhang
	 * 日 期: 2016年7月15日上午11:18:01 修 改 人: 日 期: 返 回：map
	 */
	public Map<String, Object> getTrucksByCommpanyWithMap(String companyid, int page, int size) {
		String sql = "select a.id,a.track_no,a.truck_status,a.capacity,b.name as trucktype,c.name as truckbrand FROM sys_track as a,bas_truck_type as b,bas_truck_brand as c where a.card_type_id=b.id and a.card_brand_id=c.id and a.company_id=:companyid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyid", companyid);
		return this.getPageSQLMap(sql, map, page, size);
	}

	/**
	 * 功能描述： 根据车辆编号查单个车辆 输入参数: @param id 输入参数: @param 异 常： 创 建 人:liyanzhang 日 期:
	 * 2016年7月15日上午11:18:01 修 改 人: 日 期: 返 回：list
	 */
	public List<Map<String, Object>> getTruckByIdWithMap(String id) {
		String sql = "select a.id,a.track_no,b.id as trucktypeid,b.name as trucktype,c.id as truckplateid,c.name as truckplate,a.track_long,a.capacity,d.id as truckbrandid,d.name as truckbrand,a.track_read_no,e.id as enginebrandid,e.name as enginebrand,a.engine_no,a.horsepower,a.vehiclelicense_img  FROM sys_track as a,bas_truck_type as b,bas_truck_plate as c,bas_truck_brand as d,bas_engine_brand as e where a.card_type_id=b.id and a.card_plate_id=c.id and a.card_brand_id=d.id and a.engine_brand_id=e.id and a.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 功能描述： 根据驾驶员id查询车辆 输入参数: @param accountid 输入参数: @param 异 常： 创 建
	 * 人:liyanzhang 日 期: 2016年7月15日上午11:18:01 修 改 人: 日 期: 返 回：list
	 */
	public List<Map<String, Object>> getTruckByAccountid(String id) {
		String sql = "select a.id,a.track_no,b.id as trucktypeid,b.name as trucktype,c.id as truckplateid,c.name as truckplate,a.track_long,a.capacity,d.id as truckbrandid,d.name as truckbrand,a.track_read_no,e.id as enginebrandid,e.name as enginebrand,a.engine_no,a.horsepower,a.vehiclelicense_img,a.account_id,a.company_id from sys_track as a,bas_truck_type as b,bas_truck_plate as c,bas_truck_brand as d,bas_engine_brand as e where a.card_type_id=b.id and a.card_plate_id=c.id and a.card_brand_id=d.id and a.engine_brand_id=e.id and a.account_id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 *
	 * 功 能 描 述：通过车辆ID获取实体 输 入 参 数： @param id 输 入 参 数： @return 异 常： 创 建 人：
	 * liyanzhang 日 期： 2016年5月30日 19:11:54 修 改 人： 修 改 日 期： 返 回：Truck
	 */
	public Truck getTruck(String truckid) {
		String sql = " from Truck truck where truck.id=:truckid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("truckid", truckid);
		Truck truck = this.getObjectByColumn(sql, map);
		return truck;
	}

	/*
	 * aiqiwu 2017-06-08 根据订单Id获取绑定车辆信息
	 */
	public List<Map<String, Object>> getTruckByRobOrderId(String robOrderId) {
		String sql = "SELECT sys_track.track_no,sys_track.track_long,bas_truck_type.`name` AS truck_type_name,"
				+ "sys_track.capacity,sys_track.truck_status,sys_track.sim_card_id,sys_track.gps_device_id,"
				+ "sys_track.yy_vehicel_id,sys_track.yy_vehicel_key,IFNULL(mem_account.`name`,'未分配') AS "
				+ "track_user_name,goods_basic.consignee_coordinate,goods_basic.consignee_area_name,"
				+ "goods_basic.consignee_address,goods_basic.delivery_coordinate,"
				+ "goods_basic.delivery_area_name,goods_basic.delivery_address,"
				+ "sys_track_img.track_ahead_img FROM sys_track LEFT JOIN mem_account ON "
				+ "sys_track.account_id = mem_account.id JOIN bas_truck_type ON sys_"
				+ "track.card_type_id = bas_truck_type.id JOIN sys_company ON sys_track.company_id = "
				+ "sys_company.id LEFT JOIN rob_order_confirm ON sys_track.id = rob_order_confirm.turck_id "
				+ "AND rob_order_confirm.`status` < 4 LEFT JOIN rob_order_record ON "
				+ "rob_order_record.id = rob_order_confirm.rob_order_id LEFT JOIN goods_basic ON "
				+ "goods_basic.id = rob_order_record.goods_baice_id LEFT JOIN sys_track_img ON "
				+ "sys_track.id = sys_track_img.track_id WHERE sys_track.yy_vehicel_id IS NOT NULL "
				+ "AND sys_track.yy_vehicel_key IS NOT NULL and rob_order_record.id = :robOrderId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", robOrderId);
		return this.getListBySQLMap(sql, map);
	}

	public void updateTruckMerge(Truck truck) {
		this.merge(truck);
	}

	public List<Map<String, Object>> getTruckAndAccountByCompanyID(String companyID) {
		String strSql = " SELECT account.id AS account_id,account.name,account.name as account_name,account.phone,truck.track_no,truck.id,"
				+ "truck.track_long,truck.capacity,truck.engine_no,truck.horsepower,truck.truck_status,"
				+ "truckType.`name` truck_type_name,plate.`name` truck_plate_name,brand.`name` truck_brand_name,truck.track_read_no,"
				+ "truckType.id truck_type_id,plate.id truck_plate_id,brand.id truck_brand_id,CASE WHEN account.id IS NULL THEN 9999999 ELSE 0 END AS order_num FROM sys_track AS truck"
				+ " LEFT JOIN mem_account AS account ON account.id = truck.account_id LEFT JOIN bas_truck_type AS "
				+ "truckType ON truck.card_type_id = truckType.id LEFT JOIN bas_truck_plate plate ON truck.card_plate_id = "
				+ "plate.id LEFT JOIN bas_truck_brand brand ON truck.card_brand_id = brand.id"
				+ " WHERE truck.company_id = :companyID ORDER BY order_num,truck.update_time DESC  ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyID", companyID);
		return this.getListBySQLMap(strSql, map);
	}
	public Map<String, Object> getTruckByID(String truckID) {
		String strSql = "SELECT a.id AS account_id, a.name, 	a.phone, "
				+ " b.track_no, b.id, b.track_long, b.capacity, b.engine_no, b.horsepower ,b.truck_status ,  b.gps_device_id,b.sim_card_id,"
				+ " c.id as truck_type_id, c.`name` truck_type_name ,bas_truck_brand.id as brank_id ,bas_truck_brand.id  as brand_id,  bas_truck_brand.name as brand_name "
				+ " FROM sys_track AS b LEFT JOIN mem_account AS a ON a.id = b.account_id  join bas_truck_type as c on b.card_type_id = c.id  "
				+"   join bas_truck_brand on b.card_brand_id = bas_truck_brand.id  "
				+ " WHERE  b.id =:truckID   ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("truckID", truckID);
		return	this.getSqlMap(strSql, map);
	}
	
	public Map<String, Object> getTruckByCompanyIdPage(String companyID,int start,int limit) {
		String strSql = " SELECT account.id AS account_id,account.name,account.name as account_name,account.phone,truck.track_no,truck.id,"
				+ "truck.track_long,truck.capacity,truck.engine_no,truck.horsepower,truck.truck_status,"
				+ "truckType.`name` truck_type_name,plate.`name` truck_plate_name,brand.`name` truck_brand_name,truck.track_read_no,"
				+ "truckType.id truck_type_id,plate.id truck_plate_id,brand.id truck_brand_id,CASE WHEN account.id IS NULL THEN 9999999 ELSE 0 END AS order_num FROM sys_track AS truck"
				+ " LEFT JOIN mem_account AS account ON account.id = truck.account_id LEFT JOIN bas_truck_type AS "
				+ "truckType ON truck.card_type_id = truckType.id LEFT JOIN bas_truck_plate plate ON truck.card_plate_id = "
				+ "plate.id LEFT JOIN bas_truck_brand brand ON truck.card_brand_id = brand.id"
				+ " WHERE truck.company_id = :companyID ORDER BY order_num,truck.update_time DESC  ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyID", companyID);
		return this.getPageSQLMap(strSql, map, start, limit);
	}
	
	/**
	 * 根据账号获取车辆信息
	 */
	public Map<String, Object> getTruckByAccountIdForMap(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = " SELECT truck.id, truck.track_no,type. NAME AS truckType,plate. NAME AS truckPlate,truck.track_long,"
				+ "truck.capacity,brand. NAME AS truckBrand,engineBrand. NAME AS engineBrand,truck.engine_no,truck.horsepower "
				+ "FROM sys_track AS truck,bas_truck_type AS type,bas_truck_plate AS plate,bas_truck_brand AS brand,bas_engine_brand"
				+ " AS engineBrand WHERE truck.card_type_id = type.id AND truck.card_plate_id = plate.id AND "
				+ "truck.card_brand_id = brand.id AND truck.engine_brand_id = engineBrand.id AND truck.account_id = :accountId ";
		map.put("accountId", accountId);
		return this.getSqlMap(sql, map);
	}

	public List<Truck> getBindTruckByCompanyID(String companeyID) {
		 String hql = " from Truck truck where truck.company.id = :companyID and truck.gps_device_id  is not null ";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("companyID", companeyID);
		 return  super.getListByHql(hql, map);
		
	}
}
