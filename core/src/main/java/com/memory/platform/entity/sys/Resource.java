package com.memory.platform.entity.sys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
 * 系统资源实体类
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月23日 下午4:53:08 
* 修 改 人： 
* 日   期： 
* 描   述： 
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_resource")
public class Resource extends BaseEntity{

	private static final long serialVersionUID = 1L;
	 

	@Column
	private String name;//资源名称
	@Column
	private String url;//资源地址
	@Column
	private String remark; //备注
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "company_type_id")
	private CompanyType companyType;//所属商户类型 
	
	 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the companyType
	 */
	public CompanyType getCompanyType() {
		return companyType;
	}

	/**
	 * @param companyType the companyType to set
	 */
	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	 

	 
	

}
