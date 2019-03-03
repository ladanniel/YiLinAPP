package com.memory.platform.module.goods.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsTypeService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 下午2:49:56 
* 修 改 人： 
* 日   期： 
* 描   述： 货物类型控制器
* 版 本 号：  V1.0
 */
@Controller
@RequestMapping("/goods/goodstype")
public class GoodsTypeController extends BaseController {

	@Autowired
	private IGoodsTypeService goodsTypeService;
	
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getAllList();
		JSONArray json = new JSONArray();
		if(list.size() > 0){
			for (GoodsType goodsType : list) {
				JSONObject jo = new JSONObject();
				jo.put("id", goodsType.getId()); 
				if(goodsType.getPrentId().equals("-1") || goodsType.getLevel() == 1){
					jo.put("parent", "#");
				}else{
					jo.put("parent", goodsType.getPrentId());
				}
				String descs = StringUtil.isEmpty(goodsType.getDescs()) ? "无":goodsType.getDescs();
				jo.put("text", goodsType.getName()+"--------<font color='#ed5565'>备注：</font><span class='label label-danger'>"+ descs +"</span>");
				json.put(jo);
			}
		} 
		
		model.addAttribute("json", json);
		model.addAttribute("list", list);
		return "/goods/goodstype/index";
	}
	
	 
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();
		GoodsType goodsType =new GoodsType();
		goodsType.setId("sys_goods_type");
		goodsType.setName("一级货物类型");
		goodsType.setLevel(0);
		list.add(goodsType);
		Collections.sort(list);
		model.addAttribute("list", list);
		return "/goods/goodstype/add";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(GoodsType goodsType,HttpServletRequest request){
		Account account  =UserUtil.getUser(request);
		goodsType.setCreate_time(new Date());
		goodsType.setAdd_user_id(account.getId());
		if(goodsTypeService.getGoodsTypeByName(goodsType.getName(),null) == null){
			goodsTypeService.saveGoodsType(goodsType);
		}else{
			return jsonView(false, "["+goodsType.getName()+"]货物类型已存在，请重新输入！");
		}
		return jsonView(true, SAVE_SUCCESS);
	}
	
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model,String id, HttpServletRequest request) {
		model.addAttribute("vo", goodsTypeService.getGoodsTypeById(id));
		return "/goods/goodstype/edit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(GoodsType goodsType,HttpServletRequest request){
		Account account  =UserUtil.getUser(request);
		if(goodsTypeService.getGoodsTypeByName(goodsType.getName(),goodsType.getId()) == null){
			goodsType.setUpdate_time(new Date());
			goodsType.setUpdate_user_id(account.getId());
			goodsTypeService.updateGoodsType(goodsType);
		}else{
			return jsonView(false, "["+goodsType.getName()+"]货物类型已存在，请重新输入！");
		}
		return jsonView(true, UPDATE_SUCCESS);
	}
	
	@RequestMapping("/remove")
	@ResponseBody
	public Map<String, Object> remove(String id){
		if(goodsTypeService.getListByPrentId(id).size() == 0){
			goodsTypeService.removeGoodsType(id);
		}else{
			GoodsType goodsType = goodsTypeService.getGoodsTypeById(id);
			return jsonView(false, "["+goodsType.getName()+"]货物类型下存在子数据，无法进行删除，如果需要删除，请选择先删除子数据！");
		}
		return jsonView(true, REMOVE_SUCCESS);
	}
}
