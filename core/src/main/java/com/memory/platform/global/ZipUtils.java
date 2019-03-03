package com.memory.platform.global;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.memory.platform.core.AppUtil;

public class ZipUtils {
	
	/**

	* 使用gzip进行压缩
	*/
	public static String gzip(String primStr){
		if (primStr == null || StringUtil.isEmpty(primStr)) {
			return primStr;
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip=null;
		try {
			gzip = new GZIPOutputStream(out);
			byte[] data = primStr.getBytes("UTF-8");
			AppUtil.printByteArray("utf-8 len" + data.length, data);
			gzip.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(gzip!=null){
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		AppUtil.printByteArray("gzip  len" + out.toByteArray().length, out.toByteArray());
		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}
	
	public static byte[] gzipCompress( byte [] arr){
		byte[] ret = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(output);
		} catch (Exception e) {
			 
		}
		return ret ;
	}
	
 
	/**
	*
	* <p>Description:使用gzip进行解压缩</p>
	* @param compressedStr
	* @return
	*/
	public static String gunzip(String compressedStr){
		if (compressedStr == null || StringUtil.isEmpty(compressedStr)) {
			return compressedStr;
		}
		
		ByteArrayOutputStream out= new ByteArrayOutputStream();
		ByteArrayInputStream in=null;
		GZIPInputStream ginzip=null;
		byte[] compressed=null;
		String decompressed = null;
		
		try {
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in=new ByteArrayInputStream(compressed);
			
			ginzip=new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
			out.write(buffer, 0, offset);
			}
			decompressed=out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null){
				try {
					ginzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return decompressed;
	}
	
	
	public static void main(String[] args) throws Exception {
	/*	String str = "eyJhY2NvdW50IjoidGVzdDExMSIsInBob25lIjoiMTgyODUwNTM5MzQxMTExMSIsInBhc3N3b3Jk\r\nIjoiNDA2MTg2M0NBRjdGMjhDMEIwMzQ2NzE5RTc2NEQ1NjEiLCJzdGF0dXMiOiJzdGFydCIsImF1\r\ndGhlbnRpY2F0aW9uIjoibm90QXV0aCIsImNvbXBhbnkiOnsibmFtZSI6ImFzZGZhZXJxd2UiLCJj\r\nb21wYW55VHlwZSI6eyJuYW1lIjoi6LSn5Li7IiwiaXNfcmVnaXN0ZXIiOnRydWUsImlzX2F1dCI6\r\ndHJ1ZSwiYnVzaW5lc3NfbGljZW5zZSI6dHJ1ZSwiaWRjYXJkIjp0cnVlLCJkcml2aW5nX2xpY2Vu\r\nc2UiOnRydWUsImlkIjoiMiIsImNyZWF0ZV90aW1lIjoiQXByIDIzLCAyMDE2IDY6NTQ6MjAgUE0i\r\nLCJ1cGRhdGVfdGltZSI6Ik1heSAxMiwgMjAxNiAxMDozMTowMSBQTSIsInVwZGF0ZV91c2VyX2lk\r\nIjoiMmM5MmZkODE1MjBiODRhOTAxNTIwYjg5MTIzMjAwMDAifSwiYXJlYV9pZCI6IjM0MDMxMSIs\r\nImF1ZGl0Ijoid2FpdFByb2Nlc3MiLCJhcmVhRnVsbE5hbWUiOiLlronlvr3nnIEt6JqM5Z+g5biC\r\nLea3ruS4iuWMuiIsInN0YXR1cyI6Im9wZW4iLCJhZGRyZXNzIjoi5p6v5YWL5qC85YuD6Z2p6Z2p\r\n6KaB6KaBIiwiY29udGFjdE5hbWUiOiLmrablm73luoYiLCJjb250YWN0VGVsIjoiMTgyODUwNTM5\r\nMzQiLCJzb3VyY2UiOjEsImlkY2FyZF9pZCI6IjQwMjg4MDE1NTU4NjQyNGIwMTU1ODY1NmFmZDMw\r\nMDA1IiwiYnVzaW5lc3NfbGljZW5zZV9pZCI6IjQwMjg4MDE1NTU4NjQyNGIwMTU1ODY1NmFmZGMw\r\nMDA3IiwiZHJpdmluZ19saWNlbnNlX2lkIjoiNDAyODgwMTU1NTg2NDI0YjAxNTU4NjU2YWZkOTAw\r\nMDYiLCJhY2NvdW50X2lkIjoiNDAyODgxZTc1NGQyNDZkNjAxNTRkMjQ5NDgxNTAwMDEiLCJhdXRf\r\nc3VwcGxlbWVudF90eXBlIjoiIiwiaWQiOiI0MDI4ODFlNzU0ZDI0NmQ2MDE1NGQyNDk0ODBlMDAw\r\nMCIsInVwZGF0ZV90aW1lIjoiSnVuIDI1LCAyMDE2IDI6NTQ6MzYgUE0iLCJ1cGRhdGVfdXNlcl9p\r\nZCI6IjQwMjg4MWU3NTRkMjQ2ZDYwMTU0ZDI0OTQ4MTUwMDAxIn0sInJvbGUiOnsibmFtZSI6Iui0\r\np+S4u+euoeeQhuWRmCIsInN0YXR1cyI6dHJ1ZSwicm9sZV9jb2RlIjoiUk9MRV80ZDYzZWIzZS04\r\nOGM5LTQyYTEtOTZlYS1lYjE3YmEyZDIxN2EiLCJjb21wYW55VHlwZSI6eyJuYW1lIjoi6LSn5Li7\r\nIiwiaXNfcmVnaXN0ZXIiOnRydWUsImlzX2F1dCI6dHJ1ZSwiYnVzaW5lc3NfbGljZW5zZSI6dHJ1\r\nZSwiaWRjYXJkIjp0cnVlLCJkcml2aW5nX2xpY2Vuc2UiOnRydWUsImlkIjoiMiIsImNyZWF0ZV90\r\naW1lIjoiQXByIDIzLCAyMDE2IDY6NTQ6MjAgUE0iLCJ1cGRhdGVfdGltZSI6Ik1heSAxMiwgMjAx\r\nNiAxMDozMTowMSBQTSIsInVwZGF0ZV91c2VyX2lkIjoiMmM5MmZkODE1MjBiODRhOTAxNTIwYjg5\r\nMTIzMjAwMDAifSwiaXNfYXV0Ijp0cnVlLCJpZGNhcmQiOnRydWUsImRyaXZpbmdfbGljZW5zZSI6\r\ndHJ1ZSwiaXNfYWRtaW4iOnRydWUsImlkIjoiNDAyOGE4ODE1NDcwNDgwODAxNTQ3MDQ5Mzc2NTAw\r\nMDAiLCJjcmVhdGVfdGltZSI6Ik1heSAyLCAyMDE2IDM6MDU6MjcgUE0iLCJ1cGRhdGVfdGltZSI6\r\nIk1heSAyMiwgMjAxNiAxMDoxODowMSBBTSIsImFkZF91c2VyX2lkIjoiMmM5MmZkODE1MjBiODRh\r\nOTAxNTIwYjg5MTIzMjAwMDAiLCJ1cGRhdGVfdXNlcl9pZCI6IjJjOTJmZDgxNTIwYjg0YTkwMTUy\r\nMGI4OTEyMzIwMDAwIn0sImxvZ2luX2lwIjoiMTkyLjE2OC4wLjE0OSIsImxvZ2luX2NvdW50Ijox\r\nMCwibGFzdF9sb2dpbl90aW1lIjoiSnVuIDI1LCAyMDE2IDM6NTE6NTMgUE0iLCJjYXBpdGFsU3Rh\r\ndHVzIjoibm90ZW5hYmxlIiwidXNlclR5cGUiOiJjb21wYW55IiwiYXV0X3N1cHBsZW1lbnRfdHlw\r\nZSI6IiIsImlkIjoiNDAyODgxZTc1NGQyNDZkNjAxNTRkMjQ5NDgxNTAwMDEiLCJjcmVhdGVfdGlt\r\nZSI6Ik1heSAyMSwgMjAxNiAzOjQ4OjE5IFBNIn0=";
		String gzipStr  = gzip(str);
		String gzipStr1  = gunzip(gzipStr);
		
		System.out.println("原字符串：" + str.length());
	    System.out.println("压缩：" + gzipStr.length());
        System.out.println("解压缩：" + gzipStr1.length());*/
		/*String str = "3mHsw2TwuTkNFINKzrlAxb4dL7ftBvKpL1Ndx4opKToKHPbL848mr993vVWcJlC7fF4OyB53Qf8F\r\nDpT38A2KGNrMmPs6AW2fzLBAfDwc2ueR3mWWa8i/FLHqE9lS5jBlVxT87I2aKgp2aF5Z7AzIAiOp\r\nd1qTX8sWf+YB43Kd1PM74L5VwhcS/SJtfg2fDEJtlRYtRVVUBEXS6RNmkjfj2NWi3AQl8WbV86MP\r\nzQZ0vUnmYJZRhncnLx8I9XYDuiRDdvnaymkOfJ64SxJz1wsVIbvKpVhJstQW0Ej7q3qgXlW+5r8E\r\n5ejg0aDz4jSOAQHPdLHbRnz3RmZsnJBY+NhGqD59orgJwj80kap6q5g53HEk5uo0XAryE/9K7Iv3\r\nGIyMPS6fZFDkoXclNFQcfhtSJzD1/6xqTFvl1TyzhYdC0XktHeMQcYMopY7mBL8n+KwkDqQf/FTY\r\n4FR3b6yOv9V00ZJvIDr8P5o8ulk8F+6watY17D0Vf/i/XPZY12eQCyPiQhsHPF9bTovJanyUKymE\r\nkC9vL3+T5gLHys6S9pEBwC2gC1ryxiGXSYdQC95x/yEPSxCeSXPj3DdpdaUtTIV8hGuacbIMbTAl\r\n23S5Vz+9E7ZLFGSClkrYYRCUY42ROsDT3mvzMzkx1wCpEbfHWzdnmJYZ09Q+AQsz2YRo1tN4DsGw\r\nUGrd4StZ/ueT4/2NYFn7Yst5RKQEsiJDMvEotK8sqxPt7d4fooI7xgCIAfUGVFPIOfBxq39eVJik\r\nR8YbUrW+KOruz7CNX3kWPrd4hyJeukCuQYMdcA/sIdO9sv2WS+RhJTaDII7BZ/Cs7DGFaR9xVoTf\r\n3h2MdWvRNEWi1cwYycRj1lGRKmTY1rfMq/5HhxQa0LI0NENEA5i98PJ5/LxcGkbt0QaVf6Drz0OE\r\nA8+lpsg7rAhT9+ZDkOu+9emFcSRpay+mJABxhaXd2pG52m+xItRf0Ty5g82XeUQHO1w/Hwq0RboM\r\nLGrZK4t8Oa0XFwsspbbpWj14/cpjvryx1w+tm++GMO8OQk8LJwdCDcpWGZBpTxeByi51s7lMlbua\r\n3IBYV4HMRXWgpiBitLIkhh0CvbVWupUHHI5XoRhm8I20n2DftkXh5fKBfXlXzuLnDpF4nobRelJJ\r\nTrdWHWJVva1gfFOpiNdklmQ/odmYzVSqG5mWtFwZxdyR5FgKmt5RyISuhBcFYzRp6DhSQtUhHqDv\r\naH6MO9PhPqdC6M/oWgqJGYaog1YPGbHkvvbQjwutaQOG7d6kYPTkXmW0WCgDK4wjb+Olg6LuFlct\r\nff03l4a5ecoqhFHDrRcOTEXv7N8y1+ofM9UOkGZed+Vm6IEPAftt8USrkdmEGcjo9RG+wCWaL89x\r\nSVKVRTP2Lra9JbZ31mQC8GiyMDPJXl6cVQmb89Cd";
		System.out.println(ThreeDESUtil.des3AppDecodeCBC(str));*/
		
		List<String> list = new ArrayList<String>();
		list.add("yilin_logistics_app/app/account/login.");
		System.out.println(list.contains("yilin_logistics_app/app/account/login.action"));
	}
}
