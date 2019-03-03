package com.memory.platform.entity.capital;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;

/**
 * 创 建 人： aiqiwu 日 期： 2017年4月7日 上午11:00:00 修 改 人： 日 期： 描 述： 手机跳转充值记录实体 版 本 号：
 * V1.0
 */
@Entity
@Table(name = "mem_recharge_direct")
public class RechargeDirect extends BaseEntity {
	private static final long serialVersionUID = -8178336307811686L;
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account; // 用户
	private Double money; // 充值金额
	private String remark; // 备注
	@Column(name = "trade_no")
	private String tradeNo; // 交易流水号
	private Status status; // 状态 0:待处理 1:成功 2:失败
	@Column(name = "order_id")
	private String orderId; // 订单号
	@Column(name = "txn_time")
	private String txnTime; // 交易时间
	@Column(name="response_data")
	private String responseData;

	public enum Status {
		waitProcess, success, failed
	}
	
	public String getResponseData(){
		return responseData;
	}
	
	public void setResponseData(String responseData){
		this.responseData=responseData;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
