package com.memory.platform.global;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.memory.platform.core.AppUtil;

public class DateUtil {
	/**
	 * 获取当前日日期返回
	 */
	public static String getDay() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String day = formatter.format(new Date());
		return day;
	}

	/**
	 * 获取月份
	 */
	public static String getMonth() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String month = formatter.format(new Date());
		return month;
	}

	/**
	 * 功能描述： 获取当前时间最近今天 输入参数: @param count 输入参数: @return 异 常： 创 建 人: yangjiaqiao
	 * 日 期: 2016年8月25日下午4:21:29 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	public static Map<String, Object> getDays(int count) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> dateList_v = new ArrayList<String>(); // 日期数组
		List<String> dateList = new ArrayList<String>(); // 日期数组
		SimpleDateFormat sdf_v = new SimpleDateFormat("MM月dd日");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -count);
		Date strDate = calendar.getTime();
		Date endDate = DateUtil.getbeforeDate();
		Calendar calendard = Calendar.getInstance();
		int day = daysBetween(strDate, endDate);
		for (int i = 0; i <= day; i++) {
			calendard.setTime(strDate);
			calendard.add(Calendar.DATE, i);
			Date date = calendard.getTime();
			dateList_v.add(sdf_v.format(date));
			dateList.add(sdf.format(date));
		}
		map.put("dateList", dateList);
		map.put("dateList_v", dateList_v);
		return map;
	}

	/**
	 * 功能描述： 获取当前年份的所有月份 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月24日上午11:48:59 修 改 人: 日 期: 返 回：String
	 */
	public static Map<String, Object> getMonths(String yearVal) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> months = new ArrayList<String>();
		List<String> months_v = new ArrayList<String>();
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String month = formatter.format(new Date());
		SimpleDateFormat formatterYear;
		formatterYear = new SimpleDateFormat("yyyy");
		String year = formatterYear.format(new Date());
		if (StringUtils.isEmpty(yearVal)) {
			yearVal = year;
		}
		if (Integer.parseInt(year) > Integer.parseInt(yearVal)) {
			for (int i = 1; i <= 12; i++) {
				if (i < 10) {
					months.add(yearVal + "-" + "0" + i);
					months_v.add(yearVal + "年" + "0" + i + "月");
				} else {
					months.add(yearVal + "-" + i);
					months_v.add(yearVal + "年" + i + "月");
				}

			}
		} else if (Integer.parseInt(year) == Integer.parseInt(yearVal)) {
			for (int i = 1; i <= Integer.parseInt(month); i++) {
				if (i < 10) {
					months.add(yearVal + "-" + "0" + i);
					months_v.add(yearVal + "年" + "0" + i + "月");
				} else {
					months.add(yearVal + "-" + i);
					months_v.add(yearVal + "年" + i + "月");
				}
			}
		}
		map.put("months", months);
		map.put("months_v", months_v);
		return map;
	}

	/**
	 * 获取年
	 */
	public static String getYear() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String year = formatter.format(new Date());
		return year;
	}

	public static String getYear(Date date) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String year = formatter.format(date);
		return year;
	}

	/**
	 * 功能描述： 获取当前年份，前几年的时间 输入参数: @param qianYear 输入参数: @return 异 常： 创 建 人:
	 * yangjiaqiao 日 期: 2016年8月24日上午10:06:21 修 改 人: 日 期: 返 回：List<Integer>
	 */
	public static List<Map<String, Object>> getYearQian(int qianYear) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(formatter.format(new Date()));
		int yearQian = year - qianYear;
		for (int i = yearQian; i <= year; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i);
			map.put("text", i);
			list.add(map);
		}
		return list;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制) 如Sat May 11
	 * 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'
	 * 
	 * @param time
	 *            Date 日期
	 * @return String 字符串 默认yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToString(Date time, String format) {
		String f = "yyyy-MM-dd HH:mm:ss";
		if (format != null) {
			f = format;
		}
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(f);
		String Time = formatter.format(time);
		return Time;
	}

	public static String dateNo() {
		String f = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(f);
		String Time = formatter.format(new Date());
		String no = Time.replaceAll("-", "").replaceAll(":", "")
				.replaceAll(" ", "")
				+ AppUtil.random(4).toString();
		return no;
	}

	public static String timeNo() {
		String f = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(f);
		String Time = formatter.format(new Date());
		return Time.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 20:24:39
	 * 
	 * @return String
	 */
	public static String getTime() {
		return dateToString(new Date(), null);
	}

	/**
	 * 取系统当前日期:返回只值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateToString(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 字符串转为日期
	 * 
	 * @param date
	 * @param format
	 *            默认yyyy-MM-dd
	 * @return
	 */
	public static Date stringToDate(String date, String format) {
		if (format == null) {
			format = "yyyy-MM-dd";
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 传入日期值加减去天数后的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDate(Date date, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}

	/**
	 * 传入日期值加减去天数后的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAddYear(Date date, Integer year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 
	 * 功能描述： 根据出生日期计算年龄 输入参数: @param birthDate 输入参数: @return 异 常： 创 建 人: yico-cj
	 * 日 期: 2016年6月3日下午4:52:07 修 改 人: 日 期: 返 回：int
	 */
	public static int getAge(Date birthDate) {

		if (birthDate == null)
			throw new RuntimeException("出生日期不能为null");

		int age = 0;

		Date now = new Date();

		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");

		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);

		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);

		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0)
			age -= 1;
		if (age < 0)
			age = 0;
		return age;
	}

	// 获取当前月第一天日期
	public static Date getMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -(calendar.get(Calendar.DATE) - 1));
		return calendar.getTime();
	}

	// 获取前一天日期
	public static Date getbeforeDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static final int daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(String one, String two) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return getDistanceTime(df.parse(one), df.parse(two));
		} catch (ParseException e) {
		 
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(Date one, Date two) {

		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;

		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		StringBuffer buffer = new StringBuffer();
		if (day != 0) {
			buffer.append(day + "天");
		}
		if (hour != 0) {
			buffer.append(hour + "小时");

		}
		if (min != 0) {
			buffer.append(min + "分");

		}
		if (sec != 0) {
			buffer.append(sec + "秒");
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(getDistanceTime( "2017-05-08 19:44:33","2017-09-05 14:21:00"));
		System.out.println(getDistanceTime( "2017-09-05 14:21:33","2017-09-05 14:21:00"));
		//System.out.println(dateToString(date, "yyyy-MM"));
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// System.out.println(sdf.format(new
		// Date(Long.valueOf("1468299437"+"000"))));
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Calendar
		 * calendar=Calendar.getInstance(); int i = calendar.get(Calendar.DATE);
		 * for(int k =i-1;k>=1;k--){ calendar.setTime(new Date());
		 * calendar.add(Calendar.DATE, -k); Date date=calendar.getTime();
		 * System.out.println(sdf.format(date)); }
		 */
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); int day =
		 * daysBetween(getMonthFirstDate(),getbeforeDate()); Date startDate =
		 * getMonthFirstDate(); Date endDate = getbeforeDate(); Calendar
		 * calendar=Calendar.getInstance(); for(int i=day;i<=0;i++){
		 * calendar.setTime(startDate); calendar.add(Calendar.DATE,i); Date
		 * date=calendar.getTime(); System.out.println(sdf.format(date)); }
		 */

	}
}