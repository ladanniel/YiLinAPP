package com.memory.platform.module.order.vo;

import java.util.Date;
import java.util.List;

public class OrderTruck {
	
	private String robConfirmId;//确认订单ID
	private String robOrderId;//抢单ID
	private String robOrderNo;////抢单单号
	private Date confirmData;//确认时间
	private Integer status;//状态
	private String turckID;//车辆ID
	private String turckUserName;//司机姓名
	private String phone;//司机联系电话
	private String trackNo;//车牌号
	private String cardType;//车俩类型
	private double  unitPrice;//货物单价
	private double  totalWeight;//运输货物总重量
	private double  transportationCost;//运输费用
	
	private double  transportationDeposit;//运输押金总金额	
	private double  turckCost;//车辆押金总金额
	private double  turckDeposit;//车辆押金
	private String transportNo;//运输单号
	
	private List<OrderInfo> oderInfo; //拉货详细
	
	
	private Integer specialStatus;//状态
	private Integer specialtype;//事件类型
	
	
	
	
	
	public Integer getSpecialtype() {
		return specialtype;
	}






	public void setSpecialtype(Integer specialtype) {
		this.specialtype = specialtype;
	}






	public Integer getSpecialStatus() {
		return specialStatus;
	}






	public void setSpecialStatus(Integer specialStatus) {
		this.specialStatus = specialStatus;
	}



	public String getTransportNo() {
		return transportNo;
	}


	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}



	public OrderTruck() {
		super();
	}
	
	
	
	
	
	
	
	
	public double getTransportationDeposit() {
		return transportationDeposit;
	}








	public void setTransportationDeposit(double transportationDeposit) {
		this.transportationDeposit = transportationDeposit;
	}








	public double getTurckCost() {
		return turckCost;
	}








	public void setTurckCost(double turckCost) {
		this.turckCost = turckCost;
	}








	public double getTurckDeposit() {
		return turckDeposit;
	}








	public void setTurckDeposit(double turckDeposit) {
		this.turckDeposit = turckDeposit;
	}








	public List<OrderInfo> getOderInfo() {
		return oderInfo;
	}








	public void setOderInfo(List<OrderInfo> oderInfo) {
		this.oderInfo = oderInfo;
	}








	public double getUnitPrice() {
		return unitPrice;
	}






	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}






	public double getTotalWeight() {
		return totalWeight;
	}






	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}






	public double getTransportationCost() {
		return transportationCost;
	}






	public void setTransportationCost(double transportationCost) {
		this.transportationCost = transportationCost;
	}






	public Integer getStatus() {
		return status;
	}




	public void setStatus(Integer status) {
		this.status = status;
	}




	public String getRobOrderId() {
		return robOrderId;
	}
	public void setRobOrderId(String robOrderId) {
		this.robOrderId = robOrderId;
	}
	public String getRobOrderNo() {
		return robOrderNo;
	}
	public void setRobOrderNo(String robOrderNo) {
		this.robOrderNo = robOrderNo;
	}
	public Date getConfirmData() {
		return confirmData;
	}
	public void setConfirmData(Date confirmData) {
		this.confirmData = confirmData;
	}
	public String getTurckID() {
		return turckID;
	}
	public void setTurckID(String turckID) {
		this.turckID = turckID;
	}
	public String getTurckUserName() {
		return turckUserName;
	}
	public void setTurckUserName(String turckUserName) {
		this.turckUserName = turckUserName;
	}
	
	
	
	
	
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getTrackNo() {
		return trackNo;
	}
	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}








	/**
	 * @return the robConfirmId
	 */
	public String getRobConfirmId() {
		return robConfirmId;
	}








	/**
	 * @param robConfirmId the robConfirmId to set
	 */
	public void setRobConfirmId(String robConfirmId) {
		this.robConfirmId = robConfirmId;
	}
	
	
	
	
}
