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
* 描   述：货箱属性表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_track_container")
public class TruckContainer implements Serializable{

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
	@JoinColumn(name="containers_type_id"  )
	private ContainersType containersType;//货箱类型
	
	@Column
	private Double containers_long;//货箱长度
	
	@Column
	private Double containers_width;//货箱宽度

	@Column
	private Double containers_height;//货箱高度
	
	@Column
	private Double volume;//容积
	
	@Column
	private String container_no;//车厢号码
	
	public String getContainer_no() {
		return container_no;
	}

	public void setContainer_no(String container_no) {
		this.container_no = container_no;
	}

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

	public ContainersType getContainersType() {
		return containersType;
	}

	public void setContainersType(ContainersType containersType) {
		this.containersType = containersType;
	}

	public Double getContainers_long() {
		return containers_long;
	}

	public void setContainers_long(Double containers_long) {
		this.containers_long = containers_long;
	}

	public Double getContainers_width() {
		return containers_width;
	}

	public void setContainers_width(Double containers_width) {
		this.containers_width = containers_width;
	}

	public Double getContainers_height() {
		return containers_height;
	}

	public void setContainers_height(Double containers_height) {
		this.containers_height = containers_height;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	
	
}











