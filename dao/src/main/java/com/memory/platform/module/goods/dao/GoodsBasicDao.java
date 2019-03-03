package com.memory.platform.module.goods.dao;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.rest.service.api.PutAwareCommonsMultipartResolver;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memory.platform.core.AppUtil;
import com.memory.platform.core.ArrayUtil;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsBasic.Status;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年6月12日 下午4:11:58 修 改 人： 日 期： 描 述： 货物管理DAO实现类 版 本
 * 号： V1.0
 */
@Repository
public class GoodsBasicDao extends BaseDao<GoodsBasic> {
	@Autowired
	SolrUtils solrUtils;

	/**
	 * 功能描述： 发货管理分页列表查询 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月12日下午4:16:17 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 * 
	 */
	public Map<String, Object> getPage(GoodsBasic goodsBasic, int start,
			int limit) {
		Account account = UserUtil.getUser();
		// StringBuffer hql = new StringBuffer(" from GoodsBasic goodsBasic
		// where goodsBasic.status != :status ");
		StringBuffer sql = new StringBuffer(
				" SELECT b.id,b.delivery_no AS deliveryNo,b.account_id,b.company_id,"
						+ "b.add_user_id,b.create_time,b.update_time,b.update_user_id,b.audit_person_id,b.company_name,"
						+ "b.consignee_address,b.consignee_email,b.consignee_mobile,b.consignee_name,b.remark,"
						+ "CASE b. STATUS WHEN 0 THEN 'save' WHEN 1 THEN 'apply' WHEN 2 THEN 'lock' WHEN 3 "
						+ "THEN 'back' WHEN 4 THEN 'success' WHEN 5 THEN 'scrap' END as status,b.on_line AS onLine,"
						+ "b.stock_type_names,b.total_price AS totalPrice,b.total_weight AS totalWeight,"
						+ "b.unit_price AS unitPrice,b.version,b.audit_cause,b.consignee_coordinate,"
						+ "b.delivery_address,b.delivery_coordinate,b.delivery_email,b.delivery_mobile,"
						+ "b.delivery_name,b.finite_time AS finiteTime,b.is_long_time,b.release_time AS releaseTime,"
						+ "b.consignee_area_id,b.consignee_area_name AS consigneeAreaName,b.delivery_area_id,"
						+ "b.delivery_area_name AS deliveryAreaName,b.goods_type_id,b.auditPerson,b.audit_time,"
						+ "b.hasLock,b.embark_weight AS embarkWeight,b.map_kilometer,d.specifica,"
						+ "t.name as goodsType FROM goods_basic b LEFT JOIN goods_detail d ON b.id = d.goods_baice_id"
						+ " left join goods_type t on t.id = d.goods_type_id WHERE b. STATUS != :status ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", GoodsBasic.Status.scrap);
		if (!account.getCompany().getCompanyType().getName().equals("系统")
				&& !account.getCompany().getCompanyType().getName()
						.equals("管理")) {
			if (account.getUserType().equals(Account.UserType.company)) {
				// where.append(" and goodsBasic.companyId = :companyId");
				where.append(" and b.company_id = :companyId");
				map.put("companyId", account.getCompany().getId());
			} else {
				// where.append(" and goodsBasic.account.id = :accountId");
				where.append(" and b.account_id = :accountId");
				map.put("accountId", account.getId());
			}
		} else {
			// where.append(" AND goodsBasic.status != :status1");
			where.append(" AND  b.status != :status1");
			map.put("status1", GoodsBasic.Status.save);
		}
		if (!StringUtils.isEmpty(goodsBasic.getSearch())) {
			// where.append(" AND goodsBasic.deliveryNo like :deliveryNo");
			where.append(" AND  b.delivery_no like :deliveryNo");
			map.put("deliveryNo", goodsBasic.getSearch() + "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			// where.append(" AND goodsBasic.consigneeAreaName like
			// :consigneeAreaName");
			where.append(" AND b.consignee_area_name like :consigneeAreaName");
			map.put("consigneeAreaName", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			// where.append(" AND goodsBasic.deliveryAreaName like
			// :deliveryAreaName");
			where.append(" AND b.delivery_area_name like :deliveryAreaName");
			map.put("deliveryAreaName", goodsBasic.getDeliveryAreaName() + "%");
		}
		if (goodsBasic.getGoodsTypeId().length > 0) {
			// where.append(" AND goodsBasic.goodsType.id in (:goods_type_id)");
			where.append(" AND b.goods_type_id in (:goods_type_id)");
			map.put("goods_type_id", goodsBasic.getGoodsTypeId());
		}
		if (goodsBasic.getStatus_serch().length > 0) {
			// where.append(" AND goodsBasic.status in (:statusserch)");
			// map.put("statusserch",goodsBasic.getStatus_serch());
			Status[] st = goodsBasic.getStatus_serch();
			String status = " AND b.status in (";
			for (Status s : st) {
				status += s.ordinal() + ",";
			}
			if (!StringUtils.isEmpty(status))
				status = status.substring(0, status.length() - 1) + ")";
			where.append(status);
		}
		if (goodsBasic.isOnLine() != null) {
			// where.append(" AND goodsBasic.onLine = :onLine");
			where.append(" AND b.on_line = :onLine");
			map.put("onLine", goodsBasic.isOnLine());
		}
		if (!StringUtils.isEmpty(goodsBasic.getCompanyName())) {
			// where.append(" AND goodsBasic.companyName = :companyName");
			where.append(" AND  b.company_name = :companyName");
			map.put("companyName", goodsBasic.getCompanyName());
		}
		if (!account.getCompany().getCompanyType().getName().equals("系统")
				&& !account.getCompany().getCompanyType().getName()
						.equals("管理")) {
			// where.append(" order by goodsBasic.create_time desc");
			where.append(" order by b.create_time desc");
		} else {
			// where.append(" order by
			// goodsBasic.companyName,(goodsBasic.totalWeight-goodsBasic.embarkWeight)
			// desc");
			where.append(" order by b.company_name,(b.total_weight-b.embark_weight) desc");
		}
		return this.getPageSQLMap(sql.append(where).toString(), map, start,
				limit);
		// return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	public Map<String, Object> getPageSuccess(GoodsBasic goodsBasic, int start,
			int limit) {
		Account account = UserUtil.getUser();
		StringBuffer hql = new StringBuffer(
				" from GoodsBasic goodsBasic where goodsBasic.status = :status and goodsBasic.account.id = :accountId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", GoodsBasic.Status.success);
		map.put("accountId", account.getId());
		StringBuffer where = new StringBuffer();
		where.append(" order by goodsBasic.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	public Map<String, Object> getGoodsBasicPageSuccess(GoodsBasic goodsBasic,
			int start, int limit) {
		String sql = "SELECT goods.* FROM `goods_basic` AS goods RIGHT JOIN `rob_order_record` AS rob ON goods.`id` = rob.`goods_baice_id` "
				+ "WHERE goods.`status` = 4 and  (rob.`status`= 1 or rob.`status` =0) and goods.account_id = :accountId GROUP BY goods.`id` order by goods.create_time desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", UserUtil.getUser().getId());
		return this.getPageSql(sql, map, start, limit, GoodsBasic.class);
	}

	public Map<String, Object> getGoodsBasicPage(GoodsBasic goodsBasic,
			Account account, int start, int limit) {
		StringBuffer sql = new StringBuffer(
				" SELECT a.* FROM goods_basic AS a  WHERE "
						+ "EXISTS ( SELECT b.goods_basic_id FROM goods_basic_stock_type AS b WHERE a.id = b.goods_basic_id AND EXISTS "
						+ "( SELECT c.card_type_id FROM sys_track AS c WHERE b.stock_type_id = c.card_type_id AND "
						+ "c.company_id = :company_id ) )  AND a.on_line = :on_line  AND a.status = 4");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company_id", account.getCompany().getId());
		map.put("on_line", true);
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isEmpty(goodsBasic.getSearch())) {
			where.append(" AND a.consignee_address like :consignee_address");
			map.put("consignee_address", goodsBasic.getSearch() + "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND a.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND a.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}
		if (goodsBasic.getGoodsTypeId().length > 0) {
			where.append(" AND a.goods_type_id in (:goods_type_id)");
			map.put("goods_type_id", goodsBasic.getGoodsTypeId());
		}
		where.append(" AND (a.total_weight - a.embark_weight ) > 0");

		where.append(" order by a.create_time desc");
		return this.getPageSql(sql.toString() + where, map, start, limit,
				GoodsBasic.class);
	}

	/**
	 * 功能描述： 分页查找货物信息 输入参数: @param goodsBasic 输入参数: @param account 输入参数: @param
	 * start 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月7日下午12:12:44 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getGoodsBasicPageForMap(GoodsBasic goodsBasic,
			Account account, int start, int limit) {
		StringBuffer sql = new StringBuffer(
				"SELECT a.id, a.delivery_area_name, a.consignee_area_name, b.name as goods_type_name, a.total_weight, a.unit_price,"
						+ " a.company_name, a.is_long_time, a.finite_time  ,a.embark_weight, a.total_weight - a.embark_weight surplus_weight,a.remark FROM goods_basic AS a LEFT JOIN goods_type AS b ON a.goods_type_id = b.id ");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		if (account.getCompany().getCompanyType().getName().equals("车队")
				|| account.getCompany().getCompanyType().getName()
						.equals("个人司机")) {
			sql.append("  WHERE EXISTS ( SELECT b.goods_basic_id FROM goods_basic_stock_type AS b WHERE a.id = b.goods_basic_id AND EXISTS "
					+ "( SELECT c.card_type_id FROM sys_track AS c WHERE  "
					+ "c.company_id = :company_id ) )  ");
			map.put("company_id", account.getCompany().getId());
			sql.append(" AND a.on_line = :on_line  AND a.status = 4");
			map.put("on_line", true);
		} else {
			sql.append(" WHERE a.on_line = :on_line  ");
			if (goodsBasic.getIsMe()) {
				where.append(" and a.account_id = :accountID ");
				map.put("accountID", account.getId());
				map.put("on_line", goodsBasic.isOnLine());

			} else {
				where.append(" AND a.status = 4 ");
				map.put("on_line", true);
			}
		}

		where.append(
		// lix 2017-09-06 注释 添加 超过截止时间的货源是不能抢单的
		// " AND (A.finite_time >= to_days(now()) OR A.is_long_time = 1)
		// AND (a.total_weight - a.embark_weight ) > 0 ");
		" AND (A.finite_time >= curdate() OR A.is_long_time = 1) AND (a.total_weight - a.embark_weight ) > 0 ");
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND a.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND a.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}

		where.append(" order by a.create_time desc");
		return this.getPageSqlListMap(sql.toString() + where, map, start - 1,
				limit);
	}

	public Map<String, Object> getOsPage(GoodsBasic goodsBasic, int start,
			int limit) {
		StringBuffer hql = new StringBuffer(
				" from GoodsBasic goodsBasic where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(goodsBasic.getMylock())
				&& goodsBasic.getMylock().equals("mylock")) {
			Account user = UserUtil.getUser();
			where.append(" and goodsBasic.status = :statusLock and goodsBasic.auditPersonId = :auditPersonId");
			map.put("statusLock", Status.lock);
			map.put("auditPersonId", user.getId());
		} else if (StringUtil.isNotEmpty(goodsBasic.getMylock())
				&& goodsBasic.getMylock().equals("lock")) {
			where.append(" and goodsBasic.status = :statusLock");
			map.put("statusLock", Status.lock);
		} else {
			where.append(" and goodsBasic.status = :statusApply or goodsBasic.status = :statusLock");
			map.put("statusApply", Status.apply);
			map.put("statusLock", Status.lock);
		}
		if (StringUtil.isNotEmpty(goodsBasic.getSearch())) {
			where.append(" and (goodsBasic.account.name like :name or "
					+ "goodsBasic.companyName like :companyName or "
					+ "goodsBasic.deliveryNo = :deliveryNo)");
			map.put("name", "%" + goodsBasic.getSearch() + "%");
			map.put("companyName", "%" + goodsBasic.getSearch() + "%");
			map.put("deliveryNo", goodsBasic.getSearch());
		}
		where.append(" order by goodsBasic.status,goodsBasic.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	public Map<String, Object> getLogPage(GoodsBasic goodsBasic, int start,
			int limit) {
		Account account = UserUtil.getUser();
		StringBuffer hql = new StringBuffer(
				" from GoodsBasic goodsBasic where goodsBasic.status  <> :status");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Status.save);
		if (StringUtil.isNotEmpty(goodsBasic.getSearch())) {
			where.append(" and (goodsBasic.account.name like :name or "
					+ "goodsBasic.companyName like :companyName or "
					+ "goodsBasic.deliveryNo = :deliveryNo)");
			map.put("name", "%" + goodsBasic.getSearch() + "%");
			map.put("companyName", "%" + goodsBasic.getSearch() + "%");
			map.put("deliveryNo", goodsBasic.getSearch());
		}
		if (account.getCompany().getCompanyType().getName().equals("货主")) {
			where.append(" and goodsBasic.account = :account");
			map.put("account", account);
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND goodsBasic.consigneeAreaName like :consigneeAreaName");
			map.put("consigneeAreaName", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND goodsBasic.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", goodsBasic.getDeliveryAreaName() + "%");
		}
		if (goodsBasic.getGoodsTypeId().length > 0) {
			where.append(" AND goodsBasic.goodsType.id in (:goods_type_id)");
			map.put("goods_type_id", goodsBasic.getGoodsTypeId());
		}
		if (StringUtil.isNotEmpty(goodsBasic.getCompanyName())) {
			where.append(" AND goodsBasic.companyName like :companyName");
			map.put("companyName", goodsBasic.getCompanyName() + "%");
		}
		where.append(" order by goodsBasic.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	public Map<String, Object> getRecordPage(GoodsBasic goodsBasic, int start,
			int limit) {
		StringBuffer hql = new StringBuffer(
				" from GoodsBasic goodsBasic where goodsBasic.status  <> :status and goodsBasic.status <> :apply");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Status.save);
		map.put("apply", Status.apply);
		if (StringUtil.isNotEmpty(goodsBasic.getSearch())) {
			where.append(" and (goodsBasic.account.name like :name or "
					+ "goodsBasic.companyName like :companyName or "
					+ "goodsBasic.deliveryNo = :deliveryNo or "
					+ "goodsBasic.auditPerson = :auditPerson)");
			map.put("name", "%" + goodsBasic.getSearch() + "%");
			map.put("companyName", "%" + goodsBasic.getSearch() + "%");
			map.put("deliveryNo", goodsBasic.getSearch());
			map.put("auditPerson", goodsBasic.getSearch());
		}
		if (StringUtil.isNotEmpty(goodsBasic.getMylock())) {
			Account user = UserUtil.getUser();
			where.append(" and goodsBasic.auditPersonId = :userId");
			map.put("userId", user.getId());
		}
		where.append(" order by goodsBasic.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	/**
	 * 功能描述： 修改货物上线、下线 输入参数: @param id 输入参数: @param onLine 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年7月8日下午12:29:14 修 改 人: 日 期: 返 回：void
	 */
	public void updateGoodsBasicOnLine(String id, boolean onLine) {
		Account user = UserUtil.getAccount();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update goods_basic as a set a.on_line = :on_line ";
		map.put("on_line", onLine);
		if (!user.getCompany().getCompanyType().getName().equals("系统")
				&& !user.getCompany().getCompanyType().getName().equals("管理")) {
			sql += " where id = :id and a.account_id = :account_id";
			map.put("account_id", user.getId());
		} else {
			sql += " where id = :id ";
		}
		map.put("id", id);
		this.updateSQL(sql, map);
	}

	/**
	 * 功能描述： 查询货物的基本信息及详情信息 输入参数: @param id 货物基本信息ID 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月8日上午10:43:18 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getGoodsBasicById(String id) {
		StringBuffer sql = new StringBuffer(
				" SELECT a.id,a.delivery_name,a.delivery_mobile,a.delivery_area_name,a.delivery_address,"
						+ "a.consignee_name,a.consignee_mobile,a.consignee_area_name,a.consignee_address,b. NAME AS goods_type_name,"
						+ "a.total_weight,a.embark_weight,a.unit_price,a.is_long_time,a.finite_time,(a.total_weight - a.embark_weight) "
						+ "AS surplus_weight,a.total_price,a.company_name,a.map_kilometer,a.remark,a.create_time FROM goods_basic AS a LEFT JOIN goods_type "
						+ "AS b ON a.goods_type_id = b.id WHERE a.id = :id AND a.status = 4 ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.getSqlMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 查询平台货物条数 输入参数: @param accountId 用户ID 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月20日下午12:46:03 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getGoodsBasicCount(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String sql = "SELECT  COUNT(a.id ) AS toolCount,  "// 发货总数
				+ "SUM(a.total_weight) as toolWeight," // 发货总重量
				+ "COUNT( CASE WHEN a.`status` = 1 THEN 1 ELSE NULL END  ) AS `applyCount`  ,"// 待审核发货数量
				+ "SUM(a.total_weight+IF(a.`status`=1,0,-a.total_weight)) as applyWeight," // 待审核发货重量
				+ "COUNT( CASE WHEN a.`status` = 2 THEN 1 ELSE NULL END  ) AS `lockCount` , "// 处理中的总数
				+ "SUM(a.total_weight+IF(a.`status`=2,0,-a.total_weight)) as lockWeight,"// 处理中的重量
				+ "COUNT( CASE WHEN a.`status` = 3 THEN 1 ELSE NULL END  ) AS `backCount`, "// 退回总数
				+ "SUM(a.total_weight+IF(a.`status`=3,0,-a.total_weight)) as backWeight, "// 退回总重量
				+ "COUNT( CASE WHEN a.`status` = 4 THEN 1 ELSE NULL END  ) AS `successCount` , "// 审核通过
				+ "SUM(a.total_weight+IF(a.`status`=4,0,-a.total_weight)) as successWeight, "// 审核通过总重量
				+ "COUNT( CASE WHEN a.on_line = 1 THEN 1 ELSE NULL END  ) AS `trueCount`, "// 上线总数
				+ "SUM(a.total_weight+IF(a.on_line=1,0,-a.total_weight)) as trueWeight, "// 上线总重量
				+ "COUNT( CASE WHEN a.on_line = 0 THEN 1 ELSE NULL END  ) AS `falseCount`, "// 下线总数
				+ "SUM(a.total_weight+IF(a.on_line=0,0,-a.total_weight)) as falseWeight, "
				+ "COUNT( CASE WHEN to_days(a.create_time) = to_days(now()) THEN 1 ELSE NULL END  ) AS toDayCount, "// 今天发货总数
				+ "SUM(a.total_weight+IF(to_days(a.create_time) = to_days(now()),0,-a.total_weight)) as toDayWeight, "// 今天发货总重量
				+ "COUNT( CASE WHEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(a.create_time) THEN 1 ELSE NULL END  ) AS thisMonthCount, "// 本月发货总数
				+ "SUM(a.total_weight+IF(DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(a.create_time),0,-a.total_weight)) as thisMonthWeight, "// 本月发货总重量
				+ "COUNT( CASE WHEN QUARTER(a.create_time)=QUARTER(now()) THEN 1 ELSE NULL END  ) AS quarterCount, "// 本季度发货总数
				+ "SUM(a.total_weight+IF(QUARTER(a.create_time)=QUARTER(now()),0,-a.total_weight)) as quarterWeight, "// 本季度发货总重量
				+ "COUNT( CASE WHEN YEAR(a.create_time)=YEAR(now()) THEN 1 ELSE NULL END  ) AS yearCount, "// 本年度发货总数
				+ "SUM(a.total_weight+IF(YEAR(a.create_time)=YEAR(now()),0,-a.total_weight)) as yearWeight "// 本年度发货总重量
				+ "FROM goods_basic as a WHERE a.`status` <> 0 and a.`status` <> 5 ";
		if (!StringUtils.isEmpty(accountId)) {
			sql += " AND a.account_id = :account_id ";
			map.put("account_id", accountId);
		}
		return this.getSqlMap(sql, map);
	}

	/**
	 * 功能描述： 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月25日下午3:48:49 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getGoodsBasicStatusCount(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String sql = "SELECT  COUNT(a.id ) AS toolCount,  "// 发货总数
				+ "SUM(a.total_weight) as toolWeight," // 发货总重量
				+ "COUNT( CASE WHEN a.`status` = 1 THEN 1 ELSE NULL END  ) AS `applyCount`  ,"// 待审核发货数量
				+ "SUM(a.total_weight+IF(a.`status`=1,0,-a.total_weight)) as applyWeight," // 待审核发货重量
				+ "COUNT( CASE WHEN a.`status` = 2 THEN 1 ELSE NULL END  ) AS `lockCount` , "// 处理中的总数
				+ "SUM(a.total_weight+IF(a.`status`=2,0,-a.total_weight)) as lockWeight,"// 处理中的重量
				+ "COUNT( CASE WHEN a.`status` = 3 THEN 1 ELSE NULL END  ) AS `backCount`, "// 退回总数
				+ "SUM(a.total_weight+IF(a.`status`=3,0,-a.total_weight)) as backWeight, "// 退回总重量
				+ "COUNT( CASE WHEN a.`status` = 4 THEN 1 ELSE NULL END  ) AS `successCount` , "// 审核通过
				+ "SUM(a.total_weight+IF(a.`status`=4,0,-a.total_weight)) as successWeight, "// 审核通过总重量
				+ "COUNT( CASE WHEN a.on_line = 1 THEN 1 ELSE NULL END  ) AS `trueCount`, "// 上线总数
				+ "SUM(a.total_weight+IF(a.on_line=1,0,-a.total_weight)) as trueWeight, "// 上线总重量
				+ "COUNT( CASE WHEN a.on_line = 0 THEN 1 ELSE NULL END  ) AS `falseCount`, "// 下线总数
				+ "SUM(a.total_weight+IF(a.on_line=0,0,-a.total_weight)) as falseWeight "
				+ "FROM goods_basic as a WHERE a.`status` <> 0 and a.`status` <> 5 ";
		if (!StringUtils.isEmpty(accountId)) {
			sql += " AND a.account_id = :account_id ";
			map.put("account_id", accountId);
		}
		return this.getSqlMap(sql, map);
	}

	/**
	 * 功能描述： 查询货物的基本信息及详情信息 输入参数: @param id 货物基本信息ID 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月8日上午10:43:18 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getGoodsDetailList(String goodsBasicId) {
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ "a.id,a.goods_name,a.height,a.length,a.goods_baice_id,a.goods_type_id,a.diameter,a.wing_width,a.weight,a.embark_weight,"
						+ "(a.weight - a.embark_weight) as surplus_weight,b.name AS goods_type_name,a.specifica  "
						+ "FROM goods_detail AS a LEFT JOIN goods_type AS b ON a.goods_type_id = b.id  "
						+ "WHERE a.goods_baice_id = :goodsBasicId ORDER BY a.embark_weight ASC");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsBasicId", goodsBasicId);
		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 统计平台每月的发货单数 输入参数: @param months 输入参数: @param accountId 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月24日下午1:04:22 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getAllGoodsBasicDateCount(List<String> dates, String accountId,
			String dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "'%Y-%m'";
		} else if (dateType.equals("day")) {
			date_format = "'%Y-%m-%d'";
		}
		String sql = "SELECT  ";
		for (String string : dates) {
			sql += "COUNT( CASE WHEN date_format(a.create_time," + date_format
					+ ") = '" + string + "' THEN 1 ELSE NULL END ) AS `"
					+ string + "`,";// 发货总数
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM goods_basic as a WHERE a.`status` <> 0 and a.`status` <> 5 ";
		if (!StringUtils.isEmpty(accountId)) {
			sql += " AND a.account_id = :account_id ";
			map.put("account_id", accountId);
		}
		return this.getListBySQL(sql, map);
	}

	/**
	 * 功能描述： 输入参数: @param months 输入参数: @param accountId 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年8月24日下午1:47:23 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getAllGoodsBasicDateWeight(List<String> dates,
			String accountId, String dateType) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "'%Y-%m'";
		} else if (dateType.equals("day")) {
			date_format = "'%Y-%m-%d'";
		}
		String sql = "SELECT  ";
		for (String string : dates) {
			sql += "SUM(a.total_weight+IF(date_format(a.create_time,"
					+ date_format + ") = '" + string
					+ "',0,-a.total_weight)) AS `" + string + "`,";// 发货总数
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM goods_basic as a WHERE a.`status` <> 0 and a.`status` <> 5 ";
		if (!StringUtils.isEmpty(accountId)) {
			sql += " AND a.account_id = :account_id ";
			map.put("account_id", accountId);
		}
		return this.getListBySQL(sql, map);
	}

	/**
	 * 功能描述： 统计发货数和发货量排名多少位的商户 输入参数: @param ranking 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午3:53:39 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getGoodsRankingStatistics(int ranking,
			String type) {
		if (ranking == 0) {
			ranking = 10;
		}
		String order = "ranking.count";
		if (type.equals("weight")) {
			order = "ranking.weight";
		}
		String sql = "SELECT ranking.* FROM (SELECT "
				+ "a.id, a.`name`,"
				+ "count(CASE WHEN b.`status` <> 0 and b.`status` <> 5 THEN 1 ELSE NULL END) AS count,"
				+ "sum(b.total_weight+IF(b.`status` <> 0 and b.`status` <> 5,0,-b.total_weight)) AS weight "
				+ "FROM sys_company AS a LEFT JOIN goods_basic AS b ON a.id = b.company_id  LEFT JOIN sys_company_type AS c ON c.id = a.company_type_id "
				+ "WHERE a.audit IN (1, 4)  AND c.`name` = '货主' GROUP BY a.id  ) AS ranking ORDER BY "
				+ order + " desc LIMIT 0," + ranking;
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}

	/**
	 * 功能描述： 货主抢单审核货物列表 输入参数: @param goodsBasic 检索条件 根据货物单号检索 输入参数: @param
	 * account 货主对象 输入参数: @param start 启始页 输入参数: @param limit 每页长度 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年8月13日下午12:14:29 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	public Map<String, Object> getMyGoodsOrderPage(GoodsBasic goodsBasic,
			Account account, int start, int limit) {
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ "goods.`id`,goods.`delivery_area_name`,rob.`create_time`,goods.`delivery_no`,goods.`consignee_area_name`,goods.`delivery_address`,goods.`consignee_address`,goods.`delivery_coordinate`,"
						+ "goods.`consignee_coordinate`,goods.`total_price`,goods.`total_weight`,goods.`unit_price`,goods.`map_kilometer`,goods.`finite_time`,"
						+ "goods.`status`,types.`name` AS goods_type_name,goods.is_long_time "
						+ "FROM `goods_basic` AS goods LEFT JOIN `rob_order_record` AS rob ON goods.`id` = rob.`goods_baice_id` "
						+ "LEFT JOIN `goods_type` AS types ON goods.`goods_type_id`= types.`id` "
						+ "WHERE goods.`account_id`= :accountId "
						+ "AND goods.`status`= 4 AND  (rob.`status`= 1 or rob.`status` =0)");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", account.getId());
		StringBuffer where = new StringBuffer();
		if (StringUtil.isNotEmpty(goodsBasic.getDeliveryNo())) {
			where.append(" AND goods.delivery_no = :deliveryNo");
			map.put("deliveryNo", goodsBasic.getDeliveryNo());
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND goods.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND goods.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}
		where.append("GROUP BY goods.`id` ORDER BY goods.`create_time`");
		return this.getPageSqlListMap(sql.append(where).toString(), map,
				start - 1, limit);
	}

	/**
	 * 功能描述： 获取货物基本信息 输入参数: @param goods 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年8月13日下午5:34:07 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getBasicGoods(String goodsId) {
		String sql = "SELECT goods.id,goods. `delivery_no`,goods.`delivery_area_name`,goods.`delivery_mobile`,goods.`consignee_area_name`,goods.`consignee_mobile`,goods.`delivery_address`,"
				+ "goods.`consignee_address`,goods.`total_price`,goods.`total_weight`,goods.`unit_price`,goods.`status`,goods.`finite_time`,"
				+ "goods.`embark_weight`,goods.`delivery_coordinate`,goods.`consignee_coordinate`,goods.`map_kilometer`,"
				+ "types.`name` AS goods_type_name,(goods.`total_weight` - goods.`embark_weight` ) AS  surplus_weight,goods.`remark`, "
				+ " goods.delivery_name,goods.consignee_name,goods.create_time   "
				+ " FROM `goods_basic` AS goods"
				+ " LEFT JOIN `goods_type` AS types ON types.`id`= goods.`goods_type_id`"
				+ " WHERE goods.`id`= :goodsId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		return this.getSqlMap(sql, map);
	}

	// 获取未审核货物列表 aiqiwu 2017-07-24
	public Map<String, Object> getWaitOrdersReview(GoodsBasic goodsBasic,
			Account account, int start, int limit) {
		// TODO Auto-generated method stub

		String ids = "";
		try {
			if (StringUtil.isNotEmpty(goodsBasic.getSearchKey())) { // 有关键字查询
				SolrClient client = solrUtils.getClient(SolrUtils.goodsBasic);
				SolrQuery params = new SolrQuery();
				
				String key = String.format(
						" ( keyWord:\"%s\" || keyWord1:\"%s\") ",
						goodsBasic.getSearchKey(), goodsBasic.getSearchKey())
						+ " && status:4 && account_id:" + account.getId()+" && on_line:1 ";
				
//				params.set("q", key);
//				params.set("start", start - 1);
//				params.set("rows", limit);

				Map<String,Object> docs =super.querySolr(SolrUtils.goodsBasic, key, null, null, 0, 999999999);
				List<Map<String,Object>> lst = (List<Map<String, java.lang.Object>>) docs.get("list");
				if (lst != null && lst.size() > 0) {
					for (Map<String,Object> sd : lst) {
						String goodsBasicId = sd.get("id").toString();
						ids += "'" + goodsBasicId + "',";

					}
				}
				if (StringUtil.isNotEmpty(ids)) {
					ids = ids.substring(0, ids.length() - 1);
				} else {
					ids = "'123123'";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		StringBuffer sql = new StringBuffer(
				" SELECT goods.`id`,goods.`delivery_area_name`,rob.`create_time`,goods.`delivery_no`,goods.`consignee_area_name`,"
						+ "goods.`delivery_address`,goods.`consignee_address`,goods.`delivery_coordinate`,goods.`consignee_coordinate`,"
						+ "goods.`total_price`,goods.`total_weight`,goods.`unit_price`,goods.`map_kilometer`,goods.`finite_time`,"
						+ "goods.`status`,types.`name` AS goods_type_name,goods.is_long_time,rob.`status` AS rob_status,"
						+ "goods.consignee_name,goods.consignee_mobile,goods.delivery_name,goods.delivery_mobile,goods.remark,"
						+ "(SELECT count(rob_order_record.account_id) FROM rob_order_record WHERE "
						+ "goods.id = rob_order_record.goods_baice_id and rob_order_record.`status` in(0,1)) AS rob_count FROM `goods_basic` AS goods "
						+ "LEFT JOIN `rob_order_record` AS rob ON goods.`id` = rob.`goods_baice_id` LEFT JOIN `goods_type` AS "
						+ "types ON goods.`goods_type_id` = types.`id` WHERE goods.`account_id` = :accountId "
						+ "AND goods.`status` = 4 AND rob.`status` IN (1, 0) ");
		if (StringUtil.isNotEmpty(ids)) {
			sql.append(String.format(" and goods.id in (%s)", ids));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", account.getId());
		StringBuffer where = new StringBuffer();
		if (StringUtil.isNotEmpty(goodsBasic.getDeliveryNo())) {
			where.append(" AND goods.delivery_no = :deliveryNo");
			map.put("deliveryNo", goodsBasic.getDeliveryNo());
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND goods.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND goods.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}
		where.append("GROUP BY goods.`id` ORDER BY rob.`create_time` DESC");
		return this.getPageSqlListMap(sql.append(where).toString(), map,
				start - 1, limit);
	}

	// 获取已审核货物列表 aiqiwu 2017-07-24
	public Map<String, Object> getAlreadyOrdersReview(GoodsBasic goodsBasic,
			Account account, int start, int limit) {
		// TODO Auto-generated method stub
		String ids = "";
		try {
			if (StringUtil.isNotEmpty(goodsBasic.getSearchKey())) { // 有关键字查询
				SolrClient client = solrUtils.getClient(SolrUtils.goodsBasic);
				SolrQuery params = new SolrQuery();
				String key = String.format(
						" ( keyWord:\"%s\" || keyWord1:\"%s\") ",
						goodsBasic.getSearchKey(), goodsBasic.getSearchKey())
						+ " && status:4 && account_id:" + account.getId();

//				params.set("q", key);
//				params.set("start", start - 1);
//				params.set("rows", limit);
//
//				SolrDocumentList docs = client.query(params).getResults();
//
//				for (SolrDocument sd : docs) {
//					String goodsBasicId = sd.getFieldValue("id").toString();
//					ids += "'" + goodsBasicId + "',";
//
//				}
				Map<String,Object> docs =super.querySolr(SolrUtils.goodsBasic, key, null, null, 0, 999999999);
				List<Map<String,Object>> lst = (List<Map<String, java.lang.Object>>) docs.get("list");
				if (lst != null && lst.size() > 0) {
					for (Map<String,Object> sd : lst) {
						String goodsBasicId = sd.get("id").toString();
						ids += "'" + goodsBasicId + "',";

					}
				}
				if (StringUtil.isNotEmpty(ids)) {
					ids = ids.substring(0, ids.length() - 1);
				} else {
					ids = "'123123'";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		StringBuffer sql = new StringBuffer(
				" SELECT goods.`id`,goods.`delivery_area_name`,rob.`create_time`,goods.`delivery_no`,"
						+ "goods.`consignee_area_name`,goods.`delivery_address`,goods.`consignee_address`,"
						+ "goods.`delivery_coordinate`,goods.`consignee_coordinate`,goods.`total_price`,goods.`total_weight`,"
						+ "goods.`unit_price`,goods.`map_kilometer`,goods.`finite_time`,goods.`status`,types.`name` AS goods_type_name,"
						+ "goods.is_long_time,rob.`status` AS rob_status,goods.consignee_name,goods.consignee_mobile,"
						+ "goods.delivery_name,goods.delivery_mobile,goods.remark FROM `goods_basic` AS goods "
						+ "LEFT JOIN `rob_order_record` AS rob ON goods.`id` = rob.`goods_baice_id` LEFT JOIN `goods_type` AS types "
						+ "ON goods.`goods_type_id` = types.`id` WHERE goods.`account_id` = :accountId AND goods.`status` = 4 "
						+ "AND rob.`status` in (3,2) ");
		if (StringUtil.isNotEmpty(ids)) {
			sql.append(String.format(" and goods.id in (%s)", ids));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", account.getId());
		StringBuffer where = new StringBuffer();
		if (StringUtil.isNotEmpty(goodsBasic.getDeliveryNo())) {
			where.append(" AND goods.delivery_no = :deliveryNo");
			map.put("deliveryNo", goodsBasic.getDeliveryNo());
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND goods.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND goods.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}
		where.append("GROUP BY goods.`id` ORDER BY rob.`create_time` DESC");
		return this.getPageSqlListMap(sql.append(where).toString(), map,
				start - 1, limit);
	}

	// 获取需要确认发货的订单列表 aiqiwu 2017-07-24
	public Map<String, Object> getConfirmDeliverGoodsOrders(
			GoodsBasic goodsBasic, Account account, int start, int limit) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				" SELECT goods.`id`,rob.create_time,goods.`delivery_no`,goods.`delivery_area_name`,goods.`consignee_area_name`,"
						+ "goods.`delivery_address`,goods.`consignee_address`, goods.`delivery_coordinate`,"
						+ "goods.`consignee_coordinate`,goods.`total_price`,goods.`total_weight`,goods.`unit_price`,"
						+ "goods.`map_kilometer`,goods.`finite_time`,goods.`status`,types.`name` AS goods_type_name,goods.is_long_time,"
						+ "rob.`status` AS rob_status,goods.consignee_name,goods.consignee_mobile,goods.delivery_name,"
						+ "goods.delivery_mobile,goods.remark,rob.id AS rob_id FROM `goods_basic` AS goods LEFT JOIN `rob_order_record` AS rob "
						+ "ON goods.`id` = rob.`goods_baice_id` LEFT JOIN `goods_type` AS types ON goods.`goods_type_id` = types.`id` LEFT JOIN "
						+ "rob_order_confirm confirm ON confirm.rob_order_id = rob.id"
						+ " WHERE goods.`account_id` = :accountId  AND goods.`status` = 4 AND rob.`status` = 6 AND confirm.`status` = 1 ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", account.getId());
		StringBuffer where = new StringBuffer();
		if (StringUtil.isNotEmpty(goodsBasic.getDeliveryNo())) {
			where.append(" AND goods.delivery_no = :deliveryNo");
			map.put("deliveryNo", goodsBasic.getDeliveryNo());
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND goods.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND goods.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}
		where.append("GROUP BY goods.`id` ORDER BY goods.`create_time`");
		return this.getPageSqlListMap(sql.append(where).toString(), map,
				start - 1, limit);
	}

	public Map<String, Object> getMyGoodsBasicPage(GoodsBasic goodsBasic,
			Account account, int offset, int size) {

		StringBuffer sql = new StringBuffer(
				"SELECT a.id, a.delivery_area_name, a.consignee_area_name, b.name as goods_type_name, a.total_weight, a.unit_price,"
						+ " a.company_name, a.is_long_time, a.finite_time  ,a.embark_weight, a.total_weight - a.embark_weight surplus_weight,a.remark FROM goods_basic AS a LEFT JOIN goods_type AS b ON a.goods_type_id = b.id ");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		sql.append(" WHERE     1 =1  and a.status = 4 ");

		where.append(" and a.account_id = :accountID ");
		map.put("accountID", account.getId());

		if (goodsBasic.getOnLine()) {
			where.append(
			// lix 2017-09-06 注释 添加 超过截止时间的货源是不能抢单的
			" AND (A.finite_time >= curdate() OR A.is_long_time = 1) AND (a.total_weight - a.embark_weight ) > 0 ");
			where.append(" and on_line = :online ");
			map.put("online", goodsBasic.isOnLine());

		} else {
			where.append(" and  ( on_line=0 or (  (A.finite_time < curdate() OR A.is_long_time = 1) AND (a.total_weight - a.embark_weight ) = 0 )  ) ");
		}
		if (!StringUtils.isEmpty(goodsBasic.getConsigneeAreaName())) {
			where.append(" AND a.consignee_area_name like :consignee_area_name");
			map.put("consignee_area_name", goodsBasic.getConsigneeAreaName()
					+ "%");
		}
		if (!StringUtils.isEmpty(goodsBasic.getDeliveryAreaName())) {
			where.append(" AND a.delivery_area_name like :delivery_area_name");
			map.put("delivery_area_name", goodsBasic.getDeliveryAreaName()
					+ "%");
		}
		if (StringUtil.isNotEmpty(goodsBasic.getSearchKey())) {

			String key = String.format(
					"    (keyWord:\"%s\" || keyWord1:\"%s\")",
					goodsBasic.getSearchKey(), goodsBasic.getSearchKey())
					+ String.format(" && account_id:\"%s\"", account.getId());
			Map<String, Object> dataMap = super.querySolr(SolrUtils.goodsBasic,
					key, "goods_basic_create_time desc", "", 0,999999999);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> lst = (List<Map<String, Object>>) dataMap
					.get("list");
			if (lst != null && lst.size() > 0) {
				String ids = ArrayUtil.joinListArray(lst, ",",
						new ArrayUtil.IJoinCallBack<Map<String, Object>>() {

							@SuppressWarnings("hiding")
							@Override
							public <Object> String join(Object obj, int idx) {
								@SuppressWarnings("unchecked")
								Map<String, Object> map = (Map<String, Object>) obj;
								return String.format("\"%s\"", map.get("id"));

							}
						});
				where.append(" and  a.id in (" + ids + ")");

			} else {
				where.append(" and 0<>0");
			}

		}
		where.append(" order by a.create_time desc");
		return this
				.getPageSqlListMap(sql.toString() + where, map, offset, size);
	}

	// lix 2017-09-12 添加 根据货源ID修改货源上线状态为下线
	public void updateAllGoodsOnLine(String goodsBasicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "update goods_basic set goods_basic.on_line=:on_line  where goods_basic.id=:goodsBasicId";
		map.put("on_line", 0);
		map.put("goodsBasicId", goodsBasicId);
		this.updateSQL(sql, map);
	}

	// lix 2017-09-12 添加 获取所有截止时间小于当前时间或者剩余吨位数为0且货源上线状态为上线的记录
	public List<GoodsBasic> getGoodsBasicsListByOnLine() {

		String strSql = " select * from goods_basic where goods_basic.on_line=1 and (goods_basic.finite_time < curdate() or"
				+ " (goods_basic.total_weight - goods_basic.embark_weight )= 0) ";
		Map<String, Object> map = new HashMap<String, Object>();
		return super.getListBySql(strSql, map, GoodsBasic.class);
	}
}
