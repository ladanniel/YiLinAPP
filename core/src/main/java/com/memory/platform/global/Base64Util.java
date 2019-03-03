package com.memory.platform.global;

import java.io.IOException;

import com.mchange.util.Base64Encoder;

public class Base64Util {
	@SuppressWarnings("restriction")
	public static String   base64Encode(byte[] data) {
		return new sun.misc.BASE64Encoder().encode(data);
	}
	@SuppressWarnings("restriction")
	public static byte[] base64Decode(String base64Str) {
		try {
			return  new sun.misc.BASE64Decoder().decodeBuffer(base64Str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
