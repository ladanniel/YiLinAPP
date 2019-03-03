package com.memory.platform.global;

import java.util.HashMap;
import java.util.Map;

import javassist.bytecode.stackmap.MapMaker;

import com.memory.platform.memStore.MemShardStrore;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import redis.clients.jedis.Jedis;

/**
 * 短信工具类 创 建 人： 武国庆 日 期： 2016年6月15日 下午8:00:35 修 改 人： 日 期： 描 述： 版 本 号： V1.0
 */
public class SMSUtil {

	public static final String SUCCESS = "success";
	public static final String MESSAGE = "msg";
	public static final String time = "time"; // 验证码创建时间
	public static final String exprie = "exprie";
	public static final String elapseTime = "elapseTime"; // 验证码已经存在了多少时间
	public static final String surplusSecond = "surplusSecond"; // 发送验证码剩余时间
	public static final String code = "code"; // 发送验证码剩余时间
	public static final String sendCode = "sendCode";
   public static final String errCount = "errCount";
	public static enum CachNameType {

		receiptTimeOut, // 回执单确认发货
		phoneTimeOut, // 手机验证码
		phoneRegister, // 注册
		phoneUpdatePassword, // 修改密码
		phoneFindPassword, // 找回密码
		phoneUpdatePayPassword, // 修改支付密码
		phoneUpdatePhoneNo // 修改手机号码
	}

	public static Map<String, Object> canSendCodeNew(String phone,
			CachNameType cachNameType, String businessKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		Jedis jedis = MemShardStrore.getInstance().getClient();
		String key = getPhoneKey(businessKey, cachNameType);
		long time = System.currentTimeMillis();
		long elapseTime = 0;
		long surplusSecond = 60;
		boolean success = false;
		do {
			String value = jedis.hget(key, "sendCode");
			if (StringUtil.isEmpty(value)) {
				surplusSecond = 0;
				success = true;
				break;

			}
			Map<String, Object> memMap = JsonPluginsUtil.jsonToMap(value);
		//	System.out.print(memMap.get(SMSUtil.time).getClass());
			String str = memMap.get(SMSUtil.time).toString();
			time = Long.parseLong(str);
			elapseTime = System.currentTimeMillis() - time;
			surplusSecond = Math.max(0, 60 - (elapseTime / 1000));
			success = surplusSecond == 0;

		} while (false);
		map.put(SUCCESS, success);
		map.put(SMSUtil.time, time);
		map.put(SMSUtil.elapseTime, elapseTime);
		map.put(SMSUtil.surplusSecond, surplusSecond);
		map.put("code", "");
		return map;
	}

	public static Map<String, Object> canSendCodeNew(String phone,
			CachNameType cachNameType) {
		return canSendCodeNew(phone, cachNameType, phone);

	}

	public static Map<String, Object> saveSendCodeNew(String phone,
			String code, CachNameType cachNameType, String bussinessKey) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(SUCCESS, false);
		map.put(SMSUtil.time, System.currentTimeMillis());
		map.put(SMSUtil.elapseTime, 0);
		map.put(SMSUtil.surplusSecond, 60);

		map.put("code", code);

		saveSendCodeMap(map, bussinessKey, cachNameType);
		map.put("code", "");

		return map;
	}

	public static void saveSendCodeMap(Map<String, Object> map,
			String bussinessKey, CachNameType cachNameType) {

		Jedis jedis = MemShardStrore.getInstance().getClient();
		String key = getPhoneKey(bussinessKey, cachNameType);

		String json = JsonPluginsUtil.beanToJson(map);
		jedis.hset(key, "sendCode", json);
		jedis.expire(key, 60 * 10);
	}

	public static Map<String, Object> saveSendCodeNew(String phone,
			String code, CachNameType cachNameType) {
		return saveSendCodeNew(phone, code, cachNameType, phone);
	}

	public static String getSendCode(String phone, CachNameType cachNameType,
			String bussinessKey) {
		Map<String, Object> ret = getSendCodeMap(phone, cachNameType,
				bussinessKey);

		return ret == null ? "" : ret.get("code")+"";
	}

	public static Map<String, Object> getSendCodeMap(String phone,
			CachNameType cachNameType, String bussinessKey) {

		Jedis jedis = MemShardStrore.getInstance().getClient();
		String key = getPhoneKey(bussinessKey, cachNameType);
		String value = jedis.hget(key, "sendCode");
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		Map<String, Object> memMap = JsonPluginsUtil.jsonToMap(value);

		return memMap;
	}

	public static String getSendCode(String phone, CachNameType cachNameType) {
		return getSendCode(phone, cachNameType, phone);
	}

	public static void deleteSendCode(String phone, CachNameType cahcNameType,
			String bussinessKey) {
		String key = getPhoneKey(bussinessKey, cahcNameType);
		Jedis jedis = MemShardStrore.getInstance().getClient();
		jedis.del(key);

	}

	public static void deleteSendCode(String phone, CachNameType cahcNameType) {
		deleteSendCode(phone, cahcNameType, phone);

	}

	private static String getPhoneKey(String bussinessKey,
			CachNameType cachNameType) {
		return cachNameType.toString() + bussinessKey;

	}

	/*
	 * bylil 是否可以发送验证码 如果不能发送 返回 success:false 并且返回可发送剩余时间surplusSecond 创建时间
	 * time 存在了多少时间elapseTime key：验证码在缓存中的键 CachNameType:回执单确认发货 或者手机验证码
	 */
	public static Map<String, Object> canSendPhoneCode(
			CachNameType cachNameType, String key) {

		CacheManager cacheManager = EHCacheUtil
				.initCacheManager(EHCacheUtil.ehcachePath);
		String cachName = getCachNameByCachNameType(cachNameType);

		Cache cache = cacheManager.getCache(cachName);
		Element element = cache.get(key);
		Map<String, Object> map = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		long elapseTime = 0;
		long surplusSecond = 60;
		boolean success = false;
		do {
			if (element == null) {
				success = true;
				surplusSecond = 0;
				break;
			}
			time = element.getCreationTime();
			elapseTime = System.currentTimeMillis() - time;
			surplusSecond = Math.max(0, 60 - (elapseTime / 1000));
			success = surplusSecond == 0;
		} while (false);
		map.put(SUCCESS, success);
		map.put(SMSUtil.time, time);
		map.put(SMSUtil.elapseTime, elapseTime);
		map.put(SMSUtil.surplusSecond, surplusSecond);
		return map;
	}

	public static String getCachNameByCachNameType(CachNameType cachNameType) {
		if (cachNameType == CachNameType.receiptTimeOut) {
			return "receiptTimeOut";
		} else if (cachNameType == CachNameType.phoneTimeOut) {
			return "phoneTimeOut";
		} else {

			return cachNameType.toString();
		}

	}

	public static Map<String, Object> sendPhoneCode(String phone, String code,
			String codeType, String robOrderConfirmId) {
		Map<String, Object> map = new HashMap<String, Object>();
		CacheManager cacheManager = EHCacheUtil
				.initCacheManager(EHCacheUtil.ehcachePath);

		if ("5".equals(codeType)) {// 收货验证码
			Cache phoneTime = cacheManager.getCache("receiptTimeOut");
			// String key = "time_"+codeType+robOrderConfirmId;
			// Element result = phoneTime.get(key);
			// String key_1 = "phone_time_"+codeType+robOrderConfirmId;//验证码存入时间
			Element confirmElement = phoneTime.get(robOrderConfirmId);
			if (confirmElement != null) {

				long elapseTime = System.currentTimeMillis()
						- confirmElement.getCreationTime();
				elapseTime = elapseTime / 1000;
				if (elapseTime < 60) {

					map.put(SUCCESS, false);
					map.put(MESSAGE, "请" + (60 - elapseTime) + "秒后重试");
					return map;
				}
			}

			// 验证码存入缓存
			// StringBuffer code = AppUtil.random(6);
			// Cache cacheCode = cacheManager.getCache("receiptCode");
			// Element element = new Element(robOrderConfirmId+codeType,code);
			// cacheCode.put(element);
			//
			// //手机号60秒内存活
			// Cache cachePhone = cacheManager.getCache("receiptTimeOut");
			// Element elementPhone = new Element(key,robOrderConfirmId);
			// cachePhone.put(elementPhone);
			// Element elementPhoneTime = new
			// Element(key_1,System.currentTimeMillis());
			// cachePhone.put(elementPhoneTime);
			map.put(SUCCESS, true);
			map.put(MESSAGE, "发送成功!");
			return map;
		}

		// if(codeType.equals("1")||codeType.equals("2")||codeType.equals("3")||codeType.equals("4")){
		// //1：注册手机验证码2：修改支付密码 3:修改账号密码 4：是更换手机号码 5：确认收货验证
		Cache phoneTime = cacheManager.getCache("phoneTimeOut");
		String key = "time_" + codeType + phone;
		Element result = phoneTime.get(key);
		String key_1 = "phone_time_" + codeType + phone;// 验证码存入时间

		// 是否重复发送
		if (result != null) {
			map.put(SUCCESS, false);
			map.put(MESSAGE, "请60秒后重试");
			return map;
		}

		// 验证码存入缓存
		// StringBuffer code = AppUtil.random(6);
		Cache cacheCode = cacheManager.getCache("phoneCode");
		Element element = new Element(phone + codeType, code);
		cacheCode.put(element);

		// 手机号60秒内存活
		Cache cachePhone = cacheManager.getCache("phoneTimeOut");
		Element elementPhone = new Element(key, phone);
		cachePhone.put(elementPhone);
		Element elementPhoneTime = new Element(key_1,
				System.currentTimeMillis());
		cachePhone.put(elementPhoneTime);
		map.put(SUCCESS, true);
		map.put(MESSAGE, "发送成功!");
		return map;
		// }
		// return null;
	}

	/**
	 * 发送验证码 功能描述： 输入参数: @param code 验证码 输入参数: @param phone 手机号 输入参数: @param
	 * codeType 验证类型 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年6月25日上午11:05:16 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> sendPhoneCode(String code, String phone,
			String codeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		CacheManager cacheManager = EHCacheUtil
				.initCacheManager(EHCacheUtil.ehcachePath);
		// if(codeType.equals("1")||codeType.equals("2")||codeType.equals("3")||codeType.equals("4")){
		// //注册手机验证码 3是修改手机密码 4是更换手机号码
		Cache phoneTime = cacheManager.getCache("phoneTimeOut");
		String key = "time_" + codeType + phone;
		Element result = phoneTime.get(key);
		String key_1 = "phone_time_" + codeType + phone;// 验证码存入时间
		Element result_1 = phoneTime.get(key_1);
		// 是否重复发送
		if (result != null) {
			Long preTime = (Long) result_1.getObjectValue();
			Long resulltTime = (System.currentTimeMillis() - preTime) / 1000;
			map.put(SUCCESS, false);
			map.put(MESSAGE, resulltTime + "");
			return map;
		}

		// 验证码存入缓存

		Cache cacheCode = cacheManager.getCache("phoneCode");
		Element element = new Element(phone + codeType, code.toString());
		cacheCode.put(element);

		// 手机号60秒内存活
		Cache cachePhone = cacheManager.getCache("phoneTimeOut");
		Element elementPhone = new Element(key, phone);
		cachePhone.put(elementPhone);
		Element elementPhoneTime = new Element(key_1,
				System.currentTimeMillis());
		cachePhone.put(elementPhoneTime);
		map.put(SUCCESS, true);
		map.put(MESSAGE, "发送成功！");
		return map;
		// }
		// return null;
	}

	/**
	 * 验证短信code 功能描述： 输入参数: @param phone 手机号 输入参数: @param code 验证码 输入参数: @param
	 * codeType 验证码类型 输入参数: @return 异 常： 创 建 人: Administrator 日 期:
	 * 2016年6月25日上午11:04:02 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> checkPhoneCode(String phone, String code,
			String codeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		CacheManager cacheManager = EHCacheUtil
				.initCacheManager(EHCacheUtil.ehcachePath);
		// if(codeType.equals("1")||codeType.equals("2")||codeType.equals("3")||codeType.equals("4")){
		// //验证注册手机验证码
		Cache cacheCode = cacheManager.getCache("phoneCode");
		Element result = cacheCode.get(phone + codeType);

		// 取验证错误次数
		Element errorElement = cacheCode.get("error_" + phone + codeType);
		Integer errorNum = errorElement == null ? 0 : (Integer) errorElement
				.getObjectValue();
		if (errorElement != null && errorNum > 5) {
			cacheCode.remove(phone + codeType);
			cacheCode.remove("error_" + phone + codeType);
			map.put(SUCCESS, false);
			map.put(MESSAGE, "校验码错误次数过多，请重新获取");
			return map;
		}

		if (result == null) {
			map.put(SUCCESS, false);
			map.put(MESSAGE, "验证码已失效，请重新获取");
			return map;
		}

		if (!code.equals(result.getObjectValue().toString())) {
			// 验证错误次数
			Element element = new Element("error_" + phone + codeType,
					errorNum + 1);
			cacheCode.put(element);
			map.put(SUCCESS, false);
			map.put(MESSAGE, "验证码错误");
			return map;
		}

		cacheCode.remove("error_" + phone + codeType);
		map.put(SUCCESS, true);
		map.put(MESSAGE, "");
		return map;

	}

}