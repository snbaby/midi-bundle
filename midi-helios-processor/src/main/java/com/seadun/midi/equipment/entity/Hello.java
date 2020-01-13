/**
 *
 */
package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jsqlbox.ActiveEntity;

/**
 * @author Administrator Paul Wang
 * @version 创建时间：2019年4月6日 下午5:33:36
 * @email 12900985@qq.com
 * @company Seadun
 */
public class Hello implements ActiveEntity<Hello> {


//	@ShardTable({ "MOD", "4" })
//    @Snowflake
    @Id
    private Long id;


//	@ShardDatabase({ "RANGE", "4" })
	@Id
	private Long databaseId;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}



}
