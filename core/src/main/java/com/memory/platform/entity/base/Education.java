package com.memory.platform.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年6月1日 下午3:26:39 
* 修 改 人： 
* 日   期： 
* 描   述： 学历信息字典表
* 版 本 号：  V1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "bas_education")
public class Education extends BaseEntity {

	@Column
	private String name;//学历名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
