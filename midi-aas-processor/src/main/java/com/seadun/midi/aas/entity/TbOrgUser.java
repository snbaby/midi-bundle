package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

/**
 * tb_user实体类
 *
 * @author
 *
 */
//映射使用
public class TbOrgUser  extends ActiveRecord<TbOrgUser>  {
	
	@Column(name = "code")
	private String userCode;
	/**账户名称*/
	@Column(name = "name")
	private String userName;
	
	@Column(name = "orgName")
	private String orgName;
	
	@Column(name = "orgCode")
	private String orgCode;
	
	/**密码过期时间*/
	@Column(name = "expire_time")
	private java.sql.Timestamp expireTime;
	/**状态 0-正常 1-禁用 2-锁定 3-注销*/
	@Column(name = "status")
	private String status;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**创建时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/**更新人用户id*/
	@Column(name = "upt_code")
	private String uptCode;
	/**更新时间*/
	@Column(name = "upt_time")
	private java.sql.Timestamp uptTime;
	@Column(name = "password_salt")
	private String passwordSalt;
	@Column(name = "version")
	private String version;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public java.sql.Timestamp getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(java.sql.Timestamp expireTime) {
		this.expireTime = expireTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCrtCode() {
		return crtCode;
	}
	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}
	public java.sql.Timestamp getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(java.sql.Timestamp crtTime) {
		this.crtTime = crtTime;
	}
	public String getUptCode() {
		return uptCode;
	}
	public void setUptCode(String uptCode) {
		this.uptCode = uptCode;
	}
	public java.sql.Timestamp getUptTime() {
		return uptTime;
	}
	public void setUptTime(java.sql.Timestamp uptTime) {
		this.uptTime = uptTime;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	


}
