/**
 *
 */
package com.seadun.midi.equipment.entity;

import com.github.drinkjava2.jdialects.annotation.jdia.Snowflake;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveEntity;

/**
 * @author Administrator Paul Wang
 * @version 创建时间：2019年4月6日 下午5:33:36
 * @email 12900985@qq.com
 * @company Seadun
 */

@Table(name="t_user")
public class User implements ActiveEntity<User> {


//	@ShardTable({ "MOD", "4" })
    @Snowflake
    @Id
    private Long id;



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

	public void setId(Long id) {
		this.id = id;
	}





}
