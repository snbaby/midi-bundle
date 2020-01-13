package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the tb_tosr_person_relation database table.
 *
 */
@Entity
@Table(name="tb_tosr_person_relation")
public class TbTosrPersonRelation extends ActiveRecord<TbTosrPersonRelation> {

	@Id
	private String id;

	@Column(name="crt_code")
	private String crtCode;

	@Column(name="crt_time")
	private java.sql.Timestamp crtTime;

	@Column(name="tosr_id")
	private String tosrId;

	@Column(name="person_code")
	private String personCode;

	public TbTosrPersonRelation() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTosrId() {
		return this.tosrId;
	}

	public void setTosrId(String tosrId) {
		this.tosrId = tosrId;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

}
