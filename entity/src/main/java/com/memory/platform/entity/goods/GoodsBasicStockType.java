package com.memory.platform.entity.goods;
//

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月23日 上午10:22:07 
* 修 改 人： 
* 日   期： 
* 描   述： 货物车辆关联表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "goods_basic_stock_type")
public class GoodsBasicStockType implements Serializable{

	private static final long serialVersionUID = -577122961854675377L;
	
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	@Column(name = "goods_basic_id")
	private String goodsBasicId;
	@Column(name ="stock_type_id")
	private String stockTypeId;
	public String getGoodsBasicId() {
		return goodsBasicId;
	}
	public void setGoodsBasicId(String goodsBasicId) {
		this.goodsBasicId = goodsBasicId;
	}
	public String getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
