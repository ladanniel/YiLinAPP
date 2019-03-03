package com.memory.platform.entity.capital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.sys.Paymentinterfac;
/**
 * 
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午5:02:24 
* 修 改 人： 
* 日   期： 
* 描   述： 对公账户实体对象
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_publicbankcard")
public class PublicBankCard extends BaseEntity {

	private static final long serialVersionUID = -6192261249042068825L;

	private String number;     //卡号
	private String bankname;    //银行名称
	private Paymentinterfac paymentinterfac;   //充值平台
	private Integer version = 0;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "paymentinterface_id",insertable = false, updatable = false)
	public Paymentinterfac getPaymentinterfac() {
		return paymentinterfac;
	}
	public void setPaymentinterfac(Paymentinterfac paymentinterfac) {
		this.paymentinterfac = paymentinterfac;
	}
	@Version
	@Column(name = "version", nullable = false, length=10)
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
