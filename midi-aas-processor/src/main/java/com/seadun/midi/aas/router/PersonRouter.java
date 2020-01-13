/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbPerson;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.PersonService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class PersonRouter {
    private PersonService personService = BeanBox.getBean(PersonService.class);

    private Router router;

    public PersonRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //人员分页查询
        router.post("/page").blockingHandler(this::getPersonPage, false);
        //获取人员详细信息
        router.get("/:code/detail").blockingHandler(this::getPersonDetail, false);
        //修改人员信息
        router.put("/:code").blockingHandler(this::updatePersonDetail, false);
        //修改人员状态
        router.put("/:code/status").blockingHandler(this::updatePersonStatus, false);
        //修改个人信息
        router.put("/").blockingHandler(this::updateSelfDetail, false);
        //获取人员个人详细信息
        router.get("/detail").blockingHandler(this::getSelfDetail, false);
        //创建人员
        router.post("/").blockingHandler(this::createPerson, false);
        return this.router;

    }


    public void getPersonPage(RoutingContext routingContext) {
        try {
            Page page =personService.getPersonPage(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,page);
        } catch (Exception e) {
            log.error("人员分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void getPersonDetail(RoutingContext routingContext) {
        try {
            List personDetail = personService.getPersonDetail(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,personDetail);
        } catch (Exception e) {
            log.error("获取人员详细信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void getSelfDetail(RoutingContext routingContext) {
        try {
            TbPerson list = personService.getSelfDetail(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,list);
        } catch (Exception e) {
            log.error("获取人员个人详细信息错误",e);
            e.printStackTrace();

            Utils.processException(e, routingContext);
        }
    }

    public void updatePersonDetail(RoutingContext routingContext) {
        try {
            personService.updatePersonDetail(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改人员信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void updateSelfDetail(RoutingContext routingContext) {
        try {
            personService.updateSelfDetail(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改个人信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void updatePersonStatus(RoutingContext routingContext) {
        try {
            personService.updatePersonStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改人员状态错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void createPerson(RoutingContext routingContext) {
        try {
            personService.createPerson(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建人员错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
