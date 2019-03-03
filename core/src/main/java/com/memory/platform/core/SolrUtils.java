package com.memory.platform.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

 

public class SolrUtils {
	String solrUrl;

	public String getSolrUrl() {
		return solrUrl;
	}

	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}

	public static final String goodsBasic = "goodsBasic";
	public static final String robOrderConfirm = "robOrderConfirm";
	public static final String robOrderRecord = "robOrderRecord";
	
	static ThreadLocal<Map<String, HttpSolrClient>> threadLocal = new ThreadLocal<>();

 
	public HttpSolrClient getClient(String type) {
		if (com.memory.platform.global.StringUtil.isEmpty(type)) {
			type = goodsBasic;
		}
		String url = solrUrl + type;
		Map<String, HttpSolrClient> map = threadLocal.get();
		if (threadLocal.get() == null) {
			synchronized (SolrUtils.class) {
				map = threadLocal.get();
				if (map == null) {
					map = new HashMap<String, HttpSolrClient>();
					threadLocal.set(map);
				}
			}
		}
		if (map.containsKey(url)) {
			return map.get(url);
		}
		synchronized (SolrUtils.class) {
			if (map.containsKey(url) == false) {
				map.put(url, new HttpSolrClient(url));
			}
		}
		return map.get(url);
	}
}
