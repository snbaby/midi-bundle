package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_role_app_relation实体类
 *
 * @author
 */
@Table(name = "tb_role_app_relation")
public class TbRoleAppRelation extends ActiveRecord<TbRoleAppRelation> {
    /***/
    @Id
    private String id;
    /***/
    @Column(name = "role_code")
    private String roleCode;
    /***/
    @Column(name = "app_code")
    private String appCode;
    /**
     * 创建人用户id
     */
    @Column(name = "crt_code")
    private String crtCode;
    /**
     * 创建时间
     */
    @Column(name = "crt_time")
    private java.sql.Timestamp crtTime;

    /**
     * 实例化
     */
    public TbRoleAppRelation() {
        super();
    }

    /**
     * 实例化
     *
     * @param obj
     */
    public TbRoleAppRelation(JsonObject obj) {
        this();
        if (obj.getValue("id") instanceof String) {
            this.setId((String) obj.getValue("id"));
        }
        if (obj.getValue("roleCode") instanceof String) {
            this.setRoleCode((String) obj.getValue("roleCode"));
        }
        if (obj.getValue("appCode") instanceof String) {
            this.setAppCode((String) obj.getValue("appCode"));
        }
        if (obj.getValue("crtCode") instanceof String) {
            this.setCrtCode((String) obj.getValue("crtCode"));
        }
    }

    /**
     * 实例化
     *
     * @param params
     */
    public TbRoleAppRelation(MultiMap params) {
        this();
        this.setId(params.get("id"));
        this.setRoleCode(params.get("roleCode"));
        this.setAppCode(params.get("appCode"));
        this.setCrtCode(params.get("crtCode"));
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
        if (this.getRoleCode() != null) {
            result.put("roleCode", this.getRoleCode());
        }
        if (this.getAppCode() != null) {
            result.put("appCode", this.getAppCode());
        }
        if (this.getCrtCode() != null) {
            result.put("crtCode", this.getCrtCode());
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
     * 获取roleCode
     *
     * @return
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置roleCode
     *
     * @param roleCode
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * 获取appCode
     *
     * @return
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * 设置appCode
     *
     * @param appCode
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * 获取crtCode
     *
     * @return
     */
    public String getCrtCode() {
        return crtCode;
    }

    /**
     * 设置crtCode
     *
     * @param crtCode
     */
    public void setCrtCode(String crtCode) {
        this.crtCode = crtCode;
    }

    /**
     * 获取crtTime
     *
     * @return
     */
    public Date getCrtTime() {
        return crtTime;
    }

    /**
     * 设置crtTime
     *
     * @param crtTime
     */
    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime == null ? null : new java.sql.Timestamp(crtTime.getTime());
    }

    @Override
    public String toString() {
        return "TbRoleAppRelation [id=" + id + " , roleCode=" + roleCode + " , appCode=" + appCode + " , crtCode=" + crtCode + " , crtTime=" + crtTime + "  ]";

    }


}
