/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Area;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.AreaService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class AreaSqlBoxVerticle {

    private AreaService areaService = BeanBox.getBean(AreaService.class);

    private Router router;

    AreaSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //新增场所
        router.post("/").handler(this::insertArea);
        //场所分页查询
        router.post("/page").blockingHandler(this::findAreaPageList, false);
        //获取场所详细信息
        router.get("/:code/detail").blockingHandler(this::findByAreaCode, false);
        //删除场所
        router.delete("/:code").blockingHandler(this::deleteAreaByCode, false);
        //修改场所状态
        router.put("/:code/status").blockingHandler(this::updateAreaStatus, false);
        //修改场所信息
        router.put("/:code").blockingHandler(this::updateArea, false);
        //查询数据库连接成功与否获取场所是否可用
        router.get("/isEnablePage").blockingHandler(this::findAreaIsEnablePage, false);
        //查询当前登录人所能访问的场所
//        router.post("/user/page").blockingHandler(this::findAreaUserPageList, false);

        return this.router;
    }


    private void findByAreaCode(RoutingContext routingContext) {
        try {
            Area list = areaService.findByAreaCode(routingContext);
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取场所详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertArea(RoutingContext routingContext) {
        try {
            areaService.insertArea(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增场所错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateAreaStatus(RoutingContext routingContext) {
        try {
            areaService.updateAreaStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改场所状态错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateArea(RoutingContext routingContext) {
        try {
            areaService.updateArea(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改场所信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteAreaByCode(RoutingContext routingContext) {
        try {
            areaService.deleteAreaByCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除场所错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findAreaPageList(RoutingContext routingContext) {
        try {
            List areaPageList = areaService.findAreaPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, areaPageList);
        } catch (Exception e) {
            log.error("场所分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findAreaIsEnablePage(RoutingContext routingContext) {
        try {
            List areaIsEnablePage = areaService.findAreaIsEnablePage();
            MidiResponse.success(routingContext, areaIsEnablePage);
        } catch (Exception e) {
            log.error("场所分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
//    private void findAreaUserPageList(RoutingContext routingContext) {
//        try {
//            areaService.findAreaUserPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
//        } catch (Exception e) {
//            log.error("查询当前登录人所能访问的场所错误",e);
//            e.printStackTrace();
//        }
//    }

}
