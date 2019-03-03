package com.memory.platform.module.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.sys.AppUpload;
import com.memory.platform.module.system.dao.AppUploadDao;
import com.memory.platform.module.system.service.IAppUploadService;

/**
 * 
 * 创 建 人： yico-cj 日 期： 2016年7月16日 上午11:01:59 修 改 人： 日 期： 描 述： app上传业务层实现 版 本 号：
 * V1.0
 */
@Service
public class AppUploadService implements IAppUploadService {

	@Autowired
	private AppUploadDao appUploadDao;

	@Override
	public Map<String, Object> getPage(String search, int start, int limit) {
		return appUploadDao.getPage(search, start, limit);
	}

	@Override
	public void save(AppUpload app) {
		appUploadDao.save(app);
	}

	@Override
	public AppUpload checkAppVersion(String version) {
		return appUploadDao.checkAppVersion(version);
	}

	@Override
	public Map<String, Object> getNewAppVersion(AppUpload.Type type) {
		String sql = " select * from sys_appupload where type = :type group by create_time desc ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type.ordinal());
		List<AppUpload> list = appUploadDao.getListBySql(sql, map,AppUpload.class);
		if (list.size() > 0) {
			Map<String, Object> ret = new HashMap<String, Object>();
			AppUpload a = list.get(0);
			ret.put("id", a.getId());
			ret.put("versionCode", a.getVersionCode());
			ret.put("versionName", a.getVersionName());
			ret.put("comment", a.getContent());
			ret.put("fileUrl", a.getPath());
			ret.put("createtime", a.getCreate_time());
			ret.put("enforce", "1");
			ret.put("type", a.getType());
			return ret;
		} else {
			return null;
		}

	}
}
