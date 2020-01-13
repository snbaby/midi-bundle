package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * tb_log实体类
 *
 * @author
 *
 */
@Table(name = "tb_model_log")
public class TbLogModel extends ActiveRecord<TbLogModel>{
	/***/
	@Id
	private String id;
	/**模块编码*/
	@Column(name = "model_code")
	private String modelCode;
	/**访问模块名*/
	@Column(name = "model_name")
	private String modelName;
	/**日志说明*/
	@Column(name = "log_content")
	private String logContent;
	/**ip*/
	@Column(name = "client_ip")
	private String clientIp;
	/**用户名称*/
	@Column(name = "crt_name")
	private String crtName;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**操作时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/***/
	@Column(name = "app_code")
	private String appCode;
	/**
	 * 实例化
	 */
	public TbLogModel() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbLogModel(JsonObject obj) {
		this();
		if (obj.getValue("id") instanceof String) {
			this.setId((String) obj.getValue("id"));
		}
		if (obj.getValue("modelCode") instanceof String) {
			this.setModelCode((String) obj.getValue("modelCode"));
		}
		if (obj.getValue("modelName") instanceof String) {
			this.setModelName((String) obj.getValue("modelName"));
		}
		if (obj.getValue("logContent") instanceof String) {
			this.setLogContent((String) obj.getValue("logContent"));
		}
		if (obj.getValue("clientIp") instanceof String) {
			this.setClientIp((String) obj.getValue("clientIp"));
		}
		if (obj.getValue("crtName") instanceof String) {
			this.setCrtName((String) obj.getValue("crtName"));
		}
		if (obj.getValue("crtCode") instanceof String) {
			this.setCrtCode((String) obj.getValue("crtCode"));
		}
		if (obj.getValue("appCode") instanceof String) {
			this.setAppCode((String) obj.getValue("appCode"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public TbLogModel(MultiMap params) {
		this();
		this.setId(params.get("id"));
		this.setModelCode(params.get("modelCode"));
		this.setModelName(params.get("modelName"));
		this.setLogContent(params.get("logContent"));
		this.setClientIp(params.get("clientIp"));
		this.setCrtName(params.get("crtName"));
		this.setCrtCode(params.get("crtCode"));
		this.setAppCode(params.get("appCode"));
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
		if (this.getModelCode() != null) {
			result.put("modelCode",this.getModelCode());
		}
		if (this.getModelName() != null) {
			result.put("modelName",this.getModelName());
		}
		if (this.getLogContent() != null) {
			result.put("logContent",this.getLogContent());
		}
		if (this.getClientIp() != null) {
			result.put("clientIp",this.getClientIp());
		}
		if (this.getCrtName() != null) {
			result.put("crtName",this.getCrtName());
		}
		if (this.getCrtCode() != null) {
			result.put("crtCode",this.getCrtCode());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getAppCode() != null) {
			result.put("appCode",this.getAppCode());
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
	public java.util.Date getCrtTime() {
		return crtTime;
	}

	/**
	 * 设置crtTime
	 *
	 */
	public void setCrtTime(Date string) {
		this.crtTime = string == null ? null : new java.sql.Timestamp(string.getTime());
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

	@Override
	public String toString() {
		return "TbModelLog [id=" + id + " , modelCode=" + modelCode + " , modelName=" + modelName + " , logContent=" + logContent + " , clientIp=" + clientIp + " , crtName=" + crtName + " , crtCode=" + crtCode + " , crtTime=" + crtTime + " , appCode=" + appCode + "  ]";

	}


}
