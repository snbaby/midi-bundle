package com.seadun.midi.equipment.service;


import java.util.Date;
import java.util.List;

import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.Alarm;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AlarmService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public Page getAlarmPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "upt_time desc";
        Alarm alarm = new Alarm();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            alarm = new Alarm(query);
        }
        SampleItem simpleItem = new SampleItem(alarm).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "status", filter.getString("status"));
            getSampleItem(simpleItem, "detail", filter.getString("detail"));
            getSampleItem(simpleItem, "equipment_code", filter.getString("equipmentCode"));
            getSampleItem(simpleItem, "equipment_name", filter.getString("equipmentName"));
            getSampleItem(simpleItem, "equipment_type_code", filter.getString("equipmentTypeCode"));
            getSampleItem(simpleItem, "equipment_type_name", filter.getString("equipmentTypeName"));
            getSampleItem(simpleItem, "equipment_duty_user_code", filter.getString("equipmentDutyUserCode"));
            getSampleItem(simpleItem, "equipment_duty_user_name", filter.getString("equipmentDutyUserName"));
            getSampleItem(simpleItem, "equipment_duty_org_code", filter.getString("equipmentDutyOrgCode"));
            getSampleItem(simpleItem, "equipment_duty_org_name", filter.getString("equipmentDutyOrgName"));
            getSampleItem(simpleItem, "equipment_secret_code", filter.getString("equipmentSecretCode"));
            getSampleItem(simpleItem, "equipment_secret_name", filter.getString("equipmentSecretName"));
            getSampleItem(simpleItem, "location_code", filter.getString("locationCode"));
            getSampleItem(simpleItem, "location_name", filter.getString("locationName"));
            getSampleItem(simpleItem, "location_status", filter.getString("locationStatus"));
            getSampleItem(simpleItem, "cabinet_code", filter.getString("cabinetCode"));
            getSampleItem(simpleItem, "cabinet_name", filter.getString("cabinetName"));
            getSampleItem(simpleItem, "cabinet_model", filter.getString("cabinetModel"));
            getSampleItem(simpleItem, "area_code", filter.getString("areaCode"));
            getSampleItem(simpleItem, "area_name", filter.getString("areaName"));
            getSampleItem(simpleItem, "area_detailed_location", filter.getString("areaDetailedLocation"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<Alarm> maps = ctx.eFindAll(Alarm.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Alarm> maps2 = ctx.eFindAll(Alarm.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Alarm getAlarmDetail(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        Alarm alarm = new Alarm();
        alarm.setCode(code);
        return alarm.load();
    }

    public void updateAlarmDetail(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String detail = routingContext.getBodyAsJson().getString("detail");
        if (StringUtils.isNotBlank(detail) && detail.length() > 200) {
            throw new MidiException(400, "详情描述大于200");
        }
        Alarm alarm = new Alarm();
        alarm.setCode(code);
        alarm.setUptTime(new Date());
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        alarm.setUptUser(userCode);
        alarm.setDetail(detail);
        alarm.update(SqlOption.IGNORE_NULL);
    }
}
