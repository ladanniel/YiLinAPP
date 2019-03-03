package com.memory.platform.module.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.module.goods.dao.GoodsDetailDao;
import com.memory.platform.module.goods.service.IGoodsDetailService;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 下午3:42:07 
* 修 改 人： 
* 日   期： 
* 描   述： 货物详细 － 服务接口实现类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class GoodsDetailService implements IGoodsDetailService {

	@Autowired
	private GoodsDetailDao goodsDetailDao;

	@Override
	public List<Map<String, Object>> getListForMap(String goodsBasicId) {
		return goodsDetailDao.getListForMap(goodsBasicId);
	}

	/*  
	 *  getListGoodsDetail
	 */
	@Override
	public List<GoodsDetail> getListGoodsDetail(String goodsBasicId) {
		// TODO Auto-generated method stub
		return goodsDetailDao.getListGoodsDetail(goodsBasicId);
	}

	/*
	 * aiqiwu 2017-06-03
	 * */
	@Override
	public List<Map<String, Object>> getListMapGoodsDetail(String goodsBasicId) {
		// TODO Auto-generated method stub
		return goodsDetailDao.getListMapGoodsDetail(goodsBasicId);
	}
	
}
