/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.LogService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogRouter {
    private LogService logService = BeanBox.getBean(LogService.class);
    private Router router;
    public LogRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //查询所有日志
        router.post("/page").blockingHandler(this::getAllLog, false);
        //查询管理员日志
        router.post("/aas/administrator/page").blockingHandler(this::getAdministratorLog, false);
        //查询安全员日志
        router.post("/aas/security/page").blockingHandler(this::getSecurityLog, false);
        //查询审计员日志
        router.post("/aas/auditor/page").blockingHandler(this::getAuditorLog, false);
        //查询非三员日志
        router.post("/aas/other").blockingHandler(this::getOtherLog, false);
        router.post("/login/page").blockingHandler(this::getLoginLog, false);
        router.post("/db/page").blockingHandler(this::getDBLog, false);
        router.post("/model/page").blockingHandler(this::getModelLog, false);
        return this.router;

    }

    private void getAllLog(RoutingContext routingContext) {
        try {
            Page allLog = logService.getAllLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,allLog);
        } catch (Exception e) {
            log.error("查询所有日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getAdministratorLog(RoutingContext routingContext) {
        try {
            Page administratorLog = logService.getAdministratorLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,administratorLog);
        } catch (Exception e) {
            log.error("查询管理员日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getSecurityLog(RoutingContext routingContext) {
        try {
            Page securityLog = logService.getSecurityLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,securityLog);
        } catch (Exception e) {
            log.error("查询安全员日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getAuditorLog(RoutingContext routingContext) {
        try {
            Page auditorLog = logService.getAuditorLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,auditorLog);
        } catch (Exception e) {
            log.error("查询审计员日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getOtherLog(RoutingContext routingContext) {
        try {
            Page otherLog = logService.getOtherLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,otherLog);
        } catch (Exception e) {
            log.error("查询非三员日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    
    private void getLoginLog(RoutingContext routingContext) {
        try {
            Page otherLog = logService.getLoginLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,otherLog);
        } catch (Exception e) {
            log.error("查询登录日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    
    private void getDBLog(RoutingContext routingContext) {
        try {
            Page otherLog = logService.getDBLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,otherLog);
        } catch (Exception e) {
            log.error("查询数据操作日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    
    private void getModelLog(RoutingContext routingContext) {
        try {
            Page otherLog = logService.getModelLog(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,otherLog);
        } catch (Exception e) {
            log.error("查询模块操作日志错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
}
