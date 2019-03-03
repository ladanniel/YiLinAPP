package com.memory.platform.module.capital.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.module.capital.dao.TradeSequenceDao;
import com.memory.platform.module.capital.service.ITradeSequenceService;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月15日 下午4:39:21 
* 修 改 人： 
* 日   期： 
* 描   述： 交易服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class TradeSequenceService implements ITradeSequenceService {

	@Autowired
	private TradeSequenceDao tradeSequenceDao;

	@Override
	public List<TradeSequence> getAll(TradeSequence tradeSequence) {
		return tradeSequenceDao.getAll(tradeSequence);
	}

	@Override
	public TradeSequence getTradeSequence(String id) {
		return tradeSequenceDao.getObjectById(TradeSequence.class, id);
	}

	@Override
	public void updateSequence(TradeSequence tradeSequence1, TradeSequence tradeSequence2) {
		if(null != tradeSequence1){
			tradeSequenceDao.update(tradeSequence1);
		}
		if(null != tradeSequence2){
			tradeSequenceDao.update(tradeSequence2);
		}
	}

	@Override
	public TradeSequence getTradeSequenceByNo(TradeSequence tradeSequence) {
		return tradeSequenceDao.getTradeSequenceByNo(tradeSequence);
	}
}
