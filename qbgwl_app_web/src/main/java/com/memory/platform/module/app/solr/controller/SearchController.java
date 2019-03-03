package com.memory.platform.module.app.solr.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.solr.service.ISolrSearchService;

//@Controller
//@RequestMapping("app/search")
public class SearchController extends BaseController {
	@Autowired
	ISolrSearchService searchService;

	@RequestMapping(value = "searchRobOrderRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchRobOrderRecord(String key, RobOrderRecord robOrderRecord, int start, int size) {
		Map<String, Object> map = searchService.searchRobOrderRecord(key, robOrderRecord, start - 1, size);
		return jsonView(true, "成功查询SolrByRobOrderRecord", map);
	}

	@RequestMapping(value = "searchRobOrderConfirmRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchRobOrderConfirmRecord(String key, RobOrderConfirm robOrderConfirm, int start,
			int size) {
		Map<String, Object> map = searchService.searchRobOrderConfirmRecord(key, robOrderConfirm, start - 1, size);
		return jsonView(true, "成功查询SolrByRobOrderConfirm", map);
	}
}
