package com.memory.platform.entity.truck;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月15日 上午10:53:14 
* 修 改 人： 车辆人员分配 
* 日   期： 
* 描   述： 
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_track_distribution")
public class TruckDistribution implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	@Column(name="distribution_date")
	private Date distributionDate;//分配时间
	
	@Column(name="distribution_user_id")
	private String  distributionUserId;//分配人ID
	
	@Column(name="distribution_user_name")
	private String  distributionUserName;//分配人姓名
	
	@Column(name="operation_user_id")
	private String  operationUserId;//操作人ID
	
	@Column(name="operation_user_name")
	private String  operationUserName;//操作人姓名
	
	@Column(name="track_id")
	private String  trackId;//车辆ID

	@Transient
	private String search; // 搜索关键字

	/**
	 * @return the distributionDate
	 */
	public Date getDistributionDate() {
		return distributionDate;
	}

	/**
	 * @param distributionDate the distributionDate to set
	 */
	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}

	/**
	 * @return the distributionUserId
	 */
	public String getDistributionUserId() {
		return distributionUserId;
	}

	/**
	 * @param distributionUserId the distributionUserId to set
	 */
	public void setDistributionUserId(String distributionUserId) {
		this.distributionUserId = distributionUserId;
	}

	/**
	 * @return the distributionUserName
	 */
	public String getDistributionUserName() {
		return distributionUserName;
	}

	/**
	 * @param distributionUserName the distributionUserName to set
	 */
	public void setDistributionUserName(String distributionUserName) {
		this.distributionUserName = distributionUserName;
	}

	/**
	 * @return the operationUserId
	 */
	public String getOperationUserId() {
		return operationUserId;
	}

	/**
	 * @param operationUserId the operationUserId to set
	 */
	public void setOperationUserId(String operationUserId) {
		this.operationUserId = operationUserId;
	}

	/**
	 * @return the operationUserName
	 */
	public String getOperationUserName() {
		return operationUserName;
	}

	/**
	 * @param operationUserName the operationUserName to set
	 */
	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}

	/**
	 * @return the trackId
	 */
	public String getTrackId() {
		return trackId;
	}

	/**
	 * @param trackId the trackId to set
	 */
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	
	
}











