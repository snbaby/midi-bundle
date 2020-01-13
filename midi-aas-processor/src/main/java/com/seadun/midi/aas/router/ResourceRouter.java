/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.ResourceService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResourceRouter {

    private ResourceService resourceService = BeanBox.getBean(ResourceService.class);

    private Router router;

    public ResourceRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //创建资源
        router.post("/").blockingHandler(this::insertResource, false);
        //资源分页查询
        router.post("/page").blockingHandler(this::findResourcePageList, false);
        //修改资源
        router.put("/:code").blockingHandler(this::updateResource, false);
        //删除资源
        router.delete("/:code").blockingHandler(this::deleteResourceByCode, false);
        return this.router;
    }


    public void insertResource(RoutingContext routingContext) {
        try {
            resourceService.insertResource(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void updateResource(RoutingContext routingContext) {
        try {
            resourceService.updateResource(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }

    }

    public void deleteResourceByCode(RoutingContext routingContext) {
        try {
            resourceService.deleteResourceByCode(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findResourcePageList(RoutingContext routingContext) {
        try {
            Page pageList = resourceService.findResourcePageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,pageList);
        } catch (Exception e) {
            log.error("资源分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
