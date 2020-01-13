package com.seadun.midi.aas.service;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.*;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.yml.Application;
import com.seadun.midi.aas.yml.Menu;
import io.vertx.ext.web.RoutingContext;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InitService {
	
	private final String INIT_ORG_CODE = "0";

	@TX
	public void init(RoutingContext routingContext) throws MidiException {
		initOrg();// 系统组织
		initRole();// 初始化角色
		initUser();// 初始化用户
		initMenu();// 初始化菜单
	}

	private void initRole() {
		Application.init.getRoles().forEach(item -> {
			TbRole role = new TbRole();
			role.setCode(item.getCode());
			role.setName(item.getName());
			role.setDescription(item.getDescription());

			role.setCrtCode("system");
			role.setCrtTime(new Date());
			role.setUptCode("system");
			role.setUptTime(new Date());
			role.insert();
		});
	}

	private void initOrg() {
		TbOrg tbOrg = new TbOrg();
		tbOrg.setCode(INIT_ORG_CODE);
		tbOrg.setName("系统组织");
		tbOrg.setParentCode("-1");
		tbOrg.setType("-1");
		tbOrg.setSort(0);
		tbOrg.setCrtCode("system");
		tbOrg.setCrtTime(new Date());
		tbOrg.setUptCode("system");
		tbOrg.setUptTime(new Date());
		tbOrg.insert(SqlOption.IGNORE_NULL);
	}

	private void initUser() {
		Application.init.getUsers().forEach(item -> {
			Date exTime = new Date();
			exTime.setYear(exTime.getYear()+5);
			TbUser user = new TbUser();
			user.setCode(item.getCode());
			user.setName(item.getNickName());
			// super@1234
			user.setPassword(item.getPassword());
			user.setPasswordSalt(item.getSalt());
			user.setStatus("0");// 正常
			user.setVersion("1");// 不晓得

			user.setCrtCode("system");
			user.setCrtTime(new Date());
			user.setUptCode("system");
			user.setUptTime(new Date());
			user.setExpireTime(exTime);
			user.insert();

			TbPerson superPerson = new TbPerson();
			superPerson.setCode(item.getCode());
			superPerson.setName(item.getName());
			superPerson.setSex("1");// 男
			superPerson.setSecurityClassification(item.getSecurityClassification());
			superPerson.setStatus("2");// 已开通

			superPerson.setCrtCode("system");
			superPerson.setCrtTime(new Date());
			superPerson.setUptCode("system");
			superPerson.setUptTime(new Date());
			superPerson.insert();
			
			TbOrgPersonRelation tbOrgPersonRelation = new TbOrgPersonRelation();
			tbOrgPersonRelation.setId(UUID.randomUUID().toString());
			tbOrgPersonRelation.setOrgCode(INIT_ORG_CODE);
			tbOrgPersonRelation.setPersonCode(item.getCode());
			tbOrgPersonRelation.setCrtCode("system");
			tbOrgPersonRelation.setCrtTime(new Date());
			tbOrgPersonRelation.insert(SqlOption.IGNORE_NULL);
			

			item.getRoles().forEach(role -> {
				TbRoleUserRelation superRelation = new TbRoleUserRelation();
				superRelation.setId(UUID.randomUUID().toString());
				superRelation.setRoleCode(role);
				superRelation.setUserCode(item.getCode());
				superRelation.setCrtCode("system");
				superRelation.setCrtTime(new Date());
				superRelation.insert();
			});
		});
	}

	private void initMenu() {
		insertTbResource("-1", Application.init.getMenus());
	}

	private void insertTbResource(String parentCode, List<Menu> menus) {
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);

			StringBuffer codeBuffer = new StringBuffer();
			if (!"-1".equals(parentCode)) {
				codeBuffer.append(parentCode);
			}

			if (i < 9) {
				codeBuffer.append("0" + i);
			} else {
				codeBuffer.append(i);
			}
			String code = codeBuffer.toString();
			TbResource tbResource = new TbResource();
			tbResource.setCode(code);
			tbResource.setName(menu.getName());
			tbResource.setType(menu.getType());
			tbResource.setUri(menu.getUri());
			tbResource.setParentCode(parentCode);
			tbResource.insert(SqlOption.IGNORE_NULL);

			if (menu.getChildren() != null && !menu.getChildren().isEmpty() && menu.getChildren().size() != 0) {
				insertTbResource(code, menu.getChildren());
			}
		}
	}
}
