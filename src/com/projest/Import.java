package com.projest;

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

import http.Add;
import http.JsonUtils;

/*import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/

public class Import extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			// 关闭结果集
			rs.close();
			// 关闭发送SQL对象
			ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

