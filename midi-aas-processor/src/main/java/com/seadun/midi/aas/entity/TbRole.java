package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_role实体类
 *
 * @author
 *
 */
@Table(name = "tb_role")
public class TbRole extends ActiveRecord<TbRole> {
	/**角色编码*/
	@Id
	private String code;
	/**角色名称*/
	private String name;
	/***/
	private String description;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**创建时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/** 更新人用户id */
	@Column(name = "upt_code")
	private String uptCode;
	/**更新时间*/
	@Column(name = "upt_time")
	private java.sql.Timestamp uptTime;

	/**
	 * 实例化
	 */
	public TbRole() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbRole(JsonObject obj) {
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
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public TbRole(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setDescription(params.get("description"));
		this.setCrtCode(params.get("crtCode"));
		this.setUptCode(params.get("uptCode"));
	}
	/**
	 * 将当前对象转换为JsonObject
	 *
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		if (this.getCode() != null) {
			result.put("code",this.getCode());
		}
		if (this.getName() != null) {
			result.put("name",this.getName());
		}
		if (this.getDescription() != null) {
			result.put("description",this.getDescription());
		}
		if (this.getCrtCode() != null) {
			result.put("crtCode",this.getCrtCode());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getUptCode() != null) {
			result.put("uptCode",this.getUptCode());
		}
		if (this.getUptTime() != null) {
			result.put("uptTime",this.getUptTime());
		}
		return result;
	}


	/**
	 * 获取code
	 *
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置code
	 *
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取name
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取description
	 *
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置description
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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

	/**
	 * 获取uptCode
	 *
	 * @return
	 */
	public String getUptCode() {
		return uptCode;
	}

	/**
	 * 设置uptCode
	 *
	 * @param uptCode
	 */
	public void setUptCode(String uptCode) {
		this.uptCode = uptCode;
	}

	/**
	 * 获取uptTime
	 *
	 * @return
	 */
	public Date getUptTime() {
		return uptTime;
	}

	/**
	 * 设置uptTime
	 *
	 * @param uptTime
	 */
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime == null ? null : new java.sql.Timestamp(uptTime.getTime());
	}

	@Override
	public String toString() {
		return "TbRole [code=" + code + " , name=" + name + " , description=" + description + " , crtCode=" + crtCode
				+ " , crtTime=" + crtTime + " , uptCode=" + uptCode + " , uptTime=" + uptTime + "  ]";

	}


}
