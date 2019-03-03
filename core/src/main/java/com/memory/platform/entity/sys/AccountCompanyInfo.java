package com.memory.platform.entity.sys;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 上午10:33:45 
* 修 改 人： 
* 日   期： 
* 描   述： 商户账户信息增加
* 版 本 号：  V1.0
 */
public class AccountCompanyInfo {
	
	 private String company_info;	//商户JSON信息
	 private String account_info;   //账户JSON信息
	 private String type ; //保存类型
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
	 * @return the account_info
	 */
	public String getAccount_info() {
		return account_info;
	}
	/**
	 * @param account_info the account_info to set
	 */
	public void setAccount_info(String account_info) {
		this.account_info = account_info;
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
	 
	 
}
