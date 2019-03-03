package com.memory.platform.entity.aut;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.memory.platform.entity.base.BaseEntity;
import com.memory.platform.global.AES;

/**
 * 
 * 创 建 人： yico-cj 日 期： 2016年5月2日 下午12:00:59 修 改 人： 日 期： 描 述： 身份证认证表 版 本 号： V1.0
 */
@Entity
@Table(name = "aut_idcard")
public class Idcard extends BaseEntity{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7978015080422323446L;

	@Column
	private String name; // 真实姓名

	@Column
	private String idcard_no; // 身份证号码

	@Column
	private String idcard_front_img; // 身份证正面

	@Column
	private String idcard_back_img; // 身份证反面
	
	@Column
	private String idcard_persoin_img; // 身份证手持照片

	@Column
	private String account_id; // 账户ID

	@Column
	private String company_id; // 商户ID

	private String  idcard_no1;
	

	public String getIdcard_no1() {
		return idcard_no1;
	}

	public void setIdcard_no1(String idcard_no1) {
		this.idcard_no1 = idcard_no1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the idcard_no
	 * @throws Exception 
	 */
	public String getIdcard_no() throws Exception {
		if(!StringUtils.isBlank(idcard_no)){
			return AES.Decrypt(idcard_no);
		}
		return idcard_no;
	}

	/**
	 * @param idcard_no
	 *            the idcard_no to set
	 * @throws Exception 
	 */
	public void setIdcard_no(String idcard_no) throws Exception {
		if(!StringUtils.isBlank(idcard_no)){
			this.idcard_no = AES.Encrypt(idcard_no);
		}else{
			this.idcard_no = idcard_no;
		}
		
	}

	public String getIdcard_front_img(){
		return idcard_front_img;
	}

	public void setIdcard_front_img(String idcard_front_img) {
		this.idcard_front_img = idcard_front_img;
	}

	public String getIdcard_back_img() {
		return idcard_back_img;
	}

	public void setIdcard_back_img(String idcard_back_img) {
		this.idcard_back_img = idcard_back_img;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	/**
	 * @return the company_id
	 */
	public String getCompany_id() {
		return company_id;
	}

	/**
	 * @param company_id
	 *            the company_id to set
	 */
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	/**
	 * @return the idcard_persoin_img
	 */
	public String getIdcard_persoin_img() {
		return idcard_persoin_img;
	}

	/**
	 * @param idcard_persoin_img the idcard_persoin_img to set
	 */
	public void setIdcard_persoin_img(String idcard_persoin_img) {
		this.idcard_persoin_img = idcard_persoin_img;
	}

}
