package com.memory.platform.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月24日 上午11:50:05 
* 修 改 人： 
* 日   期： 
* 描   述： 地区表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "bas_area")
public class Area implements Serializable  {
	 
 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 9198109510019625837L;
	@Id
	private String id; //地区ID
	@Column
	private String name; //地区名称
	@Column
	private String parent_id;//所属地区ID
	@Column 
	private String short_name;//地区缩写名称
	@Column
	private int level_type; //地区等级
	@Column
	private String full_name;//地区全称
	@Column
	private String lng; //地区经度
	@Column
	private String lat; // 地区纬度
	@Column
	private String pinyin; //地区拼英
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}
	/**
	 * @param parent_id the parent_id to set
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	/**
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}
	/**
	 * @param short_name the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	/**
	 * @return the level_type
	 */
	public int getLevel_type() {
		return level_type;
	}
	/**
	 * @param level_type the level_type to set
	 */
	public void setLevel_type(int level_type) {
		this.level_type = level_type;
	}
	/**
	 * @return the full_name
	 */
	public String getFull_name() {
		return full_name;
	}
	/**
	 * @param full_name the full_name to set
	 */
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	/**
	 * @return the lng
	 */
	public String getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}
	/**
	 * @param pinyin the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	


}
