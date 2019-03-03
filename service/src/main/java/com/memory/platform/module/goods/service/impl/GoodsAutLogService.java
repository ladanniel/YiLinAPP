package com.memory.platform.module.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.goods.GoodsAutLog;
import com.memory.platform.module.goods.dao.GoodsAutLogDao;
import com.memory.platform.module.goods.service.IGoodsAutLogService;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 下午6:33:07 
* 修 改 人： 
* 日   期： 
* 描   述： 货物记录服务实现类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class GoodsAutLogService implements IGoodsAutLogService {

	@Autowired
	private GoodsAutLogDao goodsAutLogDao;
	
	@Override
	public Map<String, Object> getPage(GoodsAutLog log, int start, int limit) {
		return goodsAutLogDao.getPage(log, start, limit);
	}

	@Override
	public void saveLog(GoodsAutLog log) {
		goodsAutLogDao.save(log);
	}

	@Override
	public GoodsAutLog getGoodsAutLogById(String id) {
		return goodsAutLogDao.getObjectById(GoodsAutLog.class, id);
	}

	@Override
	public List<Map<String, Object>> getListForMap(String goodsId) {
		return goodsAutLogDao.getListForMap(goodsId);
	}

}
