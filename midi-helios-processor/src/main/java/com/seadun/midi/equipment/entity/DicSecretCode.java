package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

/**
 * dic_secret_code实体类
 *
 * @author
 *
 */
@Table(name="dic_secret_code")
public class DicSecretCode extends ActiveRecord<DicSecretCode> {
	/**密级编码*/
	@Id
	@Column(name = "secret_code")
	private String secretCode;
	/**密级名称*/
	@Column(name = "secret_name")
	private String secretName;
	/***/
	@Column(name="crt_user")
	private String crtUser;
	/***/
	@Column(name="crt_time")
	private java.util.Date crtTime;
	/***/
	@Column(name="upt_user")
	private String uptUser;
	/***/
	@Column(name="upt_time")
	private java.util.Date uptTime;
	/**
	 * 实例化
	 */
	public DicSecretCode() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public DicSecretCode(JsonObject obj) {
		this();
		if (obj.getValue("secretCode") instanceof String) {
			this.setSecretCode((String) obj.getValue("secretCode"));
		}
		if (obj.getValue("secretName") instanceof String) {
			this.setSecretName((String) obj.getValue("secretName"));
		}
		if (obj.getValue("crtUser") instanceof String) {
			this.setCrtUser((String) obj.getValue("crtUser"));
		}
		if (obj.getValue("uptUser") instanceof String) {
			this.setUptUser((String) obj.getValue("uptUser"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public DicSecretCode(MultiMap params) {
		this();
		this.setSecretCode(params.get("secretCode"));
		this.setSecretName(params.get("secretName"));
		this.setCrtUser(params.get("crtUser"));
		this.setUptUser(params.get("uptUser"));
	}
	/**
	 * 将当前对象转换为JsonObject
	 *
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		if (this.getSecretCode() != null) {
			result.put("secretCode",this.getSecretCode());
		}
		if (this.getSecretName() != null) {
			result.put("secretName",this.getSecretName());
		}
		if (this.getCrtUser() != null) {
			result.put("crtUser",this.getCrtUser());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getUptUser() != null) {
			result.put("uptUser",this.getUptUser());
		}
		if (this.getUptTime() != null) {
			result.put("uptTime",this.getUptTime());
		}
		return result;
	}


	/**
	 * 获取secretCode
	 *
	 * @return
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * 设置secretCode
	 *
	 * @param secretCode
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	/**
	 * 获取secretName
	 *
	 * @return
	 */
	public String getSecretName() {
		return secretName;
	}

	/**
	 * 设置secretName
	 *
	 * @param secretName
	 */
	public void setSecretName(String secretName) {
		this.secretName = secretName;
	}

	/**
	 * 获取crtUser
	 *
	 * @return
	 */
	public String getCrtUser() {
		return crtUser;
	}

	/**
	 * 设置crtUser
	 *
	 * @param crtUser
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	/**
	 * 获取crtTime
	 *
	 * @return
	 */
	public java.util.Date getCrtTime() {
		return crtTime;
	}

	/**
	 * 设置crtTime
	 *
	 * @param crtTime
	 */
	public void setCrtTime(java.util.Date crtTime) {
		this.crtTime = crtTime;
	}

	/**
	 * 获取uptUser
	 *
	 * @return
	 */
	public String getUptUser() {
		return uptUser;
	}

	/**
	 * 设置uptUser
	 *
	 * @param uptUser
	 */
	public void setUptUser(String uptUser) {
		this.uptUser = uptUser;
	}

	/**
	 * 获取uptTime
	 *
	 * @return
	 */
	public java.util.Date getUptTime() {
		return uptTime;
	}

	/**
	 * 设置uptTime
	 *
	 * @param uptTime
	 */
	public void setUptTime(java.util.Date uptTime) {
		this.uptTime = uptTime;
	}

	@Override
	public String toString() {
		return "DicSecretCode [secretCode=" + secretCode + " , secretName=" + secretName + " , crtUser=" + crtUser + " , crtTime=" + crtTime + " , uptUser=" + uptUser + " , uptTime=" + uptTime + "  ]";

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DicSecretCode that = (DicSecretCode) o;
		return secretCode.equals(that.secretCode) &&
				secretName.equals(that.secretName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(secretCode, secretName);
	}
}
