package com.memory.platform.module.capital.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.memory.platform.core.AppUtil;
import com.memory.platform.core.BeanKVO;
import com.memory.platform.core.XmlUtils;
import com.memory.platform.entity.capital.BankCard.BandStatus;
import com.memory.platform.entity.capital.CapitalAccount;
import com.memory.platform.entity.capital.CashApplication;
import com.memory.platform.entity.capital.CashApplication.BankStatus;
import com.memory.platform.entity.capital.MoneyRecord;
import com.memory.platform.entity.capital.CashApplication.Status;
import com.memory.platform.entity.capital.MoneyRecord.Type;
import com.memory.platform.entity.capital.WeiXinAccsessToken;
import com.memory.platform.entity.capital.WeiXinAuthResp;
import com.memory.platform.entity.capital.WeiXinWithdraw;
import com.memory.platform.entity.capital.WeiXinWithdrawResponse;
import com.memory.platform.entity.member.Account;
import com.memory.platform.entity.member.Account.PhonePlatform;
import com.memory.platform.entity.capital.RechargeRecord;
import com.memory.platform.entity.capital.WeiXinPay;
import com.memory.platform.entity.capital.WeiXinPayOrder;
import com.memory.platform.exception.BusinessException;
import com.memory.platform.global.Auth;
import com.memory.platform.global.DateUtil;
import com.memory.platform.global.LgisticsUtil;
import com.memory.platform.global.RSAUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.global.sdk.BaseSdk;
import com.memory.platform.global.sdk.HttpClient;
import com.memory.platform.global.sdk.HttpService;
import com.memory.platform.global.sdk.LogUtil;
import com.memory.platform.interceptor.AuthInterceptor;
import com.memory.platform.module.capital.dao.CapitalAccountDao;
import com.memory.platform.module.capital.dao.MoneyRecordDao;
import com.memory.platform.module.capital.dao.RechargeRecordDao;
import com.memory.platform.module.capital.service.ICashApplicationService;
import com.memory.platform.module.capital.service.IRechargeRecordService;
import com.memory.platform.module.push.service.IPushService;
import com.memory.platform.module.system.service.IAccountService;

/**
 * 创 建 人： longqibo 日 期： 2016年4月26日 下午7:23:50 修 改 人： 日 期： 描 述： 充值服务类 版 本 号： V1.0
 */
@Service
@Transactional
public class RechargeRecordService implements IRechargeRecordService {
	
	// android------------------
	public String weiXinAndroidOwnerMchId = "1513485771"; // android货主端微信商户号
	public String weiXinAndroidDriverMchId = "1513486701"; // android司机端微信商户号
	
	public String weiXinAndroidOwnerAppId = "wx37d1daf4675b5075";//android货主Appid
	public String weiXinAndroidDriverAppId = "wx334faf2980a332e0";//android司机Appid
	
	private String weiXinAndroidOwnerSecretID ="e9e1f9cadbf48da07335a02144003736";//android货主认证ID号
	private String weiXinAndroidDriverSecretID ="6996d961efc7dc3a23a4e08fba30c6bf"; // android司机认证ID号
	
	public String weiXinAndroidOwnerKey = "B13qHXiXPOdD91gdJHSitz0P9XrDsLMJ"; // android货主端key(私钥)
	public String weiXinAndroidDriverKey = "WbQAt430QzkB89mjMduwz8GHEs5D1mpl"; // android司机端key(私钥)
	// ios----------------------
	public String weiXinIOSOwnerMchId = "1511582371"; // ios货主端微信商户号
	public String weiXinIOSDriverMchId = "1513143721"; // ios司机端微信商户号
	
	public String weiXinIosOwnerAppId = "wx47761649d00c88f5";//ios货主Appid
	public String weiXinIosDriverAppId = "wx20d46811e3512888";//ios司机Appid
	
	public String weiXinIosOwnerSecretID = "6183025ac4f04c81b571abdda7a8f52d";//ios货主认证ID号
	public String weiXinIosDriverSecretID = "75472d7d2d809d67dc05b7064960233f";//ios司机认证ID号
	
	public String weiXinIOSOwnerKey = "q5aEO8U30YFJKoDx3Rurtbhps1KhXAA4"; // ios货主端key(私钥)
	public String weiXinIOSDriverKey = "I1Truea20xgR94BI9OGxIB6iHWmL6VLE"; // ios司机端key(私钥)
	
	
	// 微信支付生成订单接口
	public String weiXinGeneratorOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder"; 
	// 微信支付回调接口
	public String weixinBackUrl = "http://www.qibogu.com:9998/yilin_logistics_app/app/capital/weixinpaycallback.do";
	// 微信转账 微信提现
	public String weiXinTransferUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	// 获取微信用户信息
	public String weiXinGetUserUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
	// 获取微信access
	public String weiXinGetAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
	// 微信SSL证书路径
	public String weiXinCertFolder = "E://weiXinCert/";

	@Autowired
	ICashApplicationService cashApplicationService;
	
	@Autowired
	IAccountService accountService;

	@Autowired
	private RechargeRecordDao rechargeRecordDao;

	@Autowired
	private CapitalAccountDao capitalAccountDao;

	@Autowired
	private MoneyRecordDao moneyRecordDao;

	@Autowired
	private IPushService pushService;

	@Override
	public String genAppSign(WeiXinPayOrder order, String appKey) {
		List<NameValuePair> signParams = new ArrayList<>();
		// if(order.getPhonePlatform() != PhonePlatform.ios) { //ios端不需要ID号签名
		signParams.add(new BasicNameValuePair("appid", order.getAppid()));
		// }
		signParams
				.add(new BasicNameValuePair("noncestr", order.getNonce_str()));
		signParams.add(new BasicNameValuePair("package", order
				.getPackage_value()));
		signParams.add(new BasicNameValuePair("partnerid", order.getMch_id()));
		signParams
				.add(new BasicNameValuePair("prepayid", order.getPrepay_id()));
		signParams.add(new BasicNameValuePair("timestamp", Long.toString(order
				.getTime_stamp())));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < signParams.size(); i++) {
			sb.append(signParams.get(i).getName());
			sb.append('=');
			sb.append(signParams.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(appKey);
		String appSign = AppUtil.md5(sb.toString()).toUpperCase();
		return appSign;
	}
	/*
	 * 生成微信预付款订单
	 * */
	@Override
	public Map<String, Object> generatorWeiXinOrder(WeiXinPay payInfo) {
		Account account = AppUtil.getLoginUser();
		payInfo.setTotal_fee(payInfo.getTotal_fee());
		// 获取商户号
		payInfo.setMch_id(this.getWeiXinMchId());
		payInfo.setNonce_str(StringUtil.getRandomStr(32).toUpperCase());
		payInfo.setBody("小镖人充值");
		String txnTime = DateUtil.timeNo();
		String orderId = "BC" + txnTime + AppUtil.random(4).toString();
		payInfo.setOut_trade_no(orderId);
		// 获取微信支付回调接口
		payInfo.setNotify_url(this.weixinBackUrl);
		payInfo.setTrade_type("APP");
		// 获取签名
		String str = payInfo.toSignStr();
		String key = this.getWeiXinKey("");
		String md5Str = AppUtil.md5(str + "&key=" + key);
		payInfo.setSign(md5Str);
		Map<String, Object> map = this.weiXinrechargeConsume(payInfo);
		boolean success = Boolean.parseBoolean(map.get("success").toString());
		if (success) {
			RechargeRecord rechargeRecord = new RechargeRecord();
			// 处理交易逻辑
			rechargeRecord.setSourceAccount(account.getId());
			rechargeRecord.setSourceType("微信");
			rechargeRecord.setTradeAccount(account.getId());
			rechargeRecord.setTradeName(payInfo.getBody());
			rechargeRecord.setCreate_time(new Date());
			rechargeRecord.setAccount(account);
			rechargeRecord.setStatus(RechargeRecord.Status.waitProcess);
			rechargeRecord.setTradeNo(payInfo.getOut_trade_no());
			rechargeRecord.setRemark("充值了" + (double) payInfo.getTotal_fee()
					/ 100);
			rechargeRecord.setMoney((double) payInfo.getTotal_fee() / 100);
			rechargeRecord.setBankName("微信充值");
			this.savePay(rechargeRecord);
		}
		return map;
	}

	
	public WeiXinAccsessToken getAccsessToken(){
		Account account = AppUtil.getLoginUser();
		String accountID = account.getId();
		String url = String.format(weiXinGetAccessTokenUrl+"?grant_type=client_credential&appid=%s&secret=%s", 
				getAppId(accountID),
				getSecertID(accountID));
		String result = post(url, null);
		if(StringUtil.isEmpty(result)) {
			return null;
		}
		return AppUtil.getGson().fromJson(result, WeiXinAccsessToken.class);
	
	}
	/*
	 * 获取appID号 有4个APP IOS 俩个  android俩个
	 * */
	public String getAppId(String accountId) {
		Account account = AppUtil.getLoginUser();
		if (account == null)
			account = accountService.getAccount(accountId);
		if (account == null)
			return "";
		String ret = "";
		if (account.getPhone_platform() == Account.PhonePlatform.android) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinAndroidOwnerAppId;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinAndroidDriverAppId;
			}
		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinIosOwnerAppId;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinIosDriverAppId;
			}
		}
		return ret;
	}
	/*
	 * 获取证书路径
	 * */
	public String getCerPath(String accountId) {
		Account account = AppUtil.getLoginUser();
		String ret = "";
		if(account == null) {
			account = accountService.getAccount(accountId);
			
		}
		if (account.getPhone_platform() == Account.PhonePlatform.android) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = String.format(this.weiXinCertFolder +"apiclient_cert_android_owner.p12");
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = String.format(this.weiXinCertFolder +"apiclient_cert_android_driver.p12");
			}
		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = String.format(this.weiXinCertFolder +"apiclient_cert_ios_owner.p12");
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = String.format(this.weiXinCertFolder +"apiclient_cert_ios_driver.p12");
			}
		}
		return ret;
	}

	/*
	 * 获取微信SSL证书密码
	 * */
	public String getCertPwd(String accountId){
		/*
		 * 默认密码 就是商户ID号
		 * */
		return getWeiXinMchId(accountId);
//		Account account = AppUtil.getLoginUser();
//		if (account == null)
//			account = accountService.getAccount(accountId);
//		if (account == null)
//			return "";
//		String ret = "";
//		if (account.getPhone_platform() == Account.PhonePlatform.android) {
//			if (account.getRole_type() == Account.RoleType.consignor) {
//				ret = this.weiXinAndroidOwnerKey;
//			} else if (account.getRole_type() == Account.RoleType.truck) {
//				ret = this.weiXinAndroidDriverKey;
//			}
//		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
//			if (account.getRole_type() == Account.RoleType.consignor) {
//				ret = this.weiXinIOSOwnerKey;
//			} else if (account.getRole_type() == Account.RoleType.truck) {
//				ret = this.weiXinIOSDriverKey;
//			}
//		}
//		return ret;
	}

	
	/*
	 * 获取认证ID号
	 * */
	public String getSecertID(String accountId) {
		Account account = AppUtil.getLoginUser();
		if (account == null)
			account = accountService.getAccount(accountId);
		if (account == null)
			return "";
		String ret = "";
		if (account.getPhone_platform() == Account.PhonePlatform.android) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinAndroidOwnerSecretID;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinAndroidDriverSecretID;
			}
		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinIosOwnerSecretID;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinIosDriverSecretID;
			}
		}
		return ret;
	}
	/*
	 *  生成提现报文签名
	 * */
	@SuppressWarnings("static-access")
	public String getSignWithDraw(WeiXinWithdraw withdraw, String appKey) {
		BeanKVO kvo = new BeanKVO(withdraw);
		Map<String, String> mapKeyAndValue = new HashMap<String, String>();
		final ArrayList<NameValuePair> lstArrayList = new ArrayList<NameValuePair>();
		kvo.enumProperty(withdraw, new BeanKVO.EnumPropertyCallBack() {

			@Override
			public boolean callback(String propertyName, Object value) {
				if (value != null && propertyName.equals("class") == false) {
					lstArrayList.add(new BasicNameValuePair(propertyName, value
							.toString()));
				}

				return true;
			}
		});
		Collections.sort(lstArrayList, new Comparator<NameValuePair>() {

			@Override
			public int compare(NameValuePair o1, NameValuePair o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lstArrayList.size(); i++) {
			sb.append(lstArrayList.get(i).getName());
			sb.append('=');
			sb.append(lstArrayList.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(appKey);
		 

		String appSign = AppUtil.md5(sb.toString()).toUpperCase();
		return appSign;
	}

	/*
	 * 获取微信认证信息
	 * */
	public Map<String, Object> getWeiXinAuthInfo(String weiXinAuthCode){
	 
		Account account = AppUtil.getLoginUser();
		String url = String
				.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
						getAppId(account.getId()),
						getSecertID(account.getId()), weiXinAuthCode);

		Map<String, Object> ret = new HashMap<>();
		do {
			CapitalAccount capitalAccount = capitalAccountDao
					.getCapitalAccount(account.getId());
			
			 
			String result = post(url, new HashMap<String, String>()); // 先获取微信用户
																		// 用于转账到哪个用户
			if (StringUtil.isEmpty(result)) {
				ret.put("success", false);
				break;
			}
			WeiXinAuthResp weiXinAuthResp = AppUtil.getGson().fromJson(result,
					WeiXinAuthResp.class);
			if (StringUtil.isNotEmpty(weiXinAuthResp.getErrcode())) {
				ret.put("success", false);
				ret.put("msg", weiXinAuthResp.getErrmsg());
				break;
			}
			ret.put("success",true);
			ret.put("data", weiXinAuthResp);
			
		} while (false);
		return ret ;
	}
	
	/*
	 * 获取微信Key  用于签名
	 * */
	@Override
	public String getWeiXinKey(String accountId) {
		Account account = AppUtil.getLoginUser();
		if (account == null)
			account = accountService.getAccount(accountId);
		if (account == null)
			return "";
		String ret = "";
		if (account.getPhone_platform() == Account.PhonePlatform.android) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinAndroidOwnerKey;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinAndroidDriverKey;
			}
		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinIOSOwnerKey;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinIOSDriverKey;
			}
		}
		return ret;
	}
	/*
	 * 获取商户ID号
	 * */
	@Override
	public String getWeiXinMchId() {
		Account account = AppUtil.getLoginUser();
		if (account == null)
			return "";
		String ret = "";
		if (account.getPhone_platform() == Account.PhonePlatform.android) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinAndroidOwnerMchId;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinAndroidDriverMchId;
			}
		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinIOSOwnerMchId;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinIOSDriverMchId;
			}
		}
		return ret;
	}
	/*
	 * 获取商户ID号
	 * */
	public String getWeiXinMchId(String accountId) {
		Account account = AppUtil.getLoginUser();
			if (account == null)
				account = accountService.getAccount(accountId);
		String ret = "";
		if (account.getPhone_platform() == Account.PhonePlatform.android) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinAndroidOwnerMchId;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinAndroidDriverMchId;
			}
		} else if (account.getPhone_platform() == Account.PhonePlatform.ios) {
			if (account.getRole_type() == Account.RoleType.consignor) {
				ret = this.weiXinIOSOwnerMchId;
			} else if (account.getRole_type() == Account.RoleType.truck) {
				ret = this.weiXinIOSDriverMchId;
			}
		}
		return ret;
	}


	/*
	 * 获取微信用户
	 * */
	@Override
	public Map<String, Object> getWeiXinUserInfo(String weiXinAuthCode) {
		Map<String,Object> ret = getWeiXinAuthInfo(weiXinAuthCode);
		do {
			Boolean suc = (Boolean)ret.get("success");
			if(suc == false) {
				break;
			}
			WeiXinAuthResp resp =(WeiXinAuthResp) ret.get("data");
			WeiXinAccsessToken accsessToken = getAccsessToken();
			
			String url = String.format(this.weiXinGetUserUrl + "?access_token=%s&openid=%s&lang=zh_CN", accsessToken.getAccess_token(),resp.getOpenid());
			String result = post(url, null);
			if(StringUtil.isEmpty(result)) {
				ret.put("success", false);
				ret.put("msg", "获取微信用户失败");
				break;
			}
		} while (false);
		return ret ;
	}
	
	/*
	 * http 传输
	 * */
	String post(String url, HashMap<String, String> dataHashMap) {
		HttpClient hc = new HttpClient(url, 10000, 10000);
		try {
			int status = hc.send(dataHashMap, BaseSdk.encoding_UTF8);
			if (200 == status) {
				String resultString = hc.getResult();
				if (null != resultString && !"".equals(resultString)) {
					return resultString;
				}
			} else {
				LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return null;
	}
	
	String post1(String url, String parameter) {
		HttpClient hc = new HttpClient(url, 10000, 10000);
		try {
			// hc.setSslFactory(getCertificates());
			int status = hc.send(parameter, BaseSdk.encoding_UTF8);
			if (200 == status) {
				String resultString = hc.getResult();
				if (null != resultString && !"".equals(resultString)) {
					return resultString;
				}
			} else {
				LogUtil.writeLog("返回http状态码[" + status + "]，请检查请求报文或者请求地址是否正确");
			}
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * 微信提现 ssl 提交 里面有ssl 
	 * */
	private String post2(String url, String parameter,String certPath,String password) {
		 

		String jsonStr = null;
		try {
				
			KeyStore keyStore = KeyStore.getInstance("pkcs12");

			FileInputStream instream = new FileInputStream(new File(
					certPath));
			// FileInputStream instream = new FileInputStream(new
			// File("apiclient_key.pem"));

			keyStore.load(instream, password.toCharArray());// 杩欓噷鍐欏

			instream.close();
			/* RSAUtil.getInstance().getPrivateKey(this.weiXinIOSOwnerPem); */
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, password.toCharArray()).build();

			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[] { "TLSv1" },
					null,

					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();

			HttpPost httpost = new HttpPost(url); // 璁剧疆鍝嶅簲澶翠俊鎭?

			httpost.addHeader("Connection", "keep-alive");

			httpost.addHeader("Accept", "*/*");

			httpost.addHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");

			httpost.addHeader("Host", "api.mch.weixin.qq.com");

			httpost.addHeader("X-Requested-With", "XMLHttpRequest");

			httpost.addHeader("Cache-Control", "max-age=0");

			httpost.addHeader("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

			httpost.setEntity(new StringEntity(parameter, "UTF-8"));

			CloseableHttpResponse response = httpclient.execute(httpost);

			HttpEntity entity = response.getEntity();
			jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			EntityUtils.consume(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonStr;

	}

	@Override
	public void savePay(RechargeRecord rechargeRecord) {
		if (rechargeRecord.getAccount().getAuthentication()
				.equals(Auth.notAuth)) {
			throw new BusinessException("请先认证后再操作。");
		}
		this.rechargeRecordDao.save(rechargeRecord);
	}
	/*
	 * 保存充值记录
	 * */
	@Override
	public void saveRecharge(RechargeRecord rechargeRecord) {
		if (rechargeRecord.getStatus().equals(RechargeRecord.Status.failed)) {
			this.rechargeRecordDao.update(rechargeRecord);
		} else {
			rechargeRecord.setStatus(RechargeRecord.Status.success);
			CapitalAccount capitalAccount = capitalAccountDao
					.getCapitalAccount(rechargeRecord.getAccount().getId());
			BigDecimal avaiable = new BigDecimal(capitalAccount.getAvaiable());
			BigDecimal money = new BigDecimal(rechargeRecord.getMoney());
			BigDecimal totalRecharge = new BigDecimal(
					capitalAccount.getTotalrecharge());
			BigDecimal total = new BigDecimal(capitalAccount.getTotal());
			capitalAccount.setAvaiable(avaiable.add(money).doubleValue()); // 计算可用余额
			capitalAccount.setTotalrecharge(totalRecharge.add(money)
					.doubleValue()); // 计算总充值
			capitalAccount.setTotal(total.add(money).doubleValue()); // 计算总资产

			// 添加资金记录
			MoneyRecord moneyRecord = new MoneyRecord();
			moneyRecord.setAccount(rechargeRecord.getAccount());
			moneyRecord.setCreate_time(new Date());
			moneyRecord.setMoney(rechargeRecord.getMoney());
			moneyRecord.setOperator(rechargeRecord.getAccount().getName());
			moneyRecord.setRemark("在线充值成功，充值金额¥" + rechargeRecord.getMoney()
					+ "元，流水号：" + rechargeRecord.getTradeNo());
			moneyRecord.setType(Type.recharge);
			this.rechargeRecordDao.update(rechargeRecord);
			this.capitalAccountDao.update(capitalAccount);
			moneyRecord.setSourceAccount(rechargeRecord.getSourceAccount());
			moneyRecord.setSourceType(rechargeRecord.getSourceType());
			moneyRecord.setTradeAccount(rechargeRecord.getTradeAccount());
			moneyRecord.setTradeName(rechargeRecord.getTradeName());
			this.moneyRecordDao.save(moneyRecord);
			// 推送消息
			String tmp = "尊敬的用户您好,您已成功充值一笔"
					+ moneyRecord.getMoney()
					+ "元,支付单号为:"
					+ rechargeRecord.getTradeNo()
					+ String.format(",余额：%.2f", capitalAccount.getAvaiable()
							.doubleValue());
			Map<String, String> actionMap = new HashMap<String, String>();
			actionMap.put("order_id", rechargeRecord.getTradeNo());
			pushService
					.saveRecordAndSendMessageWithAccountID(
							rechargeRecord.getAccount().getId(),
							"【" + rechargeRecord.getSourceType() + "】",
							tmp,
							actionMap,
							"com.memory.platform.module.capital.service.impl.RechargeRecordService.saveRecharge(RechargeRecord)");
		}
	}


	/**
	 * 功能描述： 微信充值 输入参数: @param 银联对象 异 常： 创 建 人: aiqiwu 日 期:2018年3月14日上午11:17:23
	 * 修 改 人: 日 期: 返 回：Map<String,Object>
	 */
	@Override
	public Map<String, Object> weiXinrechargeConsume(WeiXinPay pay) {
		final Map<String, Object> ret = new HashMap<>();
		String xml = XmlUtils.convertToXml(pay);
		WeiXinPayOrder rspData = HttpService.post(xml,
				this.weiXinGeneratorOrderUrl, BaseSdk.encoding_UTF8);

		if (rspData != null) {
			if (rspData.getReturn_code().equals("SUCCESS")) {
				// app跳转需要将appId、partnerId、prepayId、packageValue、nonceStr、timeStamp组织重新签名，才能跳转微信
				Account account = AppUtil.getLoginUser();
				rspData.setPhonePlatform(account.getPhone_platform());

				rspData.setTime_stamp(System.currentTimeMillis() / 1000);
				rspData.setPackage_value("Sign=WXPay");
				rspData.setNonce_str(StringUtil.getRandomStr(32).toUpperCase());

				rspData.setSign(this.genAppSign(rspData, this.getWeiXinKey("")));
				ret.put("success", true);
				ret.put("data", rspData);
			} else {
				// TODO 检查验证签名失败的原因
				ret.put("msg", rspData.getReturn_msg() +" postXml:" +xml);
				ret.put("success", false);
			}
		} else {
			// 未返回正确的http状态
			ret.put("msg", "请求失败");
			ret.put("success", false);
		}
		return ret;
	}
	/**
	 * 微信提现审核成功
	 * */
	@Override
	public Map<String, Object> withdrawSuccess(String cashApplicationID)
			throws BusinessException, JAXBException {
		CashApplication cashApplication = cashApplicationService
				.getCashById(cashApplicationID);
		Map<String, Object> ret = new HashMap<String, Object>();
		do {
			if (cashApplication == null) {
				throw new BusinessException("1", "cashApplication 不存在 id ="
						+ cashApplicationID);
			}
			if(cashApplication.getStatus() == Status.lock) {
				throw new BusinessException("4", "提现状态锁定中 "
						+ cashApplication.getStatus());
			}
			if (cashApplication.getStatus() != Status.success) {
				throw new BusinessException("2", "提现记录状态不是success "
						+ cashApplication.getStatus());
			}
			
			if(cashApplication.getBank_status() != BankStatus.waitProcess) {
				throw new BusinessException("3", "提现银行回执状态不是等待处理 "
						+ cashApplication.getStatus());
			}
			Account account = cashApplication.getAccount();
			String openID = cashApplication.getBankcard(); //微信订阅号ID
			double money = cashApplication.getMoney();
			String accountId = account.getId();
			WeiXinWithdraw withdraw = new WeiXinWithdraw();
			withdraw.setMch_appid(getAppId(accountId));
			withdraw.setMchid(this.getWeiXinMchId(accountId));
			String orderID = cashApplication.getTradeNo(); //订单ID 就是提现编号 便于查找
			withdraw.setPartner_trade_no(orderID);
			withdraw.setOpenid(openID);
			withdraw.setAmount((int) (money * 100));
			withdraw.setDesc(String.format("微信提现%.2f元 ,流水号:%s" , money,orderID));
			withdraw.setCheck_name("NO_CHECK");
			withdraw.setSpbill_create_ip(account.getLogin_ip());
			withdraw.setNonce_str(StringUtil.getRandomStr(32).toUpperCase());
			String sign = this.getSignWithDraw(withdraw,
					getWeiXinKey(accountId));
			withdraw.setSign(sign);

			String xml = withdraw.toPayloadXml();
			 
			String result = post2(weiXinTransferUrl, xml,getCerPath(accountId),getCertPwd(accountId));
			cashApplication.setBank_payload(xml);
			if (StringUtil.isEmpty(result)) {
				ret.put("success", false);
				break;
			}
			JAXBContext xmlContext = JAXBContext.newInstance(WeiXinWithdrawResponse.class);
			Unmarshaller unmarshaller = xmlContext.createUnmarshaller();    
	        StringReader sr = new StringReader(result);    
	        cashApplication.setBank_receive_payload(result);
	        WeiXinWithdrawResponse response =(WeiXinWithdrawResponse) unmarshaller.unmarshal(sr);
	        if( "SUCCESS".equals(response.getReturn_code() )  &&  "SUCCESS".equals(response.getResult_code())){
	        	cashApplication.setBank_status(BankStatus.success);
	        	
	        	ret.put("success", true);
	        	ret.put("msg", "微信转账成功");
	        	pushService.saveRecordAndSendMessageWithAccountID(accountId, "【微信提现】", String.format("微信提现已到账¥%.2f元,流水号:%s"
	        			, money,orderID),null , "com.memory.platform.module.capital.service.impl.RechargeRecordService.withdrawSuccess(String)");
	        	
	        }else {
	        	ret.put("success", false);
	        	ret.put("msg", response.getErr_code_des());
	        }
	       
		} while (false);
		 cashApplicationService.update(cashApplication);
		return ret;
	}
	/*
	 * 微信提现
	 */
	@Override
	public Map<String, Object> withdrawWeiXin(String weiXinAuthCode,
			double money, String payPassword) {
		Account account = AppUtil.getLoginUser();
		String url = String
				.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
						getAppId(account.getId()),
						getSecertID(account.getId()), weiXinAuthCode);

		Map<String, Object> ret = new HashMap<>();
		do {
			CapitalAccount capitalAccount = capitalAccountDao
					.getCapitalAccount(account.getId());
			if (account.getPaypassword().equals(AppUtil.md5(payPassword)) == false) {
				ret.put("success", false);
				ret.put("msg", "支付密码不正确");
				break;
			}
			if (capitalAccount.getAvaiable() < money) {
				ret.put("success", false);
				ret.put("msg", "提现金额大于活动资金");
				break;
			}
			String result = post(url, new HashMap<String, String>()); // 先获取微信用户
																		// 用于转账到哪个用户
			if (StringUtil.isEmpty(result)) {
				ret.put("success", false);
				break;
			}
			WeiXinAuthResp weiXinAuthResp = AppUtil.getGson().fromJson(result,
					WeiXinAuthResp.class);
			if (StringUtil.isNotEmpty(weiXinAuthResp.getErrcode())) {
				ret.put("success", false);
				ret.put("msg", weiXinAuthResp.getErrmsg());
				break;
			}
			String openID = weiXinAuthResp.getOpenid();
			CashApplication cashApplication = new CashApplication();
			cashApplication.setAccount(account);
			cashApplication.setBankcard(openID);// 这个是微信账号
			cashApplication.setBankname("weiXin");
			cashApplication.setCreate_time(new Date());
			cashApplication.setOpenbank("weiXin");
			cashApplication.setStatus(Status.waitProcess);
			cashApplication.setTradeNo("CN" + DateUtil.dateNo());
			cashApplication.setMoney(money);
			cashApplication.setBank_status(BankStatus.none);
			cashApplication.setBank_payload(null);
			cashApplication.setBank_receive_payload(null);
			cashApplicationService.saveCashApplication(cashApplication);
			ret.put("success", true);
			ret.put("msg", "【提现申请成功】！平台3工作日内对该提现进行审核，如遇周末或节假日往后顺延，审核成功后提现金额才能到账。请耐心等待审核结果！！");
			/*
			 * String accountId = account.getId(); WeiXinWithdraw withdraw = new
			 * WeiXinWithdraw(); withdraw.setMch_appid(getAppId(accountId));
			 * withdraw.setMchid(this.getWeiXinMchId()); String orderID
			 * =StringUtil.getRandomStr(32).toUpperCase();
			 * withdraw.setPartner_trade_no(orderID);
			 * withdraw.setOpenid(weiXinAuthResp.getOpenid());
			 * withdraw.setAmount((int)(money*100));
			 * withdraw.setDesc(String.format("微信提现%.2f元", money));
			 * withdraw.setCheck_name("NO_CHECK");
			 * withdraw.setSpbill_create_ip(account.getLogin_ip());
			 * withdraw.setNonce_str(StringUtil.getRandomStr(32).toUpperCase());
			 * String sign =
			 * this.getSignWithDraw(withdraw,getWeiXinKey(accountId) );
			 * withdraw.setSign(sign);
			 * 
			 * String xml = withdraw.toPayloadXml(); System.out.println(xml);
			 * result = post2(weiXinTransferUrl, xml);
			 * if(StringUtil.isEmpty(result)){ ret.put("success", false); break;
			 * }
			 */

		} while (false);
		return ret;
	}
	
	//
	
	
	
	@Override
	public List<RechargeRecord> getList(RechargeRecord rechargeRecord) {
		return rechargeRecordDao.getList(rechargeRecord);
	}
	@Override
	public Map<String, Object> getListForMap(String accountId, int start,
			int size) {
		return rechargeRecordDao.getListForMap(accountId, start, size);
	}
	@Override
	public Map<String, Object> getPage(RechargeRecord rechargeRecord,
			int start, int limit) {
		// TODO Auto-generated method stub
		return rechargeRecordDao.getPage(rechargeRecord, start, limit);
	}
	@Override
	public RechargeRecord getRechargeRecordById(String id) {
		return rechargeRecordDao.getObjectById(RechargeRecord.class, id);
	}
	@Override
	public RechargeRecord getRechargeRecordByOrderId(String orderId) {
		return rechargeRecordDao.getRechargeRecordByOrderId(orderId);
	}
	
	public String getWeiXinTransferUrl() {
		return weiXinTransferUrl;
	}
	
	public String getWeixinBackUrl() {
		return weixinBackUrl;
	}
	public String getWeiXinCertFolder() {
		return weiXinCertFolder;
	}

	public String getWeiXinGeneratorOrderUrl() {
		return weiXinGeneratorOrderUrl;
	}

	public String getWeiXinGetAccessTokenUrl() {
		return weiXinGetAccessTokenUrl;
	}

	public String getWeiXinGetUserUrl() {
		return weiXinGetUserUrl;
	}
	
	public String getWeiXinIosDriverAppId() {
		return weiXinIosDriverAppId;
	}
	
	public String getWeiXinIOSDriverKey() {
		return weiXinIOSDriverKey;
	}

	public String getWeiXinIOSDriverMchId() {
		return weiXinIOSDriverMchId;
	}

	public String getWeiXinIosDriverSecretID() {
		return weiXinIosDriverSecretID;
	}

	public String getWeiXinIosOwnerAppId() {
		return weiXinIosOwnerAppId;
	}

	public String getWeiXinIOSOwnerKey() {
		return weiXinIOSOwnerKey;
	}

	public String getWeiXinIOSOwnerMchId() {
		return weiXinIOSOwnerMchId;
	}

	public String getWeiXinIosOwnerSecretID() {
		return weiXinIosOwnerSecretID;
	}
	
	public String getWeiXinAndroidDriverAppId() {
		return weiXinAndroidDriverAppId;
	}
	public String getWeiXinAndroidDriverKey() {
		return weiXinAndroidDriverKey;
	}

	public String getWeiXinAndroidDriverMchId() {
		return weiXinAndroidDriverMchId;
	}
	public String getWeiXinAndroidDriverSecretID() {
		return weiXinAndroidDriverSecretID;
	}
	public String getWeiXinAndroidOwnerAppId() {
		return weiXinAndroidOwnerAppId;
	}
	public String getWeiXinAndroidOwnerKey() {
		return weiXinAndroidOwnerKey;
	}
	public String getWeiXinAndroidOwnerMchId() {
		return weiXinAndroidOwnerMchId;
	}
	public String getWeiXinAndroidOwnerSecretID() {
		return weiXinAndroidOwnerSecretID;
	}
	
	public void setWeiXinAndroidDriverAppId(String weiXinAndroidDriverAppId) {
		this.weiXinAndroidDriverAppId = weiXinAndroidDriverAppId;
	}
	public void setWeiXinAndroidDriverKey(String weiXinAndroidDriverKey) {
		this.weiXinAndroidDriverKey = weiXinAndroidDriverKey;
	}

	public void setWeiXinAndroidDriverMchId(String weiXinAndroidDriverMchId) {
		this.weiXinAndroidDriverMchId = weiXinAndroidDriverMchId;
	}

	public void setWeiXinAndroidDriverSecretID(String weiXinAndroidDriverSecretID) {
		this.weiXinAndroidDriverSecretID = weiXinAndroidDriverSecretID;
	}

	public void setWeiXinAndroidOwnerAppId(String weiXinAndroidOwnerAppId) {
		this.weiXinAndroidOwnerAppId = weiXinAndroidOwnerAppId;
	}

	public void setWeiXinAndroidOwnerKey(String weiXinAndroidOwnerKey) {
		this.weiXinAndroidOwnerKey = weiXinAndroidOwnerKey;
	}

	public void setWeiXinAndroidOwnerMchId(String weiXinAndroidOwnerMchId) {
		this.weiXinAndroidOwnerMchId = weiXinAndroidOwnerMchId;
	}

	public void setWeiXinAndroidOwnerSecretID(String weiXinAndroidOwnerSecretID) {
		this.weiXinAndroidOwnerSecretID = weiXinAndroidOwnerSecretID;
	}

	public void setWeixinBackUrl(String weixinBackUrl) {
		this.weixinBackUrl = weixinBackUrl;
	}

	public void setWeiXinCertFolder(String weiXinCertFolder) {
		this.weiXinCertFolder = weiXinCertFolder;
	}

	public void setWeiXinGeneratorOrderUrl(String weiXinGeneratorOrderUrl) {
		this.weiXinGeneratorOrderUrl = weiXinGeneratorOrderUrl;
	}

	public void setWeiXinGetAccessTokenUrl(String weiXinGetAccessTokenUrl) {
		this.weiXinGetAccessTokenUrl = weiXinGetAccessTokenUrl;
	}

	public void setWeiXinGetUserUrl(String weiXinGetUserUrl) {
		this.weiXinGetUserUrl = weiXinGetUserUrl;
	}

	public void setWeiXinIosDriverAppId(String weiXinIosDriverAppId) {
		this.weiXinIosDriverAppId = weiXinIosDriverAppId;
	}

	public void setWeiXinIOSDriverKey(String weiXinIOSDriverKey) {
		this.weiXinIOSDriverKey = weiXinIOSDriverKey;
	}

	public void setWeiXinIOSDriverMchId(String weiXinIOSDriverMchId) {
		this.weiXinIOSDriverMchId = weiXinIOSDriverMchId;
	}

	public void setWeiXinIosDriverSecretID(String weiXinIosDriverSecretID) {
		this.weiXinIosDriverSecretID = weiXinIosDriverSecretID;
	}

	public void setWeiXinIosOwnerAppId(String weiXinIosOwnerAppId) {
		this.weiXinIosOwnerAppId = weiXinIosOwnerAppId;
	}
	public void setWeiXinIOSOwnerKey(String weiXinIOSOwnerKey) {
		this.weiXinIOSOwnerKey = weiXinIOSOwnerKey;
	}
	public void setWeiXinIOSOwnerMchId(String weiXinIOSOwnerMchId) {
		this.weiXinIOSOwnerMchId = weiXinIOSOwnerMchId;
	}
	public void setWeiXinIosOwnerSecretID(String weiXinIosOwnerSecretID) {
		this.weiXinIosOwnerSecretID = weiXinIosOwnerSecretID;
	}
	public void setWeiXinTransferUrl(String weiXinTransferUrl) {
		this.weiXinTransferUrl = weiXinTransferUrl;
	}
}
