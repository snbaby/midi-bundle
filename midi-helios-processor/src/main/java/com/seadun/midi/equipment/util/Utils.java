package com.seadun.midi.equipment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.seadun.midi.equipment.entity.ConditionAttr;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSON;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.google.common.base.Preconditions;
import com.seadun.midi.equipment.VerticleApplication;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.yml.Application;
import com.seadun.midi.equipment.yml.Jdbc;

import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import static com.github.drinkjava2.jdbpro.JDBPRO.notNull;

@Log4j2
public class Utils {
	public static void processException(Exception e, RoutingContext requestHandler) {
		if (e instanceof MidiException) {
			MidiException midiException = (MidiException) e;
			MidiResponse.faild(requestHandler, midiException.getStatusCode(), midiException.getMessage());
		} else if (e.getCause() instanceof MidiException) {
			MidiException midiException = (MidiException) e.getCause();
			MidiResponse.faild(requestHandler, midiException.getStatusCode(), midiException.getMessage());
		} else {
			MidiResponse.faild(requestHandler, 500, "内部错误");
		}
	}
	public static void constructConditions(JsonObject jsb, List<ConditionAttr> conditionAttrList,
										   List<SqlItem> conditionList) {
		conditionAttrList.forEach(conditionAttr -> {
			conditionList.add(notNull(" and " + conditionAttr.getDbAttr() + " like ? ",
					getFilterAttr(jsb, conditionAttr.getJsbAttr())));
			conditionList.add(notNull(" and " + conditionAttr.getDbAttr() + " = ? ",
					getQueryAttr(jsb, conditionAttr.getJsbAttr())));
		});

	}

	private static String getFilterAttr(JsonObject jsb, String attribute) {
		if (jsb.containsKey("filter")) {
			JsonObject filterJsb = jsb.getJsonObject("filter");
			if (filterJsb.containsKey(attribute)) {
				String result = filterJsb.getString(attribute);
				return StringUtils.isBlank(result) ? null : "%" + result + "%";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private static String getQueryAttr(JsonObject jsb, String attribute) {
		if (jsb.containsKey("query")) {
			JsonObject filterJsb = jsb.getJsonObject("query");
			if (filterJsb.containsKey(attribute)) {
				String result = filterJsb.getString(attribute);
				return StringUtils.isBlank(result) ? null : result;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Page queryPage(Class clazz, int pageNo, int pageSize, String sqlTemplate, String sqlResult,
			Object... conditions) {
		SqlBoxContext ctx = SqlBoxContext.getGlobalSqlBoxContext();
		List<?> clazzList = ctx.iQueryForEntityList(clazz, String.format(sqlTemplate, sqlResult), conditions,
				JSQLBOX.pagin(pageNo, pageSize));

		Long total = ctx.iQueryForLongValue(String.format(sqlTemplate, "count(1)"), conditions, JSQLBOX.noPagin());

		Page page = new Page();
		page.setData(clazzList);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(total.intValue());
		return page;
	}

	public static void loadYml() throws FileNotFoundException {
		Yaml yaml = new Yaml();
		Application ap = null;
		String serverYmlPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "application-server.yml";
		String ymlPathOther = System.getProperty("user.dir") + System.getProperty("file.separator") + "application-other.yml";
		File fServerFile = new File(serverYmlPath);
		File fOtherFile = new File(ymlPathOther);
		if (fServerFile.exists()) {
			ap = yaml.loadAs(new FileInputStream(fServerFile), Application.class);
			log.debug("加载外部配置文件 server----->" + serverYmlPath);
		} else {
			yaml.loadAs(VerticleApplication.class.getClassLoader().getResourceAsStream("application-server.yml"),
					Application.class);
			log.debug("加载内部配置文件----->application-server.yml");
		}
		if (fOtherFile.exists()) {
			ap = yaml.loadAs(new FileInputStream(fOtherFile), Application.class);
			log.debug("加载外部配置文件 other----->" + ymlPathOther);
		} else {
			yaml.loadAs(VerticleApplication.class.getClassLoader().getResourceAsStream("application-other.yml"),
					Application.class);
			log.debug("加载内部配置文件----->application-other.yml");
		}
		@SuppressWarnings("unused")
		Application application = ap;
		Preconditions.checkNotNull(Application.server, "yml 配置 server节点缺失");
		Preconditions.checkState(Application.server.getPort() > 1000 && Application.server.getPort() < 65536,
				"yml 配置server.port:%s 错误,server.port允许范围为1000-65536", Application.server.getPort());
		Preconditions.checkNotNull(Application.server.getDatasources(), "yml 配置server.datasources配置缺失");
		for (int i = 0; i < Application.server.getDatasources().size(); i++) {
			Jdbc jdbc = Application.server.getDatasources().get(i);
			Preconditions.checkNotNull(jdbc, "yml 配置server.datasources[%s]配置缺失", i);
			Preconditions.checkNotNull(jdbc.getName(), "yml 配置server.datasources[%s].name 配置缺失", i);
			Preconditions.checkNotNull(jdbc.getUrl(), "yml 配置server.datasources[%s].url配置缺失", i);
			Preconditions.checkNotNull(jdbc.getDriverClassName(), "yml 配置server.datasources[%s].driverClassName配置缺失",
					i);
			Preconditions.checkNotNull(jdbc.getUserName(), "yml 配置server.datasources[%s].userName配置缺失", i);
			Preconditions.checkNotNull(jdbc.getPassword(), "yml 配置server.datasources[%s].password配置缺失", i);
		}
		log.info("yml 配置加载server成功----->" + JSON.toJSONString(Application.server));
		log.info("yml 配置加载shardTbs成功----->" + JSON.toJSONString(Application.shardTbs));
	}

}
