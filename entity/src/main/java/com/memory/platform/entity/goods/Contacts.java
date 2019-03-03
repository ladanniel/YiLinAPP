package com.memory.platform.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 上午11:10:07 
* 修 改 人： 
* 日   期： 
* 描   述： 货物联系人信息表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "goods_contacts")
public class Contacts extends BaseEntity {

	private static final long serialVersionUID = -2617715493645630938L;

	@Column(name = "company_id")
	private String companyId;   //创建者id
	private String name;  //联系人姓名
	private String mobile;  //联系人电话
	private String email;  //联系人邮箱
	private String areaFullName;  //区域
	@Column(name = "area_id")
	private String areaId;  //联系人区域id
	private String address;  //联系人地址
	private String point;    //联系人坐标
	private ContactsType contactsType;  //联系人类型
	//0:发货人   1:收货人
	public enum ContactsType{
		consignor,consignee
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getAreaFullName() {
		return areaFullName;
	}
	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}
	
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public ContactsType getContactsType() {
		return contactsType;
	}
	public void setContactsType(ContactsType contactsType) {
		this.contactsType = contactsType;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	
}
