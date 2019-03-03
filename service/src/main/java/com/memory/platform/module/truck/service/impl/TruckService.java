package com.memory.platform.module.truck.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.Jedis;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause.Entry;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.gps.VechicleGps;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.entity.truck.TruckDistribution;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.OSSClientUtil;
import com.memory.platform.global.StringFromateTemplate;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.global.UserUtil;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.aut.service.IAuthenticationService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.impl.RobOrderConfirmService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.trace.dto.GeoReqDTO;
import com.memory.platform.module.trace.dto.ReGeoReqDTO;
import com.memory.platform.module.trace.dto.ReGeoResponseDTO;
import com.memory.platform.module.trace.dto.ReGeoResponseDTO.Regeocode;
import com.memory.platform.module.trace.dto.TraceCar;
import com.memory.platform.module.trace.dto.YYLocationResponseDTO;
import com.memory.platform.module.trace.dto.YYLocationResponseDTO.Location;
import com.memory.platform.module.trace.service.IGaoDeWebService;
import com.memory.platform.module.trace.service.IYYGpsService;
import com.memory.platform.module.trace.service.impl.YHTGpsService;
import com.memory.platform.module.truck.dao.EngineBrandDao;
import com.memory.platform.module.truck.dao.TruckDao;
import com.memory.platform.module.truck.dao.TruckDistributionDao;
import com.memory.platform.module.truck.service.ITruckService;

/**
 * 
 * 创 建 人：liyanzhang 日 期： 2016年5月30日 19:10:47 修 改 人： 修 改 日 期： 描 述：车辆信息业务层实现类 版 本
 * 号： V1.0
 */
@Service
public class TruckService implements ITruckService {
	@Autowired
	private TruckDao truckDao;
	@Autowired
	TruckDistributionDao truckDistributionDao;
	@Autowired
	IAuthenticationService authenticationService;
	@Autowired
	IGaoDeWebService gaoDeWebService;
	@Autowired
	IYYGpsService yyGpsService;
	@Autowired
	EngineBrandDao engineBrandDao;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	MemShardStrore memStore;
	@Autowired
	YHTGpsService gpsService;

	/**
	 * 功 能 描 述：分页查询所有车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修
	 * 改 日 期： 返 回：map
	 */
	@Override
	public Map<String, Object> getPage(Truck truck, int start, int limit) {
		return truckDao.getPage(truck, start, limit);
	}

	/**
	 * 功 能 描 述：商户分页查询所有车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人：
	 * 修 改 日 期： 返 回：map
	 */
	@Override
	public Map<String, Object> getTruckPage(Truck truck, int start, int limit) {
		return truckDao.getTruckPage(truck, start, limit);
	}

	/**
	 * 功 能 描 述：根据id查询车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修
	 * 改 日 期： 返 回：Truck
	 */
	@Override
	public Truck getTruckById(String id) {
		return truckDao.getTruckById(id);
	}

	/**
	 * 功 能 描 述：保存车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修 改 日
	 * 期： 返 回：void
	 */
	@Override
	public void saveTruck(Truck truck) {
		truckDao.saveTruck(truck);

	}

	/**
	 * 功 能 描 述：修改车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修 改 日
	 * 期： 返 回：void
	 */
	@Override
	public void updateTruck(Truck truck) {
		truckDao.updateTruck(truck);
	}

	/**
	 * 功 能 描 述：检查车辆是否存在 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修
	 * 改 日 期： 返 回：void
	 */
	@Override
	public boolean getTruckByName(String name) {
		return truckDao.getTruckByName(name);
	}

	/**
	 * 功 能 描 述：检查车辆是否存在 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修
	 * 改 日 期： 返 回：void
	 */
	@Override
	public boolean getTruckByName(String name, String truckId) {
		return truckDao.getTruckByName(name, truckId);
	}

	/**
	 * 功 能 描 述：删除车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修 改 日
	 * 期： 返 回：void
	 */
	@Override
	public void removeTruck(String id) {
		Truck persistentTruck = this.getTruckById(id);

		Account currentAccount = UserUtil.getAccount();
		if (currentAccount.getCompany().getId()
				.equals(persistentTruck.getCompany().getId()) == false
				|| currentAccount.getIsAdmin() == false) {
			throw new BusinessException("你无权限操作");
		}
		RobOrderConfirm confirm = new RobOrderConfirm();
		confirm.setTurckId(id);
		RobOrderConfirm persistentConfirm = robOrderConfirmService
				.findRobOrderConfirm(confirm);
		if (persistentConfirm != null) {

			throw new BusinessException("该车辆已经有过运单不能删除");
		}
		truckDao.removeTruck(id);
		// throw new BusinessException("测试错误看看");
	}

	/**
	 * 功 能 描 述：根据车牌查询车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修
	 * 改 日 期： 返 回：Truck
	 */
	@Override
	public Truck checkTruckByNo(String no) {
		return truckDao.checkTruckByNo(no);
	}

	/**
	 * 功能描述： 获取所有商户车辆信息 输入参数: @param companyId 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年7月1日下午2:42:30 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> getCompanyTrucks(String companyId) {
		// TODO Auto-generated method stub
		return truckDao.getCompanyTrucks(companyId);

	}

	/**
	 * 功 能 描 述：易林分页查询所有车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人：
	 * 修 改 日 期： 返 回：map
	 */
	@Override
	public Map<String, Object> getTruckPageByYilin(Truck truck, int start,
			int limit) {
		return truckDao.getTruckPageByYilin(truck, start, limit);

	}

	/**
	 * 功能描述： 修改车辆状态 输入参数: @param truckIds 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年7月13日上午10:58:06 修 改 人: 日 期: 返 回：void
	 */
	@Override
	public void updateTruckStatus(String[] truckIds, TruckStatus truckStatus) {
		for (String id : truckIds) {
			truckDao.updateTruckStatus(id, truckStatus);
		}

	}

	/**
	 * 功能描述：查询车队下的司机人员信息及车辆 输入参数: @param truck 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年7月13日下午3:47:13 修 改 人: 日 期: 返
	 * 回：List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> getCompantAccountTruckList(String companyId) {
		// TODO Auto-generated method stub
		return truckDao.getCompantAccountTruckList(companyId);
	}

	/**
	 * 功能描述： 修改车辆司机人员信息 输入参数: @param trackId //修改的车辆ID 输入参数: @param
	 * distributionTrackId //分配的车辆ID 输入参数: @param distributionUserId //分配车辆的用户ID
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月15日上午11:16:07 修 改 人: 日 期: 返 回：void
	 */
	@Override
	public void updateConfirmPerson(String trackId, String distributionTrackId,
			String distributionUserId, String distributionUserName) {
		Account account = UserUtil.getUser();
		if (StringUtils.isNotEmpty(trackId)) {
			truckDao.updateTruckUserId(trackId, null, account.getCompany()
					.getId());
		}
		truckDao.updateTruckUserId(distributionTrackId, distributionUserId,
				account.getCompany().getId());
		TruckDistribution truckDistribution = new TruckDistribution();
		truckDistribution.setOperationUserId(account.getId());
		truckDistribution.setOperationUserName(account.getName());
		truckDistribution.setDistributionUserId(distributionUserId);
		truckDistribution.setDistributionUserName(distributionUserName);
		truckDistribution.setTrackId(distributionTrackId);
		truckDistribution.setDistributionDate(new Date());
		truckDistributionDao.saveTruckDistribution(truckDistribution);

	}

	/**
	 * 功能描述： 取消绑定人员 输入参数: @param trackId 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年7月18日上午10:21:28 修 改 人: 日 期: 返 回：void
	 */
	@Override
	public void updateChainBroken(String trackId) {
		Account account = UserUtil.getUser();
		truckDao.updateTruckUserId(trackId, null, account.getCompany().getId());
	}

	/**
	 * 功 能 描 述：根据驾驶员查询车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人：
	 * 修 改 日 期： 返 回：Truck
	 */
	@Override
	public Truck getTruckByAcountNo(String accountid) {
		// TODO Auto-generated method stub
		return truckDao.getTruckByAcountNo(accountid);
	}

	/**
	 * 功能描述： 查询是否有车辆装货，判断依据是：1、车辆不在运势中的状态 2、已分配司机的车辆 输入参数: @param companyId
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月30日下午10:19:42 修 改 人: 日
	 * 期: 返 回：int
	 */
	@Override
	public int getChekcTruckNum(String companyId) {
		// TODO Auto-generated method stub
		return truckDao.getChekcTruckNum(companyId);
	}

	public List<Map<String, Object>> getTraceCarInfomation(String campanyID) {
		List<Map<String, Object>> ret = truckDao
				.getTraceCarInfomation(campanyID);
		int tempI = 0;
		for (Map<String, Object> info : ret) {

			if (StringUtil.isEmpty(info.get("yy_vehicel_id") == null ? ""
					: info.get("yy_vehicel_id").toString())
					|| StringUtil
							.isEmpty(info.get("yy_vehicel_key").toString() == null ? ""
									: info.get("yy_vehicel_key").toString()))
				continue;
			YYLocationResponseDTO location = yyGpsService.getVeLocation(info
					.get("yy_vehicel_id").toString(), info
					.get("yy_vehicel_key").toString());

			String consignee_area_name = (String) info
					.get("consignee_area_name");
			String consignee_address = (String) info.get("consignee_address");
			String delivery_area_name = (String) info.get("delivery_area_name");
			String delivery_address = (String) info.get("delivery_address");
			String consignee_addr = "";
			String delivery_addr = "";
			if (StringUtil.isNotEmpty(consignee_area_name)) {
				consignee_addr = consignee_area_name.replaceAll("-", "");
			}
			if (StringUtil.isNotEmpty(consignee_address)) {
				consignee_addr += consignee_address.replaceAll("-", "");
			}
			if (StringUtil.isEmpty(consignee_addr)) {
				consignee_addr = "无";
			}
			info.put("consignee_addr", consignee_addr);

			if (StringUtil.isNotEmpty(delivery_area_name)) {
				delivery_addr = delivery_area_name.replaceAll("-", "");
			}
			if (StringUtil.isNotEmpty(delivery_address)) {
				delivery_addr += delivery_address.replaceAll("-", "");
			}
			if (StringUtil.isEmpty(delivery_addr)) {
				delivery_addr = "无";
			}

			// 添加定位信息
			if (location == null || location.getLocs().size() <= 0) {
				Location loc = new Location();
				loc.setInfo("未知");
				loc.setLat(0);
				loc.setLng(0);
				location.getLocs().add(loc);
				// continue;
			}
			ArrayList<Location> ar = location.getLocs();
			float latitude = 0;
			float longitude = 0;
			float distance = 0;
			float totalDis = 0;
			int direct = 0;
			double speed = 0;
			String state = "";
			Location loc = ar.get(0);
			latitude = (float) loc.getLat() == 0 ? tempI * 1.1f + 26.5891411742f
					: (float) loc.getLat();
			longitude = (float) loc.getLng() == 0 ? 106.6381072998f + tempI * 0.1f
					: (float) loc.getLng();
			distance = (float) loc.getDistance();
			totalDis = (float) loc.getTotalDis();
			direct = loc.getDirect();
			if (loc.getSpeed() != null)
				speed = loc.getSpeed();
			if (!StringUtil.isEmpty(loc.getState()))
				state = loc.getState();
			info.put("latitude", latitude);
			info.put("longitude", longitude);
			info.put("distance", distance);
			info.put("totalDis", totalDis);
			info.put("direct", direct);
			info.put("speed", speed);
			info.put("state", state);
			info.put("delivery_addr", delivery_addr);
			info.put("addr", loc.getInfo());
			info.put("addrCommponent", loc.getInfo());

		}
		return ret;
	}

	@Override
	public List<TraceCar> getTraceCar(String companyId) {

		List<Truck> lst = truckDao.getTruckByCompanyID(companyId);
		List<TraceCar> cars = new ArrayList<>();
		int idx = 0;
		for (Truck info : lst) {

			TraceCar car = new TraceCar();
			/*
			 * private String track_no;//车牌号
			 * 
			 * private String truckType;//车辆类型
			 * 
			 * private String truckPlate;//车牌类型
			 * 
			 * private Double track_long;//车辆长度
			 * 
			 * private Double capacity;//载重
			 * 
			 * private String truckStatus ; //默认设置车辆状态为：0（未运输）
			 * 
			 * private String truckBrand;//车辆品牌
			 * 
			 * private String track_read_no;//车辆识别码
			 * 
			 * private String engineBrand;//发动机品牌
			 * 
			 * private String engine_no;//发动机编号
			 * 
			 * private String horsepower;//马力
			 * 
			 * private Date tag_time;//上牌时间
			 * 
			 * private String description;//备注
			 */
			car.setTrack_long(info.getTrack_long());
			car.setCapacity(info.getCapacity());
			car.setTruckStatus(info.getTruckStatus().name());
			car.setTruckBrand(info.getTruckBrand().getName());
			car.setTruckType(info.getTruckType().getName());
			if (info.getAccount() != null)
				car.setUser_name(info.getAccount().getName());
			car.setTrack_no(info.getTrack_no());
			car.setLatitude(26.5891411742f + idx * 0.01f);
			car.setLongitude(106.6381072998f + idx * 0.01f);
			idx++;
			cars.add(car);
		}
		return cars;
	}

	/**
	 * 功能描述： 图片上传 输入参数: @param MultipartFile 输入参数: @param 异 常： 创 建 人:liyanzhang
	 * 日 期: 2016年7月15日上午11:18:01 修 改 人: 日 期: 返 回：图片路径
	 * 
	 * @throws IOException
	 */
	@Override
	public String uploadImage(MultipartFile file) throws IOException {
		String filename = UUID.randomUUID()
				+ file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf("."));
		FileInputStream fin = (FileInputStream) file.getInputStream();
		String url = OSSClientUtil.uploadFile2OSS(fin, filename,
				"vehiclelicense");// 此处是上传到阿里云OSS的步骤
		return url;
	}

	/**
	 * 功能描述： 查询车队车辆列表 输入参数: @param companyid 输入参数: @param 异 常： 创 建 人:liyanzhang
	 * 日 期: 2016年7月15日上午11:18:01 修 改 人: 日 期: 返 回：map
	 */
	@Override
	public Map<String, Object> getTrucksByCommpanyWithMap(String companyid,
			int page, int size) {
		return truckDao.getTrucksByCommpanyWithMap(companyid, page, size);
	}

	@Override
	public List<Map<String, Object>> getTruckByIdWithMap(String id) {
		return truckDao.getTruckByIdWithMap(id);
	}

	@Override
	public List<Map<String, Object>> getTruckByAccountid(String id) {
		return truckDao.getTruckByAccountid(id);
	}

	/**
	 * 
	 * 功 能 描 述：根据商户查询车辆及绑定驾驶员 输 入 参 数： @param id 异 常： 创 建 人： liyanzhang 日 期：
	 * 2016年5月30日 19:21:53 修 改 人： 修 改 日 期： 返 回：list
	 */
	@Override
	public List<Map<String, Object>> getTruckAndAccountListWithMap(
			String companyId) {
		return truckDao.getTruckAndAccountListWithMap(companyId);
	}

	/**
	 * 功能描述： 分配驾驶员 输入参数: @param trackId 输入参数: @param accountId 异 常： 创 建
	 * 人:liyanzhang 日 期: 2016年7月15日上午11:20:22 修 改 人: 日 期: 返 回：void
	 */
	@Override
	public void updateTruckUserId(String trackId, String accountId,
			String companyId) {
		truckDao.updateTruckUserId(trackId, TruckStatus.transit, accountId,
				companyId);
	}

	/**
	 * 功能描述：取消驾驶员绑定 输入参数: @param trackId 输入参数: @param accountId 异 常： 创 建 人:
	 * liyanzhang 日 期: 2016年8月15日上午11:20:22 修 改 人: 日 期: 返 回：void
	 */
	@Override
	public void removeBinding(String trackId, String companyId) {
		truckDao.updateTruckUserId(trackId, TruckStatus.notransit, null,
				companyId);
	}

	/*
	 * aiqiwu 2017-06-08 根据订单ID获取车辆详细信息
	 */
	@Override
	public List<Map<String, Object>> getTruckByRobOrderId(String robOrderId) {
		List<Map<String, Object>> ret = truckDao
				.getTruckByRobOrderId(robOrderId);
		int tempI = 0;
		for (Map<String, Object> info : ret) {
			if (StringUtil.isEmpty(info.get("yy_vehicel_id") == null ? ""
					: info.get("yy_vehicel_id").toString())
					|| StringUtil
							.isEmpty(info.get("yy_vehicel_key").toString() == null ? ""
									: info.get("yy_vehicel_key").toString()))
				continue;
			YYLocationResponseDTO location = yyGpsService.getVeLocation(info
					.get("yy_vehicel_id").toString(), info
					.get("yy_vehicel_key").toString());
			String consignee_area_name = (String) info
					.get("consignee_area_name");
			String consignee_address = (String) info.get("consignee_address");
			String delivery_area_name = (String) info.get("delivery_area_name");
			String delivery_address = (String) info.get("delivery_address");
			String consignee_addr = "";
			String delivery_addr = "";
			if (StringUtil.isNotEmpty(consignee_area_name)) {
				consignee_addr = consignee_area_name.replaceAll("-", "");
			}
			if (StringUtil.isNotEmpty(consignee_address)) {
				consignee_addr += consignee_address.replaceAll("-", "");
			}
			if (StringUtil.isEmpty(consignee_addr)) {
				consignee_addr = "无";
			}
			info.put("consignee_addr", consignee_addr);

			if (StringUtil.isNotEmpty(delivery_area_name)) {
				delivery_addr = delivery_area_name.replaceAll("-", "");
			}
			if (StringUtil.isNotEmpty(delivery_address)) {
				delivery_addr += delivery_address.replaceAll("-", "");
			}
			if (StringUtil.isEmpty(delivery_addr)) {
				delivery_addr = "无";
			}

			// 添加定位信息
			if (location == null || location.getLocs().size() <= 0) {
				Location loc = new Location();
				loc.setInfo("未知");
				loc.setLat(0);
				loc.setLng(0);
				location.getLocs().add(loc);
				// continue;
			}
			ArrayList<Location> ar = location.getLocs();
			float latitude = 0;
			float longitude = 0;
			float distance = 0;
			float totalDis = 0;
			int direct = 0;
			double speed = 0;
			String state = "";
			Location loc = ar.get(0);
			latitude = (float) loc.getLat() == 0 ? tempI * 1.1f + 26.5891411742f
					: (float) loc.getLat();
			longitude = (float) loc.getLng() == 0 ? 106.6381072998f + tempI * 0.1f
					: (float) loc.getLng();
			distance = (float) loc.getDistance();
			totalDis = (float) loc.getTotalDis();
			direct = loc.getDirect();
			speed = loc.getSpeed();
			state = loc.getState();
			info.put("latitude", latitude);
			info.put("longitude", longitude);
			info.put("distance", distance);
			info.put("totalDis", totalDis);
			info.put("direct", direct);
			info.put("speed", speed);
			info.put("state", state);
			info.put("delivery_addr", delivery_addr);
			info.put("addr", loc.getInfo());
			info.put("addrCommponent", loc.getInfo());

		}
		return ret;
	}

	public void updateTruckMerge(Truck truck) {
		truckDao.updateTruckMerge(truck);
	}

	@Override
	public List<Map<String, Object>> getTruckAndAccountByCompanyID(
			String companyID) {
		return truckDao.getTruckAndAccountByCompanyID(companyID);

	}

	@Override
	public Map<String, Object> getTruckByAccountIdForMap(String accountId) {
		return truckDao.getTruckByAccountIdForMap(accountId);
	}

	@Override
	public void saveTruckNew(Truck truck) {
		Truck persistentTruck = StringUtil.isEmpty(truck.getId()) ? truck
				: this.getTruckById(truck.getId());
		boolean isAdd = persistentTruck == truck;
		Account currentAccount = UserUtil.getAccount();

		if (truck.getAccount() != null) {
			String truckUserID = truck.getAccount().getId();
			if (StringUtil.isNotEmpty(truckUserID)) {

				List<Map<String, Object>> lstList = this
						.getTruckByAccountid(truckUserID);
				Truck otherTuck = null;
				for (Map<String, Object> map : lstList) {
					if (map.get("id").toString().equals(truck.getId()) == false) {
						otherTuck = this.getTruckById(map.get("id").toString());

						break;
					}
				}
				if (otherTuck != null) {
					if (otherTuck.getTruckStatus() == TruckStatus.transit) {
						throw new BusinessException("车辆:"
								+ otherTuck.getTrack_no() + "的驾驶员("
								+ otherTuck.getAccount().getName()
								+ ") 正在运输中,不能修改驾驶员");
					}
					otherTuck.setAccount(null);
					truckDao.save(otherTuck);
				}
			}
		}

		if (isAdd == false) {
			if (truck.getAccount() == null
					|| StringUtil.isEmpty(truck.getAccount().getId())) {
				persistentTruck.setAccount(null);
			} else {

				persistentTruck.setAccount(truck.getAccount());

			}

			if (StringUtil.isNotEmpty(truck.getVehiclelicense_img())) {
				persistentTruck.setVehiclelicense_img(truck
						.getVehiclelicense_img());
			}
			if (StringUtil.isNotEmpty(truck.getTrack_no())) {
				persistentTruck.setTrack_no(truck.getTrack_no());
			}

			persistentTruck.setTrack_long(truck.getTrack_long());
			persistentTruck.setTruckBrand(truck.getTruckBrand());
			persistentTruck.setTruckType(truck.getTruckType());
			persistentTruck.setCapacity(truck.getCapacity());
			if (StringUtil.isNotEmpty(truck.getGps_device_id())) {
				persistentTruck.setGps_device_id(truck.getGps_device_id());
			}
			if (StringUtil.isNotEmpty(truck.getYy_vehicel_id())) {
				persistentTruck.setYy_vehicel_id(truck.getYy_vehicel_id());
			}
			if (StringUtil.isNotEmpty(truck.getYy_vehicel_key())) {
				persistentTruck.setYy_vehicel_key(truck.getYy_vehicel_key());
			}
			if (StringUtil.isNotEmpty(truck.getGps_device_id())) {
				String json = memStore.getClient().hget("vehicleGpsData",
						truck.getGps_device_id());
				if (StringUtil.isEmpty(json)) {
					throw new BusinessException("gps设备号错误,未找到此设备");
				}
			}
			persistentTruck.setGps_device_id(truck.getGps_device_id());
			persistentTruck.setSim_card_id(truck.getSim_card_id());
		}
		if (persistentTruck.getEngineBrand() == null) {
			persistentTruck.setEngineBrand(engineBrandDao.getEngineBrandList()
					.get(0));
		}
		persistentTruck.setCompany(currentAccount.getCompany());
		truckDao.save(persistentTruck);

	}

	@Override
	public Map<String, Object> getTruckByCompanyIdPage(String companyID,
			int start, int limit) {
		return truckDao.getTruckByCompanyIdPage(companyID, start, limit);

	}

	@Override
	public List<Truck> getBindTruckByCompanyID(String companeyID) {
		return truckDao.getBindTruckByCompanyID(companeyID);
	}

	@Override
	public List<VechicleGps> getCompamyGpsData(String companeyID) {
		List<VechicleGps> ret = new ArrayList<VechicleGps>();
		do {
			List<Truck> trucks = getBindTruckByCompanyID(companeyID);
			if (trucks == null || trucks.size() == 0) {
				break;
			}
			Comparator<Truck> cmpComparator = new Comparator<Truck>() {

				@Override
				public int compare(Truck o1, Truck o2) {

					return o1.getGps_device_id().compareTo(
							o2.getGps_device_id());
				}

			};
			Collections.sort(trucks, cmpComparator);
			String[] deviceIDArray = new String[trucks.size()];
			int i = 0;
			for (Truck truckInfo : trucks) {
				deviceIDArray[i++] = truckInfo.getGps_device_id();
			}
			Jedis client = memStore.getClient();
			List<String> data = client.hmget("vehicleGpsData", deviceIDArray);
			for (String json : data) {
				if (StringUtil.isEmpty(json))
					continue;
				VechicleGps gpsInfo = JsonPluginsUtil.jsonToBean(json,
						VechicleGps.class);
				Truck truckInfo = null;
				Truck serchInfo = new Truck();
				serchInfo.setGps_device_id(gpsInfo.getDeviceId());
				int idx = Collections.binarySearch(trucks, serchInfo,
						cmpComparator);
				if (idx != -1) {
					truckInfo = trucks.get(idx);
				}

				if (truckInfo != null) {
					gpsInfo.setCardNo(truckInfo.getTrack_no());
					if (truckInfo.getAccount() != null)
						gpsInfo.setDriverName(truckInfo.getAccount().getName());
					else {
						gpsInfo.setDriverName("未分配");
					}
				}
				ret.add(gpsInfo);
			}
		} while (false);
		return ret;
	}

	@Override
	public Map<String, Object> getTruckByID(String id) {
		return truckDao.getTruckByID(id);
	}
}
