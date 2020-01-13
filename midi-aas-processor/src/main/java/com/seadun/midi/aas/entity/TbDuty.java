package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;

/**
 * The persistent class for the tb_duty database table.
 * 
 */
@Entity
@Table(name = "tb_duty")
public class TbDuty extends ActiveRecord<TbDuty> {

	@Id
	private String code;

	@Column(name = "crt_code")
	private String crtCode;

	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;

	@Column(name = "upt_code")
	private String uptCode;

	@Column(name = "upt_time")
	private java.sql.Timestamp uptTime;

	public TbDuty() {
	}

	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbDuty(JsonObject obj) {
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
		if (obj.getValue("crtCode") instanceof String) {
			this.setCrtCode((String) obj.getValue("crtCode"));
		}
		if (obj.getValue("uptCode") instanceof String) {
			this.setUptCode((String) obj.getValue("uptCode"));
		}
	}

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

}