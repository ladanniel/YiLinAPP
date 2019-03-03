package com.memory.platform.module.app.map.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.gps.VechicleGps;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.trace.service.IGaoDeWebService;
import com.memory.platform.module.trace.service.IYHTGpsService;
import com.memory.platform.module.trace.service.impl.YHTGpsService;
import com.memory.platform.module.truck.service.ITruckService;

@Controller
@RequestMapping("app/map/")
public class MapController extends BaseController {
	@Autowired
	IGaoDeWebService gaodeSvr;
	@Autowired
	IGoodsBasicService goodsBasicService;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	ITruckService truckService;
	@Autowired
	IYHTGpsService gpsSvr;

	/*
	 * 根据goodsBasic 获取出发地区 和收货地区 驾驶路径 strage 0，不考虑当时路况，返回耗时最短的路线，但是此路线不一定距离最短
	 * 1，不走收费路段，且耗时最少的路线
	 */
	@RequestMapping("/getGoodsBasicDrivingPath")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getGoodsBasicDrivingPath(String goodsBasicID,
			Integer strage) {

		return jsonView(true, "获取驾驶路径成功",
				goodsBasicService.getGoodsBasicDrivingPath(goodsBasicID,
						strage == null ? 0 : strage.intValue()));
	}

	/*
	 * 根据运单驾驶员 获取驾驶员车辆当前位置
	 */
	@RequestMapping("/getDriverPositionWithConfirmID")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getDriverPositionWithConfirmID(String confirmID) {
		RobOrderConfirm confirm = robOrderConfirmService
				.getRobOrderConfirmById(confirmID);
		if (confirm == null) {
			throw new BusinessException("该运单不存在");
		}
		Truck truck = truckService.getTruckById(confirm.getTurckId());

		String deviceID = truck.getGps_device_id();
		if (StringUtil.isEmpty(deviceID)) {
			throw new BusinessException("未能得到车辆GPS设备");
		}
		Account truckAccount = truck.getAccount();
		VechicleGps gpsData = gpsSvr.getGpsDataWithDeviceID(deviceID);
		if(truckAccount!=null) {
			gpsData.setDriverName(truckAccount.getName());
		}
		gpsData.setCardNo(truck.getTrack_no());
		return jsonView(true, "", gpsData);

	}
	/*
	 * 获取当前登录用户商户下所有车辆GPS 信息
	 * */
	@RequestMapping("/getCompanyGpsDataWithMyAccountID")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getCompanyGpsDataWithMyAccountID() {
		Account myAccount = UserUtil.getAccount();
		List<VechicleGps> ret =  truckService.getCompamyGpsData(myAccount.getCompany().getId());
		return jsonView(true,	"",ret);
	}
}
