package com.memory.platform.module.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.IBankCardService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("bankCardDirective")
public class BankCardDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "bankCards";
	private static final String CARD_VIST = "cardVist";
	@Autowired
	private IBankCardService bankCardService;

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if (params.containsKey("split") == false) {
			Account account = UserUtil.getUser();
			List<BankCard> list = bankCardService.getAll(account.getId());
			setLocalVariable(VARIABLE_NAME, list, env, body);
		} else {
			String splitCard = params.get("split").toString();
			String visitCard = "";
			for (int i = 0; i < splitCard.length(); i++) {
				if (i < 3) {
					visitCard += splitCard.substring(i, i + 1);
				} else if (i > splitCard.length() - 3) {
					visitCard += splitCard.substring(i, i + 1);
				} else {
					visitCard += "*";
				}
			}
			setLocalVariable(CARD_VIST, visitCard, env, body);
		}
	}

}
