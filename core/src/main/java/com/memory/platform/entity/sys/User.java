package com.memory.platform.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "mem_account")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column
	private String account; // 账号
	@Column
	private String acc_phone; // 手机账户
	@Column
	private String password; // 密码
	@Column
	private Integer status; // 状态
	@Column
	private boolean authentication;// 是否通过实名认证
	@Column
	private String name; // 姓名
	@Column
	private String company_id;// 商户ID
	@Column
	private String role_id;// 角色ID
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
	@Column
	private String company_section_id;// 所属部门ID

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
	 * @return the acc_phone
	 */
	public String getAcc_phone() {
		return acc_phone;
	}

	/**
	 * @param acc_phone
	 *            the acc_phone to set
	 */
	public void setAcc_phone(String acc_phone) {
		this.acc_phone = acc_phone;
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

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the authentication
	 */
	public boolean isAuthentication() {
		return authentication;
	}

	/**
	 * @param authentication
	 *            the authentication to set
	 */
	public void setAuthentication(boolean authentication) {
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
	public String getCompany_id() {
		return company_id;
	}

	/**
	 * @param company_id
	 *            the company_id to set
	 */
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	/**
	 * @return the role_id
	 */
	public String getRole_id() {
		return role_id;
	}

	/**
	 * @param role_id
	 *            the role_id to set
	 */
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	/**
	 * @return the idcard_id
	 */
	public String getIdcard_id() {
		return idcard_id;
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

	/**
	 * @return the company_section_id
	 */
	public String getCompany_section_id() {
		return company_section_id;
	}

	/**
	 * @param company_section_id
	 *            the company_section_id to set
	 */
	public void setCompany_section_id(String company_section_id) {
		this.company_section_id = company_section_id;
	}

}
