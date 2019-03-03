package com.memory.platform.entity.sys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
 * 系统角色实体类 创 建 人： yangjiaqiao 日 期： 2016年4月23日 下午4:51:50 修 改 人： 日 期： 描 述： 版 本 号：
 * V1.0
 */
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column
	private String name;// 角色名称
	@Column
	private Boolean status;// 角色状态
	@Column(updatable = false)
	private String role_code;// 角色唯一码
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "company_type_id")
	private CompanyType companyType;// 所属商户类型
	@Column
	private Boolean is_aut;// 是否启用认证 0:关闭 1:开启
	@Column
	private Boolean idcard;// 身份证认证 0：不认证身份证 1:认证身份证
	@Column
	private Boolean driving_license;// 驾驶证 0：不认证身份证 1:认证身份证
	@Column
	private Boolean is_admin;// 是否管理员

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
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * @return the role_code
	 */
	public String getRole_code() {
		return role_code;
	}

	/**
	 * @param role_code
	 *            the role_code to set
	 */
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	/**
	 * @return the companyType
	 */
	public CompanyType getCompanyType() {
		return companyType;
	}

	/**
	 * @param companyType
	 *            the companyType to set
	 */
	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	/**
	 * @return the is_aut
	 */
	public Boolean getIs_aut() {
		return is_aut;
	}

	/**
	 * @param is_aut
	 *            the is_aut to set
	 */
	public void setIs_aut(Boolean is_aut) {
		this.is_aut = is_aut;
	}

	/**
	 * @return the idcard
	 */
	public Boolean getIdcard() {
		return idcard;
	}

	/**
	 * @param idcard
	 *            the idcard to set
	 */
	public void setIdcard(Boolean idcard) {
		this.idcard = idcard;
	}

	/**
	 * @return the driving_license
	 */
	public Boolean getDriving_license() {
		return driving_license;
	}

	/**
	 * @param driving_license
	 *            the driving_license to set
	 */
	public void setDriving_license(Boolean driving_license) {
		this.driving_license = driving_license;
	}

	/**
	 * @return the is_admin
	 */
	public Boolean getIs_admin() {
		return is_admin;
	}

	/**
	 * @param is_admin
	 *            the is_admin to set
	 */
	public void setIs_admin(Boolean is_admin) {
		this.is_admin = is_admin;
	}

}
