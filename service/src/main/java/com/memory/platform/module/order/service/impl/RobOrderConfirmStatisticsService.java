package com.memory.platform.module.order.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.order.RobOrderConfirm;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.module.order.dao.RobOrderConfirmStatisticsDao;
import com.memory.platform.module.order.service.IRobOrderConfirmStatisticsService;
/**
* 创 建 人： 武国庆
* 日    期： 2016年6月17日 上午10:29:50 
* 修 改 人： 
* 日   期： 
* 描   述：订单统计
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class RobOrderConfirmStatisticsService implements IRobOrderConfirmStatisticsService { 
	@Autowired
	RobOrderConfirmStatisticsDao robOrderConfirmStatisticsDao;

	@Override
	public List<Map<String, Object>> getRobOrderConfirmStatusCount(
			RobOrderConfirm robOrderConfirm) {
		return robOrderConfirmStatisticsDao.getRobOrderConfirmStatusCount(robOrderConfirm);
	}

	@Override
	public Map<String, Object> getRobOrderConfirm(Date strDate,
			Date endDate, RobOrderConfirm robOrderConfirm,SimpleDateFormat sdf1) {
		return robOrderConfirmStatisticsDao.getRobOrderConfirm(strDate, endDate, robOrderConfirm,sdf1);
	}

	@Override
	public Map<String, Object> getRobOrderConfirmWeight(Date strDate,
			Date endDate, RobOrderConfirm robOrderConfirm, SimpleDateFormat sdf1) {
		return robOrderConfirmStatisticsDao.getRobOrderConfirmWeight(strDate, endDate, robOrderConfirm, sdf1);
	}

	@Override
	public Map<String, Object> getRobOrderConfirmCount(Account account) {
		return robOrderConfirmStatisticsDao.getRobOrderConfirmCount(account);
	}

	@Override
	public List getAllConfirmMonthCount(List<String> months, Account account,
			String dateType,RobOrderRecord robOrderRecord) {
		// TODO Auto-generated method stub
		return robOrderConfirmStatisticsDao.getAllConfirmMonthCount(months, account, dateType,robOrderRecord);
	}

	@Override
	public List getConfirmCompletionMonthCount(List<String> months,
			Account account, String dateType,RobOrderRecord robOrderRecord) {
		// TODO Auto-generated method stub
		return robOrderConfirmStatisticsDao.getConfirmCompletionMonthCount(months, account, dateType,robOrderRecord);
	}

	@Override
	public List getAllConfirmMonthWeight(List<String> months, Account account,
			String dateType,RobOrderRecord robOrderRecord) {
		// TODO Auto-generated method stub
		return robOrderConfirmStatisticsDao.getAllConfirmMonthWeight(months, account, dateType,robOrderRecord);
	}

	@Override
	public List getConfirmCompletionMonthWeight(List<String> months,
			Account account, String dateType,RobOrderRecord robOrderRecord) {
		return robOrderConfirmStatisticsDao.getConfirmCompletionMonthWeight(months, account, dateType,robOrderRecord);
	}

	@Override
	public Map<String, Object> getConfirmRankingStatistics(int ranking,
			String type) {
		Map<String, Object> map_v = new HashMap<String, Object>();
		List<Map<String, Object>> list = robOrderConfirmStatisticsDao.getConfirmRankingStatistics(ranking, type);
		List<String> counts = new ArrayList<>();
		List<String> weights = new ArrayList<>();
		List<String> xAxis = new ArrayList<>();
		for (Map<String, Object> map : list) {
			xAxis.add(map.get(type).toString());
			weights.add(map.get("weight")==null?"0":map.get("weight").toString());
			counts.add(map.get("count").toString());
		}
		map_v.put("counts", counts);
		map_v.put("weights", weights);
		map_v.put("xAxis", xAxis);
		map_v.put("success", true);
		map_v.put("msg", "统计数据成功！");
		return map_v;
	}
	
	 
}
