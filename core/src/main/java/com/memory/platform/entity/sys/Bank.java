package com.memory.platform.entity.sys;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
* 创 建 人： longqibo
* 日    期： 2016年7月24日 下午3:48:44 
* 修 改 人： 
* 日   期： 
* 描   述： 银行数据字典实体
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_bank")
public class Bank extends BaseEntity {

	private static final long serialVersionUID = 6426748260370590903L;

	private String cnName;  //中文名称
	private String shortName;   //英文名称缩写
	private String image;   //图片logo
	private String markImage;   //银行标志logo
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getMarkImage() {
		return markImage;
	}
	public void setMarkImage(String markImage) {
		this.markImage = markImage;
	}
	
}
