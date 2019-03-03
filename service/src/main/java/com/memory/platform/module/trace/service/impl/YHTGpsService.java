package com.memory.platform.module.trace.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mchange.v2.beans.BeansUtils;
import com.memory.platform.entity.gps.VechicleGps;
import com.memory.platform.global.Coordinate;
import com.memory.platform.global.HttpClientKeepSession;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.trace.service.IYHTGpsService;

/*
 * 易航通GPS 服务
 * */
public class YHTGpsService implements IYHTGpsService {
	// 存在redis的map key 这个map 对应的 deviceID key value 为GPS数据
	public String vehicleGpsData = "vehicleGpsData";
	public String carIdStringToDeviceID = "carIdStringToDeviceID";

	String userName = "易林物流";
	String password = "567890";

	String baseUrl = "http://api.yhtgps.com/manage/api/";

	 

	public String getListVehicleUrl() {
		return  baseUrl + "listVehicle";
	}

 

	Logger log = Logger.getLogger(YHTGpsService.class);

	HttpClientKeepSession staticHttpClient;
	@Autowired
	MemShardStrore memStore;
   
	public String getBaseUrl() {
		return baseUrl;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	@PostConstruct
	public void init() {
		staticHttpClient = new HttpClientKeepSession();
	}

	/*
	 * 获取所有车辆信息
	 */
	@Override
	public Map<String, Object> getAllVehicle() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("size", "999999999");
		Map<String, Object> ret = this.post1(this.getListVehicleUrl(), map);
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

	public Map<String, Object> post1(String url, Map<String, String> params) {
		Map<String, Object> ret = null;
		if (url.contains("?") == false) {
			url += "?";
		}
		params.put("userName", userName);
		params.put("userPwd", this.password);

		String keyAndValue = this.parseKeyAndValue(params, "utf-8");
		url += keyAndValue;
		String result = AcpService.get(url, "utf-8");
		// log.info("result:" + result);
		if (StringUtil.isEmpty(result) == false) {
			if (result.indexOf("error") != -1) {
			} else {
				ret = JsonPluginsUtil.jsonToMap(result);
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

		// params.put("key", getConfig().getKey());
		String keyAndValue = this.parseKeyAndValue(params);

		// url = url + keyAndValue;
		String result = AcpService.get(url, "utf-8");
		;
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

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public VechicleGps getGpsDataWithCardNo(String cardNo) {
		return this.getGpsDataWithCardNo(memStore.getClient(), cardNo);
	}
	@Override
	public VechicleGps getGpsDataWithCardNo(Jedis client, String cardNo) {
		String deviceID = client.hget(this.carIdStringToDeviceID, cardNo);
		if (StringUtil.isEmpty(deviceID)) {
			return null;
		}
		return this.getGpsDataWithDeviceID(client, deviceID);
	}
	@Override
	public List<VechicleGps> getGpsDataWithDeviceIDs(List<String> deviceIDS) {
		List<VechicleGps> ret = new ArrayList<VechicleGps>(deviceIDS.size());
		Jedis client = memStore.getClient();
		for (String deviceID : deviceIDS) {
			ret.add(this.getGpsDataWithDeviceID(client, deviceID));
		}
		return ret;
	}
	@Override
	public VechicleGps getGpsDataWithDeviceID(String deviceID) {
		Jedis client = memStore.getClient();
		return getGpsDataWithDeviceID(client, deviceID);
	}

	
	private VechicleGps getGpsDataWithDeviceID(Jedis client, String deviceID) {
		String json = client.hget(this.vehicleGpsData, deviceID);
		VechicleGps ret = null;
		do {
			if (StringUtil.isEmpty(json))
				break;
			ret = JsonPluginsUtil.jsonToBean(json, VechicleGps.class);
		} while (false);
		return ret;
	}

	/*
	 * 自动同步车辆gps数据到redis
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void autoSaveVechicle() {
		Map<String, Object> gpsData = this.getAllVehicle();
		if (gpsData == null) {
			log.info("获取GPS信息异常");
			return;
		}
		JSONArray array = (JSONArray) gpsData.get("views");
		if (array == null) {
			log.info("获取GPS信息异常");
			return;
		}
		Jedis client = memStore.getClient();
		for (int i = 0; i < array.size(); i++) {
			Map<String, Object> info = (Map<String, Object>) array.get(i);
			if(info.get("bdLng")==null) continue;
			float bdLng = Float.parseFloat( info.get("bdLng").toString());
			float bdLat = Float.parseFloat( info.get("bdLat").toString());
			float lng = Float.parseFloat( info.get("lng").toString());
			float lat =  Float.parseFloat( info.get("lat").toString());
			Coordinate cordinate = GaoDeWebService.bd_decrypt(new Coordinate(bdLng, bdLat));
			info.put("gdLng", cordinate.getLongitude());
			info.put("gdLat", cordinate.getLatitude());
			String deviceID = (String) info.get("deviceId");
			String json = JsonPluginsUtil.mapToJson(info, null, false);
			client.hset(this.vehicleGpsData, deviceID, json);
			String carIDString = (String) info.get("carIdString");
			if (StringUtil.isNotEmpty(carIDString)) {
				if (carIDString.length() > 7) {
					carIDString = carIDString.substring(0, 7);
				}
				client.hset(this.carIdStringToDeviceID, carIDString, deviceID);
			}
		}
	}
}
