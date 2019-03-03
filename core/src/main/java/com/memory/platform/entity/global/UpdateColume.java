package com.memory.platform.entity.global;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年5月27日 下午1:48:41 修 改 人： 日 期： 描 述： 修改列数据实体 版 本 号：
 * V1.0
 */
public class UpdateColume {
	private String id;// 数据ID
	private String field; // 修改字段
	private String field_value; // 修改的值

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the field_value
	 */
	public String getField_value() {
		return field_value;
	}

	/**
	 * @param field_value
	 *            the field_value to set
	 */
	public void setField_value(String field_value) {
		this.field_value = field_value;
	}

}
