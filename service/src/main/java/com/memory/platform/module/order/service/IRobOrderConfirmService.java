package com.memory.platform.module.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.OrderSpecialApply;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;
import com.memory.platform.entity.sys.LgisticsCompanies;

/**
 * 创 建 人：武国庆 日 期： 2016年6月16日 下午9:02:25 修 改 人： 日 期： 描 述： 我的订单服务接口 版 本 号： V1.0
 */
public interface IRobOrderConfirmService {

	/**
	 * 
	 * 功能描述： 根据实体查询订单信息 输入参数: @param robOrderConfirm 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月7日下午3:59:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getCompanyOrderPage(RobOrderRecord robOrderRecord, int start, int limit);

	/**
	 * 
	 * 功能描述： 获取急救信息 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getSpecialOrderPage(Account account, RobOrderRecord robOrderRecord, int start, int limit);

	/**
	 * 
	 * 功能描述： 根据实体查询订单信息 输入参数: @param robOrderConfirm 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月7日下午3:59:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord, int start, int limit);

	/**
	 * 
	 * 功能描述： 根据实体查询报表信息 输入参数: @param robOrderConfirm 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月7日下午3:59:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getReportFormsPage(RobOrderRecord robOrderRecord, String goodsName, String trackNo, int start,
			int limit);

	/**
	 * 获取订单拉货车列表 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月8日上午11:24:08 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getTruckPage(String robOrderId, int start, int limit);

	/**
	 * 获取全部订单拉货车列表 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月8日上午11:24:08 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getTruckAll(String robOrderId);

	/**
	 * 获取司机拉货信息 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月8日上午11:24:08 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getOrderInfoPage(String robOrderId, String truckID, int start, int limit);

	/**
	 * 根据单号和开车人ID查询 功能描述： 输入参数: @param robOrderId 输入参数: @param truckUserID
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月11日上午11:25:19 修 改 人:
	 * 日 期: 返 回：RobOrderConfirm
	 */
	RobOrderConfirm findRobOrderConfirm(String robOrderId, String truckUserID);

	/**
	 * 根据实体查询 功能描述： 输入参数: @param robOrderId 输入参数: @param truckUserID
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月11日上午11:25:19 修 改 人:
	 * 日 期: 返 回：RobOrderConfirm
	 */
	public RobOrderConfirm findRobOrderConfirm(RobOrderConfirm robOrderConfirm);

	/**
	 * 获取全部订单拉货车列表 功能描述： 输入参数: @param robOrderId 抢单ID 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月8日上午11:24:08 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public Map<String, Object> getTruckAll(RobOrderConfirm robOrderConfirm);

	/**
	 * 
	 * 功能描述： 保存回人员 输入参数: 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月26日下午3:01:33 修 改 人:
	 * 日 期: 返 回：void
	 */
	public void savereceiptuser(RobOrderConfirm robOrderConfirm, @RequestParam(value = "path[]") String[] path,
			String rootPath);

	/**
	 * 
	 * 功能描述： 保存运单处理信息 输入参数: 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月26日下午3:01:33 修 改
	 * 人: 日 期: 返 回：void
	 */
	public void savespecialorderinfo(Account account, RobOrderConfirm robOrderConfirm,
			OrderSpecialApply orderSpecialApply, String remark, String type);

	/**
	 * 
	 * 功能描述： 保存仲裁信息 输入参数: 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月26日下午3:01:33 修 改
	 * 人: 日 期: 返 回：void
	 */
	public void savearbitration(Account account, RobOrderConfirm robOrderConfirm, OrderSpecialApply orderSpecialApply,
			String remark, String savetype, RocessingResult rocessingResult);

	/**
	 * 
	 * 功能描述： 获取回执任务列表 输入参数: @param robOrderConfirm 输入参数: @param start
	 * 输入参数: @param limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月7日下午3:59:35 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getReceipttask(RobOrderRecord robOrderRecord, int start, int limit);

	/**
	 * 根据ID查询 功能描述： 输入参数: @param robOrderId 输入参数: @param truckUserID
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月11日上午11:25:19 修 改 人:
	 * 日 期: 返 回：RobOrderConfirm
	 */
	RobOrderConfirm findRobOrderConfirmById(String id);

	/**
	 * 
	 * 功能描述： 获取订单状态 输入参数: @param robOrderConfirm 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:59:35 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	List<Map<String, Object>> orderstatus(String robConfirmId);

	Map<String, Object> getRabOrderDelivered(RobOrderConfirm robOrderConfirm, int i, int j);

	Map<String, Object> getOrderPage(RobOrderRecord robOrderRecord, Account account, int start, int limit);

	Map<String, Object> getCompanyOredrList(RobOrderRecord robOrderRecord, Account account, int start, int limit);

	List<Map<String, Object>> getTruckList(String robOrderId, Account account);

	RobOrderConfirm getRobOrderConfirmById(String id);

	Map<String, Object> getOrderConfirmInfo(String robOrderConfirmId);

	List<Map<String, Object>> getOrderInfoPageForMap(String robOrderId, String truckID);

	void saveConfirmPullGoods(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			Double actualWeight);

	void saveConfirmload(Account account, RobOrderRecord robOrderRecord, RobOrderConfirm robOrderConfirm,
			Double actualWeight);

	void saveConfirmreCeipt(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord);

	void saveReceipt(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			LgisticsCompanies lgisticsCompanies, String lgisticsNum, List<String> path, String rootPath);

	void savePayment(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord);

	void saveEmergency(Account account, RobOrderConfirm robOrderConfirm, String remark, List<String> path,
			String rootPath);

	void saveArbitration(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord, String remark,
			List<String> path, String rootPath);

	Map<String, Object> getRobOrderConfirmByRobOrderId(String id);

	Map<String, Object> getConfirmDeliverDetailsByRobOrderRecordId(RobOrderRecord recode);

	Map<String, Object> getMyRobOrderConfirmList(RobOrderConfirm robOrderRecordConfirm, Account account, int start,
			int limit);

	Map<String, Object> getMyRobOrderConfirmDetail(Account account, RobOrderConfirm confirm);

	boolean accountCanViewGoodsBasic(Account account, String goodsBasicID);

	Map<String, Object> getRobOrderConfirmLoadPayment(String robOrderConfirmID);
	
	Map<String,Object> getRobOrderConfirmLoadPaymentNew(String robOrderConfirmID,Double actualWeight,String additionalExpensesRecordJson);

	void saveDelivery(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			String lgisticsNum, List<String> path, String rootPath);

	List<RobOrderConfirm> getRobOrderConfirmListByRobOrderID(String robOrderRecordID);

	List<RobOrderConfirm> getAutoPaymentConfrim();

	void autoPayment();

	void savePayment(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord, boolean isSystem);

	void encrimentAutoPaymentErr(String id);

	void saveConfirmReleaseGoods(Account account, RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			Double actualWeight, String additionalExpensesRecordJson);



	void saveArbitrationNew(Account account, RobOrderConfirm confirm,
			String remark, RocessingResult rocessingResult);
	
	Map<String,Object> getSolrRobOrderConfirmByIds(String ids,Account account,int start,int limit);
}
