package com.memory.platform.entity.sys;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月24日 下午3:46:32 
* 修 改 人： 
* 日   期： 
* 描   述： 意见反馈
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_feedback")
public class Feedback extends BaseEntity {

	private static final long serialVersionUID = -5567577802974314497L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id"  )
	private Account account;
	private String title;
	private String info;
	//来源类型0：网站，1：安卓，2：IOS
	public enum Type{
		web,android,ios
	}
	private Type type;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
