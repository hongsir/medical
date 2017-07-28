package org.utils.http;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年7月12日
 * @version 1.0
 * @description Get方式调用
 */
public class GetClient {
	private static final Logger logger = LoggerFactory.getLogger(GetClient.class);
	static ExecutorService pool = Executors.newCachedThreadPool();
	private static HttpClient httpClient = HttpClientBuilder.create().build();
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(10000).build();// 设置请求和传输超时时间

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param url
	 * @param headers
	 * @date 2017年7月12日
	 * @version 1.0
	 * @description Get异步调用
	 */
	public static void asynchronous(String url, Map<String, String> headers) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				HttpGet get = new HttpGet(url);
				/* 设置链接等待时间和超时时间 */
				get.setConfig(requestConfig);
				/* 设置Header */
				if (headers != null && headers.size() > 0) {
					for (Entry<String, String> header : headers.entrySet()) {
						get.setHeader(header.getKey(), header.getValue());
					}
				}
				try {
					long startTime = System.currentTimeMillis();
					HttpResponse response = httpClient.execute(get);
					String resposeText = EntityUtils.toString(response.getEntity());
					long times = System.currentTimeMillis() - startTime;
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						if (logger.isInfoEnabled()) {
							logger.info("GET:[{}] Success Taking:[{}] Header:[{}] Response:[{}]", url, times, headers, resposeText);
						}
					} else {
						if (logger.isWarnEnabled()) {
							logger.warn("GET:[{}] Fail Taking:[{}] Header:[{}] Response:[{}]", url, times, headers, resposeText);
						}
					}
				} catch (IOException e) {
					logger.error("GET:[{}] Error Taking:[{}] Header:[{}] Error Message:[{}]", url, headers, e);
				}
			}
		});
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param url
	 * @param headers
	 * @return {@link String}
	 * @date 2017年7月12日
	 * @version 1.0
	 * @description Get同步调用
	 */
	public static String synchronization(String url, Map<String, String> headers) {
		HttpGet get = new HttpGet(url);
		/* 设置链接等待时间和超时时间 */
		get.setConfig(requestConfig);
		/* 设置Header */
		if (headers != null && headers.size() > 0) {
			for (Entry<String, String> header : headers.entrySet()) {
				get.setHeader(header.getKey(), header.getValue());
			}
		}
		try {
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(get);
			String resposeText = EntityUtils.toString(response.getEntity());
			long times = System.currentTimeMillis() - startTime;
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (logger.isInfoEnabled()) {
					logger.info("GET:[{}] Success Taking:[{}] Header:[{}] Response:[{}]", url, times, headers, resposeText);
				}
				return resposeText;
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("GET:[{}] Fail Taking:[{}] Header:[{}] Response:[{}]", url, times, headers, resposeText);
				}
			}
		} catch (IOException e) {
			logger.error("GET:[{}] Error Taking:[{}] Header:[{}] Error Message:[{}]", url, headers, e);
		}
		return null;
	}
}
