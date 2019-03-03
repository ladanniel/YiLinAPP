package com.memory.platform.module.app.capital.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.capital.BankCard;
import com.memory.platform.entity.capital.UnionPay;
import com.memory.platform.entity.capital.BankCard.BandStatus;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.DataBaseException;
import com.memory.platform.global.AES;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.IBankCardService;
import com.memory.platform.module.capital.service.IUnionPayService;
import com.memory.platform.module.global.controller.BaseController;

/**
 * 银行卡控制器 aiqiwu 2017-09-11
 */
@Controller
@RequestMapping("app/capital/bankcard")
public class BankCardController extends BaseController {
	@Autowired
	IBankCardService bankCardService;
	@Autowired
	private IUnionPayService unionPayService;

	/**
	 * 获取用户所有绑定的银行卡列表 aiqiwu 2017-09-11
	 */
	@RequestMapping(value = "getMyBindBankCardPage", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> getMyBindBankCardPage(int start, int limit, HttpServletRequest request) {
		Account account = UserUtil.getAccount();
		Map<String, Object> map = bankCardService.getMyBindBankCardPage(account.getId(), start, limit);
		return jsonView(true, "获取我的银行卡数据成功", map);
	}

	/**
	 * 删除绑定银行卡 aiqiwu 2017-09-15
	 */
	@RequestMapping(value = "deleteBankCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBankCard(String bankCardId) {
		BankCard card = null;
		try {
			card = bankCardService.getBankCard(bankCardId);
		} catch (Exception e1) {
			return jsonView(false, REMOVE_FAIL);
		}
		if (card == null)
			return jsonView(false, "  !");
		this.bankCardService.removeBankCard(card);
		return jsonView(true, REMOVE_SUCCESS);
	}

	/**
	 * 功能描述：获取开通银行卡短信 输入参数: @param id 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期:
	 * 2017年4月10日上午11:36:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getBindBankCardSMSCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSMSCode(String cardNo, String phoneNo) throws IOException {
		Map<String, Object> map = unionPayService.sendOpenCardSMSCode(cardNo, phoneNo);
		boolean success = map == null ? false : true;
		String msg = "成功获取验证码";
		if (!success)
			msg = "获取验证码失败!";
		return jsonView(success, msg, map);
	}

	/**
	 * 检查银行卡 aiqiwu 2017-09-15
	 */
	@RequestMapping(value = "checkBankCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkBankCard(String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		BankCard tempBankCard = null;
		String msg = "";
		boolean success = false;
		try {
			tempBankCard = bankCardService.getBankCardByBankCard(AES.Encrypt(cardNo));
			if (tempBankCard != null) {
				msg = "银行卡已经被绑定";
			}
			tempBankCard = bankCardService.verify(cardNo);
			if (tempBankCard == null) {
				msg = "检查银行卡失败,未知银行卡";
			}
			map.put("bankCard", tempBankCard);
			msg = "检查银行卡成功";
			success = true;
		} catch (Exception e1) {
			msg ="检查银行卡异常";
		}
		return jsonView(success, msg, map);
	}

	/**
	 * 新增绑定银行卡 aiqiwu 2017-09-15
	 */
	@RequestMapping(value = "openQueryCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> openQueryCard(UnionPay unionPay) throws IOException {
		boolean isOpen = unionPayService.openQueryCard(unionPay);
		Account account = UserUtil.getAccount();
		Map<String, Object> map = null;
		if (isOpen) {
			// 写库
			BankCard bankCard = new BankCard();
			bankCard.setAccount(account);
			bankCard.setBandStatus(BankCard.BandStatus.defalut);
			bankCard.setOrderNo(unionPay.getOrderId());
			bankCard.setOpenBank(unionPay.getOpenBank());
			bankCard.setBankCard(unionPay.getCardNo());
			bankCard.setCreate_time(new Date());
			bankCard.setBandStatus(BandStatus.success);
			bankCard.setMobile(unionPay.getPhoneNo());
			map = bankCardService.saveBankCard(bankCard);
		} else {
			// 处理未开通逻辑
			boolean isSuccess = unionPayService.openCard(unionPay);
			if (isSuccess) {
				// 写库
				BankCard bankCard = new BankCard();
				bankCard.setAccount(account);
				bankCard.setBandStatus(BankCard.BandStatus.defalut);
				bankCard.setOrderNo(unionPay.getOrderId());
				bankCard.setOpenBank(unionPay.getOpenBank());
				bankCard.setBankCard(unionPay.getCardNo());
				bankCard.setCreate_time(new Date());
				bankCard.setBandStatus(BandStatus.success);
				bankCard.setMobile(unionPay.getPhoneNo());
				map = bankCardService.saveBankCard(bankCard);
			} else {
				// 绑定卡失败
				return jsonView(false, "绑定失败");
			}
		}
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		String msg = map.get("msg").toString();
		return jsonView(success, msg);
	}
}
