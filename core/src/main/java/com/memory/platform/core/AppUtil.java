package com.memory.platform.core;

import java.awt.image.RescaleOp;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import com.alibaba.dubbo.rpc.RpcContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.JsonPluginsUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.ThreeDESUtil;
import com.memory.platform.memStore.MemShardStrore;

public class AppUtil {
	public static final String account_id_login_user = "account_id_login_user";
	public static final String token_login_user = "token_login_user";
	static Logger log = Logger.getLogger(AppUtil.class);
	static ThreadLocal<Map<String, Object>> threadLocalMap = new ThreadLocal<Map<String, Object>>();
	private static ApplicationContext applicationContex;
	private static ExecutorService singleThreadPool = Executors
			.newSingleThreadScheduledExecutor();

	public static ExecutorService getSingleThreadPool() {
		return singleThreadPool;
	}

	public static void setSingleThreadPool(ExecutorService singleThreadPool) {
		AppUtil.singleThreadPool = singleThreadPool;
	}

	public static ApplicationContext getApplicationContex() {
		return applicationContex;
	}

	public static void setApplicationContex(ApplicationContext applicationContex) {
		AppUtil.applicationContex = applicationContex;
	}

	public static Gson getGson() {
		return new GsonBuilder().serializeNulls()
				.setDateFormat("YYYY-MM-dd HH:mm:ss").create();
	}

	public static Map<String, Object> getThreadMap() {
		Map<String, Object> map = threadLocalMap.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			threadLocalMap.set(map);
		}
		return threadLocalMap.get();
	}

	public static void clearThreadMap() {

		getThreadMap().clear();
	}

	public static <T> T getThreadLocalObjectForKey(String key) {
		if (getThreadMap().containsKey(key)) {
			return (T) getThreadMap().get(key);

		}
		return null;
	}

	public static void setThreadLocalObject(String key, Object value) {
		getThreadMap().put(key, value);
	}

	public static void setLoginUser(Account account) {

		MemShardStrore strore = MemShardStrore.getInstance();
		Jedis jedis = strore.getClient();
		String key = account.getToken();
		String userJson = AppUtil.getGson().toJson(account);

		String accountUserJson = jedis.hget(account_id_login_user,
				account.getId());
		Transaction transaction = jedis.multi();
		if (StringUtil.isNotEmpty(accountUserJson)) {
			Account memAccount = JsonPluginsUtil.jsonToBean(accountUserJson,
					Account.class);

			transaction.hdel(token_login_user, memAccount.getToken());
		}

		transaction.hset(token_login_user, account.getToken(), userJson);
		transaction.hset(account_id_login_user, account.getId(), userJson);
		transaction.exec();
		AppUtil.setThreadLocalObject("USER", account);
	}

	public static Account getAccountWithMemstoreByToken(String tokenID) {
		MemShardStrore strore = MemShardStrore.getInstance();
		Jedis jedis = strore.getClient();
		String accountUserJson = jedis.hget(token_login_user, tokenID);
		Account account = null;
		if (StringUtil.isNotEmpty(accountUserJson))
			account = JsonPluginsUtil
					.jsonToBean(accountUserJson, Account.class);
		return account;
	}

	public static Account getLoginUser() {

		Account ac = AppUtil.getThreadLocalObjectForKey("USER");
		if (ac != null) {
			return ac;
		}

		String tokenID = RpcContext.getContext().getAttachment("tokenID");
		if (StringUtil.isNotEmpty(tokenID)) {
			ac = AppUtil.getAccountWithMemstoreByToken(tokenID);
			RpcContext.getContext().setAttachment("user", "1");
			// AppUtil.setThreadLocalObject("USER", ac);
		}
		return ac;

	}

	public static String desJsonEncode(Object obj, String token) {
		// token =token;
		Gson gons = getGson();
		String json = gons.toJson(obj);
		String desStr = null;
		try {
			desStr = ThreeDESUtil.des3AppEncodeCBC(json, token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return desStr;
	}

	public static String getUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	public static String getToken() {
		UUID uuid = UUID.randomUUID();
		long time = System.currentTimeMillis();

		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		String md5Str = AppUtil.md5By16(String.valueOf(time)).toLowerCase();
		String token = uuidStr + md5Str;
		return token;
	}

	public static String getWebPath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path;
		return basePath;
	}

	public static String getUpLoadPath(HttpServletRequest request) {
		String upLoadPath = request.getSession().getServletContext()
				.getRealPath("");
		return upLoadPath;
	}

	public static String getExMsg(String msg) {
		return "<{}>" + msg;
	}

	public static String md5(String str) {
		StringBuffer sb = new StringBuffer(32);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes("utf-8"));
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}

	public static String md5By16(String str) {
		StringBuffer sb = new StringBuffer(32);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes("utf-8"));
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			return null;
		}
		return sb.toString().substring(8, 24);
	}

	/*
	 * 生成随机数 int number---生成随机数个数
	 */
	public static StringBuffer random(int number) {

		Random random = new Random();
		StringBuffer random5 = new StringBuffer();
		for (int i = 0; i < number; i++) {
			random5.append(random.nextInt(10));
		}
		return random5;
	}

	/*
	 * 返回指定时间的随机数 Calendar time----传入的时间
	 */
	public static StringBuffer getrandom(Calendar time) {
		// Calendar time=Calendar.getInstance();
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(time.getTime().toString());
		int sj = Integer.parseInt(m.replaceAll("").substring(6, 8));
		if ((0 <= sj) && (sj <= 6)) {
			return random(6);
		} else {
			if ((7 <= sj) && (sj <= 12)) {
				return random(7);
			} else {
				if ((13 <= sj) && (sj <= 19)) {
					return random(8);
				} else {
					return random(9);
				}
			}
		}
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getMenuTypeName(int type) {
		String menuType = "";
		switch (type) {
		case 0:
			menuType = "系统菜单";
			break;
		case 1:
			menuType = "顶级菜单";
			break;
		case 2:
			menuType = "模块";
			break;
		case 3:
			menuType = "菜单";
			break;
		case 4:
			menuType = "功能";
			break;

		default:
			break;
		}
		return menuType;
	}

	/**
	 * 根据用户选择的菜单类型，确定子类型是什么 功能描述： 输入参数: @param type 类型ID 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年4月28日下午4:49:00 修 改 人: 日 期: 返
	 * 回：Map<String,String>
	 */
	public static Map<String, String> getMenuTypeMap(int type) {
		Map<String, String> typeMap = new HashMap<String, String>();
		switch (type) {
		case 0:
			type = 1;
			typeMap.put("type", type + "");
			typeMap.put("name", "顶级菜单");
			break;
		case 1:
			type = 2;
			typeMap.put("type", type + "");
			typeMap.put("name", "模块");
			break;
		case 2:
			type = 3;
			typeMap.put("type", type + "");
			typeMap.put("name", "菜单");
			break;
		case 3:
			type = 4;
			typeMap.put("type", type + "");
			typeMap.put("name", "操作");
			break;
		}
		return typeMap;
	}

	public static String getByteHex(byte[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i != arr.length - 1) {
				sb.append(String.format("%02x,", arr[i]));
			} else {
				sb.append(String.format("%02x", arr[i]));
			}
		}
		return sb.toString();
	}

	public static void printByteArray(String info, byte[] arr) {
		if (arr != null)
			log.info(String.format("%s:%s", info, getByteHex(arr)));
	}

	public static float getDistance(float lat, float lng, float disLat,
			float disLng) {
		float factor = 111 * 1000;
		// factor = (float) Math.sqrt( Math.pow( (factor+ factor) + (factor+
		// factor),2));
		float ret = 0;
		float tmp = (disLng - lng) * (disLng - lng) + (disLat - lat)
				* (disLat - lat);
		tmp = (float) Math.sqrt(tmp);
		ret = tmp * factor / 1000;
		return ret;
	}

	public static void main(String[] params) {
		// 110000 北京市 100000 北京 1 北京 116.405285 39.904989 Beijing
		float f = AppUtil.getDistance(39.904989f, 116.405285f, 30.287459f,
				120.153576f);
		System.out.println(f);

	}

}