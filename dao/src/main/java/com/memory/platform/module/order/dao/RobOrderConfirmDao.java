package com.memory.platform.module.order.dao;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.memory.platform.core.AppUtil;
import com.memory.platform.core.MapUtil;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.LockStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialType;
import com.memory.platform.entity.order.RobOrderConfirm.Status;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.global.ArrayUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.dao.BaseDao;
import com.memory.platform.module.order.vo.OrderInfo;
import com.memory.platform.module.order.vo.OrderTruck;
import com.memory.platform.solr.SolrStatementUtils;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年7月2日 下午3:45:46 修 改 人： 日 期： 描 述： 抢单确认DAO 版 本 号：
 * V1.0
 */
@Repository
public class RobOrderConfirmDao extends BaseDao<RobOrderConfirm> {
	@Autowired
	SolrUtils solrUtils;

	/**
	 * 功能描述： 保存抢单确认信息，根据所拉货物的车辆，保存抢单ID、单号 输入参数: @param list 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年7月2日下午3:47:53 修 改 人: 日 期: 返 回：void
	 */
	public void saveRobOrderConfirmList(List<RobOrderConfirm> list) {
		for (RobOrderConfirm robOrderConfirm : list) {
			this.save(robOrderConfirm);
		}
	}

	/**
	 * 
	 * 功能描述： 根据实体查询订单信息 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getCompanyOrderPage(
			RobOrderRecord robOrderRecord, int start, int limit) {
		Account account = UserUtil.getUser();
		UserType userType = account.getUserType();

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM myorder_query as record where 1=1");
		if (UserType.company.equals(userType)) {// 商户
			CompanyType companyType = account.getCompany().getCompanyType();
			String omcpanyTypeName = companyType.getName();

			if (omcpanyTypeName.equals("货主")) {
				where.append(" AND record.robbedCompanyId =:robbedCompanyId");
				map.put("robbedCompanyId", account.getCompany().getId());
			}

			if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
				where.append(" AND record.accountCompanyId =:accountCompanyId");
				map.put("accountCompanyId", account.getCompany().getId());
			}

		}

		if (!StringUtils.isEmpty(robOrderRecord.getConsigneeAreaName())) {
			where.append(" AND record.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal",
					robOrderRecord.getConsigneeAreaName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" AND record.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobOrderNo())) {
			where.append(" AND record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", "%" + robOrderRecord.getRobOrderNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getTransportNo())) {
			where.append(" AND record.transportNo like :transportNo");
			map.put("transportNo", "%" + robOrderRecord.getTransportNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getCompanyName())) {
			where.append(" AND record.companyName like :companyName");
			map.put("companyName", "%" + robOrderRecord.getCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobbedCompanyName())) {
			where.append(" AND record.robbedCompanyName like :robbedCompanyName");
			map.put("robbedCompanyName",
					"%" + robOrderRecord.getRobbedCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryNo())) {
			where.append(" AND record.deliveryNo =:deliveryNo");
			map.put("deliveryNo", robOrderRecord.getDeliveryNo());
		}

		if (robOrderRecord.getGoodsTypeId() != null
				&& robOrderRecord.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsTypeId in (:goodsTypeId)");
			map.put("goodsTypeId", robOrderRecord.getGoodsTypeId());
		}

		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" AND record.status in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}

		where.append(" group by(record.id)");
		return this.getPageSQLMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 
	 * 功能描述： 根据实体查询订单信息 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getOrderPage(RobOrderRecord robOrderRecord,
			Account account, int start, int limit) {

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT record.robOrderNo,record.consigneeAreaName,"
						+ "record.deliveryAreaName," + "record.goodType,"
						+ "record.weight," + "record.unitPrice,"
						+ "record.robbedCompanyName," + "record.finiteTime,"
						+ "record.id," + "record.robOrderConfirmId"
						+ " FROM myorder_query as record where 1=1");
		/*
		 * if(UserType.company.equals(userType)){//商户 CompanyType companyType =
		 * account.getCompany().getCompanyType(); String omcpanyTypeName =
		 * companyType.getName();
		 * 
		 * 
		 * if(omcpanyTypeName.equals("货主")||omcpanyTypeName.equals("个人司机")){
		 * where.append(" AND record.robbedCompanyId =:robbedCompanyId");
		 * map.put("robbedCompanyId",account.getCompany().getId()); }
		 * 
		 * if(omcpanyTypeName.equals("车队")){
		 * where.append(" AND record.accountCompanyId =:accountCompanyId");
		 * map.put("accountCompanyId",account.getCompany().getId()); }
		 * 
		 * 
		 * }
		 */

		if ("车队".equals(account.getCompany().getCompanyType().getName())
				|| "个人司机".equals(account.getCompany().getCompanyType()
						.getName())) {
			where.append(" AND record.truckUserId =:truckUserId");
			map.put("truckUserId", account.getId());
		}
		if ("货主".equals(account.getCompany().getCompanyType().getName())) {
			where.append(" AND record.robbedAccountId =:robbedAccountId");
			map.put("robbedAccountId", account.getId());
		}

		if ("管理".equals(account.getCompany().getCompanyType().getName())
				|| "系统".equals(account.getCompany().getCompanyType().getName())) {
			where.append(" AND record.receiptUserId =:receiptUserId");
			map.put("receiptUserId", account.getId());
		}

		if (!StringUtils.isEmpty(robOrderRecord.getConsigneeAreaName())) {
			where.append(" AND record.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal",
					robOrderRecord.getConsigneeAreaName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" AND record.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobOrderNo())) {
			where.append(" AND record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", "%" + robOrderRecord.getRobOrderNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getCompanyName())) {
			where.append(" AND record.companyName like :companyName");
			map.put("companyName", "%" + robOrderRecord.getCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobbedCompanyName())) {
			where.append(" AND record.robbedCompanyName like :robbedCompanyName");
			map.put("robbedCompanyName",
					"%" + robOrderRecord.getRobbedCompanyName() + "%");
		}

		if (robOrderRecord.getGoodsTypeId() != null
				&& robOrderRecord.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsTypeId in (:goodsTypeId)");
			map.put("goodsTypeId", robOrderRecord.getGoodsTypeId());
		}

		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" AND record.status in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}

		where.append(" group by(record.id)");
		// return this.getPageSQLMap(sql.append(where).toString(), map, start,
		// limit);
		return this.getPageSqlListMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 根据状态和车俩ID获取订单信息
	 * 
	 * @param status
	 * @param truckId
	 * @return
	 */
	public RobOrderConfirm getRobOrderConfirm(Status status, String truckId) {
		StringBuffer hql = new StringBuffer(
				" from RobOrderConfirm record where record.status=:status and record.turckId =:turckId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("turckId", truckId);
		List<RobOrderConfirm> list = this.getListByHql(hql.toString(), map);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取订单下没有完结的运单
	 * 
	 * @param RobOrderRecordId
	 * @return
	 */
	public List<RobOrderConfirm> getRobOrderConfirm(String robOrderRecordId) {
		StringBuffer hql = new StringBuffer(
				" from RobOrderConfirm record where record.status !=:status_a and record.status!=:status_b and record.robOrderId=:robOrderRecordId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status_a", Status.ordercompletion);
		map.put("status_b", Status.singlepin);
		map.put("robOrderRecordId", robOrderRecordId);

		return this.getListByHql(hql.toString(), map);
	}

	/**
	 * 获取运单详细信息
	 * 
	 * @param robOrderConfirmId
	 * @return
	 */

	public Map<String, Object> getOrderConfirmInfo(String robOrderConfirmId) {
		StringBuffer sql = new StringBuffer(
				"SELECT  c.id as robOrderConfirmId,b.unit_price as consignorUnitPrice,"
						+ "c.rob_order_no as robOrderNo,"
						+ "c.transport_no as transportNo,"
						+ "c.total_weight as totalWeight,"
						+ "c.actual_weight as actualWeight,"
						+ "c.unit_price as unitPrice,"
						+ "c.transportation_deposit as transportationDeposit,"
						+ "c.turck_cost as turckCost,"
						+ "c.`status` as status,"
						+ "c.`turck_id` as turckId,"
						+ "c.`rob_order_id` as robOrderId,"
						+ "c.transportation_cost as transportationCost,"
						+ "c.special_type as specialType,"
						+ "c.special_status as specialStatus,"
						+ "c.rocessing_result as rocessingResult,"
						+ "c.lock_status as lockStatus,"
						+ "c.original_receipt_img as originalReceiptImg,"
						+ "c.receipt_img as receiptImg,"
						+ "c.lgistics_name as lgisticsName,"
						+ "c.lgistics_num as lgisticsNum,"
						+ "c.lgistics_code as lgisticsCode,"
						+ "sys_d.`name` as deliveryCompanyName,"
						+ "b.delivery_name as deliveryName,"
						+ "b.delivery_mobile as deliveryMobile,"
						+ "b.delivery_area_name as deliveryAreaName,"
						+ "b.delivery_address as deliveryAddress,"
						+ "b.consignee_name as consigneeName,"
						+ "b.consignee_mobile as consigneeMobile,"
						+ "b.consignee_address as consigneeAddress,"
						+ "b.consignee_area_name as consigneeAreaName,"
						+ "b.delivery_coordinate as deliveryCoordinate,"
						+ "b.consignee_coordinate as consigneeCoordinate,"
						+ "b.map_kilometer as mapKilometer,"
						+ "t.track_no as trackNo,"
						+ "tu.`name` as turckUserName,"
						+ "tu.phone as turckUserPhone,"
						+ "(select name from sys_company where id=tu.company_id)as turckCompanyName"
						+ " from rob_order_confirm c"
						+ " LEFT JOIN `rob_order_record` `r` on r.id = c.rob_order_id"
						+ " LEFT JOIN goods_basic b on b.id = r.goods_baice_id"
						+ " LEFT JOIN `sys_company` `sys_d` on sys_d.id = b.company_id"
						+ " LEFT JOIN sys_track as t on t.id = c.turck_id"
						+ " LEFT JOIN mem_account as tu on tu.id = c.turck_user_id"
						+ " LEFT JOIN sys_company as sys_t on t.id = tu.company_id"
						+ " WHERE c.id=:robOrderConfirmId");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderConfirmId", robOrderConfirmId);
		return this.getSqlMap(sql.toString(), map);
	}

	/**
	 * 获取司机拉货信息 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:24:08 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getOrderInfoPage(String robOrderId,
			String truckID) {
		StringBuffer sql = new StringBuffer(
				"SELECT (r.unit_price*i.actual_weight)as totalPrice,d.goods_name as goodName,t.`name` as goodTypeName,r.unit_price as unitPrice,i.actual_weight as weight FROM rob_order_record_info i"
						+ " LEFT JOIN rob_order_record r on r.id = i.rob_order_record_id"
						+ " LEFT JOIN goods_detail d on d.id = i.goods_detail_id"
						+ " LEFT JOIN goods_type t on t.id = d.goods_type_id"
						+ " WHERE i.stock_id=:truckID and i.rob_order_record_id=:robOrderId");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", robOrderId);
		map.put("truckID", truckID);

		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 获取订单拉货车列表
	 * 
	 * @param robOrderId
	 * @return
	 */
	public List<Map<String, Object>> getTruckList(String robOrderId,
			Account account) {
		StringBuffer sql = new StringBuffer(
				"SELECT  c.id as robOrderConfirmId,"
						+ "c.`status` as status,"
						+ "c.transport_no as transportNo,"
						+ "c.total_weight as totalWeight,"
						+ "c.special_type as specialType,"
						+ "c.special_status as specialStatus,"
						+ "c.lock_status as lockStatus,"
						+ "tu.`name`,"
						+ "tu.phone,"
						+ "type.name as typeName,"
						+ "t.track_no as trackNo"
						+ " from rob_order_confirm c "
						+ " LEFT JOIN sys_track t on t.id = c.turck_id"
						+ " LEFT JOIN mem_account tu on tu.id = c.turck_user_id"
						+ " LEFT JOIN bas_truck_type type on type.id = t.card_type_id"
						+ " WHERE c.rob_order_id=:robOrderId ");

		if ((account.getCompany().getCompanyType().getName().equals("车队") || account
				.getCompany().getCompanyType().getName().equals("个人司机"))) {
			sql.append("and c.turck_user_id=:turckUserId");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", robOrderId);
		map.put("turckUserId", account.getId());
		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 
	 * 功能描述： 获取商户定单 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getCompanyOredrList(
			RobOrderRecord robOrderRecord, Account account, int start, int limit) {

		UserType userType = account.getUserType();

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT record.robOrderNo,record.consigneeAreaName,"
						+ "record.deliveryAreaName," + "record.goodType,"
						+ "record.weight," + "record.unitPrice,"
						+ "record.robbedCompanyName," + "record.finiteTime,"
						+ "record.id," + "record.robOrderConfirmId"
						+ " FROM myorder_query as record where 1=1");
		if (UserType.company.equals(userType)) {// 商户
			CompanyType companyType = account.getCompany().getCompanyType();
			String omcpanyTypeName = companyType.getName();

			if (omcpanyTypeName.equals("货主") || omcpanyTypeName.equals("个人司机")) {
				where.append(" AND record.robbedCompanyId =:robbedCompanyId");
				map.put("robbedCompanyId", account.getCompany().getId());
			}

			if (omcpanyTypeName.equals("车队")) {
				where.append(" AND record.accountCompanyId =:accountCompanyId");
				map.put("accountCompanyId", account.getCompany().getId());
			}

		} else {
			if ("车队".equals(account.getCompany().getCompanyType().getName())
					|| "个人司机".equals(account.getCompany().getCompanyType()
							.getName())) {
				where.append(" AND record.truckUserId =:truckUserId");
				map.put("truckUserId", account.getId());
			}
			if ("货主".equals(account.getCompany().getCompanyType().getName())) {
				where.append(" AND record.robbedAccountId =:robbedAccountId");
				map.put("robbedAccountId", account.getId());
			}

			if ("管理".equals(account.getCompany().getCompanyType().getName())
					|| "系统".equals(account.getCompany().getCompanyType()
							.getName())) {
				where.append(" AND record.receiptUserId =:receiptUserId");
				map.put("robbedAccountId", account.getId());
			}
		}

		if (!StringUtils.isEmpty(robOrderRecord.getConsigneeAreaName())) {
			where.append(" AND record.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal",
					robOrderRecord.getConsigneeAreaName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" AND record.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobOrderNo())) {
			where.append(" AND record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", "%" + robOrderRecord.getRobOrderNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getCompanyName())) {
			where.append(" AND record.companyName like :companyName");
			map.put("companyName", "%" + robOrderRecord.getCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobbedCompanyName())) {
			where.append(" AND record.robbedCompanyName like :robbedCompanyName");
			map.put("robbedCompanyName",
					"%" + robOrderRecord.getRobbedCompanyName() + "%");
		}

		if (robOrderRecord.getGoodsTypeId() != null
				&& robOrderRecord.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsTypeId in (:goodsTypeId)");
			map.put("goodsTypeId", robOrderRecord.getGoodsTypeId());
		}

		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" AND record.status in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}

		where.append(" group by(record.id)");
		// return this.getPageSQLMap(sql.append(where).toString(), map, start,
		// limit);
		return this.getPageSqlListMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 
	 * 功能描述： 根据实体查询订单信息 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord,
			int start, int limit) {
		Account account = UserUtil.getAccount();

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM myorder_query as record where 1=1");
		/*
		 * if(UserType.company.equals(userType)){//商户 CompanyType companyType =
		 * account.getCompany().getCompanyType(); String omcpanyTypeName =
		 * companyType.getName();
		 * 
		 * 
		 * if(omcpanyTypeName.equals("货主")||omcpanyTypeName.equals("个人司机")){
		 * where.append(" AND record.robbedCompanyId =:robbedCompanyId");
		 * map.put("robbedCompanyId",account.getCompany().getId()); }
		 * 
		 * if(omcpanyTypeName.equals("车队")){
		 * where.append(" AND record.accountCompanyId =:accountCompanyId");
		 * map.put("accountCompanyId",account.getCompany().getId()); }
		 * 
		 * 
		 * }
		 */

		if ("车队".equals(account.getCompany().getCompanyType().getName())
				|| "个人司机".equals(account.getCompany().getCompanyType()
						.getName())) {
			where.append(" AND record.truckUserId =:truckUserId");
			map.put("truckUserId", account.getId());
		}
		if ("货主".equals(account.getCompany().getCompanyType().getName())) {
			where.append(" AND record.robbedAccountId =:robbedAccountId");
			map.put("robbedAccountId", account.getId());
		}

		if (!StringUtils.isEmpty(robOrderRecord.getConsigneeAreaName())) {
			where.append(" AND record.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal",
					robOrderRecord.getConsigneeAreaName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" AND record.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobOrderNo())) {
			where.append(" AND record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", "%" + robOrderRecord.getRobOrderNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getTransportNo())) {
			where.append(" AND record.transportNo like :transportNo");
			map.put("transportNo", "%" + robOrderRecord.getTransportNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getCompanyName())) {
			where.append(" AND record.companyName like :companyName");
			map.put("companyName", "%" + robOrderRecord.getCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobbedCompanyName())) {
			where.append(" AND record.robbedCompanyName like :robbedCompanyName");
			map.put("robbedCompanyName",
					"%" + robOrderRecord.getRobbedCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryNo())) {
			where.append(" AND record.deliveryNo =:deliveryNo");
			map.put("deliveryNo", robOrderRecord.getDeliveryNo());
		}

		if (robOrderRecord.getGoodsTypeId() != null
				&& robOrderRecord.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsTypeId in (:goodsTypeId)");
			map.put("goodsTypeId", robOrderRecord.getGoodsTypeId());
		}

		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" AND record.status in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}

		where.append(" group by(record.id)");
		return this.getPageSQLMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 
	 * 功能描述： 根据实体查询报表信息 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getReportFormsPage(
			RobOrderRecord robOrderRecord, String goodsName, String trackNo,
			int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		// StringBuffer sql = new
		// StringBuffer("SELECT * FROM report_forms as record where 1=1");
		// if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
		// where.append(" AND record.deliveryAreaName like :deliveryAreaName");
		// map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName() +
		// "%");
		// }
		// if (robOrderRecord.getStatusIDs() != null &&
		// robOrderRecord.getStatusIDs().length > 0) {
		// where.append(" AND record.status in (:status) ");
		// map.put("status", robOrderRecord.getStatusIDs());
		// }
		// if (!StringUtils.isEmpty(goodsName)) {
		// where.append(" AND record.goodsName like :goodsName ");
		// map.put("goodsName", goodsName + "%");
		// }
		// if (!StringUtils.isEmpty(trackNo)) {
		// where.append(" AND record.trackNo like :trackNo ");
		// map.put("trackNo", trackNo + "%");
		// }
		// where.append(" group by(record.id)");
		StringBuffer sql = new StringBuffer(
				"select b.delivery_area_name deliveryAreaName,t.`name` goodsName,"
						+ "tk.track_no trackNo,c.end_data endDate,d.specifica specification,b.total_weight totalWeight,"
						+ "c.total_weight actualWeight,c.unit_price unitPrice,c.total_cost totalPrice,a.`name` userName"
						+ " from rob_order_record r  left join goods_basic b on b.id = r.goods_baice_id  left join"
						+ " goods_type t on t.id = b.goods_type_id left join rob_order_confirm c on c.rob_order_id ="
						+ " r.id left join sys_track tk on tk.id = c.turck_id left join goods_detail d on d.goods_baice_id ="
						+ " b.id left join mem_account a on a.id = tk.account_id where 1 = 1");
		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" and b.delivery_area_name like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}
		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" and c.`status` in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}
		if (!StringUtils.isEmpty(goodsName)) {
			where.append(" and t.`name` like :goodsName ");
			map.put("goodsName", goodsName + "%");
		}
		if (!StringUtils.isEmpty(trackNo)) {
			where.append(" and tk.track_no like :trackNo ");
			map.put("trackNo", trackNo + "%");
		}
		where.append(" order by c.end_data DESC ");
		return this.getPageSQLMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 获取订单拉货车列表 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:24:08 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getTruckPage(String robOrderId, int start,
			int limit) {
		StringBuffer sql = new StringBuffer(
				"SELECT c.turck_deposit as turckDeposit,c.turck_cost as turckCost,c.transportation_deposit as transportationDeposit,c.transport_no as transportNo,c.id as robConfirmId,c.unit_price as unitPrice, c.total_weight as totalWeight,c.transportation_cost as transportationCost, c.rob_order_id as robOrderId,c.rob_order_no as robOrderNo,c.confirm_data as confirmData,c.`status` as status,t.id as turckID,a.`name` as turckUserName,a.phone,t.track_no as trackNo,type.`name` as cardType FROM rob_order_confirm c  "
						+ " LEFT JOIN sys_track t on c.turck_id=t.id "
						+ " LEFT JOIN mem_account a on a.id = t.account_id"
						+ " LEFT JOIN bas_truck_type type on type.id = t.card_type_id "
						+ " WHERE rob_order_id=:robOrderId");

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		map.put("robOrderId", robOrderId);
		where.append(" order by c.confirm_data desc");
		return this.getPageSqlTransformer(sql.toString() + where, map, start,
				limit, OrderTruck.class);
	}

	/**
	 * 获取司机拉货信息 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:24:08 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getOrderInfoPage(String robOrderId,
			String truckID, int start, int limit) {
		StringBuffer sql = new StringBuffer(
				"SELECT (r.unit_price*i.actual_weight)as totalPrice,d.goods_name as goodName,t.`name` as goodTypeName,r.unit_price as unitPrice,i.actual_weight as weight FROM rob_order_record_info i"
						+ " LEFT JOIN rob_order_record r on r.id = i.rob_order_record_id"
						+ " LEFT JOIN goods_detail d on d.id = i.goods_detail_id"
						+ " LEFT JOIN goods_type t on t.id = d.goods_type_id"
						+ " WHERE i.stock_id=:truckID and i.rob_order_record_id=:robOrderId");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", robOrderId);
		map.put("truckID", truckID);

		return this.getPageSqlTransformer(sql.toString(), map, start, limit,
				OrderInfo.class);
	}

	/**
	 * 根据单号和开车人ID查询 功能描述： 输入参数: @param robOrderId 输入参数: @param truckUserID 输入参数: @return
	 * 异 常： 创 建 人: Administrator 日 期: 2016年7月11日上午11:25:19 修 改 人: 日 期: 返
	 * 回：RobOrderConfirm
	 */
	public RobOrderConfirm findRobOrderConfirm(String robOrderId,
			String truckUserID) {
		StringBuffer hql = new StringBuffer(
				" from RobOrderConfirm record where record.robOrderId = :robOrderId and record.turckUserId = :turckUserId");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", robOrderId);
		map.put("turckUserId", truckUserID);
		return this.getObjectByColumn(hql.toString(), map);
	}

	/**
	 * 根据实体查询 功能描述： 输入参数: @param robOrderId 输入参数: @param truckUserID 输入参数: @return
	 * 异 常： 创 建 人: Administrator 日 期: 2016年7月11日上午11:25:19 修 改 人: 日 期: 返
	 * 回：RobOrderConfirm
	 */
	public RobOrderConfirm findRobOrderConfirm(RobOrderConfirm robOrderConfirm) {
		StringBuffer hql = new StringBuffer(
				" from RobOrderConfirm record where 1=1");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getLgisticsCode())
				&& !robOrderConfirm.getLgisticsCode().equals("-1")) {
			where.append(" AND record.lgisticsCode = :lgisticsCode");
			map.put("lgisticsCode", robOrderConfirm.getLgisticsCode());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getLgisticsNum())) {
			where.append(" AND record.lgisticsNum =:lgisticsNum");
			map.put("lgisticsNum", robOrderConfirm.getLgisticsNum());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getRobOrderId())) {
			where.append(" AND record.robOrderId =:robOrderId");
			map.put("robOrderId", robOrderConfirm.getRobOrderId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getTurckId())) {
			where.append(" AND record.turckId =:turckId");
			map.put("turckId", robOrderConfirm.getTurckId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getTurckUserId())) {
			where.append(" AND record.turckUserId =:turckUserId");
			map.put("turckUserId", robOrderConfirm.getTurckUserId());
		}

		if (robOrderConfirm.getStatus() != null) {
			where.append(" AND record.status =:status");
			map.put("status", robOrderConfirm.getStatus());
		}

		return this.getSingleByHql(hql.append(where).toString(), map);
	}

	/**
	 * 获取全部订单拉货车列表 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:24:08 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getTruckAll(String robOrderId) {
		StringBuffer sql = new StringBuffer(
				"SELECT c.turck_deposit as turckDeposit,c.turck_cost as turckCost,c.transportation_deposit as transportationDeposit,c.transport_no as transportNo,c.id as robConfirmId,c.unit_price as unitPrice, c.total_weight as totalWeight,c.transportation_cost as transportationCost, c.rob_order_id as robOrderId,c.rob_order_no as robOrderNo,c.confirm_data as confirmData,c.`status` as status,t.id as turckID,a.`name` as turckUserName,a.phone,t.track_no as trackNo,type.`name` as cardType FROM rob_order_confirm c  "
						+ " LEFT JOIN sys_track t on c.turck_id=t.id "
						+ " LEFT JOIN mem_account a on a.id = t.account_id"
						+ " LEFT JOIN bas_truck_type type on type.id = t.card_type_id "
						+ " WHERE rob_order_id=:robOrderId");

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		map.put("robOrderId", robOrderId);
		where.append(" order by c.confirm_data desc");
		return this.getSqlTransformer(sql.toString() + where, map,
				OrderTruck.class);
	}

	/**
	 * 获取全部订单拉货车列表 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:24:08 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getTruckAll(RobOrderConfirm robOrderConfirm) {
		StringBuffer sql = new StringBuffer(
				"SELECT IFNULL(-1,  c.special_status) as specialStatus,IFNULL(c.special_type,-1) as specialtype,c.turck_deposit as turckDeposit,c.turck_cost as turckCost,c.transportation_deposit as transportationDeposit,c.transport_no as transportNo,c.id as robConfirmId,c.unit_price as unitPrice, c.total_weight as totalWeight,c.transportation_cost as transportationCost, c.rob_order_id as robOrderId,c.rob_order_no as robOrderNo,c.confirm_data as confirmData,c.`status` as status,t.id as turckID,a.`name` as turckUserName,a.phone,t.track_no as trackNo,type.`name` as cardType FROM rob_order_confirm c  "
						+ " LEFT JOIN sys_track t on c.turck_id=t.id "
						+ " LEFT JOIN mem_account a on a.id = t.account_id"
						+ " LEFT JOIN bas_truck_type type on type.id = t.card_type_id "
						+ " WHERE rob_order_id=:robOrderId");

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		// if
		// (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm.getLgisticsCode()))
		// {
		// where.append(" AND c.lgistics_code =:lgisticscode");
		// map.put("lgisticscode", robOrderConfirm.getLgisticsCode());
		// }

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getLgisticsNum())) {
			where.append(" AND c.lgistics_num =:lgisticsnum");
			map.put("lgisticsnum", robOrderConfirm.getLgisticsNum());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getRobOrderId())) {
			where.append(" AND rob_order_id =:robOrderId");
			map.put("robOrderId", robOrderConfirm.getRobOrderId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getAccountId())) {
			where.append(" AND c.account_id =:accountId");
			map.put("accountId", robOrderConfirm.getAccountId());
		}

		if (robOrderConfirm.getIsSpecial()) {
			where.append(" AND c.special_type is not null and special_status!=3");
		}

		/*
		 * Map<String, Object> map = new HashMap<String,Object>(); StringBuffer
		 * where = new StringBuffer(); map.put("robOrderId",robOrderId);
		 */

		where.append(" order by c.confirm_data desc");
		return this.getPageSQLMap(sql.toString(), map, 0, 99999999);
		// return this.getListBySQLMap(sql, map) ;
		// / return this.getSqlTransformer(sql.toString() + where, map,
		// OrderTruck.class);
	}

	/**
	 * 
	 * 功能描述： 获取回执任务列表 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getReceipttask(RobOrderRecord robOrderRecord,
			int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM myorder_query as record where 1=1");

		if (!StringUtils.isEmpty(robOrderRecord.getReceiptUserId())) {
			where.append(" AND record.receiptUserId = :receiptUserId");
			map.put("receiptUserId", robOrderRecord.getReceiptUserId());
		}

		if (!StringUtils.isEmpty(robOrderRecord.getConsigneeAreaName())) {
			where.append(" AND record.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal",
					robOrderRecord.getConsigneeAreaName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" AND record.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobOrderNo())) {
			where.append(" AND record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", "%" + robOrderRecord.getRobOrderNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getCompanyName())) {
			where.append(" AND record.companyName like :companyName");
			map.put("companyName", "%" + robOrderRecord.getCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobbedCompanyName())) {
			where.append(" AND record.robbedCompanyName like :robbedCompanyName");
			map.put("robbedCompanyName",
					"%" + robOrderRecord.getRobbedCompanyName() + "%");
		}

		if (robOrderRecord.getGoodsTypeId() != null
				&& robOrderRecord.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsTypeId in (:goodsTypeId)");
			map.put("goodsTypeId", robOrderRecord.getGoodsTypeId());
		}

		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" AND record.status in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}

		where.append(" group by(record.id)");
		return this.getPageSQLMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 
	 * 功能描述： 获取急救信息 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getSpecialOrderPage(Account account,
			RobOrderRecord robOrderRecord, int start, int limit) {

		UserType userType = account.getUserType();

		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM myorder_query as record where 1=1");

		if (UserType.company.equals(userType)) {// 商户
			CompanyType companyType = account.getCompany().getCompanyType();
			String omcpanyTypeName = companyType.getName();

			if (omcpanyTypeName.equals("货主")) {
				where.append(" AND record.robbedCompanyId =:robbedCompanyId");
				map.put("robbedCompanyId", account.getCompany().getId());
			}

			if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
				where.append(" AND record.accountCompanyId =:accountCompanyId");
				map.put("accountCompanyId", account.getCompany().getId());
			}

		} else {
			if ("车队".equals(account.getCompany().getCompanyType().getName())
					|| "个人司机".equals(account.getCompany().getCompanyType()
							.getName())) {
				where.append(" AND record.truckUserId =:truckUserId");
				map.put("truckUserId", account.getId());
			}
			if ("货主".equals(account.getCompany().getCompanyType().getName())) {
				where.append(" AND record.robbedAccountId =:robbedAccountId");
				map.put("robbedAccountId", account.getId());
			}
		}

		if (!StringUtils.isEmpty(robOrderRecord.getConsigneeAreaName())) {
			where.append(" AND record.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal",
					robOrderRecord.getConsigneeAreaName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getDeliveryAreaName())) {
			where.append(" AND record.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", robOrderRecord.getDeliveryAreaName()
					+ "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobOrderNo())) {
			where.append(" AND record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", "%" + robOrderRecord.getRobOrderNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getTransportNo())) {
			where.append(" AND record.transportNo like :transportNo");
			map.put("transportNo", "%" + robOrderRecord.getTransportNo() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getCompanyName())) {
			where.append(" AND record.companyName like :companyName");
			map.put("companyName", "%" + robOrderRecord.getCompanyName() + "%");
		}

		if (!StringUtils.isEmpty(robOrderRecord.getRobbedCompanyName())) {
			where.append(" AND record.robbedCompanyName like :robbedCompanyName");
			map.put("robbedCompanyName",
					"%" + robOrderRecord.getRobbedCompanyName() + "%");
		}

		if (robOrderRecord.getGoodsTypeId() != null
				&& robOrderRecord.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsTypeId in (:goodsTypeId)");
			map.put("goodsTypeId", robOrderRecord.getGoodsTypeId());
		}

		if (robOrderRecord.getStatusIDs() != null
				&& robOrderRecord.getStatusIDs().length > 0) {
			where.append(" AND record.status in (:status) ");
			map.put("status", robOrderRecord.getStatusIDs());
		}

		if (robOrderRecord.getSpecialStatusIDs() != null
				&& robOrderRecord.getSpecialStatusIDs().length > 0) {
			where.append(" AND record.specialStatus in (:specialStatus) ");
			map.put("specialStatus", robOrderRecord.getSpecialStatusIDs());
		}

		if (robOrderRecord.getSpecialStatusIDs() != null
				&& robOrderRecord.getSpecialStatusIDs().length > 0) {
			where.append(" AND record.specialStatus in (:specialStatus) ");
			map.put("specialStatus", robOrderRecord.getSpecialStatusIDs());
		}

		if (robOrderRecord.getSpecialType() != null) {
			where.append(" AND record.specialType = :specialType");
			map.put("specialType", robOrderRecord.getSpecialType());
		}

		where.append(" AND record.specialType is not null and record.specialStatus!=3");

		where.append(" group by(record.id)");
		return this.getPageSQLMap(sql.append(where).toString(), map, start,
				limit);
	}

	/**
	 * 
	 * 功能描述： 获取订单状态 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> orderstatus(String robConfirmId) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		StringBuffer sql = new StringBuffer(
				"SELECT l.confirm_status as type,l.rob_order_record_id as soId,l.rob_orderconfirm_id as robConfirmId,r.turck_id as turckID from order_aut_log l");
		sql.append(" LEFT JOIN rob_order_confirm r on r.id =l.rob_orderconfirm_id");

		map.put("robConfirmId", robConfirmId);
		where.append(" WHERE l.rob_orderconfirm_id=:robConfirmId and l.special_status is null ORDER BY l.confirm_status ASC");

		return this.getListBySQLMap(sql.append(where).toString(), map);
	}

	public Map<String, Object> getRabOrderDelivered(
			RobOrderConfirm robOrderConfirm, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select DISTINCT myorder_query.*,robOrderConfirm.lgistics_code FROM rob_order_confirm robOrderConfirm join  "
				+ "rob_order_record robOrder on robOrderConfirm.rob_order_id= robOrder.id join myorder_query on myorder_query.id=robOrder.id "
				+ "where 1=1 ";
		String where = "";
		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getLgisticsCode())
				&& !robOrderConfirm.getLgisticsCode().equals("-1")) {
			where += " AND robOrderConfirm.lgistics_Code = :lgisticsCode";
			map.put("lgisticsCode", robOrderConfirm.getLgisticsCode());
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getLgisticsNum())) {
			where += " AND robOrderConfirm.lgistics_Num =:lgisticsNum";
			map.put("lgisticsNum", robOrderConfirm.getLgisticsNum());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getRobOrderId())) {
			where += " AND robOrderConfirm.robOrder_Id =:robOrderId";
			map.put("robOrderId", robOrderConfirm.getRobOrderId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getTurckId())) {
			where += " AND robOrderConfirm.turck_Id =:turckId";
			map.put("turckId", robOrderConfirm.getTurckId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderConfirm
				.getTurckUserId())) {
			where += " AND robOrderConfirm.turckUser_Id =:turckUserId";
			map.put("turckUserId", robOrderConfirm.getTurckUserId());
		}

		if (robOrderConfirm.getStatus() != null) {
			where += " AND rerobOrderConfirmcord.status =:status";
			map.put("status", robOrderConfirm.getStatus());
		}
		sql += where;
		return this.getPageSQLMap(sql, map, start, limit);
	}

	public Map<String, Object> getRobOrderConfirmByRobOrderId(String robOrderId) {
		// TODO Auto-generated method stub
		String sql = " select status from rob_order_confirm where rob_order_id = :robOrderId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", robOrderId);
		return this.getSqlMap(sql, map);
	}

	public Map<String, Object> getConfirmDeliverDetailsByRobOrderRecordId(
			RobOrderRecord recode) {
		String sql = "SELECT confirm.id,confirm.transport_no,confirm.turck_id,confirm.rob_order_no,"
				+ "confirm.unit_price,confirm.total_weight AS actual_weight,confirm.`status`,confirm.confirm_data,"
				+ "track.track_no,account.`name` AS driver_name,account.phone AS driver_phone,record.weight "
				+ "AS rob_weight,record.deposit_unit_price FROM rob_order_confirm confirm LEFT JOIN sys_track track "
				+ "ON track.id = confirm.turck_id LEFT JOIN mem_account account ON account.id = track.account_id LEFT "
				+ "JOIN rob_order_record record ON record.id = confirm.rob_order_id "
				+ "WHERE confirm.rob_order_id = :robOrderId ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderId", recode.getId());
		return this.getSqlMap(sql, map);
	}

	public Map<String, Object> queryMyRoborderConfrimListWithSolr(
			RobOrderConfirm robOrderRecordConfirm, Account account, int start,
			int limit) {
		try {
			// if (StringUtil.isNotEmpty(robOrderRecordConfirm.getSearchKey()))
			// { // 有关键字查询
			Map<String, Object> ret = null;
			SolrQuery params = new SolrQuery();
			@SuppressWarnings("static-access")
			List<RobOrderConfirm.Status> listStatus = robOrderRecordConfirm
					.getStatusWithQueryStatus(robOrderRecordConfirm
							.getQueryStatus());
			List<String> listStrStatus = new ArrayList<>();
			String statusQuery = "*:* ";
			if (listStatus != null && listStatus.size() > 0) {
				for (RobOrderConfirm.Status status : listStatus) {
					listStrStatus.add(String.valueOf(status.ordinal()));
				}
				statusQuery += " && "
						+ SolrStatementUtils.generateOrQueryByValuesWithField(
								"status", listStrStatus);
			}
			String key = "" + statusQuery;
			if (StringUtil.isNotEmpty(robOrderRecordConfirm.getSearchKey())) {

				// key += " && keyWord:" +
				// String.format("\"%s\"",robOrderRecordConfirm.getSearchKey()
				// );
				key += String.format(" ( keyWord1:\"%s\" || keyWord:\"%s\" )",
						robOrderRecordConfirm.getSearchKey(),
						robOrderRecordConfirm.getSearchKey());

			}
			String roleName = account.getCompany().getCompanyType().getName();
			if ("货主".equals(roleName)) {
				key += " && robbed_account_id:"
						+ String.format("\"%s\"", account.getId());
			} else if ("车队".equals(roleName) || "个人司机".equals(roleName)) {
				key += " && (account_id:"
						+ String.format("\"%s\"", account.getId())
						+ " || turck_user_id:"
						+ String.format("\"%s\"", account.getId()) + ")";
			}

			if (robOrderRecordConfirm.getSpecialType() != null
					&& !robOrderRecordConfirm.getSpecialType().equals(
							SpecialType.none)) {

				if (robOrderRecordConfirm.getSpecialStatus() != null
						&& (robOrderRecordConfirm.getSpecialStatus().equals(
								SpecialStatus.suchprocessing) || robOrderRecordConfirm
								.getSpecialStatus().equals(
										SpecialStatus.processing))) {// 处理中和带介入全部归一类

					key += String.format("  &&  lock_status: %d ",
							LockStatus.locking.ordinal());
					key += String.format(
							" &&  (special_status: %d || special_status:%d)",
							SpecialStatus.suchprocessing.ordinal(),
							SpecialStatus.processing.ordinal());
				} else { // 已经处理的 只要有申请记录并且申请仲裁记录状态为success
					key += String.format(" &&  lock_status :%d ",
							LockStatus.unlock.ordinal());
					key += String.format(" &&  special_status_success :%d ",
							RobOrderConfirm.SpecialStatus.success.ordinal());

				}
			}
			// } else if (robOrderRecordConfirm.getQueryStatus() != null) {
			// @SuppressWarnings("static-access")
			// List<RobOrderConfirm.Status> status = robOrderRecordConfirm
			// .getStatusWithQueryStatus(robOrderRecordConfirm.getQueryStatus());
			// String s = ArrayUtil.joinListArray(status, ",", new
			// ArrayUtil.IJoinCallBack<RobOrderConfirm.Status>() {
			//
			// @Override
			// public String join(Object tatus, int idx) {
			// return ((RobOrderConfirm.Status) tatus).ordinal() + "";
			// }
			//
			// });
			// key += String.format(" &&    status:%s", s);
			// key += " && lock_status :" + LockStatus.unlock.ordinal();
			// }

			ret = super.querySolr(SolrUtils.robOrderConfirm, key, "", "",
					start, limit);
			// ret = new HashMap<String,Object>();
			// ret.put("total", list.getNumFound());
			// ret.put("list", list);
			//
			// params.set("q", key);
			// params.set("start", start);
			// params.set("rows", limit);
			// SolrClient client =
			// solrUtils.getClient(SolrUtils.robOrderConfirm);
			// try {
			// SolrDocumentList docs = client.query(params).getResults();
			// if(docs.size()>0) {
			// ret = new HashMap<String,Object>();
			// List<Map<String, Object>> list = new ArrayList<Map<String,
			// Object>>();
			// for (SolrDocument sd : docs) {
			// // 找出数据，组织回发
			// list.add(MapUtil.toHashMap(sd.getFieldValueMap()));
			// }
			// ret.put("total", docs.getNumFound());
			// ret.put("list", list);
			// }
			// } catch (SolrServerException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			return ret;
			// }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Map<String, Object> getMyRobOrderConfirmList(
			RobOrderConfirm robOrderRecordConfirm, Account account, int start,
			int limit) {
		// return queryMyRoborderConfrimListWithSolr(robOrderRecordConfirm,
		// account, start, limit);
		String sql = "SELECT goods_basic.id AS goods_basic_id, goods_basic.delivery_address, goods_basic.delivery_area_name, goods_basic.delivery_mobile, goods_basic.delivery_name , goods_basic.delivery_coordinate, goods_basic.consignee_address, "
				+ "goods_basic.consignee_area_name, goods_basic.consignee_coordinate, goods_basic.consignee_name , "
				+ "goods_basic.consignee_mobile, goods_basic.map_kilometer, goods_basic.create_time AS goods_basic_create_time, "
				+ "goods_basic.update_time AS goods_basic_update_time, goods_basic.unit_price AS goods_basic_unit_price , "
				+ "goods_basic.total_price AS goods_basic_total_price, goods_basic.total_weight, "
				+ "goods_basic.company_name AS goods_basic_company_name, goods_basic.remark AS goods_basic_remark, 	rob_order_confirm.create_time  as rob_order_create_time,rob_order_record.unit_price AS rob_order_unit_price,	"
				+ " goods_type.`name` as goods_type_name , "
				+ "SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN rob_order_confirm.transportation_cost "
				+ "when rob_order_confirm.status =1 THEN myRound( rob_order_confirm.actual_weight * rob_order_confirm.unit_price) "
				+ "WHEN rob_order_confirm.`status` > 1 AND rob_order_confirm.`status` <> 7 "
				+ "THEN rob_order_confirm.actualtransportation_cost END) AS transportation_cost , "
				+ " SUM( CASE WHEN rob_order_confirm.`status` = 0 OR rob_order_confirm.`status` = 1 THEN rob_order_confirm.transportation_deposit WHEN rob_order_confirm.`status` = 7 THEN 0 ELSE rob_order_confirm.total_cost END )  AS  payment ,  "
				+ "SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN rob_order_confirm.total_weight WHEN rob_order_confirm.`status` > 0 AND rob_order_confirm.`status` <> 7 "
				+ "THEN rob_order_confirm.actual_weight ELSE 0 END) AS actual_weight, "
				+ "sum(rob_order_confirm.additional_cost) additional_cost,"
				+ "sum(rob_order_confirm.total_cost) total_cost,"
				+ "SUM(1) AS count "
				+ "FROM goods_basic JOIN rob_order_record ON goods_basic.id = rob_order_record.goods_baice_id "
				+ " join goods_type on goods_basic.goods_type_id = goods_type.id  "
				+ "JOIN rob_order_confirm ON rob_order_confirm.rob_order_id = rob_order_record.id JOIN mem_account ON mem_account.id = rob_order_record.account_id "
				+ " JOIN sys_company on sys_company.id = mem_account.company_id "
				+ " where 1=1 {where} "
				+ " GROUP BY goods_basic.id "
				+ " ORDER BY   rob_order_confirm.create_time desc ";

		String where = "";
		String roleName = account.getCompany().getCompanyType().getName();

		String accountID = account.getId();
		Map<String, Object> map = new HashMap<>();

		if ("货主".equals(roleName)) {
			where += String
					.format(" and rob_order_record.robbed_account_id = :accountID ");
			map.put("accountID", account.getId());

		} else if ("车队".equals(roleName) || "个人司机".equals(roleName)) {

			where += String
					.format("   and  (rob_order_record.account_id =:accountID  or rob_order_confirm.turck_user_id =:accountID  ) ");

			map.put("accountID", account.getId());
		}
		// 特殊运单仲裁这些
		if (robOrderRecordConfirm.getSpecialType() != null
				&& !robOrderRecordConfirm.getSpecialType().equals(
						SpecialType.none)) {

			if (robOrderRecordConfirm.getSpecialStatus() != null
					&& (robOrderRecordConfirm.getSpecialStatus().equals(
							SpecialStatus.suchprocessing) || robOrderRecordConfirm
							.getSpecialStatus()
							.equals(SpecialStatus.processing))) {

				where += String.format(" and  lock_status = %d ",
						LockStatus.locking.ordinal());
				where += String.format(" and  special_status in (%d,%d)",
						SpecialStatus.suchprocessing.ordinal(),
						SpecialStatus.processing.ordinal());
			} else { // 已经处理的 只要有申请记录并且申请仲裁记录状态为success
				where += String.format(" and  lock_status = %d ",
						LockStatus.unlock.ordinal());
				where += " and EXISTS  ( "
						+ " SELECT 1 "
						+ "  FROM order_special_apply "
						+ "  WHERE rob_order_confirm.id = order_special_apply.rob_orderconfirm_id "
						+ "and order_special_apply.special_status ="
						+ RobOrderConfirm.SpecialStatus.success.ordinal()
						+ " ) ";
				// where +=
				// String.format(" and special_status = %d
				// ",robOrderRecordConfirm.getSpecialStatus().ordinal());
			}

		} else if (robOrderRecordConfirm.getQueryStatus() != null) {
			@SuppressWarnings("static-access")
			List<RobOrderConfirm.Status> status = robOrderRecordConfirm
					.getStatusWithQueryStatus(robOrderRecordConfirm
							.getQueryStatus());
			String s = ArrayUtil.joinListArray(status, ",",
					new ArrayUtil.IJoinCallBack<RobOrderConfirm.Status>() {

						@Override
						public String join(Object tatus, int idx) {
							return ((RobOrderConfirm.Status) tatus).ordinal()
									+ "";
						}

					});
			where += String.format(" and  rob_order_confirm.`status` in (%s)",
					s);
			where += " and lock_status = " + LockStatus.unlock.ordinal();
		}
		if (StringUtil.isNotEmpty(robOrderRecordConfirm.getSearchKey())) { // 全文检索
			// 先从solr 查出ID号
			String key =  String.format("    (keyWord:\"%s\" || keyWord1:\"%s\")",
					robOrderRecordConfirm.getSearchKey(),
					robOrderRecordConfirm.getSearchKey());
		 
			if ("货主".equals(roleName)) {
				key += String.format(" && (robbed_account_id:\"%s\")",
						account.getId());

			} else if ("车队".equals(roleName) || "个人司机".equals(roleName)) {
				key += String.format(" && (account_id:\"%s\" || turck_user_id:\"%s\")",
						account.getId(),account.getId());	
			
			
			}
			 
			
			 
		
			Map<String, Object> solrDataMap = super.querySolr(
					SolrUtils.robOrderConfirm, key, "", "", start, 999999);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> lst = (List<Map<String, Object>>) solrDataMap
					.get("list");
			if (lst!=null && lst.size() > 0) {
				StringBuilder sb = new StringBuilder();
				sb.append(" and  rob_order_confirm.id in (");
				int index = 0;
				for (Map<String, Object> confirmInfo : lst) {
					sb.append("'" + confirmInfo.get("id") + "'");
					if (index != lst.size() - 1) {
						sb.append(",");
					}
					index++;
				}
				sb.append(")   ");
				where  = sb.toString() + where  ;
			} else { // 全文检索没找到说明没有数据
				where = "  and   0<>0  " + where ;
			}
		}
		sql = sql.replace("{where}", where);
		return this.getPageSqlListMap(sql, map, start, limit);
	}

	public List<Map<String, Object>> getMyRobOrderConfirm(Account account,
			RobOrderConfirm confirm) {

		String strSql = "select * from rob_order_confirm_view  where 1 =1 ";

		String roleName = account.getCompany().getCompanyType().getName();
		Map<String, Object> map = new HashMap<String, Object>();
		String where = " and rob_order_confirm_view.goods_basic_id = :goods_basic_id ";
		map.put("goods_basic_id", confirm.getGoods_basic_id());
		if (confirm.getSpecialType() != null
				&& !confirm.getSpecialType().equals(SpecialType.none)) { // 仲裁的不需要状态查询
																			// 只要有仲裁申请
			where += " and EXISTS ( SELECT 1 FROM order_special_apply WHERE rob_order_confirm_view.rob_order_confirm_id = order_special_apply.rob_orderconfirm_id  ) ";
		} else if (confirm.getQueryStatus() != null) {
			@SuppressWarnings("static-access")
			List<RobOrderConfirm.Status> status = confirm
					.getStatusWithQueryStatus(confirm.getQueryStatus());
			String s = ArrayUtil.joinListArray(status, ",",
					new ArrayUtil.IJoinCallBack<RobOrderConfirm.Status>() {

						@Override
						public String join(Object tatus, int idx) {
							return ((RobOrderConfirm.Status) tatus).ordinal()
									+ "";
						}

					});
			where += String
					.format(" and  rob_order_confirm_view.`rob_order_confirm_status` in (%s) ",
							s);
		}
		if (StringUtil.isNotEmpty(confirm.getId())) {
			where += String
					.format(" and rob_order_confirm_view.rob_order_confirm_id = :id ");
			map.put("id", confirm.getId());

		}
		if ("货主".equals(roleName)) {
			where += String
					.format(" and rob_order_confirm_view.robbed_account_id = :accountID ");
			map.put("accountID", account.getId());

		} else if ("车队".equals(roleName) || "个人司机".equals(roleName)) {

			where += String
					.format("   and  (rob_order_confirm_view.rob_order_record_account_id =:accountID  or rob_order_confirm_view.turck_user_id  =:accountID  ) ");

			map.put("accountID", account.getId());
		}

		strSql = strSql + where;
		return this.getListBySQLMap(strSql, map);

	}

	public boolean accountCanViewGoodsBasic(Account account, String goodsBasicID) {
		String strSql = " select 1 from rob_order_confirm_view "
				+ "where rob_order_confirm_view.goods_basic_id = :goodsBasicID  "
				+ "and ( rob_order_confirm_view.turck_user_id = :accountID  "
				+ "or rob_order_confirm_view.rob_order_record_account_id= :accountID ) ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsBasicID", goodsBasicID);
		map.put("accountID", account.getId());
		List<Map<String, Object>> lst = this.getListBySQLMap(strSql, map);
		return lst != null && lst.size() > 0;
	}

	public List<Map<String, Object>> getConfirmDetailWithConfirmID(
			String confimID) {
		String sql = "SELECT rob_order_record_info.weight, "
				+ " goods_detail.goods_name, goods_detail.specifica, "
				+ " goods_detail.length, goods_detail.weight AS goods_detail_weight ,"
				+ " goods_type.`name` AS goods_type_name "
				+ "FROM rob_order_record_info JOIN rob_order_confirm "
				+ "ON rob_order_confirm.rob_order_id = rob_order_record_info.rob_order_record_id "
				+ "AND rob_order_record_info.stock_id = rob_order_confirm.turck_id "
				+ "JOIN goods_detail ON rob_order_record_info.goods_detail_id = goods_detail.id "
				+ "JOIN goods_type ON goods_detail.goods_type_id = goods_type.id where rob_order_confirm.id=:confimID ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("confimID", confimID);
		return super.getListBySQLMap(sql, map);
	}

	public List<RobOrderConfirm> getRobOrderConfirmWithRecordID(String recordID) {
		String hql = " from RobOrderConfirm where robOrderId=:recordID ";
		Map<String, Object> map = new HashMap<>();
		map.put("recordID", recordID);
		return super.getListByHql(hql, map);

	}

	@SuppressWarnings("unchecked")
	public List<RobOrderConfirm> getAutoPaymentConfirm(int maxCount) {

		String strSql = " select * from rob_order_confirm where `status` = 5  and lock_status=0  and :now > confirm_receipted_date and auto_payment_err=0 ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now", new Date());
		SQLQuery query = super.getSession().createSQLQuery(strSql);
		query.setProperties(map);
		query.setMaxResults(maxCount);
		query.addEntity(RobOrderConfirm.class);
		return query.list();
	}

	public void encrimentAutoPaymentErr(String id) {
		String strSql = "update rob_order_confirm set auto_payment_err=auto_payment_err+1 where id = :id  ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		super.getSession().createSQLQuery(strSql).setProperties(map)
				.executeUpdate();
	}

	public Map<String, Object> getSolrRobOrderConfirmByIds(String ids,
			Account account, int start, int limit) {
		String sql = "SELECT goods_basic.id AS goods_basic_id, goods_basic.delivery_address, goods_basic.delivery_area_name, goods_basic.delivery_mobile, goods_basic.delivery_name , goods_basic.delivery_coordinate, goods_basic.consignee_address, "
				+ "goods_basic.consignee_area_name, goods_basic.consignee_coordinate, goods_basic.consignee_name , "
				+ "goods_basic.consignee_mobile, goods_basic.map_kilometer, goods_basic.create_time AS goods_basic_create_time, "
				+ "goods_basic.update_time AS goods_basic_update_time, goods_basic.unit_price AS goods_basic_unit_price , "
				+ "goods_basic.total_price AS goods_basic_total_price, goods_basic.total_weight, "
				+ "goods_basic.company_name AS goods_basic_company_name, goods_basic.remark AS goods_basic_remark, 	rob_order_confirm.create_time  as rob_order_create_time,rob_order_record.unit_price AS rob_order_unit_price,	"
				+ " goods_type.`name` as goods_type_name , "
				+ "SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN rob_order_confirm.transportation_cost "
				+ "when rob_order_confirm.status =1 THEN myRound( rob_order_confirm.actual_weight * rob_order_confirm.unit_price) "
				+ "WHEN rob_order_confirm.`status` > 1 AND rob_order_confirm.`status` <> 7 "
				+ "THEN rob_order_confirm.actualtransportation_cost END) AS transportation_cost , "
				+ " SUM( CASE WHEN rob_order_confirm.`status` = 0 OR rob_order_confirm.`status` = 1 THEN rob_order_confirm.transportation_deposit WHEN rob_order_confirm.`status` = 7 THEN 0 ELSE rob_order_confirm.total_cost END )  AS  payment ,  "
				+ "SUM(CASE WHEN rob_order_confirm.`status` = 0 THEN rob_order_confirm.total_weight WHEN rob_order_confirm.`status` > 0 AND rob_order_confirm.`status` <> 7 "
				+ "THEN rob_order_confirm.actual_weight ELSE 0 END) AS actual_weight, "
				+ "sum(rob_order_confirm.additional_cost) additional_cost,"
				+ "sum(rob_order_confirm.total_cost) total_cost,"
				+ "SUM(1) AS count "
				+ "FROM goods_basic JOIN rob_order_record ON goods_basic.id = rob_order_record.goods_baice_id "
				+ " join goods_type on goods_basic.goods_type_id = goods_type.id  "
				+ "JOIN rob_order_confirm ON rob_order_confirm.rob_order_id = rob_order_record.id JOIN mem_account ON mem_account.id = rob_order_record.account_id "
				+ " JOIN sys_company on sys_company.id = mem_account.company_id "
				+ " where 1=1 {where} "
				+ " GROUP BY goods_basic.id "
				+ " ORDER BY   rob_order_confirm.create_time desc ";

		String where = " and rob_order_confirm.id in (:robOrderConfirmIds) ";
		Map<String, Object> map = new HashMap<>();
		String roleName = account.getCompany().getCompanyType().getName();
		if ("货主".equals(roleName)) {
			where += String
					.format(" and rob_order_record.robbed_account_id = :accountID ");
			map.put("accountID", account.getId());
		} else if ("车队".equals(roleName) || "个人司机".equals(roleName)) {
			where += String
					.format("   and  (rob_order_record.account_id =:accountID  or rob_order_confirm.turck_user_id =:accountID  ) ");
			map.put("accountID", account.getId());
		}
		sql = sql.replace("{where}", where);
		map.put("robOrderConfirmIds", ids);
		return this.getPageSqlListMap(sql, map, start, limit);
	}

}
