package com.memory.platform.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午5:07:54 
* 修 改 人： 
* 日   期： 
* 描   述： 支付平台接口信息
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_paymentinterfac")
public class Paymentinterfac extends BaseEntity {

	private static final long serialVersionUID = 1938279660404464495L;
	
	private String merchant_id;  //商户号
	private String key;  //商户key
	private Double recharge_fee;  //费率
	private String return_url;  //回调地址
	@Version
	@Column(name = "version")
	private Integer version = 0;
	private String notice_url;  //通知地址
	private String chartset;   //编码方式
	private String name;  //接口名称
	private String signtype;   //加密方式
	private Integer hasdefault = 0;  //是否默认
	private Integer has_enable_single = 0;  //是否直调
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Double getRecharge_fee() {
		return recharge_fee;
	}
	public void setRecharge_fee(Double recharge_fee) {
		this.recharge_fee = recharge_fee;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getNotice_url() {
		return notice_url;
	}
	public void setNotice_url(String notice_url) {
		this.notice_url = notice_url;
	}
	public String getChartset() {
		return chartset;
	}
	public void setChartset(String chartset) {
		this.chartset = chartset;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSigntype() {
		return signtype;
	}
	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}
	public Integer getHasdefault() {
		return hasdefault;
	}
	public void setHasdefault(Integer hasdefault) {
		this.hasdefault = hasdefault;
	}
	public Integer getHas_enable_single() {
		return has_enable_single;
	}
	public void setHas_enable_single(Integer has_enable_single) {
		this.has_enable_single = has_enable_single;
	}
	

}
