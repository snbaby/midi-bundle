/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.DicSecretCode;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.DicSecretCodeTypeService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class DicSecretCodeSqlBoxVerticle {

    public DicSecretCodeTypeService secretCodeTypeService = BeanBox.getBean(DicSecretCodeTypeService.class);

    private Router router;

    public DicSecretCodeSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //新增设备
        router.post("/").blockingHandler(this::insertDic, false);
        //修改密级
        router.put("/:secretCode").blockingHandler(this::updateDic, false);
        //删除密级
        router.delete("/:secretCode").blockingHandler(this::deleteDic, false);
        //获取密级详细信息
        router.get("/:secretCode").blockingHandler(this::findByDicTypeCode, false);
        //密级分页查询
        router.post("/page").blockingHandler(this::findDicPageList, false);

        return this.router;
    }


    private void findByDicTypeCode(RoutingContext routingContext) {
        try {
            List<DicSecretCode> byDicTypeCode = secretCodeTypeService.findByDicTypeCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, byDicTypeCode);
        } catch (Exception e) {
            log.error("获取密级详细信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertDic(RoutingContext routingContext) {
        try {
            secretCodeTypeService.insertDic(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("新增设备错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void updateDic(RoutingContext routingContext) {
        try {
            secretCodeTypeService.updateDic(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改密级错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteDic(RoutingContext routingContext) {
        try {
            secretCodeTypeService.deleteDic(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除密级错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findDicPageList(RoutingContext routingContext) {
        try {
            Page list = secretCodeTypeService.findDicPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("密级分页查询错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


}
