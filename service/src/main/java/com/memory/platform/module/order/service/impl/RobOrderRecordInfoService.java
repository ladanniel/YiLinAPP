package com.memory.platform.module.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.InitBinder;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Arith;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.dao.GoodsBasicDao;
import com.memory.platform.module.goods.dao.GoodsDetailDao;
import com.memory.platform.module.order.dao.RobOrderRecordDao;
import com.memory.platform.module.order.dao.RobOrderRecordInfoDao;
import com.memory.platform.module.order.service.IRobOrderRecordInfoService;
import com.memory.platform.module.truck.dao.TruckDao;
 

/**
 * 创 建 人： longqibo 日 期： 2016年6月17日 上午10:41:25 修 改 人： 日 期： 描 述： 订单详情服务实现类 版 本 号：
 * V1.0
 */
@Service
@Transactional
public class RobOrderRecordInfoService implements IRobOrderRecordInfoService {

	@Autowired
	private RobOrderRecordDao robOrderRecordDao;

	@Autowired
	private RobOrderRecordInfoDao robOrderRecordInfoDao;
	@Autowired
	private TruckDao TruckDao;

	@Autowired
	GoodsDetailDao goodsDetailDao;
	@Autowired
	private GoodsBasicDao goodsBasicDao;
	@Autowired
	TruckDao truckDao;

	@Override
	public void saveInfo(RobOrderRecordInfo info) {
		robOrderRecordInfoDao.save(info);
	}

	@Override
	public void updateInfo(RobOrderRecordInfo info) {
		robOrderRecordInfoDao.update(info);
	}

	@Override
	public RobOrderRecordInfo getInfoById(String id) {
		return robOrderRecordInfoDao
				.getObjectById(RobOrderRecordInfo.class, id);
	}

	@Override
	public List<Map<String, Object>> getInfoForList(String orderId) {
		return robOrderRecordInfoDao.getInfoForList(orderId);
	}

	@Override
	public List<RobOrderRecordInfo> getList(String orderId) {
		return robOrderRecordInfoDao.getList(orderId);
	}

	/*
	 * getListByRobOrderRecordId
	 */
	@Override
	public List<RobOrderRecordInfo> getListByRobOrderRecordId(String robOrderId) {
		// TODO Auto-generated method stub
		Account user = UserUtil.getUser();
		List<RobOrderRecordInfo> list = robOrderRecordInfoDao
				.getListByRobOrderRecordId(robOrderId);
		Truck truck = truckDao.getTruckByAcountNo(user.getId());
		if (user.getCompany().getCompanyType().getName().equals("个人司机")) {
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				robOrderRecordInfo.setStockId(truck.getId());
				robOrderRecordInfo.setStockName(truck.getTrack_no() + "---"
						+ truck.getAccount().getName() + "---"
						+ truck.getAccount().getPhone());
			}
		}
		return list;
	}

	/*
	 * getListByRobOrderRecordInfoByPid
	 */
	@Override
	public List<Map<String, Object>> getListByRobOrderRecordInfoByPid(
			String pid, String robOrderId) {
		// TODO Auto-generated method stub
		return robOrderRecordInfoDao.getListByRobOrderRecordInfoByPid(pid,
				robOrderId);
	}

	/*
	 * getRobOrderRecordInfoById
	 */
	@Override
	public RobOrderRecordInfo getRobOrderRecordInfoById(String id) {
		// TODO Auto-generated method stub
		return robOrderRecordInfoDao.getRobOrderRecordInfoById(id);
	}

	/*
	 * saveRobOrderSplit
	 */
	@Override
	public Map<String, Object> saveRobOrderSplit(String robOrderId,
			String robOrderRecordInfoId, Double surplusWeight,
			String splitRobOrderRecordInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Account user = UserUtil.getUser();
		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderId);
		RobOrderRecordInfo robOrderRecordInfo = this
				.getRobOrderRecordInfoById(robOrderRecordInfoId);
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				splitRobOrderRecordInfo, RobOrderRecordInfo.class);
		if (!robOrderRecordInfo.getRobOrderRecord().getAccount().getId()
				.equals(user.getId())) {
			map.put("success", false);
			map.put("msg", "你所拆分的货物不是自己的，不能进行拆分！");
			return map;
		}
		if (!robOrderRecordInfo.getRobOrderRecord().getId()
				.equals(robOrderRecord.getId())) {
			map.put("success", false);
			map.put("msg", "传入的抢单ID或抢单详细货物的ID不能对应！");
			return map;
		}
		if (robOrderRecordInfo.getOriginalWeight() != (surplusWeight + getRobOrderSplitWeight(list))) {
			map.put("success", false);
			map.put("msg", "货物拆分的重量不对，请检查拆分的货物重量！");
			return map;
		}
		robOrderRecordInfoDao
				.deleteRobOrderRecordInfoSplit(robOrderRecordInfoId);
		for (RobOrderRecordInfo robOrderRecordInfosave : list) {
			robOrderRecordInfosave.setParent(false);
			robOrderRecordInfosave.setSplit(false);
			robOrderRecordInfosave.setAdd_user_id(user.getId());
			robOrderRecordInfosave.setCreate_time(new Date());
			GoodsDetail goodsDetail = goodsDetailDao.getObjectById(
					GoodsDetail.class,
					robOrderRecordInfosave.getGoodsDetailId());
			robOrderRecordInfosave.setGoodsDetail(goodsDetail);
			robOrderRecordInfosave.setRobOrderRecord(robOrderRecord);
			robOrderRecordInfosave.setActualWeight(robOrderRecordInfosave
					.getWeight());
		}
		robOrderRecordInfoDao.saveRobOrderRecordInfo(list);
		if (list.size() == 0) {
			robOrderRecordInfo.setSplit(false);
		} else {
			robOrderRecordInfo.setSplit(true);
		}
		robOrderRecordInfo.setActualWeight(surplusWeight);
		robOrderRecordInfo.setWeight(surplusWeight);
		robOrderRecordInfoDao.update(robOrderRecordInfo);
		map.put("success", true);
		map.put("msg", "货物拆分成功！");
		return map;
	}

	/**
	 * 功能描述： 获取被拆分货物的总重量 输入参数: @param list 输入参数: @return 异 常： 创 建 人: yangjiaqiao
	 * 日 期: 2016年8月10日上午10:55:43 修 改 人: 日 期: 返 回：double
	 */
	public double getRobOrderSplitWeight(List<RobOrderRecordInfo> list) {
		double splitWeight = 0;
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			splitWeight += robOrderRecordInfo.getWeight();
		}
		return splitWeight;
	}

	@Override
	public List<RobOrderRecordInfo> getListByRobOrderRecord(String robOrderId,
			String truckID) {
		// TODO Auto-generated method stub
		return robOrderRecordInfoDao.getListByRobOrderRecord(robOrderId,
				truckID);
	}

	/*
	 * robOrderId：拆分的签单记录 robOrderRecordInfoId：被拆分的抢单详细
	 * splitRobOrderRecordInfo:拆分后的子记录
	 * [{'parentId':'4028814a5bd14d28015bd14e52840001','weight':'15'},
	 * {'parentId':'4028814a5bd14d28015bd14e52840001','weight':'10'}]
	 */
	@Override
	public Map<String, Object> saveRobOrderSplit(String robOrderId,
			String robOrderRecordInfoId, Double surplusWeight,
			String splitRobOrderRecordInfo, Account account) {
		Map<String, Object> map = new HashMap<String, Object>();
		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderId);
		RobOrderRecordInfo robOrderRecordInfo = robOrderRecordInfoDao
				.getObjectById(RobOrderRecordInfo.class, robOrderRecordInfoId);
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				splitRobOrderRecordInfo, RobOrderRecordInfo.class);
		if (!robOrderRecordInfo.getRobOrderRecord().getAccount().getId()
				.equals(account.getId())) {
			map.put("success", false);
			map.put("msg", "你所拆分的货物不是自己的，不能进行拆分！");
			return map;
		}
		if (!robOrderRecordInfo.getRobOrderRecord().getId()
				.equals(robOrderRecord.getId())) {
			map.put("success", false);
			map.put("msg", "传入的抢单ID或抢单详细货物的ID不能对应！");
			return map;
		}
		if (robOrderRecordInfo.getOriginalWeight() != (surplusWeight + getRobOrderSplitWeight(list))) {
			map.put("success", false);
			map.put("msg", "货物拆分的重量不对，请检查拆分的货物重量！");
			return map;
		}
		robOrderRecordInfoDao
				.deleteRobOrderRecordInfoSplit(robOrderRecordInfoId);
		for (RobOrderRecordInfo robOrderRecordInfosave : list) {
			robOrderRecordInfosave.setParent(false);
			robOrderRecordInfosave.setSplit(false);
			robOrderRecordInfosave.setAdd_user_id(account.getId());
			robOrderRecordInfosave.setCreate_time(new Date());
			robOrderRecordInfosave.setGoodsDetail(robOrderRecordInfo
					.getGoodsDetail());
			robOrderRecordInfosave.setRobOrderRecord(robOrderRecord);
			robOrderRecordInfosave.setActualWeight(robOrderRecordInfosave
					.getWeight());
		}
		robOrderRecordInfoDao.saveRobOrderRecordInfo(list);
		if (list.size() == 0) {
			robOrderRecordInfo.setSplit(false);
		} else {
			robOrderRecordInfo.setSplit(true);
		}
		robOrderRecordInfo.setActualWeight(surplusWeight);
		robOrderRecordInfo.setWeight(surplusWeight);
		robOrderRecordInfoDao.update(robOrderRecordInfo);
		map.put("success", true);
		map.put("msg", "货物拆分成功！");
		map.put("list", list);
		return map;
	}

	

}
