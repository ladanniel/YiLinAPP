package com.memory.platform.entity.capital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午4:01:03 
* 修 改 人： 
* 日   期： 
* 描   述： 资金账户信息表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_capital_account")
public class CapitalAccount extends BaseEntity {

	private static final long serialVersionUID = 9193823662742110206L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id", nullable = false)
	private Account account;   //用户
	private Double total;    //总资产
	private Double avaiable;    //可用余额
	private Double frozen;    //冻结资金
	private Double totalrecharge;    //总充值
	private Double totalcash;    //总提现
	@Version
	@Column(name = "version", nullable = false, length=10)
	private Integer version = 0;
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getAvaiable() {
		return avaiable;
	}
	public void setAvaiable(Double avaiable) {
		this.avaiable = avaiable;
	}
	public Double getFrozen() {
		return frozen;
	}
	public void setFrozen(Double frozen) {
		this.frozen = frozen;
	}
	public Double getTotalrecharge() {
		return totalrecharge;
	}
	public void setTotalrecharge(Double totalrecharge) {
		this.totalrecharge = totalrecharge;
	}
	public Double getTotalcash() {
		return totalcash;
	}
	public void setTotalcash(Double totalcash) {
		this.totalcash = totalcash;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
