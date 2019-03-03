package com.memory.platform.module.goods.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsDetail;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsDetailService;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年6月12日 下午3:53:47 
* 修 改 人： 
* 日   期： 
* 描   述： 货物详细信息
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/goods/goodsdetail")
public class GoodsDetailController extends BaseController {
	
	@Autowired
	IGoodsDetailService goodsDetailService;
	
	/**
	* 功能描述： 查询货物详细信息
	* 输入参数:  @param goodsBasicId
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年6月12日下午4:18:36
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping("/getListGoodsDetail")
	@ResponseBody
	public List<Map<String,Object>> getListGoodsDetail(String goodsBasicId,HttpServletRequest request){
		List<Map<String,Object>> ret  = goodsDetailService.getListMapGoodsDetail(goodsBasicId);
		return ret;
	}
}
