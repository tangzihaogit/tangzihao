package com.projest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;




public class AddServlet extends HttpServlet{

	/**
	 *作者：唐子豪
	 *2017年12月1日下午7:35:50
	 *
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{
			String id=req.getParameter("id");
			String sql="select count(*) from test where id=? ";
			Connection conn = MysqlConnection.getCon();
			PreparedStatement ps=(PreparedStatement)conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.executeQuery();// 执行查询
			ResultSet rs = ps.getResultSet();
			rs.next();
			int count = rs.getInt(1);
			if(count == 0){
				resp.getWriter().write("0");
			}
			else{
				resp.getWriter().write("1");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id=req.getParameter("id");
		String name=req.getParameter("name");
		String sex=req.getParameter("sex");
		String age=req.getParameter("age");
		
		String jsonObject2 = AddServlet.sendPost("http://localhost:8080/HttpProject/add", "id="+id+"&name="+name+"&sex="+sex+"&age="+age);
		//System.out.println(jsonObject2);
		resp.getWriter().write(jsonObject2);
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public static Logger logger = Logger.getLogger(HttpRequestUtils.class);
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		} finally { // 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
}
