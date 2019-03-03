package com.memory.platform.module.capital.service;

import java.util.List;

import com.memory.platform.entity.capital.TradeSequence;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月15日 下午4:37:49 
* 修 改 人： 
* 日   期： 
* 描   述： 交易序列服务接口
* 版 本 号：  V1.0
 */
public interface ITradeSequenceService {

	/**
	* 功能描述： 获取交易列表
	* 输入参数:  @param tradeSequence
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月15日下午8:33:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	List<TradeSequence> getAll(TradeSequence tradeSequence);
	
	/**
	* 功能描述： 根据id获取序列
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午6:07:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：TradeSequence
	 */
	TradeSequence getTradeSequence(String id);
	
	/**
	* 功能描述： 更新序列
	* 输入参数:  @param tradeSequence1
	* 输入参数:  @param tradeSequence2
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午6:09:26
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateSequence(TradeSequence tradeSequence1,TradeSequence tradeSequence2);
	
	/**
	* 功能描述： 根据编号获取序列
	* 输入参数:  @param no
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午6:16:11
	* 修 改 人: 
	* 日    期: 
	* 返    回：TradeSequence
	 */
	TradeSequence getTradeSequenceByNo(TradeSequence tradeSequence);
}
