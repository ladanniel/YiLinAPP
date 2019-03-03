package com.memory.platform.module.app.truck.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.AxleType;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.truck.service.IAxleTypeService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：拖挂轮轴类型控制器
* 版    本    号：  V1.0
*/
@Controller
@RequestMapping("/truck/axleType")
public class AxleTypeController extends BaseController {
	
	@Autowired
	private IAxleTypeService axleTypeService;
	
	@Autowired
	private IAccountService accountService;
	
	/**
	 * 
	* 功能描述： 获取拖挂轮轴类型
	* 输入参数:  @param
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getAxleType")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getAxleType(@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list =axleTypeService.getAxleTypeListForMap();
		return jsonView(true, "成功获取拖挂轮轴类型列表!",list);
	}
}
