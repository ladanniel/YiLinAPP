package com.memory.platform.entity.capital;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.sys.CompanyType;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月10日 下午2:36:04 
* 修 改 人： 
* 日   期： 
* 描   述： 转账类型数据字典
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_transfer_type")
public class TransferType extends BaseEntity {

	private static final long serialVersionUID = 5115865608020270736L;

	private String name;  //转账类型名称
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="company_type_id", nullable = false)
	private CompanyType companyType;  

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
	
	
}
