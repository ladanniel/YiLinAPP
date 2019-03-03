package com.memory.platform.module.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.order.service.IRobOrderRecordInfoService;
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
@RequestMapping("/order/roborderinfo")
public class RobOrderInfoController extends BaseController {

	@Autowired
	IRobOrderRecordService robOrderRecordService;
	@Autowired
	IRobOrderRecordInfoService robOrderRecordInfoService;
	
	/**
	* 功能描述： 货物抢单详细信息
	* 输入参数:  @param robOrderRecordId
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月28日上午10:09:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	@RequestMapping("/getListGoodsDetail")
	@ResponseBody
	public List<RobOrderRecordInfo> getListGoodsDetail(String robOrderRecordId,HttpServletRequest request){
		return robOrderRecordInfoService.getListByRobOrderRecordId(robOrderRecordId);
	}
	
	/**
	* 功能描述： 查询已拆分的货物信息
	* 输入参数:  @param pid 父级ID robOrderId 抢单ID
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午2:23:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	@RequestMapping("/getListGoodsDetailByPid")
	@ResponseBody
	public List<Map<String, Object>> getListGoodsDetailByPid(String pid,String robOrderId,HttpServletRequest request){
		return robOrderRecordInfoService.getListByRobOrderRecordInfoByPid(pid,robOrderId);
	}
	
	
	/**
	* 功能描述： 进入货物拆分/合并页面
	* 输入参数:  @param id
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月9日下午3:30:18
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping("/robsplitmerge")
	public String getGoodsDetailById(String id,Model model, HttpServletRequest request){
		RobOrderRecordInfo robOrderRecordInfo = robOrderRecordInfoService.getRobOrderRecordInfoById(id);
		model.addAttribute("robOrderRecordInfo", robOrderRecordInfo);
		return "/order/roborder/robsplitmerge";
	}
	
	/**
	* 功能描述： 货物拆分保存
	* 输入参数:  @param pid
	* 输入参数:  @param robOrderId
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年8月10日上午10:10:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<RobOrderRecordInfo>
	 */
	@RequestMapping("/saveRobOrderSplit")
	@ResponseBody
	public Map<String, Object> saveRobOrderSplit(String robOrderId,String robOrderRecordInfoId,Double surplusWeight,String splitRobOrderRecordInfo,HttpServletRequest request){
		return robOrderRecordInfoService.saveRobOrderSplit(robOrderId, robOrderRecordInfoId, surplusWeight, splitRobOrderRecordInfo);
	}
	
	
	
}
