package org.system.intercept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.core.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年10月28日
 * @Version 1.0
 * @Description 自动分页插件
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object parameter = invocation.getArgs()[1];
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		if (parameter instanceof BaseEntity) {
			BaseEntity params = (BaseEntity) parameter;
			if (params.getRows() != null && params.getPage() != null) {
				Integer offset = params.getPage();
				Integer limit = params.getRows();
				Integer start = (offset - 1) * limit;

				Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
				String countSql = "SELECT COUNT(1) FROM (" + boundSql.getSql() + ") count";
				if (logger.isDebugEnabled()) {
					logger.debug("==>  Total Query : {} ", countSql);
				}
				PreparedStatement countStmt = connection.prepareStatement(countSql);
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameter);
				setParameters(countStmt, mappedStatement, countBS, parameter);
				ResultSet rs = countStmt.executeQuery();
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
					if (logger.isDebugEnabled()) {
						logger.debug("==>  Total is : {}", count);
					}
					params.setTotal(count);
				}
				rs.close();
				countStmt.close();
				connection.close();
				BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), boundSql.getSql() + " LIMIT " + start + "," + limit, boundSql.getParameterMappings(), boundSql.getParameterObject());
				MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

				invocation.getArgs()[0] = newMs;
			}
		}
		return invocation.proceed();
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	@SuppressWarnings("unchecked")
	public void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					Configuration configuration = mappedStatement.getConfiguration();
					if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (parameterObject == null) {
						value = null;
					} else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else {
						MetaObject metaObject = configuration.newMetaObject(parameterObject);
						value = metaObject.getValue(propertyName);
					}
					@SuppressWarnings("rawtypes")
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					JdbcType jdbcType = parameterMapping.getJdbcType();
					if (value == null && jdbcType == null)
						jdbcType = configuration.getJdbcTypeForNull();
					typeHandler.setParameter(ps, i + 1, value, jdbcType);
				}
			}
		}
	}

	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		MappedStatement newMs = builder.build();
		return newMs;
	}
}
