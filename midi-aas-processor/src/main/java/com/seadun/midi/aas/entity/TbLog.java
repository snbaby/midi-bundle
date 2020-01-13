package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;

/**
 * tb_log实体类
 *
 * @author
 *
 */
@Table(name = "tb_log")
public class TbLog extends ActiveRecord<TbLog>{
	/***/
	@Id
	private String id;
	/**用户id*/
	@Column(name = "user_code")
	private String userCode;
	/***/
	@Column(name = "resource_code")
	private String resourceCode;
	/***/
	@Column(name = "app_code")
	private String appCode;
	/***/
	@Column(name = "log_content")
	private String logContent;
	/***/
	@Column(name = "client_ip")
	private String clientIp;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**创建人账户名称*/
	@Column(name = "crt_name")
	private String crtName;
	/**创建时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/***/
	@Column(name = "model_code")
	private String modelCode;
	/***/
	@Column(name = "model_name")
	private String modelName;
	/***/
	@Column(name = "login_type")
	private String loginType;
	/**
	 * 实例化
	 */
	public TbLog() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbLog(JsonObject obj) {
		this();

		if (obj.getValue("userCode") instanceof String) {
			this.setUserCode((String) obj.getValue("userCode"));
		}
		if (obj.getValue("resourceCode") instanceof String) {
			this.setResourceCode((String) obj.getValue("resourceCode"));
		}
		if (obj.getValue("appCode") instanceof String) {
			this.setAppCode((String) obj.getValue("appCode"));
		}

		if (obj.getValue("clientIp") instanceof String) {
			this.setClientIp((String) obj.getValue("clientIp"));
		}

		if (obj.getValue("modelCode") instanceof String) {
			this.setModelCode((String) obj.getValue("modelCode"));
		}

		if (obj.getValue("modelName") instanceof String) {
			this.setModelName((String) obj.getValue("modelName"));
		}

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
		if (this.getUserCode() != null) {
			result.put("userCode",this.getUserCode());
		}
		if (this.getResourceCode() != null) {
			result.put("resourceCode",this.getResourceCode());
		}
		if (this.getAppCode() != null) {
			result.put("appCode",this.getAppCode());
		}
		if (this.getLogContent() != null) {
			result.put("logContent",this.getLogContent());
		}
		if (this.getClientIp() != null) {
			result.put("clientIp",this.getClientIp());
		}
		if (this.getCrtCode() != null) {
			result.put("crtCode",this.getCrtCode());
		}
		if (this.getCrtName() != null) {
			result.put("crtName",this.getCrtName());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getModelCode() != null) {
			result.put("modelCode",this.getModelCode());
		}
		if (this.getModelName() != null) {
			result.put("modelName",this.getModelName());
		}
		if (this.getLoginType() != null) {
			result.put("loginType",this.getLoginType());
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
	 * 获取logContent
	 *
	 * @return
	 */
	public String getLogContent() {
		return logContent;
	}

	/**
	 * 设置logContent
	 *
	 * @param logContent
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	/**
	 * 获取clientIp
	 *
	 * @return
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * 设置clientIp
	 *
	 * @param clientIp
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
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
	 * 获取crtName
	 *
	 * @return
	 */
	public String getCrtName() {
		return crtName;
	}

	/**
	 * 设置crtName
	 *
	 * @param crtName
	 */
	public void setCrtName(String crtName) {
		this.crtName = crtName;
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
	 * @param string
	 */
	public void setCrtTime(Date string) {
		this.crtTime = string == null ? null : new java.sql.Timestamp(string.getTime());
	}

	/**
	 * 获取modelCode
	 *
	 * @return
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * 设置modelCode
	 *
	 * @param modelCode
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * 获取modelName
	 *
	 * @return
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * 设置modelName
	 *
	 * @param modelName
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 获取loginType
	 *
	 * @return
	 */
	public String getLoginType() {
		return loginType;
	}

	/**
	 * 设置loginType
	 *
	 * @param loginType
	 */
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "TbLog [id=" + id + " , userCode=" + userCode + " , resourceCode=" + resourceCode + " , appCode=" + appCode + " , logContent=" + logContent + " , clientIp=" + clientIp + " , crtCode=" + crtCode + " , crtName=" + crtName + " , crtTime=" + crtTime + " , modelCode=" + modelCode + " , modelName=" + modelName + " , loginType=" + loginType + "  ]";

	}


}
