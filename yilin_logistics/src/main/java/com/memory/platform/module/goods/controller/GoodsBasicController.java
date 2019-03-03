package com.memory.platform.module.goods.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.goods.GoodsBasic;
import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.aut.service.IIdcardService;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsAutLogService;
import com.memory.platform.module.goods.service.IGoodsBasicService;
import com.memory.platform.module.goods.service.IGoodsTypeService;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年6月12日 下午3:53:47 修 改 人： 日 期： 描 述： 发货管理控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/goods/goodsbasic")
public class GoodsBasicController extends BaseController {

	@Autowired
	private IGoodsBasicService goodsBasicService;

	@Autowired
	private IGoodsTypeService goodsTypeService;

	@Autowired
	private IIdcardService idcardService;

	@Autowired
	private IGoodsAutLogService goodsAutLogService;

	/**
	 * 功能描述： 我的货物信息页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: yangjiaqiao 日 期: 2016年7月16日上午10:48:05 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String myroborder(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();
		model.addAttribute("goodsTypeList", list);
		return "/goods/goodsbasic/index";
	}

	@RequestMapping(value = "/view/allindex", method = RequestMethod.GET)
	protected String allGoods(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();
		model.addAttribute("goodsTypeList", list);

		return "/goods/goodsbasic/allindex";
	}

	/**
	 * 功能描述： 分页查询货物信息 输入参数: @param goodsBasic 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月12日下午4:18:36 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(GoodsBasic goodsBasic, HttpServletRequest request) {
		return goodsBasicService.getPage(goodsBasic, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 查找货物信息 输入参数: @param goodsBasic 输入参数: @param request 输入参数: @return 异
	 * 常： 创 建 人: yangjiaqiao 日 期: 2016年6月21日下午3:24:14 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/getGoodsBasicPage")
	@ResponseBody
	public Map<String, Object> getGoodsBasicPage(GoodsBasic goodsBasic, HttpServletRequest request) {
		return goodsBasicService.getGoodsBasicPage(goodsBasic, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 货物发布页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: yangjiaqiao 日 期: 2016年6月13日上午9:42:29 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/releasegoodsbasic", method = RequestMethod.GET)
	protected String add(Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		Idcard idcard = idcardService.getAccountId(account.getId());
		List<GoodsType> list = goodsTypeService.getListByLeav();
		model.addAttribute("goodsTypeList", list);
		model.addAttribute("idcard", idcard);
		model.addAttribute("account", account);
		return "/goods/goodsbasic/releasegoodsbasic";
	}

	/**
	 * 功能描述： 联系人选择 输入参数: @param model 输入参数: @param contactsType 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年6月15日上午9:43:50 修
	 * 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/selectcontacts", method = RequestMethod.GET)
	protected String selectContacts(Model model, String contactsType, HttpServletRequest request) {
		model.addAttribute("contactsType", contactsType);
		return "/goods/goodsbasic/selectcontacts";
	}

	/**
	 * 功能描述： 输入参数: @param goodsBasic 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月15日上午9:44:02 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/saveSubGoodsBasicDetail")
	@ResponseBody
	public Map<String, Object> saveSubGoodsBasicDetail(GoodsBasic goodsBasic, HttpServletRequest request) {
		if (StringUtil.isEmpty(goodsBasic.getStockTypeNames()) || StringUtil.isEmpty(goodsBasic.getStockTypes())) {
			throw new BusinessException("请选择载货车辆类型，再执行审核功能。");
		}
		goodsBasicService.saveSubGoodsBasicDetail(goodsBasic);
		String msg = "";
		if (goodsBasic.getStatus().toString().equals("save")) {
			msg = "货物信息保存成功！";
		} else if (goodsBasic.getStatus().toString().equals("apply")) {
			msg = "恭喜您，你的货物信息提交成功，管理员会进行审核，请等待！";
		}
		return jsonView(true, msg);
	}

	/**
	 * 功能描述： 地图GIS 输入参数: @param model 输入参数: @param point 输入参数: @param id
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年6月16日下午2:10:01 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/gis", method = RequestMethod.GET)
	protected String gis(Model model, String point, String id, HttpServletRequest request) {
		if (StringUtil.isEmpty(point)) {
			point = "106.720628,26.574155";
		}
		model.addAttribute("point", point);
		model.addAttribute("areaName", id.split(",")[0]);
		model.addAttribute("addressId", id.split(",")[1]);
		model.addAttribute("pointId", id.split(",")[2]);
		return "/goods/goodsbasic/gis";
	}

	@RequestMapping(value = "/view/gisRoute", method = RequestMethod.GET)
	protected String gisRoute(Model model, String id, HttpServletRequest request) {
		GoodsBasic goodsBasic = goodsBasicService.getGoodsBasicById(id);
		model.addAttribute("goodsBasic", goodsBasic);
		return "/goods/goodsbasic/gis_route";
	}

	@RequestMapping(value = "/editgoodsbasic", method = RequestMethod.GET)
	protected String editGoodsBasic(Model model, String id, HttpServletRequest request) {
		GoodsBasic goodsBasic = goodsBasicService.getGoodsBasicById(id);
		List<GoodsType> list = goodsTypeService.getListByLeav();
		List<String> lstStockTypeName = new ArrayList<String>();
		for(String typeName : goodsBasic.getStockTypeNames().split(",")){
			lstStockTypeName.add(typeName);
		}
		model.addAttribute("stockTypeNameList", lstStockTypeName);
		model.addAttribute("goodsTypeList", list);
		model.addAttribute("goodsBasic", goodsBasic);
		model.addAttribute("list", goodsAutLogService.getListForMap(id));
		return "/goods/goodsbasic/editgoodsbasic";
	}

	/**
	 * 功能描述： 输入参数: @param goodsBasic 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月15日上午9:44:02 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/editSubGoodsBasicDetail")
	@ResponseBody
	public Map<String, Object> editSubGoodsBasicDetail(GoodsBasic goodsBasic, HttpServletRequest request) {
		boolean isfalse = true;
		String msg = "";
		GoodsBasic basic = goodsBasicService.getGoodsBasicById(goodsBasic.getId());
		if (basic.getStatus().toString().equals("apply")) {
			isfalse = false;
			msg = "当前货物信息，正在等待管理员审核，无法修改，如需修改，请撤回发货申请！";
			return jsonView(isfalse, msg);
		}
		if (basic.getStatus().toString().equals("lock")) {
			isfalse = false;
			msg = "当前货物信息，正在审核中，无法修改！";
			return jsonView(isfalse, msg);
		}
		if (basic.getStatus().toString().equals("success")) {
			isfalse = false;
			msg = "当前货物信息，已经通过审核，无法修改！";
			return jsonView(isfalse, msg);
		}
		this.goodsBasicService.editSubGoodsBasicDetail(goodsBasic);
		if (goodsBasic.getStatus().toString().equals("save")) {
			msg = "货物信息修改成功！";
		} else if (goodsBasic.getStatus().toString().equals("apply")) {
			msg = "恭喜您，你的货物信息提交成功，管理员会进行审核，请等待！";
		}
		return jsonView(isfalse, msg);
	}

	/**
	 * 功能描述： 设置货物上线，下线 输入参数: @param id 输入参数: @param request 输入参数: @return 异 常： 创
	 * 建 人: yangjiaqiao 日 期: 2016年6月16日下午8:47:02 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/uodateGoodsBasicOnLine")
	@ResponseBody
	public Map<String, Object> uodateGoodsBasicOnLine(String id, boolean onLine, HttpServletRequest request) {
		GoodsBasic basic = goodsBasicService.getGoodsBasicById(id);
		String msg = onLine == true ? "上线" : "下线";
		if (basic.getStatus().toString().equals("apply")) {
			return jsonView(false, "当前货物信息，正在等待管理员审核，无法" + msg + "！");
		}
		if (basic.getStatus().toString().equals("lock")) {
			return jsonView(false, "当前货物信息，正在处理中，无法" + msg + "！");
		}
		if (basic.getStatus().toString().equals("save")) {
			return jsonView(false, "当前货物信息，还未提交审核，无法" + msg + "！");
		}
		if (basic.getStatus().toString().equals("back")) {
			return jsonView(false, "当前货物信息，未通过审核，无法" + msg + "！");
		}
		if (basic.getStatus().toString().equals("scrap")) {
			return jsonView(false, "当前货物信息，已作废，无法" + msg + "！");
		}
		goodsBasicService.updateGoodsBasicOnLine(id, onLine);
		return jsonView(true, "发货信息上线成功！");
	}

	@RequestMapping("/removeGoodsBasicDetail")
	@ResponseBody
	public Map<String, Object> removeGoodsBasicDetail(String id, HttpServletRequest request) {
		GoodsBasic basic = goodsBasicService.getGoodsBasicById(id);
		if (basic.getStatus().toString().equals("apply")) {
			return jsonView(false, "当前货物信息，正在等待管理员审核，无法删除！");
		}
		if (basic.getStatus().toString().equals("lock")) {
			return jsonView(false, "当前货物信息，正在处理中，无法删除！");
		}
		if (basic.getStatus().toString().equals("success")) {
			return jsonView(false, "当前货物信息，已经通过审核，无法删除！");
		}
		goodsBasicService.removeGoodsBasicDetail(id, basic);
		return jsonView(true, "发货信息删除成功！");
	}

	/**
	 * 功能描述： 发货申请撤回 输入参数: @param id 输入参数: @param request 输入参数: @return 异 常： 创 建
	 * 人: yangjiaqiao 日 期: 2016年6月17日上午11:13:53 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping("/translateGoodsBasicDetail")
	@ResponseBody
	public Map<String, Object> translateGoodsBasicDetail(String id, HttpServletRequest request) {
		GoodsBasic goodsBasic = goodsBasicService.getGoodsBasicById(id);
		if (!goodsBasic.getStatus().toString().equals("apply")) {
			String msg = "当前货物信息，";
			switch (goodsBasic.getStatus().toString()) {
			case "lock":
				msg = msg + "已处于【锁定状态】管理员正常处理";
				break;
			case "back":
				msg = msg + "已处于【退回状态】，无法撤回，请刷新页面查看状态！";
				break;
			case "success":
				msg = msg + "已经通过审核，无法撤回，请刷新页面查看状态！";
				break;
			case "scrap":
				msg = msg + "已作废，无法撤回，请刷新页面查看状态！";
				break;
			case "save":
				msg = msg + "处于保存状态，无需撤回！";
				break;
			}
			return jsonView(false, msg);
		}
		if (goodsBasic.getStatus().toString().equals("save")) {
			return jsonView(false, "当前货物信息，处于保存状态，无需撤回！");
		}
		goodsBasicService.updateGoodsBasicTranslate(goodsBasic);
		return jsonView(true, "发货信息撤回成功！");
	}

	/**
	 * 功能描述： 跳转到查询货物总数的页面 输入参数: @param model 输入参数: @param id 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月21日上午10:50:13 修
	 * 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/goodsCount", method = RequestMethod.GET)
	protected String getGoodsCount(Model model, String id, HttpServletRequest request) {
		return "/goods/goodsbasic/goodsCount";
	}

	/**
	 * 功能描述： 货物统计页面 输入参数: @param model 输入参数: @param id 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月21日下午2:35:13 修 改 人: 日
	 * 期: 返 回：String
	 */
	@RequestMapping(value = "/goodsStatistics", method = RequestMethod.GET)
	protected String getGoodsStatistics(Model model, String id, HttpServletRequest request) {
		model.addAttribute("years", DateUtil.getYearQian(3));
		model.addAttribute("yearDay", DateUtil.getYear());
		return "/goods/goodsbasic/goodsStatistics";
	}

	/**
	 * 功能描述： 货物统计，月统计 输入参数: @param year 统计年份 输入参数: @param model 输入参数: @param id
	 * 输入参数: @param request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年8月24日上午11:48:09 修 改 人: 日 期: 返 回：String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/goodsMonthStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String, Object> getGoodsMonthStatistics(String year, Model model, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		String accountId = null;
		if (!account.getCompany().getCompanyType().getName().equals("系统")
				&& !account.getCompany().getCompanyType().getName().equals("管理")) {
			accountId = account.getId();
		}
		Map<String, Object> month_map = DateUtil.getMonths(year);
		List<String> months = (List<String>) month_map.get("months");
		List counts = goodsBasicService.getAllGoodsBasicMonthCount(months, accountId);
		List weights = goodsBasicService.getAllGoodsBasicMonthWeight(months, accountId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts.get(0));
		map.put("weights", weights.get(0));
		map.put("xAxis", month_map.get("months_v"));
		map.put("success", true);
		map.put("msg", "统计数据成功！");
		return map;
	}

	/**
	 * 功能描述： 平台商户发货排名统计 输入参数: @param year 输入参数: @param model 输入参数: @param
	 * request 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年8月24日下午2:56:00 修
	 * 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "/goodsRankingStatistics", method = RequestMethod.GET)
	@ResponseBody
	protected Map<String, Object> getGoodsRankingStatistics(String ranking, String type, Model model,
			HttpServletRequest request) {
		Map<String, Object> month_map = goodsBasicService
				.getGoodsRankingStatistics(ranking == null ? 0 : Integer.parseInt(ranking), type);
		return month_map;
	}

}
