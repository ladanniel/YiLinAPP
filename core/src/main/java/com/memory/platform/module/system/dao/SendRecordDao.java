package com.memory.platform.module.system.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.SelfDescribing;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 创 建 人： longqibo 日 期： 2016年5月31日 下午2:20:30 修 改 人： 日 期： 描 述： 短信发送记录dao 版 本 号：
 * V1.0
 */
@Repository
public class SendRecordDao extends BaseDao<SendRecord> {

	public List<SendRecord> getRecordPage(SendRecord sendRecord, int start,
			int limit) {
		StringBuffer hql = new StringBuffer(
				" from SendRecord sendRecord where 1 = 1  ");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();
		
		where.append(" and sendRecord.status = :status and sendRecord.send_type = :sendType ");
		
		map.put("status", sendRecord.getStatus());
		map.put("sendType", sendRecord.getSend_type());
	 
		Query query = getSession().createQuery(hql.toString() + where.toString() );
		 
		query = super.setParameter(query, map);  
		query.setFirstResult(start*limit);
		query.setMaxResults(limit);
		return query.list();
	}
	public Map<String, Object> getMyPage(SendRecord sendRecord, int start,
			int limit) { 
		Map<String, Object> map = new HashMap<String, Object>();
		String where ="";
		if(StringUtil.isNotEmpty( sendRecord.getReceive_user_id())) {
			where += " and receive_user_id = :userID ";
			map.put("userID", sendRecord.getReceive_user_id());
		}
		String strSql = " select sys_send_record.create_time, sys_send_record.`status` ,"
				+ " sys_send_record.id , sys_send_record.content ,sys_send_record.extend_data,sys_send_record.title "
				+ " from sys_send_record  where 1=1 " + where
				+ " ORDER BY sys_send_record.create_time desc  ";
		
		 
		return this.getPageSqlListMap(strSql, map, start, limit);
		 
	}
	/**
	 * 功能描述： 发送短信记录列表 输入参数: @param sendMessage 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年5月31日下午2:30:44 修 改 人:
	 * 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getPage(SendRecord sendRecord, int start,
			int limit) {
		StringBuffer hql = new StringBuffer(
				" from SendRecord sendRecord where 1 = 1");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer where = new StringBuffer();

		if (null != sendRecord.getStart()) {
			where.append(" and sendRecord.create_time between :start and :end");
			if (sendRecord.getStart().getTime() == sendRecord.getEnd()
					.getTime()) {
				map.put("start", sendRecord.getStart());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(sendRecord.getEnd());
				calendar.add(Calendar.DATE, 1);
				map.put("end", calendar.getTime());
			} else {
				map.put("start", sendRecord.getStart());
				map.put("end", sendRecord.getEnd());
			}
		}
		if (null != sendRecord.getStatus()) {
			where.append(" and sendRecord.status = :status");
			map.put("status", sendRecord.getStatus());
		}
		if (StringUtil.isNotEmpty(sendRecord.getSearch())) {
			where.append(" and (sendRecord.sendMessage.name like :name or "
					+ "sendRecord.phone = :phone or "
					+ "sendRecord.sendPoint = :sendPoint)");
			map.put("name", "%" + sendRecord.getSearch() + "%");
			map.put("phone", sendRecord.getSearch());
			map.put("sendPoint", sendRecord.getSearch());
		}
		where.append(" order by sendRecord.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

}
