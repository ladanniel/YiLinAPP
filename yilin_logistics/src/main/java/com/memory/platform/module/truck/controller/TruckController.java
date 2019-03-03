package com.memory.platform.module.truck.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.entity.truck.TruckAxle;
import com.memory.platform.entity.truck.TruckContainer;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.ICompanyService;
import com.memory.platform.module.trace.dto.YYAddVehicleReqDTO;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO.Vehicle;
import com.memory.platform.module.trace.dto.YYGroupResponseDTO.YYGroup;
import com.memory.platform.module.trace.dto.YYLocationResponseDTO;
import com.memory.platform.module.trace.dto.YYQueryVehicelRespDTO;
import com.memory.platform.module.trace.dto.YYQueryVehicleReq;
import com.memory.platform.module.trace.dto.YYStandResponseDTO;
import com.memory.platform.module.trace.service.IYYGpsService;
import com.memory.platform.module.trace.service.impl.YYGpsService;
import com.memory.platform.module.truck.service.IAxleTypeService;
import com.memory.platform.module.truck.service.IBearingNumService;
import com.memory.platform.module.truck.service.IContainersTypeService;
import com.memory.platform.module.truck.service.IEngineBrandService;
import com.memory.platform.module.truck.service.ITruckAxleService;
import com.memory.platform.module.truck.service.ITruckBrandService;
import com.memory.platform.module.truck.service.ITruckContainerService;
import com.memory.platform.module.truck.service.ITruckPlateService;
import com.memory.platform.module.truck.service.ITruckService;
import com.memory.platform.module.truck.service.ITruckTypeService;

/**
 * 
 * 创 建 人：liyanzhang 日 期： 2016年5月30日 19:10:47 修 改 人： 修 改 日 期： 描 述：车辆信息控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/truck/systruck")
public class TruckController extends BaseController {

	@Autowired
	private ITruckService truckService;

	@Autowired
	private ITruckTypeService truckTypeService;

	@Autowired
	private ITruckPlateService truckPlateService;

	@Autowired
	private ITruckBrandService truckBrandService;

	@Autowired
	private IEngineBrandService engineBrandService;

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private ITruckAxleService truckAxleService;

	@Autowired
	private ITruckContainerService truckContainerService;

	@Autowired
	private IAxleTypeService axleTypeService;

	@Autowired
	private IBearingNumService bearingNumService;

	@Autowired
	private IContainersTypeService containersTypeService;

	@Autowired
	private IYYGpsService yyGpsService;

	/**
	 * 功 能 描 述：分页查询所有车辆 异 常： 创 建 人： liyanzhang 日 期： 2016年6月12日 16:10:44 修 改 人： 修
	 * 改 日 期： 返 回：map
	 */
	@RequestMapping("/getPage")
	@ResponseBody
	public Map<String, Object> getPage(Truck truck, HttpServletRequest request) {
		return this.truckService.getPage(truck, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 商户多条件查询车辆信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("/getTruckPage")
	@ResponseBody
	public Map<String, Object> getTruckPage(Truck truck, HttpServletRequest request) {
		return this.truckService.getTruckPage(truck, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 查询车队下的司机人员信息及车辆 输入参数: @param truck 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月13日下午3:46:01 修 改 人: 日
	 * 期: 返 回：List<Map<String,Object>>
	 */
	@RequestMapping("/getCompantAccountTruckList")
	@ResponseBody
	public List<Map<String, Object>> getCompantAccountTruckList(Truck truck, HttpServletRequest request) {
		Account account = UserUtil.getUser(request);
		List<Map<String, Object>> list = this.truckService.getCompantAccountTruckList(account.getCompany().getId());
		return list;
	}

	/**
	 * 功能描述：易林多条件查询车辆信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping("/getTruckPageByYilin")
	@ResponseBody
	public Map<String, Object> getTruckPageByYilin(Truck truck, HttpServletRequest request) {
		return this.truckService.getTruckPageByYilin(truck, getStart(request), getLimit(request));
	}

	/**
	 * 功能描述： 进入易林查询车辆页面 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/yilinindex", method = RequestMethod.GET)
	protected String yilinindex(Model model, HttpServletRequest request) {
		String url = "/truck/systruck/yilinindex";
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
		return url;
	}

	/**
	 * 功能描述： 进入人员分配页面 输入参数: @param model 输入参数: @param request 输入参数: @return 异 常：
	 * 创 建 人: yangjiaqiao 日 期: 2016年7月15日上午11:06:37 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/personnelAllotment", method = RequestMethod.GET)
	protected String personnelAllotment(Model model, HttpServletRequest request) {
		String url = "/truck/systruck/personnel_allotment";
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
		return url;
	}

	/**
	 * 功能描述： 人员选择 输入参数: @param id 输入参数: @param model 输入参数: @param request
	 * 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日 期: 2016年7月15日上午11:06:55 修 改 人: 日
	 * 期: 返 回：String
	 */
	@RequestMapping(value = "/confirmPerson", method = RequestMethod.GET)
	protected String confirmPerson(String id, Model model, HttpServletRequest request) {
		String url = "/truck/systruck/confirm_person";
		model.addAttribute("truck", this.truckService.getTruckById(id));
		return url;
	}

	/**
	 * 功能描述： 修改车辆司机人员 输入参数: @param truck 输入参数: @return 异 常： 创 建 人: yangjiaqiao 日
	 * 期: 2016年7月15日上午11:07:49 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "updateConfirmPerson", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmPerson(String trackId, String distributionTrackId,
			String distributionUserId, String distributionUserName) {
		truckService.updateConfirmPerson(trackId, distributionTrackId, distributionUserId, distributionUserName);
		return jsonView(true, "车辆人员分配成功！");
	}

	/**
	 * 功能描述： 输入参数: @param trackId 输入参数: @param distributionTrackId 输入参数: @param
	 * distributionUserId 输入参数: @param distributionUserName 输入参数: @return 异 常： 创
	 * 建 人: yangjiaqiao 日 期: 2016年7月18日上午10:17:56 修 改 人: 日 期: 返
	 * 回：Map<String,Object>
	 */
	@RequestMapping(value = "updateChainBroken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateChainBroken(String trackId) {
		truckService.updateChainBroken(trackId);
		return jsonView(true, "车辆人员解绑成功！");
	}

	/**
	 * 功能描述： 车辆首页查询 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		Account user = UserUtil.getUser(request);
		String url = "/truck/systruck/index";
		// if (user.getCompany().getCompanyType().getName().equals("个人司机")) {
		// url = "/truck/systruck/driverindex";
		// }
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
		return url;
	}

	/**
	 * 功能描述：进入添加车辆信息页面 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	protected String add(Model model, String id, HttpServletRequest request) {
		Account user = UserUtil.getUser(request);
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
		model.addAttribute("enginebrandList", this.engineBrandService.getEngineBrandList());
		if (user.getCompany().getCompanyType().getName().equals("个人司机")) {
			return "/truck/systruck/addaccounttruck";
		}
		return "/truck/systruck/add";
	}

	/**
	 * 功能描述： 添加车辆 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Truck truck, HttpServletRequest request) {
		Account user = UserUtil.getUser(request);
		String msg = "";
		boolean flag = true;
		if (truckService.getTruckByName(truck.getTrack_no(), null)) {
			if (user.getCompany().getCompanyType().getName().equals("个人司机")) {
				truck.setAccount(user);
			}
			// aiqiwu 2017-06-07新增,如果填写了sim卡号和gps设备号就请求注册新增车辆
			if (!StringUtil.isEmpty(truck.getSim_card_id()) && !StringUtil.isEmpty(truck.getGps_device_id())) {
				// 请求亿源gps注册新增车辆
				YYAddVehicleReqDTO vehicleReq = new YYAddVehicleReqDTO();
				vehicleReq.setCarName(truck.getSim_card_id() + truck.getTrack_no());
				vehicleReq.setSim(truck.getSim_card_id());
				vehicleReq.setGprs(truck.getGps_device_id());
				YYStandResponseDTO resp = yyGpsService.addVehicle(vehicleReq);
				if (resp.getFlag()) {
					YYGroupResponseDTO groups1 = yyGpsService.getVehicles();
					for (int i = 0; i < groups1.getGroups().size(); i++) {
						YYGroup group = groups1.getGroups().get(i);
						for (int j = 0; j < group.getVehicles().size(); j++) {
							Vehicle v = group.getVehicles().get(j);
							if (v.getName().equals(vehicleReq.getCarName())) {
								truck.setYy_vehicel_id(v.getId() + "");
								truck.setYy_vehicel_key(v.getvKey());
								break;
							}
						}
					}
				}
			}
			this.truckService.saveTruck(truck);
			msg = SAVE_SUCCESS;
		} else {
			msg = "[" + truck.getTrack_no() + "]车辆，在数据库已存在！";
			flag = false;
		}
		return jsonView(flag, msg);
	}

	/**
	 * 功能描述：添加轮轴信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "addaxle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addaxle(TruckAxle truckAxle) {
		String msg = "";
		boolean flag = true;
		if (this.truckAxleService.checkTruckAxleByTruckno(truckAxle.getTruck().getId()) == null) {
			this.truckAxleService.saveTruckAxle(truckAxle);
			msg = SAVE_SUCCESS;
		} else {
			msg = "[" + truckAxle.getTruck().getTrack_no() + "]的车辆已有轮轴属性！";
			flag = false;
		}
		return jsonView(flag, msg);
	}

	/**
	 * 功能描述： 添加货箱信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "addcontainer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addcontainer(TruckContainer truckContainer) {
		String msg = "";
		boolean flag = true;
		if (this.truckContainerService.checkTruckContainerByTruckno(truckContainer.getTruck().getId()) == null) {
			this.truckContainerService.saveTruckContainer(truckContainer);
			msg = SAVE_SUCCESS;
		} else {
			msg = "[" + truckContainer.getTruck().getTrack_no() + "]的车辆已有货箱属性！";
			flag = false;
		}
		return jsonView(flag, msg);
	}

	/**
	 * 功能描述： 查看个人车辆信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/getTruckByAccountno", method = RequestMethod.GET)
	protected String getTruckByAccountid(Model model) {
		Account account = UserUtil.getUser();
		Truck truck = this.truckService.getTruckByAcountNo(account.getId());
		model.addAttribute("truck", truck);
		return "/truck/systruck/mytruck";
	}

	/**
	 * 功能描述： 检测车辆是否有轮轴信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/track_axle", method = RequestMethod.GET)
	protected String track_axle(Model model, String id) {
		Truck truck = this.truckService.getTruckById(id);
		TruckAxle truckAxle = this.truckAxleService.checkTruckAxleByTruckno(truck.getId());
		if (truckAxle == null) {
			model.addAttribute("truck", truck);
			model.addAttribute("axleTypeList", this.axleTypeService.getAxleTypeList());
			model.addAttribute("bearingNumList", this.bearingNumService.getBearingNumList());
			return "/truck/systruck/addaxle";
		} else {
			model.addAttribute("truckAxle", truckAxle);
			return "/truck/systruck/axledetail";
		}
	}

	/**
	 * 功能描述： 检测车辆是否有货箱信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/container", method = RequestMethod.GET)
	protected String container(Model model, String id) {
		Truck truck = this.truckService.getTruckById(id);
		TruckContainer truckContainer = this.truckContainerService.checkTruckContainerByTruckno(truck.getId());
		if (truckContainer == null) {
			model.addAttribute("truck", truck);
			model.addAttribute("containersTypeList", this.containersTypeService.getContainersTypeList());
			return "/truck/systruck/addcontainer";
		} else {
			model.addAttribute("truckContainer", truckContainer);
			return "/truck/systruck/containerdetail";
		}
	}

	/**
	 * 功能描述： 获取车辆货箱轮轴详情 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/getTruckDetail", method = RequestMethod.GET)
	protected String getTruckDetail(Model model, String id) {
		Truck truck = this.truckService.getTruckById(id);
		TruckContainer truckContainer = this.truckContainerService.checkTruckContainerByTruckno(truck.getId());
		TruckAxle truckAxle = this.truckAxleService.checkTruckAxleByTruckno(truck.getId());
		model.addAttribute("truckContainer", truckContainer);
		model.addAttribute("truckAxle", truckAxle);
		model.addAttribute("truck", truck);
		return "/truck/systruck/truckdetail";
	}

	/**
	 * 功能描述： 进入编辑车辆信息页面 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：String
	 */
	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
	protected String edit(Model model, String id, HttpServletRequest request) {
		Account user = UserUtil.getUser(request);
		Truck truck = this.truckService.getTruckById(id);
		model.addAttribute("truck", truck);
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
		model.addAttribute("enginebrandList", this.engineBrandService.getEngineBrandList());
		if (user.getCompany().getCompanyType().getName().equals("个人司机")) {
			return "/truck/systruck/accountedit";
		}
		return "/truck/systruck/edit";
	}

	/**
	 * 功能描述：编辑车辆信息 输入参数: @param Truck truck 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(Truck truck) {
		String msg = "";
		boolean flag = true;
		Truck tempTruck = truckService.getTruckById(truck.getId());
		if (truckService.getTruckByName(truck.getTrack_no(), truck.getId())) {
			// aiqiwu 2017-06-07新增,如果填写了sim卡号和gps设备号就请求注册新增车辆
			if (!StringUtil.isEmpty(truck.getSim_card_id()) && !StringUtil.isEmpty(truck.getGps_device_id())) {
				// 请求亿源gps注册新增车辆
				YYAddVehicleReqDTO vehicleReq = new YYAddVehicleReqDTO();
				vehicleReq.setCarName(truck.getSim_card_id() + truck.getTrack_no());
				vehicleReq.setSim(truck.getSim_card_id());
				vehicleReq.setGprs(truck.getGps_device_id());
				YYStandResponseDTO resp = null;
				if (!StringUtil.isEmpty(truck.getYy_vehicel_id())) {
					Map<String, String> queryParam = new HashMap<String, String>();
					queryParam.put("vehicle.gprs", tempTruck.getGps_device_id());
					YYQueryVehicelRespDTO req = yyGpsService.queryVehicle(queryParam);
					if (req.getData().size() > 0) {
						vehicleReq.setClientID(yyGpsService.getClientId());
						String vehicelId = req.getData().get(0).getId();
						resp = yyGpsService.updateVehicle(vehicelId, vehicleReq);
					} else {
						resp = yyGpsService.addVehicle(vehicleReq);
					}
				} else {
					resp = yyGpsService.addVehicle(vehicleReq);
				}
				if (resp.getFlag()) {
					YYGroupResponseDTO groups1 = yyGpsService.getVehicles();
					for (int i = 0; i < groups1.getGroups().size(); i++) {
						YYGroup group = groups1.getGroups().get(i);
						for (int j = 0; j < group.getVehicles().size(); j++) {
							Vehicle v = group.getVehicles().get(j);
							if (v.getName().equals(vehicleReq.getCarName())) {
								truck.setYy_vehicel_id(v.getId() + "");
								truck.setYy_vehicel_key(v.getvKey());
								break;
							}
						}
					}
				}
			}
			this.truckService.updateTruckMerge(truck);
			msg = UPDATE_SUCCESS;
		} else {
			flag = false;
			msg = "[" + truck.getTrack_no() + "]车辆，在数据库已存在！";
		}
		return jsonView(flag, msg);
	}

	/**
	 * 功能描述： 检查车牌号是否存在 输入参数: @param String track_no 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：boolean
	 */
	@RequestMapping(value = "checkNo", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkNo(Model model, String track_no, String old, HttpServletRequest request) {
		if (StringUtil.isNotEmpty(old)) {
			if (track_no.equals(old)) {
				return true;
			}
		}
		Truck truck = truckService.checkTruckByNo(track_no);
		return truck == null ? true : false;
	}

	/**
	 * 功能描述： 删除车辆 输入参数: @param ids 异 常： 创 建 人: liyanzhang 日 期:
	 * 2016年7月18日上午10:17:56 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@RequestMapping(value = "remove")
	@ResponseBody
	public Map<String, Object> remove(String ids) {
		try {
			String[] resArr = ids.split(",");
			for (String resId : resArr) {
				this.truckService.removeTruck(resId);
			}
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}

		return jsonView(true, REMOVE_SUCCESS);
	}
}
