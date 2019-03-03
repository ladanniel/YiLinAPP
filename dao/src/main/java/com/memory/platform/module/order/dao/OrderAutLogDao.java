package com.memory.platform.module.order.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月16日 下午8:14:49 
* 修 改 人： 
* 日   期： 
* 描   述：  订单操作记录 － dao
* 版 本 号：  V1.0
 */
@Repository
public class OrderAutLogDao extends BaseDao<OrderAutLog> {

	/**
	* 功能描述：操作记录分页 
	* 输入参数:  @param log
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月16日下午8:16:21
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getPage(OrderAutLog log,int start,int limit){
		StringBuffer hql = new StringBuffer(" from OrderAutLog log where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		if(null != log.getStart()){
			where.append(" and log.create_time between :start and :end");
			if(log.getStart().getTime() ==  log.getEnd().getTime()){
				map.put("start", log.getStart());
				Calendar   calendar   =   new   GregorianCalendar(); 
			    calendar.setTime(log.getEnd()); 
			    calendar.add(calendar.DATE,1);
				map.put("end", calendar.getTime());
			}else{
				map.put("start", log.getStart());
				map.put("end", log.getEnd());
			}
		}
		if(StringUtil.isNotEmpty(log.getSearch())){
			where.append(" and (log.robOrderRecord.account.name like :name or "
					+ "log.robOrderRecord.companyName like :companyName or "
					+ "log.auditPerson like :auditPerson or "
					+ "log.robOrderRecord.account.account = :account or "
					+ "log.robOrderRecord.account.phone = : phone)");
			map.put("name", "%" + log.getSearch() + "%");
			map.put("companyName", "%" + log.getSearch() + "%");
			map.put("auditPerson", "%" + log.getSearch() + "%");
			map.put("account", log.getSearch());
			map.put("phone", log.getSearch());
		}
		where.append(" order by log.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	public List<Map<String, Object>> getListForMap(String orderId){
		StringBuffer sql = new StringBuffer("select * from order_aut_log as log where log.rob_order_record_id = :orderId and log.rob_orderconfirm_id is null");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		sql.append(" order by log.create_time");
		return this.getListBySQLMap(sql.toString(), map);
	}
	
	public List<Map<String, Object>> getListForMap(String orderId,String order){
		StringBuffer sql = new StringBuffer("select * from order_aut_log as log where log.rob_order_record_id = :orderId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		if(StringUtils.isEmpty(order)){
			sql.append(" order by log.create_time");
		}else{
			sql.append(" order by log.create_time " + order);
		}
		return this.getListBySQLMap(sql.toString(), map);
	}
	
	
	/**
	* 功能描述： 根据属性获取操作记录
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月16日下午8:18:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：OrderAutLog
	 */
	public OrderAutLog getLog(OrderAutLog log){
		StringBuffer hql = new StringBuffer(" from OrderAutLog log where 1 = 1 ");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(log.getRobOrderConfirmId())){
			where.append(" and log.robOrderConfirmId = :robOrderConfirmId");
			map.put("robOrderConfirmId", log.getRobOrderConfirmId());
		}
		if(log.getConfirmStatus()!=null){
			where.append(" and log.confirmStatus = :confirmStatus");
			map.put("confirmStatus", log.getConfirmStatus());
		}
		
		where.append(" and log.specialType is null");
		return this.getObjectByColumn(hql.append(where).toString(), map);
	}
		
	/**
	* 功能描述： 根据抢单查看该抢单操作记录
	* 输入参数:  @param orderId   抢单单号ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月14日下午4:23:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getOrderLog(String orderId){
		String sql = "SELECT a.*,b.`name` AS company_name,c.`name` AS company_type_name"
				+ " FROM ( "
				+ " SELECT log.`title`, log.`audit_person`, log.`afterStatus`, log.`remark`, member.`company_id`,log.create_time"
				+ " FROM `order_aut_log` AS log"
				+ " LEFT JOIN `mem_account` AS member ON member.`id`= log.`audit_person_id`"
				+ " WHERE log.`rob_order_record_id`= :orderId"
				+ " AND log.rob_orderconfirm_id is null) AS a LEFT JOIN `sys_company` AS b ON b.`id` = a.company_id LEFT JOIN `sys_company_type` AS c ON c.`id` = b.`company_type_id`"
				+ " ORDER BY a.create_time DESC";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderId", orderId);
		return this.getListBySQLMap(sql, map);
	}
	/**
	 * 获取订单日志
	 * @param robConfirmId
	 * @return
	 */
	public List<OrderAutLog> getOrderlog(String robConfirmId){
		StringBuffer hql = new StringBuffer(" from OrderAutLog log where robOrderConfirmId = :robOrderConfirmId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("robOrderConfirmId", robConfirmId);
		
		return this.getListByHql(hql.toString(), map);
	}
	/**
	* 功能描述： 根据抢单查看该抢单操作记录
	* 输入参数:  @param orderId   抢单单号ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月14日下午4:23:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getOrderlogListMap(String robConfirmId){
		String sql = "SELECT  c.`name` as companyName,"
				+ "t.`name` as companyTypeName,"
				+ "log.`title` as title,"
				+ "log.audit_person as userName,"
				+ "log.create_time,"
				+ "log.confirm_status as confirmStatus,"
				+ "log.special_status as specialStatus,"
				+ "log.remark as remark"
				+ " from order_aut_log log"
				+ " LEFT JOIN mem_account u on log.audit_person_id = u.id"
				+ " LEFT JOIN sys_company c on c.id = u.company_id"
				+ " LEFT JOIN sys_company_type t on t.id = c.company_type_id"
				+ " WHERE log.rob_orderconfirm_id=:robConfirmId order by log.create_time desc";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("robConfirmId", robConfirmId);
		return this.getListBySQLMap(sql, map);
	}
	
	
	/**
	* 功能描述： 根据抢单查看该抢单操作记录
	* 输入参数:  @param orderId   抢单单号ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年8月14日下午4:23:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getOrderConfirmLogListMap(String robConfirmId){
		String sql = "SELECT  c.`name` as company_name,"
				+ "t.`name` as company_type_name,"
				+ "log.`title` as title,"
				+ "log.audit_person as audit_person,"
				+ "log.create_time,"
				+ "log.confirm_status as afterStatus,"
				+ "log.special_status as specialStatus,"
				+ "log.remark as remark"
				+ " from order_aut_log log"
				+ " LEFT JOIN mem_account u on log.audit_person_id = u.id"
				+ " LEFT JOIN sys_company c on c.id = u.company_id"
				+ " LEFT JOIN sys_company_type t on t.id = c.company_type_id"
				+ " WHERE log.rob_orderconfirm_id=:robConfirmId order by log.create_time desc";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("robConfirmId", robConfirmId);
		return this.getListBySQLMap(sql, map);
	}
}
