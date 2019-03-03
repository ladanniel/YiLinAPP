package com.memory.platform.module.order.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.ehcache.hibernate.management.impl.QueryStats;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.SelfDescribing;
import org.hibernate.service.spi.Manageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.additional.AdditionalExpensesRecord;
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.RoleType;
import com.memory.platform.entity.order.OrderAutLog;
import com.memory.platform.entity.order.OrderSpecialApply;
import com.memory.platform.entity.order.OrderWaybillLog;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderConfirm.LockStatus;
import com.memory.platform.entity.order.RobOrderConfirm.PaymentType;
import com.memory.platform.entity.order.RobOrderConfirm.QueryStatus;
import com.memory.platform.entity.order.RobOrderConfirm.RocessingResult;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialStatus;
import com.memory.platform.entity.order.RobOrderConfirm.SpecialType;
import com.memory.platform.entity.order.RobOrderConfirm.Status;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.order.RobOrderRecordInfo;
import com.memory.platform.entity.sys.LgisticsCompanies;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.ArrayUtil;
import com.memory.platform.global.DoubleHelper;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.OSSClientUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.additional.service.IAdditionalExpensesRecordService;
import com.memory.platform.module.capital.dao.CapitalAccountDao;
import com.memory.platform.module.capital.service.impl.CapitalAccountService;
import com.memory.platform.module.goods.dao.GoodsBasicDao;
import com.memory.platform.module.goods.dao.GoodsDetailDao;
import com.memory.platform.module.goods.service.IGoodsDetailService;
import com.memory.platform.module.order.dao.OrderAutLogDao;
import com.memory.platform.module.order.dao.OrderSpecialApplyDao;
import com.memory.platform.module.order.dao.OrderWaybillLogDao;
import com.memory.platform.module.order.dao.RobOrderConfirmDao;
import com.memory.platform.module.order.dao.RobOrderRecordDao;
import com.memory.platform.module.order.dao.RobOrderRecordInfoDao;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.own.service.IRobOrderConfirmOwnService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.dao.AccountDao;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.truck.dao.TruckDao;

/**
 * 创 建 人： 武国庆 日 期： 2016年6月17日 上午10:29:50 修 改 人： 日 期： 描 述： 我的订单接口服务实现类 版 本 号：
 * V1.0
 */
@Service
@Transactional
public class RobOrderConfirmService implements IRobOrderConfirmService {
	@Autowired
	RobOrderConfirmDao robOrderConfirmDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	OrderAutLogDao orderAutLogDao;

	@Autowired
	RobOrderRecordDao robOrderRecordDao;

	@Autowired
	OrderWaybillLogDao orderWaybillLogDao;

	@Autowired
	OrderSpecialApplyDao orderSpecialApplyDao;

	@Autowired
	CapitalAccountService capitalAccountService;

	@Autowired
	IGoodsDetailService goodsDetailService;

	@Autowired
	GoodsBasicDao goodsBasicDao;

	@Autowired
	GoodsDetailDao goodsDetailDao;

	@Autowired
	RobOrderRecordInfoDao robOrderRecordInfoDao;

	@Autowired
	ISendMessageService sendMessageService;

	@Autowired
	CapitalAccountDao capitalAccountDao;
	@Autowired
	TruckDao truckDao;

	@Autowired
	IPushService pushService;

	@Autowired
	private IRobOrderRecordService orderRecordService;
	@Autowired
	private IRobOrderConfirmOwnService robOrderConfirmOwnService;
	@Autowired
	private IAdditionalExpensesRecordService additionalExpensesReocrdService;

	@Override
	public Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord,
			int start, int limit) {
		return robOrderConfirmDao.getMyRobOrderPage(robOrderRecord, start,
				limit);
	}

	@Override
	public Map<String, Object> getReportFormsPage(
			RobOrderRecord robOrderRecord, String goodsName, String trackNo,
			int start, int limit) {
		return robOrderConfirmDao.getReportFormsPage(robOrderRecord, goodsName,
				trackNo, start, limit);
	}

	@Override
	public Map<String, Object> getTruckPage(String robOrderId, int start,
			int limit) {
		return robOrderConfirmDao.getTruckPage(robOrderId, start, limit);
	}

	@Override
	public Map<String, Object> getOrderInfoPage(String robOrderId,
			String truckID, int start, int limit) {
		return robOrderConfirmDao.getOrderInfoPage(robOrderId, truckID, start,
				limit);
	}

	@Override
	public RobOrderConfirm findRobOrderConfirm(String robOrderId,
			String truckUserID) {
		return robOrderConfirmDao.findRobOrderConfirm(robOrderId, truckUserID);
	}

	@Override
	public Map<String, Object> getTruckAll(String robOrderId) {
		// TODO Auto-generated method stub
		return robOrderConfirmDao.getTruckAll(robOrderId);
	}

	@Override
	public RobOrderConfirm findRobOrderConfirm(RobOrderConfirm robOrderConfirm) {
		return robOrderConfirmDao.findRobOrderConfirm(robOrderConfirm);
	}

	@Override
	public Map<String, Object> getTruckAll(RobOrderConfirm robOrderConfirm) {
		return robOrderConfirmDao.getTruckAll(robOrderConfirm);
	}

	@Override
	public void savereceiptuser(RobOrderConfirm robOrderConfirm, String[] path,
			String rootPath) {
		String receiptImg = "";
		for (String str : path) {
			try {
				ImageFileUtil.move(
						new File(rootPath + str),
						new File(rootPath
								+ str.substring(0, str.lastIndexOf("/"))
										.replace("temporary", "uploading")));
				FileInputStream fin = new FileInputStream(new File(rootPath
						+ str.replace("temporary", "uploading")));
				String url = OSSClientUtil.uploadFile2OSS(fin,
						str.substring(str.lastIndexOf("/"), str.length()),
						"idcard");// 此处是上传到阿里云OSS的步骤
				receiptImg += url + ",";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		RobOrderConfirm rc = robOrderConfirmDao.getObjectById(
				RobOrderConfirm.class, robOrderConfirm.getId());
		receiptImg = receiptImg.substring(0, receiptImg.length() - 1);
		rc.setReceiptImg(receiptImg);
		rc.setAccountId(robOrderConfirm.getAccountId());
		rc.setStatus(Status.confirmreceipt);// 送还回执中
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.add(calendar.DATE, 3);// 把日期往后增加3天.整数往后推,负数往前移动
		date = calendar.getTime();
		// rc.setConfirmData(new Date());
		rc.setConfirm_receipted_date(date); // 用来判断最后三天自动付款
		Account account = UserUtil.getUser();
		OrderAutLog log = new OrderAutLog();

		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderConfirm.getRobOrderId());

		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.confirmreceipt);
		log.setReceiptUserID(robOrderConfirm.getAccountId());
		log.setReceiptImg(receiptImg);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【回执人员分配】");
		// lix 2017-08-17 添加 获取 回执单收货人员
		Account receiptAccount = accountDao.getObjectById(Account.class,
				robOrderConfirm.getAccountId());
		log.setRemark("回执人员：【" + receiptAccount.getName() + "】   联系电话："
				+ receiptAccount.getPhone());
		log.setRobOrderConfirmId(rc.getId());
		orderAutLogDao.save(log);
		robOrderConfirmDao.merge(rc);

		Account robbedAccoun = accountDao.getObjectById(Account.class,
				robOrderRecord.getRobbedAccountId());
		// lix 2017-08-17 注释 修改消息通知方式
		// String msg = "回执发还：尊敬的货主用户您好，您的订单号：" +
		// robOrderConfirm.getRobOrderNo()
		// + "，运单号：" + robOrderConfirm.getTransportNo()
		// + "的货物回执已经到达易林公司。易林工作人员正在为您提供上门送还回执的服务，轻您耐心等待。";
		// sendMessageService
		// .saveRecordAndSendMessage(
		// robbedAccoun.getPhone(),
		// msg,
		// "com.memory.platform.module.order.controller.RobOrderConfirmController.savereceiptuser");
		// lix 2017-08-17 添加 修改消息通知方式
		String tmp = "尊敬的货主用户您好，您有一笔抢单单号为：" + rc.getRobOrderNo() + "，运单单号为："
				+ rc.getTransportNo()
				+ "的货物回执单已经到达公司。工作人员正在为您提供上门送还回执单的服务，请您耐心等待！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderRecord.getRobbedAccountId(), "【回执人员分配】", tmp,
				actionMap, "RobOrderConfirmService.savereceiptuser");

	}

	/*
	 * getReceipttask
	 */
	@Override
	public Map<String, Object> getReceipttask(RobOrderRecord robOrderRecord,
			int start, int limit) {
		return robOrderConfirmDao.getReceipttask(robOrderRecord, start, limit);
	}

	/*
	 * findRobOrderConfirmById
	 */
	@Override
	public RobOrderConfirm findRobOrderConfirmById(String id) {
		return robOrderConfirmDao.getObjectById(RobOrderConfirm.class, id);
	}

	@Override
	public Map<String, Object> getCompanyOrderPage(
			RobOrderRecord robOrderRecord, int start, int limit) {
		return robOrderConfirmDao.getCompanyOrderPage(robOrderRecord, start,
				limit);
	}

	@Override
	public Map<String, Object> getSpecialOrderPage(Account account,
			RobOrderRecord robOrderRecord, int start, int limit) {
		return robOrderConfirmDao.getSpecialOrderPage(account, robOrderRecord,
				start, limit);
	}

	@Override
	public void savespecialorderinfo(Account account,
			RobOrderConfirm robOrderConfirm,
			OrderSpecialApply orderSpecialApply, String remark, String type) {

		SpecialStatus specialStatus = null;

		if ("1".equals(type)) {// 处理完成
			specialStatus = SpecialStatus.success;
			robOrderConfirm.setLockStatus(LockStatus.unlock);
		} else {
			specialStatus = SpecialStatus.processing;
		}

		// 特殊状态日志
		OrderWaybillLog log = new OrderWaybillLog();
		log.setRobOrderConfirm(robOrderConfirm);
		log.setConfirmStatus(robOrderConfirm.getStatus());
		log.setSpecialStatus(specialStatus);
		log.setSpecialType(SpecialType.emergency);
		log.setDealtPersonName(account.getName());
		log.setDealtPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setOrderSpecialApplyId(orderSpecialApply.getId());
		log.setRemark(remark);
		orderWaybillLogDao.save(log);

		OrderAutLog orderAutLog = new OrderAutLog();

		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderConfirm.getRobOrderId());
		orderAutLog.setRobOrderRecord(robOrderRecord);
		orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setTitle("【急救处理】");
		orderAutLog.setSpecialStatus(specialStatus);
		orderAutLog.setSpecialType(SpecialType.emergency);
		orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLog.setRemark(remark);
		orderAutLogDao.save(orderAutLog);

		orderSpecialApply.setSpecialStatus(specialStatus);
		if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
			orderSpecialApply.setDealtPersonId(account.getId());
		}
		orderSpecialApplyDao.merge(orderSpecialApply);

		// 更新状态
		robOrderConfirm.setSpecialStatus(specialStatus);

		robOrderConfirmDao.merge(robOrderConfirm);

	}

	/*
	 * 仲裁
	 */
	@Override
	public void saveArbitrationNew(Account account, RobOrderConfirm confirm,
			String remark, RocessingResult rocessingResult) {
		String sendPoint = "RobOrderConfirmService.saveArbitrationNew";
		SpecialStatus specialStatus = SpecialStatus.success;
		RobOrderConfirm robOrderConfirm = this.getRobOrderConfirmById(confirm
				.getId());
		if (robOrderConfirm == null) {
			throw new BusinessException("该运单不存在");

		}
		if (robOrderConfirm.getStatus().equals(Status.singlepin)) {

			throw new BusinessException("该运单已经消单不能操作");

		}
		if (robOrderConfirm.getStatus().equals(Status.ordercompletion)) {

			throw new BusinessException("该运单已经完结不能操作");
		}
		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderConfirm.getRobOrderId());
		// 司机
		Account truckUser = accountDao.getAccount(robOrderConfirm
				.getTurckUserId());

		// robOrderRecordDao.getObjectById(RobOrderRecord.class,robOrderConfirm.getr)
		// 货主资金账户
		CapitalAccount consignCapitalAccount = capitalAccountDao
				.getCapitalAccount(robOrderRecord.getRobbedAccountId());
		// 车队资金账户
		CapitalAccount truckCapitalAccount = capitalAccountDao
				.getCapitalAccount(truckUser.getCompany().getAccount_id());
		// 仲裁申请人
		OrderSpecialApply queryApply = new OrderSpecialApply();
		queryApply.setRobOrderConfirmId(robOrderConfirm.getId());
		queryApply.setSpecialType(SpecialType.arbitration);
		queryApply.setSpecialStatus(SpecialStatus.suchprocessing);

		OrderSpecialApply orderSpecialApply = orderSpecialApplyDao
				.getOrderSpecialApply(queryApply);
		if (orderSpecialApply == null) {
			throw new BusinessException("未找到申请仲裁记录");
		}
		if (RocessingResult.noperation.equals(rocessingResult)) { // 继续执行运单流程
			OrderAutLog orderAutLog = new OrderAutLog();
			remark += "【继续执行运单】";
			orderAutLog.setRobOrderRecord(robOrderRecord);
			orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
			orderAutLog.setAuditPerson(account.getName());
			orderAutLog.setAuditPersonId(account.getId());
			orderAutLog.setCreate_time(new Date());
			orderAutLog.setTitle("【仲裁处理】");
			orderAutLog.setSpecialStatus(specialStatus);
			orderAutLog.setSpecialType(SpecialType.arbitration);
			orderAutLog.setRocessingResult(rocessingResult);
			orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLog.setRemark(remark);
			orderAutLogDao.save(orderAutLog);

			String remark_1 = "尊敬的用户您好，您有一笔运单单号为："
					+ robOrderConfirm.getTransportNo() + "的运单仲裁已处理,处理结果为:"
					+ remark;
			Map<String, String> extendData = new HashMap<String, String>();
			extendData.put("rob_order_confirm_id", robOrderConfirm.getId());
			extendData.put("arbitration", "true");

			pushService.saveRecordAndSendMessageWithAccountID(
					consignCapitalAccount.getAccount().getId(), "【仲裁】",
					remark_1, extendData, sendPoint);
			pushService.saveRecordAndSendMessageWithAccountID(
					truckUser.getId(), "【仲裁】", remark_1, extendData, sendPoint);

			// 特殊状态日志
			OrderWaybillLog log = new OrderWaybillLog();
			log.setRobOrderConfirm(robOrderConfirm);
			log.setConfirmStatus(robOrderConfirm.getStatus());
			log.setSpecialStatus(specialStatus);
			log.setSpecialType(SpecialType.arbitration);
			log.setDealtPersonName(account.getName());
			log.setDealtPersonId(account.getId());
			log.setCreate_time(new Date());
			log.setOrderSpecialApplyId(orderSpecialApply.getId());
			log.setRemark(remark);
			log.setRocessingResult(rocessingResult);
			orderWaybillLogDao.save(log);

			orderSpecialApply.setRocessingResult(rocessingResult);
			orderSpecialApply.setSpecialStatus(specialStatus);
			if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
				orderSpecialApply.setDealtPersonId(account.getId());
			}
			orderSpecialApplyDao.merge(orderSpecialApply);
			// 更新状态
			robOrderConfirm.setLockStatus(LockStatus.unlock);
			robOrderConfirm.setSpecialStatus(specialStatus);
			robOrderConfirmDao.merge(robOrderConfirm);
			return;

		}
		Account applyAccount = accountDao.getObjectById(Account.class,
				orderSpecialApply.getApplypersonid());// 申请仲裁人
		// 转账金额
		double indemnity = 0.0;
		do {
			RobOrderConfirm.QueryStatus queryStatus = RobOrderConfirm
					.getQueryStatusWithStatus(robOrderConfirm.getStatus());
			if (RocessingResult.singlepin.equals(rocessingResult)) { // 消单
				// 货主退回金额
				BigDecimal money = new BigDecimal(0);
				if (queryStatus.equals(QueryStatus.waitReciving)) {
					money = new BigDecimal(
							robOrderConfirm.getTransportationDeposit());
				} else {

					money = new BigDecimal(robOrderConfirm.getTotalCost());
				}
				String remark_1 = "尊敬的货主用户您好，您有一笔运单单号为："
						+ robOrderConfirm.getTransportNo()
						+ "的运单已销单，解除冻结运输费用，金额：" + String.format("%.2f", money)
						+ "元";
				capitalAccountService.updateFreezeTool(
						consignCapitalAccount.getAccount(), money, remark_1,
						account.getName());
				Map<String, String> extendData = new HashMap<String, String>();
				extendData.put("rob_order_confirm_id", robOrderConfirm.getId());
				extendData.put("arbitration", "true");
				pushService.saveRecordAndSendMessageWithAccountID(
						consignCapitalAccount.getAccount().getId(), "【仲裁】",
						remark_1, extendData, sendPoint);

				// 车队退回
				BigDecimal depositMoney = new BigDecimal(
						robOrderConfirm.getTurckDeposit());
				remark_1 = "尊敬的司机用户您好，您有一笔运单单号为："
						+ robOrderConfirm.getTransportNo()
						+ "的运单已销单，解除冻结运输押金费用，金额："
						+ String.format("%.2f", depositMoney) + "元";
				capitalAccountService.updateFreezeTool(
						truckCapitalAccount.getAccount(), depositMoney,
						remark_1, account.getName());
				
				pushService.saveRecordAndSendMessageWithAccountID(
						truckCapitalAccount.getAccount().getId(), "【仲裁】",
						remark_1, extendData, sendPoint);
				if (truckUser.getId().equals(
						truckCapitalAccount.getAccount().getId()) == false) { // 发送给司机
																				// 如果司机和车队长用户不一样
					pushService.saveRecordAndSendMessageWithAccountID(
							truckUser.getId(), "【仲裁】", remark_1, extendData,
							sendPoint);
				}
				remark_1 = "销单处理："
						+ remark
						+ "，解冻运输"
						+ (queryStatus.equals(QueryStatus.waitReciving) ? "押金"
								: "费用") + String.format("%.2f", money) + "元【"
						+ consignCapitalAccount.getAccount().getName()
						+ "】，解冻车辆押金" + String.format("%.2f", depositMoney)
						+ "元【" + truckCapitalAccount.getAccount().getName()
						+ "】";
				OrderAutLog orderAutLog = new OrderAutLog();
				orderAutLog.setRobOrderRecord(robOrderRecord);
				orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
				orderAutLog.setAuditPerson(account.getName());
				orderAutLog.setAuditPersonId(account.getId());
				orderAutLog.setCreate_time(new Date());
				orderAutLog.setTitle("【仲裁处理】");
				orderAutLog.setRemark(remark_1);
				orderAutLog.setSpecialStatus(specialStatus);
				orderAutLog.setSpecialType(SpecialType.arbitration);
				orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
				orderAutLogDao.save(orderAutLog);

				orderSpecialApply.setSpecialStatus(SpecialStatus.success);
				// 特殊状态日志
				OrderWaybillLog log = new OrderWaybillLog();
				log.setRobOrderConfirm(robOrderConfirm);
				log.setConfirmStatus(robOrderConfirm.getStatus());
				log.setSpecialStatus(specialStatus);
				log.setSpecialType(SpecialType.arbitration);
				log.setDealtPersonName(account.getName());
				log.setDealtPersonId(account.getId());
				log.setCreate_time(new Date());
				log.setOrderSpecialApplyId(orderSpecialApply.getId());
				log.setRemark(remark_1);
				orderWaybillLogDao.save(log);

				orderSpecialApply.setSpecialStatus(specialStatus);
				if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
					orderSpecialApply.setDealtPersonId(account.getId());
				}
				orderSpecialApplyDao.merge(orderSpecialApply);
				// 更新状态
				robOrderConfirm.setSpecialStatus(specialStatus);
				robOrderConfirm.setStatus(Status.singlepin);
				robOrderConfirmDao.merge(robOrderConfirm);
				break;
			}
			if (RocessingResult.indemnify.equals(rocessingResult)) { // 赔付

				CapitalAccount capitalAccount = null; // 转账资金账户
				CapitalAccount toCapitalAccount = null; // 接受转账资金账户
				String preRemark = "";
				if (applyAccount.getRole_type().equals(RoleType.consignor)) { // 货主申请的
					capitalAccount = truckCapitalAccount;
					toCapitalAccount = consignCapitalAccount;
					BigDecimal money = new BigDecimal(0);
					indemnity = robOrderConfirm.getTurckDeposit();
					if (QueryStatus.waitReciving.equals(queryStatus)) {// 等待装货
						// 运输押金总金额
						money = new BigDecimal(
								robOrderConfirm.getTransportationDeposit()); // 需要退回的金额
						String remark_1 = "尊敬的货主用户您好，您有一笔运单单号为："
								+ robOrderConfirm.getTransportNo()
								+ "的运单已销单，解除冻结运输押金，金额："
								+ String.format("%.2f", money) + "元";

						capitalAccountService.updateFreezeTool(
								consignCapitalAccount.getAccount(), money,
								remark_1, account.getName());
						preRemark = "【"
								+ consignCapitalAccount.getAccount().getName()
								+ "】解除冻结运输押金" + String.format("%.2f", money);
					} else {
						money = new BigDecimal(robOrderConfirm.getTotalCost()); // 运输费用
																				// 需要退回的金额
						String remark_1 = "尊敬的货主用户您好，您有一笔运单单号为："
								+ robOrderConfirm.getTransportNo()
								+ "的运单已销单，解除冻结运输费用，金额："
								+ String.format("%.2f", money) + "元";
						capitalAccountService.updateFreezeTool(
								consignCapitalAccount.getAccount(), money,
								remark_1, account.getName());
						preRemark = "【"
								+ consignCapitalAccount.getAccount().getName()
								+ "】解除冻结运输费" + String.format("%.2f", money);
					}
				} else if (applyAccount.getRole_type().equals(RoleType.truck)) { // 车队申请的
																					// 退回车队押金
					capitalAccount = consignCapitalAccount;
					toCapitalAccount = truckCapitalAccount;
					indemnity = 0;
					BigDecimal money = new BigDecimal(
							robOrderConfirm.getTurckDeposit()); // 需要退回的金额
					String remark_1 = "尊敬的司机用户您好，您有一笔运单单号为："
							+ robOrderConfirm.getTransportNo()
							+ "的运单已销单，解除冻结车辆押金，金额："
							+ String.format("%.2f", money) + "元";
					capitalAccountService.updateFreezeTool(
							truckCapitalAccount.getAccount(), money, remark_1,
							account.getName());
					preRemark = "【"
							+ truckCapitalAccount.getAccount().getName()
							+ "】解除冻结车辆押金" + String.format("%.2f", money);
					if (QueryStatus.waitReciving.equals(queryStatus)) {// 等待装货
						indemnity = robOrderConfirm.getTransportationDeposit();

					} else {
						indemnity = robOrderConfirm.getTotalCost();
					}
				}

				// 赔偿费用
				String remarkindemnity = "接受仲裁，支付赔偿费用：" + indemnity
						+ "元，抢单单号为：" + robOrderConfirm.getRobOrderNo()
						+ ",运单单号为:" + robOrderConfirm.getTransportNo();
				String toRemark = "仲裁受理成功，接受支付赔偿费用：" + indemnity + "元，抢单单号为："
						+ robOrderConfirm.getRobOrderNo() + ",运单单号为:"
						+ robOrderConfirm.getTransportNo();
				capitalAccountService.saveArbitrationCompensateFor(
						capitalAccount, toCapitalAccount, new BigDecimal(
								indemnity), MoneyRecord.Type.paid,
						remarkindemnity, toRemark);

				OrderAutLog orderAutLog = new OrderAutLog();
				orderAutLog.setRobOrderRecord(robOrderRecord);
				orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
				orderAutLog.setAuditPerson(account.getName());
				orderAutLog.setAuditPersonId(account.getId());
				orderAutLog.setCreate_time(new Date());
				orderAutLog.setTitle("【仲裁赔付】");
				orderAutLog.setSpecialStatus(specialStatus);
				orderAutLog.setSpecialType(SpecialType.arbitration);
				orderAutLog.setRocessingResult(rocessingResult);
				orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
				String orderRemark = remark + "," + preRemark + ","
						+ capitalAccount.getAccount().getName()
						+ "接受仲裁，支付赔偿费用：" + indemnity + "元,"
						+ toCapitalAccount.getAccount().getName()
						+ "仲裁受理成功，接受支付赔偿费用：" + indemnity + "元" + ",运单单号为:"
						+ robOrderConfirm.getTransportNo();
				orderAutLog.setRemark(orderRemark);
				orderAutLogDao.save(orderAutLog);
				Map<String, String> extendData = new HashMap<String, String>();
				extendData.put("rob_order_confirm_id", robOrderConfirm.getId());
				extendData.put("arbitration", "true");
				pushService.saveRecordAndSendMessageWithAccountID(
						consignCapitalAccount.getAccount().getId(), "【仲裁赔付】",
						orderRemark, extendData, sendPoint);
				pushService.saveRecordAndSendMessageWithAccountID(
						truckCapitalAccount.getAccount().getId(), "【仲裁赔付】",
						orderRemark, extendData, sendPoint);
				if (truckCapitalAccount.getAccount().getId()
						.equals(truckUser.getId()) == false) {
					pushService.saveRecordAndSendMessageWithAccountID(
							truckUser.getId(), "【仲裁赔付】", orderRemark,
							extendData, sendPoint);

				}
				// 特殊状态日志
				OrderWaybillLog log = new OrderWaybillLog();
				log.setRobOrderConfirm(robOrderConfirm);
				log.setConfirmStatus(Status.singlepin);
				log.setSpecialStatus(specialStatus);
				log.setSpecialType(SpecialType.arbitration);
				log.setDealtPersonName(account.getName());
				log.setDealtPersonId(account.getId());
				log.setCreate_time(new Date());
				log.setOrderSpecialApplyId(orderSpecialApply.getId());
				log.setRemark(orderRemark);
				log.setRocessingResult(rocessingResult);
				log.setIndemnity(indemnity);
				orderWaybillLogDao.save(log);

				orderSpecialApply.setRocessingResult(rocessingResult);
				orderSpecialApply.setSpecialStatus(specialStatus);
				orderSpecialApply.setIndemnity(indemnity);
				orderSpecialApply.setRocessingResult(rocessingResult);
				orderSpecialApply.setConfirmStatus(Status.singlepin);
				if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
					orderSpecialApply.setDealtPersonId(account.getId());
				}
				orderSpecialApplyDao.merge(orderSpecialApply);

				break;
			}

		} while (false);
		// 更新状态
		robOrderConfirm.setSpecialStatus(specialStatus);
		robOrderConfirm.setIndemnity(indemnity);
		robOrderConfirm.setRocessingResult(rocessingResult);
		robOrderConfirm.setLockStatus(LockStatus.unlock);
		robOrderConfirm.setStatus(Status.singlepin);// 销单
		robOrderConfirm.setEndDate(new Date());
		robOrderConfirmDao.merge(robOrderConfirm);

		// 还原货物
		double totalWeight = 0.0;
		List<RobOrderRecordInfo> infoList = robOrderRecordInfoDao
				.getListByRobOrderRecord(robOrderConfirm.getRobOrderId(),
						robOrderConfirm.getTurckId());
		for (RobOrderRecordInfo robOrderRecordInfo : infoList) {
			totalWeight += robOrderRecordInfo.getActualWeight();

			GoodsDetail goodsDetail = robOrderRecordInfo.getGoodsDetail();
			goodsDetail.setEmbarkWeight(goodsDetail.getEmbarkWeight()
					- robOrderRecordInfo.getActualWeight());
			goodsDetailDao.merge(goodsDetail);
		}

		GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
		goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight() - totalWeight);
		goodsBasicDao.merge(goodsBasic);
	}

	@Override
	public void savearbitration(Account account,
			RobOrderConfirm robOrderConfirm,
			OrderSpecialApply orderSpecialApply, String remark,
			String savetype, RocessingResult rocessingResult) {

		SpecialStatus specialStatus = null;
		RobOrderConfirm persistentConfirm = this
				.getRobOrderConfirmById(robOrderConfirm.getId());
		if (persistentConfirm == null) {
			throw new BusinessException("该运单不存在");

		}
		if (persistentConfirm.getStatus().equals(Status.singlepin)) {

			throw new BusinessException("该运单已经消单不能操作");

		}
		if (persistentConfirm.getStatus().equals(Status.ordercompletion)) {

			throw new BusinessException("该运单已经完结不能操作");
		}
		if ("0".equals(savetype)) {// 继续处理
			specialStatus = SpecialStatus.processing;

			OrderAutLog orderAutLog = new OrderAutLog();

			RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
					RobOrderRecord.class, robOrderConfirm.getRobOrderId());
			orderAutLog.setRobOrderRecord(robOrderRecord);
			orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
			orderAutLog.setAuditPerson(account.getName());
			orderAutLog.setAuditPersonId(account.getId());
			orderAutLog.setCreate_time(new Date());
			orderAutLog.setTitle("【仲裁处理】");
			orderAutLog.setSpecialStatus(specialStatus);
			orderAutLog.setSpecialType(SpecialType.arbitration);
			orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLog.setRemark(remark);
			orderAutLogDao.save(orderAutLog);

			// 特殊状态日志
			OrderWaybillLog log = new OrderWaybillLog();
			log.setRobOrderConfirm(robOrderConfirm);
			log.setConfirmStatus(robOrderConfirm.getStatus());
			log.setSpecialStatus(specialStatus);
			log.setSpecialType(SpecialType.arbitration);
			log.setDealtPersonName(account.getName());
			log.setDealtPersonId(account.getId());
			log.setCreate_time(new Date());
			log.setOrderSpecialApplyId(orderSpecialApply.getId());
			log.setRemark(remark);
			orderWaybillLogDao.save(log);

			orderSpecialApply.setSpecialStatus(specialStatus);
			if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
				orderSpecialApply.setDealtPersonId(account.getId());
			}
			orderSpecialApplyDao.merge(orderSpecialApply);
			// 更新状态
			robOrderConfirm.setSpecialStatus(specialStatus);
			robOrderConfirmDao.merge(robOrderConfirm);
			return;
		}

		specialStatus = SpecialStatus.success;

		if (RocessingResult.noperation.equals(rocessingResult)) { // 仲裁无操作

			OrderAutLog orderAutLog = new OrderAutLog();

			RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
					RobOrderRecord.class, robOrderConfirm.getRobOrderId());
			orderAutLog.setRobOrderRecord(robOrderRecord);
			orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
			orderAutLog.setAuditPerson(account.getName());
			orderAutLog.setAuditPersonId(account.getId());
			orderAutLog.setCreate_time(new Date());
			orderAutLog.setTitle("【仲裁处理】");
			orderAutLog.setSpecialStatus(specialStatus);
			orderAutLog.setSpecialType(SpecialType.arbitration);
			orderAutLog.setRocessingResult(rocessingResult);
			orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLog.setRemark(remark);
			orderAutLogDao.save(orderAutLog);

			// 特殊状态日志
			OrderWaybillLog log = new OrderWaybillLog();
			log.setRobOrderConfirm(robOrderConfirm);
			log.setConfirmStatus(robOrderConfirm.getStatus());
			log.setSpecialStatus(specialStatus);
			log.setSpecialType(SpecialType.arbitration);
			log.setDealtPersonName(account.getName());
			log.setDealtPersonId(account.getId());
			log.setCreate_time(new Date());
			log.setOrderSpecialApplyId(orderSpecialApply.getId());
			log.setRemark(remark);
			log.setRocessingResult(rocessingResult);
			orderWaybillLogDao.save(log);

			orderSpecialApply.setRocessingResult(rocessingResult);
			orderSpecialApply.setSpecialStatus(specialStatus);
			if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
				orderSpecialApply.setDealtPersonId(account.getId());
			}
			orderSpecialApplyDao.merge(orderSpecialApply);
			// 更新状态
			robOrderConfirm.setLockStatus(LockStatus.unlock);
			robOrderConfirm.setSpecialStatus(specialStatus);
			robOrderConfirmDao.merge(robOrderConfirm);
			return;
		}

		if (RocessingResult.indemnify.equals(rocessingResult)) { // 仲裁赔偿和消单

			Account applyAccount = accountDao.getObjectById(Account.class,
					orderSpecialApply.getApplypersonid());// 申请仲裁人
			double indemnity = 0.0;

			CapitalAccount capitalAccount = null; // 转账资金账户
			CapitalAccount toCapitalAccount = null; // 接受转账资金账户
			RobOrderConfirm.QueryStatus queryStatus = RobOrderConfirm
					.getQueryStatusWithStatus(robOrderConfirm.getStatus());

			if (applyAccount.getRole_type().equals(RoleType.consignor)) { // 货主申请的或者消单操作
																			// 退回押金或者运输金额和附加费
				indemnity = robOrderConfirm.getTurckDeposit(); // 车辆押金总金额 赔付金额
				Account turckUser = accountDao.getAccount(robOrderConfirm
						.getTurckUserId()); // 司机

				capitalAccount = capitalAccountService
						.getCapitalAccount(turckUser.getCompany()
								.getAccount_id());
				// toCapitalAccount =
				// capitalAccountService.getCapitalAccount(orderSpecialApply.getApplypersonid());
				toCapitalAccount = capitalAccountDao
						.getCapitalAccount(orderSpecialApply.getApplypersonid());
				// 解冻货主资金

				if (QueryStatus.waitReciving.equals(queryStatus)) {// 等待装货
					BigDecimal money = new BigDecimal(
							robOrderConfirm.getTransportationDeposit()); // 运输押金总金额
																			// 需要退回的金额
					String remark_1 = "尊敬的货主用户您好，您有一笔运单单号为："
							+ robOrderConfirm.getTransportNo()
							+ "的运单已销单，解除冻结运输押金，金额："
							+ String.format("%.2f", money) + "元";

					capitalAccountService.updateFreezeTool(accountDao
							.getAccount(orderSpecialApply.getApplypersonid()),
							money, remark_1, account.getName());
				} else {
					BigDecimal money = new BigDecimal(
							robOrderConfirm.getTransportationCost()); // 运输费用
																		// 需要退回的金额
					String remark_1 = "尊敬的货主用户您好，您有一笔运单单号为："
							+ robOrderConfirm.getTransportNo()
							+ "的运单已销单，解除冻结运输费用，金额："
							+ String.format("%.2f", money) + "元";
					capitalAccountService.updateFreezeTool(accountDao
							.getAccount(orderSpecialApply.getApplypersonid()),
							money, remark_1, account.getName());
				}

			}
			if (applyAccount.getRole_type().equals(RoleType.truck)) { // 车队申请的或者消单
				indemnity = robOrderConfirm.getTransportationDeposit(); // 运输押金总金额

				RobOrderRecord robOrderRecord = robOrderRecordDao
						.getObjectById(RobOrderRecord.class,
								robOrderConfirm.getRobOrderId());
				capitalAccount = capitalAccountService
						.getCapitalAccount(robOrderRecord.getRobbedAccountId()); // 货主账号

				Account turckUser = accountDao.getAccount(robOrderConfirm
						.getTurckUserId()); // 司机
				toCapitalAccount = capitalAccountService
						.getCapitalAccount(turckUser.getCompany()
								.getAccount_id());// 司机商户账号

				// 解冻司机资金
				BigDecimal money = new BigDecimal(
						robOrderConfirm.getTurckDeposit()); // / 车辆押金总金额
				String remark_1 = "尊敬的司机用户您好，您有一笔运单单号为："
						+ robOrderConfirm.getTransportNo()
						+ "的运单信息已销单，解除冻结车辆押金，金额："
						+ String.format("%.2f", money) + "元";

				capitalAccountService.updateArbitrationCompensateFor(
						toCapitalAccount, money, remark_1, account.getName());
				// 解冻货主运输费（总运输费-运输押金）
				if (!QueryStatus.waitReciving.equals(queryStatus)) {// 等待装货

					indemnity = robOrderConfirm.getActualTransportationCost();
				}
			}
			// 赔偿费用
			String remarkindemnity = "接受仲裁，支付赔偿费用：" + indemnity + "元，抢单单号为："
					+ robOrderConfirm.getRobOrderNo() + ",运单单号为:"
					+ robOrderConfirm.getTransportNo();
			String toRemark = "仲裁受理成功，接受支付赔偿费用：" + indemnity + "元，抢单单号为："
					+ robOrderConfirm.getRobOrderNo() + ",运单单号为:"
					+ robOrderConfirm.getTransportNo();
			if (rocessingResult
					.equals(RobOrderConfirm.RocessingResult.noperation)) {
				indemnity = 0;
			}

			if (indemnity != 0) {
				capitalAccountService.saveArbitrationCompensateFor(
						capitalAccount, toCapitalAccount, new BigDecimal(
								indemnity), MoneyRecord.Type.paid,
						remarkindemnity, toRemark);
			}
			// 货物信息还原到货物信息
			RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
					RobOrderRecord.class, robOrderConfirm.getRobOrderId());
			double totalWeight = 0.0;
			List<RobOrderRecordInfo> infoList = robOrderRecordInfoDao
					.getListByRobOrderRecord(robOrderConfirm.getRobOrderId(),
							robOrderConfirm.getTurckId());
			for (RobOrderRecordInfo robOrderRecordInfo : infoList) {
				totalWeight += robOrderRecordInfo.getActualWeight();

				GoodsDetail goodsDetail = robOrderRecordInfo.getGoodsDetail();
				goodsDetail.setEmbarkWeight(goodsDetail.getEmbarkWeight()
						- robOrderRecordInfo.getActualWeight());
				goodsDetailDao.merge(goodsDetail);
			}

			GoodsBasic goodsBasic = robOrderRecord.getGoodsBasic();
			goodsBasic.setEmbarkWeight(goodsBasic.getEmbarkWeight()
					- totalWeight);
			goodsBasicDao.merge(goodsBasic);

			OrderAutLog orderAutLog = new OrderAutLog();

			// RobOrderRecord robOrderRecord =
			// robOrderRecordDao.getObjectById(RobOrderRecord.class,
			// robOrderConfirm.getRobOrderId());
			orderAutLog.setRobOrderRecord(robOrderRecord);
			orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
			orderAutLog.setAuditPerson(account.getName());
			orderAutLog.setAuditPersonId(account.getId());
			orderAutLog.setCreate_time(new Date());
			orderAutLog.setTitle("【仲裁处理】");
			orderAutLog.setSpecialStatus(specialStatus);
			orderAutLog.setSpecialType(SpecialType.arbitration);
			orderAutLog.setRocessingResult(rocessingResult);
			orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLog.setRemark(remark);
			orderAutLogDao.save(orderAutLog);

			OrderAutLog orderAutLog1 = new OrderAutLog();
			orderAutLog1.setRobOrderRecord(robOrderRecord);
			orderAutLog1.setConfirmStatus(Status.singlepin);
			orderAutLog1.setAuditPerson(account.getName());
			orderAutLog1.setAuditPersonId(account.getId());
			orderAutLog1.setCreate_time(new Date());
			orderAutLog1.setTitle("【销单】");
			orderAutLog1.setRobOrderConfirmId(robOrderConfirm.getId());
			orderAutLogDao.save(orderAutLog1);

			// 特殊状态日志
			OrderWaybillLog log = new OrderWaybillLog();
			log.setRobOrderConfirm(robOrderConfirm);
			log.setConfirmStatus(Status.singlepin);
			log.setSpecialStatus(specialStatus);
			log.setSpecialType(SpecialType.arbitration);
			log.setDealtPersonName(account.getName());
			log.setDealtPersonId(account.getId());
			log.setCreate_time(new Date());
			log.setOrderSpecialApplyId(orderSpecialApply.getId());
			log.setRemark(remark);
			log.setRocessingResult(rocessingResult);
			log.setIndemnity(indemnity);
			orderWaybillLogDao.save(log);

			orderSpecialApply.setRocessingResult(rocessingResult);
			orderSpecialApply.setSpecialStatus(specialStatus);
			orderSpecialApply.setIndemnity(indemnity);
			orderSpecialApply.setRocessingResult(rocessingResult);
			orderSpecialApply.setConfirmStatus(Status.singlepin);
			if (StringUtils.isBlank(orderSpecialApply.getDealtPersonId())) {
				orderSpecialApply.setDealtPersonId(account.getId());
			}
			orderSpecialApplyDao.merge(orderSpecialApply);

			// 更新状态
			robOrderConfirm.setSpecialStatus(specialStatus);
			robOrderConfirm.setIndemnity(indemnity);
			robOrderConfirm.setRocessingResult(rocessingResult);
			robOrderConfirm.setLockStatus(LockStatus.unlock);
			robOrderConfirm.setStatus(Status.singlepin);// 销单
			robOrderConfirm.setEndDate(new Date());
			robOrderConfirmDao.merge(robOrderConfirm);
		}
	}

	/*
	 * 消单
	 */
	public void singlepinRoborderConfrim(RobOrderConfirm robOrderConfirm,
			boolean isArbitration) {

	}

	@Override
	public List<Map<String, Object>> orderstatus(String robConfirmId) {
		return robOrderConfirmDao.orderstatus(robConfirmId);
	}

	@Override
	public Map<String, Object> getRabOrderDelivered(
			RobOrderConfirm robOrderConfirm, int i, int j) {
		return robOrderConfirmDao.getRabOrderDelivered(robOrderConfirm, i, j);
	}

	@Override
	public Map<String, Object> getOrderPage(RobOrderRecord robOrderRecord,
			Account account, int start, int limit) {
		return robOrderConfirmDao.getOrderPage(robOrderRecord, account, start,
				limit);
	}

	@Override
	public Map<String, Object> getCompanyOredrList(
			RobOrderRecord robOrderRecord, Account account, int start, int limit) {
		return robOrderConfirmDao.getCompanyOredrList(robOrderRecord, account,
				start, limit);
	}

	@Override
	public List<Map<String, Object>> getTruckList(String robOrderId,
			Account account) {
		return robOrderConfirmDao.getTruckList(robOrderId, account);
	}

	/*
	 * getRobOrderConfirmById
	 */
	@Override
	public RobOrderConfirm getRobOrderConfirmById(String id) {
		return robOrderConfirmDao.getObjectById(RobOrderConfirm.class, id);
	}

	@Override
	public Map<String, Object> getOrderConfirmInfo(String robOrderConfirmId) {
		return robOrderConfirmDao.getOrderConfirmInfo(robOrderConfirmId);
	}

	@Override
	public List<Map<String, Object>> getOrderInfoPageForMap(String robOrderId,
			String truckID) {
		// TODO Auto-generated method stub
		return robOrderConfirmDao.getOrderInfoPage(robOrderId, truckID);
	}

	public static void main(String[] args) {
		double d1 = 19.994;
		double d2 = 119;
		double result = new BigDecimal(d1 * d2).setScale(2,
				RoundingMode.CEILING).doubleValue();
		System.out.println(result);
		result = 1.329;
		result = new BigDecimal(1.325).setScale(2, RoundingMode.HALF_UP)
				.doubleValue();
		System.out.println(result);
	}

	@Override
	public void saveConfirmPullGoods(Account account,
			RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			Double actualWeight) {

		// 冻结运输费
		double totalMoney = robOrderConfirm.getUnitPrice() * actualWeight;
		totalMoney = DoubleHelper.round(totalMoney);
		double money = totalMoney - robOrderConfirm.getTransportationDeposit(); // 运输费等于//
																				// 总运输费-运输押金
		// double money =
		// robOrderConfirm.getTransportationCost()-robOrderConfirm.getTransportationDeposit();
		// //运输费等于 总运输费-运输押金

		String remark = "确认发货成功，冻结运输金额：" + String.format("%.2f", money) + "元"
				+ "" + "，抢单单号为：" + robOrderConfirm.getRobOrderNo() + ",运单单号为:"
				+ robOrderConfirm.getTransportNo() + ",实际吨位是:"
				+ robOrderConfirm.getActualWeight() + "吨";
		capitalAccountService.saveFreezeTool(account,
				new BigDecimal(String.format("%.2f", money)), remark,
				account.getName());

		// 确认发货日志
		OrderAutLog log = new OrderAutLog();
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.transit);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【确认发货】");
		log.setRemark(remark);
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		log.setConfirmreceiptUserId(account.getId());
		log.setTurckId(robOrderConfirm.getTurckId());
		log.setTurckUserId(robOrderConfirm.getTurckUserId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		robOrderConfirm.setStatus(Status.transit);
		// robOrderConfirm.setActualWeight(actualWeight); //实际重量
		robOrderConfirm.setActualTransportationCost(totalMoney);// 实际运输费
		robOrderConfirmDao.merge(robOrderConfirm);

		// lix 2017-08-14 添加 通知消息 货主用户确认发货通知司机
		String tmp = "尊敬的司机用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
				+ ",运单单号为:" + robOrderConfirm.getTransportNo()
				+ "的运单信息，货主用户已经成功确认发货，实际装货吨数是："
				+ robOrderConfirm.getActualWeight()
				+ "吨。请您尽快登录小镖人APP查看详情并进行下一步运输操作！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderConfirm.getTurckUserId(), "【确认发货】", tmp, actionMap,
				"RobOrderConfirmService.saveConfirmPullGoods");
	}

	@Override
	public void saveConfirmload(Account account, RobOrderRecord robOrderRecord,
			RobOrderConfirm robOrderConfirm, Double actualWeight) {

		String remark = "确认装货成功，实际吨位:" + actualWeight + "吨";

		// 确认拉货日志
		OrderAutLog log = new OrderAutLog();
		log.setRemark(remark);
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.confirmload);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【确认装货】");
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		log.setConfirmreceiptUserId(account.getId());
		log.setTurckId(robOrderConfirm.getTurckId());
		log.setTurckUserId(robOrderConfirm.getTurckUserId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		robOrderConfirm.setStatus(Status.confirmload);
		robOrderConfirm.setActualWeight(actualWeight); // 实际重量
		robOrderConfirmDao.merge(robOrderConfirm);

		// lix 2017-08-14 添加 通知消息 装货完成通知货主确认发货
		String tmp = "尊敬的货主用户您好，您有一笔抢单单号为：" + robOrderRecord.getRobOrderNo()
				+ "的抢单信息，司机用户已经确认装货，实际装货吨数是：" + actualWeight
				+ "吨。请您尽快登录小镖人APP查看详情并操作确认发货！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderRecord.getRobbedAccountId(), "【确认装货】", tmp, actionMap,
				"RobOrderConfirmService.saveConfirmload");

	}

	@Override
	public void saveConfirmreCeipt(Account account,
			RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord) {

		// 确认收货日志
		OrderAutLog log = new OrderAutLog();
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.delivered);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【确认收货】");
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		robOrderConfirm.setStatus(Status.delivered);
		robOrderConfirmDao.merge(robOrderConfirm);

		Account robbedAccoun = accountDao.getAccount(robOrderRecord
				.getRobbedAccountId());
		// lix 2017-08-17 注释 修改消息通知方式
		// String msg = "卸货：尊敬的货主用户您好，您的订单号：" + robOrderConfirm.getRobOrderNo()
		// + "，运单号：" + robOrderConfirm.getTransportNo()
		// + "的货物已经卸货完毕。您可以随时登陆手机APP或网页后台进行查看。";
		// sendMessageService
		// .saveRecordAndSendMessage(
		// robbedAccoun.getPhone(),
		// msg,
		// "com.memory.platform.module.order.controller.RobOrderConfirmController.confirmpullgoods");
		// lix 2017-08-17 添加 修改消息通知方式
		String tmp = "尊敬的货主用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
				+ "，运单单号为：" + robOrderConfirm.getTransportNo()
				+ "的货物已经确认收货。请您尽快登录小镖人APP查看详情！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderRecord.getRobbedAccountId(), "【确认收货】", tmp, actionMap,
				"RobOrderConfirmService.saveConfirmreCeipt");

	}

	@Override
	public void saveReceipt(Account account, RobOrderConfirm robOrderConfirm,
			RobOrderRecord robOrderRecord, LgisticsCompanies lgisticsCompanies,
			String lgisticsNum, List<String> path, String rootPath) {
		String receiptImg = "";
		for (String str : path) {
			try {
				ImageFileUtil.move(
						new File(rootPath + str),
						new File(rootPath
								+ str.substring(0, str.lastIndexOf("/"))
										.replace("temporary", "uploading")));
				FileInputStream fin = new FileInputStream(new File(rootPath
						+ str.replace("temporary", "uploading")));
				String url = OSSClientUtil.uploadFile2OSS(fin,
						str.substring(str.lastIndexOf("/"), str.length()),
						"receiptImg");// 此处是上传到阿里云OSS的步骤
				receiptImg += url + ",";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 确认收货日志
		OrderAutLog log = new OrderAutLog();
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.receipt);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【寄送回执单】");
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		receiptImg = receiptImg.substring(0, receiptImg.length() - 1);
		robOrderConfirm.setOriginalReceiptImg(receiptImg);
		robOrderConfirm.setStatus(Status.receipt);
		robOrderConfirm.setLgisticsCode(lgisticsCompanies.getCode());
		robOrderConfirm.setLgisticsName(lgisticsCompanies.getName());
		robOrderConfirm.setLgisticsNum(lgisticsNum);
		robOrderConfirmDao.merge(robOrderConfirm);

		Account robbedAccoun = accountDao.getObjectById(Account.class,
				robOrderRecord.getRobbedAccountId());
		// lix 2017-08-17 注释 修改消息通知方式
		// String msg = "回执发还：尊敬的货主用户您好，您的订单号：" +
		// robOrderConfirm.getRobOrderNo()
		// + "，运单号：" + robOrderConfirm.getTransportNo()
		// + "的货物驾驶员已经将回执由快递公司寄送回易林公司途中，您可以随时登陆手机APP或网页后台进行查看。";
		// sendMessageService
		// .saveRecordAndSendMessage(robbedAccoun.getPhone(), msg,
		// "com.memory.platform.module.order.controller.RobOrderConfirmController.receipt");
		// lix 2017-08-17 添加 修改消息通知方式
		String tmp = "尊敬的货主用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
				+ "，运单单号为：" + robOrderConfirm.getTransportNo()
				+ "的货物，司机用户已经将回执由快递公司寄送回公司途中，请您尽快登录小镖人APP查看详情！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderRecord.getRobbedAccountId(), "【回执单寄送】", tmp, actionMap,
				"RobOrderConfirmService.saveReceipt");

	}
	//确认送达
	@Override
	public void saveDelivery(Account account, RobOrderConfirm robOrderConfirm,
			RobOrderRecord robOrderRecord, String lgisticsNum,
			List<String> path, String rootPath) {
		String receiptImg = ArrayUtil.joinListArray(path, ",",
				new ArrayUtil.IJoinCallBack<String>() {

					@Override
					public String join(Object obj, int idx) {
						// TODO Auto-generated method stub
						return obj.toString();
					}

				});
		;
		if(robOrderConfirm.getSettlement_weight()<=0) { //如果未填入结算吨位（收货吨位）
			robOrderConfirm.setSettlement_weight(robOrderConfirm.getActualWeight());
		}
		//设置结算金额
		robOrderConfirm.setSettlement_cost(
				DoubleHelper.round( robOrderConfirm.getSettlement_weight()*robOrderConfirm.getUnitPrice())
				);
		// for (String str : path) {
		// try {
		// ImageFileUtil.move(new File(rootPath + str),
		// new File(rootPath + str.substring(0,
		// str.lastIndexOf("/")).replace("temporary", "uploading")));
		// FileInputStream fin = new FileInputStream(new File(rootPath +
		// str.replace("temporary", "uploading")));
		// String url = OSSClientUtil.uploadFile2OSS(fin,
		// str.substring(str.lastIndexOf("/"), str.length()),
		// "receiptImg");// 此处是上传到阿里云OSS的步骤
		// receiptImg += url + ",";
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		// 确认收货日志
		OrderAutLog log = new OrderAutLog();
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.receipt);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【确认送达】");
		log.setRemark("司机：【" + account.getName() + "】确认送达成功,回执单物流编号:"
				+ lgisticsNum + String.format(",结算吨位：%f吨",	robOrderConfirm.getSettlement_weight())
				);
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		// receiptImg = receiptImg.substring(0, receiptImg.length() - 1);
		robOrderConfirm.setOriginalReceiptImg(receiptImg);
		robOrderConfirm.setStatus(Status.receipt);
		// robOrderConfirm.setLgisticsCode(lgisticsCompanies.getCode());
		// robOrderConfirm.setLgisticsName(lgisticsCompanies.getName());
		// robOrderConfirm.setLgisticsCode( lgisticsNum);
		robOrderConfirm.setLgisticsNum(lgisticsNum);
		robOrderConfirmDao.merge(robOrderConfirm);

		Account robbedAccoun = accountDao.getObjectById(Account.class,
				robOrderRecord.getRobbedAccountId());
		// lix 2017-08-14 注释 通知消息修改
		// String msg = "回执发还：尊敬的货主用户您好，您的订单号：" +
		// robOrderConfirm.getRobOrderNo() + "，运单号："
		// + robOrderConfirm.getTransportNo() +
		// "的货物驾驶员已经将回执由快递公司寄送回易林公司途中，您可以随时登陆手机APP或网页后台进行查看。";
		// sendMessageService.saveRecordAndSendMessage(robbedAccoun.getPhone(),
		// msg,
		// "com.memory.platform.module.order.controller.RobOrderConfirmController.receipt");
		// lix 2017-08-14 添加 通知消息 货物送达通知货主用户
		String tmp = "尊敬的货主用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
				+ "，运单单号为：" + robOrderConfirm.getTransportNo()
				+ "的货物已经确认送达，司机用户已经将回执单由快递公司寄送回公司途中，请您尽快登录小镖人APP查看详情！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderRecord.getRobbedAccountId(), "【回执单寄送】", tmp, actionMap,
				"RobOrderConfirmService.saveDelivery");

	}
	/*
	 * 确认付款
	 * */
	@Override
	public void savePayment(Account account, RobOrderConfirm robOrderConfirm,
			RobOrderRecord robOrderRecord, boolean isSystem) {

		String accountId = robOrderRecord.getAccount().getCompany()
				.getAccount_id();

		CapitalAccount toCapitalAccount = capitalAccountService
				.getCapitalAccount(accountId);// 车队管理资金账户
		CapitalAccount capitalAccount = capitalAccountService
				.getCapitalAccount(robOrderRecord.getRobbedAccountId());// 货主资金账户

		// 运输费
		//double money = robOrderConfirm.getActualTransportationCost();
		/*
		 * 按结算吨位付款 补齐差价
		 * */
		if(robOrderConfirm.getPayment_type() == PaymentType.settlementWeight) {
//			if(robOrderConfirm.getSettlement_cost()<=0) {
//				throw new Exception("结算金额不能为0");
//			}
//			robOrderConfirm.setSettlement_cost(
//					DoubleHelper.round( robOrderConfirm.getSettlement_weight() * robOrderConfirm.getUnitPrice())
//					);
			double  settlementPayment = robOrderConfirm.getSettlement_cost() + robOrderConfirm.getAdditionalCost();//按结算吨位结算的金额
			//robOrderConfirm.setTransportationCost( robOrderConfirm.getSettlement_cost());//运费改为按收货吨位收货的运费
			robOrderConfirm.setActualTransportationCost( robOrderConfirm.getSettlement_cost());
			double  totalPayment = robOrderConfirm.getTotalCost(); //按发货吨位计算的金额
			double  deltaMoney = totalPayment -  settlementPayment; //差价多少 需要补齐的差价
			capitalAccount.setFrozen( capitalAccount.getFrozen()-deltaMoney);
			capitalAccount.setAvaiable(capitalAccount.getAvaiable() + deltaMoney);
			if(capitalAccount.getAvaiable() < 0) {
				throw new BusinessException("您的资金不足无法付款");
			}
			robOrderConfirm.setTotalCost(settlementPayment);
 
		}
		double money = robOrderConfirm.getTotalCost();//aiqiwu 2017-09-15 update 实际运费(包含附加费用)
		String remark = "确认运输货物成功，支付运输费用：" + money + "元，抢单单号："
				+ robOrderConfirm.getRobOrderNo() + ",运单单号:"
				+ robOrderConfirm.getTransportNo();
		String toRemark = "运输货物成功，接受货主支付运输费用：" + money + "元，抢单单号："
				+ robOrderConfirm.getRobOrderNo() + ",运单单号:"
				+ robOrderConfirm.getTransportNo();
		capitalAccountService.savePayment(capitalAccount, toCapitalAccount,
				new BigDecimal(money), MoneyRecord.Type.transportSection,
				remark, toRemark);

		// 解冻车辆押金
		String truckRemark = "运输货物成功，解冻车辆押金：" + robOrderConfirm.getTurckDeposit()
				+ "元，抢单单号：" + robOrderConfirm.getRobOrderNo() + ",运单单号:"
				+ robOrderConfirm.getTransportNo();
		Account admin = accountDao.getAccount(accountId);// 车队管理员账号
		capitalAccountService
				.updateFreezeTool(admin,
						new BigDecimal(robOrderConfirm.getTurckDeposit()),
						truckRemark, account.getName());

		String turckId = robOrderConfirm.getTurckId();
		RobOrderConfirm robOrderConfirm_1 = robOrderConfirmDao
				.getRobOrderConfirm(Status.receiving, turckId);
		if (robOrderConfirm_1 == null) {// 如果这辆车没有要拉的货设置车辆为未运输状态
			// 设置车辆未运输
			Truck truck = truckDao.getObjectById(Truck.class, turckId);
			truck.setTruckStatus(TruckStatus.notransit);
			truckDao.merge(truck);
		}

		List<RobOrderConfirm> robconfirmList = robOrderConfirmDao
				.getRobOrderConfirm(robOrderRecord.getId());// 获取订单下没有完成的运单
		if (robconfirmList == null || robconfirmList.size() == 0) {
			robOrderRecord.setStatus(RobOrderRecord.Status.ordercompletion); // 设置运单为完结
			robOrderRecordDao.merge(robOrderRecord);
		}

		// 确认收货日志
		OrderAutLog log = new OrderAutLog();
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.ordercompletion);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle(isSystem == false ? "【付款】" : "【自动付款】");
		log.setRemark(isSystem == false ? "确认运输货物成功" : "已经超过最后付款期限"
				+ ",货主支付运输费用：" + money + "元，车队解冻车辆押金："
				+ robOrderConfirm.getTurckDeposit() + "元，抢单单号："
				+ robOrderConfirm.getRobOrderNo() + ",运单单号:"
				+ robOrderConfirm.getTransportNo());
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		robOrderConfirm.setStatus(Status.ordercompletion);
		robOrderConfirm.setEndDate(new Date());
		robOrderConfirmDao.merge(robOrderConfirm);
		// lix 添加 货主确认收货后通知车队
		String tmp = "尊敬的司机用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
				+ "，运单单号为：" + robOrderConfirm.getTransportNo()
				+ "的运单信息，货主用户已经付款成功处理完结，付款金额为：" + money
				+ "元,请您尽快登录小镖人APP查看详情！";
		if (isSystem) {

			tmp = "尊敬的司机用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
					+ "，运单单号为：" + robOrderConfirm.getTransportNo()
					+ "的运单信息，已经超过货主最后付款期限,系统自动处理完结，付款金额为：" + money
					+ "元,请您尽快登录小镖人APP查看详情！";

		}
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderConfirm.getTurckUserId(), log.getTitle(), tmp,
				actionMap, "RobOrderConfirmService.savePayment");

		// 系统自动扣款的发送到货主
		if (isSystem) {
			tmp = "尊敬的货主用户您好，您有一笔运单单号为：" + robOrderConfirm.getTransportNo()
					+ "的运单信息，已经超过货主最后付款期限,系统自动处理完结，付款金额为：" + money
					+ "元,请您尽快登录小镖人APP查看详情！";
			pushService.saveRecordAndSendMessageWithAccountID(
					robOrderRecord.getRobbedAccountId(), log.getTitle(), tmp,
					actionMap, "RobOrderConfirmService.savePayment");
		}
	}

	@Override
	public void savePayment(Account account, RobOrderConfirm robOrderConfirm,
			RobOrderRecord robOrderRecord) {
		this.savePayment(account, robOrderConfirm, robOrderRecord, false);
	}

	@Override
	public void saveEmergency(Account account, RobOrderConfirm robOrderConfirm,
			String remark, List<String> path, String rootPath) {
		String emergencyImg = "";
		if (path != null && path.size() > 0) {

		}
		if (path != null && path.size() != 0) {

			for (String str : path) {
				try {
					ImageFileUtil
							.move(new File(rootPath + str), new File(rootPath
									+ str.substring(0, str.lastIndexOf("/"))
											.replace("temporary", "uploading")));
					FileInputStream fin = new FileInputStream(new File(rootPath
							+ str.replace("temporary", "uploading")));
					String url = OSSClientUtil.uploadFile2OSS(fin,
							str.substring(str.lastIndexOf("/"), str.length()),
							"emergencyImg");// 此处是上传到阿里云OSS的步骤
					emergencyImg += url + ",";
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			emergencyImg = emergencyImg.substring(0, emergencyImg.length() - 1);
		}

		OrderAutLog orderAutLog = new OrderAutLog();

		RobOrderRecord robOrderRecord = robOrderRecordDao.getObjectById(
				RobOrderRecord.class, robOrderConfirm.getRobOrderId());
		orderAutLog.setRobOrderRecord(robOrderRecord);
		orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setTitle("【申请急救】");
		orderAutLog.setSpecialStatus(SpecialStatus.suchprocessing);
		orderAutLog.setSpecialType(SpecialType.emergency);
		orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLog.setRemark(remark);
		orderAutLogDao.save(orderAutLog);

		// 申请记录
		OrderSpecialApply orderSpecialApply = new OrderSpecialApply();
		orderSpecialApply.setRobOrderConfirmId(robOrderConfirm.getId());
		orderSpecialApply.setConfirmStatus(robOrderConfirm.getStatus());
		orderSpecialApply.setSpecialType(SpecialType.emergency);
		orderSpecialApply.setSpecialStatus(SpecialStatus.suchprocessing);
		orderSpecialApply.setApplypersonid(account.getId());
		orderSpecialApply.setApplypersonName(account.getName());
		orderSpecialApply.setApplyCompanyId(account.getCompany().getId());
		orderSpecialApply.setRemark(remark);
		orderSpecialApply.setCreate_time(new Date());
		orderSpecialApply.setEmergencyImg(emergencyImg);
		orderSpecialApplyDao.save(orderSpecialApply);

		// 日志记录
		OrderWaybillLog orderWaybillLog = new OrderWaybillLog();
		orderWaybillLog.setConfirmStatus(robOrderConfirm.getStatus());
		orderWaybillLog.setRobOrderConfirm(robOrderConfirm);
		orderWaybillLog.setSpecialType(SpecialType.emergency);
		orderWaybillLog.setSpecialStatus(SpecialStatus.suchprocessing);
		orderWaybillLog.setApplyCompanyId(account.getCompany().getId());
		orderWaybillLog.setApplypersonid(account.getId());
		orderWaybillLog.setApplypersonName(account.getName());
		orderWaybillLog.setRemark(remark);
		orderWaybillLog.setCreate_time(new Date());
		orderWaybillLog.setOrderSpecialApplyId(orderSpecialApply.getId());
		orderWaybillLogDao.save(orderWaybillLog);

		// 更新订单状态
		robOrderConfirm.setSpecialType(SpecialType.emergency);
		robOrderConfirm.setSpecialStatus(SpecialStatus.suchprocessing);
		robOrderConfirm.setLockStatus(LockStatus.locking);
		robOrderConfirmDao.merge(robOrderConfirm);

		// 急救发送短信
		List<Account> list = accountDao.getAdminAccount();
		sendSMS(list, "emergency", robOrderConfirm.getTransportNo());

	}

	/**
	 * 急救和仲裁发送短信
	 * 
	 * @param userList
	 */
	public void sendSMS(List<Account> userList, String type, String transportNo) {
		String msg = "";
		if ("emergency".equals(type)) {
			msg = "运单：" + transportNo + ",申请急救！";
		} else {
			msg = "运单：" + transportNo + ",申请仲裁！";
		}
		for (Account account : userList) {
			sendMessageService
					.saveRecordAndSendMessage(
							account.getPhone(),
							msg,
							"com.memory.platform.module.order.controller.RobOrderConfirmController.confirmreceiptsend");
		}
	}

	/*
	 * 仲裁
	 */
	@Override
	public void saveArbitration(Account account,
			RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			String remark, List<String> path, String rootPath) {

		String emergencyImg = "";
		if (path != null && path.size() > 0) {

			emergencyImg = ArrayUtil.joinListArray(path, ",",
					new ArrayUtil.IJoinCallBack<String>() {

						@Override
						public String join(Object obj, int idx) {
							// TODO Auto-generated method stub
							return obj.toString();
						}

					});

		}
		// if (path != null && path.size() != 0) {
		//
		// for (String str : path) {
		// try {
		// ImageFileUtil
		// .move(new File(rootPath + str), new File(rootPath
		// + str.substring(0, str.lastIndexOf("/"))
		// .replace("temporary", "uploading")));
		// FileInputStream fin = new FileInputStream(new File(rootPath
		// + str.replace("temporary", "uploading")));
		// String url = OSSClientUtil.uploadFile2OSS(fin,
		// str.substring(str.lastIndexOf("/"), str.length()),
		// "emergencyImg");// 此处是上传到阿里云OSS的步骤
		// emergencyImg += url + ",";
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// emergencyImg = emergencyImg.substring(0, emergencyImg.length() - 1);
		// }

		// 申请记录
		OrderSpecialApply orderSpecialApply = new OrderSpecialApply();
		orderSpecialApply.setRobOrderConfirmId(robOrderConfirm.getId());
		orderSpecialApply.setConfirmStatus(robOrderConfirm.getStatus());
		orderSpecialApply.setSpecialType(SpecialType.arbitration);
		orderSpecialApply.setSpecialStatus(SpecialStatus.suchprocessing);
		orderSpecialApply.setApplypersonid(account.getId());
		orderSpecialApply.setApplypersonName(account.getName());
		orderSpecialApply.setApplyCompanyId(account.getCompany().getId());
		orderSpecialApply.setRemark(remark);
		orderSpecialApply.setEmergencyImg(emergencyImg);
		orderSpecialApply.setCreate_time(new Date());
		if (account.getCompany().getCompanyType().getName().equals("货主")) {
			orderSpecialApply.setBeArbitrationPersonId(robOrderRecord
					.getAccount().getId());
			orderSpecialApply.setBeArbitrationCompanyId(robOrderRecord
					.getAccount().getCompany().getId());
		} else {
			orderSpecialApply.setBeArbitrationPersonId(robOrderRecord
					.getRobbedAccountId());
			orderSpecialApply.setBeArbitrationCompanyId(robOrderRecord
					.getRobbedCompanyId());
		}

		orderSpecialApplyDao.save(orderSpecialApply);

		OrderAutLog orderAutLog = new OrderAutLog();
		// RobOrderRecord robOrderRecord =
		// robOrderRecordDao.getObjectById(RobOrderRecord.class,
		// robOrderConfirm.getRobOrderId());
		orderAutLog.setRobOrderRecord(robOrderRecord);
		orderAutLog.setConfirmStatus(robOrderConfirm.getStatus());
		orderAutLog.setAuditPerson(account.getName());
		orderAutLog.setAuditPersonId(account.getId());
		orderAutLog.setCreate_time(new Date());
		orderAutLog.setTitle("【申请仲裁】");
		orderAutLog.setSpecialType(SpecialType.arbitration);
		orderAutLog.setSpecialStatus(SpecialStatus.suchprocessing);
		orderAutLog.setRobOrderConfirmId(robOrderConfirm.getId());
		orderAutLog.setRemark(remark);
		orderAutLogDao.save(orderAutLog);

		// 日志记录
		OrderWaybillLog orderWaybillLog = new OrderWaybillLog();
		orderWaybillLog.setConfirmStatus(robOrderConfirm.getStatus());
		orderWaybillLog.setRobOrderConfirm(robOrderConfirm);
		orderWaybillLog.setSpecialType(SpecialType.arbitration);
		orderWaybillLog.setSpecialStatus(SpecialStatus.suchprocessing);
		orderWaybillLog.setApplyCompanyId(account.getCompany().getId());
		orderWaybillLog.setApplypersonid(account.getId());
		orderWaybillLog.setApplypersonName(account.getName());
		orderWaybillLog.setRemark(remark);
		orderWaybillLog.setCreate_time(new Date());
		orderWaybillLog.setOrderSpecialApplyId(orderSpecialApply.getId());
		orderWaybillLogDao.save(orderWaybillLog);

		// 更新订单状态
		robOrderConfirm.setSpecialType(SpecialType.arbitration);
		robOrderConfirm.setSpecialStatus(SpecialStatus.suchprocessing);
		robOrderConfirm.setLockStatus(LockStatus.locking);
		robOrderConfirmDao.merge(robOrderConfirm);

		// 仲裁发送短信
		// List<Account> list = accountDao.getAdminAccount();
		// sendSMS(list, "arbitration", robOrderConfirm.getTransportNo());
	}

	@Override
	public Map<String, Object> getRobOrderConfirmByRobOrderId(String robOrderId) {
		// TODO Auto-generated method stub
		return robOrderConfirmDao.getRobOrderConfirmByRobOrderId(robOrderId);
	}

	@Override
	public Map<String, Object> getConfirmDeliverDetailsByRobOrderRecordId(
			RobOrderRecord recode) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> map = robOrderConfirmDao
				.getConfirmDeliverDetailsByRobOrderRecordId(recode);
		List<Map<String, Object>> goodDetail = goodsDetailDao
				.getGoodsDetailByGoodsBasicId(recode.getGoodsBasic().getId());
		ret.put("goodsDetails", goodDetail);
		ret.put("confirmInfo", map);
		Map<String, Double> money = getRoborderFaHuoJinE(recode.getId());
		ret.put("advanceMoney", money.get("advanceMoney"));
		ret.put("paymentMoney", money.get("paymentMoney"));
		return ret;
	}

	public Map<String, Double> getRoborderFaHuoJinE(String robID) {
		Map<String, Double> ret = new HashMap<String, Double>();
		RobOrderRecord order = orderRecordService.getRecordById(robID);
		BigDecimal bd = new BigDecimal(order.getWeight()
				* order.getDepositUnitPrice());
		ret.put("advanceMoney", bd.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
		ret.put("paymentMoney", order.getTotalPrice());
		return ret;
	}

	@Override
	public Map<String, Object> getMyRobOrderConfirmList(
			RobOrderConfirm robOrderRecordConfirm, Account account, int start,
			int limit) {
		return robOrderConfirmDao.getMyRobOrderConfirmList(
				robOrderRecordConfirm, account, start, limit);

	}

	/*
	 * 获取我的运单详情 包括货主和车队的显示 by:lil
	 * 
	 * @see confirm.goods_basic_id //必填 confirm.queryStatus //必填
	 */
	@Override
	public Map<String, Object> getMyRobOrderConfirmDetail(Account account,
			RobOrderConfirm confirm) {
		String goodsBasicID = confirm.getGoods_basic_id();

		Map<String, Object> map = goodsBasicDao.getBasicGoods(goodsBasicID);
		// 加入操作权限

		Map<String, Object> ownMap = robOrderConfirmOwnService
				.getOwnWithGoodsBasic(goodsBasicID);
		for (String ownKey : ownMap.keySet()) {
			map.put(ownKey, ownMap.get(ownKey));
		}
		List<Map<String, Object>> detialMap = goodsDetailDao
				.getGoodsDetailByGoodsBasicId(goodsBasicID);
		map.put("goods_details", detialMap);
		List<Map<String, Object>> confirmMaps = robOrderConfirmDao
				.getMyRobOrderConfirm(account, confirm);
		for (Map<String, Object> confirmMap : confirmMaps) {
			String confimID = confirmMap.get("rob_order_confirm_id").toString();

			// 加入运单操作权限 装货，发货，送达，确认收货付款
			Map<String, Object> confirmOwn = robOrderConfirmOwnService
					.getOwnWithRobOrderConfirm(confirmMap);
			for (String ownKey : confirmOwn.keySet()) {
				confirmMap.put(ownKey, confirmOwn.get(ownKey));
			}
			List<Map<String, Object>> lstDetails = robOrderConfirmDao
					.getConfirmDetailWithConfirmID(confimID);
			confirmMap.put("rob_order_confirm_details", lstDetails);
		}
		map.put("rob_order_confirms", confirmMaps);

		return map;
	}

	@Override
	public boolean accountCanViewGoodsBasic(Account account, String goodsBasicID) {
		return robOrderConfirmDao.accountCanViewGoodsBasic(account,
				goodsBasicID);

	}

	@Override
	public Map<String, Object> getRobOrderConfirmLoadPayment(
			String robOrderConfirmID) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Account account = UserUtil.getAccount();
		double payMoney = 0;
		CapitalAccount capital = capitalAccountDao.getCapitalAccount(account
				.getId());
		RobOrderConfirm confirm = robOrderConfirmDao.getObjectById(
				RobOrderConfirm.class, robOrderConfirmID);
		if (confirm != null) {

			double transferPrice = confirm.getUnitPrice(); // 货物单价
			double transferDeposit = confirm.getTransportationDeposit(); // 运输押金
			double weight = confirm.getActualWeight(); // 实际吨位
			payMoney = DoubleHelper.round(transferPrice * weight)
					- transferDeposit;
			ret.put("alreadyPayment", transferDeposit);
		}
		ret.put("payMoney", payMoney);
		ret.put("avaiable", capital.getAvaiable());
		return ret;
	}

	@Override
	public List<RobOrderConfirm> getRobOrderConfirmListByRobOrderID(
			String robOrderRecordID) {

		return robOrderConfirmDao
				.getRobOrderConfirmWithRecordID(robOrderRecordID);
	}

	@Override
	public List<RobOrderConfirm> getAutoPaymentConfrim() {
		return robOrderConfirmDao.getAutoPaymentConfirm(1000);

	}

	@Override
	public void autoPayment() {
		List<RobOrderConfirm> confirms = this.getAutoPaymentConfrim();
		for (RobOrderConfirm robOrderConfirm : confirms) {
			RobOrderRecord robOrderRecord = orderRecordService
					.getRecordById(robOrderConfirm.getRobOrderId());
			Account account = accountDao.getAccount(robOrderRecord
					.getRobbedAccountId());

			this.savePayment(account, robOrderConfirm, robOrderRecord, true);
		}
	}

	@Override
	public void encrimentAutoPaymentErr(String id) {

		robOrderConfirmDao.encrimentAutoPaymentErr(id);

	}

	/**
	 * aiqiwu 2017-09-13 货主确认发货
	 */
	@Override
	public void saveConfirmReleaseGoods(Account account,
			RobOrderConfirm robOrderConfirm, RobOrderRecord robOrderRecord,
			Double actualWeight, String additionalExpensesRecordJson) {

		double additionalCost = 0d;
		String additionName = "";
		if (!StringUtils.isEmpty(additionalExpensesRecordJson)) {
			List<AdditionalExpensesRecord> listAdditionalExpensesRecord = JsonPluginsUtil
					.jsonToBeanList(additionalExpensesRecordJson,
							AdditionalExpensesRecord.class);
			if (listAdditionalExpensesRecord != null
					&& listAdditionalExpensesRecord.size() > 0) {
				for (AdditionalExpensesRecord ae : listAdditionalExpensesRecord) {
					additionalCost += ae.getTotal();
					ae.setRobOrderConfirm(robOrderConfirm);
					// 保存附加费用记录
					additionalExpensesReocrdService.saveRecord(ae);
				}
				additionName = ArrayUtil
						.joinListArray(
								listAdditionalExpensesRecord,
								",",
								new ArrayUtil.IJoinCallBack<AdditionalExpensesRecord>() {

									@Override
									public <T> String join(T obj, int idx) {
										AdditionalExpensesRecord info = (AdditionalExpensesRecord) obj;
										return info.getAdditionalExpenses()
												.getName()
												+ ":"
												+ String.format("%.2f",
														info.getTotal());
									}
								});
			}
		}

		if (actualWeight == null || actualWeight <= 0) {
			actualWeight = robOrderConfirm.getActualWeight();
		}
		// 冻结运输费
		double totalMoney = robOrderConfirm.getUnitPrice() * actualWeight;
		totalMoney = DoubleHelper.round(totalMoney);
		double money = totalMoney - robOrderConfirm.getTransportationDeposit()
				+ additionalCost; // 运输费等于
		double totalCost = DoubleHelper.round(totalMoney + additionalCost);
		// 总运输费-运输押金
		String otherMoneyTip = StringUtil.isEmpty(additionName) ? "" : ",附加费用("
				+ additionName + ")";
		String remark = "确认发货成功，冻结运输金额：" + String.format("%.2f", money) + "元"
				+ "，抢单单号为：" + robOrderConfirm.getRobOrderNo() + ",运单单号为:"
				+ robOrderConfirm.getTransportNo() + ",实际吨位是:" + actualWeight
				+ "吨" + otherMoneyTip;
		capitalAccountService.saveFreezeTool(account,
				new BigDecimal(String.format("%.2f", money)), remark,
				account.getName());

		// 确认发货日志
		OrderAutLog log = new OrderAutLog();
		log.setRobOrderRecord(robOrderRecord);
		log.setConfirmStatus(Status.transit);
		log.setAuditPerson(account.getName());
		log.setAuditPersonId(account.getId());
		log.setCreate_time(new Date());
		log.setTitle("【确认发货】");
		log.setRemark(remark);
		log.setRobOrderConfirmId(robOrderConfirm.getId());
		log.setConfirmreceiptUserId(account.getId());
		log.setTurckId(robOrderConfirm.getTurckId());
		log.setTurckUserId(robOrderConfirm.getTurckUserId());
		orderAutLogDao.save(log);

		// 更新确认收货单状态
		robOrderConfirm.setStatus(Status.transit);
		robOrderConfirm.setActualTransportationCost(totalMoney);// 实际运输费
		robOrderConfirm.setActualWeight(actualWeight); // 实际发货重量
		robOrderConfirm.setAdditionalCost(additionalCost); // 附加费用总费用
		robOrderConfirm.setTotalCost(totalCost); // 附加费用总费用+实际运费
		robOrderConfirmDao.merge(robOrderConfirm);

		// lix 2017-08-14 添加 通知消息 货主用户确认发货通知司机
		String tmp = "尊敬的司机用户您好，您有一笔抢单单号为：" + robOrderConfirm.getRobOrderNo()
				+ ",运单单号为:" + robOrderConfirm.getTransportNo()
				+ "的运单信息，货主用户已经成功确认发货，实际装货吨数是："
				+ robOrderConfirm.getActualWeight()
				+ "吨。请您尽快登录小镖人APP查看详情并进行下一步运输操作！";
		Map<String, String> actionMap = new HashMap<String, String>();
		actionMap.put("rob_order_confirm_id", robOrderConfirm.getId());
		pushService.saveRecordAndSendMessageWithAccountID(
				robOrderConfirm.getTurckUserId(), "【确认发货】", tmp, actionMap,
				"RobOrderConfirmService.saveConfirmPullGoods");
	}

	/**
	 * 货主发货获取支付信息
	 */
	@Override
	public Map<String, Object> getRobOrderConfirmLoadPaymentNew(
			String robOrderConfirmID, Double actualWeight,
			String additionalExpensesRecordJson) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Account account = UserUtil.getAccount();
		double payMoney = 0;

		CapitalAccount capital = capitalAccountDao.getCapitalAccount(account
				.getId());
		RobOrderConfirm confirm = robOrderConfirmDao.getObjectById(
				RobOrderConfirm.class, robOrderConfirmID);
		if (actualWeight == null || actualWeight <= 0) {
			actualWeight = confirm.getActualWeight();
		}
		if (confirm != null) {
			double additionalCost = 0d;
			if (!StringUtils.isEmpty(additionalExpensesRecordJson)) {
				List<AdditionalExpensesRecord> listAdditionalExpensesRecord = JsonPluginsUtil
						.jsonToBeanList(additionalExpensesRecordJson,
								AdditionalExpensesRecord.class);
				if (listAdditionalExpensesRecord != null
						&& listAdditionalExpensesRecord.size() > 0) {
					for (AdditionalExpensesRecord ae : listAdditionalExpensesRecord) {
						additionalCost += ae.getTotal();
					}
				}
			}
			double transferPrice = confirm.getUnitPrice(); // 货物单价
			double transferDeposit = confirm.getTransportationDeposit(); // 运输押金
			payMoney = DoubleHelper.round(transferPrice * actualWeight)
					- transferDeposit + additionalCost;
			ret.put("alreadyPayment", transferDeposit);
		}
		ret.put("payMoney", payMoney);
		ret.put("avaiable", capital.getAvaiable());
		return ret;
	}
	
	public Map<String, Object> getSolrRobOrderConfirmByIds(String ids,Account account,int start,int limit){
		return robOrderConfirmDao.getSolrRobOrderConfirmByIds(ids,account,start,limit);
	}
}
