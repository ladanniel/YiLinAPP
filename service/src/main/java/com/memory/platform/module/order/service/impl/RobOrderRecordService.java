package com.memory.platform.module.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialType;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecord.Status;
import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.entity.sys.ParameterManage;
import com.memory.platform.entity.sys.ParameterManage.ParaType;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Arith;
import com.memory.platform.global.ArrayUtil;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StatusMap;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.goods.dao.GoodsBasicDao;
import com.memory.platform.module.goods.dao.GoodsDetailDao;
import com.memory.platform.module.order.dao.OrderAutLogDao;
import com.memory.platform.module.order.dao.RobOrderConfirmDao;
import com.memory.platform.module.order.dao.RobOrderRecordDao;
import com.memory.platform.module.order.dao.RobOrderRecordInfoDao;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.dao.AccountDao;
import com.memory.platform.module.system.dao.ParameterManageDao;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.system.service.impl.ParameterManageService;
import com.memory.platform.module.truck.dao.TruckDao;
import com.memory.platform.module.truck.service.ITruckService;

/**
 * 创 建 人： longqibo 日 期： 2016年6月17日 上午10:29:50 修 改 人： 日 期： 描 述： 订单记录接口服务实现类 版 本
 * 号： V1.0
 */
/**
 * @author rog
 *
 */
@Service
@Transactional
public class RobOrderRecordService implements IRobOrderRecordService {
	@Autowired
	private RobOrderRecordDao robOrderRecordDao;
	@Autowired
	private RobOrderRecordInfoDao robOrderRecordInfoDao;
	@Autowired
	private OrderAutLogDao orderAutLogDao;
	@Autowired
	private GoodsBasicDao goodsBasicDao;
	@Autowired
	private GoodsDetailDao goodsDetailDao;
	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private ParameterManageDao parameterManageDao;
	@Autowired
	private ISendMessageService sendMessageService;
	@Autowired
	private TruckDao truckDao;
	@Autowired
	private ITruckService truckService;
	@Autowired
	private RobOrderConfirmDao robOrderConfirmDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ParameterManageService parameterManageService;

	@Autowired
	IPushService pushService;

	@Override
	public Map<String, Object> getPage(String goodsId, int start, int limit) {
		return robOrderRecordDao.getPageByGoodsId(goodsId, start, limit);
	}

	@Override
	
	public Map<String, Object> saveRecord(RobOrderRecord record) {
		Map<String, Object> map_res = new HashMap<String, Object>();
		boolean isfalse = true;
		String msg = "";
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.saveRecord";
		GoodsBasic goodsBasic = goodsBasicDao.getObjectById(GoodsBasic.class,
				record.getGoodsBasic().getId());
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				record.getRobOrderRecordInfos(), RobOrderRecordInfo.class);
		double sumWeight = getGoodsTotalWeight(list);
		Account account = UserUtil.getUser().getCompany().getName() == null
				&& UserUtil.getUser().getCompany().getName().equals("") ? accountDao
				.getAccount(UserUtil.getUser().getId()) : UserUtil.getUser();
		record.setRobOrderNo("YLOR" + DateUtil.dateNo());
		record.setAccount(account);
		record.setAdd_user_id(account.getId());
		record.setCreate_time(new Date());
		record.setWeight(sumWeight);
		record.setCompanyName(account.getCompany().getName());
		record.setTotalPrice(Arith.mul(record.getUnitPrice(), sumWeight, 4));
		record.setGoodsBasic(goodsBasic);
		List<String> goodsTypeNames = new ArrayList<String>();
		record.setRobbedAccountId(goodsBasic.getAccount().getId());
		record.setRobbedCompanyId(goodsBasic.getCompanyId());
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			robOrderRecordInfo.setAdd_user_id(account.getId());
			robOrderRecordInfo.setCreate_time(new Date());
			GoodsDetail goodsDetail = goodsDetailDao.getObjectById(
					GoodsDetail.class, robOrderRecordInfo.getGoodsDetailId());
			if (!goodsDetail.getGoodsBasic().getId()
					.equals(record.getGoodsBasic().getId())) {
				isfalse = false;
				msg += "您所抢的详细货物不是正确货源信息！";
				break;
			}
			if (robOrderRecordInfo.getWeight() == 0) {
				isfalse = false;
				msg += "货物类型：［" + goodsDetail.getGoodsType().getName()
						+ "］, 货物名称：［" + goodsDetail.getGoodsName()
						+ "］,货物抢单重量必须大于0吨;";
			}
			if (goodsDetail.getSurplusWeight() == 0) {
				isfalse = false;
				msg += "货物类型：［" + goodsDetail.getGoodsType().getName()
						+ "］, 货物名称：［" + goodsDetail.getGoodsName()
						+ "］,货物已被抢单，剩余重量为：0吨;";
			}
			if (!goodsTypeNames.contains(goodsDetail.getGoodsType().getName())) {
				goodsTypeNames.add(goodsDetail.getGoodsType().getName());
			}
			robOrderRecordInfo.setGoodsDetail(goodsDetail);
			robOrderRecordInfo.setRobOrderRecord(record);
			robOrderRecordInfo.setParent(true);
			robOrderRecordInfo.setSplit(false);
			robOrderRecordInfo
					.setOriginalWeight(robOrderRecordInfo.getWeight());// 设置原有货物重量
		}
		if (!isfalse) {
			map_res.put("success", false);
			map_res.put("msg", msg + "请重新填写重量！");
			return map_res;
		}
		robOrderRecordDao.save(record);
		robOrderRecordInfoDao.saveRobOrderRecordInfo(list);
		record.setGoodsTypes(ArrayUtil.listToString(goodsTypeNames));
		robOrderRecordDao.merge(record);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.apply);
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setTitle("【抢单申请】");
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
		String sendMsg = "尊敬的货主用户您好，您有一笔货物单号为：" + goodsBasic.getDeliveryNo()
				+ "的货物已被司机用户抢单,抢单单号为：" + record.getRobOrderNo()
				+ "，请您尽快登录小镖人APP进行抢单审核操作！";
		Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(
				goodsBasic.getAccount().getPhone(), sendMsg, inPoint);
		if (!Boolean.valueOf(map.get("success").toString())) {
			throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		}
		map_res.put("success", true);
		map_res.put("msg", "抢单成功，已提交给货主，等待货主确认！");
		return map_res;
	}

	@Override
	public void updateRecord(RobOrderRecord record, OrderAutLog log) {
		orderAutLogDao.save(log);
		robOrderRecordDao.update(record);
	}

	@Override
	public RobOrderRecord getRecordById(String id) {
		return robOrderRecordDao.getObjectById(RobOrderRecord.class, id);
	}

	public double getGoodsTotalWeight(List<RobOrderRecordInfo> list) {
		double sumWeight = 0;
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			sumWeight = sumWeight + robOrderRecordInfo.getWeight();
		}
		return Arith.round(sumWeight, 2);
	}

	@Override
	public void updateStatus(RobOrderRecord record, String payPassword) {
		Account user = UserUtil.getUser();
		RobOrderRecord prent = getRecordById(record.getId());
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateStatus";

		if (!prent.getStatus().equals(Status.dealing)) {
			throw new BusinessException("处理中的数据才能审核，其他状态下无法做此操作。");
		}

		if (record.getStatus().equals(Status.success)) {
			if (StringUtil.isEmpty(payPassword)) {
				throw new BusinessException("请输入支付密码。");
			}
			if (!AppUtil.md5(payPassword).equals(user.getPaypassword())) {
				throw new BusinessException("您输入的支付密码不正确。");
			}
			double weight = 0;
			GoodsBasic goodsBasic = null;
			List<RobOrderRecordInfo> list = robOrderRecordInfoDao
					.getListByRobOrderRecordId(prent.getId());
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				GoodsDetail detail = robOrderRecordInfo.getGoodsDetail();
				goodsBasic = detail.getGoodsBasic();
				if (detail.getWeight() == detail.getEmbarkWeight()) {
					robOrderRecordInfo.setActualWeight(0);
					robOrderRecordInfoDao.update(robOrderRecordInfo);
				} else {
					if (detail.getEmbarkWeight()
							+ robOrderRecordInfo.getWeight() > detail
								.getWeight()) {
						robOrderRecordInfo.setActualWeight(detail.getWeight()
								- detail.getEmbarkWeight());
						detail.setEmbarkWeight(detail.getWeight());
					} else {
						robOrderRecordInfo.setActualWeight(robOrderRecordInfo
								.getWeight());
						detail.setEmbarkWeight(detail.getEmbarkWeight()
								+ robOrderRecordInfo.getActualWeight());
					}
				}
				goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight()
						+ robOrderRecordInfo.getActualWeight());
				weight += robOrderRecordInfo.getActualWeight();

				// 系统自动处理失效抢单
				if (goodsBasic.getTotalWeight() == goodsBasic.getEmbarkWeight()) {
					List<RobOrderRecord> list1 = robOrderRecordDao
							.getRobOrderRecordListByStatus();
					for (RobOrderRecord robOrderRecord : list1) {
						if (!robOrderRecord.getId().equals(prent.getId())) {
							robOrderRecord
									.setStatus(RobOrderRecord.Status.scrap);
							robOrderRecordDao.update(robOrderRecord);

							OrderAutLog orderAutLog = new OrderAutLog();
							orderAutLog.setRobOrderRecord(robOrderRecord);
							orderAutLog.setAfterStatus(robOrderRecord
									.getStatus());
							orderAutLog
									.setBeforeStatus(RobOrderRecord.Status.apply);
							orderAutLog.setRemark("系统自动处理，发货货物已被抢完。该抢单失效。");
							orderAutLog.setTitle("【抢单销毁】");
							orderAutLog.setCreate_time(new Date());
							orderAutLog.setAuditPerson("系统自动处理");
							orderAutLog.setAuditPersonId(user.getId());
							orderAutLogDao.save(orderAutLog);
							String sendMsg = "尊敬的司机用户您好，您有一笔抢单单号为："
									+ robOrderRecord.getRobOrderNo()
									+ "的抢单信息由于货物已被抢完，系统自动处理该抢单失效。请您尽快登录小镖人APP查看详情！";
							Map<String, Object> map = sendMessageService
									.saveRecordAndSendMessage(robOrderRecord
											.getAccount().getPhone(), sendMsg,
											inPoint);
							if (!Boolean.valueOf(map.get("success").toString())) {
								throw new BusinessException(
										"短信提示异常，请联系管理员,审核失败。");
							}
						}
					}
				}
				goodsDetailDao.update(detail);
			}
			goodsBasicDao.update(goodsBasic);

			ParameterManage parameterManage = parameterManageDao
					.getTypeInfo(ParaType.consignor);
			prent.setDepositUnitPrice(parameterManage.getValue());
			BigDecimal money = new BigDecimal(parameterManage.getValue())
					.multiply(new BigDecimal(weight));
			String remark = "抢单审核成功，冻结预付款金额：" + String.format("%.2f", money)
					+ "元，发货单号：" + goodsBasic.getDeliveryNo() + ",抢单单号："
					+ prent.getRobOrderNo();
			capitalAccountService.saveFreezeTool(user, money, remark,
					user.getName());
			prent.setWeight(weight);

		}

		prent.setStatus(record.getStatus());
		prent.setRemark(record.getRemark());

		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(prent);
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAfterStatus(record.getStatus());
		orderAutLog.setBeforeStatus(prent.getStatus());
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(user.getName());
		orderAutLog.setAuditPersonId(user.getId());

		String msg = "尊敬的司机用户您好，";
		if (record.getStatus().equals(Status.success)) {
			orderAutLog.setTitle("【抢单审核】");
			msg += "您的有一笔抢单单号为：" + prent.getRobOrderNo()
					+ "的抢单信息已经通过货主用户审核成功，请您尽登录小镖人APP尽快处理该抢单信息！";
		} else if (record.getStatus().equals(Status.back)) {
			orderAutLog.setTitle("【抢单退回】");
			msg += "您的有一笔抢单单号为:" + prent.getRobOrderNo()
					+ "的抢单信息已经被货主用户退回，请您尽快登录小镖人APP查看详情！";
		} else if (record.getStatus().equals(Status.scrap)) {
			orderAutLog.setTitle("【抢单销毁】");
			msg += "您的有一笔抢单单号为:" + prent.getRobOrderNo()
					+ "，的抢单信息已销毁，该抢单无效。请您尽快登录小镖人APP查看详情！";
		}
		Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(
				prent.getAccount().getPhone(), msg + record.getRemark(),
				inPoint);
		if (!Boolean.valueOf(map.get("success").toString())) {
			throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		}
		orderAutLogDao.save(orderAutLog);
		robOrderRecordDao.update(prent);

	}

	//*货主审核确认抢单
	@Override
	public String updateStatus2(RobOrderRecord record, String payPassword) {
		Account user = UserUtil.getAccount();
		RobOrderRecord prent = getRecordById(record.getId());
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateStatus";
		String ret = "";
		List<RobOrderRecord> invalidateRoborders = new ArrayList<RobOrderRecord>();
		if (record.getStatus().equals(Status.success)) {
			if (!prent.getStatus().equals(Status.dealing)
					&& !prent.getStatus().equals(Status.apply)) {
				throw new BusinessException("该抢单状态已经被更改 不能审核 ");
			}
			if (StringUtil.isEmpty(payPassword)) {
				throw new BusinessException("请输入支付密码。");
			}
			if (!AppUtil.md5(payPassword).equals(user.getPaypassword())) {
				throw new BusinessException("您输入的支付密码不正确。");
			}
			
			
			double weight = 0;
			GoodsBasic goodsBasic = null;
			List<RobOrderRecordInfo> list = robOrderRecordInfoDao
					.getListByRobOrderRecordId(prent.getId());
			Boolean isdir = false;
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				GoodsDetail detail = robOrderRecordInfo.getGoodsDetail();
				goodsBasic = detail.getGoodsBasic();
				// if (detail.getWeight() == detail.getEmbarkWeight()) {
				// robOrderRecordInfo.setActualWeight(0);
				// robOrderRecordInfoDao.update(robOrderRecordInfo);
				// } else {
				if ((detail.getEmbarkWeight() + robOrderRecordInfo.getWeight() > detail
						.getWeight())
						|| detail.getWeight() == detail.getEmbarkWeight()) {

					prent.setStatus(RobOrderRecord.Status.scrap);
					robOrderRecordDao.update(prent);

					OrderAutLog orderAutLog = new OrderAutLog();
					orderAutLog.setRobOrderRecord(prent);
					orderAutLog.setAfterStatus(prent.getStatus());
					orderAutLog.setBeforeStatus(RobOrderRecord.Status.apply);
					orderAutLog.setRemark("系统自动处理，抢单吨位已经超过剩余吨位。该抢单失效。");
					orderAutLog.setTitle("【抢单销毁】");
					orderAutLog.setCreate_time(new Date());
					orderAutLog.setAuditPerson("系统自动处理");
					orderAutLog.setAuditPersonId(user.getId());
					orderAutLogDao.save(orderAutLog);
					// lix 2017-08-14 注释 修改通知方式
					// String sendMsg = "尊敬的用户您好！您的订单号为：" +
					// prent.getRobOrderNo() + "的抢单由于超过剩余吨位，该抢单失效。";
					// Map<String, Object> map = sendMessageService
					// .saveRecordAndSendMessage(prent.getAccount().getPhone(),
					// sendMsg, inPoint);
					// throw new BusinessException("该抢单已经超过剩余吨位 已被系统销毁");

					// lix 2017-08-14 添加 修改通知方式
					String sendMsg = "尊敬的司机用户您好，您有一笔抢单单号为："
							+ prent.getRobOrderNo()
							+ "的抢单信息由于超过剩余吨位，系统自动处理该抢单失效，请您尽快登录小镖人APP查看详情！";
					Map<String, String> actionMap = new HashMap<String, String>();
					actionMap.put("rob_order_record_id", prent.getId());
					pushService.saveRecordAndSendMessageWithAccountID(prent
							.getAccount().getId(), "【抢单销毁】", sendMsg,
							actionMap, "RobOrderRecordService.updateStatus2");

					ret = "该抢单已经超过剩余吨位 已被系统销毁";
					return ret;

				} else {
					robOrderRecordInfo.setActualWeight(robOrderRecordInfo
							.getWeight());
					detail.setEmbarkWeight(detail.getEmbarkWeight()
							+ robOrderRecordInfo.getActualWeight());
				}
				// }
				goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight()
						+ robOrderRecordInfo.getActualWeight());
				weight += robOrderRecordInfo.getActualWeight();
				// 查询失效的抢单信息
				double surplusDetailWeight = detail.getWeight()
						- detail.getEmbarkWeight();
				List<Map<String, Object>> robOrderIDSList = robOrderRecordDao
						.getInvalidRobOrderRecord(surplusDetailWeight,
								goodsBasic.getId(), detail.getId(),
								prent.getId());
				// 系统自动处理失效抢单
				for (Map<String, Object> map : robOrderIDSList) {
					String id = map.get("rob_order_record_id").toString();
					RobOrderRecord invalidateRecord = robOrderRecordDao
							.getObjectById(RobOrderRecord.class, id);
					if (invalidateRoborders.indexOf(invalidateRecord) == -1) {
						invalidateRecord.setStatus(RobOrderRecord.Status.scrap);
						robOrderRecordDao.update(invalidateRecord);
						invalidateRoborders.add(invalidateRecord);
						OrderAutLog orderAutLog = new OrderAutLog();
						orderAutLog.setRobOrderRecord(invalidateRecord);
						orderAutLog
								.setAfterStatus(invalidateRecord.getStatus());
						orderAutLog
								.setBeforeStatus(RobOrderRecord.Status.apply);
						orderAutLog.setRemark("系统自动处理，发货货物已被抢完。该抢单失效。");
						orderAutLog.setTitle("【抢单销毁】");
						orderAutLog.setCreate_time(new Date());
						orderAutLog.setAuditPerson("系统自动处理");
						orderAutLog.setAuditPersonId(user.getId());
						orderAutLogDao.save(orderAutLog);

						// lix 2017-08-14 注释 修改通知方式
						// String sendMsg = "尊敬的用户您好！您的订单号为：" +
						// robOrderRecord.getRobOrderNo() +
						// "的抢单由于货物已被抢完，该抢单失效。";
						// Map<String, Object> map = sendMessageService
						//
						// .saveRecordAndSendMessage(robOrderRecord.getAccount().getPhone(),
						// sendMsg, inPoint);
						// if
						// (!Boolean.valueOf(map.get("success").toString()))
						// {
						// throw new
						// BusinessException("短信提示异常，请联系管理员,审核失败。");
						// }
						// lix 2017-08-14 添加 修改通知方式
						String sendMsg = "尊敬的司机用户您好，您有一笔抢单单号为："
								+ invalidateRecord.getRobOrderNo()
								+ "的抢单信息由于货物已被抢完，系统自动处理该抢单失效，请您尽快登录小镖人APP查看详情！";
						Map<String, String> actionMap = new HashMap<String, String>();
						actionMap.put("rob_order_record_id",
								invalidateRecord.getId());
						pushService.saveRecordAndSendMessageWithAccountID(
								invalidateRecord.getAccount().getId(),
								"【抢单销毁】", sendMsg, actionMap,
								"RobOrderRecordService.updateStatus2");
					}
				}
				goodsDetailDao.update(detail);
			}
			goodsBasicDao.update(goodsBasic);

			ParameterManage parameterManage = parameterManageDao
					.getTypeInfo(ParaType.consignor);
//			prent.setDepositUnitPrice(parameterManage.getValue());
			BigDecimal money = new BigDecimal(prent.getDeposit_level_money());//BigDecimal防止损失精度
			String remark = "抢单审核成功，冻结预付款金额：" + String.format("%.2f", prent.getDeposit_level_money())
					+ "元，发货单号：" + goodsBasic.getDeliveryNo() + ",抢单单号："
					+ prent.getRobOrderNo();
			capitalAccountService.saveFreezeTool(user, money, remark, //冻结资金
					user.getName());
			prent.setWeight(weight);

		}

		prent.setStatus(record.getStatus());
		// prent.setRemark(record.getRemark());

		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(prent);
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAfterStatus(record.getStatus());
		orderAutLog.setBeforeStatus(prent.getStatus());
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(user.getName());
		orderAutLog.setAuditPersonId(user.getId());

		// lix 2017-08-14 注释 修改通知方式
		// String msg = "尊敬的用户您好：";
		// if (record.getStatus().equals(Status.success)) {
		// orderAutLog.setTitle("【审核】成功");
		// msg += "[抢单成功]，您的抢单已通过，请尽快处理该抢单信息。单号：" + prent.getRobOrderNo();
		// } else if (record.getStatus().equals(Status.back)) {
		// orderAutLog.setTitle("【审核】退回");
		// msg += "[抢单失效]，您的抢单已退回，请尽快与货主协商。单号：" + prent.getRobOrderNo();
		// } else if (record.getStatus().equals(Status.scrap)) {
		// orderAutLog.setTitle("【审核】销毁");
		// msg += "[抢单失败]，您的抢单已销毁，该抢单无效。单号：" + prent.getRobOrderNo();
		// }
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(prent.getAccount().getPhone(),
		// msg + record.getRemark(), inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改通知方式
		String tmp = "尊敬的司机用户您好，";
		String strTitle = "";
		Map<String, String> actionMap = new HashMap<String, String>();
		if (record.getStatus().equals(Status.success)) {
			orderAutLog.setTitle("【抢单审核】");
			tmp += "您有一笔抢单单号为：" + prent.getRobOrderNo()
					+ "的抢单信息已经通过货主用户审核成功，请您尽快登录小镖人APP尽快处理该抢单信息！";
			strTitle = "【抢单审核】";
		} else if (record.getStatus().equals(Status.back)) {
			orderAutLog.setTitle("【抢单退回】");
			tmp += "您的有一笔抢单单号为:" + prent.getRobOrderNo()
					+ "的抢单信息已经被货主用户退回，请您尽快登录小镖人APP查看详情！";
			strTitle = "【抢单退回】";
		} else if (record.getStatus().equals(Status.scrap)) {
			orderAutLog.setTitle("【抢单销毁】");
			tmp += "您的有一笔抢单单号为:" + prent.getRobOrderNo()
					+ "，的抢单信息已销毁，该抢单无效。请您尽快登录小镖人APP查看详情！";
			strTitle = "【抢单销毁】";
		}

		orderAutLogDao.save(orderAutLog);
		robOrderRecordDao.update(prent);
		actionMap.put("rob_order_record_id", prent.getId());
		pushService.saveRecordAndSendMessageWithAccountID(prent.getAccount()
				.getId(), strTitle, tmp, actionMap,
				"RobOrderRecordService.updateStatus2");
		return ret;
	}

	/*
	 * saveRobOrderCancel
	 */
	@Override
	public void updateRobOrderCancel(RobOrderRecord record, Account account,
			String type) {
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateRobOrderCancel";
		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, record.getId());
		String money_msg = "";
		GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
		if (robOrderRecord.getStatus().toString().equals("success")) {

			List<RobOrderRecordInfo> list = robOrderRecordInfoDao
					.getList(robOrderRecord.getId());
			// 获得货物总重量
			double weight = this.getGoodsActualWeight(null, list);
			ParameterManage parameterManage = parameterManageDao
					.getTypeInfo(ParaType.consignor);
//			BigDecimal money = new BigDecimal(parameterManage.getValue())
//					.multiply(new BigDecimal(weight));
			
			BigDecimal money = new BigDecimal(robOrderRecord.getDeposit_level_money());//
			String remark = "尊敬的货主用户您好，您有一笔抢单单号为："
					+ robOrderRecord.getRobOrderNo()
					+ "的抢单信息已被用户取消，解除冻结预付款资金，金额："
					+ String.format("%.2f", money) + "元";
			capitalAccountService.updateFreezeTool(goodsBasic.getAccount(),
					money, remark, "系统自动处理");
			// 修改货物基本信息的重量
			goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight() - weight);
			goodsBasicDao.merge(goodsBasic);
			List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				GoodsDetail goodsDetail = robOrderRecordInfo.getGoodsDetail();
				goodsDetail.setEmbarkWeight(goodsDetail.getEmbarkWeight()
						- robOrderRecordInfo.getActualWeight());
				goodsDetailList.add(goodsDetail);
			}
			this.goodsDetailDao.updateGoodsDetailList(goodsDetailList);
			money_msg = "由于司机用户取消抢单，你所支付的预付款资金(" + String.format("%.2f", money)
					+ ")元已解冻！";
		}
		robOrderRecord.setCancelAccount(account);
		robOrderRecord.setStatus(RobOrderRecord.Status.scrap);
		robOrderRecord.setCancelRemark(record.getCancelRemark());
		robOrderRecord.setCancelDate(new Date());
		robOrderRecordDao.merge(robOrderRecord);
		OrderAutLog orderAutLog = new OrderAutLog();
		if (StringUtils.isEmpty(type)) {
			orderAutLog.setRobOrderRecord(robOrderRecord);
			orderAutLog.setAfterStatus(RobOrderRecord.Status.scrap);
			orderAutLog.setBeforeStatus(record.getStatus());
			orderAutLog.setRemark(record.getCancelRemark());
			orderAutLog.setCreate_time(new Date());
			orderAutLog.setTitle("【抢单取消】");
			orderAutLog.setAuditPerson(account.getName());
			orderAutLog.setAuditPersonId(account.getId());
		} else {
			orderAutLog.setRobOrderRecord(robOrderRecord);
			orderAutLog.setAfterStatus(robOrderRecord.getStatus());
			orderAutLog.setBeforeStatus(RobOrderRecord.Status.apply);
			orderAutLog.setRemark("系统自动处理，发货货物已被抢完。该抢单失效。");
			orderAutLog.setTitle("【抢单销毁】");
			orderAutLog.setCreate_time(new Date());
			orderAutLog.setAuditPerson("系统自动处理");
			orderAutLog.setAuditPersonId(account.getId());
		}
		orderAutLogDao.save(orderAutLog);
		// lix 2017-08-14 注释 修改消息通知方式
		// String sendMsg = "尊敬的货主用户您好！您有一笔订单号为：" +
		// robOrderRecord.getRobOrderNo() + "的抢单信息，已被用户取消。" + money_msg;
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
		// sendMsg, inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }
		// lix 2017-08-14 添加 修改消息通知方式
		String tmp = "尊敬的货主用户您好，您有一笔抢单单号为：" + robOrderRecord.getRobOrderNo()
				+ "的抢单信息，已被司机用户取消。" + money_msg + "请您尽快登录小镖人APP查看详情！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_record_id", robOrderRecord.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic
				.getAccount().getId(), "【抢单取消】", tmp, actionMap,
				"RobOrderRecordService.updateRobOrderCancel");
	}

	/*
	 * getRobOrderCancelByDay
	 */
	@Override
	public int getRobOrderCancelByDay() {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getCancelRobOrderByDay();
	}

	/*
	 * getMyPage
	 */
	@Override
	public Map<String, Object> getMyPage(RobOrderRecord record, int start,
			int limit) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getMyPage(record, start, limit);
	}

	@Override
	public void updateRecord(RobOrderRecord record) {
		robOrderRecordDao.update(record);
	}

	/*
	 * updateWithdrawRobOrder
	 */
	@Override
	public void updateWithdrawRobOrder(RobOrderRecord robOrderRecord) {
		// TODO Auto-generated method stub
		Account account = UserUtil.getUser();
		robOrderRecord.setStatus(RobOrderRecord.Status.withdraw);
		robOrderRecordDao.merge(robOrderRecord);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(robOrderRecord);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.withdraw);
		orderAutLog.setBeforeStatus(robOrderRecord.getStatus());
		orderAutLog.setRemark(robOrderRecord.getRemark());
		orderAutLog.setTitle("【抢单撤回】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
	}

	/*
	 * updateRobOrderRecord
	 */
	@Override
	public void updateRobOrderRecord(RobOrderRecord record) {
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateRobOrderRecord";
		RobOrderRecord record_p = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, record.getId());
		String robOrderNo = record_p.getRobOrderNo();
		GoodsBasic goodsBasic = goodsBasicDao.getObjectById(GoodsBasic.class,
				record.getGoodsBasic().getId());
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				record.getRobOrderRecordInfos(), RobOrderRecordInfo.class);
		double sumWeight = getGoodsTotalWeight(list);
		Account account = UserUtil.getUser();
		record.setAccount(account);
		record.setUpdate_user_id(account.getId());
		record.setUpdate_time(new Date());
		record.setAdd_user_id(record_p.getAdd_user_id());
		record.setCancelDate(record_p.getCreate_time());
		record.setWeight(sumWeight);
		record.setCompanyName(account.getCompany().getName());
		record.setTotalPrice(Arith.mul(record.getUnitPrice(), sumWeight, 2));
		record.setGoodsBasic(goodsBasic);
		List<String> goodsTypeNames = new ArrayList<String>();
		List<RobOrderRecordInfo> robOrderRecordInfoList = new ArrayList<RobOrderRecordInfo>();
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			if (robOrderRecordInfo.getWeight() > 0) {
				robOrderRecordInfo.setAdd_user_id(account.getId());
				robOrderRecordInfo.setCreate_time(new Date());
				GoodsDetail goodsDetail = goodsDetailDao.getObjectById(
						GoodsDetail.class,
						robOrderRecordInfo.getGoodsDetailId());
				if (!goodsTypeNames.contains(goodsDetail.getGoodsType()
						.getName())) {
					goodsTypeNames.add(goodsDetail.getGoodsType().getName());
				}
				robOrderRecordInfo.setGoodsDetail(goodsDetail);
				robOrderRecordInfo.setRobOrderRecord(record_p);
				robOrderRecordInfoList.add(robOrderRecordInfo);
				robOrderRecordInfo.setParent(true);
				robOrderRecordInfo.setSplit(false);
				robOrderRecordInfo.setOriginalWeight(robOrderRecordInfo
						.getWeight());// 设置原有货物重量
			}
		}
		robOrderRecordInfoDao.deleteRobOrderRecordInfo(record.getId());
		robOrderRecordInfoDao.saveRobOrderRecordInfo(robOrderRecordInfoList);
		record.setGoodsTypes(ArrayUtil.listToString(goodsTypeNames));
		robOrderRecordDao.merge(record);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.apply);
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setTitle("【抢单申请】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
		String sendMsg = "尊敬的货主用户您好,您有一笔退回的抢单已被用户重新提交确认，抢单单号：" + robOrderNo
				+ "请及时审阅。";
		Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(
				goodsBasic.getAccount().getPhone(), sendMsg, inPoint);
		if (!Boolean.valueOf(map.get("success").toString())) {
			throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		}
	}

	/**
	 * 功能描述： 获取货物的拉货的实际重量 输入参数: @param list 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年6月30日下午4:44:24 修 改 人: 日 期: 返 回：double
	 */
	public double getGoodsActualWeight(String truckId,
			List<RobOrderRecordInfo> list) {
		if (truckId == null) {
			double sumWeight = 0;
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				sumWeight = sumWeight + robOrderRecordInfo.getActualWeight();
			}
			return Arith.round(sumWeight, 2);
		} else {
			double sumWeight = 0;
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				if (robOrderRecordInfo.getStockId().equals(truckId)) {
					sumWeight = sumWeight
							+ robOrderRecordInfo.getActualWeight();
				}
			}
			return Arith.round(sumWeight, 2);
		}

	}

	/*
	 * confirmRobOrder
	 */
	@Override
	public void saveConfirmRobOrder(RobOrderRecord robOrderRecord,
			String payPassword) {
		Account user = accountDao.getAccount(UserUtil.getUser().getId());
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.saveConfirmRobOrder";
		RobOrderRecord record_p = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderRecord.getId());
		GoodsBasic goodsBasic = goodsBasicDao.getObjectById(GoodsBasic.class,
				record_p.getGoodsBasic().getId());
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				robOrderRecord.getRobOrderRecordInfos(),
				RobOrderRecordInfo.class);
		if (StringUtil.isEmpty(payPassword)) {
			throw new BusinessException("请输入支付密码。");
		}
		if (!AppUtil.md5(payPassword).equals(user.getPaypassword())) {
			throw new BusinessException("您输入的支付密码不正确。");
		}
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			if (robOrderRecordInfo.getStockId() == null
					|| robOrderRecordInfo.getStockId().equals("")) {
				throw new BusinessException("还有货物未分配车辆，请分配车辆后确认。");
			}
			this.robOrderRecordInfoDao.updateRobOrderRecordInfo(
					robOrderRecordInfo.getId(),
					robOrderRecordInfo.getStockId(), user.getId(),
					robOrderRecord.getId());
		}
		record_p.setStatus(RobOrderRecord.Status.end);
		this.robOrderRecordDao.merge(record_p);
		String[] truckIdArr = this.getTruckIds(list);
		ParameterManage parameterManage = parameterManageDao
				.getTypeInfo(ParaType.driver);
//		BigDecimal money = new BigDecimal(parameterManage.getValue())
//				.multiply(new BigDecimal(truckIdArr.length));
		BigDecimal money = new BigDecimal(record_p.getDeposit_level_money());
		String remark = "抢单确认成功，冻结预付款金额：" + String.format("%.2f", money)
				+ "元，抢单单号：" + record_p.getRobOrderNo();
		capitalAccountService.saveFreezeTool(user, money, remark,
				user.getName());

		/** 订单日志 **/
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record_p);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.end);
		orderAutLog.setBeforeStatus(record_p.getStatus());
		orderAutLog.setRemark("");
		orderAutLog.setTitle("【抢单确认】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(user.getName());
		orderAutLog.setAuditPersonId(user.getId());
		orderAutLogDao.save(orderAutLog);
		/** 订单日志 **/
		// List<RobOrderConfirm> orderConfirms = new
		// ArrayList<RobOrderConfirm>();
		for (String truckId : truckIdArr) {
			RobOrderConfirm robOrderConfirm = new RobOrderConfirm();
			robOrderConfirm.setRobOrderId(record_p.getId());
			robOrderConfirm.setTransportNo("YLTS" + DateUtil.dateNo());// 保存运单号
			robOrderConfirm.setTurckCost(parameterManage.getValue());// 保存车辆押金总金额
			robOrderConfirm.setTurckDeposit(parameterManage.getValue());// 保存车辆押金单价
			robOrderConfirm.setRobOrderNo(record_p.getRobOrderNo());
			robOrderConfirm.setTurckId(truckId);
			Truck truck = truckDao.getObjectById(Truck.class, truckId);
			robOrderConfirm.setTurckUserId(truck.getAccount().getId());
			robOrderConfirm.setStatus(RobOrderConfirm.Status.receiving);
			double sumWeight = getGoodsActualWeight(truckId, list);
			robOrderConfirm.setTotalWeight(sumWeight);
			robOrderConfirm.setUnitPrice(record_p.getUnitPrice());
			;
			robOrderConfirm.setTransportationCost(Arith.mul(
					record_p.getUnitPrice(), sumWeight, 4));
			robOrderConfirm.setTransportationDeposit(Arith.mul(
					record_p.getDepositUnitPrice(), sumWeight, 4));// 计算车辆所落货物押金
			robOrderConfirm.setCreate_time(new Date());
			robOrderConfirm.setConfirmData(new Date());
			// orderConfirms.add(robOrderConfirm);
			robOrderConfirmDao.save(robOrderConfirm);

			// lix 2017-08-14 注释 修改消息通知方式
			// String sendMsg = "尊敬的驾驶员您好，有一单订单号为：" + record_p.getRobOrderNo() +
			// "，运单号为："
			// + robOrderConfirm.getTransportNo() +
			// "的运输单已分配到你处，请及时登录易林物流APP查看，并和发货人取得联系进行运输任务。";
			// Map<String, Object> map =
			// sendMessageService.saveRecordAndSendMessage(truck.getAccount().getPhone(),
			// sendMsg, inPoint);
			// if (!Boolean.valueOf(map.get("success").toString())) {
			// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
			// }

			// lix 2017-08-14 添加 修改消息通知方式
			String tmp = "尊敬的司机用户您好，您有一笔抢单单号为：" + record_p.getRobOrderNo()
					+ "，运单单号为：" + robOrderConfirm.getTransportNo()
					+ "的运单信息已分配到您处，请您尽快登录小镖人APP查看详情，并联系发货人进行下一步运输任务！";
			Map<String, String> actionMap = new HashMap<String, String>();
			actionMap.put("rob_order_record_id", record_p.getId());
			pushService.saveRecordAndSendMessageWithAccountID(truck
					.getAccount().getId(), "【抢单分配】", tmp, actionMap,
					"RobOrderRecordService.saveConfirmRobOrder");

			/** 确认订单日志 **/
			OrderAutLog log = new OrderAutLog();
			log.setRobOrderRecord(record_p);
			log.setConfirmStatus(RobOrderConfirm.Status.receiving);
			log.setBeforeStatus(record_p.getStatus());
			log.setRemark("");
			log.setTitle("【抢单确认】");
			log.setTurckId(truck.getId());
			log.setTurckUserId(truck.getAccount().getId());
			log.setCreate_time(new Date());
			log.setAuditPerson(user.getName());
			log.setAuditPersonId(user.getId());
			log.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLogDao.save(log);
			/** 确认订单日志 **/

		}
		truckService.updateTruckStatus(truckIdArr, TruckStatus.transit);
		// robOrderConfirmDao.saveRobOrderConfirmList(orderConfirms);
		/** 短信发送 **/
		// lix 2017-08-14 注释 修改消息通知方式
		// String sendMsg = "尊敬的用户您好！您已确认订单号：" + record_p.getRobOrderNo() +
		// "的抢单信息，已支付运输预付款："
		// + String.format("%.2f", money) + "元。";
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(user.getPhone(), sendMsg,
		// inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改消息通知方式
		String sendMsg = "尊敬的司机用户您好，您已经成功确认抢单单号为：" + record_p.getRobOrderNo()
				+ "的抢单信息，并已经成功支付运输预付款：" + String.format("%.2f", money)
				+ "元。请您尽快登录小镖人APP查看详情！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_record_id", record_p.getId());
		pushService.saveRecordAndSendMessageWithAccountID(user.getId(),
				"【抢单确认】", sendMsg, actionMap,
				"RobOrderRecordService.saveConfirmRobOrder");

		// lix 2017-08-14 注释 修改消息通知方式
		// String sendMsg1 = "尊敬的货主用户您好！您有一笔订单号：" + record_p.getRobOrderNo() +
		// "的抢单信息，用户已确认，对方已支付车辆押金："
		// + String.format("%.2f", money) + "元。";
		// Map<String, Object> map1 =
		// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
		// sendMsg1, inPoint);
		// if (!Boolean.valueOf(map1.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改消息通知方式
		String sendMsg1 = "尊敬的货主用户您好，您有一笔抢单单号为：" + record_p.getRobOrderNo()
				+ "的抢单信息，司机用户已经确认，对方已经支付车辆押金：" + String.format("%.2f", money)
				+ "元。请您尽快登录小镖人APP查看详情！";
		Map<String, String> actionMap1 = new HashMap<String, String>();
		actionMap1.put("rob_order_record_id", record_p.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic
				.getAccount().getId(), "【抢单确认】", sendMsg1, actionMap1,
				"RobOrderRecordService.saveConfirmRobOrder");
		/** 短信发送 **/
	}

	@Override
	public Map<String, Object> getLogPage(RobOrderRecord orderRecord,
			int start, int limit) {
		return robOrderRecordDao.getLogPage(orderRecord, start, limit);
	}

	/*
	 * getAccountRobOrderCount
	 */
	@Override
	public List<Map<String, Object>> getAccountRobOrderCount(String accountId) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getAccountRobOrderCount(accountId);
	}

	/**
	 * 功能描述： 获取车辆数量 输入参数: @param list 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月13日下午6:19:29 修 改 人: 日 期: 返 回：List<String>
	 */
	public String[] getTruckIds(List<RobOrderRecordInfo> list) {
		List<String> truckIdArr = new ArrayList<String>();
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			if (!truckIdArr.contains(robOrderRecordInfo.getStockId())) {
				truckIdArr.add(robOrderRecordInfo.getStockId());
			}
		}
		return (String[]) truckIdArr.toArray(new String[truckIdArr.size()]);
	}

	@Override
	public Map<String, Object> getRobOrderCount(Account account) {
		return robOrderRecordDao.getRobOrderCount(account);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllRobOrderMonthCount(List<String> months, Account account,
			String dateType, RobOrderRecord robOrderRecord) {
		return robOrderRecordDao.getAllRobOrderMonthCount(months, account,
				dateType, robOrderRecord);
	}

	@Override
	public List getOrderCompletionMonthCount(List<String> months,
			Account account, String dateType, RobOrderRecord robOrderRecord) {
		return robOrderRecordDao.getOrderCompletionMonthCount(months, account,
				dateType, robOrderRecord);
	}

	@Override
	public List getOrderCompletionMonthWeight(List<String> months,
			Account account, String dateType, RobOrderRecord robOrderRecord) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getOrderCompletionMonthWeight(months, account,
				dateType, robOrderRecord);
	}

	@Override
	public List getAllRobOrderMonthWeight(List<String> months, Account account,
			String dateType, RobOrderRecord robOrderRecord) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getAllRobOrderMonthWeight(months, account,
				dateType, robOrderRecord);
	}

	@Override
	public Map<String, Object> getRobOrderRankingStatistics(int ranking,
			String type) {
		Map<String, Object> map_v = new HashMap<String, Object>();
		List<Map<String, Object>> list = robOrderRecordDao
				.getRobOrderRankingStatistics(ranking, type);
		List<String> counts = new ArrayList<>();
		List<String> weights = new ArrayList<>();
		List<String> xAxis = new ArrayList<>();
		for (Map<String, Object> map : list) {
			xAxis.add(map.get(type).toString());
			weights.add(map.get("weight") == null ? "0" : map.get("weight")
					.toString());
			counts.add(map.get("count").toString());
		}
		map_v.put("counts", counts);
		map_v.put("weights", weights);
		map_v.put("xAxis", xAxis);
		map_v.put("success", true);
		map_v.put("msg", "统计数据成功！");
		return map_v;
	}

	@Override
	public Map<String, Object> updateStatus(RobOrderRecord record,
			String payPassword, Account user) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		RobOrderRecord prent = getRecordById(record.getId());
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateStatus";

		if (!(prent.getStatus().equals(Status.dealing) || prent.getStatus()
				.equals(Status.apply))) {
			returnMap.put("success", false);
			returnMap.put("msg", "当前抢单已不在审核状态，无法进行此操作。");
			return returnMap;
		}
		// record.getStatus() 客户端操作
		if (record.getStatus().equals(Status.success)) {
			if (StringUtil.isEmpty(payPassword)) {
				returnMap.put("success", false);
				returnMap.put("msg", "请输入支付密码。");
				return returnMap;
			}
			if (!AppUtil.md5(payPassword).equals(user.getPaypassword())) {
				returnMap.put("success", false);
				returnMap.put("msg", "您输入的支付密码不正确。");
				return returnMap;
			}
			double weight = 0;
			GoodsBasic goodsBasic = null;
			List<RobOrderRecordInfo> list = robOrderRecordInfoDao
					.getListByRobOrderRecordId(prent.getId());
			// 循环车队下面的所有抢单记录
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				GoodsDetail detail = robOrderRecordInfo.getGoodsDetail();
				goodsBasic = detail.getGoodsBasic();
				// 抢单总量 ==实际装载总量
				if (detail.getWeight() == detail.getEmbarkWeight()) {
					robOrderRecordInfo.setActualWeight(0); // 剩余总量
					robOrderRecordInfoDao.update(robOrderRecordInfo);
				} else {
					// 已被抢重量 + 本次抢货重量> 发货的重量
					if (detail.getEmbarkWeight()
							+ robOrderRecordInfo.getWeight() > detail
								.getWeight()) {
						// 最后只有剩余重量 ActualWeight实际抢货重量
						robOrderRecordInfo.setActualWeight(detail.getWeight()
								- detail.getEmbarkWeight());
						detail.setEmbarkWeight(detail.getWeight());
					} else {
						// 最后只有剩余重量 ActualWeight实际抢货重量
						robOrderRecordInfo.setActualWeight(robOrderRecordInfo
								.getWeight());
						detail.setEmbarkWeight(detail.getEmbarkWeight()
								+ robOrderRecordInfo.getActualWeight());
					}
				}
				goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight()
						+ robOrderRecordInfo.getActualWeight());
				weight += robOrderRecordInfo.getActualWeight();

				// 系统自动处理失效抢单
				if (goodsBasic.getTotalWeight() == goodsBasic.getEmbarkWeight()) {
					List<RobOrderRecord> list1 = robOrderRecordDao
							.getRobOrderRecordListByStatus();
					for (RobOrderRecord robOrderRecord : list1) {
						if (!robOrderRecord.getId().equals(prent.getId())) {
							robOrderRecord
									.setStatus(RobOrderRecord.Status.scrap);
							robOrderRecordDao.update(robOrderRecord);

							OrderAutLog orderAutLog = new OrderAutLog();
							orderAutLog.setRobOrderRecord(robOrderRecord);
							orderAutLog.setAfterStatus(robOrderRecord
									.getStatus());
							orderAutLog
									.setBeforeStatus(RobOrderRecord.Status.apply);
							orderAutLog.setRemark("系统自动处理，发货货物已被抢完。该抢单失效。");
							orderAutLog.setTitle("【抢单销毁】");
							orderAutLog.setCreate_time(new Date());
							orderAutLog.setAuditPerson("系统自动处理");
							orderAutLog.setAuditPersonId(user.getId());
							orderAutLogDao.save(orderAutLog);
							String sendMsg = "尊敬的司机用户您好！您的抢单单号为："
									+ robOrderRecord.getRobOrderNo()
									+ "的抢单信息，由于货物已被抢完，系统自动处理该抢单失效。";
							Map<String, Object> map = sendMessageService
									.saveRecordAndSendMessage(robOrderRecord
											.getAccount().getPhone(), sendMsg,
											inPoint);
							if (!Boolean.valueOf(map.get("success").toString())) {
								returnMap.put("success", false);
								returnMap.put("msg", "短信提示异常，请联系管理员,审核失败。");
								return returnMap;
							}
						}
					}
				}
				goodsDetailDao.update(detail);
			}
			goodsBasicDao.update(goodsBasic);

			ParameterManage parameterManage = parameterManageDao
					.getTypeInfo(ParaType.consignor);
			prent.setDepositUnitPrice(parameterManage.getValue());
//			BigDecimal money = new BigDecimal(parameterManage.getValue())
//					.multiply(new BigDecimal(weight));
			
			BigDecimal money = new BigDecimal(prent.getDeposit_level_money());
			String remark = "抢单审核成功，冻结预付款金额：" + String.format("%.2f", money)
					+ "元，发货单号：" + goodsBasic.getDeliveryNo() + ",抢单单号："
					+ prent.getRobOrderNo();
			capitalAccountService.saveFreezeTool(user, money, remark,
					user.getName());
			prent.setWeight(weight);

		}

		prent.setStatus(record.getStatus());
		prent.setRemark(record.getRemark());

		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(prent);
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAfterStatus(record.getStatus());
		orderAutLog.setBeforeStatus(prent.getStatus());
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(user.getName());
		orderAutLog.setAuditPersonId(user.getId());

		String msg = "尊敬的司机用户您好：";
		if (record.getStatus().equals(Status.success)) {
			orderAutLog.setTitle("【抢单审核】");
			msg += "您有一笔抢单单号为：" + prent.getRobOrderNo()
					+ "的抢单信息已经通过货主用户审核成功，请您尽快登录小镖人APP尽快处理该抢单信息！";
		} else if (record.getStatus().equals(Status.back)) {
			orderAutLog.setTitle("【抢单退回】");
			msg += "您的有一笔抢单单号为:" + prent.getRobOrderNo()
					+ "的抢单信息已经被货主用户退回，请您尽快登录小镖人APP查看详情！";
		} else if (record.getStatus().equals(Status.scrap)) {
			orderAutLog.setTitle("【抢单销毁】");
			msg += "您的有一笔抢单单号为:" + prent.getRobOrderNo()
					+ "，的抢单信息已销毁，该抢单无效。请您尽快登录小镖人APP查看详情！";
		}

		String remark = record.getRemark().equals("")
				|| record.getRemark() == null ? "无" : record.getRemark();
		Map<String, Object> map = sendMessageService
				.saveRecordAndSendMessage(prent.getAccount().getPhone(), msg
						+ "。对方留言：" + remark, inPoint);
		if (!Boolean.valueOf(map.get("success").toString())) {
			returnMap.put("success", false);
			returnMap.put("msg", "短信提示异常，请联系管理员,审核失败。");
		}
		orderAutLogDao.save(orderAutLog);
		robOrderRecordDao.update(prent);
		returnMap.put("success", true);
		returnMap.put("msg", "操作成功。");
		return returnMap;
	}

	@Override
	public Map<String, Object> getOrderRecordByGoodsId(RobOrderRecord order,
			int start, int limit) {
		Map<String, Object> map = goodsBasicDao.getBasicGoods(order
				.getGoodsBasic().getId());
		map.put("orders",
				robOrderRecordDao.getOrderRecordByGoodsId(order, start, limit));
		return map;
	}

	@Override
	public Map<String, Object> getOrderDetails(String orderId) {
		Map<String, Object> map = robOrderRecordDao.getOrderDetails(orderId);
		map.put("orderDetails", robOrderRecordDao.getOrderInfoList(orderId));
		map.put("accountInfo",
				accountDao.getAccountInfo(map.get("account_id").toString()));
		map.put("advanceQuickly",
				parameterManageDao.getTypeInfo(ParaType.consignor).getValue()
						* Double.parseDouble(map.get("weight").toString()));
		return map;
	}

	@Override
	public Map<String, Object> saveRecordForMap(RobOrderRecord record,
			Account account) {
		Map<String, Object> map_res = new HashMap<String, Object>();
		boolean isfalse = true;
		String msg = "";
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.saveRecord";
		GoodsBasic goodsBasic = goodsBasicDao.getObjectById(GoodsBasic.class,
				record.getGoodsBasic().getId());
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				record.getRobOrderRecordInfos(), RobOrderRecordInfo.class);
		double sumWeight = getGoodsTotalWeight(list);
		account = account.getCompany().getName() == null
				&& account.getCompany().getName().equals("") ? accountDao
				.getAccount(account.getId()) : account;
		record.setRobOrderNo("YLOR" + DateUtil.dateNo());
		record.setAccount(account);
		record.setAdd_user_id(account.getId());
		record.setCreate_time(new Date());
		record.setWeight(sumWeight);
		record.setCompanyName(account.getCompany().getName());
		record.setTotalPrice(Arith.mul(record.getUnitPrice(), sumWeight, 4));
		record.setGoodsBasic(goodsBasic);
		String goodsTypeNames = "";
		record.setRobbedAccountId(goodsBasic.getAccount().getId());
		record.setRobbedCompanyId(goodsBasic.getCompanyId());
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			robOrderRecordInfo.setAdd_user_id(account.getId());
			robOrderRecordInfo.setCreate_time(new Date());
			GoodsDetail goodsDetail = goodsDetailDao.getObjectById(
					GoodsDetail.class, robOrderRecordInfo.getGoodsDetailId());
			double surplusWeight = goodsDetail.getWeight()
					- goodsDetail.getEmbarkWeight();
			if (!goodsDetail.getGoodsBasic().getId()
					.equals(record.getGoodsBasic().getId())) {
				isfalse = false;
				msg += "您所抢的详细货物不是正确货源信息！";
				break;
			}
			if (robOrderRecordInfo.getWeight() <= 0) {
				msg = "货物类型：【" + goodsDetail.getGoodsType().getName()
						+ "】,货物名称：【" + goodsDetail.getGoodsName()
						+ "】，抢单重量必须大于0，请重新输入！";
				throw new BusinessException(msg);
			}
			if (surplusWeight == 0) {
				isfalse = false;
				msg += "货物类型：［" + goodsDetail.getGoodsType().getName()
						+ "］, 货物名称：［" + goodsDetail.getGoodsName()
						+ "］,货物已被抢完，剩余重量为：0吨;";
			}

			if (robOrderRecordInfo.getWeight() > (goodsDetail.getWeight() - goodsDetail
					.getEmbarkWeight())) {
				msg = "货物类型：【" + goodsDetail.getGoodsType().getName()
						+ "】,货物名称：【" + goodsDetail.getGoodsName()
						+ "】，抢单重量大于剩余重量(" + surplusWeight + "/吨)，请重新输入！";
				throw new BusinessException(msg);
			}
			goodsTypeNames = goodsTypeNames
					+ goodsDetail.getGoodsType().getName() + ",";
			robOrderRecordInfo.setGoodsDetail(goodsDetail);
			robOrderRecordInfo.setRobOrderRecord(record);
			robOrderRecordInfo.setParent(true);
			robOrderRecordInfo.setSplit(false);
			robOrderRecordInfo
					.setOriginalWeight(robOrderRecordInfo.getWeight());// 设置原有货物重量
		}
		if (!isfalse) {
			map_res.put("success", false);
			map_res.put("msg", msg + "请重新填写重量！");
			map_res.put("data", null);
			return map_res;
		}
		robOrderRecordDao.save(record);
		robOrderRecordInfoDao.saveRobOrderRecordInfo(list);
		record.setGoodsTypes(goodsTypeNames.substring(0,
				goodsTypeNames.lastIndexOf(",")));
		robOrderRecordDao.merge(record);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.apply);
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setTitle("【抢单申请】");
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
		// lix 2017-08-14 注释 修改通知方式
		// String sendMsg = "尊敬的货主用户您好！您有一笔货物单号为：" + goodsBasic.getDeliveryNo()
		// + "的发货信息，已被用户抢单，请及时审阅。";
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
		// sendMsg, inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// map_res.put("success", false);
		// map_res.put("msg", "短信提示异常，请联系管理员,审核失败。");
		// map_res.put("data", null);
		// }
		// lix 2017-08-14 添加 修改通知方式
		String tmp = "尊敬的货主用户您好，您有一笔货物单号为：" + goodsBasic.getDeliveryNo()
				+ "的货物信息，已被司机用户抢单，请您尽快登录小镖人APP进行抢单审核操作！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_record_id", record.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic
				.getAccount().getId(), "【抢单申请】", tmp, actionMap,
				"RobOrderRecordService.saveRecordForMap");

		map_res.put("success", true);
		map_res.put("msg", "抢单成功，已提交给货主，等待货主确认！");
		map_res.put("data", null);
		return map_res;
	}

	/*
	 * getMyPage
	 */
	@Override
	public Map<String, Object> getMyPage(RobOrderRecord record,
			Account account, int start, int limit) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getMyPage(record, account, start, limit);
	}

	/*
	 * getRobOrderCancelByDay
	 */
	@Override
	public int getRobOrderCancelByDay(String accountId) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getCancelRobOrderByDay(accountId);
	}

	/*
	 * updateRobOrderCancel
	 */
	@Override
	public void updateRobOrderCancel(RobOrderRecord record, Account account) {
		// TODO Auto-generated method stub
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateRobOrderCancel";
		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, record.getId());
		String money_msg = "";
		GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
		if (robOrderRecord.getStatus().toString().equals("success")) {

			List<RobOrderRecordInfo> list = robOrderRecordInfoDao
					.getList(robOrderRecord.getId());
			// 获得货物总重量
			double weight = this.getGoodsActualWeight(null, list);
			ParameterManage parameterManage = parameterManageDao
					.getTypeInfo(ParaType.consignor);
//			BigDecimal money = new BigDecimal(parameterManage.getValue())
//					.multiply(new BigDecimal(weight));
			BigDecimal money = new BigDecimal(robOrderRecord.getDeposit_level_money());
			String remark = "尊敬的货主用户您好，您有一笔抢单单号为："
					+ robOrderRecord.getRobOrderNo()
					+ "的抢单信息已被用户取消，解除冻结预付款资金，金额："
					+ String.format("%.2f", money) + "元";
			capitalAccountService.updateFreezeTool(goodsBasic.getAccount(),
					money, remark, "系统自动处理");
			// 修改货物基本信息的重量
			goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight() - weight);
			goodsBasicDao.merge(goodsBasic);
			List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
			for (RobOrderRecordInfo robOrderRecordInfo : list) {
				GoodsDetail goodsDetail = robOrderRecordInfo.getGoodsDetail();
				goodsDetail.setEmbarkWeight(goodsDetail.getEmbarkWeight()
						- robOrderRecordInfo.getActualWeight());
				goodsDetailList.add(goodsDetail);
			}
			this.goodsDetailDao.updateGoodsDetailList(goodsDetailList);
			money_msg = "由于司机用户取消抢单，您所支付的预付款资金(" + String.format("%.2f", money)
					+ ")元已解冻！";
		}
		robOrderRecord.setCancelAccount(account);
		robOrderRecord.setStatus(RobOrderRecord.Status.scrap);
		robOrderRecord.setCancelRemark(record.getCancelRemark());
		robOrderRecord.setCancelDate(new Date());
		robOrderRecordDao.merge(robOrderRecord);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(robOrderRecord);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.scrap);
		orderAutLog.setBeforeStatus(record.getStatus());
		orderAutLog.setRemark(record.getCancelRemark());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setTitle("【抢单取消】");
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
		// lix 2017-08-14 注释 修改通知方式
		// String sendMsg = "尊敬的货主用户您好！您有一笔订单号为：" +
		// robOrderRecord.getRobOrderNo() + "的抢单信息，已被用户取消。" + money_msg;
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
		// sendMsg, inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }
		// lix 2017-08-14 添加 修改通知方式
		String sendMsg = "尊敬的货主用户您好，您有一笔抢单单号为："
				+ robOrderRecord.getRobOrderNo() + "的抢单信息，已被司机用户取消。"
				+ money_msg;
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_record_id", robOrderRecord.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic
				.getAccount().getId(), "【抢单取消】", sendMsg, actionMap,
				"RobOrderRecordService.updateRobOrderCancel");
	}

	/*
	 * updateWithdrawRobOrder
	 */
	@Override
	public void updateWithdrawRobOrder(RobOrderRecord robOrderRecord,
			Account account) {
		// TODO Auto-generated method stub
		robOrderRecord.setStatus(RobOrderRecord.Status.withdraw);
		robOrderRecordDao.merge(robOrderRecord);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(robOrderRecord);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.withdraw);
		orderAutLog.setBeforeStatus(robOrderRecord.getStatus());
		orderAutLog.setRemark(robOrderRecord.getRemark());
		orderAutLog.setTitle("【抢单撤回】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
	}

	/*
	 * getRobOrderRecordById
	 */
	@Override
	public Map<String, Object> getRobOrderRecordById(String id, Account account) {
		Map<String, Object> map = robOrderRecordDao.getRobOrderRecordById(id,
				account);
		List<Map<String, Object>> list = robOrderRecordInfoDao
				.getRobOrderRecordInfoList(id);
		ParameterManage parameterManage = parameterManageDao
				.getTypeInfo(ParaType.driver);
		if (account.getCompany().getCompanyType().getName().equals("个人司机")) {
			Truck truck = truckDao.getTruckByAcountNo(account.getId());
			for (Map<String, Object> mapRobInfo : list) {
				mapRobInfo.put("stockId", truck.getId());
				mapRobInfo.put("stockName", truck.getTrack_no() + ",驾驶员："
						+ truck.getAccount().getName() + "("
						+ truck.getAccount().getPhone() + ")");
			}
		}
		map.put("promptMsg", StatusMap.getRobOrderStatusMsg(
				RobOrderRecord.Status.values()[Integer.parseInt(map.get(
						"status").toString())], account));
		map.put("driverParameter", parameterManage.getValue());
		map.put("robOrderList", list);
		return map;
	}

	/*
	 * updateRobOrderRecord
	 */
	@Override
	public void updateRobOrderRecord(RobOrderRecord record, Account account) {
		String msg = "";
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.updateRobOrderRecord";
		RobOrderRecord record_p = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, record.getId());
		String robOrderNo = record_p.getRobOrderNo();
		GoodsBasic goodsBasic = record_p.getGoodsBasic();
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				record.getRobOrderRecordInfos(), RobOrderRecordInfo.class);
		double sumWeight = getGoodsTotalWeight(list);
		record.setAccount(account);
		record.setUpdate_user_id(account.getId());
		record.setUpdate_time(new Date());
		record.setAdd_user_id(record_p.getAdd_user_id());
		record.setCancelDate(record_p.getCreate_time());
		record.setWeight(sumWeight);
		record.setCompanyName(account.getCompany().getName());
		record.setTotalPrice(Arith.mul(record.getUnitPrice(), sumWeight, 2));
		record.setGoodsBasic(goodsBasic);
		String goodsTypeNames = "";
		List<RobOrderRecordInfo> robOrderRecordInfoList = new ArrayList<RobOrderRecordInfo>();
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			GoodsDetail goodsDetail = goodsDetailDao.getObjectById(
					GoodsDetail.class, robOrderRecordInfo.getGoodsDetailId());
			double surplusWeight = goodsDetail.getWeight()
					- goodsDetail.getEmbarkWeight();
			if (surplusWeight == 0) {
				msg = "货物类型：［" + goodsDetail.getGoodsType().getName()
						+ "］, 货物名称：［" + goodsDetail.getGoodsName()
						+ "］,货物已被抢完，剩余重量为：0吨;";
				throw new BusinessException(msg);
			}
			if (robOrderRecordInfo.getWeight() > 0
					&& robOrderRecordInfo.getWeight() < surplusWeight) {
				robOrderRecordInfo.setAdd_user_id(account.getId());
				robOrderRecordInfo.setCreate_time(new Date());
				goodsTypeNames = goodsTypeNames
						+ goodsDetail.getGoodsType().getName() + ",";
				robOrderRecordInfo.setGoodsDetail(goodsDetail);
				robOrderRecordInfo.setRobOrderRecord(record_p);
				robOrderRecordInfoList.add(robOrderRecordInfo);
			} else if (robOrderRecordInfo.getWeight() <= 0) {
				msg = "货物类型：【" + goodsDetail.getGoodsType().getName()
						+ "】,货物名称：【" + goodsDetail.getGoodsName()
						+ "】，抢单重量必须大于0，请重新输入！";
				throw new BusinessException(msg);
			} else if (robOrderRecordInfo.getWeight() > (goodsDetail
					.getWeight() - goodsDetail.getEmbarkWeight())) {
				msg = "货物类型：【" + goodsDetail.getGoodsType().getName()
						+ "】,货物名称：【" + goodsDetail.getGoodsName()
						+ "】，抢单重量大于剩余重量(" + surplusWeight + "/吨)，请重新输入！";
				throw new BusinessException(msg);
			}
			robOrderRecordInfo.setParent(true);
			robOrderRecordInfo.setSplit(false);
			robOrderRecordInfo
					.setOriginalWeight(robOrderRecordInfo.getWeight());// 设置原有货物重量
		}
		robOrderRecordInfoDao.deleteRobOrderRecordInfo(record.getId());
		robOrderRecordInfoDao.saveRobOrderRecordInfo(robOrderRecordInfoList);
		record.setGoodsTypes(goodsTypeNames.substring(0,
				goodsTypeNames.lastIndexOf(",")));
		robOrderRecordDao.merge(record);
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.apply);
		orderAutLog.setRemark(record.getRemark());
		orderAutLog.setTitle("【抢单申请】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLogDao.save(orderAutLog);
		String sendMsg = "尊敬的货主用户您好，您有一笔退回的抢单单号为：" + robOrderNo
				+ "的抢单信息，已被司机用户重新提交确认，请您登录小镖人APP查看及时审阅。";
		Map<String, Object> map = sendMessageService.saveRecordAndSendMessage(
				goodsBasic.getAccount().getPhone(), sendMsg, inPoint);
		if (!Boolean.valueOf(map.get("success").toString())) {
			throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		}
	}

	/*
	 * getRobOrderRecordIndoSplitList
	 */
	@Override
	public Map<String, Object> getRobOrderRecordIndoSplitList(String id,
			Account account) {
		Map<String, Object> map = robOrderRecordInfoDao
				.getRobOrderRecordInfoById(id, account);
		List<Map<String, Object>> list = robOrderRecordInfoDao
				.getRobOrderRecordInfoSplitList(id);
		map.put("splitList", list);
		return map;
	}

	/*
	 * getCompanyTrucks
	 */
	@Override
	public List<Map<String, Object>> getCompanyTrucks(String companyId) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getCompanyTrucks(companyId);
	}

	/*
	 * [{'id':'4028814a5bd14d28015bd14e52840001','stockId':'
	 * 402881e55afe1ad2015afe308827000b'},
	 * {'id':'4028814a5bd212cd015bd226c91e000d','stockId':'
	 * 402881e55afe1ad2015afe308827000b'},
	 * {'id':'4028814a5bd212cd015bd226c920000e','stockId':'
	 * 402881ea5af3cbc0015af3f1f4ac0007'}] saveConfirmRobOrder
	 * [{'id':'4028814a5bd14d28015bd14e52840001','stockId':'
	 * 402881ea5af3cbc0015af3f1f4ac0007'},
	 * {'id':'4028814a5bd2395a015bd23a48a40000','stockId':'
	 * 402881e55afe1ad2015afe308827000b'}]
	 */
	@Override
	public void saveConfirmRobOrder(RobOrderRecord robOrderRecord,
			String payPassword, Account user) {
		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.saveConfirmRobOrder";
		RobOrderRecord record_p = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderRecord.getId());
		GoodsBasic goodsBasic = goodsBasicDao.getObjectById(GoodsBasic.class,
				record_p.getGoodsBasic().getId());
		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				robOrderRecord.getRobOrderRecordInfos(),
				RobOrderRecordInfo.class);
		List<RobOrderRecordInfo> dbList = new ArrayList<RobOrderRecordInfo>();
		for (RobOrderRecordInfo info : list) {
			RobOrderRecordInfo dbInfo = robOrderRecordInfoDao
					.getRobOrderRecordInfoById(info.getId());
			dbList.add(dbInfo);
			dbInfo.setStockId(info.getStockId());
		}

		if (StringUtil.isEmpty(payPassword)) {
			throw new BusinessException("请输入支付密码。");
		}
		if (!AppUtil.md5(payPassword).equals(user.getPaypassword())) {
			throw new BusinessException("您输入的支付密码不正确。");
		}
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			if (robOrderRecordInfo.getStockId() == null
					|| robOrderRecordInfo.getStockId().equals("")) {
				throw new BusinessException("还有货物未分配车辆，请分配车辆后确认。");
			}
			this.robOrderRecordInfoDao.updateRobOrderRecordInfo(
					robOrderRecordInfo.getId(),
					robOrderRecordInfo.getStockId(), user.getId(),
					robOrderRecord.getId());
		}
		record_p.setStatus(RobOrderRecord.Status.end);
		this.robOrderRecordDao.merge(record_p);
		String[] truckIdArr = this.getTruckIds(list);
		ParameterManage parameterManage = parameterManageDao
				.getTypeInfo(ParaType.driver);
//		BigDecimal money = new BigDecimal(parameterManage.getValue())
//				.multiply(new BigDecimal(truckIdArr.length));
		BigDecimal money =new BigDecimal(record_p.getDeposit_level_money());
		String remark = "抢单确认成功，冻结预付款金额：" + String.format("%.2f", money)
				+ "元，抢单单号：" + record_p.getRobOrderNo();
		capitalAccountService.saveFreezeTool(user, money, remark,
				user.getName());

		/** 订单日志 **/
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record_p);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.end);
		orderAutLog.setBeforeStatus(record_p.getStatus());
		orderAutLog.setRemark("");
		orderAutLog.setTitle("【抢单确认】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(user.getName());
		orderAutLog.setAuditPersonId(user.getId());
		orderAutLogDao.save(orderAutLog);
		/** 订单日志 **/
		// List<RobOrderConfirm> orderConfirms = new
		// ArrayList<RobOrderConfirm>();
		for (String truckId : truckIdArr) {
			RobOrderConfirm robOrderConfirm = new RobOrderConfirm();
			robOrderConfirm.setRobOrderId(record_p.getId());
			robOrderConfirm.setTransportNo("YLTS" + DateUtil.dateNo());// 保存运单号
			robOrderConfirm.setTurckCost(parameterManage.getValue());// 保存车辆押金总金额
			robOrderConfirm.setTurckDeposit(record_p.getDeposit_level_money());// 保存车辆押金单价
			
			robOrderConfirm.setRobOrderNo(record_p.getRobOrderNo());
			robOrderConfirm.setTurckId(truckId);
			Truck truck = truckDao.getObjectById(Truck.class, truckId);
			robOrderConfirm.setTurckUserId(truck.getAccount().getId());
			robOrderConfirm.setStatus(RobOrderConfirm.Status.receiving);
			double sumWeight = getGoodsActualWeight(truckId, dbList);
			robOrderConfirm.setTotalWeight(sumWeight);
			robOrderConfirm.setUnitPrice(record_p.getUnitPrice());
			robOrderConfirm.setTransportationCost(Arith.mul(
					record_p.getUnitPrice(), sumWeight, 4));
//			robOrderConfirm.setTransportationDeposit(Arith.mul(
//					record_p.getDepositUnitPrice(), sumWeight, 4));// 计算车辆所落货物押金
			robOrderConfirm.setTransportationDeposit(record_p.getDeposit_level_money()/list.size());// 计算车辆所落货物押金
			robOrderConfirm.setCreate_time(new Date());
			robOrderConfirm.setConfirmData(new Date());
			// orderConfirms.add(robOrderConfirm);
			robOrderConfirmDao.save(robOrderConfirm);
			// lix 2017-08-14 注释 修改 通知方式
			// String sendMsg = "尊敬的驾驶员您好，有一单订单号为：" + record_p.getRobOrderNo() +
			// "，运单号为："
			// + robOrderConfirm.getTransportNo() +
			// "的运输单已分配到你处，请及时登录易林物流APP查看，并和发货人取得联系进行运输任务。";
			// Map<String, Object> map =
			// sendMessageService.saveRecordAndSendMessage(truck.getAccount().getPhone(),
			// sendMsg, inPoint);
			// if (!Boolean.valueOf(map.get("success").toString())) {
			// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
			// }
			// lix 2017-08-14 添加 修改 通知方式
			String sendMsg = "尊敬的司机用户您好，有一笔抢单单号为：" + record_p.getRobOrderNo()
					+ "，运单单号为：" + robOrderConfirm.getTransportNo()
					+ "的运单信息已分配到您处，请您尽快登录小镖人APP查看详情，并联系发货人进行下一步运输任务！";
			Map<String, String> actionMap = new HashMap<String, String>();
			actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
			pushService.saveRecordAndSendMessageWithAccountID(truck
					.getAccount().getId(), "【抢单分配】", sendMsg, actionMap,
					"RobOrderRecordService.saveConfirmRobOrder");

			/** 确认订单日志 **/
			OrderAutLog log = new OrderAutLog();
			log.setRobOrderRecord(record_p);
			log.setConfirmStatus(RobOrderConfirm.Status.receiving);
			log.setBeforeStatus(record_p.getStatus());
			log.setRemark("");
			log.setTitle("【抢单确认】");
			log.setTurckId(truck.getId());
			log.setTurckUserId(truck.getAccount().getId());
			log.setCreate_time(new Date());
			log.setAuditPerson(user.getName());
			log.setAuditPersonId(user.getId());
			log.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLogDao.save(log);
			/** 确认订单日志 **/

		}
		truckService.updateTruckStatus(truckIdArr, TruckStatus.transit);
		// robOrderConfirmDao.saveRobOrderConfirmList(orderConfirms);
		/** 短信发送 **/
		// lix 2017-08-14 注释 修改 通知方式
		// String sendMsg = "尊敬的用户您好！您已完成抢单,订单号：" + record_p.getRobOrderNo() +
		// "，已支付运输预付款：" + String.format("%.2f", money)
		// + "元。";
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(user.getPhone(), sendMsg,
		// inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改 通知方式
		String sendMsg = "尊敬的司机用户您好，您已经成功确认抢单,抢单单号为："
				+ record_p.getRobOrderNo() + "，已经支付运输预付款："
				+ String.format("%.2f", money) + "元。";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_record_id", record_p.getId());
		pushService.saveRecordAndSendMessageWithAccountID(user.getId(),
				"【抢单确认】", sendMsg, actionMap,
				"RobOrderRecordService.saveConfirmRobOrder");

		// lix 2017-08-14 注释 修改 通知方式
		// String sendMsg1 = "尊敬的货主用户您好！您有一笔订单号：" + record_p.getRobOrderNo() +
		// "的抢单信息，用户已确认，对方已支付车辆押金："
		// + String.format("%.2f", money) + "元。";
		// Map<String, Object> map1 =
		// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
		// sendMsg1, inPoint);
		// if (!Boolean.valueOf(map1.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改 通知方式
		String sendMsg1 = "尊敬的货主用户您好，您有一笔抢单单号为：" + record_p.getRobOrderNo()
				+ "的抢单信息，司机用户已确认，对方已支付车辆押金：" + String.format("%.2f", money)
				+ "元。";
		Map<String, String> actionMap1 = new HashMap<String, String>();
		actionMap1.put("rob_order_record_id", record_p.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic
				.getAccount().getId(), "【抢单确认】", sendMsg1, actionMap1,
				"RobOrderRecordService.saveConfirmRobOrder");

		/** 短信发送 **/

	}

	@Override
	public Map<String, Object> getWaitOrdersReviewDetails(RobOrderRecord order,
			int start, int limit) {
		Map<String, Object> map = goodsBasicDao.getBasicGoods(order
				.getGoodsBasic().getId());
		// Map<String, Object> map = new HashMap<String, Object>();
		// 获取抢单人信息
		List<Map<String, Object>> robUser = robOrderRecordDao
				.getWaitRobUserByGoodsId(order);
		for (Map<String, Object> mp : robUser) {
			String robOrderID = mp.get("id").toString();
			List<Map<String, Object>> robUserDetail = robOrderRecordDao
					.getRobUserDetailsByRobOrderId(robOrderID);
			mp.put("robUserDetails", robUserDetail);
		}
		map.put("robUsers", robUser);
		List<Map<String, Object>> goodDetail = goodsDetailDao
				.getGoodsDetailByGoodsBasicId(order.getGoodsBasic().getId());
		map.put("goodsDetails", goodDetail);
		return map;
	}

	@Override
	public Map<String, Object> getAlreadyOrdersReviewDetails(
			RobOrderRecord order, int start, int limit) {
		Map<String, Object> map = goodsBasicDao.getBasicGoods(order
				.getGoodsBasic().getId());
		// 获取抢单人信息
		List<Map<String, Object>> robUser = robOrderRecordDao
				.getAlreadyRobUserByGoodsId(order);
		for (Map<String, Object> mp : robUser) {
			String robOrderID = mp.get("id").toString();
			List<Map<String, Object>> robUserDetail = robOrderRecordDao
					.getRobUserDetailsByRobOrderId(robOrderID);
			mp.put("robUserDetails", robUserDetail);
		}
		map.put("robUsers", robUser);
		List<Map<String, Object>> goodDetail = goodsDetailDao
				.getGoodsDetailByGoodsBasicId(order.getGoodsBasic().getId());
		map.put("goodsDetails", goodDetail);
		return map;
	}

	@Override
	public Map<String, Object> getMyPageNew(RobOrderRecord record,
			Account account, int start, int limit) {

		return robOrderRecordDao.getMyPageNew(record, account, start, limit);
	}

	@Override
	public List<Map<String, Object>> getAccountRobOrderCountNew(String accountId) {
		// TODO Auto-generated method stub
		return robOrderRecordDao.getAccountRobOrderCountNew(accountId);

	}

	// 获取抢单记录详细
	@Override
	public Map<String, Object> getMyRobOrderDetails(RobOrderRecord order) {
		Map<String, Object> robOrderRecord = robOrderRecordDao.getMapById(order
				.getId());
		Map<String, Object> map = goodsBasicDao.getBasicGoods(robOrderRecord
				.get("goods_baice_id").toString());
		List<Map<String, Object>> goodDetail = goodsDetailDao
				.getGoodsDetailByGoodsBasicId(map.get("id").toString());
		map.put("goodsDetails", goodDetail);

		List<Map<String, Object>> lstRecordInfo = robOrderRecordInfoDao
				.getRobOrderRecordInfoList(order.getId());
		robOrderRecord.put("robOrderRecordInfos", lstRecordInfo);
		List<Map<String, Object>> robOrderRecords = new ArrayList<Map<String, Object>>(
				Arrays.asList(robOrderRecord));
		map.put("robOrderRecords", robOrderRecords);
		return map;

	}

	/*
	 * lil parentID == RoborderRecordID json: [{
	 * 'parentId':'4028814a5bd14d28015bd14e52840001','weight':'15',"stockId":
	 * "aaaaa" }, {
	 * 'parentId':'4028814a5bd14d28015bd14e52840001','weight':'10'",stockId":
	 * "aaaaa" }]
	 */
	@Override
	public Map<String, Object> saveSuccessRobOrderNew(
			String splitRobOrderRecordInfoJson, String payPassword) {

		List<RobOrderRecordInfo> list = JsonPluginsUtil.jsonToBeanList(
				splitRobOrderRecordInfoJson, RobOrderRecordInfo.class);
		if (list == null || list.size() == 0) {

			throw new BusinessException("传入参数有误");
		}

		// 把第一个替换成原来的数据
		Map<String, List<RobOrderRecordInfo>> group = new HashMap<String, List<RobOrderRecordInfo>>();
		Map<String, Object> map = new HashMap<String, Object>();

		for (RobOrderRecordInfo info : list) {

			List<RobOrderRecordInfo> groupList = null;
			// 把第一个设置成原有抢单info数据
			if (group.containsKey(info.getParentId()) == false) {

				groupList = new ArrayList<RobOrderRecordInfo>();
				group.put(info.getParentId(), groupList);
				RobOrderRecordInfo dbInfo = robOrderRecordInfoDao
						.getObjectById(RobOrderRecordInfo.class,
								info.getParentId());
				if (dbInfo.getOriginalWeight() != sumWeightWithParent(
						dbInfo.getId(), list)) {
					throw new BusinessException("货物拆分的重量不对，请检查拆分的货物重量！");
				}
				dbInfo.setActualWeight(info.getWeight());
				dbInfo.setWeight(info.getWeight());
				dbInfo.setParent(false);
				dbInfo.setParentId(null);
				dbInfo.setSplit(false);
				;

				dbInfo.setStockId(info.getStockId());
				dbInfo.setStockName(info.getStockName());
				info = dbInfo;
			} else {

				groupList = group.get(info.getParentId());
			}

			groupList.add(info);
		}
		String parentID = list.get(0).getParentId();
		RobOrderRecordInfo robInfo = robOrderRecordInfoDao.getObjectById(
				RobOrderRecordInfo.class, parentID);
		String roborderRecordId = robInfo.getRobOrderRecord().getId();
		RobOrderRecord record = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, roborderRecordId);
		if (record == null) {

			throw new BusinessException("抢单记录不存在");
		}
		if (record.getStatus() != Status.success) {

			throw new BusinessException("该抢单状态已经改变 不能确认");
		}
		// 判断传上来的记录是否匹配
		List<Map<String, Object>> dbList = robOrderRecordInfoDao
				.getRobOrderRecordInfoList(roborderRecordId);

		if (dbList.size() != group.size()) {
			throw new BusinessException("传入参数有误 parentID条数 不匹配");

		}

		Account account = UserUtil.getAccount();

		// List<RobOrderRecordInfo> dbRecordInfos = robOrderRecordInfoDao.getrob
		// 保存split記錄
		for (String robOrderInfoID : group.keySet()) {

			boolean isFind = false;
			for (Map<String, Object> dbRobOrderRecordInfo : dbList) {
				if (robOrderInfoID.equals(dbRobOrderRecordInfo.get("id")
						.toString())) {

					isFind = true;
					break;
				}

			}
			if (isFind == false) {
				throw new BusinessException(String.format(
						"parentID :%s 在改抢单不错在", robOrderInfoID));

			}
			List<RobOrderRecordInfo> splitList = group.get(robOrderInfoID);
			RobOrderRecordInfo parent = robOrderRecordInfoDao.getObjectById(
					RobOrderRecordInfo.class, robOrderInfoID);

			if (!parent.getRobOrderRecord().getAccount().getId()
					.equals(account.getId())) {
				map.put("success", false);
				throw new BusinessException("你所拆分的货物不是自己的，不能进行拆分！");

			}

			// 不用拆分、

			// if (splitList.size() == 1) {
			// RobOrderRecordInfo splitInfo = splitList.get(0);
			// if (StringUtil.isEmpty(splitInfo.getStockId())) {
			// throw new BusinessException("还有货物未分配车辆，请分配车辆后确认。");
			// }
			// Truck truck = truckDao.getTruckById(splitInfo.getStockId());
			//
			// if (truck == null) {
			//
			// throw new BusinessException(String.format("未找到司机%s",
			// splitInfo.getStockId()));
			//
			// }
			// if (truck.getTruckStatus() == TruckStatus.transit) {
			//
			// throw new BusinessException(String.format(
			// "司机(%s):%s 正在运输中，不能分配", truck.getTrack_no(), truck
			// .getAccount().getName()));
			// }
			//
			// splitList.clear();
			//
			// parent.setActualWeight(parent.getWeight());
			// parent.setParent(false);
			// parent.setParentId(null);
			// parent.setSplit(false);;
			// splitList.add(parent);
			// parent.setStockId(splitInfo.getStockId());
			// parent.setStockName(splitInfo.getStockName());
			// robOrderRecordInfoDao.merge(parent);
			// continue;
			// }
			boolean isFirst = true;
			RobOrderRecordInfo firstRobrecrodInfo = null;
			for (RobOrderRecordInfo splitInfo : splitList) {

				if (StringUtil.isEmpty(splitInfo.getStockId())) {
					throw new BusinessException("还有货物未分配车辆，请分配车辆后确认。");
				}
				Truck truck = truckDao.getTruckById(splitInfo.getStockId());

				if (truck == null) {

					throw new BusinessException(String.format("未找到司机%s",
							splitInfo.getStockId()));

				}
				if (truck.getTruckStatus() == TruckStatus.transit) {

					throw new BusinessException(String.format(
							"司机(%s):%s 正在运输中，不能分配", truck.getTrack_no(), truck
									.getAccount().getName()));
				}

				// 是第一个且这个下面的子节点>1才拆分
				splitInfo.setParent(isFirst && splitList.size() > 1);
				splitInfo.setSplit(isFirst && splitList.size() > 1);
				if (firstRobrecrodInfo != null)
					splitInfo.setParentId(firstRobrecrodInfo.getId());
				splitInfo.setAdd_user_id(account.getId());
				splitInfo.setCreate_time(new Date());
				splitInfo.setGoodsDetail(parent.getGoodsDetail());
				splitInfo.setRobOrderRecord(parent.getRobOrderRecord());
				splitInfo.setActualWeight(splitInfo.getWeight());
				splitInfo.setStockName(truck.getAccount().getName());

				robOrderRecordInfoDao.merge(splitInfo);
				if (isFirst) {
					isFirst = false;
					firstRobrecrodInfo = splitInfo;
				}
			}

		}

		// 按司机分配运单
		List<RobOrderRecordInfo> listRoborderRecordInfos = new ArrayList<RobOrderRecordInfo>();
		for (String recordInfoID : group.keySet()) {
			listRoborderRecordInfos.addAll(group.get(recordInfoID));

		}
		this.saveConfirmRobOrderNew(roborderRecordId, listRoborderRecordInfos,
				payPassword);
		// Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
		// log.info(AppUtil.getGson().toJson(group));
		// throw new
		// BusinessException(AppUtil.getGson().toJson(group.toString()));
		return map;

	}

	private double sumWeightWithParent(String id, List<RobOrderRecordInfo> list) {
		double sum = 0;
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			if (id.equals(robOrderRecordInfo.getParentId())) {
				sum += robOrderRecordInfo.getWeight();
			}
		}
		return sum;
	}

	public BigDecimal getSuccessRobOrderPayment(int truckCount) {
		ParameterManage parameterManage = parameterManageDao
				.getTypeInfo(ParaType.driver);
		BigDecimal money = new BigDecimal(parameterManage.getValue())
				.multiply(new BigDecimal(truckCount));

		return money;
	}

	public void saveConfirmRobOrderNew(String robOrderRecordId,
			List<RobOrderRecordInfo> infos, String payPassword) {

		String inPoint = "com.memory.platform.module.goods.service.impl.RobOrderRecordService.saveConfirmRobOrder";
		RobOrderRecord record_p = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderRecordId);
		GoodsBasic goodsBasic = goodsBasicDao.getObjectById(GoodsBasic.class,
				record_p.getGoodsBasic().getId());
		List<RobOrderRecordInfo> list = infos;
		Account user = UserUtil.getAccount();
		// List<RobOrderRecordInfo> dbList = new
		// ArrayList<RobOrderRecordInfo>();
		// for (RobOrderRecordInfo info : list) {
		// RobOrderRecordInfo dbInfo =
		// robOrderRecordInfoDao.getRobOrderRecordInfoById(info.getId());
		// dbList.add(dbInfo);
		// dbInfo.setStockId(info.getStockId());
		// }

		if (StringUtil.isEmpty(payPassword)) {
			throw new BusinessException("请输入支付密码。");
		}
		if (!AppUtil.md5(payPassword).equals(user.getPaypassword())) {
			throw new BusinessException("您输入的支付密码不正确。");
		}
		for (RobOrderRecordInfo robOrderRecordInfo : list) {
			if (robOrderRecordInfo.getStockId() == null
					|| robOrderRecordInfo.getStockId().equals("")) {
				throw new BusinessException("还有货物未分配车辆，请分配车辆后确认。");
			}

		}
		record_p.setStatus(RobOrderRecord.Status.end);
		this.robOrderRecordDao.merge(record_p);
		String[] truckIdArr = this.getTruckIds(list);
		ParameterManage parameterManage = parameterManageDao
				.getTypeInfo(ParaType.driver);
		// BigDecimal money = new
		// BigDecimal(parameterManage.getValue()).multiply(new
		// BigDecimal(truckIdArr.length));
//		BigDecimal money = this.getSuccessRobOrderPayment(truckIdArr.length);
		BigDecimal money = new BigDecimal(record_p.getDeposit_level_money());
		String remark = "抢单确认成功，冻结运单押金金额：" + String.format("%.2f", money)
				+ "元，抢单单号：" + record_p.getRobOrderNo();
		capitalAccountService.saveFreezeTool(user, money, remark,
				user.getName());

		/** 订单日志 **/
		OrderAutLog orderAutLog = new OrderAutLog();
		orderAutLog.setRobOrderRecord(record_p);
		orderAutLog.setAfterStatus(RobOrderRecord.Status.end);
		orderAutLog.setBeforeStatus(record_p.getStatus());
		orderAutLog.setRemark(remark);
		orderAutLog.setTitle("【抢单确认】");
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setAuditPerson(user.getName());
		orderAutLog.setAuditPersonId(user.getId());
		orderAutLogDao.save(orderAutLog);
		/** 订单日志 **/
		// List<RobOrderConfirm> orderConfirms = new
		// ArrayList<RobOrderConfirm>();
		double trackMon=0;
		double transMon=0;
		for (String truckId : truckIdArr) {
			RobOrderConfirm robOrderConfirm = new RobOrderConfirm();
			robOrderConfirm.setRobOrderId(record_p.getId());
			robOrderConfirm.setTransportNo("YLTS" + DateUtil.dateNo());// 保存运单号
			robOrderConfirm.setTurckCost(parameterManage.getValue());// 保存车辆总金额
			//标记 1
			if(truckId.equals(truckIdArr[truckIdArr.length-1])) {
				robOrderConfirm.setTurckDeposit(record_p.getDeposit_level_money()-trackMon);// 保存车辆押金  按车平分 平分除不尽时为最后一位 保留2位小数
			}else {
				BigDecimal decimal = new BigDecimal(record_p.getDeposit_level_money()/truckIdArr.length);
				BigDecimal setScale = decimal.setScale(2,BigDecimal.ROUND_DOWN);//保留两位小数--去尾
				robOrderConfirm.setTurckDeposit(setScale.doubleValue());// 保存车辆押金  按车平分 平分除不尽时为最后一位 保留2位小数
				trackMon+=setScale.doubleValue();
			}
			
			robOrderConfirm.setRobOrderNo(record_p.getRobOrderNo());
			robOrderConfirm.setTurckId(truckId);
			robOrderConfirm.setSpecialStatus(SpecialStatus.none);
			robOrderConfirm.setSpecialType(SpecialType.none);
			robOrderConfirm.setRocessingResult(RocessingResult.none);
			Truck truck = truckDao.getObjectById(Truck.class, truckId);
			robOrderConfirm.setTurckUserId(truck.getAccount().getId());
			robOrderConfirm.setStatus(RobOrderConfirm.Status.receiving);
			double sumWeight = getGoodsActualWeight(truckId, list);
			robOrderConfirm.setTotalWeight(sumWeight);
			robOrderConfirm.setUnitPrice(record_p.getUnitPrice());
			robOrderConfirm.setTransportationCost(Arith.mul(
					record_p.getUnitPrice(), sumWeight, 4));// 计算车辆所落货物金额
//			robOrderConfirm.setTransportationDeposit(Arith.mul(
//					record_p.getDepositUnitPrice(), sumWeight, 4));// 计算车辆所落货物押金
			
			///这里和 标记 1的算法一模一样，保证司机获得的车辆押金和运输押金一致，
			if(truckId.equals(truckIdArr[truckIdArr.length-1])) {
				robOrderConfirm.setTransportationDeposit(record_p.getDeposit_level_money()-transMon);// 保存运输押金  按车平分 平分除不尽时为最后一位 保留2位小数
			}else {
				BigDecimal decimal = new BigDecimal(record_p.getDeposit_level_money()/truckIdArr.length);
				BigDecimal setScale = decimal.setScale(2,BigDecimal.ROUND_DOWN);//保留两位小数--去尾
				robOrderConfirm.setTransportationDeposit(setScale.doubleValue());// 保存运输押金  按车平分 平分除不尽时为最后一位 保留2位小数
				transMon+=setScale.doubleValue();
			}
			
			robOrderConfirm.setCreate_time(new Date());
			robOrderConfirm.setConfirmData(new Date());
			// orderConfirms.add(robOrderConfirm);
			robOrderConfirmDao.save(robOrderConfirm);

			// lix 2017-08-14 注释 修改 通知方式
			// String sendMsg = "尊敬的驾驶员您好，有一单订单号为：" + record_p.getRobOrderNo() +
			// "，运单号为："
			// + robOrderConfirm.getTransportNo() +
			// "的运输单已分配到你处，请及时登录易林物流APP查看，并和发货人取得联系进行运输任务。";
			// Map<String, Object> map =
			// sendMessageService.saveRecordAndSendMessage(truck.getAccount().getPhone(),
			// sendMsg, inPoint);
			// if (!Boolean.valueOf(map.get("success").toString())) {
			// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
			// }
			// lix 2017-08-14 添加 修改 通知方式
			String sendMsg = "尊敬的司机用户您好，您有一笔抢单单号为：" + record_p.getRobOrderNo()
					+ "，运单号为：" + robOrderConfirm.getTransportNo()
					+ "的运单信息已分配到您处，请您尽快登录小镖人APP查看详情，并联系发货人进行下一步运输任务！";
			Map<String, String> actionMap = new HashMap<String, String>();
			actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
			pushService.saveRecordAndSendMessageWithAccountID(truck
					.getAccount().getId(), "【抢单分配】", sendMsg, actionMap,
					"RobOrderRecordService.saveConfirmRobOrderNew");

			/** 确认订单日志 **/
			OrderAutLog log = new OrderAutLog();
			log.setRobOrderRecord(record_p);
			log.setConfirmStatus(RobOrderConfirm.Status.receiving);
			log.setBeforeStatus(record_p.getStatus());
			String remarkFenPei = "【" + user.getName() + "】分配给【"
					+ truck.getAccount().getName() + "】,总重：" + sumWeight
					+ "吨,运单号：" + robOrderConfirm.getTransportNo();
			log.setRemark(remarkFenPei);
			log.setTitle("【运单分配】");
			log.setTurckId(truck.getId());
			log.setTurckUserId(truck.getAccount().getId());
			log.setCreate_time(new Date());
			log.setAuditPerson(user.getName());
			log.setAuditPersonId(user.getId());
			log.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLogDao.save(log);
			/** 确认订单日志 **/

		}
		// truckService.updateTruckStatus(truckIdArr, TruckStatus.transit);
		// robOrderConfirmDao.saveRobOrderConfirmList(orderConfirms);
		/** 短信发送 **/
		// lix 2017-08-14注释 修改 通知方式
		// String sendMsg = "尊敬的用户您好！您已完成抢单,订单号：" + record_p.getRobOrderNo() +
		// "，已支付运输预付款：" + String.format("%.2f", money)
		// + "元。";
		// Map<String, Object> map =
		// sendMessageService.saveRecordAndSendMessage(user.getPhone(), sendMsg,
		// inPoint);
		// if (!Boolean.valueOf(map.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改 通知方式
		String sendMsg = "尊敬的司机用户您好，您已经成功确认抢单,抢单单号为："
				+ record_p.getRobOrderNo() + "，已经支付运输预付款："
				+ String.format("%.2f", money) + "元。";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_record_id", record_p.getId());
		pushService.saveRecordAndSendMessageWithAccountID(user.getId(),
				"【抢单确认】", sendMsg, actionMap,
				"RobOrderRecordService.saveConfirmRobOrderNew");

		// lix 2017-08-14注释 修改 通知方式
		// String sendMsg1 = "尊敬的货主用户您好！您有一笔订单号：" + record_p.getRobOrderNo() +
		// "的抢单信息，用户已确认，对方已支付车辆押金："
		// + String.format("%.2f", money) + "元。";
		// Map<String, Object> map1 =
		// sendMessageService.saveRecordAndSendMessage(goodsBasic.getAccount().getPhone(),
		// sendMsg1, inPoint);
		// if (!Boolean.valueOf(map1.get("success").toString())) {
		// throw new BusinessException("短信提示异常，请联系管理员,审核失败。");
		// }

		// lix 2017-08-14 添加 修改 通知方式
		String sendMsg1 = "尊敬的货主用户您好，您有一笔抢单单号为：" + record_p.getRobOrderNo()
				+ "的抢单信息，司机用户已确认，对方已支付车辆押金：" + String.format("%.2f", money)
				+ "元。";
		Map<String, String> actionMap1 = new HashMap<String, String>();
		actionMap1.put("rob_order_record_id", record_p.getId());
		pushService.saveRecordAndSendMessageWithAccountID(goodsBasic
				.getAccount().getId(), "【抢单确认】", sendMsg1, actionMap1,
				"RobOrderRecordService.saveConfirmRobOrderNew");
		/** 短信发送 **/

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

	/**
	 * 功能描述： 获取被拆分货物的总重量 输入参数: 异 常： 创 建 人: lil 日 期: 2016年8月10日上午10:55:43 修 改 人:
	 * 日 期: 返 回：double
	 */
	@Override
	public Map<String, Object> getSuccessRobOrderPayment(
			String splitRoborderRecordInfoJson, int truckCount,String roborderRecordId) {

		Account account = UserUtil.getAccount();
		
		Map<String, Object> map = new HashMap<>();
		if (truckCount > 0) {

		} else if (StringUtil.isNotEmpty(splitRoborderRecordInfoJson)) {

			List<RobOrderRecordInfo> infos = JsonPluginsUtil.jsonToBeanList(
					splitRoborderRecordInfoJson, RobOrderRecordInfo.class);
			List<String> arrTruckID = new ArrayList<>();
			
			for (RobOrderRecordInfo info : infos) {
				if (arrTruckID.contains(info.getStockId()) == false) {
					arrTruckID.add(info.getStockId());
				}
			}
			truckCount = arrTruckID.size();
			
			if(roborderRecordId==null) {
				roborderRecordId=(String) robOrderRecordInfoDao.getRobOrderRecordInfoById(infos.get(0).getParentId(), account).get("robOrderId");
			}
		}
		CapitalAccount capAccount = capitalAccountService
				.getCapitalAccount(account.getId());
		//BigDecimal bd = this.getSuccessRobOrderPayment(truckCount);
//		map.put("payMoney", bd.setScale(2, BigDecimal.ROUND_HALF_UP)
//				.doubleValue());
		Map<String, Object> reMap=robOrderRecordDao.getRobOrderRecordById(roborderRecordId, account);
		map.put("payMoney",reMap.get("deposit_level_money"));
		map.put("avaiable", capAccount.getAvaiable());
		return map;
	}

	/*
	 * 获取我对这个货物的抢单记录
	 * 
	 * @see com.memory.platform.module.order.service.IRobOrderRecordService#
	 * getMyRobOrderRecordWithGoodsBasicID(java.lang.String,
	 * com.memory.platform.entity.member.Account)
	 */
	@Override
	public List<RobOrderRecord> getMyRobOrderRecordWithGoodsBasicID(
			String goodsBasicID, Account account) {

		return robOrderRecordDao.getMyRobOrderRecordWithGoodsBasicID(
				goodsBasicID, account);
	}

	@Override
	public Map<String, Object> getSolrRobOrderRecordByIds(String ids, Account account, int start, int size) {
		return robOrderRecordDao.getSolrRobOrderRecordByIds(ids,account,start,size);
	}

}
