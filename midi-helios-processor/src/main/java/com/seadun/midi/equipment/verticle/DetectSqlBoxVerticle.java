/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Detect;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.DetectService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DetectSqlBoxVerticle {
    private DetectService detectService = BeanBox.getBean(DetectService.class);

    private Router router;

    DetectSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }


    public Router getApi() {
        //新增多路侦测器
        router.post("/").blockingHandler(this::insertDetect, false);
        //多路侦测器分页查询
        router.post("/page").blockingHandler(this::getDetectPage, false);
        //修改多路侦测器状态
        router.post("/:code/status").blockingHandler(this::updateDetectStatus, false);
        //获取多路侦测器详细信息
        router.get("/:code/detail").blockingHandler(this::getDetectDetail, false);
        //修改多路侦测器信息
        router.put("/:code").blockingHandler(this::updateDetectDetail, false);
        //删除多路侦测器
        router.delete("/:code").blockingHandler(this::deleteDetect, false);
        return this.router;
    }

    private void insertDetect(RoutingContext routingContext) {
        try {
            detectService.insertDetect(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增多路侦测器错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getDetectPage(RoutingContext routingContext) {
        try {
            Page page = detectService.getDetectPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, page);
        } catch (Exception e) {
            log.error("多路侦测器分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getDetectDetail(RoutingContext routingContext) {
        try {
            Detect list = detectService.getDetectDetail(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取多路侦测器详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateDetectDetail(RoutingContext routingContext) {
        try {
            detectService.updateDetectDetail(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改多路侦测器信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateDetectStatus(RoutingContext routingContext) {
        try {
            detectService.updateDetectStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改多路侦测器状态错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteDetect(RoutingContext routingContext) {
        try {
            detectService.deleteDetect(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除多路侦测器错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
}
