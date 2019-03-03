package com.memory.platform.module.system.service;

import java.util.Map;

import com.memory.platform.entity.sys.AppUpload;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年7月16日 上午11:01:03 
* 修 改 人： 
* 日   期： 
* 描   述： app上传service
* 版 本 号：  V1.0
 */
public interface IAppUploadService {

	/**
	 * 
	* 功能描述： 读取app列表
	* 输入参数:  @param search
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年7月30日上午10:50:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	Map<String, Object> getPage(String search, int start, int limit);

	/**
	 * 
	* 功能描述： 保存app版本信息
	* 输入参数:  @param app
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年7月30日上午10:51:11
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void save(AppUpload app);

	/**
	 * 
	* 功能描述： 检查app版本号是否重复
	* 输入参数:  @param version
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年7月30日上午10:52:53
	* 修 改 人: 
	* 日    期: 
	* 返    回：AppUpload
	 */
	AppUpload checkAppVersion(String version);

	Map<String, Object> getNewAppVersion(AppUpload.Type type);

}
