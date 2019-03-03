 
package com.memory.platform.global;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;

/** 
* 创 建 人： yangjiaqiao
* 日    期： 2016年7月7日 下午4:14:56 
* 修 改 人： 
* 日   期： 
* 描   述： 
* 版 本 号：  V1.0   
*/
public class OSSClientUtil {
	  static Log log = LogFactory.getLog(OSSClientUtil.class);
	  // endpoint以杭州为例，其它region请按实际情况填写
	  private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com/";
	  // accessKey
	  private static String accessKeyId = "LTAIdkoSD77S2lJM";
	  private static String accessKeySecret = "tyz0wr9DpP7nW7ZuVuQ9NStQ81RL4X";
	  //空间
	  private static String bucketName = "qibogu";
	  //文件存储目录
	  private String filedir = "data2/";
	 
	  private static OSSClient ossClient;
	 
	  public OSSClientUtil() {
	    ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	  }
	 
	  /**
	   * 初始化
	   */
	  public void init() {
	    ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	  }
	 
	  /**
	   * 销毁
	   */
	  public void destory() {
	    ossClient.shutdown();
	  }
	 
	  /**
	   * 上传到OSS服务器  如果同名文件会覆盖服务器上的
	   *
	   * @param instream 文件流
	   * @param fileName 文件名称 包括后缀名
	   * @return 出错返回"" ,唯一MD5数字签名
	   */
	  public static String uploadFile2OSS(InputStream instream, String fileName,String filedir) {
	    try {
	      if(ossClient == null){
	    	  ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	      }
	      //创建上传Object的Metadata 
	      ObjectMetadata objectMetadata = new ObjectMetadata();
	      objectMetadata.setContentLength(instream.available());
	      objectMetadata.setCacheControl("no-cache");
	      objectMetadata.setHeader("Pragma", "no-cache");
	      objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
	      objectMetadata.setContentDisposition("inline;filename=" + fileName);
	      
	      //上传文件 
	      ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
	    } catch (IOException e) {
	      log.error(e.getMessage(), e);
	    } finally {
	      try {
	        instream.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	    Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
	    // 生成URL
	    URL url = ossClient.generatePresignedUrl(bucketName, filedir+fileName, expiration); 
	    return url.toString();
	  }
	 
	  /**
	   * 从OSS获取文件
	   *
	   * @param filename 文件名
	   * @return InputStream 调用方法把流关闭  文件不存在返回null
	   */
	  public InputStream downFileFromOSS(String filename) {
	    if(ossClient == null){
    	   ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
	    boolean fileExist = ossClient.doesObjectExist(bucketName, filedir + filename);
	    if (!fileExist)
	      return null;
	    OSSObject ossObj = ossClient.getObject(bucketName, filedir + filename);
	    return ossObj.getObjectContent();
	  }
	 
	  /**
	   * 根据文件名删除OSS服务器上的文件
	   *
	   * @param filename
	   * @return
	   */
	  public String deleteFile(String filename) {
	    if(ossClient == null){
    	   ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
	    boolean fileExist = ossClient.doesObjectExist(bucketName, filedir + filename);
	    if (fileExist) {
	      ossClient.deleteObject(bucketName, filedir + filename);
	      return "delok";
	    } else
	      return filename + " not found";
	  }
	 
	 
	  /**
	   * Description: 判断OSS服务文件上传时文件的contentType
	   *
	   * @param FilenameExtension 文件后缀
	   * @return String
	   */
	  public static String getcontentType(String FilenameExtension) {
	    if (FilenameExtension.equalsIgnoreCase("bmp")) {
	      return "image/bmp";
	    }
	    if (FilenameExtension.equalsIgnoreCase("gif")) {
	      return "image/gif";
	    }
	    if (FilenameExtension.equalsIgnoreCase("jpeg") ||
	        FilenameExtension.equalsIgnoreCase("jpg") ||
	        FilenameExtension.equalsIgnoreCase("png")) {
	      return "image/jpeg";
	    }
	    if (FilenameExtension.equalsIgnoreCase("html")) {
	      return "text/html";
	    }
	    if (FilenameExtension.equalsIgnoreCase("txt")) {
	      return "text/plain";
	    }
	    if (FilenameExtension.equalsIgnoreCase("vsd")) {
	      return "application/vnd.visio";
	    }
	    if (FilenameExtension.equalsIgnoreCase("pptx") ||
	        FilenameExtension.equalsIgnoreCase("ppt")) {
	      return "application/vnd.ms-powerpoint";
	    }
	    if (FilenameExtension.equalsIgnoreCase("docx") ||
	        FilenameExtension.equalsIgnoreCase("doc")) {
	      return "application/msword";
	    }
	    if (FilenameExtension.equalsIgnoreCase("xml")) {
	      return "text/xml";
	    }
	    return "image/jpeg";
	  }
	 
	  /**
	   * 获得url链接
	   *
	   * @param key
	   * @return
	   */
	  public static URL getUrl(String key) {
	    if(ossClient == null){
	    	ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
	    // 设置URL过期时间为1小时
	    Date expiration = new Date(new Date().getTime() + 3600 * 1000);
	    // 生成URL
	    URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
	    return url;
	  }
}
