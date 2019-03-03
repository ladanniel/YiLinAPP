package com.memory.platform.module.own.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.ptg.AttrPtg.SpaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.LockStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialType;
import com.memory.platform.entity.order.RobOrderConfirm.Status;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.dao.GoodsBasicDao;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.dao.RobOrderConfirmDao;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.own.service.IRobOrderConfirmOwnService;

@Service
public class RobOrderConfirmOwnService extends BaseOwnService implements
		IRobOrderConfirmOwnService {
	// 是否可看此物品的运单
	public static final String canViewRobOrderConfirmGoodsBasic = "ownCanViewRobOrderConfirmGoodsBasic";
	/*
	 * //0:等待装货、1、确认装货、2:运输中 3:送达 4:回执发还中 5:送还回执中6:订单完结 7:销单 public enum Status{
	 * receiving
	 * ,confirmload,transit,delivered,receipt,confirmreceipt,ordercompletion
	 * ,singlepin }
	 */
	// 运单操作 装货，发货，送达，确认收货付款
	public static final String canReceiving = "ownCanReceiving"; // 装货
	public static final String canConfirmload = "ownCanConfirmload"; // 发货
	public static final String canDelivered = "ownCanDelivered"; // 送达
	public static final String canConfirmReceipt = "ownCanConfirmReceipt"; // 确认付款
	public static final String ownConfirmTip = "ownConfirmTip";// 操作提示
	public static final String canArbitration = "ownCanArbitration";// 仲裁
	@Autowired
	IRobOrderConfirmService robOrderConfirmSerivce;
	@Autowired
	IGoodsBasicService goodsBasicService;

	@Override
	public Map<String, Object> getOwnWithGoodsBasic(String goodsBasicID) {
		Map<String, Object> map = new HashMap<String, Object>();
		Account account = UserUtil.getAccount();
		GoodsBasic goodsBasic = goodsBasicService
				.getGoodsBasicById(goodsBasicID);
		do {

			if (goodsBasic == null) {
				map.put(canViewRobOrderConfirmGoodsBasic, false);
				break;
			}
			if (super.isConsignor()) { // 货主
				boolean b = goodsBasic.getAccount().getCompany().getId()
						.equals(account.getCompany().getId());
				map.put(canViewRobOrderConfirmGoodsBasic, b);
			} else if (super.isTruck() || super.isDriver()) { // 车队司机
				boolean b = robOrderConfirmSerivce.accountCanViewGoodsBasic(
						account, goodsBasicID);
				map.put(canViewRobOrderConfirmGoodsBasic, b);
			} else {

				map.put(canViewRobOrderConfirmGoodsBasic, false);
			}

		} while (false);

		return map;
	}

	/*
	 * 运单操作权限
	 * 
	 * @see com.memory.platform.module.own.service.IRobOrderConfirmOwnService#
	 * getOwnWithRobOrderConfirm(java.util.Map)
	 */
	@Override
	public Map<String, Object> getOwnWithRobOrderConfirm(
			Map<String, Object> confirmMap) {
		confirmMap.get("rob_order_confirm_status");
		Map<String, Object> map = new HashMap<String, Object>();
		int statusInteger = Integer.parseInt(confirmMap.get(
				"rob_order_confirm_status").toString());
		RobOrderConfirm.Status status = RobOrderConfirm.Status.values()[statusInteger];
		Account account = UserUtil.getAccount();
		String truck_user_id = confirmMap.get("turck_user_id").toString();
		map.put(ownConfirmTip, "无");
		if(status.equals(Status.singlepin)) {
			map.put(ownConfirmTip, "改运单已经消单");
			return map;
		}
		Integer specialTypeInt = (Integer) confirmMap.get("special_type");
		SpecialType specialType = SpecialType.none;
		Integer lockStatus = (Integer) confirmMap.get("lock_status");
		if (lockStatus != null && lockStatus.equals(LockStatus.locking)) {
			if (specialTypeInt != null) {
				specialType = SpecialType.values()[specialTypeInt.intValue()];
			}

			if (specialType != SpecialType.none) {
				// robOrderConfirmSerivce.geta(account, robOrderRecord, start,
				// limit)
				map.put(ownConfirmTip, "该运单已申请仲裁");
				return map;
			}
		}
		if (super.isConsignor()) { // 货主
			if (status == RobOrderConfirm.Status.confirmload) {
				map.put(canConfirmload, true);
				map.put(ownConfirmTip, "请确认发货,发货前请仔细查看实际吨位!");
			} else if (status == RobOrderConfirm.Status.receiving) {

				map.put(ownConfirmTip, "需司机为你装货后才能发货！");
			}
			if (status == RobOrderConfirm.Status.confirmreceipt) {
				map.put(canConfirmReceipt, true);
				Date date = (Date) confirmMap.get("confirm_receipted_date");
				String tip = "";
				if (date != null) {
					Date now = new Date();
					tip = DateUtil.getDistanceTime(now, date);
					if (StringUtil.isNotEmpty(tip)) {
						tip = "距" + tip + "自动付款。";
					} else {
						tip = "请确认付款";
					}
				}
				map.put(ownConfirmTip, tip);
			}
			if (status == RobOrderConfirm.Status.transit) {
				map.put(ownConfirmTip, "正在运输中！");
			}
			addArbitrationOwn(confirmMap, map, status);
		} else if ((super.isDriver() || super.isTruck())) {
			if (truck_user_id.equals(account.getId())) {
				addArbitrationOwn(confirmMap, map, status);
				if (status == RobOrderConfirm.Status.receiving) {
					map.put(canReceiving, true);
					map.put(ownConfirmTip, "确认装货！");
				} else if (status == RobOrderConfirm.Status.transit) {

					map.put(canDelivered, true);
					map.put(ownConfirmTip, "确认送达！");
				} else if (status == RobOrderConfirm.Status.confirmload) {
					map.put(ownConfirmTip, "请让货主确认发货！可点击下面的二维码让货主扫描发货！");
				}
			} else {

				map.put(ownConfirmTip, "您不是拉货司机不能操作！");
			}
			if (status == RobOrderConfirm.Status.confirmreceipt) {

				Date date = (Date) confirmMap.get("confirm_receipted_date");
				String tip = "";
				if (date != null) {
					Date now = new Date();
					tip = DateUtil.getDistanceTime(now, date);
					if (StringUtil.isNotEmpty(tip)) {
						tip = "距" + tip + "自动付款。";
					}
				}
				map.put(ownConfirmTip, tip);
			}
		}

		return map;
	}

	private void addArbitrationOwn(Map<String, Object> confirmMap,
			Map<String, Object> map, RobOrderConfirm.Status status) {
		if (status != RobOrderConfirm.Status.ordercompletion) {
			Integer lockStatus = (Integer) confirmMap.get("lock_status");
			if (lockStatus == null
					|| lockStatus.equals(LockStatus.unlock.ordinal())) {
				map.put(canArbitration, true);
				return;

			}

			Integer number = (Integer) confirmMap.get("special_type");
			boolean isArbitration = false;
			if (number == null) {
				isArbitration = true;

			} else {
				SpecialType type = RobOrderConfirm.SpecialType.values()[number
						.intValue()];
				isArbitration = type == SpecialType.none;

			}
			map.put(canArbitration, isArbitration);
		}
	}
}
