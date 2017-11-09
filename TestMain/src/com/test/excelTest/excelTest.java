package com.test.excelTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;



/**
 * 
* Title: excelTest
* Description: excel表格读取
* 注意:引用poi 架包版本要一致
* 如:
* poi-3.13.jar
* poi-ooxml-3.13.jar
* poi-ooxml-schemas-3.13.jar
* poi-scratchpad-3.13.jar
* 这些架包版本随意
* stax-api.jar
* xmlbeans.jar
* dom4j.jar
* Version:1.0.0  
* @author pancm
* @date 2017年11月8日
 */
public class excelTest {
	
	private static final String path="D:\\file\\test.xlsx"; 
	private static final String path1="D:\\file\\test1.xlsx"; 
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		readExcel(path);
		writeExcel(path1);
	}
    
	/**
	 * 读取Excel表格内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void readExcel(String str) throws FileNotFoundException, IOException{
		File file=new File(str);
		// HSSFWorkbook 2003的excel .xls,XSSFWorkbook导入2007的excel   .xlsx
//		HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(file)));
		XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
		 Sheet sheet=workbook.getSheetAt(0);//读取第一个 sheet
		 List list= new ArrayList<>();
		 Row row=null;
		 int count=sheet.getPhysicalNumberOfRows();
		//逐行处理 excel 数据
		for (int i = 1; i <count; i++) {
			 JSONObject json=new JSONObject();
			  row=sheet.getRow(i);
			 Cell cell0 = row.getCell(0);
			 //设置取值为String
			 //整数数据要转，否则会变成浮点数
			cell0.setCellType(Cell.CELL_TYPE_STRING);
			Cell cell1 = row.getCell(1); 
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			json.put("Id",cell0.toString()); //编号
			json.put("Name",cell1.toString()); //名称
			list.add(json);
			System.out.println("json:"+json);
		}
		workbook.close();
		System.out.println("list:"+list);
	}
	
	/**
	 * 写入Excel表格内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "rawtypes", "unchecked" })
	private static void writeExcel(String str) throws FileNotFoundException, IOException{
		File file=new File(str);
		// HSSFWorkbook 2003的excel .xls,XSSFWorkbook导入2007的excel   .xlsx
//		HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(new File(file)));
		XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
		List resultList =new ArrayList<>();
		
		Sheet sheet1 = workbook.createSheet();//创建 sheet 对象
		Row row = sheet1.createRow(0);//第一行，标题
		row.createCell(0).setCellValue("A");
		row.createCell(1).setCellValue("B");
		row.createCell(2).setCellValue("C");
		row.createCell(3).setCellValue("D");
		row.createCell(4).setCellValue("E");
		//拼接数据
		for(int i=1;i<=10;i++){
			JSONObject json1=new JSONObject();
			json1.put("A", i);
			json1.put("B", i*2);
			json1.put("C", i*3);
			json1.put("D", i*4);
			json1.put("E", i*5);
			resultList.add(json1);
		}
		System.out.println("resultList:"+resultList);
		Row row1;
		for (int i = 1, len = resultList.size(); i <=len; i++) {//循环创建数据行
			//因为第一行已经设置了，所以从第二行开始
			row1 = sheet1.createRow(i);
			JSONObject json=(JSONObject) resultList.get(i-1);
			row1.createCell(0).setCellValue(json.getString("A"));
			row1.createCell(1).setCellValue(json.getString("B"));
			row1.createCell(2).setCellValue(json.getString("C"));
			row1.createCell(3).setCellValue(json.getString("D"));
			row1.createCell(4).setCellValue(json.getString("E"));
		}
		FileOutputStream fos = new FileOutputStream(path1); 
		workbook.write(fos);//写文件
		fos.close();
		System.out.println("写入成功！");
	}
}
