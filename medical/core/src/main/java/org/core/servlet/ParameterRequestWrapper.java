package org.core.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年7月11日
 * @version 1.0
 * @description 自定义请求类
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

	private final Map<String, String[]> params = new HashMap<String, String[]>();

	public ParameterRequestWrapper(HttpServletRequest request) {
		super(request);
		/* 继承原请求的参数 */
		this.params.putAll(request.getParameterMap());
	}

	/* 重载一个构造方法 */
	public ParameterRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
		this(request);
		/* 批量添加参数 */
		addAllParameters(extendParams);
	}

	@Override
	public String getParameter(String name) {
		/* 重写getParameter，代表参数从当前类中的map获取 */
		String[] values = params.get(name);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}

	public String[] getParameterValues(String name) {
		return params.get(name);
	}

	public void addAllParameters(Map<String, Object> otherParams) {
		for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
			addParameter(entry.getKey(), entry.getValue());
		}
	}

	public void addParameter(String name, Object value) {
		if (value != null) {
			if (value instanceof String[]) {
				params.put(name, (String[]) value);
			} else if (value instanceof String) {
				params.put(name, new String[] { (String) value });
			} else {
				params.put(name, new String[] { String.valueOf(value) });
			}
		}
	}
}