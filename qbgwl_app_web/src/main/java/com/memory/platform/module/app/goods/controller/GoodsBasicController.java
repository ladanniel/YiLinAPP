package com.memory.platform.module.app.goods.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.visitor.functions.Nil;
import com.alibaba.dubbo.rpc.RpcContext;
import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.own.service.IGoodsBasicOwnService;
import com.memory.platform.module.own.service.impl.GoodsBasicOwnService;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年8月7日 上午11:53:49 修 改 人： 日 期： 描 述： 货源信息接口控制器 版 本
 * 号： V1.0
 */
@Controller
@RequestMapping("app/goods/")
public class GoodsBasicController extends BaseController {

	
	IGoodsBasicService goodsBasicService;
	
	public IGoodsBasicService getGoodsBasicService() {
		return goodsBasicService;
	}
	@Autowired
	public void setGoodsBasicService(IGoodsBasicService goodsBasicService) {
		this.goodsBasicService = goodsBasicService;
	}

	@Autowired
	IGoodsBasicOwnService goodsBasicOwnService;
	/**
	 * 功能描述： 分页查询货源信息 输入参数: @param goodsBasic 查询条件：目前有发货地区，收获地区 输入参数: @param
	 * start 起始页数 输入参数: @param size 查询数据条数 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月8日上午10:04:21 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getGoodsBasic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGoodsBasicListMap(GoodsBasic goodsBasic, int start, int size,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = goodsBasicService.getGoodsBasicPageForMap(goodsBasic, account, start, size);
	
		return jsonView(true, "货源信息查询成功!", map );
	}

	/**
	 * 功能描述： 分页查询货源信息 输入参数: @param goodsBasic 查询条件：目前有发货地区，收获地区 输入参数: @param
	 * start 起始页数 输入参数: @param size 查询数据条数 输入参数: @param headers 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月8日上午10:04:21 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getMyGoodsBasicPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMyGoodsBasicPage(GoodsBasic goodsBasic, int start, int size,
			@RequestHeader HttpHeaders headers, HttpServletRequest request) {
		Account account = (Account) request.getAttribute(ACCOUNT);
		Map<String, Object> map = goodsBasicService.getMyGoodsPage(goodsBasic, account, start-1, size);
	
		return jsonView(true, "货源信息查询成功!", map );
	}
	
	/**
	 * 功能描述： 查询货物详细信息 输入参数: @param goodsBasicId 输入参数: @param headers
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月8日下午1:45:13 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "getGoodsBasicById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGoodsBasicById(String goodsBasicId, @RequestHeader HttpHeaders headers,
			HttpServletRequest request) {
		Map<String, Object> map = goodsBasicService.getGoodsBasicByIdForMap(goodsBasicId);
		Map<String, Object> own = goodsBasicOwnService.getMyOwnWithGoodsBasicID(goodsBasicId);
		return jsonView(true, "货源详细信息查询成功!", map,own);
	}

	/**
	 * 功能描述： 输入参数: @param goodsBasic 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月15日上午9:44:02 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/saveSubGoodsBasicDetail")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> saveSubGoodsBasicDetail(GoodsBasic goodsBasic, HttpServletRequest request) {
		String msg = "";
		try {

			if (StringUtil.isEmpty(goodsBasic.getStockTypeNames()) || StringUtil.isEmpty(goodsBasic.getStockTypes())) {
				throw new BusinessException("请选择载货车辆类型，再执行审核功能。");
			}
			 
			goodsBasicService.saveSubGoodsBasicDetail(goodsBasic);
			if (goodsBasic.getStatus().toString().equals("save")) {
				msg = "货物信息保存成功！";
			} else if (goodsBasic.getStatus().toString().equals("apply")) {
				msg = "恭喜您，你的货物信息提交成功，管理员会进行审核，请等待！";
			}
		} catch (BusinessException e) {
			// TODO: handle exception
			return jsonView(false, e.getMessage());
		}
		return jsonView(true, msg);
	}
	
	/**
	 * 功能描述：上下线 输入参数: @param goodsBasic 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月15日上午9:44:02 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/updateGoodsBasicOnline")
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> updateGoodsBasicOnline(GoodsBasic goodsBasic) {
		String msg = "操作成功";
		try {
			Account currentAccount = UserUtil.getAccount();
			
			if(StringUtil.isEmpty(  goodsBasic.getId())) {
					throw new BusinessException("请确认货物ID号");
			}
			GoodsBasic persistentGoodsBasic = goodsBasicService.getGoodsBasicById(goodsBasic.getId());
			if(persistentGoodsBasic == null) {
				
				throw new BusinessException("未找到此记录");
			}
			if(persistentGoodsBasic.getAccount().getId().equals(currentAccount.getId()) == false) {
					throw new BusinessException("您无权操作此记录");
				
			}
			Map<String,Object> map =  goodsBasicOwnService.getMyOwnWithGoodsBasicID(goodsBasic.getId());
			Boolean  upOnline =(Boolean)   map.get("ownCanUpOnline");
			Boolean downOnline = (Boolean) map.get("ownCanDownOnline");
			if(goodsBasic.getOnLine()) {
				if(upOnline==false)
				throw new BusinessException("此货物不能上线");
			}else if(goodsBasic.getOnLine()==false) {
				if(downOnline==false)
				throw new BusinessException("此货物不能下线");
			}
			 
			 goodsBasicService.updateGoodsBasicOnLine(goodsBasic.getId(), goodsBasic.isOnLine());
		} catch (BusinessException e) {
			// TODO: handle exception
			return jsonView(false, e.getMessage());
		}
		return jsonView(true, msg);
	}
}
