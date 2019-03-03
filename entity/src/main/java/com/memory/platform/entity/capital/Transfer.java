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
import com.memory.platform.global.ThreeDESUtil;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月4日 下午8:03:06 
* 修 改 人： 
* 日   期： 
* 描   述： 转账实体
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_transfer")
public class Transfer extends BaseEntity {

	private static final long serialVersionUID = -1631730845626365734L;
	
	@Column(name = "source_account")
	private String sourceAccount;  //来源账号
	@Column(name = "source_type")
	private String sourcType;  //来源类型
	@ManyToOne
	@JoinColumn(name="account_id", nullable = false)
	private Account account;   //发起转账用户
	@ManyToOne
	@JoinColumn(name="toaccount_id", nullable = false)
	private Account toAccount;   //收账帐户
	@ManyToOne
	@JoinColumn(name="type_id", nullable = false)
	private TransferType transferType;  //转账类型
	@Column
	private double money;  //转账金额
	@Column(name = "trade_account")
	private String tradeAccount;  //交易账号
	@Column(name = "trade_name")
	private String tradeName;   //交易名称
	@Transient
	private Date start;
	@Transient
	private Date end;
	
	
	public String getTradeAccount() {
		String des = null;
		try {
			if(null == tradeAccount){
				return tradeAccount;
			}
			des = AES.Decrypt(tradeAccount); 
		} catch (Exception e) {
			throw new BusinessException("数据解密异常。");
		}		
		return des;
	}
	public void setTradeAccount(String tradeAccount) {
		String des = null;
		try {
			if(null == tradeAccount){
				this.tradeAccount = tradeAccount;
				return;
			}
			des = AES.Encrypt(tradeAccount);
		} catch (Exception e) {
			throw new BusinessException("数据加密异常。");
		}
		this.tradeAccount = des;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getSourceAccount() {
		String des = null;
		try {
			des = AES.Decrypt(sourceAccount);
		} catch (Exception e) {
			throw new BusinessException("数据解密异常。");
		}		
		return des;  
	}
	public void setSourceAccount(String sourceAccount) {
		String des = null;
		try {
			des = AES.Encrypt(sourceAccount);
		} catch (Exception e) {
			throw new BusinessException("数据加密异常。");
		}
		this.sourceAccount = des;
	}
	public String getSourcType() {
		return sourcType;
	}
	public void setSourcType(String sourcType) {
		this.sourcType = sourcType;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(name = "trade_no")
	private String tradeNo;  //交易流水号
	//0:成功   1:失败
	public enum Status{
		success,failed
	}
	@Column
	private Status status;   //状态
	@Column
	private String remark;   //备注
	@Version
	private Integer version;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Account getToAccount() {
		return toAccount;
	}
	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public TransferType getTransferType() {
		return transferType;
	}
	public void setTransferType(TransferType transferType) {
		this.transferType = transferType;
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
	
}
