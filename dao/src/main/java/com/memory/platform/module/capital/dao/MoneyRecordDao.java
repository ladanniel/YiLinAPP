package com.memory.platform.module.capital.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午6:34:04 修 改 人： 日 期： 描 述： 资金记录dao 版 本 号：
 * V1.0
 */
@Repository
public class MoneyRecordDao extends BaseDao<MoneyRecord> {

	/**
	 * 功能描述： 平台分页资金记录 输入参数: @param moneyRecord 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月29日上午11:02:37 修 改 人:
	 * 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getPage(MoneyRecord moneyRecord, int start, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from MoneyRecord moneyRecord where 1 = 1");
		StringBuffer where = new StringBuffer();
		if (null != moneyRecord.getAccount()) {
			where.append(" and moneyRecord.account.id = :userId");
			map.put("userId", moneyRecord.getAccount().getId());
		}
		if (null != moneyRecord.getStartTime()) {
			if (moneyRecord.getStartTime().getTime() == moneyRecord.getEndTime().getTime()) {
				where.append(" and moneyRecord.create_time between :start and :end");
				map.put("start", moneyRecord.getStartTime());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(moneyRecord.getEndTime());
				calendar.add(calendar.DATE, 1);
				map.put("end", calendar.getTime());
			} else {
				where.append(" and moneyRecord.create_time between :start and :end");
				map.put("start", moneyRecord.getStartTime());
				map.put("end", moneyRecord.getEndTime());
			}
		}
		if (null != moneyRecord.getType()) {
			where.append(" and moneyRecord.type = :type");
			map.put("type", moneyRecord.getType());
		}
		if (StringUtil.isNotEmpty(moneyRecord.getSearch())) {
			if (null != moneyRecord.getAccount()) {
				where.append(" and moneyRecord.tradeName like :tradeName");
			} else {
				where.append(
						" and (moneyRecord.account.account = :account or " + "moneyRecord.account.phone = :phone or "
								+ "moneyRecord.account.name = :name or " + "moneyRecord.tradeName like :tradeName)");
				map.put("account", moneyRecord.getSearch());
				map.put("phone", moneyRecord.getSearch());
				map.put("name", moneyRecord.getSearch());
			}
			map.put("tradeName", "%" + moneyRecord.getSearch() + "%");
		}

		where.append(" order by moneyRecord.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	/**
	 * 功能描述： excel数据源 输入参数: @param moneyRecord 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年5月28日上午10:28:24 修 改 人: 日 期: 返 回：List<MoneyRecord>
	 */
	@SuppressWarnings("static-access")
	public List<MoneyRecord> getList(MoneyRecord moneyRecord) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from MoneyRecord moneyRecord where 1 = 1");
		StringBuffer where = new StringBuffer();
		if (null != moneyRecord.getAccount()) {
			where.append(" and moneyRecord.account.id = :userId");
			map.put("userId", moneyRecord.getAccount().getId());
		}
		if (null != moneyRecord.getStartTime()) {
			if (moneyRecord.getStartTime().getTime() == moneyRecord.getEndTime().getTime()) {
				where.append(" and moneyRecord.create_time between :start and :end");
				map.put("start", moneyRecord.getStartTime());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(moneyRecord.getEndTime());
				calendar.add(calendar.DATE, 1);
				map.put("end", calendar.getTime());
			} else {
				where.append(" and moneyRecord.create_time between :start and :end");
				map.put("start", moneyRecord.getStartTime());
				map.put("end", moneyRecord.getEndTime());
			}
		}
		if (null != moneyRecord.getType()) {
			where.append(" and moneyRecord.type = :type");
			map.put("type", moneyRecord.getType());
		}
		if (StringUtil.isNotEmpty(moneyRecord.getSearch())) {
			if (null != moneyRecord.getAccount()) {
				where.append(" and moneyRecord.tradeName like :tradeName");
			} else {
				where.append(
						" and (moneyRecord.account.account = :account or " + "moneyRecord.account.phone = :phone or "
								+ "moneyRecord.account.name = :name or " + "moneyRecord.tradeName like :tradeName)");
				map.put("account", moneyRecord.getSearch());
				map.put("phone", moneyRecord.getSearch());
				map.put("name", moneyRecord.getSearch());
			}
			map.put("tradeName", "%" + moneyRecord.getSearch() + "%");
		}

		where.append(" order by moneyRecord.create_time desc");
		return this.getListByHql(hql.append(where).toString(), map);
	}

	/**
	 * 功能描述： 根据账户统计各类型交易数据信息 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年8月6日下午5:25:19 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getMoneyRecordInfo(String accountId) {
		StringBuffer sql = new StringBuffer(
				"SELECT SUM(`money`) AS money,`type` FROM `mem_money_record` WHERE `account_id` = :accountId GROUP BY `type` ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 统计平台交易信息 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年8月20日下午12:32:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public List<Map<String, Object>> getMoneyRecordInfo() {
		String sql = "SELECT SUM(`money`) AS money,`type`" + " FROM `mem_money_record`" + " GROUP BY `type`";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = this.getListBySQLMap(sql.toString(), map);
		return list;
	}

	/**
	 * 功能描述： 根据账户及时间段统计各类型月交易数据信息 输入参数: @param accountId 账户ID 输入参数: @param
	 * startTime 开始时间 输入参数: @param endTime 结束时间 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年8月10日下午3:42:10 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	@Cacheable(value = "monthMoneyReord")
	public List<Map<String, Object>> getMoneyRecordInfo(String accountId, String startTime, String endTime) {
		String sql = "SELECT SUM(`money`) AS money,`type`,date_format(`create_time`, '%Y') AS yearDate,date_format(`create_time`, '%m') AS monthDate,date_format(`create_time`, '%Y-%m') AS formatDate"
				+ " FROM `mem_money_record`"
				+ " WHERE `account_id`= :accountId AND `create_time` BETWEEN :startTime AND :endTime"
				+ " GROUP BY `type`,date_format(`create_time`, '%Y-%m')";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.getListBySQLMap(sql.toString(), map);
	}

	/**
	 * 功能描述： 资金统计 输入参数: @param dates 输入参数: @param accountId 输入参数: @param
	 * dateType 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年9月4日下午4:37:06 修 改
	 * 人: 日 期: 返 回：List
	 */
	public Map<String, Object> getAllMoneyRecord(List<String> dates, String accountId, String dateType, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		String date_format = "%Y-%m";
		if (dateType.equals("month")) {
			date_format = "'%Y-%m'";
		} else if (dateType.equals("day")) {
			date_format = "'%Y-%m-%d'";
		}
		String sql = "SELECT  ";
		for (String string : dates) {
			sql += "SUM(a.`money`+ IF((date_format(a.create_time, " + date_format + ")= '" + string
					+ "'  and a.`type` = " + type + "), 0, -a.money)) `" + string + "`,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " FROM `mem_money_record` AS a ";
		if (!StringUtils.isEmpty(accountId)) {
			sql += " WHERE a.account_id = :account_id ";
			map.put("account_id", accountId);
		}
		return this.getSqlMap(sql, map);
	}

	public Map<String, Object> getListForMap(String accountId, String type, int start, int size, Date startTime,
			Date endTime) {
		StringBuffer sql = new StringBuffer("select * from mem_money_record as vo where vo.account_id = :accountId");
		StringBuffer hqlCount = new StringBuffer(
				" from MoneyRecord moneyRecord where moneyRecord.account.id = :accountId");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapCount = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		StringBuffer whereCount = new StringBuffer();
		if (StringUtil.isNotEmpty(type)) {
			MoneyRecord.Type state = null;
			if (type.equals("recharge")) {
				where.append(" and vo.type = 0");
				state = MoneyRecord.Type.recharge;
			} else if (type.equals("cash")) {
				where.append(" and vo.type = 1");
				state = MoneyRecord.Type.cash;
			} else if (type.equals("transfer")) {
				where.append(" and vo.type = 2");
				state = MoneyRecord.Type.transfer;
			} else if (type.equals("fee")) {
				where.append(" and vo.type = 3");
				state = MoneyRecord.Type.fee;
			} else if (type.equals("consume")) {
				where.append(" and vo.type = 4");
				state = MoneyRecord.Type.consume;
			} else if (type.equals("frozen")) {
				where.append(" and vo.type = 5");
				state = MoneyRecord.Type.frozen;
			} else if (type.equals("income")) {
				where.append(" and vo.type = 6");
				state = MoneyRecord.Type.income;
			} else if (type.equals("other")) {
				where.append(" and vo.type = 7");
				state = MoneyRecord.Type.other;
			} else if (type.equals("paid")) {
				where.append(" and vo.type = 8");
				state = MoneyRecord.Type.paid;
			} else if (type.equals("thaw")) {
				where.append(" and vo.type = 9");
				state = MoneyRecord.Type.thaw;
			} else if (type.equals("transportSection")) {
				where.append(" and vo.type = 10");
				state = MoneyRecord.Type.transportSection;
			}
			whereCount.append(" and moneyRecord.type = :type");
			mapCount.put("type", state);
		}
		if (null != startTime) {
			if (startTime.getTime() == endTime.getTime()) {
				whereCount.append(" and moneyRecord.create_time between :startTime and :endTime");
				where.append(" and vo.create_time between :startTime and :endTime");
				mapCount.put("startTime", startTime);
				;
				map.put("startTime", startTime);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(endTime);
				calendar.add(calendar.DATE, 1);
				mapCount.put("endTime", calendar.getTime());
				map.put("endTime", calendar.getTime());
			} else {
				whereCount.append(" and moneyRecord.create_time between :startTime and :endTime");
				where.append(" and vo.create_time between :startTime and :endTime");
				map.put("startTime", startTime);
				map.put("endTime", endTime);
				mapCount.put("startTime", startTime);
				mapCount.put("endTime", endTime);
			}
		}
		mapCount.put("accountId", accountId);
		map.put("accountId", accountId);
		map.put("start", start);
		map.put("size", size);
		where.append(" order by create_time desc limit :start,:size");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", this.getCountHql(hqlCount.append(whereCount).toString(), mapCount));
		data.put("list", this.getListBySQLMap(sql.append(where).toString(), map));
		return data;
	}

	/**
	 * 分页资金记录 aiqiwu 2017-09-18
	 */
	public Map<String, Object> getCapitalRecordPage(MoneyRecord moneyRecord, int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				" SELECT record.id,record.account_id,record.create_time,record.money,record.operator,record.remark,"
						+ "record.source_account,record.source_type,record.trade_account,record.trade_name,"
						+ "record.type FROM mem_money_record record where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		if (null != moneyRecord.getAccount()) {
			where.append(" and record.account_id = :userId");
			map.put("userId", moneyRecord.getAccount().getId());
		}
		if (null != moneyRecord.getStartTime()) {
			if (moneyRecord.getStartTime().getTime() == moneyRecord.getEndTime().getTime()) {
				where.append(" and DATE_FORMAT(record.create_time,'%Y-%m-%d') between :start and :end");
				map.put("start", moneyRecord.getStartTime());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(moneyRecord.getEndTime());
				calendar.add(calendar.DATE, 1);
				map.put("end", calendar.getTime());
			} else {
				where.append(" and DATE_FORMAT(record.create_time,'%Y-%m-%d') between :start and :end");
				map.put("start", moneyRecord.getStartTime());
				map.put("end", moneyRecord.getEndTime());
			}
		}
		if (null != moneyRecord.getType()) {
			where.append(" and record.type = :type");
			map.put("type", moneyRecord.getType());
		}
		where.append(" order by record.create_time desc");
		return this.getPageSQLMap(sql.append(where).toString(), map, start, limit);
	}
}
