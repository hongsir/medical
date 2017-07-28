package org.system.service.platform.impl.user;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.core.annotation.db.DataSource;
import org.springframework.stereotype.Service;
import org.system.entity.platform.user.BaseUser;
import org.system.entity.platform.user.BaseUserDetail;
import org.system.mapper.platform.user.BaseUserDetailMapper;
import org.system.mapper.platform.user.BaseUserMapper;
import org.system.message.Prompt;
import org.system.service.platform.iface.user.IBaseUserService;

@Service
@DataSource("dataSourceTwo")
public class BaseUserService implements IBaseUserService {
	@Resource
	private BaseUserMapper baseUserMapper;
	@Resource
	private BaseUserDetailMapper baseUserDetailMapper;

	@Override
	public Map<String, Object> getRepeat(BaseUser baseUser) {
		/* 检查BaseUser表数据是否重复 */
		Map<String, Object> baseUserMap = baseUserMapper.getRepeat(baseUser);
		if (baseUserMap != null && baseUserMap.size() > 0) {
			baseUser.setErrmsg(Prompt.bundle("base.user.info.is.repeat"));
		}
		return baseUserMap;
	}

	@Override
	public boolean insert(BaseUser baseUser) {
		boolean result = true;

		/* 新增BaseUser数据 */
		baseUser.setIsUsed(true);
		baseUser.setRegisterDate(new Date());
		baseUser.setUpdateTime(new Date());
		baseUserMapper.insert(baseUser);

		/* 新增BaseUserDetail数据 */
		BaseUserDetail baseUserDetail = baseUser.getBaseUserDetail();
		if (baseUserDetail != null) {
			baseUserDetail.setUserId(baseUser.getId());
			baseUserDetail.setCreateTime(new Date());
			baseUserDetail.setUpdateTime(new Date());
			baseUserDetailMapper.insert(baseUserDetail);
		}
		return result;
	}

	@Override
	public boolean update(BaseUser baseUser) {
		boolean result = true;

		/* 更新BaseUser信息 */
		baseUser.setUpdateTime(new Date());
		baseUserMapper.update(baseUser);

		/* 更新BaseUserDetail数据 */
		BaseUserDetail baseUserDetail = baseUser.getBaseUserDetail();
		if (baseUserDetail != null) {
			baseUserDetail.setUserId(baseUser.getId());
			baseUserDetail.setUpdateTime(new Date());
			baseUserDetailMapper.update(baseUserDetail);
		}
		return result;
	}

	@Override
	public Map<String, Object> getOne(BaseUser baseUser) {
		return baseUserMapper.getOne(baseUser);
	}
}
