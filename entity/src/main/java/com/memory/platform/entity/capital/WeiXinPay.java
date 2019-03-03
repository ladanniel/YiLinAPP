package com.memory.platform.entity.capital;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.memory.platform.core.AppUtil;
import com.memory.platform.core.BeanKVO;
import com.memory.platform.core.XmlCDataString;
import com.memory.platform.core.BeanKVO.EnumPropertyCallBack;
import com.memory.platform.core.XmlCDataInt;
import com.memory.platform.global.Config;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;

@XmlAccessorType(XmlAccessType.FIELD)    
//XML文件中的根标识    
@XmlRootElement(name = "xml")    
//控制JAXB 绑定类中属性和字段的排序    
@XmlType(propOrder = {     
     "appid",    
     "mch_id",
     "device_info",     
     "nonce_str",     
     "sign",
     "sign_type",
     "body",
     "detail",
     "attach",
     "out_trade_no",
     "fee_type",
     "total_fee",
     "spbill_create_ip",
     "time_start",
     "time_expire",
     "goods_tag",
     "notify_url",
     "trade_type",
     "limit_pay",
     "scene_info",
})    

public class WeiXinPay implements Serializable{
	private String appid; //应用ID
	private String mch_id; //商户号
	private String device_info; //设备号
	private String nonce_str; //随机字符串
	private String sign; //签名
	private String sign_type; //签名类型 目前支持HMAC-SHA256和MD5，默认为MD5
	private String body; //商品描述
	private String detail; //商品详情
	private String attach; //附加数据
	private String out_trade_no; //商户订单号
	private String fee_type; //货币类型
	private int total_fee; //总金额  单位：分
	private String spbill_create_ip; //终端IP
	private String time_start; //交易起始时间
	private String time_expire; //交易结束时间
	private String goods_tag; //订单优惠标记
	private String notify_url; //通知地址
	private String trade_type; //交易类型
	private String limit_pay; //指定支付方式
	private String scene_info; //场景信息

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
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getLimit_pay() {
		return limit_pay;
	}
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
	public String getScene_info() {
		return scene_info;
	}
	public void setScene_info(String scene_info) {
		this.scene_info = scene_info;
	}
	public String toSignStr() {
		final Map<String,Object> map = new HashMap<String,Object>();
		final List<String> orderKeys = new ArrayList<String>();
		BeanKVO.enumProperty(this, new EnumPropertyCallBack() {
			
			public boolean callback(String propertyName, Object value) {
			 
				if(propertyName.equals( "class")|| propertyName.equals( "toSignStr")) {
					return true;
				}
				 if(value != null) {
					 map.put(propertyName, value);
					 orderKeys.add(propertyName);
				 }
				return true;
			}
		});
		 
		Collections.sort(orderKeys,new Comparator<String>() {

			public int compare(String o1, String o2) {
				 
				return o1.compareTo(o2);
			}
		});
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String key : orderKeys) {
			Object v = map.get(key);
			if(i!=0 ) {
				sb.append("&");
			}
			sb.append(key+"=" +v.toString());
			
			i ++;
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		WeiXinPay	payInfo = new  WeiXinPay();
//		payInfo.setAppid("wxc09f5291672b76c9");
//		payInfo.setMch_id(Config.weiXinAndroidDriverMchId);
//		payInfo.setNonce_str(StringUtil.getRandomStr(32));
//		payInfo.setBody("小镖人充值");
//		String txnTime = DateUtil.timeNo();
//		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
//		payInfo.setOut_trade_no(orderId);
//		payInfo.setNotify_url("http://www.qibogu.com/yilin_logistics_app/app/capital/weixinpaycallback.do");
//		payInfo.setTrade_type("APP");
//		String ret = payInfo.toSignStr();
//		ret += ret + "&key=" + Config.weiXinAndroidDriverKey;
//		System.out.println(ret);
//		System.out.println(AppUtil.md5(ret));
	}
}
