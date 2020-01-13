package com.seadun.midi.jk;


import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jtransactions.tinytx.TinyTxConnectionManager;
import com.seadun.midi.jk.config.DataSourceCfgAas;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class VerticleJK {

    public static void main(String[] args) {
        try {
            Utils.loadYml();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SqlBoxContext ctx = new SqlBoxContext((DataSource) BeanBox.getBean(DataSourceCfgAas.class));
        ctx.setConnectionManager(TinyTxConnectionManager.instance());
        SqlBoxContext.setGlobalSqlBoxContext(ctx);

        ScheduledExecutorService service = newScheduledThreadPool(1);
//		 从现在开始2秒钟之后，每隔60秒钟执行一次
        service.scheduleWithFixedDelay(new JdbcClient(), 2, 60, TimeUnit.SECONDS);
    }

}
