package com.memory.platform.module.aut.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 下午5:09:18 
* 修 改 人： 
* 日   期： 
* 描   述： 身份证信息DAO类
* 版 本 号：  V1.0
 */
@Repository
public class IdcardDao extends BaseDao<Idcard> {
    
	/**
	* 功能描述： 通过身份证号码查询身份证信息
	* 输入参数:  @param no
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年5月5日下午7:43:30
	* 修 改 人: 
	* 日    期: 
	* 返    回：Idcard
	 */
	public Idcard getIdcardByNo(String no,String id){
		String hql = " from Idcard idcard where idcard.idcard_no=:idcard_no ";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("idcard_no", no);
		if(!StringUtils.isEmpty(id)){
			hql += " and  idcard.id != :id";
			map.put("id", id);
		} 
		Idcard idcard = this.getObjectByColumn(hql, map);
		return idcard;
	}
	
	
	public Idcard getAccountId(String id){
		String hql = " from Idcard idcard where idcard.account_id=:account_id ";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("account_id", id);
		Idcard idcard = this.getObjectByColumn(hql, map);
		return idcard;
	}
}
