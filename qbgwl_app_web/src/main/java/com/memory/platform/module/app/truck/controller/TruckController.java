package com.memory.platform.module.app.truck.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.memory.platform.Interface.UnInterceptor;
import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.entity.truck.TruckAxle;
import com.memory.platform.entity.truck.TruckContainer;
import com.memory.platform.entity.truck.TruckPlate;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.TruckStatus;
import com.memory.platform.global.UserUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.system.service.IAccountService;
import com.memory.platform.module.system.service.ICompanyService;
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
import com.wordnik.swagger.annotations.Api;

/**
 * 
 * 创 建 人：liyanzhang 日 期： 2016年5月30日 19:10:47 修 改 人： 修 改 日 期： 描 述：车辆信息控制器 版 本 号：
 * V1.0
 */
@Controller
@RequestMapping("/truck/systruck")
@Api(value = "/truck/systruck", description = "车辆相关操作",consumes="application/json")
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
	private IAccountService accountService;
	
	/**
	* 功能描述： 文件上传
	* 输入参数:  @param request
	* 输入参数:  @param file
	* 输入参数:  @return
	* 输入参数:  @throws IllegalStateException
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年8月13日 14:17:35
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> uploadimg(@RequestParam MultipartFile file) throws IllegalStateException, IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(file.isEmpty()){
    		map.put("success", false);
    		map.put("msg", "文件为空！");
    		return  map;
        }
		String url = truckService.uploadImage(file);
		map.put("success", true);
		map.put("msg", "上传成功！");
		map.put("imgpath", url);
        return  map;
	}
	/**
	 * 
	* 功能描述： 添加车辆
	* 输入参数:  @param companyid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 * 
	 */
	@RequestMapping(value = "saveTruck", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> saveTruck(Truck truck,@RequestHeader HttpHeaders headers) {
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(truck.getTrack_no())){
			return jsonView(false,"车牌号不能为空");
		}
		if(truckService.checkTruckByNo(truck.getTrack_no())!=null){
			return jsonView(false,"该车牌号已存在");
		}
		if(StringUtil.isEmpty(truck.getTruckType().getId())){
			return jsonView(false,"请选择车辆类型");
		}
		if(StringUtil.isEmpty(truck.getTruckPlate().getId())){
			return jsonView(false,"请选择车牌类型");
		}
		if(truck.getTrack_long()==null){
			return jsonView(false,"车辆长度不能为空");
		}
		if(truck.getCapacity()==null){
			return jsonView(false,"车辆载重量不能为空");
		}
		if(StringUtil.isEmpty(truck.getTruckBrand().getId())){
			return jsonView(false,"请选择车辆品牌");
		}
		if(StringUtil.isEmpty(truck.getTrack_read_no())){
			return jsonView(false,"车辆识别码不能为空");
		}
		if(StringUtil.isEmpty(truck.getEngineBrand().getId())){
			return jsonView(false,"请选择发动机品牌");
		}
		if(StringUtil.isEmpty(truck.getEngine_no())){
			return jsonView(false,"发动机编号不能为空");
		}
		if(StringUtil.isEmpty(truck.getHorsepower())){
			return jsonView(false,"车辆马力不能为空");
		}
//		if(StringUtil.isEmpty(truck.getVehiclelicense_img())){
//			return jsonView(false,"行驶证图片为空");
//		}
		if("个人司机".equals(account.getCompany().getCompanyType().getName())){
			truck.setAccount(account);
			truck.setCompany(account.getCompany());
		}else{
			truck.setCompany(account.getCompany());
		}
		truck.setTruckStatus(TruckStatus.notransit);
		truckService.saveTruck(truck);
		return jsonView(true, "车辆添加成功!");
	}
	/**
	 * 
	* 功能描述： 编辑车辆
	* 输入参数:  @param companyid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 * @throws IOException 
	 */
	@RequestMapping(value = "editTruck", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> editTruck(Truck truck,@RequestHeader HttpHeaders headers) throws IOException{
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(truck.getId())){
			return jsonView(false,"请选择车辆。");
		}
		if(StringUtil.isEmpty(truck.getTrack_no())){
			return jsonView(false,"车牌号不能为空");
		}
		Truck t=truckService.checkTruckByNo(truck.getTrack_no());
		if(t!=null){
			if(!t.getId().equals(truck.getId())){
				return jsonView(false,"该车牌号已存在");
			}
			if(t.getTruckStatus()==TruckStatus.transit){
				return jsonView(false,"该车辆处于运输中不可修改");
			}
		}
		
		if(StringUtil.isEmpty(truck.getTruckType().getId())){
			return jsonView(false,"请选择车辆类型");
		}
		if(StringUtil.isEmpty(truck.getTruckPlate().getId())){
			return jsonView(false,"请选择车牌类型");
		}
		if(truck.getTrack_long()==null){
			return jsonView(false,"车辆长度不能为空");
		}
		if(truck.getCapacity()==null){
			return jsonView(false,"车辆载重量不能为空");
		}
		if(StringUtil.isEmpty(truck.getTruckBrand().getId())){
			return jsonView(false,"请选择车辆品牌");
		}
		if(StringUtil.isEmpty(truck.getTrack_read_no())){
			return jsonView(false,"车辆识别码不能为空");
		}
		if(StringUtil.isEmpty(truck.getEngineBrand().getId())){
			return jsonView(false,"请选择发动机品牌");
		}
		if(StringUtil.isEmpty(truck.getEngine_no())){
			return jsonView(false,"发动机编号不能为空");
		}
		if(StringUtil.isEmpty(truck.getHorsepower())){
			return jsonView(false,"车辆马力不能为空");
		}
		if(StringUtil.isEmpty(truck.getVehiclelicense_img())){
			return jsonView(false,"行驶证图片为空");
		}
		if(StringUtil.isEmpty(truck.getAccount().getId())){
			truck.setAccount(null);
		}
		if(StringUtil.isEmpty(truck.getCompany().getId())){
			return jsonView(false,"商户编号不能为空");
		}
		truck.setTruckStatus(TruckStatus.notransit);
		truckService.updateTruck(truck);
		return jsonView(true, "车辆修改成功");
	}
	
	@RequestMapping(value = "removeTruckNew", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> removeTruckNew(Truck truck ) {
	
		truckService.removeTruck(truck.getId());
		return jsonView(true, "删除车辆成功!");
	}
	
	
	@RequestMapping(value = "saveTruckNew", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public Map<String, Object> saveTruckNew(Truck truck ) throws IOException{
		 
		Account account = UserUtil.getAccount();
		 
		
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		 
		truck.setCompany(account.getCompany());
		 
		if(StringUtil.isEmpty(truck.getTrack_no())){
			return jsonView(false,"车牌号不能为空");
		}
		Truck t=truckService.checkTruckByNo(truck.getTrack_no());
		if(t!=null){
			if(!t.getId().equals(truck.getId())){
				return jsonView(false,"该车牌号已存在");
			}
			if(t.getTruckStatus()==TruckStatus.transit){
				return jsonView(false,"该车辆处于运输中不可修改");
			}
		}
 
			
		
	 
		if(StringUtil.isEmpty(truck.getTruckType().getId())){
			return jsonView(false,"请选择车辆类型");
		}
		if(truck.getTruckPlate() == null) {
			TruckPlate truckPlate =  truckPlateService.getTruckPlateList().get(0);
			truck.setTruckPlate(truckPlate);
			
		}
		
		if(truck.getTrack_long()==null){
			return jsonView(false,"车辆长度不能为空");
		}
		if(truck.getCapacity()==null){
			return jsonView(false,"车辆载重量不能为空");
		}
		if(StringUtil.isEmpty(truck.getTruckBrand().getId())){
			return jsonView(false,"请选择车辆品牌");
		}
		 
		 
		
		
		if( truck.getAccount()==null|| StringUtil.isEmpty(truck.getAccount().getId())){
			truck.setAccount(null);
		}
		
		truck.setTruckStatus(TruckStatus.notransit);
		truckService.saveTruckNew(truck);
		
		return jsonView(true, "车辆修改成功");
	}
	
	/**
	 * 
	* 功能描述： 添加车辆轮轴属性
	* 输入参数:  @param companyid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "saveAxle", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> saveAxle(TruckAxle truckAxle,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(truckAxle.getTruck().getId())){
			return jsonView(false, "请选择车辆。");
		}
		if(StringUtil.isEmpty(truckAxle.getAxleType().getId())){
			return jsonView(false, "请选择轮轴类型。");
		}
		if(StringUtil.isEmpty(truckAxle.getBearingNum().getId())){
			return jsonView(false,"请选择轮轴数。");
		}
		truckAxleService.saveTruckAxle(truckAxle);
		return jsonView(true, "轮轴属性添加成功!");
	}
	/**
	 * 
	* 功能描述： 编辑车辆轮轴属性
	* 输入参数:  @param 
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "editAxle", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> editAxle(TruckAxle truckAxle,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(truckAxle.getTruck().getId())){
			return jsonView(false, "请选择车辆。");
		}
		if(StringUtil.isEmpty(truckAxle.getAxleType().getId())){
			return jsonView(false, "请选择轮轴类型。");
		}
		if(StringUtil.isEmpty(truckAxle.getBearingNum().getId())){
			return jsonView(false,"请选择轮轴数。");
		}
		truckAxleService.updateTruckAxle(truckAxle);
		return jsonView(true, "轮轴属性修改成功!");
	}
	/**
	 * 
	* 功能描述： 添加车辆货箱属性
	* 输入参数:  @param 
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "saveContainer", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> saveContainer(TruckContainer container,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(container.getTruck().getId())){
			return jsonView(false, "请选择车辆。");
		}
		if(StringUtil.isEmpty(container.getContainersType().getId())){
			return jsonView(false, "请选择货箱类型。");
		}
		if(container.getVolume()==null){
			return jsonView(false, "货箱体积不能为空。");
		}
		if(container.getContainers_long()==null){
			return jsonView(false, "货箱长度不能为空。");
		}
		if(StringUtil.isEmpty(container.getContainer_no())){
			return jsonView(false, "货箱号码不能为空。");
		}
//		if(container.getContainers_width()==null){
//			return jsonView(false, "货箱宽度不能为空。");
//		}
//		if(container.getContainers_height()==null){
//			return jsonView(false, "货箱高度不能为空。");
//		}
		truckContainerService.saveTruckContainer(container);
		return jsonView(true, "货箱属性添加成功!");
	}
	
	/**
	 * 
	* 功能描述： 编辑车辆货箱属性
	* 输入参数:  @param companyid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "editContainer", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> editContainer(TruckContainer container,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(container.getTruck().getId())){
			return jsonView(false, "请选择车辆。");
		}
		if(StringUtil.isEmpty(container.getContainersType().getId())){
			return jsonView(false, "请选择货箱类型。");
		}
		if(container.getVolume()==null){
			return jsonView(false, "货箱体积不能为空。");
		}
		if(container.getContainers_long()==null){
			return jsonView(false, "货箱长度不能为空。");
		}
		if(StringUtil.isEmpty(container.getContainer_no())){
			return jsonView(false, "货箱号码不能为空。");
		}
//		if(container.getContainers_width()==null){
//			return jsonView(false, "货箱宽度不能为空。");
//		}
//		if(container.getContainers_height()==null){
//			return jsonView(false, "货箱高度不能为空。");
//		}
		truckContainerService.updateTruckContainer(container);
		return jsonView(true, "货箱属性修改成功!");
	}
	/**
	 * 
	* 功能描述： 根据车辆编号查询货箱信息
	* 输入参数:  @param truckid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getContainerByTruckid")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getContainerByTruckid(String truckid,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list=truckContainerService.checkTruckContainerByTrucknoWithMap(truckid);
		return jsonView(true, "成功获取车辆货箱信息!",list);
	}
	
	/**
	 * 
	* 功能描述： 根据车辆编号查询轮轴信息
	* 输入参数:  @param truckid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getAxleByTruckid")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getAxleByTruckid(String truckid,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list=truckAxleService.checkTruckAxleByTrucknoWithMap(truckid);
		return jsonView(true, "成功获取车辆轮轴信息!",list);
	}
	/**
	 * 
	* 功能描述： 删除车辆
	* 输入参数:  @param companyid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "removeTruck", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> removeTruck(String truckid,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(truckid)){
			return jsonView(false, "请选择车辆。");
		}
		Truck truck=truckService.getTruckById(truckid);
		if(truck.getTruckStatus()==TruckStatus.transit){
			return jsonView(false, "该车辆正在运输货物中，不可删除。");
		}
		if(truck.getAccount()!=null){
			return jsonView(false, "该车辆已分配驾驶员，不可删除。");
		}
		try {
			truckService.removeTruck(truckid);
		} catch (Exception e) {
			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
		}
		return jsonView(true, "车辆删除成功!");
	}

	/**
	 * 
	* 功能描述： 查询车队车辆
	* 输入参数:  @param companyid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getTrucksByCommpany")
	@ResponseBody
	public Map<String, Object> getTrucksByCommpany(Integer page,Integer size,@RequestHeader HttpHeaders headers){
	 
		Account account = UserUtil.getAccount();
 
		Map<String, Object> map =truckService.getTrucksByCommpanyWithMap(account.getCompany().getId(),page,size);
		return jsonView(true, "成功获取商户下车辆列表!",map);
	}
	
	/**
	 * 
	* 功能描述： 根据车辆编号查询车辆
	* 输入参数:  @param truckid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getTruckById")
	@ResponseBody
	public Map<String, Object> getTruckById(String truckid,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list =truckService.getTruckByIdWithMap(truckid);
		return jsonView(true, "成功获取车辆信息!",list);
	}
	
	/**
	 * 
	* 功能描述： 根据驾驶员编号查询车辆
	* 输入参数:  @param accountid
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getTruckByAccountid")
	@ResponseBody
	public Map<String, Object> getTruckByAccountid(@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list =truckService.getTruckByAccountid(account.getId());
		return jsonView(true, "成功获取当前驾驶员车辆信息!",list);
	}
	
	/**
	* 功能描述： 根据商户查询车辆及绑定驾驶员
	* 输入参数:  @param 
	* 输入参数:  @param
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年8月28日 22:05:25
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "getTruckAndAccountList")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getTruckAndAccountList(@RequestHeader HttpHeaders headers) {
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list =  this.truckService.getTruckAndAccountListWithMap(account.getCompany().getId());
		return jsonView(true, "成功获取车队车辆及驾驶员绑定情况列表!",list);
	}
	/**
	 * 
	* 功 能 描 述：查询车队下的司机人员信息及车辆
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：Map<String, Object>
	 */
	@RequestMapping(value = "getCompantAccountTruckList")
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> getCompantAccountTruckList(@RequestHeader HttpHeaders headers) {
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		List<Map<String, Object>> list =  this.truckService.getCompantAccountTruckList(account.getCompany().getId());
		return jsonView(true, "成功获取车队司机及绑定车辆情况列表!",list);
	}

	/**
	 * 
	* 功能描述： 分配驾驶员
	* 输入参数:  @param 
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "distributeDriver", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> distributeDriver(String trackId,String accountId,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(trackId)){
			return jsonView(false, "请选择车辆。");
		}
		if(StringUtil.isEmpty(accountId)){
			return jsonView(false, "请选择驾驶员。");
		}
		this.truckService.updateTruckUserId(trackId, accountId, account.getCompany().getId());
		return jsonView(true, "人员分配成功!");
	}
	
	/**
	 * 
	* 功能描述： 取消绑定驾驶员
	* 输入参数:  @param 
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年6月15日下午9:15:29
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "removeBinding", method = RequestMethod.POST)
	@ResponseBody
	@UnInterceptor
	public Map<String, Object> removeBinding(String trackId,@RequestHeader HttpHeaders headers){
		String token = headers.getFirst("token");
		Account account = accountService.getAccountByToken(token);
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		if(StringUtil.isEmpty(trackId)){
			return jsonView(false, "请选择车辆。");
		}
		truckService.removeBinding(trackId, account.getCompany().getId());
		return jsonView(true, "车辆人员解绑成功！");
	}
	
	/**
	* 功能描述： 进入易林查询车辆页面
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/yilinindex", method = RequestMethod.GET)
	protected String yilinindex(Model model, HttpServletRequest request) {
		String url = "/truck/systruck/yilinindex";
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateListWithMap());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandListWithMap());
		return url;
	}

	
	
	

	/**
	* 功能描述： 进入人员分配页面
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日上午11:06:37
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/personnelAllotment", method = RequestMethod.GET)
	protected String personnelAllotment(Model model, HttpServletRequest request) {
		String url = "/truck/systruck/personnel_allotment";
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateListWithMap());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandListWithMap());
		return url;
	}
	
	/**
	* 功能描述： 人员选择
	* 输入参数:  @param id
	* 输入参数:  @param model
	* 输入参数:  @param request
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日上午11:06:55
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/confirmPerson", method = RequestMethod.GET)
	protected String confirmPerson(String id,Model model, HttpServletRequest request) {
		String url = "/truck/systruck/confirm_person";
		model.addAttribute("truck", this.truckService.getTruckByIdWithMap(id));
		return url;
	}
	
	/**
	* 功能描述： 修改车辆司机人员
	* 输入参数:  @param truck
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月15日上午11:07:49
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "updateConfirmPerson", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmPerson(String trackId,String distributionTrackId,String distributionUserId,String distributionUserName) {
		truckService.updateConfirmPerson(trackId, distributionTrackId, distributionUserId,distributionUserName);
		return jsonView(true, "车辆人员分配成功！");
	}
	
	/**
	* 功能描述：    
	* 输入参数:  @param trackId
	* 输入参数:  @param distributionTrackId
	* 输入参数:  @param distributionUserId
	* 输入参数:  @param distributionUserName
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: yangjiaqiao
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "updateChainBroken", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateChainBroken(String trackId) {
		truckService.updateChainBroken(trackId);
		return jsonView(true, "车辆人员解绑成功！");
	}
	
	/**
	* 功能描述： 车辆首页查询
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/index", method = RequestMethod.GET)
	protected String index(Model model, HttpServletRequest request) {
		Account user = UserUtil.getUser(request);
		String url = "/truck/systruck/index";
		if (user.getCompany().getCompanyType().getName().equals("个人司机")) {
			url = "/truck/systruck/driverindex";
		}
		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateListWithMap());
		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandListWithMap());
		return url;
	}
	@RequestMapping("/getTruckAndAccountWithMyCompany")
	@ResponseBody
	@AuthInterceptor
	public  Map<String, Object>  getTruckAndAccountWithMyCompany() {
		
		Account account = UserUtil.getAccount();
		List<Map<String, Object>> list = this.truckService.getTruckAndAccountByCompanyID(account.getCompany().getId());
		return jsonView( true	, "获取车队车辆信息成功", list);
		
	}
	
	@RequestMapping("/getTruckByID")
	@ResponseBody
	@AuthInterceptor
	public  Map<String, Object>  getTruckByID(String id) {
		
		Account account = UserUtil.getAccount();
		 Map<String, Object>  map = this.truckService.getTruckByID(id );
		return jsonView( true	, "获取车队车辆信息成功", map);
		
	}
	/**
	* 功能描述： 添加车辆
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "save", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> save(Truck truck) {
//		String msg = "";
//		boolean flag = true;
//		if (truckService.getTruckByName(truck.getTrack_no(), null)) {
//			this.truckService.saveTruck(truck);
//			msg = SAVE_SUCCESS;
//		} else {
//			msg = "[" + truck.getTrack_no() + "]车辆，在数据库已存在！";
//			flag = false;
//		}
//		return jsonView(flag, msg);
//	}
	/**
	* 功能描述：添加轮轴信息
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "addaxle", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addaxle(TruckAxle truckAxle) {
//		String msg = "";
//		boolean flag = true;
//		if (this.truckAxleService.checkTruckAxleByTruckno(truckAxle.getTruck().getId()) == null) {
//			this.truckAxleService.saveTruckAxle(truckAxle);
//			msg = SAVE_SUCCESS;
//		} else {
//			msg = "[" + truckAxle.getTruck().getTrack_no() + "]的车辆已有轮轴属性！";
//			flag = false;
//		}
//		return jsonView(flag, msg);
//	}
	/**
	* 功能描述： 添加货箱信息
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "addcontainer", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addcontainer(TruckContainer truckContainer) {
//		String msg = "";
//		boolean flag = true;
//		if (this.truckContainerService.checkTruckContainerByTruckno(truckContainer.getTruck().getId()) == null) {
//			this.truckContainerService.saveTruckContainer(truckContainer);
//			msg = SAVE_SUCCESS;
//		} else {
//			msg = "[" + truckContainer.getTruck().getTrack_no() + "]的车辆已有货箱属性！";
//			flag = false;
//		}
//		return jsonView(flag, msg);
//	}
	/**
	* 功能描述： 进入编辑车辆信息页面
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "/view/edit", method = RequestMethod.GET)
//	protected String edit(Model model, String id) {
//		model.addAttribute("truck", this.truckService.getTruckById(id));
//		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
//		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
//		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
//		model.addAttribute("enginebrandList", this.engineBrandService.getEngineBrandList());
//		return "/truck/systruck/edit";
//	}
	/**
	* 功能描述： 进入编辑车辆信息页面
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "/getTruckByAccountno", method = RequestMethod.GET)
//	protected String getTruckByAccountid(Model model) {
//		Account account = UserUtil.getUser();
//		model.addAttribute("truck", this.truckService.getTruckByAcountNo(account.getId()));
//		return "/truck/systruck/mytruck";
//	}

	/**
	* 功能描述： 检测车辆是否有轮轴信息
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "/view/track_axle", method = RequestMethod.GET)
//	protected String track_axle(Model model, String id) {
//		Truck truck = this.truckService.getTruckById(id);
//		TruckAxle truckAxle = this.truckAxleService.checkTruckAxleByTruckno(truck.getId());
//		if (truckAxle == null) {
//			model.addAttribute("truck", truck);
//			model.addAttribute("axleTypeList", this.axleTypeService.getAxleTypeList());
//			model.addAttribute("bearingNumList", this.bearingNumService.getBearingNumList());
//			return "/truck/systruck/addaxle";
//		} else {
//			model.addAttribute("truckAxle", truckAxle);
//			return "/truck/systruck/axledetail";
//		}
//	}

	/**
	* 功能描述： 检测车辆是否有货箱信息
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "/view/container", method = RequestMethod.GET)
//	protected String container(Model model, String id) {
//		Truck truck = this.truckService.getTruckById(id);
//		TruckContainer truckContainer = this.truckContainerService.checkTruckContainerByTruckno(truck.getId());
//		if (truckContainer == null) {
//			model.addAttribute("truck", truck);
//			model.addAttribute("containersTypeList", this.containersTypeService.getContainersTypeList());
//			return "/truck/systruck/addcontainer";
//		} else {
//			model.addAttribute("truckContainer", truckContainer);
//			return "/truck/systruck/containerdetail";
//		}
//	}

	/**
	* 功能描述： 获取车辆货箱轮轴详情
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "/getTruckDetail", method = RequestMethod.GET)
//	protected String getTruckDetail(Model model, String id) {
//		Truck truck = this.truckService.getTruckById(id);
//		TruckContainer truckContainer = this.truckContainerService.checkTruckContainerByTruckno(truck.getId());
//		TruckAxle truckAxle = this.truckAxleService.checkTruckAxleByTruckno(truck.getId());
//		model.addAttribute("truckContainer", truckContainer);
//		model.addAttribute("truckAxle", truckAxle);
//		model.addAttribute("truck", truck);
//		return "/truck/systruck/truckdetail";
//	}
	/**
	* 功能描述：进入添加车辆信息页面
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
//	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
//	protected String add(Model model, String id) {
//		model.addAttribute("trucktypeList", this.truckTypeService.getTruckTypeList());
//		model.addAttribute("truckplateList", this.truckPlateService.getTruckPlateList());
//		model.addAttribute("truckbrandList", this.truckBrandService.getTruckBrandList());
//		model.addAttribute("enginebrandList", this.engineBrandService.getEngineBrandList());
//		return "/truck/systruck/add";
//	}
	/**
	* 功能描述：编辑车辆信息
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
//	@RequestMapping(value = "edit", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> edit(Truck truck) {
//		String msg = "";
//		boolean flag = true;
//		if (truckService.getTruckByName(truck.getTrack_no(), truck.getId())) {
//			this.truckService.updateTruck(truck);
//			msg = UPDATE_SUCCESS;
//		} else {
//			flag = false;
//			msg = "[" + truck.getTrack_no() + "]车辆，在数据库已存在！";
//		}
//		return jsonView(flag, msg);
//	}
	/**
	* 功能描述： 检查车牌号是否存在
	* 输入参数:  @param String track_no
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：boolean
	 */
//	@RequestMapping(value = "checkNo", method = RequestMethod.POST)
//	@ResponseBody
//	public boolean checkNo(Model model, String track_no, String old, HttpServletRequest request) {
//		if (StringUtil.isNotEmpty(old)) {
//			if (track_no.equals(old)) {
//				return true;
//			}
//		}
//		Truck truck = truckService.checkTruckByNo(track_no);
//		return truck == null ? true : false;
//	}
	/**
	* 功能描述： 删除车辆
	* 输入参数:  @param ids
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
//	@RequestMapping(value = "remove")
//	@ResponseBody
//	public Map<String, Object> remove(String ids) {
//		try {
//			String[] resArr = ids.split(",");
//			for (String resId : resArr) {
//				this.truckService.removeTruck(resId);
//			}
//		} catch (Exception e) {
//			return jsonView(false, "系统异常，或者该数据已关联其他项目！");
//		}
//
//		return jsonView(true, REMOVE_SUCCESS);
//	}
	
	@RequestMapping(value = "getTruckByCompanyIdPage", method = RequestMethod.POST)
	@ResponseBody
	@AuthInterceptor
	public  Map<String, Object>  getTruckByCompanyIdPage(int start,int limit) {
		Account account = UserUtil.getAccount();
		Map<String,Object> map = this.truckService.getTruckByCompanyIdPage(account.getCompany().getId(),start,limit);
		return jsonView( true	, "获取车队车辆信息成功",map);
	}
	
	/**
	 * aiqiwu 2017-09-20
	 *根据账号ID获取车辆信息
	 */
	@RequestMapping(value = "getTruckByAccountIdForMap")
	@ResponseBody
	public Map<String, Object> getTruckByAccountIdForMap(){
		Account account = UserUtil.getAccount();
		if(null == account){
			return jsonView(false, "请重新登录。");
		}
		Map<String, Object> map =truckService.getTruckByAccountIdForMap(account.getId());
		return jsonView(true, "成功获取当前驾驶员车辆信息!",map);
	}
}
