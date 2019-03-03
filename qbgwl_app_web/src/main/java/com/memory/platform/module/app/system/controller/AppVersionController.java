package com.memory.platform.module.app.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.entity.sys.AppUpload;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAppUploadService;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年7月26日 下午4:23:24 
* 修 改 人： 
* 日   期： 
* 描   述： APP版本更新信息 
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/app/appupload")
public class AppVersionController extends BaseController {
	
	@Autowired
	private IAppUploadService appUploadService;

	/**
	 * 
	* 功能描述： 获取最新版本信息
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年7月26日下午4:39:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "versionInfo")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> versionInfo(AppUpload.Type type){
		Map<String, Object> map = appUploadService.getNewAppVersion(type);
		return jsonView(true, "成功获取版本信息。",map); 
	}
}
