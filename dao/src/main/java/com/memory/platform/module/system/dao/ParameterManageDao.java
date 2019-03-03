package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月23日 下午3:08:27 
* 修 改 人： 
* 日   期： 
* 描   述： 参数dao
* 版 本 号：  V1.0
 */
@Repository 
public class ParameterManageDao extends BaseDao<ParameterManage> {

	public Map<String, Object> getPage(ParameterManage parameterManage,int start, int limit){
		StringBuffer hql = new StringBuffer(" from ParameterManage vo where 1 = 1");
		Map<String, Object> map = new HashMap<String,Object>();
		StringBuffer where = new StringBuffer();
		where.append(" order by vo.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
	
	public ParameterManage getTypeInfo(ParaType type){
		StringBuffer hql = new StringBuffer(" from ParameterManage vo where vo.key = :type");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("type", type);
		return this.getListByHql(hql.toString(), map).get(0);
	}
}
