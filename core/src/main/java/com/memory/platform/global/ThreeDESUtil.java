package com.memory.platform.global;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javassist.bytecode.ByteArray;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alibaba.druid.sql.visitor.functions.Length;
import com.memory.platform.core.AppUtil;

import freemarker.cache.StrongCacheStorage;
/**
* 创 建 人： longqibo
* 日    期： 2016年5月11日 下午12:34:25 
* 修 改 人： 
* 日   期： 
* 描   述： 3DES加密解密工具类
* 版 本 号：  V1.0
 */
public class ThreeDESUtil {
	// 算法名称 
    private static final String KEY_ALGORITHM =  "DES";//"DES";
    // 算法名称/加密模式/填充方式 
    private static final String CIPHER_ALGORITHM = "DES"; //"DESede/CBC/PKCS5Padding";
    //48位秘钥串
    private static final byte[] KEY = "4519674450051753179569836DC0F83869836DC0F838C0F7".getBytes();
    //8位10进制秘钥数组
    private static final byte[] KEYIV = {1,9,9,0,0,5,1,7};

    
    /**
    * 功能描述： CBC加密
    * 输入参数:  @param key 密钥
    * 输入参数:  @param keyiv
    * 输入参数:  @param data  明文
    * 输入参数:  @return  Base64编码的密文 
    * 输入参数:  @throws Exception
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午12:35:03
    * 修 改 人: 
    * 日    期: 
    * 返    回：byte[]
     */
    public static String des3EncodeCBC(String data) throws Exception {
        Security.addProvider(new BouncyCastleProvider()); 
        Key deskey = keyGenerator(new String(KEY));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(KEYIV);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(string2Bytes(data));
        for (int k = 0; k < bOut.length; k++) {
        }
        return base64Encoder(bOut);
        
    }
    public static String  testDes3Encode(String data,String key) throws Exception {
    	Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
    	  Key deskey = keyGenerator(key);
        IvParameterSpec ips = new IvParameterSpec(KEYIV);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] arr =  string2Bytes(data);
        AppUtil.printByteArray("加密的byte len " + arr.length,arr);
        byte[] bOut =  cipher.doFinal(arr);
        AppUtil.printByteArray("加密后的byte len" + bOut.length, bOut);
        return base64Encoder(bOut);
	}
    
    /**
    * 功能描述： 生成密钥key对象
    * 输入参数:  @param keyStr 密钥字符串 
    * 输入参数:  @return 密钥对象 
    * 输入参数:  @throws Exception
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午12:39:21
    * 修 改 人: 
    * 日    期: 
    * 返    回：Key
     */
    private static Key keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        byte[] newInput = new byte[8];
        System.arraycopy(input, 0, newInput, 0	, 8);
       // AppUtil.printByteArray("len:"+newInput.length+ " key的byte: ",newInput);
//        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
//        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
//        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
        return new SecretKeySpec(newInput, KEY_ALGORITHM);
    }

    /**
    * 功能描述： 
    * 输入参数:  @param c
    * 输入参数:  @return
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午12:40:03
    * 修 改 人: 
    * 日    期: 
    * 返    回：int
     */
    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }
 
    /**
    * 功能描述： 16进制转换字节数组
    * 输入参数:  @param hexstr
    * 输入参数:  @return
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午12:40:25
    * 修 改 人: 
    * 日    期: 
    * 返    回：byte[]
     */
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
    /**
     * 功能描述： CBC加密
     * 输入参数:  @param key 密钥
     * 输入参数:  @param keyiv
     * 输入参数:  @param data  明文
     * 输入参数:  @return  Base64编码的密文 
     * 输入参数:  @throws Exception
     * 异    常： 
     * 创 建 人: longqibo
     * 日    期: 2016年5月11日下午12:35:03
     * 修 改 人: 
     * 日    期: 
     * 返    回：byte[]
      */
     public static String des3AppEncodeCBC(String data,String key) throws Exception {
     	//data  = ZipUtils.gzip(data);
     	//System.out.println("加密gzipString: " + data);
         Security.addProvider(new BouncyCastleProvider()); 
         Key deskey = keyGenerator(key);
         int length = deskey.getEncoded().length;
        // System.out.print(length);
         Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
         IvParameterSpec ips = new IvParameterSpec(KEYIV);
         cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
         byte[] arr =  string2Bytes(data);
       //  AppUtil.printByteArray("加密的byte",arr);
         byte[] bOut = cipher.doFinal(arr);
     //    ZipUtils.gzip(primStr)
         return base64Encoder(bOut);
         
     }

    /**
    * 功能描述： CBC解密 
    * 输入参数:  @param key  密钥 
    * 输入参数:  @param keyiv
    * 输入参数:  @param data  Base64编码的密文 
    * 输入参数:  @return
    * 输入参数:  @throws Exception
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午12:41:59
    * 修 改 人: 
    * 日    期: 
    * 返    回：byte[]
     */
    public static String des3DecodeCBC(String data) throws Exception {
    	    if(StringUtils.isBlank(data))
    		return null;
	        Key deskey = keyGenerator(new String(KEY));
	        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	        IvParameterSpec ips = new IvParameterSpec(KEYIV);
	        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
	        byte[] bOut = cipher.doFinal(base64Decoder(data));
	        return new String(bOut, "UTF-8");
    }

    /**
     * 功能描述： CBC解密 
     * 输入参数:  @param key  密钥 
     * 输入参数:  @param keyiv
     * 输入参数:  @param data  Base64编码的密文 
     * 输入参数:  @return
     * 输入参数:  @throws Exception
     * 异    常： 
     * 创 建 人: longqibo
     * 日    期: 2016年5月11日下午12:41:59
     * 修 改 人: 
     * 日    期: 
     * 返    回：byte[]
      */
     public static String des3DecodeCBC(String data,String token) throws Exception {
     	    if(StringUtils.isBlank(data))
     		return null;
 	        Key deskey = keyGenerator(new String(token));
 	        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
 	        IvParameterSpec ips = new IvParameterSpec(KEYIV);
 	        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
 	        byte[] dataOut = base64Decoder(data);
 	       // AppUtil.printByteArray("数据的base64",dataOut);
 	        byte[] bOut = cipher.doFinal(dataOut);
 	       // AppUtil.printByteArray("解密后的byte",bOut);
 	        String str =  new String(bOut, "UTF-8");
 	        return str;
 	        //return  ZipUtils.gunzip(str);
     }
    /**
    * 功能描述： 字符转换byte
    * 输入参数:  @param data
    * 输入参数:  @return
    * 输入参数:  @throws UnsupportedEncodingException
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午2:39:36
    * 修 改 人: 
    * 日    期: 
    * 返    回：byte[]
     */
    public static  byte[] string2Bytes(String data) throws UnsupportedEncodingException{
    	return data.getBytes("UTF-8");
    }
    
    /**
    * 功能描述： base64编码
    * 输入参数:  @param data
    * 输入参数:  @return
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午2:50:15
    * 修 改 人: 
    * 日    期: 
    * 返    回：String
     */
	public static String base64Encoder(byte[] data){
    	return  new sun.misc.BASE64Encoder().encode(data);
    }
    
    /**
    * 功能描述：  base64解码
    * 输入参数:  @param data
    * 输入参数:  @return
    * 输入参数:  @throws IOException
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午3:25:17
    * 修 改 人: 
    * 日    期: 
    * 返    回：byte[]
     */
	public static byte[] base64Decoder(String data) throws IOException{
    	return new sun.misc.BASE64Decoder().decodeBuffer(data);
    }
    
    /**
    * 功能描述： 测试
    * 输入参数:  @param args
    * 输入参数:  @throws Exception
    * 异    常： 
    * 创 建 人: longqibo
    * 日    期: 2016年5月11日下午12:47:05
    * 修 改 人: 
    * 日    期: 
    * 返    回：void
     */
    public static void main(String[] args) throws Exception {
        String data = "123";
        System.out.println("原需要加密的数据：" + data);
        System.out.println("CBC加密解密");
        String str = des3EncodeCBC(data);
        System.out.println("加密结果：" + str);
        System.out.println("解密结果：" + des3DecodeCBC(str));
     //   System.out.println("解密结果：" + des3DecodeCBC("9bRq0tyAmwe="));
    }
}