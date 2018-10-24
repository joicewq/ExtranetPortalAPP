package cn.gov.tjp.app.ga.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpUtil {
	private static int CONNECTION_REQUEST_TIMEOUT = 5 * 1000;// 线程池等待时间
	private static int CONNECT_TIMEOUT = 60 * 1000;// 连接时间
	private static int SOCKET_TIMEOUT = 1000 * 1000;// 等待服务器响应时间

	private static String ENCODING_UTF_8 = "UTF-8";// UTF-8
	private static String CONTENT_TYPE_JSON = "application/json";// json

	/**
	 * 连接配置文件
	 */
	private static RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();

	/**
	 * "get"请求
	 * 
	 * @author : Nansen
	 * @param url
	 *            :地址
	 * @return
	 * @throws Exception
	 * @date : 2017年10月17日上午11:47:20
	 */
	public static String get(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);

		// 获取服务器返回数据
		return dealResponse(buildClient().execute(httpGet));
	}

	/**
	 * "post"请求
	 * 
	 * @author : Nansen
	 * @param url
	 *            :地址
	 * @param params
	 *            :参数
	 * @return
	 * @throws Exception
	 * @date : 2017年10月17日上午11:47:42
	 */
	public static String post(String url, Map<String, Object> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(JSON.toJSONString(params), ENCODING_UTF_8);

		// 配置参数格式
		stringEntity.setContentEncoding(ENCODING_UTF_8);
		stringEntity.setContentType(CONTENT_TYPE_JSON);

		// 添加参数
		httpPost.setEntity(stringEntity);

		// 获取服务器返回数据
		return dealResponse(buildClient().execute(httpPost));
	}

	/**
	 * "post"的表单请求
	 * 
	 * @author : Nansen
	 * @param url
	 *            :地址
	 * @param params
	 *            :参数
	 * @return
	 * @throws Exception
	 * @date : 2017年10月17日上午11:48:22
	 */
	public static String postFormData(String url, Map<String, Object> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();

		// 把Map中的参数组装到list中
		for (Entry<String, Object> entry : params.entrySet()) {
			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
		}

		// 配置参数
		httpPost.setEntity(new UrlEncodedFormEntity(paramList, ENCODING_UTF_8));
		return dealResponse(buildClient().execute(httpPost));
	}

	/**
	 * 获取实体请求方法
	 * 
	 * @author : Nansen
	 * @param response
	 * @param url
	 * @param params
	 * @throws Exception
	 * @date : 2018年1月15日下午3:18:06
	 */
	public static CloseableHttpResponse getEntry(HttpServletResponse response, String url, Map<String, Object> params) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		return buildClient().execute(httpGet);
	}

	/**
	 * 创建一个"CloseableHttpClient"
	 * 
	 * @author : Nansen
	 * @return
	 * @date : 2017年10月17日上午11:48:51
	 */
	private static CloseableHttpClient buildClient() {
		return HttpClientBuilder.create().setDefaultRequestConfig(REQUEST_CONFIG).build();
	}

	/**
	 * 集中处理"response"
	 * 
	 * @author : Nansen
	 * @param response
	 * @return
	 * @throws Exception
	 * @date : 2017年10月17日上午11:49:25
	 */
	private static String dealResponse(CloseableHttpResponse response) throws Exception {
		String result = null;
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} else {
			result = String.valueOf(statusCode);
		}
		return result;
	}
}
