package com.memory.platform.entity.member;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.base.Education;
/**
* 创 建 人： yico-cj
* 日    期： 2016年6月1日 下午3:26:39 
* 修 改 人： 
* 日   期： 
* 描   述：  宜林用户详细信息表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_staff")
public class Staff extends BaseEntity {

	private static final long serialVersionUID = 7850669215556316090L;
 
	
	@Column
	private Integer age; // 年龄
	@Column
	private String name; // 姓名
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id", nullable = false)
	private Account account;
	@Column
	private Date birthday;// 出生日期
	@Column
	private Sex sex;// 性别
	@Column
	private String nation;// 名族
	@Column
	private String origin;// 籍贯
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="education_id")
	private Education education; //学历
	@Column
	private String major;  //专业
	//0：男，1:女
	public enum Sex{
		female,male
	}
	@Column
	private  String graduate_school;    //毕业学校
	@Column
	private String email;//邮箱
	
	@Transient
	private String companyId;
	

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGraduate_school() {
		return graduate_school;
	}

	public void setGraduate_school(String graduate_school) {
		this.graduate_school = graduate_school;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	 
}
