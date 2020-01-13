package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_resource实体类
 *
 * @author
 *
 */
@Table(name = "tb_resource")
public class TbResource extends ActiveRecord<TbResource> {
	/**资源编码*/
	@Id
	private String code;
	/**资源名称*/
	private String name;
	/**资源类型
            0-菜单
            1-按钮
            2-api接口*/
	private String type;
	/***/
	private String uri;
	/***/
	private String method;
	/**资源内容：由ｊｓｏｎ字符串构成*/
	private String content;
	/**父资源编码*/
	@Column(name ="parent_code")
	private String parentCode;
	/***/
	private String description;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**创建时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/**更新人用户id*/
	@Column(name = "upt_code")
	private String uptCode;
	/**更新时间*/
	@Column(name = "upt_time")
	private java.sql.Timestamp uptTime;
	/**
	 * 实例化
	 */
	public TbResource() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbResource(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("type") instanceof String) {
			this.setType((String) obj.getValue("type"));
		}
		if (obj.getValue("uri") instanceof String) {
			this.setUri((String) obj.getValue("uri"));
		}
		if (obj.getValue("method") instanceof String) {
			this.setMethod((String) obj.getValue("method"));
		}
		if (obj.getValue("content") instanceof String) {
			this.setContent((String) obj.getValue("content"));
		}
		if (obj.getValue("parentCode") instanceof String) {
			this.setParentCode((String) obj.getValue("parentCode"));
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
	public TbResource(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setType(params.get("type"));
		this.setUri(params.get("uri"));
		this.setMethod(params.get("method"));
		this.setContent(params.get("content"));
		this.setParentCode(params.get("parentCode"));
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
		if (this.getType() != null) {
			result.put("type",this.getType());
		}
		if (this.getUri() != null) {
			result.put("uri",this.getUri());
		}
		if (this.getMethod() != null) {
			result.put("method",this.getMethod());
		}
		if (this.getContent() != null) {
			result.put("content",this.getContent());
		}
		if (this.getParentCode() != null) {
			result.put("parentCode",this.getParentCode());
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
	 * 获取type
	 *
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置type
	 *
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取uri
	 *
	 * @return
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * 设置uri
	 *
	 * @param uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * 获取method
	 *
	 * @return
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置method
	 *
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 获取content
	 *
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置content
	 *
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取parentCode
	 *
	 * @return
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * 设置parentCode
	 *
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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
	public java.util.Date getUptTime() {
		return uptTime;
	}

	/**
	 * 设置uptTime
	 *
	 * @param uptTime
	 */
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime == null ? null : new java.sql.Timestamp(new Date().getTime());
	}

	@Override
	public String toString() {
		return "TbResource [code=" + code + " , name=" + name + " , type=" + type + " , uri=" + uri + " , method=" + method + " , content=" + content + " , parentCode=" + parentCode + " , description=" + description + " , crtCode=" + crtCode + " , crtTime=" + crtTime + " , uptCode=" + uptCode + " , uptTime=" + uptTime + "  ]";

	}


}
