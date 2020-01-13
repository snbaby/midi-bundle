/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Alarm;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.AlarmService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AlarmSqlBoxVerticle {
    private AlarmService alarmService = BeanBox.getBean(AlarmService.class);
    private Router router;

    AlarmSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //警报分页查询
        router.post("/page").blockingHandler(this::getAlarmPage, false);
        //获取警报详细信息
        router.get("/:code").blockingHandler(this::getAlarmDetail, false);
        //修改警报信息
        router.put("/:code").blockingHandler(this::updateAlarmDetail, false);
        return this.router;
    }

    private void getAlarmPage(RoutingContext routingContext) {
        try {
            Page alarmPage = alarmService.getAlarmPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, alarmPage);
        } catch (Exception e) {
            log.error("警报分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getAlarmDetail(RoutingContext routingContext) {
        try {
            Alarm alarmDetail = alarmService.getAlarmDetail(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, alarmDetail);
        } catch (Exception e) {
            log.error("获取警报详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateAlarmDetail(RoutingContext routingContext) {
        try {
            alarmService.updateAlarmDetail(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改警报信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
}
