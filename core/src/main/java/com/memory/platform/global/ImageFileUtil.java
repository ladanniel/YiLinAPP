package com.memory.platform.global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.memory.platform.core.AppUtil;

/**
 * 创 建 人： yangjiaqiao 日 期： 2016年5月4日 下午12:21:46 修 改 人： 日 期： 描 述： 版 本 号： V1.0
 */
public class ImageFileUtil {
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(ImageFileUtil.class);

	/**
	 * 图片最大限制 单位MB
	 */
	private static final int UPLOAD_IMAGE_MAX_SIZE_MB = 5;

	/**
	 * 图片最大限制 单位B
	 */
	private static final long UPLOAD_IMAGE_MAX_SIZE_B = UPLOAD_IMAGE_MAX_SIZE_MB * 1024 * 1024L;

	/**
	 * 允许的图片格式
	 */
	private static final String UPLOAD_IMAGE_EXTS = "jpg/jpeg/png/gif";

	/**
	 * 身份证存放文件夹
	 */
	private static final String UPLOAD_IDCARD_PATH = "idcard";

	/**
	 * 驾驶证存放文件夹
	 */
	private static final String UPLOAD_DRIVING_PATH = "driving";

	/**
	 * 营业执照存放文件夹
	 */
	private static final String UPLOAD_BUSINESS_PATH = "business";
	
	private static final String UPLOAD_BANK_PATH = "bank";
	/**
	 * 车辆图片存放文件夹
	 */
	private static final String UPLOAD_TRUCK_PATH="truck";

	/**
	 * 压缩图片到指定的路径下，图片采用springmvc上传而来
	 * 
	 * @param multiFile
	 *            将要压缩的图片，采用springmvc上传而来
	 * @param destPath
	 *            压缩后的图片保存路径
	 * @return 压缩后的图片完整路径，包括文件名
	 * @throws IOException
	 */
	public static String compressImageDefault(MultipartFile multiFile, String destPath, String type)
			throws IOException {
		// 判断图片格式
		String ext = FilenameUtils.getExtension(multiFile.getOriginalFilename());
		// 将文件后缀转成小写
		if (StringUtils.isNotBlank(ext)) {
			ext = ext.toLowerCase();
		}
		if (!UPLOAD_IMAGE_EXTS.contains(ext)) {
			log.warn(" file ext:" + ext + " not allowed in:" + UPLOAD_IMAGE_EXTS);
			// throw new ExtNotAllowedException(UPLOAD_IMAGE_EXT_LIMIT);
		}
		// 判断图片大小
		long size = multiFile.getSize();
		if (size > UPLOAD_IMAGE_MAX_SIZE_B) {
			log.warn(" file size:" + size + " outof sizeMaxLimit:" + UPLOAD_IMAGE_MAX_SIZE_B);
			// throw new SizeOutOfLimitException(UPLOAD_IMAGR_SIZE_LIMIT);
		}
		Map<String, Object> fileMap = creadFile(destPath, ext, type, false);
		File file = null;
		file = (File) fileMap.get("file");
		multiFile.transferTo(file);
		return fileMap.get("imgPath").toString();
	}

	/**
	 * 压缩图片到指定的路径下，图片采用springmvc上传而来
	 * 
	 * @param multiFile
	 *            将要压缩的图片，采用springmvc上传而来
	 * @param destPath
	 *            压缩后的图片保存路径
	 * @return 压缩后的图片完整路径，包括文件名
	 * @throws IOException
	 */
	public static String uploadTemporary(MultipartFile multiFile, String destPath, String type) throws IOException {
		// 判断图片格式
		String ext = FilenameUtils.getExtension(multiFile.getOriginalFilename());
		// 将文件后缀转成小写
		if (StringUtils.isNotBlank(ext)) {
			ext = ext.toLowerCase();
		}
		if (!UPLOAD_IMAGE_EXTS.contains(ext)) {
			log.warn(" file ext:" + ext + " not allowed in:" + UPLOAD_IMAGE_EXTS);
			// throw new ExtNotAllowedException(UPLOAD_IMAGE_EXT_LIMIT);
		}
		// 判断图片大小
		long size = multiFile.getSize();
		if (size > UPLOAD_IMAGE_MAX_SIZE_B) {
			log.warn(" file size:" + size + " outof sizeMaxLimit:" + UPLOAD_IMAGE_MAX_SIZE_B);
			// throw new SizeOutOfLimitException(UPLOAD_IMAGR_SIZE_LIMIT);
		}
		Map<String, Object> fileMap = creadFile(destPath, ext, type, true);
		File file = null;
		file = (File) fileMap.get("file");
		multiFile.transferTo(file);
		return fileMap.get("imgPath").toString().replace("//","/");
	}

	public static Map<String, Object> creadFile(String path, String ext, String type, boolean temporary) {
		Map<String, Object> fileMap = new HashMap<String, Object>();

		StringBuilder imgPath = new StringBuilder(temporary ? "//temporary" : "//uploading");
		StringBuilder pathImag = new StringBuilder(path + (temporary ? "//temporary" : "//uploading"));
		if (type.equals("IDCARD")) {
			imgPath.append("//").append(UPLOAD_IDCARD_PATH);
			pathImag.append("//").append(UPLOAD_IDCARD_PATH);
		} else if (type.equals("DRIVING")) {
			imgPath.append("//").append(UPLOAD_DRIVING_PATH);
			pathImag.append("//").append(UPLOAD_DRIVING_PATH);
		} else if(type.equals("BANK")){
			imgPath.append("//").append(UPLOAD_BANK_PATH);
			pathImag.append("//").append(UPLOAD_BANK_PATH);
		} else if(type.equals("TRUCK")){
			imgPath.append("//").append(UPLOAD_TRUCK_PATH);
			pathImag.append("//").append(UPLOAD_TRUCK_PATH);
		} else{
			imgPath.append("//").append(UPLOAD_BUSINESS_PATH);
			pathImag.append("//").append(UPLOAD_BUSINESS_PATH);
		}
		String today = DateFormatUtils.format(new Date(), "yyyyMMdd");
		File dest = new File(pathImag + "//" + today);
		if (!dest.exists()) {
			dest.mkdirs();
		}
		imgPath.append("//").append(today).append("//").append(AppUtil.getUUID()).append(".").append(ext);
		File uploadFile = new File(path + imgPath.toString());
		fileMap.put("imgPath", imgPath.toString());
		fileMap.put("file", uploadFile);
		return fileMap;
	}
	 

	

	/**
	 * 删除文件（夹）
	 * 
	 * @param file
	 *            文件（夹）
	 */
	public static void delete(File file) {
		if (!file.exists())
			return;
		if (file.isFile()) {
			file.delete();
		} else {
			for (File f : file.listFiles()) {
				delete(f);
			}
			file.delete();
		}
	}

	/**
	 * 复制文件（夹）到一个目标文件夹
	 * 
	 * @param resFile
	 *            源文件（夹）
	 * @param objFolderFile
	 *            目标文件夹
	 * @throws IOException
	 *             异常时抛出
	 */
	public static void copy(File resFile, File objFolderFile) throws IOException {
		if (!resFile.exists())
			return;
		if (!objFolderFile.exists())
			objFolderFile.mkdirs();
		if (resFile.isFile()) {
			File objFile = new File(objFolderFile.getPath() + File.separator + resFile.getName());
			// 复制文件到目标地
			InputStream ins = new FileInputStream(resFile);
			FileOutputStream outs = new FileOutputStream(objFile);
			byte[] buffer = new byte[1024 * 512];
			int length;
			while ((length = ins.read(buffer)) != -1) {
				outs.write(buffer, 0, length);
			}
			ins.close();
			outs.flush();
			outs.close();
		} else {
			String objFolder = objFolderFile.getPath() + File.separator + resFile.getName();
			File _objFolderFile = new File(objFolder);
			_objFolderFile.mkdirs();
			for (File sf : resFile.listFiles()) {
				copy(sf, new File(objFolder));
			}
		}
	}

	/**
	 * 将文件（夹）移动到令一个文件夹
	 * 
	 * @param resFile
	 *            源文件（夹）
	 * @param objFolderFile
	 *            目标文件夹
	 * @throws IOException
	 *             异常时抛出
	 */
	public static void move(File resFile, File objFolderFile) throws IOException {
			copy(resFile, objFolderFile);
			delete(resFile);
		
	}
	
 
	public static void main(String args[]) throws IOException {
		// delete(new File("C:/aaa"));
		// copy(new File("D:\\work\\mrpt"), new File("C:\\aaa"));
		move(new File("D:\\bbb\\1.jpg"), new File("D:\\ccc"));

	}

}
