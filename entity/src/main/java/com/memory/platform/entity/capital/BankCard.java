package com.memory.platform.entity.capital;

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
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午4:55:25 
* 修 改 人： 
* 日   期： 
* 描   述： 用户银行卡实体对象
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_bank_card")
public class BankCard extends BaseEntity {

	private static final long serialVersionUID = -3773638847121230647L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id", nullable = false)
	private Account account;   //用户
	@Column(name="bank_card")
	private String bankCard;     //银行卡号
	@Column(name="open_bank")
	private String openBank;    //开户行名称
	@Column(name="bank_name")
	private String bankName;   //银行名称
	@Column(name="cn_name")
	private String cnName;
	private String image;
	private String markImage;
	//0:信用卡   1:储蓄卡
	public enum BankType{
		creditCard,debitCard
	}
	@Column(name = "bank_type")
	private BankType bankType;   //银行卡种类型
	private String mobile;  //银行预留手机号
	@Version
	@Column(name = "version")
	private Integer version;
	private String orderNo;
	//绑定状态   0：默认未审核状态   1：成功   2：失败
	public enum BandStatus{
		defalut,success,fail
	}
	private BandStatus bandStatus;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getBankCard() {
		String card = null;
		try {
			card = AES.Decrypt(bankCard);
		} catch (Exception e) {
			throw new DataBaseException("数据解密异常。");
		}
		return card;
	}
	public void setBankCard(String bankCard) {
		try {
			this.bankCard = AES.Encrypt(bankCard);
		} catch (Exception e) {
			throw new DataBaseException("数据加密异常。");
		}
	}
	public String getOpenBank() {
		return openBank;
	}
	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public BankType getBankType() {
		return bankType;
	}
	public void setBankType(BankType bankType) {
		this.bankType = bankType;
	}
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getMarkImage() {
		return markImage;
	}
	public void setMarkImage(String markImage) {
		this.markImage = markImage;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public BandStatus getBandStatus() {
		return bandStatus;
	}
	public void setBandStatus(BandStatus bandStatus) {
		this.bandStatus = bandStatus;
	}
	
	
}
