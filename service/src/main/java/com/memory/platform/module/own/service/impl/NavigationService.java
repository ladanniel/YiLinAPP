package com.memory.platform.module.own.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.own.service.IGoodsBasicOwnService;
import com.memory.platform.module.own.service.INavigationService;
import com.memory.platform.module.own.service.IRobOrderConfirmOwnService;
import com.memory.platform.module.own.service.IRobOrderRecordOwnService;

/**
 * 导航 根据goodsBasicID roborderConfirmID roborderRecordID 指导用户下一步干什么 用在二维码 和通知
 * 
 * @author lil
 *
 */
@Service
public class NavigationService implements INavigationService {
	public static final String action = "action";
	public static final String actionMsg = "actionMsg";

	public static enum ActionType {
		ActionError, // 不能操作 比如发货的货物与当前的操作人不匹配
		ActionGoodsBasicOperation, // 货物详情操作
		ActionRobOrderVerifyWait, // 抢单审核待审核
		ActionRobOrderVerifyAlready, // 抢单已审核
		ActionRobOrderRecordOperation, // 抢单记录页面
		ActionRobOrderConfirmOperation, // 运单操作
	}

	@Autowired
	IGoodsBasicOwnService goodsBasicOwnService;
	@Autowired
	IRobOrderRecordOwnService robOrderRecordOwnService;
	@Autowired
	IRobOrderConfirmOwnService robOrderConfirmOwnService;
	@Autowired
	IRobOrderRecordService robOrderRecordService;
	@Autowired
	IGoodsBasicService goodsBasicService;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;

	BaseOwnService baseOwnService = new BaseOwnService();

	@Override
	public Map<String, Object> getGoodsBasicNavigationAction(String goodsBasicID) {

		Map<String, Object> goodsOwn = goodsBasicOwnService
				.getMyOwnWithGoodsBasicID(goodsBasicID);
		goodsOwn.put(action, ActionType.ActionGoodsBasicOperation.ordinal());
		goodsOwn.put("goods_basic_id", goodsBasicID);
		return goodsOwn;
	}

	@Override
	public Map<String, Object> getRobOrderRecordNavigationAction(
			String robOrderRecordID) {
		Map<String, Object> ret = new HashMap<>();
		do {
			Account account = UserUtil.getAccount();
			RobOrderRecord robOrderRecord = robOrderRecordService
					.getRecordById(robOrderRecordID);
			if (robOrderRecord == null) {
				ret.put(action, ActionType.ActionError.ordinal());
				ret.put(actionMsg, "未找到此抢单");
				break;
			}
			GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
			if (baseOwnService.isConsignor()) { // 货主

				Map<String, Object> map = robOrderRecordOwnService
						.consignorCanVerifyGoodsBasic(goodsBasic.getId());
				Boolean canVerifyRobOrderRecord = Boolean.parseBoolean(map.get(
						RobOrderRecordOwnService.canVerifyRobOrderRecord)
						.toString());
				if (false == canVerifyRobOrderRecord.booleanValue()) {
					ret.put(action, ActionType.ActionError.ordinal());
					ret.put(actionMsg, "无权限操作");
					break;
				}
				ret.put("goods_basic_id", goodsBasic.getId());
				ret.put("rob_order_record_id", robOrderRecordID);
				// 如果此抢单在申请或者操作中 待审核页面
				if (robOrderRecord.getStatus().equals(
						RobOrderRecord.Status.apply)
						|| robOrderRecord.getStatus().equals(
								RobOrderRecord.Status.dealing)) {
					ret.put(action,
							ActionType.ActionRobOrderVerifyWait.ordinal());

					break;
				}
				if (robOrderRecord.getStatus()
						.equals(RobOrderRecord.Status.end)) { // 此抢单已经完结
																// 跳转到此抢单的运单
					List<RobOrderConfirm> confirms = robOrderConfirmService
							.getRobOrderConfirmListByRobOrderID(robOrderRecordID);
					RobOrderConfirm confirm = this
							.getMinStatusConfirm(confirms); // 获取状态最小的Confirm
					RobOrderConfirm.QueryStatus queryStatus = RobOrderConfirm
							.getQueryStatusWithStatus(confirm.getStatus());
					ret.put(action,
							ActionType.ActionRobOrderConfirmOperation.ordinal());
					ret.put("queryStatus", queryStatus.ordinal());
					break;
				}

				ret.put(action,
						ActionType.ActionRobOrderVerifyAlready.ordinal());
				break;
			}

			if (baseOwnService.isTruck() || baseOwnService.isDriver()) { // 车队
				Map<String, Object> map = robOrderRecordOwnService
						.truckCanViewRobOrderRecord(robOrderRecordID);
				Boolean canTruckViewRobOrderRecord = Boolean
						.parseBoolean(map
								.get(RobOrderRecordOwnService.canTruckViewRobOrderRecord)
								.toString());
				ret.put("rob_order_record_id", robOrderRecordID);
				ret.put("goods_basic_id", goodsBasic.getId());
				if (canTruckViewRobOrderRecord.booleanValue() == false) {
					ret.put(action, ActionType.ActionError.ordinal());
					ret.put(actionMsg, "无权限操作");
					break;
				}

				if (robOrderRecord.getStatus()
						.equals(RobOrderRecord.Status.end)) { // 此抢单已经完结
																// 跳转到此抢单的运单
					List<RobOrderConfirm> confirms = robOrderConfirmService
							.getRobOrderConfirmListByRobOrderID(robOrderRecordID);
					RobOrderConfirm confirm = this
							.getMinStatusConfirm(confirms); // 获取状态最小的Confirm
					RobOrderConfirm.QueryStatus queryStatus = RobOrderConfirm
							.getQueryStatusWithStatus(confirm.getStatus());
					ret.put(action,
							ActionType.ActionRobOrderConfirmOperation.ordinal());
					ret.put("queryStatus", queryStatus.ordinal());
					break;
				} else {

					ret.put(action,
							ActionType.ActionRobOrderRecordOperation.ordinal());

					RobOrderRecord.QueryStatus queryStatus = RobOrderRecord
							.getQueryStatusWithStatus(robOrderRecord
									.getStatus());
					ret.put("queryStatus", queryStatus.ordinal());
					break;

				}

			}
		} while (false);

		return ret;
	}

	@Override
	public Map<String, Object> getRobOrderRecordConfirmNavigationAction(
			String robOrderConfirmID) {
		Map<String, Object> ret = new HashMap<>();
		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmID);
		do {

			if (robOrderConfirm == null) {
				ret.put(action, ActionType.ActionError.ordinal());
				ret.put(actionMsg, "无此运单");
				break;
			}
			RobOrderRecord robOrderRecord = robOrderRecordService
					.getRecordById(robOrderConfirm.getRobOrderId());
			if (robOrderRecord == null) {
				ret.put(action, ActionType.ActionError.ordinal());
				ret.put(actionMsg, "无此抢单");
				break;

			}
			GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
			if (goodsBasic == null) {
				ret.put(action, ActionType.ActionError.ordinal());
				ret.put(actionMsg, "无此货物");
				break;

			}

			Map<String, Object> map = robOrderConfirmOwnService
					.getOwnWithGoodsBasic(goodsBasic.getId());
			Boolean boolean1 = Boolean.parseBoolean(map.get(
					RobOrderConfirmOwnService.canViewRobOrderConfirmGoodsBasic)
					.toString());
			if (boolean1.booleanValue() == false) {
				ret.put(action, ActionType.ActionError.ordinal());
				ret.put(actionMsg, "无权限操作");
				break;
			}
			ret.put(action, ActionType.ActionRobOrderConfirmOperation.ordinal());
			ret.put("goods_basic_id", goodsBasic.getId());
			ret.put("rob_order_confirm_id", robOrderConfirmID);
			RobOrderConfirm.QueryStatus queryStatus = RobOrderConfirm
					.getQueryStatusWithStatus(robOrderConfirm.getStatus());
			ret.put("queryStatus", queryStatus.ordinal());

		} while (false);

		return ret;

	}

	private RobOrderConfirm getMinStatusConfirm(List<RobOrderConfirm> confirms) {
		if (confirms == null || confirms.size() == 0)
			return null;
		if (confirms.size() == 1)
			return confirms.get(0);
		int status = 10000;
		RobOrderConfirm confirm = null;
		for (RobOrderConfirm robOrderConfirm : confirms) {
			if (robOrderConfirm.getStatus().ordinal() < status) {
				status = robOrderConfirm.getStatus().ordinal();
				confirm = robOrderConfirm;
			}
		}
		return confirm;
	}
}
