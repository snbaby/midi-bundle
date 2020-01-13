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
@Table(name = "tb_login_log")
public class TbLogLogin extends ActiveRecord<TbLogLogin>{
	/***/
	@Id
	private String id;
	
	@Column(name = "app_code")
	private String appCode;
	/***/
	@Column(name = "log_content")
	private String logContent;
	/***/
	@Column(name = "type")
	private String type;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getCrtCode() {
		return crtCode;
	}
	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}
	public String getCrtName() {
		return crtName;
	}
	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}
	public java.sql.Timestamp getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(java.sql.Timestamp crtTime) {
		this.crtTime = crtTime;
	}

}
