package com.seadun.midi.equipment.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;


/**
 * The persistent class for the detect database table.
 *
 */
@Entity
@Table(name = "detect")
public class Detect extends ActiveRecord<Detect> {

	@Id
	private String code;

	@Column(name="crt_time")
	private Date crtTime;

	@Column(name="crt_user")
	private String crtUser;

	@Column(name = "area_code")
	private String areaCode;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	private String ip;

	private String model;

	private String name;

	@Column(name="secret_code")
	private String secretCode;

	private String status;

	@Column(name="upt_time")
	private Date uptTime;

	@Column(name="upt_user")
	private String uptUser;

	public Detect() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public Detect(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("secretCode") instanceof String) {
			this.setSecretCode((String) obj.getValue("secretCode"));
		}
		if (obj.getValue("areaCode") instanceof String) {
			this.setAreaCode((String) obj.getValue("areaCode"));
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
		if (obj.getValue("ip") instanceof String) {
			this.setIp((String) obj.getValue("ip"));
		}

	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getCrtUser() {
		return this.crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecretCode() {
		return this.secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUptTime() {
		return this.uptTime;
	}

	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}

	public String getUptUser() {
		return this.uptUser;
	}

	public void setUptUser(String uptUser) {
		this.uptUser = uptUser;
	}

}
