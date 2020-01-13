package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the tb_station_duty_relation database table.
 *
 */
@Entity
@Table(name="tb_station_duty_relation")
public class TbStationDutyRelation extends ActiveRecord<TbStationDutyRelation> {

	@Id
	private String id;

	@Column(name="crt_code")
	private String crtCode;

	@Column(name="crt_time")
	private java.sql.Timestamp crtTime;

	@Column(name="duty_code")
	private String dutyCode;

	@Column(name="station_code")
	private String stationCode;

	public TbStationDutyRelation() {
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

	public String getDutyCode() {
		return this.dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

}
