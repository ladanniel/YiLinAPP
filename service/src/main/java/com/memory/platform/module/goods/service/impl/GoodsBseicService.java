package com.memory.platform.module.goods.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcContext;
import com.google.gson.Gson;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.base.Area;
import com.memory.platform.entity.goods.Contacts;
import com.memory.platform.entity.goods.GoodsAutLog;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsBasic.Status;
import com.memory.platform.entity.goods.GoodsBasicStockType;
import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.sys.NotifcationAction;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Arith;
import com.memory.platform.global.Coordinate;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.DoubleHelper;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.goods.dao.ContactsDao;
import com.memory.platform.module.goods.dao.GoodsAutLogDao;
import com.memory.platform.module.goods.dao.GoodsBasicDao;
import com.memory.platform.module.goods.dao.GoodsBasicStockTypeDao;
import com.memory.platform.module.goods.dao.GoodsDetailDao;
import com.memory.platform.module.goods.dao.GoodsTypeDao;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.dao.AreaDao;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.trace.service.IGaoDeWebService;

import freemarker.core.ReturnInstruction.Return;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年6月12日 下午4:10:44 修 改 人： 日 期： 描 述:货物管理业务接口实现类 版 本
 * 号： V1.0
 */

@Transactional
public class GoodsBseicService implements IGoodsBasicService {

	@Autowired
	private GoodsBasicDao goodsBasicDao;
	@Autowired
	private GoodsAutLogDao goodsAutLogDao;
	@Autowired
	private GoodsDetailDao goodsDetailDao;
	@Autowired
	private ContactsDao contactsDao;
	@Autowired
	private ISendMessageService sendMessageService;
	@Autowired
	private GoodsBasicStockTypeDao goodsBasicStockTypeDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private GoodsTypeDao goodsTypeDao;
	@Autowired
	private IGaoDeWebService gaodeService;
	@Autowired
	IPushService pushService;

	/*
	 * getPage
	 */
	@Override
	public Map<String, Object> getPage(GoodsBasic goodsBasic, int start, int limit) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = goodsBasicDao.getPage(goodsBasic, start, limit);
		return ret;
	}

	@Override
	public Map<String, Object> getOsPage(GoodsBasic goodsBasic, int start, int limit) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getOsPage(goodsBasic, start, limit);
	}

	@Override
	public GoodsBasic getGoodsBasicById(String id) {
		return goodsBasicDao.getObjectById(GoodsBasic.class, id);
	}

	@Override
	public Map<String, Object> getGoodsBasicDrivingPath(String goodsBasicID, int strageType) {
		Map<String, Object> ret = null;
		GoodsBasic goodsBasic = this.getGoodsBasicById(goodsBasicID);
		if (goodsBasic == null) {
			throw new BusinessException("货物信息不存在");
		}
		String end = goodsBasic.getConsigneeCoordinate();
		String start = goodsBasic.getDeliveryCoordinate();
		Coordinate startCoordinate = Coordinate.parse(start);
		Coordinate endCoordinate = Coordinate.parse(end);
		ret = gaodeService.getDrivingPath(startCoordinate, endCoordinate, strageType);
		return ret;
	}

	// @Override
	// public void updateGoodsBasic(GoodsBasic goodsBasic,GoodsAutLog
	// log,String[] array,Map<String, Object> logs) {
	//
	// if(!goodsBasic.getStatus().equals(Status.lock) &&
	// !goodsBasic.getStatus().equals(Status.apply)){
	// String sendMsg = "尊敬的货主用户您好！";
	// if(goodsBasic.getStatus().equals(Status.success)){
	// log.setTitle(log.getTitle() + "成功");
	// sendMsg += "您的发货信息已通过审核，货物单号：" + goodsBasic.getDeliveryNo();
	// }else if(goodsBasic.getStatus().equals(Status.back)){
	// log.setTitle(log.getTitle() + "退回");
	// sendMsg += "您提交的发货信息已被管理员退回，退回原因：(" + goodsBasic.getAuditCause()
	// +"),请重新编辑后再提交发货申请。货物单号：" + goodsBasic.getDeliveryNo();
	// }else{
	// log.setTitle(log.getTitle() + "销毁");
	// sendMsg += "您提交的发货信息已被管理员销毁，销毁原因：(" + goodsBasic.getAuditCause() +
	// ")，货物单号：" + goodsBasic.getDeliveryNo();
	// }
	// String inPoint =
	// "com.memory.platform.module.goods.service.impl.GoodsBseicService.updateGoodsBasic";
	// Map<String, Object> map =
	// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
	// sendMsg, inPoint);
	// if (Boolean.valueOf(map.get("success").toString())) {
	// goodsBasic.setOnLine(true);
	// goodsBasic.setHasLock(false);
	// } else {
	// throw new BusinessException("审核失败，原因短信提示异常，请联系管理员。");
	// }
	// }
	//
	// if(null != array){
	// for (String string : array) {
	// GoodsBasicStockType info = new GoodsBasicStockType();
	// info.setGoodsBasicId(goodsBasic.getId());
	// info.setStockTypeId(string);
	// goodsBasicStockTypeDao.save(info);
	// }
	// if(null != logs && logs.size() != 0){
	// List<GoodsDetail> list =
	// JsonPluginsUtil.jsonToBeanList(goodsBasic.getGoodsDetail(),GoodsDetail.class);
	// goodsDetailDao.updateGoodsDetailList(list);
	// }
	// }
	//
	// goodsBasicDao.update(goodsBasic);
	// goodsAutLogDao.save(log);
	// }

	@Override
	public void updateGoodsBasic(GoodsBasic goodsBasic, GoodsAutLog log, Map<String, Object> logs) {

		if (!goodsBasic.getStatus().equals(Status.lock) && !goodsBasic.getStatus().equals(Status.apply)) {
			String sendMsg = "尊敬的货主用户您好！";
			if (goodsBasic.getStatus().equals(Status.success)) {
				log.setTitle(log.getTitle() + "成功");
				sendMsg += "您的发货信息已通过审核，货物单号：" + goodsBasic.getDeliveryNo();
			} else if (goodsBasic.getStatus().equals(Status.back)) {
				log.setTitle(log.getTitle() + "退回");
				sendMsg += "您提交的发货信息已被管理员退回，退回原因：(" + goodsBasic.getAuditCause() + "),请重新编辑后再提交发货申请。货物单号："
						+ goodsBasic.getDeliveryNo();
			} else {
				log.setTitle(log.getTitle() + "销毁");
				sendMsg += "您提交的发货信息已被管理员销毁，销毁原因：(" + goodsBasic.getAuditCause() + ")，货物单号："
						+ goodsBasic.getDeliveryNo();
			}
			String inPoint = "com.memory.platform.module.goods.service.impl.GoodsBseicService.updateGoodsBasic";
			Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
					sendMsg, inPoint);
			if (Boolean.valueOf(map.get("success").toString())) {
				goodsBasic.setOnLine(true);
				goodsBasic.setHasLock(false);
			} else {
				throw new BusinessException("审核失败，原因短信提示异常，请联系管理员。");
			}
		}
		if (null != logs && logs.size() != 0) {
			List<GoodsDetail> list = JsonPluginsUtil.jsonToBeanList(goodsBasic.getGoodsDetail(), GoodsDetail.class);
			goodsDetailDao.updateGoodsDetailList(list);
		}
		goodsBasicDao.update(goodsBasic);
		goodsAutLogDao.save(log);
	}

	/*
	 * saveGoodsBasicDetail
	 */
	@Override
	public void saveSubGoodsBasicDetail(GoodsBasic goodsBasic) {
		List<GoodsDetail> list = JsonPluginsUtil.jsonToBeanList(goodsBasic.getGoodsDetail(), GoodsDetail.class);
		for (GoodsDetail goodsDetail : list) {

			goodsDetail.setWeight(DoubleHelper.round(goodsDetail.getWeight()));
		}
		double sumWeight = getGoodsTotalWeight(list);
		if (StringUtils.isEmpty(goodsBasic.getDeliveryAreaId()))
			throw new BusinessException("发货人区域ID不能为空");
		if (StringUtils.isEmpty(goodsBasic.getConsigneeAreaId()))
			throw new BusinessException("收货人区域ID不能为空");
		Area deliveryArea = areaDao.getObjectById(Area.class, goodsBasic.getDeliveryAreaId());
		Area consigneeArea = areaDao.getObjectById(Area.class, goodsBasic.getConsigneeAreaId());
		goodsBasic.setDeliveryAreaName(deliveryArea.getFull_name());
		goodsBasic.setConsigneeAreaName(consigneeArea.getFull_name());
		goodsBasic.setConsigneeCoordinate(consigneeArea.getLng() + "," + consigneeArea.getLat());
		goodsBasic.setDeliveryCoordinate(deliveryArea.getLng() + "," + deliveryArea.getLat());
		// 生成高德驾驶路径速度优先不考虑路况策略
		Map<String, Object> path = gaodeService.getDrivingPath(Coordinate.parse(goodsBasic.getDeliveryCoordinate()),
				Coordinate.parse(goodsBasic.getConsigneeCoordinate()), 0);
		long lngDistance = Long.parseLong(path.get("distance").toString());

		float distance = lngDistance / 1000.0f;
		goodsBasic.setMapKilometer(distance + "");
		// String tokenID =RpcContext.getContext().getAttachment("tokenID");
		Account account = UserUtil.getAccount();

		goodsBasic.setAccount(account);
		goodsBasic.setDeliveryNo("DG" + DateUtil.dateNo());
		goodsBasic.setAdd_user_id(account.getId());
		goodsBasic.setCreate_time(new Date());
		goodsBasic.setCompanyId(account.getCompany().getId());
		goodsBasic.setCompanyName(account.getCompany().getName());
		goodsBasic.setReleaseTime(new Date());
		goodsBasic.setTotalWeight(sumWeight);
		goodsBasic.setTotalPrice(Arith.mul(goodsBasic.getUnitPrice(), sumWeight, 2));
		GoodsType tempType = new GoodsType();
		tempType.setId(list.get(0).getGoodsTypeId());
		goodsBasic.setGoodsType(tempType);
		if (goodsBasic.isLongTime()) {
			goodsBasic.setFiniteTime(null);
		} else {
			goodsBasic.setFiniteTime(DateUtil.stringToDate(goodsBasic.getFiniteTimeStr(), null));
		}

		if (goodsBasic.isSaveDelivery()) {
			Contacts contacts = new Contacts();
			contacts.setAdd_user_id(account.getId());
			contacts.setCreate_time(new Date());
			contacts.setAreaFullName(goodsBasic.getDeliveryAreaName());
			contacts.setAreaId(goodsBasic.getDeliveryAreaId());
			contacts.setCompanyId(account.getCompany().getId());
			contacts.setAddress(goodsBasic.getDeliveryAddress());
			contacts.setEmail(goodsBasic.getDeliveryEmail());
			contacts.setMobile(goodsBasic.getDeliveryMobile());
			contacts.setName(goodsBasic.getDeliveryName());
			contacts.setPoint(goodsBasic.getDeliveryCoordinate());
			contacts.setContactsType(Contacts.ContactsType.consignor);
			contactsDao.save(contacts);
		}
		// 是否保存收货人信息
		if (goodsBasic.isSaveConsignee()) {
			Contacts contacts = new Contacts();
			contacts.setAdd_user_id(account.getId());
			contacts.setCreate_time(new Date());
			contacts.setAreaFullName(goodsBasic.getConsigneeAreaName());
			contacts.setAreaId(goodsBasic.getConsigneeAreaId());
			contacts.setCompanyId(account.getCompany().getId());
			contacts.setAddress(goodsBasic.getConsigneeAddress());
			contacts.setEmail(goodsBasic.getConsigneeEmail());
			contacts.setMobile(goodsBasic.getConsigneeMobile());
			contacts.setName(goodsBasic.getConsigneeName());
			contacts.setPoint(goodsBasic.getConsigneeCoordinate());
			contacts.setContactsType(Contacts.ContactsType.consignee);
			contactsDao.save(contacts);
		}
		// 是否保存发货人信息
		if (goodsBasic.isSaveConsignor()) {
			Contacts contacts = new Contacts();
			contacts.setAdd_user_id(account.getId());
			contacts.setCreate_time(new Date());
			contacts.setAreaFullName(goodsBasic.getDeliveryAreaName());
			contacts.setAreaId(goodsBasic.getConsigneeAreaId());
			contacts.setCompanyId(account.getCompany().getId());
			contacts.setAddress(goodsBasic.getDeliveryAddress());
			contacts.setEmail(goodsBasic.getDeliveryEmail());
			contacts.setMobile(goodsBasic.getDeliveryMobile());
			contacts.setName(goodsBasic.getDeliveryName());
			contacts.setPoint(goodsBasic.getDeliveryCoordinate());
			contacts.setContactsType(Contacts.ContactsType.consignor);
			contactsDao.save(contacts);
		}
		goodsBasic.setAuditPerson("系统");
		goodsBasic.setAuditTime(new Date());
		goodsBasic.setStatus(Status.success);
		goodsBasic.setOnLine(true);
		goodsBasicDao.save(goodsBasic);
		for (String string : goodsBasic.getStockTypes().split(",")) {
			GoodsBasicStockType info = new GoodsBasicStockType();
			info.setGoodsBasicId(goodsBasic.getId());
			info.setStockTypeId(string);
			goodsBasicStockTypeDao.save(info);
		}
		for (GoodsDetail goodsDetail : list) {
			GoodsType goodsType = new GoodsType();
			goodsType.setId(goodsDetail.getGoodsTypeId());
			goodsDetail.setGoodsType(goodsType);
			goodsDetail.setGoodsBasic(goodsBasic);
			// 加入goodsName
			if (StringUtil.isEmpty(goodsDetail.getGoods_name_id())) {
				throw new BusinessException("goods_name_id 不能为空");

			}
			GoodsType goodsName = goodsTypeDao.getObjectById(GoodsType.class, goodsDetail.getGoods_name_id());
			goodsDetail.setGoodsName(goodsName.getName());
		}
		goodsDetailDao.saveGoodsDetailList(list);
		if (goodsBasic.getStatus().toString().equals("apply")) {
			GoodsAutLog autLog = new GoodsAutLog();
			autLog.setAdd_user_id(account.getId());
			autLog.setCreate_time(new Date());
			autLog.setBeforeStatus(goodsBasic.getStatus());
			autLog.setAfterStatus(goodsBasic.getStatus());
			autLog.setGoodsBasic(goodsBasic);
			autLog.setRemark("提交发货申请！");
			autLog.setTitle("【货源发布】");
			autLog.setAuditPerson(account.getName());
			autLog.setAuditPersonId(account.getId());
			goodsAutLogDao.save(autLog);
		}
		String tmp = "尊敬的货主用户您好，您有一笔货物单号为：" + goodsBasic.getDeliveryNo() + "的货物信息发货成功，请您登录小镖人APP了解货物进度！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("goods_basic_id", goodsBasic.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic.getAccount().getId(), "【货源发布】", tmp, actionMap,
				"GoodsBseicService.saveSubGoodsBasicDetail");
	}

	public double getGoodsTotalWeight(List<GoodsDetail> list) {
		double sumWeight = 0;
		for (GoodsDetail goodsDetail : list) {
			sumWeight = sumWeight + goodsDetail.getWeight();
		}
		return Arith.round(sumWeight, 2);
	}

	/*
	 * editSubGoodsBasicDetail
	 */
	@Override
	public void editSubGoodsBasicDetail(GoodsBasic goodsBasic) {
		// TODO Auto-generated method stub
		GoodsBasic gasic = goodsBasicDao.getObjectById(GoodsBasic.class, goodsBasic.getId());
		List<GoodsDetail> list = JsonPluginsUtil.jsonToBeanList(goodsBasic.getGoodsDetail(), GoodsDetail.class);
		double sumWeight = getGoodsTotalWeight(list);
		Account account = UserUtil.getUser();
		goodsBasic.setAccount(account);
		goodsBasic.setUpdate_user_id(account.getId());
		goodsBasic.setUpdate_time(new Date());
		goodsBasic.setCompanyId(account.getCompany().getId());
		goodsBasic.setCompanyName(account.getCompany().getName());
		goodsBasic.setReleaseTime(new Date());
		goodsBasic.setTotalWeight(sumWeight);
		goodsBasic.setTotalPrice(Arith.mul(goodsBasic.getUnitPrice(), sumWeight, 2));
		goodsBasic.setVersion(gasic.getVersion());
		goodsBasic.setAdd_user_id(account.getId());
		GoodsType tempType = new GoodsType();
		tempType.setId(list.get(0).getGoodsTypeId());
		goodsBasic.setGoodsType(tempType);
		try {
			goodsBasic.setCreate_time(gasic.getCreate_time());
		} catch (Exception e) {

		}
		if (goodsBasic.isLongTime()) {
			goodsBasic.setFiniteTime(null);
		} else {
			goodsBasic.setFiniteTime(DateUtil.stringToDate(goodsBasic.getFiniteTimeStr(), null));
		}
		for (GoodsDetail goodsDetail : list) {
			GoodsType goodsType = new GoodsType();
			goodsType.setId(goodsDetail.getGoodsTypeId());
			goodsDetail.setGoodsType(goodsType);
			goodsDetail.setGoodsBasic(gasic);
			goodsDetail.setId(null);
		}
		goodsDetailDao.deleteGoodsDetailList(goodsBasic.getId());
		goodsDetailDao.saveGoodsDetailList(list);
		goodsBasicDao.merge(goodsBasic);
		if (goodsBasic.getStatus().toString().equals("apply")) {
			GoodsAutLog autLog = new GoodsAutLog();
			autLog.setAdd_user_id(account.getId());
			autLog.setCreate_time(new Date());
			autLog.setBeforeStatus(gasic.getStatus());
			autLog.setAfterStatus(goodsBasic.getStatus());
			autLog.setGoodsBasic(gasic);
			autLog.setRemark("提交发货申请!");
			autLog.setTitle("【发货申请】");
			autLog.setAuditPerson(account.getName());
			autLog.setAuditPersonId(account.getId());
			goodsAutLogDao.save(autLog);
		}

	}

	/*
	 * removeGoodsBasicDetail
	 */
	@Override
	public void removeGoodsBasicDetail(String id, GoodsBasic goodsBasic) {
		Account account = UserUtil.getUser();
		goodsBasic.setStatus(GoodsBasic.Status.scrap);
		goodsBasicDao.merge(goodsBasic);
		GoodsAutLog autLog = new GoodsAutLog();
		autLog.setAdd_user_id(account.getId());
		autLog.setCreate_time(new Date());
		autLog.setBeforeStatus(GoodsBasic.Status.scrap);
		autLog.setAfterStatus(goodsBasic.getStatus());
		autLog.setGoodsBasic(goodsBasic);
		autLog.setRemark("删除发货信息，取消发货！");
		autLog.setAuditPerson(account.getName());
		autLog.setAuditPersonId(account.getId());
		autLog.setTitle("【取消发货】");
		goodsAutLogDao.save(autLog);

	}

	/*
	 * translateGoodsBasicDetail
	 */
	@Override
	public void updateGoodsBasicTranslate(GoodsBasic goodsBasic) {
		Account account = UserUtil.getUser();
		goodsBasic.setStatus(GoodsBasic.Status.save);
		goodsBasicDao.merge(goodsBasic);
		GoodsAutLog autLog = new GoodsAutLog();
		autLog.setAdd_user_id(account.getId());
		autLog.setCreate_time(new Date());
		autLog.setBeforeStatus(GoodsBasic.Status.apply);
		autLog.setAfterStatus(GoodsBasic.Status.save);
		autLog.setGoodsBasic(goodsBasic);
		autLog.setRemark("撤回发货申请！");
		autLog.setAuditPerson(account.getName());
		autLog.setAuditPersonId(account.getId());
		autLog.setTitle("【撤回发货申请】");
		goodsAutLogDao.save(autLog);

	}

	/*
	 * saveSubGoodsBasic
	 */
	@Override
	public void saveSubGoodsBasic(GoodsBasic goodsBasic) {
		// TODO Auto-generated method stub
		goodsBasicDao.save(goodsBasic);
	}

	@Override
	public Map<String, Object> getLogPage(GoodsBasic goodsBasic, int start, int limit) {
		return goodsBasicDao.getLogPage(goodsBasic, start, limit);
	}

	/*
	 * getGoodsBasicPage
	 */
	@Override
	public Map<String, Object> getGoodsBasicPage(GoodsBasic goodsBasic, int start, int limit) {
		// TODO Auto-generated method stub
		Account account = UserUtil.getUser();
		return goodsBasicDao.getGoodsBasicPage(goodsBasic, account, start, limit);
	}

	@Override
	public Map<String, Object> getPageSuccess(GoodsBasic goodsBasic, int start, int limit) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getPageSuccess(goodsBasic, start, limit);
	}

	@Override
	public Map<String, Object> getRecordPage(GoodsBasic goodsBasic, int start, int limit) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getRecordPage(goodsBasic, start, limit);
	}

	/*
	 * updateGoodsBasicOnLine
	 */
	@Override
	public void updateGoodsBasicOnLine(String id, boolean onLine) {
		goodsBasicDao.updateGoodsBasicOnLine(id, onLine);
	}

	@Override
	public Map<String, Object> getGoodsBasicPageSuccess(GoodsBasic goodsBasic, int start, int limit) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getGoodsBasicPageSuccess(goodsBasic, start, limit);
	}

	/*
	 * getAllGoodsBasicCount
	 */
	@Override
	public Map<String, Object> getAllGoodsBasicCount(String accountId) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getGoodsBasicCount(accountId);
	}

	/*
	 * getAllGoodsBasicChareCount
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllGoodsBasicMonthCount(List<String> months, String accountId) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getAllGoodsBasicDateCount(months, accountId, "month");
	}

	/*
	 * getAllGoodsBasicMonthWeight
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllGoodsBasicMonthWeight(List<String> months, String accountId) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getAllGoodsBasicDateWeight(months, accountId, "month");
	}

	/*
	 * getGoodsRankingStatistics
	 */
	@Override
	public Map<String, Object> getGoodsRankingStatistics(int ranking, String type) {
		Map<String, Object> map_v = new HashMap<String, Object>();
		List<Map<String, Object>> list = goodsBasicDao.getGoodsRankingStatistics(ranking, type);
		List<String> counts = new ArrayList<>();
		List<String> weights = new ArrayList<>();
		List<String> xAxis = new ArrayList<>();
		for (Map<String, Object> map : list) {
			xAxis.add(map.get("name").toString());
			weights.add(map.get("weight") == null ? "0" : map.get("weight").toString());
			counts.add(map.get("count").toString());
		}
		if (type.equals("all")) {
			map_v.put("counts", counts);
			map_v.put("weights", weights);
			map_v.put("xAxis", xAxis);
			map_v.put("legendData", null);
			map_v.put("success", true);
			map_v.put("msg", "统计数据成功！");
		} else if (type.equals("count")) {
			map_v.put("weights", null);
			map_v.put("counts", counts);
			map_v.put("xAxis", xAxis);
			map_v.put("legendData", new String[] { "发货单数" });
			map_v.put("success", true);
			map_v.put("msg", "统计数据成功！");
		} else if (type.equals("weight")) {
			map_v.put("weights", weights);
			map_v.put("counts", null);
			map_v.put("xAxis", xAxis);
			map_v.put("legendData", new String[] { "发货重量" });
			map_v.put("success", true);
			map_v.put("msg", "统计数据成功！");
		}

		return map_v;
	}

	/*
	 * getGoodsStatusCountStatistics
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsStatusCountStatistics(String accountId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> countMap = goodsBasicDao.getGoodsBasicStatusCount(accountId);
		Map<String, Object> dates = DateUtil.getDays(7);
		Gson gson = new Gson();
		map.put("countMap", countMap);
		map.put("xAxis", gson.toJson(dates.get("dateList_v")));
		map.put("countData", gson.toJson(goodsBasicDao
				.getAllGoodsBasicDateCount((List<String>) dates.get("dateList"), accountId, "day").get(0)));
		map.put("weightData", gson.toJson(goodsBasicDao
				.getAllGoodsBasicDateWeight((List<String>) dates.get("dateList"), accountId, "day").get(0)));
		return map;
	}

	@Override
	public Map<String, Object> getGoodsBasicPage(GoodsBasic goodsBasic, Account account, int start, int limit) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getGoodsBasicPage(goodsBasic, account, start, limit);
	}

	@Override
	public Map<String, Object> getGoodsBasicPageForMap(GoodsBasic goodsBasic, Account account, int start, int limit) {
		// TODO Auto-generated method stub
		return goodsBasicDao.getGoodsBasicPageForMap(goodsBasic, account, start, limit);
	}

	/*
	 * getGoodsBasicById
	 */
	@Override
	public Map<String, Object> getGoodsBasicByIdForMap(String id) {
		Map<String, Object> map = goodsBasicDao.getGoodsBasicById(id);
		List<Map<String, Object>> listMap = goodsBasicDao.getGoodsDetailList(id);
		map.put("goodsDetailList", listMap);
		return map;
	}

	@Override
	public Map<String, Object> getMyGoodsOrderPage(GoodsBasic goodsBasic, Account account, int start, int limit) {
		return goodsBasicDao.getMyGoodsOrderPage(goodsBasic, account, start, limit);
	}

	// 获取等待审核的订单列表
	@Override
	public Map<String, Object> getWaitOrdersReview(GoodsBasic goodsBasic, Account account, int start, int limit) {
		return goodsBasicDao.getWaitOrdersReview(goodsBasic, account, start, limit);
	}

	// 获取已审核的订单列表
	@Override
	public Map<String, Object> getAlreadyOrdersReview(GoodsBasic goodsBasic, Account account, int start, int limit) {
		return goodsBasicDao.getAlreadyOrdersReview(goodsBasic, account, start, limit);
	}

	// 获取需要确认发货的订单列表
	@Override
	public Map<String, Object> getConfirmDeliverGoodsOrders(GoodsBasic goodsBasic, Account account, int start,
			int limit) {
		return goodsBasicDao.getConfirmDeliverGoodsOrders(goodsBasic, account, start, limit);
	}

	// lix 2017-08-09 添加 用户判断车队用户对当前货源是否可以操作抢单
	@Override
	public Map<String, Object> getMyGoodsBasicStockTypeWithGoodsBasicID(String goodsBasicID, Account account) {
		// TODO Auto-generated method stub
		return goodsBasicStockTypeDao.getMyGoodsBasicStockTypeWithGoodsBasicID(goodsBasicID, account);
	}

	/*
	 * 获取我发布的货源
	 */
	@Override
	public Map<String, Object> getMyGoodsPage(GoodsBasic goodsBasic, Account account, int offset, int size) {
		return goodsBasicDao.getMyGoodsBasicPage(goodsBasic, account, offset, size);
		// return null;
	}

	@Override
	public void updateAllGoodsOnLine(String goodsBasicId) {
		goodsBasicDao.updateAllGoodsOnLine(goodsBasicId);
	}

	@Override
	public List<GoodsBasic> getGoodsBasicsListByOnLine() {
		return goodsBasicDao.getGoodsBasicsListByOnLine();
	}
}
