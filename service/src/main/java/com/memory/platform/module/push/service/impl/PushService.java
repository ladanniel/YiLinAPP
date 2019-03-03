package com.memory.platform.module.push.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.poifs.storage.ListManagedBlock;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.Nil;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.PhonePlatform;
import com.memory.platform.entity.member.Account.RoleType;
import com.memory.platform.entity.sys.SendMessage;
import com.memory.platform.entity.sys.SendRecord;
import com.memory.platform.entity.sys.SendRecord.SendType;
import com.memory.platform.entity.sys.SendRecord.Status;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ISendMessageService;
import com.memory.platform.module.system.service.ISendRecordService;
import com.memory.platform.module.system.service.impl.SendRecordService;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;

/*
 * 推送服务  by lil
 * */
/*@Component
@com.alibaba.dubbo.config.annotation.Service*/
@com.alibaba.dubbo.config.annotation.Service
@Transactional
public class PushService implements IPushService {
	@Autowired
	IAccountService accountService;

	// 货主
	String consignorMasterSecret = "edea64eee3ad733d5375e6f5";
	String consignorAppKey = "478193ca3bbff8fd1add8b4e";

	// 车队
	String truckMasterSecret = "abbb9b5d3abdd8b07f92b87c";
	String truckAppKey = "7ca7e6d09f4aa28a31088838";

	// 管理
	String managerMasterSecret = "5b3efea092714ccce3b30018";
	String manager = "a2594451efb397980cd81052";
	Boolean isProduct =false;
	public Boolean getIsProduct() {
		return isProduct;
	}

	public void setIsProduct(Boolean isProduct) {
		this.isProduct = isProduct;
	}

	Logger logger = Logger.getLogger(PushService.class);
	JPushClient consignorPushClient;
	JPushClient truckPushClient;
	@Autowired
	ISendRecordService sendRecordService;
	@Autowired
	ISendMessageService sendMessageService;

	@PostConstruct
	public void init() {
		ClientConfig clientConfig = ClientConfig.getInstance();
		
		clientConfig.setApnsProduction(isProduct);
		
		consignorPushClient = new JPushClient(consignorMasterSecret,
				consignorAppKey, null, ClientConfig.getInstance());
		truckPushClient = new JPushClient(truckMasterSecret, truckAppKey, null,
				ClientConfig.getInstance());
	}

	@Override
	public void pushMessage(List<SendRecord> records) {

		for (SendRecord sendRecord : records) {

			Account.PhonePlatform platform = sendRecord.getPhone_platform();
			if (platform == Account.PhonePlatform.none) {
				sendRecord.setStatus(Status.fail);
				sendRecordService.updateStatus(sendRecord);
				continue;
			}
			if (StringUtil.isEmpty(sendRecord.getPush_id())) {
				sendRecord.setStatus(Status.fail);
				sendRecord.setPush_msg("push_id为空");
				sendRecordService.updateStatus(sendRecord);
				continue;
			}
			if (StringUtil.isEmpty(sendRecord.getPush_client_id())) {
				sendRecord.setStatus(Status.fail);
				sendRecord.setPush_msg("Push_client_id为空");

				sendRecordService.updateStatus(sendRecord);
				continue;
			}
			Map<String, String> extendMap = StringUtil.isEmpty(sendRecord
					.getExtend_data()) ? null : JsonPluginsUtil
					.jsonToMap(sendRecord.getExtend_data());
			Notification notification = createNotificationWithPlatform(
					platform, sendRecord.getTitle(), sendRecord.getContent(),
					extendMap);

			JPushClient client = this.getClientWithClientID(sendRecord
					.getPush_client_id());
			if (client == null) {
				sendRecord.setStatus(Status.fail);

				sendRecord.setPush_msg("未找到JPushClient");

				sendRecordService.updateStatus(sendRecord);
				continue;
			}
			Audience audience = Audience
					.registrationId(sendRecord.getPush_id());
			PushPayload payload = PushPayload.newBuilder()
					.setPlatform(Platform.all()).setAudience(audience)
					.setNotification(notification).build();
			try {
				PushResult result = client.sendPush(payload);
				sendRecord.setStatus(Status.success);
				String resultMsg = result.toString();
				resultMsg = resultMsg.length() > 255 ? resultMsg.substring(0,
						255) : resultMsg;
				sendRecord.setPush_msg(resultMsg);
				sendRecordService.updateStatus(sendRecord);

			} catch (Exception e) {
				sendRecord.setStatus(Status.fail);
				String str1 = e.getMessage().length() > 255 ? e.getMessage()
						.substring(0, 255) : e.getMessage();
				sendRecord.setPush_msg(str1);
				sendRecordService.updateStatus(sendRecord);
				e.printStackTrace();
			}

		}

	}

	private JPushClient getClientWithClientID(String push_client_id) {

		if (StringUtil.isEmpty(push_client_id)) {

		} else if (push_client_id.equals(consignorMasterSecret)) {
			return consignorPushClient;
		} else if (push_client_id.equals(truckMasterSecret)) {
			return truckPushClient;
		}
		return null;
	}

	@Override
	public void saveRecordAndSendMessageWithAccountIDArray(
			List<String> accountIDS, String title, String content,
			Map<String, String> extendData, String sendPoint) {
		Map<Account.PhonePlatform, List<Account>> accounts = null; // 所有按平台推送的账号
		List<Account> noneBindPhoneAccount = new ArrayList<>(); // 没有用手机登陆过的用户
		do {
			if (accountIDS == null) { // 全部发送 不存库
				Audience audience = Audience.all();
				for (int i = 0; i < 2; i++) {

					Notification notification = i == 0 ? createNotificationWithPlatform(
							PhonePlatform.android, title, content, extendData)
							: createNotificationWithPlatform(PhonePlatform.ios,
									title, content, extendData);

					PushPayload payload = PushPayload.newBuilder()
							.setPlatform(Platform.all()).setAudience(audience)
							.setNotification(notification).build();

					PushResult result;
					try {
						result = consignorPushClient.sendPush(payload);
						logger.info(result);
						result = truckPushClient.sendPush(payload);
						logger.info(result);
					} catch (APIConnectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (APIRequestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				break;
			}

			accounts = accountService
					.getAccountListWithPhonePlatform(accountIDS);
			List<SendMessage> msg = sendMessageService.getEnabledSendMessage();
			SendMessage message = msg.get(0);
					

			String jsonExtend = AppUtil.getGson().toJson(extendData);
			if ("null".equals(jsonExtend)) {
				jsonExtend = null;
			}
			for (Account.PhonePlatform platform : accounts.keySet()) {

				Notification notification = createNotificationWithPlatform(
						platform, title, content, extendData);
				if (notification == null) {
					noneBindPhoneAccount.addAll(accounts.get(platform));
					continue;
				}
				List<Account> accounts2 = accounts.get(platform);
				Map<JPushClient, List<Account>> groupClient = groupClientWithAccount(accounts2);
				Account currentAccount = UserUtil.getAccount();

				for (JPushClient client : groupClient.keySet()) {

					List<Account> accounts3 = groupClient.get(client);
					List<String> push_ids = new ArrayList<>();
					for (Account account : accounts3) {
						SendRecord record = new SendRecord();
						record.setAdd_user_id(currentAccount == null ? "0"
								: currentAccount.getId());
						record.setContent(content);
						record.setStatus(SendRecord.Status.waitSend);
						record.setExtend_data(jsonExtend);
						record.setIs_received(false);
						record.setReceive_user_id(account.getId());
						record.setSend_user_id(currentAccount == null ? "0"
								: currentAccount.getId());
						record.setSendPoint(sendPoint);
						record.setSend_type(SendType.push);
						record.setSendMessage(message);
						record.setPhone_platform(platform);
						record.setPush_id(account.getPush_id());
						record.setTitle(title);
						record.setCreate_time(new Date());
						String clientID = "";
						if (account.getRole_type() == RoleType.consignor) {
							clientID = consignorMasterSecret;
						} else if (account.getRole_type() == RoleType.truck) {
							clientID = truckMasterSecret;
						} else {
							clientID = managerMasterSecret;

						}
						record.setPush_client_id(clientID);
						sendRecordService.saveRecord(record);
						push_ids.add(account.getPush_id());
						
					}

				}

			}
		} while (false);

	}

	private Map<JPushClient, List<Account>> groupClientWithAccount(
			List<Account> accounts2) {
		Map<JPushClient, List<Account>> map = new HashMap<>();
		for (Account account : accounts2) {
			JPushClient client = getClientWithAccountID(account.getId());
			if (map.containsKey(client) == false) {
				map.put(client, new ArrayList<Account>());
			}
			List<Account> arr = map.get(client);
			arr.add(account);
		}
		return map;
	}

	private Notification createNotificationWithPlatform(PhonePlatform platform,
			String title, String content, Map<String, String> extendData) {
		Notification notification = null;
		if (platform == PhonePlatform.ios)

			notification = Notification
					.newBuilder()
					.addPlatformNotification(
							IosNotification.newBuilder().setAlert(content)
									.setBadge(1).setSound("default")
									.addExtras(extendData).build()).build();
		else if (platform == PhonePlatform.android) {
			notification = Notification
					.newBuilder()
					.addPlatformNotification(
							AndroidNotification.newBuilder().setAlert(content)
									.addExtras(extendData).setTitle(title)
									.build()).build();
		}
		return notification;
	}

	@Override
	public void saveRecordAndSendMessageWithAccountID(String accountID,
			String title, String content, Map<String, String> extendData,
			String sendPoint) {

		saveRecordAndSendMessageWithAccountIDArray(
				StringUtil.isEmpty(accountID) ? null : new ArrayList<String>(
						Arrays.asList(accountID)), title, content, extendData,
				sendPoint);
	}

	public TagAliasResult getDeviceTagAlias(String accountID, String push_id)
			throws APIConnectionException, APIRequestException {

		return getClientWithAccountID(accountID).getDeviceTagAlias(push_id);

	}

	public JPushClient getClientWithAccountID(String accountID) {
		Account account = accountService.getAccount(accountID);
		if (account == null)
			return null;
		if (account.getRole_type() == RoleType.consignor) {

			return consignorPushClient;
		} else if (account.getRole_type() == RoleType.truck) {

			return truckPushClient;
		}
		return null;

	}

	@Override
	public void updateAliasAndTag(String accountID, String push_id,
			String alias, String tag) throws APIConnectionException,
			APIRequestException {
		TagAliasResult tags = getDeviceTagAlias(accountID, push_id);
		tags = tags == null ? new TagAliasResult() : tags;
		tags.tags = tags.tags == null ? new ArrayList<String>() : tags.tags;
		boolean isChange = false;
		Set<String> addAlias = new HashSet<String>();
		if (tags.tags.indexOf(tag) == -1) {
			addAlias.add(tag);
			isChange = true;
		}
		if (StringUtil.isEmpty(tags.alias) || tags.alias.equals(alias) == false) {
			tags.alias = alias;
			isChange = true;
		}
		if (isChange) {
			Set<String> set = new HashSet<String>();
			set.addAll(tags.tags);
			getClientWithAccountID(accountID).updateDeviceTagAlias(push_id,
					tags.alias, addAlias, new HashSet<String>());
		}
	}
}
