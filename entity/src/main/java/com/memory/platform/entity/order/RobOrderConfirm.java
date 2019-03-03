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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

 





import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.memory.platform.entity.additional.AdditionalExpensesRecord;
import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.order.RobOrderRecord.QueryStatus;


/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月2日 下午2:59:51 
* 修 改 人： 
* 日   期： 
* 描   述： 订单确认表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "rob_order_confirm")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RobOrderConfirm extends BaseEntity {
	/*
	 * 支付方式
	 * */
	public   enum PaymentType{
		 actualWeight   , //按发货吨位结算
		 settlementWeight //按结算吨位结算
	}
	private static final long serialVersionUID = -1621049455099656460L;
	 @Column(name = "transport_no",updatable = false)
	 private String transportNo;//运输单号
	 @Column(name = "turck_id")
	 private String turckId;//车辆ID
	 @Column(name = "rob_order_id")
	 private String  robOrderId;//抢单ID
	 @Column(name = "rob_order_no")
	 private String  robOrderNo;//抢单单号
	 @Column(name = "unit_price")
	 private double  unitPrice;//货物单价
	 @Column(name = "total_weight")
	 private double  totalWeight;//运输货物总重量'
	 @Column(name = "actual_weight")
	 private double actualWeight;//实际拉货重量
	 
	 
	 @Column(name = "transportation_cost")
	 private double  transportationCost;//运输费用
	 @Column(name = "transportation_deposit")
	 private double  transportationDeposit;//运输押金总金额
	 @Column(name = "turck_cost")
	 private double  turckCost;//车辆押金总金额
	 @Column(name = "turck_deposit")
	 private double  turckDeposit;//车辆押金
	 
	 @Column(name = "actualtransportation_cost")
	 private double actualTransportationCost;//实际运输费用
	 @Column(name = "auto_payment_err")
	 private int auto_payment_err; //自动付款失败次数
	 
	 
	@OneToMany(mappedBy = "robOrderConfirm",fetch=FetchType.EAGER)
	List<AdditionalExpensesRecord> additionalExpensesRecords = new ArrayList<AdditionalExpensesRecord>();
	
	@Column(name="additional_cost")
	private double additionalCost;  //附加费用总费用
	@Column(name="total_cost")  
	private double totalCost;  //附加费用总费用+实际运费
	@Column(name="time_stamp")  
	private Date timeStamp;  //时间戳
	@Column(name="payment_type")  
	private  PaymentType payment_type; //支付方式 按发货吨位结算付款为0 按结算吨位付款为1
	@Column(name="settlement_weight")  
	private double settlement_weight ;//结算吨位 司机确认送达的时候结算的吨位
	@Column(name="settlement_cost")  
	private double settlement_cost ; //按结算吨位计算的结算金额
	public PaymentType getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(PaymentType payment_type) {
		this.payment_type = payment_type;
	}

	public double getSettlement_weight() {
		return settlement_weight;
	}

	public void setSettlement_weight(double settlement_weight) {
		this.settlement_weight = settlement_weight;
	}

	public double getSettlement_cost() {
		return settlement_cost;
	}

	public void setSettlement_cost(double settlement_cost) {
		this.settlement_cost = settlement_cost;
	}
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

	public double getAdditionalCost() {
		return additionalCost;
	}

	public void setAdditionalCost(double additionalCost) {
		this.additionalCost = additionalCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<AdditionalExpensesRecord> getAdditionalExpensesRecords() {
		return additionalExpensesRecords;
	}

	public void setAdditionalExpensesRecords(List<AdditionalExpensesRecord> additionalExpensesRecords) {
		this.additionalExpensesRecords = additionalExpensesRecords;
	}
	 
	public int getAuto_payment_err() {
		return auto_payment_err;
	}
	public void setAuto_payment_err(int auto_payment_err) {
		this.auto_payment_err = auto_payment_err;
	}
	@Column(name = "lock_status",columnDefinition="int default 0")
	 private LockStatus lockStatus;
	 //by lil 
	 @Column(name = "confirm_receipted_date")
	 private Date confirm_receipted_date; //回执单确认时间 做最后付款期限 如果状态已经是回执结束 最后3天系统自动付款
	 
	 
	public Date getConfirm_receipted_date() {
		return confirm_receipted_date;
	}
	public void setConfirm_receipted_date(Date confirm_receipted_date) {
		this.confirm_receipted_date = confirm_receipted_date;
	}
	//1：锁定 0：没有锁定
	 public enum LockStatus{//锁定状态
		 unlock, locking
	 };
	 
	 
	//0:等待装货、1、确认装货、2:运输中 3:送达  4:回执发还中  5:送还回执中6:订单完结 7:销单
	 public enum Status{
		 receiving,confirmload,transit,delivered,receipt,confirmreceipt,ordercompletion,singlepin
	 }
	 
	 //0:等待介入 、1:正在处理、 2:处理完成
	 public enum SpecialStatus{
		 suchprocessing,processing,success,none
	 }
	 //申请类型  0：急救  1：仲裁
	 public enum SpecialType{
		 emergency,arbitration,none
	 }
	 
	 //仲裁结果  0:无操作  1：赔偿 2无,3 消单
	 public enum RocessingResult{
		 noperation,indemnify,none,singlepin
	 }
	 
	 public static enum QueryStatus {
		 waitReciving, //待装货和代发货   receiving，confirmload
		 transit, //运输中  transit
		 confirmreceipt, //回执返还中 跳过delivered（货值发还中） 送达提交后直接改到 receipt
		 completion,  //已经完成  ordercompletion,singlepin
		 confirmreceipted,//回执已经返还 (付款)
	 }
	 static Map<QueryStatus,ArrayList< Status>> queryDic;
	 static {
		 queryDic =  new HashMap<RobOrderConfirm.QueryStatus, ArrayList<Status>>();
		 ArrayList<Status> arr = new ArrayList<RobOrderConfirm.Status>(Arrays.asList(Status.receiving,Status.confirmload));
		 queryDic.put(QueryStatus.waitReciving, arr);
		 arr = new ArrayList<RobOrderConfirm.Status>(Arrays.asList(Status.transit ));
		 queryDic.put(QueryStatus.transit, arr);
		 arr = new ArrayList<RobOrderConfirm.Status>(Arrays.asList(Status.receipt,Status.delivered ));
		 queryDic.put(QueryStatus.confirmreceipt, arr);
		 arr = new ArrayList<RobOrderConfirm.Status>(Arrays.asList(Status.ordercompletion,Status.singlepin ));
		 queryDic.put(QueryStatus.completion, arr);
		 arr = new ArrayList<RobOrderConfirm.Status>(Arrays.asList(Status.confirmreceipt   ));
		 queryDic.put(QueryStatus.confirmreceipted, arr);
		 
	 }
	 public static List<Status> getStatusWithQueryStatus(QueryStatus queryStatus) {
		return queryDic.get(queryStatus);
	}
	 public static QueryStatus getQueryStatusWithStatus(Status status) {
		 for (QueryStatus queryStatus : queryDic.keySet()) {
			 List<Status> statusLst =  queryDic.get(queryStatus);
			for (Status statusIfno : statusLst) {
				if(statusIfno == status) return queryStatus;
			}
		}
		 return QueryStatus.waitReciving;
		
	}
	 @Transient
	 private QueryStatus queryStatus;
	 public QueryStatus getQueryStatus() {
		return queryStatus;
	}
	 @Transient
	private String goods_basic_id;
	public String getGoods_basic_id() {
		return goods_basic_id;
	}

	public void setGoods_basic_id(String goods_basic_id) {
		this.goods_basic_id = goods_basic_id;
	}

	public void setQueryStatus(QueryStatus queryStatus) {
		this.queryStatus = queryStatus;
	}
	@Column(name = "rocessing_result")
	 private RocessingResult rocessingResult; //仲裁结果 
	 
	 @Column(name = "special_status")
	 private  SpecialStatus specialStatus;//特殊状态
	 
	 
	 @Column(name = "special_type")
	 private  SpecialType specialType;//类型
	 
	 private  Status status;//状态
	 @Column(name = "turck_user_id")
	 private String  turckUserId;//拉货司机
	 @Column(name = "confirm_data")
	 private Date confirmData;//确认时间
	 
	 @Column(name = "end_data")
	 private Date EndDate;// 订单完结时间
	 
	 @Column(name = "lgistics_code")
	 private String lgisticsCode;//物流公司编号
	 
	 @Column(name = "lgistics_num")
	 private String lgisticsNum;//物流单号
	 
	 @Column(name = "lgistics_name")
	 private String lgisticsName;//物流名称
	 
	 @Column(name = "account_id")
	 private String accountId;//回执访问人员
	 
	 @Column(name = "receipt_img")
	 private String receiptImg;//回执图片
	 
	 @Column(name = "original_receipt_img")
	 private String originalReceiptImg;//司机 上传回执图片
	 
	 
	 @Column(name = "indemnity", columnDefinition ="double default 0")
	 private Double indemnity; //赔偿金额
	 
	 
	 
	 @Transient
	 private String robbedCompanyId;  //被抢人商户Id
	 
	 
	 @Transient
	 private String companyName;  //商户名称
	 
	 @Transient
	 private String robbedAccountId;  //被抢人用户Id
	 
	 @Transient
	 private Boolean isSpecial = false; //是否查特殊运单
	 
	 
	 
	 
	 
	 
	 
	 
	 

	public double getActualTransportationCost() {
		return actualTransportationCost;
	}

	public void setActualTransportationCost(double actualTransportationCost) {
		this.actualTransportationCost = actualTransportationCost;
	}

	public void setIndemnity(Double indemnity) {
		this.indemnity = indemnity;
	}

	public LockStatus getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(LockStatus lockStatus) {
		this.lockStatus = lockStatus;
	}

	public double getIndemnity() {
		return indemnity;
	}

	public void setIndemnity(double indemnity) {
		this.indemnity = indemnity;
	}

	public RocessingResult getRocessingResult() {
		return rocessingResult;
	}

	public void setRocessingResult(RocessingResult rocessingResult) {
		this.rocessingResult = rocessingResult;
	}

	public SpecialType getSpecialType() {
		return specialType;
	}

	public void setSpecialType(SpecialType specialType) {
		this.specialType = specialType;
	}

	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	 
	 



	public SpecialStatus getSpecialStatus() {
		return specialStatus;
	}


	public void setSpecialStatus(SpecialStatus specialStatus) {
		this.specialStatus = specialStatus;
	}


	public Date getEndDate() {
		return EndDate;
	}


	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}


	public String getOriginalReceiptImg() {
		return originalReceiptImg;
	}


	public void setOriginalReceiptImg(String originalReceiptImg) {
		this.originalReceiptImg = originalReceiptImg;
	}


	public String getRobbedAccountId() {
		return robbedAccountId;
	}
    
    
	public void setRobbedAccountId(String robbedAccountId) {
		this.robbedAccountId = robbedAccountId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRobbedCompanyId() {
		return robbedCompanyId;
	}
	public void setRobbedCompanyId(String robbedCompanyId) {
		this.robbedCompanyId = robbedCompanyId;
	}
		/**
		 * @return the img
		 */
		public List<String> getImgList() {
			List<String> list = new ArrayList<String>();
			if(org.apache.commons.lang3.StringUtils.isNotBlank(this.getReceiptImg())){
				 String[] imgArry = this.getReceiptImg().split(",");
				 for(String str:imgArry){
					 if(org.apache.commons.lang3.StringUtils.isNotBlank(str)){
						 list.add(str);
					 }
				 }
			}
			return list;
		}
		
	public List<String> getTruckReceiptImgList(){
		List<String> list = new ArrayList<String>();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(this.getOriginalReceiptImg())){
			 String[] imgArry = this.getOriginalReceiptImg().split(",");
			 for(String str:imgArry){
				 if(org.apache.commons.lang3.StringUtils.isNotBlank(str)){
					 list.add(str);
				 }
			 }
		}
		return list;
	}
		
	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * @return the receiptImg
	 */
	public String getReceiptImg() {
		return receiptImg;
	}
	/**
	 * @param receiptImg the receiptImg to set
	 */
	public void setReceiptImg(String receiptImg) {
		this.receiptImg = receiptImg;
	}
	public String getLgisticsName() {
		return lgisticsName;
	}
	public void setLgisticsName(String lgisticsName) {
		this.lgisticsName = lgisticsName;
	}
	public String getLgisticsCode() {
		return lgisticsCode;
	}
	public void setLgisticsCode(String lgisticsCode) {
		this.lgisticsCode = lgisticsCode;
	}
	public String getLgisticsNum() {
		return lgisticsNum;
	}
	public void setLgisticsNum(String lgisticsNum) {
		this.lgisticsNum = lgisticsNum;
	}
	/**
	 * @return the turckId
	 */
	public String getTurckId() {
		return turckId;
	}
	/**
	 * @param turckId the turckId to set
	 */
	public void setTurckId(String turckId) {
		this.turckId = turckId;
	}
	/**
	 * @return the robOrderId
	 */
	public String getRobOrderId() {
		return robOrderId;
	}
	/**
	 * @param robOrderId the robOrderId to set
	 */
	public void setRobOrderId(String robOrderId) {
		this.robOrderId = robOrderId;
	}
	/**
	 * @return the robOrderNo
	 */
	public String getRobOrderNo() {
		return robOrderNo;
	}
	/**
	 * @param robOrderNo the robOrderNo to set
	 */
	public void setRobOrderNo(String robOrderNo) {
		this.robOrderNo = robOrderNo;
	}
	/**
	 * @return the transportationCost
	 */
	public double getTransportationCost() {
		return transportationCost;
	}
	/**
	 * @param transportationCost the transportationCost to set
	 */
	public void setTransportationCost(double transportationCost) {
		this.transportationCost = transportationCost;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the turckUserId
	 */
	public String getTurckUserId() {
		return turckUserId;
	}
	/**
	 * @param turckUserId the turckUserId to set
	 */
	public void setTurckUserId(String turckUserId) {
		this.turckUserId = turckUserId;
	}
	/**
	 * @return the confirmData
	 */
	public Date getConfirmData() {
		return confirmData;
	}
	/**
	 * @param confirmData the confirmData to set
	 */
	public void setConfirmData(Date confirmData) {
		this.confirmData = confirmData;
	}
	/**
	 * @return the unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * @return the totalWeight
	 */
	public double getTotalWeight() {
		return totalWeight;
	}
	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
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
	/**
	 * @return the transportNo
	 */
	public String getTransportNo() {
		return transportNo;
	}
	/**
	 * @param transportNo the transportNo to set
	 */
	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}
	/**
	 * @return the transportationDeposit
	 */
	public double getTransportationDeposit() {
		return transportationDeposit;
	}
	/**
	 * @param transportationDeposit the transportationDeposit to set
	 */
	public void setTransportationDeposit(double transportationDeposit) {
		this.transportationDeposit = transportationDeposit;
	}
	/**
	 * @return the turckCost
	 */
	public double getTurckCost() {
		return turckCost;
	}
	/**
	 * @param turckCost the turckCost to set
	 */
	public void setTurckCost(double turckCost) {
		this.turckCost = turckCost;
	}
	/**
	 * @return the turckDeposit
	 */
	public double getTurckDeposit() {
		return turckDeposit;
	}
	/**
	 * @param turckDeposit the turckDeposit to set
	 */
	public void setTurckDeposit(double turckDeposit) {
		this.turckDeposit = turckDeposit;
	}

	 
	 
}
