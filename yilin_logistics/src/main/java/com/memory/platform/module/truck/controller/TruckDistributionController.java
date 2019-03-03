package com.memory.platform.module.truck.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.truck.TruckDistribution;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.truck.service.ITruckDistributionService;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月15日 下午3:44:10 
* 修 改 人： 
* 日   期： 
* 描   述： 车辆分配历史纪录控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/truck/truckDistribution")
public class TruckDistributionController extends BaseController {

	@Autowired
	private ITruckDistributionService truckDistributionService;

	/**
	* 功能描述： 
	* 输入参数:  @param truckDistribution
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日下午3:37:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(TruckDistribution truckDistribution, HttpServletRequest request) {
		return this.truckDistributionService.getPage(truckDistribution, getStart(request), getLimit(request));
	}

	 
}
