package org.system.service.platform.impl.permission;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.core.annotation.db.DataSource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.system.entity.platform.permission.BaseModule;
import org.system.global.BaseGlobal;
import org.system.mapper.platform.permission.BaseModuleMapper;
import org.system.service.platform.iface.permission.IBaseModuleService;

@Service
@CacheConfig(cacheNames = { BaseGlobal.CACHE_MOUDLE })
@DataSource(BaseGlobal.DATA_SOURCE_ONE)
public class BaseModuleService implements IBaseModuleService {
	@Resource
	private BaseModuleMapper baseModuleMapper;

	@Override
	@Cacheable(key = "'module:info'+#baseModule.appKey", condition = "#baseModule.appKey NE null", unless = "#result EQ null")
	public Map<String, Object> getOne(BaseModule baseModule) {
		return baseModuleMapper.getOne(baseModule);
	}

	@Override
	@Cacheable(key = "'module:role'+#baseModule.appKey", condition = "#baseModule.appKey NE null", unless = "#result EQ null")
	public List<Map<String, Object>> getRoleList(BaseModule baseModule) {
		return baseModuleMapper.getRoleList(baseModule);
	}

	@Override
	@Cacheable(key = "'module:permission'+#baseModule.appKey", condition = "#baseModule.appKey NE null", unless = "#result EQ null")
	public List<Map<String, Object>> getPermissionList(BaseModule baseModule) {
		return baseModuleMapper.getPermissionList(baseModule);
	}

	@Override
	@Cacheable(key = "'module:operation'+#baseModule.appKey", condition = "#baseModule.appKey NE null", unless = "#result EQ null")
	public List<Map<String, Object>> getOperationList(BaseModule baseModule) {
		return baseModuleMapper.getOperationList(baseModule);
	}

}
