package com.memory.platform.module.app.order.controller;

import java.io.UnsupportedEncodingException;
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

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.interceptor.LockInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.order.service.IRobOrderRecordInfoService;
import com.memory.platform.module.order.service.IRobOrderRecordService;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年8月8日 下午12:22:09 修 改 人： 日 期： 描 述： 抢单业务接口 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("app/roborder/")

public class RobOrderController extends BaseController {
	@Autowired
	IRobOrderRecordService robOrderRecordService;// 抢单业务接口

	@Autowired
	IRobOrderRecordInfoService robOrderRecordInfoService;// 抢单详细货物业务接口

	/**
	 * 功能描述： 保存抢单信息 输入参数: @param record 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月8日下午4:31:37 修 改
	 * 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "saveRobOrder", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> saveRobOrder(RobOrderRecord record,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> ret = robOrderRecordService.saveRecordForMap(
				record, account);
		return ret;
	}

	/**
	 * 功能描述： 我的抢单查询 输入参数: @param robOrderRecord
	 * 抢单查询条件（consigneeAreaName（收获地区名称），deliveryAreaName（发货地区名称），status（状态））
	 * 输入参数: @param start （起始页） 输入参数: @param size （数据条数） 输入参数: @param headers
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月8日下午5:06:21 修 改 人: 日 期: 返 回：Map<String,Object>
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getMyRobOrderPage")
	@ResponseBody
	public Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord,
			int start, int size, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) throws UnsupportedEncodingException {
		Account account = (Account) request.getAttribute(ACCOUNT);
		if (StringUtil.isNotEmpty(robOrderRecord.getDeliveryAreaName())) {
			try {
				robOrderRecord.setDeliveryAreaName(new String(robOrderRecord
						.getDeliveryAreaName().getBytes(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String, Object> map = robOrderRecordService.getMyPage(
				robOrderRecord, account, start, size);
		List<Map<String, Object>> lst = robOrderRecordService
				.getAccountRobOrderCount(account.getId());
		map.put("status", lst.get(0));
		return jsonView(true, "查询我的抢单信息成功!", map);
	}

	/**
	 * 功能描述： 取消订单 输入参数: @param robOrderRecord 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月10日下午7:02:20 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/cancelRobOrder")
	@ResponseBody
	@LockInterceptor
	@AuthInterceptor
	public Map<String, Object> saveRobOrderCancel(
			RobOrderRecord robOrderRecord, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		RobOrderRecord robOrderRecord_p = robOrderRecordService
				.getRecordById(robOrderRecord.getId());
		if (!robOrderRecord_p.getAccount().getId().equals(account.getId())) {
			return jsonView(false, "您取消的抢单，不是自己的，无法取消！");
		}
		if (!robOrderRecord_p.getStatus().toString().equals("apply")) {
			String msg = "当前抢单信息，";
			switch (robOrderRecord_p.getStatus().toString()) {
			case "dealing":
				msg = msg + "货主正在处理中，无法取消！";
				return jsonView(false, msg);
			case "scrap":
				msg = msg + "已取消该订单，无需重复取消！";
				return jsonView(false, msg);
			case "end":
				msg = "当前抢单已生成订单，无法取消该抢单！";
				return jsonView(false, msg);
			case "ordercompletion":
				msg = "当前订单已完结，无法取消该订单！";
				return jsonView(false, msg);

			}
		}
		if (robOrderRecordService.getRobOrderCancelByDay(account.getId()) >= 500) {
			return jsonView(false, "取消失败，每天只能取消500次抢单！");
		} else {
			robOrderRecordService.updateRobOrderCancel(robOrderRecord, account);
		}
		return jsonView(true, "取消抢单成功！");
	}

	/**
	 * 功能描述： 撤回抢单 输入参数: @param id 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月10日下午9:18:08 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/withdrawRobOrder")
	@ResponseBody
	public Map<String, Object> withdrawRobOrder(String id,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account user = (Account) request.getAttribute(ACCOUNT);
		RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		if (!user.getId().equals(robOrderRecord.getAccount().getId())) {
			return jsonView(false, "您所撤回的抢单，不是自己的，不能进行撤回操作！");
		}
		if (!robOrderRecord.getStatus().toString().equals("apply")) {
			String msg = "当前抢单信息，";
			switch (robOrderRecord.getStatus().toString()) {
			case "dealing":
				msg = msg + "管理员正在处理，无法撤回！";
				break;
			case "back":
				msg = msg + "已处于【退回状态】，无法撤回，请刷新页面查看状态！";
				break;
			case "success":
				msg = msg + "已经通过审核，无法撤回，请返回 的抢单查看状态！";
				break;
			case "scrap":
				msg = msg + "已作废，无法撤回，请刷新页面查看状态！";
				break;
			case "withdraw":
				msg = msg + "已撤回，无需重复操作！";
				break;
			}
			return jsonView(false, msg);
		}
		robOrderRecordService.updateWithdrawRobOrder(robOrderRecord, user);
		return jsonView(true, "抢单信息撤回成功！");
	}

	/**
	 * 功能描述： 获取抢单详细信息 输入参数: @param goodsBasicId 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月11日上午10:43:50 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getRobOrderRecordById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRobOrderRecordById(String id,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = robOrderRecordService.getRobOrderRecordById(
				id, account);
		return jsonView(true, "抢单详细信息查询成功!", map);
	}

	/**
	 * 功能描述： 修改抢单信息 输入参数: @param robOrderRecord 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月11日下午4:21:28 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/updateRobOrderRecord")
	@ResponseBody
	public Map<String, Object> updateRobOrderRecord(
			RobOrderRecord robOrderRecord, HttpServletRequest request) {
		boolean isfalse = true;
		String msg = "";
		Account account = (Account) request.getAttribute(ACCOUNT);
		RobOrderRecord robOrderRecord_p = robOrderRecordService
				.getRecordById(robOrderRecord.getId());
		if (!account.getId().equals(robOrderRecord_p.getAccount().getId())) {
			return jsonView(false, "您所修改的抢单，不是自己的，不能进行修改！");
		}
		if (robOrderRecord_p.getStatus().toString().equals("apply")) {
			isfalse = false;
			msg = "当前抢单信息，正在等待货主审核，无法修改，如需修改，请撤回抢单申请！";
			return jsonView(isfalse, msg, null);
		}
		if (robOrderRecord_p.getStatus().toString().equals("dealing")) {
			isfalse = false;
			msg = "当前抢单信息，正在确认中，无法修改！";
			return jsonView(isfalse, msg, null);
		}
		if (robOrderRecord_p.getStatus().toString().equals("success")) {
			isfalse = false;
			msg = "当前抢单信息，已经通过审核，无法修改！";
			return jsonView(isfalse, msg, null);
		}
		if (robOrderRecord_p.getStatus().toString().equals("scrap")) {
			isfalse = false;
			msg = "当前抢单信息，已经取消，无法修改！";
			return jsonView(isfalse, msg, null);
		}
		robOrderRecordService.updateRobOrderRecord(robOrderRecord, account);
		return jsonView(true, "抢单修改，已提交给货主，等待货主确认！", null);
	}

	/**
	 * 功能描述： 获取被拆分的货物列表 输入参数: @param id 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月13日下午3:38:14 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getRobOrderRecordInfoSplitList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRobOrderRecordInfoSplitList(String id,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = robOrderRecordService
				.getRobOrderRecordIndoSplitList(id, account);
		return jsonView(true, "获取货物拆分数据成功!", map);
	}

	/**
	 * 功能描述： 保存拆分的货物数据 输入参数: @param robOrderId 输入参数: @param robOrderRecordInfoId
	 * 输入参数: @param surplusWeight 输入参数: @param splitRobOrderRecordInfo 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月13日下午4:04:55 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/saveRobOrderSplit")
	@ResponseBody
	public Map<String, Object> saveRobOrderSplit(String robOrderId,
			String robOrderRecordInfoId, Double surplusWeight,
			String splitRobOrderRecordInfo, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		return robOrderRecordInfoService.saveRobOrderSplit(robOrderId,
				robOrderRecordInfoId, surplusWeight, splitRobOrderRecordInfo,
				account);
	}

	/**
	 * 功能描述： 查询可以分配的车辆列表 输入参数: @param headers 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月14日下午3:08:58 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	@RequestMapping("/getCompanyTrucks")
	@ResponseBody
	public Map<String, Object> getCompanyTrucks(
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		return jsonView(true, "获取车辆信息成功!",
				robOrderRecordService.getCompanyTrucks(account.getCompany()
						.getId()));
	}

	/**
	 * 功能描述： 抢单确认信息保存 输入参数: @param robOrderRecord //抢单对象，分车对象 输入参数: @param
	 * payPassword //支付密码 输入参数: @param turckIds 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月14日下午12:03:29 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/confirmRobOrderRecord")
	@ResponseBody
	public Map<String, Object> confirmRobOrderRecord(
			RobOrderRecord robOrderRecord, String payPassword,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		boolean isfalse = true;
		String msg = "";
		RobOrderRecord robOrderRecord_p = robOrderRecordService
				.getRecordById(robOrderRecord.getId());
		if (!robOrderRecord_p.getAccount().getId().equals(account.getId())) {
			isfalse = false;
			msg = "当前抢单不是你自己的，无法确认！";
			return jsonView(isfalse, msg);
		}
		if (robOrderRecord_p.getStatus().toString().equals("apply")) {
			isfalse = false;
			msg = "当前抢单信息，正在等待货主审核，无法确认！";
			return jsonView(isfalse, msg);
		}
		if (robOrderRecord_p.getStatus().toString().equals("dealing")) {
			isfalse = false;
			msg = "当前抢单信息，货主正在确认中，无法确认！";
			return jsonView(isfalse, msg);
		}
		if (robOrderRecord_p.getStatus().toString().equals("scrap")) {
			isfalse = false;
			msg = "当前抢单信息，已经取消，无法确认！";
			return jsonView(isfalse, msg);
		}
		if (robOrderRecord_p.getStatus().toString().equals("back")) {
			isfalse = false;
			msg = "当前抢单信息，未通过货主审核，无法确认！";
			return jsonView(isfalse, msg);
		}
		if (robOrderRecord_p.getStatus().toString().equals("withdraw")) {
			isfalse = false;
			msg = "当前抢单信息，未通过货主审核，无法确认！";
			return jsonView(isfalse, msg);
		}
		if (robOrderRecord_p.getStatus().toString().equals("end")) {
			isfalse = false;
			msg = "当前抢单信息，已被确认，无需重复确认！";
			return jsonView(isfalse, msg);
		}

		try {
			robOrderRecordService.saveConfirmRobOrder(robOrderRecord,
					payPassword, account);
		} catch (BusinessException e) {
			return jsonView(false, e.getMessage());
		}
		return jsonView(true, "抢单确认成功，已生成订单！");
	}

	@RequestMapping("/getMyRobOrderPageNew")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getMyRobOrderPageNew(
			RobOrderRecord robOrderRecord, int start, int size,
			@RequestHeader HttpHeaders headers, HttpServletRequest request)
			throws UnsupportedEncodingException {
		Account account = UserUtil.getAccount();
		if (StringUtil.isNotEmpty(robOrderRecord.getDeliveryAreaName())) {
			try {
				robOrderRecord.setDeliveryAreaName(new String(robOrderRecord
						.getDeliveryAreaName().getBytes(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String, Object> map = robOrderRecordService.getMyPageNew(
				robOrderRecord, account, start, size);
		map = map==null?new HashMap<String, Object>():map;
		List<Map<String, Object>> lst = robOrderRecordService
				.getAccountRobOrderCountNew(account.getId());
		map.put("status", lst.get(0));
		return jsonView(true, "查询我的抢单信息成功!", map);
	}

	@RequestMapping("/successRoborderRecord")
	@ResponseBody
	@LockInterceptor
	@AuthInterceptor(capitalValid = true)
	public Map<String, Object> successRoborderRecord(
			String splitRoborderRecordInfoJson, String payPassword) {

		Map<String, Object> map = robOrderRecordService.saveSuccessRobOrderNew(
				splitRoborderRecordInfoJson, payPassword);
		map.put(SUCCESS, true);
		map.put(MESSAGE, "订单确认成功");
		return map;
	}

	// 获取车辆调配的押金
	@RequestMapping("/getSuccessRobOrderPayment")
	@ResponseBody
	@AuthInterceptor(capitalValid = true)
	public Map<String, Object> getSuccessRobOrderPayment(
			String splitRoborderRecordInfoJson, int truckCount,String roborderRecordId) {

		
		
		Map<String, Object> map = robOrderRecordService
				.getSuccessRobOrderPayment(splitRoborderRecordInfoJson,
						truckCount,roborderRecordId);
		
		return jsonView(true, "获取车队押金成功", map);
	}
}
