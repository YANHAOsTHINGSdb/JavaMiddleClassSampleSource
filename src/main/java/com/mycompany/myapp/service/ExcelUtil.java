package com.mycompany.myapp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

//导出：
//// 查询数据,此处省略
//List<EasyPOIModel> list = new ArrayList<EasyPOIModel>();
//int count1 = 0 ;
//EasyPOIModel easyPOIModel11 = new EasyPOIModel(count1++,"信科","张三","男",20) ;
//EasyPOIModel easyPOIModel12 = new EasyPOIModel(count1++,"信科","李四","男",17) ;
//
//list.add(easyPOIModel11) ;
//list.add(easyPOIModel12) ;
//
//// 获取导出excel指定模版
//TemplateExportParams params = new TemplateExportParams();
//// 标题开始行
//params.setHeadingStartRow(0);
//// 标题行数
//params.setHeadingRows(2);
//// 设置sheetName，若不设置该参数，则使用得原本得sheet名称
//params.setSheetName("班级信息");
//
//params.setHeadingRows(2);
//params.setHeadingStartRow(2);
//params.setTempParams("t");
//Map<String,Object> data = new HashMap<String, Object>();
//data.put("list", list);
//
//book = ExcelUtil.getWorkbook(params, data, "1easypoiExample.xls");
//
////下载
//
//ExcelUtil.export(response, workbook, "easypoi-excel.xls");
//
//ExcelUtil：
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;

public class ExcelUtil {

	/**
	* 模板路径
	*/
	private static final String TEMPLATE_PATH = "template/";

	/**
	* 生成excel对象
	* @param params 模板导出参数设置
	* @param data 模板导出数据
	* @param templateName 模板名称
	* @return workBook对象
	* @throws Exception 异常抛出
	*/
	public static Workbook getWorkbook(TemplateExportParams params, Map<String, Object> data, String templateName)
			throws Exception {
		String templatePath = TEMPLATE_PATH + templateName;
		File file = getTemplateFile(templatePath);
		params.setTemplateUrl(file.getAbsolutePath());
		Workbook book = ExcelExportUtil.exportExcel(params, data);
		if (file.exists()) {
			file.delete();
		}
		return book;
	}

	/**
	* 导出excel对象
	* @param response httpResponse对象
	* @param workbook workBook对象
	* @param fileName 导出文件名
	* @throws Exception 异常抛出
	*/
	public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
		response.reset();
		response.setContentType("application/x-msdownload");
		fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
		ServletOutputStream outStream = null;
		try {
			outStream = response.getOutputStream();
			workbook.write(outStream);
		} finally {
			workbook.close();
			outStream.close();
		}
	}

	/**
	* 获取模板文件--获取到的文件为临时文件，用完后需要手动删除
	* <p>由于springboot打包成jar之后，不能以绝对路径的形式读取模板文件，故此处将模板文件以临时文件的形式写到磁盘中，用完请手动删除</p>
	* @param templatePath 模板路径
	* @return 模板文件
	* @throws Exception 异常抛出
	*/
	public static File getTemplateFile(String templatePath) throws Exception {
		File file = File.createTempFile("temp", null);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources(templatePath);
		if (resources.length == 1) {
			InputStream inputStream = resources[0].getInputStream();
			inputStreamToFile(inputStream, file);
		} else {
			System.out.println("请检查模板文件是否存在");
		}
		return file;
	}

	/**
	* InputStream 转file
	* @param ins 输入流
	* @param file 目标文件
	*/
	public static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}