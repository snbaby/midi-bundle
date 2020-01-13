/**
 *
 */
package com.seadun.midi.equipment.verticle;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.response.MidiResponse;
import com.seadun.midi.equipment.service.ProcessService;
import com.seadun.midi.equipment.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProcessSqlBoxVerticle {
    private ProcessService processService = BeanBox.getBean(ProcessService.class);
    private Router router;

    ProcessSqlBoxVerticle(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //创建流程
        router.post("/").blockingHandler(this::insertProcess, false);
        //审批流程
        router.put("/:id").blockingHandler(this::approveProcess, false);
        //查询流程分页信息
        router.post("/page").blockingHandler(this::getProcessPage, false);
        //查询个人申请流程分页信息
        router.post("/apply/page").blockingHandler(this::getProcessApplyPage, false);
        //查询个人审批流程分页信息
        router.post("/approve/page").blockingHandler(this::getProcessApprovePage, false);
        //查询可审批人
        router.get("/user").blockingHandler(this::getProcessUser, false);
        return this.router;
    }

    private void getProcessPage(RoutingContext routingContext) {
        try {
            Page processPage = processService.getProcessPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, processPage);
        } catch (Exception e) {
            log.error("查询流程分页信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getProcessApplyPage(RoutingContext routingContext) {
        try {
            Page processPage = processService.getProcessApplyPage(routingContext,
                    SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, processPage);
        } catch (Exception e) {
            log.error("查询个人申请流程分页信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getProcessApprovePage(RoutingContext routingContext) {
        try {
            Page processPage = processService.getProcessApprovePage(routingContext,
                    SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, processPage);
        } catch (Exception e) {
            log.error("查询个人审批流程分页信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getProcessUser(RoutingContext routingContext) {
        try {
            Page processPage = processService.getProcessUser(routingContext,
                    SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, processPage);
        } catch (Exception e) {
            log.error("查询个人审批流程分页信息错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertProcess(RoutingContext routingContext) {
        try {
            processService.insertProcess(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建流程错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void putProcess(RoutingContext routingContext) {
        try {
            processService.insertProcess(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void approveProcess(RoutingContext routingContext) {
        try {
            processService.approveProcess(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("审批流程错误", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
}
