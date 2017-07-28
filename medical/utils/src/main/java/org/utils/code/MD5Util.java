package org.utils.code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName MD5Util
 * @author <font color="red"><b>LiuGangQiang</b></font>
 * @date 2016年1月31日 上午10:48:10
 * @version 1.0
 * @description MD5加密解密
 */
public class MD5Util {
	private static Logger log = LoggerFactory.getLogger(MD5Util.class);

	private static MD5Util instance = new MD5Util();

	private MD5Util() {
	}

	public static MD5Util getInstance() {
		if (null == instance) {
			instance = new MD5Util();
		}
		return instance;
	}

	/**
	 * @param inStr
	 * @return {@link String}
	 * @version 1.0
	 * @date 2016年1月31日 上午10:49:20
	 * @description 得到MD5加密字符串
	 */
	public String getMD5Code(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5Utils Error:{}", e);
			return "";
		}
		byte[] md5Bytes = md5.digest(inStr.getBytes());
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}

	/**
	 * @param inStr
	 * @return {@link String}
	 * @version 1.0
	 * @date 2016年1月31日 上午10:50:36
	 * @description 解密MD5字符串
	 */
	public String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		return new String(a);
	}

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param primary
	 * @return {@link String}
	 * @date 2016年11月4日
	 * @version 1.0
	 * @description 生成登录Token
	 */
	public String getSessionToken(Object primary) {
		return getMD5Code(UUID.randomUUID().toString()) + primary;
	}
}
