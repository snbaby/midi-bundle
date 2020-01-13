package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.DutyService;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j2
public class DutyRouter {
    private DutyService dutyService = BeanBox.getBean(DutyService.class);
    private Router api;

    public DutyRouter(Vertx vertx) {
        this.api = Router.router(vertx);
    }

    public Router getApi() {
        api.post("/").handler(requestHandler -> {
            try {
                dutyService.createDuty(requestHandler);
                MidiResponse.success(requestHandler);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("创建岗位职责错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        api.put("/:code").handler(requestHandler -> {
            try {
                dutyService.updateDuty(requestHandler);
                MidiResponse.success(requestHandler);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("修改岗位职责错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        api.delete("/:code").handler(requestHandler -> {
            try {
                dutyService.deleteDuty(requestHandler);
                MidiResponse.success(requestHandler);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("删除岗位职责错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        api.post("/page").handler(requestHandler -> {
            try {
                MidiResponse.success(requestHandler, dutyService.queryDutyPage(requestHandler));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("岗位职责分页查询错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        return this.api;
    }
}
