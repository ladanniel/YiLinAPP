package com.memory.platform.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.bind.ServletRequestBindingException;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月31日 下午1:47:15 
* 修 改 人： 
* 日   期： 
* 描   述： 短信发送记录表 
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_send_record")
public class SendRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7015279771007659381L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="send_message_id", nullable = false)
	private SendMessage sendMessage;   //短信接口
	@Column(name = "send_point")
	private String sendPoint;  //短信发送切入点全路径
	private String content;  //发送文本内容
	private String title;  //title
	private String  push_client_id; //jClientID
	
	public String getPush_client_id() {
		return push_client_id;
	}
	public void setPush_client_id(String push_client_id) {
		this.push_client_id = push_client_id;
	}

	private Account.PhonePlatform phone_platform;
 
	public Account.PhonePlatform getPhone_platform() {
		return phone_platform;
	}
	public void setPhone_platform(Account.PhonePlatform phone_platform) {
		this.phone_platform = phone_platform;
	}
	 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	//0失败  1成功 2等待发送
	public enum Status{
		fail,success,waitSend
	}
	private Status status;  //状态
	private String phone;  //接受者手机号码
	private String push_id;//pushID
	public String getPush_id() {
		return push_id;
	}
	public void setPush_id(String push_id) {
		this.push_id = push_id;
	}
	//发送类型 短信和推送
	public enum SendType {
		sms,
		push
	}
	
	private String send_user_id;
	public String getSend_user_id() {
		return send_user_id;
	}
	public void setSend_user_id(String send_user_id) {
		this.send_user_id = send_user_id;
	}
	public String getReceive_user_id() {
		return receive_user_id;
	}
	public void setReceive_user_id(String receive_user_id) {
		this.receive_user_id = receive_user_id;
	}
	public String getSend_user_name() {
		return send_user_name;
	}
	public void setSend_user_name(String send_user_name) {
		this.send_user_name = send_user_name;
	}

	private String receive_user_id;
	private String send_user_name;
	@Column
	private String extend_data;
	@Column
	private  SendType send_type; 
	@Column //是否已经被用户取过
	private boolean is_received;
 
	//推送返回消息
	private String push_msg;
	public String getPush_msg() {
		return push_msg;
	}
	public void setPush_msg(String push_msg) {
		this.push_msg = push_msg;
	}
	public SendType getSend_type() {
		return send_type;
	}
	public void setSend_type(SendType send_type) {
		this.send_type = send_type;
	}
	public boolean getIs_received() {
		return is_received;
	}
	public void setIs_received(boolean is_received) {
		this.is_received = is_received;
	}
	public String getExtend_data() {
		return extend_data;
	}
	public void setExtend_data(String extend_data) {
		this.extend_data = extend_data;
	}
	
	@Transient
	private Date start;
	@Transient
	private Date end;
	public SendMessage getSendMessage() {
		return sendMessage;
	}
	public void setSendMessage(SendMessage sendMessage) {
		this.sendMessage = sendMessage;
	}
	public String getSendPoint() {
		return sendPoint;
	}
	public void setSendPoint(String sendPoint) {
		this.sendPoint = sendPoint;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	
}
