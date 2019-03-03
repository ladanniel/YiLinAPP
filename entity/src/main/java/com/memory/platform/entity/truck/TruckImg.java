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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： liyanzhang
* 日    期： 2016年5月30日 19:00:27
* 修 改 人： 
* 日   期： 
* 描   述：车牌图片实体
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_track_img")
@DynamicUpdate(true)
public class TruckImg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="track_id"  )
	private Truck truck;//车辆对象
	@Column
	private String track_ahead_img;//货车正前方图片
	@Column
	private String track_abehind_img;//货车正后方图片
	@Column
	private String track_console_img;//货车中控图片
	@Column
	private String track_left_front_img;//货车左前面45度图片
	@Column
	private String track_right_front_img;//货车右前面45度图片
	@Column
	private String track_side_img;//货车侧身图片
	@Column
	private String track_behind_img;//货车后侧面图片
	@Column      
	private String track_dashboard_img;//货车仪表盘图片
	public Truck getTruck() {
		return truck;
	}
	public void setTruck(Truck truck) {
		this.truck = truck;
	}
	public String getTrack_ahead_img() {
		return track_ahead_img;
	}
	public void setTrack_ahead_img(String track_ahead_img) {
		this.track_ahead_img = track_ahead_img;
	}
	public String getTrack_abehind_img() {
		return track_abehind_img;
	}
	public void setTrack_abehind_img(String track_abehind_img) {
		this.track_abehind_img = track_abehind_img;
	}
	public String getTrack_console_img() {
		return track_console_img;
	}
	public void setTrack_console_img(String track_console_img) {
		this.track_console_img = track_console_img;
	}
	public String getTrack_left_front_img() {
		return track_left_front_img;
	}
	public void setTrack_left_front_img(String track_left_front_img) {
		this.track_left_front_img = track_left_front_img;
	}
	public String getTrack_right_front_img() {
		return track_right_front_img;
	}
	public void setTrack_right_front_img(String track_right_front_img) {
		this.track_right_front_img = track_right_front_img;
	}
	public String getTrack_side_img() {
		return track_side_img;
	}
	public void setTrack_side_img(String track_side_img) {
		this.track_side_img = track_side_img;
	}
	public String getTrack_behind_img() {
		return track_behind_img;
	}
	public void setTrack_behind_img(String track_behind_img) {
		this.track_behind_img = track_behind_img;
	}
	public String getTrack_dashboard_img() {
		return track_dashboard_img;
	}
	public void setTrack_dashboard_img(String track_dashboard_img) {
		this.track_dashboard_img = track_dashboard_img;
	}
	
	
	
	
}







































