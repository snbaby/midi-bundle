/**
 *
 */
package com.seadun.midi.aas.router;

import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.service.AppService;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Log4j2
public class AppRouter {

    private AppService appService = BeanBox.getBean(AppService.class);

    private Router router;

    public AppRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //创建应用
        router.post("/").blockingHandler(this::insertApp, false);
        //应用分页查询
        router.post("/page").blockingHandler(this::findAppPageList, false);
        //删除应用
        router.delete("/:code").blockingHandler(this::deleteAppByCode, false);
        //应用添加资源
        router.post("/:appCode/resource-acl").blockingHandler(this::insertAppResource, false);
        //应用添加角色
        router.post("/:appCode/role-acl").blockingHandler(this::insertAppRole, false);
        //应用删除资源
        router.delete("/:appCode/resource-acl").blockingHandler(this::deleteAppResource, false);
        //应用删除角色
        router.delete("/:appCode/role-acl").blockingHandler(this::deleteAppRole, false);
        //应用中资源的分页查询
        router.post("/:appCode/resource-acl/page").blockingHandler(this::findAppResourcePageList, false);
        //应用中角色的分页查询
        router.post("/:appCode/role-acl/page").blockingHandler(this::findAppRolePageList, false);
        //修改应用信息
        router.put("/:code").blockingHandler(this::updateApp, false);
        return this.router;

    }

    private void updateApp(RoutingContext routingContext) {
        try {
            appService.updateApp(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建应用错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }


    }

    private void findAppRolePageList(RoutingContext routingContext) {
        try {
            Page page = appService.findAppRolePageList(routingContext);
            MidiResponse.success(routingContext, page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("应用中角色的分页查询",e);
            Utils.processException(e, routingContext);
        }
    }

    private void findAppResourcePageList(RoutingContext routingContext) {
        try {
            Page page = appService.findAppResourcePageList(routingContext);
            MidiResponse.success(routingContext, page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("应用中资源的分页查询",e);
            Utils.processException(e, routingContext);
        }
    }

    private void deleteAppRole(RoutingContext routingContext) {
        try {
            appService.deleteAppRole(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("应用删除角色错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteAppResource(RoutingContext routingContext) {
        try {
            appService.deleteAppResource(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("应用删除资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertAppRole(RoutingContext routingContext) {
        try {
            appService.insertAppRole(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("应用添加角色错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertAppResource(RoutingContext routingContext) {
        try {
            appService.insertAppResource(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("应用添加资源失败", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertApp(RoutingContext routingContext) {
        try {
            appService.insertApp(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建应用失败", e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void deleteAppByCode(RoutingContext routingContext) {
        try {
            appService.deleteAppByCode(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除应用错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void findAppPageList(RoutingContext routingContext) {
        try {
            Page page = appService.findAppPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, page);
        } catch (Exception e) {
            log.error("应用分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
}
