package com.memory.platform.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "deposit_level")
public class DepositLevel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4814681583341463079L;
//	String id;
	String level_name;
	Double level_money;
//	Date create_time;
//	String add_user_id;
//	Date update_time;
//	String update_id;
	
	
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}
	public Double getLevel_money() {
		return level_money;
	}
	public void setLevel_money(Double level_money) {
		this.level_money = level_money;
	}
//	public Date getCreate_time() {
//		return create_time;
//	}
//	public void setCreate_time(Date create_time) {
//		this.create_time = create_time;
//	}
//	public String getAdd_user_id() {
//		return add_user_id;
//	}
//	public void setAdd_user_id(String add_user_id) {
//		this.add_user_id = add_user_id;
//	}
//	public Date getUpdate_time() {
//		return update_time;
//	}
//	public void setUpdate_time(Date update_time) {
//		this.update_time = update_time;
//	}
//	public String getUpdate_id() {
//		return update_id;
//	}
//	public void setUpdate_id(String update_id) {
//		this.update_id = update_id;
//	}


}
