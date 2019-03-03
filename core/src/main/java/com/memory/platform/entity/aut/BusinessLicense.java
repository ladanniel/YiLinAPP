package com.memory.platform.entity.aut;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.global.AES;
import com.memory.platform.global.ThreeDESUtil;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月3日 上午10:05:27 
* 修 改 人： 
* 日   期： 
* 描   述： 营业执照表认证表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "aut_business_license")
public class BusinessLicense extends BaseEntity{

	private static final long serialVersionUID = 7978015080422323446L;
	@Column
	private String name; //营业执照名称
	@Column
	private String company_address; //地址
	@Column
	private String company_no; //营业执照编号
	@Column
	private Date company_create_date; //公司创建时间
	@Column
	private Date company_validity_date; //有限时间
	@Column
	private String company_img; //营业执照图片
	@Column
	private String account_id; //用户ID
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the company_address
	 */
	public String getCompany_address() {
		return company_address;
	}
	/**
	 * @param company_address the company_address to set
	 */
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	/**
	 * @return the company_no
	 * @throws Exception 
	 */
	public String getCompany_no() throws Exception {
		if(StringUtils.isBlank(company_no)){
			return AES.Decrypt(company_no);
		}else{
			return company_no;
		}
	}
	/**
	 * @param company_no the company_no to set
	 * @throws Exception 
	 */
	public void setCompany_no(String company_no) throws Exception {
		if(StringUtils.isBlank(company_no)){
			this.company_no = AES.Encrypt(company_no);
		}else{
			this.company_no = company_no;
		}
		
	}
	/**
	 * @return the company_create_date
	 */
	public Date getCompany_create_date() {
		return company_create_date;
	}
	/**
	 * @param company_create_date the company_create_date to set
	 */
	public void setCompany_create_date(Date company_create_date) {
		this.company_create_date = company_create_date;
	}
	/**
	 * @return the company_validity_date
	 */
	public Date getCompany_validity_date() {
		return company_validity_date;
	}
	/**
	 * @param company_validity_date the company_validity_date to set
	 */
	public void setCompany_validity_date(Date company_validity_date) {
		this.company_validity_date = company_validity_date;
	}
	/**
	 * @return the company_img
	 */
	public String getCompany_img() {
		return company_img;
	}
	/**
	 * @param company_img the company_img to set
	 */
	public void setCompany_img(String company_img) {
		this.company_img = company_img;
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

}
