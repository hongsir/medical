package org.core.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年11月23日
 * @Version 1.2
 * @Description 国际化资源文件,默认采用本地语言加载对应提示文件内容
 */
public class SystemMessage {

	private static String filePath = "org.core.message.system_message";

	/**
	 * <font color="red">无占位符的字符资源</font>
	 * 
	 * @Title bundle
	 * @param key
	 * @return {@linkplain String}
	 * @since 1.0
	 */

	public static String bundle(String key) {
		return ResourceBundle.getBundle(filePath, Locale.getDefault()).getString(key);
	}

	/**
	 * <font color="red">有占位符的字符资源</font>
	 * 
	 * @Title bundle
	 * @param key
	 * @param arguments
	 * @return {@linkplain String}
	 * @since 1.0
	 */
	public static String bundle(String key, Object... arguments) {
		return MessageFormat.format(ResourceBundle.getBundle(filePath, Locale.getDefault()).getString(key), arguments);
	}
}
