package org.system.intercept;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.validation.Valid;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.core.result.ResultCode;
import org.core.result.ResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ValidInterceptor
 * @author <font color="red"><b>LiuGangQiang</b></font>
 * @date 2016年2月1日
 * @version 1.0
 * @description 用于拦截hibernate validator校验的公用拦截器
 */
public class ValidInterceptor implements MethodInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ValidInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		/* 得到方法 */
		Method method = invocation.getMethod();
		/* 得到参数 */
		Parameter[] paramters = method.getParameters();
		for (Parameter parameter : paramters) {
			/* 判断参数是否用使用Valid注解的 */
			if (parameter.isAnnotationPresent(Valid.class) || parameter.isAnnotationPresent(Validated.class)) {
				/* 得到方法参数 */
				Object[] obj = invocation.getArguments();
				for (Object object : obj) {
					/* 取得BindingResult类型参数 */
					if (object instanceof BindingResult) {
						BindingResult result = (BindingResult) object;
						/* 判断是否有错误 */
						if (result.hasErrors()) {
							/* 判断返回类型 这里只处理了返回json的类型 如果其他类型可自行处理 */
							if (method.isAnnotationPresent(ResponseBody.class)) {
								/* 返回json */
								HttpHeaders headers = new HttpHeaders();
								headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
								if (logger.isInfoEnabled()) {
									logger.info("valid message : {}", result.getFieldError().getDefaultMessage());
								}
								return ResultMap.convertMap(ResultCode.PARAMETER_ERROR, result.getFieldError().getDefaultMessage());
							}
						}
						break;
					}
				}
				break;
			}
		}
		return invocation.proceed();
	}
}
