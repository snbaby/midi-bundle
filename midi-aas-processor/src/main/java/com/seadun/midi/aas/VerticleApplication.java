package com.seadun.midi.aas;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jtransactions.tinytx.TinyTxConnectionManager;
import com.seadun.midi.aas.config.DataSourceCfg;
import com.seadun.midi.aas.util.Utils;
import com.seadun.midi.aas.verticle.AasVerticle;
import io.vertx.core.Vertx;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.io.FileNotFoundException;

@Log4j2
public class VerticleApplication {
	public static void main(String[] args) throws FileNotFoundException {
		log.debug("starting----->");
		Utils.loadYml();// 初始化数据
		SqlBoxContext ctx = new SqlBoxContext((DataSource) BeanBox.getBean(DataSourceCfg.class));
		ctx.setConnectionManager(TinyTxConnectionManager.instance());
		ctx.setAllowShowSQL(true);
		SqlBoxContext.setGlobalSqlBoxContext(ctx);
		Vertx.vertx().deployVerticle(AasVerticle.class.getName());
		log.debug("started----->");
	}
}
