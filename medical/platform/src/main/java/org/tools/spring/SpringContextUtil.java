package org.tools.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName SpringContextUtil
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @date 2016年1月31日 上午10:51:14
 * @version 1.0
 * @description Spring Bean工具类
 */
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param name
	 * @return {@link Object}
	 * @throws BeansException
	 * @version 1.0
	 * @return
	 * @date 2016年1月31日 上午10:51:54
	 * @description 返回Bean实例对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, Class<T> t) throws BeansException {
		return (T) applicationContext.getBean(name);
	}
}
