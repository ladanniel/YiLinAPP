package com.memory.platform.entity.capital;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

/**
 *b2b出入金账户/绑定银行卡  
 * */

@Entity
@Table(name = "mem_b2b_bank_account")
public class B2bBankAccount extends BaseEntity{

	private static final long serialVersionUID = 705063266638492871L;

	//账户信息（数据库）
	String subAccount;
	String tradeMemCode;
	String merchantNo;
	Integer outComeAccountType;
	Integer isOther;
	Integer accountSign;
	Integer isOtherBank;
	String settleAccountName;//账户名
	String settleAccount;//账户号/卡号
	String payBank;
	String bankName;
	String modifyDate;
	String ouathEndTimes;
	Integer linkState;
	Integer ouathWay;
	//发送数据 含上面部分数据
	public static enum OperType{
		add(1,"绑定"),update(2,"变更"),delete(3,"删除");
		
		int index;
		String textInfo;
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getTextInfo() {
			return textInfo;
		}
		public void setTextInfo(String textInfo) {
			this.textInfo = textInfo;
		}
		OperType(int index,String textInfo){
			this.index=index;
			this.textInfo=textInfo;
		}
	}
	OperType operType;
	Integer linkAccountType;
	Boolean IsSecondAcc;//是否二类账户 0-1
	Integer papersType;//证件类型
	String papersCode;//证件号码
	Boolean strideValidate;//跨行是否验证信息 0-1
	String currCode;//币种
	
	
	public String getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}
	public String getTradeMemCode() {
		return tradeMemCode;
	}
	public void setTradeMemCode(String tradeMemCode) {
		this.tradeMemCode = tradeMemCode;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public Integer getOutComeAccountType() {
		return outComeAccountType;
	}
	public void setOutComeAccountType(Integer outComeAccountType) {
		this.outComeAccountType = outComeAccountType;
	}
	public Integer getIsOther() {
		return isOther;
	}
	public void setIsOther(Integer isOther) {
		this.isOther = isOther;
	}
	public Integer getAccountSign() {
		return accountSign;
	}
	public void setAccountSign(Integer accountSign) {
		this.accountSign = accountSign;
	}
	public Integer getIsOtherBank() {
		return isOtherBank;
	}
	public void setIsOtherBank(Integer isOtherBank) {
		this.isOtherBank = isOtherBank;
	}
	public String getSettleAccountName() {
		return settleAccountName;
	}
	public void setSettleAccountName(String settleAccountName) {
		this.settleAccountName = settleAccountName;
	}
	public String getSettleAccount() {
		return settleAccount;
	}
	public void setSettleAccount(String settleAccount) {
		this.settleAccount = settleAccount;
	}
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getOuathEndTimes() {
		return ouathEndTimes;
	}
	public void setOuathEndTimes(String ouathEndTimes) {
		this.ouathEndTimes = ouathEndTimes;
	}
	public Integer getLinkState() {
		return linkState;
	}
	public void setLinkState(Integer linkState) {
		this.linkState = linkState;
	}
	public Integer getOuathWay() {
		return ouathWay;
	}
	public void setOuathWay(Integer ouathWay) {
		this.ouathWay = ouathWay;
	}
	public OperType getOperType() {
		return operType;
	}
	public void setOperType(OperType operType) {
		this.operType = operType;
	}
	public Integer getLinkAccountType() {
		return linkAccountType;
	}
	public void setLinkAccountType(Integer linkAccountType) {
		this.linkAccountType = linkAccountType;
	}
	public Boolean getIsSecondAcc() {
		return IsSecondAcc;
	}
	public void setIsSecondAcc(Boolean isSecondAcc) {
		IsSecondAcc = isSecondAcc;
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
	public Boolean getStrideValidate() {
		return strideValidate;
	}
	public void setStrideValidate(Boolean strideValidate) {
		this.strideValidate = strideValidate;
	}
	public String getCurrCode() {
		return currCode;
	}
	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}
	
	
}
