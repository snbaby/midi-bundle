package com.seadun.midi.jk.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.drinkjava2.jbeanbox.BeanBox;
import com.seadun.midi.jk.Application;
import com.seadun.midi.jk.Jdbc;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class DataSourceCfgAas extends BeanBox {
    public DataSource create() throws SQLException {
        List<Jdbc> jdbcList = Application.server.getDatasources();
        Jdbc jdbc = jdbcList.get(0);
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(jdbc.getUrl());
        ds.setDriverClassName(jdbc.getDriverClassName());
        ds.setUsername(jdbc.getUserName());
        ds.setPassword(jdbc.getPassword());
        ds.setName(jdbc.getName());
        this.setPreDestroy("close");// jBeanBox will close pool
        log.info(".info(\"单库初始化数据源：------->\", JSON.toJSONString(jdbc));");
        return ds;
    }
};
