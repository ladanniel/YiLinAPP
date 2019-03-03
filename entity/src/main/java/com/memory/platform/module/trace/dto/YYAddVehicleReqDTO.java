package com.memory.platform.module.trace.dto;

public class YYAddVehicleReqDTO {
	
	//车牌号
	String carName;
	//所属客户Id
	String clientID;
	//设备Id
	String gprs;
	//SIM号码
	 String sim;
	 //车机类型
	 String mobileId="216";
	 //车辆类型
	 String  vehicleTypeId="3" ;
	 //设备过期时间（格式：yyyy-MM-dd hh:mm:ss） 一个月、半年、一年
	 String overduetime="2057/06/07 12:00:00";
	 //备注
	 String remark;
	int cpys=1;	//	车牌颜色 【0：蓝色  1：黄色  2：黑色 3：白色】
	String sccj;	//	车机生产厂家
	int goupListPage=0;	//	所属分组(必选)
	String exkey; //		登录成功后返回的exkey(可省略)
	int perListPage=0;	//	已选中外接设备一的id（可省略表示不修改，为零时清除）
	int perListPage2=0;//		已选中外接设备二的id（可省略表示不修改，为零时清除）
	int policeListPage=0;//		已选中报警类型一的id（可省略表示不修改，为零时清除）
	int policeListPage2=0;//		已选中报警类型二的id（可省略表示不修改，为零时清除）
	public String getCarName() {
		return carName;
	}
	public String getClientID() {
		return clientID;
	}
	public int getCpys() {
		return cpys;
	}
	public String getExkey() {
		return exkey;
	}
	public int getGoupListPage() {
		return goupListPage;
	}
	public String getGprs() {
		return gprs;
	}
	public String getMobileId() {
		return mobileId;
	}
	public String getOverduetime() {
		return overduetime;
	}
	public int getPerListPage() {
		return perListPage;
	}
	public int getPerListPage2() {
		return perListPage2;
	}
	public int getPoliceListPage() {
		return policeListPage;
	}
	public int getPoliceListPage2() {
		return policeListPage2;
	}
	public String getRemark() {
		return remark;
	}
	public String getSccj() {
		return sccj;
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
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public void setCpys(int cpys) {
		this.cpys = cpys;
	}
	public void setExkey(String exkey) {
		this.exkey = exkey;
	}
	public void setGoupListPage(int goupListPage) {
		this.goupListPage = goupListPage;
	}
	public void setGprs(String gprs) {
		this.gprs = gprs;
	}
	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
	public void setOverduetime(String overduetime) {
		this.overduetime = overduetime;
	}
	 
	 
	 public void setPerListPage(int perListPage) {
		this.perListPage = perListPage;
	}
	 public void setPerListPage2(int perListPage2) {
		this.perListPage2 = perListPage2;
	}
	 public void setPoliceListPage(int policeListPage) {
		this.policeListPage = policeListPage;
	}
	 public void setPoliceListPage2(int policeListPage2) {
		this.policeListPage2 = policeListPage2;
	}
	 public void setRemark(String remark) {
		this.remark = remark;
	}
	 public void setSccj(String sccj) {
		this.sccj = sccj;
	}
	 public void setSim(String sim) {
		this.sim = sim;
	}
	 public void setVehicleTypeId(String vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}

}
