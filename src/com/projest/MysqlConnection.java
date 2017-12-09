package com.projest;

import java.sql.Connection;
import java.sql.DriverManager;


public class MysqlConnection {
	static Connection  conn = null;
	public static Connection getCon() {
		try {
			if(conn == null)
				{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://192.168.1.31:3306/tzh", "root", "gao");
			}else{
				return conn;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}