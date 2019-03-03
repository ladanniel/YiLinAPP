package com.memory.platform.entity.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.base.BaseEntity;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 上午10:08:50 
* 修 改 人： 
* 日   期： 
* 描   述： 货物类型
* 版 本 号：  V1.0
 */

@Entity
@Table(name = "goods_type")
@DynamicUpdate(true)
public class GoodsType extends BaseEntity  implements Comparable<GoodsType>{

	private static final long serialVersionUID = 2337786905146098640L;
	
	private String name;  //名称
	@Column(name = "prent_id")
	private String prentId;   //父级id
	private Integer  level;//所属等级默认为1
	private String descs;//备注
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrentId() {
		return prentId;
	}
	public void setPrentId(String prentId) {
		this.prentId = prentId;
	}
	 
	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * @return the descs
	 */
	public String getDescs() {
		return descs;
	}
	/**
	 * @param descs the descs to set
	 */
	public void setDescs(String descs) {
		this.descs = descs;
	}
	/*  
	 *  compareTo
	 */
	@Override
	public int compareTo(GoodsType arg0) {
		// TODO Auto-generated method stub
		return  this.getLevel().compareTo(arg0.getLevel());
	}

	
}
