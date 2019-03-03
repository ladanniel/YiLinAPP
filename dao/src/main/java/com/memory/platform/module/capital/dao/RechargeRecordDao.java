package com.memory.platform.module.capital.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.ThreeDESUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午6:39:06 
* 修 改 人： 
* 日   期： 
* 描   述：App 充值dao
* 版 本 号：  V1.0
 */
@Repository
public class RechargeRecordDao extends BaseDao<RechargeRecord> {

	/**
	* 功能描述：app列表分页充值记录 
	* 输入参数:  @param rechargeRecord
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午3:09:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getPage(RechargeRecord rechargeRecord,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from RechargeRecord rechargeRecord where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != rechargeRecord.getAccount()){
			where.append(" and rechargeRecord.account.id = :userId");
			map.put("userId",rechargeRecord.getAccount().getId());
		}
		if(null != rechargeRecord.getStart()){
			where.append(" and rechargeRecord.create_time between :start and :end");
			if(rechargeRecord.getStart().getTime() ==  rechargeRecord.getEnd().getTime()){
				map.put("start", rechargeRecord.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(rechargeRecord.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", rechargeRecord.getStart());
				map.put("end", rechargeRecord.getEnd());
			}
		}
		if(null != rechargeRecord.getStatus()){
			where.append(" and rechargeRecord.status = :status");
			map.put("status", rechargeRecord.getStatus());
		}
		if(StringUtil.isNotEmpty(rechargeRecord.getSearch())){
			where.append(" and (rechargeRecord.tradeNo = :tradeNo or "
					+ "rechargeRecord.tradeName like :tradeName or "
					+ "rechargeRecord.tradeAccount like :tradeAccount or "
					+ "rechargeRecord.account.account = :account or "
					+ "rechargeRecord.account.phone = :phone or "
					+ "rechargeRecord.account.name = :name)");
			map.put("tradeNo", rechargeRecord.getSearch());
			map.put("tradeName", "%" + rechargeRecord.getSearch() + "%");
			map.put("tradeAccount", rechargeRecord.getSearch() + "%");
			map.put("account",  rechargeRecord.getSearch());
			map.put("phone",  rechargeRecord.getSearch());
			map.put("name",  rechargeRecord.getSearch());
		}
		where.append(" order by rechargeRecord.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	@SuppressWarnings("static-access")
	public List<RechargeRecord> getList(RechargeRecord rechargeRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from RechargeRecord rechargeRecord where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != rechargeRecord.getAccount()){
			where.append(" and rechargeRecord.account.id = :userId");
			map.put("userId",rechargeRecord.getAccount().getId());
		}
		if(null != rechargeRecord.getStart()){
			where.append(" and rechargeRecord.create_time between :start and :end");
			if(rechargeRecord.getStart().getTime() ==  rechargeRecord.getEnd().getTime()){
				map.put("start", rechargeRecord.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(rechargeRecord.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", rechargeRecord.getStart());
				map.put("end", rechargeRecord.getEnd());
			}
		}
		if(null != rechargeRecord.getStatus()){
			where.append(" and rechargeRecord.status = :status");
			map.put("status", rechargeRecord.getStatus());
		}
		if(StringUtil.isNotEmpty(rechargeRecord.getSearch())){
			where.append(" and (rechargeRecord.tradeNo = :tradeNo or "
					+ "rechargeRecord.tradeName like :tradeName or "
					+ "rechargeRecord.tradeAccount like :tradeAccount or "
					+ "rechargeRecord.account.account = :account or "
					+ "rechargeRecord.account.phone = :phone or "
					+ "rechargeRecord.account.name = :name)");
			map.put("tradeNo", rechargeRecord.getSearch());
			map.put("tradeName", "%" + rechargeRecord.getSearch() + "%");
			map.put("tradeAccount", rechargeRecord.getSearch() + "%");
			map.put("account",  rechargeRecord.getSearch());
			map.put("phone",  rechargeRecord.getSearch());
			map.put("name",  rechargeRecord.getSearch());
		}
		where.append(" order by rechargeRecord.create_time desc");
		return this.getListByHql(hql.append(where).toString(),map);
	}
	
	/**
	* 功能描述： 统计平台总充值及充值成功次数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:36:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalRecharge(){
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_recharge_record` WHERE `status` = 1";
		return this.getSqlMap(sql, new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计个人总充值及充值成次数
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:50:07
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalRecharge(Account account){
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_recharge_record` WHERE `status` = 1 AND `account_id` = :accountId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计平台本月充值
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日下午8:26:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getMonthTotalRecharge(Account account){
		Date date= new Date();
		String startTime = DateUtil.dateToString(date, "yyyy-MM") + "-01 00:00:00";
		String endTime = DateUtil.dateToString(date, null);
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_recharge_record` WHERE `status` = 1 AND `create_time` BETWEEN :startTime AND :endTime";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		if(null != account){
			sql += " AND `account_id` = :accountId";
			map.put("accountId", account.getId());
		}
		return this.getSqlMap(sql, map);
	}
	
	/**
	 * 根据订单编号查询充值记录
	 * @param orderId
	 * @return
	 */
	public RechargeRecord getRechargeRecordByOrderId(String orderId){
		String sql = " from RechargeRecord vo where vo.tradeNo = :tradeNo";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tradeNo", orderId);
		List<RechargeRecord> list = this.getListByHql(sql, map);
		if(null != list && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getListForMap(String accountId, int start, int size) {
		String sql = "select vo.trade_no,vo.source_type,vo.trade_account,vo.money,vo.create_time,vo.status,vo.trade_name from mem_recharge_record as vo where vo.account_id = :accountId order by create_time desc limit :start,:size";
		String hqlCount = " from RechargeRecord vo where vo.account.id = '" + accountId + "'"; 
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("start", start);
		map.put("size", size);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("total", this.getCountHql(hqlCount, new HashMap<String,Object>()));
		data.put("list", this.getListBySQLMap(sql, map));
		return data;
	}
}
