package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.Feedback;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月24日 下午3:56:38 
* 修 改 人： 
* 日   期： 
* 描   述： 意见反馈 - dao
* 版 本 号：  V1.0
 */
@Repository
public class FeedbackDao extends BaseDao<Feedback> {

	public Map<String, Object> getPage(Feedback feedback,int start,int limit){
		StringBuffer hql = new StringBuffer(" from Feedback vo where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtil.isNotEmpty(feedback.getSearch())){
			where.append(" and (vo.account.phone like :phone or "
					+ "vo.account.name like :name "
					+ "vo.account.account like :account)");
			map.put("phone", "%" + feedback.getSearch() + "%");
			map.put("name", "%" + feedback.getSearch() + "%");
			map.put("account", "%" + feedback.getSearch() + "%");
		}
		where.append(" order by vo.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}
}
