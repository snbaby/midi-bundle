package com.seadun.midi.equipment.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.entity.SysDatasource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.github.drinkjava2.jbeanbox.BeanBox;
import com.seadun.midi.equipment.yml.Application;
import com.seadun.midi.equipment.yml.Jdbc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceCfg extends BeanBox {

	public DataSource create() throws SQLException {
		SysDatasource sysDatasource = new SysDatasource();
		SampleItem simpleItem = new SampleItem(sysDatasource);
		List<SysDatasource> datasources = new SqlBoxContext((DataSource) BeanBox.getBean(DataSourceCfgAas.class)).eFindAll(SysDatasource.class,simpleItem);
		log.info("datasources-------------->"+datasources.get(0));

//		List<Jdbc> jdbcList = Application.server.getDatasources();
		if (datasources.size() == 1) {// 单库
			SysDatasource jdbc = datasources.get(0);
			DruidDataSource ds = new DruidDataSource();
			ds.setUrl(jdbc.getJdbcUrl());
			ds.setDriverClassName(jdbc.getDriverClass());
			ds.setUsername(jdbc.getUsername());
			ds.setPassword(jdbc.getPassword());
			ds.setName(jdbc.getName());
			this.setPreDestroy("close");// jBeanBox will close pool
			log.info("单库初始化数据源：------->",JSON.toJSONString(jdbc));
			return ds;
		} else {// 分库--仅做查询使用
			Map<String, DataSource> dataSourceMap = new HashMap<>();
			datasources.forEach(jdbc -> {
				if("0".equals(jdbc.getStatus())){
					DruidDataSource dataSource = new DruidDataSource();
					dataSource.setDriverClassName(jdbc.getDriverClass());
					dataSource.setUrl(jdbc.getJdbcUrl());
					dataSource.setUsername(jdbc.getUsername());
					dataSource.setPassword(jdbc.getPassword());
					dataSource.setName(jdbc.getName());
					dataSourceMap.put(jdbc.getName(), dataSource);
					log.info("分库初始化数据源：------->",JSON.toJSONString(jdbc));
				}else {
					log.info("此数据库不加载-----》",JSON.toJSONString(jdbc));
				}
			});
			ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
			Application.getShardTbs().forEach(tb -> {
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
