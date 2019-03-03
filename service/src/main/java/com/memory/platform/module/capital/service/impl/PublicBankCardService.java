package com.memory.platform.module.capital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.module.capital.dao.PublicBankCardDao;
import com.memory.platform.module.capital.service.IPublicBankCardService;
/**
* 创 建 人： longqibo
* 日    期： 2016年4月26日 下午7:21:11 
* 修 改 人： 
* 日   期： 
* 描   述： 对公账户服务类
* 版 本 号：  V1.0
 */
@Service
@Transactional
public class PublicBankCardService implements IPublicBankCardService {

	@Autowired
	private PublicBankCardDao publicBankCardDao;
}
