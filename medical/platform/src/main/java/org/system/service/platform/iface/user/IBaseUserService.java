package org.system.service.platform.iface.user;

import java.util.Map;

import org.system.entity.platform.user.BaseUser;

public interface IBaseUserService {

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseUser
	 * @return {@link Map}
	 * @date 2017年6月27日
	 * @version 1.0
	 * @description 检查用户数据是否重复 返回提示语句
	 */
	Map<String, Object> getRepeat(BaseUser baseUser);

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseUser
	 * @return {@link Boolean}
	 * @date 2017年6月27日
	 * @version 1.0
	 * @description 新增用户
	 */
	boolean insert(BaseUser baseUser);

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseUser
	 * @return {@link Boolean}
	 * @date 2017年7月7日
	 * @version 1.0
	 * @description 更新用户
	 */
	boolean update(BaseUser baseUser);

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param baseUser
	 * @return {@link Map}
	 * @date 2017年7月7日
	 * @version 1.0
	 * @description 根据ID获取用户详情
	 */
	Map<String, Object> getOne(BaseUser baseUser);

}
