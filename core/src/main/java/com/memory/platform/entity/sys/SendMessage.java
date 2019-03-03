package com.memory.platform.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午1:46:07 
* 修 改 人： 
* 日   期： 
* 描   述：  短信发送接口实体
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_send_message")
public class SendMessage extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2384734111364132279L;

	private String name;   //接口名称
	@Column(name = "send_url")
	private String sendUrl;  //发送短信url
	@Column(name = "query_balance_url")
	private String queryBalanceUrl;  //查询余额url
	//0,禁用   1，启用
	public enum Status{
		notenabled,enabled
	} 
	private Status status;  //状态
	@Column(name = "owned_business")
	private String ownedBusiness;  //短信所属商
	@Column(name = "send_success")
	private String sendSuccess; //发送成功表达式
	@Column(name = "query_bal_success")
	private String queryBalSuccess;   //查询余额成功表达式
	@Column(name = "return_blu_key")
	private String returnBluKey;  //接收余额关键字key
	public enum Type{
		json,xml,text,array
	}
	private Type type;  //返回数据类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSendUrl() {
		return sendUrl;
	}
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}
	public String getQueryBalanceUrl() {
		return queryBalanceUrl;
	}
	public void setQueryBalanceUrl(String queryBalanceUrl) {
		this.queryBalanceUrl = queryBalanceUrl;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getOwnedBusiness() {
		return ownedBusiness;
	}
	public void setOwnedBusiness(String ownedBusiness) {
		this.ownedBusiness = ownedBusiness;
	}
	public String getSendSuccess() {
		return sendSuccess;
	}
	public void setSendSuccess(String sendSuccess) {
		this.sendSuccess = sendSuccess;
	}
	public String getQueryBalSuccess() {
		return queryBalSuccess;
	}
	public void setQueryBalSuccess(String queryBalSuccess) {
		this.queryBalSuccess = queryBalSuccess;
	}
	public String getReturnBluKey() {
		return returnBluKey;
	}
	public void setReturnBluKey(String returnBluKey) {
		this.returnBluKey = returnBluKey;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
