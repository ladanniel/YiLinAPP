package com.memory.platform.module.capital.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.BankCard;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午6:58:43 修 改 人： 日 期： 描 述：  银行卡服务接口 版 本 号：
 * V1.0
 */
public interface IBankCardService {

	/**
	 * 功能描述： 绑定银行卡 输入参数: @param bankCard 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月26日下午10:09:47 修 改 人: 日 期: 返 回：void
	 */
	Map<String, Object> saveBankCard(BankCard bankCard);

	/**
	 * 功能描述： 删除绑定银行卡 输入参数: @param id 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月26日下午10:12:07 修 改 人: 日 期: 返 回：void
	 */
	void removeBankCard(BankCard bankCard);

	/**
	 * 功能描述： 获取用户下所有银行卡 输入参数: @param userId 输入参数: @return 异 常： 创 建 人: longqibo 日
	 * 期: 2016年4月26日下午10:15:19 修 改 人: 日 期: 返 回：Collection<BankCard>
	 */
	List<BankCard> getAll(String userId);

	/**
	 * 功能描述： 认证输入银行卡信息 输入参数: @param bankCard 输入参数: @return 异 常： 创 建 人: longqibo
	 * 日 期: 2016年4月27日下午12:10:35 修 改 人: 日 期: 返 回：BankCard
	 */
	Map<String, Object> verifyApp(String nubmber);

	BankCard verify(String nubmber);

	/**
	 * 功能描述： 获取银行卡信息 输入参数: @param id 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月28日下午9:13:44 修 改 人: 日 期: 返 回：BankCard
	 */
	BankCard getBankCard(String id);

	/**
	 * 功能描述： 根据卡号获取银行卡信息 输入参数: @param bankCard 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年5月10日下午7:30:00 修 改 人: 日 期: 返 回：BankCard
	 */
	BankCard getBankCardByBankCard(String bankCard);

	BankCard getBankCardByOrderId(String orderId);

	void updateBankCard(BankCard bankCard);

	List<Map<String, Object>> getAllForMap(String userId);

	/**
	 * 根据用户id获取分页绑定银行卡
	 */
	Map<String, Object> getMyBindBankCardPage(String accountId, int start, int limit);
}
