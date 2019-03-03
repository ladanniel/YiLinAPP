package com.memory.platform.module.order.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memory.platform.core.MapUtil;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecord.Status;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.ArrayUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.dao.BaseDao;
import com.memory.platform.solr.SolrStatementUtils;

/**
 * 创 建 人： longqibo 日 期： 2016年6月16日 下午8:59:50 修 改 人： 日 期： 描 述： 订单记录分页 版 本 号： V1.0
 */
@Repository
public class RobOrderRecordDao extends BaseDao<RobOrderRecord> {
	@Autowired
	SolrUtils solrUtils;

	/**
	 * 功能描述： 分页我的抢单记录 输入参数: @param log 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年6月16日下午8:56:51 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	public Map<String, Object> getPageByGoodsId(String goodsId, int start,
			int limit) {
		StringBuffer hql = new StringBuffer(
				" from RobOrderRecord record where (record.status = :status or record.status = :statusDeal)");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		where.append(" and record.goodsBasic.id = :goodsId");
		map.put("status", RobOrderRecord.Status.apply);
		map.put("statusDeal", RobOrderRecord.Status.dealing);
		map.put("goodsId", goodsId);
		where.append(" order by record.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	/**
	 * 功能描述： 分页订单记录 输入参数: @param log 输入参数: @param start 输入参数: @param limit 输入参数: @return
	 * 异 常： 创 建 人: longqibo 日 期: 2016年6月16日下午8:56:51 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	public Map<String, Object> getMyPage(RobOrderRecord record, int start,
			int limit) {
		Account account = UserUtil.getUser();
		StringBuffer hql = new StringBuffer(
				" from RobOrderRecord record where record.account.id = :accountId ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", account.getId());
		if (!StringUtils.isEmpty(record.getSearch())) {
			where.append(" AND record.goodsBasic.consigneeAreaName like :consigneeAreaName");
			map.put("consigneeAreaName", "%" + record.getSearch() + "%");
			where.append(" or record.robOrderNo like :robOrderNo");
			map.put("robOrderNo", record.getSearch() + "%");
		}
		if (!StringUtils.isEmpty(record.getConsigneeAreaName())) {
			where.append(" AND record.goodsBasic.consigneeAreaName like :consigneeAreaNameVal");
			map.put("consigneeAreaNameVal", record.getConsigneeAreaName() + "%");
		}
		if (!StringUtils.isEmpty(record.getDeliveryAreaName())) {
			where.append(" AND record.goodsBasic.deliveryAreaName like :deliveryAreaName");
			map.put("deliveryAreaName", record.getDeliveryAreaName() + "%");
		}
		if (record.getGoodsTypeId().length > 0) {
			where.append(" AND record.goodsBasic.goodsType.id in (:goodsTypeId)");
			map.put("goodsTypeId", record.getGoodsTypeId());
		}
		if (record.getStatus_serch() != null
				&& record.getStatus_serch().length > 0) {
			where.append(" AND record.status in (:statuss) ");
			map.put("statuss", record.getStatus_serch());
		}
		where.append(" order by record.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	/**
	 * 功能描述： 分页查询我的抢单信息 输入参数: @param record
	 * 抢单查询条件（consigneeAreaName（收获地区名称），deliveryAreaName（发货地区名称），status（状态））
	 * 输入参数: @param account 当前登录用户 输入参数: @param start （起始页） 输入参数: @param limit
	 * （数据条数） 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月8日下午5:03:00 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getMyPage(RobOrderRecord record,
			Account account, int start, int limit) {
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ "a.id,a.rob_order_no,b.delivery_area_name,b.delivery_coordinate,b.consignee_area_name,b.consignee_coordinate, "
						+ "b.is_long_time,b.finite_time,b.company_name,c.name AS goods_type_name,a.weight,a.unit_price,a.total_price,a.status "
						+ "FROM rob_order_record AS a LEFT JOIN goods_basic AS b ON a.goods_baice_id = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id "
						+ " WHERE  a.account_id = :account_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account_id", account.getId());
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isEmpty(record.getConsigneeAreaName())) {
			where.append(" AND b.consignee_area_name like :consigneeAreaName");
			map.put("consigneeAreaName", record.getConsigneeAreaName() + "%");
		}
		if (!StringUtils.isEmpty(record.getDeliveryAreaName())) {
			where.append(" AND b.delivery_area_name LIKE  :deliveryAreaName");
			map.put("deliveryAreaName", record.getDeliveryAreaName() + "%");
		}
		if (!StringUtils.isEmpty(record.getRobOrderNo())) {
			where.append(" AND a.rob_order_no =  :rob_order_no");
			map.put("rob_order_no", record.getRobOrderNo());
		}
		if (!StringUtils.isEmpty(record.getCompanyName())) {
			where.append(" AND b.company_name LIKE  :company_name");
			map.put("company_name", "%" + record.getCompanyName() + "%");
		}
		if (record.getStatus() != null) {
			where.append(" AND a.status = :statuss ");
			map.put("statuss", record.getStatus().ordinal());
		}
		where.append(" ORDER BY a.update_time DESC");
		return this.getPageSqlListMap(sql.toString() + where, map, start - 1,
				limit);
	}

	/**
	 * 功能描述： 查询商户下，可以分配车辆 输入参数: @param companyId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月14日下午2:54:11 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getCompanyTrucks(String companyId) {
		String sql = "SELECT a.id , a.track_no,a.capacity,a.track_long,a.horsepower,b.name,b.phone "
				+ "FROM sys_track AS a LEFT JOIN mem_account AS b ON a.account_id = b.id "
				+ "WHERE a.company_id = :companyId   AND a.account_id IS NOT NULL ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		return this.getListBySQLMap(sql, map);
	}

	public int getCancelRobOrderByDay() {
		String sql = "SELECT count(a.id) FROM rob_order_record as a WHERE a.cancel_user_id = :cancel_user_id and TO_DAYS(a.cancel_date) = TO_DAYS(NOW())";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancel_user_id", UserUtil.getUser().getId());
		return this.getCountSql(sql, map);
	}

	public int getCancelRobOrderByDay(String accountId) {
		String sql = "SELECT count(a.id) FROM rob_order_record as a WHERE a.cancel_user_id = :cancel_user_id and TO_DAYS(a.cancel_date) = TO_DAYS(NOW())";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cancel_user_id", accountId);
		return this.getCountSql(sql, map);
	}

	/**
	 * 功能描述： 获取申请处理和处理中的抢单列表 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年6月28日上午10:51:29 修 改 人: 日 期: 返 回：List<RobOrderRecord>
	 */
	public List<RobOrderRecord> getRobOrderRecordListByStatus() {
		StringBuffer hql = new StringBuffer(
				" from RobOrderRecord record where record.status = :apply or record.status = :dealing");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apply", RobOrderRecord.Status.apply);
		map.put("dealing", RobOrderRecord.Status.dealing);
		return this.getListByHql(hql.toString(), map);
	}

	/**
	 * 功能描述： 分页订单记录日志 输入参数: @param record 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年7月1日下午3:13:21 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	public Map<String, Object> getLogPage(RobOrderRecord record, int start,
			int limit) {
		Account account = UserUtil.getUser();
		StringBuffer hql = new StringBuffer(
				" from RobOrderRecord record where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(record.getCompanyName())) {
			where.append(" and record.companyName = :companyName");
			map.put("companyName", record.getCompanyName());
		}
		if (account.getCompany().getCompanyType().getName().equals("货主")) {
			where.append(" and record.goodsBasic.account = :account");
			map.put("account", account);
		} else if (account.getCompany().getCompanyType().getName().equals("车队")
				|| (account.getCompany().getCompanyType().getName()
						.equals("个人司机"))) {
			where.append(" and record.account = :account");
			map.put("account", account);
		}
		if (StringUtil.isNotEmpty(record.getRobOrderNo())) {
			where.append(" and record.robOrderNo = :robOrderNo");
			map.put("robOrderNo", record.getRobOrderNo());
		}
		if (StringUtil.isNotEmpty(record.getGoodsTypes())) {
			where.append(" and record.goodsBasic.deliveryNo = :goodsNo");
			map.put("goodsNo", record.getGoodsTypes());
		}
		where.append(" order by record.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	public List<Map<String, Object>> getAccountRobOrderCount(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 0:提交申请 1:处理中 2:退回 3:成功 4:作废 5:撤回 6:结束 **/
		String sql = "SELECT  COUNT(a.id ) AS toolCount,  " // 抢单总数
				+ "COUNT( CASE WHEN a.`status` = 0 THEN 1 ELSE NULL END  ) AS `applyCount` ," // 等待货主确认总数
				+ "COUNT( CASE WHEN a.`status` = 1 THEN 1 ELSE NULL END  ) AS `dealingCount` , "// 处理中的总数
				+ "COUNT( CASE WHEN a.`status` = 2 THEN 1 ELSE NULL END  ) AS `backCount`, "// 退回总数
				+ "COUNT( CASE WHEN a.`status` = 3 THEN 1 ELSE NULL END  ) AS `successCount` , "// 货主确认成功总数
				+ "COUNT( CASE WHEN a.`status` = 4 THEN 1 ELSE NULL END  ) AS `scrapCount` , "// 作废总数
				+ "COUNT( CASE WHEN a.`status` = 5 THEN 1 ELSE NULL END  ) AS `withdrawCount` , "// 撤回总数
				+ "COUNT( CASE WHEN a.`status` = 6 THEN 1 ELSE NULL END  ) AS `endCount` "// 抢单成功总数
				+ "FROM rob_order_record as a WHERE a.account_id = :account_id";
		map.put("account_id", accountId);
		return this.getListBySQLMap(sql, map);
	}

	/**
	 * 订单各种状态数量统计
	 * 
	 * @param account
	 * @return
	 */

	public Map<String, Object> getRobOrderCount(Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		StringBuffer where = new StringBuffer();
		StringBuffer sql = new StringBuffer(
				"SELECT COUNT( CASE WHEN r.`status` = 0 THEN 1 ELSE NULL END  ) AS `applyCount`," // 申请订单总数
						+ "COUNT( CASE WHEN r.`status` != 6 and  r.`status` != 7 THEN 1 ELSE NULL END  ) AS `toolCount` ,"
						+ "SUM(r.weight+IF(r.`status`=0,0,-r.weight)) as applyWeight," // 申请订单总重量
						+ "COUNT( CASE WHEN r.`status` = 1 THEN 1 ELSE NULL END  ) AS `dealingCount`,"// 处理中的总数
						+ "SUM(r.weight+IF(r.`status`=1,0,-r.weight)) as dealingWeight,"// 处理中的总重量
						+ "COUNT( CASE WHEN r.`status` = 2 THEN 1 ELSE NULL END  ) AS `backCount`,"// 退回总数
						+ "SUM(r.weight+IF(r.`status`=2,0,-r.weight)) as backWeight,"// 退回总重量
						+ "COUNT( CASE WHEN r.`status` = 3 THEN 1 ELSE NULL END  ) AS `successCount`,"// 货主确认成功总数
						+ "SUM(r.weight+IF(r.`status`=3,0,-r.weight)) as successWeight,"// 货主确认总重量
						+ "COUNT( CASE WHEN r.`status` = 4 THEN 1 ELSE NULL END  ) AS `scrapCount`,"// 作废总数
						+ "SUM(r.weight+IF(r.`status`=4,0,-r.weight)) as scrapWeight,"// 作废总重量
						+ "COUNT( CASE WHEN r.`status` = 5 THEN 1 ELSE NULL END  ) AS `withdrawCount`,"// 撤回总数
						+ "SUM(r.weight+IF(r.`status`=5,0,-r.weight)) as withdrawWeight, "// 撤回总重量
						+ "COUNT( CASE WHEN to_days(r.create_time) = to_days(now()) and r.`status`=6  THEN 1 ELSE NULL END  ) AS toDayCount,"// 今天成功抢单数量
						+ "SUM(r.weight+IF(to_days(r.create_time) = to_days(now()) and r.`status`=6,0,-r.weight)) as toDayWeight,"// 今天成功抢单重量
						+ "COUNT( CASE WHEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(r.create_time)  and r.`status`=6 THEN 1 ELSE NULL END  ) AS thisMonthCount,"// 本月成功抢单数量
						+ "SUM(r.weight+IF(DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(r.create_time) and r.`status`=6,0,-r.weight)) as thisMonthWeight,"// 本月成功抢单重量
						+ "COUNT( CASE WHEN QUARTER(r.create_time)=QUARTER(now())  and r.`status`=6 THEN 1 ELSE NULL END  ) AS quarterCount,"// 本季度成功抢单数量
						+ "SUM(r.weight+IF(QUARTER(r.create_time)=QUARTER(now())  and r.`status`=6,0,-r.weight)) as quarterWeight,"// 本季度成功抢单重量
						+ "COUNT( CASE WHEN YEAR(r.create_time)=YEAR(now()) and r.`status`=6 THEN 1 ELSE NULL END  ) AS yearCount,"// 本年度成功抢单数量
						+ "SUM(r.weight+IF(YEAR(r.create_time)=YEAR(now()) and r.`status`=6,0,-r.weight)) as yearWeight,"// 本季度成功抢单重量
						+ "COUNT( CASE WHEN r.`status` = 6 or r.`status` = 7 THEN 1 ELSE NULL END  ) AS allCount,"
						+ "SUM(r.weight+IF(r.`status`=6 or r.`status` = 7,0,-r.weight)) as allWeight"
						+ " from rob_order_record r where 1=1");

		if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
			where.append(" AND r.companyName =:companyName");
			map.put("companyName", account.getCompany().getName());
		}

		if (omcpanyTypeName.equals("货主")) {
			where.append(" AND r.robbed_company_id =:robbedcompanyId");
			map.put("robbedcompanyId", account.getCompany().getId());
		}
		return this.getSqlMap(sql.append(where).toString(), map);
	}

	/**
	 * 功能描述： 通过月分查订单数 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getAllRobOrderMonthCount(List<String> months, Account account,
			String dateType, RobOrderRecord robOrderRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "%Y-%m";
		} else if (dateType.equals("day")) {
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "COUNT( CASE WHEN date_format(a.create_time,'" + date_format
					+ "') = '" + string + "' THEN 1 ELSE NULL END ) AS `"
					+ string + "`,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM rob_order_record as a WHERE (a.`status` = 6 or a.`status` = 7 )";

		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();

		if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
			sql += " AND a.companyName =:companyName";
			map.put("companyName", account.getCompany().getName());
		}

		if (omcpanyTypeName.equals("货主")) {
			sql += " AND a.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId", account.getCompany().getId());
		}
		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getTurckUserId())) {
			sql += " AND a.account_id =:turckUserId";
			map.put("turckUserId", robOrderRecord.getTurckUserId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getUserID())) {
			sql += " AND a.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId", robOrderRecord.getUserID());
		}

		return this.getListBySQL(sql, map);
	}

	public Map<String, Object> getRobOrderRecordById(String id, Account account) {
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ "a.id,a.rob_order_no,a.status,b.consignee_area_name,b.map_kilometer,a.weight,b.unit_price AS goods_unit_price,a.deposit_level_money, "
						+ "(a.weight*b.unit_price) AS goods_price,a.unit_price,a.total_price,((a.weight*b.unit_price) - a.total_price) as price_difference, "
						+ "b.delivery_name,b.delivery_mobile,b.company_name,b.delivery_address,b.delivery_coordinate,b.consignee_name,b.consignee_mobile,b.consignee_address,b.consignee_coordinate, "
						+ "(a.deposit_unit_price*a.weight) AS goods_deposit_price,a.remark,a.version"
						+ " FROM rob_order_record AS a LEFT JOIN goods_basic as b ON a.goods_baice_id = b.id"
						+ " WHERE a.account_id = :account_id AND a.id = :id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("account_id", account.getId());
		return this.getSqlMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 通过月分查完结订单数 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getOrderCompletionMonthCount(List<String> months,
			Account account, String dateType, RobOrderRecord robOrderRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "%Y-%m";
		} else if (dateType.equals("day")) {
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "COUNT( CASE WHEN date_format(a.create_time,'" + date_format
					+ "') = '" + string + "' THEN 1 ELSE NULL END ) AS `"
					+ string + "`,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM rob_order_record as a WHERE a.`status` = 7";

		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();

		if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
			sql += " AND a.companyName =:companyName";
			map.put("companyName", account.getCompany().getName());
		}

		if (omcpanyTypeName.equals("货主")) {
			sql += " AND a.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId", account.getCompany().getId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getTurckUserId())) {
			sql += " AND a.account_id =:turckUserId";
			map.put("turckUserId", robOrderRecord.getTurckUserId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getUserID())) {
			sql += " AND a.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId", robOrderRecord.getUserID());
		}

		return this.getListBySQL(sql, map);

	}

	/**
	 * 功能描述： 根据货物ID获取抢单信息 输入参数: @param order 检索条件 输入参数: @param start 启始页 输入参数: @param
	 * limit 每页显示长度 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日下午3:32:09
	 * 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getOrderRecordByGoodsId(RobOrderRecord order,
			int start, int limit) {
		StringBuffer sql = new StringBuffer(
				"SELECT orders.`id`,orders.`rob_order_no`,orders.`create_time`,orders.`weight` as rob_weight,"
						+ "orders.`unit_price` AS driver_quote,orders.`companyName` AS company_name,"
						+ "orders.`goods_types` AS goods_type_name,orders.`total_price`,orders.`deposit_unit_price`,"
						+ "account.`name` AS driver_name,account.phone AS driver_phone,goods.unit_price AS consignor_quote,"
						+ "orders.remark FROM `rob_order_record` AS orders LEFT JOIN mem_account account ON "
						+ "account.id = orders.account_id LEFT JOIN goods_basic goods ON goods.id = orders.goods_baice_id WHERE"
						+ " orders.`goods_baice_id` = :goodsId  AND (orders.`status` = 1 OR orders.`status` = 0)");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", order.getGoodsBasic().getId());
		StringBuffer where = new StringBuffer();
		if (null != order.getStatus()) {
			if (order.getStatus().equals(RobOrderRecord.Status.apply)) {
				where.append(" AND orders.`status` = 0");
			} else if (order.getStatus().equals(RobOrderRecord.Status.dealing)) {
				where.append(" AND orders.`status` = 1");
			} else if (order.getStatus().equals(RobOrderRecord.Status.back)) {
				where.append(" AND orders.`status` = 2");
			} else if (order.getStatus().equals(RobOrderRecord.Status.end)) {
				where.append(" AND orders.`status` = 6");
			} else if (order.getStatus().equals(RobOrderRecord.Status.scrap)) {
				where.append(" AND orders.`status` = 4");
			} else if (order.getStatus().equals(RobOrderRecord.Status.withdraw)) {
				where.append(" AND orders.`status` = 5");
			} else if (order.getStatus().equals(RobOrderRecord.Status.success)) {
				where.append(" AND orders.`status` = 3");
			}
		}
		where.append(" ORDER BY orders.`create_time` DESC,orders.`status` ");
		return this.getPageSqlListMap(sql.toString(), map, start - 1, limit);
	}

	/**
	 * 功能描述： 通过月分查完结订单重量 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getOrderCompletionMonthWeight(List<String> months,
			Account account, String dateType, RobOrderRecord robOrderRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "%Y-%m";
		} else if (dateType.equals("day")) {
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "SUM(a.weight+IF(date_format(a.create_time,'" + date_format
					+ "') = '" + string + "',0,-a.weight)) AS `" + string
					+ "`,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM rob_order_record as a WHERE a.`status` = 7";

		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();

		if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
			sql += " AND a.companyName =:companyName";
			map.put("companyName", account.getCompany().getName());
		}

		if (omcpanyTypeName.equals("货主")) {
			sql += " AND a.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId", account.getCompany().getId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getTurckUserId())) {
			sql += " AND a.account_id =:turckUserId";
			map.put("turckUserId", robOrderRecord.getTurckUserId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getUserID())) {
			sql += " AND a.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId", robOrderRecord.getUserID());
		}

		return this.getListBySQL(sql, map);
	}

	public Map<String, Object> getOrderDetails(String orderId) {
		StringBuffer sql = new StringBuffer(
				"SELECT orders.`id`,orders.`total_price`,orders.`unit_price`,orders.`weight`,orders.`status`,orders.`remark`,"
						+ "orders.`goods_types`,orders.`companyName` AS company_name,orders.`create_time`,orders.`rob_order_no`,orders.`account_id`,goods.`delivery_no`,goods.`consignee_area_name`,"
						+ "goods.`delivery_area_name`,goods.`total_price` AS goods_total_price,goods.`unit_price` AS goods_unit_price,goods.`total_weight` AS goods_total_weight,"
						+ "goods.`finite_time`,goods.`map_kilometer`,types.`name` AS goods_type_name,goods.`consignee_coordinate` ,goods.`delivery_coordinate`,(case  when  orders.`deposit_unit_price` IS NULL THEN 0 ELSE  1 END ) AS isPay "
						+ " FROM `rob_order_record` AS orders"
						+ " LEFT JOIN `goods_basic` AS goods ON orders.`goods_baice_id`= goods.`id`"
						+ " LEFT JOIN `goods_type` AS types ON types.`id`= goods.`goods_type_id`"
						+ " WHERE orders.`id`= :orderId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.getSqlMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 通过月分查订单重量 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getAllRobOrderMonthWeight(List<String> months, Account account,
			String dateType, RobOrderRecord robOrderRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货 2:锁定处理－正在处理 3:退回 4:通过 5:作废 **/
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "%Y-%m";
		} else if (dateType.equals("day")) {
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "SUM(a.weight+IF(date_format(a.create_time,'" + date_format
					+ "') = '" + string + "',0,-a.weight)) AS `" + string
					+ "`,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM rob_order_record as a WHERE (a.`status` = 6 or a.`status` = 7 )";

		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();

		if (omcpanyTypeName.equals("车队") || omcpanyTypeName.equals("个人司机")) {
			sql += " AND a.companyName =:companyName";
			map.put("companyName", account.getCompany().getName());
		}

		if (omcpanyTypeName.equals("货主")) {
			sql += " AND a.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId", account.getCompany().getId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getTurckUserId())) {
			sql += " AND a.account_id =:turckUserId";
			map.put("turckUserId", robOrderRecord.getTurckUserId());
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord
				.getUserID())) {
			sql += " AND a.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId", robOrderRecord.getUserID());
		}

		return this.getListBySQL(sql, map);
	}

	/**
	 * 功能描述： 根据订单获取订单详情列表 输入参数: @param orderId 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年8月13日下午4:50:59 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getOrderInfoList(String orderId) {
		StringBuffer sql = new StringBuffer(
				"SELECT detail.id,detail.`goods_name` ,detail.`height` ,detail.`length` ,detail.`weight`,detail.`diameter` ,detail.`embark_weight`,types.`name`,infos.`actual_weight`,infos.`weight` AS weighting "
						+ " FROM `rob_order_record_info` AS  infos LEFT JOIN `goods_detail` AS detail ON infos.`goods_detail_id` = detail.`id`LEFT JOIN `goods_type` AS types ON types.`id` = detail.`goods_type_id`"
						+ " WHERE `rob_order_record_id`= :orderId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 统计订单排行 输入参数: @param ranking 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日
	 * 期: 2016年8月24日下午3:53:39 修 改 人: 日 期: 返 回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getRobOrderRankingStatistics(int ranking,
			String type) {
		if (ranking == 0) {
			ranking = 10;
		}
		String sql = "SELECT count(*) AS count," + "sum(r.weight) AS weight,"
				+ "c.`name` as company," + "tc.`name` as truckCompany"
				+ " from rob_order_record as r"
				+ " LEFT JOIN sys_company c on c.id = r.robbed_company_id"
				+ " LEFT JOIN sys_company tc on tc.`name` = r.companyName"
				+ " WHERE r.`status` =6 or r.`status` =7 GROUP BY " + type
				+ " ORDER BY count desc LIMIT 0," + ranking;
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}

	public List<RobOrderRecord> getRobOrderRecordListByGoodsBasicId(String id) {
		StringBuffer hql = new StringBuffer(
				" from RobOrderRecord record where record.status in (:apply,:dealing) and record.goodsBasic.id = :goodsBasicId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apply", RobOrderRecord.Status.apply);
		map.put("dealing", RobOrderRecord.Status.dealing);
		map.put("goodsBasicId", id);
		return this.getListByHql(hql.toString(), map);
	}

	/**
	 * 功能描述： 根据货物ID获取抢单信息 输入参数: @param order 检索条件 输入参数: @param start 启始页 输入参数: @param
	 * limit 每页显示长度 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日下午3:32:09
	 * 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getWaitRobUserByGoodsId(
			RobOrderRecord order) {
		StringBuffer sql = new StringBuffer(
				"SELECT orders.`id`,orders.`rob_order_no`,account.`name` AS driver_name,account.phone AS driver_phone,"
						+ "orders.`unit_price` AS driver_price,goods.unit_price AS consignor_price,orders.remark "
						+ "FROM `rob_order_record` AS orders LEFT JOIN mem_account account ON account.id = orders.account_id "
						+ "LEFT JOIN goods_basic goods ON goods.id = orders.goods_baice_id WHERE "
						+ "orders.`goods_baice_id` = :goodsId AND (orders.`status` = 1 OR orders.`status` = 0) ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", order.getGoodsBasic().getId());
		if (StringUtil.isNotEmpty(order.getId())) {

			sql.append(" and orders.id = :roborderID ");
			map.put("roborderID", order.getId());
		}
		StringBuffer where = new StringBuffer();
		where.append(" ORDER BY orders.`unit_price`,orders.`create_time` DESC ");
		return this.getListBySQLMap(sql.toString() + where, map);
	}

	/**
	 * 功能描述： 根据货物ID获取抢单信息 输入参数: @param order 检索条件 输入参数: @param start 启始页 输入参数: @param
	 * limit 每页显示长度 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日下午3:32:09
	 * 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getAlreadyRobUserByGoodsId(
			RobOrderRecord order) {
		StringBuffer sql = new StringBuffer(
				"SELECT orders.`id`,orders.`rob_order_no`,account.`name` AS driver_name,account.phone AS driver_phone,"
						+ "orders.`unit_price` AS driver_price,goods.unit_price AS consignor_price,orders.remark,orders.`status` "
						+ "FROM `rob_order_record` AS orders LEFT JOIN mem_account account ON account.id = orders.account_id "
						+ "LEFT JOIN goods_basic goods ON goods.id = orders.goods_baice_id WHERE "
						+ "orders.`goods_baice_id` = :goodsId AND orders.`status` in (2,3,4)   ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", order.getGoodsBasic().getId());
		if (StringUtil.isNotEmpty(order.getId())) {

			sql.append(" and orders.id = :roborderID ");
			map.put("roborderID", order.getId());
		}
		StringBuffer where = new StringBuffer();
		where.append(" ORDER BY orders.`unit_price`,orders.`create_time` DESC ");
		return this.getListBySQLMap(sql.toString() + where, map);
	}

	/**
	 * 功能描述： 根据货物ID获取抢单信息 输入参数: @param order 检索条件 输入参数: @param start 启始页 输入参数: @param
	 * limit 每页显示长度 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日下午3:32:09
	 * 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getRobUserDetailsByRobOrderId(
			String robOrderId) {
		StringBuffer sql = new StringBuffer(
				" SELECT orders.`id`,orders.`rob_order_no`,info.original_weight AS rob_weight,detail.goods_name,detail.embark_weight"
						+ " FROM rob_order_record_info info LEFT JOIN rob_order_record orders ON "
						+ "orders.id = info.rob_order_record_id LEFT JOIN goods_detail detail ON detail.id = info.goods_detail_id "
						+ "LEFT JOIN mem_account account ON account.id = orders.account_id WHERE "
						+ "info.rob_order_record_id = :robOrderRecordId ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("robOrderRecordId", robOrderId);
		return this.getListBySQLMap(sql.toString(), map);
	}

	public List<Map<String, Object>> getAccountRobOrderCountNew(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		/** 0:提交申请 1:处理中 2:退回 3:成功 4:作废 5:撤回 6:结束 **/
		String sql = "SELECT    " // 抢单总数
				+ " COUNT( CASE WHEN a.`status` = 2 THEN 1    WHEN a.`status` = 4 THEN 1 WHEN a.`status` = 5 THEN 1  ELSE NULL END  ) AS `faildCount`," // 失败数
				+ "  COUNT( CASE WHEN a.`status` = 0 THEN 1   WHEN a.`status` = 1 THEN 1  ELSE NULL END  ) AS `waitCount` , "// 等待确认
				+ "COUNT( CASE WHEN a.`status` = 3 THEN 1   ELSE NULL END  ) AS `successCount`  "// 审核成功书
				+ "FROM rob_order_record as a WHERE a.account_id = :account_id";
		map.put("account_id", accountId);
		return this.getListBySQLMap(sql, map);
	}

	public Map<String, Object> getMyPageNew(RobOrderRecord record,
			Account account, int start, int limit) {
		try {
			if (StringUtil.isNotEmpty(record.getSearchKey())) { // 有关键字查询
				Map<String, Object> ret = null;
				SolrQuery params = new SolrQuery();
				@SuppressWarnings("static-access")
				List<RobOrderRecord.Status> listStatus = record
						.getStatusWithQueryStatus(record.getQueryStatus());
				List<String> listStrStatus = new ArrayList<>();
				for (RobOrderRecord.Status status : listStatus) {
					listStrStatus.add(String.valueOf(status.ordinal()));
				}
				String statusQuery = SolrStatementUtils
						.generateOrQueryByValuesWithField("status",
								listStrStatus);
	 
				String key =  String.format("    (keyWord:\"%s\" || keyWord1:\"%s\")",record.getSearchKey(),
						record.getSearchKey())
						+ " && "
						+ statusQuery;
				key += " && account_id:" + account.getId();
				params.set("q", key);
				params.set("start", start - 1);
				params.set("rows", limit);
				SolrClient client = solrUtils
						.getClient(SolrUtils.robOrderRecord);
				try {
					QueryResponse response = client.query(params);
					SolrDocumentList docs = response.getResults();
					if (docs.size() > 0) {
						ret = new HashMap<String, Object>();
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						for (SolrDocument sd : docs) {
							// 找出数据，组织回发
							list.add(MapUtil.toHashMap( sd.getFieldValueMap()));
						}
						ret.put("total", docs.getNumFound());
						ret.put("list", list);
					}
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ "a.id,a.rob_order_no,b.delivery_area_name,b.delivery_coordinate,b.consignee_area_name,b.consignee_coordinate, "
						+ "b.is_long_time,b.finite_time,b.company_name,c.name AS goods_type_name,a.weight,a.unit_price,a.total_price,a.status,"
						+ "b.delivery_name,b.consignee_name,b.total_weight goods_total_weight, b.map_kilometer, a.create_time rob_order_time, b.create_time goods_basic_create_time, b.unit_price goods_unit_price  "
						+ " FROM rob_order_record AS a LEFT JOIN goods_basic AS b ON a.goods_baice_id = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id "
						+ " WHERE  a.account_id = :account_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account_id", account.getId());
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isEmpty(record.getConsigneeAreaName())) {
			where.append(" AND b.consignee_area_name like :consigneeAreaName");
			map.put("consigneeAreaName", record.getConsigneeAreaName() + "%");
		}
		if (!StringUtils.isEmpty(record.getDeliveryAreaName())) {
			where.append(" AND b.delivery_area_name LIKE  :deliveryAreaName");
			map.put("deliveryAreaName", record.getDeliveryAreaName() + "%");
		}
		if (!StringUtils.isEmpty(record.getRobOrderNo())) {
			where.append(" AND a.rob_order_no =  :rob_order_no");
			map.put("rob_order_no", record.getRobOrderNo());
		}
		if (!StringUtils.isEmpty(record.getCompanyName())) {
			where.append(" AND b.company_name LIKE  :company_name");
			map.put("company_name", "%" + record.getCompanyName() + "%");
		}
		String status = "";
		if (record.getQueryStatus() != null) {

			List<Status> statusArray = RobOrderRecord
					.getStatusWithQueryStatus(record.getQueryStatus());
			status = ArrayUtil.joinListArray(statusArray, ",",
					new ArrayUtil.IJoinCallBack<Status>() {

						@SuppressWarnings("unchecked")
						@Override
						public <Status> String join(Status obj, int idx) {
							return ((Enum<com.memory.platform.entity.order.RobOrderRecord.Status>) obj)
									.ordinal() + "";
						}

					});
			where.append(String.format(" and a.status in  (%s) ", status));
		}
		if (record.getStatus() != null) {
			where.append(" AND a.status = :statuss ");
			map.put("statuss", record.getStatus().ordinal());
		}
		where.append(" ORDER BY a.update_time DESC ,a.create_time DESC");
		return this.getPageSqlListMap(sql.toString() + where, map, start - 1,
				limit);

	}

	public Map<String, Object> getMapById(String id) {
		String sql = "select * from rob_order_record where  rob_order_record.id = :ID";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", id);
		return this.getSqlMap(sql, map);
	}

	public List<RobOrderRecord> getMyRobOrderRecordWithGoodsBasicID(
			String goodsBasicID, Account account) {

		String sql = "from RobOrderRecord where account.id = :accountID and goodsBasic.id =:goodsBasicID";
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("accountID", account.getId());
		parameter.put("goodsBasicID", goodsBasicID);
		return this.getListByHql(sql, parameter);
	}

	/*
	 * 获取抢单审核成功后 系统自动判断失效的抢单记录 判断规则 当审核后 如果剩余吨位已经小于抢单详细吨位 就判断失效
	 */
	public List<Map<String, Object>> getInvalidRobOrderRecord(
			double surplusDetailWeight, String goodsbasicID,
			String goodsDetialID, String robOrderRecordID) {
		String strSql = " SELECT rob_order_record.id rob_order_record_id FROM rob_order_record "
				+ " WHERE rob_order_record.`status` IN (0, 1) "
				+ " AND rob_order_record.goods_baice_id = :goodsBasicID "
				+ " and rob_order_record.id <> :robOrderRecordID "
				+ " AND EXISTS ( SELECT 1 "
				+ "      FROM rob_order_record_info "
				+ "    WHERE rob_order_record_info.rob_order_record_id = rob_order_record.id "
				+ "  AND rob_order_record.weight > :surplusDetailWeight  AND rob_order_record_info.goods_detail_id = :goodsDetialID ) ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsBasicID", goodsbasicID);
		map.put("goodsDetialID", goodsDetialID);
		map.put("robOrderRecordID", robOrderRecordID);
		map.put("surplusDetailWeight", surplusDetailWeight);

		return this.getListBySQLMap(strSql, map);

	}

	public Map<String, Object> getSolrRobOrderRecordByIds(String ids,
			Account account, int start, int size) {
		StringBuffer sql = new StringBuffer(
				"SELECT "
						+ "a.id,a.rob_order_no,b.delivery_area_name,b.delivery_coordinate,b.consignee_area_name,b.consignee_coordinate, "
						+ "b.is_long_time,b.finite_time,b.company_name,c.name AS goods_type_name,a.weight,a.unit_price,a.total_price,a.status,"
						+ "b.delivery_name,b.consignee_name,b.total_weight goods_total_weight, b.map_kilometer, a.create_time rob_order_time, b.create_time goods_basic_create_time, b.unit_price goods_unit_price  "
						+ " FROM rob_order_record AS a LEFT JOIN goods_basic AS b ON a.goods_baice_id = b.id LEFT JOIN goods_type AS c ON b.goods_type_id = c.id "
						+ " WHERE  a.account_id = :account_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account_id", account.getId());
		StringBuffer where = new StringBuffer();
		where.append(" and a.id in (:ids) ");
		map.put("ids", ids);
		where.append(" ORDER BY a.update_time DESC ,a.create_time DESC");
		return this.getPageSqlListMap(sql.toString() + where, map, start, size);
	}
}
