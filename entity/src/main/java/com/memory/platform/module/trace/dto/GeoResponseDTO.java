package com.memory.platform.module.trace.dto;

import java.util.ArrayList;
import java.util.List;

public class GeoResponseDTO extends ResponseBaseDTO {
	public static class GeoCode{
		/*
		"formatted_address": "贵州省贵阳市观山湖区铜仁路",
        "province": "贵州省",
        "citycode": "0851",
        "city": "贵阳市",
        "district": "观山湖区",*/
		String formatted_address;
		String province;
		String citycode;
		String city;
		String district;
		String adcode;
		String location;
		String level;
		//纬度 横坐标
		float latitudi;
		//经度 纵坐标
		float longitudi;
		public String getAdcode() {
			return adcode;
		}
		public String getCity() {
			return city;
		}
		public String getCitycode() {
			return citycode;
		}
		public String getDistrict() {
			return district;
		}
		public String getFormatted_address() {
			return formatted_address;
		}
		public String getLevel() {
			return level;
		}
		public String getLocation() {
			return location;
		}
		public String getProvince() {
			return province;
		}
		public void setAdcode(String adcode) {
			this.adcode = adcode;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public void setCitycode(String citycode) {
			this.citycode = citycode;
		}
		public void setDistrict(String district) {
			this.district = district;
		}
		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public void setLocation(String location) {
			this.location = location;
			if(this.location!=null){
				String[] arr = this.location.split(",");
				this.latitudi = Float.parseFloat( arr[0]);
				this.longitudi = Float.parseFloat(arr[1]);
				
			}
		}
		public void setProvince(String province) {
			this.province = province;
		}
		@Override
		public String toString() {
			return "GeoCode [formatted_address=" + formatted_address + ", province=" + province + ", citycode="
					+ citycode + ", city=" + city + ", district=" + district + ", adcode=" + adcode + ", location="
					+ location + ", level=" + level + ", latitudi=" + latitudi + ", longitudi=" + longitudi + "]";
		}
		
	}
	List<GeoCode> geocodes = new ArrayList<>();
	public List<GeoCode> getGeocodes() {
		return geocodes;
	}
	public void setGeocodes(List<GeoCode> geocodes) {
		this.geocodes = geocodes;
	}
}
