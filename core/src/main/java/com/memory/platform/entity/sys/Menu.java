package com.memory.platform.entity.sys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
 * 
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月23日 下午7:13:30 
* 修 改 人： 
* 日   期： 
* 描   述： 菜单实体类
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	@Column
	private String name; //菜单名称
	@Column
	private String iconcls;//菜单图标
	@Column
	private String parent_id;//所属菜单ID
	@Column
	private Integer expanded; //是否咱开 
	@Column
	private Integer soft;//排序
	@Column
	private Integer type; //类型
	@Column
	private boolean enable;//是否启用
	
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "resource_id")
	private Resource resource;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIconcls() {
		return iconcls;
	}


	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}


	public String getParent_id() {
		return parent_id;
	}


	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}


	public Integer getExpanded() {
		return expanded;
	}


	public void setExpanded(Integer expanded) {
		this.expanded = expanded;
	}


	public Integer getSoft() {
		return soft;
	}


	public void setSoft(Integer soft) {
		this.soft = soft;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Resource getResource() {
		return resource;
	}


	public void setResource(Resource resource) {
		this.resource = resource;
	}

	 

}
