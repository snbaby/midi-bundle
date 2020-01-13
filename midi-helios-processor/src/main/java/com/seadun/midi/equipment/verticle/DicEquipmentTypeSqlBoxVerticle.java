/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.DicEquipmentType;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.DicEquipmentTypeService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DicEquipmentTypeSqlBoxVerticle {

    private DicEquipmentTypeService dicEquipmentTypeService = BeanBox.getBean(DicEquipmentTypeService.class);

    private Router router;

    DicEquipmentTypeSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //新增设备
        router.post("/").blockingHandler(this::insertDic, false);
        //修改设备类型
        router.put("/:typeCode").blockingHandler(this::updateDic, false);
        //删除设备类型
        router.delete("/:typeCode").blockingHandler(this::deleteDic, false);
        //获取设备类型详细信息
        router.get("/:typeCode/detail").blockingHandler(this::findByDicTypeCode, false);
        //设备类型分页查询
        router.post("/page").blockingHandler(this::findDicPageList, false);

        return this.router;
    }


    private void findByDicTypeCode(RoutingContext routingContext) {
        try {
            DicEquipmentType byDicTypeCode = dicEquipmentTypeService.findByDicTypeCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, byDicTypeCode);
        } catch (Exception e) {
            log.error("获取设备类型详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertDic(RoutingContext routingContext) {
        try {
            dicEquipmentTypeService.insertDic(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void updateDic(RoutingContext routingContext) {
        try {
            dicEquipmentTypeService.updateDic(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改设备类型错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteDic(RoutingContext routingContext) {
        try {
            dicEquipmentTypeService.deleteEquipmentByCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除设备类型错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findDicPageList(RoutingContext routingContext) {
        try {
            Page dicPageList = dicEquipmentTypeService.findDicPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, dicPageList);
        } catch (Exception e) {
            log.error("设备类型分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


}
