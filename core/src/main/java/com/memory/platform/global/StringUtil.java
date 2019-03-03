package com.memory.platform.global;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.memory.platform.core.AppUtil;

/**
 * 创 建 人： longqibo 日 期： 2016年5月3日 下午3:39:31 修 改 人： 日 期： 描 述： 字符串工具类 版 本 号： V1.0
 */
public class StringUtil {

	// 账号正则
	// public static String ACCOUNT_REG = "^[a-z]{1}[a-z0-9]{5,15}$";
	public static String ACCOUNT_REG = "^[a-z0-9]{6,16}$";
	// 手机号码正则
	public static String MOBILE_REG = "^1\\d{10}$";
	// 字母数字混合6-16位
	// public static String PASSWORD_REG =
	// "^(?!\\D+$)(?![^a-z]+$)[a-zA-Z\\d]{6,16}$";
	public static String PASSWORD_REG = "^[a-zA-Z\\d]{6,16}$";

	/**
	 * 功能描述：生成32位随机编码 输入参数: @return 异 常： 创 建 人: longqibo 日 期: 2016年4月28日下午4:22:06 修
	 * 改 人: 日 期: 返 回：String
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 功能描述： 判断字符串是否不为空 输入参数: @param str 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年4月30日上午10:15:17 修 改 人: 日 期: 返 回：boolean
	 */
	public static boolean isNotEmpty(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * 功能描述：判断字符串是否为空 输入参数: @param str 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月3日下午1:54:24 修 改 人: 日 期: 返 回：boolean
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 功能描述： 字符串正则表达式验证 输入参数: @param str 字符串 输入参数: @param reg 正则表达式 输入参数: @return 异
	 * 常： 创 建 人: longqibo 日 期: 2016年5月3日下午2:58:23 修 改 人: 日 期: 返 回：boolean
	 */
	public static boolean regExp(String str, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * aiqiwu 2018-03-15获取指定长度的随机字符串，包含大小写字母和数字
	 * */
	public static String getRandomStr(int length) {
		int random = createRandomInt();
		return createRandomStr(random, length);
	}

	private static String createRandomStr(int random, int len) {
		Random rd = new Random(random);
		final int maxNum = 62;
		StringBuffer sb = new StringBuffer();
		int rdGet;// 取得随机数
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		int count = 0;
		while (count < len) {
			rdGet = Math.abs(rd.nextInt(maxNum));// 生成的数最大为62-1
			if (rdGet >= 0 && rdGet < str.length) {
				sb.append(str[rdGet]);
				count++;
			}
		}
		return sb.toString();
	}

	private static int createRandomInt() {
		// 得到0.0到1.0之间的数字，并扩大100000倍
		double temp = Math.random() * 100000;
		// 如果数据等于100000，则减少1
		if (temp >= 100000) {
			temp = 99999;
		}
		int tempint = (int) Math.ceil(temp);
		return tempint;
	}

	public static void main(String[] args) {
		String a = getRandomStr(32);
		String txnTime = DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
		String b = a;
	}
}