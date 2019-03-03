package com.memory.platform.module.capital.service;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.capital.WeiXinPay;
import com.memory.platform.entity.capital.WeiXinPayOrder;
import com.memory.platform.exception.BusinessException;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午7:01:10 
* 修 改 人： 
* 日   期： 
* 描   述： 充值记录接口
* 版 本 号：  V1.0
 */
public interface IRechargeRecordService {

	/**
	* 功能描述： 分页充值记录列表
	* 输入参数:  @param rechargeRecord
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午3:01:37
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(RechargeRecord rechargeRecord,int start, int limit);
	
	/**
	* 功能描述： 添加充值记录
	* 输入参数:  @param rechargeRecord
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年4月28日下午4:25:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveRecharge(RechargeRecord rechargeRecord);
	
	void savePay(RechargeRecord rechargeRecord);

	/**
	* 功能描述： 根据id获取充值记录
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月25日下午1:51:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：RechargeRecord
	 */
	RechargeRecord getRechargeRecordById(String id);
	
	List<RechargeRecord> getList(RechargeRecord rechargeRecord);
	
	RechargeRecord getRechargeRecordByOrderId(String orderId);

	Map<String, Object> getListForMap(String accountId,int start,int size);

	String getWeiXinKey(String accountId);

	String getWeiXinMchId();

	String genAppSign(WeiXinPayOrder order, String appKey);

	Map<String, Object> weiXinrechargeConsume(WeiXinPay pay);

	Map<String, Object> generatorWeiXinOrder(WeiXinPay payInfo);
	/*
	 * 绑定微信认证 用于提现
	 * */


	Map<String, Object> withdrawWeiXin(String weiXinAuthCode, double d,
			String payPassword);

	Map<String, Object> withdrawSuccess(String cashApplicationID)
			throws BusinessException, JAXBException;

	Map<String, Object> getWeiXinUserInfo(String weiXinAuthCode);
 
}
