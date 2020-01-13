package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.Date;

/**
 * port实体类
 *
 * @author
 *
 */
@Table(name = "port")
public class Port extends ActiveRecord<Port> {

	@Id
	private String code;

	@Column(name="crt_time")
	private Date crtTime;

	@Column(name="crt_user")
	private String crtUser;

	@Column(name="port")
	private Integer port;

	@Column(name="port_name")
	private String portName;

	private String status;

	@Column(name="upt_time")
	private Date uptTime;

	@Column(name="upt_user")
	private String uptUser;

	@Column(name="detect_code")
	private String detectCode;
	/**
	 * 实例化
	 */
	public Port() {
		super();
	}
	/**
	 * 实例化
	 *
	 * @param obj
	 */
	public Port(JsonObject obj) {
		this();
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("portName") instanceof String) {
			this.setPortName((String) obj.getValue("portName"));
		}
		if (obj.getValue("port") instanceof Number) {
			this.setPort(((Number) obj.getValue("port")).intValue());
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("detectCode") instanceof String) {
			this.setDetectCode((String) obj.getValue("detectCode"));
		}
		if (obj.getValue("uptUser") instanceof String) {
			this.setUptUser((String) obj.getValue("uptUser"));
		}
		if (obj.getValue("crtUser") instanceof String) {
			this.setCrtUser((String) obj.getValue("crtUser"));
		}
	}
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public Port(MultiMap params) {
		this();
		this.setCode(params.get("code"));
		this.setPortName(params.get("portName"));
		this.setPort(new Integer(params.get("port")));
		this.setStatus(params.get("status"));
		this.setDetectCode(params.get("detectCode"));
		this.setUptUser(params.get("uptUser"));
		this.setCrtUser(params.get("crtUser"));
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
		if (this.getPortName() != null) {
			result.put("portName",this.getPortName());
		}
		if (this.getPort() != null) {
			result.put("port",this.getPort());
		}
		if (this.getStatus() != null) {
			result.put("status",this.getStatus());
		}
		if (this.getDetectCode() != null) {
			result.put("detectCode",this.getDetectCode());
		}
		if (this.getUptUser() != null) {
			result.put("uptUser",this.getUptUser());
		}
		if (this.getUptTime() != null) {
			result.put("uptTime",this.getUptTime());
		}
		if (this.getCrtUser() != null) {
			result.put("crtUser",this.getCrtUser());
		}
		if (this.getCrtTime() != null) {
			result.put("crtTime",this.getCrtTime());
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
	 * 获取portName
	 *
	 * @return
	 */
	public String getPortName() {
		return portName;
	}

	/**
	 * 设置portName
	 *
	 * @param portName
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}

	/**
	 * 获取port
	 *
	 * @return
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * 设置port
	 *
	 * @param port
	 */
	public void setPort(Integer port) {
		this.port = port;
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
	 * 获取detectCode
	 *
	 * @return
	 */
	public String getDetectCode() {
		return detectCode;
	}

	/**
	 * 设置detectCode
	 *
	 * @param detectCode
	 */
	public void setDetectCode(String detectCode) {
		this.detectCode = detectCode;
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

	@Override
	public String toString() {
		return "Port [code=" + code + " , portName=" + portName + " , port=" + port + " , status=" + status + " , detectCode=" + detectCode + " , uptUser=" + uptUser + " , uptTime=" + uptTime + " , crtUser=" + crtUser + " , crtTime=" + crtTime + "  ]";

	}


}
