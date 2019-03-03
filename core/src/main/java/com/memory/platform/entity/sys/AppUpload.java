package com.memory.platform.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "sys_appupload")
@DynamicUpdate(true)
public class AppUpload extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String versionName;
	@Column
	private int versionCode = 0;
	@Column
	private String content;
	@Column
	private String path;
	@Column
	private Type type;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	//apk类型,0货主,1司机
	public enum Type{
		owner,driver
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	
	
}
