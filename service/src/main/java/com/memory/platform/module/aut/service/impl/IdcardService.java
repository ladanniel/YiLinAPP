package com.memory.platform.module.aut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.module.aut.dao.IdcardDao;
import com.memory.platform.module.aut.service.IIdcardService;
/**
* 创 建 人： yangjiaqiao
* 日    期： 2016年5月4日 下午4:54:32 
* 修 改 人： 
* 日   期： 
* 描   述： 身份证业务接口实现类
* 版 本 号：  V1.0
 */
@Service
public class IdcardService implements IIdcardService {

	
	@Autowired
	private IdcardDao idcardDao;
	/*  
	 *  get
	 */
	@Override
	public Idcard get(String idcard_id) {
		// TODO Auto-generated method stub
		return idcardDao.getObjectById(Idcard.class, idcard_id);
	}

	/*  
	 *  getNo
	 */
	@Override
	public Idcard getNo(String no,String id) {
		// TODO Auto-generated method stub
		return idcardDao.getIdcardByNo(no,id);
	}

	@Override
	public Idcard getAccountId(String accountId) {
		// TODO Auto-generated method stub
		return idcardDao.getAccountId(accountId);
	}

	 
}
