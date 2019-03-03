package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.Bank;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月24日 下午3:59:26 
* 修 改 人： 
* 日   期： 
* 描   述： 银行 - dao
* 版 本 号：  V1.0
 */
@Repository
public class BankDao extends BaseDao<Bank> {

	public Map<String, Object> getPage(Bank bank,int start,int limit){
		StringBuffer hql = new StringBuffer(" from Bank bank where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtil.isNotEmpty(bank.getSearch())){
			where.append(" and (bank.cnName like :cnName or "
					+ "bank.shortName like :shortName)");
			map.put("cnName", "%" + bank.getSearch() + "%");
			map.put("shortName", "%" + bank.getSearch() + "%");
		}
		where.append(" order by bank.create_time desc");
		return this.getPage(hql.append(where).toString(), map, start, limit);
	}

	public Bank getBankByShortName(String shortName){
		String hql = " from Bank bank where bank.shortName = :shortName";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("shortName", shortName);
		List<Bank> list = this.getListByHql(hql, map);
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	
	public Bank getBankByCnName(String cnName){
		String hql = " from Bank bank where bank.cnName = :cnName";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("cnName", cnName);
		List<Bank> list = this.getListByHql(hql, map);
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}
}
