package com.memory.platform.entity.sys;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;

/**
 * 
* 创 建 人： yico-cj
* 日    期： 2016年4月27日 上午10:34:51 
* 修 改 人： 
* 日   期： 
* 描   述： 组织机构实体
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_company_section")
public class CompanySection extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column
	private String name; // 组织机构名字
	@Column
	private String parent_id;//所属组织机构ID
	@Column
	private String parent_name;//所属组织名称
	@Column
	private String company_id;//商户id
	@Column
	private String descs;//描述信息
	@Column
	private int status;//描述信息
	@Column
	private Integer leav;//组织机构级别
	@Column
	private String order_path = "";//组织机构排序
	@Transient
	private List<Map<String, Object>> staffs;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getLeav() {
		return leav;
	}
	public void setLeav(Integer leav) {
		this.leav = leav;
	}
	public String getOrder_path() {
		return order_path;
	}
	public void setOrder_path(String order_path) {
		this.order_path = order_path;
	}
	public List<Map<String, Object>> getStaffs() {
		return staffs;
	}
	public void setStaffs(List<Map<String, Object>> staffs) {
		this.staffs = staffs;
	}
	
}
