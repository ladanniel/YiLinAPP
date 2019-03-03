package com.memory.platform.module.order.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.RobOrderRecord;

/**
 * 创 建 人： longqibo 日 期： 2016年6月16日 下午9:02:25 修 改 人： 日 期： 描 述： 订单记录服务接口 版 本 号：
 * V1.0
 */
public interface IRobOrderRecordService {

	/**
	 * 功能描述： 分页订单记录 输入参数: @param record 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年6月16日下午9:03:08 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	Map<String, Object> getPage(String id, int start, int limit);

	/**
	 * 功能描述： 分页订单日志记录 输入参数: @param orderRecord 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年7月1日下午3:01:57 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	Map<String, Object> getLogPage(RobOrderRecord orderRecord, int start, int limit);

	/**
	 * 功能描述： 分页订单记录 输入参数: @param record 输入参数: @param start 输入参数: @param limit
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年6月16日下午9:03:08 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	Map<String, Object> getMyPage(RobOrderRecord record, int start, int limit);

	/**
	 * 功能描述： 保存订单记录 输入参数: @param record 异 常： 创 建 人: longqibo 日 期:
	 * 2016年6月16日下午9:06:13 修 改 人: 日 期: 返 回：void
	 */
	Map<String, Object> saveRecord(RobOrderRecord record);

	/**
	 * 功能描述： 修改抢单信息 输入参数: @param record 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月28日上午10:55:30 修 改 人: 日 期: 返 回：void
	 */
	void updateRobOrderRecord(RobOrderRecord record);

	/**
	 * 功能描述： 编辑记录 输入参数: @param record 异 常： 创 建 人: longqibo 日 期: 2016年6月16日下午9:10:37
	 * 修 改 人: 日 期: 返 回：void
	 */
	void updateRecord(RobOrderRecord record, OrderAutLog log);

	void updateRecord(RobOrderRecord record);

	/**
	 * 功能描述： 根据id获取记录 输入参数: @param id 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年6月16日下午9:13:19 修 改 人: 日 期: 返 回：RobOrderRecord
	 */
	RobOrderRecord getRecordById(String id);

	/**
	 * 功能描述： 抢单认证 输入参数: @param record 异 常： 创 建 人: longqibo 日 期: 2016年6月27日下午4:02:19
	 * 修 改 人: 日 期: 返 回：void
	 */
	void updateStatus(RobOrderRecord record, String payPassword);

	// aiqiwu 2017-07-24新增，旧方法处理逻辑有问题（会将状态为0或1的信息状态全部设置为4）
	String updateStatus2(RobOrderRecord record, String payPassword);

	/**
	 * 功能描述： 取消抢单 输入参数: @param record 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月27日下午4:54:03 修 改 人: 日 期: 返 回：void
	 */
	void updateRobOrderCancel(RobOrderRecord record, Account account, String type);

	/**
	 * 功能描述： 获取每天用户取消订单的数量 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月27日下午4:57:01 修 改 人: 日 期: 返 回：int
	 */
	int getRobOrderCancelByDay();

	/**
	 * 功能描述： 撤回抢单信息 输入参数: @param robOrderRecord 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月28日上午9:32:55 修 改 人: 日 期: 返 回：void
	 */
	void updateWithdrawRobOrder(RobOrderRecord robOrderRecord);

	/**
	 * 功能描述： 抢单信息确认 输入参数: @param robOrderRecord 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年7月1日下午3:16:19 修 改 人: 日 期: 返 回：void
	 */
	void saveConfirmRobOrder(RobOrderRecord robOrderRecord, String payPassword);

	/**
	 * 功能描述： 统一用户抢单总数、各状态的抢单数量 输入参数: @param robOrderRecord 输入参数: @param payPassword
	 * 输入参数: @param truckIds 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月6日下午2:13:46 修 改 人:
	 * 日 期: 返 回：void
	 */
	List<Map<String, Object>> getAccountRobOrderCount(String accountId);

	/**
	 * 订单各种状态数量统计
	 * 
	 * @param account
	 * @return
	 */

	Map<String, Object> getRobOrderCount(Account account);

	/**
	 * 功能描述： 通过月分查订单数 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日
	 * 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	List getAllRobOrderMonthCount(List<String> months, Account account, String dateType, RobOrderRecord robOrderRecord);

	/**
	 * 功能描述： 通过月分查完结订单数 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人: yangjiaqiao
	 * 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	List getOrderCompletionMonthCount(List<String> months, Account account, String dateType,
			RobOrderRecord robOrderRecord);

	/**
	 * 功能描述： 通过月分查完结订单重量 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	List getOrderCompletionMonthWeight(List<String> months, Account account, String dateType,
			RobOrderRecord robOrderRecord);

	/**
	 * 功能描述： 通过月分查订单重量 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人: yangjiaqiao
	 * 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	List getAllRobOrderMonthWeight(List<String> months, Account account, String dateType,
			RobOrderRecord robOrderRecord);

	/**
	 * 功能描述： 统计订单排行 输入参数: @param ranking 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月24日下午3:53:39 修 改 人: 日 期: 返 回：List<Map<String,Object>>
	 */
	public Map<String, Object> getRobOrderRankingStatistics(int ranking, String type);

	Map<String, Object> updateStatus(RobOrderRecord record, String payPassword, Account user);

	Map<String, Object> getOrderRecordByGoodsId(RobOrderRecord order, int start, int limit);

	Map<String, Object> getOrderDetails(String orderId);

	Map<String, Object> saveRecordForMap(RobOrderRecord record, Account account);

	Map<String, Object> getMyPage(RobOrderRecord record, Account account, int start, int limit);

	int getRobOrderCancelByDay(String accountId);

	void updateRobOrderCancel(RobOrderRecord record, Account account);

	void updateWithdrawRobOrder(RobOrderRecord robOrderRecord, Account account);

	Map<String, Object> getRobOrderRecordById(String id, Account account);

	void updateRobOrderRecord(RobOrderRecord record, Account account);

	Map<String, Object> getRobOrderRecordIndoSplitList(String id, Account account);

	List<Map<String, Object>> getCompanyTrucks(String companyId);

	void saveConfirmRobOrder(RobOrderRecord robOrderRecord, String payPassword, Account user);

	Map<String, Object> getWaitOrdersReviewDetails(RobOrderRecord order, int start, int limit);

	Map<String, Object> getAlreadyOrdersReviewDetails(RobOrderRecord order, int start, int limit);

	Map<String, Object> getMyPageNew(RobOrderRecord robOrderRecord, Account account, int start, int size);

	List<Map<String, Object>> getAccountRobOrderCountNew(String id);

	Map<String, Object> getMyRobOrderDetails(RobOrderRecord order);

	public Map<String, Object> saveSuccessRobOrderNew(String splitRobOrderRecordInfoJson, String payPassword);

	Map<String, Object> getSuccessRobOrderPayment(String splitRoborderRecordInfoJson, int truckCount,String roborderRecordId);

	List<RobOrderRecord> getMyRobOrderRecordWithGoodsBasicID(String goodsBasicID, Account account);

	Map<String, Object> getSolrRobOrderRecordByIds(String ids, Account account, int start, int size);
}
