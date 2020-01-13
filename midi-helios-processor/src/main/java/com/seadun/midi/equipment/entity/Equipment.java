package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * equipment实体类
 *
 * @author
 *
 */
@Table(name = "equipment")
public class Equipment extends ActiveRecord<Equipment> {
	/**设备编码*/
	@Id
	private String code;
	/**设备名称*/
	private String name;
	/**设备类型*/
	@Column(name = "type_code")
	private String typeCode;
	@Column(name = "area_code")
	private String areaCode;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**设备状态
            1-正常
            2-停用中
            3-离开
            4-报警
            5-废弃*/
	private String status;
	/***/
	@Column(name="duty_user_code")
	private String dutyUserCode;
	/***/
	@Column(name="duty_org_code")
	private String dutyOrgCode;
	/**密级*/
	@Column(name="secret_code")
	private String secretCode;
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
	public Equipment() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public Equipment(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("typeCode") instanceof String) {
			this.setTypeCode((String) obj.getValue("typeCode"));
		}
		if (obj.getValue("areaCode") instanceof String) {
			this.setAreaCode((String) obj.getValue("areaCode"));
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("dutyUserCode") instanceof String) {
			this.setDutyUserCode((String) obj.getValue("dutyUserCode"));
		}
		if (obj.getValue("dutyOrgCode") instanceof String) {
			this.setDutyOrgCode((String) obj.getValue("dutyOrgCode"));
		}
		if (obj.getValue("secretCode") instanceof String) {
			this.setSecretCode((String) obj.getValue("secretCode"));
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
	public Equipment(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setTypeCode(params.get("typeCode"));
		this.setAreaCode(params.get("areaCode"));
		this.setStatus(params.get("status"));
		this.setDutyUserCode(params.get("dutyUserCode"));
		this.setDutyOrgCode(params.get("dutyOrgCode"));
		this.setSecretCode(params.get("secretCode"));
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
		if (this.getCode() != null) {
			result.put("code",this.getCode());
		}
		if (this.getName() != null) {
			result.put("name",this.getName());
		}
		if (this.getTypeCode() != null) {
			result.put("typeCode",this.getTypeCode());
		}
		if (this.getAreaCode() != null) {
			result.put("areaCode",this.getAreaCode());
		}
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
		}
		if (this.getDutyUserCode() != null) {
			result.put("dutyUserCode",this.getDutyUserCode());
		}
		if (this.getDutyOrgCode() != null) {
			result.put("dutyOrgCode",this.getDutyOrgCode());
		}
		if (this.getSecretCode() != null) {
			result.put("secretCode",this.getSecretCode());
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
	 * 获取status
	 *
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置status
	 *
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取dutyUserCode
	 *
	 * @return
	 */
	public String getDutyUserCode() {
		return dutyUserCode;
	}

	/**
	 * 设置dutyUserCode
	 *
	 * @param dutyUserCode
	 */
	public void setDutyUserCode(String dutyUserCode) {
		this.dutyUserCode = dutyUserCode;
	}

	/**
	 * 获取dutyOrgCode
	 *
	 * @return
	 */
	public String getDutyOrgCode() {
		return dutyOrgCode;
	}

	/**
	 * 设置dutyOrgCode
	 *
	 * @param dutyOrgCode
	 */
	public void setDutyOrgCode(String dutyOrgCode) {
		this.dutyOrgCode = dutyOrgCode;
	}


	/**
	 * 获取secretCode
	 *
	 * @return
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * 设置secretCode
	 *
	 * @param secretCode
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
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
		return "Equipment [code=" + code + " , name=" + name + " , typeCode=" + typeCode + " , status=" + status + " , dutyUserCode=" + dutyUserCode + " , dutyOrgCode=" + dutyOrgCode + " , secretCode=" + secretCode + " , crtUser=" + crtUser + " , crtTime=" + crtTime + " , uptUser=" + uptUser + " , uptTime=" + uptTime + "  ]";

	}


}
