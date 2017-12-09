package com.projest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test2 extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			File filePath = new File("C:/Users/Administrator/Desktop/user.xlsx");
			showExcel(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ���е�excel������
	 */
	@SuppressWarnings("resource")
	public static void showExcel(File filePath) throws Exception {
		// ���� XSSFWorkbook����,filePath �����ļ�·��
		// ��ȡExcel 2003
		// HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));
		// ��ȡExcel 2007
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));//�õ�excel����
		
		XSSFSheet sheet = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// ��ȡÿ��Sheet��
			//workbook.getSheet(arg0)
			sheet = workbook.getSheetAt(i);//ѭ���õ�Sheet
			System.out.println(sheet.getSheetName());
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {// ��ȡÿ��
				XSSFRow row = sheet.getRow(j);//�õ�ÿһ��
				if (null != row) {// ���ղ�����rowѭ����ȡÿ����Ԫ��
					for (int k = 0; k < row.getLastCellNum(); k++) {// ��ȡÿ����Ԫ��
						if (null != row.getCell(k) || "".equals(row.getCell(k))) {
							System.out.print(row.getCell(k) + "\t");
						}
					}
				}
				System.out.println();
			}
		}
	}
}
