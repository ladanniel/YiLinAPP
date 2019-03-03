package com.memory.platform.entity.goods;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月6日 上午11:05:14 
* 修 改 人： 
* 日   期： 
* 描   述： 货物审核日志记录
* 版 本 号：  V1.0
 */
import com.memory.platform.entity.goods.GoodsBasic.Status;
@Entity
@Table(name = "goods_aut_log")
public class GoodsAutLog extends BaseEntity {

	private static final long serialVersionUID = 5951798759517563229L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="goods_basic_id", nullable = false)
	private GoodsBasic goodsBasic;   //货物基本信息
	private Status beforeStatus;   //审核前状态
	private Status afterStatus;  //审核后状态
	private String remark;  //审核信息说明
	@Column(name = "audit_person")
	private String auditPerson;  //审核人
	@Column(name = "audit_person_id")
	private String auditPersonId;   //审核人ID
	private String title;
	@Transient
	private Date start;
	@Transient
	private Date end;
	public GoodsBasic getGoodsBasic() {
		return goodsBasic;
	}
	public void setGoodsBasic(GoodsBasic goodsBasic) {
		this.goodsBasic = goodsBasic;
	}
	public Status getBeforeStatus() {
		return beforeStatus;
	}
	public void setBeforeStatus(Status beforeStatus) {
		this.beforeStatus = beforeStatus;
	}
	public Status getAfterStatus() {
		return afterStatus;
	}
	public void setAfterStatus(Status afterStatus) {
		this.afterStatus = afterStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAuditPerson() {
		return auditPerson;
	}
	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	public String getAuditPersonId() {
		return auditPersonId;
	}
	public void setAuditPersonId(String auditPersonId) {
		this.auditPersonId = auditPersonId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
