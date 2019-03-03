package com.memory.platform.entity.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.entity.sys.CompanySection;
import com.memory.platform.entity.sys.Role;
import com.memory.platform.global.Auth;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午5:27:13 修 改 人： 日 期： 描 述： 账户表 版 本 号： V1.0
 */
@Entity
@Table(name = "mem_account")
public class Account extends BaseEntity {

	private static final long serialVersionUID = 7850669215556316090L;

	@Column
	private String account; // 账号
	@Column
	private String phone; // 手机账户
	@Column
	private String password; // 密码

	// 0:启用 1:停用 2:删除
	public enum Status {
		start, stop, delete
	}

	@Column
	private Status status; // 状态

	private Auth authentication;// 是否通过实名认证
	@Column
	private String name; // 姓名
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Column
	private String idcard_id;// 身份证ID
	@Column
	private String driving_license_id;// 驾驶证ID
	@Column
	private String login_ip;// 登陆IP
	@Column
	private int login_count;// 登陆次数
	@Column
	private Date last_login_time;// 最后登录时间
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_section_id")
	private CompanySection companySection;
	@Column
	private String paypassword; // 支付密码

	// 0：未启用，1:已开启，2:冻结关闭
	public enum CapitalStatus {
		notenable, open, close
	}

	@Column
	private CapitalStatus capitalStatus = CapitalStatus.notenable;
	@Column
	private String avatar; // 用户头像

	// 用户类型 0：商户 1：个人员工 2：管理员
	public enum UserType {
		company, user, admin
	}

	@Column
	private UserType userType = UserType.user;

	// 手机环境 IOS Android
	public enum PhonePlatform {
		none, ios, android
	}

	// 角色类型 货主，车队,货主员工,车队员工
	public enum RoleType {
		none, consignor, truck 
	}

	private RoleType role_type;

	public RoleType getRole_type() {
		return role_type;
	}

	public void setRole_type(RoleType role_type) {
		this.role_type = role_type;
	}
	
	
	public boolean getIsEmployee() {
		return  getIsAdmin() == false;
	}
	
	
	public boolean getIsAdmin() {
		return this.getRole().getIs_admin();
	}
	
	@Column
	private PhonePlatform phone_platform;
	@Column
	private String device_id;
	@Column
	private String push_id;

	 
	public PhonePlatform getPhone_platform() {
		return phone_platform;
	}

	public void setPhone_platform(PhonePlatform phone_platform) {
		this.phone_platform = phone_platform;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getPush_id() {
		return push_id;
	}

	public void setPush_id(String push_id) {
		this.push_id = push_id;
	}

	private Date aut_time;// 审核时间
	private String aut_user_id;// 审核人
	private String aut_supplement_type = "";// 认证补录类型
	private String failed_msg;// 认证未通过原因

	private String token;// app 接口 token
	@Transient
	private String companyType;// 商户类型

	@Transient
	private String code;// 手机验证码
	@Transient
	private Auth[] accountAuths;// 认证状态，用于数据查询
	@Transient
	private String companyName;// 认证状态，用于数据查询
	@Transient
	private List<Map<String, Object>> appList;// 认证状态，用于数据查询

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Auth getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Auth authentication) {
		this.authentication = authentication;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the company_id
	 */

	/**
	 * @return the idcard_id
	 */
	public String getIdcard_id() {
		return idcard_id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @param idcard_id
	 *            the idcard_id to set
	 */
	public void setIdcard_id(String idcard_id) {
		this.idcard_id = idcard_id;
	}

	/**
	 * @return the driving_license_id
	 */
	public String getDriving_license_id() {
		return driving_license_id;
	}

	/**
	 * @param driving_license_id
	 *            the driving_license_id to set
	 */
	public void setDriving_license_id(String driving_license_id) {
		this.driving_license_id = driving_license_id;
	}

	/**
	 * @return the login_ip
	 */
	public String getLogin_ip() {
		return login_ip;
	}

	/**
	 * @param login_ip
	 *            the login_ip to set
	 */
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	/**
	 * @return the login_count
	 */
	public int getLogin_count() {
		return login_count;
	}

	/**
	 * @param login_count
	 *            the login_count to set
	 */
	public void setLogin_count(int login_count) {
		this.login_count = login_count;
	}

	/**
	 * @return the last_login_time
	 */
	public Date getLast_login_time() {
		return last_login_time;
	}

	/**
	 * @param last_login_time
	 *            the last_login_time to set
	 */
	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public CompanySection getCompanySection() {
		return companySection;
	}

	public void setCompanySection(CompanySection companySection) {
		this.companySection = companySection;
	}

	/**
	 * @return the paypassword
	 */
	public String getPaypassword() {
		return paypassword;
	}

	/**
	 * @param paypassword
	 *            the paypassword to set
	 */
	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public CapitalStatus getCapitalStatus() {
		return capitalStatus;
	}

	public void setCapitalStatus(CapitalStatus capitalStatus) {
		this.capitalStatus = capitalStatus;
	}

	/**
	 * @return the aut_time
	 */
	public Date getAut_time() {
		return aut_time;
	}

	/**
	 * @param aut_time
	 *            the aut_time to set
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
	 * @param aut_user_id
	 *            the aut_user_id to set
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
	 * @param aut_supplement_type
	 *            the aut_supplement_type to set
	 */
	public void setAut_supplement_type(String aut_supplement_type) {
		this.aut_supplement_type = aut_supplement_type;
	}

	/**
	 * @return the failed_msg
	 */
	public String getFailed_msg() {
		return failed_msg;
	}

	/**
	 * @param failed_msg
	 *            the failed_msg to set
	 */
	public void setFailed_msg(String failed_msg) {
		this.failed_msg = failed_msg;
	}

	/**
	 * @return the accountAuths
	 */
	public Auth[] getAccountAuths() {
		return accountAuths;
	}

	/**
	 * @param accountAuths
	 *            the accountAuths to set
	 */
	public void setAccountAuths(Auth[] accountAuths) {
		this.accountAuths = accountAuths;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the appList
	 */
	public List<Map<String, Object>> getAppList() {
		return appList;
	}

	/**
	 * @param appList
	 *            the appList to set
	 */
	public void setAppList(List<Map<String, Object>> appList) {
		this.appList = appList;
	}

}
