package com.memory.platform.module.truck.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.memory.platform.entity.truck.TruckImg;
import com.memory.platform.module.global.dao.BaseDao;

/**
* 
* 创    建    人：liyanzhang
* 日             期： 2016年5月30日 19:10:47
* 修    改    人：
* 修 改 日 期：
* 描             述：车辆图片DAO层
* 版    本    号：  V1.0
*/
@Repository
public class TruckImgDao extends BaseDao<TruckImg> {

	
	
	/**
	 *
	* 功 能 描 述：通过车辆图片信息id号获取实体
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：truckImg
	 */
	public TruckImg getTruckImgById(String id) {
		return this.getObjectById(TruckImg.class,id);
	}
	/**
	 *
	* 功 能 描 述：通过车辆ID获取图片信息
	* 输 入 参 数： @param id
	* 输 入 参 数： @return
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:11:54
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：TruckImg
	 */
	public TruckImg checkTruckImgByTruckno(String no) {
		String sql = " from TruckImg truckImg where truckImg.truck.id=:id";
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("id", no);
		TruckImg truckImg=this.getObjectByColumn(sql, map);
		return truckImg;
	}
	
	/**
	 * 
	* 功 能 描 述：保存车辆图片信息
	* 输 入 参 数： @param TruckImg
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:15:13
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void saveTruckImg(TruckImg truckImg) {
		this.save(truckImg);
	}
	
	/**
	 * 
	* 功 能 描 述：更新车辆图片信息
	* 输 入 参 数： @param TruckImg
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:16:35
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void updateTruckImg(TruckImg truckImg) {
		this.update(truckImg);
	}
	
	
	
	
	/**
	 * 
	* 功 能 描 述：删除车辆图片信息
	* 输 入 参 数： @param id
	* 异             常：
	* 创    建    人： liyanzhang
	* 日             期： 2016年5月30日 19:21:53
	* 修    改    人：
	* 修 改 日 期：
	* 返             回：void
	 */
	public void removeTruckImg(String id){
		TruckImg  truckImg = new TruckImg();
		truckImg.setId(id);
		this.delete(truckImg);
	}
	
	
}
