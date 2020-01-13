package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;


/**
 * The persistent class for the tb_station database table.
 *
 */
@Entity
@Table(name="tb_station")
public class TbStation extends ActiveRecord<TbStation> {

	@Id
	private String code;

	@Column(name="crt_code")
	private String crtCode;

	@Column(name="crt_time")
	private java.sql.Timestamp crtTime;

	private String description;

	private String name;
	
	private String id;
	
	private String type;

	@Column(name="security_classification")
	private String securityClassification;

	@Column(name="upt_code")
	private String uptCode;

	@Column(name="upt_time")
	private java.sql.Timestamp uptTime;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCrtCode() {
		return this.crtCode;
	}

	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}

	public Date getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime == null ? null : new java.sql.Timestamp(crtTime.getTime());
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecurityClassification() {
		return this.securityClassification;
	}

	public void setSecurityClassification(String securityClassification) {
		this.securityClassification = securityClassification;
	}

	public String getUptCode() {
		return this.uptCode;
	}

	public void setUptCode(String uptCode) {
		this.uptCode = uptCode;
	}

	public Date getUptTime() {
		return this.uptTime;
	}

	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime == null ? null : new java.sql.Timestamp(uptTime.getTime());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TbStation() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbStation(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("description") instanceof String) {
			this.setDescription((String) obj.getValue("description"));
		}
		if (obj.getValue("securityClassification") instanceof String) {
			this.setSecurityClassification((String) obj.getValue("securityClassification"));
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
