package com.memory.platform.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月6日 上午11:54:48 
* 修 改 人： 
* 日   期： 
* 描   述： 运单急救仲裁日志
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "order_waybill_log")
public class OrderWaybillLog extends BaseEntity {

	private static final long serialVersionUID = 877759974075994503L;

	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="rob_orderconfirm_id", nullable = false)
	private RobOrderConfirm robOrderConfirm;
	
	
	@Column(name = "confirm_status")
	private RobOrderConfirm.Status confirmStatus; //订单流程状态
	
	@Column(name = "special_status")
	private RobOrderConfirm.SpecialStatus specialStatus; //处理状态
	
	@Column(name = "special_type")
	private RobOrderConfirm.SpecialType specialType; //事件类型
	
	
	@Column(name = "rocessing_result")
	private RocessingResult rocessingResult; //仲裁结果 
	
	@Column(name = "dealt_person_name")
	private String dealtPersonName;  //处理人姓名
	@Column(name = "dealt_person_id")
	private String dealtPersonId;   //处理人ID
	@Column(name = "remark")
	private String remark;  //处理说明
	
	@Column(name = "apply_person_id")
	private String applypersonid;  //申请人ID
	@Column(name = "apply_person_name")
	private String applypersonName;  //申请人姓名
	@Column(name = "apply_company_id")
	private String applyCompanyId;  //申请商户ID
	
	@Column(name = "order_special_apply_id")
	private String orderSpecialApplyId;//申请记录ID
	
	
	 @Column(name = "indemnity")
	 private Double indemnity; //赔偿金额
	
	
	
	
	
	
	
	
	public double getIndemnity() {
		return indemnity;
	}
	public void setIndemnity(double indemnity) {
		this.indemnity = indemnity;
	}
	public RocessingResult getRocessingResult() {
		return rocessingResult;
	}
	public void setRocessingResult(RocessingResult rocessingResult) {
		this.rocessingResult = rocessingResult;
	}
	public String getOrderSpecialApplyId() {
		return orderSpecialApplyId;
	}
	public void setOrderSpecialApplyId(String orderSpecialApplyId) {
		this.orderSpecialApplyId = orderSpecialApplyId;
	}
	public RobOrderConfirm.SpecialType getSpecialType() {
		return specialType;
	}
	public void setSpecialType(RobOrderConfirm.SpecialType specialType) {
		this.specialType = specialType;
	}
	public String getApplyCompanyId() {
		return applyCompanyId;
	}
	public void setApplyCompanyId(String applyCompanyId) {
		this.applyCompanyId = applyCompanyId;
	}
	public String getApplypersonid() {
		return applypersonid;
	}
	public void setApplypersonid(String applypersonid) {
		this.applypersonid = applypersonid;
	}
	public String getApplypersonName() {
		return applypersonName;
	}
	public void setApplypersonName(String applypersonName) {
		this.applypersonName = applypersonName;
	}
	
	
	public RobOrderConfirm getRobOrderConfirm() {
		return robOrderConfirm;
	}
	public void setRobOrderConfirm(RobOrderConfirm robOrderConfirm) {
		this.robOrderConfirm = robOrderConfirm;
	}
	public RobOrderConfirm.Status getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(RobOrderConfirm.Status confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public RobOrderConfirm.SpecialStatus getSpecialStatus() {
		return specialStatus;
	}
	public void setSpecialStatus(RobOrderConfirm.SpecialStatus specialStatus) {
		this.specialStatus = specialStatus;
	}
	public String getDealtPersonName() {
		return dealtPersonName;
	}
	public void setDealtPersonName(String dealtPersonName) {
		this.dealtPersonName = dealtPersonName;
	}
	public String getDealtPersonId() {
		return dealtPersonId;
	}
	public void setDealtPersonId(String dealtPersonId) {
		this.dealtPersonId = dealtPersonId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
	
	
	
	
}
