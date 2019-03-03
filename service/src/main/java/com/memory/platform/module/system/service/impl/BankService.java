package com.memory.platform.module.system.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.sys.Bank;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.OSSClientUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.module.system.dao.BankDao;
import com.memory.platform.module.system.service.IBankService;
/**
* 创 建 人： longqibo
* 日    期： 2016年7月25日 上午10:02:26 
* 修 改 人： 
* 日   期： 
* 描   述： 	银行服务类接口
* 版 本 号：  V1.0
 */
@Service
public class BankService implements IBankService {
	
	@Autowired
	private BankDao bankDao;

	@Override
	public void saveInfo(Bank bank,String path) throws IOException {
		if(StringUtil.isEmpty(bank.getImage())){
			throw new BusinessException("上传图片出错。");
		}
		ImageFileUtil.move(new File(path+bank.getImage()), new File(path+bank.getImage().substring(0, bank.getImage().lastIndexOf("/")).replace("temporary", "uploading")));
		FileInputStream fin = new FileInputStream(new File(path+bank.getImage().replace("temporary", "uploading")));
		String url = OSSClientUtil.uploadFile2OSS(fin, bank.getImage().substring(bank.getImage().lastIndexOf("/"), bank.getImage().length()),"bank");//此处是上传到阿里云OSS的步骤
		bank.setImage(url);
		
		ImageFileUtil.move(new File(path+bank.getMarkImage()), new File(path+bank.getMarkImage().substring(0, bank.getMarkImage().lastIndexOf("/")).replace("temporary", "uploading")));
		FileInputStream fileInputStream = new FileInputStream(new File(path+bank.getMarkImage().replace("temporary", "uploading")));
		String markUrl = OSSClientUtil.uploadFile2OSS(fileInputStream, bank.getMarkImage().substring(bank.getMarkImage().lastIndexOf("/"), bank.getMarkImage().length()),"bank");//此处是上传到阿里云OSS的步骤
		bank.setMarkImage(markUrl);
		
		ImageFileUtil.delete(new File(path + bank.getImage()));
		ImageFileUtil.delete(new File(path + bank.getMarkImage()));
		bankDao.save(bank);
	}

	@Override
	public void updateInfo(Bank bank,String path) throws IOException {
		Bank prent = getBankById(bank.getId());
		if(!bank.getImage().equals(prent.getImage())){
			ImageFileUtil.move(new File(path+bank.getImage()), new File(path+bank.getImage().substring(0, bank.getImage().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+bank.getImage().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, bank.getImage().substring(bank.getImage().lastIndexOf("/"), bank.getImage().length()),"bank");//此处是上传到阿里云OSS的步骤
			bank.setImage(url);
			ImageFileUtil.delete(new File(path + prent.getImage()));
			ImageFileUtil.delete(new File(path + bank.getImage()));
			prent.setImage(bank.getImage());
		}
		if(!bank.getMarkImage().equals(prent.getMarkImage())){
			ImageFileUtil.move(new File(path+bank.getMarkImage()), new File(path+bank.getMarkImage().substring(0, bank.getMarkImage().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(new File(path+bank.getMarkImage().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, bank.getMarkImage().substring(bank.getMarkImage().lastIndexOf("/"), bank.getMarkImage().length()),"bank");//此处是上传到阿里云OSS的步骤
			bank.setMarkImage(url);
			ImageFileUtil.delete(new File(path + prent.getMarkImage()));
			ImageFileUtil.delete(new File(path + bank.getMarkImage()));
			prent.setMarkImage(bank.getMarkImage());
		}
		prent.setCnName(bank.getCnName());
		prent.setShortName(bank.getShortName());
		bankDao.update(prent);
	}

	@Override
	public Bank getBankByShortName(String shortName) {
		return bankDao.getBankByShortName(shortName);
	}

	@Override
	public Bank getBankById(String id) {
		return bankDao.getObjectById(Bank.class, id);
	}

	@Override
	public Map<String, Object> getPage(Bank bank, int start, int limit) {
		return bankDao.getPage(bank, start, limit);
	}

	@Override
	public Bank getBankByCnName(String cnName) {
		return bankDao.getBankByCnName(cnName);
	}

	@Override
	public void removeInfo(String id) {
		bankDao.delete(getBankById(id));
	}

}
