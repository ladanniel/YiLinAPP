package com.memory.platform.module.capital.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.entity.member.Account;
import com.memory.platform.module.global.dao.BaseDao;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月15日 下午4:36:39 
* 修 改 人： 
* 日   期： 
* 描   述： 交易序列dao
* 版 本 号：  V1.0
 */
@Repository
public class TradeSequenceDao extends BaseDao<TradeSequence> {

	/**
	* 功能描述： 获取交易列表
	* 输入参数:  @param tradeSequence
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午5:22:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<TradeSequence>
	 */
	public List<TradeSequence> getAll(TradeSequence tradeSequence){
		StringBuffer hql = new StringBuffer(" from TradeSequence tradeSequence where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		where.append(" and tradeSequence.account = :account");
		map.put("account", tradeSequence.getAccount());
		if(null != tradeSequence.getHasCash()){
			where.append(" and tradeSequence.hasCash = :hasCash");
			map.put("hasCash", tradeSequence.getHasCash());
		}
		if(null != tradeSequence.getHasRecharge()){
			where.append(" and tradeSequence.hasRecharge = :recharge");
			map.put("recharge", tradeSequence.getHasRecharge());
		}
		if(null != tradeSequence.getHasTransfer()){
			where.append(" and tradeSequence.hasTransfer = :transfer");
			map.put("transfer", tradeSequence.getHasTransfer());
		}
		where.append(" order by tradeSequence.sequenceNo");
		return this.getListByHql(hql.append(where).toString(), map);
	}
	
	/**
	* 功能描述： 获取总数
	* 输入参数:  @param account
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午4:41:50
	* 修 改 人: 
	* 日    期: 
	* 返    回：int
	 */
	public int getTotal(Account account){
		String hql = " from TradeSequence tradeSequence where tradeSequence.account = :account";
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("account", account);
		return this.getCountHql(hql, map);
	}
	
	/**
	* 功能描述： 根据排序号获取序列
	* 输入参数:  @param no
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午6:15:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：TradeSequence
	 */
	public TradeSequence getTradeSequenceByNo(TradeSequence tradeSequence){
		StringBuffer hql = new StringBuffer(" from TradeSequence tradeSequence where  tradeSequence.account = :account");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("account", tradeSequence.getAccount());
		where.append(" and tradeSequence.sequenceNo = :no");
		map.put("no", tradeSequence.getSequenceNo());
		return this.getListByHql(hql.append(where).toString(), map).get(0);
	}
	
	/**
	* 功能描述： 根据来源id查找序列
	* 输入参数:  @param sourceId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午7:55:19
	* 修 改 人: 
	* 日    期: 
	* 返    回：TradeSequence
	 */
	public TradeSequence getTradeSequenceBySourceId(String sourceId){
		StringBuffer hql = new StringBuffer(" from TradeSequence tradeSequence where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		where.append(" and tradeSequence.sourceId = :sourceId");
		map.put("sourceId", sourceId);
		return this.getListByHql(hql.append(where).toString(), map).get(0);
	}
	
	/**
	* 功能描述： 获取大于no序列的列表
	* 输入参数:  @param sequence
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午8:01:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<TradeSequence>
	 */
	public List<TradeSequence> getListByNo(TradeSequence sequence){
		StringBuffer hql = new StringBuffer(" from TradeSequence tradeSequence where 1 = 1");
		StringBuffer where = new StringBuffer();
		Map<String, Object> map = new HashMap<String,Object>();
		where.append(" and tradeSequence.account = :account");
		map.put("account", sequence.getAccount());
		where.append(" and tradeSequence.sequenceNo > :no");
		map.put("no", sequence.getSequenceNo());
		return this.getListByHql(hql.append(where).toString(), map);
	}
	
}
