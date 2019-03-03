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
* 描   述：车牌类型表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "bas_truck_plate")
public class TruckPlate extends BaseEntity  {

	private static final long serialVersionUID = 1L;
	@Column
	private String name;//车牌类型名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
