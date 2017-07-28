package org.core.result;

import org.core.message.SystemMessage;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年10月28日
 * @Version 1.0
 * @Description 状态码枚举类
 */
public enum ResultCode {

	/**
	 * @Fields <font color="blue">SUCCESS</font>
	 * @description 操作成功
	 */
	SUCCESS(200, "result.message.success"),
	/**
	 * @Fields <font color="blue">FAIL</font>
	 * @description 操作失败
	 */
	FAIL(202, "result.message.fail"),
	/**
	 * @Fields <font color="blue">UNAUTHORIZED</font>
	 * @description 无权限
	 */
	UNAUTHORIZED(403, "result.message.unauthorized"),
	/**
	 * @Fields <font color="blue">PARAMETER_ERROR</font>
	 * @description 参数错误
	 */
	PARAMETER_ERROR(400, "result.message.parameter.error"),
	/**
	 * @Fields <font color="blue">UNSIGNATURE</font>
	 * @description 签名无效
	 */
	UNSIGNATURE(401, "result.message.unsignature"),
	/**
	 * @Fields <font color="blue">DATA_UNEXIST</font>
	 * @description 数据不存在
	 */
	DATA_UNEXIST(404, "result.message.data.unexist"),
	/**
	 * @Fields <font color="blue">DATA_EXIST</font>
	 * @description 数据已存在
	 */
	DATA_EXIST(409, "result.message.data.exist"),
	/**
	 * @Fields <font color="blue">UNSUPPORTED</font>
	 * @description 请求数据格式不支持
	 */
	UNSUPPORTED(415, "result.message.unsupported"),
	/**
	 * @Fields <font color="blue">SYSTEM_ERROR</font>
	 * @description 系统内部异常
	 */
	SYSTEM_ERROR(500, "result.message.system.error"),
	/**
	 * @Fields <font color="blue">SYSTEM_DEFINE</font>
	 * @description 系统方法为定义无法访问
	 */
	SYSTEM_DEFINE(501, "result.message.system.define");

	private final int value;
	private final String message;

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	ResultCode(int value, String message) {
		this.value = value;
		this.message = SystemMessage.bundle(message);
	}
}