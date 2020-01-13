package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * equipment_location_relation实体类
 *
 * @author
 */
@Table(name = "equipment_location_relation")
public class EquipmentLocationRelation extends ActiveRecord<EquipmentLocationRelation> {
    /***/
    @Id
    private String id;
    /**
     * 位置code
     */
    @Column(name = "location_code")
    private String locationCode;
    /**
     * 设备编码
     */
    @Column(name = "equipment_code")
    private String equipmentCode;
    /***/
    @Column(name = "crt_user")
    private String crtUser;
    /***/
    @Column(name = "crt_time")
    private java.util.Date crtTime;

    /**
     * 实例化
     */
    public EquipmentLocationRelation() {
        super();
    }

    /**
     * 实例化
     *
     * @param obj
     */
    public EquipmentLocationRelation(JsonObject obj) {
        this();
        if (obj.getValue("id") instanceof String) {
            this.setId((String) obj.getValue("id"));
        }
        if (obj.getValue("locationCode") instanceof String) {
            this.setLocationCode((String) obj.getValue("locationCode"));
        }
        if (obj.getValue("equipmentCode") instanceof String) {
            this.setEquipmentCode((String) obj.getValue("equipmentCode"));
        }
        if (obj.getValue("crtUser") instanceof String) {
            this.setCrtUser((String) obj.getValue("crtUser"));
        }
    }

    /**
     * 实例化
     *
     * @param params
     */
    public EquipmentLocationRelation(MultiMap params) {
        this();
        this.setId(params.get("id"));
        this.setLocationCode(params.get("locationCode"));
        this.setEquipmentCode(params.get("equipmentCode"));
        this.setCrtUser(params.get("crtUser"));
    }

    /**
     * 将当前对象转换为JsonObject
     *
     * @return
     */
    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        if (this.getId() != null) {
            result.put("id", this.getId());
        }
        if (this.getLocationCode() != null) {
            result.put("locationCode", this.getLocationCode());
        }
        if (this.getEquipmentCode() != null) {
            result.put("equipmentCode", this.getEquipmentCode());
        }
        if (this.getCrtUser() != null) {
            result.put("crtUser", this.getCrtUser());
        }
        if (this.getCrtTime() != null) {
            result.put("crtTime", this.getCrtTime());
        }
        return result;
    }


    /**
     * 获取id
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取locationCode
     *
     * @return
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * 设置locationCode
     *
     * @param locationCode
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * 获取equipmentCode
     *
     * @return
     */
    public String getEquipmentCode() {
        return equipmentCode;
    }

    /**
     * 设置equipmentCode
     *
     * @param equipmentCode
     */
    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
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

    @Override
    public String toString() {
        return "EquipmentLocationRelation [id=" + id + " , locationCode=" + locationCode + " , equipmentCode=" + equipmentCode + " , crtUser=" + crtUser + " , crtTime=" + crtTime + "  ]";

    }


}
