package com.memory.platform.module.capital.service;

import java.util.Map;

import com.memory.platform.entity.capital.UnionPay;

public interface IUnionPayService {
	/**
	 * 功能描述：开通查询 输入参数: @param 银联对象 异 常： 创 建 人: aiqiwu 日 期: 2017年4月11日上午12:52:25
	 * 修 改 人: 日 期: 返 回：boolean
	 */
	boolean openQueryCard(UnionPay unionPay);

	/**
	 * 功能描述： 获取开通验证码 输入参数: @param 卡号 输入参数: @param 手机号 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月11日上午14:55:29 修 改 人: 日 期: 返 回：boolean
	 */
	Map<String, Object> sendOpenCardSMSCode(String cardNo, String phoneNo);

	/**
	 * 功能描述：后台开通银联 输入参数: @param 银联对象 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月11日上午14:53:11 修 改 人: 日 期: 返 回：boolean
	 */
	boolean openCard(UnionPay unionPay);

	/**
	 * 功能描述： 获取消费验证码 输入参数: @param 卡号 输入参数: @param 手机号 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月12日下午17:01:29 修 改 人: 日 期: 返 回：boolean
	 */
	Map<String, Object> sendConsumeSMSCode(String cardNo, String phoneNo, double money);

	/**
	 * 功能描述： 交易消费 输入参数: @param 卡号 输入参数: @param 手机号 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月12日下午17:15:17 修 改 人: 日 期: 返 回：boolean
	 */
	Map<String, Object> rechargeConsume(UnionPay unionPay);

	/**
	 * 功能描述： 前台跳转开通银行卡 输入参数: @param 卡号 输入参数: @param 手机号 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年5月3日上午9:27:17 修 改 人: 日 期: 返 回：boolean
	 */
	String directOpenCard(UnionPay unionPay);
}
