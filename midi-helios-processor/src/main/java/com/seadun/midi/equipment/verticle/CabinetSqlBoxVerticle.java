/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Cabinet;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.CabinetService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CabinetSqlBoxVerticle {

    private CabinetService cabinetService = BeanBox.getBean(CabinetService.class);

    private Router router;

    CabinetSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //新增机柜
        router.post("/").blockingHandler(this::insertCabinet, false);
        //修改机柜信息
        router.put("/:code").blockingHandler(this::updateCabinet, false);
        //修改机柜状态
        router.put("/:code/status").blockingHandler(this::updateCabinetStatus, false);
        //删除机柜
        router.delete("/:code").blockingHandler(this::deleteCabinetByCode, false);
        //查询机柜信息
        router.get("/:code").blockingHandler(this::findByCabinetCode, false);
        //机柜分页查询
        router.post("/page").blockingHandler(this::findCabinetPageList, false);

        return this.router;
    }


    private void findByCabinetCode(RoutingContext routingContext) {
        try {
            Cabinet cabinet = cabinetService.findByCabinetCode(routingContext);
            MidiResponse.success(routingContext, cabinet);
        } catch (Exception e) {
            log.error("查询机柜信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertCabinet(RoutingContext routingContext) {
        try {
            cabinetService.insertCabinet(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增机柜错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateCabinetStatus(RoutingContext routingContext) {
        try {
            cabinetService.updateAreaStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改机柜状态错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateCabinet(RoutingContext routingContext) {
        try {
            cabinetService.updateCabinet(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改机柜信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteCabinetByCode(RoutingContext routingContext) {
        try {
            cabinetService.deleteAreaByCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除机柜错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findCabinetPageList(RoutingContext routingContext) {
        try {
            Page list = cabinetService.findCabinetPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("机柜分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
