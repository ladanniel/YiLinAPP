package com.memory.platform.module.order.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.OrderSpecialApply;
import com.memory.platform.entity.order.OrderWaybillLog;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialStatus;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.LgisticsCompanies;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.LgisticsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.LockInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsTypeService;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IOrderSpecialApplyService;
import com.memory.platform.module.order.service.IOrderWaybillLogService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.order.vo.Lgistics;
import com.memory.platform.module.order.vo.OrderInfo;
import com.memory.platform.module.order.vo.OrderTruck;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.system.service.ILgisticsCompaniesService;
import com.memory.platform.module.trace.dto.GaodeApiConfig;
import com.memory.platform.module.trace.service.IGaoDeWebService;
import com.memory.platform.module.truck.service.ITruckService;

/**
 * 创 建 人： 武国庆 日 期： 2016年6月17日 上午10:45:32 修 改 人： 日 期： 描 述：我的订单控制器 版 本 号： V1.0
 */
@Controller
@RequestMapping("/order/myorder")
public class RobOrderConfirmController extends BaseController {

	@Autowired
	IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	private IGoodsTypeService goodsTypeService;
	@Autowired
	IRobOrderRecordService robOrderRecordService;

	@Autowired
	private ICompanyService companyService; // 商户业务层接口

	@Autowired
	private ILgisticsCompaniesService lgisticsCompaniesService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IOrderAutLogService orderAutLogService;

	@Autowired
	private IOrderWaybillLogService orderWaybillLogService;

	@Autowired
	private IOrderSpecialApplyService orderSpecialApplyService;

	@Autowired
	private ITruckService truckService;

	@Autowired
	IGaoDeWebService gaoDeWebService;

	/**
	 * 
	 * 功能描述： 我的订单 输入参数: @param robOrderRecordId 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人:武国庆 日 期: 2016年7月7日下午12:09:09 修 改 人: 日 期: 返
	 * 回：List<RobOrderRecordInfo>
	 */
	@RequestMapping(value = "/view/myorderinfo", method = RequestMethod.GET)
	public String orderinfo(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();

		Account account = UserUtil.getAccount();

		model.addAttribute("goodsTypeList", list);
		model.addAttribute("account", account);

		if ("货主".equals(account.getCompany().getCompanyType().getName())) {
			return "/order/myorder/corderinfo";
		}

		Truck truck = truckService.getTruckByAcountNo(account.getId());

		model.addAttribute("truck", truck);
		GaodeApiConfig cfg = gaoDeWebService.getConfig();
		model.addAttribute("gaoDeCfg", cfg);
		return "/order/myorder/userorderinfo";
		/*
		 * if("车队".equals(account.getCompany().getCompanyType().getName())){
		 * return "/order/myorder/userorderinfo"; }
		 * 
		 * return "/order/myorder/orderinfo";
		 */

	}

	/**
	 * 
	 * 功能描述： 商户订单 输入参数: @param robOrderRecordId 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人:武国庆 日 期: 2016年7月7日下午12:09:09 修 改 人: 日 期: 返
	 * 回：List<RobOrderRecordInfo>
	 */
	@RequestMapping(value = "/view/companyorderinfo", method = RequestMethod.GET)
	public String companyorderinfo(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();

		Account account = UserUtil.getUser();

		model.addAttribute("goodsTypeList", list);
		model.addAttribute("account", account);

		/*
		 * if("车队".equals(account.getCompany().getCompanyType().getName())){
		 * return "/order/myorder/userorderinfo"; }
		 */
		if ("易林".equals(account.getCompany().getCompanyType().getName())
				|| "系统".equals(account.getCompany().getCompanyType().getName())) {
			return "/order/myorder/sysorderinfo";
		}

		return "/order/myorder/orderinfo";

	}

	/***
	 * 查询我的订单 功能描述： 输入参数: @param robOrderRecord 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:55:07 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/getMyOrderPage")
	@ResponseBody
	public Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord, HttpServletRequest request) {
		return robOrderConfirmService.getMyRobOrderPage(robOrderRecord, getStart(request), getLimit(request));
	}

	/***
	 * 查询我的订单 功能描述： 输入参数: @param robOrderRecord 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月7日下午3:55:07 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/getCompanyOrderPage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getCompanyOrderPage(RobOrderRecord robOrderRecord, HttpServletRequest request) {
		return robOrderConfirmService.getCompanyOrderPage(robOrderRecord, getStart(request), getLimit(request));

	}

	/**
	 * 获取订单拉货车列表 功能描述： 输入参数: @param robOrderId 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:19:51 修 改 人: 日
	 * 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/getTruckPage")
	@ResponseBody
	public Map<String, Object> getTruckPage(RobOrderConfirm robOrderConfirm, HttpServletRequest request) {
		// return
		// robOrderConfirmService.getTruckPage(robOrderId,getStart(request),
		// getLimit(request));
		return robOrderConfirmService.getTruckAll(robOrderConfirm);
	}

	/**
	 * 
	 * 功能描述： 获取司机拉货详细信息 输入参数: @param robOrderId 输入参数: @param truckID
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月9日上午11:07:55 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping("/getOrderInfoPage")
	@ResponseBody
	public Map<String, Object> getOrderInfoPage(String robOrderId, String truckID, HttpServletRequest request) {

		Account account = UserUtil.getAccount();

		/*
		 * if("货主".equals(account.getCompany().getCompanyType().getName())){
		 * RobOrderConfirm robOrderConfirm = new RobOrderConfirm();
		 * robOrderConfirm.setRobOrderId(robOrderId); return
		 * robOrderConfirmService.getTruckAll(robOrderId); }
		 */

		if ("车队".equals(account.getCompany().getCompanyType().getName()) && StringUtils.isBlank(truckID)) {
			RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderId, account.getId());
			truckID = robOrderConfirm.getTurckId();
		}
		if ("个人司机".equals(account.getCompany().getCompanyType().getName()) && StringUtils.isBlank(truckID)) {
			RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderId, account.getId());
			truckID = robOrderConfirm.getTurckId();
		}

		return robOrderConfirmService.getOrderInfoPage(robOrderId, truckID, getStart(request), getLimit(request));
	}

	/**
	 * 
	 * 功能描述： 获取发货人和收货人信息 输入参数: @param model 输入参数: @param request 输入参数: @param id
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月9日上午11:09:03 修 改 人: 日
	 * 期: 返 回：String
	 */
	@RequestMapping("/getgoodsinfo")
	public String getGoodsInfo(Model model, HttpServletRequest request, String id, String robConfirmId) {
		RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		GoodsBasic goods = robOrderRecord.getGoodsBasic();
		if (!StringUtil.isEmpty(robConfirmId)) {
			List<Map<String, Object>> listMap = robOrderConfirmService.orderstatus(robConfirmId);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("index", listMap.size());
			Integer type = 0;
			for (Map<String, Object> m : listMap) {
				Integer t = (Integer) m.get("type");
				if (t != 7 && t > type) {
					type = t;
				}
				m.put("title", getStatusName(t));
				m.put("select", true);
			}
			model.addAttribute("status", type);
		}
		model.addAttribute("goods", goods);
		model.addAttribute("robOrderId", id);
		return "/order/myorder/goodsinfo";
	}

	/**
	 * 
	 * 功能描述： 获取发货人和收货人信息 输入参数: @param model 输入参数: @param request 输入参数: @param id
	 * 输入参数: @return 异 常： 创 建 人: Administrator 日 期: 2016年7月9日上午11:09:03 修 改 人: 日
	 * 期: 返 回：String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/orderdetailsinfo")
	public String orderdetailsinfo(Model model, HttpServletRequest request, String id) {
		RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		GoodsBasic goods = robOrderRecord.getGoodsBasic();
		Company company = companyService.getCompanyById(robOrderRecord.getRobbedCompanyId());

		Map<String, Object> truckMap = robOrderConfirmService.getTruckAll(id);
		List<OrderTruck> truckList = (List<OrderTruck>) truckMap.get("rows");

		for (OrderTruck truck : truckList) {
			List<OrderInfo> list = (List<OrderInfo>) robOrderConfirmService
					.getOrderInfoPage(id, truck.getTurckID(), 0, 30).get("rows");
			truck.setOderInfo(list);
		}

		model.addAttribute("truckList", truckList);
		model.addAttribute("goods", goods);
		model.addAttribute("company", company);
		model.addAttribute("robOrderRecord", robOrderRecord);
		return "/order/myorder/orderdetailsinfo";
	}

	/**
	 * 
	 * 功能描述： 管理员获取订单信息 输入参数: @param model 输入参数: @param request 输入参数: @return 异
	 * 常： 创 建 人: Administrator 日 期: 2016年7月11日下午4:04:34 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/sysorderinfo", method = RequestMethod.GET)
	public String sysOrderinfo(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();

		Account account = UserUtil.getUser();
		UserType userType = account.getUserType();

		model.addAttribute("goodsTypeList", list);
		model.addAttribute("account", account);

		if ("车队".equals(account.getCompany().getCompanyType().getName()) && UserType.user.equals(userType)) {
			return "/order/myorder/userorderinfo";
		}

		if ("易林".equals(account.getCompany().getCompanyType().getName())
				|| "系统".equals(account.getCompany().getCompanyType().getName())) {
			return "/order/myorder/sysorderinfo";
		}

		return "/order/myorder/orderinfo";
	}

	/**
	 * 功能描述： 商户信息查询 输入参数: @param company 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: yangjiaqiao 日 期: 2016年5月24日下午4:44:36 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getCompanyList")
	@ResponseBody
	public Map<String, Object> getCompanyList(String keyword, String conpanyTypeName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("value", this.companyService.getPageCompanyByName(keyword, conpanyTypeName, 0, 5).get("rows"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 订单详细信息 功能描述： 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: Administrator 日 期: 2016年7月13日下午1:55:01 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/orderdetails", method = RequestMethod.GET)
	public String orderdetails(Model model, HttpServletRequest request) {

		List<GoodsType> list = goodsTypeService.getListByLeav();
		Account account = UserUtil.getUser();

		model.addAttribute("goodsTypeList", list);
		model.addAttribute("account", account);

		return "/order/myorder/orderdetails";
	}

	/**
	 * 
	 * 功能描述： 订单状态信息 输入参数: @param model 输入参数: @param status订单状态 输入参数: @param
	 * robOrderId 订单号 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * Administrator 日 期: 2016年7月18日上午11:31:26 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/orderstatus", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> orderstatus(Model model, String robConfirmId, HttpServletRequest request) {
		List<Map<String, Object>> listMap = robOrderConfirmService.orderstatus(robConfirmId);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("index", listMap.size());
		Integer type = 0;
		for (Map<String, Object> m : listMap) {
			Integer t = (Integer) m.get("type");
			if (t != 7 && t > type) {
				type = t;
			}
			m.put("title", getStatusName(t));
			m.put("select", true);
		}
		System.out.print(type);
		model.addAttribute("status", type);
		Map<String, Object> item1 = listMap.get(0);
		for (int i = type + 1; i <= 6; i++) {
			Map<String, Object> item = new HashMap<String, Object>(item1);
			item.put("title", getStatusName(i));
			item.put("type", getStatusName(i));
			item.put("select", false);
			listMap.add(item);
		}
		data.put("list", listMap);
		return data;
	}

	public String getStatusName(Integer index) {
		// 0:等待装货、1:运输中 2:送达 3:回执发还中 4:送还回执中 5:订单完结 6:销单
		String name = "";
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

	/**
	 * 
	 * 功能描述： 订单状态信息 输入参数: @param model 输入参数: @param status订单状态 输入参数: @param
	 * robOrderId 订单号 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * Administrator 日 期: 2016年7月18日上午11:31:26 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/statusinfo", method = RequestMethod.GET)
	public String statusInfo(Model model, Integer status, String robOrderId, String turckId, String robConfirmId,
			HttpServletRequest request) {
		OrderAutLog logs = new OrderAutLog();
		OrderAutLog log = null;
		if (StringUtils.isBlank(robConfirmId) || robConfirmId.equals("0")) {
			RobOrderConfirm robOrderConfirm1 = new RobOrderConfirm();
			robOrderConfirm1.setRobOrderId(robOrderId);
			robOrderConfirm1.setTurckId(turckId);
			RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderConfirm1);
			robConfirmId = robOrderConfirm.getId();
		}

		RobOrderConfirm robOrderConfirm = null;

		switch (status) {
		case 0: // 等待装货
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.receiving);
			log = orderAutLogService.getLog(logs);
			model.addAttribute("account", accountService.getAccount(log.getTurckUserId()));
			break;
		case 1: // 确认装货
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.confirmload);
			log = orderAutLogService.getLog(logs);
			robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
			model.addAttribute("robOrderConfirm", robOrderConfirm);
			break;
		case 2: // 运输中
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.transit);
			log = orderAutLogService.getLog(logs);
			robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
			double price = robOrderConfirm.getActualTransportationCost() - robOrderConfirm.getTransportationDeposit(); // 运输费等于
																														// 总运输费-运输押金
			model.addAttribute("price", price);
			model.addAttribute("robOrderConfirm", robOrderConfirm);
			break;
		case 3: // 收货
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.transit);
			log = orderAutLogService.getLog(logs);
			RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(robOrderId);
			GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
			model.addAttribute("goodsBasic", goodsBasic);
			break;
		case 4: // 回执发还中
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.receipt);
			log = orderAutLogService.getLog(logs);
			robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
			model.addAttribute("robOrderConfirm", robOrderConfirm);
			break;
		case 5: // 送还回执中
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.confirmreceipt);
			log = orderAutLogService.getLog(logs);
			Account account = accountService.getAccount(log.getReceiptUserID()); // 回执人员
			model.addAttribute("account", account);
		case 6: // 订单完结
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.confirmreceipt);
			log = orderAutLogService.getLog(logs);
			robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
			// 运输费
			double money = robOrderConfirm.getTransportationCost();
			model.addAttribute("money", money);
			break;
		case 7: // 销单
			logs.setRobOrderConfirmId(robConfirmId);
			logs.setConfirmStatus(RobOrderConfirm.Status.singlepin);
			log = orderAutLogService.getLog(logs);
			robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
			robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
			model.addAttribute("robOrderConfirm", robOrderConfirm);
			break;
		}

		model.addAttribute("log", log);
		// model.addAttribute("robOrderConfirm", robOrderConfirm);
		model.addAttribute("status", status);
		return "/order/myorder/statusinfo";
	}

	/**
	 * 
	 * 功能描述： 订单状态信息 输入参数: @param model 输入参数: @param status订单状态 输入参数: @param
	 * robOrderId 订单号 输入参数: @param request 输入参数: @return 异 常： 创 建 人:
	 * Administrator 日 期: 2016年7月18日上午11:31:26 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/lgisticsinfo", method = RequestMethod.GET)
	public String lgisticsInfo(Model model, Integer status, String robOrderId, String turckId,
			HttpServletRequest request) {

		RobOrderConfirm robOrderConfirm1 = new RobOrderConfirm();
		robOrderConfirm1.setRobOrderId(robOrderId);
		robOrderConfirm1.setTurckId(turckId);
		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderConfirm1);

		Lgistics lgistics = LgisticsUtil.getLgisticsNode(robOrderConfirm.getLgisticsCode(),
				robOrderConfirm.getLgisticsNum());

		model.addAttribute("listInfo", lgistics);
		return "/order/myorder/lgisticsinfo";
	}

	/**
	 * 
	 * 功能描述： 回执人员分配面页 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/confirmreceiptuser", method = RequestMethod.GET)
	public String confirmreceiptUser(Model model, HttpServletRequest request) {

		List<LgisticsCompanies> lgisticsList = lgisticsCompaniesService.getLgisticsCompanies();
		model.addAttribute("lgisticsList", lgisticsList);

		return "/order/myorder/confirmreceiptuser";
	}

	/**
	 * 
	 * 功能描述： 回执人员分配面页 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/confirmreceiptuser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> confirmreceiptUser(Model model, RobOrderConfirm robOrderConfirm,
			HttpServletRequest request) {
		RobOrderConfirm orderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderConfirm);
		if (orderConfirm != null) {
			RobOrderRecord robOrderRecord = new RobOrderRecord();
			robOrderRecord.setRobOrderNo(orderConfirm.getRobOrderNo());
			return robOrderConfirmService.getCompanyOrderPage(robOrderRecord, 0, 100);
		}
		return null;

	}

	@RequestMapping(value = "/confirmreceiptuser1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> confirmreceiptUser1(Model model, RobOrderConfirm robOrderConfirm, int limit, int offset,
			HttpServletRequest request) {
		Map<String, Object> ret = robOrderConfirmService.getRabOrderDelivered(robOrderConfirm, getStart(request),
				getLimit(request));
		return ret;
	}

	/**
	 * 
	 * 功能描述： 回执人员分配面页 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/receiptuser", method = RequestMethod.GET)
	public String receiptUser(Model model, String robOrderId, String robConfirmId, HttpServletRequest request) {

		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
		model.addAttribute("robOrderId", robOrderId);
		model.addAttribute("robConfirmId", robConfirmId);
		model.addAttribute("robOrderConfirm", robOrderConfirm);
		return "/order/myorder/receiptuser";
	}

	/**
	 * 功能描述： 分页显示账号列表 输入参数: @param cashApplication 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月29日下午4:17:59 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(HttpServletRequest request) {
		Account account = UserUtil.getUser();
		account.getCompanySection().setId(null);
		Map<String, Object> map = this.accountService.getPage(account, getStart(request), getLimit(request));
		return map;
	}

	/**
	 * 功能描述： 文件上传 输入参数: @param request 输入参数: @param file 输入参数: @return
	 * 输入参数: @throws IllegalStateException 输入参数: @throws IOException 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年5月5日下午7:08:51 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> uploadimg(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws IllegalStateException, IOException {
		String dataType = "receiptimg";
		Map<String, Object> map = new HashMap<String, Object>();
		if (file.isEmpty()) {
			System.out.println("【文件为空！】");
			map.put("success", false);
			map.put("msg", "文件为空！");
		}
		String img_path = ImageFileUtil.uploadTemporary(file, AppUtil.getUpLoadPath(request), dataType);
		map.put("success", true);
		map.put("msg", "上传成功！");
		map.put("imgpath", img_path);
		return map;
	}

	/**
	 * 
	 * 功能描述： 保存回执人员分配 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/savereceiptuser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savereceiptuser(Model model, RobOrderConfirm robOrderConfirm,
			@RequestParam(value = "path[]") String[] path, HttpServletRequest request) {

		if (robOrderConfirm == null || StringUtils.isBlank(robOrderConfirm.getAccountId())) {
			return jsonView(false, "人员不能为空");
		}

		if (robOrderConfirm == null || StringUtils.isBlank(robOrderConfirm.getRobOrderId())) {
			return jsonView(false, "单号ID不能为空");
		}

		if (path == null) {
			return jsonView(false, "图片不能为空");
		}
		String rootPath = AppUtil.getUpLoadPath(request);
		robOrderConfirmService.savereceiptuser(robOrderConfirm, path, rootPath);
		return jsonView(true, SAVE_SUCCESS);

	}

	/**
	 * 
	 * 功能描述： 回执详细 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/receiptinfo", method = RequestMethod.GET)
	public String receiptinfo(Model model, Integer status, String robOrderId, String turckId,
			HttpServletRequest request) {
		RobOrderConfirm robOrderConfirm1 = new RobOrderConfirm();
		robOrderConfirm1.setRobOrderId(robOrderId);
		robOrderConfirm1.setTurckId(turckId);
		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderConfirm1);

		Account account = accountService.getAccount(robOrderConfirm.getAccountId()); // 回执人员
		model.addAttribute("account", account);
		model.addAttribute("robOrderConfirm", robOrderConfirm);
		return "/order/myorder/receiptinfo";
	}

	/**
	 * 
	 * 功能描述：查看 司机上传回执详细 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/truckreceiptinfo", method = RequestMethod.GET)
	public String truckreceiptinfo(Model model, Integer status, String robOrderId, String turckId,
			HttpServletRequest request) {
		RobOrderConfirm robOrderConfirm1 = new RobOrderConfirm();
		robOrderConfirm1.setRobOrderId(robOrderId);
		robOrderConfirm1.setTurckId(turckId);
		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirm(robOrderConfirm1);

		model.addAttribute("robOrderConfirm", robOrderConfirm);
		return "/order/myorder/truckreceiptinfo";
	}

	/**
	 * 
	 * 功能描述：特殊运单处理 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/specialorder", method = RequestMethod.GET)
	public String specialorder(Model model, Integer status, String robOrderId, String turckId,
			HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();

		Account account = UserUtil.getUser();

		model.addAttribute("goodsTypeList", list);
		model.addAttribute("account", account);
		return "/order/myorder/specialorder";
	}

	/**
	 * 
	 * 功能描述：特殊运单处理 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/specialorderinfo", method = RequestMethod.GET)
	public String specialorderinfo(Model model, String robConfirmId, HttpServletRequest request) {
		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
		Account account = UserUtil.getUser();

		OrderSpecialApply apply = new OrderSpecialApply();
		apply.setApplypersonid(robOrderConfirm.getTurckUserId());
		apply.setRobOrderConfirmId(robConfirmId);
		apply.setConfirmStatus(robOrderConfirm.getStatus());
		apply.setSpecialStatus(robOrderConfirm.getSpecialStatus());
		apply.setSpecialType(robOrderConfirm.getSpecialType());

		OrderSpecialApply applyinfo = orderSpecialApplyService.getOrderSpecialApply(apply);

		List<OrderWaybillLog> logList = orderWaybillLogService.getOrderWaybillLogList(applyinfo.getId());

		if ((account.getId().equals(applyinfo.getDealtPersonId())
				&& applyinfo.getSpecialStatus().equals(SpecialStatus.processing))
				|| applyinfo.getSpecialStatus().equals(SpecialStatus.suchprocessing)) {
			model.addAttribute("show", true);
		}

		model.addAttribute("logList", logList);
		model.addAttribute("applyinfo", applyinfo);
		return "/order/myorder/specialorderinfo";
	}

	/**
	 * 
	 * 功能描述仲裁运单处理 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/arbitrationinfo", method = RequestMethod.GET)
	public String arbitrationinfo(Model model, String robConfirmId, HttpServletRequest request) {
		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
		Account account = UserUtil.getAccount();

		OrderSpecialApply apply = new OrderSpecialApply();
		apply.setRobOrderConfirmId(robConfirmId);
		apply.setConfirmStatus(robOrderConfirm.getStatus());
		apply.setSpecialStatus(robOrderConfirm.getSpecialStatus());
		apply.setSpecialType(robOrderConfirm.getSpecialType());

		OrderSpecialApply applyinfo = orderSpecialApplyService.getOrderSpecialApply(apply);

		List<OrderWaybillLog> logList = orderWaybillLogService.getOrderWaybillLogList(applyinfo.getId());

		if ((account.getId().equals(applyinfo.getDealtPersonId())
				&& applyinfo.getSpecialStatus().equals(SpecialStatus.processing))
				|| applyinfo.getSpecialStatus().equals(SpecialStatus.suchprocessing)) {
			model.addAttribute("show", true);
		}

		model.addAttribute("applyinfo", applyinfo);
		model.addAttribute("logList", logList);
		model.addAttribute("robOrderConfirm", robOrderConfirm);
		return "/order/myorder/arbitrationinfo";
	}

	/**
	 * 
	 * 功能描述：保存运单处理信息 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/savespecialorderinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savespecialorderinfo(Model model, String type, String remark, String robConfirmId,
			HttpServletRequest request) {
		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "运单不存在！");
		}
		Account account = UserUtil.getUser();

		OrderSpecialApply apply = new OrderSpecialApply();
		apply.setApplypersonid(robOrderConfirm.getTurckUserId());
		apply.setRobOrderConfirmId(robConfirmId);
		apply.setConfirmStatus(robOrderConfirm.getStatus());
		apply.setSpecialStatus(robOrderConfirm.getSpecialStatus());
		apply.setSpecialType(robOrderConfirm.getSpecialType());

		OrderSpecialApply applyinfo = orderSpecialApplyService.getOrderSpecialApply(apply);

		if (!applyinfo.getSpecialStatus().equals(SpecialStatus.suchprocessing)
				&& !applyinfo.getDealtPersonId().equals(account.getId())) {
			return jsonView(false, "运单以被其他人处理！！");
		}

		robOrderConfirmService.savespecialorderinfo(account, robOrderConfirm, applyinfo, remark, type);
		return jsonView(true, "受理成功！");
	}

	/**
	 * 
	 * 功能描述：保存仲裁信息 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/savearbitration", method = RequestMethod.POST)
	@ResponseBody
	@LockInterceptor
	public Map<String, Object> savearbitration(Model model, String savetype, RocessingResult rocessingResult,
			String remark, String robConfirmId, HttpServletRequest request) {

		RobOrderConfirm robOrderConfirm = robOrderConfirmService.findRobOrderConfirmById(robConfirmId);
		if (robOrderConfirm == null) {
			return jsonView(false, "运单不存在！");
		}

		Account account = UserUtil.getAccount();

		OrderSpecialApply apply = new OrderSpecialApply();
		// apply.setApplypersonid(robOrderConfirm.getTurckUserId());
		apply.setRobOrderConfirmId(robConfirmId);
		apply.setConfirmStatus(robOrderConfirm.getStatus());
		apply.setSpecialStatus(robOrderConfirm.getSpecialStatus());
		apply.setSpecialType(robOrderConfirm.getSpecialType());

		OrderSpecialApply applyinfo = orderSpecialApplyService.getOrderSpecialApply(apply);

		if (!applyinfo.getSpecialStatus().equals(SpecialStatus.suchprocessing)
				&& !applyinfo.getDealtPersonId().equals(account.getId())) {
			return jsonView(false, "运单以被其他人处理！！");
		}
		robOrderConfirmService.saveArbitrationNew(account, robOrderConfirm, remark, rocessingResult);

		return jsonView(true, "受理成功！");
	}

	/**
	 * 
	 * 功能描述：特殊运单处理 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/specialorder", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> specialorder(Model model, RobOrderRecord robOrderRecord, HttpServletRequest request) {
		Account account = UserUtil.getUser();
		return robOrderConfirmService.getSpecialOrderPage(account, robOrderRecord, getStart(request),
				getLimit(request));
	}

	/**
	 * 
	 * 功能描述： 回执任务 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年7月20日下午3:25:11 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/receipttask", method = RequestMethod.GET)
	public String receipttask(Model model, Integer status, String robOrderId, String turckId,
			HttpServletRequest request) {
		model.addAttribute("accountId", "4028800a5596dfc40155973989480017");
		return "/order/myorder/receipttask";
	}

	/**
	 * 获取回执任务列表 功能描述： 输入参数: @param robOrderId 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: Administrator 日 期: 2016年7月8日上午11:19:51 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getReceipttask")
	@ResponseBody
	public Map<String, Object> getReceipttask(RobOrderRecord robOrderRecord, HttpServletRequest request) {
		// robOrderRecord.setReceiptUserId("4028800a5596dfc40155973989480017");
		return robOrderConfirmService.getReceipttask(robOrderRecord, 0, 20);
	}

}
