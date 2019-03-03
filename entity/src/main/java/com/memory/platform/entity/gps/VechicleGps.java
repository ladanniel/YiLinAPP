package com.memory.platform.entity.gps;

import java.io.Serializable;

public class VechicleGps implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7152485927290126766L;
	// 设备编号
	String deviceId;
	// 手机卡号
	String simId;
	// 整形车辆编号
	String carId;
	// 文本式车辆编号
	String carIdString;
	String driverName;
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	String cardNo;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	int fuelTankCapacity;
	int speed;
	float degree;
	String direction;
	float lng;
	float lat;
	float gdLat;
	float gdLng;
	String position;
	float bdLng;
	float bdLat;
	float dayMileage;
	float mouthMileage;
	float sumOill;
	float oill;
	float oill2;
	String state;
	String statusAcc;
	float oillPercent;
	float oillPercent2;
	public float getBdLat() {
		return bdLat;
	}
	public float getBdLng() {
		return bdLng;
	}
	public String getCarId() {
		return carId;
	}
	public String getCarIdString() {
		return carIdString;
	}
	public float getDayMileage() {
		return dayMileage;
	}
	public float getDegree() {
		return degree;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public String getDirection() {
		return direction;
	}
	public int getFuelTankCapacity() {
		return fuelTankCapacity;
	}
	public float getGdLat() {
		return gdLat;
	}
	public float getGdLng() {
		return gdLng;
	}
	public float getLat() {
		return lat;
	}
	public float getLng() {
		return lng;
	}
	public float getMouthMileage() {
		return mouthMileage;
	}
	public float getOill() {
		return oill;
	}
	public float getOill2() {
		return oill2;
	}
	public float getOillPercent() {
		return oillPercent;
	}
	public float getOillPercent2() {
		return oillPercent2;
	}
	public String getPosition() {
		return position;
	}
	public String getSimId() {
		return simId;
	}
	public int getSpeed() {
		return speed;
	}
	public String getState() {
		return state;
	}
	public String getStatusAcc() {
		return statusAcc;
	}
	public float getSumOill() {
		return sumOill;
	}
	public void setBdLat(float bdLat) {
		this.bdLat = bdLat;
	}
	public void setBdLng(float bdLng) {
		this.bdLng = bdLng;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public void setCarIdString(String carIdString) {
		this.carIdString = carIdString;
	}
	public void setDayMileage(float dayMileage) {
		this.dayMileage = dayMileage;
	}
	public void setDegree(float degree) {
		this.degree = degree;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void setFuelTankCapacity(int fuelTankCapacity) {
		this.fuelTankCapacity = fuelTankCapacity;
	}
	public void setGdLat(float gdLat) {
		this.gdLat = gdLat;
	}
	public void setGdLng(float gdLng) {
		this.gdLng = gdLng;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public void setMouthMileage(float mouthMileage) {
		this.mouthMileage = mouthMileage;
	}
	public void setOill(float oill) {
		this.oill = oill;
	}
	public void setOill2(float oill2) {
		this.oill2 = oill2;
	}
	public void setOillPercent(float oillPercent) {
		this.oillPercent = oillPercent;
	}
	public void setOillPercent2(float oillPercent2) {
		this.oillPercent2 = oillPercent2;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setStatusAcc(String statusAcc) {
		this.statusAcc = statusAcc;
	}
	public void setSumOill(float sumOill) {
		this.sumOill = sumOill;
	}

}
