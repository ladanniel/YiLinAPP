package com.memory.platform.entity.aut;

 

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 上午10:33:45 
* 修 改 人： 
* 日   期： 
* 描   述： 商户认证信息保存
* 版 本 号：  V1.0
 */
public class AutInfo {
	 private String company_id ;//商户ID
	 private String company_info;	//商户基本信息JSON字符串
	 private String idcard_info; //身份证信息JSON字符串
	 private String driving_license_info; //驾驶证信息JSON字符串
	 private String business_license_info; //营业执照信息JSON字符串
	 private String type ; //菜单类型
	/**
	 * @return the company_info
	 */
	public String getCompany_info() {
		 
		return company_info;
	}
	/**
	 * @param company_info the company_info to set
	 */
	public void setCompany_info(String company_info) {
		this.company_info = company_info;
	}
	/**
	 * @return the idcard_info
	 */
	public String getIdcard_info() {
		return idcard_info;
	}
	/**
	 * @param idcard_info the idcard_info to set
	 */
	public void setIdcard_info(String idcard_info) {
		this.idcard_info = idcard_info;
	}
	/**
	 * @return the driving_license_info
	 */
	public String getDriving_license_info() {
		return driving_license_info;
	}
	/**
	 * @param driving_license_info the driving_license_info to set
	 */
	public void setDriving_license_info(String driving_license_info) {
		this.driving_license_info = driving_license_info;
	}
	/**
	 * @return the business_license_info
	 */
	public String getBusiness_license_info() {
		return business_license_info;
	}
	/**
	 * @param business_license_info the business_license_info to set
	 */
	public void setBusiness_license_info(String business_license_info) {
		this.business_license_info = business_license_info;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the company_id
	 */
	public String getCompany_id() {
		return company_id;
	}
	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	 
	 
	 
}
