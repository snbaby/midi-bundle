package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * area实体类
 *
 * @author
 *
 */
@Table(name = "area")
public class Area extends ActiveRecord<Area> {
	/**场所编码*/
	@Id
	private String code;
	/**场所名称*/
	private String name;
	/**1-正常
	 2-停用
	 3-废弃*/
	private String status;
	/***/
	@Column(name = "crt_user")
	private String crtUser;
	/**详细位置*/
	@Column(name = "detailed_location")
	private String detailedLocation;
	@Column(name = "crt_time")
	private java.util.Date crtTime;
	@Column(name = "upt_user")
	private String uptUser;
	@Column(name = "upt_time")
	private java.util.Date uptTime;
	/**职责人员编码*/
	@Column(name = "duty_user_code")
	private String dutyUserCode;
	/**职责部门编码*/
	@Column(name = "duty_org_code")
	private String dutyOrgCode;
	/**
	 * 实例化
	 */
	public Area() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public Area(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("detailedLocation") instanceof String) {
			this.setDetailedLocation((String) obj.getValue("detailedLocation"));
		}
		if (obj.getValue("crtUser") instanceof String) {
			this.setCrtUser((String) obj.getValue("crtUser"));
		}
		if (obj.getValue("uptUser") instanceof String) {
			this.setUptUser((String) obj.getValue("uptUser"));
		}
		if (obj.getValue("dutyUserCode") instanceof String) {
			this.setDutyUserCode((String) obj.getValue("dutyUserCode"));
		}
		if (obj.getValue("dutyOrgCode") instanceof String) {
			this.setDutyOrgCode((String) obj.getValue("dutyOrgCode"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public Area(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setStatus(params.get("status"));
		this.setDetailedLocation(params.get("detailedLocation"));
		this.setCrtUser(params.get("crtUser"));
		this.setUptUser(params.get("uptUser"));
		this.setDutyUserCode(params.get("dutyUserCode"));
		this.setDutyOrgCode(params.get("dutyOrgCode"));
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
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
		}
		if (this.getDetailedLocation() != null) {
			result.put("detailedLocation",this.getDetailedLocation());
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
		if (this.getDutyUserCode() != null) {
			result.put("dutyUserCode",this.getDutyUserCode());
		}
		if (this.getDutyOrgCode() != null) {
			result.put("dutyOrgCode",this.getDutyOrgCode());
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
	 * 获取detailedLocation
	 *
	 * @return
	 */
	public String getDetailedLocation() {
		return detailedLocation;
	}

	/**
	 * 设置detailedLocation
	 *
	 * @param detailedLocation
	 */
	public void setDetailedLocation(String detailedLocation) {
		this.detailedLocation = detailedLocation;
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
	public Date getCrtTime() {
		return crtTime;
	}

	/**
	 * 设置crtTime
	 *
	 * @param crtTime
	 */
	public void setCrtTime(Date crtTime) {
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
	public Date getUptTime() {
		return uptTime;
	}

	/**
	 * 设置uptTime
	 *
	 * @param uptTime
	 */
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
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

	@Override
	public String toString() {
		return "Area [code=" + code + " , name=" + name + " , status=" + status + " , detailedLocation=" + detailedLocation + " , crtUser=" + crtUser + " , crtTime=" + crtTime + " , uptUser=" + uptUser + " , uptTime=" + uptTime + " , dutyUserCode=" + dutyUserCode + " , dutyOrgCode=" + dutyOrgCode + "  ]";

	}


}
