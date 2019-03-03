package com.memory.platform.module.solr.service;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

public interface ISolrService {

	HttpSolrClient getClient(String type);

}
