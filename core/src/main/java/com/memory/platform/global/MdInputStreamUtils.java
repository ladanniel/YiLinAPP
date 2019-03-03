package com.memory.platform.global;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  

public class MdInputStreamUtils {  
    final static int BUFFER_SIZE = 4096;  
    private static String hexString = "0123456789ABCDEF";
    
    /** 
     * 将InputStream转换成String */
    public static String InputStreamTOString(InputStream in) throws Exception{  
          
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return new String(outStream.toByteArray(),"ISO-8859-1");  
    }  
      
    /** 
     * 将InputStream转换成某种字符编码的String 
     * @param in 
     * @param encoding 
     * @return 
     * @throws Exception 
     */  
         public static String InputStreamTOString(InputStream in,String encoding) throws Exception{  
          
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return new String(outStream.toByteArray(),"ISO-8859-1");  
    }  
      
    /** 
     * 将String转换成InputStream 
     * @param in 
     * @return 
     * @throws Exception 
     */  
    public static InputStream StringTOInputStream(String in) throws Exception{  
          
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("UTF-8"));  
        return is;  
    }  
      
    /** 
     * 将InputStream转换成byte数组 
     * @param in InputStream 
     * @return byte[] 
     * @throws IOException 
     */  
    public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
          
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return outStream.toByteArray();  
    }  
      
    /** 
     * 将byte数组转换成InputStream 
     * @param in 
     * @return 
     * @throws Exception 
     */  
    public static InputStream byteTOInputStream(byte[] in) throws Exception{  
          
        ByteArrayInputStream is = new ByteArrayInputStream(in);  
        return is;  
    }  
      
    /** 
     * 将byte数组转换成String 
     * @param in 
     * @return 
     * @throws Exception 
     */  
    public static String byteTOString(byte[] in) throws Exception{  
        InputStream is = byteTOInputStream(in);  
        return InputStreamTOString(is);
    }
    public static byte[] StringTObyte(String str) throws Exception{
    	InputStream is = StringTOInputStream(str);    	
    	return InputStreamTOByte(is);
    }
 
      
    /* 
    * 将字符串编码成16进制数字,适用于所有字符（包括中文） 
    */  
    public static String encode(String str) {  
       // 根据默认编码获取字节数组  
       byte[] bytes = str.getBytes();  
       StringBuilder sb = new StringBuilder(bytes.length * 2);  
       // 将字节数组中每个字节拆解成2位16进制整数  
       for (int i = 0; i < bytes.length; i++) {  
        sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));  
        sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));  
       }  
       return sb.toString();  
    }  
    /* 
    * 将16进制数字解码成字符串,适用于所有字符（包括中文） 
    */  
    public static String decode(String bytes) {  
       ByteArrayOutputStream baos = new ByteArrayOutputStream(  
         bytes.length() / 2);  
       // 将每2位16进制整数组装成一个字节  
       for (int i = 0; i < bytes.length(); i += 2)  
        baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString  
          .indexOf(bytes.charAt(i + 1))));  
       return new String(baos.toByteArray());  
    }    
}  