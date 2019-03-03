package com.memory.platform.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月18日 上午10:42:01 
* 修 改 人： 
* 日   期： 
* 描   述： APP资源表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_menu_app")
public class MenuApp extends BaseEntity {

	public enum AppType{
		MoneyManager,SupplyManager
	}
	 
	//0:安装原生 1：HTML5连接地址
	public enum Type{
		Native,Html5
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String name; //资源名称
	@Column
	private String iconcls;//资源图标
	@Column(name="iconcls_color")
	private String iconclsColor;//资源图标颜色
	@Column(name="app_type")
	private AppType appType;
	@Column
	private Type type;   //状态
	@Column
    private String path;//资源地址
	public AppType getAppType() {
		return appType;
	}
	/**
	 * @return the iconcls
	 */
	public String getIconcls() {
		return iconcls;
	}
	/**
	 * @return the iconclsColor
	 */
	public String getIconclsColor() {
		return iconclsColor;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	/**
	 * @param iconcls the iconcls to set
	 */
	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}
	/**
	 * @param iconclsColor the iconclsColor to set
	 */
	public void setIconclsColor(String iconclsColor) {
		this.iconclsColor = iconclsColor;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
