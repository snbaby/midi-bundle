package com.seadun.midi.equipment.verticle;

import com.seadun.midi.equipment.yml.Application;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class HeliosVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {

        HttpServer server = vertx.createHttpServer();
        Router mainRouter = Router.router(vertx);
        mainRouter.route().handler(BodyHandler.create());
        //area接口
        mainRouter.mountSubRouter("/area", new AreaSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/alarm", new AlarmSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/detect", new DetectSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/cabinet", new CabinetSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/port", new PortSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/location", new LocationSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/equipment", new EquipmentSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/dic/equipment", new DicEquipmentTypeSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/dic/secret", new DicSecretCodeSqlBoxVerticle(vertx).getApi());
        mainRouter.mountSubRouter("/process", new ProcessSqlBoxVerticle(vertx).getApi());

        server.requestHandler(mainRouter).listen(Application.server.getPort(), ar -> {
            if (ar.succeeded()) {
                try {
                    log.info("成功启动equipment->>>>>>>>>>>>>>>>>>>>>>");
                    super.start(startFuture);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("equipment启动失败->>>>>>>>>>>>>>>>>>>>>");
                }
            } else {
                ar.cause().printStackTrace();
            }
        });
    }


}

