/**
 *
 */
package com.seadun.midi.aas.router;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.InitService;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Log4j2
public class InitRouter {
    private InitService initService = BeanBox.getBean(InitService.class);
    private Router router;

    public InitRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //初始化
        router.get("/").blockingHandler(this::init, false);
        return this.router;
    }


    public void init(RoutingContext routingContext) {
        try {
            initService.init(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("初始化错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
}
