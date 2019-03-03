package com.memory.platform.module.truck.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.core.AppUtil;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.truck.Truck;
import com.memory.platform.entity.truck.TruckAxle;
import com.memory.platform.entity.truck.TruckImg;
import com.memory.platform.entity.truck.TruckType;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.UserUtil;
import com.memory.platform.module.global.controller.BaseController;
import com.memory.platform.module.truck.service.ITruckImgService;
import com.memory.platform.module.truck.service.ITruckService;
import com.memory.platform.module.truck.service.ITruckTypeService;

@Controller
@RequestMapping("/truck/truckimg")
public class TruckImgController extends BaseController {
	
	@Autowired
	private ITruckImgService truckImgService;
	
	@Autowired
	private ITruckService truckService;
	
	
	/**
	* 功能描述： 添加车辆图片
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(TruckImg truckImg, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String path = AppUtil.getUpLoadPath(request);
		try {
			truckImgService.saveTruckImg(truckImg,path);
			map.put("success", true);
			map.put("msg", "车辆图片信息添加成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
		}
		return map;
	}
	/**
	* 功能描述： 修改车辆图片
	* 输入参数:  @param Truck truck
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(TruckImg truckImg, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String path = AppUtil.getUpLoadPath(request);
		try {
			truckImgService.saveTruckImg(truckImg,path);
			map.put("success", true);
			map.put("msg", "车辆图片信息添加成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	
	
	/**
	* 功能描述： 检测车辆是否有图片信息
	* 输入参数:  @param String id
	* 异    常： 
	* 创 建 人: liyanzhang
	* 日    期: 2016年7月18日上午10:17:56
	* 修 改 人: 
	* 日    期: 
	* 返    回：String
	 */
	@RequestMapping(value = "/view/track_img", method = RequestMethod.GET)
	protected String track_img(Model model, String id) {
		Truck truck = this.truckService.getTruckById(id);
		TruckImg truckImg=this.truckImgService.checkTruckImgByTruckno(truck.getId());
		if (truckImg == null) {
			model.addAttribute("truck", truck);
			return "/truck/systruck/addimg";
		} else {
			model.addAttribute("truckImg", truckImg);
			return "/truck/systruck/imgdetail";
		}
	}
	/**
	* 功能描述： 文件上传
	* 输入参数:  @param request
	* 输入参数:  @param file
	* 输入参数:  @return
	* 输入参数:  @throws IllegalStateException
	* 输入参数:  @throws IOException
	* 异    常： 
	* 创 建 人:liyanzhang
	* 日    期: 2016年8月5日下午7:08:51
	* 修 改 人: 
	* 日    期: 
	* 返    回：Map<String,Object>
	 */
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> uploadimg( HttpServletRequest request,@RequestParam(value = "file",required=false) MultipartFile file) throws IllegalStateException, IOException{
		String dataType = request.getParameter("datatype");
		Map<String, Object> map = new HashMap<String, Object>();
		if(file.isEmpty()){
            System.out.println("【文件为空！】");
    		map.put("success", false);
    		map.put("msg", "文件为空！");
        }
		String img_path = ImageFileUtil.uploadTemporary(file,AppUtil.getUpLoadPath(request),dataType) ; 
//		String img_path=truckImgService.uploadImage(file);
		System.out.println(img_path);
		map.put("success", true);
		map.put("msg", "上传成功！");
		map.put("imgpath", img_path);
        return  map;
	}
}


































