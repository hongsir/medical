package org.system.controller.platform.iface.user;

import java.util.Map;

import org.core.annotation.permission.RequiresRoles;
import org.core.entity.BaseEntity.Insert;
import org.core.entity.BaseEntity.Update;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.system.entity.platform.user.BaseUser;
import org.system.global.BaseGlobal;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年6月27日
 * @version 1.0
 * @description 基础用户接口
 */
@RequestMapping(value = { "/base" })
public interface IBaseUserController {

	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param user
	 * @param result
	 * @param moduleId
	 * @return {@link Map}
	 * @date 2017年6月27日
	 * @version 1.0
	 * @description 注册用户
	 */
	@RequestMapping(value = { "/user" }, method = RequestMethod.POST)
	@ResponseBody
	@RequiresRoles(value = { "base:user" })
	public Map<String, Object> insertBaseUser(@RequestParam(BaseGlobal.MOUDLE_ID) Integer moduleId, @Validated({ Insert.class }) @RequestBody BaseUser baseUser, BindingResult result);

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param id
	 * @param baseUser
	 * @param result
	 * @return {@link Map}
	 * @date 2017年7月7日
	 * @version 1.0
	 * @description 更新用户资料
	 */
	@RequestMapping(value = { "/user/{id}" }, method = RequestMethod.PUT)
	@ResponseBody
	@RequiresRoles(value = { "base:user" })
	public Map<String, Object> updateBaseUser(@PathVariable("id") Integer id, @Validated({ Update.class }) @RequestBody BaseUser baseUser, BindingResult result);
}
