package com.memory.platform.module.capital.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.capital.B2bAccount;
import com.memory.platform.entity.capital.B2bBankAccount;
import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.ResData;

/**
 * B2B接口
 * V1.0
 */
public interface IB2bAccountService {

	/**
	 * 100000 获取商户公钥
	 * 	  
	 * */
	public ResData getKey();
	
	/**
	 * 101010 子帐户开户 传入 子账户实体
	 * 
	 * */
	public ResData saveB2bAccount(B2bAccount b2bAccount);
	
	/**
	 * 101020 通过子账户号查询子账户信息 传入 子账户号
	 * 
	 * */
	public ResData getB2bAccountByNo(String subAccount);
	
	/**
	 * 101050 子账户信息变更 传入 子账户实体
	 * 
	 * */
	public ResData updateB2bAccount(B2bAccount b2bAccount);
	
	/**
	 * 101060 子账户状态变更 传入 子账户号  交易会员代码 状态 是否强制销户
	 * 
	 * */
	public ResData updateB2bAccountState(String subAccount,String tradeMemCode ,Integer state ,Boolean isCoerce);
	
	/**
	 * 201030 通过摊位号查询子账户信息 传入摊位号
	 * 
	 * */
	public ResData getB2bAccountByBoothNo(String boothNo);
//	
	/**
	 * 101070 绑定出入金账户..传入出入金账户（银行卡账户）
	 * 
	 * */
	public ResData bindBankAccount(B2bBankAccount b2bBankCard);
	
	/** 
	 * 101080 子账户绑定出入金结果查询 传入 子账号 交易会员代码 出入金账户类别
	 * 
	 * */
	public ResData bindBankAccountResult(String subAccount,String tradeMemCode,int outComeAccountType);

	/**
	 * 103010 本行出金..传入 支付单号 子账号 交易会员代码 出金金额（分） 渠道号 总扣款金额（出金金额+客户自付手续费）
	 * 	手续费 客户自付手续费 商户代付手续费 出金账户类型 出金帐号 出金帐号户名 交易摘要     [打包实体]
	 * */
	public ResData outComeCapital(String payCode,String subAccount,String tradeMemCode,Double outComeMoney,String channelNo,
			Double sumSubMoney,Double otherBankCost,Double cusPayMoney,Double merOtherPayMoney,int outComeAccountType,String outAccount,
			String outAccountName,String tradeAbstract);
	
	/**
	 * 103020 本行入金..传入 支付单号 子账号 交易会员代码 入金金额 交易摘要
	 * 
	 * */
	public ResData onCapital(String payCode,String subAccount,String tradeMemCode,Double inMoney,String tradeAbstract);
	
	/**
	 * 103030 商户本行出金审核结果查询  传入 子账号  原出金支付单号
	 * 
	 * */
	public ResData outComeCapitalRecordByPayCode(String subAccount,String oldOutComePayCode);
	
	/**
	 * 103130 子账户余额查询  传入 交易会员代码 子账号 币种
	 * 
	 * */
	public ResData inquiryBalance(String tradeMemCode,String subAccount,String currency);
	
	
	
	
	
	
	
	
}
