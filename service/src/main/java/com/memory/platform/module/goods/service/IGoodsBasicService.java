package com.memory.platform.module.goods.service;

import java.util.List;
import java.util.Map;

import com.memory.platform.entity.goods.GoodsAutLog;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年6月12日 下午4:02:03 修 改 人： 日 期： 描 述： 发货管理控制器 版 本 号：
 * V1.0
 */
public interface IGoodsBasicService {

	/**
	 * 功能描述： 货物信息管理列表 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月12日下午4:09:10 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getPage(GoodsBasic goodsBasic, int start, int limit);

	/**
	 * 功能描述： 查找货物信息 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * limit 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月21日下午3:25:31 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getGoodsBasicPage(GoodsBasic goodsBasic, int start, int limit);

	/**
	 * 功能描述： 保存/提交货物信息 输入参数: @param goodsBasic 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月15日上午9:45:44 修 改 人: 日 期: 返 回：void
	 */
	void saveSubGoodsBasicDetail(GoodsBasic goodsBasic);

	/**
	 * 功能描述： 修改保存/提交货物信息 输入参数: @param goodsBasic 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月16日下午3:55:27 修 改 人: 日 期: 返 回：void
	 */
	void editSubGoodsBasicDetail(GoodsBasic goodsBasic);

	/**
	 * 功能描述： 删除货物信息 输入参数: @param id 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月16日下午8:42:06 修 改 人: 日 期: 返 回：void
	 */
	void removeGoodsBasicDetail(String id, GoodsBasic goodsBasic);

	/**
	 * 功能描述： 撤回发货申请 输入参数: @param goodsBasic 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月17日上午11:15:03 修 改 人: 日 期: 返 回：void
	 */
	void updateGoodsBasicTranslate(GoodsBasic goodsBasic);

	/**
	 * 功能描述： 货物上线、下线 输入参数: @param goodsBasic 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年7月8日下午12:17:57 修 改 人: 日 期: 返 回：void
	 */
	void updateGoodsBasicOnLine(String id, boolean onLine);

	Map<String, Object> getOsPage(GoodsBasic goodsBasic, int start, int limit);

	GoodsBasic getGoodsBasicById(String id);

	// void updateGoodsBasic(GoodsBasic goodsBasic,GoodsAutLog log,String[]
	// array,Map<String, Object> logs);
	void updateGoodsBasic(GoodsBasic goodsBasic, GoodsAutLog log, Map<String, Object> logs);

	void saveSubGoodsBasic(GoodsBasic goodsBasic);

	Map<String, Object> getLogPage(GoodsBasic goodsBasic, int start, int limit);

	Map<String, Object> getRecordPage(GoodsBasic goodsBasic, int start, int limit);

	Map<String, Object> getPageSuccess(GoodsBasic goodsBasic, int start, int limit);

	Map<String, Object> getGoodsBasicPageSuccess(GoodsBasic goodsBasic, int start, int limit);

	/**
	 * 功能描述： 查询平台货物的数量 accountId //全平台为null,指定商户为用户ID 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月20日下午12:42:58 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getAllGoodsBasicCount(String accountId);

	/**
	 * 功能描述： 通过平台所有的发货单数 输入参数: @param accountId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午1:00:54 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	List getAllGoodsBasicMonthCount(List<String> months, String accountId);

	/**
	 * 功能描述：统计平台每月所有的发货重量 输入参数: @param months 输入参数: @param accountId
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月24日下午1:49:20 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@SuppressWarnings("rawtypes")
	List getAllGoodsBasicMonthWeight(List<String> months, String accountId);

	/**
	 * 功能描述： 查询发货量排名信息 输入参数: @param ranking 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日下午3:48:22 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getGoodsRankingStatistics(int ranking, String type);

	/**
	 * 功能描述：统计平台货物最近一周的发货情况和平台货物的状态比例 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月25日下午4:13:14 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	Map<String, Object> getGoodsStatusCountStatistics(String accountId);

	Map<String, Object> getGoodsBasicPage(GoodsBasic goodsBasic, Account account, int start, int limit);

	Map<String, Object> getGoodsBasicByIdForMap(String id);

	Map<String, Object> getMyGoodsOrderPage(GoodsBasic goodsBasic, Account account, int start, int limit);

	// 获取待审核货物列表
	Map<String, Object> getWaitOrdersReview(GoodsBasic goodsBasic, Account account,  int start, int limit);

	// 获取已审核货物列表
	Map<String, Object> getAlreadyOrdersReview(GoodsBasic goodsBasic, Account account, int start, int limit);

	// 获取需要确认发货的订单列表
	Map<String, Object> getConfirmDeliverGoodsOrders(GoodsBasic goodsBasic, Account account, int start, int limit);

	Map<String, Object> getGoodsBasicPageForMap(GoodsBasic goodsBasic, Account account, int start, int limit);
	//lix 2017-08-09 添加 用户判断车队用户对当前货源是否可以操作抢单
	Map<String, Object> getMyGoodsBasicStockTypeWithGoodsBasicID(String goodsBasicID,Account account);

	Map<String, Object> getMyGoodsPage(GoodsBasic goodsBasic, Account account,
			int i, int size);
	
	 //lix 2017-09-12 添加 根据货源ID修改货源上线状态为下线
	void updateAllGoodsOnLine(String goodsBasicId);
	 //lix 2017-09-12 添加 获取所有截止时间小于当前时间或者剩余吨位数为0且货源上线状态为上线的记录
	List<GoodsBasic> getGoodsBasicsListByOnLine();
	
	/*获取goodsbasic  出发地区和收货地区的驾驶路径 strageType
	 * 下方策略 0~9的策略，仅会返回一条路径规划结果。
下方10~20的策略，会返回多条路径规划结果。（高德地图APP策略也包含在内）

下方策略仅返回一条路径规划结果
0，不考虑当时路况，返回耗时最短的路线，但是此路线不一定距离最短
1，不走收费路段，且耗时最少的路线
2，不考虑路况，仅走距离最短的路线，但是可能存在穿越小路/小区的情况
3，不走快速路，例如京通快速路（因为策略迭代，建议使用13）
4，躲避拥堵的路线，但是可能会存在绕路的情况，耗时可能较长
5，多策略（同时使用速度优先、费用优先、距离优先三个策略计算路径）。
其中必须说明，就算使用三个策略算路，会根据路况不固定的返回一~三条路径规划信息。
6，不走高速，但是不排除走其余收费路段
7，不走高速且避免所有收费路段
8，躲避收费和拥堵，可能存在走高速的情况，并且考虑路况不走拥堵路线，但有可能存在绕路和时间较长
9，不走高速且躲避收费和拥堵

下方策略返回多条路径规划结果
10，返回结果会躲避拥堵，路程较短，尽量缩短时间，与高德地图的默认策略也就是不进行任何勾选一致
11，返回三个结果包含：时间最短；距离最短；躲避拥堵 （由于有更优秀的算法，建议用10代替）
12，返回的结果考虑路况，尽量躲避拥堵而规划路径，与高德地图的“躲避拥堵”策略一致
13，返回的结果不走高速，与高德地图“不走高速”策略一致
14，返回的结果尽可能规划收费较低甚至免费的路径，与高德地图“避免收费”策略一致
15，返回的结果考虑路况，尽量躲避拥堵而规划路径，并且不走高速，与高德地图的“躲避拥堵&不走高速”策略一致
16，返回的结果尽量不走高速，并且尽量规划收费较低甚至免费的路径结果，与高德地图的“避免收费&不走高速”策略一致
17，返回路径规划结果会尽量的躲避拥堵，并且规划收费较低甚至免费的路径结果，与高德地图的“躲避拥堵&避免收费”策略一致
18，返回的结果尽量躲避拥堵，规划收费较低甚至免费的路径结果，并且尽量不走高速路，与高德地图的“避免拥堵&避免收费&不走高速”策略一致
19，返回的结果会优先选择高速路，与高德地图的“高速优先”策略一致
20，返回的结果会优先考虑高速路，并且会考虑路况躲避拥堵，与高德地图的“躲避拥堵&高速优先”策略一致
	 * */ 
	Map<String, Object> getGoodsBasicDrivingPath(String goodsBasicID,
			int strageType);
}
