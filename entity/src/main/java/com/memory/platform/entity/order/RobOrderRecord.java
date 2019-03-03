package com.memory.platform.entity.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;

/**
 * 创 建 人： longqibo 日 期： 2016年6月6日 上午11:19:07 修 改 人： 日 期： 描 述： 抢单记录表 版 本 号： V1.0
 */
@Entity
@Table(name = "rob_order_record")
@DynamicUpdate(true)
public class RobOrderRecord extends BaseEntity {

	private static final long serialVersionUID = -1621049455099656460L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account; // 抢单人信息
	@Column(name = "rob_order_no", updatable = false)
	private String robOrderNo;// 抢单单号
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "goods_baice_id")
	private GoodsBasic goodsBasic; // 抢单货物信息
	@Column(name = "unit_price")
	private double unitPrice; // 所报单价
	private double weight; // 重量
	private double actualWeight; // 实际成交重量
	@Column(name = "total_price")
	private double totalPrice; // 总价
	@Column(name = "goods_types")
	private String goodsTypes; // 货物类型
	
	private Double deposit_level_money;//选择的押金水平
	
	// 0:提交申请 1:处理中 2:退回 3:成功 4:作废 5:撤回 6:结束 7：订单完结

	public enum Status {
		apply, dealing, back, success, scrap, withdraw, end, ordercompletion
	}

	private Status status; // 状态
	private String companyName; // 商户名称
	@Column(name = "robbed_company_id", updatable = false)
	private String robbedCompanyId; // 被抢人商户Id
	@Column(name = "robbed_account_id", updatable = false)
	private String robbedAccountId; // 被抢人用户Id
	@Column(name = "audit_person")
	private String auditPerson; // 审核人
	@Column(name = "audit_person_id")
	private String auditPersonId; // 审核人ID
	@Column(name = "audit_time")
	private Date auditTime; // 审核时间
	private String remark; // 审核信息说明
	@Version
	private Integer version; // 版本信息
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cancel_user_id")
	private Account cancelAccount; // 取消抢单人信息
	@Column(name = "cancel_remark")
	private String cancelRemark; // 取消原因
	@Column(name = "cancel_date")
	private Date cancelDate; // 取消时间
	@Column(name = "deposit_unit_price")
	private Double depositUnitPrice; // 所交押金单价
	@Transient
	private String robOrderRecordInfos; // 抢单货物详细信息JSON字符串
	@Transient
	private String deliveryAreaName; // 发货区域,用于查询
	@Transient
	private String consigneeAreaName;// 收获区域,用于查询
	@Transient
	private String[] goodsTypeId;// 货物类型ID,用于查询
	@Transient
	private Status[] status_serch;// 货物类型ID,用于查询

	@Transient
	private Integer[] statusIDs;// 货物类型ID,用于查询

	@Transient
	private Integer[] specialStatusIDs;// 特殊处理状态

	@Transient
	private String robbedCompanyName;// 被抢人商户名称

	@Transient
	private String turckUserId;// 拉货司机
	@Transient
	private String receiptUserId;// 回执人员ID

	@Transient
	private String transportNo;// 运输单号

	@Transient
	private Integer specialType;// 申请类型

	@Transient
	private String deliveryNo; // 货物单号

	@Transient
	private String userID; // 发货人id
	
	@Column(name="time_stamp")  
	private Date timeStamp;  //时间戳
	@Transient
	private  String searchKey; //查询key
	
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	// 新添加查询状态
	public static enum QueryStatus {
		wait, // 待确认 apply 申请,dealing 处理中,
		faild, // back 退回 ，scrap 作废,withdraw 撤回
		success, // success 货主确认成功
		end // end 车辆调配完毕
	}

	public double getDeposit_level_money() {
		return deposit_level_money;
	}

	public void setDeposit_level_money(double deposit_level_money) {
		this.deposit_level_money = deposit_level_money;
	}

	private static Map<QueryStatus, List<Status>> queryStatusMap = new HashMap<>();
	// 新添加查询状态
	@Transient
	private QueryStatus queryStatus;

	public QueryStatus getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(QueryStatus queryStatus) {
		this.queryStatus = queryStatus;
	}

	static {
		ArrayList<Status> arrStatus = new ArrayList<>(Arrays.asList(Status.apply, Status.dealing));
		queryStatusMap.put(QueryStatus.wait, arrStatus);
		arrStatus = new ArrayList<>(Arrays.asList(Status.back, Status.scrap, Status.withdraw));
		queryStatusMap.put(QueryStatus.faild, arrStatus);
		arrStatus = new ArrayList<>(Arrays.asList(Status.success));
		queryStatusMap.put(QueryStatus.success, arrStatus);
		arrStatus = new ArrayList<>(Arrays.asList(Status.end));
		queryStatusMap.put(QueryStatus.end, arrStatus);
	}

	public static List<Status> getStatusWithQueryStatus(QueryStatus status) {
		if (queryStatusMap.containsKey(status) == false)
			return null;
		return queryStatusMap.get(status);

	}

	public static QueryStatus getQueryStatusWithStatus(Status status) {
		for (QueryStatus queryStatus : queryStatusMap.keySet()) {
			List<Status> statusList = queryStatusMap.get(queryStatus);
			for (Status statusInfo : statusList) {
				if (statusInfo.equals(status)) {
					return queryStatus;
				}
			}
		}
		return QueryStatus.wait;

	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public Integer getSpecialType() {
		return specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}

	public Integer[] getSpecialStatusIDs() {
		return specialStatusIDs;
	}

	public void setSpecialStatusIDs(Integer[] specialStatusIDs) {
		this.specialStatusIDs = specialStatusIDs;
	}

	public String getTransportNo() {
		return transportNo;
	}

	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}

	public String getRobbedCompanyName() {
		return robbedCompanyName;
	}

	public void setRobbedCompanyName(String robbedCompanyName) {
		this.robbedCompanyName = robbedCompanyName;
	}

	public String getTurckUserId() {
		return turckUserId;
	}

	public void setTurckUserId(String turckUserId) {
		this.turckUserId = turckUserId;
	}

	public Integer[] getStatusIDs() {
		return statusIDs;
	}

	public void setStatusIDs(Integer[] statusIDs) {
		this.statusIDs = statusIDs;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public GoodsBasic getGoodsBasic() {
		return goodsBasic;
	}

	public void setGoodsBasic(GoodsBasic goodsBasic) {
		this.goodsBasic = goodsBasic;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getGoodsTypes() {
		return goodsTypes;
	}

	public void setGoodsTypes(String goodsTypes) {
		this.goodsTypes = goodsTypes;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * @return the robOrderRecordInfos
	 */
	public String getRobOrderRecordInfos() {
		return robOrderRecordInfos;
	}

	/**
	 * @param robOrderRecordInfos
	 *            the robOrderRecordInfos to set
	 */
	public void setRobOrderRecordInfos(String robOrderRecordInfos) {
		this.robOrderRecordInfos = robOrderRecordInfos;
	}

	/**
	 * @return the deliveryAreaName
	 */
	public String getDeliveryAreaName() {
		return deliveryAreaName;
	}

	/**
	 * @param deliveryAreaName
	 *            the deliveryAreaName to set
	 */
	public void setDeliveryAreaName(String deliveryAreaName) {
		this.deliveryAreaName = deliveryAreaName;
	}

	/**
	 * @return the consigneeAreaName
	 */
	public String getConsigneeAreaName() {
		return consigneeAreaName;
	}

	/**
	 * @param consigneeAreaName
	 *            the consigneeAreaName to set
	 */
	public void setConsigneeAreaName(String consigneeAreaName) {
		this.consigneeAreaName = consigneeAreaName;
	}

	/**
	 * @return the goodsTypeId
	 */
	public String[] getGoodsTypeId() {
		return goodsTypeId;
	}

	/**
	 * @param goodsTypeId
	 *            the goodsTypeId to set
	 */
	public void setGoodsTypeId(String[] goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	/**
	 * @return the status_serch
	 */
	public Status[] getStatus_serch() {
		return status_serch;
	}

	/**
	 * @param status_serch
	 *            the status_serch to set
	 */
	public void setStatus_serch(Status[] status_serch) {
		this.status_serch = status_serch;
	}

	/**
	 * @return the cancelAccount
	 */
	public Account getCancelAccount() {
		return cancelAccount;
	}

	/**
	 * @param cancelAccount
	 *            the cancelAccount to set
	 */
	public void setCancelAccount(Account cancelAccount) {
		this.cancelAccount = cancelAccount;
	}

	/**
	 * @return the cancelRemark
	 */
	public String getCancelRemark() {
		return cancelRemark;
	}

	/**
	 * @param cancelRemark
	 *            the cancelRemark to set
	 */
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	/**
	 * @return the cancelDate
	 */
	public Date getCancelDate() {
		return cancelDate;
	}

	/**
	 * @param cancelDate
	 *            the cancelDate to set
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public double getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}

	/**
	 * @return the robOrderNo
	 */
	public String getRobOrderNo() {
		return robOrderNo;
	}

	/**
	 * @param robOrderNo
	 *            the robOrderNo to set
	 */
	public void setRobOrderNo(String robOrderNo) {
		this.robOrderNo = robOrderNo;
	}

	/**
	 * @return the robbedCompanyId
	 */
	public String getRobbedCompanyId() {
		return robbedCompanyId;
	}

	/**
	 * @param robbedCompanyId
	 *            the robbedCompanyId to set
	 */
	public void setRobbedCompanyId(String robbedCompanyId) {
		this.robbedCompanyId = robbedCompanyId;
	}

	/**
	 * @return the robbedAccountId
	 */
	public String getRobbedAccountId() {
		return robbedAccountId;
	}

	/**
	 * @param robbedAccountId
	 *            the robbedAccountId to set
	 */
	public void setRobbedAccountId(String robbedAccountId) {
		this.robbedAccountId = robbedAccountId;
	}

	/**
	 * @return the receiptUserId
	 */
	public String getReceiptUserId() {
		return receiptUserId;
	}

	/**
	 * @param receiptUserId
	 *            the receiptUserId to set
	 */
	public void setReceiptUserId(String receiptUserId) {
		this.receiptUserId = receiptUserId;
	}

	/**
	 * @return the depositUnitPrice
	 */
	public Double getDepositUnitPrice() {
		return depositUnitPrice;
	}

	/**
	 * @param depositUnitPrice
	 *            the depositUnitPrice to set
	 */
	public void setDepositUnitPrice(Double depositUnitPrice) {
		this.depositUnitPrice = depositUnitPrice;
	}

}
