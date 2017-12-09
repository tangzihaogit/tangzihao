package com.projest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateServlet extends HttpServlet{

	/**
	 *���ߣ����Ӻ�
	 *2017��12��1������7:37:00
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
		req.setCharacterEncoding("UTF-8");
		try {
			String id = req.getParameter("id");
			String name = req.getParameter("name");
			String sex = req.getParameter("sex");
			String age = req.getParameter("age");
			
			Connection conn = MysqlConnection.getCon();
			String sql = "update test set name = ?,sex=?,age=? where id = ?";// SQL���
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);// ����SQL�����ݿ�
			ps.setString(1,name);
			ps.setString(2,sex);
			ps.setString(3,age);
			ps.setString(4,id);
			//ps.setInt(3, Integer.valueOf(id));
			ps.executeUpdate();// ִ���޸�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
}
