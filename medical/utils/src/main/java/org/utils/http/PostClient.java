package org.utils.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年7月12日
 * @version 1.0
 * @description Post方式调用
 */
public class PostClient {
	private static final Logger logger = LoggerFactory.getLogger(PostClient.class);
	static ExecutorService pool = Executors.newCachedThreadPool();
	private static HttpClient httpClient = HttpClientBuilder.create().build();
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(10000).build();// 设置请求和传输超时时间

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param url
	 * @param params
	 * @param headers
	 * @date 2017年7月12日
	 * @version 1.0
	 * @description Post异步调用 表单
	 */
	public static void asynchronous(String url, Map<String, Object> params, Map<String, String> headers) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				HttpPost post = new HttpPost(url);
				/* 设置链接等待时间和超时时间 */
				post.setConfig(requestConfig);
				/* 设置响应头 */
				if (headers != null && headers.size() > 0) {
					for (Entry<String, String> header : headers.entrySet()) {
						post.setHeader(header.getKey(), header.getValue());
					}
				}
				/* 设置需要提交的参数 非JSON格式提交 */
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				if (params != null && params.size() > 0) {
					for (Entry<String, Object> entry : params.entrySet()) {
						Object objValue = entry.getValue();
						list.add(new BasicNameValuePair(entry.getKey(), objValue == null ? "" : objValue.toString()));
					}
				}
				try {
					post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
					long startTime = System.currentTimeMillis();
					HttpResponse response = httpClient.execute(post);
					String resposeText = EntityUtils.toString(response.getEntity());
					long times = System.currentTimeMillis() - startTime;
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						if (logger.isInfoEnabled()) {
							logger.info("POST:[{}] Success Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
						}
					} else {
						if (logger.isWarnEnabled()) {
							logger.warn("POST:[{}] Fail Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
						}
					}
				} catch (IOException e) {
					logger.error("POST:[{}] Error Taking:[{}] Header:[{}] Params:[{}] Error Message:[{}]", url, headers, params, e);
				}
			}
		});
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param url
	 * @param params
	 * @param headers
	 * @return {@link String}
	 * @date 2017年7月12日
	 * @version 1.0
	 * @description Post同步调用 表单
	 */
	public static String synchronization(String url, Map<String, Object> params, Map<String, String> headers) {
		HttpPost post = new HttpPost(url);
		/* 设置链接等待时间和超时时间 */
		post.setConfig(requestConfig);
		/* 设置响应头 */
		if (headers != null && headers.size() > 0) {
			for (Entry<String, String> header : headers.entrySet()) {
				post.setHeader(header.getKey(), header.getValue());
			}
		}
		/* 设置需要提交的参数 非JSON格式提交 */
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				Object objValue = entry.getValue();
				list.add(new BasicNameValuePair(entry.getKey(), objValue == null ? "" : objValue.toString()));
			}
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			String resposeText = EntityUtils.toString(response.getEntity());
			long times = System.currentTimeMillis() - startTime;
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (logger.isInfoEnabled()) {
					logger.info("POST:[{}] Success Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
				}
				return resposeText;
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("POST:[{}] Fail Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
				}
			}
		} catch (IOException e) {
			logger.error("POST:[{}] Error Taking:[{}] Header:[{}] Params:[{}] Error Message:[{}]", url, headers, params, e);
		}
		return null;
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param url
	 * @param params
	 * @param headers
	 * @date 2017年7月12日
	 * @version 1.0
	 * @description Post异步调用 JSON
	 */
	public static void asynchronousForJSON(String url, Map<String, Object> params, Map<String, String> headers) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				HttpPost post = new HttpPost(url);
				/* 设置链接等待时间和超时时间 */
				post.setConfig(requestConfig);
				/* 设置响应头 */
				if (headers != null && headers.size() > 0) {
					for (Entry<String, String> header : headers.entrySet()) {
						post.setHeader(header.getKey(), header.getValue());
					}
				}
				/* 设置需要提交的参数 JSON格式提交 */
				JSONObject jsonParam = new JSONObject();
				if (params != null && params.size() > 0) {
					for (Entry<String, Object> entry : params.entrySet()) {
						jsonParam.put(entry.getKey(), entry.getValue());
					}
				}
				try {
					StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
					entity.setContentEncoding("UTF-8");
					entity.setContentType("application/json");
					post.setEntity(entity);
					long startTime = System.currentTimeMillis();
					HttpResponse response = httpClient.execute(post);
					long times = System.currentTimeMillis() - startTime;
					String resposeText = EntityUtils.toString(response.getEntity());
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						if (logger.isInfoEnabled()) {
							logger.info("POST:[{}] Success Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
						}
					} else {
						if (logger.isWarnEnabled()) {
							logger.warn("POST:[{}] Fail Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
						}
					}
				} catch (IOException e) {
					logger.error("POST:[{}] Error Taking:[{}] Header:[{}] Params:[{}] Error Message:[{}]", url, headers, params, e);
				}
			}
		});
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param url
	 * @param params
	 * @param headers
	 * @return {@link String}
	 * @date 2017年7月12日
	 * @version 1.0
	 * @description Post同步调用 JSON
	 */
	public static String synchronizationForJSON(String url, Map<String, Object> params, Map<String, String> headers) {
		HttpPost post = new HttpPost(url);
		/* 设置链接等待时间和超时时间 */
		post.setConfig(requestConfig);
		/* 设置响应头 */
		if (headers != null && headers.size() > 0) {
			for (Entry<String, String> header : headers.entrySet()) {
				post.setHeader(header.getKey(), header.getValue());
			}
		}
		/* 设置需要提交的参数 JSON格式提交 */
		JSONObject jsonParam = new JSONObject();
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				jsonParam.put(entry.getKey(), entry.getValue());
			}
		}
		try {
			StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(post);
			long times = System.currentTimeMillis() - startTime;
			String resposeText = EntityUtils.toString(response.getEntity());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (logger.isInfoEnabled()) {
					logger.info("POST:[{}] Success Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
				}
				return resposeText;
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("POST:[{}] Fail Taking:[{}] Header:[{}] Params:[{}] Response:[{}]", url, times, headers, params, resposeText);
				}
			}
		} catch (IOException e) {
			logger.error("POST:[{}] Error Taking:[{}] Header:[{}] Params:[{}] Error Message:[{}]", url, headers, params, e);
		}
		return null;
	}
}
