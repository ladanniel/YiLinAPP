package com.memory.platform.module.capital.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.CashApplication;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.ThreeDESUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午6:32:39 
* 修 改 人： 
* 日   期： 
* 描   述：BankCard提现dao
* 版 本 号：  V1.0
 */
@Repository
public class CashApplicationDao extends BaseDao<CashApplication> {

	/**
	* 功能描述： 分页列表
	* 输入参数:  @param cashApplication
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午5:58:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getPage(CashApplication cashApplication,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from CashApplication cashApplication where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != cashApplication.getAccount()){
			where.append(" and cashApplication.account.id = :userId");
			map.put("userId",cashApplication.getAccount().getId());
		}
		if(null != cashApplication.getStart()){
			where.append(" and cashApplication.create_time between :start and :end");
			if(cashApplication.getStart().getTime() ==  cashApplication.getEnd().getTime()){
				map.put("start", cashApplication.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", cashApplication.getStart());
				map.put("end", cashApplication.getEnd());
			}
		}
		if(null != cashApplication.getStartTwo()){
			where.append(" and cashApplication.verifytime between :startTwo and :endTwo");
			if(cashApplication.getStartTwo().getTime() ==  cashApplication.getEndTwo().getTime()){
				map.put("startTwo", cashApplication.getStartTwo());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEndTwo()); 
			    calendar.add(calendar.DATE,1);
				map.put("endTwo", calendar.getTime());
			}else{
				map.put("startTwo", cashApplication.getStartTwo());
				map.put("endTwo", cashApplication.getEndTwo());
			}
		}
		if(null != cashApplication.getStatus()){
			where.append(" and cashApplication.status = :status");
			map.put("status", cashApplication.getStatus());
		}
		if(StringUtil.isNotEmpty(cashApplication.getSearch())){
			where.append(" and (cashApplication.account.phone = :phone or "
					+ "cashApplication.account.name = :name "
					+ "or cashApplication.verifytPeopson = :verifytPeopson or "
					+ "cashApplication.bankname like :bankname "
					+ "or cashApplication.bankcard = :bankcard)");
			map.put("phone", cashApplication.getSearch());
			map.put("name",  cashApplication.getSearch());
			map.put("verifytPeopson",  cashApplication.getSearch());
			map.put("bankname",  "%" + cashApplication.getSearch() + "%");
			map.put("bankcard", cashApplication.getSearch());
		}
		where.append(" order by cashApplication.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	@SuppressWarnings("static-access")
	public Map<String, Object> getOsLogPage(CashApplication cashApplication,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from CashApplication cashApplication where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != cashApplication.getAccount()){
			where.append(" and cashApplication.account.id = :userId");
			map.put("userId",cashApplication.getAccount().getId());
		}
		if(null != cashApplication.getStart()){
			where.append(" and cashApplication.create_time between :start and :end");
			if(cashApplication.getStart().getTime() ==  cashApplication.getEnd().getTime()){
				map.put("start", cashApplication.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", cashApplication.getStart());
				map.put("end", cashApplication.getEnd());
			}
		}
		if(null != cashApplication.getStartTwo()){
			where.append(" and cashApplication.verifytime between :startTwo and :endTwo");
			if(cashApplication.getStartTwo().getTime() ==  cashApplication.getEndTwo().getTime()){
				map.put("startTwo", cashApplication.getStartTwo());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEndTwo()); 
			    calendar.add(calendar.DATE,1);
				map.put("endTwo", calendar.getTime());
			}else{
				map.put("startTwo", cashApplication.getStartTwo());
				map.put("endTwo", cashApplication.getEndTwo());
			}
		}
		if(null != cashApplication.getStatus()){
			where.append(" and cashApplication.status = :status");
			map.put("status", cashApplication.getStatus());
		}else{
			where.append(" and (cashApplication.status = :status or cashApplication.status = :statust)");
			map.put("status", CashApplication.Status.success);
			map.put("statust", CashApplication.Status.failed);
		}
		if(StringUtil.isNotEmpty(cashApplication.getSearch())){
			where.append(" and (cashApplication.account.phone = :phone or "
					+ "cashApplication.account.name = :name "
					+ "or cashApplication.verifytPeopson = :verifytPeopson or "
					+ "cashApplication.bankname like :bankname "
					+ "or cashApplication.bankcard = :bankcard)");
			map.put("phone", cashApplication.getSearch());
			map.put("name",  cashApplication.getSearch());
			map.put("verifytPeopson",  cashApplication.getSearch());
			map.put("bankname",  "%" + cashApplication.getSearch() + "%");
			map.put("bankcard", cashApplication.getSearch());
		}
		where.append(" order by cashApplication.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	/**
	* 功能描述： 分页列表
	* 输入参数:  @param cashApplication
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午5:58:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getOsPage(CashApplication cashApplication,int start, int limit){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from CashApplication cashApplication where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != cashApplication.getAccount()){
			where.append(" and cashApplication.account.id = :userId");
			map.put("userId",cashApplication.getAccount().getId());
		}
		if(null != cashApplication.getStart()){
			where.append(" and cashApplication.create_time between :start and :end");
			if(cashApplication.getStart().getTime() ==  cashApplication.getEnd().getTime()){
				map.put("start", cashApplication.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", cashApplication.getStart());
				map.put("end", cashApplication.getEnd());
			}
		}
		if(null != cashApplication.getStartTwo()){
			where.append(" and cashApplication.verifytime between :startTwo and :endTwo");
			if(cashApplication.getStartTwo().getTime() ==  cashApplication.getEndTwo().getTime()){
				map.put("startTwo", cashApplication.getStartTwo());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEndTwo()); 
			    calendar.add(calendar.DATE,1);
				map.put("endTwo", calendar.getTime());
			}else{
				map.put("startTwo", cashApplication.getStartTwo());
				map.put("endTwo", cashApplication.getEndTwo());
			}
		}
		if(null != cashApplication.getStatus()){
			where.append(" and cashApplication.status = :status");
			map.put("status", cashApplication.getStatus());
		}else{
			where.append(" and (cashApplication.status = :status or cashApplication.status = :statust)");
			map.put("status", CashApplication.Status.waitProcess);
			map.put("statust", CashApplication.Status.lock);
		}
		if(StringUtil.isNotEmpty(cashApplication.getSearch())){
			where.append(" and (cashApplication.account.phone = :phone or "
					+ "cashApplication.account.name = :name "
					+ "or cashApplication.verifytPeopson = :verifytPeopson or "
					+ "cashApplication.bankname like :bankname "
					+ "or cashApplication.bankcard = :bankcard)");
			map.put("phone", cashApplication.getSearch());
			map.put("name",  cashApplication.getSearch());
			map.put("verifytPeopson",  cashApplication.getSearch());
			map.put("bankname",  "%" + cashApplication.getSearch() + "%");
			map.put("bankcard", cashApplication.getSearch());
		}
		if(StringUtil.isNotEmpty(cashApplication.getDatestatus())){
			Date now = new Date();
			Calendar   calendar   =   new   GregorianCalendar(); 
		    calendar.setTime(now); 
		    calendar.add(calendar.DATE,-cashApplication.getDay());
			if(cashApplication.getDatestatus().equals("normal")){
				where.append(" and cashApplication.create_time < :datetime");
				map.put("datetime", calendar.getTime());
			}else if(cashApplication.getDatestatus().equals("particularity")){
				where.append(" and cashApplication.create_time between :start and :end");
				map.put("start", calendar.getTime());
				map.put("end", now);
			}
		}
		where.append(" order by cashApplication.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	/**
	* 功能描述： excel数据源
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月27日下午5:28:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<CashApplication>
	 */
	@SuppressWarnings("static-access")
	public List<CashApplication> getList(CashApplication cashApplication,String type){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(" from CashApplication cashApplication where 1 = 1");
		StringBuffer where = new StringBuffer();
		if(null != cashApplication.getAccount()){
			where.append(" and cashApplication.account.id = :userId");
			map.put("userId",cashApplication.getAccount().getId());
		}
		if(null != cashApplication.getStart()){
			where.append(" and cashApplication.create_time between :start and :end");
			if(cashApplication.getStart().getTime() ==  cashApplication.getEnd().getTime()){
				map.put("start", cashApplication.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", cashApplication.getStart());
				map.put("end", cashApplication.getEnd());
			}
		}
		if(null != cashApplication.getStartTwo()){
			where.append(" and cashApplication.verifytime between :startTwo and :endTwo");
			if(cashApplication.getStartTwo().getTime() ==  cashApplication.getEndTwo().getTime()){
				map.put("startTwo", cashApplication.getStartTwo());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(cashApplication.getEndTwo()); 
			    calendar.add(calendar.DATE,1);
				map.put("endTwo", calendar.getTime());
			}else{
				map.put("startTwo", cashApplication.getStartTwo());
				map.put("endTwo", cashApplication.getEndTwo());
			}
		}
		if(null != cashApplication.getStatus()){
			where.append(" and cashApplication.status = :status");
			map.put("status", cashApplication.getStatus());
		}else{
			where.append(" and (cashApplication.status = :status or cashApplication.status = :statust)");
			if(type.equals("log")){
				map.put("status", CashApplication.Status.success);
				map.put("statust", CashApplication.Status.failed);
			}else if(type.equals("verify")){
				map.put("status", CashApplication.Status.waitProcess);
				map.put("statust", CashApplication.Status.lock);
			}
		}
		if(StringUtil.isNotEmpty(cashApplication.getDatestatus())){
			Date now = new Date();
			Calendar   calendar   =   new   GregorianCalendar(); 
		    calendar.setTime(now); 
		    calendar.add(calendar.DATE,-cashApplication.getDay());
			if(cashApplication.getDatestatus().equals("normal")){
				where.append(" and cashApplication.create_time < :datetime");
				map.put("datetime", calendar.getTime());
			}else if(cashApplication.getDatestatus().equals("particularity")){
				where.append(" and cashApplication.create_time between :start and :end");
				map.put("start", calendar.getTime());
				map.put("end", now);
			}
		}
		if(StringUtil.isNotEmpty(cashApplication.getSearch())){
			where.append(" and (cashApplication.account.phone = :phone or "
					+ "cashApplication.account.name = :name "
					+ "or cashApplication.verifytPeopson = :verifytPeopson or "
					+ "cashApplication.bankname like :bankname "
					+ "or cashApplication.bankcard = :bankcard)");
			map.put("phone", cashApplication.getSearch());
			map.put("name",  cashApplication.getSearch());
			map.put("verifytPeopson",  cashApplication.getSearch());
			map.put("bankname",  "%" + cashApplication.getSearch() + "%");
			map.put("bankcard", cashApplication.getSearch());
		}
		where.append(" order by cashApplication.create_time desc");
		return this.getListByHql(hql.append(where).toString(),map);
	}
	
	/**
	* 功能描述： 统计发起提现申请未处理的数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月1日下午4:08:15
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getToatlWaitProcess(){
		return this.getSqlMap("SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `status` = 0 or `status` = 3", new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计平台规定提现期限内未处理的数据
	* 输入参数:  @param day
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月1日下午4:25:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：int
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getToatlWaitProcessAndDate(int day){
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE (`status` = 0 or `status` = 3)");
		Date now = new Date();
		Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(now); 
	    calendar.add(calendar.DATE,-day);
	    sql.append(" and `create_time` < :datetime");
		map.put("datetime", calendar.getTime());
		return this.getSqlMap(sql.toString(), map);
	}
	
	/**
	* 功能描述： 统计已审核并且成功的数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月1日下午4:28:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：int
	 */
	public Map<String, Object> getTotalSuccess(){
		return this.getSqlMap("SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `status` = 1", new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计已审核并且失败的数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:19:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：int
	 */
	public Map<String, Object> getTotalFail(){
		return this.getSqlMap("SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `status` = 2", new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计个人申请提现次数
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:24:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalCashCount(Account account){
		String sql = "SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `account_id` = :accountId";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, map);
	}
	
	/**
	* 功能描述： 统计个人提现等待处理数据
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:31:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalCashWaitProcess(Account account){
		String sql = "SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `account_id` = :accountId AND (`status` = 0 OR `status` = 3)";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, map);
	}
	
	/**
	* 功能描述： 统计个人提现审核成功数据
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:32:30
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalCashSuccess(Account account){
		String sql = "SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `account_id` = :accountId AND `status` = 1";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, map);
	}
	
	/**
	* 功能描述： 统计个人提现审核失败数据
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:34:20
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalCashFail(Account account){
		String sql = "SELECT COUNT(*) AS total  FROM `mem_cashapplication` WHERE `account_id` = :accountId AND `status` = 2";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", account.getId());
		return this.getSqlMap(sql, map);
	}
	
	/**
	* 功能描述： 统计平台总提现
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日上午10:41:08
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getTotalCash(){
		String sql = "SELECT SUM(`money`)  AS money  FROM `mem_cashapplication` WHERE `status` = 1";
		return this.getSqlMap(sql, new HashMap<String,Object>());
	}
	
	/**
	* 功能描述： 统计平台本月提现
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年9月2日下午8:26:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getMonthTotalCash(Account account){
		Date date= new Date();
		String startTime = DateUtil.dateToString(date, "yyyy-MM") + "-01 00:00:00";
		String endTime = DateUtil.dateToString(date, null);
		String sql = "SELECT SUM(`money`)  AS money  FROM `mem_cashapplication` WHERE `status` = 1 AND `create_time` BETWEEN :startTime AND :endTime";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		if(null != account){
			sql += " AND `account_id` = :accountId";
			map.put("accountId", account.getId());
		}
		return this.getSqlMap(sql, map);
	}

	public Map<String, Object> getListForMap(String accountId, int start, int size) {
		String sql = "select vo.money,vo.bankcard,vo.openbank,vo.bankname,vo.create_time,vo.status,vo.actualMoney,vo.remark,vo.verifytime from mem_cashapplication as vo where vo.account_id = :accountId order by create_time desc limit :start,:size";
		String hqlCount = " from CashApplication vo where vo.account.id = '" + accountId + "'"; 
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
