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
* 日    期： 2016年4月26日 下午4:18:35 
* 修 改 人： 
* 日   期： 
* 描   述： 资金记录实体对象
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "mem_money_record")
public class MoneyRecord extends BaseEntity {

	private static final long serialVersionUID = 2370334223004759693L;
	
	@ManyToOne
	@JoinColumn(name = "account_id",updatable = false)
	private Account account;   //用户
	private Double money;   //消费金额
	private String remark;  //备注信息
	//0:充值  1:提现  2:转账  3:手续费   4:消费  5:冻结   6:收款   7:其它  8:仲裁赔付   9：解冻  10:运输款
	public enum Type{
		recharge,cash,transfer,fee,consume,frozen,income,other,paid,thaw,transportSection
	}
	private Type type;
	@Version
	@Column(name = "version", nullable = false, length=10)
	private Integer version = 0;
	@Column(name = "trade_account")
	private String tradeAccount;  //交易账号
	@Column(name = "trade_name")
	private String tradeName;   //交易名称
	@Transient
	private Date startTime;
	@Transient
	private Date endTime;
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
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	private String operator;   //操作人
	@Column(name = "source_account")
	private String sourceAccount; //来源账号
	@Column(name = "source_type")
	private String sourceType;  //来源类型
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
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
