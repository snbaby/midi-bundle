package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * tb_role_user_relation实体类
 *
 * @author
 */
@Table(name = "tb_role_user_relation")
public class TbRoleUserRelation extends ActiveRecord<TbRoleUserRelation> {
    /***/
    @Id
    private String id;
    /***/
    @Column(name = "role_code")
    private String roleCode;
    /***/
    @Column(name = "user_code")
    private String userCode;
    /**
     * 创建人用户id
     */
    @Column(name = "crt_code")
    private String crtCode;
    /**
     * 创建时间
     */
    @Column(name = "crt_code")
    private java.sql.Timestamp crtTime;

    /**
     * 实例化
     */
    public TbRoleUserRelation() {
        super();
    }

    /**
     * 实例化
     *
     * @param obj
     */
    public TbRoleUserRelation(JsonObject obj) {
        this();
        if (obj.getValue("id") instanceof String) {
            this.setId((String) obj.getValue("id"));
        }
        if (obj.getValue("roleCode") instanceof String) {
            this.setRoleCode((String) obj.getValue("roleCode"));
        }
        if (obj.getValue("userCode") instanceof String) {
            this.setUserCode((String) obj.getValue("userCode"));
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
    public TbRoleUserRelation(MultiMap params) {
        this();
        this.setId(params.get("id"));
        this.setRoleCode(params.get("roleCode"));
        this.setUserCode(params.get("userCode"));
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
        if (this.getUserCode() != null) {
            result.put("userCode", this.getUserCode());
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
     * 获取userCode
     *
     * @return
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置userCode
     *
     * @param userCode
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
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
        return "TbRoleUserRelation [id=" + id + " , roleCode=" + roleCode + " , userCode=" + userCode + " , crtCode=" + crtCode + " , crtTime=" + crtTime + "  ]";

    }


}
