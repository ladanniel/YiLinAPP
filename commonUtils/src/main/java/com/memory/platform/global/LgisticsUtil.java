package com.memory.platform.global;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.memory.platform.module.order.vo.Lgistics;

/**
 * 物流信息工具类
 *  
 * 
 */
public class LgisticsUtil {
	private  static String URL = "https://sp0.baidu.com/9_Q4sjW91Qh3otqbppnN2DJv/pae/channel/data/asyncqury?appid=4001&com=yunda&nu=3969410009709&vcode=&token=&_=1468650888424";
	private static String COOKIE = "eos_style_cookie=default; BAIDUID=A08EC5E1B29CEFC30DEA95892C4867DC:FG=1; expires=Wed, 19-Jul-17 01:42:04 GMT; max-age=31536000; path=/; domain=.baidu.com; version=1;";
	/**
	 * 
	* 功能描述： 获取物流信息
	* 输入参数:  @param codeddc
	* 输入参数:  @param nu
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: Administrator
	* 日    期: 2016年7月18日下午3:23:33
	* 修 改 人: 
	* 日    期: 
	* 返    回：LgisticsInfo
	 */
	public static Lgistics getLgisticsNode(String code,String nu) {
		String url = URL+"appid=4001&com="+code+"&nu="+nu+"&vcode=&token=&_=1468650888424";
		String json = sendGet(url);
	
		Gson gson = new Gson();
		Lgistics lgistics = gson.fromJson(json, Lgistics.class);
	    return lgistics;
	}
	
    /** 
     * 将JSONObjec对象转换成Map-List集合 
     * @param json 
     * @return 
     */  
    public static Map<String, Object> toMap(JsonObject json){  
        Map<String, Object> map = new HashMap<String, Object>();  
        Set<Entry<String, JsonElement>> entrySet = json.entrySet();  
        for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ){  
            Entry<String, JsonElement> entry = iter.next();  
            String key = entry.getKey();  
            Object value = entry.getValue();  
            if(value instanceof JsonArray)  
                map.put((String) key, toList((JsonArray) value));  
            else if(value instanceof JsonObject)  
                map.put((String) key, toMap((JsonObject) value));  
            else  
                map.put((String) key, value);  
        }  
        return map;  
    } 
    
    
    /** 
     * 将JSONArray对象转换成List集合 
     * @param json 
     * @return 
     */  
    public static List<Object> toList(JsonArray json){  
        List<Object> list = new ArrayList<Object>();  
        for (int i=0; i<json.size(); i++){  
            Object value = json.get(i);  
            if(value instanceof JsonArray){  
                list.add(toList((JsonArray) value));  
            }  
            else if(value instanceof JsonObject){  
                list.add(toMap((JsonObject) value));  
            }  
            else{  
                list.add(value);  
            }  
        }  
        return list;  
    }  
    
    
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Cache-Control ", "private");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.setRequestProperty("Cookie","Cookie: "+COOKIE); // 注入cookie （String// cookie）
            
            
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
	
	
	public static void main(String[] args) {
		Lgistics lgistics  = LgisticsUtil.getLgisticsNode("yuantong","882293292387068507");
		System.out.println(lgistics);
	}


}
