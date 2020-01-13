package com.seadun.midi.aas.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;


/**
 * The persistent class for the tb_org database table.
 *
 */
//@Entity
@Table(name="tb_org")
public class TbOrg extends ActiveRecord<TbOrg> {

	@Id
	private String code;

	@Column(name="crt_code")
	private String crtCode;

	@Column(name="crt_time")
	private java.sql.Timestamp  crtTime;

	private String name;

	@Column(name="parent_code")
	private String parentCode;

	@Column(name="parent_name")
	private String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name="sort")
	private BigDecimal sort;

	private String type;

	@Column(name="upt_code")
	private String uptCode;

	@Column(name="upt_time")
	private java.sql.Timestamp  uptTime;

	public TbOrg() {
		super();
	}

	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbOrg(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("parentCode") instanceof String) {
			this.setParentCode((String) obj.getValue("parentCode"));
		}
		if (obj.getValue("parentName") instanceof String) {
			this.setParentCode((String) obj.getValue("parentName"));
		}
		if (obj.getValue("sort") instanceof Integer) {
			this.setSort(obj.getInteger("sort"));
		}
		if (obj.getValue("type") instanceof String) {
			this.setType((String) obj.getValue("type"));
		}
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
		if (this.getParentCode() != null) {
			result.put("parentCode",this.getParentCode());
		}
		if (this.getParentCode() != null) {
			result.put("parentName",this.getParentName());
		}
		if (this.getType() != null) {
			result.put("type",this.getType());
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
		if(this.getSort() != null) {
			result.put("sort",this.getSort());
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



	public BigDecimal getSort() {
		return sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public void setSort(Integer sort) {
		this.sort = new BigDecimal(sort);
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
		return "TbOrg [code=" + code + " , name=" + name + " , parentCode=" + parentCode + " , type=" + type + " , crtCode=" + crtCode + " , crtTime=" + crtTime + " , uptCode=" + uptCode + " , uptTime=" + uptTime + "  ]";

	}
}
