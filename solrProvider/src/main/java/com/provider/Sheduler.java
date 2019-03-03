package com.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.mail.search.DateTerm;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ctc.wstx.util.StringUtil;
import com.memory.platform.core.ArrayUtil;
import com.memory.platform.core.SolrUtils;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.solr.DeleteRecord;
import com.memory.platform.global.UserUtil;
import com.memory.platform.memStore.MemShardStrore;
import com.memory.platform.module.solr.service.ISolrDeleteRecordService;
import com.memory.platform.module.solr.service.ISolrGoodsBasicService;
import com.memory.platform.module.solr.service.ISolrRobOrderConfirmService;
import com.memory.platform.module.solr.service.ISolrRobOrderRecordService;
import com.memory.platform.module.solr.service.impl.SolrDeleteRecordService;
 

import com.mysql.fabric.xmlrpc.base.Array;

import antlr.StringUtils;

@Component
public class Sheduler implements ApplicationContextAware {
	long rate = 100;
    boolean isClear =false;
    @Autowired
    SolrUtils solrUtils;
	public boolean isClear() {
		return isClear;
	}

	public void setClear(boolean isClear) {
		this.isClear = isClear;
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;

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
		if(this.isClear()) {
			clearRecords();
			this.setClear(false);
		}
		
		uploadGoodsBasic();
		uploadRobOrderRecord();
		uploadRobOrderConfirm();
		deleteRecord();
	}

	public void clearRecords() {
		try {
			SolrClient client = solrUtils.getClient(solrUtils.goodsBasic);
			client.deleteByQuery("*");
			client.commit();
			client = solrUtils.getClient(solrUtils.robOrderConfirm);
			client.deleteByQuery("*");
			client.commit();
			client = solrUtils.getClient(solrUtils.robOrderRecord);
			client.deleteByQuery("*");
			client.commit();
			memStore.getClient().hdel("goodsBasicTimeStamp","solrTimeStamp");
			memStore.getClient().hdel("robOrderRecordTimeStamp","solrTimeStamp");
			memStore.getClient().hdel("robOrderConfirmTimeStamp","solrTimeStamp");
			System.out.println("清除记录成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 上传GoodBasic记录
	 */
	private void uploadGoodsBasic() {
		String strTimeStamp = memStore.getClient().hget("goodsBasicTimeStamp",
				"solrTimeStamp");
		long time = 0;

		if (com.memory.platform.global.StringUtil.isNotEmpty(strTimeStamp)) {
			time = Long.parseLong(strTimeStamp);
		}
		Date date = new Date(time);
		List<GoodsBasic> listGoodsBasic = goodsBasicService
				.getGoodsBasicListWithTimestamp(date);
		if(listGoodsBasic==null || listGoodsBasic.size() == 0) {
			return; 
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrClient client = solrUtils.getClient(solrUtils.goodsBasic);
		for (GoodsBasic gb : listGoodsBasic) {
			if (gb.getTimeStamp() == null)
				continue;
			if (gb.getTimeStamp().getTime() > time) {
				time = gb.getTimeStamp().getTime();
			}
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", gb.getId());
			doc.addField("delivery_area_name", gb.getDeliveryAreaName());
			doc.addField("consignee_address", gb.getConsigneeAddress());
			doc.addField("consignee_name", gb.getConsigneeName());
			doc.addField("company_name", gb.getCompanyName());
			doc.addField("stock_type_names", gb.getStockTypeNames());
			doc.addField("delivery_name", gb.getDeliveryName());
			doc.addField("consignee_area_name", gb.getConsigneeAreaName());
			doc.addField("account_id",gb.getAccount().getId());
			doc.addField("status",gb.getStatus().ordinal());
			doc.addField("total_price", gb.getTotalPrice());
			doc.addField("unit_price", gb.getUnitPrice());
			doc.addField("embark_weight", gb.getEmbarkWeight());
			doc.addField("total_weight", gb.getTotalWeight());
			docs.add(doc);
		}
		try {
			if (docs.size() > 0) {
				client.add(docs);
				memStore.getClient().hset("goodsBasicTimeStamp",
						"solrTimeStamp", time + "");
				System.out.println("同步GoodsBasicSolr");
			}
			client.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 上传RobOrderRecord记录
	 */
	private void uploadRobOrderRecord() {
		String strTimeStamp = memStore.getClient().hget(
				"robOrderRecordTimeStamp", "solrTimeStamp");
		long time = 0;
		if (com.memory.platform.global.StringUtil.isNotEmpty(strTimeStamp)) {
			time = Long.parseLong(strTimeStamp);
		}
		Date date = new Date(time);
		List<Map<String, Object>> listRobOrderRecord = robOrderRecordService
				.getSolrRobOrderRecordByTimeStamp(date);
		if(listRobOrderRecord==null || listRobOrderRecord.size() ==0 ) {
			return ;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (Map<String, Object> ret : listRobOrderRecord) {
			 Date timeStamp =(Date) ret.get("time_stamp");
			 if(timeStamp.getTime()>time  ) {
				 time  = timeStamp.getTime();
			 }
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", ret.get("id"));
				doc.addField("rob_order_no", ret.get("rob_order_no"));
				doc.addField("delivery_area_name", ret.get("delivery_area_name"));
				doc.addField("delivery_coordinate", ret.get("delivery_coordinate"));
				doc.addField("consignee_area_name", ret.get("consignee_area_name"));
				doc.addField("consignee_coordinate", ret.get("consignee_coordinate"));
				doc.addField("is_long_time", ret.get("is_long_time"));
				doc.addField("finite_time", ret.get("finite_time"));
				doc.addField("company_name", ret.get("company_name"));
				doc.addField("goods_type_name", ret.get("goods_type_name"));
				doc.addField("weight", ret.get("weight"));
				doc.addField("unit_price", ret.get("unit_price"));
				doc.addField("total_price", ret.get("total_price"));
				doc.addField("status", ret.get("status"));
				doc.addField("delivery_name", ret.get("delivery_name"));
				doc.addField("consignee_name", ret.get("consignee_name"));
				doc.addField("goods_total_weight", ret.get("goods_total_weight"));
				doc.addField("map_kilometer", ret.get("map_kilometer"));
				doc.addField("rob_order_time", ret.get("rob_order_time"));
				doc.addField("goods_basic_create_time", ret.get("goods_basic_create_time"));
				doc.addField("goods_unit_price", ret.get("goods_unit_price"));
				doc.addField("account_id", ret.get("account_id"));
				docs.add(doc);
		}
	
 		SolrClient client = solrUtils.getClient(solrUtils.robOrderRecord);
//		for (RobOrderRecord gb : listRobOrderRecord) {
//			if (gb.getTimeStamp() == null)
//				continue;
//			if (gb.getTimeStamp().getTime() > time) {
//				time = gb.getTimeStamp().getTime();
//			}
//			//查询记录数据
//			Map<String,Object> ret = robOrderRecordService.getSolrRobOrderRecordById(gb.getId());
//			if(ret != null) {
//				SolrInputDocument doc = new SolrInputDocument();
//				doc.addField("id", ret.get("id"));
//				doc.addField("rob_order_no", ret.get("rob_order_no"));
//				doc.addField("delivery_area_name", ret.get("delivery_area_name"));
//				doc.addField("delivery_coordinate", ret.get("delivery_coordinate"));
//				doc.addField("consignee_area_name", ret.get("consignee_area_name"));
//				doc.addField("consignee_coordinate", ret.get("consignee_coordinate"));
//				doc.addField("is_long_time", ret.get("is_long_time"));
//				doc.addField("finite_time", ret.get("finite_time"));
//				doc.addField("company_name", ret.get("company_name"));
//				doc.addField("goods_type_name", ret.get("goods_type_name"));
//				doc.addField("weight", ret.get("weight"));
//				doc.addField("unit_price", ret.get("unit_price"));
//				doc.addField("total_price", ret.get("total_price"));
//				doc.addField("status", ret.get("status"));
//				doc.addField("delivery_name", ret.get("delivery_name"));
//				doc.addField("consignee_name", ret.get("consignee_name"));
//				doc.addField("goods_total_weight", ret.get("goods_total_weight"));
//				doc.addField("map_kilometer", ret.get("map_kilometer"));
//				doc.addField("rob_order_time", ret.get("rob_order_time"));
//				doc.addField("goods_basic_create_time", ret.get("goods_basic_create_time"));
//				doc.addField("goods_unit_price", ret.get("goods_unit_price"));
//				doc.addField("account_id", ret.get("account_id"));
//				docs.add(doc);
//			}
//		}
		try {
			if (docs.size() > 0) {
				client.add(docs);
				memStore.getClient().hset("robOrderRecordTimeStamp",
						"solrTimeStamp", time + "");
				System.out.println("同步RobOrderRecordSolr");
			}
			client.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 上传RobOrderConfirm记录
	 */
	private void uploadRobOrderConfirm() {
		String strTimeStamp = memStore.getClient().hget(
				"robOrderConfirmTimeStamp", "solrTimeStamp");
		long time = 0;

		if (com.memory.platform.global.StringUtil.isNotEmpty(strTimeStamp)) {
			time = Long.parseLong(strTimeStamp);
		}
		Date date = new Date(time);
		List<RobOrderConfirm> listRobOrderConfirm = robOrderConfirmService
				.getRobOrderConfirmListWithTimestamp(date);
	
		if(listRobOrderConfirm.size() == 0) {
			return ;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrClient client = solrUtils.getClient(solrUtils.robOrderConfirm);
		List<String > ids = new ArrayList<String>(listRobOrderConfirm.size());
		
		for (RobOrderConfirm confirm :listRobOrderConfirm) {
			ids.add(confirm.getId());
			if(confirm.getTimeStamp().getTime() > time) {
				time = confirm.getTimeStamp().getTime();
			}
		}
		//优化一次性查询结束 一下查询多个比一个一个查询快
		List<Map<String, Object>> datas =  robOrderConfirmService.getSolrRoborderConfirmListByTimeStamp(date);
		for (Map<String, Object> ret : datas) {
			
 
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", ret.get("id"));
			doc.addField("status", ret.get("status"));
			doc.addField("goods_basic_id", ret.get("goods_basic_id"));
			doc.addField("delivery_address", ret.get("delivery_address"));
			doc.addField("delivery_area_name", ret.get("delivery_area_name"));
			doc.addField("delivery_mobile", ret.get("delivery_mobile"));
			doc.addField("delivery_name", ret.get("delivery_name"));
			doc.addField("delivery_coordinate", ret.get("delivery_coordinate"));
			doc.addField("consignee_address", ret.get("consignee_address"));
			doc.addField("consignee_area_name", ret.get("consignee_area_name"));
			doc.addField("consignee_coordinate", ret.get("consignee_coordinate"));
			doc.addField("consignee_name", ret.get("consignee_name"));
			doc.addField("consignee_mobile", ret.get("consignee_mobile"));
			doc.addField("map_kilometer", ret.get("map_kilometer"));
			doc.addField("goods_basic_create_time", ret.get("goods_basic_create_time"));
			doc.addField("goods_basic_update_time", ret.get("goods_basic_update_time"));
			doc.addField("goods_basic_unit_price", ret.get("goods_basic_unit_price"));
			doc.addField("goods_basic_total_price", ret.get("goods_basic_total_price"));
			doc.addField("total_weight", ret.get("total_weight"));
			doc.addField("goods_basic_company_name", ret.get("goods_basic_company_name"));
			doc.addField("goods_basic_remark", ret.get("goods_basic_remark"));
			doc.addField("rob_order_create_time", ret.get("rob_order_create_time"));
			doc.addField("rob_order_unit_price", ret.get("rob_order_unit_price"));
			doc.addField("goods_type_name", ret.get("goods_type_name"));
			doc.addField("transportation_cost", ret.get("transportation_cost"));
			doc.addField("payment", ret.get("payment"));
			doc.addField("actual_weight", ret.get("actual_weight"));
			doc.addField("additional_cost", ret.get("additional_cost"));
			doc.addField("total_cost", ret.get("total_cost"));
			doc.addField("count", ret.get("count"));
			doc.addField("robbed_account_id", ret.get("robbed_account_id"));
			doc.addField("account_id", ret.get("account_id"));
			doc.addField("turck_user_id", ret.get("turck_user_id"));
			doc.addField("lock_status", ret.get("lock_status"));
			doc.addField("special_status_sucess", ret.get("special_status_sucess"));
			doc.addField("special_status", ret.get("special_status"));
			docs.add(doc);
		}
//		for (RobOrderConfirm roc : listRobOrderConfirm) {
//			if (roc.getTimeStamp() == null)
//				continue;
//			if (roc.getTimeStamp().getTime() > time) {
//				time = roc.getTimeStamp().getTime();
//			}
//			//查询记录数据
//			Map<String,Object> ret = robOrderConfirmService.getSolrRobOrderConfirmById(roc.getId());
//			if(ret != null) {
//				SolrInputDocument doc = new SolrInputDocument();
//				doc.addField("id", ret.get("id"));
//				doc.addField("status", ret.get("status"));
//				doc.addField("goods_basic_id", ret.get("goods_basic_id"));
//				doc.addField("delivery_address", ret.get("delivery_address"));
//				doc.addField("delivery_area_name", ret.get("delivery_area_name"));
//				doc.addField("delivery_mobile", ret.get("delivery_mobile"));
//				doc.addField("delivery_name", ret.get("delivery_name"));
//				doc.addField("delivery_coordinate", ret.get("delivery_coordinate"));
//				doc.addField("consignee_address", ret.get("consignee_address"));
//				doc.addField("consignee_area_name", ret.get("consignee_area_name"));
//				doc.addField("consignee_coordinate", ret.get("consignee_coordinate"));
//				doc.addField("consignee_name", ret.get("consignee_name"));
//				doc.addField("consignee_mobile", ret.get("consignee_mobile"));
//				doc.addField("map_kilometer", ret.get("map_kilometer"));
//				doc.addField("goods_basic_create_time", ret.get("goods_basic_create_time"));
//				doc.addField("goods_basic_update_time", ret.get("goods_basic_update_time"));
//				doc.addField("goods_basic_unit_price", ret.get("goods_basic_unit_price"));
//				doc.addField("goods_basic_total_price", ret.get("goods_basic_total_price"));
//				doc.addField("total_weight", ret.get("total_weight"));
//				doc.addField("goods_basic_company_name", ret.get("goods_basic_company_name"));
//				doc.addField("goods_basic_remark", ret.get("goods_basic_remark"));
//				doc.addField("rob_order_create_time", ret.get("rob_order_create_time"));
//				doc.addField("rob_order_unit_price", ret.get("rob_order_unit_price"));
//				doc.addField("goods_type_name", ret.get("goods_type_name"));
//				doc.addField("transportation_cost", ret.get("transportation_cost"));
//				doc.addField("payment", ret.get("payment"));
//				doc.addField("actual_weight", ret.get("actual_weight"));
//				doc.addField("additional_cost", ret.get("additional_cost"));
//				doc.addField("total_cost", ret.get("total_cost"));
//				doc.addField("count", ret.get("count"));
//				doc.addField("robbed_account_id", ret.get("robbed_account_id"));
//				doc.addField("account_id", ret.get("account_id"));
//				doc.addField("turck_user_id", ret.get("turck_user_id"));
//				docs.add(doc);
//			}
//		}
		try {
			if (docs.size() > 0) {
				client.add(docs);
				memStore.getClient().hset("robOrderConfirmTimeStamp",
						"solrTimeStamp", time + "");
				System.out.println("同步RobOrderConfirmSolr");
			}
			client.commit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * RobOrderConfirm和GoodsBasic刪除记录
	 */
	private void deleteRecord() {
		SolrClient client = null;
		List<DeleteRecord> listDeleteRecord = deleteRecordService
				.getDeleteRecordList();
		if (listDeleteRecord.size() <= 0)
			return;
		List<String> robOrderConfirmIds = new ArrayList<String>();
		List<String> goodsBasicIds = new ArrayList<String>();
		for (DeleteRecord dr : listDeleteRecord) {
			if (com.memory.platform.global.StringUtil.isEmpty(dr
					.getRobOrderConfirmId())
					&& com.memory.platform.global.StringUtil.isEmpty(dr
							.getGoodsBasicId()))
				continue;
			if (!com.memory.platform.global.StringUtil.isEmpty(dr
					.getRobOrderConfirmId())) {
				robOrderConfirmIds.add(dr.getRobOrderConfirmId());
			} else if (!com.memory.platform.global.StringUtil.isEmpty(dr
					.getGoodsBasicId())) {
				goodsBasicIds.add(dr.getGoodsBasicId());
			}
		}
		try {
			if (robOrderConfirmIds.size() > 0) {
				client = solrUtils.getClient(solrUtils.robOrderConfirm);
				client.deleteById(robOrderConfirmIds);
				client.commit();
			}
			if (goodsBasicIds.size() > 0) {
				client = solrUtils.getClient(solrUtils.goodsBasic);
				client.deleteById(goodsBasicIds);
				client.commit();

			}
			for (DeleteRecord record : listDeleteRecord) {
				deleteRecordService.removeRecord(record);
				System.out.println("删除DeleteReocrd记录,id为:" + record.getId());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Search() {
		SolrQuery params = new SolrQuery();
		params.set("q", "id:id");
		params.set("start", 0);
		params.set("rows", 10);
		SolrClient client = solrUtils.getClient(solrUtils.robOrderConfirm);
		try {
			SolrDocumentList docs = client.query(params).getResults();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 提交并退出
	 */
	private void commitAndCloseSolr(SolrClient client) {
		try {
			client.commit();
			client.close();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
