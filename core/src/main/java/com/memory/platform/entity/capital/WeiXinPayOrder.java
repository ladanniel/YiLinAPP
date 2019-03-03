package com.memory.platform.entity.capital;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.memory.platform.entity.member.Account.PhonePlatform;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "xml")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = { 
		"return_code",
		"return_msg",
		"appid", 
		"mch_id",
		"device_info",
		"nonce_str",
		"sign",
		"result_code",
		"err_code", 
		"err_code_des",
		"trade_type",
		"prepay_id", 
		})

public class WeiXinPayOrder implements Serializable {
	private String return_code;  //返回状态码 SUCCESS/FAIL
	private String return_msg; //返回信息
	private String appid; //应用APPID
	private String mch_id; //商户号
	private String device_info; //设备号
	private String nonce_str; //随机字符串
	private String sign; //签名
	private String result_code; //业务结果
	private String err_code; //错误代码
	private String err_code_des; //错误代码描述
	private String trade_type; //交易类型
	private String prepay_id; //预支付交易会话标识
	@XmlTransient
	private long time_stamp; //时间戳，客户端需要
	@XmlTransient
	private String package_value; //package 默认"Sign=WXPay"
	@XmlTransient
	private PhonePlatform phonePlatform;
	public PhonePlatform getPhonePlatform() {
		return phonePlatform;
	}

	public void setPhonePlatform(PhonePlatform phonePlatform) {
		this.phonePlatform = phonePlatform;
	}

	public String getPackage_value() {
		return package_value;
	}

	public void setPackage_value(String package_value) {
		this.package_value = package_value;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	
	public long getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(long time_stamp) {
		this.time_stamp = time_stamp;
	}
}
