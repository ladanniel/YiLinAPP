package com.memory.platform.module.trace.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * 逆向地理编码请求
 * */
public class ReGeoReqDTO {
	List<String> location = new ArrayList<>();
	/*
	 * 规则： 最多支持20个坐标点。多个点之间用"|"分割。经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不得超过6位
	 * */
	public List<String> getLocation() {
		return location;
	}
	public String toQueryString(){
		StringBuilder sb = new StringBuilder();
		int idx =0;
		for(String info :location){
			
			sb.append(info);
			if(idx != location.size()-1){
				sb.append("|");
			}
		}
		 
		return sb.toString();
	}
}
