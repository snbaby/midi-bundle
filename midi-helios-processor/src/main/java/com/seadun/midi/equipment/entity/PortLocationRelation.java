package com.seadun.midi.equipment.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the port_location_relation database table.
 *
 */
@Table(name = "port_location_relation")
public class PortLocationRelation extends ActiveRecord<PortLocationRelation> {

	@Id
	private String id;

	@Column(name="crt_time")
	private Date crtTime;

	@Column(name="crt_user")
	private String crtUser;

	@Column(name="location_code")
	private String locationCode;

	@Column(name="port_code")
	private String portCode;

	public PortLocationRelation() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getPortCode() {
		return this.portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

}
