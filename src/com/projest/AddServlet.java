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
	 *���ߣ����Ӻ�
	 *2017��12��1������7:35:50
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
			ps.executeQuery();// ִ�в�ѯ
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
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		} finally { // ʹ��finally�����ر��������������
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
