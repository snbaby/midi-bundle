package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Objects;

/**
 * dic_equipment_type实体类
 *
 * @author
 *
 */
@Table(name = "dic_equipment_type")
public class DicEquipmentType extends ActiveRecord<DicEquipmentType> {
	/***/
	@Id
	@Column(name="type_code")
	private String typeCode;
	/***/
	@Column(name="type_name")
	private String typeName;
	/***/
	@Column(name="crt_user")
	private String crtUser;
	/***/
	@Column(name="crt_time")
	private java.util.Date crtTime;
	/***/
	@Column(name="upt_user")
	private String uptUser;
	/***/
	@Column(name="upt_time")
	private java.util.Date uptTime;
	/**
	 * 实例化
	 */
	public DicEquipmentType() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public DicEquipmentType(JsonObject obj) {
		this();
		if (obj.getValue("typeCode") instanceof String) {
			this.setTypeCode((String) obj.getValue("typeCode"));
		}
		if (obj.getValue("typeName") instanceof String) {
			this.setTypeName((String) obj.getValue("typeName"));
		}
		if (obj.getValue("crtUser") instanceof String) {
			this.setCrtUser((String) obj.getValue("crtUser"));
		}
		if (obj.getValue("uptUser") instanceof String) {
			this.setUptUser((String) obj.getValue("uptUser"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public DicEquipmentType(MultiMap params) {
		this();
		this.setTypeCode(params.get("typeCode"));
		this.setTypeName(params.get("typeName"));
		this.setCrtUser(params.get("crtUser"));
		this.setUptUser(params.get("uptUser"));
	}
	/**
	 * 将当前对象转换为JsonObject
	 *
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		if (this.getTypeCode() != null) {
			result.put("typeCode",this.getTypeCode());
		}
		if (this.getTypeName() != null) {
			result.put("typeName",this.getTypeName());
		}
		if (this.getCrtUser() != null) {
			result.put("crtUser",this.getCrtUser());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getUptUser() != null) {
			result.put("uptUser",this.getUptUser());
		}
		if (this.getUptTime() != null) {
			result.put("uptTime",this.getUptTime());
		}
		return result;
	}


	/**
	 * 获取typeCode
	 *
	 * @return
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * 设置typeCode
	 *
	 * @param typeCode
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * 获取typeName
	 *
	 * @return
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 设置typeName
	 *
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	/**
	 * 获取uptUser
	 *
	 * @return
	 */
	public String getUptUser() {
		return uptUser;
	}

	/**
	 * 设置uptUser
	 *
	 * @param uptUser
	 */
	public void setUptUser(String uptUser) {
		this.uptUser = uptUser;
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
	public void setUptTime(java.util.Date uptTime) {
		this.uptTime = uptTime;
	}

	@Override
	public String toString() {
		return "DicEquipmentType [typeCode=" + typeCode + " , typeName=" + typeName + " , crtUser=" + crtUser + " , crtTime=" + crtTime + " , uptUser=" + uptUser + " , uptTime=" + uptTime + "  ]";

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DicEquipmentType that = (DicEquipmentType) o;
		return typeCode.equals(that.typeCode) &&
				typeName.equals(that.typeName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(typeCode, typeName);
	}
}
