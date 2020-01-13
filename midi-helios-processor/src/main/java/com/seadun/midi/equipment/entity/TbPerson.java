package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_person实体类
 *
 * @author
 *
 */
@Table(name="tb_person")
public class TbPerson extends ActiveRecord<TbPerson> {
	/**人员唯一标识*/
	@Id
	private String code;
	/**姓名*/
	private String name;
	/**0-女
	 1-男
	 2-其他*/
	private String sex;
	/**0-非涉密人员
	 1- 一般涉密人员
	 2- 重要涉密人员
	 3- 核心涉密人员*/
	@Column(name="security_classification")
	private String securityClassification;
	/**0- 注销 1- 正常*/
	private String status;
	/**创建人用户id*/
	@Column(name="crt_code")
	private String crtCode;
	/**创建时间*/
	@Column(name="crt_time")
	private java.sql.Timestamp crtTime;
	/**更新人用户id*/
	@Column(name="upt_code")
	private String uptCode;
	/**更新时间*/
	@Column(name="upt_time")
	private java.sql.Timestamp uptTime;
	/***/
	private String email;
	/***/
	private String mobile;
	/***/
	private String telephone;
	/**地址*/
	private String address;
	/**
	 * 实例化
	 */
	public TbPerson() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbPerson(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("sex") instanceof String) {
			this.setSex((String) obj.getValue("sex"));
		}
		if (obj.getValue("securityClassification") instanceof String) {
			this.setSecurityClassification((String) obj.getValue("securityClassification"));
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("crtCode") instanceof String) {
			this.setCrtCode((String) obj.getValue("crtCode"));
		}
		if (obj.getValue("uptCode") instanceof String) {
			this.setUptCode((String) obj.getValue("uptCode"));
		}
		if (obj.getValue("email") instanceof String) {
			this.setEmail((String) obj.getValue("email"));
		}
		if (obj.getValue("mobile") instanceof String) {
			this.setMobile((String) obj.getValue("mobile"));
		}
		if (obj.getValue("telephone") instanceof String) {
			this.setTelephone((String) obj.getValue("telephone"));
		}
		if (obj.getValue("address") instanceof String) {
			this.setAddress((String) obj.getValue("address"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public TbPerson(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
		this.setSex(params.get("sex"));
		this.setSecurityClassification(params.get("securityClassification"));
		this.setStatus(params.get("status"));
		this.setCrtCode(params.get("crtCode"));
		this.setUptCode(params.get("uptCode"));
		this.setEmail(params.get("email"));
		this.setMobile(params.get("mobile"));
		this.setTelephone(params.get("telephone"));
		this.setAddress(params.get("address"));
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
		if (this.getSex() != null) {
			result.put("sex",this.getSex());
		}
		if (this.getSecurityClassification() != null) {
			result.put("securityClassification",this.getSecurityClassification());
		}
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
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
		if (this.getEmail() != null) {
			result.put("email",this.getEmail());
		}
		if (this.getMobile() != null) {
			result.put("mobile",this.getMobile());
		}
		if (this.getTelephone() != null) {
			result.put("telephone",this.getTelephone());
		}
		if (this.getAddress() != null) {
			result.put("address",this.getAddress());
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
	 * 获取sex
	 *
	 * @return
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置sex
	 *
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 获取securityClassification
	 *
	 * @return
	 */
	public String getSecurityClassification() {
		return securityClassification;
	}

	/**
	 * 设置securityClassification
	 *
	 * @param securityClassification
	 */
	public void setSecurityClassification(String securityClassification) {
		this.securityClassification = securityClassification;
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

	/**
	 * 获取email
	 *
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置email
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取mobile
	 *
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置mobile
	 *
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取telephone
	 *
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置telephone
	 *
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 获取address
	 *
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置address
	 *
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "TbPerson [code=" + code + " , name=" + name + " , sex=" + sex + " , securityClassification=" + securityClassification + " , status=" + status + " , crtCode=" + crtCode + " , crtTime=" + crtTime + " , uptCode=" + uptCode + " , uptTime=" + uptTime + " , email=" + email + " , mobile=" + mobile + " , telephone=" + telephone + " , address=" + address + "  ]";

	}


}
