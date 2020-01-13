package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * location实体类
 *
 * @author
 *
 */
@Table(name = "location")
public class Location  extends ActiveRecord<Location> {
	/**机柜唯一标识*/
	@Id
	private String code;
	/***/
	private String name;
	/**1-正常
            2-未使用*/
	private String status;
	/***/
	@Column(name = "cabinet_code")
	private String cabinetCode;
	/***/
	@Column(name = "crt_user")
	private String crtUser;
	/***/
	@Column(name = "crt_time")
	private java.util.Date crtTime;
	/***/
	@Column(name = "upt_time")
	private java.util.Date uptTime;
	/***/
	@Column(name = "upt_user")
	private String uptUser;
	/**
	 * 实例化
	 */
	public Location() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public Location(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("cabinetCode") instanceof String) {
			this.setCabinetCode((String) obj.getValue("cabinetCode"));
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
	public Location(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setStatus(params.get("status"));
		this.setCabinetCode(params.get("cabinetCode"));
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
		if (this.getCode() != null) {
			result.put("code",this.getCode());
		}
		if (this.getName() != null) {
			result.put("name",this.getName());
		}
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
		}
		if (this.getCabinetCode() != null) {
			result.put("cabinetCode",this.getCabinetCode());
		}
		if (this.getCrtUser() != null) {
			result.put("crtUser",this.getCrtUser());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getUptTime() != null) {
			result.put("uptTime",this.getUptTime());
		}
		if (this.getUptUser() != null) {
			result.put("uptUser",this.getUptUser());
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
	 * 获取cabinetCode
	 *
	 * @return
	 */
	public String getCabinetCode() {
		return cabinetCode;
	}

	/**
	 * 设置cabinetCode
	 *
	 * @param cabinetCode
	 */
	public void setCabinetCode(String cabinetCode) {
		this.cabinetCode = cabinetCode;
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

	@Override
	public String toString() {
		return "Location [code=" + code + " , name=" + name + " , status=" + status + " , cabinetCode=" + cabinetCode + " , crtUser=" + crtUser + " , crtTime=" + crtTime + " , uptTime=" + uptTime + " , uptUser=" + uptUser + "  ]";

	}


}
