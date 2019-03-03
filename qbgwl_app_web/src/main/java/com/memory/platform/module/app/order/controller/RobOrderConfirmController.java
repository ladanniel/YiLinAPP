package com.memory.platform.module.app.order.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.search.DateTerm;
import javax.servlet.http.HttpServletRequest;

import jnr.ffi.Struct.int16_t;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.RoleType;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.LockStatus;
import com.memory.platform.entity.order.RobOrderConfirm.PaymentType;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialType;
import com.memory.platform.entity.order.RobOrderConfirm.Status;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.LgisticsCompanies;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DoubleHelper;
import com.memory.platform.global.EHCacheUtil;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.OSSClientUtil;
import com.memory.platform.global.SMSUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.SMSUtil.CachNameType;
import com.memory.platform.global.StatusMap;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.interceptor.LockInterceptor;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IOrderWaybillLogService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.system.service.ILgisticsCompaniesService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.truck.service.ITruckService;

/**
 * 创 建 人： 武国庆 日 期： 2016年6月17日 上午10:45:32 修 改 人： 日 期： 描 述：我的订单控制器 版 本 号： V1.0
 */
@Controller
@RequestMapping("app/order/myorder")
public class RobOrderConfirmController extends BaseController {

	@Autowired
	IRobOrderConfirmService robOrderConfirmService;

	@Autowired
	IRobOrderRecordService robOrderRecordService;

	@Autowired
	private ISendMessageService sendMessageService;

	@Autowired
	private ILgisticsCompaniesService lgisticsCompaniesService;

	@Autowired
	IOrderAutLogService orderAutLogService;

	@Autowired
	ITruckService truckService;

	@Autowired
	IOrderWaybillLogService orderWaybillLogService;
	@Autowired
	ICapitalAccountService capitalAccountService;
	@Autowired
	private IGoodsBasicService goodsBasicService;

	/**
	 * 获取我的订单列表(司机)
	 * 
	 * @param goodsBasic
	 * @param start
	 * @param size
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getMyOredrListMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyOredrListMap(RobOrderRecord robOrderRecord,
			int start, int size, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = robOrderConfirmService.getOrderPage(
				robOrderRecord, account, start, size);
		return jsonView(true, "查询成功", map);
	}

	/**
	 * 获取商户订单
	 * 
	 * @param goodsBasic
	 * @param start
	 * @param size
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getCompanyOredrList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCompanyOredrList(
			RobOrderRecord robOrderRecord, int start, int size,
			HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = robOrderConfirmService.getCompanyOredrList(
				robOrderRecord, account, start, size);
		return jsonView(true, "查询成功", map);
	}

	/**
	 * 获取订单拉货司机列表
	 * 
	 * @param robOrderId
	 *            订单ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getRobOrderConfirmList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRobOrderConfirmList(String robOrderId,
			HttpServletRequest request) {
		Account account = UserUtil.getAccount();
		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderId);
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
		map.put("deliveryAreaName", goodsBasic.getDeliveryAreaName());
		map.put("consigneeAreaName", goodsBasic.getConsigneeAreaName());
		map.put("deliveryCoordinate", goodsBasic.getDeliveryCoordinate());
		map.put("consigneeCoordinate", goodsBasic.getConsigneeCoordinate());
		map.put("mapKilometer", goodsBasic.getMapKilometer());
		map.put("weight", robOrderRecord.getWeight());
		map.put("unitPrice", robOrderRecord.getUnitPrice());
		map.put("companyName", robOrderRecord.getCompanyName());
		map.put("robOrderNo", robOrderRecord.getRobOrderNo());
		List<Map<String, Object>> truckList = robOrderConfirmService
				.getTruckList(robOrderId, account);
		map.put("truckList", truckList);

		return jsonView(true, "查询成功", map);
	}

	/**
	 * 获取我的运单详细
	 * 
	 * @param goodsBasic
	 * @param start
	 * @param size
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getRobOrderConfirmInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRobOrderConfirmInfo(String robOrderConfirmId,
			HttpServletRequest request) {
		Account account = UserUtil.getAccount();
		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "运单ID不能为空！", null);
		}
		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}
		Map<String, Object> map = robOrderConfirmService
				.getOrderConfirmInfo(robOrderConfirmId);
		String turckId = (String) map.get("turckId");
		String robOrderId = (String) map.get("robOrderId");
		double actualWeight = (double) map.get("actualWeight");
		if (actualWeight <= 0) {
			actualWeight = (double) map.get("totalWeight");
		}
		// lix 2017-08-18 添加 实际吨位数保存2位小数
		actualWeight = Double.parseDouble(String.format("%.2f", actualWeight));
		List<Map<String, Object>> goodsList = robOrderConfirmService
				.getOrderInfoPageForMap(robOrderId, turckId);
		map.put("goodsList", goodsList);
		map.put("mailingAddress", MAILING_ADDRESS);
		map.put("mailingUserName", MAILING_USERNAME);
		map.put("mailingPhone", MAILING_PHONE);
		map.put("actualWeight", actualWeight);
		map.put("msg",
				StatusMap.getRobOrderConfirmMsg(robOrderConfirm, account));
		map.put("specialMsg", StatusMap.getRobOrderConfirmSpecialMsg(
				robOrderConfirm, account));
		map.put("actualMoney",
				Double.valueOf(map.get("actualWeight").toString())
						* Double.valueOf(map.get("unitPrice").toString()));
		map.put("yunJia",
				Double.valueOf(map.get("unitPrice").toString())
						* Double.valueOf(map.get("actualWeight").toString())
						- Double.valueOf(map.get("transportationDeposit")
								.toString()));
		return jsonView(true, "查询成功", map);
	}

	/**
	 * 
	 * 功能描述： 确认发货 输入参数: @param robOrderConfirmId 确认订单ID 输入参数: @param
	 * actualWeight 实际拉货重量 输入参数: @param header s 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月29日下午3:24:19 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "confirmpullgoods", method = RequestMethod.POST)
	@ResponseBody
	@LockInterceptor
	@AuthInterceptor
	public Map<String, Object> confirmpullgoods(String robOrderConfirmId,
			String payPassword, Double actualWeight,
			String additionalExpensesRecordJson) {
		Account account = (Account) UserUtil.getAccount();

		if (!account.getCompany().getCompanyType().getName().equals("货主")) {
			return jsonView(false, "你不是货主无权访问该接口！", null);
		}
		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}
		if (actualWeight==null ||  actualWeight == 0) {

			actualWeight = robOrderConfirm.getActualWeight();
			if(actualWeight==0)
			return jsonView(false, "实际吨位为空！", null);
			
		}
		
		
		// lix 2017-08-18 添加 实际吨位数保存3位小数
		if (actualWeight != null && actualWeight != 0) {
			actualWeight = Double.parseDouble(String.format("%.3f",
					actualWeight));
		}
		// if (actualWeight == null) {
		// actualWeight = robOrderConfirm.getActualWeight();
		// if(actualWeight == 0 ) {
		//
		// return jsonView(false, "实际吨位为空！", null);
		// }
		//
		// }

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}
		if (StringUtils.isBlank(payPassword)) {
			return jsonView(false, "支付密码不能为空！", null);
		}

		if (robOrderConfirm.getStatus() != Status.confirmload) {
			return jsonView(false, "订单不是确认装货状态！", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (!robOrderRecord.getRobbedAccountId().equals(account.getId())) {
			return jsonView(false, "你无权处理该订单！", null);
		}

		if (!AppUtil.md5(payPassword).equals(account.getPaypassword())) {
			return jsonView(false, "支付密码错误！", null);
		}
		/*
		 * double totalMoney = robOrderConfirm.getUnitPrice() * actualWeight;
		 * double money = totalMoney -
		 * robOrderConfirm.getTransportationDeposit(); // 运输费等于 // 总运输费-运输押金
		 */// if(money<=0){
			// return jsonView(false,"实际重量过小",null);
			// }

		try {
			robOrderConfirmService.saveConfirmReleaseGoods(account,
					robOrderConfirm, robOrderRecord, actualWeight,
					additionalExpensesRecordJson);

		} catch (BusinessException e) {
			return jsonView(false, e.getMessage().toString(), null);
		}

		return jsonView(true, "操作成功!", null);
	}

	/**
	 * 
	 * 功能描述： 确认拉货 输入参数: @param robOrderConfirmId 确认订单ID 输入参数: @param
	 * actualWeight 实际拉货重量 输入参数: @param header s 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月29日下午3:24:19 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "confirmload", method = RequestMethod.POST)
	@ResponseBody
	@LockInterceptor
	@AuthInterceptor
	public Map<String, Object> confirmload(String robOrderConfirmId,
			Double actualWeight, HttpServletRequest request) {

		// lix 2017-08-18 添加 实际吨位数保存3位小数
		if (actualWeight != null && actualWeight != 0) {
			actualWeight = Double.parseDouble(String.format("%.3f",
					actualWeight));
		}
		Account account = (Account) request.getAttribute(ACCOUNT);

		if (!(account.getCompany().getCompanyType().getName().equals("车队") || account
				.getCompany().getCompanyType().getName().equals("个人司机"))) {
			return jsonView(false, "你不是司机无权访问该接口！", null);
		}

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}

		if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
			return jsonView(false, "你没有该订单！", null);
		}

		if (!robOrderConfirm.getStatus().equals(Status.receiving)) {
			return jsonView(false, "订单状态不是等待装货状态,不能确认装货!", null);
		}

		double totalMoney = robOrderConfirm.getUnitPrice() * actualWeight;
		double money = totalMoney - robOrderConfirm.getTransportationDeposit(); // 运输费等于
																				// 总运输费-运输押金
		// if(money<=0){
		// return jsonView(false,"实际重量过小",null);
		// }

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());
		robOrderConfirmService.saveConfirmload(account, robOrderRecord,
				robOrderConfirm, actualWeight);
		return jsonView(true, "操作成功!", null);
	}

	/*
	 * byLil 获取缓验证码存时间
	 */
	@RequestMapping(value = "getCachTime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCachTime(SMSUtil.CachNameType cachType,
			String key, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {

		Map<String, Object> retMap = SMSUtil.canSendPhoneCode(cachType, key);
		// if( (Boolean)retMap.get(SMSUtil.SUCCESS) == true) {
		// retMap.put(SMSUtil.surplusSecond, 0);
		// }
		return jsonView(true, "操作成功!", retMap);
	}

	/**
	 * 
	 * 功能描述： 确认收货发送验证码 输入参数: @param robOrderConfirmId 确认订单ID 输入参数: @param
	 * headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年7月29日下午3:24:19 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "confirmreceiptsend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmreceiptsend(String robOrderConfirmId,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);

		if (!(account.getCompany().getCompanyType().getName().equals("车队") || account
				.getCompany().getCompanyType().getName().equals("个人司机"))) {
			return jsonView(false, "你不是司机无权访问该接口！", null);
		}

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}

		if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
			return jsonView(false, "你没有该订单！", null);
		}

		if (!robOrderConfirm.getStatus().equals(Status.transit)) {
			return jsonView(false, "订单状态不在运输中,不能发送收货确认!", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());
		GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
		String consigneeMobile = goodsBasic.getConsigneeMobile();// 收货人联系电话

		// 发送收货验证码
		String code = AppUtil.random(6).toString();
		Map<String, Object> map = SMSUtil.canSendPhoneCode(
				SMSUtil.CachNameType.receiptTimeOut, robOrderConfirmId);
		Boolean flag = (Boolean) map.get(SUCCESS);
		if (flag) {
			Truck turck = truckService.getTruckById(robOrderConfirm
					.getTurckId());
			String msg = "您好，您的收货验证码是:" + code + ",请在确认卸货完成后将该验证码告知驾驶员。车牌号:"
					+ turck.getTrack_no() + " 驾驶员:" + account.getName()
					+ " 联系电话：" + account.getPhone();
			sendMessageService
					.saveRecordAndSendMessage(
							consigneeMobile,
							msg,
							"com.memory.platform.module.order.controller.RobOrderConfirmController.confirmreceiptsend");

			CacheManager cacheManager = EHCacheUtil
					.initCacheManager(EHCacheUtil.ehcachePath);
			Cache cacheCode = cacheManager.getCache("receiptTimeOut");
			Element element = new Element(robOrderConfirmId, code);

			cacheCode.put(element);
			map = SMSUtil.canSendPhoneCode(SMSUtil.CachNameType.receiptTimeOut,
					robOrderConfirmId);
			return jsonView(true, "发送成功!", map);
		}
		return jsonView(false, (String) map.get(MESSAGE), map);
	}

	/**
	 * 
	 * 功能描述： 确认收货 输入参数: @param robOrderConfirmId 确认订单ID 输入参数: @param headers
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年7月29日下午3:24:19 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "confirmreceipt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmreceipt(String robOrderConfirmId,
			String code, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Account account = UserUtil.getAccount();

		if (!(account.getCompany().getCompanyType().getName().equals("车队") || account
				.getCompany().getCompanyType().getName().equals("个人司机"))) {
			return jsonView(false, "你不是司机无权访问该接口！", null);
		}

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);

		if (robOrderConfirm == null) {
			return jsonView(false, "运单不存在！", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
			return jsonView(false, "你没有该运单！", null);
		}

		if (!robOrderConfirm.getStatus().equals(Status.transit)) {
			return jsonView(false, "订单状态不在运输中,不能发送收货确认!", null);
		}
		if (StringUtil.isEmpty(code)) {
			throw new BusinessException("验证码不能为空");
		}
		String result = SMSUtil.getSendCode("", CachNameType.receiptTimeOut,
				robOrderConfirmId);
		if (StringUtil.isEmpty(result)) {

			throw new BusinessException("验证码不存在或者已经过期,情重新发送");
		}
		/*
		 * CacheManager cacheManager = EHCacheUtil
		 * .initCacheManager(EHCacheUtil.ehcachePath); Cache cacheCode =
		 * cacheManager.getCache("receiptTimeOut"); Element result =
		 * cacheCode.get(robOrderConfirmId);
		 * 
		 * // 取验证错误次数 Element errorElement = cacheCode.get("error_" +
		 * robOrderConfirmId); Integer errorNum = errorElement == null ? 0 :
		 * (Integer) errorElement .getObjectValue();
		 * 
		 * if (errorElement != null && errorNum > 4) {
		 * cacheCode.remove(robOrderConfirmId); cacheCode.remove("error_" +
		 * robOrderConfirmId); return jsonView(false, "校验码错误次数过多，请重新获取!", null);
		 * }
		 * 
		 * if (result == null) { return jsonView(false, "验证码过期请重新发送!", null); }
		 */

		if (!code.equals(result)) {

			return jsonView(false, "验证码不正确!", null);
		}

		SMSUtil.deleteSendCode(null, CachNameType.receiptTimeOut,
				robOrderConfirmId);

		robOrderConfirmService.saveConfirmreCeipt(account, robOrderConfirm,
				robOrderRecord);

		return jsonView(true, "确认收货成功!", null);
	}

	/**
	 * 快递回执信息
	 * 
	 * @param robOrderConfirmId
	 *            确认订号ID
	 * @param lgisticsCode
	 *            物流公司编号
	 * @param lgisticsNum
	 *            物流单号
	 * @param headers
	 * @param request
	 * @return
	 */
	// @RequestMapping(value = "receipt", method = RequestMethod.POST)
	// @ResponseBody
	public Map<String, Object> receipt(String[] path, String robOrderConfirmId,
			String lgisticsCode, String lgisticsNum,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);

		if (!(account.getCompany().getCompanyType().getName().equals("车队") || account
				.getCompany().getCompanyType().getName().equals("个人司机"))) {
			return jsonView(false, "你不是司机无权访问该接口！", null);
		}

		if (path.length == 0) {
			return jsonView(false, "回执图片不能为空！", null);
		}

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);

		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
			return jsonView(false, "你没有该订单！", null);
		}

		if (!robOrderConfirm.getStatus().equals(Status.transit)) {
			return jsonView(false, "订单状态不在送达状态,不能寄送回执信息!", null);
		}

		LgisticsCompanies lgisticsCompanies = lgisticsCompaniesService
				.getLgisticsCompanies(lgisticsCode);
		if (lgisticsCompanies == null) {
			return jsonView(false, "没有找到物流公司信息，请确认物流公司编号是否正确！", null);
		}

		/*
		 * Lgistics lgistics =
		 * LgisticsUtil.getLgisticsNode(lgisticsCompanies.getCode(),lgisticsNum)
		 * ; if(lgistics.getStatus().equals(-3)){ return jsonView(false,
		 * lgistics.getMsg(),null); }
		 */

		String rootPath = AppUtil.getUpLoadPath(request);
		/*
		 * String dataType="originalReceiptImg"; List<String> path = new
		 * ArrayList<String>(); for(MultipartFile f:file){ try { String img_path
		 * = ImageFileUtil.uploadTemporary(f,AppUtil.getUpLoadPath(request),
		 * dataType); path.add(img_path); } catch (IOException e) { return
		 * jsonView(false, "上传图片异常请重试!",null); }
		 * 
		 * }
		 */
		List<String> pathList = Arrays.asList(path);
		robOrderConfirmService.saveReceipt(account, robOrderConfirm,
				robOrderRecord, lgisticsCompanies, lgisticsNum, pathList,
				rootPath);
		return jsonView(true, "回执信息寄送成功!", null);
	}

	/*
	 * 确认送达
	 */
	@RequestMapping(value = "successDelivery", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> successDelivery(String[] path,
			String robOrderConfirmId, String code, String lgisticsNum,double settlement_weight, //收货吨位（结算吨位）
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);

		if (!(account.getCompany().getCompanyType().getName().equals("车队") || account
				.getCompany().getCompanyType().getName().equals("个人司机"))) {
			return jsonView(false, "你不是司机无权访问该接口！", null);
		}

		if (path.length == 0) {
			return jsonView(false, "回执图片不能为空！", null);
		}

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);

		if (robOrderConfirm == null) {
			return jsonView(false, "运单不存在！", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
			return jsonView(false, "你没有该运单！", null);
		}

		if (!robOrderConfirm.getStatus().equals(Status.transit)) {
			return jsonView(false, "运单状态不在运输中,不能确认送达!", null);
		}

		// CacheManager cacheManager = EHCacheUtil
		// .initCacheManager(EHCacheUtil.ehcachePath);
		// Cache cacheCode = cacheManager.getCache("receiptTimeOut");
		// Element result = cacheCode.get(robOrderConfirmId);
		//
		// // 取验证错误次数
		// Element errorElement = cacheCode.get("error_" + robOrderConfirmId);
		// Integer errorNum = errorElement == null ? 0 : (Integer) errorElement
		// .getObjectValue();
		//
		// if (errorElement != null && errorNum > 4) {
		// cacheCode.remove(robOrderConfirmId);
		// cacheCode.remove("error_" + robOrderConfirmId);
		// return jsonView(false, "校验码错误次数过多，请重新获取!", null);
		// }
		//
		// if (result == null) {
		// return jsonView(false, "验证码过期请重新发送!", null);
		// }
		//
		// if (!code.equals(result.getObjectValue().toString())) {
		// // 验证错误次数
		// Element element = new Element("error_" + robOrderConfirmId,
		// errorNum + 1);
		// cacheCode.put(element);
		// return jsonView(false, "验证码不正确!", null);
		// }
		//
		// // 验证成功删除Key
		// cacheCode.remove(robOrderConfirmId);
		// cacheCode.remove("error_" + robOrderConfirmId);
		Map<String, Object> codeMap = SMSUtil.getSendCodeMap("",
				CachNameType.receiptTimeOut, robOrderConfirmId);
		String result = codeMap == null ? "" : codeMap.get(SMSUtil.code) + "";
		if (StringUtil.isEmpty(result)) {

			throw new BusinessException("验证码不存在或者已经过期,情重新发送");
		}

		if (!code.equals(result)) {

			int count = codeMap.containsKey("errorCount") == false ? 0
					: Integer.parseInt(codeMap.get("errorCount") + "");

			if (count >= 3) {
				SMSUtil.deleteSendCode(null, CachNameType.receiptTimeOut,
						robOrderConfirmId);
				throw new BusinessException("输入的验证码错误次数过多,情重新发送");
			}
			count++;
			codeMap.put("errorCount", count);
			SMSUtil.saveSendCodeMap(codeMap, robOrderConfirmId,
					CachNameType.receiptTimeOut);
			return jsonView(false, "验证码不正确!", null);
		}

		SMSUtil.deleteSendCode(null, CachNameType.receiptTimeOut,
				robOrderConfirmId);
		// LgisticsCompanies lgisticsCompanies =
		// lgisticsCompaniesService.getLgisticsCompanies(lgisticsCode);
		// if (lgisticsCompanies == null) {
		// return jsonView(false, "没有找到物流公司信息，请确认物流公司编号是否正确！", null);
		// }

		/*
		 * Lgistics lgistics =
		 * LgisticsUtil.getLgisticsNode(lgisticsCompanies.getCode(),lgisticsNum)
		 * ; if(lgistics.getStatus().equals(-3)){ return jsonView(false,
		 * lgistics.getMsg(),null); }
		 */

		String rootPath = AppUtil.getUpLoadPath(request);
		/*
		 * String dataType="originalReceiptImg"; List<String> path = new
		 * ArrayList<String>(); for(MultipartFile f:file){ try { String img_path
		 * = ImageFileUtil.uploadTemporary(f,AppUtil.getUpLoadPath(request),
		 * dataType); path.add(img_path); } catch (IOException e) { return
		 * jsonView(false, "上传图片异常请重试!",null); }
		 * 
		 * }
		 */
		List<String> pathList = Arrays.asList(path);
		robOrderConfirm.setSettlement_weight(settlement_weight);
		robOrderConfirmService.saveDelivery(account, robOrderConfirm,
				robOrderRecord, lgisticsNum, pathList, rootPath);
		// robOrderConfirmService.saveReceipt(account, robOrderConfirm,
		// robOrderRecord, lgisticsCompanies, lgisticsNum,
		// pathList, rootPath);
		return jsonView(true, "确认送达成功!", null);
	}

	/**
	 * 快递回执信息
	 * 
	 * @param robOrderConfirmId
	 *            确认订号ID
	 * @param lgisticsCode
	 *            物流公司编号
	 * @param lgisticsNum
	 *            物流单号
	 * @param headers
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateimg", method = RequestMethod.POST)
	@UnInterceptor
	@ResponseBody
	public Map<String, Object> updateimg(
			@RequestParam(value = "img", required = false) MultipartFile[] imgArry,
			String lgisticsCode, String lgisticsNum,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		String dataType = "originalReceiptImg";
		String path = "";
		for (MultipartFile img : imgArry) {
			try {
				String img_path = ImageFileUtil.uploadTemporary(img,
						AppUtil.getUpLoadPath(request), dataType);
				path += img_path + ",";
			} catch (IOException e) {
				return jsonView(false, "上传图片异常请重试!", null);
			}

		}
		path = path.substring(0, path.length() - 1);

		String emergencyImg = "";
		String[] pathArray = path.split(",");
		String rootPath = AppUtil.getUpLoadPath(request);
		if (pathArray != null && pathArray.length != 0) {

			for (String str : pathArray) {
				try {
					ImageFileUtil
							.move(new File(rootPath + str), new File(rootPath
									+ str.substring(0, str.lastIndexOf("/"))
											.replace("temporary", "uploading")));
					FileInputStream fin = new FileInputStream(new File(rootPath
							+ str.replace("temporary", "uploading")));
					String url = OSSClientUtil.uploadFile2OSS(fin,
							str.substring(str.lastIndexOf("/"), str.length()),
							"emergencyImg");// 此处是上传到阿里云OSS的步骤
					emergencyImg += url + ",";
				} catch (IOException e) {

					e.printStackTrace();
					return jsonView(false, "上传图片异常请重试!阿里云同步错误！", null);
				}
			}

			emergencyImg = emergencyImg.substring(0, emergencyImg.length() - 1);
		}
		return jsonView(true, "上传图片成功", emergencyImg,false);
	}

	/**
	 * 获取运单确认付款金额 是从冻结资金转过去的 by lil
	 * 
	 * @param robOrderConfirmId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getPayment", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor(capitalValid = true)
	public Map<String, Object> getPayment(String robOrderConfirmID,Integer payment_type) {
		Account account = UserUtil.getAccount();
		payment_type = payment_type==null?0:payment_type;
		Map<String, Object> map = new HashMap<String, Object>();
		CapitalAccount capAccount = capitalAccountService
				.getCapitalAccount(account.getId());
		RobOrderConfirm confirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmID);
		if (confirm == null) {
			throw new BusinessException("没有该运单");

		}
		
		BigDecimal bd = new BigDecimal(confirm.getTotalCost());
		PaymentType paymentType2 = PaymentType.values()[payment_type];
		if(paymentType2 == PaymentType.settlementWeight) {
			bd =new BigDecimal(  DoubleHelper.round( confirm.getSettlement_weight()* confirm.getUnitPrice() 
					+ confirm.getAdditionalCost()));
		}
		map.put("payMoney", bd.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
		map.put("avaiable", capAccount.getAvaiable());
		map.put("frozen", capAccount.getFrozen());
		return jsonView(true, "成功", map);
	}

	/**
	 * 确认支付款
	 * 
	 * @param robOrderConfirmId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "payment", method = RequestMethod.POST)
	@ResponseBody
	@LockInterceptor
	@AuthInterceptor(capitalValid = true)
	public Map<String, Object> payment(String robOrderConfirmId,
			String paypassword, Integer payment_type /*0为按发货吨位计算 1为按结算吨位计算*/) {
		Account account = AppUtil.getLoginUser();
		payment_type = payment_type==null?0:payment_type;
		if (!account.getCompany().getCompanyType().getName().equals("货主")) {
			return jsonView(false, "你不是货主无权访问该接口！", null);
		}

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		if (StringUtils.isBlank(paypassword)) {
			return jsonView(false, "支付密码不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}

		if (robOrderConfirm.getStatus() != Status.confirmreceipt) {
			return jsonView(false, "订单不是送还回执中状态，不能确认收货！", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (!robOrderRecord.getRobbedAccountId().equals(account.getId())) {
			return jsonView(false, "你无权处理该订单！", null);
		}

		if (!AppUtil.md5(paypassword).equals(account.getPaypassword())) {
			return jsonView(false, "支付密码错误！", null);
		}

		try {
			PaymentType peyPaymentType=	PaymentType.values()[payment_type];
			robOrderConfirm.setPayment_type(peyPaymentType);
			robOrderConfirmService.savePayment(account, robOrderConfirm,
					robOrderRecord);
		} catch (BusinessException e) {
			return jsonView(false, e.getMessage().toString(), null);
		}

		return jsonView(true, "支付成功!", null);
	}

	/**
	 * 获取快递公司
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getLgisticsCompanies", method = RequestMethod.GET)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getLgisticsCompanies(HttpServletRequest request) {
		List<Map<String, Object>> map = lgisticsCompaniesService
				.getLgisticsCompaniesForMap();
		return jsonView(true, "查询成功!", map);
	}

	/**
	 * 请求急救
	 * 
	 * @param robOrderConfirmId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "emergency", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> emergency(String[] imgPath,
			String robOrderConfirmId, String remark, HttpServletRequest request) {

		Account account = (Account) request.getAttribute(ACCOUNT);

		imgPath = imgPath == null ? new String[] {} : imgPath;

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		if (StringUtils.isBlank(remark)) {
			return jsonView(false, "急救信息不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "订单不存在！", null);
		}

		if (robOrderConfirm.getSpecialType() != null
				&& !robOrderConfirm.getSpecialStatus().equals(
						SpecialStatus.success)) {
			if (robOrderConfirm.getSpecialType().equals(SpecialType.emergency)) {
				return jsonView(false, "你的申请的急救正在处理中", null);
			}
			return jsonView(false, "你已申请仲裁，不能申请急救", null);
		}

		if (robOrderConfirm.getStatus().equals(Status.ordercompletion)) {
			return jsonView(false, "订单已完结不能申请急救！", null);
		}

		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (account.getCompany().getCompanyType().getName().equals("货主")) {
			if (!robOrderRecord.getRobbedAccountId().equals(account.getId())) {
				return jsonView(false, "你无权处理该订单！", null);
			}
		}

		if (account.getCompany().getCompanyType().getName().equals("车队")
				|| account.getCompany().getCompanyType().getName()
						.equals("个人司机")) {
			if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
				return jsonView(false, "你没有该订单！", null);
			}
		}

		String rootPath = AppUtil.getUpLoadPath(request);
		List<String> pathList = Arrays.asList(imgPath);
		robOrderConfirmService.saveEmergency(account, robOrderConfirm, remark,
				pathList, rootPath);

		return jsonView(true, "请救成功!", null);
	}

	/**
	 * 申请仲裁
	 * 
	 * @param robOrderConfirmId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "arbitration", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> arbitration(String[] imgPath,
			String robOrderConfirmId, String remark, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);

		imgPath = imgPath == null ? new String[] {} : imgPath;

		if (StringUtils.isBlank(robOrderConfirmId)) {
			return jsonView(false, "确认订单ID不能为空！", null);
		}

		if (StringUtils.isBlank(remark)) {
			return jsonView(false, "仲裁信息不能为空！", null);
		}

		RobOrderConfirm robOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmById(robOrderConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "运单不存在！", null);
		}
		if (robOrderConfirm.getLockStatus().equals(LockStatus.locking)) {

			return jsonView(false, "运单已经仲裁中！", null);
		}
		if (robOrderConfirm.getSpecialType() != null
				&& !robOrderConfirm.getSpecialStatus().equals(
						SpecialStatus.success)
				&& !robOrderConfirm.getSpecialStatus().equals(
						SpecialStatus.none)) {
			if (robOrderConfirm.getSpecialType().equals(SpecialType.emergency)) {
				return jsonView(false, "你已申请急救，不能申请仲裁！", null);
			}
			return jsonView(false, "你申请的仲裁正在处理中", null);
		}

		if (robOrderConfirm.getStatus().equals(Status.ordercompletion)) {
			return jsonView(false, "运单已完结不能申请仲裁！", null);
		}
		if (robOrderConfirm.getStatus().equals(Status.singlepin)) {
			return jsonView(false, "运单已销单不能申请仲裁！", null);
		}
		RobOrderRecord robOrderRecord = robOrderRecordService
				.getRecordById(robOrderConfirm.getRobOrderId());

		if (account.getCompany().getCompanyType().getName().equals("货主")) {
			if (!robOrderRecord.getRobbedAccountId().equals(account.getId())) {
				return jsonView(false, "你无权处理该订单！", null);
			}
		}
		if (account.getRole_type().equals(RoleType.consignor)) {
			if (!robOrderRecord.getRobbedAccountId().equals(account.getId())) {
				throw new BusinessException("您无权处理该运单");
			}
		} else if (account.getRole_type().equals(RoleType.truck)) {
			if (!account.getId().equals(robOrderConfirm.getTurckUserId())) {
				throw new BusinessException("你没有该运单！");
			}
		}
		/*
		 * if (account.getCompany().getCompanyType().getName().equals("车队") ||
		 * account.getCompany().getCompanyType().getName() .equals("个人司机")) { if
		 * (!account.getId().equals(robOrderConfirm.getTurckUserId())) { return
		 * jsonView(false, "你没有该订单！", null); } }
		 */

		String rootPath = AppUtil.getUpLoadPath(request);
		List<String> pathList = Arrays.asList(imgPath);

		robOrderConfirmService.saveArbitration(account, robOrderConfirm,
				robOrderRecord, remark, pathList, rootPath);

		return jsonView(true, "申请仲裁成功!", null);
	}

	/**
	 * 
	 * @param robOrderConfirmId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "confirmOrderLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmOrderLog(String robOrderConfirmId,
			HttpServletRequest request) {
		List<Map<String, Object>> logList = orderAutLogService
				.getOrderlogListMap(robOrderConfirmId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : logList) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", map.get("title"));
			item.put("companyTypeName", map.get("companyTypeName"));
			item.put("create_time", map.get("create_time"));
			item.put("userName", map.get("userName"));
			item.put("companyName", map.get("companyName"));
			item.put("remark", map.get("remark"));
			item.put("status", getStatusName(map));
			list.add(item);
		}
		return jsonView(true, "查询成功!", list);
	}

	public String getStatusName(Map<String, Object> map) {
		// 0:等待装货、1:运输中 2:送达 3:回执发还中 4:送还回执中 5:订单完结 6:销单
		String name = "";
		if (map.get("specialStatus") == null) {
			Integer index = (Integer) map.get("confirmStatus");
			switch (index) {
			case 0:
				name = "等待装货";
				break;
			case 1:
				name = "确认装货";
				break;
			case 2:
				name = "运输中";
				break;
			case 3:
				name = "送达";
				break;
			case 4:
				name = "回执发还中";
				break;
			case 5:
				name = "送还回执中";
				break;
			case 6:
				name = "订单完结";
				break;
			case 7:
				name = "销单";
				break;
			default:
				break;
			}

			return name;
		}

		// 0:等待介入 、1:正在处理、 2:处理完成
		Integer index = (Integer) map.get("specialStatus");
		switch (index) {
		case 0:
			name = "等待介入";
			break;
		case 1:
			name = "正在处理";
			break;
		case 2:
			name = "处理完成";
			break;
		default:
			break;
		}

		return name;
	}

	/**
	 * 功能描述： 获取需确认发货记录列表 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * size 输入参数: @param headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * aiqiwu 日 期: 2017-07-28 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getConfirmDeliverGoodsOrders", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getConfirmDeliverGoodsOrders(
			GoodsBasic goodsBasic, int start, int size,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = goodsBasicService
				.getConfirmDeliverGoodsOrders(goodsBasic, account, start, size);
		return jsonView(true, "成功获取我的货物审阅列表。", map);
	}

	/**
	 * 功能描述： 获取需确认发货记录详细 输入参数: @param goodsBasic 输入参数: @param start 输入参数: @param
	 * size 输入参数: @param headers 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * aiqiwu 日 期: 2017-07-28 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getConfirmDeliverDetailsByRobOrderRecordId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getConfirmDeliverDetailsByRobOrderRecordId(
			RobOrderRecord record, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Map<String, Object> map = robOrderConfirmService
				.getConfirmDeliverDetailsByRobOrderRecordId(record);
		return jsonView(true, "成功获取待确认发货记录详细。", map);
	}

	/**
	 * bylil 获取我的运单列表 queryStatus必填
	 * 
	 * @param confirm
	 *            goods_Baisc_ID queryStatus必填
	 * @return
	 */
	@RequestMapping(value = "getMyRobOrderConfirmList", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getMyRobOrderConfirmList(
			RobOrderConfirm confirm, int start, int size) {
		Account account = UserUtil.getAccount();

		Map<String, Object> map = robOrderConfirmService
				.getMyRobOrderConfirmList(confirm, account, start - 1, size);
		return jsonView(true, "成功获取我的运单列表。", map);
	}

	/**
	 * bylil 获取我的运单详情
	 * 
	 * @param confirm
	 *            goods_Baisc_ID queryStatus必填
	 * @return
	 */
	@RequestMapping(value = "getMyRobOrderConfirmDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyRobOrderConfirmDetail(
			RobOrderConfirm confirm) {
		Account account = UserUtil.getAccount();
		Map<String, Object> map = robOrderConfirmService
				.getMyRobOrderConfirmDetail(account, confirm);
		return jsonView(true, "成功获取我的运单详情。", map);
	}

	/**
	 * bylil 获取运单发货应支付的金额
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "getRobOrderConfirmLoadPayment", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> getRobOrderConfirmLoadPayment(
			String robOrderConfirmId,Double actualWeight,String additionalExpensesRecordJson) {
	
		Map<String, Object> ret =robOrderConfirmService.getRobOrderConfirmLoadPaymentNew(robOrderConfirmId,
				actualWeight, 
				additionalExpensesRecordJson);
		return jsonView(true, "获取我要发货支付金额成功", ret);
	}

}
