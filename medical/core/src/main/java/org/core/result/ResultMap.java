package org.core.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.core.message.SystemMessage;

/** 
* @author <font color="green"><b>Liu.Gang.Qiang</b></font> 
* @date 2017年6月23日 
* @version 1.0
* @description 结果集封装类
*/
public class ResultMap {
	
	private final static String total = "total";
	private final static String rows = "rows";

	/**
	 * 采用内定消息 有数据实体 用于单条
	 * 
	 * @param code
	 * @param body
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Object body) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		return map;
	}

	/**
	 * 采用自定义消息 有数据实体 用于单条
	 * 
	 * @param code
	 * @param body
	 * @param message
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Object body, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), message);
		return map;
	}

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param code
	 * @param body
	 * @param message
	 * @param otherMap
	 * @return {@link Map}
	 * @date 2017年6月16日
	 * @version 1.0
	 * @description
	 */
	public static Map<String, Object> convertMap(ResultCode code, Object body, String message,
			Map<String, Object> otherMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), message);
		for (Entry<String, Object> entry : otherMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * 采用内定消息 有数据实体 用于多条
	 * 
	 * @param code
	 * @param total
	 * @param data
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Integer total, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put(ResultMap.total, total);
		body.put(ResultMap.rows, data);
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		return map;
	}

	/**
	 * 采用自定义消息 有数据实体 用于多条
	 * 
	 * @param code
	 * @param data
	 * @param total
	 * @param message
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Integer total, Object data, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put(ResultMap.total, total);
		body.put(ResultMap.rows, data);
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), message);
		return map;
	}

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param code
	 * @param total
	 * @param data
	 * @param otherMap
	 * @return
	 * @date 2017年6月16日
	 * @version 1.0
	 * @description
	 */
	public static Map<String, Object> convertMap(ResultCode code, Integer total, Object data,
			Map<String, Object> otherMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put(ResultMap.total, total);
		body.put(ResultMap.rows, data);
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		for (Entry<String, Object> entry : otherMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param code
	 * @param total
	 * @param data
	 * @param message
	 * @param otherMap
	 * @return
	 * @date 2017年6月16日
	 * @version 1.0
	 * @description
	 */
	public static Map<String, Object> convertMap(ResultCode code, Integer total, Object data, String message,
			Map<String, Object> otherMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put(ResultMap.total, total);
		body.put(ResultMap.rows, data);
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), message);
		for (Entry<String, Object> entry : otherMap.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/**
	 * 采用自定义消息 无数据实体 用于无数据返回
	 * 
	 * @param code
	 * @param message
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.message.key"), message);
		return map;
	}

	/**
	 * 采用内定消息 无数据实体 用于无数据返回
	 * 
	 * @param code
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		return map;
	}

}