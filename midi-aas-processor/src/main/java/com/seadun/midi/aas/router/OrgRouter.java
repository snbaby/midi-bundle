/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.OrgService;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrgRouter {
    private OrgService orgService = BeanBox.getBean(OrgService.class);

    private Router router;

    public OrgRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        router.post("/:orgCode/person-acl/page").blockingHandler(this::getPersonOrg, false);//组织中人员分页查询
        router.post("/").blockingHandler(this::createOrg, false);//创建组织
        router.post("/page").blockingHandler(this::getOrgPage, false);//组织分页查询
        router.put("/:code").blockingHandler(this::updateOrg, false);//修改组织
        router.delete("/:code").blockingHandler(this::deleteOrg, false);//删除组织
        router.post("/:orgCode/station-acl").blockingHandler(this::insertStationOrg, false);//组织中添加岗位
        router.post("/:orgStaionId/org-station-person-acl").blockingHandler(this::insertPersonOrgStation, false);//组织岗位中添加人员
        router.post("/:orgCode/person-acl").blockingHandler(this::insertPersonOrg, false);//组织中添加人员
        router.delete("/:orgCode/station-acl").blockingHandler(this::deleteOrgStation, false);//删除组织中岗位
        router.delete("/:orgStaionId/org-station-person-acl").blockingHandler(this::deletePersonOrgStation, false);//删除组织岗位中人员
        router.delete("/:orgCode/person-acl").blockingHandler(this::deletePersonOrg, false);//删除组织中人员
        router.post("/:orgCode/station-acl/page").blockingHandler(this::getStationOrg, false);//组织中岗位分页查询
        router.post("/:orgCode/station-acl/_page").blockingHandler(this::getNotInStationOrg, false);//组织中岗位分页查询
        router.post("/:orgStaionId/org-station-person-acl/page").blockingHandler(this::getPersonStationOrg, false);//组织岗位中用户分页查询
        router.post("/:orgStaionId/org-station-person-acl/_page").blockingHandler(this::getNotInPersonStationOrg, false);//不在组织岗位中用户分页查询
        router.post("/station/relation/page").blockingHandler(this::getOrgStationPage, false);//组织岗位分页查询
        return this.router;

    }


    private void getPersonOrg(RoutingContext routingContext) {
        try {
            Page page = orgService.getPersonOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, page);
        } catch (Exception e) {
            log.error("组织中用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void createOrg(RoutingContext routingContext) {
        try {
            orgService.createOrg(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建组织错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getOrgPage(RoutingContext routingContext) {
        try {
            Page orgPage = orgService.getOrgPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, orgPage);
        } catch (Exception e) {
            log.error("组织分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getOrgStationPage(RoutingContext routingContext) {
        try {
            Page orgPage = orgService.getOrgStationPage(routingContext,SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, orgPage);
        } catch (Exception e) {
            log.error("组织岗位分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void updateOrg(RoutingContext routingContext) {
        try {
            orgService.updateOrg(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteOrg(RoutingContext routingContext) {
        try {
            orgService.deleteOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除组织错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void getStationOrg(RoutingContext routingContext) {
        try {
            Page stationOrg = orgService.getStationOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, stationOrg);
        } catch (Exception e) {
            log.error("组织中岗位分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getNotInStationOrg(RoutingContext routingContext) {
        try {
            Page stationOrg = orgService.getNotInStationOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, stationOrg);
        } catch (Exception e) {
            log.error("非当前组织中岗位分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    private void insertPersonOrgStation(RoutingContext routingContext) {
        try {
            orgService.insertPersonOrgStation(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("组织岗位中添加用户错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertPersonOrg(RoutingContext routingContext) {
        try {
            orgService.insertPersonOrg(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("组织中添加用户错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deleteOrgStation(RoutingContext routingContext) {
        try {
            orgService.deleteOrgStation(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除组织中岗位错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deletePersonOrgStation(RoutingContext routingContext) {
        try {
            orgService.deletePersonOrgStation(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除组织岗位中用户错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void deletePersonOrg(RoutingContext routingContext) {
        try {
            orgService.deletePersonOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除组织中用户错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void insertStationOrg(RoutingContext routingContext) {
        try {
            orgService.insertStationOrg(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("组织中添加岗位错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }

    }

    private void getPersonStationOrg(RoutingContext routingContext) {
        try {
            Page userStationOrg = orgService.getPersonStationOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, userStationOrg);
        } catch (Exception e) {
            log.error("组织岗位中用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    private void getNotInPersonStationOrg(RoutingContext routingContext) {
        try {
            Page userStationOrg = orgService.getNotInPersonStationOrg(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, userStationOrg);
        } catch (Exception e) {
            log.error("非当前组织岗位中用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


}
