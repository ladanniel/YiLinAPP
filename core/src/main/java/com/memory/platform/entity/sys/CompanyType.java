package com.memory.platform.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "sys_company_type")
public class CompanyType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column
	private String name; // 类型名称
	@Column
	private Boolean is_register; // 是否在注册页面显示
	@Column
	private Boolean is_aut;// 是否启用认证 0:关闭 1:开启
	@Column
	private Boolean business_license;// 是否启用营业执照 0:不认证营业执照 1:认证营业执照
	@Column
	private Boolean idcard;// 是否启用身份证 0：不认证身份证 1:认证身份证
	@Column
	private Boolean driving_license; // 是否启用驾驶证0：不认证驾驶证 1:认证驾驶证

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the is_register
	 */
	public Boolean getIs_register() {
		return is_register;
	}

	/**
	 * @param is_register
	 *            the is_register to set
	 */
	public void setIs_register(Boolean is_register) {
		this.is_register = is_register;
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
	 * @return the business_license
	 */
	public Boolean getBusiness_license() {
		return business_license;
	}

	/**
	 * @param business_license
	 *            the business_license to set
	 */
	public void setBusiness_license(Boolean business_license) {
		this.business_license = business_license;
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

}
