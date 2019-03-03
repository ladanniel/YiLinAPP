package com.memory.platform.module.trace.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.truck.EngineBrand;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.entity.truck.TruckBrand;
import com.memory.platform.entity.truck.TruckPlate;
import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.global.TruckStatus;

public class TraceCar {
  
	float latitude;
	float longitude;
 
	private String track_no;//车牌号

	private String truckType;//车辆类型

	private String truckPlate;//车牌类型
	 
	private Double track_long;//车辆长度
	 
	private Double capacity;//载重
	 
	private String truckStatus ;  //默认设置车辆状态为：0（未运输）
	 
	private String truckBrand;//车辆品牌

	private String track_read_no;//车辆识别码

	private String engineBrand;//发动机品牌

	private String engine_no;//发动机编号

	private String horsepower;//马力

	private Date tag_time;//上牌时间
 
	private String description;//备注
	private String user_name;//驾驶人员
	//新加的 sim号
	private String sim;
	//gps设备id
	private String gpsID;
	//亿源车辆的ID
	private String yyVeID;
	//亿源车辆keyID号
	private String yyKeyID;
	public Double getCapacity() {
		return capacity;
	}
	public String getDescription() {
		return description;
	}

	public String getEngine_no() {
		return engine_no;
	}
	 
	public String getEngineBrand() {
		return engineBrand;
	}
	 
	public String getGpsID() {
		return gpsID;
	}
	public String getHorsepower() {
		return horsepower;
	}
	public float getLatitude() {
		return latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	 
	public String getSim() {
		return sim;
	}
	public Date getTag_time() {
		return tag_time;
	}
	public Double getTrack_long() {
		return track_long;
	}

	public String getTrack_no() {
		return track_no;
	}

	public String getTrack_read_no() {
		return track_read_no;
	}
	 
	public String getTruckBrand() {
		return truckBrand;
	}
	 
	public String getTruckPlate() {
		return truckPlate;
	}
	 
	public String getTruckStatus() {
		return truckStatus;
	}

	public String getTruckType() {
		return truckType;
	}

	public String getUser_name() {
		return user_name;
	}

	 

	public String getYyKeyID() {
		return yyKeyID;
	}

	public String getYyVeID() {
		return yyVeID;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

 

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}

	public void setEngineBrand(String engineBrand) {
		this.engineBrand = engineBrand;
	}

	public void setGpsID(String gpsID) {
		this.gpsID = gpsID;
	}

	public void setHorsepower(String horsepower) {
		this.horsepower = horsepower;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public void setTag_time(Date tag_time) {
		this.tag_time = tag_time;
	}

	public void setTrack_long(Double track_long) {
		this.track_long = track_long;
	}

	public void setTrack_no(String track_no) {
		this.track_no = track_no;
	}

	 

	public void setTrack_read_no(String track_read_no) {
		this.track_read_no = track_read_no;
	}

	public void setTruckBrand(String truckBrand) {
		this.truckBrand = truckBrand;
	}

	public void setTruckPlate(String truckPlate) {
		this.truckPlate = truckPlate;
	}

	public void setTruckStatus(String truckStatus) {
		this.truckStatus = truckStatus;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setYyKeyID(String yyKeyID) {
		this.yyKeyID = yyKeyID;
	}

	public void setYyVeID(String yyVeID) {
		this.yyVeID = yyVeID;
	}
}
