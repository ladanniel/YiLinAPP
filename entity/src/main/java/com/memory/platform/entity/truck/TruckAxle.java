package com.memory.platform.entity.truck;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
* 创 建 人： liyanzhang
* 日    期： 2016年5月30日 19:00:27
* 修 改 人： 
* 日   期： 
* 描   述：轮轴属性表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_track_axle")
public class TruckAxle implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="track_id"  )
	private Truck truck;//车辆对象
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="axle_type_id"  )
	private AxleType axleType;//轮轴类型
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bearing_num_id"  )
	private BearingNum bearingNum;//轮轴数对象
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Truck getTruck() {
		return truck;
	}

	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	public AxleType getAxleType() {
		return axleType;
	}

	public void setAxleType(AxleType axleType) {
		this.axleType = axleType;
	}

	public BearingNum getBearingNum() {
		return bearingNum;
	}

	public void setBearingNum(BearingNum bearingNum) {
		this.bearingNum = bearingNum;
	}
	
}











