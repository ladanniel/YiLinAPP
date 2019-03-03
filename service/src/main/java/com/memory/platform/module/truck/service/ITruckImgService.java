package com.memory.platform.module.truck.service;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.truck.TruckImg;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆图片信息业务层
* 版    本    号：  V1.0
*/
public interface ITruckImgService {
	/**
	* 功 能 描 述：根据车辆id查询车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckImg
	 */
	public TruckImg checkTruckImgByTruckno(String no);
	/**
	* 功 能 描 述：根据图片信息id查询图片
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckImg
	 */
	public TruckImg getTruckImgById(String id);
	/**
	* 功 能 描 述：保存车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckImg(TruckImg truckImg,String path)throws IOException;
	/**
	* 功 能 描 述：修改车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckImg(TruckImg truckImg,String path)throws IOException;
	/**
	* 功 能 描 述：删除车辆图片信息
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年6月12日 16:10:44
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckImg(String id);
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
	public String uploadImage(MultipartFile file) throws IOException;
	/**
	* 功能描述： 获取图片上传路径
	* 输入参数:  @param MultipartFile
	* 输入参数:  @param 
	* 异    常： 
	* 创 建 人:liyanzhang
	* 日    期: 2016年7月15日上午11:18:01
	* 修 改 人: 
	* 日    期: 
	* 返    回：图片
	 * @throws IOException 
	 */
	public TruckImg  getTruckImagePath(TruckImg truckImg,String path,String type) throws IOException;
}
