package com.seadun.midi.equipment.entity;

import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import io.vertx.core.json.JsonObject;

/**
 * tb_user实体类
 *
 * @author
 *
 */
@Table(name = "tb_process_user")
public class TbProcessUser  extends ActiveRecord<TbProcessUser>  {
	/**用户id*/
	@Id
	@Column(name = "code")
	private String code;
	/**账户名称*/
	@Column(name = "name")
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
