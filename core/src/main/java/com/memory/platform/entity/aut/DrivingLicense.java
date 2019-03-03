package com.memory.platform.entity.aut;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.base.DrivingLicenseType;
import com.memory.platform.global.AES;
import com.memory.platform.global.ThreeDESUtil;

/**
 * 
 * 创 建 人： yico-cj 日 期： 2016年5月2日 下午12:27:16 修 改 人： 日 期： 描 述： 驾驶证认证表 版 本 号： V1.0
 */
@Entity
@Table(name = "aut_driving_license")
public class DrivingLicense extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4493032927866951550L;
	@Column
	private String name; // 驾驶员姓名
	@Column
	private String driving_license_no;// 驾驶证编号
	@Column
	private Integer sex; // 性别
	@Column
	private String address; // 住址
	@Column
	private String driving_license_type_id;// 准驾车型
	@Column
	private Date valid_start_date; // 有效开始日期
	@Column
	private int valid_year; // 有效年份
	@Column
	private Date valid_end_date;// 有限结束日期
	@Column
	private String persion_img; // 驾驶人员图片
	@Column
	private String account_id;
	@Column
	private String company_id;// 所属商户信息
	@Column
	private String driving_license_img;// 驾驶证照片
	@Transient
	private DrivingLicenseType drivingLicenseType; // 准驾车型对象
	@Transient
	private String driving_license_no1;

	public String getDriving_license_no1() {
		return driving_license_no1;
	}

	public void setDriving_license_no1(String driving_license_no1) {
		this.driving_license_no1 = driving_license_no1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriving_license_no() throws Exception {
		if (!StringUtils.isBlank(driving_license_no)) {
			return AES.Decrypt(driving_license_no);
		} else {
			return driving_license_no;
		}
	}

	public void setDriving_license_no(String driving_license_no)
			throws Exception {
		if (!StringUtils.isBlank(driving_license_no)) {
			this.driving_license_no = AES.Encrypt(driving_license_no);
		} else {
			this.driving_license_no = driving_license_no;
		}
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDriving_license_type_id() {
		return driving_license_type_id;
	}

	public void setDriving_license_type_id(String driving_license_type_id) {
		this.driving_license_type_id = driving_license_type_id;
	}

	public Date getValid_start_date() {
		return valid_start_date;
	}

	public void setValid_start_date(Date valid_start_date) {
		this.valid_start_date = valid_start_date;
	}

	public Date getValid_end_date() {
		return valid_end_date;
	}

	public void setValid_end_date(Date valid_end_date) {
		this.valid_end_date = valid_end_date;
	}

	public String getPersion_img() {
		return persion_img;
	}

	public void setPersion_img(String persion_img) {
		this.persion_img = persion_img;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	/**
	 * @return the driving_license_img
	 */
	public String getDriving_license_img() {
		return driving_license_img;
	}

	/**
	 * @param driving_license_img
	 *            the driving_license_img to set
	 */
	public void setDriving_license_img(String driving_license_img) {
		this.driving_license_img = driving_license_img;
	}

	/**
	 * @return the valid_year
	 */
	public int getValid_year() {
		return valid_year;
	}

	/**
	 * @param valid_year
	 *            the valid_year to set
	 */
	public void setValid_year(int valid_year) {
		this.valid_year = valid_year;
	}

	/**
	 * @return the drivingLicenseType
	 */
	public DrivingLicenseType getDrivingLicenseType() {
		return drivingLicenseType;
	}

	/**
	 * @param drivingLicenseType
	 *            the drivingLicenseType to set
	 */
	public void setDrivingLicenseType(DrivingLicenseType drivingLicenseType) {
		this.drivingLicenseType = drivingLicenseType;
	}

}
