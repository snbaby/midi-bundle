/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.entity.Port;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.PortService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PortSqlBoxVerticle {
    private PortService portService = BeanBox.getBean(PortService.class);

    private Router router;

    PortSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }


    public Router getApi() {
        //端口分页查询
        router.post("/page").blockingHandler(this::getPortPage, false);
        //获取端口详细信息
        router.get("/:code/detail").blockingHandler(this::getPortDetail, false);
        //修改端口信息
        router.put("/:code").blockingHandler(this::updatePortDetail, false);
        //修改端口状态
        router.put("/:code/status").blockingHandler(this::updatePortStatus, false);
        return this.router;
    }

    private void getPortPage(RoutingContext routingContext) {
        try {
            Page portPage = portService.getPortPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, portPage);
        } catch (Exception e) {
            log.error("端口分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getPortDetail(RoutingContext routingContext) {
        try {
            Port list = portService.getPortDetail(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取端口详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updatePortDetail(RoutingContext routingContext) {
        try {
            portService.updatePortDetail(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改端口信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updatePortStatus(RoutingContext routingContext) {
        try {
            portService.updatePortStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改端口状态错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
