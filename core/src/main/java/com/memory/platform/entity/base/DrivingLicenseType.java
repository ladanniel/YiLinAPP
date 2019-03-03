package com.memory.platform.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月9日 下午4:32:05 
* 修 改 人： 
* 日   期： 
* 描   述： 驾驶证准驾车型实体类
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "bas_driving_license_type")
public class DrivingLicenseType extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	 
	@Column
	private String no;//驾驶证准驾车型代码
	
	@Column
	private String desc;//驾驶证准驾车型描述

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
