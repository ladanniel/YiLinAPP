package com.memory.platform.global;

import java.io.*;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.memory.platform.exception.SystemException;

/**
* 创 建 人： longqibo
* 日    期： 2016年5月27日 下午5:21:25 
* 修 改 人： 
* 日   期： 
* 描   述： Excel文件导出
* 版 本 号：  V1.0
 */
public class ExportExcelUtil {

	/**
	* 功能描述： 创建Excel文件
	* 输入参数:  @param list  数据源
	* 输入参数:  @param keys  列名key
	* 输入参数:  @param columnNames   列名称
	* 输入参数:  @return
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月27日下午5:22:22
	* 修 改 人: 
	* 日    期: 
	* 返    回：Workbook
	 */
	public static Workbook createWorkBook(List<Map<String, Object>> list,
			String[] keys, String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			sheet.setColumnWidth((short) i, (short) (35.7 * 150));
		}

		// 创建第一行
		Row row = sheet.createRow((short) 0);

		// 创建两种单元格格式
		CellStyle cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 12);
		f.setColor(IndexedColors.BLACK.getIndex());
		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 12);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short i = 1; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow((short) i);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list
						.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}

	/**
	* 功能描述： 生成/创建Excel文件
	* 输入参数:  @param request
	* 输入参数:  @param response
	* 输入参数:  @param fileName
	* 输入参数:  @param projectlist
	* 输入参数:  @param listmap
	* 输入参数:  @param columnNames
	* 输入参数:  @param keys
	* 异    常： 
	* 创 建 人: longqibo
	* 日    期: 2016年5月27日下午5:23:40
	* 修 改 人: 
	* 日    期: 
	* 返    回：void
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void createExcel(HttpServletRequest request,
			HttpServletResponse response, String fileName, List projectlist,
			List<Map<String, Object>> listmap, String columnNames[], String keys[]) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			createWorkBook(listmap, keys, columnNames).write(os);
		} catch (IOException e) {
			throw new SystemException("创建Excel工作薄异常。" + e.getMessage());
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();

			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw new SystemException("Excel导出异常。" + e.getMessage());
		} finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				throw new SystemException("Excel导出异常。" + e.getMessage());
			}
		}
	}
}
