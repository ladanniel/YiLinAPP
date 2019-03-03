package com.memory.platform.module.truck.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.truck.TruckImg;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.OSSClientUtil;
import com.memory.platform.module.truck.dao.TruckImgDao;
import com.memory.platform.module.truck.service.ITruckImgService;
/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆图片业务层实现类
* 版    本    号：  V1.0
*/
@Service
public class TruckImgService implements ITruckImgService {
	@Autowired
	private TruckImgDao truckImgDao;
	/**
	* 功 能 描 述：根据车辆id查询图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckImg
	 */
	@Override
	public TruckImg checkTruckImgByTruckno(String no) {
		return truckImgDao.checkTruckImgByTruckno(no);
	}
	/**
	* 功 能 描 述：根据id查询图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckAxle
	 */
	@Override
	public TruckImg getTruckImgById(String id) {
		return truckImgDao.getTruckImgById(id);
	}
	/**
	* 功 能 描 述：保存车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 * @throws IOException 
	 */
	@Override
	public void saveTruckImg(TruckImg truckImg,String path) throws IOException {
		truckImg=this.getTruckImagePath(truckImg, path,"save");
		truckImgDao.saveTruckImg(truckImg);
	}
	/**
	* 功 能 描 述：修改车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void updateTruckImg(TruckImg truckImg,String path)throws IOException{
		truckImg=this.getTruckImagePath(truckImg, path,"edit");
		truckImgDao.updateTruckImg(truckImg);
	}
	/**
	* 功 能 描 述：删除车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	@Override
	public void removeTruckImg(String id) {
		truckImgDao.removeTruckImg(id);
	}
	/**
	* 功能描述： 图片上传
	* 输入参数:  @param MultipartFile
	* 输入参数:  @param 
	* 异    常： 
	* 创 建 人:liyanzhang
	* 日    期: 2016年7月15日上午11:18:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：图片路径
	 * @throws IOException 
	 */
	@Override
	public String uploadImage(MultipartFile file) throws IOException{
		String filename=UUID.randomUUID()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		FileInputStream fin=(FileInputStream) file.getInputStream();
		//String url  = OSSClientUtil.uploadFile2OSS(fin,filename,"truck");//此处是上传到阿里云OSS的步骤
		String url  = OSSClientUtil.uploadFile2OSS(fin,filename,"vehiclelicense");
		return url;
	}
	/**
	* 功能描述： 获取图片上传路径
	* 输入参数:  @param
	* 输入参数:  @param 
	* 异    常： 
	* 创 建 人:liyanzhang
	* 日    期: 2016年7月15日上午11:18:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：图片路径
	 * @throws IOException 
	 */
	@Override
	public TruckImg getTruckImagePath(TruckImg truckImg, String path,String type) throws IOException {
		TruckImg img=null;
		if("edit".equals(type)){
			img = truckImgDao.getObjectById(TruckImg.class, truckImg.getId());
		}
		if(truckImg.getTrack_ahead_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_ahead_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_ahead_img()), new File(path+truckImg.getTrack_ahead_img().substring(0, truckImg.getTrack_ahead_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_ahead_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_ahead_img().substring(truckImg.getTrack_ahead_img().lastIndexOf("/"), truckImg.getTrack_ahead_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_ahead_img(url);
		}
		if(truckImg.getTrack_left_front_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_left_front_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_left_front_img()), new File(path+truckImg.getTrack_left_front_img().substring(0, truckImg.getTrack_left_front_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_left_front_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_left_front_img().substring(truckImg.getTrack_left_front_img().lastIndexOf("/"), truckImg.getTrack_left_front_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_left_front_img(url);
		}
		if(truckImg.getTrack_right_front_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_right_front_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_right_front_img()), new File(path+truckImg.getTrack_right_front_img().substring(0, truckImg.getTrack_right_front_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_right_front_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_right_front_img().substring(truckImg.getTrack_right_front_img().lastIndexOf("/"), truckImg.getTrack_right_front_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_right_front_img(url);
		}
		if(truckImg.getTrack_abehind_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_abehind_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_abehind_img()), new File(path+truckImg.getTrack_abehind_img().substring(0, truckImg.getTrack_abehind_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_abehind_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_abehind_img().substring(truckImg.getTrack_abehind_img().lastIndexOf("/"), truckImg.getTrack_abehind_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_abehind_img(url);
		}
		if(truckImg.getTrack_behind_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_behind_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_behind_img()), new File(path+truckImg.getTrack_behind_img().substring(0, truckImg.getTrack_behind_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_behind_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_behind_img().substring(truckImg.getTrack_behind_img().lastIndexOf("/"), truckImg.getTrack_behind_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_behind_img(url);
		}
		if(truckImg.getTrack_side_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_side_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_side_img()), new File(path+truckImg.getTrack_side_img().substring(0, truckImg.getTrack_side_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_side_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_side_img().substring(truckImg.getTrack_side_img().lastIndexOf("/"), truckImg.getTrack_side_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_side_img(url);
		}
		if(truckImg.getTrack_console_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_console_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_console_img()), new File(path+truckImg.getTrack_console_img().substring(0, truckImg.getTrack_console_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_console_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_console_img().substring(truckImg.getTrack_console_img().lastIndexOf("/"), truckImg.getTrack_console_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_console_img(url);
		}
		if(truckImg.getTrack_dashboard_img().indexOf("temporary")>-1){
			if(img != null){
				ImageFileUtil.delete(new File(path+img.getTrack_dashboard_img()));
			}
			ImageFileUtil.move(new File(path+truckImg.getTrack_dashboard_img()), new File(path+truckImg.getTrack_dashboard_img().substring(0, truckImg.getTrack_dashboard_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+truckImg.getTrack_dashboard_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, truckImg.getTrack_dashboard_img().substring(truckImg.getTrack_dashboard_img().lastIndexOf("/"), truckImg.getTrack_dashboard_img().length()),"truck");//此处是上传到阿里云OSS的步骤
		    truckImg.setTrack_dashboard_img(url);
		}
		
		return truckImg;
	}
	

}
















