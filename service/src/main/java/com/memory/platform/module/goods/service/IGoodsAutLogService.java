package com.memory.platform.module.goods.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.goods.GoodsAutLog;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月14日 下午6:29:31 
* 修 改 人： 
* 日   期： 
* 描   述： 货物操作日志服务接口
* 版 本 号：  V1.0
 */
public interface IGoodsAutLogService {

	/**
	* 功能描述： 分页列表
	* 输入参数:  @param log
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月14日下午6:29:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(GoodsAutLog log,int start,int limit);
	
	
	/**
	* 功能描述： 保存记录
	* 输入参数:  @param log
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月14日下午6:30:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveLog(GoodsAutLog log);
	
	/**
	* 功能描述： 根据id获取记录
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月14日下午6:31:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：GoodsAutLog
	 */
	GoodsAutLog getGoodsAutLogById(String id);
	
	List<Map<String, Object>> getListForMap(String goodsId);
	
}
