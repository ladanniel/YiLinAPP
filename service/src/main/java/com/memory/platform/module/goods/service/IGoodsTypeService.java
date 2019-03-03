package com.memory.platform.module.goods.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.goods.GoodsType;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 上午11:04:15 
* 修 改 人： 
* 日   期： 
* 描   述： 货物类型 － 业务接口
* 版 本 号：  V1.0
 */
public interface IGoodsTypeService {

	/**
	* 功能描述： 保存货物类型
	* 输入参数:  @param goodsType
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午2:27:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveGoodsType(GoodsType goodsType);
	
	/**
	* 功能描述： 更新货物类型
	* 输入参数:  @param goodsType
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午2:28:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateGoodsType(GoodsType goodsType);
	
	/**
	* 功能描述： 货物所有货物类型数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月6日上午10:18:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<GoodsType>
	 */
	List<GoodsType> getAllList();
	
	List<Map<String, Object>> getGoodsType();
	/**
	* 功能描述： 查询为1级的货物类型信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月6日上午10:52:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<GoodsType>
	 */
	List<GoodsType> getListByLeav();
	
	/**
	* 功能描述： 删除货物类型
	* 输入参数:  @param id
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午3:08:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void removeGoodsType(String id);
	
	/**
	* 功能描述： 获取货物类型
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月2日下午3:10:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：GoodsType
	 */
	GoodsType getGoodsTypeById(String id);
	
	/**
	* 功能描述： 通过名称获取货物类型
	* 输入参数:  @param name
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月6日上午11:49:20
	* 修 改 人: 
	* 日    期: 
	* 返    回：GoodsType
	 */
	GoodsType getGoodsTypeByName(String name,String id);
	
	/**
	* 功能描述： 通过ID查询该货物类型下面是否存在子级
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月6日下午2:31:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<GoodsType>
	 */
	List<GoodsType> getListByPrentId(String id);
	
	/**
	* 功能描述： 通过ID查询该货物类型下面是否存在子级
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月6日下午2:31:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<GoodsType>
	 */
	List<Map<String, Object>> getListMapByPrentId(String id);
}
