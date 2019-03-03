package com.memory.platform.module.goods.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.goods.GoodsDetail;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 下午3:41:08 
* 修 改 人： 
* 日   期： 
* 描   述： 货物详情 － 服务接口
* 版 本 号：  V1.0
 */
public interface IGoodsDetailService {

	List<Map<String, Object>> getListForMap(String goodsBasicId);
	
	/**
	* 功能描述： 通过货物基本信息ID，查询详细的货物信息
	* 输入参数:  @param goodsBasicId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月16日下午2:24:21
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<GoodsDetail>
	 */
	List<GoodsDetail> getListGoodsDetail(String goodsBasicId);
	/*
	 * aiqiwu 2017-06-03
	 * */
	List<Map<String,Object>> getListMapGoodsDetail(String goodsBasicId);
}
