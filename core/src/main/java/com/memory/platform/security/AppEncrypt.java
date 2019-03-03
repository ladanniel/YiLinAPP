package com.memory.platform.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import org.aspectj.weaver.patterns.IfPointcut.IfFalsePointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.memory.platform.core.AppUtil;
import com.memory.platform.global.AESUtil;
import com.memory.platform.global.Base64Util;
import com.memory.platform.global.ByteArrayUtil;
import com.memory.platform.global.GZipUtil;
import com.memory.platform.global.RSAUtil;
import com.memory.platform.global.StringUtil;
import com.memory.platform.memStore.MemShardStrore;

/*
 * by lil 
 * 服务器 加密 utf8编码->rsa私钥加密->Aes加密(登陆时候用登陆的TOKEN当KEY 否则默认KEY)->base64编码
 * 服务器解密  base64解码->aes解密Aes(登陆时候用登陆的TOKEN当KEY 否则默认KEY)->rsa私钥解密->utf8解码
 * 
 * 客户端加密 utf8编码->rsa公钥加密->Aes加密(登陆时候用登陆的TOKEN当KEY 否则默认KEY)->base64编码
 * 客户端解密  base64解码->aes解密(登陆时候用登陆的TOKEN当KEY 否则默认KEY)->rsa公钥解密->utf8解码
 * */
public class AppEncrypt implements ApplicationContextAware {

	static ApplicationContext applicationContext;
	static AppEncrypt gInstance;

	public static AppEncrypt getInstance() {

		if (gInstance == null) {
			synchronized (AppEncrypt.class) {
				if (gInstance == null) {
					try {
						if (applicationContext != null)
							gInstance = applicationContext
									.getBean(AppEncrypt.class);
					} catch (Exception e) {

					}
					if (gInstance == null)
						gInstance = new AppEncrypt();
				}
			}
		}
		return gInstance;
	}

	private String strEncode = "UTF8";

	// 16字节 默认加密向量
	private byte[] aesIv = { (byte) 0xfe, (byte) 0xb6, (byte) 0xf9,
			(byte) 0x89, (byte) 0x75, (byte) 0x0e, (byte) 0x98, (byte) 0x56,
			(byte) 0x99, (byte) 0x53, (byte) 0xa9, (byte) 0xa8, (byte) 0xb9,
			(byte) 0x16, (byte) 0x55, (byte) 0x97 };
	// 16字节 默认Key
	private byte[] KEY;
	// 默认公钥
	private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZvOyph9N2A5DThKGs70EkmeT1u1BfCbmFmeJvKQkx/hPHrhLoh1NdIS4z9c0buGkSJHQQ6VkvFch2CqS5ii4gSGZJ0S8PyAoAPXr+xHQGsqOsyoCBGCwqKx5Su69nWpq8OUAlvBD/nUcNMeqvnzjZpO69MeKLXuAAejjpBTwBLQIDAQAB";
	// 默认私钥
	private String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJm87KmH03YDkNOEoazvQSSZ5PW7UF8JuYWZ4m8pCTH+E8euEuiHU10hLjP1zRu4aRIkdBDpWS8VyHYKpLmKLiBIZknRLw/ICgA9ev7EdAayo6zKgIEYLCorHlK7r2damrw5QCW8EP+dRw0x6q+fONmk7r0x4ote4AB6OOkFPAEtAgMBAAECgYBRrAwOBBpSl5JYzB0XwgLZbugzo08PLhtg3l9srmA0LsZTTia9YSboUrg44lwUXeNzv+npEg5O9UOJch4Qc1gM+PF4/9PQzqckNfBPiwDfKAuMftHmzXGFhlexXYw00KU7Fi+QP1/2l8nPshC5axHrzJSVHZYZgyCCNDLrTS0AAQJBAOiq6Oo+MyE4Ib0LQkn83d6VTtxaSbacWV0uyzPGnd3sYn57SLsnLLB59FPYXGsnYUKbtCRNafa0UBN7ITSepxECQQCpJ7eZMxexO2CkDN9WHAalJ2RxOErqUvhkx5cjsR9kfVK0VnaKcPTeNb98a+M7/CAo6Oe+NLmxJ+KaHYyFJFBdAkAF1wxjnM4RvmPyZ87xJny3Mo45L7b78262zQ7irNCXmnBadmO79t7DSzxbwmmHcwSY9GmQI7VOMPZ1lridpoHRAkAlyWmKH4+7V7hO7VE2uywA2LvnTtvtXHuAh9WLc5W/rP88qdPxOSp6OSd9c9xfGgNVIDIkm0KgAHjuVIt/co1BAkBUlVNJkcPfn9Dpg1YXN8nVSl/vT4qV/obwCDvC2OUteC3p4zB/I6xPrOsvlQT4iieoTqDZ/5O+OeEhzK6Xv0rC";
	//设置默认AES key 和iv
	public AppEncrypt() {
		try {
			this.setKey("66a5d79394b647c18f7d4eba4a56e737992bd63b2c342689");
			this.setAesIv("feb6f989750e98569953a9a8b9165597");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String getStrEncode() {
		return strEncode;
	}

	public void setStrEncode(String strEncode) {
		this.strEncode = strEncode;
	}

	public void setKey(String key) throws Exception {
		if (key.length() < 32) {
			throw new Exception("必须32字符");
		}
		key = key.substring(0, 32);
		KEY = ByteArrayUtil.hex2byte(key);
	}

	public void setAesIv(String iv) throws Exception {
		if (iv.length() < 32) {
			throw new Exception("必须16字符");
		}

		iv = iv.substring(0, 32);
		this.aesIv = ByteArrayUtil.hex2byte(iv);
		 
	}

	private byte[] _encript(byte[] data, boolean isPrivate, String key,
			String iv, String aesKey) throws Exception {
		byte[] ret = null;
		String rsaKey = key;
		if (StringUtil.isEmpty(key)) {
			rsaKey = isPrivate ? privateKey : publicKey;
		}

		byte[] byteData = data;
		RSAUtil rsaUtil = RSAUtil.getInstance();
//		if (isPrivate) {
//			byteData = rsaUtil.encrypt(rsaUtil.getPrivateKey(rsaKey), byteData);
//		} else {
//			byteData = rsaUtil.encrypt(rsaUtil.getPublicKey(rsaKey), byteData);
//		}
		byte[] byteIv = aesIv;
		byte[] byteKey = KEY;
		if (StringUtil.isNotEmpty(iv)) {
			byteIv = ByteArrayUtil.hex2byte(iv);
		}
		if (StringUtil.isNotEmpty(aesKey)) {
			if (aesKey.length() > 32) {
				aesKey = aesKey.substring(0, 32);
			}
			byteKey = ByteArrayUtil.hex2byte(aesKey);
		}

		byteData = AESUtil.encryptAesCBC(byteData, byteKey, byteIv);
		ret = byteData;
		return ret;
	}

	 

	private byte[] _decript(byte[] data, boolean isPrivate, String key,
			String iv, String aesKey) throws Exception {

		String rsaKey = key;
		if (StringUtil.isEmpty(key)) {
			rsaKey = isPrivate ? privateKey : publicKey;
		}
		byte[] byteIv = aesIv;
		byte[] byteKey = KEY;
		if (StringUtil.isNotEmpty(iv)) {
			byteIv = ByteArrayUtil.hex2byte(iv);
		}
		if (StringUtil.isNotEmpty(aesKey)) {
			if (aesKey.length() > 32) {
				aesKey = aesKey.substring(0, 32);
			}
			byteKey = ByteArrayUtil.hex2byte(aesKey);
		}
		byte[] byteData = AESUtil.decryptAesCBC(data, byteKey, byteIv);
	//	byte[] byteData = data;
//		RSAUtil rsaUtil = RSAUtil.getInstance();
//		if (isPrivate) {
//			byteData = rsaUtil.decrypt(rsaUtil.getPrivateKey(rsaKey), byteData);
//		} else {
//			byteData = rsaUtil.decrypt(rsaUtil.getPublicKey(rsaKey), byteData);
//		}
		return byteData;
	}

	public String encriptPrivateKey(String data, String privateKey, String iv,
			String aesKey) throws Exception {
		byte[] byteData = data.getBytes(strEncode);
		byteData = this._encript(byteData, true, privateKey, iv, aesKey);
		return Base64Util.base64Encode(byteData);
	}

	public String decriptPrivateKey(String data, String privateKey, String iv,
			String aesKey) throws Exception {
		byte[] byteData = Base64Util.base64Decode(data);
		byteData = this._decript(byteData, true, privateKey, iv, aesKey);
		return new String(byteData, strEncode);
	}

	public String encriptPublicKey(String data, String privateKey, String iv,
			String aesKey) throws Exception {
		byte[] byteData = data.getBytes(strEncode);
		byteData = this._encript(byteData, false, privateKey, iv, aesKey);
		return Base64Util.base64Encode(byteData);
	}

	public String decriptPublicKey(String data, String privateKey, String iv,
			String aesKey) throws Exception {
		byte[] byteData = Base64Util.base64Decode(data);
		byteData = this._decript(byteData, false, privateKey, iv, aesKey);
		return new String(byteData, strEncode);
	}

	public String decryptPrivateKey(String data, String tokenID)
			throws Exception {
		String privateKey = null;
		//return data;
		if (StringUtil.isNotEmpty(tokenID)) {
			Jedis client = MemShardStrore.getInstance().getClient();
			privateKey = client.hget("clientPrivate", tokenID);
		}
		return this.decriptPrivateKey(data, privateKey, null, tokenID);
	}

	public String encryptPrivateKey(String data, String tokenID)
			throws Exception {
		String privateKey = null;
	 
		if (StringUtil.isNotEmpty(tokenID)) {
			Jedis client = MemShardStrore.getInstance().getClient();
			privateKey = client.hget("clientPrivate", tokenID);
		}
		return this.encriptPrivateKey(data, privateKey, null, tokenID);
	}
	public String encryptPublicKey(String data, String tokenID)
			throws Exception {


		return this.encriptPublicKey(data, publicKey, null, tokenID);
	}
	public String decryptPublicKey(String data, String tokenID)
			throws Exception {

		return this.decriptPublicKey(data, publicKey, null, tokenID);
	}
	public static void main(String[] args) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		AppEncrypt encript = new AppEncrypt();
		do {
			String str = reader.readLine();
			if (str.equals("\n")) {
				break;
			}
			long tm = System.currentTimeMillis();
			String str1 =	encript.encryptPublicKey(str,null);
			String str2 =  encript.decryptPrivateKey(str1, null);
			System.out.println("公钥加密：" + str1 + "\n  私钥解密:" +str2);
			
			str1 =	encript.encryptPrivateKey(str, null);
			str2 =  encript.decryptPublicKey(str1, null);
			System.out.println("私钥加密：" + str1 + "\n  公钥解密:" +str2);
			
//			Map<String, String> map = RSAUtil.getInstance().genKey();
//			System.out.println("生成Key 耗时 " + (System.currentTimeMillis() - tm));
//
//			String privateKey = map.get(RSAUtil.PRIVATE_KEY);
//			String publicKey = map.get(RSAUtil.PUBLIC_KEY);
//
//			tm = System.currentTimeMillis();
//			String encyptStr = encript.encriptPrivateKey(str, privateKey, null,
//					null);
//			System.out.println("privateKey 加密耗时 :"
//					+ (System.currentTimeMillis() - tm) + "\n" + encyptStr);
//
//			tm = System.currentTimeMillis();
//			String decriptStr = encript.decriptPublicKey(encyptStr, publicKey,
//					null, null);
//			System.out.println("publicKey 解密耗时 :"
//					+ (System.currentTimeMillis() - tm) + "\n" + decriptStr);
//			AppEncrypt appEncrypt = AppEncrypt.getInstance();
//			String testEncrypt = appEncrypt.encriptPublicKey(str, null, null,
//					null);
//			System.out.println("app加密：" + testEncrypt);
//			testEncrypt = appEncrypt.decryptPrivateKey(testEncrypt, null);
//			System.out.println("app解密:" + testEncrypt);
		} while (true);
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		AppEncrypt.applicationContext = appContext;

	}

}
