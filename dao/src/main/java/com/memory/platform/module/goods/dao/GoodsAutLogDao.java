package com.memory.platform.module.goods.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.goods.GoodsAutLog;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 下午6:28:21 
* 修 改 人： 
* 日   期： 
* 描   述： 货物操作日志 － dao
* 版 本 号：  V1.0
 */
@Repository
public class GoodsAutLogDao extends BaseDao<GoodsAutLog> {

	
	/**
	* 功能描述：货物操作日志分页 
	* 输入参数:  @param log
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月14日下午6:28:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> getPage(GoodsAutLog log,int start,int limit){
		StringBuffer hql = new StringBuffer(" from GoodsAutLog log where 1 = 1");
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
			where.append(" and (log.goodsBasic.account.name like :name or "
					+ "log.goodsBasic.companyName like :companyName or "
					+ "log.auditPerson like :auditPerson)");
			map.put("name", "%" + log.getSearch() + "%");
			map.put("companyName", "%" + log.getSearch() + "%");
			map.put("auditPerson", "%" + log.getSearch() + "%");
		}
		where.append(" order by log.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	public List<Map<String, Object>> getListForMap(String goodsId){
		StringBuffer sql = new StringBuffer("select * from goods_aut_log as log where log.goods_basic_id = :goodsId");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("goodsId", goodsId);
		sql.append(" order by log.create_time");
		return this.getListBySQLMap(sql.toString(), map);
	}
}
