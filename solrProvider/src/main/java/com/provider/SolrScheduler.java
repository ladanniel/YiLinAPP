package com.provider;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.solr.DeleteRecord;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.solr.service.ISolrDeleteRecordService;
import com.memory.platform.module.solr.service.ISolrGoodsBasicService;
import com.memory.platform.module.solr.service.ISolrRobOrderConfirmService;
import com.memory.platform.module.solr.service.ISolrRobOrderRecordService;
import com.solr.make.AbstractSolrShedulerInfo;

public class SolrScheduler {
	Logger logger = Logger.getLogger(SolrScheduler.class);
	long rate = 100;
	boolean isClear = false;
	@Autowired
	SolrUtils solrUtils;
	@Autowired
	DruidDataSource dataSource;

	public boolean isClear() {
		return isClear;
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}

	List<AbstractSolrShedulerInfo> shedulerInfos;

	/*
	 * 需要同步到Solr的集合 在spring中配置
	 */
	public List<AbstractSolrShedulerInfo> getShedulerInfos() {
		return shedulerInfos;
	}

	public void setShedulerInfos(List<AbstractSolrShedulerInfo> shedulerInfos) {
		this.shedulerInfos = shedulerInfos;
	}

	String solrUrl = "";
	@Autowired
	MemShardStrore memStore;
	@Autowired
	ISolrGoodsBasicService goodsBasicService;
	@Autowired
	ISolrRobOrderConfirmService robOrderConfirmService;

	@Autowired
	ISolrDeleteRecordService deleteRecordService;
	@Autowired
	ISolrRobOrderRecordService robOrderRecordService;

	public String getSolrUrl() {
		return solrUrl;
	}

	public long getRate() {
		return rate;
	}

	public void setRate(long rate) {
		this.rate = rate;
	}

	ApplicationContext context;
	protected ExecutorService singleThreadPool;

	@PostConstruct
	protected void init() {
		singleThreadPool = Executors.newCachedThreadPool();

	}

	public Runnable makeRunnable() {
		return new Runnable() {

			@Override
			public void run() {

				try {
					SyncSolr();
					Thread.sleep(rate);
				} catch (Exception e) {

					e.printStackTrace();
				}
				singleThreadPool.submit(makeRunnable());
			}

		};
	}

	public void start() {
		singleThreadPool.submit(makeRunnable());
	}

	private void SyncSolr() {
		if (this.isClear()) {
			clearRecords();
			this.setClear(false);
		}
		for (AbstractSolrShedulerInfo abstractSolrShedulerInfo : shedulerInfos) {
			abstractSolrShedulerInfo.doSomthing();
		}
		deleteRecord();
	}

	public void clearRecords() {
		try {
			for (AbstractSolrShedulerInfo abstractSolrShedulerInfo : shedulerInfos) {
				SolrClient client = solrUtils
						.getClient(abstractSolrShedulerInfo.getSolrName());
				client.deleteByQuery("*");
				client.commit();
				memStore.getClient().hdel("solrTimeStamp",abstractSolrShedulerInfo.getSolrName() );
				 
			}
			System.out.println("清除记录成功");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * RobOrderConfirm和GoodsBasic刪除记录
	 */
	private void deleteRecord() {

		do {

			try {

				List<DeleteRecord> listDeleteRecords = deleteRecordService
						.getDeleteRecordList();
				if (listDeleteRecords == null || listDeleteRecords.size() == 0) {
					break;
				}
				HashMap<String, ArrayList<String>> solrIds = new HashMap<String, ArrayList<String>>();
				for (AbstractSolrShedulerInfo shedulerInfo : shedulerInfos) {
					solrIds.put(shedulerInfo.getSolrName(),
							new ArrayList<String>());
				}
				for (DeleteRecord deleteRecord : listDeleteRecords) {
					ArrayList<String> lst = solrIds.get(deleteRecord
							.getSolr_name());
					lst.add(deleteRecord.getBusiness_id());
				}
				for (Entry<String, ArrayList<String>> deleteSolr : solrIds
						.entrySet()) {
					SolrClient solrClient = solrUtils.getClient(deleteSolr
							.getKey());
					solrClient.deleteById(deleteSolr.getValue());
					solrClient.commit();
					logger.info("删除" + deleteSolr.getKey() + "成功 "
							+ deleteSolr.getValue().size());
				}

			} catch (Exception exception) {

			}
		} while (false);

	}

}
