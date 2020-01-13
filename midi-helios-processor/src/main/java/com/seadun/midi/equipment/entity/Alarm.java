package com.seadun.midi.equipment.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;


/**
 * The persistent class for the alarm database table.
 */
@Entity
@Table(name = "alarm")
public class Alarm extends ActiveRecord<Alarm> {

    @Id
    private String code;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "AREA_DETAILED_LOCATION")
    private String areaDetailedLocation;

    @Column(name = "AREA_NAME")
    private String areaName;

    @Column(name = "CABINET_CODE")
    private String cabinetCode;

    @Column(name = "CABINET_MODEL")
    private String cabinetModel;

    @Column(name = "CABINET_NAME")
    private String cabinetName;

    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "CRT_USER")
    private String crtUser;

    private String detail;

    @Column(name = "EQUIPMENT_CODE")
    private String equipmentCode;

    @Column(name = "EQUIPMENT_DUTY_ORG_CODE")
    private String equipmentDutyOrgCode;


    @Column(name = "EQUIPMENT_DUTY_USER_CODE")
    private String equipmentDutyUserCode;


    @Column(name = "EQUIPMENT_NAME")
    private String equipmentName;

    @Column(name = "EQUIPMENT_SECRET_CODE")
    private String equipmentSecretCode;

    @Column(name = "EQUIPMENT_SECRET_NAME")
    private String equipmentSecretName;

    @Column(name = "EQUIPMENT_TYPE_CODE")
    private String equipmentTypeCode;

    @Column(name = "EQUIPMENT_TYPE_NAME")
    private String equipmentTypeName;

    @Column(name = "LOCATION_CODE")
    private String locationCode;

    @Column(name = "LOCATION_NAME")
    private String locationName;

    @Column(name = "LOCATION_STATUS")
    private String locationStatus;

    private String status;

    @Column(name = "UPT_TIME")
    private Date uptTime;

    @Column(name = "UPT_USER")
    private String uptUser;

    public Alarm() {
        super();
    }

    /**
     * 实例化
     *
     * @param obj
     */
    public Alarm(JsonObject obj) {
        this();
        if (obj.getValue("status") instanceof String) {
            this.setStatus((String) obj.getValue("status"));
        }
        if (obj.getValue("detail") instanceof String) {
            this.setDetail((String) obj.getValue("detail"));
        }
        if (obj.getValue("equipmentCode") instanceof String) {
            this.setEquipmentCode((String) obj.getValue("equipmentCode"));
        }
        if (obj.getValue("equipmentName") instanceof String) {
            this.setEquipmentName((String) obj.getValue("equipmentName"));
        }
        if (obj.getValue("equipmentTypeCode") instanceof String) {
            this.setEquipmentTypeCode((String) obj.getValue("equipmentTypeCode"));
        }
        if (obj.getValue("equipmentTypeName") instanceof String) {
            this.setEquipmentTypeName((String) obj.getValue("equipmentTypeName"));
        }
        if (obj.getValue("equipmentDutyUserCode") instanceof String) {
            this.setEquipmentDutyUserCode((String) obj.getValue("equipmentDutyUserCode"));
        }
        if (obj.getValue("equipmentDutyOrgCode") instanceof String) {
            this.setEquipmentDutyOrgCode((String) obj.getValue("equipmentDutyOrgCode"));
        }
        if (obj.getValue("equipmentSecretCode") instanceof String) {
            this.setEquipmentSecretCode((String) obj.getValue("equipmentSecretCode"));
        }
        if (obj.getValue("equipmentSecretName") instanceof String) {
            this.setEquipmentSecretName((String) obj.getValue("equipmentSecretName"));
        }
        if (obj.getValue("locationCode") instanceof String) {
            this.setLocationCode((String) obj.getValue("locationCode"));
        }
        if (obj.getValue("locationName") instanceof String) {
            this.setLocationName((String) obj.getValue("locationName"));
        }
        if (obj.getValue("locationStatus") instanceof String) {
            this.setLocationStatus((String) obj.getValue("locationStatus"));
        }
        if (obj.getValue("cabinetCode") instanceof String) {
            this.setCabinetCode((String) obj.getValue("cabinetCode"));
        }
        if (obj.getValue("cabinetName") instanceof String) {
            this.setCabinetName((String) obj.getValue("cabinetName"));
        }
        if (obj.getValue("cabinetModel") instanceof String) {
            this.setCabinetModel((String) obj.getValue("cabinetModel"));
        }
        if (obj.getValue("areaCode") instanceof String) {
            this.setAreaCode((String) obj.getValue("areaCode"));
        }
        if (obj.getValue("areaName") instanceof String) {
            this.setAreaName((String) obj.getValue("areaName"));
        }
        if (obj.getValue("areaDetailedLocation") instanceof String) {
            this.setAreaDetailedLocation((String) obj.getValue("areaDetailedLocation"));
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaDetailedLocation() {
        return this.areaDetailedLocation;
    }

    public void setAreaDetailedLocation(String areaDetailedLocation) {
        this.areaDetailedLocation = areaDetailedLocation;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCabinetCode() {
        return this.cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getCabinetModel() {
        return this.cabinetModel;
    }

    public void setCabinetModel(String cabinetModel) {
        this.cabinetModel = cabinetModel;
    }

    public String getCabinetName() {
        return this.cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
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

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEquipmentCode() {
        return this.equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentDutyOrgCode() {
        return this.equipmentDutyOrgCode;
    }

    public void setEquipmentDutyOrgCode(String equipmentDutyOrgCode) {
        this.equipmentDutyOrgCode = equipmentDutyOrgCode;
    }


    public String getEquipmentDutyUserCode() {
        return this.equipmentDutyUserCode;
    }

    public void setEquipmentDutyUserCode(String equipmentDutyUserCode) {
        this.equipmentDutyUserCode = equipmentDutyUserCode;
    }


    public String getEquipmentName() {
        return this.equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentSecretCode() {
        return this.equipmentSecretCode;
    }

    public void setEquipmentSecretCode(String equipmentSecretCode) {
        this.equipmentSecretCode = equipmentSecretCode;
    }

    public String getEquipmentSecretName() {
        return this.equipmentSecretName;
    }

    public void setEquipmentSecretName(String equipmentSecretName) {
        this.equipmentSecretName = equipmentSecretName;
    }


    public String getEquipmentTypeCode() {
        return this.equipmentTypeCode;
    }

    public void setEquipmentTypeCode(String equipmentTypeCode) {
        this.equipmentTypeCode = equipmentTypeCode;
    }

    public String getEquipmentTypeName() {
        return this.equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationStatus() {
        return this.locationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
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
