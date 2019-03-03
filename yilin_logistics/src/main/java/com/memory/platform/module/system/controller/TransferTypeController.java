package com.memory.platform.module.system.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.capital.TransferType;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ITransferTypeService;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月10日 下午2:41:09 
* 修 改 人： 
* 日   期： 
* 描   述： 转账类型控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/system/transfertype")
public class TransferTypeController extends BaseController {

	@Autowired
	private ITransferTypeService transferTypeService;
	
	/**
	* 功能描述： 列表分页
	* 输入参数:  @param transferType
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:17:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(TransferType transferType,HttpServletRequest request) {
		return this.transferTypeService.getPage(transferType, getStart(request), getLimit(request));
	}

	/**
	* 功能描述： 编辑页面
	* 输入参数:  @param model
	* 输入参数:  @param id
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:17:39
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id) {
		model.addAttribute("transferType", this.transferTypeService.getTransferType(id));
		return "/system/transfertype/edit";
	}

	/**
	* 功能描述： 添加页面
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:18:23
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add1() {
		return "/system/transfertype/add";
	}
	
	/**
	* 功能描述： 保存数据
	* 输入参数:  @param transferType
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:18:32
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(TransferType transferType) {
		if(StringUtil.isEmpty(transferType.getName())){
			throw new BusinessException("名称不能为空。");
		}
		if(transferTypeService.checkName(transferType.getName(),transferType.getCompanyType().getId()) != null){
			throw new BusinessException("该类型已存在。");
		}
		transferType.setCreate_time(new Date());
		transferTypeService.save(transferType);
		return jsonView(true, SAVE_SUCCESS);
	}

	/**
	* 功能描述： 编辑数据
	* 输入参数:  @param transferType
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:18:44
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(TransferType transferType) {
		TransferType prent = transferTypeService.getTransferType(transferType.getId());
		if(StringUtil.isEmpty(transferType.getName())){
			throw new BusinessException("名称不能为空。");
		}
		if(transferTypeService.checkName(transferType.getName(),transferType.getCompanyType().getId()) != null && !prent.getName().equals(transferType.getName())){
			throw new BusinessException("该类型已存在。");
		}
		prent.setName(transferType.getName());
		prent.setCompanyType(transferType.getCompanyType());
		transferTypeService.update(prent);
		return jsonView(true, UPDATE_SUCCESS);
	}

	/**
	* 功能描述： 删除数据
	* 输入参数:  @param ids
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:19:00
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "remove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> remove(String id) {
		try {
			this.transferTypeService.remove(id);
		} catch (Exception e) {
			throw new BusinessException("该转账类型被使用中，不能删除。");
		}
		return jsonView(true, REMOVE_SUCCESS);
	}
	
	/**
	* 功能描述： 检测名称是否存在
	* 输入参数:  @param model
	* 输入参数:  @param name
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月10日下午3:22:02
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
	@RequestMapping(value = "/checkName",method = RequestMethod.POST)
	@ResponseBody
	public boolean checkName(Model model,String name,String oldName,String transferTypeId,HttpServletRequest request) {
		TransferType transferType = transferTypeService.checkName(name,transferTypeId);
		if(null == oldName){
			oldName = "";
		}
		return transferType == null || oldName.equals(name) ? true : false;
	}
	
}
