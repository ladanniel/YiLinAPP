package com.memory.platform.entity.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;

/**
 * 创 建 人： 日 期： 2016年6月6日 上午11:54:48 修 改 人： 日 期： 描 述： 运单特殊情况申请记录 版 本 号： V1.0
 */
@Entity
@Table(name = "order_special_apply")
public class OrderSpecialApply extends BaseEntity {

	private static final long serialVersionUID = 877759974075994503L;

	@Column(name = "rob_orderconfirm_id")
	private String robOrderConfirmId;

	@Column(name = "confirm_status")
	private RobOrderConfirm.Status confirmStatus; // 订单流程状态

	@Column(name = "special_status")
	private RobOrderConfirm.SpecialStatus specialStatus; // 处理状态

	@Column(name = "special_type")
	private RobOrderConfirm.SpecialType specialType; // 事件类型

	@Column(name = "rocessing_result")
	private RocessingResult rocessingResult; // 仲裁结果

	@Column(name = "dealt_person_id")
	private String dealtPersonId; // 处理人ID

	@Column(name = "apply_person_id")
	private String applypersonid; // 申请人ID

	@Column(name = "apply_person_name")
	private String applypersonName; // 申请人姓名

	@Column(name = "be_arbitration_person_id")
	private String beArbitrationPersonId;// 被仲裁用户ID

	@Column(name = "indemnity")
	private Double indemnity; // 赔偿金额

	@Column(name = "apply_company_id")
	private String applyCompanyId;// 申请商户ID

	@Column(name = "beArbitration_Company_Id")
	private String beArbitrationCompanyId;// 被仲裁用户商户ID
	@Column(name = "emergency_img")
	private String emergencyImg;// 急救图片

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmergencyImg() {
		return emergencyImg;
	}

	public void setEmergencyImg(String emergencyImg) {
		this.emergencyImg = emergencyImg;
	}

	public void setIndemnity(Double indemnity) {
		this.indemnity = indemnity;
	}

	public String getApplyCompanyId() {
		return applyCompanyId;
	}

	public void setApplyCompanyId(String applyCompanyId) {
		this.applyCompanyId = applyCompanyId;
	}

	public String getBeArbitrationCompanyId() {
		return beArbitrationCompanyId;
	}

	public void setBeArbitrationCompanyId(String beArbitrationCompanyId) {
		this.beArbitrationCompanyId = beArbitrationCompanyId;
	}

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

	@Column(name = "remark")
	private String remark; // 处理说明

	public String getBeArbitrationPersonId() {
		return beArbitrationPersonId;
	}

	public void setBeArbitrationPersonId(String beArbitrationPersonId) {
		this.beArbitrationPersonId = beArbitrationPersonId;
	}

	public String getApplypersonName() {
		return applypersonName;
	}

	public void setApplypersonName(String applypersonName) {
		this.applypersonName = applypersonName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRobOrderConfirmId() {
		return robOrderConfirmId;
	}

	public void setRobOrderConfirmId(String robOrderConfirmId) {
		this.robOrderConfirmId = robOrderConfirmId;
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

	public RobOrderConfirm.SpecialType getSpecialType() {
		return specialType;
	}

	public void setSpecialType(RobOrderConfirm.SpecialType specialType) {
		this.specialType = specialType;
	}

	public String getDealtPersonId() {
		return dealtPersonId;
	}

	public void setDealtPersonId(String dealtPersonId) {
		this.dealtPersonId = dealtPersonId;
	}

	public String getApplypersonid() {
		return applypersonid;
	}

	public void setApplypersonid(String applypersonid) {
		this.applypersonid = applypersonid;
	}

	public List<String> getEmergencyImgList() {
		List<String> list = new ArrayList<String>();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(this
				.getEmergencyImg())) {
			String[] imgArry = this.getEmergencyImg().split(",");
			for (String str : imgArry) {
				if (org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
					list.add(str);
				}
			}
		}
		return list;
	}

}
