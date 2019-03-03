package com.memory.platform.module.trace.service.impl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.memory.platform.core.AppUtil;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Coordinate;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.global.sdk.HttpClient;
import com.memory.platform.global.sdk.SDKUtil;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.trace.dto.GaodeApiConfig;
import com.memory.platform.module.trace.dto.GeoReqDTO;
import com.memory.platform.module.trace.dto.GeoResponseDTO;
import com.memory.platform.module.trace.dto.ReGeoReqDTO;
import com.memory.platform.module.trace.dto.ReGeoResponseDTO;
import com.memory.platform.module.trace.service.IGaoDeWebService;

@Service
public class GaoDeWebService implements IGaoDeWebService {
	Logger log = Logger.getLogger(GaoDeWebService.class);
	@Autowired
	GaodeApiConfig apiConfig;
	@Autowired
	MemShardStrore memStore;

	
	@Override
	public GaodeApiConfig getConfig() {
		return apiConfig;

	}

	public Map<String, String> post(String url, Map<String, String> params) {
		Map<String, String> ret = new HashMap<String, String>();
		if (url.contains("?") == false) {
			url += "?";
		}
		params.put("key", apiConfig.getKey());
		String keyAndValue = this.parseKeyAndValue(params);
		url = url + keyAndValue;
		String result = AcpService.get(url, "utf-8");

		if (StringUtil.isEmpty(result) == false) {
			if (result.indexOf("error") != -1) {

			} else {

				ret = SDKUtil.convertResultStringToMap(result);
			}
		}
		return ret;

	}

	// 高德会把null 全部替换成[]
	private String preResult(String result) {
		return result.replaceAll(":\\[\\]", ":null").replaceAll(
				":\\[\\[\\]\\]", ":null");
	}

	public <T> T post1(String url, Map<String, String> params, Class<T> type) {
		T ret = null;
		if (url.contains("?") == false) {
			url += "?";
		}
		params.put("key", getConfig().getKey());
		String keyAndValue = this.parseKeyAndValue(params);
		url = url + keyAndValue;
		String result = AcpService.get(url, "utf-8");
		log.info("result:" + result);
		if (StringUtil.isEmpty(result) == false) {
			if (result.indexOf("error") != -1) {

			} else {
				Gson gons = new GsonBuilder().serializeNulls().create();
				result = preResult(result);
				// result.replaceAll(regex, replacement);
				ret = gons.fromJson(result, type);

			}
		}
		return ret;

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> post2(String url, Map<String, String> params) {
		Map<String, Object> ret = null;
		if (url.contains("?") == false) {
			url += "?";
		}
		params.put("key", getConfig().getKey());
		String keyAndValue = this.parseKeyAndValue(params);
		url = url + keyAndValue;
		String result = AcpService.get(url, "utf-8");
		if (log.isInfoEnabled()) {
			log.info("result:" + result);
		}
		if (StringUtil.isEmpty(result) == false) {
			if (result.indexOf("error") != -1) {

			} else {
				ret = JsonPluginsUtil.jsonToMap(result);

			}
		}
		return ret;

	}

	private String parseKeyAndValue(Map<String, String> params) {
		TreeMap<String, String> map = new TreeMap<>();
		for (String key : params.keySet()) {
			map.put(key, params.get(key));
		}
		StringBuilder sb = new StringBuilder();
		sb.append("a=1");
		for (Entry<String, String> info : map.entrySet()) {
			sb.append("&" + info.getKey() + "=" + info.getValue());
		}
		return sb.toString();
	}

	@Override
	// 获取地理编码
	public GeoResponseDTO getGeo(GeoReqDTO geoReq) {
		GeoResponseDTO ret = null;
		do {
			Map<String, String> reqst = new HashMap<String, String>();
			new IfNullTemplate(reqst).put("address", geoReq.getAddress())
					.put("city", geoReq.getCity())
					.put("batch", geoReq.getBatch());
			ret = post1(apiConfig.geoUrl, reqst, GeoResponseDTO.class);

		} while (false);
		return ret;
	}

	// 获取驾驶路径规划 开始坐标 结束坐标 规划类型
	/*
	 * 0，不考虑当时路况，返回耗时最短的路线，但是此路线不一定距离最短 1，不走收费路段，且耗时最少的路线
	 * 2，不考虑路况，仅走距离最短的路线，但是可能存在穿越小路/小区的情况 3，不走快速路，例如京通快速路（因为策略迭代，建议使用13）
	 * 4，躲避拥堵的路线，但是可能会存在绕路的情况，耗时可能较长 5，多策略（同时使用速度优先、费用优先、距离优先三个策略计算路径）。
	 * 其中必须说明，就算使用三个策略算路，会根据路况不固定的返回一~三条路径规划信息。 6，不走高速，但是不排除走其余收费路段
	 * 7，不走高速且避免所有收费路段 8，躲避收费和拥堵，可能存在走高速的情况，并且考虑路况不走拥堵路线，但有可能存在绕路和时间较长
	 * 9，不走高速且躲避收费和拥堵
	 */
	@Override
	public Map<String, Object> getDrivingPath(Coordinate start, Coordinate end,
			int strage) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String key = start.getLatitude() + "," + start.getLongitude() + ","
				+ end.getLatitude() + "," + end.getLongitude() + ","
				+ strage;
		Jedis jedis = this.memStore.getClient();
		String path = jedis.hget("drivingPath", key);
		if (StringUtil.isNotEmpty(path)) {
			ret = JsonPluginsUtil.jsonToMap(path);
			return ret;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("origin", start.getLongitude() + "," + start.getLatitude());
		params.put("destination", end.getLongitude() + "," + end.getLatitude());
		params.put("strategy", strage + "");
		params.put("extensions", "base");
		ret = this.post2(this.apiConfig.drivingPathUrl, params); // 次数据有800K左右
																	// 进行优化保存
		if (ret != null) {
			ret = this.marginDrivingPath(ret);
			if (ret == null) {
				throw new BusinessException("路径优化出错");
			}
			jedis.hset("drivingPath", key, JsonPluginsUtil.beanToJson(ret));
		}
		return ret;
	}

	/*
	 * 优化保存 只要路径点 和一些基础数据 其他的导航点如（多少米右转这些不需要） 因为这个数据太大
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private Map<String, Object> marginDrivingPath(Map<String, Object> path) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> route = (Map<String, Object>) path.get("route");
		ret.put("origin", route.get("origin"));
		ret.put("destination", route.get("destination"));
		List<Object> array = (List<Object>) route.get("paths");
		Map<String, Object> pathInfo = (Map<String, Object>) array.get(0);
		ret.put("distance", pathInfo.get("distance"));
		ret.put("duration", pathInfo.get("duration"));
		ret.put("strategy", pathInfo.get("strategy"));
		ret.put("tolls", pathInfo.get("tolls"));
		ret.put("toll_distance", pathInfo.get("toll_distance"));
		List<Object> steps = (List<Object>) pathInfo.get("steps");
		StringBuilder builder = new StringBuilder(1024 * 20); //20K 的增长缓存
		int endIndex = steps.size() - 1;
		for (int i = 0; i < steps.size(); i++) {
			Map<String, Object> stepInfo = (Map<String, Object>) steps.get(i);
			if (i != endIndex) {
				builder.append(stepInfo.get("polyline") + ";");
			} else {
				builder.append(stepInfo.get("polyline"));
			}
		}
		ret.put("polyline", builder.toString());
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.memory.platform.module.trace.service.impl.IGaoDeWebService#getReGeo(
	 * com.memory.platform.module.trace.dto.ReGeoReqDTO)
	 */
	@Override
	public ReGeoResponseDTO getReGeo(ReGeoReqDTO addr) {
		ReGeoResponseDTO ret = null;
		do {
			// 最多20次做分解请求
			int reqCount = addr.getLocation().size() / 20
					+ (addr.getLocation().size() % 20 > 0 ? 1 : 0);
			for (int i = 0; i < reqCount; i++) {
				ReGeoReqDTO reqst = new ReGeoReqDTO();
				int addrCount = addr.getLocation().size() - i * 20 > 20 ? 20
						: addr.getLocation().size() - i * 20;
				for (int j = 0; j < addrCount; j++) {
					String addrInfo = addr.getLocation().get(i * 20 + j);
					reqst.getLocation().add(addrInfo);
				}
				Map<String, String> req = new HashMap<>();
				req.put("location", reqst.toQueryString());
				req.put("batch", addrCount > 1 ? "true" : "false");
				ReGeoResponseDTO resps = post1(apiConfig.reGeoUrl, req,
						ReGeoResponseDTO.class);

				if (resps == null) {
					throw new BusinessException("服务器请求出错");
				} else {
					if ("OK".equals(resps.getInfo())) {
						if (ret == null) {
							ret = resps;
						} else {
							ret.getRegeocodes().addAll(resps.getRegeocodes());
						}
					} else {
						throw new BusinessException("服务器请求出错:"
								+ resps.getInfocode() + " status:"
								+ resps.getStatus());
					}
				}
			}
		} while (false);
		return ret;
	}

	protected static class IfNullTemplate {
		Map<String, String> map;

		public IfNullTemplate(Map<String, String> map) {
			this.map = map;
		}

		public IfNullTemplate put(String key, String v) {
			if (StringUtil.isEmpty(v) == false)
				this.map.put(key, v);
			return this;
		}
	}

	public static void main(String[] args) {
		// new GaoDeWebService().geoTest();
		// String str=
		// "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"regeocodes\":[{\"formatted_address\":\"重庆市渝北区龙塔街道兴盛大道33附53-54号上品.拾陆\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"渝北区\",\"adcode\":\"500112\",\"township\":\"龙塔街道\",\"towncode\":\"500112013000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":\"兴盛大道\",\"number\":\"33附53-54号\",\"location\":\"106.546421,29.5892569\",\"direction\":\"南\",\"distance\":\"21.4222\"},\"businessAreas\":[{\"location\":\"106.53832759259258,29.590372836419753\",\"name\":\"黄泥塝\",\"id\":\"500112\"},{\"location\":\"106.55894909259257,29.580763614814824\",\"name\":\"五里店\",\"id\":\"500105\"},{\"location\":\"106.53611417427386,29.581412792531104\",\"name\":\"兴隆\",\"id\":\"500105\"}]}},{\"formatted_address\":\"重庆市渝北区两路街道机场南联络道\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"渝北区\",\"adcode\":\"500112\",\"township\":\"两路街道\",\"towncode\":\"500112016000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市渝北区古路镇大水村\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"渝北区\",\"adcode\":\"500112\",\"township\":\"古路镇\",\"towncode\":\"500112141000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市渝北区统景镇恒化村\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"渝北区\",\"adcode\":\"500112\",\"township\":\"统景镇\",\"towncode\":\"500112133000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市长寿区洪湖镇瓦屋村\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"长寿区\",\"adcode\":\"500115\",\"township\":\"洪湖镇\",\"towncode\":\"500115136000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"四川省广安市邻水县黎家乡兰家沟\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"四川省\",\"city\":\"广安市\",\"citycode\":\"0826\",\"district\":\"邻水县\",\"adcode\":\"511623\",\"township\":\"黎家乡\",\"towncode\":\"511623213000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"四川省广安市邻水县复盛乡干河沟\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"四川省\",\"city\":\"广安市\",\"citycode\":\"0826\",\"district\":\"邻水县\",\"adcode\":\"511623\",\"township\":\"复盛乡\",\"towncode\":\"511623219000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"四川省广安市邻水县兴仁镇龚家梁子\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"四川省\",\"city\":\"广安市\",\"citycode\":\"0826\",\"district\":\"邻水县\",\"adcode\":\"511623\",\"township\":\"兴仁镇\",\"towncode\":\"511623116000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市垫江县新民镇572县道\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"垫江县\",\"adcode\":\"500231\",\"township\":\"新民镇\",\"towncode\":\"500231101000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市垫江县沙坪镇蓝家湾\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"垫江县\",\"adcode\":\"500231\",\"township\":\"沙坪镇\",\"towncode\":\"500231102000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市梁平区屏锦镇大水巷50号\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"梁平区\",\"adcode\":\"500228\",\"township\":\"屏锦镇\",\"towncode\":\"500228104000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":\"大水巷\",\"number\":\"50号\",\"location\":\"107.546909,30.5893731\",\"direction\":\"东\",\"distance\":\"45.6697\"},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市梁平区礼让镇102省道\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"梁平区\",\"adcode\":\"500228\",\"township\":\"礼让镇\",\"towncode\":\"500228102000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市梁平区文化镇滴水岩\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"梁平区\",\"adcode\":\"500228\",\"township\":\"文化镇\",\"towncode\":\"500228119000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"四川省达州市开江县广福镇大岩洞\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"四川省\",\"city\":\"达州市\",\"citycode\":\"0818\",\"district\":\"开江县\",\"adcode\":\"511723\",\"township\":\"广福镇\",\"towncode\":\"511723108000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"四川省达州市开江县讲治镇猫儿梁\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"四川省\",\"city\":\"达州市\",\"citycode\":\"0818\",\"district\":\"开江县\",\"adcode\":\"511723\",\"township\":\"讲治镇\",\"towncode\":\"511723105000\",\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市开州区大坪梁\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"开州区\",\"adcode\":\"500154\",\"township\":[],\"towncode\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市开州区金桥村\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"开州区\",\"adcode\":\"500154\",\"township\":[],\"towncode\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市开州区508县道\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"开州区\",\"adcode\":\"500154\",\"township\":[],\"towncode\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市开州区035县道\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"开州区\",\"adcode\":\"500154\",\"township\":[],\"towncode\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}},{\"formatted_address\":\"重庆市开州区二青庙\",\"addressComponent\":{\"country\":\"中国\",\"province\":\"重庆市\",\"city\":[],\"citycode\":\"023\",\"district\":\"开州区\",\"adcode\":\"500154\",\"township\":[],\"towncode\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"streetNumber\":{\"street\":[],\"number\":[],\"direction\":[],\"distance\":[]},\"businessAreas\":[[]]}}]}";
		// str= str.replaceAll(":\\[\\]",":null").replaceAll(":\\[\\[\\]\\]",
		// ":null");
		// Gson gs = new GsonBuilder().serializeNulls().create();
		// ReGeoResponseDTO resp = gs.fromJson(str, ReGeoResponseDTO.class);
		// System.out.println(str);
		new GaoDeWebService().regeoTest();
	}

	private void regeoTest() {
		ReGeoReqDTO req = new ReGeoReqDTO();

		for (int i = 0; i < 30; i++) {
			req.getLocation().add(
					(106.650282f + i * 0.1f) + "," + (26.613364f + i * 0.1f));
		}
		this.getReGeo(req);

	}
	
	
	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;  
	  
    /** 
     * 对double类型数据保留小数点后多少位 
     *  高德地图转码返回的就是 小数点后6位，为了统一封装一下 
     * @param digit 位数 
     * @param in 输入 
     * @return 保留小数位后的数 
     */  
     static double dataDigit(int digit,double in){  
        return new   BigDecimal(in).setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();  
  
    }  
  
    /** 
     * 将火星坐标转变成百度坐标 
     * @param lngLat_gd 火星坐标（高德、腾讯地图坐标等） 
     * @return 百度坐标 
     */  
      
 public static Coordinate bd_encrypt(Coordinate lngLat_gd)  
    {  
        double x = lngLat_gd.getLongitude(), y = lngLat_gd.getLatitude();  
        double z =Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
        double theta =Math. atan2(y, x) + 0.000003 * Math.cos(x *  x_pi);  
        return new Coordinate(dataDigit(6,z * Math.cos(theta) + 0.0065),dataDigit(6,z * Math.sin(theta) + 0.006));  
  
    }  
    /** 
     * 将百度坐标转变成火星坐标 
     * @param lngLat_bd 百度坐标（百度地图坐标） 
     * @return 火星坐标(高德、腾讯地图等) 
     */  
    static Coordinate bd_decrypt(Coordinate lngLat_bd)  
    {  
        double x = lngLat_bd.getLongitude() - 0.0065, y = lngLat_bd.getLatitude() - 0.006;  
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
        return new Coordinate( dataDigit(6,z * Math.cos(theta)),dataDigit(6,z * Math.sin(theta)));  
  
    }  

	public void geoTest() {
		GeoReqDTO req = new GeoReqDTO();
		req.setAddress("贵州省贵阳市观山湖区金岭社区铜仁路多丽贵州电子商务产业园");
		GeoResponseDTO resp = this.getGeo(req);
		for (GeoResponseDTO.GeoCode codeInfo : resp.getGeocodes()) {
			log.info(codeInfo.toString());
		}
	}

}
