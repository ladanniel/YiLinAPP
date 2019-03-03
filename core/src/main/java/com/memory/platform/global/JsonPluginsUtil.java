package com.memory.platform.global;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年5月5日 下午3:18:49 修 改 人： 日 期： 描 述： 版 本 号： V1.0
 */
public class JsonPluginsUtil {
	static SerializeConfig mapping = new SerializeConfig();
	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer(
				"yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {
		/*String[] dateFormats = new String[] { "yyyy-MM-dd",
				"yyyy-MM-dd HH:mm:ss" };*/
		return JSON.parseObject(jsonString, beanCalss);
		/*
		 * JSONUtils.getMorpherRegistry().registerMorpher(new
		 * DateMorpher(dateFormats)); JSONObject jsonObject =
		 * JSONObject.fromObject(jsonString); T bean = (T)
		 * JSONObject.toBean(jsonObject, beanCalss); return bean;
		 */

	}

	/**
	 * 将java对象转换成json字符串
	 *
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean) {
		return  JSON.toJSONString(bean);
	 

	}

	/**
	 * 将java对象转换成json字符串
	 *
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean, String[] _nory_changes,
			boolean nory) {

		JSONObject json = null;

		if (nory) {// 转换_nory_changes里的属性

			Field[] fields = bean.getClass().getDeclaredFields();
			String str = "";
			for (Field field : fields) {
				// System.out.println(field.getName());
				str += (":" + field.getName());
			}
			fields = bean.getClass().getSuperclass().getDeclaredFields();
			for (Field field : fields) {
				// System.out.println(field.getName());
				str += (":" + field.getName());
			}
			str += ":";
			for (String s : _nory_changes) {
				str = str.replace(":" + s + ":", ":");
			}
			json = JSONObject.fromObject(bean, configJson(str.split(":")));

		} else {// 转换除了_nory_changes里的属性

			json = JSONObject.fromObject(bean, configJson(_nory_changes));
		}

		return json.toString();

	}

	private static JsonConfig configJson(String[] excludes) {

		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.setExcludes(excludes);
		//
		jsonConfig.setIgnoreDefaultExcludes(false);
		//
		// jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		// jsonConfig.registerJsonValueProcessor(Date.class,
		//
		// new DateJsonValueProcessor(datePattern));

		return jsonConfig;

	}

	/**
	 * 将java对象List集合转换成json字符串
	 * 
	 * @param beans
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String beanListToJson(List beans) {
		return  JSON.toJSONString( beans);
	/*	StringBuffer rest = new StringBuffer();

		rest.append("[");

		int size = beans.size();

		for (int i = 0; i < size; i++) {

			rest.append(beanToJson(beans.get(i)) + ((i < size - 1) ? "," : ""));

		}

		rest.append("]");

		return rest.toString();*/

	}

	/**
	 * 
	 * @param beans
	 * @param _no_changes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String beanListToJson(List beans, String[] _nory_changes,
			boolean nory) {
		
		StringBuffer rest = new StringBuffer();

		rest.append("[");

		int size = beans.size();

		for (int i = 0; i < size; i++) {
			try {
				rest.append(beanToJson(beans.get(i), _nory_changes, nory));
				if (i < size - 1) {
					rest.append(",");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		rest.append("]");

		return rest.toString();

	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 *
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static Map jsonToMap(String jsonString) {
		return  JSON.parseObject(jsonString );
	/*	JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {

			key = (String) keyIter.next();
			value = jsonObject.get(key).toString();
			valueMap.put(key, value);

		}

		return valueMap;*/
	}

	/**
	 * map集合转换成json格式数据
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<String, ?> map, String[] _nory_changes,
			boolean nory) {
		return JSON.toJSONString(map);
		/*String s_json = "{";

		Set<String> key = map.keySet();
		for (Iterator<?> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (map.get(s) == null) {

			} else if (map.get(s) instanceof List<?>) {
				s_json += (s + ":" + JsonPluginsUtil.beanListToJson(
						(List<?>) map.get(s), _nory_changes, nory));

			} else {
				JSONObject json = JSONObject.fromObject(map);
				s_json += (s + ":" + json.toString());
				;
			}

			if (it.hasNext()) {
				s_json += ",";
			}
		}

		s_json += "}";
		return s_json;*/
	}

	/**
	 * 从json数组中得到相应java数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Object[] jsonToObjectArray(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);

		return jsonArray.toArray();

	}

	public static String listToJson(List<?> list) {

		JSONArray jsonArray = JSONArray.fromObject(list);

		return jsonArray.toString();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 *
	 * @param jsonString
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonToBeanList(String jsonString,
			Class<T> beanClass) {
	 
		  	return JSON.parseArray(jsonString, beanClass) ;
		/*JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		T bean;
		int size = jsonArray.size();
		List<T> list = new ArrayList<T>(size);

		for (int i = 0; i < size; i++) {

			jsonObject = jsonArray.getJSONObject(i);
			bean = (T) JSONObject.toBean(jsonObject, beanClass);
			list.add(bean);

		}

		return list;*/

	}

	/**
	 * 从json数组中解析出java字符串数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static String[] jsonToStringArray(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		int size = jsonArray.size();

		for (int i = 0; i < size; i++) {

			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/**
	 * 从json数组中解析出javaLong型对象数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Long[] jsonToLongArray(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		int size = jsonArray.size();
		Long[] longArray = new Long[size];

		for (int i = 0; i < size; i++) {

			longArray[i] = jsonArray.getLong(i);

		}

		return longArray;

	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Integer[] jsonToIntegerArray(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		int size = jsonArray.size();
		Integer[] integerArray = new Integer[size];

		for (int i = 0; i < size; i++) {

			integerArray[i] = jsonArray.getInt(i);

		}

		return integerArray;

	}

	/**
	 * 从json数组中解析出java Double型对象数组
	 *
	 * @param jsonString
	 * @return
	 */
	public static Double[] jsonToDoubleArray(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		int size = jsonArray.size();
		Double[] doubleArray = new Double[size];

		for (int i = 0; i < size; i++) {

			doubleArray[i] = jsonArray.getDouble(i);

		}

		return doubleArray;

	}
}
