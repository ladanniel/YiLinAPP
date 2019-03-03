package com.memory.platform.module.app.order.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StatusMap;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.interceptor.LockInterceptor;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IDepositLevelService;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.own.service.IRobOrderRecordOwnService;
import com.memory.platform.module.system.service.IParameterManageService;

/**
 * 创 建 人： longqibo 日 期： 2016年8月13日 上午10:15:31 修 改 人： 日 期： 描 述： 抢单审核app接口控制器 版 本
 * 号： V1.0
 */

@Controller
@RequestMapping("app/ordermanage")
public class OrderManageController extends BaseController {

	@Autowired
	private IGoodsBasicService goodsBasicService;
	@Autowired
	private IRobOrderRecordService orderRecordService;
	@Autowired
	private IOrderAutLogService orderAutLogService;
	@Autowired
	private IParameterManageService parameterManageService;
	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	private IRobOrderRecordOwnService robOrderRecordOwnService;
	@Autowired
	private IDepositLevelService depositLevelService;
	/**
	 * 功能描述： 货主审核抢单 输入参数: @param headers 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: longqibo 日 期: 2016年8月13日上午10:18:09 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyOrder(RobOrderRecord record,
			String payPassword, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		return orderRecordService.updateStatus(record, payPassword, account);
	}

	/**
	 * 功能描述： 获取我的货物审阅列表 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * size 输入参数: @param headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * longqibo 日 期: 2016年8月13日下午12:26:31 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getMyGoodsOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyGoodsOrder(GoodsBasic goodsBasic,
			int start, int size, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = goodsBasicService.getMyGoodsOrderPage(
				goodsBasic, account, start, size);
		return jsonView(true, "成功获取我的货物审阅列表。", map);
	}

	/**
	 * 功能描述： 获取货物下的抢单列表 输入参数: @param goodsId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日上午11:05:27 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getMyOrderByGoods", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyOrderByGoods(RobOrderRecord order,
			int start, int size, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Map<String, Object> map = orderRecordService.getOrderRecordByGoodsId(
				order, start, size);
		return jsonView(true, "成功获取货物抢单列表。", map);
	}

	/**
	 * 功能描述： 获取抢单详情 输入参数: @param orderId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日上午11:07:14 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getOrderDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getOrderDetails(String orderId,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = orderRecordService.getOrderDetails(orderId);
		map.put("promptMsg", StatusMap.getOrderStatusMsg(map.get("status")
				.toString(), account));
		return jsonView(true, "成功获取抢单详情。", map);
	}

	/**
	 * 功能描述： 获取抢单日志列表 输入参数: @param orderId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年8月13日上午11:09:28 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getOrderLogs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getOrderLogs(String orderId,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		List<Map<String, Object>> list = orderAutLogService
				.getOrderLog(orderId);
		return jsonView(true, "成功获取抢单日志列表。", list);
	}

	/**
	 * 功能描述： 获取抢单日志列表和运单日志列表 输入参数: @param orderId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: lil 日 期: 2016年8月13日上午11:09:28 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getOrderAndConfirmLogs", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getOrderAndConfirmLogs(String orderId,
			String confirmID, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		List<Map<String, Object>> list = orderAutLogService
				.getOrderAndConfirmLogListMap(orderId, confirmID);
		return jsonView(true, "成功获取抢和运单单日志列表。", list);
	}

	/**
	 * 功能描述： 获取待审核货物列表 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * size 输入参数: @param headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * aiqiwu 日 期: 2017-07-24 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getWaitOrdersReview", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getWaitOrdersReview(GoodsBasic goodsBasic, String keys,
			int start, int size) {
		Account account = AppUtil.getLoginUser();
		
		Map<String, Object> map = goodsBasicService.getWaitOrdersReview(
				goodsBasic, account, start, size);
		return jsonView(true, "成功获取我的货物审阅列表。", map);
	}

	/**
	 * 功能描述： 获取待审核货物列表 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * size 输入参数: @param headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * aiqiwu 日 期: 2017-07-24 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getAlreadyOrdersReview", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAlreadyOrdersReview(GoodsBasic goodsBasic,
			int start, int size, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = goodsBasicService.getAlreadyOrdersReview(
				goodsBasic, account, start, size);
		return jsonView(true, "成功获取我的货物审阅列表。", map);
	}

	/**
	 * 功能描述： 获取货物下的抢单列表 输入参数: @param goodsId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期: 2017年07月25日上午11:05:27 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getWaitOrdersReviewDetails", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getWaitOrdersReviewDetails(RobOrderRecord order,
			int start, int size, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Map<String, Object> map = orderRecordService
				.getWaitOrdersReviewDetails(order, start, size);
		Map<String, Object> own = robOrderRecordOwnService
				.consignorCanVerifyGoodsBasic(order.getGoodsBasic().getId());
		return super.jsonView(true, "成功获取货物抢单列表。" ,  map, own);
		 
	}

	/**
	 * 功能描述： 获取货物下的抢单列表 输入参数: @param goodsId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期: 2017年07月25日上午11:05:27 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getAlreadyOrdersReviewDetails", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAlreadyOrdersReviewDetails(
			RobOrderRecord order, int start, int size,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Map<String, Object> map = orderRecordService
				.getAlreadyOrdersReviewDetails(order, start, size);
		Map<String, Object> own = robOrderRecordOwnService
				.consignorCanVerifyGoodsBasic(order.getGoodsBasic().getId());
		return jsonView(true, "成功获取货物抢单列表。", map, own);
	}
	
	/**
	 * 获取所有押金档位
	 * */
	@RequestMapping(value = "getDeposit_level", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> getDeposit_level(@RequestHeader HttpHeaders headers,
			HttpServletRequest request){
		
		Map<String, Object> map=new HashMap<>();
		map.put("list", depositLevelService.getDepositLevelList());
		return jsonView(true, "成功获取押金档位。", map);
	}
	
//	/**
//	 * 设置订单的押金档位
//	 * */
//	@RequestMapping(value = "setDeposit_level_money", method = RequestMethod.POST)
//	@ResponseBody
//	@AuthInterceptor(capitalValid = true)
//	protected Map<String, Object> setDeposit_level_money( @RequestHeader HttpHeaders headers,
//			HttpServletRequest request,RobOrderRecord order,Double level_money){
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		String id=order.getId();
//		order = orderRecordService.getRecordById(order.getId());
//		Account acount = UserUtil.getAccount();
//		if (!acount.getId().equals(order.getGoodsBasic().getAccount().getId())) {
//			throw new BusinessException("你不能处理别人的发货抢单。");
//		}
//
//		if (!order.getStatus().equals(RobOrderRecord.Status.apply)
//				&& !order.getStatus().equals(RobOrderRecord.Status.dealing)) {
//			throw new BusinessException("请选择申请处理的数据进行处理。");
//		}
//		order.setDeposit_level_money(level_money);
//		orderRecordService.updateRecord(order);
//		return jsonView(true, "档位设置成功");
//	}
	
	/**
	 * 获取相应的押金支付信息
	 * */
	@RequestMapping(value = "getOrderPayInfoByRobRecordId", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor(capitalValid = true)
	protected Map<String, Object> getOrderPayInfoByRobRecordId(
			RobOrderRecord order, @RequestHeader HttpHeaders headers,
			HttpServletRequest request , Double deposit_level_money) {
		Map<String, Object> map = new HashMap<String, Object>();
		order = orderRecordService.getRecordById(order.getId());
		Account acount = UserUtil.getAccount();
		if (!acount.getId().equals(order.getGoodsBasic().getAccount().getId())) {
			throw new BusinessException("你不能处理别人的发货抢单。");
		}

		if (!order.getStatus().equals(RobOrderRecord.Status.apply)
				&& !order.getStatus().equals(RobOrderRecord.Status.dealing)) {
			throw new BusinessException("请选择申请处理的数据进行处理。");
		}
		
		order.setDeposit_level_money(deposit_level_money);
		
		ParameterManage parameterManage = parameterManageService
				.getTypeInfo(ParaType.consignor);
		CapitalAccount capAccount = capitalAccountService
				.getCapitalAccount(acount.getId());

		// order.setStatus(RobOrderRecord.Status.dealing);
		orderRecordService.updateRecord(order);
		BigDecimal bd = new BigDecimal(deposit_level_money);
		//map.put("payMoney", bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		map.put("payMoney",bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		map.put("avaiable", capAccount.getAvaiable());
		return jsonView(true, "成功获取订单支付信息。", map);
	}

	/**
	 * 审核抢单
	 * */
	@RequestMapping(value = "confirmPayRobOrderPrepayments", method = RequestMethod.POST)
	@ResponseBody
	@LockInterceptor
	@AuthInterceptor(capitalValid=true)
	public Map<String, Object> confirmPayRobOrderPrepayments(
			RobOrderRecord record, String payPassword) {
		try {
			String ret = orderRecordService.updateStatus2(record, payPassword);
			if (StringUtil.isNotEmpty(ret)) {
				return jsonView(false, ret);
			}

		} catch (BusinessException e) {
			// TODO: handle exception
			return jsonView(false, e.getMessage());
		}
		return jsonView(true, "审核成功");
	}

	/**
	 * 功能描述：获取我的抢单记录详细: @param goodsId 输入参数: @param headers 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: lil 日 期: 2017年07月25日上午11:05:27 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "getMyRobOrderDetails", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getMyRoborderDetails(RobOrderRecord order,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Map<String, Object> map = orderRecordService
				.getMyRobOrderDetails(order);
		Map<String, Object> own = robOrderRecordOwnService
				.truckCanViewRobOrderRecord(order.getId());
		return jsonView(true, "成功获取抢单记录详细。", map, own);
	}
}
