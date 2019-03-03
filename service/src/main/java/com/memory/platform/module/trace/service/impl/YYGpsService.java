package com.memory.platform.module.trace.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.HttpClientKeepSession;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.module.trace.dto.StaticLoginResponseDTO;
import com.memory.platform.module.trace.dto.YYAddClientRespDTO;
import com.memory.platform.module.trace.dto.YYAddGroupReqDTO;
import com.memory.platform.module.trace.dto.YYAddGroupRespDTO;
import com.memory.platform.module.trace.dto.YYAddVehicleReqDTO;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO.Vehicle;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO.YYGroup;
import com.memory.platform.module.trace.dto.YYQueryGroupRespDTO;
import com.memory.platform.module.trace.dto.YYQueryVehicelRespDTO;
import com.memory.platform.module.trace.dto.YYQueryVehicleReq;
import com.memory.platform.module.trace.dto.YYStandResponseDTO;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO;
import com.memory.platform.module.trace.dto.YYLocationResponseDTO;
import com.memory.platform.module.trace.dto.YYLoginResponseDTO;
import com.memory.platform.module.trace.dto.YYQueryClientResponseDTO;
import com.memory.platform.module.trace.service.IYYGpsService;

/**
 * 亿源GPS服务
 * 
 * @author rog
 *
 */
public class YYGpsService implements IYYGpsService {
	// "
	// {"id":154782,"compyname":"齐博谷物流平台","compyperson":"--","phone":"13037806522","officeaddrs":"贵州省贵阳市"},"
	Logger loger = Logger.getLogger(YYGpsService.class);
	// 默认的客户ID
    String clientId;
	// 默认的分组ID 所有车辆都加入这个客户和分组里面
	String groupId;
	String userName;
	String pwd;
	String baseUrl;
	String version = "1";
	String staticLoginUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/zdyloginAction_autologin.action";
	String staticAddGroupUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_add.action";
	String staticAddClientUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_add.action";
	String staticQueryClientUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_find.action";
	String staticDeleteClientUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_del.action";
	String staticDeleteGroupUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_del.action";
	String staticUpdateGroupUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_update.action";
	String staticQueryGroupUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_find.action";
	String staticAddVehicleUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_add.action";
	String staticdeleteVehicleUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_del.action";
	String staticGetDicDataUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_zidian.action";
	String staticUpdateVehicleUrl = "http://vip4.exlive.cn/synthReports/mobile_to_synth/mobileTosynthAction_update.action";
	// String
	// staticAddGroupUrl="http://vip4.exlive.cn/synthReports/synthReports/mobileosynthaction_add.action";
	String staticServerID = "10125";
	StaticLoginResponseDTO staticLoginResp;
	HttpClientKeepSession staticHttpClient;
	public Logger log = Logger.getLogger(YYGpsService.class);
	YYLoginResponseDTO loginResp = null;
	StaticLoginResponseDTO groupName = null;

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getPwd() {
		return pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void init() throws BusinessException {
		// return;
		staticHttpClient = new HttpClientKeepSession();
		return ;
//		login();
//		test();
	}

	// 测试接口
	private void test() {
		// YYGroupResponseDTO groups = getVehicles();
		// YYAddVehicleReqDTO req = new YYAddVehicleReqDTO();
		// req.setCarName("贵A81099");
		// req.setClientID(this.clientId);
		// //req.setVehicleTypeId(vehicleTypeId);
		// req.setSim("1064879259406");
		// req.setGprs("868120160717960");
		// req.setGoupListPage(Integer.parseInt( groupId));
		// YYStandResponseDTO resp = addVehicle(req);
		// if(resp.getFlag()){
		// loger.info("添加车辆成功");
		// YYGroupResponseDTO groups1 = getVehicles();
		// for (int i = 0; i < groups1.getGroups().size(); i++) {
		// YYGroup group = groups1.getGroups().get(i);
		// for(int j =0;j< group.getVehicles().size();j++){
		// Vehicle v =group.getVehicles().get(j);
		// String id = v.getId()+"";
		// String key = v.getvKey();
		//
		// YYLocationResponseDTO location = this.getVeLocation(id, key);
		// }
		// }
		//
		//
		// }

	}

	/**
	 * 获取数据字典表
	 * 
	 * @return
	 */
	public Map<String, Object> getDicData() {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		Map<String, String> parm = new HashMap<String, String>();
		ret = post2(staticGetDicDataUrl, parm, ret.getClass());
		return ret;
	}

	/**
	 * 修改客户
	 * 
	 * @param id
	 * @param compyname
	 * @param phone
	 * @param officeaddrs
	 * @return
	 */
	public YYQueryClientResponseDTO updateClient(String id, String compyname, String phone, String officeaddrs) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", "client");
		params.put("client.id", id);
		params.put("client.compyname", compyname);
		params.put("client.phone", phone);
		params.put("exkey", staticLoginResp.getExkey());
		YYQueryClientResponseDTO clientResponseDto = post2(staticDeleteClientUrl, params,
				YYQueryClientResponseDTO.class);
		return clientResponseDto;
	}

	/**
	 * 删除客户
	 * 
	 * @param id
	 * @return
	 */
	public YYQueryClientResponseDTO deleteClient(String id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", "client");
		params.put("client.id", id);
		params.put("exkey", staticLoginResp.getExkey());
		YYQueryClientResponseDTO clientResponseDto = post2(staticDeleteClientUrl, params,
				YYQueryClientResponseDTO.class);
		return clientResponseDto;
	}

	/**
	 * 静态接口登陆 同一个会话
	 * 
	 * @return
	 */
	public StaticLoginResponseDTO staticLogin() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("puser.userName", userName);
		params.put("puser.userPwd", pwd);
		params.put("puser.serverId", staticServerID);
		params.put("pt", "1");
		StaticLoginResponseDTO ret = post2(staticLoginUrl, params, StaticLoginResponseDTO.class);
		return ret;

	}

	/**
	 * 普通接口登陆 gps数据登陆
	 * 
	 * @throws BusinessException
	 */
	public void login() throws BusinessException {
		// 静态接口登陆
		staticLoginResp = staticLogin();
		if (staticLoginResp == null || staticLoginResp.getFlag() == false) {
			throw new BusinessException("gps静态接口登陆失败");
		}
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("compyname", "齐帛谷物流平台");
		YYQueryClientResponseDTO client = queryClient(queryParam);
		if (client == null || client.getData().size() == 0) {
			YYAddClientRespDTO addClient = addClient("齐帛谷物流平台", "13037806522", "贵州省贵阳市");
			clientId = addClient.getData().get(0).getId() + "";
		}
		if (StringUtil.isEmpty(clientId)) {
			clientId = client.getData().get(0).getId() + "";
		}
		YYQueryGroupRespDTO group = queryGroup("贵州齐帛谷分组001");

		if (group == null || group.getData().size() == 0) {
			YYAddGroupRespDTO groupRespDTO = AddGroup("贵州齐帛谷分组001", 100000, 100000, Integer.parseInt(clientId));
			groupId = groupRespDTO.getData().get(0).getId() + "";
		}
		if (StringUtil.isEmpty(groupId)) {
			groupId = group.getData().get(0).getId();
		}
		// http接口登陆
		loginGps();

	}

	/**
	 * 添加客户
	 * 
	 * @param compyname
	 *            企业名称
	 * @param phone
	 *            企业联系电话
	 * @param officeaddrs
	 *            办公地址
	 */
	public YYAddClientRespDTO addClient(String compyname, String phone, String officeaddrs) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", "client");
		params.put("client.compyname", compyname);
		params.put("client.phone", phone);
		params.put("client.officeaddrs", officeaddrs);
		YYAddClientRespDTO ret = post2(staticAddClientUrl, params, YYAddClientRespDTO.class);
		return ret;
	}

	/**
	 * 查询客户
	 * 
	 * @param colNameAndValue
	 *            查询条件MAP 列明和列值
	 */
	public YYQueryClientResponseDTO queryClient(Map<String, String> colNameAndValue) {
		// 字段名
		String pro = "";
		// 字段值
		String pva = "";
		Map<String, String> params = new HashMap<String, String>();
		if (colNameAndValue != null) {
			for (String key : colNameAndValue.keySet()) {
				// pro+=
			}

		}
		params.put("tableName", "client");
		params.put("exkey", staticLoginResp.getExkey());

		YYQueryClientResponseDTO ret = post2(staticQueryClientUrl, params, YYQueryClientResponseDTO.class);
		return ret;
	}

	/**
	 * http接口登陆
	 * 
	 * @throws BusinessException
	 */
	public void loginGps() throws BusinessException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("method", "loginSystem");
		params.put("name", userName);
		params.put("pwd", pwd);
		loginResp = post1(baseUrl, params, YYLoginResponseDTO.class);
		if (loginResp == null || loginResp.isSuccess() == false) {
			throw new BusinessException("登陆GPS失败");
		}
	}

	/**
	 * 添加分组
	 * 
	 * @param req
	 * @return
	 */
	public YYAddGroupRespDTO AddGroup(String groupName, int userCount, int vhcCount, int clientID) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("group.groupName", groupName);
		params.put("group.userCount", (userCount <= 0 ? 1000 : userCount) + "");
		params.put("group.vhcCount", (vhcCount <= 0 ? 1000 : vhcCount) + "");
		params.put("tableName", "group");
		params.put("userId", clientID + "");
		params.put("exkey", staticLoginResp.getExkey());
		YYAddGroupRespDTO ret = post2(staticAddGroupUrl, params, YYAddGroupRespDTO.class);
		return ret;
	}

	/**
	 * 修改分组
	 * 
	 * @param groupID
	 * @param groupName
	 * @param userCount
	 * @param vhcCount
	 * @param ClientID
	 * @return
	 */
	public YYAddGroupRespDTO updateGroup(String groupID, String groupName, int userCount, int vhcCount, int ClientID) {
		YYAddGroupRespDTO ret = null;
		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("group.id", groupID);
			params.put("group.groupName", groupName);
			params.put("group.userCount", (userCount <= 0 ? 1000 : userCount) + "");
			params.put("group.vhcCount", (vhcCount <= 0 ? 1000 : vhcCount) + "");
			params.put("tableName", "group");
			params.put("exkey", staticLoginResp.getExkey());
			ret = post2(staticUpdateGroupUrl, params, YYAddGroupRespDTO.class);
		} while (false);
		return ret;
	}

	/**
	 * 删除group
	 * 
	 * @param groupID
	 * @return
	 */
	public YYAddGroupRespDTO deleteGroup(String groupID) {
		YYAddGroupRespDTO ret = null;

		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("tableName", "group");
			params.put("group.id", groupID);
			params.put("exkey", staticLoginResp.getExkey());
			ret = post2(staticDeleteGroupUrl, params, YYAddGroupRespDTO.class);
		} while (false);
		return ret;
	}

	/**
	 * 查询分组
	 * 
	 * @param groupName
	 * @return
	 */
	public YYQueryGroupRespDTO queryGroup(String groupName) {

		YYQueryGroupRespDTO ret = null;

		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("tableName", "group");
			if (StringUtil.isEmpty(groupName) == false) {
				params.put("group.groupName", groupName);
			}
			params.put("exkey", staticLoginResp.getExkey());
			ret = post2(staticQueryGroupUrl, params, YYQueryGroupRespDTO.class);
		} while (false);
		return ret;
	}

	/**
	 * 添加车辆
	 * 
	 * @param req
	 * @return
	 */
	public YYStandResponseDTO addVehicle(YYAddVehicleReqDTO req) {
		YYStandResponseDTO ret = null;

		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("tableName", "vehicle");
			params.put("vehicel.carName", req.getCarName());
			params.put("vehicel.clientID", this.clientId);
			params.put("vehicel.gprs", req.getGprs());
			params.put("vehicel.sim", req.getSim());
			params.put("vehicel.mobileId", req.getMobileId());
			params.put("vehicel.vehicleTypeId", req.getVehicleTypeId());
			params.put("vehicel.overduetime", req.getOverduetime());
			params.put("vehicel.remark", req.getRemark());
			params.put("cpys", req.getCpys() + "");
			params.put("sccj", req.getSccj());
			params.put("goupListPage", Integer.parseInt(groupId) + "");
			params.put("exkey", staticLoginResp.getExkey());
			ret = post2(staticAddVehicleUrl, params, YYStandResponseDTO.class);
		} while (false);
		return ret;
	}

	/**
	 * 删除车辆
	 * 
	 * @param req
	 * @return
	 */
	public YYStandResponseDTO deleteVehicle(String id) {
		YYStandResponseDTO ret = null;

		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("tableName", "vehicle");
			params.put("vehicel.id", id);

			params.put("exkey", staticLoginResp.getExkey());
			ret = post2(staticdeleteVehicleUrl, params, YYStandResponseDTO.class);
		} while (false);
		return ret;
	}

	public void queryVechicle() {

	}

	/**
	 * 修改车辆信息
	 * 
	 * @param id
	 * @param req
	 * @return
	 */
	public YYStandResponseDTO updateVehicle(String vehicleId, YYAddVehicleReqDTO req) {
		YYStandResponseDTO ret = null;

		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("tableName", "vehicle");
			params.put("vehicel.id", vehicleId);
			params.put("vehicel.carName", req.getCarName());
			params.put("vehicel.clientID", req.getClientID());
			params.put("vehicel.gprs", req.getGprs());
			params.put("vehicel.sim", req.getSim());
			params.put("vehicel.mobileId", req.getMobileId());
			params.put("vehicel.vehicleTypeId", req.getVehicleTypeId());
			params.put("vehicel.overduetime", req.getOverduetime());
			params.put("vehicel.remark", req.getRemark());
			params.put("cpys", req.getCpys() + "");
			params.put("sccj", req.getSccj());
			params.put("goupListPage", req.getGoupListPage() + "");
			params.put("exkey", staticLoginResp.getExkey());
			ret = post2(staticUpdateVehicleUrl, params, YYStandResponseDTO.class);
		} while (false);
		return ret;

	}

	/**
	 * 获取分组车辆
	 * 
	 * @return
	 */
	public YYGroupResponseDTO getGroup() {
		YYGroupResponseDTO ret = null;
		Map<String, String> params = new HashMap<String, String>();
		do {
			params.put("method", "loadVehicles");
			params.put("uid", loginResp.getUid() + "");
			params.put("uKey", loginResp.getuKey());
			ret = post1(baseUrl, params, YYGroupResponseDTO.class);
		} while (false);
		return ret;
	}

	/**
	 * 获取车辆位子信息
	 * 
	 * @param vid
	 *            车辆id
	 * @param vKey
	 *            车辆授权码
	 * @return
	 */
	public YYLocationResponseDTO getVeLocation(String vid, String vKey) {
		YYLocationResponseDTO ret = null;
		do {
			// http://120.55.187.229:89/gpsonline/GPSAPI?version=1&method=loadLocation&vid=8373507&vKey=9cf6994d1beea565182d587eb2456885
			Map<String, String> params = new HashMap<String, String>();
			params.put("method", "loadLocation");
			params.put("vid", vid);
			params.put("vKey", vKey);
			ret = post1(baseUrl, params, YYLocationResponseDTO.class);
		} while (false);
		return ret;

	}

	/**
	 * 获取所有车辆
	 * 
	 * @return
	 */
	public YYGroupResponseDTO getVehicles() {
		YYGroupResponseDTO ret = null;
		//
		do {
			Map<String, String> params = new HashMap<String, String>();
			params.put("method", "loadVehicles");
			params.put("uid", loginResp.getUid() + "");
			params.put("uKey", loginResp.getuKey());
			// String url = baseUrl+"?version=1&method=loadVehicles&uid" +
			// loginResp.getUid() + "&vKey=" + loginResp.getuKey();
			// ret = post1(baseUrl+, params, type)
			ret = post1(baseUrl, params, YYGroupResponseDTO.class);
		} while (false);
		return ret;
	}

	private String parseKeyAndValue(Map<String, String> params) {
		return parseKeyAndValue(params, null);
	}

	private String parseKeyAndValue(Map<String, String> params, String encode) {
		TreeMap<String, String> map = new TreeMap<>();
		for (String key : params.keySet()) {
			String value = params.get(key);
			try {
				if (StringUtil.isEmpty(encode) == false) {
					value = URLEncoder.encode(value, "utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(key, value);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("a=1");
		for (Entry<String, String> info : map.entrySet()) {
			sb.append("&" + info.getKey() + "=" + info.getValue());
		}
		return sb.toString();
	}

	public <T> T post1(String url, Map<String, String> params, Class<T> type) {
		T ret = null;
		if (url.contains("?") == false) {
			url += "?";
		}
		if (params.containsKey("version") == false) {
			params.put("version", version);
		}
		// params.put("key", getConfig().getKey());
		// String keyAndValue = this.parseKeyAndValue(params);
		// url = url + keyAndValue;

		String result = AcpService.post1(params, url, "utf-8");
		log.info("result:" + result);
		if (StringUtil.isEmpty(result) == false) {
			if (result.indexOf("error") != -1) {

			} else {
				Gson gons = new GsonBuilder().serializeNulls().create();
				// result = preResult(result);
				// result.replaceAll(regex, replacement);
				ret = gons.fromJson(result, type);

			}
		}
		return ret;
	}

	/**
	 * 使用保持连接的会话
	 * 
	 * @param url
	 * @param params
	 * @param type
	 * @return
	 */
	public <T> T post2(String url, Map<String, String> params, Class<T> type) {
		T ret = null;
		if (url.contains("?") == false) {
			url += "?";
		}
		if (params.containsKey("version") == false) {
			params.put("version", version);
		}
		// params.put("key", getConfig().getKey());
		String keyAndValue = this.parseKeyAndValue(params);
		// url = url + keyAndValue;
		String result = staticHttpClient.postResult(url, keyAndValue, "utf-8");
		log.info("result:" + result);
		if (StringUtil.isEmpty(result) == false) {
			if (result.indexOf("error") != -1) {

			} else {
				Gson gons = new GsonBuilder().serializeNulls().create();
				// result = preResult(result);
				// result.replaceAll(regex, replacement);
				try {
					ret = gons.fromJson(result, type);
				} catch (Exception e) {
				   log.info(e.toString());
				}
			

			}
		}
		return ret;

	}
	
	/**
	 * 查询车辆
	 * 
	 * @param colNameAndValue
	 *            查询条件MAP 列明和列值
	 */
	public YYQueryVehicelRespDTO queryVehicle(Map<String, String> params) {
		params.put("tableName", "vehicle");
		params.put("exkey", staticLoginResp.getExkey());
		YYQueryVehicelRespDTO ret = post2(staticQueryClientUrl, params, YYQueryVehicelRespDTO.class);
		return ret;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getClientId() {
		return this.clientId;
	}
}
