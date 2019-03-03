package com.memory.platform.entity.goods;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月6日 上午10:52:21 
* 修 改 人： 
* 日   期： 
* 描   述： 货物详细信息表
* 版 本 号：  V1.0
 */

@Entity
@Table(name = "goods_detail")
@DynamicUpdate(true)
public class GoodsDetail extends BaseEntity {

	private static final long serialVersionUID = -782454094279406925L;

	@OneToOne(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST})
	@JoinColumn(name="goods_baice_id", nullable = false)
	private GoodsBasic goodsBasic;  //货物基本信息
	@Column(name = "goods_name")
	private String goodsName;  //名称
	@OneToOne(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST} )
	@JoinColumn(name="goods_type_id", nullable = false)
	private GoodsType goodsType;  //货物类型
	private double weight;   //重量
	@Column(name = "embark_weight")
	private double embarkWeight = 0;   //已装载重量
	private double length;  //长度
	private double height;  //高度
	private double diameter; //直径
	@Column(name = "wing_width")
	private double wingWidth; //翼宽
	@Version
	private Integer version;  //版本号
	@Transient
	private String goodsTypeId; // 货物类型ID
	@Transient
	private double actualWeight; //实际重量
	@Transient
	private double surplusWeight; //实际重量
	private String specifica;  //规格
	//加入goodsname_id
	private String goods_name_id;
	public String getGoods_name_id() {
		return goods_name_id;
	}
	public void setGoods_name_id(String goods_name_id) {
		this.goods_name_id = goods_name_id;
	}
	public String getSpecifica() {
		return specifica;
	}
	public void setSpecifica(String specifica) {
		this.specifica = specifica;
	}
	public GoodsBasic getGoodsBasic() {
		return goodsBasic;
	}
	public void setGoodsBasic(GoodsBasic goodsBasic) {
		this.goodsBasic = goodsBasic;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public GoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	 
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the diameter
	 */
	public double getDiameter() {
		return diameter;
	}
	/**
	 * @param diameter the diameter to set
	 */
	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}
	/**
	 * @return the wingWidth
	 */
	public double getWingWidth() {
		return wingWidth;
	}
	/**
	 * @param wingWidth the wingWidth to set
	 */
	public void setWingWidth(double wingWidth) {
		this.wingWidth = wingWidth;
	}
	/**
	 * @return the goodsTypeId
	 */
	public String getGoodsTypeId() {
		if(goodsType != null &&  goodsType.getId() != null){
			return goodsType.getId();
		}else{
			return goodsTypeId;
		}
	}
	/**
	 * @param goodsTypeId the goodsTypeId to set
	 */
	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	/**
	 * @return the actualWeight
	 */
	public double getActualWeight() {
		return actualWeight;
	}
	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	public double getEmbarkWeight() {
		return embarkWeight;
	}
	public void setEmbarkWeight(double embarkWeight) {
		this.embarkWeight = embarkWeight;
	}
	/**
	 * @return the surplusWeight
	 */
	public double getSurplusWeight() {
		return this.weight - this.embarkWeight;
	}
	/**
	 * @param surplusWeight the surplusWeight to set
	 */
	public void setSurplusWeight(double surplusWeight) {
		this.surplusWeight = surplusWeight;
	}
	
}
