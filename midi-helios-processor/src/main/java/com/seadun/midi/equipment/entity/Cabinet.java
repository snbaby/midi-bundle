package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * cabinet实体类
 *
 * @author
 *
 */
@Table(name = "cabinet")
public class Cabinet extends ActiveRecord<Cabinet> {
	/**机柜唯一标识*/
	@Id
	private String code;
	/**机柜名称*/
	private String name;
	/**机柜型号*/
	private String model;
	/**1-使用
            2-停用
            3-废弃*/
	private String status;
	/***/
	@Column(name = "area_code")
	private String areaCode;
	/***/
	@Column(name = "crt_user")
	private String crtUser;
	/***/
	@Column(name = "crt_time")
	private java.util.Date crtTime;
	/***/
	@Column(name = "upt_user")
	private String uptUser;
	/***/
	@Column(name = "upt_time")
	private java.util.Date uptTime;
	/**
	 * 实例化
	 */
	public Cabinet() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public Cabinet(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("model") instanceof String) {
			this.setModel((String) obj.getValue("model"));
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("areaCode") instanceof String) {
			this.setAreaCode((String) obj.getValue("areaCode"));
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
	public Cabinet(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setModel(params.get("model"));
		this.setStatus(params.get("status"));
		this.setAreaCode(params.get("areaCode"));
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
		if (this.getModel() != null) {
			result.put("model",this.getModel());
		}
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
		}
		if (this.getAreaCode() != null) {
			result.put("areaCode",this.getAreaCode());
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
	 * 获取model
	 *
	 * @return
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 设置model
	 *
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * 获取areaCode
	 *
	 * @return
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 设置areaCode
	 *
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
		return "Cabinet [code=" + code + " , name=" + name + " , model=" + model + " , status=" + status + " , areaCode=" + areaCode + " , crtUser=" + crtUser + " , crtTime=" + crtTime + " , uptUser=" + uptUser + " , uptTime=" + uptTime + "  ]";

	}


}
