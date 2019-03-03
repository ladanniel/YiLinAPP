
package com.memory.platform.global;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年5月21日 上午10:12:39 修 改 人： 日 期： 描 述： 版 本 号： V1.0
 */
public class ThreeDes {
	private static final String Algorithm = "DESede"; // 定义加密算法,可用
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);// 在单一方面的加密或解密
		} catch (java.security.NoSuchAlgorithmException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static String en3des(String szSrc) {
		String szcode = "";
		final byte[] keyBytes = { 0x12, 0x23, 0x5F, 0x68, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51,
				(byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 }; // 24字节的密钥

		// String szSrc = "This is a 3DES test.测试信息";
		byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());
		try {
			String s1s = MdInputStreamUtils.byteTOString(encoded);
			szcode = MdInputStreamUtils.encode(s1s);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return szcode;
	}

	public static String de3des(String szencode) {
		String szcode = "";
		final byte[] keyBytes = { 0x12, 0x23, 0x5F, 0x68, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51,
				(byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 }; // 24字节的密钥
		try {
			String szdiscode = MdInputStreamUtils.decode(szencode);
			byte[] s1b = MdInputStreamUtils.StringTObyte(szdiscode);
			byte[] srcBytes1 = decryptMode(keyBytes, s1b);
			szcode = (new String(srcBytes1)) + "";
		} catch (Exception e) {
			e.getStackTrace();
		}
		return szcode;
	}

	public static void main(String[] args) {
		String szSource = "522127198910062012";
		String szencode = en3des(szSource);
		String szdecode = de3des("C2AE6F6BC39F30C392C3BEC2855B016D6EC2AF6F706CC2B9C282C399C2B1121EC396C28A");
		System.out.println("原始信息:" + szSource);
		System.out.println("加密信息:" + szencode);
		System.out.println("解密信息:" + szdecode);
	}
}
