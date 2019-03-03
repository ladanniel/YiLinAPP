/**
 * 
 */
package com.memory.platform.module.capital.dto;

import com.memory.platform.entity.capital.BankCard.BankType;
import com.memory.platform.module.global.dto.BaseDTO;

/**   
*    
* 项目名称：qbgwl_app_web   
* 类名称：BankCardDTO   
* 类描述：   
* 创建人：ROG   
* 创建时间：2017年4月19日 上午9:28:38   
* 修改人：ROG   
* 修改时间：2017年4月19日 上午9:28:38   
* 修改备注：   
* @version    
*    
*/
/**
 * 功能描述： 输入参数: @param 异 常： 创 建 人: ROG 日 期:2017年4月19日 上午9:28:38 修 改 人:ROG 日
 * 期:2017年4月19日 上午9:28:38 返 回：
 */
public class BankCardDTO {
	private String bankCardId;
	private String bankCard; // 银行卡号

	private String openBank; // 开户行名称

	private String bankName; // 银行名称
	private int bankType;

	private String cardType;

	private String cn_name; // 银行名称

	private String image; // logo地址
	private String markImage;
	private String mobile; // 银行预留手机号
	private Integer version;
	/**
	 * @return the bankCard
	 */
	public String getBankCard() {
		return bankCard;
	}

	/**
	 * @return the bankCardId
	 */
	public String getBankCardId() {
		return bankCardId;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @return the bankType
	 */
	public int getBankType() {
		return bankType;
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @return the cn_name
	 */
	public String getCn_name() {
		return cn_name;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return the markImage
	 */
	public String getMarkImage() {
		return markImage;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the openBank
	 */
	public String getOpenBank() {
		return openBank;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param bankCard
	 *            the bankCard to set
	 */
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	/**
	 * @param bankCardId
	 *            the bankCardId to set
	 */
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @param bankType
	 *            the bankType to set
	 */
	public void setBankType(int bankType) {
		this.bankType = bankType;
	}

	/**
	 * @param cardType
	 *            the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @param cn_name
	 *            the cn_name to set
	 */
	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @param markImage
	 *            the markImage to set
	 */
	public void setMarkImage(String markImage) {
		this.markImage = markImage;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param openBank
	 *            the openBank to set
	 */
	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
}
