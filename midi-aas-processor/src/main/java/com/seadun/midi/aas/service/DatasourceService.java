package com.seadun.midi.aas.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.ConditionAttr;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.SysDatasource;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class DatasourceService {
	public void getSampleItem(SampleItem simpleItem, String sql, String param) {
		if (StringUtils.isNotBlank(param)) {
			simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
		}
	}

	@TX
	public void createDatasource(RoutingContext requestHandler,SqlBoxContext ctx) throws MidiException {
		JsonObject bodyAsJson = requestHandler.getBodyAsJson();
        SysDatasource datasource = new SysDatasource(bodyAsJson);
        if (StringUtils.isBlank(datasource.getName())) {
			throw new MidiException(400, "数据源编码不能为空");
		}
        if (StringUtils.isBlank(datasource.getTitle())) {
			throw new MidiException(400, "数据源名称不能为空");
		}
        if (StringUtils.isBlank(datasource.getDbType())) {
			throw new MidiException(400, "数据源类型不能为空");
		}
        if (StringUtils.isBlank(datasource.getUsername())) {
			throw new MidiException(400, "数据源用户名不能为空");
		}
        if (StringUtils.isBlank(datasource.getPassword())) {
			throw new MidiException(400, "数据源密码不能为空");
		}
        if (StringUtils.isBlank(datasource.getJdbcUrl())) {
			throw new MidiException(400, "数据源连接路径不能为空");
		}
        if (StringUtils.isBlank(datasource.getDriverClass())) {
			throw new MidiException(400, "数据源连接驱动不能为空");
		}else {
			String jdbcUrl = datasource.getJdbcUrl();
			Integer i = ctx.nQueryForIntValue("select count(*) from sys_datasource where jdbc_url = ?", jdbcUrl);
			if (i > 0) {
                throw new MidiException(400, "添加失败，该数据源连接路径已存在");
            }
        	String username = datasource.getUsername();
			String password = datasource.getPassword();
			//1.加载驱动程序
			try {
				Class.forName(datasource.getDriverClass());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new MidiException(400, "数据源连接驱动错误");
			}
			//2.获得数据库链接
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(jdbcUrl, username, password);
//    				log.info("测试链接成功----->>>" + datasource.getUsername());
				//链接成功后将status改为true
				datasource.setStatus("0");
			} catch (SQLException e) {
				e.printStackTrace();
//    				log.info("链接出错");
				//链接失败后将status改为false
				datasource.setStatus("1");
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//        if (StringUtils.isBlank(datasource.getStatus())) {
//			throw new MidiException(400, "数据源状态不能为空");
//		}
        datasource.setId(UUID.randomUUID().toString());
        datasource.insert(SqlOption.IGNORE_NULL);
	}

	@TX
	public void updateDatasource(RoutingContext requestHandler,SqlBoxContext ctx) throws MidiException {

		JsonObject bodyAsJson = requestHandler.getBodyAsJson();
        SysDatasource datasource = new SysDatasource(bodyAsJson);

        if (StringUtils.isBlank(datasource.getTitle())) {
			throw new MidiException(400, "数据源名称不能为空");
		}
        if (StringUtils.isBlank(datasource.getDbType())) {
			throw new MidiException(400, "数据源类型不能为空");
		}
        if (StringUtils.isBlank(datasource.getUsername())) {
			throw new MidiException(400, "数据源用户名不能为空");
		}
        if (StringUtils.isBlank(datasource.getPassword())) {
			throw new MidiException(400, "数据源密码不能为空");
		}
        if (StringUtils.isBlank(datasource.getJdbcUrl())) {
			throw new MidiException(400, "数据源连接路径不能为空");
		}
        if (StringUtils.isBlank(datasource.getDriverClass())) {
			throw new MidiException(400, "数据源连接驱动不能为空");
		}
		else {
			String jdbcUrl = datasource.getJdbcUrl();
			Integer i = ctx.nQueryForIntValue("select count(*) from sys_datasource where jdbc_url = ?", jdbcUrl);
			if (i > 0) {
				i = ctx.nQueryForIntValue("select count(*) from sys_datasource where jdbc_url = ? and id = ?", jdbcUrl, requestHandler.pathParam("code"));
                if(i <= 0) {
                	throw new MidiException(400, "修改失败，该数据源连接路径已存在");
                }
            }
			String username = datasource.getUsername();
			String password = datasource.getPassword();
			//1.加载驱动程序
			try {
				Class.forName(datasource.getDriverClass());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new MidiException(400, "数据源连接驱动错误");
			}
			//2.获得数据库链接
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(jdbcUrl, username, password);
//				log.info("测试链接成功----->>>" + datasource.getUsername());
				//链接成功后将status改为true
				datasource.setStatus("0");
			} catch (SQLException e) {
				e.printStackTrace();
//				log.info("链接出错");
				//链接失败后将status改为false
				datasource.setStatus("1");
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//        if (StringUtils.isBlank(datasource.getStatus())) {
//			throw new MidiException(400, "数据源状态不能为空");
//		}
        datasource.setId(requestHandler.pathParam("code"));
        datasource.update(SqlOption.IGNORE_NULL);
	}

	@TX
	public void deleteDatasource(RoutingContext requestHandler) throws MidiException {
		String code = requestHandler.pathParam("code");

		SysDatasource datasource = new SysDatasource();

		datasource.setId(code);
		datasource.deleteTry(SqlOption.IGNORE_NULL);
	}

	@TX
	public Page datasourcePage(RoutingContext requestHandler) throws MidiException {
		JsonObject bodyAsJson = requestHandler.getBodyAsJson();
		Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
		Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;

        String sqlTemplate = "select %s from sys_datasource where 1=1 ";

        String sqlResult = "*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("name", "name"));
        conditionAttrList.add(new ConditionAttr("title", "title"));
        conditionAttrList.add(new ConditionAttr("status", "status"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        return Utils.queryPage(SysDatasource.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
	}

}
