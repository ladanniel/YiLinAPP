package com.memory.platform.entity.solr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.memory.platform.entity.base.BaseEntity;

@Entity
@Table(name = "delete_record")
public class DeleteRecord extends BaseEntity {
	@Column(name = "rob_order_confirm_id")
	private String robOrderConfirmId;
	
	@Column(name = "goods_basic_id")
	private String goodsBasicId;
	@Column(name = "solr_name")
	private  String solr_name;
	@Column(name = "business_id")
	private  String business_id;

	public String getBusiness_id() {
		return business_id;
	}

	public String getGoodsBasicId() {
		return goodsBasicId;
	}

	public String getRobOrderConfirmId() {
		return robOrderConfirmId;
	}
	/*
	 * solr 服务名称
	 * */
	public String getSolr_name() {
		return solr_name;
	}
	
	
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public void setGoodsBasicId(String goodsBasicId) {
		this.goodsBasicId = goodsBasicId;
	}

	public void setRobOrderConfirmId(String robOrderConfirmId) {
		this.robOrderConfirmId = robOrderConfirmId;
	}

	public void setSolr_name(String solr_name) {
		this.solr_name = solr_name;
	}
}