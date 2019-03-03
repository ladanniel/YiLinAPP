package com.memory.platform.global;

import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/*
 * by lil AES加密
 * */
public class AESUtil {
	public static final String AES_CBS = "AES/CBC/PKCS5Padding";
	public static final String AES_KEY = "AES";
	private static final String KEY = "7e493d6153887a0a7e493d6153887a0a";
	// 8位10进制秘钥数组
	private static final byte[] KEYIV = { 1, 9, 9, 0, 0, 5, 1, 7, 8, 3, 7, 1, 0, 8, 4, 8 };

	private static byte[] encryptAesCBC(byte[] data, byte[] key, byte[] iv, int encriptMode) {
		byte[] ret = null;
		try {

			if (key == null) {
				key = ByteArrayUtil.hex2byte(KEY);
			}
			if (iv == null) {
				iv = KEYIV;
			}
			Cipher cipher = Cipher.getInstance(AES_CBS);
			SecretKeySpec keySpec = new SecretKeySpec(key, AES_KEY);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			cipher.init(encriptMode, keySpec, ivParameterSpec);
			int cnt = (data.length + 15) / 16 * 16;
			ByteArrayOutputStream stream = new ByteArrayOutputStream(cnt);
			stream.write(cipher.doFinal(data));
			ret = stream.toByteArray();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ret;
	}

	public static byte[] encryptAesCBC(byte[] data, byte[] key, byte[] iv) {
		return encryptAesCBC(data, key, iv, Cipher.ENCRYPT_MODE);
	}

	public static byte[] decryptAesCBC(byte[] data, byte[] key, byte[] iv) {
		return encryptAesCBC(data, key, iv, Cipher.DECRYPT_MODE);
	}
}
