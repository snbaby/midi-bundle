/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Equipment;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.EquipmentService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EquipmentSqlBoxVerticle {

    private EquipmentService equipmentService = BeanBox.getBean(EquipmentService.class);

    private Router router;

    EquipmentSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //新增设备
        router.post("/").blockingHandler(this::insertEquipment, false);
        //修改设备信息
        router.put("/:code").blockingHandler(this::updateEquipment, false);
        //删除设备
        router.delete("/:code").blockingHandler(this::deleteEquipmentByCode, false);
        //获取场所详细信息
        router.get("/:code/detail").blockingHandler(this::findByEquipmentCode, false);
        //查询设备台账分页信息
        router.post("/page").blockingHandler(this::findEquipmentPageList, false);
        //修改设备状态
        router.put("/:code/status").blockingHandler(this::updateEquipmentStatus, false);

        return this.router;
    }


    private void findByEquipmentCode(RoutingContext routingContext) {
        try {
            Equipment list = equipmentService.findByEquipmentCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取场所详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertEquipment(RoutingContext routingContext) {
        try {
            equipmentService.insertEquipment(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateEquipmentStatus(RoutingContext routingContext) {
        try {
            equipmentService.updateEquipmentStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改设备状态错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void updateEquipment(RoutingContext routingContext) {
        try {
            equipmentService.updateEquipment(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改设备信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteEquipmentByCode(RoutingContext routingContext) {
        try {
            equipmentService.deleteEquipmentByCode(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findEquipmentPageList(RoutingContext routingContext) {
        try {
            Page equipmentPageList = equipmentService.findEquipmentPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, equipmentPageList);
        } catch (Exception e) {
            log.error("查询设备台账分页信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
