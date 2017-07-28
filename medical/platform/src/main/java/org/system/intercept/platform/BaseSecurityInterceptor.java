package org.system.intercept.platform;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.core.annotation.permission.RequiresOperation;
import org.core.annotation.permission.RequiresPermission;
import org.core.annotation.permission.RequiresRoles;
import org.core.result.ResultCode;
import org.core.result.ResultMap;
import org.core.servlet.ParameterRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.system.entity.platform.permission.BaseModule;
import org.system.global.BaseGlobal;
import org.system.service.platform.iface.permission.IBaseModuleService;

import com.alibaba.fastjson.JSON;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年7月4日
 * @version 1.0
 * @description 安全拦截器，用于校验该请求携带的令牌是否能访问系统某方法
 */
public class BaseSecurityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(BaseSecurityInterceptor.class);

	@Resource
	private IBaseModuleService baseModuleService;

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param list
	 * @param auths
	 * @return {@link Boolean}
	 * @date 2017年7月4日
	 * @version 1.0
	 * @description 权限校验方法
	 */
	private boolean validate(List<Map<String, Object>> list, String[] auths) {
		for (String auth : auths) {
			for (Map<String, Object> map : list) {
				if (auth.equals(map.get("code"))) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {

			/* 设置响应头为Json */
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);

			/* 获取客户端ID */
			Integer moduleId = Integer.parseInt(requestWrapper.getParameter(BaseGlobal.MOUDLE_ID));
			BaseModule baseModule = new BaseModule();
			baseModule.setId(moduleId);
			/* 获取方法 */
			HandlerMethod method = (HandlerMethod) handler;
			/* 获取角色注解 */
			RequiresRoles roles = method.getMethodAnnotation(RequiresRoles.class);
			if (roles != null) {
				if (roles.ignore()) {
					return true;
				} else {
					/* 获取角色列表 */
					List<Map<String, Object>> roleList = baseModuleService.getRoleList(baseModule);
					if (validate(roleList, roles.value())) {
						return true;
					}
				}
			}

			/* 获取权限注解 */
			RequiresPermission permission = method.getMethodAnnotation(RequiresPermission.class);
			if (permission != null) {
				if (permission.ignore()) {
					return true;
				} else {
					/* 获取权限列表 */
					List<Map<String, Object>> permissionList = baseModuleService.getPermissionList(baseModule);
					if (validate(permissionList, roles.value())) {
						return true;
					}
				}
			}
			/* 获取操作注解 */
			RequiresOperation operation = method.getMethodAnnotation(RequiresOperation.class);
			if (operation != null) {
				if (operation.ignore()) {
					return true;
				} else {
					/* 获取操作列表 */
					List<Map<String, Object>> operationList = baseModuleService.getOperationList(baseModule);
					if (validate(operationList, roles.value())) {
						return true;
					}
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("Module:[id={}] Unauthorized", moduleId);
			}
			response.getWriter().write(JSON.toJSONString(ResultMap.convertMap(ResultCode.UNAUTHORIZED)));
			return false;

		}
		return true;
	}
}