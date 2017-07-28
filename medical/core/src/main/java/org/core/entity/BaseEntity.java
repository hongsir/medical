package org.core.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年10月28日
 * @Version 1.0
 * @Description 顶层实体类抽象类 包含分页属性
 */
public class BaseEntity {

	/**
	 * @Fields <font color="blue">page</font>
	 * @description 当前页数 从1开始计数
	 */
	@JSONField(serialize = false)
	Integer page;
	/**
	 * @Fields <font color="blue">rows</font>
	 * @description 每页记录数 必须大于0
	 */
	@JSONField(serialize = false)
	Integer rows;
	/**
	 * @Fields <font color="blue">total</font>
	 * @description 记录总数 设置分页会自动封装此属性
	 */
	@JSONField(serialize = false)
	Integer total;

	/**
	 * @Fields <font color="blue">errmsg</font>
	 * @description 异常消息
	 */
	@JSONField(serialize = false)
	String errmsg;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page > 0 ? page : 1;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows >= 0 ? rows : 10;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @Date 2016年10月28日
	 * @Version 1.0
	 * @Description 用于新增单条记录的校验分组接口
	 */
	public interface Insert {
	};

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @Date 2016年10月28日
	 * @Version 1.0
	 * @Description 用于修改单条记录的校验分组接口
	 */
	public interface Update {
	};

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @Date 2016年10月28日
	 * @Version 1.0
	 * @Description 用于查询多条记录的校验分组接口
	 */
	public interface SelectAll {
	};

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @Date 2016年10月28日
	 * @Version 1.0
	 * @Description 用于查询条单条记录的校验分组接口
	 */
	public interface SelectOne {
	};

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @Date 2016年10月28日
	 * @Version 1.0
	 * @Description 用于删除记录的校验分组接口
	 */
	public interface Delete {
	};
}
