package com.memory.platform.module.trace.dto;

import java.util.ArrayList;

import antlr.collections.List;

public class YYLocationResponseDTO extends BaseYYResponseDTO {
	public static class Location{
		//车辆ID
		int id;	  //	车辆ID
		String name;	//	车牌号码
		long recvtime;	//	服务器时间(毫秒数)
		long gpstime;	 //	GPS时间(毫秒数)
		double lat;		//纬度
		double lng;		//经度 
		double lat_xz;	//	纬度修正值
		double lng_xz	;//	经度修正值
		String state;//		车辆状态
		Double speed;	//	速度
		int direct;	//	方向
		Double temp;	//	温度
		Double oil;		//油量
		int oilMN1	;//	模拟量1
		int oilMN2	;	//模拟量2
		double distance;	//	行驶里程
		double totalDis	;//	总里程
		String av	 ;//	有效性
		String info	;	//文字位置信息
		int vhcofflinemin;//		不在线时长（分钟）
		double	stopDefDis;//		设防距离
		double stopDefLat	;//	设防纬度
		double stopDefLng	;//	设防经度
		String temp1;//		温度1
		String temp2	;//	温度2
		String temp3	;//	温度3
		String temp4	;//	温度4
		public String getAv() {
			return av;
		}
		public int getDirect() {
			return direct;
		}
		public double getDistance() {
			return distance;
		}
		public long getGpstime() {
			return gpstime;
		}
		public int getId() {
			return id;
		}
		public String getInfo() {
			return info;
		}
		public double getLat() {
			return lat;
		}
		public double getLat_xz() {
			return lat_xz;
		}
		public double getLng() {
			return lng;
		}
		public double getLng_xz() {
			return lng_xz;
		}
		public String getName() {
			return name;
		}
		public Double getOil() {
			return oil;
		}
		public int getOilMN1() {
			return oilMN1;
		}
		public int getOilMN2() {
			return oilMN2;
		}
		public long getRecvtime() {
			return recvtime;
		}
		public Double getSpeed() {
			return speed;
		}
		public String getState() {
			return state;
		}
		public double getStopDefDis() {
			return stopDefDis;
		}
		public double getStopDefLat() {
			return stopDefLat;
		}
		public double getStopDefLng() {
			return stopDefLng;
		}
		public Double getTemp() {
			return temp;
		}
		public String getTemp1() {
			return temp1;
		}
		public String getTemp2() {
			return temp2;
		}
		public String getTemp3() {
			return temp3;
		}
		public String getTemp4() {
			return temp4;
		}
		public double getTotalDis() {
			return totalDis;
		}
		public int getVhcofflinemin() {
			return vhcofflinemin;
		}
		public void setAv(String av) {
			this.av = av;
		}
		public void setDirect(int direct) {
			this.direct = direct;
		}
		public void setDistance(double distance) {
			this.distance = distance;
		}
		public void setGpstime(long gpstime) {
			this.gpstime = gpstime;
		}
		public void setId(int id) {
			this.id = id;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public void setLat_xz(double lat_xz) {
			this.lat_xz = lat_xz;
		}
		public void setLng(double lng) {
			this.lng = lng;
		}
		public void setLng_xz(double lng_xz) {
			this.lng_xz = lng_xz;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setOil(Double oil) {
			this.oil = oil;
		}
		public void setOilMN1(int oilMN1) {
			this.oilMN1 = oilMN1;
		}
		public void setOilMN2(int oilMN2) {
			this.oilMN2 = oilMN2;
		}
		public void setRecvtime(long recvtime) {
			this.recvtime = recvtime;
		}
		public void setSpeed(Double speed) {
			this.speed = speed;
		}
		public void setState(String state) {
			this.state = state;
		}
		public void setStopDefDis(double stopDefDis) {
			this.stopDefDis = stopDefDis;
		}
		public void setStopDefLat(double stopDefLat) {
			this.stopDefLat = stopDefLat;
		}
		public void setStopDefLng(double stopDefLng) {
			this.stopDefLng = stopDefLng;
		}
		public void setTemp(Double temp) {
			this.temp = temp;
		}
		public void setTemp1(String temp1) {
			this.temp1 = temp1;
		}
		public void setTemp2(String temp2) {
			this.temp2 = temp2;
		}
		public void setTemp3(String temp3) {
			this.temp3 = temp3;
		}
		public void setTemp4(String temp4) {
			this.temp4 = temp4;
		}
		public void setTotalDis(double totalDis) {
			this.totalDis = totalDis;
		}
		public void setVhcofflinemin(int vhcofflinemin) {
			this.vhcofflinemin = vhcofflinemin;
		}

	}
	ArrayList<Location> locs = new ArrayList<YYLocationResponseDTO.Location>();
	public ArrayList<Location> getLocs() {
		return locs;
	}
	public void setLocs(ArrayList<Location> locs) {
		this.locs = locs;
	}
	

}
