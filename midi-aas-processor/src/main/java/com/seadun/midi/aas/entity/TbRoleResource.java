package com.seadun.midi.aas.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

/**
 * tb_resource实体类
 *
 * @author
 *
 */
 //映射使用 勿删除
public class TbRoleResource extends ActiveRecord<TbRoleResource> {
	/**资源编码*/
	@Id
	@Column(name ="code")
	private String resourceCode;
	/**资源名称*/
	@Column(name ="name")
	private String resourceName;
	/**资源类型
            0-菜单
            1-按钮
            2-api接口*/
	private String type;
	/***/
	private String uri;
	/***/
	private String method;
	/**资源内容：由ｊｓｏｎ字符串构成*/
	private String content;
	/**父资源编码*/
	@Column(name ="parent_code")
	private String parentCode;
	private String roleCode;

	private String roleName;
	/***/
	private String description;
	/**创建人用户id*/
	@Column(name = "crt_code")
	private String crtCode;
	/**创建时间*/
	@Column(name = "crt_time")
	private Date crtTime;
	/**更新人用户id*/
	@Column(name = "upt_code")
	private String uptCode;
	/**更新时间*/
	@Column(name = "upt_time")
	private Date uptTime;
	/**
	 * 实例化
	 */
	/**
	 * 实例化
	 *
	 * @param params
	 */
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCrtCode() {
		return crtCode;
	}
	public void setCrtCode(String crtCode) {
		this.crtCode = crtCode;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public String getUptCode() {
		return uptCode;
	}
	public void setUptCode(String uptCode) {
		this.uptCode = uptCode;
	}
	public Date getUptTime() {
		return uptTime;
	}
	public void setUptTime(Date uptTime) {
		this.uptTime = uptTime;
	}


}
