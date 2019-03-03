package com.memory.platform.module.order.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.CompanyType;
import com.memory.platform.global.DateUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月2日 下午3:45:46 
* 修 改 人： 
* 日   期： 
* 描   述： 订单统计
* 版 本 号：  V1.0
 */
@Repository
public class RobOrderConfirmStatisticsDao extends BaseDao<RobOrderConfirm> {
	
	/**
	* 功能描述：     获取订单状态条数
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param payPassword
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月6日下午2:13:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public List<Map<String, Object>> getRobOrderConfirmStatusCount(RobOrderConfirm robOrderConfirm){
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		//0:等待装货、1:运输中 2:送达   3:回执发还中  4:送还回执中 5:订单完结
		StringBuffer sql = new StringBuffer("SELECT "+
						"COUNT( CASE WHEN a.`status` = 0 THEN 1 ELSE NULL END  ) AS `待装货`,"+
						"COUNT( CASE WHEN a.`status` = 1 THEN 1 ELSE NULL END  ) AS `运输中`,"+
						"COUNT( CASE WHEN a.`status` = 2 THEN 1 ELSE NULL END  ) AS `送达`,"+
						"COUNT( CASE WHEN a.`status` = 3 THEN 1 ELSE NULL END  ) AS `回执发还中`,"+
						"COUNT( CASE WHEN a.`status` = 4 THEN 1 ELSE NULL END  ) AS `送还回执中`,"+
						"COUNT( CASE WHEN a.`status` = 5 THEN 1 ELSE NULL END  ) AS `订单完结`"+
						"FROM rob_order_confirm a LEFT JOIN rob_order_record r on a.rob_order_id = r.id where 1=1");
		 
		if(StringUtils.isNoneBlank(robOrderConfirm.getRobbedCompanyId())){
				where.append(" AND r.robbed_company_id =:robbedCompanyId");
				map.put("robbedCompanyId",robOrderConfirm.getRobbedCompanyId());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getCompanyName())){
			where.append(" AND r.companyName =:companyName");
			map.put("companyName",robOrderConfirm.getCompanyName());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getRobbedAccountId())){
			where.append(" AND r.robbed_account_id =:robbed_account_id");
			map.put("robbed_account_id",robOrderConfirm.getRobbedAccountId());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getTurckUserId())){
			where.append(" AND a.turck_user_id =:turckUserId");
			map.put("turckUserId",robOrderConfirm.getTurckUserId());
		}
		return this.getListBySQLMap(sql.append(where).toString(), map);
	}
	
	
	/**
	* 功能描述：    运单量统计
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param payPassword
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月6日下午2:13:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public Map<String, Object> getRobOrderConfirm(Date strDate,Date endDate,RobOrderConfirm robOrderConfirm,SimpleDateFormat sdf1){
		List<String> dateList = new ArrayList<String>(); //日期数组
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf1 = sdf1==null?sdf:sdf1;
		Calendar calendar=Calendar.getInstance();
		int day = DateUtil.daysBetween(strDate,endDate);
		StringBuffer sql = new StringBuffer("SELECT ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<=day;i++){
			calendar.setTime(strDate);
			calendar.add(Calendar.DATE,i);
			Date date=calendar.getTime();
			dateList.add(sdf1.format(date));
			sql.append("COUNT( CASE WHEN DATE_FORMAT(confirm_data,'%Y-%m-%d')='"+sdf.format(date)+"' THEN 1 ELSE NULL END  ) AS `"+sdf.format(date)+"`,");
		}
		
		sql = new StringBuffer(sql.substring(0, sql.length()-1));
		
		sql.append(" FROM rob_order_confirm a LEFT JOIN rob_order_record r on a.rob_order_id = r.id where 1=1 ");
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getRobbedCompanyId())){
			where.append(" AND r.robbed_company_id =:robbedCompanyId");
			map.put("robbedCompanyId",robOrderConfirm.getRobbedCompanyId());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getCompanyName())){
			where.append(" AND r.companyName =:companyName");
			map.put("companyName",robOrderConfirm.getCompanyName());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getRobbedAccountId())){
			where.append(" AND r.robbed_account_id =:robbed_account_id");
			map.put("robbed_account_id",robOrderConfirm.getRobbedAccountId());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getTurckUserId())){
			where.append(" AND a.turck_user_id =:turckUserId");
			map.put("turckUserId",robOrderConfirm.getTurckUserId());
		}
		
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("list", this.getListBySQL(sql.append(where).toString(), map));
		dataMap.put("dateList", dateList);
		
		return dataMap;
	}
	
	/**
	* 功能描述：    运单t重量统计
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param payPassword
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月6日下午2:13:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	public Map<String, Object> getRobOrderConfirmWeight(Date strDate,Date endDate,RobOrderConfirm robOrderConfirm,SimpleDateFormat sdf1){
		List<String> dateList = new ArrayList<String>(); //日期数组
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf1 = sdf1==null?sdf:sdf1;
		Calendar calendar=Calendar.getInstance();
		int day = DateUtil.daysBetween(strDate,endDate);
		StringBuffer sql = new StringBuffer("SELECT ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<=day;i++){
			calendar.setTime(strDate);
			calendar.add(Calendar.DATE,i);
			Date date=calendar.getTime();
			dateList.add(sdf1.format(date));
			sql.append("SUM( CASE WHEN DATE_FORMAT(confirm_data,'%Y-%m-%d')='"+sdf.format(date)+"' THEN total_weight ELSE 0 END  ) AS `"+sdf.format(date)+"`,");
		}
		
		sql = new StringBuffer(sql.substring(0, sql.length()-1));
		
		sql.append(" FROM rob_order_confirm a LEFT JOIN rob_order_record r on a.rob_order_id = r.id where 1=1 ");
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getRobbedCompanyId())){
			where.append(" AND r.robbed_company_id =:robbedCompanyId");
			map.put("robbedCompanyId",robOrderConfirm.getRobbedCompanyId());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getCompanyName())){
			where.append(" AND r.companyName =:companyName");
			map.put("companyName",robOrderConfirm.getCompanyName());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getRobbedAccountId())){
			where.append(" AND r.robbed_account_id =:robbed_account_id");
			map.put("robbed_account_id",robOrderConfirm.getRobbedAccountId());
		}
		
		if(StringUtils.isNoneBlank(robOrderConfirm.getTurckUserId())){
			where.append(" AND a.turck_user_id =:turckUserId");
			map.put("turckUserId",robOrderConfirm.getTurckUserId());
		}
		
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("list", this.getListBySQL(sql.append(where).toString(), map));
		dataMap.put("dateList", dateList);
		
		return dataMap;
	}
	
	/**
	 * 运单状态数统计
	 * @param account
	 * @return
	 */
	public Map<String, Object> getRobOrderConfirmCount(Account account){
		Map<String, Object> map = new HashMap<String, Object>();
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		StringBuffer where = new StringBuffer();
		StringBuffer sql = new StringBuffer("SELECT COUNT( CASE WHEN r.`status` = 0 THEN 1 ELSE NULL END  ) AS `receivingCount`," //等待装货
				   + "COUNT( CASE WHEN r.`status` != 5 and  r.`status` != 6 THEN 1 ELSE NULL END  ) AS `toolCount` ,"
				   + "COUNT(r.id) AS allToolCount,"
				   + "COUNT(r.total_weight) AS allToolWeight,"
				   + "SUM(r.total_weight+IF(r.`status`=0,0,-r.total_weight)) as receivingWeight," //等待装货总重量
				   + "COUNT( CASE WHEN r.`status` = 1 THEN 1 ELSE NULL END  ) AS `transitCount`,"//运输中数
				   + "SUM(r.total_weight+IF(r.`status`=1,0,-r.total_weight)) as transitWeight,"//运输中总重量
				   + "COUNT( CASE WHEN r.`status` = 2 THEN 1 ELSE NULL END  ) AS `deliveredCount`,"//送达数
				   + "SUM(r.total_weight+IF(r.`status`=2,0,-r.total_weight)) as deliveredWeight,"//送达重量
				   + "COUNT( CASE WHEN r.`status` = 3 THEN 1 ELSE NULL END  ) AS `receiptCount`,"//回执发还中数
				   + "SUM(r.total_weight+IF(r.`status`=3,0,-r.total_weight)) as receiptWeight,"//回执发还中重量
				   + "COUNT( CASE WHEN r.`status` = 4 THEN 1 ELSE NULL END  ) AS `confirmreceiptCount`,"//送还回执中数
				   + "SUM(r.total_weight+IF(r.`status`=4,0,-r.total_weight)) as confirmreceiptWeight,"//送还回执中重量
				   + "COUNT( CASE WHEN r.`status` = 5 THEN 1 ELSE NULL END  ) AS `ordercompletionCount`,"//订单完结数
				   + "SUM(r.total_weight+IF(r.`status`=5,0,-r.total_weight)) as ordercompletionWeight, "//订单完结重量
				   + "COUNT( CASE WHEN r.`status` = 5 THEN 1 ELSE NULL END  ) AS `singlepinCount`,"//销单数
				   + "SUM(r.total_weight+IF(r.`status`=5,0,-r.total_weight)) as singlepinWeight, "//销单重量
				   + "COUNT( CASE WHEN r.`special_type` = 1 and special_status!=2 THEN 1 ELSE NULL END  ) AS `emergencyCount`,"//急救数
				   + "SUM(r.total_weight+IF(r.`special_type`=1 and special_status!=2 ,0,-r.total_weight)) as emergencyWeight, "//急救重量
				   + "COUNT( CASE WHEN r.`special_type` = 1 and special_status!=2 THEN 1 ELSE NULL END  ) AS `arbitrationCount`,"//仲裁数
				   + "SUM(r.total_weight+IF(r.`special_type`=2 and special_status!=2 ,0,-r.total_weight)) as arbitrationWeight, "//仲裁重量
				   + "COUNT( CASE WHEN to_days(r.create_time) = to_days(now()) THEN 1 ELSE NULL END  ) AS toDayCount,"//今天成功抢单数量
				   + "SUM(r.total_weight+IF(to_days(r.create_time) = to_days(now()),0,-r.total_weight)) as toDayWeight"//今天成功抢单重量
				   + " from rob_order_confirm r LEFT JOIN rob_order_record a on a.id = r.rob_order_id where 1=1");
				
				if(omcpanyTypeName.equals("车队")||omcpanyTypeName.equals("个人司机")){
					where.append(" AND a.companyName =:companyName");
					map.put("companyName",account.getCompany().getName());
				}
				
				if(omcpanyTypeName.equals("货主")){
					where.append(" AND a.robbed_company_id =:robbedcompanyId");
					map.put("robbedcompanyId",account.getCompany().getId());
				}
	
		 return this.getSqlMap(sql.append(where).toString(), map);
	}
	
	
	/**
	* 功能描述：     通过月分查运单数
	* 输入参数:  @param accountId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午1:00:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getAllConfirmMonthCount(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货  2:锁定处理－正在处理  3:退回  4:通过   5:作废**/
		String date_format = "%Y-%m";
		if(dateType.equals("month")){
			date_format = "%Y-%m";
		}else if(dateType.equals("day")){
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "COUNT( CASE WHEN date_format(a.create_time,'"+date_format+"') = '"+string+"' THEN 1 ELSE NULL END ) AS `"+string+"`,";
		}
		sql = sql.substring(0, sql.length()-1);
		sql+= " FROM rob_order_confirm as a LEFT JOIN rob_order_record r on r.id = a.rob_order_id WHERE 1=1";
		
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		
		if(omcpanyTypeName.equals("车队")||omcpanyTypeName.equals("个人司机")){
			sql+=" AND r.companyName =:companyName";
			map.put("companyName",account.getCompany().getName());
		}
		
		if(omcpanyTypeName.equals("货主")){
			sql+=" AND r.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId",account.getCompany().getId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getTurckUserId())){
			sql+=" AND r.account_id =:turckUserId";
			map.put("turckUserId",robOrderRecord.getTurckUserId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getUserID())){
			sql+=" AND r.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId",robOrderRecord.getUserID());
		}
		
		return this.getListBySQL(sql, map);
	}
	
	/**
	* 功能描述：     通过月分查完结运单数
	* 输入参数:  @param accountId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午1:00:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getConfirmCompletionMonthCount(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货  2:锁定处理－正在处理  3:退回  4:通过   5:作废**/
		String date_format = "%Y-%m";
		if(dateType.equals("month")){
			date_format = "%Y-%m";
		}else if(dateType.equals("day")){
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "COUNT( CASE WHEN date_format(a.create_time,'"+date_format+"') = '"+string+"' THEN 1 ELSE NULL END ) AS `"+string+"`,";
		}
		sql = sql.substring(0, sql.length()-1);
		sql+= " FROM rob_order_confirm as a LEFT JOIN rob_order_record r on r.id = a.rob_order_id WHERE a.`status` = 5";
		
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		
		if(omcpanyTypeName.equals("车队")||omcpanyTypeName.equals("个人司机")){
			sql+=" AND r.companyName =:companyName";
			map.put("companyName",account.getCompany().getName());
		}
		
		if(omcpanyTypeName.equals("货主")){
			sql+=" AND r.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId",account.getCompany().getId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getTurckUserId())){
			sql+=" AND r.account_id =:turckUserId";
			map.put("turckUserId",robOrderRecord.getTurckUserId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getUserID())){
			sql+=" AND r.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId",robOrderRecord.getUserID());
		}
		
		return this.getListBySQL(sql, map);
	}
	
	
	/**
	* 功能描述：     通过月分查运单重量
	* 输入参数:  @param accountId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午1:00:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getAllConfirmMonthWeight(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货  2:锁定处理－正在处理  3:退回  4:通过   5:作废**/
		String date_format = "%Y-%m";
		if(dateType.equals("month")){
			date_format = "%Y-%m";
		}else if(dateType.equals("day")){
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "SUM(a.total_weight+IF(date_format(a.create_time,'"+date_format+"') = '"+string+"',0,-a.total_weight)) AS `"+string+"`,";
		}
		sql = sql.substring(0, sql.length()-1);
		sql+= " FROM rob_order_confirm as a LEFT JOIN rob_order_record r on r.id = a.rob_order_id WHERE 1=1";
		
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		
		if(omcpanyTypeName.equals("车队")||omcpanyTypeName.equals("个人司机")){
			sql+=" AND r.companyName =:companyName";
			map.put("companyName",account.getCompany().getName());
		}
		
		if(omcpanyTypeName.equals("货主")){
			sql+=" AND r.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId",account.getCompany().getId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getTurckUserId())){
			sql+=" AND r.account_id =:turckUserId";
			map.put("turckUserId",robOrderRecord.getTurckUserId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getUserID())){
			sql+=" AND r.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId",robOrderRecord.getUserID());
		}
		
		return this.getListBySQL(sql, map);
	}
	
	
	/**
	* 功能描述：     通过月分查完结运单重量
	* 输入参数:  @param accountId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午1:00:54
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	public List getConfirmCompletionMonthWeight(List<String> months,Account account,String dateType,RobOrderRecord robOrderRecord){
		Map<String, Object> map = new HashMap<String, Object>();
		/** 1:申请发货  2:锁定处理－正在处理  3:退回  4:通过   5:作废**/
		String date_format = "%Y-%m";
		if(dateType.equals("month")){
			date_format = "%Y-%m";
		}else if(dateType.equals("day")){
			date_format = "%Y-%m-%d";
		}
		String sql = "SELECT  ";
		for (String string : months) {
			sql += "SUM(a.total_weight+IF(date_format(a.create_time,'"+date_format+"') = '"+string+"',0,-a.total_weight)) AS `"+string+"`,";
		}
		sql = sql.substring(0, sql.length()-1);
		sql+= " FROM rob_order_confirm as a LEFT JOIN rob_order_record r on r.id = a.rob_order_id WHERE a.`status` = 5";
		
		CompanyType companyType = account.getCompany().getCompanyType();
		String omcpanyTypeName = companyType.getName();
		
		if(omcpanyTypeName.equals("车队")||omcpanyTypeName.equals("个人司机")){
			sql+=" AND r.companyName =:companyName";
			map.put("companyName",account.getCompany().getName());
		}
		
		if(omcpanyTypeName.equals("货主")){
			sql+=" AND r.robbed_company_id =:robbedcompanyId";
			map.put("robbedcompanyId",account.getCompany().getId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getTurckUserId())){
			sql+=" AND r.account_id =:turckUserId";
			map.put("turckUserId",robOrderRecord.getTurckUserId());
		}
		
		if(org.apache.commons.lang3.StringUtils.isNotBlank(robOrderRecord.getUserID())){
			sql+=" AND r.robbed_account_id =:robbedAccountId";
			map.put("robbedAccountId",robOrderRecord.getUserID());
		}
		
		return this.getListBySQL(sql, map);
	}
	
	
	/**
	* 功能描述： 统计运单排行
	* 输入参数:  @param ranking
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午3:53:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getConfirmRankingStatistics(int ranking,String type){
		if(ranking == 0){
			ranking = 10;
		}
		String sql = "SELECT count(*) AS count,"
				+ "sum(c.total_weight) AS weight,"
				+ "r.companyName as truckCompany,"
				+ "(SELECT name from sys_company WHERE id=r.robbed_company_id) as company"
				+ " from rob_order_confirm c"
				+ " LEFT JOIN rob_order_record r on r.id = c.rob_order_id"
				+ " LEFT JOIN sys_company com on c.id = r.robbed_company_id"
				+ " GROUP BY "+type+" ORDER BY count desc LIMIT 0,"+ranking;
		return this.getListBySQLMap(sql, new HashMap<String, Object>());
	}
	
}
