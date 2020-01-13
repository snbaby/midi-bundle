package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * tb_log实体类
 *
 * @author
 *
 */
@Table(name = "tb_db_log")
public class TbLogDB extends ActiveRecord<TbLogDB>{
	/***/
	@Id
	private String id;
	/**用户编码*/
	@Column(name = "crt_code")
	private String crtCode;
	/**用户名称*/
	@Column(name = "crt_name")
	private String crtName;
	/**说明*/
	@Column(name = "log_content")
	private String logContent;
	/**ip*/
	@Column(name = "client_ip")
	private String clientIp;
	/**操作时间*/
	@Column(name = "crt_time")
	private java.sql.Timestamp crtTime;
	/**数据表*/
	@Column(name = "db_name")
	private String dbName;
	/**操作类型 .删除 .查询 .修改*/
	@Column(name = "operation_type")
	private String operationType;
	/**
	 * 实例化
	 */
	public TbLogDB() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public TbLogDB(JsonObject obj) {
		this();
		if (obj.getValue("id") instanceof String) {
			this.setId((String) obj.getValue("id"));
		}
		if (obj.getValue("crtCode") instanceof String) {
			this.setCrtCode((String) obj.getValue("crtCode"));
		}
		if (obj.getValue("crtName") instanceof String) {
			this.setCrtName((String) obj.getValue("crtName"));
		}
		if (obj.getValue("logContent") instanceof String) {
			this.setLogContent((String) obj.getValue("logContent"));
		}
		if (obj.getValue("clientIp") instanceof String) {
			this.setClientIp((String) obj.getValue("clientIp"));
		}
		if (obj.getValue("dbName") instanceof String) {
			this.setDbName((String) obj.getValue("dbName"));
		}
		if (obj.getValue("operationType") instanceof String) {
			this.setOperationType((String) obj.getValue("operationType"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public TbLogDB(MultiMap params) {
		this();
		this.setId(params.get("id"));
		this.setCrtCode(params.get("crtCode"));
		this.setCrtName(params.get("crtName"));
		this.setLogContent(params.get("logContent"));
		this.setClientIp(params.get("clientIp"));
		this.setDbName(params.get("dbName"));
		this.setOperationType(params.get("operationType"));
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
		if (this.getCrtCode() != null) {
			result.put("crtCode",this.getCrtCode());
		}
		if (this.getCrtName() != null) {
			result.put("crtName",this.getCrtName());
		}
		if (this.getLogContent() != null) {
			result.put("logContent",this.getLogContent());
		}
		if (this.getClientIp() != null) {
			result.put("clientIp",this.getClientIp());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
		}
		if (this.getDbName() != null) {
			result.put("dbName",this.getDbName());
		}
		if (this.getOperationType() != null) {
			result.put("operationType",this.getOperationType());
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
	 * 获取dbName
	 *
	 * @return
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * 设置dbName
	 *
	 * @param dbName
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * 获取operationType
	 *
	 * @return
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * 设置operationType
	 *
	 * @param operationType
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Override
	public String toString() {
		return "TbDbLog [id=" + id + " , crtCode=" + crtCode + " , crtName=" + crtName + " , logContent=" + logContent + " , clientIp=" + clientIp + " , crtTime=" + crtTime + " , dbName=" + dbName + " , operationType=" + operationType + "  ]";

	}



}
