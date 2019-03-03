package com.memory.platform.module.trace.dto;

public class YYQueryVehicleReq {
	//-1:已过期，0或 NULL：全部，7：一个星期过期，60：两个月过期
	int ot = 0;
	//查询条件(可省略)
	String carName;
	//设备ID
	String gprs;
	////车辆类型 vehicleTypeId
	String vehicleTypeId;
	//sim卡号
	String sim;
	public String getCarName() {
		return carName;
	}
	public String getGprs() {
		return gprs;
	}
	public int getOt() {
		return ot;
	}
	public String getSim() {
		return sim;
	}
	public String getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public void setGprs(String gprs) {
		this.gprs = gprs;
	}
	public void setOt(int ot) {
		this.ot = ot;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public void setVehicleTypeId(String vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
}
