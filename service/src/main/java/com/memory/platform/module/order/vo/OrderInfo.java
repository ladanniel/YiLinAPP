package com.memory.platform.module.order.vo;

public class OrderInfo {
	
	private String goodName;//货物名称
	private String goodTypeName;//货物类型
	private double unitPrice;   //所报单价
	private double weight;   //重量
	private String deliveryName;   //发货人姓名
	private String deliveryMobile;  //发货人联系电话
	private double totalPrice; //总价
	
	
	private double  iunitPrice;//货物单价
	private double  itotalWeight;//运输货物总重量
	private double  itransportationCost;//运输费用
	
	
	
	
	public OrderInfo() {
		super();
	}
	
	
	
	
	
	public double getIunitPrice() {
		return iunitPrice;
	}





	public void setIunitPrice(double iunitPrice) {
		this.iunitPrice = iunitPrice;
	}





	public double getItotalWeight() {
		return itotalWeight;
	}





	public void setItotalWeight(double itotalWeight) {
		this.itotalWeight = itotalWeight;
	}





	public double getItransportationCost() {
		return itransportationCost;
	}





	public void setItransportationCost(double itransportationCost) {
		this.itransportationCost = itransportationCost;
	}





	public double getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}



	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getGoodTypeName() {
		return goodTypeName;
	}
	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryMobile() {
		return deliveryMobile;
	}
	public void setDeliveryMobile(String deliveryMobile) {
		this.deliveryMobile = deliveryMobile;
	}
	
	
	

}
