package com.memory.platform.module.capital.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ITradeSequenceService;
import com.memory.platform.module.global.controller.BaseController;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月15日 下午4:48:48 
* 修 改 人： 
* 日   期： 
* 描   述： 转账序列控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/capital/tradesequence")
public class TradeSequenceController extends BaseController {

	@Autowired
	private ITradeSequenceService tradeSequenceService;
	
	/**
	* 功能描述： 序列列表
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午5:29:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		TradeSequence tradeSequence = new TradeSequence();
		tradeSequence.setAccount(UserUtil.getUser(request));
		List<TradeSequence> list = tradeSequenceService.getAll(tradeSequence);
		model.addAttribute("list", list);
		return "/capital/tradesequence/index";
	}
	
	/**
	* 功能描述： 交易序列排序
	* 输入参数:  @param id
	* 输入参数:  @param sort
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月16日下午6:06:03
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/updateSequence")
	@ResponseBody
	public Map<String, Object> updateSequence(String id,String sort, HttpServletRequest request){
		if(StringUtil.isEmpty(sort) || StringUtil.isEmpty(id)){
			throw new BusinessException("数据错误。");
		}
		TradeSequence tradeSequence = new TradeSequence();
		tradeSequence.setAccount(UserUtil.getUser(request));
		if(sort.equals("up")){
			TradeSequence tradeSequence1 = tradeSequenceService.getTradeSequence(id);
			tradeSequence.setSequenceNo(tradeSequence1.getSequenceNo() - 1);
			TradeSequence tradeSequence2 = tradeSequenceService.getTradeSequenceByNo(tradeSequence);
			tradeSequence1.setSequenceNo(tradeSequence1.getSequenceNo() - 1);
			tradeSequence2.setSequenceNo(tradeSequence2.getSequenceNo() + 1);
			tradeSequenceService.updateSequence(tradeSequence1, tradeSequence2);
		}else{
			TradeSequence tradeSequence1 = tradeSequenceService.getTradeSequence(id);
			tradeSequence.setSequenceNo(tradeSequence1.getSequenceNo() + 1);
			TradeSequence tradeSequence2 = tradeSequenceService.getTradeSequenceByNo(tradeSequence);
			tradeSequence1.setSequenceNo(tradeSequence1.getSequenceNo() + 1);
			tradeSequence2.setSequenceNo(tradeSequence2.getSequenceNo() - 1);
			tradeSequenceService.updateSequence(tradeSequence1, tradeSequence2);
		}
		return jsonView(true, "设置成功。");
	}
}
