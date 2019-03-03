package com.memory.platform.module.capital.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.Transfer;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月4日 下午8:22:50 
* 修 改 人： 
* 日   期： 
* 描   述： 转账 － 业务接口
* 版 本 号：  V1.0
 */
public interface ITransferService {

	/**
	* 功能描述： 分页转账列表
	* 输入参数:  @param transfer
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:36:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(Transfer transfer,int start, int limit);
	
	/**
	* 功能描述： 转账业务
	* 输入参数:  @param transfer
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:55:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveTransfer(Transfer transfer);
	
	/**
	 * 转账业务  选择其它支付方式转账－先充值后转账
	* 功能描述： 
	* 输入参数:  @param transfer
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月18日上午10:14:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveRechargeToTransfer(Transfer transfer);
	
	/**
	* 功能描述： 根据id查看转账详情
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日下午3:03:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：Transfer
	 */
	Transfer getTransferById(String id);
	
	/**
	* 功能描述： excel数据源
	* 输入参数:  @param transfer
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月28日上午9:57:27
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Transfer>
	 */
	List<Transfer> getList(Transfer transfer);
	
	List<Transfer> getListByTypeId(String id);

	Map<String, Object> getListForMap(String accountId,int start,int size);
}
