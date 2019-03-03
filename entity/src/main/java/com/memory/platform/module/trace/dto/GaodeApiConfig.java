package com.memory.platform.module.trace.dto;

public class GaodeApiConfig {
	//web服务地址key
	//public    String gKey = "2a662ebd58b3a35afdd2ae798ff89256";
	//baseURL
	public   String baseUrl = "http://restapi.amap.com/v3/";
	//地理编码URL
	public   String geoUrl = baseUrl  +"geocode/geo";
	//地理编码URL
	public   String reGeoUrl = baseUrl  +"geocode/regeo";
	//获取驾车路径URL
	public   String drivingPathUrl = baseUrl +"direction/driving";
	String key = "2a662ebd58b3a35afdd2ae798ff89256" ;
	public void setKey(String key) {
		this.key = key;
	}
	 
	public String getKey() {
		return key;
	}

}
