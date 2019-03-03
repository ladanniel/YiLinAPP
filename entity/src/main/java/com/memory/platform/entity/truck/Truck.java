package com.memory.platform.entity.truck;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.global.TruckStatus;
/**
* 创 建 人： liyanzhang
* 日    期： 2016年5月30日 19:00:27
* 修 改 人： 
* 日   期： 
* 描   述：车辆基本信息表
* 版 本 号：  V1.0
 */
@Entity
@Table(name = "sys_track")
public class Truck extends BaseEntity  {

	private static final long serialVersionUID = 1L;
	@Column
	private String track_no;//车牌号
	@ManyToOne
	@JoinColumn(name="card_type_id", nullable = false)
	private TruckType truckType;//车辆类型
	@ManyToOne
	@JoinColumn(name="card_plate_id",nullable = false)
	private TruckPlate truckPlate;//车牌类型
	@Column
	private Double track_long;//车辆长度
	@Column
	private Double capacity;//载重
	@Column(name="truck_status")
	private TruckStatus truckStatus = TruckStatus.notransit;  //默认设置车辆状态为：0（未运输）
	@ManyToOne
	@JoinColumn(name="card_brand_id",nullable = false)
	private TruckBrand truckBrand;//车辆品牌
	@Column
	private String track_read_no;//车辆识别码
	@ManyToOne
	@JoinColumn(name="engine_brand_id",nullable = false)
	private EngineBrand engineBrand;//发动机品牌
	@Column
	private String engine_no;//发动机编号
	@Column
	private String horsepower;//马力
	@Column
	private Date tag_time;//上牌时间
	@Column
	private String description;//备注
	@ManyToOne
	@JoinColumn(name="company_id",nullable = false)
	private Company company; //商户对象
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="account_id"  )
	private Account account;   //驾驶人信息
	@Column
	private String vehiclelicense_img;//行驶证图片地址
	@Transient
	private  String[]  companyIds; //商户ID,用于查询
	@Transient
	private  String[]  truckTypeIds; //车辆类型ID,用于查询
	@Transient
	private  String[]  truckPlateIds; //车牌类型ID,用于查询
	@Transient
	private  String[]  truckBrandIds; //车辆 品牌ID,用于查询
	@Transient
	private String companyName;//商户名称
	@Column
	private String sim_card_id;  //sim卡id号
	@Column
	private String gps_device_id;  //gps设备id号
	@Column
	private String yy_vehicel_id;
	@Column
	private String yy_vehicel_key;
	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}
	public Double getCapacity() {
		return capacity;
	}
	public Company getCompany() {
		return company;
	}
	public String[] getCompanyIds() {
		return companyIds;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getDescription() {
		return description;
	}
	public String getEngine_no() {
		return engine_no;
	}
	public EngineBrand getEngineBrand() {
		return engineBrand;
	}
	
	public String getGps_device_id() {
		return gps_device_id;
	}
	public String getHorsepower() {
		return horsepower;
	}
	public String getSim_card_id() {
		return sim_card_id;
	}
	public Date getTag_time() {
		return tag_time;
	}
	public Double getTrack_long() {
		return track_long;
	}
	public String getTrack_no() {
		return track_no;
	}
	public String getTrack_read_no() {
		return track_read_no;
	}
	public TruckBrand getTruckBrand() {
		return truckBrand;
	} 
	
	 
	public String[] getTruckBrandIds() {
		return truckBrandIds;
	}
	public TruckPlate getTruckPlate() {
		return truckPlate;
	}
	public String[] getTruckPlateIds() {
		return truckPlateIds;
	}
	/**
	 * @return the truckStatus
	 */
	public TruckStatus getTruckStatus() {
		return truckStatus;
	}
	public TruckType getTruckType() {
		return truckType;
	}
	public String[] getTruckTypeIds() {
		return truckTypeIds;
	}
	public String getVehiclelicense_img() {
		return vehiclelicense_img;
	}
	public String getYy_vehicel_id() {
		return yy_vehicel_id;
	}
	public String getYy_vehicel_key() {
		return yy_vehicel_key;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public void setCompanyIds(String[] companyIds) {
		this.companyIds = companyIds;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}
	public void setEngineBrand(EngineBrand engineBrand) {
		this.engineBrand = engineBrand;
	}
	public void setGps_device_id(String gps_device_id) {
		this.gps_device_id = gps_device_id;
	}
	public void setHorsepower(String horsepower) {
		this.horsepower = horsepower;
	}
	public void setSim_card_id(String sim_card_id) {
		this.sim_card_id = sim_card_id;
	}
	public void setTag_time(Date tag_time) {
		this.tag_time = tag_time;
	}
	public void setTrack_long(Double track_long) {
		this.track_long = track_long;
	}
	public void setTrack_no(String track_no) {
		this.track_no = track_no;
	}
	public void setTrack_read_no(String track_read_no) {
		this.track_read_no = track_read_no;
	}
	public void setTruckBrand(TruckBrand truckBrand) {
		this.truckBrand = truckBrand;
	}
	public void setTruckBrandIds(String[] truckBrandIds) {
		this.truckBrandIds = truckBrandIds;
	}
	public void setTruckPlate(TruckPlate truckPlate) {
		this.truckPlate = truckPlate;
	}
	public void setTruckPlateIds(String[] truckPlateIds) {
		this.truckPlateIds = truckPlateIds;
	}
	/**
	 * @param truckStatus the truckStatus to set
	 */
	public void setTruckStatus(TruckStatus truckStatus) {
		this.truckStatus = truckStatus;
	}
	public void setTruckType(TruckType truckType) {
		this.truckType = truckType;
	}
	public void setTruckTypeIds(String[] truckTypeIds) {
		this.truckTypeIds = truckTypeIds;
	}
	public void setVehiclelicense_img(String vehiclelicense_img) {
		this.vehiclelicense_img = vehiclelicense_img;
	}
	public void setYy_vehicel_id(String yy_vehicel_id) {
		this.yy_vehicel_id = yy_vehicel_id;
	}
	public void setYy_vehicel_key(String yy_vehicel_key) {
		this.yy_vehicel_key = yy_vehicel_key;
	}

	
}






























