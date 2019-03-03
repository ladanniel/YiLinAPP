package com.memory.platform.module.solr.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.id.SequenceIdentityGenerator.Delegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.memory.platform.core.SolrUtils;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.order.service.IRobOrderRecordService;
import com.memory.platform.module.solr.service.ISolrSearchService;
import com.memory.platform.module.solr.service.ISolrService;
import com.memory.platform.solr.SolrStatementUtils;

public class SolrSearchService implements ISolrSearchService {
	@Autowired
	SolrUtils solrUtils;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;
	@Autowired
	IRobOrderRecordService robOrderRecordService;// 抢单业务接口

	@Override
	public Map<String, Object> searchRobOrderRecord(String key, RobOrderRecord robOrderRecord, int start, int size) {
		Map<String, Object> ret = null;
		SolrQuery params = new SolrQuery();
		@SuppressWarnings("static-access")
		List<RobOrderRecord.Status> listStatus = RobOrderRecord
				.getStatusWithQueryStatus(robOrderRecord.getQueryStatus());
		List<String> listStrStatus = new ArrayList<>();
		for (RobOrderRecord.Status status : listStatus) {
			listStrStatus.add(status.toString());
		}
		String statusQuery = SolrStatementUtils.generateOrQueryByValuesWithField("status", listStrStatus);
		if (StringUtil.isEmpty(key))
			key = "*:* && status:" + statusQuery;
		else
			key = "keyWord:" + key + " && status:" + statusQuery;
		params.set("q", key);
		params.set("start", start);
		params.set("rows", size);
		SolrClient client = solrUtils.getClient(SolrUtils.robOrderRecord);
		try {
			SolrDocumentList docs = client.query(params).getResults();
			String ids = "";
			for (SolrDocument sd : docs) {
				String goodsBasicId = sd.getFieldValue("id").toString();
				ids += goodsBasicId + ",";
				System.out.println(goodsBasicId);
			}
			if (ids.length() > 0) {
				ids = ids.substring(0, ids.length() - 1);
				Account account = UserUtil.getAccount();
				ret = robOrderRecordService.getSolrRobOrderRecordByIds(ids, account, start, size);
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Map<String, Object> searchRobOrderConfirmRecord(String key, RobOrderConfirm robOrderConfirm, int start,
			int size) {
		Map<String, Object> ret = null;
		SolrQuery params = new SolrQuery();
		@SuppressWarnings("static-access")
		List<RobOrderConfirm.Status> listStatus = robOrderConfirm
				.getStatusWithQueryStatus(robOrderConfirm.getQueryStatus());
		List<String> listStrStatus = new ArrayList<>();
		for (RobOrderConfirm.Status status : listStatus) {
			listStrStatus.add(status.toString());
		}
		String statusQuery = SolrStatementUtils.generateOrQueryByValuesWithField("status", listStrStatus);
		if (StringUtil.isEmpty(key))
			key = "*:* && status:" + statusQuery;
		else
			key = "keyWord:" + key + " && status:" + statusQuery;
		Account account = UserUtil.getAccount();
		String roleName = account.getCompany().getCompanyType().getName();
		if ("货主".equals(roleName)) {
			key += " && robbed_account_id:" + account.getId();
		} else if ("车队".equals(roleName) || "个人司机".equals(roleName)) {
			key += " (&& account_id:" + account.getId() + " || turck_user_id:" + account.getId() + ")";
		}
		params.set("q", key);
		params.set("start", start);
		params.set("rows", size);
		SolrClient client = solrUtils.getClient(SolrUtils.robOrderConfirm);
		try {
			SolrDocumentList docs = client.query(params).getResults();
			String ids = "";
			for (SolrDocument sd : docs) {
				String robOrderConfirmId = sd.getFieldValue("id").toString();
				ids += robOrderConfirmId + ",";
				System.out.println(robOrderConfirmId);
			}
			// 找出数据，组织回发
			
			if (ids.length() > 0) {
				ids = ids.substring(0, ids.length() - 1);
				ret = robOrderConfirmService.getSolrRobOrderConfirmByIds(ids, account, start, size);
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}