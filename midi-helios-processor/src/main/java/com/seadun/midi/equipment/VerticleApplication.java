package com.seadun.midi.equipment;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jtransactions.tinytx.TinyTxConnectionManager;
import com.seadun.midi.equipment.config.DataSourceCfg;
import com.seadun.midi.equipment.monitoring.MonitoringAlarm;
import com.seadun.midi.equipment.util.*;
import com.seadun.midi.equipment.verticle.HeliosVerticle;
import io.vertx.core.Vertx;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

@Log4j2
public class VerticleApplication {

	public static void main(String[] args) throws FileNotFoundException {
		log.debug("starting----->");
		Utils.loadYml();

		SqlBoxContext ctx2 = new SqlBoxContext((DataSource) BeanBox.getBean(DataSourceCfg.class));
		ctx2.setConnectionManager(TinyTxConnectionManager.instance());
		SqlBoxContext.setGlobalSqlBoxContext(ctx2);

		ctx2.setAllowShowSQL(true);
		Vertx.vertx().deployVerticle(HeliosVerticle.class.getName());

		ScheduledExecutorService service = newScheduledThreadPool(10);
////		 从现在开始2秒钟之后，每隔2秒钟执行一次
		service.scheduleWithFixedDelay(new MonitoringAlarm(), 2, 2, TimeUnit.SECONDS);
		log.debug("started----->");
		Runner.runExample(WebSocketAlarmVerticle.class);
		Runner.runExample(WebSocketCabinetVerticle.class);
		Runner.runExample(WebSocketEquipmentVerticle.class);
	}

}
