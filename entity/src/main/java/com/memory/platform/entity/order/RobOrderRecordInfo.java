package com.memory.platform.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月6日 上午11:37:37 
* 修 改 人： 
* 日   期： 
* 描   述： 抢单信息详细表
* 版 本 号：  V1.0
 */
import com.memory.platform.entity.goods.GoodsDetail;
@Entity
@Table(name = "rob_order_record_info")
public class RobOrderRecordInfo extends BaseEntity {

	private static final long serialVersionUID = -3623819806632112590L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="rob_order_record_id")
	private RobOrderRecord robOrderRecord;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="goods_detail_id")
	private GoodsDetail goodsDetail;
	private double weight;   //抢单重量
	@Column(name = "actual_weight")
	private double actualWeight;  //实际重量
	@Column(name = "original_weight")
	private double originalWeight;  //原有重量
	@Column(name = "stock_id")
	private String stockId;   //装货车辆id
	@Column(name = "is_parent")
	private Boolean parent;   //是否父级
	@Column(name = "is_split")
	private Boolean split;   //是否拆分
	@Column(name = "parent_id")
	private String parentId;   //拆分货物的父级ID
	@Version
	private Integer version;   // 版本号
	@Transient
	private String goodsDetailId; // 抢单货物详细Id
	@Transient
	private String stockName;   //装货车辆id
	public RobOrderRecord getRobOrderRecord() {
		return robOrderRecord;
	}
	public void setRobOrderRecord(RobOrderRecord robOrderRecord) {
		this.robOrderRecord = robOrderRecord;
	}
	public GoodsDetail getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(GoodsDetail goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the goodsDetailId
	 */
	public String getGoodsDetailId() {
		return goodsDetailId;
	}
	/**
	 * @param goodsDetailId the goodsDetailId to set
	 */
	public void setGoodsDetailId(String goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
		GoodsDetail goodsDetail = new GoodsDetail();
		goodsDetail.setId(goodsDetailId);
		this.goodsDetail = goodsDetail;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the parent
	 */
	public Boolean isParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Boolean parent) {
		this.parent = parent;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the split
	 */
	public Boolean isSplit() {
		return split;
	}
	/**
	 * @param split the split to set
	 */
	public void setSplit(Boolean split) {
		this.split = split;
	}
	/**
	 * @return the originalWeight
	 */
	public double getOriginalWeight() {
		return originalWeight;
	}
	/**
	 * @param originalWeight the originalWeight to set
	 */
	public void setOriginalWeight(double originalWeight) {
		this.originalWeight = originalWeight;
	}
	/**
	 * @return the stockName
	 */
	public String getStockName() {
		return stockName;
	}
	/**
	 * @param stockName the stockName to set
	 */
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	/**
	 * @return the parent
	 */
	public Boolean getParent() {
		return parent;
	}
	/**
	 * @return the split
	 */
	public Boolean getSplit() {
		return split;
	}
	 
	
}
