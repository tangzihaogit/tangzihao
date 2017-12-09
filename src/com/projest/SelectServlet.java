package com.projest;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class SelectServlet extends HttpServlet{

	/**
	 *作者：唐子豪
	 *2017年12月1日下午7:36:35
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
		String jsonObject = SelectServlet.httpGet("http://localhost:8080/HttpProject/select");
		System.out.println(jsonObject);
		resp.getWriter().write(jsonObject);	
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
	
	
	public static Logger logger = Logger.getLogger(HttpRequestUtils.class);// 日志记录
	//private static String url = "http://192.168.1.101:8080/ServletA/testHttp";
	public static String httpGet(String url){
		// get请求返回结果
		String jsonResult = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();//客户端
			// 发送get请求
			HttpGet request = new HttpGet(url);//请求的路径(url)
			HttpResponse response = client.execute(request);//客户端的执行URL请求
			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println(response.getStatusLine().getStatusCode());
				/** 读取服务器返回过来的json字符串数组 **/
				String strResult = EntityUtils.toString(response.getEntity());
				System.out.println(strResult);
				/** 把json字符串转换成json对象 **/
				//jsonResult = JSONObject.fromObject(strResult);
				url = URLDecoder.decode(url, "UTF-8");
				return strResult;
			} else {
				logger.error("get请求提交失败:" + url);
			}
		} catch (IOException e) {
			logger.error("get请求提交失败:" + url, e);
		}
		return jsonResult;
	}
}
