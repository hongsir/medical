package org.system.service.platform.iface.permission;

import java.util.List;
import java.util.Map;

import org.system.entity.platform.permission.BaseModule;

public interface IBaseModuleService {

	/** 
	* @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	* @param BaseModule
	* @return {@link Map}
	* @date 2017年7月4日 
	* @version 1.0
	* @description 获取客户端信息
	*/
	Map<String, Object> getOne(BaseModule baseModule);

	/** 
	* @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	* @param BaseModule
	* @return {@link List}
	* @date 2017年7月4日 
	* @version 1.0
	* @description 获取客户端对应的角色集合
	*/
	List<Map<String, Object>> getRoleList(BaseModule module);

	/** 
	* @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	* @param BaseModule
	* @return {@link List}
	* @date 2017年7月4日 
	* @version 1.0
	* @description 获取客户端对应的权限集合
	*/
	List<Map<String, Object>> getPermissionList(BaseModule baseModule);

	/** 
	* @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	* @param BaseModule
	* @return {@link List}
	* @date 2017年7月4日 
	* @version 1.0
	* @description 获取客户端对应的操作集合
	*/
	List<Map<String, Object>> getOperationList(BaseModule baseModule);

}
