package com.seadun.midi.aas.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.entity.SysDatasource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.github.drinkjava2.jbeanbox.BeanBox;
import com.seadun.midi.aas.yml.Application;
import com.seadun.midi.aas.yml.Jdbc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceCfg extends BeanBox {

	public DataSource create() throws SQLException {
		List<Jdbc> jdbcList = Application.server.getDatasources();
		if (jdbcList.size() == 1) {// 单库
			Jdbc jdbc = jdbcList.get(0);
			DruidDataSource ds = new DruidDataSource();
			ds.setUrl(jdbc.getUrl());
			ds.setDriverClassName(jdbc.getDriverClassName());
			ds.setUsername(jdbc.getUserName());
			ds.setPassword(jdbc.getPassword());
			ds.setName(jdbc.getName());
			this.setPreDestroy("close");// jBeanBox will close pool
			log.info("单库初始化数据源：------->",JSON.toJSONString(jdbc));
			return ds;
		} else {// 分库--仅做查询使用
			Map<String, DataSource> dataSourceMap = new HashMap<>();
			jdbcList.forEach(jdbc -> {
				DruidDataSource dataSource = new DruidDataSource();
				dataSource.setDriverClassName(jdbc.getDriverClassName());
				dataSource.setUrl(jdbc.getUrl());
				dataSource.setUsername(jdbc.getUserName());
				dataSource.setPassword(jdbc.getPassword());
				dataSource.setName(jdbc.getName());
				dataSourceMap.put(jdbc.getName(), dataSource);
				log.info("分库初始化数据源：------->",JSON.toJSONString(jdbc));
			});
			ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
			Application.shardTbs.forEach(tb -> {
				TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration(tb);
				shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfig);
				log.info("分库初始化分库表：------->",tb);
			});
			DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,
					new Properties());
			return dataSource;
		}
	}
};
