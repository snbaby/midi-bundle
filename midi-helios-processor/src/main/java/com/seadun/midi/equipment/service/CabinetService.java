package com.seadun.midi.equipment.service;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.Area;
import com.seadun.midi.equipment.entity.Cabinet;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class CabinetService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertCabinet(RoutingContext routingContext) throws MidiException {
        String name = routingContext.getBodyAsJson().getString("name");
        String areaCode = routingContext.getBodyAsJson().getString("areaCode");
        if (StringUtils.isBlank(name)) {
            throw new MidiException(400, "名称不能为空");
        }
        if (name.length() > 45) {
            throw new MidiException(400, "名称长度不能大于45");
        }
       
        Area area = new Area();
        area.setCode(areaCode);
        if (!area.exist()) {
            throw new MidiException(400, "添加失败 场所不存在");
        }
        Cabinet cabinet = new Cabinet();
        cabinet.setCode(UUID.randomUUID().toString());
        cabinet.setName(name);
        cabinet.setStatus("1");
        cabinet.setAreaCode(areaCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        cabinet.setUptUser(userCode);
        cabinet.setUptTime(new Date());
        cabinet.setCrtTime(new Date());
        cabinet.setCrtUser(userCode);
        cabinet.insert(SqlOption.IGNORE_NULL);
    }

    public Page findCabinetPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        Cabinet cabinet = new Cabinet();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            cabinet = new Cabinet(query);
        }
        SampleItem simpleItem = new SampleItem(cabinet).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "code", filter.getString("code"));
            getSampleItem(simpleItem, "name", filter.getString("name"));
            getSampleItem(simpleItem, "model", filter.getString("model"));
            getSampleItem(simpleItem, "area_code", filter.getString("areaCode"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<Cabinet> maps = ctx.eFindAll(Cabinet.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Cabinet> maps2 = ctx.eFindAll(Cabinet.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Cabinet findByCabinetCode(RoutingContext routingContext) {
        String code = routingContext.pathParam("code");
        Cabinet cabinet = new Cabinet();
        cabinet.setCode(code);
        return cabinet.load();
    }

    @TX
    public void deleteAreaByCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        ctx.nExecute("delete from cabinet where code = ?", code);
    }

    @TX
    public void updateAreaStatus(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String status = routingContext.getBodyAsJson().getString("status");
        if (StringUtils.isNotBlank(status)) {
            if("3".equals(status)){
                throw new MidiException(400,"机柜已被废弃不可更改状态");
            }
            Pattern psex = compile("^[1-3]{1}$");
            Matcher msex = psex.matcher(status);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确状态");
            }
        }
        if(StringUtils.isNotBlank(status) && "3".equals(status)){
            SqlBoxContext sqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
            sqlBoxContext.nExecute("delete from location where cabinet_code = ?",code);
        }
        if(StringUtils.isNotBlank(status) && "2".equals(status)){
            SqlBoxContext sqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
            sqlBoxContext.nExecute("update location set status = 2 where cabinet_code = ?",code);
        }
        if(StringUtils.isNotBlank(status) && "1".equals(status)){
            SqlBoxContext sqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
            sqlBoxContext.nExecute("update location set status = 1 where cabinet_code = ?",code);
        }

        Cabinet cabinet = new Cabinet();
        //返回成功与否
        cabinet.setCode(code);
        if (!cabinet.exist()) {
            throw new MidiException(400, "修改失败 机柜不存在");
        }
        cabinet.setStatus(status);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        cabinet.setUptUser(userCode);
        cabinet.setUptTime(new Date());
        cabinet.updateTry(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updateCabinet(RoutingContext routingContext) throws MidiException{
        String code = routingContext.pathParam("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String model = routingContext.getBodyAsJson().getString("model");
        String areaCode = routingContext.getBodyAsJson().getString("areaCode");
        if (StringUtils.isBlank(code)) {
            throw new MidiException(400, "唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 45) {
                throw new MidiException(400, "名称长度不能大于45");
            }
        }
        if (code.length() > 45) {
            throw new MidiException(400, "唯一标识长度不能大于45");
        }
        if(StringUtils.isNotBlank(areaCode)){
            Area area = new Area();
            area.setCode(areaCode);
            if (!area.exist()) {
                throw new MidiException(400, "修改失败 场所不存在");
            }
        }
        Cabinet cabinet = new Cabinet();
        cabinet.setCode(code);
        if (!cabinet.exist()) {
            throw new MidiException(400, "机柜不存在");
        }
        cabinet.setName(name);
        cabinet.setModel(model);
        cabinet.setAreaCode(areaCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        cabinet.setUptUser(userCode);
        cabinet.setUptTime(new Date());
        cabinet.update(SqlOption.IGNORE_NULL);

    }

}
