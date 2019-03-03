package com.memory.platform.entity.capital;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.AES;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.ThreeDESUtil;

/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午3:31:40 
* 修 改 人： 
* 日   期： 
* 描   述：提现申请记录表 
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_cashapplication")
public class CashApplication extends BaseEntity {

	private static final long serialVersionUID = 3631568061337035396L;
	@ManyToOne
	@JoinColumn(name="account_id", nullable = false)
	private Account account;   //用户
	private Double money;     //提现金额
	private String bankcard;    //银行卡号
	private String openbank;	//开户行名称
	private String bankname;   //银行名称
	private String tradeNo;  //提现编号
	private Double actualMoney;  //实际到账金额
	public enum Status{
		waitProcess,success,failed,lock 
	}
	public enum BankStatus{
		none,
		waitProcess,
		success
		
		
	}
	/*
	 * 发送银行的报文
	 * */
	private String bank_payload;
	private String bank_receive_payload;
	public String getBank_payload() {
		return bank_payload;
	}
	public void setBank_payload(String bank_payload) {
		this.bank_payload = bank_payload;
	}
	public String getBank_receive_payload() {
		return bank_receive_payload;
	}
	public void setBank_receive_payload(String bank_receive_payload) {
		this.bank_receive_payload = bank_receive_payload;
	}
	@Column(name="bank_status")
	private BankStatus bank_status;
	public BankStatus getBank_status() {
		return bank_status;
	}
	public void setBank_status(BankStatus bank_status) {
		this.bank_status = bank_status;
	}
	private Status status;    //状态  0:待审核   1:通过    2:未通过  3:锁定
	private String remark;   //备注
	private String verifytPeopson;   //审核人
	private Date verifytime;   //审核时间
	@Transient
	private Date start;
	@Transient
	private Date end;
	@Transient
	private Date startTwo;
	@Transient
	private Date endTwo;
	@Version
	@Column(name = "version", nullable = false, length=10)
	private Integer version;   //版本号
	@Transient
	private String datestatus;
	@Transient
	private Integer day;
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getBankcard() {
		if(StringUtil.isEmpty(bankcard)){
			return bankcard;
		}
		String des = null;
		try {
			des = AES.Decrypt(bankcard);
		} catch (Exception e) {
			throw new BusinessException("数据解密异常。");
		}
		return des;
	}
	public void setBankcard(String bankcard) {
		if(StringUtil.isEmpty(bankcard)){
			this.bankcard = bankcard;
			return ;
		}
		String des = null;
		try {
			des = AES.Encrypt(bankcard);
		} catch (Exception e) {
			throw new BusinessException("数据加密异常。");
		}
		this.bankcard = des;
	}
	public String getOpenbank() {
		return openbank;
	}
	public void setOpenbank(String openbank) {
		this.openbank = openbank;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	
	public String getVerifytPeopson() {
		return verifytPeopson;
	}
	public void setVerifytPeopson(String verifytPeopson) {
		this.verifytPeopson = verifytPeopson;
	}
	public Date getVerifytime() {
		return verifytime;
	}
	public void setVerifytime(Date verifytime) {
		this.verifytime = verifytime;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Double getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Date getStartTwo() {
		return startTwo;
	}
	public void setStartTwo(Date startTwo) {
		this.startTwo = startTwo;
	}
	public Date getEndTwo() {
		return endTwo;
	}
	public void setEndTwo(Date endTwo) {
		this.endTwo = endTwo;
	}
	public String getDatestatus() {
		return datestatus;
	}
	public void setDatestatus(String datestatus) {
		this.datestatus = datestatus;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	
	
}
