package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.capital.TradeSequence;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.FreemarkerUtils;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ITradeSequenceService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月17日 上午10:14:50 
* 修 改 人： 
* 日   期： 
* 描   述： 交易序列模版工具类
* 版 本 号：  V1.0
 */
@Component("tradeSequenceDirective")
public class TradeSequenceDirective extends BaseDirective {

	@Autowired
	private ITradeSequenceService tradeSequenceService;
	
	private static final String VARIABLE_NAME = "tradeSequences";
	private static final String PARAMS_CASH = "cash";
	private static final String PARAMS_RECHARGE = "recharge";
	private static final String PARAMS_TRADE = "trade";
	
	@Override
	@SuppressWarnings("unchecked")
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Account account = UserUtil.getUser();
		String cash = FreemarkerUtils.getParameter(PARAMS_CASH, String.class, params);
		String recharge = FreemarkerUtils.getParameter(PARAMS_RECHARGE, String.class, params);
		String trade = FreemarkerUtils.getParameter(PARAMS_TRADE, String.class, params);
		TradeSequence tradeSequence = new TradeSequence();
		if(StringUtil.isNotEmpty(cash)){
			tradeSequence.setHasCash(cash.equals("1") ? true : false);
		}
		if(StringUtil.isNotEmpty(recharge)){
			tradeSequence.setHasRecharge(recharge.equals("1") ? true : false);
		}
		if(StringUtil.isNotEmpty(trade)){
			tradeSequence.setHasTransfer(trade.equals("1") ? true : false);
		}
		tradeSequence.setAccount(account);
		List<TradeSequence> list = tradeSequenceService.getAll(tradeSequence);
		setLocalVariable(VARIABLE_NAME, list, env, body);
	}

}
