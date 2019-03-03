package com.memory.platform.module.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IRobOrderRecordInfoService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月21日 上午9:46:06 
* 修 改 人： 
* 日   期： 
* 描   述： 抢单管理控制器
* 版 本 号：  V1.0
 */
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.system.service.IParameterManageService;
@Controller
@RequestMapping("/order/ordermanage")
public class OrderManageController extends BaseController {

	@Autowired
	private IRobOrderRecordService orderRecordService;
	@Autowired
	private IParameterManageService parameterManageService;
	@Autowired
	private IRobOrderRecordInfoService robOrderRecordInfoService;
	@Autowired
	private IGoodsBasicService goodsBasicService;
	@Autowired
	private IOrderAutLogService orderLogService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/order/ordermanage/index";
	}
	
	@RequestMapping(value = "/view/orderList", method = RequestMethod.GET)
	protected String orderList(Model model,String id, HttpServletRequest request) {
		model.addAttribute("goodsId", id);
		model.addAttribute("goodsId_return", id + "return");
		return "/order/ordermanage/orderList";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(String id,HttpServletRequest request){
		return orderRecordService.getPage(id, getStart(request), getLimit(request));  
	}
	
	@RequestMapping("/getGoodsPage")
	@ResponseBody
	public Map<String, Object> getGoodsPage(GoodsBasic goodsBasic,HttpServletRequest request){
		return goodsBasicService.getGoodsBasicPageSuccess(goodsBasic, getStart(request), getLimit(request));
	}
	
	@RequestMapping("/getListOrderDetail")
	@ResponseBody
	public List<RobOrderRecordInfo> getListOrderDetail(RobOrderRecord order,HttpServletRequest request){
		return robOrderRecordInfoService.getList(order.getId());
	}
	
	@RequestMapping(value = "/view/verify", method = RequestMethod.GET)
	protected String verify(Model model,String id, HttpServletRequest request) {
		RobOrderRecord order = orderRecordService.getRecordById(id);
		
		Account user = UserUtil.getUser();
		if(!user.getId().equals(order.getGoodsBasic().getAccount().getId())){
			throw new BusinessException("你不能处理别人的发货抢单。");
		}
		
		if(!order.getStatus().equals(RobOrderRecord.Status.apply) && !order.getStatus().equals(RobOrderRecord.Status.dealing)){
			throw new BusinessException("请选择申请处理的数据进行处理。");
		}
		
		ParameterManage parameterManage = parameterManageService.getTypeInfo(ParaType.consignor);
		
		order.setStatus(RobOrderRecord.Status.dealing);
		orderRecordService.updateRecord(order);
		
		model.addAttribute("money", parameterManage.getValue() * order.getWeight());
		model.addAttribute("parameter", parameterManage.getValue());
		model.addAttribute("info", order);
		model.addAttribute("list", orderLogService.getListForMap(order.getId()));
		return "/order/ordermanage/verify";
	}
	
	@RequestMapping("/verify")
	@ResponseBody
	public Map<String, Object> verify(RobOrderRecord record,String payPassword){
		orderRecordService.updateStatus(record,payPassword);
		return jsonView(true, "审核处理成功。");
	}
}
