package com.memory.platform.module.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.memory.platform.entity.sys.AppUpload;
import com.memory.platform.module.global.dao.BaseDao;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年7月16日 上午11:03:50 
* 修 改 人： 
* 日   期： 
* 描   述： app上传DAO 
* 版 本 号：  V1.0
 */
@Repository
public class AppUploadDao extends BaseDao<AppUpload>{

	/**
	 * 
	* 功能描述： 获取appjson数据
	* 输入参数:  @param search
	* 输入参数:  @param start
	* 输入参数:  @param limit
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年7月30日上午10:53:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	public Map<String, Object> getPage(String search, int start, int limit) {
		String hql = " from AppUpload where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(search) ) {
			hql += " and version like :version ";
			map.put("version","%"+search+"%");
		}
		return this.getPage(hql, map, start, limit);
	}

	/**
	 * 
	* 功能描述： 验证app版本信息
	* 输入参数:  @param version
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yico-cj
	* 日    期: 2016年7月30日上午10:54:04
	* 修 改 人: 
	* 日    期: 
	* 返    回：AppUpload
	 */
	public AppUpload checkAppVersion(String version) {
		String hql = " from AppUpload where version=:version ";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version",version);
		Map<String, Object> page = this.getPage(hql, map, 0, 10);
		@SuppressWarnings("unchecked")
		List<AppUpload> list = (List<AppUpload>) page.get("rows");
		if(list != null && list.size()>0) { 
			return list.get(0);
		}else {
			return null;
		}
	}

}
