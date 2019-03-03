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

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月17日 上午10:45:32 
* 修 改 人： 
* 日   期： 
* 描   述： 订单操作控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/order/orderlog")
public class OrderAutLogController extends BaseController {

	@Autowired
	private IRobOrderRecordService robOrderRecordService;
	
	@Autowired
	private IOrderAutLogService orderAutLogService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser();
		if(account.getCompany().getCompanyType().getName().equals("货主") 
				|| account.getCompany().getCompanyType().getName().equals("车队") 
				|| account.getCompany().getCompanyType().getName().equals("个人司机")){
			model.addAttribute("query", "0");
		}else{
			model.addAttribute("query", "1");
		}
		return "/order/orderlog/index";
	}
	
	@RequestMapping("/getLogPage")
	@ResponseBody
	public Map<String, Object> getLogPage(RobOrderRecord robOrderRecord,HttpServletRequest request,String orderNo,String goodsNo){
		if(StringUtil.isNotEmpty(orderNo)){
			robOrderRecord.setRobOrderNo(orderNo);
		}
		if(StringUtil.isNotEmpty(goodsNo)){
			robOrderRecord.setGoodsTypes(goodsNo);
		}
		return robOrderRecordService.getLogPage(robOrderRecord, getStart(request), getLimit(request));  
	}
	
	
	
	/**
	 * 我的订单日志
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view/myorderlog", method = RequestMethod.GET)
	protected String orderlog(Model model, HttpServletRequest request) {
		return "/order/orderlog/myorderlog";
	}
	
	
	/**
	 * 我的订单状态
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view/statusinfo", method = RequestMethod.GET)
	protected String statusinfo(Model model,String robConfirmId, HttpServletRequest request) {
		List<OrderAutLog> logList =  orderAutLogService.getOrderlog(robConfirmId);
		
		model.addAttribute("logList", logList);
		return "/order/orderlog/statusinfo";
	}
	
	
	
	
	
	
	
}
