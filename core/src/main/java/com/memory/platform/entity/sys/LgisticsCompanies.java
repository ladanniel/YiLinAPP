package com.memory.platform.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月30日 上午10:28:33 
* 修 改 人： 
* 日   期： 
* 描   述： 快递公司
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_lgistics_companies")
@DynamicUpdate(true)
public class LgisticsCompanies extends BaseEntity {

	private static final long serialVersionUID = -8822244996460585284L;
	
	private String name;
	private String code;
	
	private Integer index;//排序
	
	
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
