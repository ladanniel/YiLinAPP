package com.memory.platform.module.capital.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.MoneyRecord.Type;
import com.memory.platform.global.ExportExcelUtil;
import com.memory.platform.module.capital.service.IMoneyRecordService;
import com.memory.platform.module.global.controller.BaseController;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月25日 下午3:14:29 
* 修 改 人： 
* 日   期： 
* 描   述： 交易记录控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/capital/traderecord")
public class TradeRecordController extends BaseController {

	@Autowired
	private IMoneyRecordService moneyRecordService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/capital/traderecord/index";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(MoneyRecord moneyRecord,HttpServletRequest request) {
		return this.moneyRecordService.getPage(moneyRecord, getStart(request), getLimit(request));
	}
	
	/**
	* 功能描述： 查看详情
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月25日下午4:51:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", moneyRecordService.getMoneyRecordById(id));
		return "/capital/traderecord/look";
	}
	
	/**
	* 功能描述： excel导出
	* 输入参数:  @param request
	* 输入参数:  @param response
	* 输入参数:  @param moneyRecord
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月28日上午10:34:57
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String exportexcel(HttpServletRequest request,HttpServletResponse response,MoneyRecord moneyRecord) {
		String fileName = System.currentTimeMillis() + "交易记录";
		String columnNames[] = { "用户名", "用户手机号","用户姓名","交易方式类型","交易来源名称（银行或支付平台）","交易来源账号（卡号或支付账号）","类型","交易金额","交易日期"};
		String keys[] = { "account", "phone", "name", "sourceType", "tradeName", "tradeAccount", "type", "money","create_time" };
		
		List<MoneyRecord> list = moneyRecordService.getList(moneyRecord);
		List<Map<String, Object>> listmap = createExcelRecord(list);

		ExportExcelUtil.createExcel(request, response, fileName, list, listmap,columnNames, keys);
		return null;
	}
	
	/**
	* 功能描述： list转换map
	* 输入参数:  @param list
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月27日下午5:26:11
	* 修 改 人: 
	* 日    期: 
	* 返    回：List<Map<String,Object>>
	 */
	private List<Map<String, Object>> createExcelRecord(List<MoneyRecord> list) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);

		for (MoneyRecord vo : list) {
			Map<String, Object> exceMap = new HashMap<String, Object>();
			exceMap.put("account", vo.getAccount().getAccount());
			exceMap.put("phone", vo.getAccount().getPhone());
			exceMap.put("name", vo.getAccount().getName());
			exceMap.put("sourceType", vo.getSourceType());
			exceMap.put("tradeName", vo.getTradeName());
			exceMap.put("tradeAccount", vo.getTradeAccount());
			exceMap.put("type", vo.getType());
			String type = "";
			if(vo.getType().equals(Type.recharge)){
				type = "充值";
			}else if(vo.getType().equals(Type.frozen)){
				type = "冻结";
			}else if(vo.getType().equals(Type.cash)){
				type = "提现";
			}else if(vo.getType().equals(Type.transfer)){
				type = "转账";
			}else if(vo.getType().equals(Type.fee)){
				type = "手续费";
			}else if(vo.getType().equals(Type.consume)){
				type = "消费";
			}else if(vo.getType().equals(Type.income)){
				type = "收款";
			}else{
				type = "其它";
			}
			exceMap.put("type", type);
			exceMap.put("money", vo.getMoney());
			exceMap.put("create_time", vo.getCreate_time());
			listmap.add(exceMap);
		}
		return listmap;
	}
}
