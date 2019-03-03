package com.memory.platform.entity.capital;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.AES;

/**
 * B2B账户
 */
@Entity
@Table(name = "mem_b2b_account")
public class B2bAccount extends BaseEntity {

	private static final long serialVersionUID = -3773638847121230647L;

	String boothNo;
	String subAcc;
	String currency;
	String tradeMemCode;
	String tradeMemBerName;
	Integer tradeMemBerGrade;
	String gradeCode;
	Integer tradeMemberProperty;
	String contact;
	String contactPhone;
	String phone;
	String contactAddr;
	String businessName;
	Integer papersType;
	String papersCode;
	Integer isMessager;
	String messagePhone;
	String email;
	String linkSubAccount;
	String settleAccount;
	String settleAccountName;
	String settleAccountBank;
	Integer signType;
	Integer accountType;
	Integer signBank;
	Integer signOtherBank;
	Integer signState;
	Integer isOuath;
	Integer ouathWay;
	String ouathTimes;
	public String getBoothNo() {
		return boothNo;
	}
	public void setBoothNo(String boothNo) {
		this.boothNo = boothNo;
	}
	public String getSubAcc() {
		return subAcc;
	}
	public void setSubAcc(String subAcc) {
		this.subAcc = subAcc;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTradeMemCode() {
		return tradeMemCode;
	}
	public void setTradeMemCode(String tradeMemCode) {
		this.tradeMemCode = tradeMemCode;
	}
	public String getTradeMemBerName() {
		return tradeMemBerName;
	}
	public void setTradeMemBerName(String tradeMemBerName) {
		this.tradeMemBerName = tradeMemBerName;
	}
	public Integer getTradeMemBerGrade() {
		return tradeMemBerGrade;
	}
	public void setTradeMemBerGrade(Integer tradeMemBerGrade) {
		this.tradeMemBerGrade = tradeMemBerGrade;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public Integer getTradeMemberProperty() {
		return tradeMemberProperty;
	}
	public void setTradeMemberProperty(Integer tradeMemberProperty) {
		this.tradeMemberProperty = tradeMemberProperty;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContactAddr() {
		return contactAddr;
	}
	public void setContactAddr(String contactAddr) {
		this.contactAddr = contactAddr;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getPapersType() {
		return papersType;
	}
	public void setPapersType(Integer papersType) {
		this.papersType = papersType;
	}
	public String getPapersCode() {
		return papersCode;
	}
	public void setPapersCode(String papersCode) {
		this.papersCode = papersCode;
	}
	public Integer getIsMessager() {
		return isMessager;
	}
	public void setIsMessager(Integer isMessager) {
		this.isMessager = isMessager;
	}
	public String getMessagePhone() {
		return messagePhone;
	}
	public void setMessagePhone(String messagePhone) {
		this.messagePhone = messagePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLinkSubAccount() {
		return linkSubAccount;
	}
	public void setLinkSubAccount(String linkSubAccount) {
		this.linkSubAccount = linkSubAccount;
	}
	public String getSettleAccount() {
		return settleAccount;
	}
	public void setSettleAccount(String settleAccount) {
		this.settleAccount = settleAccount;
	}
	public String getSettleAccountName() {
		return settleAccountName;
	}
	public void setSettleAccountName(String settleAccountName) {
		this.settleAccountName = settleAccountName;
	}
	public String getSettleAccountBank() {
		return settleAccountBank;
	}
	public void setSettleAccountBank(String settleAccountBank) {
		this.settleAccountBank = settleAccountBank;
	}
	public Integer getSignType() {
		return signType;
	}
	public void setSignType(Integer signType) {
		this.signType = signType;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getSignBank() {
		return signBank;
	}
	public void setSignBank(Integer signBank) {
		this.signBank = signBank;
	}
	public Integer getSignOtherBank() {
		return signOtherBank;
	}
	public void setSignOtherBank(Integer signOtherBank) {
		this.signOtherBank = signOtherBank;
	}
	public Integer getSignState() {
		return signState;
	}
	public void setSignState(Integer signState) {
		this.signState = signState;
	}
	public Integer getIsOuath() {
		return isOuath;
	}
	public void setIsOuath(Integer isOuath) {
		this.isOuath = isOuath;
	}
	public Integer getOuathWay() {
		return ouathWay;
	}
	public void setOuathWay(Integer ouathWay) {
		this.ouathWay = ouathWay;
	}
	public String getOuathTimes() {
		return ouathTimes;
	}
	public void setOuathTimes(String ouathTimes) {
		this.ouathTimes = ouathTimes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
