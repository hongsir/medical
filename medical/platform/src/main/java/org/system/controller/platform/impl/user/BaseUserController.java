package org.system.controller.platform.impl.user;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.core.result.ResultCode;
import org.core.result.ResultMap;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.system.controller.platform.iface.user.IBaseUserController;
import org.system.entity.platform.user.BaseUser;
import org.system.message.Prompt;
import org.system.service.platform.iface.user.IBaseUserService;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年6月27日
 * @version 1.0
 * @description 基础用户接口实现
 */
@Controller
public class BaseUserController implements IBaseUserController {
	@Resource
	private IBaseUserService baseUserService;

	@Override
	public Map<String, Object> insertBaseUser(Integer moduleId, BaseUser baseUser, BindingResult result) {
		baseUser.setBaseModuleId(moduleId);
		/* 查询用户是否存在 目前验证条件只有身份证+状态 */
		BaseUser repeatBaseUser = new BaseUser();
		repeatBaseUser.setIdCard(baseUser.getIdCard());
		repeatBaseUser.setIsUsed(true);
		Map<String, Object> userMap = baseUserService.getRepeat(repeatBaseUser);

		/* 存在则提示重复 */
		if (!MapUtils.isEmpty(userMap)) {
			return ResultMap.convertMap(ResultCode.DATA_EXIST, repeatBaseUser.getErrmsg());
		}
		/* 不存在则新增 */
		if (baseUserService.insert(baseUser)) {
			return ResultMap.convertMap(ResultCode.SUCCESS, baseUser);
		}
		return ResultMap.convertMap(ResultCode.FAIL);
	}

	@Override
	public Map<String, Object> updateBaseUser(Integer id, BaseUser baseUser, BindingResult result) {
		baseUser.setId(id);
		/* 查询用户是否存在 */
		BaseUser existsBaseUser = new BaseUser();
		existsBaseUser.setId(id);
		Map<String, Object> existsUserMap = baseUserService.getOne(existsBaseUser);

		/* 不存在则提示 */
		if (MapUtils.isEmpty(existsUserMap)) {
			return ResultMap.convertMap(ResultCode.DATA_UNEXIST, Prompt.bundle("base.user.info.is.empty"));
		}
		/* 判断是否修改身份证 */
		if (!StringUtils.isEmpty(baseUser.getIdCard())) {
			/* 查询用户是否存在 目前验证条件只有身份证+状态 */
			BaseUser repeatBaseUser = new BaseUser();
			repeatBaseUser.setIdCard(baseUser.getIdCard());
			repeatBaseUser.setIsUsed(true);
			Map<String, Object> userMap = baseUserService.getRepeat(repeatBaseUser);

			/* 存在则提示重复 */
			if (!MapUtils.isEmpty(userMap)) {
				return ResultMap.convertMap(ResultCode.DATA_EXIST, repeatBaseUser.getErrmsg());
			}
		}
		/* 更新用户资料 */
		if (baseUserService.update(baseUser)) {
			return ResultMap.convertMap(ResultCode.SUCCESS);
		}
		return ResultMap.convertMap(ResultCode.FAIL);
	}
}
