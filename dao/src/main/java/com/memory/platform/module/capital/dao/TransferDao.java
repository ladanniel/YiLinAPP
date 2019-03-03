package com.memory.platform.module.capital.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.Transfer;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月4日 下午8:21:31 
* 修 改 人： 
* 日   期： 
* 描   述： 转账 － dao
* 版 本 号：  V1.0
 */
@Repository
public class TransferDao extends BaseDao<Transfer> {

	/**
	* 功能描述： 分页转账列表
	* 输入参数:  @param transfer
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:35:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getPage(Transfer transfer,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from Transfer transfer where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != transfer.getAccount()){
			where.append(" and transfer.account = :account");
			map.put("account",transfer.getAccount());
		}
		if(null != transfer.getStart()){
			where.append(" and transfer.create_time between :start and :end");
			if(transfer.getStart().getTime() ==  transfer.getEnd().getTime()){
				map.put("start", transfer.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(transfer.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", transfer.getStart());
				map.put("end", transfer.getEnd());
			}
		}
		if(StringUtil.isNotEmpty(transfer.getSearch())){
			if(null == transfer.getAccount()){
				where.append(" and (transfer.tradeNo = :tradeNo or "
						+ "transfer.account.account = :account or "
						+ "transfer.account.phone = :phone or "
						+ "transfer.account.name = :name or "
						+ "transfer.toAccount.account = :toaccount or "
						+ "transfer.toAccount.phone = :tophone or "
						+ "transfer.toAccount.name = :toname or "
						+ "transfer.transferType.name = :typeName)");
				map.put("account", transfer.getSearch());
				map.put("phone", transfer.getSearch());
				map.put("name", transfer.getSearch());
			}else{
				where.append(" and (transfer.tradeNo = :tradeNo or "
						+ "transfer.toAccount.account = :toaccount or "
						+ "transfer.toAccount.phone = :tophone or "
						+ "transfer.toAccount.name = :toname or "
						+ "transfer.transferType.name = :typeName)");
			}
			
			map.put("tradeNo", transfer.getSearch());
			map.put("toaccount", transfer.getSearch());
			map.put("tophone", transfer.getSearch());
			map.put("toname", transfer.getSearch());
			map.put("typeName", transfer.getSearch());
		}
		if(null == transfer.getAccount()){
			where.append(" and transfer.money > 0");
		}
		where.append(" order by transfer.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	/**
	* 功能描述： excel数据源
	* 输入参数:  @param transfer
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月28日上午9:53:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Transfer>
	 */
	@SuppressWarnings("static-access")
	public List<Transfer> getList(Transfer transfer){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from Transfer transfer where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != transfer.getAccount()){
			where.append(" and transfer.account = :account");
			map.put("account",transfer.getAccount());
		}
		if(null != transfer.getTransferType()){
			where.append(" and transfer.transferType.id = :transferTypeId");
			map.put("transferTypeId",transfer.getTransferType().getId());
		}
		if(null != transfer.getStart()){
			where.append(" and transfer.create_time between :start and :end");
			if(transfer.getStart().getTime() ==  transfer.getEnd().getTime()){
				map.put("start", transfer.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(transfer.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", transfer.getStart());
				map.put("end", transfer.getEnd());
			}
		}
		if(StringUtil.isNotEmpty(transfer.getSearch())){
			if(null == transfer.getAccount()){
				where.append(" and (transfer.tradeNo = :tradeNo or "
						+ "transfer.account.account = :account or "
						+ "transfer.account.phone = :phone or "
						+ "transfer.account.name = :name or "
						+ "transfer.toAccount.account = :toaccount or "
						+ "transfer.toAccount.phone = :tophone or "
						+ "transfer.toAccount.name = :toname or "
						+ "transfer.transferType.name = :typeName)");
				map.put("account", transfer.getSearch());
				map.put("phone", transfer.getSearch());
				map.put("name", transfer.getSearch());
			}else{
				where.append(" and (transfer.tradeNo = :tradeNo or "
						+ "transfer.toAccount.account = :toaccount or "
						+ "transfer.toAccount.phone = :tophone or "
						+ "transfer.toAccount.name = :toname or "
						+ "transfer.transferType.name = :typeName)");
			}
			
			map.put("tradeNo", transfer.getSearch());
			map.put("toaccount", transfer.getSearch());
			map.put("tophone", transfer.getSearch());
			map.put("toname", transfer.getSearch());
			map.put("typeName", transfer.getSearch());
		}
		if(null == transfer.getAccount()){
			where.append(" and transfer.money > 0");
		}
		where.append(" order by transfer.create_time desc");
		return this.getListByHql(hql.append(where).toString(), map);
	}
	
	public List<Transfer> getListByTypeId(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.getListByHql(" from Transfer transfer where transfer.transferType.id = :id",map);
	}
	
	/**
	* 功能描述： 统计平台转账总数及次数
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:59:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalTransfer(){
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_transfer` WHERE `status` = 0 AND `money` > 0";
		return this.getSqlMap(sql, new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计个人用户转账总数及次数
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午11:03:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalTransfer(Account account){
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_transfer` WHERE `status` = 0 AND `account_id` = :accountId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, map); 
	}
	
	/**
	* 功能描述： 统计个人用户收款总数及次数
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午11:07:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalIncome(Account account){
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_transfer` WHERE `status` = 0 AND `toaccount_id` = :accountId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, map); 
	}
	
	/**
	* 功能描述： 统计平台本月转账
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日下午8:26:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getMonthTotalTransfer(){
		Date date= new Date();
		String startTime = DateUtil.dateToString(date, "yyyy-MM") + "01 00:00:00";
		String endTime = DateUtil.dateToString(date, null);
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_transfer` WHERE `status` = 0 AND `money` > 0 AND `create_time` BETWEEN :startTime AND :endTime";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.getSqlMap(sql, map);
	}
	
	/**
	* 功能描述： 统计个人用户转账总数及次数（本月）
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午11:03:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getMonthTotalTransfer(Account account){
		Date date= new Date();
		String startTime = DateUtil.dateToString(date, "yyyy-MM") + "-01 00:00:00";
		String endTime = DateUtil.dateToString(date, null);
		String sql = "SELECT SUM(`money`) AS money,COUNT(*) AS total FROM `mem_transfer` WHERE `status` = 0 AND `account_id` = :accountId AND `create_time` BETWEEN :startTime AND :endTime";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return this.getSqlMap(sql, map); 
	}

	public Map<String, Object> getListForMap(String accountId, int start, int size) {
		String sql = "select * from mem_transfer as vo where vo.account_id = :accountId order by create_time desc limit :start,:size";
		String hqlCount = " from Transfer vo where vo.account.id = '" + accountId + "'"; 
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
