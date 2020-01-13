package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

/**
 * tb_user实体类
 *
 * @author
 *
 */
// 映射使用 勿删除
public class TbRoleUser extends ActiveRecord<TbRoleUser> {
	/** 用户id */
	@Id
	@Column(name = "userCode")
	private String userCode;
	@Column(name = "userName")
	private String userName;
	@Column(name = "userStatus")
	private String userStatus;
	/** 密码过期时间 */
	@Column(name = "expireTime")
	private java.sql.Timestamp expireTime;

	@Column(name = "personCode")
	private String personCode;
	@Column(name = "personName")
	private String personName;
	@Column(name = "personSex")
	private String personSex;

	@Column(name = "personSecurityClassification")
	private String personSecurityClassification;

	@Column(name = "personStatus")
	private String personStatus;

	@Column(name = "email")
	private String email;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "address")
	private String address;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(java.sql.Timestamp expireTime) {
		this.expireTime = expireTime;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	public String getPersonSecurityClassification() {
		return personSecurityClassification;
	}
	public void setPersonSecurityClassification(String personSecurityClassification) {
		this.personSecurityClassification = personSecurityClassification;
	}
	public String getPersonStatus() {
		return personStatus;
	}
	public void setPersonStatus(String personStatus) {
		this.personStatus = personStatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
