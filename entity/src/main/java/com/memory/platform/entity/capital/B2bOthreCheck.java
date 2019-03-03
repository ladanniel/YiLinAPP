package com.memory.platform.entity.capital;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "mem_b2b_other_check")
public class B2bOthreCheck extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8760328833604404217L;
	String PayeeSign; //收款标志  
	//0、付款 1、收款
	String transCode; //交易码    
	//1、成功 2、失败
	String accountType; // 账户类型 
	//1、子账户 2、实体账户 3、资金监管账户
	
  
	String account;   //账号
	public String getPayeeSign() {
		return PayeeSign;
	}
	public void setPayeeSign(String payeeSign) {
		PayeeSign = payeeSign;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	String payCode;
	String subAccount;
	String treadeMenCode;
	String transCodeId; //交易流水号
	String otherSubAccount; //对方子账号
	Date endDay;  //日切日期
	Date tradeTimes;
	Date startTimes;
	Date endTimes;
	int order;
	double CreMoney;
	String memBerCode;
   
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}
	public String getTreadeMenCode() {
		return treadeMenCode;
	}
	public void setTreadeMenCode(String treadeMenCode) {
		this.treadeMenCode = treadeMenCode;
	}
	public String getTransCodeId() {
		return transCodeId;
	}
	public void setTransCodeId(String transCodeId) {
		this.transCodeId = transCodeId;
	}
	public String getOtherSubAccount() {
		return otherSubAccount;
	}
	public void setOtherSubAccount(String otherSubAccount) {
		this.otherSubAccount = otherSubAccount;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
	public Date getTradeTimes() {
		return tradeTimes;
	}
	public void setTradeTimes(Date tradeTimes) {
		this.tradeTimes = tradeTimes;
	}
	public Date getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(Date startTimes) {
		this.startTimes = startTimes;
	}
	public Date getEndTimes() {
		return endTimes;
	}
	public void setEndTimes(Date endTimes) {
		this.endTimes = endTimes;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public double getCreMoney() {
		return CreMoney;
	}
	public void setCreMoney(double creMoney) {
		CreMoney = creMoney;
	}
	public String getMemBerCode() {
		return memBerCode;
	}
	public void setMemBerCode(String memBerCode) {
		this.memBerCode = memBerCode;
	}
	
	
	
	

}
