/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.StationService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StationRouter extends AbstractVerticle {
    private StationService stationService = BeanBox.getBean(StationService.class);

    private Router router;

    public StationRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        router.post("/:stationCode/person-acl/page").blockingHandler(this::getPersonStation, false);//组织中人员分页查询
        router.post("/").blockingHandler(this::createStation, false);//创建岗位
        router.post("/page").blockingHandler(this::getStationPage, false);//岗位分页查询
        router.put("/:code").blockingHandler(this::updateStation, false);//修改岗位
        router.delete("/:code").blockingHandler(this::deleteStation, false);//删除岗位
        router.post("/:stationCode/duty-acl").blockingHandler(this::insertStationDuty, false);//岗位中添加岗位职责
        router.delete("/:stationCode/duty-acl").blockingHandler(this::deleteStationDuty, false);//删除岗位中岗位职责
        router.post("/:stationCode/duty-acl/page").blockingHandler(this::getStationDutyPage, false);//岗位中岗位职责分页查询
        return this.router;
    }

    private void getPersonStation(RoutingContext routingContext) {
        try {
            Page page = stationService.getPersonStation(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, page);
        } catch (Exception e) {
            log.error("岗位中用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    public void createStation(RoutingContext routingContext) {
        try {
        	stationService.createStation(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建岗位错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void getStationPage(RoutingContext routingContext) {
    	try {
            Page stationPage = stationService.getStationPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,stationPage);
        } catch (Exception e) {
            log.error("岗位分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void updateStation(RoutingContext routingContext) {
        try {
        	stationService.updateStation(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改岗位错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void insertStationDuty(RoutingContext routingContext) {
        try {
                  stationService.insertStationDuty(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("岗位中添加岗位职责错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }

    }

    public void deleteStationDuty(RoutingContext routingContext) {
    	try {
            stationService.deleteStationDuty(routingContext,SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除岗位中岗位职责错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
   }

   public void getStationDutyPage(RoutingContext routingContext) {
        try {
            Page stationDutyPage = stationService.getStationDutyPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,stationDutyPage);
        } catch (Exception e) {
            log.error("岗位中岗位职责分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

   public void deleteStation(RoutingContext routingContext) {
   	try {
           stationService.deleteStation(routingContext,SqlBoxContext.getGlobalSqlBoxContext());
        MidiResponse.success(routingContext);
    } catch (Exception e) {
        log.error("删除岗位错误",e);
        e.printStackTrace();
        Utils.processException(e, routingContext);
    }
   }

}
