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
	 *���ߣ����Ӻ�
	 *2017��12��1������7:36:35
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
	
	
	public static Logger logger = Logger.getLogger(HttpRequestUtils.class);// ��־��¼
	//private static String url = "http://192.168.1.101:8080/ServletA/testHttp";
	public static String httpGet(String url){
		// get���󷵻ؽ��
		String jsonResult = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();//�ͻ���
			// ����get����
			HttpGet request = new HttpGet(url);//�����·��(url)
			HttpResponse response = client.execute(request);//�ͻ��˵�ִ��URL����
			/** �����ͳɹ������õ���Ӧ **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println(response.getStatusLine().getStatusCode());
				/** ��ȡ���������ع�����json�ַ������� **/
				String strResult = EntityUtils.toString(response.getEntity());
				System.out.println(strResult);
				/** ��json�ַ���ת����json���� **/
				//jsonResult = JSONObject.fromObject(strResult);
				url = URLDecoder.decode(url, "UTF-8");
				return strResult;
			} else {
				logger.error("get�����ύʧ��:" + url);
			}
		} catch (IOException e) {
			logger.error("get�����ύʧ��:" + url, e);
		}
		return jsonResult;
	}
}
