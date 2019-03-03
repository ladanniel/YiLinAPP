package com.memory.platform.module.capital.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.CashApplication;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午7:00:11 修 改 人： 日 期： 描 述： 用户提现接口 版 本 号： V1.0
 */
public interface ICashApplicationService {

	/**
	 * 功能描述： 分页列表 输入参数: @param cashApplication 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月28日下午5:54:56 修 改 人:
	 * 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getPage(CashApplication cashApplication, int start,
			int limit);

	Map<String, Object> getOsLogPage(CashApplication cashApplication,
			int start, int limit);

	/**
	 * 功能描述： 分页列表 输入参数: @param cashApplication 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月28日下午5:54:56 修 改 人:
	 * 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getOsPage(CashApplication cashApplication, int start,
			int limit);

	/**
	 * 功能描述： 保存数据 输入参数: @param cashApplication 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月28日下午5:55:20 修 改 人: 日 期: 返 回：void
	 */
	void saveCashApplication(CashApplication cashApplication);

	/**
	 * 功能描述： 审核提现 输入参数: @param cashApplication 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月11日上午10:44:23 修 改 人: 日 期: 返 回：void
	 * 
	 * @throws Exception
	 */
	void updateCashApplication(CashApplication cashApplication)
			throws Exception;

	/**
	 * 功能描述： 根据id获取提现信息 输入参数: @param id 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月24日上午10:41:57 修 改 人: 日 期: 返 回：CashApplication
	 */
	CashApplication getCashById(String id);

	/**
	 * 功能描述： 锁定信息 输入参数: @param cashApplication 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月24日上午11:43:23 修 改 人: 日 期: 返 回：void
	 */
	void updateLock(CashApplication cashApplication);

	/**
	 * 功能描述： excel数据源 输入参数: @param cashApplication 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年5月27日下午5:30:35 修 改 人: 日 期: 返 回：List<CashApplication>
	 */
	List<CashApplication> getList(CashApplication cashApplication, String type);

	Map<String, Object> getListForMap(String accountId, int start, int size);

	void save(CashApplication cashApplication);

	void update(CashApplication cashApplication);
}
