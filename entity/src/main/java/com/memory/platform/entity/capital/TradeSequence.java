package com.memory.platform.entity.capital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.AES;
import com.memory.platform.global.ThreeDESUtil;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月15日 下午4:34:09 
* 修 改 人： 
* 日   期： 
* 描   述： 交易序列信息表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_trade_sequence")
public class TradeSequence extends BaseEntity{

	private static final long serialVersionUID = 531654125217858907L;
	
	@Column(name = "source_account")
	private String sourceAccount;  //来源账号
	@Column(name = "source_id")
	private String sourceId;  //来源账号
	@Column(name = "source_type")
	private String sourcType;  //来源类型
	@ManyToOne
	@JoinColumn(name = "account_id",updatable = false)
	private Account account;
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@Column(name = "sequence_no")
	private Integer sequenceNo;
	@Version
	private Integer version;
	@Column(name = "has_recharge")
	private Boolean hasRecharge;
	@Column(name = "has_cash")
	private Boolean hasCash;
	@Column(name = "has_transfer")
	private Boolean hasTransfer;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Integer getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
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
	public Boolean getHasRecharge() {
		return hasRecharge;
	}
	public void setHasRecharge(Boolean hasRecharge) {
		this.hasRecharge = hasRecharge;
	}
	public Boolean getHasCash() {
		return hasCash;
	}
	public void setHasCash(Boolean hasCash) {
		this.hasCash = hasCash;
	}
	public Boolean getHasTransfer() {
		return hasTransfer;
	}
	public void setHasTransfer(Boolean hasTransfer) {
		this.hasTransfer = hasTransfer;
	}
	

}
