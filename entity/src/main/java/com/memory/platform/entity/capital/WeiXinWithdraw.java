package com.memory.platform.entity.capital;
/*
 * 微信转账
 * */
public class WeiXinWithdraw {
	String mch_appid;
	public String getMch_appid() {
		return mch_appid;
	}
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCheck_name() {
		return check_name;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	String mchid;
	String nonce_str;
	String partner_trade_no;
	String openid;
	String check_name;
	int amount;
	String desc;
	String  spbill_create_ip;
	String sign;
	public String toPayloadXml() {
		return "<xml>"  +
				String.format("<mch_appid>%s</mch_appid>", this.getMch_appid()) +
				String.format("<mchid>%s</mchid>",this.getMchid()) +
				String.format("<nonce_str>%s</nonce_str>",this.getNonce_str()) +
				String.format("<partner_trade_no>%s</partner_trade_no>",this.getPartner_trade_no())+
				String.format("<openid>%s</openid>",this.getOpenid()) +
				String.format("<check_name>%s</check_name>",this.getCheck_name()) +
				String.format("<amount>%d</amount>",this.getAmount()) +
				String.format("<desc>%s</desc>",this.getDesc()) +
				String.format("<spbill_create_ip>%s</spbill_create_ip>",this.getSpbill_create_ip()) +
				String.format("<sign>%s</sign>",this.getSign()) +
				 "</xml>";
		
	}
 
}
