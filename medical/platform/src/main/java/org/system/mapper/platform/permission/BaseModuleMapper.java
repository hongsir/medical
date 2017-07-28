package org.system.mapper.platform.permission;

import java.util.List;
import java.util.Map;

import org.core.mapper.IBaseMapper;
import org.system.entity.platform.permission.BaseModule;

public interface BaseModuleMapper extends IBaseMapper<BaseModule> {

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseModule
	 * @return {@link List}
	 * @date 2017年7月4日
	 * @version 1.0
	 * @description 查询客户端角色集合
	 */
	List<Map<String, Object>> getRoleList(BaseModule baseModule);

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseModule
	 * @return {@link List}
	 * @date 2017年7月4日
	 * @version 1.0
	 * @description 查询客户端权限集合
	 */
	List<Map<String, Object>> getPermissionList(BaseModule	baseModule);

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseModule
	 * @return {@link List}
	 * @date 2017年7月4日
	 * @version 1.0
	 * @description 查询客户端操作集合
	 */
	List<Map<String, Object>> getOperationList(BaseModule baseModule);
}