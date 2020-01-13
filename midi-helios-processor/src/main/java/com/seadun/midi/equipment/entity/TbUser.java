package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_user实体类
 *
 * @author
 *
 */
@Table(name = "tb_user")
public class TbUser  extends ActiveRecord<TbUser>  {
	/**用户id*/
	@Id
	@Column(name = "code")
	private String code;
	/**账户名称*/
	@Column(name = "name")
	private String name;
	/**账户密码*/
	@Column(name = "password")
	private String password;
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
	/**
	 * 实例化
	 */
	public TbUser() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbUser(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
//		if (obj.getValue("password") instanceof String) {
//			this.setPassword((String) obj.getValue("password"));
//		}
		this.setExpireTime((Date) obj.getValue("expireTime"));
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("crtCode") instanceof String) {
			this.setCrtCode((String) obj.getValue("crtCode"));
		}
		this.setCrtTime((Date) obj.getValue("crtTime"));
		if (obj.getValue("uptCode") instanceof String) {
			this.setUptCode((String) obj.getValue("uptCode"));
		}
		this.setUptTime((Date) obj.getValue("uptTime"));
	}



	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	/**
	 * 将当前对象转换为JsonObject
	 *
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		if (this.getCode() != null) {
			result.put("code",this.getCode());
		}
		if (this.getName() != null) {
			result.put("name",this.getName());
		}
//		if (this.getPassword() != null) {
//			result.put("password",this.getPassword());
//		}
		if (this.getExpireTime() != null) {
			result.put("expireTime",this.getExpireTime());
		}
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
		}
		if (this.getCrtCode() != null) {
			result.put("crtCode",this.getCrtCode());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getUptCode() != null) {
			result.put("uptCode",this.getUptCode());
		}
		if (this.getUptTime() != null) {
			result.put("uptTime",this.getUptTime());
		}
		return result;
	}


	/**
	 * 获取code
	 *
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置code
	 *
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取name
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取password
	 *
	 * @return
	 */
//	public String getPassword() {
//		return password;
//	}

	/**
	 * 设置password
	 *
	 * @param password
	 */
//	public void setPassword(String password) {
//		this.password = password;
//	}

	/**
	 * 获取expireTime
	 *
	 * @return
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * 设置expireTime
	 *
	 * @param expireTime
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime == null ? null : new java.sql.Timestamp(expireTime.getTime());
	}

	/**
	 * 获取status
	 *
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置status
	 *
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取crtCode
	 *
	 * @return
	 */
	public String getCrtCode() {
		return crtCode;
	}

	/**
	 * 设置crtCode
	 *
	 * @param crtCode
	 */
	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}

	/**
	 * 获取crtTime
	 *
	 * @return
	 */
	public Date getCrtTime() {
		return crtTime;
	}

	/**
	 * 设置crtTime
	 *
	 * @param crtTime
	 */
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime == null ? null : new java.sql.Timestamp(crtTime.getTime());
	}

	/**
	 * 获取uptCode
	 *
	 * @return
	 */
	public String getUptCode() {
		return uptCode;
	}

	/**
	 * 设置uptCode
	 *
	 * @param uptCode
	 */
	public void setUptCode(String uptCode) {
		this.uptCode = uptCode;
	}

	/**
	 * 获取uptTime
	 *
	 * @return
	 */
	public Date getUptTime() {
		return uptTime;
	}

	/**
	 * 设置uptTime
	 *
	 * @param uptTime
	 */
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime == null ? null : new java.sql.Timestamp(uptTime.getTime());
	}

	@Override
	public String toString() {
		return "TbUser [code=" + code + " , name=" + name + " , password=" + password + " , expireTime=" + expireTime + " , status=" + status + " , crtCode=" + crtCode + " , crtTime=" + crtTime + " , uptCode=" + uptCode + " , uptTime=" + uptTime + "  ]";

	}


}
