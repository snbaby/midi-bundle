package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.json.JsonObject;


/**
 * The persistent class for the tb_org_station_relation database table.
 */
@Entity
@Table(name = "tb_org_station_relation")
public class TbOrgStationRelation extends ActiveRecord<TbOrgStationRelation> {

    @Id
    private String id;

    @Column(name = "crt_code")
    private String crtCode;

    @Column(name = "crt_time")
    private java.sql.Timestamp  crtTime;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "station_code")
    private String stationCode;
    //映射使用 勿删除
    private String stationName;
    //映射使用 勿删除    
    public String getStationName() {
		return stationName;
	}
    //映射使用 勿删除
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
    //映射使用 勿删除
	public String getOrgName() {
		return orgName;
	}
    //映射使用 勿删除
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    //映射使用 勿删除
	private String orgName;

    /**
     * 实例化
     *
     * @param obj
     */
    public TbOrgStationRelation(JsonObject obj) {
        this();
        if (obj.getValue("id") instanceof String) {
            this.setId((String) obj.getValue("id"));
        }
        if (obj.getValue("orgCode") instanceof String) {
            this.setOrgCode((String) obj.getValue("orgCode"));
        }
        if (obj.getValue("stationCode") instanceof String) {
            this.setStationCode((String) obj.getValue("stationCode"));
        }
        if (obj.getValue("crtCode") instanceof String) {
            this.setCrtCode((String) obj.getValue("crtCode"));
        }
    }

    public TbOrgStationRelation() {
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

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getStationCode() {
        return this.stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

}
