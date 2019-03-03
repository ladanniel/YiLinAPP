package com.memory.platform.entity.goods;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月6日 上午10:19:19 
* 修 改 人： 
* 日   期： 
* 描   述： 货物基本信息
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "goods_basic")
@DynamicUpdate(true)
public class GoodsBasic extends BaseEntity {

	//0:保存  1:申请发货  2:锁定处理－正在处理  3:退回  4:通过   5:作废
	public enum Status{
		save,apply,lock,back,success,scrap
	}
    private static final long serialVersionUID = 461200521728321315L;
	/**发货人信息**/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id", nullable = false)
	private Account account;  //所属用户
	@Column(name = "delivery_no")
	private String deliveryNo;//发货单号
	@Column(name = "company_id")
	private String companyId;  //所属商户ID
	@Column(name = "delivery_name")
	private String deliveryName;   //发货人姓名
	@Column(name = "delivery_mobile")
	private String deliveryMobile;  //发货人联系电话
	@Column(name = "delivery_email")
	private String deliveryEmail;   //发货人email
	@Column(name = "delivery_area_name")
	private String deliveryAreaName;   //发货区域名称
	@Column(name = "delivery_area_id")
	private String deliveryAreaId;   //发货区域Id
	@Column(name = "delivery_address")
	private String deliveryAddress;  //发货详细地址地址
	@Column(name = "delivery_coordinate")
	private String deliveryCoordinate;   //发货人坐标
	
	@Transient
	private boolean  saveDelivery; // 是否保存发货人信息
	/**发货人信息**/
	/**收货人信息**/
	@Column(name = "consignee_name")
	private String consigneeName;  //收货人姓名
	@Column(name = "consignee_mobile")
	private String consigneeMobile;  //收货人联系电话
	@Column(name = "consignee_email")
	private String consigneeEmail;   //收货人email
	@Column(name = "consignee_area_name")
	private String consigneeAreaName;   //收货区域名称
	@Column(name = "consignee_area_id")
	private String consigneeAreaId;   //收货区域ID
	
	@Column(name = "consignee_address")
	private String consigneeAddress;   //收货人详细地址 区域＋地址   贵州省贵阳市南明区花果园金融街22号
	
	@Column(name = "consignee_coordinate")
	private String consigneeCoordinate;   //收货人坐标
	
	/**收货人信息**/
	@Transient
	private boolean  saveConsignee; // 是否保存收货人信息

	/**收货人信息**/
	@Transient
	private boolean saveConsignor; //是否保存发货人信息
	@Column(name = "map_kilometer")
	private String mapKilometer;   //公里数
	
	@Column(name = "release_time",updatable = false)
	private Date releaseTime;   //发布时间
	@Column(name = "finite_time")
	private Date finiteTime;   //有效时间
	
	@Transient
	private String  finiteTimeStr; // 有效时间字符串
	
	@Column(name = "is_long_time")
	private boolean longTime;  //是否长期
	private boolean hasLock = false;   //是否锁定
	@Column(name = "on_line")
	private Boolean onLine = false;//是否上线
	private Status status;  //状态
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="goods_type_id", nullable = false)
	private GoodsType goodsType;  //货物类型
	@Column(name = "unit_price")
	private double unitPrice;   //单价
	@Column(name = "total_price")
	private double totalPrice;   //总价
	@Column(name = "total_weight")
	private double totalWeight;   //总重量
	@Column(name = "embark_weight")
	private double embarkWeight = 0;   //已装载重量
	@Column(name = "company_name")
	private String companyName;  //商户名称
	@Column
	private String auditPerson;  //审核人姓名
	@Column(name = "audit_time")
	private Date auditTime;   //审核时间
	@Column(name = "audit_person_id")
	private String auditPersonId;   //审核人ID
	@Column(name = "audit_cause")
	private String auditCause;   //审核原因
	@Column(name = "stock_type_names")
	private String stockTypeNames;  //车辆类型名称
	@Version
	private Integer version;  //版本号
	private String remark;  //备注
	@Transient
	private String goodsDetail; // 货物详细信息JSON字符串
	@Transient
	private  String[]  goodsTypeId; //货物类型ID,用于查询
	@Transient
	private Status[] status_serch;//状态数组
	@Transient
	private String mylock;   //我的锁定
	@Transient
	private String stockTypes;
	@Transient
	private boolean isMe =false; //是否是我发布的物品
	@Column(name = "time_stamp")
	private Date timeStamp;   //时间戳
	@Transient
	private  String searchKey; //查询key
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String seachKey) {
		this.searchKey = seachKey;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public boolean getIsMe() {
		return isMe;
	}
	public void setIsMe(boolean isMe) {
		this.isMe = isMe;
	}
	public Account getAccount() {
		return account;
	}
	/**
	 * @return the auditCause
	 */
	public String getAuditCause() {
		return auditCause;
	}
	/**
	 * @return the auditPerson
	 */
	public String getAuditPerson() {
		return auditPerson;
	}
	public String getAuditPersonId() {
		return auditPersonId;
	}
	
	/**
	 * @return the auditTime
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	/**
	 * @return the consigneeAreaId
	 */
	public String getConsigneeAreaId() {
		return consigneeAreaId;
	}
	/**
	 * @return the consigneeAreaName
	 */
	public String getConsigneeAreaName() {
		return consigneeAreaName;
	}
	/**
	 * @return the consigneeCoordinate
	 */
	public String getConsigneeCoordinate() {
		return consigneeCoordinate;
	}
	public String getConsigneeEmail() {
		return consigneeEmail;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	/**
	 * @return the deliveryAreaId
	 */
	public String getDeliveryAreaId() {
		return deliveryAreaId;
	}
	 
	/**
	 * @return the deliveryAreaName
	 */
	public String getDeliveryAreaName() {
		return deliveryAreaName;
	}
	/**
	 * @return the deliveryCoordinate
	 */
	public String getDeliveryCoordinate() {
		return deliveryCoordinate;
	}
	/**
	 * @return the deliveryEmail
	 */
	public String getDeliveryEmail() {
		return deliveryEmail;
	}
	/**
	 * @return the deliveryMobile
	 */
	public String getDeliveryMobile() {
		return deliveryMobile;
	}
	/**
	 * @return the deliveryName
	 */
	public String getDeliveryName() {
		return deliveryName;
	}
	/**
	 * @return the deliveryNo
	 */
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public double getEmbarkWeight() {
		return embarkWeight;
	}
	/**
	 * @return the finiteTime
	 */
	public Date getFiniteTime() {
		return finiteTime;
	}
	 
	/**
	 * @return the finiteTimeStr
	 */
	public String getFiniteTimeStr() {
		return finiteTimeStr;
	}
	 
	/**
	 * @return the goodsDetail
	 */
	public String getGoodsDetail() {
		return goodsDetail;
	}
	/**
	 * @return the goodsType
	 */
	public GoodsType getGoodsType() {
		return goodsType;
	}
	/**
	 * @return the goodsTypeId
	 */
	public String[] getGoodsTypeId() {
		return goodsTypeId;
	}
	public boolean getHasLock() {
		return hasLock;
	}
	/**
	 * @return the mapKilometer
	 */
	public String getMapKilometer() {
		return mapKilometer;
	}
	public String getMylock() {
		return mylock;
	}
	/**
	 * @return the onLine
	 */
	public Boolean getOnLine() {
		return onLine;
	}
	/**
	 * @return the releaseTime
	 */
	public Date getReleaseTime() {
		return releaseTime;
	}
	public String getRemark() {
		return remark;
	}
	public Status getStatus() {
		return status;
	}
	/**
	 * @return the status_serch
	 */
	public Status[] getStatus_serch() {
		return status_serch;
	}
	public String getStockTypeNames() {
		return stockTypeNames;
	}
	public String getStockTypes() {
		return stockTypes;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	 
	public double getTotalWeight() {
		return totalWeight;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public Integer getVersion() {
		return version;
	}
	/**
	 * @return the longTime
	 */
	public boolean isLongTime() {
		return longTime;
	}
	/**
	 * @return the onLine
	 */
	public Boolean isOnLine() {
		return onLine;
	}
	/**
	 * @return the saveConsignee
	 */
	public boolean isSaveConsignee() {
		return saveConsignee;
	}
	public boolean isSaveConsignor() {
		return saveConsignor;
	}
	/**
	 * @return the saveDelivery
	 */
	public boolean isSaveDelivery() {
		return saveDelivery;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * @param auditCause the auditCause to set
	 */
	public void setAuditCause(String auditCause) {
		this.auditCause = auditCause;
	}
	/**
	 * @param auditPerson the auditPerson to set
	 */
	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}
	public void setAuditPersonId(String auditPersonId) {
		this.auditPersonId = auditPersonId;
	}
	/**
	 * @param auditTime the auditTime to set
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	/**
	 * @param consigneeAreaId the consigneeAreaId to set
	 */
	public void setConsigneeAreaId(String consigneeAreaId) {
		this.consigneeAreaId = consigneeAreaId;
	}
	/**
	 * @param consigneeAreaName the consigneeAreaName to set
	 */
	public void setConsigneeAreaName(String consigneeAreaName) {
		this.consigneeAreaName = consigneeAreaName;
	}
	/**
	 * @param consigneeCoordinate the consigneeCoordinate to set
	 */
	public void setConsigneeCoordinate(String consigneeCoordinate) {
		this.consigneeCoordinate = consigneeCoordinate;
	}
	public void setConsigneeEmail(String consigneeEmail) {
		this.consigneeEmail = consigneeEmail;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	/**
	 * @param deliveryAreaId the deliveryAreaId to set
	 */
	public void setDeliveryAreaId(String deliveryAreaId) {
		this.deliveryAreaId = deliveryAreaId;
	}
	 
	 
	/**
	 * @param deliveryAreaName the deliveryAreaName to set
	 */
	public void setDeliveryAreaName(String deliveryAreaName) {
		this.deliveryAreaName = deliveryAreaName;
	}
	/**
	 * @param deliveryCoordinate the deliveryCoordinate to set
	 */
	public void setDeliveryCoordinate(String deliveryCoordinate) {
		this.deliveryCoordinate = deliveryCoordinate;
	}
	/**
	 * @param deliveryEmail the deliveryEmail to set
	 */
	public void setDeliveryEmail(String deliveryEmail) {
		this.deliveryEmail = deliveryEmail;
	}
	/**
	 * @param deliveryMobile the deliveryMobile to set
	 */
	public void setDeliveryMobile(String deliveryMobile) {
		this.deliveryMobile = deliveryMobile;
	}
	/**
	 * @param deliveryName the deliveryName to set
	 */
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	/**
	 * @param deliveryNo the deliveryNo to set
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public void setEmbarkWeight(double embarkWeight) {
		this.embarkWeight = embarkWeight;
	}
	/**
	 * @param finiteTime the finiteTime to set
	 */
	public void setFiniteTime(Date finiteTime) {
		this.finiteTime = finiteTime;
	}
	/**
	 * @param finiteTimeStr the finiteTimeStr to set
	 */
	public void setFiniteTimeStr(String finiteTimeStr) {
		this.finiteTimeStr = finiteTimeStr;
	}

	/**
	 * @param goodsDetail the goodsDetail to set
	 */
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	/**
	 * @param goodsType the goodsType to set
	 */
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	/**
	 * @param goodsTypeId the goodsTypeId to set
	 */
	public void setGoodsTypeId(String[] goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	public void setHasLock(boolean hasLock) {
		this.hasLock = hasLock;
	}
	/**
	 * @param longTime the longTime to set
	 */
	public void setLongTime(boolean longTime) {
		this.longTime = longTime;
	}
	/**
	 * @param mapKilometer the mapKilometer to set
	 */
	public void setMapKilometer(String mapKilometer) {
		this.mapKilometer = mapKilometer;
	}
	public void setMylock(String mylock) {
		this.mylock = mylock;
	}
	/**
	 * @param onLine the onLine to set
	 */
	public void setOnLine(Boolean onLine) {
		this.onLine = onLine;
	}
	/**
	 * @param releaseTime the releaseTime to set
	 */
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @param saveConsignee the saveConsignee to set
	 */
	public void setSaveConsignee(boolean saveConsignee) {
		this.saveConsignee = saveConsignee;
	}
	public void setSaveConsignor(boolean saveConsignor) {
		this.saveConsignor = saveConsignor;
	}
	/**
	 * @param saveDelivery the saveDelivery to set
	 */
	public void setSaveDelivery(boolean saveDelivery) {
		this.saveDelivery = saveDelivery;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @param status_serch the status_serch to set
	 */
	public void setStatus_serch(Status[] status_serch) {
		this.status_serch = status_serch;
	}
	public void setStockTypeNames(String stockTypeNames) {
		this.stockTypeNames = stockTypeNames;
	}
	public void setStockTypes(String stockTypes) {
		this.stockTypes = stockTypes;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	 
}
