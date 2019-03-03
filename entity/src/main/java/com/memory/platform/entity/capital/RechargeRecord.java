package com.memory.platform.entity.capital;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Paymentinterfac;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.AES;
import com.memory.platform.global.ThreeDESUtil;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午4:46:24 修 改 人： 日 期： 描 述： 用户充值记录实体 版 本 号：
 * V1.0
 */
@Entity
@Table(name = "mem_recharge_record")
public class RechargeRecord extends BaseEntity {

	private static final long serialVersionUID = -1551582392024245313L;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account; // 用户
	private Double money; // 充值金额
	private String bankName; // 充值银行
	private String remark; // 备注
	@Column(name = "trade_no")
	private String tradeNo; // 交易流水号
	@Column(name = "source_account")
	private String sourceAccount; // 来源账号
	@Column(name = "trade_account")
	private String tradeAccount; // 交易账号
	@Column(name = "trade_name")
	private String tradeName; // 交易名称
	@Transient
	private Date start;
	@Transient
	private Date end;
	@Column
	private String query_id;

	public String getTradeAccount() {
		String des = null;
		try {
			if (null == tradeAccount) {
				return tradeAccount;
			}
			des = AES.Decrypt(tradeAccount, null);
		} catch (Exception e) {
			throw new BusinessException("数据解密异常。");
		}
		return des;
	}

	public void setTradeAccount(String tradeAccount) {
		String des = null;
		try {
			if (null == tradeAccount) {
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

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "source_type")
	private String sourceType; // 来源类型

	public enum Status {
		waitProcess, success, failed
	}

	private Status status; // 状态 0:待处理 1:成功 2:失败

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	/**
	 * @return the query_id
	 */
	public String getQuery_id() {
		return query_id;
	}

	/**
	 * @param query_id
	 *            the query_id to set
	 */
	public void setQuery_id(String query_id) {
		this.query_id = query_id;
	}
}
