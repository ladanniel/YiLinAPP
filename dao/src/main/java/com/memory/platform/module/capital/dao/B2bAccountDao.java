package com.memory.platform.module.capital.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.memory.platform.entity.capital.B2bAccount;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.sys.MenuApp;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 创 建 人： B2B
* 日    期： 2018年8月28日 下午3:32:02 
* 描   述： B2B账号
* 版 本 号：  V1.0
 */
@Repository
public class B2bAccountDao extends BaseDao<B2bAccount> {

	
	/**
	 * 保存账号(开通B2B支付)
	 * 当开启B2B银行支付时自动为用户注册生成本账号信息
	 * 提现与充值用该账号完成
	 * */
	public void saveB2bAccount(B2bAccount b2bAccount){
		this.save(b2bAccount);
	}
	
	/**
	 * 根据子账户号查询子账户信息
	 * 
	 * */
	
	
	
	/**
	 * 根据B2B摊位号查询账号信息
	 * 
	 * */
	public B2bAccount getB2bAccountByBoothNo(String boothNo){
		String hql = " from B2bAccount b2bAccount where b2bAccount.boothNo = :boothNo";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("boothNo", boothNo);
		List<B2bAccount> list = this.getListByHql(hql, map);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
}
