package org.system.entity.platform.user;

import java.util.Date;
import org.core.entity.BaseEntity;

public class BaseUserDetail extends BaseEntity {
	private Integer userId;

	private String residenceAddress;

	private String nation;

	private String degree;

	private String occupation;

	private Boolean islocal;

	private Integer marriageType;

	private String contact;

	private String photo;

	private Date updateTime;

	private Date createTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Boolean getIslocal() {
		return islocal;
	}

	public void setIslocal(Boolean islocal) {
		this.islocal = islocal;
	}

	public Integer getMarriageType() {
		return marriageType;
	}

	public void setMarriageType(Integer marriageType) {
		this.marriageType = marriageType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}