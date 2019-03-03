package com.memory.platform.module.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.entity.goods.GoodsType;
import com.memory.platform.module.goods.dao.GoodsTypeDao;
import com.memory.platform.module.goods.service.IGoodsTypeService;
/**
* 创 建 人： longqibo
* 日    期： 2016年6月2日 下午2:14:21 
* 修 改 人： 
* 日   期： 
* 描   述： 货物类型 － 服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class GoodsTypeService implements IGoodsTypeService {

	@Autowired
	private GoodsTypeDao goodsTypeDao;
	
	@Override
	public void saveGoodsType(GoodsType goodsType) {
		if(goodsType.getPrentId().equals("sys_goods_type")){
			goodsType.setLevel(1);
			goodsType.setPrentId("-1");
		}else{
			goodsType.setLevel(2);
		}
		goodsTypeDao.save(goodsType);
	}

	@Override
	public void updateGoodsType(GoodsType goodsType) {
		GoodsType goodsTypep = goodsTypeDao.getObjectById(GoodsType.class, goodsType.getId());
		goodsTypep.setName(goodsType.getName());
		goodsTypep.setDescs(goodsType.getDescs());
		goodsTypep.setUpdate_time(goodsType.getUpdate_time());
		goodsTypep.setUpdate_user_id(goodsType.getUpdate_user_id());
		goodsTypeDao.merge(goodsTypep);
	}

	@Override
	public void removeGoodsType(String id) {
		goodsTypeDao.delete(getGoodsTypeById(id));
	}

	@Override
	public GoodsType getGoodsTypeById(String id) {
		return goodsTypeDao.getObjectById(GoodsType.class, id);
	}

	/*  
	 *  getList
	 */
	@Override
	public List<GoodsType> getAllList() {
		return goodsTypeDao.getAllList();
	}
	
	@Override
	public List<Map<String, Object>> getGoodsType(){
		return goodsTypeDao.getGoodsType();
	}

	/*  
	 *  getListByLeav
	 */
	@Override
	public List<GoodsType> getListByLeav() {
		return goodsTypeDao.getListByLeav();
	}

	/*  
	 *  getGoodsTypeByName
	 */
	@Override
	public GoodsType getGoodsTypeByName(String name,String id) {
		// TODO Auto-generated method stub
		return goodsTypeDao.getGoodsTypeByName(name, id);
	}

	/*  
	 *  getListByPrentId
	 */
	@Override
	public List<GoodsType> getListByPrentId(String id) {
		// TODO Auto-generated method stub
		return goodsTypeDao.getGoodsTypeByPrentId(id);
	}

	/*  
	 *  getListMapByPrentId
	 */
	@Override
	public List<Map<String, Object>> getListMapByPrentId(String id) {
		String sql = "SELECT a.id as value,a.name as text FROM goods_type a where a.prent_id = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return goodsTypeDao.getListBySQLMap(sql, map);
	}

}
