package org.system.intercept;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.core.result.ResultCode;
import org.core.result.ResultMap;
import org.core.servlet.ParameterRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.system.entity.platform.permission.BaseModule;
import org.system.global.BaseGlobal;
import org.system.message.Prompt;
import org.system.service.platform.iface.permission.IBaseModuleService;
import org.system.service.platform.impl.permission.BaseModuleService;
import org.tools.spring.SpringContextUtil;
import org.utils.rsa.RSAUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/** 
* @author <font color="green"><b>Liu.Gang.Qiang</b></font> 
* @date 2017年7月17日 
* @version 1.0
* @description 统一鉴权及全局异常拦截
*/
public class BaseSecurityFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(BaseSecurityFilter.class);

	/**
	 * 客户端身份唯一标识
	 */
	private final String APP_KEY = "appKey";
	/**
	 * 客户端令牌
	 */
	private final String TOKEN = "token";

	private IBaseModuleService baseModuleService;

	@Override
	protected void initFilterBean() throws ServletException {
		baseModuleService = SpringContextUtil.getBean("baseModuleService", BaseModuleService.class);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			/* 设置响应头为Json */
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");

			ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
			/* 获取客户端唯一标识 */
			String appKey = requestWrapper.getHeader(APP_KEY);
			/* 获取令牌 */
			String token = requestWrapper.getHeader(TOKEN);

			/* 判断标识是否为空 为空则拒绝访问 */
			if (StringUtils.isEmpty(appKey)) {
				if (logger.isInfoEnabled()) {
					logger.info("[{}] request : [{} {}] be refused reason : app key is empty", getIpAddress(request), request.getMethod(), request.getRequestURI());
				}
				/* 客户端标识为空判定为不合法请求直接拒绝不再执行任何请求 */
				response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED, Prompt.bundle("app.key.is.empty"))));
			} else {
				/* 标识不为空则根据该标识获取该客户端的配置信息 */
				BaseModule baseModule = new BaseModule();
				baseModule.setAppKey(appKey);
				Map<String, Object> baseModuleMap = baseModuleService.getOne(baseModule);
				if (MapUtils.isEmpty(baseModuleMap)) {
					/* 客户端标识在系统内无信息则提示该客户端未注册 */
					if (logger.isInfoEnabled()) {
						logger.info("[{}] request : [{} {}] be refused reason : app key [{}] unregistered", getIpAddress(request), request.getMethod(), request.getRequestURI(), appKey);
					}
					response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED, Prompt.bundle("app.key.not.exists"))));
				} else {
					/* 信息存在则校验令牌是否匹配 */
					if (StringUtils.isEmpty(token)) {
						/* 令牌为空直接提示 */
						if (logger.isInfoEnabled()) {
							logger.info("[{}] request : [{} {}] be refused reason : app key [{}] token is empty", getIpAddress(request), request.getMethod(), request.getRequestURI(), appKey);
						}
						response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED, Prompt.bundle("app.token.is.empty"))));
					} else {
						try {
							/* 令牌不为空加载私钥进行解密 */
							String sourceStr = RSAUtils.RSADecode(RSAUtils.getPrivateKey(baseModuleMap.get("privateKey").toString()), token);
							/* 转码为Map */
							Map<String, Object> sourceMap = JSONObject.parseObject(sourceStr);
							/* 判断是否超时 */
							long createTime = Long.parseLong(sourceMap.get("createDate").toString());
							int acquisitive = Integer.parseInt(sourceMap.get("acquisitive").toString());
							if (System.currentTimeMillis() < (createTime + acquisitive * 1000 * 60)) {
								/* 判断用密码密码是否正确 */
								if (sourceMap.get("appSecret").equals(baseModuleMap.get("appSecret"))) {
									/* 设置全局公用参数 模块ID */
									requestWrapper.addParameter(BaseGlobal.MOUDLE_ID, baseModuleMap.get("id"));
									filterChain.doFilter(requestWrapper, response);
								} else {
									if (logger.isInfoEnabled()) {
										logger.info("[{}] request : [{} {}] be refused reason : app key [{}] token [{}] app secret error", getIpAddress(request), request.getMethod(), request.getRequestURI(), appKey, token);
									}
									response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED, Prompt.bundle("app.token.app.secret.error"))));
								}
							} else {
								/* 时间过期 */
								if (logger.isInfoEnabled()) {
									logger.info("[{}] request : [{} {}] be refused reason : app key [{}] token [{}] acquisitive expired", getIpAddress(request), request.getMethod(), request.getRequestURI(), appKey, token);
								}
								response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED, Prompt.bundle("app.token.acquisitive.expired"))));
							}
						} catch (Exception e) {
							e.printStackTrace();
							if (logger.isWarnEnabled()) {
								logger.warn("[{}] request : [{} {}] be refused reason : app key [{}] token [{}] error", getIpAddress(request), request.getMethod(), request.getRequestURI(), appKey, token);
							}
							response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.SYSTEM_ERROR, Prompt.bundle("app.token.encryption.error"))));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("[{}] request : [{} {}] be refused  Error Message:[{}]", getIpAddress(request), request.getMethod(), request.getRequestURI(), e.getMessage());
			response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.SYSTEM_ERROR)));
		}
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
