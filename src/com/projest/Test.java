package com.projest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 写入excl
 * @作者   LiuCong
 *
 * @时间 2017年5月2日上午10:39:06
 */
public class Test extends HttpServlet{

        /**
	 *作者：唐子豪
	 *2017年12月8日下午5:31:35
	 */
	private static final long serialVersionUID = 1L;

		/**
         * @param args
         */
        @Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        	 // 创建Excel的工作书册 Workbook,对应到一个excel文档
            @SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();

            // 创建Excel的工作sheet,对应到一个excel文档的tab
            HSSFSheet sheet = wb.createSheet("刘聪");

            // 设置excel每列宽度
            //sheet.setColumnWidth(0, 4000);
            //sheet.setColumnWidth(1, 3500);

            // 创建字体样式
            HSSFFont font = wb.createFont();
            font.setFontName("Verdana");
           // font.setBoldweight((short) 100);
            font.setFontHeight((short) 300);
            //font.setColor(HSSFColor.BLUE.index);

          
            HSSFCellStyle style = wb.createCellStyle();
            style.setFont(font);
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell0 = row.createCell(0);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);

            cell0.setCellValue("编号");
            cell1.setCellValue("姓名");
            cell2.setCellValue("性别");
            cell3.setCellValue("年龄");    
            
            try{
    			Connection conn = MysqlConnection.getCon();
    			String sql="select * from test";
    			PreparedStatement ps=(PreparedStatement) conn.prepareStatement(sql);
    			ps.executeQuery();
    			ResultSet rs = ps.getResultSet();// 获取查询结果
    			List<Add> list=new ArrayList<>();
    			while(rs.next()){
    				Add add=new Add();
    				add.setId(rs.getString(1));
    				add.setName(rs.getString(2));
    				add.setSex(rs.getString(3));
    				add.setAge(rs.getString(4));
    				list.add(add);
    			}
    			for (int i = 0; i < list.size(); i++) {
					Add add1=list.get(i);
					HSSFRow row1 = sheet.createRow(i+1);
					HSSFCell cella = row1.createCell(0);
					HSSFCell cellb = row1.createCell(1);
					HSSFCell cellc = row1.createCell(2);
					HSSFCell celld = row1.createCell(3);
					
					cella.setCellValue(add1.getId());
					cellb.setCellValue(add1.getName());
					cellc.setCellValue(add1.getSex());
					celld.setCellValue(add1.getAge());
				}
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    			
            FileOutputStream os = new FileOutputStream("C:/Users/Administrator/Desktop/user.xlsx");
            wb.write(os);
            os.close();
		}
}