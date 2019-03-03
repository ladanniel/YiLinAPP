package com.memory.platform.module.report.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.CapitalStatus;
import com.memory.platform.entity.order.RobOrderRecord;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.global.ExportExcelUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.goods.service.IGoodsTypeService;
import com.memory.platform.module.order.service.IRobOrderConfirmService;
import com.memory.platform.module.truck.service.ITruckService;

import freemarker.cache.StrongCacheStorage;

@Controller
@RequestMapping("/report/forms")
public class ReportFormsController extends BaseController {
	@Autowired
	private IGoodsTypeService goodsTypeService;
	@Autowired
	private ITruckService truckService;
	@Autowired
	IRobOrderConfirmService robOrderConfirmService;

	@RequestMapping(value = "/view/settlement", method = RequestMethod.GET)
	public String reportformsinfo(Model model, HttpServletRequest request) {
		List<GoodsType> list = goodsTypeService.getListByLeav();
		Account account = UserUtil.getUser();
		model.addAttribute("goodsTypeList", list);
		model.addAttribute("account", account);
		if ("货主".equals(account.getCompany().getCompanyType().getName())) {
			return "/order/myorder/corderinfo";
		}
		Truck truck = truckService.getTruckByAcountNo(account.getId());
		model.addAttribute("truck", truck);
		return "/report/settlement/reportinfo";
	}

	/***
	 * 查询报表 功能描述： 输入参数: @param robOrderRecord 输入参数: @param request 输入参数: @return
	 * 异 常： 创 建 人: aiqiwu 日 期: 2017年5月19日下午14:42:07 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "/getFormsInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMyRobOrderPage(RobOrderRecord robOrderRecord, String goodsName, String trackNo) {
		// String goodsName = request.getParameter("goodsName");
		// String trackNo = request.getParameter("trackNo");
		// try {
		// if (!StringUtils.isEmpty(goodsName))
		// goodsName = new String(goodsName.getBytes("ISO-8859-1"), "UTF-8");
		// if (!StringUtils.isEmpty(trackNo))
		// trackNo = new String(trackNo.getBytes("ISO-8859-1"), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		Map<String, Object> ret = robOrderConfirmService.getReportFormsPage(robOrderRecord, goodsName, trackNo,
				getStart(request), getLimit(request));
		return ret;
	}

	/**
	 * 功能描述： excel导出 输入参数: @param request 输入参数: @param response 输入参数: @param
	 * capitalAccount 输入参数: @return 异 常： 创 建 人: aiqiwu 日 期: 2017年5月19日下午15:30:00
	 * 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/export")
	@ResponseBody
	public String exportexcel(String goodsName, String trackNo, HttpServletResponse response,
			RobOrderRecord robOrderRecord) {
		String fileName = System.currentTimeMillis() + "结算单";
		String columnNames[] = { "收货时间", "货物名称", "规格型号", "计量单位", "发货数量", "结算数量", "汽运单价", "汽运金额", "起运点", "车号", "驾驶员" };
		String keys[] = { "endDate", "goodsName", "specification", "unit", "totalWeight", "actualWeight", "unitPrice",
				"totalPrice", "deliveryAreaName", "trackNo", "userName" };
		Map<String, Object> ret = robOrderConfirmService.getReportFormsPage(robOrderRecord, goodsName, trackNo,0,1000);
		List<Map<String, Object>> listmap = createExcelRecord(ret);
		ExportExcelUtil.createExcel(request, response, fileName, null, listmap, columnNames, keys);
		return null;
	}

	/**
	 * 功能描述： list转换map 输入参数: @param list 输入参数: @return 异 常： 创 建 人: longqibo 日 期:
	 * 2016年5月27日下午5:26:11 修 改 人: 日 期: 返 回：List<Map<String,Object>>
	 */
	private List<Map<String, Object>> createExcelRecord(Map<String, Object> ret) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = (List<Map<String, Object>>) ret.get("rows");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);

		for (Map<String, Object> vo : list) {
			Map<String, Object> exceMap = new HashMap<String, Object>();
			exceMap.put("endDate", vo.get("endDate"));
			exceMap.put("goodsName", vo.get("goodsName"));
			exceMap.put("specification", vo.get("specification"));
			exceMap.put("unit", "吨");
			exceMap.put("totalWeight", vo.get("totalWeight"));
			exceMap.put("actualWeight", vo.get("actualWeight"));
			exceMap.put("unitPrice", vo.get("unitPrice"));
			exceMap.put("totalPrice", vo.get("totalPrice"));
			exceMap.put("deliveryAreaName", vo.get("deliveryAreaName"));
			exceMap.put("trackNo", vo.get("trackNo"));
			exceMap.put("userName", vo.get("userName"));
			listmap.add(exceMap);
		}
		return listmap;
	};
}
