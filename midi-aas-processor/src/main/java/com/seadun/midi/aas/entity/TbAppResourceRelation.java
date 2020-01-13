package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_app_resource_relation实体类
 *
 * @author
 *
 */
@Table(name = "tb_app_resource_relation")
public class TbAppResourceRelation extends ActiveRecord<TbAppResourceRelation> {
	/**物理主键*/
	@Id
	private String id;
	/***/
	@Column(name = "app_code")
	private String appCode;
	/***/
	@Column(name = "resource_code")
	private String resourceCode;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**创建时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/**
	 * 实例化
	 */
	public TbAppResourceRelation() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbAppResourceRelation(JsonObject obj) {
		this();
		if (obj.getValue("id") instanceof String) {
			this.setId((String) obj.getValue("id"));
		}
		if (obj.getValue("appCode") instanceof String) {
			this.setAppCode((String) obj.getValue("appCode"));
		}
		if (obj.getValue("resourceCode") instanceof String) {
			this.setResourceCode((String) obj.getValue("resourceCode"));
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
	public TbAppResourceRelation(MultiMap params) {
		this();
		this.setId(params.get("id"));
		this.setAppCode(params.get("appCode"));
		this.setResourceCode(params.get("resourceCode"));
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
			result.put("id",this.getId());
		}
		if (this.getAppCode() != null) {
			result.put("appCode",this.getAppCode());
		}
		if (this.getResourceCode() != null) {
			result.put("resourceCode",this.getResourceCode());
		}
		if (this.getCrtCode() != null) {
			result.put("crtCode",this.getCrtCode());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
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
	 * 获取resourceCode
	 *
	 * @return
	 */
	public String getResourceCode() {
		return resourceCode;
	}

	/**
	 * 设置resourceCode
	 *
	 * @param resourceCode
	 */
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
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
		return "TbAppResourceRelation [id=" + id + " , appCode=" + appCode + " , resourceCode=" + resourceCode + " , crtCode=" + crtCode + " , crtTime=" + crtTime + "  ]";

	}


}
