package com.seadun.midi.equipment.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

/**
 * process实体类
 *
 * @author
 *
 */
@Table(name = "process")
public class Process  extends ActiveRecord<Process> {
	/***/
	@Id
	private String id; 
	/**0-0 场所-启用
0-1 场所-停用
0-2 场所-废弃

1-0 机柜-启用
1-1 机柜-停用
1-2 机柜-废弃

2-0  重要设备-启用
2-1  重要设备-停用
2-2  重要设备-离开
2-3  重要设备-废弃
2-4  重要设备-返回

3-0 串口服务器-启用
3-1 串口服务器-停用
3-2 串口服务器-废弃

4-0 报警流程*/
	@Column(name = "type")
	private String type; 
	/**申请人唯一标识*/
	@Column(name = "apply_code")
	private String applyCode; 
	/**申请人名称*/
	@Column(name = "apply_name")
	private String applyName; 
	/***/
	@Column(name = "apply_des")
	private String applyDes; 
	/**申请信息*/
	@Column(name = "process_info")
	private String processInfo; 
	/**审批人唯一标识*/
	@Column(name = "approve_code")
	private String approveCode; 
	/**审批人名称*/
	@Column(name = "approve_name")
	private String approveName; 
	/***/
	@Column(name = "approve_des")
	private String approveDes; 
	/**流程状态
0 -  待审批
1 -  已审批*/
	@Column(name = "process_status")
	private String processStatus; 
	/**审批状态
0 - 待审批
1 - 审批通过
2 - 审批不通过*/
	@Column(name = "approve_status")
	private String approveStatus; 
	/***/
	@Column(name = "apply_time")
	private Date applyTime; 
	/***/
	@Column(name = "approve_time")
	private Date approveTime; 
	/**场所编码
机柜编码
重要设备编码
串口服务器编码
报警ID*/
	@Column(name = "code")
	private String code; 
	/**场所名称
机柜名称
串口服务器名称
重要设备名称*/
	@Column(name = "name")
	private String name; 
	/**
	 * 实例化
	 */
	public Process() {
		super();
	}
	/**
	 * 实例化
	 * 
	 * @param obj
	 */
	public Process(JsonObject obj) {
		this();
		if (obj.getValue("id") instanceof String) {
			this.setId((String) obj.getValue("id"));
		}
		if (obj.getValue("type") instanceof String) {
			this.setType((String) obj.getValue("type"));
		}
		if (obj.getValue("applyCode") instanceof String) {
			this.setApplyCode((String) obj.getValue("applyCode"));
		}
		if (obj.getValue("applyName") instanceof String) {
			this.setApplyName((String) obj.getValue("applyName"));
		}
		if (obj.getValue("applyDes") instanceof String) {
			this.setApplyDes((String) obj.getValue("applyDes"));
		}
		if (obj.getValue("processInfo") instanceof String) {
			this.setProcessInfo((String) obj.getValue("processInfo"));
		}
		if (obj.getValue("approveCode") instanceof String) {
			this.setApproveCode((String) obj.getValue("approveCode"));
		}
		if (obj.getValue("approveName") instanceof String) {
			this.setApproveName((String) obj.getValue("approveName"));
		}
		if (obj.getValue("approveDes") instanceof String) {
			this.setApproveDes((String) obj.getValue("approveDes"));
		}
		if (obj.getValue("processStatus") instanceof String) {
			this.setProcessStatus((String) obj.getValue("processStatus"));
		}
		if (obj.getValue("approveStatus") instanceof String) {
			this.setApproveStatus((String) obj.getValue("approveStatus"));
		}
		if (obj.getValue("code") instanceof String) {
			this.setCode((String) obj.getValue("code"));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
	}
	/**
	 * 实例化
	 * 
	 * @param params
	 */
	public Process(MultiMap params) {
		this();
		this.setId(params.get("id"));
		this.setType(params.get("type"));
		this.setApplyCode(params.get("applyCode"));
		this.setApplyName(params.get("applyName"));
		this.setApplyDes(params.get("applyDes"));
		this.setProcessInfo(params.get("processInfo"));
		this.setApproveCode(params.get("approveCode"));
		this.setApproveName(params.get("approveName"));
		this.setApproveDes(params.get("approveDes"));
		this.setProcessStatus(params.get("processStatus"));
		this.setApproveStatus(params.get("approveStatus"));
		this.setCode(params.get("code"));
		this.setName(params.get("name"));
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
		if (this.getType() != null) {
			result.put("type",this.getType());
		}
		if (this.getApplyCode() != null) {
			result.put("applyCode",this.getApplyCode());
		}
		if (this.getApplyName() != null) {
			result.put("applyName",this.getApplyName());
		}
		if (this.getApplyDes() != null) {
			result.put("applyDes",this.getApplyDes());
		}
		if (this.getProcessInfo() != null) {
			result.put("processInfo",this.getProcessInfo());
		}
		if (this.getApproveCode() != null) {
			result.put("approveCode",this.getApproveCode());
		}
		if (this.getApproveName() != null) {
			result.put("approveName",this.getApproveName());
		}
		if (this.getApproveDes() != null) {
			result.put("approveDes",this.getApproveDes());
		}
		if (this.getProcessStatus() != null) {
			result.put("processStatus",this.getProcessStatus());
		}
		if (this.getApproveStatus() != null) {
			result.put("approveStatus",this.getApproveStatus());
		}
		if (this.getApplyTime() != null) {
			result.put("applyTime",this.getApplyTime());
		}
		if (this.getApproveTime() != null) {
			result.put("approveTime",this.getApproveTime());
		}
		if (this.getCode() != null) {
			result.put("code",this.getCode());
		}
		if (this.getName() != null) {
			result.put("name",this.getName());
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
	 * 获取applyCode
	 * 
	 * @return
	 */
	public String getApplyCode() {
		return applyCode;
	}

	/**
	 * 设置applyCode
	 * 
	 * @param applyCode
	 */
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	
	/**
	 * 获取applyName
	 * 
	 * @return
	 */
	public String getApplyName() {
		return applyName;
	}

	/**
	 * 设置applyName
	 * 
	 * @param applyName
	 */
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	/**
	 * 获取applyDes
	 * 
	 * @return
	 */
	public String getApplyDes() {
		return applyDes;
	}

	/**
	 * 设置applyDes
	 * 
	 * @param applyDes
	 */
	public void setApplyDes(String applyDes) {
		this.applyDes = applyDes;
	}
	
	/**
	 * 获取processInfo
	 * 
	 * @return
	 */
	public String getProcessInfo() {
		return processInfo;
	}

	/**
	 * 设置processInfo
	 * 
	 * @param processInfo
	 */
	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}
	
	/**
	 * 获取approveCode
	 * 
	 * @return
	 */
	public String getApproveCode() {
		return approveCode;
	}

	/**
	 * 设置approveCode
	 * 
	 * @param approveCode
	 */
	public void setApproveCode(String approveCode) {
		this.approveCode = approveCode;
	}
	
	/**
	 * 获取approveName
	 * 
	 * @return
	 */
	public String getApproveName() {
		return approveName;
	}

	/**
	 * 设置approveName
	 * 
	 * @param approveName
	 */
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	
	/**
	 * 获取approveDes
	 * 
	 * @return
	 */
	public String getApproveDes() {
		return approveDes;
	}

	/**
	 * 设置approveDes
	 * 
	 * @param approveDes
	 */
	public void setApproveDes(String approveDes) {
		this.approveDes = approveDes;
	}
	
	/**
	 * 获取processStatus
	 * 
	 * @return
	 */
	public String getProcessStatus() {
		return processStatus;
	}

	/**
	 * 设置processStatus
	 * 
	 * @param processStatus
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	/**
	 * 获取approveStatus
	 * 
	 * @return
	 */
	public String getApproveStatus() {
		return approveStatus;
	}

	/**
	 * 设置approveStatus
	 * 
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	/**
	 * 获取applyTime
	 * 
	 * @return
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * 设置applyTime
	 * 
	 * @param applyTime
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	/**
	 * 获取approveTime
	 * 
	 * @return
	 */
	public Date getApproveTime() {
		return approveTime;
	}

	/**
	 * 设置approveTime
	 * 
	 * @param approveTime
	 */
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
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

	@Override
	public String toString() {
		return "Process [id=" + id + " , type=" + type + " , applyCode=" + applyCode + " , applyName=" + applyName + " , applyDes=" + applyDes + " , processInfo=" + processInfo + " , approveCode=" + approveCode + " , approveName=" + approveName + " , approveDes=" + approveDes + " , processStatus=" + processStatus + " , approveStatus=" + approveStatus + " , applyTime=" + applyTime + " , approveTime=" + approveTime + " , code=" + code + " , name=" + name + "  ]";
	
	}
	
	
}