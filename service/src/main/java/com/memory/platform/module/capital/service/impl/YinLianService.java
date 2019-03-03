package com.memory.platform.module.capital.service.impl;

 

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memory.platform.global.Config;
import com.memory.platform.global.sdk.AcpService;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.module.capital.service.IYinLianService;

@Service
@Transactional
public class YinLianService implements IYinLianService {
	// 需要加密的字段encrypt
	static ArrayList<String> encryptData = new ArrayList<String>(Arrays.asList("accNo"));

	static String encoding = "UTF-8";

	static {

	}

	@Override
	public Map<String, String> sendPacket(String url ,Map<String, String> data) {
		Map<String, String> ret = new HashMap<String, String>();
		do {
			if (data == null)
				break;
			cfgData(ret);

			AcpService.sign(data, encoding);
			AcpService.post(data, url, encoding);
		} while (false);
		return null;
	}

	private void cfgData(Map<String, String> ret) {
		do {
			/*
			 * //此测试商户号777290058110097 后台开通业务只支持 贷记卡 Map<String,String>
			 * customerInfoMap = new HashMap<String,String>();
			 * //customerInfoMap.put("certifTp", "01"); //证件类型
			 * //customerInfoMap.put("certifId", "341126197709218366"); //证件号码
			 * //customerInfoMap.put("customerNm", "全渠道"); //姓名
			 * customerInfoMap.put("phoneNo", "13552535506"); //手机号
			 * //customerInfoMap.put("pin", "123456"); //密码【这里如果送密码 商户号必须配置
			 * ”商户允许采集密码“】 customerInfoMap.put("cvn2", "123"); //卡背面的cvn2三位数字
			 * customerInfoMap.put("expired", "1711"); //有效期 年在前月在后
			 * customerInfoMap.put("smsCode", "111111"); //短信验证码
			 * 
			 * 
			 * contentData.put("merId", Config.merId);
			 * //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【
			 * 自己注册的测试777开头的商户号不支持代收产品】 contentData.put("accessType", "0");
			 * //接入类型，商户接入固定填0，不需修改 contentData.put("orderId", orderId);
			 * //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则 contentData.put("txnTime",
			 * txnTime); //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			 * contentData.put("accType", "01"); //账号类型
			 * 
			 * 
			 */
			if (ret == null)
				break;
			if (ret.containsKey("frontUrl") == false) {

			}
			if (ret.containsKey("encryptCertId") == false) {

			}
			if (ret.containsKey("signMethod") == false) {
				ret.put("signMethod", "01");
			}
			// 商户信息
			if (ret.containsKey("merId") == false) {

				ret.put("merId", Config.merId);
			}
			if (ret.containsKey("accType") == false) {
				ret.put("accType", "0");
			}
			if (ret.containsKey("frontUrl") == false)
				ret.put("frontUrl", BaseSdk.frontUrl);
			if (ret.containsKey("backUrl") == false) {
				ret.put("backUrl", BaseSdk.bankOpenUrl);
			}
			for (int i = 0; i < encryptData.size(); i++) {
				String key = encryptData.get(i);
				if (ret.containsKey(key)) {
					String val = ret.get(key);
					ret.put(key, AcpService.encryptData(val, encoding));
				}
			}
		} while (false);

	}

}
