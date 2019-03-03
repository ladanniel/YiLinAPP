package com.memory.platform.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
* 创 建 人： yangjiaqiao
* 日    期： 2016年4月23日 下午4:35:48 
* 修 改 人： 
* 日   期： 
* 描   述： 父类实体
* 版 本 号：  V1.0
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	@Column(updatable = false)
	private Date create_time;// 创建时间
	@Column
	private Date update_time;// 修改时间
	@Column(updatable = false)
	private String add_user_id;// 创建人
	@Column
	private String update_user_id;// 修改人

	@Transient
	private String search; // 搜索关键字

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time
	 *            the create_time to set
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return the update_time
	 */
	public Date getUpdate_time() {
		return update_time;
	}

	/**
	 * @param update_time
	 *            the update_time to set
	 */
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	/**
	 * @return the add_user_id
	 */
	public String getAdd_user_id() {
		return add_user_id;
	}

	/**
	 * @param add_user_id
	 *            the add_user_id to set
	 */
	public void setAdd_user_id(String add_user_id) {
		this.add_user_id = add_user_id;
	}

	 

	/**
	 * @return the update_user_id
	 */
	public String getUpdate_user_id() {
		return update_user_id;
	}

	/**
	 * @param update_user_id the update_user_id to set
	 */
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
