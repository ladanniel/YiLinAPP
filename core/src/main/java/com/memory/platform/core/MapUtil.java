package com.memory.platform.core;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	public static  <K,V> HashMap<K,V> toHashMap(Map<K,V> map) {
	    HashMap<K, V> ret = new HashMap<K, V>();
	    for (K key :map.keySet()) {
			 ret.put(key, map.get(key));
		}
		return ret;
		
	}
}
