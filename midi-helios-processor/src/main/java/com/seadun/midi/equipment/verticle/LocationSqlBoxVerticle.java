/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Equipment;
import com.seadun.midi.equipment.entity.Location;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.entity.Port;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.LocationService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LocationSqlBoxVerticle {
    private LocationService locationService = BeanBox.getBean(LocationService.class);

    private Router router;

    LocationSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }


    public Router getApi() {
        //新增位置
        router.post("/").blockingHandler(this::insertLocation, false);
        //位置分页查询
        router.post("/page").blockingHandler(this::findLocationPageList, false);
        //使用areaCode查询equipmentPage
        router.post("/equipment/:areaCode").blockingHandler(this::findEquipmentByAreaCodePageList, false);
        //获取位置详细信息
        router.get("/:code/detail").blockingHandler(this::findByLocationCode, false);
        //修改位置信息
        router.put("/:code").blockingHandler(this::updateLocation, false);
        //修改位置状态
        router.put("/:code/status").blockingHandler(this::updateLocationStatus, false);
        //删除位置
        router.delete("/:code").blockingHandler(this::deleteLocationByCode, false);
        //查询位置上端口详细信息
        router.get("/:locationCode/port-detail").blockingHandler(this::findLocationPort, false);
        //位置上添加端口
        router.post("/:locationCode/port-acl").blockingHandler(this::insertLocationPort, false);
        //位置上删除端口
        router.delete("/:locationCode/port-acl").blockingHandler(this::deleteLocationPort, false);
        //查询位置上的设备信息
        router.get("/:locationCode/equipment-detail").blockingHandler(this::findLocationEquipment, false);
        //位置上添加设备
        router.post("/:locationCode/equipment-acl").blockingHandler(this::insertLocationEquipment, false);
        //位置上删除设备
        router.delete("/:locationCode/equipment-acl").blockingHandler(this::deleteLocationEquipment, false);
        //查询不在位置上的设备
        router.get("/equipmentNotOnLocation/:locationCode").blockingHandler(this::equipmentNotOnLocationPage, false);
        //查询不在位置上的端口
        router.post("/portNotOnLocation").blockingHandler(this::portNotOnLocation, false);

        return this.router;
    }


    private void findLocationPort(RoutingContext routingContext) {
        try {
            Port list = locationService.findLocationPort(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("查询位置上端口详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findLocationEquipment(RoutingContext routingContext) {
        try {
            Equipment list = locationService.findLocationEquipment(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("查询位置上的设备信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findByLocationCode(RoutingContext routingContext) {
        try {
            Location list = locationService.findByLocationCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取位置详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertLocation(RoutingContext routingContext) {
        try {
            locationService.insertLocation(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增位置错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertLocationPort(RoutingContext routingContext) {
        try {
            locationService.insertLocationPort(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("位置上添加端口错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertLocationEquipment(RoutingContext routingContext) {
        try {
            locationService.insertLocationEquipment(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("位置上添加设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateLocationStatus(RoutingContext routingContext) {
        try {
            locationService.updateLocationStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改位置状态错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateLocation(RoutingContext routingContext) {
        try {
            locationService.updateLocation(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改位置信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteLocationByCode(RoutingContext routingContext) {
        try {
            locationService.deleteLocationByCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除位置错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteLocationPort(RoutingContext routingContext) {
        try {
            locationService.deleteLocationPort(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("位置上删除端口错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteLocationEquipment(RoutingContext routingContext) {
        try {
            locationService.deleteLocationEquipment(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("位置上删除设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findLocationPageList(RoutingContext routingContext) {
        try {
            Page list = locationService.findLocationPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("位置分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void equipmentNotOnLocationPage(RoutingContext routingContext) {
        try {
            Page list = locationService.equipmentNotOnLocationPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("查询不在位置上的设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void portNotOnLocation(RoutingContext routingContext) {
        try {
            Page list = locationService.portNotOnLocation(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("查询不在位置上的端口错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findEquipmentByAreaCodePageList(RoutingContext routingContext) {
        try {
            Page list = locationService.findEquipmentByAreaCodePageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("使用areaCode查询equipmentPage错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
