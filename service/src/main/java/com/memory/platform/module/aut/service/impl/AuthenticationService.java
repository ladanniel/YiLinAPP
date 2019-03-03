package com.memory.platform.module.aut.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memory.platform.entity.aut.BusinessLicense;
import com.memory.platform.entity.aut.DrivingLicense;
import com.memory.platform.entity.aut.Idcard;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.sys.Company;
import com.memory.platform.global.Auth;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.ImageFileUtil;
import com.memory.platform.global.OSSClientUtil;
import com.memory.platform.module.aut.dao.BusinessLicenseDao;
import com.memory.platform.module.aut.dao.DrivingLicenseDao;
import com.memory.platform.module.aut.dao.IdcardDao;
import com.memory.platform.module.aut.service.IAuthenticationService;
import com.memory.platform.module.system.dao.AccountDao;
import com.memory.platform.module.system.dao.CompanyDao;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年5月4日 下午4:54:32 修 改 人： 日 期： 描 述： 版 本 号： V1.0
 */
@Service
public class AuthenticationService implements IAuthenticationService {
	@Autowired
	private IdcardDao idcardDao;// 身份证DAO
	@Autowired
	private DrivingLicenseDao drivingLicenseDao;// 驾驶证DAO
	@Autowired
	private BusinessLicenseDao businessLicenseDao;// 营业执照DAO
	@Autowired
	private CompanyDao companyDao;// 商户DAO
	@Autowired
	private AccountDao accountDao;// 用户DAO

	/*
	 * saveAuthenticationInfo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveAuthenticationCompantInfo(Company company, Idcard idcard,
			DrivingLicense drivingLicense, BusinessLicense businessLicense, String path, Account account, String type,
			String aut_supplement_type) throws Exception {
		Map<String, Object> map_check = this.getCheckAuthenticationInfo(company, idcard, drivingLicense,
				businessLicense, type);

		if ((boolean) map_check.get("isfalse")) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if (type.equals("supplement") || type.equals("supplementedit")
						|| account.getCompany().getSource() == 0) {
					company = account.getCompany();
				} else {
					company.setId(account.getCompany().getId());
					company.setAdd_user_id(account.getCompany().getAdd_user_id());
				}
				if (idcard != null && idcard.getIdcard_no() != null && !"".equals(idcard.getIdcard_no())) {
					idcard.setAdd_user_id(account.getId());
					idcard.setCreate_time(new Date());
					idcard.setAccount_id(account.getId());
					idcard.setCompany_id(company.getId());
					idcard = this.getIdcardImagePath(idcard, path, type);
					if (type.equals("save")) {
						idcardDao.save(idcard);
					} else if (type.equals("edit")) {
						if (StringUtils.isBlank(idcard.getId())) {
							idcardDao.save(idcard);
						} else {
							idcardDao.merge(idcard);
						}
					} else if (type.equals("supplement")) {
						idcardDao.save(idcard);
					} else if (type.equals("supplementedit")) {
						idcardDao.merge(idcard);
					}
					company.setIdcard_id(idcard.getId());
				}
				if (drivingLicense != null && drivingLicense.getDriving_license_no() != null
						&& !"".equals(drivingLicense.getDriving_license_no())) {
					drivingLicense.setAdd_user_id(account.getId());

					drivingLicense.setAccount_id(account.getId());
					drivingLicense.setCompany_id(company.getId());
					drivingLicense.setValid_end_date(DateUtil.getDateAddYear(drivingLicense.getValid_start_date(),
							drivingLicense.getValid_year()));
					drivingLicense = this.getDrivingLicenseImagePath(drivingLicense, path, type);
					if (type.equals("save")) {
						drivingLicense.setCreate_time(new Date());
						drivingLicenseDao.save(drivingLicense);
					} else if (type.equals("edit")) {
						if (StringUtils.isBlank(drivingLicense.getId())) {
							drivingLicenseDao.save(drivingLicense);
						} else {
							drivingLicenseDao.merge(drivingLicense);
						}
					} else if (type.equals("supplement")) {
						drivingLicenseDao.save(drivingLicense);
					} else if (type.equals("supplementedit")) {
						drivingLicenseDao.merge(drivingLicense);
					}
					company.setDriving_license_id(drivingLicense.getId());
				}
				if (businessLicense != null && businessLicense.getCompany_no() != null
						&& !"".equals(businessLicense.getCompany_no())) {
					businessLicense.setAdd_user_id(account.getId());
					businessLicense.setCreate_time(new Date());
					businessLicense.setAccount_id(account.getId());
					businessLicense = this.getBusinessLicenseImagePath(businessLicense, path, type);
					if (type.equals("save")) {
						businessLicenseDao.save(businessLicense);
					} else if (type.equals("edit")) {
						if (StringUtils.isBlank(businessLicense.getId())) {
							businessLicenseDao.save(businessLicense);
						} else {
							businessLicenseDao.merge(businessLicense);
						}
					} else if (type.equals("supplement")) {
						businessLicenseDao.save(businessLicense);
					} else if (type.equals("supplementedit")) {
						businessLicenseDao.merge(businessLicense);
					}
					company.setBusiness_license_id(businessLicense.getId());
				}
				company.setUpdate_user_id(account.getId());
				company.setUpdate_time(new Date());
				if (company.getAudit() != null && company.getAudit().equals(Auth.supplement)) {
					company.setAudit(Auth.waitProcessSupplement);
					company.setAut_supplement_type(aut_supplement_type);
					account.setAuthentication(Auth.waitProcessSupplement);
					account.setAut_supplement_type(aut_supplement_type);
				} else {
					company.setAudit(Auth.waitProcess);
					account.setAuthentication(Auth.waitProcess);
				}
				accountDao.merge(account);
				company.setCompanyType(account.getCompany().getCompanyType());
				company.setStatus(account.getCompany().getStatus());
				company.setSource(account.getCompany().getSource());
				company.setAccount_id(account.getCompany().getAccount_id());
				company.setUpdate_user_id(account.getId());
				company.setUpdate_time(new Date());
				companyDao.merge(company);
				map.put("success", true);
				map.put("msg", "认证信息提交成功，等待管理员审核！");
			} catch (Exception e) {
				map.put("success", false);
				map.put("msg", e.getMessage());
			}
			return map;
		} else {
			return (Map<String, Object>) map_check.get("map");
		}

	}

	/*
	 * saveAuthenticationUserInfo
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveAuthenticationUserInfo(Idcard idcard, DrivingLicense drivingLicense, String path,
			Account account, String type, String aut_supplement_type) throws Exception {
		Map<String, Object> map_check = this.getCheckAuthenticationInfo(null, idcard, drivingLicense, null, type);
		if ((boolean) map_check.get("isfalse")) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if (idcard != null && idcard.getIdcard_no() != null && !"".equals(idcard.getIdcard_no())) {
					idcard.setAdd_user_id(account.getId());
					idcard.setCreate_time(new Date());
					idcard.setAccount_id(account.getId());
					idcard = this.getIdcardImagePath(idcard, path, type);
					if (type.equals("save")) {
						idcardDao.save(idcard);
					} else if (type.equals("edit")) {
						if (StringUtils.isBlank(idcard.getId())) {
							idcardDao.save(idcard);
						} else {
							idcardDao.merge(idcard);
						}
					} else if (type.equals("supplement")) {
						idcardDao.save(idcard);
					} else if (type.equals("supplementedit")) {
						idcardDao.merge(idcard);
					}
					account.setIdcard_id(idcard.getId());
				}
				if (drivingLicense != null && drivingLicense.getDriving_license_no() != null
						&& !"".equals(drivingLicense.getDriving_license_no())) {
					drivingLicense.setAdd_user_id(account.getId());
					drivingLicense.setCreate_time(new Date());
					drivingLicense.setAccount_id(account.getId());
					drivingLicense.setValid_end_date(DateUtil.getDateAddYear(drivingLicense.getValid_start_date(),
							drivingLicense.getValid_year()));
					drivingLicense = this.getDrivingLicenseImagePath(drivingLicense, path, type);
					if (type.equals("save")) {
						drivingLicenseDao.save(drivingLicense);
					} else if (type.equals("edit")) {
						if (StringUtils.isBlank(drivingLicense.getId())) {
							drivingLicenseDao.save(drivingLicense);
						} else {
							drivingLicenseDao.merge(drivingLicense);
						}
					} else if (type.equals("supplement")) {
						drivingLicenseDao.save(drivingLicense);
					} else if (type.equals("supplementedit")) {
						drivingLicenseDao.merge(drivingLicense);
					}
					account.setDriving_license_id(drivingLicense.getId());
				}
				account.setUpdate_user_id(account.getId());
				account.setUpdate_time(new Date());
				if (account.getAuthentication() != null && account.getAuthentication().equals(Auth.supplement)) {
					account.setAuthentication(Auth.waitProcessSupplement);
					account.setAut_supplement_type(aut_supplement_type);
				} else {
					account.setAuthentication(Auth.waitProcess);
				}
				accountDao.update(account);
				map.put("success", true);
				map.put("msg", "认证信息提交成功，等待管理员审核！");
			} catch (Exception e) {
				map.put("success", false);
				map.put("msg", e.getMessage());
			}
			return map;
		} else {
			return (Map<String, Object>) map_check.get("map");
		}
	}

	/**
	 * 功能描述： 验证数据的准确性 输入参数: @param company //商户对象 输入参数: @param idcard //身份证对象
	 * 输入参数: @param drivingLicense //驾驶证对象 输入参数: @param businessLicense //营业执照对象
	 * 输入参数: @param type //save:保存、edit:修改 输入参数: @return 异 常： 创 建 人: yangjiaqiao
	 * 日 期: 2016年5月9日下午6:40:14 修 改 人: 日 期: 返 回：Map<String,Object>
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> getCheckAuthenticationInfo(Company company, Idcard idcard, DrivingLicense drivingLicense,
			BusinessLicense businessLicense, String type) throws Exception {
		Map<String, Object> map_result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isfalse = true;
		if (!type.equals("supplement") && !type.equals("supplementedit")) {
			if (company != null) {
				Company company_check = companyDao.getCompanyByName(company.getName(),
						type.equals("save") || type.equals("supplement") ? null : company.getId());
				if (company_check != null) {
					map.put("success", false);
					map.put("msg", "【" + company.getName() + "】商户名称已经存在!");
					isfalse = false;
					map_result.put("isfalse", isfalse);
					map_result.put("map", map);
					return map_result;
				}
			}
		}
		if (idcard != null && !StringUtils.isBlank(idcard.getIdcard_no())) {
			Idcard idcard_check = idcardDao.getIdcardByNo(idcard.getIdcard_no(),
					type.equals("save") || type.equals("supplement") ? null : idcard.getId());
			if (idcard_check != null) {
				map.put("success", false);
				map.put("msg", "【" + idcard.getIdcard_no() + "】身份证号已经存在!");
				isfalse = false;
				map_result.put("isfalse", isfalse);
				map_result.put("map", map);
				return map_result;
			}
		}
		if (drivingLicense != null && !StringUtils.isBlank(drivingLicense.getDriving_license_no())) {
			DrivingLicense drivingLicense_check = drivingLicenseDao.getDrivingLicenseByNo(
					drivingLicense.getDriving_license_no(),
					type.equals("save") || type.equals("supplement") ? null : drivingLicense.getId());
			if (drivingLicense_check != null) {
				map.put("success", false);
				map.put("msg", "【" + drivingLicense.getDriving_license_no() + "】驾驶证编号已经存在!");
				isfalse = false;
				map_result.put("isfalse", isfalse);
				map_result.put("map", map);
				return map_result;
			}
		}
		if (businessLicense != null && !StringUtils.isBlank(businessLicense.getCompany_no())) {
			BusinessLicense businessLicense_check = businessLicenseDao.getBusinessLicenseByNo(
					businessLicense.getCompany_no(),
					type.equals("save") || type.equals("supplement") ? null : businessLicense.getId());
			if (businessLicense_check != null) {
				map.put("success", false);
				map.put("msg", "【" + businessLicense.getCompany_no() + "】营业执照编号已经存在!");
				isfalse = false;
				map_result.put("isfalse", isfalse);
				map_result.put("map", map);
				return map_result;
			}
		}
		map_result.put("isfalse", isfalse);
		map_result.put("map", map);
		return map_result;

	}

	/**
	 * 功能描述： 获取身份证上传文件的路径 输入参数: @param idcard //身份证信息 输入参数: @param path //上传文件路径
	 * 输入参数: @return 输入参数: @throws IOException 异 常： 创 建 人: yangjiaqiao 日 期:
	 * 2016年5月17日下午2:24:00 修 改 人: 日 期: 返 回：Idcard
	 */
	public Idcard getIdcardImagePath(Idcard idcard, String path, String type) throws IOException {
		Idcard p_idcard = null;
		if (type.equals("edit") || type.equals("supplementedit")) {
			p_idcard = idcardDao.getObjectById(Idcard.class, idcard.getId());
		}
		if (idcard.getIdcard_front_img().indexOf("temporary") > -1) {
			if (p_idcard != null) {
				ImageFileUtil.delete(new File(path + p_idcard.getIdcard_front_img()));
			}
			ImageFileUtil
					.move(new File(path + idcard.getIdcard_front_img()),
							new File(path + idcard.getIdcard_front_img()
									.substring(0, idcard.getIdcard_front_img().lastIndexOf("/"))
									.replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(
					new File(path + idcard.getIdcard_front_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, idcard.getIdcard_front_img().substring(
					idcard.getIdcard_front_img().lastIndexOf("/"), idcard.getIdcard_front_img().length()), "idcard");// 此处是上传到阿里云OSS的步骤
			idcard.setIdcard_front_img(url);
		}

		if (idcard.getIdcard_back_img().indexOf("temporary") > -1) {
			if (p_idcard != null) {
				ImageFileUtil.delete(new File(path + p_idcard.getIdcard_back_img()));
			}
			ImageFileUtil.move(new File(path + idcard.getIdcard_back_img()), new File(path + idcard.getIdcard_back_img()
					.substring(0, idcard.getIdcard_back_img().lastIndexOf("/")).replace("temporary", "uploading")));
			FileInputStream fin = new FileInputStream(
					new File(path + idcard.getIdcard_back_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin, idcard.getIdcard_back_img().substring(
					idcard.getIdcard_back_img().lastIndexOf("/"), idcard.getIdcard_back_img().length()), "idcard");// 此处是上传到阿里云OSS的步骤
			idcard.setIdcard_back_img(url);
		}
		if (null != idcard.getIdcard_persoin_img() && !"".equals(idcard.getIdcard_persoin_img())) {
			if (idcard.getIdcard_persoin_img().indexOf("temporary") > -1) {
				if (p_idcard != null) {
					ImageFileUtil.delete(new File(path + p_idcard.getIdcard_persoin_img()));
				}
				ImageFileUtil.move(new File(path + idcard.getIdcard_persoin_img()),
						new File(path + idcard.getIdcard_persoin_img()
								.substring(0, idcard.getIdcard_persoin_img().lastIndexOf("/")).replace("temporary",
										"uploading")));
				FileInputStream fin = new FileInputStream(
						new File(path + idcard.getIdcard_persoin_img().replace("temporary", "uploading")));
				String url = OSSClientUtil.uploadFile2OSS(fin,
						idcard.getIdcard_persoin_img().substring(idcard.getIdcard_persoin_img().lastIndexOf("/"),
								idcard.getIdcard_persoin_img().length()),
						"idcard");// 此处是上传到阿里云OSS的步骤
				idcard.setIdcard_persoin_img(url);
			}
		}
		return idcard;
	}

	/**
	 * 功能描述： 获取驾驶证上传文件的路径 输入参数: @param drivingLicense 驾驶证信息 输入参数: @param path
	 * 上传文件路劲 输入参数: @return 输入参数: @throws IOException 异 常： 创 建 人: yangjiaqiao 日
	 * 期: 2016年5月17日下午2:27:28 修 改 人: 日 期: 返 回：DrivingLicense
	 */
	public DrivingLicense getDrivingLicenseImagePath(DrivingLicense drivingLicense, String path, String type)
			throws IOException {
		DrivingLicense p_drivingLicense = null;
		if (type.equals("edit") || type.equals("supplementedit")) {
			p_drivingLicense = drivingLicenseDao.getObjectById(DrivingLicense.class, drivingLicense.getId());
		}
		if (drivingLicense.getDriving_license_img().indexOf("temporary") > -1) {
			if (p_drivingLicense != null) {
				ImageFileUtil.delete(new File(path + p_drivingLicense.getDriving_license_img()));
			}
			ImageFileUtil.move(new File(path + drivingLicense.getDriving_license_img()),
					new File(path + drivingLicense.getDriving_license_img()
							.substring(0, drivingLicense.getDriving_license_img().lastIndexOf("/")).replace("temporary",
									"uploading")));
			FileInputStream fin = new FileInputStream(
					new File(path + drivingLicense.getDriving_license_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin,
					drivingLicense.getDriving_license_img().substring(
							drivingLicense.getDriving_license_img().lastIndexOf("/"),
							drivingLicense.getDriving_license_img().length()),
					"driving");// 此处是上传到阿里云OSS的步骤
			drivingLicense.setDriving_license_img(url);
		}
		return drivingLicense;
	}

	/**
	 * 功能描述： 获取营业执照图片上传路径 输入参数: @param businessLicense 营业执照信息 输入参数: @param path
	 * 保存文件路径 输入参数: @return 输入参数: @throws IOException 异 常： 创 建 人: yangjiaqiao 日
	 * 期: 2016年5月17日下午2:31:47 修 改 人: 日 期: 返 回：BusinessLicense
	 */
	public BusinessLicense getBusinessLicenseImagePath(BusinessLicense businessLicense, String path, String type)
			throws IOException {
		BusinessLicense p_businessLicense = null;
		if (type.equals("edit") || type.equals("supplementedit")) {
			p_businessLicense = businessLicenseDao.getObjectById(BusinessLicense.class, businessLicense.getId());
		}
		if (businessLicense.getCompany_img().indexOf("temporary") > -1) {
			if (p_businessLicense != null) {
				ImageFileUtil.delete(new File(path + p_businessLicense.getCompany_img()));
			}
			ImageFileUtil.move(new File(path + businessLicense.getCompany_img()),
					new File(path + businessLicense.getCompany_img()
							.substring(0, businessLicense.getCompany_img().lastIndexOf("/")).replace("temporary",
									"uploading")));
			FileInputStream fin = new FileInputStream(
					new File(path + businessLicense.getCompany_img().replace("temporary", "uploading")));
			String url = OSSClientUtil.uploadFile2OSS(fin,
					businessLicense.getCompany_img().substring(businessLicense.getCompany_img().lastIndexOf("/"),
							businessLicense.getCompany_img().length()),
					"business");// 此处是上传到阿里云OSS的步骤
			businessLicense.setCompany_img(url);
		}
		return businessLicense;
	}
}
