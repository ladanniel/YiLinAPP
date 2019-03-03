package com.memory.platform.entity.sys;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.Auth;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月30日 上午10:28:33 
* 修 改 人： 
* 日   期： 
* 描   述： 商户详情表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_company")
@DynamicUpdate(true)
public class Company extends BaseEntity {

	private static final long serialVersionUID = -8822244996460585284L;


	private String name;
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "company_type_id")
	private CompanyType companyType;
	private String area_id;
	private Auth audit;
	@Column(name="area_full_name")
	private String areaFullName;
	//商户状态  0：关闭  1:启用 2:管理员 
	public enum Status{
		colse,open,admin
	}
	private Status status;
	private String address;
	@Column(name="contact_name")
	private String contactName;
	@Column(name="contact_tel")
	private String contactTel;
	@Column(updatable = false)
	private Integer source; //注册来源：  0：后台   1：前端
	private String idcard_id;
	private String business_license_id;
	private String driving_license_id;
	private String account_id;//账户ID
	private String failed_msg;//认证未通过原因
	private Date aut_time;//审核时间
	private String aut_user_id;//审核人
	private String aut_supplement_type="";//认证补录类型
	@Transient
	private String supplementType; //补录Type
	@Transient
	private String[] companyTypes; //商户类型
	@Transient
	private String[] areaIds; //区域IDs
	@Transient
	private Auth[] companyAuths; //认证状态
	@Transient
	private Integer[] source_serch; //来源
	@Transient
	private Status[] status_serch;//状态数组
 
 
	
		@Transient
	private Account account; //账户信息
	@Transient
	private String companyTypeId; //商户类型
	
	@Transient
	private Idcard idCard;//身份证
	
	@Transient
	private BusinessLicense businessLicense;// 营业执
	
	@Transient
	private DrivingLicense drivingLicense;//驾驶证
	
	
	
	public BusinessLicense getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(BusinessLicense businessLicense) {
		this.businessLicense = businessLicense;
	}
	public DrivingLicense getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(DrivingLicense drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Idcard getIdCard() {
		return idCard;
	}
	public void setIdCard(Idcard idCard) {
		this.idCard = idCard;
	}
	/**
	 * @return the supplementType
	 */
	public String getSupplementType() {
		return supplementType;
	}
	/**
	 * @param supplementType the supplementType to set
	 */
	public void setSupplementType(String supplementType) {
		this.supplementType = supplementType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CompanyType getCompanyType() {
		return companyType;
	}
	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public Auth getAudit() {
		return audit;
	}
	public void setAudit(Auth audit) {
		this.audit = audit;
	}
	public String getAreaFullName() {
		return areaFullName;
	}
	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getIdcard_id() {
		return idcard_id;
	}
	public void setIdcard_id(String idcard_id) {
		this.idcard_id = idcard_id;
	}
	public String getBusiness_license_id() {
		return business_license_id;
	}
	public void setBusiness_license_id(String business_license_id) {
		this.business_license_id = business_license_id;
	}
	public String getDriving_license_id() {
		return driving_license_id;
	}
	public void setDriving_license_id(String driving_license_id) {
		this.driving_license_id = driving_license_id;
	}
	/**
	 * @return the account_id
	 */
	public String getAccount_id() {
		return account_id;
	}
	/**
	 * @param account_id the account_id to set
	 */
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	/**
	 * @return the failed_msg
	 */
	public String getFailed_msg() {
		return failed_msg;
	}
	/**
	 * @param failed_msg the failed_msg to set
	 */
	public void setFailed_msg(String failed_msg) {
		this.failed_msg = failed_msg;
	}
	/**
	 * @return the aut_time
	 */
	public Date getAut_time() {
		return aut_time;
	}
	/**
	 * @param aut_time the aut_time to set
	 */
	public void setAut_time(Date aut_time) {
		this.aut_time = aut_time;
	}
	/**
	 * @return the aut_user_id
	 */
	public String getAut_user_id() {
		return aut_user_id;
	}
	/**
	 * @param aut_user_id the aut_user_id to set
	 */
	public void setAut_user_id(String aut_user_id) {
		this.aut_user_id = aut_user_id;
	}
	/**
	 * @return the aut_supplement_type
	 */
	public String getAut_supplement_type() {
		return aut_supplement_type;
	}
	/**
	 * @param aut_supplement_type the aut_supplement_type to set
	 */
	public void setAut_supplement_type(String aut_supplement_type) {
		this.aut_supplement_type = aut_supplement_type;
	}
	/**
	 * @return the companyTypes
	 */
	public String[] getCompanyTypes() {
		return companyTypes;
	}
	/**
	 * @param companyTypes the companyTypes to set
	 */
	public void setCompanyTypes(String[] companyTypes) {
		this.companyTypes = companyTypes;
	}
	/**
	 * @return the areaIds
	 */
	public String[] getAreaIds() {
		return areaIds;
	}
	/**
	 * @param areaIds the areaIds to set
	 */
	public void setAreaIds(String[] areaIds) {
		this.areaIds = areaIds;
	}
	/**
	 * @return the companyAuths
	 */
	public Auth[] getCompanyAuths() {
		return companyAuths;
	}
	/**
	 * @param companyAuths the companyAuths to set
	 */
	public void setCompanyAuths(Auth[] companyAuths) {
		this.companyAuths = companyAuths;
	}
	/**
	 * @return the companyTypeId
	 */
	public String getCompanyTypeId() {
		return companyTypeId;
	}
	/**
	 * @param companyTypeId the companyTypeId to set
	 */
	public void setCompanyTypeId(String companyTypeId) {
		this.companyTypeId = companyTypeId;
	}
	/**
	 * @return the source_serch
	 */
	public Integer[] getSource_serch() {
		return source_serch;
	}
	/**
	 * @param source_serch the source_serch to set
	 */
	public void setSource_serch(Integer[] source_serch) {
		this.source_serch = source_serch;
	}
	/**
	 * @return the status_serch
	 */
	public Status[] getStatus_serch() {
		return status_serch;
	}
	/**
	 * @param status_serch the status_serch to set
	 */
	public void setStatus_serch(Status[] status_serch) {
		this.status_serch = status_serch;
	}
	 
	 
}
