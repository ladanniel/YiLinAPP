package com.memory.platform.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;
import com.memory.platform.entity.order.RobOrderRecord.Status;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月6日 上午11:54:48 
* 修 改 人： 
* 日   期： 
* 描   述： 抢单操作记录表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "order_aut_log")
public class OrderAutLog extends BaseEntity {

	private static final long serialVersionUID = 877759974075994503L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="rob_order_record_id", nullable = false)
	private RobOrderRecord robOrderRecord;  
	private Status beforeStatus;
	private Status afterStatus;
	private String remark;  //审核信息说明
	@Column(name = "audit_person")
	private String auditPerson;  //审核人
	@Column(name = "audit_person_id")
	private String auditPersonId;   //审核人ID
	private String title;
	
	@Column(name = "confirm_status")
	private RobOrderConfirm.Status confirmStatus; //订单流程状态
	
	@Column(name = "receipt_user_id")
	private String receiptUserID; //回执人员ID
	@Column(name = "receipt_img")
	private String receiptImg;//回执图片
	
	@Column(name = "turck_id")
	private String turckId;//车辆ID
	
	@Column(name = "turck_user_id")
	private String turckUserId;//司机ID
	
	@Column(name = "rob_orderconfirm_id") //确认订单ID
	private String robOrderConfirmId;
	
	@Column(name = "confirm_receipt_user_id")
	private String confirmreceiptUserId; //确认收货人ID
	
	
	@Column(name = "special_status")
	private RobOrderConfirm.SpecialStatus specialStatus; //处理状态
	
	@Column(name = "special_type")
	private RobOrderConfirm.SpecialType specialType; //事件类型
	
	
	@Column(name = "rocessing_result")
	private RocessingResult rocessingResult; //仲裁结果 
	
	
	public RobOrderConfirm.SpecialStatus getSpecialStatus() {
		return specialStatus;
	}
	public void setSpecialStatus(RobOrderConfirm.SpecialStatus specialStatus) {
		this.specialStatus = specialStatus;
	}
	public RobOrderConfirm.SpecialType getSpecialType() {
		return specialType;
	}
	public void setSpecialType(RobOrderConfirm.SpecialType specialType) {
		this.specialType = specialType;
	}
	public RocessingResult getRocessingResult() {
		return rocessingResult;
	}
	public void setRocessingResult(RocessingResult rocessingResult) {
		this.rocessingResult = rocessingResult;
	}
	@Transient
	private Date start;
	@Transient
	private Date end;
	
	
	
	public RobOrderConfirm.Status getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(RobOrderConfirm.Status confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public RobOrderRecord getRobOrderRecord() {
		return robOrderRecord;
	}
	public void setRobOrderRecord(RobOrderRecord robOrderRecord) {
		this.robOrderRecord = robOrderRecord;
	}
	public Status getBeforeStatus() {
		return beforeStatus;
	}
	public void setBeforeStatus(Status beforeStatus) {
		this.beforeStatus = beforeStatus;
	}
	public Status getAfterStatus() {
		return afterStatus;
	}
	public void setAfterStatus(Status afterStatus) {
		this.afterStatus = afterStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuditPerson() {
		return auditPerson;
	}
	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	public String getAuditPersonId() {
		return auditPersonId;
	}
	public void setAuditPersonId(String auditPersonId) {
		this.auditPersonId = auditPersonId;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the receiptUserID
	 */
	public String getReceiptUserID() {
		return receiptUserID;
	}
	/**
	 * @param receiptUserID the receiptUserID to set
	 */
	public void setReceiptUserID(String receiptUserID) {
		this.receiptUserID = receiptUserID;
	}
	/**
	 * @return the receiptImg
	 */
	public String getReceiptImg() {
		return receiptImg;
	}
	/**
	 * @param receiptImg the receiptImg to set
	 */
	public void setReceiptImg(String receiptImg) {
		this.receiptImg = receiptImg;
	}
	/**
	 * @return the turckId
	 */
	public String getTurckId() {
		return turckId;
	}
	/**
	 * @param turckId the turckId to set
	 */
	public void setTurckId(String turckId) {
		this.turckId = turckId;
	}
	/**
	 * @return the turckUserId
	 */
	public String getTurckUserId() {
		return turckUserId;
	}
	/**
	 * @param turckUserId the turckUserId to set
	 */
	public void setTurckUserId(String turckUserId) {
		this.turckUserId = turckUserId;
	}
	/**
	 * @return the robOrderConfirmId
	 */
	public String getRobOrderConfirmId() {
		return robOrderConfirmId;
	}
	/**
	 * @param robOrderConfirmId the robOrderConfirmId to set
	 */
	public void setRobOrderConfirmId(String robOrderConfirmId) {
		this.robOrderConfirmId = robOrderConfirmId;
	}
	/**
	 * @return the confirmreceiptUserId
	 */
	public String getConfirmreceiptUserId() {
		return confirmreceiptUserId;
	}
	/**
	 * @param confirmreceiptUserId the confirmreceiptUserId to set
	 */
	public void setConfirmreceiptUserId(String confirmreceiptUserId) {
		this.confirmreceiptUserId = confirmreceiptUserId;
	}
	
}
