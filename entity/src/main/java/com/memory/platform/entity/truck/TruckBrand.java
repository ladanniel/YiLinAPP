package com.memory.platform.entity.truck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： liyanzhang
* 日    期： 2016年5月30日 19:00:27
* 修 改 人： 
* 日   期： 
* 描   述：发动机品牌表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "bas_truck_brand")
public class TruckBrand extends BaseEntity  {

	private static final long serialVersionUID = 1L;
	@Column
	private String name;//品牌名称
	@Column(name="first_letter")
	private String first_letter;//品牌首字母

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst_letter() {
		return first_letter;
	}

	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}
	
}
