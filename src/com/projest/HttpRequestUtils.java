package com.projest;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

/**
 * httpClient框架的http请求
 * 实例:https://www.cnblogs.com/QQParadise/articles/5020215.html
 * 注意:由服务端提供http地址,我们负责获取数据
 * 
 * @author Administrator
 */
public class HttpRequestUtils {
	public static Logger logger = Logger.getLogger(HttpRequestUtils.class);// 日志记录
	//private static String url = "http://192.168.1.101:8080/ServletA/testHttp";

	/**
	 * httpPost
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpPost(String url, JSONObject jsonParam) {
		return httpPost(url, jsonParam, false);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结??
	 * @return
	 */
	public static String httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
		// post请求返回结果

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String str = "";
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {

				try {
					/** 读取服务器返回过来的json字符串数组 **/
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}

				} catch (Exception e) {
					logger.error("post请求提交失败:" + url, e);
				}
			}
		} catch (IOException e) {
			logger.error("post请求提交失败:" + url, e);
		}
		return str;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static String httpGet(String url) {
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

	public static void main(String[] args) {
		/**
		 * get请求
		 */
		String jsonObject = HttpRequestUtils.httpGet("http://localhost:8080/HttpProject/select");
		System.out.println(jsonObject);
//		User user = (User) JSONObject.toBean(jsonObject, User.class);
//		System.out.println(user.getStatus());
//		Data data = user.getData();
//		System.out.println(data.toString());

		/**
		 * post请求
		 */
		//String jsonObject2 = HttpRequestUtils.httpPost(url, JSONObject.fromObject("{\"key\":123,\"v\":456}"));
		//System.out.println(jsonObject2);
	}
}