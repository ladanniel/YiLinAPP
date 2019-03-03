package com.memory.platform.module.order.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecordInfo;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月17日 上午10:40:13 
* 修 改 人： 
* 日   期： 
* 描   述： 订单详情服务接口
* 版 本 号：  V1.0
 */
public interface IRobOrderRecordInfoService {

	/**
	* 功能描述： 保存订单详情
	* 输入参数:  @param info
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月17日上午10:39:58
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void saveInfo(RobOrderRecordInfo info);
	
	/**
	* 功能描述： 编辑订单详情
	* 输入参数:  @param info
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月17日上午10:40:31
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateInfo(RobOrderRecordInfo info);
	
	/**
	* 功能描述： 获取订单详情
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月17日上午10:40:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：RobOrderRecordInfo
	 */
	RobOrderRecordInfo getInfoById(String id);
	
	/**
	* 功能描述： 根据抢单id获取抢单详情
	* 输入参数:  @param orderId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月21日下午4:04:07
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	List<Map<String, Object>> getInfoForList(String orderId);
	
	/**
	* 功能描述： 根据抢单id获取抢单详情
	* 输入参数:  @param orderId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年6月21日下午5:30:50
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	List<RobOrderRecordInfo> getList(String orderId);
	
	/**
	* 功能描述： 通过抢单ID,查询抢单详细列表
	* 输入参数:  @param orderId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日下午12:24:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	List<RobOrderRecordInfo> getListByRobOrderRecordId(String robOrderId);
	
	
	/**
	* 功能描述： 通过抢单ID,查询抢单详细列表
	* 输入参数:  @param orderId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日下午12:24:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	List<RobOrderRecordInfo> getListByRobOrderRecord(String robOrderId, String truckID);
	
	
	/**
	* 功能描述： 查询已拆分的货物信息
	* 输入参数:  @param pid 父级ID
	* 输入参数:  @robOrderId 抢单ID
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午2:24:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	List<Map<String, Object>> getListByRobOrderRecordInfoByPid(String pid,String robOrderId);
	
	
	/**
	* 功能描述： 货物详细货物信息
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午3:24:41
	* 修 改 人: 
	* 日    期: 
	* 返    回：RobOrderRecordInfo
	 */
	RobOrderRecordInfo getRobOrderRecordInfoById(String id);
	
	/**
	* 功能描述： 
	* 输入参数:  @param robOrderId   抢单ID
	* 输入参数:  @param robOrderRecordInfoId   被拆分的货物ID
	* 输入参数:  @param surplusWeight  被拆分货物的剩余重量
	* 输入参数:  @param splitRobOrderRecordInfo  拆分的货物数据
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月10日上午10:44:45
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> saveRobOrderSplit(String robOrderId,String robOrderRecordInfoId,Double surplusWeight,String splitRobOrderRecordInfo);

	Map<String, Object> saveRobOrderSplit(String robOrderId,
			String robOrderRecordInfoId, Double surplusWeight,
			String splitRobOrderRecordInfo, Account account);
}
