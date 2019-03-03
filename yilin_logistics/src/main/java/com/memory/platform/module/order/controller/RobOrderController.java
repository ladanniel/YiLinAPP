package com.memory.platform.module.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.UserType;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.goods.service.IGoodsDetailService;
import com.memory.platform.module.goods.service.IGoodsTypeService;
import com.memory.platform.module.order.service.IOrderAutLogService;
import com.memory.platform.module.order.service.IRobOrderRecordInfoService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.system.service.IAccountService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月17日 上午10:45:32 
* 修 改 人： 
* 日   期： 
* 描   述： 订单操作控制器
* 版 本 号：  V1.0
 */
import com.memory.platform.module.system.service.IParameterManageService;
@Controller
@RequestMapping("/order/roborder")
public class RobOrderController extends BaseController {

	@Autowired 
	private IGoodsTypeService goodsTypeService;
	@Autowired
	private IGoodsBasicService goodsBasicService;
	@Autowired
	IRobOrderRecordService robOrderRecordService;
	@Autowired
	IRobOrderRecordInfoService robOrderRecordInfoService;
	@Autowired
	IParameterManageService parameterManageService;
	@Autowired
	private IOrderAutLogService orderLogService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IGoodsDetailService goodsDetailService;
	
	
	/**
	* 功能描述： 抢单列表
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月23日下午2:49:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();
		model.addAttribute("goodsTypeList", list);
		return "/order/roborder/index";
	}
	
	/**
	* 功能描述： 进入
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日上午10:31:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/myroborder", method = RequestMethod.GET)
	protected String myroborder(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();
		model.addAttribute("goodsTypeList", list);
		return "/order/roborder/myroborder";
	}
	
	
	/**
	* 功能描述： 进入抢单页面
	* 输入参数:  @param id
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月25日上午11:09:31
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/addroborder", method = RequestMethod.GET)
	protected String robOrder(String id,Model model, HttpServletRequest request) {
//		GoodsBasic goodsBasic = goodsBasicService.getGoodsBasicById(id);
//		model.addAttribute("goodsBasic", goodsBasic);
		GoodsBasic goods = goodsBasicService.getGoodsBasicById(id);
		model.addAttribute("goods", goods);
		model.addAttribute("goodsBasic", goods);
		model.addAttribute("list", goodsDetailService.getListForMap(goods.getId()));
		return "/order/roborder/addroborder";
	}
	
	/**
	* 功能描述： 抢单信息修改
	* 输入参数:  @param id
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月28日上午10:20:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/editroborder", method = RequestMethod.GET)
	protected String editRobOrder(String id,Model model, HttpServletRequest request) {
		RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		model.addAttribute("robOrderRecord", robOrderRecord);
		model.addAttribute("list", orderLogService.getListForMap(id,"desc"));
		return "/order/roborder/editroborder";
	}
	
	/**
	* 功能描述： 验证抢单货物是否都被抢单
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月22日下午5:43:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/checkeditroborder")
	@ResponseBody
	public Map<String, Object> checkeditroborder(String id,HttpServletRequest request){
		Account account = UserUtil.getUser(request);
		List<RobOrderRecordInfo> list = robOrderRecordInfoService.getListByRobOrderRecordId(id);
		List<RobOrderRecordInfo> list1 =new ArrayList<RobOrderRecordInfo>();
		String msg = "";
		if(list.size() > 0){
			for (int i = 0;i <list.size();i++) {
				RobOrderRecordInfo robOrderRecordInfo = list.get(i);
				if(robOrderRecordInfo.getGoodsDetail().getSurplusWeight() == 0){
					msg +="货物类型：［"+robOrderRecordInfo.getGoodsDetail().getGoodsType().getName()+"］, 货物名称：［"+robOrderRecordInfo.getGoodsDetail().getGoodsName()+"］,</br>";
					list1.add(robOrderRecordInfo);
				}
			}
		}
		list.removeAll(list1);
		if(list.size() == 0){
			 RobOrderRecord robOrderRecord  = new RobOrderRecord();
			 robOrderRecord.setId(id);
			 robOrderRecordService.updateRobOrderCancel(robOrderRecord, account,"sys");
			 return jsonView(false,"你所抢的货物【"+msg+"】,货物已被抢完，剩余重量为：0吨,系统已自动销单！");
		}else{
			 return jsonView(true,"还可以继续修改！");
		}
	}
	
	
	/**
	* 功能描述： 抢单信息确认
	* 输入参数:  @param id
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月1日下午1:18:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/confirmroborder", method = RequestMethod.GET)
	protected String confirmRobOrder(String id,Model model, HttpServletRequest request) {
		RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		ParameterManage parameterManage = parameterManageService.getTypeInfo(ParaType.driver);
		model.addAttribute("robOrderRecord", robOrderRecord);
		model.addAttribute("parameterManage", parameterManage);
		model.addAttribute("list", orderLogService.getListForMap(id,"desc"));
		return "/order/roborder/confirmroborder";
	}
	
	/**
	* 功能描述： 获取抢单信息
	* 输入参数:  @param id
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日下午12:08:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/getRobOrderInfo", method = RequestMethod.GET)
	protected String getRobOrderInfo(String id,Model model, HttpServletRequest request) {
		RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		List<RobOrderRecordInfo> list = robOrderRecordInfoService.getListByRobOrderRecordId(robOrderRecord.getId());
		model.addAttribute("robOrderRecord", robOrderRecord);
		model.addAttribute("robOrderRecordInfo", list);
		return "/global/roborderinfo";
	}
	
	
	
	/**
	* 功能描述： 保存抢单信息
	* 输入参数:  @param goodsBasic
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月15日上午9:44:02
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/saveRobOrderRecord")
	@ResponseBody
	public Map<String, Object> saveRobOrderRecord(RobOrderRecord robOrderRecord,HttpServletRequest request){
		return robOrderRecordService.saveRecord(robOrderRecord);
	}
	
	/**
	* 功能描述： 抢单信息修改
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月28日上午10:53:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/confirmRobOrderRecord")
	@ResponseBody
	public Map<String, Object> confirmRobOrderRecord(RobOrderRecord robOrderRecord,String payPassword,String turckIds,HttpServletRequest request){
		 boolean isfalse = true;
		 String msg = "";
		 RobOrderRecord robOrderRecord_p = robOrderRecordService.getRecordById(robOrderRecord.getId());
		 if(robOrderRecord_p.getStatus().toString().equals("apply")){
			 isfalse = false;
			 msg = "当前抢单信息，正在等待货主审核，无法确认！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("dealing")){
			 isfalse = false;
			 msg = "当前抢单信息，货主正在确认中，无法确认！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("scrap")){
			 isfalse = false;
			 msg = "当前抢单信息，已经取消，无法确认！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("back")){
			 isfalse = false;
			 msg = "当前抢单信息，未通过货主审核，无法确认！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("withdraw")){
			 isfalse = false;
			 msg = "当前抢单信息，未通过货主审核，无法确认！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("end")){
			 isfalse = false;
			 msg = "当前抢单信息，已被确认，无需重复确认！";
			 return jsonView(isfalse,msg);
		 }
		 
		 robOrderRecordService.saveConfirmRobOrder(robOrderRecord,payPassword);
		 return jsonView(true,"抢单确认成功，已生成订单！");
	}
	
	/**
	* 功能描述： 
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月1日下午3:11:59
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/updateRobOrderRecord")
	@ResponseBody
	public Map<String, Object> updateRobOrderRecord(RobOrderRecord robOrderRecord,HttpServletRequest request){
		 boolean isfalse = true;
		 String msg = "";
		 RobOrderRecord robOrderRecord_p = robOrderRecordService.getRecordById(robOrderRecord.getId());
		 if(robOrderRecord_p.getStatus().toString().equals("apply")){
			 isfalse = false;
			 msg = "当前抢单信息，正在等待货主审核，无法修改，如需修改，请撤回抢单申请！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("dealing")){
			 isfalse = false;
			 msg = "当前抢单信息，正在确认中，无法修改！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("success")){
			 isfalse = false;
			 msg = "当前抢单信息，已经通过审核，无法修改！";
			 return jsonView(isfalse,msg);
		 }
		 if(robOrderRecord_p.getStatus().toString().equals("scrap")){
			 isfalse = false;
			 msg = "当前抢单信息，已经取消，无法修改！";
			 return jsonView(isfalse,msg);
		 }
		 robOrderRecordService.updateRobOrderRecord(robOrderRecord);
		 return jsonView(true,"抢单成功，已提交给货主，等待货主确认！");
	}
	
	/**
	* 功能描述： 查询我的抢单数据
	* 输入参数:  @param goodsBasic
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日上午10:36:16
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getMyRobOrderPage")
	@ResponseBody
	public Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord,HttpServletRequest request){
		return robOrderRecordService.getMyPage(robOrderRecord, getStart(request), getLimit(request));
	}
	
	
	/**
	* 功能描述： 取消抢单
	* 输入参数:  @param robOrderRecord
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月27日下午4:52:14
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/saveRobOrderCancel")
	@ResponseBody
	public Map<String, Object> saveRobOrderCancel(RobOrderRecord robOrderRecord,HttpServletRequest request){
		Account account = UserUtil.getUser(request);
		RobOrderRecord robOrderRecord_p = robOrderRecordService.getRecordById(robOrderRecord.getId());
		if(!robOrderRecord_p.getAccount().getId().equals(account.getId())){
			return jsonView(false,"您取消的抢单，不是自己的，无法取消！");
		}
		if(!robOrderRecord_p.getStatus().toString().equals("apply")){
			 String msg = "当前抢单信息，";
			 switch (robOrderRecord_p.getStatus().toString()) {
				 case "dealing":
					 	msg = msg+"货主正在处理中，无法取消！";
					 	return jsonView(false,msg);
				 case "scrap":
						msg = msg+"已取消该订单，无需重复取消！";
						return jsonView(false,msg);
				 case "end":
						msg = "当前抢单已生成订单，无法取消该抢单！";
						return jsonView(false,msg);
				 case "ordercompletion":
						msg = "当前订单已完结，无法取消该订单！";
						return jsonView(false,msg);
						
			 }
		 } 
		if(robOrderRecordService.getRobOrderCancelByDay() >= 500){
			return jsonView(false,"取消失败，每天只能取消500次抢单！");
		}else{
			robOrderRecordService.updateRobOrderCancel(robOrderRecord,account,null);
		}
		return jsonView(true,"取消抢单成功！");
	}
	
	/**
	* 功能描述： 抢单撤回
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月28日上午9:29:24
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/withdrawRobOrder")
	@ResponseBody
	public Map<String, Object> withdrawRobOrder(String id,HttpServletRequest request){
		 Account user = UserUtil.getUser(request);
		 RobOrderRecord robOrderRecord = robOrderRecordService.getRecordById(id);
		 if(!user.getId().equals(robOrderRecord.getAccount().getId())){
			 return jsonView(false,"您所撤回的抢单，不是自己的，不能进行撤回操作！");
		 }
		 if(!robOrderRecord.getStatus().toString().equals("apply")){
			 String msg = "当前抢单信息，";
			 switch (robOrderRecord.getStatus().toString()) {
			 case "dealing":
				msg = msg+"管理员正常处理，无法撤回！";
				break;
			 case "back":
					msg = msg+"已处于【退回状态】，无法撤回，请刷新页面查看状态！";
					break;
			 case "success":
					msg = msg+"已经通过审核，无法撤回，请刷新页面查看状态！";
					break;
			 case "scrap":
					msg = msg+"已作废，无法撤回，请刷新页面查看状态！";
					break;
			 case "withdraw":
					msg = msg+"已撤回，无需重复操作！";
					break; 
			 }
			 return jsonView(false,msg);
		 } 
		 robOrderRecordService.updateWithdrawRobOrder(robOrderRecord);
		 return jsonView(true,"抢单信息撤回成功！");
	}
	
	
	/**
	* 功能描述：     订单统计
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月21日下午2:35:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/robOrderStatistics", method = RequestMethod.GET)
	protected String getGoodsStatistics(Model model,String id, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		if((account.getCompany().getCompanyType().getName().equals("车队"))&&account.getUserType().equals(UserType.company)){//车队管理员
			List<Account> userList = accountService.getListByCompanyId(account.getCompany().getId());
			model.addAttribute("userList", userList);
		}
		if((account.getCompany().getCompanyType().getName().equals("货主"))&&account.getUserType().equals(UserType.company)){//货主管理员
			List<Account> userList = accountService.getListByCompanyId(account.getCompany().getId());
			model.addAttribute("comUserList", userList);
		}
		
		model.addAttribute("years", DateUtil.getYearQian(3));
		model.addAttribute("yearDay", DateUtil.getYear());
		return "/order/roborder/robOrderStatistics";
	}
	
	/**
	* 功能描述： 货物统计，月统计
	* 输入参数:  @param year 统计年份
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日上午11:48:09
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/robOrderMonthStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String,Object> getGoodsMonthStatistics(RobOrderRecord robOrderRecord,String year,Model model,HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		
		Map<String, Object> month_map = DateUtil.getMonths(year);
		List<String> months = (List<String>)month_map.get("months");
		List counts = robOrderRecordService.getAllRobOrderMonthCount(months, account, "months",robOrderRecord);
		List orderCompletioncounts = robOrderRecordService.getOrderCompletionMonthCount(months, account, "months",robOrderRecord);
		List weight = robOrderRecordService.getAllRobOrderMonthWeight(months, account, "months",robOrderRecord);
		List endWeight = robOrderRecordService.getOrderCompletionMonthWeight(months, account, "months",robOrderRecord);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts.get(0));
		map.put("orderCompletioncounts", orderCompletioncounts.get(0));
		map.put("weight", weight.get(0));
		map.put("endWeight", endWeight.get(0));
		map.put("xAxis", month_map.get("months_v"));
		map.put("success", true);
		map.put("msg", "统计数据成功！");
		return map;
	}
	
	/**
	* 功能描述： 平台商户发货排名统计
	* 输入参数:  @param year
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月24日下午2:56:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/robOrderRankingStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String,Object> getGoodsRankingStatistics(String ranking,String type,Model model,HttpServletRequest request) {
		//type="company";
		//ranking="10";
		Map<String, Object> month_map = robOrderRecordService.getRobOrderRankingStatistics(ranking == null ?0:Integer.parseInt(ranking), type);
		return month_map;
	}
}
