package org.system.entity.platform.user;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.core.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseUser extends BaseEntity {
	private Integer id;

	@JSONField(serialize = false)
	private Integer baseModuleId;

	@NotBlank(message = "{base.user.phone.notblank.valid}", groups = { Insert.class })
	private String phone;

	private Date registerDate;

	private String homeAddress;

	@NotBlank(message = "{base.user.id.card.notblank.valid}", groups = { Insert.class })
	private String idCard;

	@NotBlank(message = "{base.user.name.notblank.valid}", groups = { Insert.class })
	private String name;

	@NotNull(message = "{base.user.sex.notnull.valid}", groups = { Insert.class })
	private Integer sex;

	private Boolean isUsed;

	@NotNull(message = "{base.user.birthday.notnull.valid}", groups = { Insert.class })
	@Past(message = "{base.user.birthday.past.valid}", groups = { Insert.class })
	private Date birthday;

	private Date updateTime;

	private String remark;

	@Valid
	private BaseUserDetail baseUserDetail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBaseModuleId() {
		return baseModuleId;
	}

	public void setBaseModuleId(Integer baseModuleId) {
		this.baseModuleId = baseModuleId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BaseUserDetail getBaseUserDetail() {
		return baseUserDetail;
	}

	public void setBaseUserDetail(BaseUserDetail baseUserDetail) {
		this.baseUserDetail = baseUserDetail;
	}

}