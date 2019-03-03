package com.memory.platform.global;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Assert;

import com.mxgraph.io.graphml.mxGraphMlKey.keyForValues;

public class RSAUtil {
	public final static String RSA = "RSA";
	public final static String PUBLIC_KEY = "a3";
	public final static String PRIVATE_KEY = "a2";
	public final static String STRING_ENCODE = "UTF8";
	private static RSAUtil instance = new RSAUtil();

	public static RSAUtil getInstance() {
		return instance;
	}

	public Map<String, String> genKey() {
		Map<String, String> ret = new HashMap<>();
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance(RSA);
			gen.initialize(1024);
			KeyPair keyPair = gen.generateKeyPair();
			String publicKey = Base64Util.base64Encode (keyPair.getPublic().getEncoded());
			ret.put(PUBLIC_KEY, publicKey);
			String privateKey = Base64Util.base64Encode (keyPair.getPrivate().getEncoded());
			ret.put(PRIVATE_KEY, privateKey);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		return ret;

	}

	public PublicKey getPublicKey(String key) {
		byte[] keyBytes;
		keyBytes = Base64Util.base64Decode(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance(RSA);
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public PrivateKey getPrivateKey(String key) {
		byte[] keyBytes;
		keyBytes = Base64Util.base64Decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance(RSA);
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// 拆分byte数组
	public static byte[][] splitBytes(byte[] bytes, int splitLength) {
		int x; // 商，数据拆分的组数，余数不为0时+1
		int y; // 余数
		y = bytes.length % splitLength;
		if (y != 0) {
			x = bytes.length / splitLength + 1;
		} else {
			x = bytes.length / splitLength;
		}
		byte[][] arrays = new byte[x][];
		byte[] array;
		for (int i = 0; i < x; i++) {
			int copyLen = bytes.length - i * splitLength;
			if (copyLen >= splitLength) {
				copyLen = splitLength;
			}
			array = new byte[copyLen];
			System.arraycopy(bytes, i * splitLength, array, 0, copyLen);
			arrays[i] = array;
		}
		return arrays;
	}

	private byte[] _encrypt(Key key, byte[] plainTextData) throws Exception {
		 
		Cipher cipher = null;

		cipher = Cipher.getInstance(RSA);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		int splitCount = 117;
		int byteCount = (plainTextData.length + 127) / 128 * 128;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(byteCount);
		for (int i = 0; i < plainTextData.length; i += splitCount) {
			int copyCnt = splitCount;
			if (plainTextData.length - i < copyCnt) {
				copyCnt = plainTextData.length - i;
			}
			outputStream.write(cipher.doFinal(plainTextData, i, copyCnt));
		}

		return outputStream.toByteArray();

	}

	public byte[] encrypt(PrivateKey privateKey, byte[] plainTextData) throws Exception {
		return _encrypt(privateKey, plainTextData);

	}

	public byte[] encrypt(PublicKey publicKey, byte[] plainTextData) throws Exception {
		return _encrypt(publicKey, plainTextData);

	}

	/**
	 * 解密过程
	 * 
	 * @param key
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	private byte[] _decrypt(Key key, byte[] cipherData) throws Exception {
	 
		Cipher cipher = null;

		cipher = Cipher.getInstance("RSA");// , new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, key);
		int splitCount = 128;

		int byteCount = (cipherData.length + 127) / 128 * 128;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(byteCount);

		for (int i = 0; i < cipherData.length; i += splitCount) {
			int copyCnt = splitCount;

			outputStream.write(cipher.doFinal(cipherData, i, copyCnt));
		}
		return outputStream.toByteArray();

	}

	public byte[] decrypt(PrivateKey privateKey, byte[] cipherData) throws Exception {
		return _decrypt(privateKey, cipherData);
	}

	public byte[] decrypt(PublicKey publicKey, byte[] cipherData) throws Exception {
		return _decrypt(publicKey, cipherData);
	}

	public String encriptPublicKey(String data, String publicKey) {
		String ret = "";
		try {
			byte[] byteData = this.encrypt(this.getPublicKey(publicKey), data.getBytes(STRING_ENCODE));
			byteData =GZipUtil.compress(byteData);
			ret = Base64Util.base64Encode(byteData);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}

	public String encriptPrivateKey(String data, String privateKey) {
		String ret = "";
		try {
			byte[] byteData = this.encrypt(this.getPrivateKey(privateKey), data.getBytes(STRING_ENCODE));
			byteData = GZipUtil.compress(byteData);
			ret = Base64Util.base64Encode (byteData);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}

	public String decriptPublicKey(String data, String publicKey) {

		String ret = "";
		try {
			byte[] byteData = Base64Util.base64Decode (data);
			byteData = GZipUtil.uncompress(byteData);
			byteData = this.decrypt(this.getPublicKey(publicKey), byteData);
			ret = new String(byteData, STRING_ENCODE);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}

	public String decriptPrivateKey(String data, String privateKey) {

		String ret = "";
		try {
			byte[] byteData =Base64Util.base64Decode (data);
			byteData = GZipUtil.uncompress(byteData);
			byteData = this.decrypt(this.getPrivateKey(privateKey), byteData);
			ret = new String(byteData, STRING_ENCODE);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		testWeixin();
		//testInfo();
	}

	private static void testInfo() {
		RSAUtil rsaUtil = RSAUtil.getInstance();
		Map<String, String> keys = rsaUtil.genKey();
		String strPublic = keys.get(PUBLIC_KEY);
		String strPrivate = keys.get(PRIVATE_KEY);

		System.out.println(String.format("public:%s \n private:%s \n", strPublic, strPrivate));

		PublicKey publicKey = rsaUtil.getPublicKey(strPublic);
		PrivateKey privateKey = rsaUtil.getPrivateKey(strPrivate);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		do {
			try {
				str = reader.readLine();
				System.out.println("*******************");
				System.out.println("*******************");
				long current = System.currentTimeMillis();
				byte[] data = rsaUtil.encrypt(publicKey, str.getBytes(STRING_ENCODE));
				System.out.println("加密速度:" + (System.currentTimeMillis() - current) + "毫秒");
				System.out.println("公钥加密数据 :" + Base64Util.base64Encode (data));
				current = System.currentTimeMillis();
				byte[] utf8Data = rsaUtil.decrypt(privateKey, data);
				System.out.println("解密速度:" + (System.currentTimeMillis() - current) + "毫秒");
				String utf8String = new String(utf8Data, STRING_ENCODE);
				System.out.println("公钥匙加密 私钥解密：" + utf8String);

				data = rsaUtil.encrypt(privateKey, str.getBytes(STRING_ENCODE));
				System.out.println("私钥加密数据 :" +  Base64Util.base64Encode(data));
				utf8Data = rsaUtil.decrypt(publicKey, data);
				utf8String = new String(utf8Data, STRING_ENCODE);
				System.out.println("私钥匙加密 公钥解密：" + utf8String);
				test2(str, strPublic, strPrivate);
				// 直接私密解密
				test3(str, strPrivate);
				// data = rsaUtil.
			} catch (Exception e) {

				e.printStackTrace();
			}

		} while (StringUtil.isNotEmpty(str) && !str.equals("\n"));
	}

	private static void testWeixin() {
		 String weiXinPrivate = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJltCbrgSB4C3Y2jlqWFIchjxPlO5Hzdx5UhaAODU+VrczZy3Iam3HI0ZG98NJpV2wHUrdaItRJfDYiCyyvkdb57YeI3b/rEIkQ5GLZ14Tmw1a8FG1Oenbeq4vS6yw6rxNPSd93RRZibBKr33cu4vb7JoCxO2W7Jl7nzcYuVG21NWhVPRCFGKBVn8C4HiNRu616EIbT6Ixzejv8aL3h2jRYziienUABDsubqlHTKyfohR6t5MUN+O+DeZHmb9RUjLIaxXAEnJDTySesZ+/XODbuWy9QvMu7T0xv0KCOrGDWceixrPbZ1cImySsdCxCYJXRcXr0MEydMoE/3bkN2NZtAgMBAAECggEAGl37kyFDvakQ1hhVAi/jLY/xO4eDUyj9+XBfOQ4hCLJW7AZG2rvw9+cJPicSCAP1KR/rtuIBtu97fRbyob55nu/DxlVlLzPcwtmDo/Q1PeM97/MW/NlCWU7GkbEYOVu6nW+uRqwwCTYVNO+XStPNC0r7KfmQCfmQabb9geJbacpZY9qPq2al5RQkRuIMAqZcL/6vxD4HXy6aOtGlZ710UfunJQycovp6k075bgP6WkD0rKTyzZ2x/WOzLoM5HPTe04mTM3lzzWIwS7pPY0hn09o5ezwFs1cPITPyOoTUPvf3scfCES5WwhxjxiFTtuaGTYAuMHO8tX0QFxiBNxmlgQKBgQDpeGqy9L6cKk7slrN/40mnKmehy+rlFjyNnDwIelEB69Ovsqrb9WCZdgDzj6e7lvQd3C1JyL5DeKdmDEdBDeNSt1CBQVXGdxi0yseghGK7Qs6rHzIVsRWkyMtP1aHke5QLvyR2Rmn7H2mAPlNaqRTn/j6dlPHuPnfxLBSGo1vv5QKBgQDdCtAbnS8IDOeEPHE3Hr/K/6Zh3X7pZAUwhLDwGX+g2drCnUWxLzhPt+eJITQpWg00NlLfvHfYkEAjOg5LITVsD2tfL2Wbd3T7qB1FbK2ZtBeiCWoSq3PkneaV5o//cq56u2R8JnpvIV1tZ2fXcgvaJLb+zTUiuTm9OlpzDg+T6QKBgBstflY4sVuifS+DV3/iqHdCM3AdOcnTLddaQY7HFlj/59ODG+qhBM64cuHnbiIjL2WudMMrECLwj6QMd9r1z+mYF1IElgSVZpXdKSHhZ1DfNQmshvsJcziMe0Ze2wqq7A/1/zuZdMrb3D8mgjOCjUF4Ujer2AHg2buEYxtNNu2VAoGASYDL/TLCrfmkjxuORlcKThW/HTILfs7MzvqA4AaKo1FuZmLoBuh94Dx2WOparxmL5H3gBxhj/fbR7STkFeWgHyPtf4SCVDVgCEM6IJjoDYGOjkp1JGoxKqe7QsOdxMM89AEWhBZDhmrg532IUWNSP6MP2yEf90ECy0L5SuM1GOkCgYEAwhUp78ZmGJZBkVujFqvrzeFAsIhsT+NQajap60eVJxxMJXdsAGxJfYrxJT6W8Bomr1xx7CQAY1OO+PpSYZ6GvIdj+AiZhIJBPC8ymB05RJCKlH4iwX4jIiwIwLI2kQbPQM9MR965Zuhc6/pOcb/rjGNkC+CX4CVw+z+t4rIyV9A=";
		 RSAUtil rsaUtil = RSAUtil.getInstance();
		PrivateKey privateKey =  rsaUtil.getPrivateKey(weiXinPrivate);
		String str ="";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		do {
			try {
				  str = reader.readLine();
				  String data = rsaUtil.encriptPrivateKey(str, weiXinPrivate);
				  System.out.print(data);
			}catch (Exception e) {

				e.printStackTrace();
			}
		}while(StringUtil.isNotEmpty(str) && !str.equals("\n"));
	}

	private static void test3(String str, String strPrivate) {
		RSAUtil rsaUtil = RSAUtil.getInstance();
		byte[] base64 =  Base64Util.base64Decode(str);
		try {
			byte[] data = rsaUtil.decrypt(rsaUtil.getPrivateKey(strPrivate), base64);
			String unString = new String(data);
			System.out.println("直接私密解密: " + unString);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static void test2(String str, String publicKey, String privateKey) {
		RSAUtil rsaUtil = RSAUtil.getInstance();
		System.out.println("压缩过后的测试************************************************");
		String data = rsaUtil.encriptPublicKey(str, publicKey);
		System.out.println("publicKey 加密：" + data);
		data = rsaUtil.decriptPrivateKey(data, privateKey);
		System.out.println("privateKey 解密：" + data);

		data = rsaUtil.encriptPrivateKey(str, privateKey);
		System.out.println("privateKey 加密：" + data);
		data = rsaUtil.decriptPublicKey(data, publicKey);
		System.out.println("publicKey 解密：" + data);

	}
}
