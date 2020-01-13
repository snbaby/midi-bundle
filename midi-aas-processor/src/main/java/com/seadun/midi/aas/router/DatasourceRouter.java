package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.DatasourceService;
import com.seadun.midi.aas.service.DutyService;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DatasourceRouter {
    private DatasourceService datasourceService = BeanBox.getBean(DatasourceService.class);
    private Router api;

    public DatasourceRouter(Vertx vertx) {
        this.api = Router.router(vertx);
    }

    public Router getApi() {
        api.post("/").handler(requestHandler -> {
            try {
            	datasourceService.createDatasource(requestHandler, SqlBoxContext.getGlobalSqlBoxContext());
                MidiResponse.success(requestHandler);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("创建数据源错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        api.put("/:code").handler(requestHandler -> {
            try {
            	datasourceService.updateDatasource(requestHandler, SqlBoxContext.getGlobalSqlBoxContext());
                MidiResponse.success(requestHandler);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("修改数据源错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        api.delete("/:code").handler(requestHandler -> {
            try {
            	datasourceService.deleteDatasource(requestHandler);
                MidiResponse.success(requestHandler);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("删除数据源错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        api.post("/page").handler(requestHandler -> {
            try {
                MidiResponse.success(requestHandler, datasourceService.datasourcePage(requestHandler));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("数据源分页查询错误",e);
                Utils.processException(e, requestHandler);
            }
        });
        return this.api;
    }
}
