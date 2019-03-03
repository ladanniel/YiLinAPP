package com.memory.platform.module.system.controller;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
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
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.AppUpload;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAppUploadService;

/**
 * 
 * 创 建 人： yico-cj 日 期： 2016年7月16日 上午9:26:37 修 改 人： 日 期： 描 述： 版 本 号： V1.0
 */
@Controller
@RequestMapping("/system/appupload")
public class AppUploadController extends BaseController {

	@Autowired
	private IAppUploadService appUploadService;

	/**
	 * 
	 * 功能描述： app应用更新列表界面 输入参数: @param search 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yico-cj 日 期: 2016年7月30日上午10:45:42 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(String search, HttpServletRequest request) {
		return this.appUploadService.getPage(search, getStart(request), getLimit(request));
	}

	/**
	 * 
	 * 功能描述：app添加界面 输入参数: @param request 输入参数: @param model 输入参数: @return 异 常： 创
	 * 建 人: yico-cj 日 期: 2016年7月30日上午10:46:28 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(HttpServletRequest request, Model model) {
		return "/system/appupload/add";
	}

	/**
	 * 
	 * 功能描述： app添加更新 输入参数: @param request 输入参数: @param file 输入参数: @param
	 * versionName 输入参数: @param versionCode 输入参数: @param content 输入参数: @return 异
	 * 常： 创 建 人: yico-cj 日 期: 2016年7月30日上午10:47:33 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> addStaff(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file, String versionName, Integer versionCode,
			String content, Integer type) {
		if (file == null || file.getSize() < 1) {
			return jsonView(false, "请选择上传文件。");
		}
		if (type == null) {
			return jsonView(false, "请选择apk类型");
		}
		Account user = UserUtil.getUser(request);
		// 判断图片格式
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		// 将文件后缀转成小写
		if (StringUtils.isNotBlank(ext)) {
			ext = ext.toLowerCase();
		}
		if (!"apk".equals(ext)) {
			return jsonView(false, "上传文件格式不正确");
		}
		String path = AppUtil.getUpLoadPath(request) + "/upload";
		File targetFile = new File(path, System.currentTimeMillis() + "." + ext);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			jsonView(false, "文件上传失败");
		}
		AppUpload app = new AppUpload();
		app.setVersionCode(versionCode);
		app.setVersionName(versionName);
		app.setContent(content);
		app.setCreate_time(new Date());
		if (type == 0)
			app.setType(AppUpload.Type.owner);
		else if (type == 1)
			app.setType(AppUpload.Type.driver);
		app.setPath("upload/" + targetFile.getName());
		app.setAdd_user_id(user.getId());
		appUploadService.save(app);
		return jsonView(true, "apk上传成功");
	}

	/**
	 * 
	 * 功能描述： 验证版本号是否重复 输入参数: @param model 输入参数: @param version 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yico-cj 日 期: 2016年7月30日上午10:50:13 修 改
	 * 人: 日 期: 返 回：boolean
	 */
	@RequestMapping(value = "/checkAppVersion", method = RequestMethod.POST)
	@ResponseBody
	protected boolean checkAppVersion(Model model, String version, HttpServletRequest request) {
		AppUpload app = appUploadService.checkAppVersion(version);
		return app == null ? true : false;
	}
}
