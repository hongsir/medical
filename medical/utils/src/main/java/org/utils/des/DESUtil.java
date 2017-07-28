package org.utils.des;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.iharder.Base64;

/**
 * @ClassName DESUtil
 * @author <font color="red"><b>LiuGangQiang</b></font>
 * @date 2016年2月17日 下午4:05:13
 * @version 1.0
 * @description 配置文件解密
 */
public class DESUtil {
	Logger log = LoggerFactory.getLogger(DESUtil.class);
	private final static String DES = "DES";

	public static void main(String[] args) throws Exception {
		//String data = "develop";
		String data = "jdbc:mysql://company.99kst.com:3306/develop?useUnicode=true&characterEncoding=utf8&mysqlEncoding=utf8";
		String key = "scyxtyzg";
		System.out.println(encrypt(data, key));
		// System.err.println(decrypt(data, key));
	}

	/**
	 * @author <font color="green"><b>LiuGangQiang</b></font>
	 * @param data
	 * @param key
	 * @return {@link String}
	 * @throws Exception
	 * @date 2016年2月17日 下午4:05:53
	 * @version 1.0
	 * @description 加密
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes("UTF-8"));
		String strs = new String(Base64.encodeBytes(bt));
		return strs;
	}

	/**
	 * @author <font color="green"><b>LiuGangQiang</b></font>
	 * @param data
	 * @param key
	 * @return {@link String}
	 * @throws Exception
	 * @date 2016年2月17日 下午4:06:40
	 * @version 1.0
	 * @description 解密
	 */
	public static String decrypt(String data, String key) throws Exception {
		if (data == null)
			return null;
		byte[] buf = Base64.decode(data);
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt, "UTF-8");
	}

	/**
	 * @author <font color="green"><b>LiuGangQiang</b></font>
	 * @param data
	 * @param key
	 * @return {@link Byte[]}
	 * @throws Exception
	 * @date 2016年2月17日 下午4:07:14
	 * @version 1.0
	 * @description 加密
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * <font color="red">根据键值进行解密</font>
	 * 
	 * @Title decrypt
	 * @param data
	 * @param key
	 * @return {@link Byte}
	 * @throws Exception
	 * @since 1.0
	 * @WorkFlow
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
