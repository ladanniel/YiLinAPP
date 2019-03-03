package com.memory.platform.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
* 创 建 人： longqibo
* 日    期： 2016年6月23日 下午2:48:49 
* 修 改 人： 
* 日   期： 
* 描   述： 参数设置
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_parameter_manage")
public class ParameterManage extends BaseEntity {

	private static final long serialVersionUID = -8431880093470579606L;

	//0:货主  1:司机
	public enum ParaType{
		consignor,driver,withdrawal
	}
	@Column(name = "type")
	private ParaType key;   //参数类型
	@Column(name = "val")
	private double value;    //参数值
	private String description;   //描述
	public ParaType getKey() {
		return key;
	}
	public void setKey(ParaType key) {
		this.key = key;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
