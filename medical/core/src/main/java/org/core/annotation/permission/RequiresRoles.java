package org.core.annotation.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年10月28日
 * @Version 1.0
 * @Description 类似于shiro的角色注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresRoles {
	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link String[]}
	 * @date 2016年10月28日
	 * @version 1.0
	 * @description 角色描述值
	 */
	String[] value();

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link Boolean}
	 * @date 2016年11月29日
	 * @version 1.0
	 * @description 是否忽略校验 用于不需要校验权限的方法
	 */
	boolean ignore() default false;

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link Boolean}
	 * @date 2016年11月29日
	 * @version 1.0
	 * @description 是否需要登录验证 用于只需要登录即可访问的方法
	 */
	boolean authc() default false;
}
