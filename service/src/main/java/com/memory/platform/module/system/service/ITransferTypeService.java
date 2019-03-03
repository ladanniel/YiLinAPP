package com.memory.platform.module.system.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.TransferType;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月10日 下午2:44:58 
* 修 改 人： 
* 日   期： 
* 描   述： 转账类型数据字典业务接口
* 版 本 号：  V1.0
 */
public interface ITransferTypeService {
	
	/**
	* 功能描述： 保存
	* 输入参数:  @param transferType
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午2:47:12
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void save(TransferType transferType);

	/**
	* 功能描述： 更新
	* 输入参数:  @param transferType
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午2:47:38
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void update(TransferType transferType);
	
	/**
	* 功能描述： 删除
	* 输入参数:  @param id
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午2:48:20
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void remove(String id);
	
	/**
	* 功能描述： 根据id查询
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午2:49:20
	* 修 改 人: 
	* 日    期: 
	* 返    回：TransferType
	 */
	TransferType getTransferType(String id);
	
	/**
	* 功能描述： 查询列表
	* 输入参数:  @param transferType
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午2:50:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(TransferType transferType, int start, int limit);
	
	/**
	* 功能描述： 名称搜索
	* 输入参数:  @param name
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:10:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：TransferType
	 */
	TransferType checkName(String name,String typeId);
	
	/**
	* 功能描述： 获取全部类型
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午5:17:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<TransferType>
	 */
	List<TransferType> getAll(String companyTypeId);

	List<Map<String, Object>> getTransferTypeForMap(String companyTypeId);
	
	
}
