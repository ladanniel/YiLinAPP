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

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.capital.Transfer;
import com.memory.platform.entity.capital.Transfer.Status;
import com.memory.platform.entity.capital.TransferType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.ExportExcelUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.capital.service.ICapitalAccountService;
import com.memory.platform.module.capital.service.ITransferService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月4日 下午8:40:01 
* 修 改 人： 
* 日   期： 
* 描   述： 转账控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/capital/transfer")
public class TransferController extends BaseController {

	@Autowired
	private ITransferService transferService;
	@Autowired
	private ICapitalAccountService capitalAccountService;
	@Autowired
	private IAccountService accountService;
	
	/**
	 * 
	* 功能描述： 转账列表
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:43:02
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		return "/capital/transfer/index";
	}
	
	/**
	* 功能描述： 转账页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:44:25
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		model.addAttribute("capitalAccount", capitalAccountService.getCapitalAccount(UserUtil.getUser(request).getId()));
		model.addAttribute("phone", UserUtil.getUser(request).getPhone());
		return "/capital/transfer/add";
	}
	
	/**
	* 功能描述： 分页转账列表
	* 输入参数:  @param transfer
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:47:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Transfer transfer,String transferTypeId,HttpServletRequest request) {
		transfer.setAccount(UserUtil.getUser(request));
		TransferType transferType = null;
		if(StringUtil.isNotEmpty(transferTypeId)){
			transferType = new TransferType();
			transferType.setId(transferTypeId);
		}
		transfer.setTransferType(transferType);
		return this.transferService.getPage(transfer, getStart(request), getLimit(request));
	}
	
	/**
	* 功能描述： 保存转账数据
	* 输入参数:  @param transfer
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月4日下午8:49:40
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Transfer transfer,String accountId,String payPassword,double money, HttpServletRequest request,String source) {
		Account account = UserUtil.getUser(request);
		Account toAccount = null;
		transfer.setMoney(money);
		if(StringUtil.isNotEmpty(accountId)){
			toAccount = accountService.checkAccount(accountId);
			if(null == toAccount){
				throw new BusinessException("请输入正确的收款帐户。");
			}else if(toAccount.getId().equals(account.getId())){
				throw new BusinessException("不能给自己帐户转账。");
			}
			try {
				capitalAccountService.getCapitalAccount(toAccount.getId());
			} catch (Exception e) {
				throw new BusinessException("该收款帐户还未认证不能进行转账。");
			}
		}else{
			throw new BusinessException("请输入正确的收款帐户。");
		}
		if(StringUtil.isEmpty(account.getPaypassword())){
			throw new BusinessException("请先设置支付密码。");
		}
		if(StringUtil.isEmpty(payPassword)){
			throw new BusinessException("请输入支付密码。");
		}
		if(!account.getPaypassword().equals(AppUtil.md5(payPassword))){
			throw new BusinessException("请输入正确的支付密码。");
		}
		if(transfer.getMoney() <= 0 ){
			throw new BusinessException("请输入正确的金额。");
		}
		transfer.setAccount(account);
		transfer.setToAccount(toAccount);
		String[] array = source.split("-");
		transfer.setSourceAccount(array[0] + "-" + array[1]);
		transfer.setSourcType(array[2]);
		transfer.setTradeAccount(array[1]);
		transfer.setTradeName(array[0]);
		if(StringUtil.isEmpty(transfer.getTransferType().getId())){
			throw new BusinessException("请联系管理员，初始化转账类型。");
		}
		this.transferService.saveTransfer(transfer);
		return jsonView(true, "转账成功");
		
	}
	
	/**
	* 功能描述： 验证账号
	* 输入参数:  @param model
	* 输入参数:  @param account
	* 输入参数:  @param old
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月5日下午4:31:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	@RequestMapping(value = "/checkAccount",method = RequestMethod.POST)
	@ResponseBody
	public boolean checkAccount(Model model,String account,HttpServletRequest request) {
		Account user = accountService.checkAccount(account);
		return user==null?false:true;
	}
	
	/**
	* 功能描述： 
	* 输入参数:  @param account
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月5日下午4:35:05
	* 修 改 人: 
	* 日    期: 
	* 返    回：Account
	 */
	@RequestMapping(value = "/getAccount",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccount(String account,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String,Object>();
		Account user = accountService.checkAccount(account);
		if(null != user){
			if(null == capitalAccountService.getCapitalAccount(user.getId())){
				throw new BusinessException("该收款帐户还未认证不能进行转账。");
			}
			if(user.getId().equals(UserUtil.getUser(request).getId())){
				throw new BusinessException("不能给自己帐户转账。");
			}
		}else{
			throw new BusinessException("帐户不存在。");
		}
		user.setPassword(null);
		user.setPaypassword(null);
		map.put("result", true);
		map.put("msg", user);
		return map;
	}
	
	@RequestMapping(value = "/checkToAccount",method = RequestMethod.POST)
	@ResponseBody
	public boolean checkToAccount(String accountId){
		Account user = accountService.checkAccount(accountId);
		if(null != user){
				if(null == capitalAccountService.getCapitalAccount(user.getId())){
					return false;
				}
			if(user.getId().equals(UserUtil.getUser().getId())){
				return false;
			}
		}else{
			return false;
		}
		return true;
	}
	
	/**
	* 功能描述： 易林财务转账列表
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日下午1:38:50
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/osindex", method = RequestMethod.GET)
	protected String osIndex(Model model, HttpServletRequest request) {
		return "/capital/transfer/osindex";
	}
	
	/**
	* 功能描述： 易林财务转账列表分页
	* 输入参数:  @param transfer
	* 输入参数:  @param reAccount
	* 输入参数:  @param transferTypeId
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日下午1:39:17
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getOsPage")
	@ResponseBody
	public Map<String, Object> getOsPage(Transfer transfer,String transferTypeId,HttpServletRequest request) {
		TransferType transferType = null;
		if(StringUtil.isNotEmpty(transferTypeId)){
			transferType = new TransferType();
			transferType.setId(transferTypeId);
		}
		transfer.setTransferType(transferType);
		return this.transferService.getPage(transfer, getStart(request), getLimit(request));
	}
	
	/**
	* 功能描述： 查看转账详情
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月26日下午3:02:48
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/look", method = RequestMethod.GET)
	protected String look(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", transferService.getTransferById(id));
		return "/capital/transfer/look";
	}
	
	/**
	* 功能描述：查看转账详情 
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月28日上午9:58:46
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/fontlook", method = RequestMethod.GET)
	protected String fontlook(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", transferService.getTransferById(id));
		return "/capital/transfer/fontlook";
	}
	
	/**
	* 功能描述： 导出excel
	* 输入参数:  @param request
	* 输入参数:  @param response
	* 输入参数:  @param transfer
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月28日上午9:59:52
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String exportexcel(HttpServletRequest request,HttpServletResponse response,Transfer transfer,String transferTypeId) {
		String fileName = System.currentTimeMillis() + "转账记录";
		String columnNames[] = { "转账流水号", "转账用户账号", "转账用户手机号","转账用户姓名","对方用户账号","对方用户手机号","对方用户姓名","转账类型","转账金额","转账日期","状态","备注"};
		String keys[] = { "tradeNo", "toaccount", "tophone", "toname", "account", "phone", "name", "type", "money","create_time","status","remark" };
		
		TransferType transferType = null;
		if(StringUtil.isNotEmpty(transferTypeId)){
			transferType = new TransferType();
			transferType.setId(transferTypeId);
		}
		transfer.setTransferType(transferType);
		List<Transfer> list = transferService.getList(transfer);
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
	private List<Map<String, Object>> createExcelRecord(List<Transfer> list) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);

		for (Transfer vo : list) {
			Map<String, Object> exceMap = new HashMap<String, Object>();
			exceMap.put("tradeNo", vo.getTradeNo());
			exceMap.put("account", vo.getAccount().getAccount());
			exceMap.put("phone", vo.getAccount().getPhone());
			exceMap.put("name", vo.getAccount().getName());
			exceMap.put("toaccount", vo.getToAccount().getAccount());
			exceMap.put("tophone", vo.getToAccount().getPhone());
			exceMap.put("toname", vo.getToAccount().getName());
			exceMap.put("type", vo.getTransferType().getName());
			exceMap.put("money", vo.getMoney());
			exceMap.put("create_time", vo.getCreate_time());
			String status = "";
			if(vo.getStatus().equals(Status.success)){
				status = "成功";
			}else if(vo.getStatus().equals(Status.failed)){
				status = "失败";
			}else{
				status = "待处理";
			}
			exceMap.put("status", status);
			exceMap.put("remark", vo.getRemark());
			listmap.add(exceMap);
		}
		return listmap;
	}
}
