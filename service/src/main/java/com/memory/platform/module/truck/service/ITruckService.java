package com.memory.platform.module.truck.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.entity.gps.VechicleGps;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.module.trace.dto.TraceCar;
 

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆信息业务层
* 版    本    号：  V1.0
*/
public interface ITruckService {
	/**
	* 功 能 描 述：分页查询所有车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getPage(Truck truck,int start, int limit);
	/**
	* 功 能 描 述：商户分页查询所有车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getTruckPage(Truck truck,int start, int limit);
	/**
	* 功 能 描 述：易林分页查询所有车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：map
	 */
	Map<String, Object> getTruckPageByYilin(Truck truck,int start, int limit);
	/**
	* 功 能 描 述：根据id查询车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：Truck
	 */
	Truck getTruckById(String id);
	/**
	* 功 能 描 述：保存车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void saveTruck(Truck truck);
	/**
	* 功 能 描 述：修改车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void updateTruck(Truck truck);
	/**
	* 功 能 描 述：检查车辆是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	boolean getTruckByName(String name);
	/**
	* 功 能 描 述：检查车辆是否存在
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	boolean getTruckByName(String name, String truckId);
	/**
	* 功 能 描 述：删除车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	void removeTruck(String id); 
	/**
	* 功 能 描 述：根据车牌查询车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：Truck
	 */
	Truck checkTruckByNo(String no); 
	/**
	* 功 能 描 述：根据驾驶员查询车辆
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：Truck
	 */
	Truck getTruckByAcountNo(String accountid);
	/**
	* 功能描述： 获取所有商户车辆信息
	* 输入参数:  @param companyId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月1日下午2:42:30
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getCompanyTrucks(String companyId); 
	
	/**
	* 功能描述： 修改车辆状态
	* 输入参数:  @param truckIds
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月13日上午10:58:06
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateTruckStatus(String[] truckIds,TruckStatus truckStatus);
	
	/**
	* 功能描述：查询车队下的司机人员信息及车辆
	* 输入参数:  @param truck
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月13日下午3:47:13
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	List<Map<String, Object>> getCompantAccountTruckList(String companyId);
	
	/**
	* 功能描述：  修改车辆司机人员信息
	* 输入参数:  @param trackId //修改的车辆ID
	* 输入参数:  @param distributionTrackId //分配的车辆ID
	* 输入参数:  @param distributionUserId //分配车辆的用户ID
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日上午11:16:07
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateConfirmPerson(String trackId,String distributionTrackId,String distributionUserId,String distributionUserName);
	
	/**
	* 功能描述： 取消绑定人员
	* 输入参数:  @param trackId
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月18日上午10:21:28
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	void updateChainBroken(String trackId);
	
	/**
	* 功能描述： 查询是否有车辆装货，判断依据是：1、车辆不在运势中的状态      2、已分配司机的车辆
	* 输入参数:  @param companyId
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月30日下午10:19:42
	* 修 改 人: 
	* 日    期: 
	* 返    回：int
	 */
	int getChekcTruckNum(String companyId);
	List<TraceCar> getTraceCar(String companyId);
	List<Map<String,Object>> getTraceCarInfomation(String campanyID);
	String uploadImage(MultipartFile file) throws IOException;
	Map<String, Object> getTrucksByCommpanyWithMap(String companyid, int page,
			int size);
	List<Map<String, Object>> getTruckByIdWithMap(String id);
	List<Map<String, Object>> getTruckByAccountid(String id);
	List<Map<String, Object>> getTruckAndAccountListWithMap(String companyId);
	void updateTruckUserId(String trackId, String accountId, String companyId);
	void removeBinding(String trackId, String companyId);
	List<Map<String,Object>> getTruckByRobOrderId(String robOrderId);
	void updateTruckMerge(Truck truck);
	List<Map<String, Object>> getTruckAndAccountByCompanyID(String companyID);
	
	/**
	 * 根据CompanyId获取车辆信息
	 * */
	Map<String,Object> getTruckByCompanyIdPage(String companyID,int start,int limit);
	
	/**
	 * aiqiwu 根据账号获取车辆信息
	 * 2017-09-01
	 * */
	Map<String,Object> getTruckByAccountIdForMap(String accountId);
	void saveTruckNew(Truck truck);
	Map<String, Object> getTruckByID(String id);
	List<Truck> getBindTruckByCompanyID(String companeyID);
	List<VechicleGps> getCompamyGpsData(String companeyID);
}
