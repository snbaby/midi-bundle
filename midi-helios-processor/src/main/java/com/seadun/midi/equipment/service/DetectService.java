package com.seadun.midi.equipment.service;


import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.*;
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

public class DetectService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertDetect(RoutingContext routingContext) throws MidiException {
        Integer count = 0;
        Integer ports = 7011;
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String name = bodyAsJson.getString("name");
        String secretCode = bodyAsJson.getString("secretCode");
        String model = bodyAsJson.getString("model");
        String ip = bodyAsJson.getString("ip");
        String areaCode = bodyAsJson.getString("areaCode");
        if (StringUtils.isBlank(name)) {
            throw new MidiException(400, "名称不能为空");
        }
        if (name.length() > 45) {
            throw new MidiException(400, "名称长度不能大于45");
        }
        if (StringUtils.isBlank(model)) {
            throw new MidiException(400, "多路侦测器型号不能为空");
        }
        if (model.length() > 45) {
            throw new MidiException(400, "多路侦测器型号长度不能大于45");
        }
        if (StringUtils.isNotBlank(secretCode)) {
            DicSecretCode dicSecretCode = new DicSecretCode();
            dicSecretCode.setSecretCode(secretCode);
            if (!dicSecretCode.exist()) {
                throw new MidiException(400, "添加失败 密级不存在");
            }
        }
        Area area = new Area();
        area.setCode(areaCode);
        if (!area.exist()) {
            throw new MidiException(400, "添加失败 场所不存在");
        }
        if (StringUtils.isBlank(ip)) {
            throw new MidiException(400, "ip不能为null");
        }
        if (StringUtils.isNotBlank(ip)) {
            String pattern = "(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})(\\.(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})){3}";
            Pattern r = compile(pattern);
            Matcher m = r.matcher(ip);
            if (!m.matches()) {
                throw new MidiException(400, "请添加正确的ip");
            }
        }

        Detect detect = new Detect(bodyAsJson);
        detect.setCode(UUID.randomUUID().toString());
        detect.setStatus("1"); //设置为启用
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        detect.setUptUser(userCode);
        detect.setUptTime(new Date());
        detect.setCrtTime(new Date());
        detect.setCrtUser(userCode);
        detect.insert(SqlOption.IGNORE_NULL);
        while (count < 16) {
            count++;
            Port port = new Port();
            port.setCode(UUID.randomUUID().toString());
            port.setPort(ports);
            port.setDetectCode(detect.getCode());
            port.setStatus("2");
            port.setPortName("U" + count);
            port.setUptUser(userCode);
            port.setUptTime(new Date());
            port.setCrtTime(new Date());
            port.setCrtUser(userCode);
            port.insert(SqlOption.IGNORE_NULL);
            ports+=10;
        }
    }

    @TX
    public Page getDetectPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "upt_time desc";
        Detect detect = new Detect();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            detect = new Detect(query);
        }
        SampleItem simpleItem = new SampleItem(detect).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "code", filter.getString("code"));
            getSampleItem(simpleItem, "name", filter.getString("name"));
            getSampleItem(simpleItem, "model", filter.getString("model"));
            getSampleItem(simpleItem, "ip", filter.getString("ip"));
            getSampleItem(simpleItem, "status", filter.getString("status"));
            getSampleItem(simpleItem, "secret_code", filter.getString("secretCode"));

        }
        simpleItem.sql(" order by " + orderBy);
        List<Detect> maps = ctx.eFindAll(Detect.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Detect> maps2 = ctx.eFindAll(Detect.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    @TX
    public Detect getDetectDetail(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        Detect detect = new Detect();
        detect.setCode(code);
        return detect.load();
    }

    @TX
    public void updateDetectDetail(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Detect detect = new Detect();
        String code = routingContext.pathParam("code");
        String name = bodyAsJson.getString("name");
        String secretCode = bodyAsJson.getString("secretCode");
        String model = bodyAsJson.getString("model");
        String ip = bodyAsJson.getString("ip");
        String areaCode = bodyAsJson.getString("areaCode");
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
        if (StringUtils.isBlank(model)) {
            throw new MidiException(400, "机柜型号不能为空");
        }
        if (model.length() > 45) {
            throw new MidiException(400, "机柜型号长度不能大于45");
        }
        if (StringUtils.isNotBlank(secretCode)) {
            DicSecretCode dicSecretCode = new DicSecretCode();
            dicSecretCode.setSecretCode(secretCode);
            if (!dicSecretCode.exist()) {
                throw new MidiException(400, "添加失败 密级不存在");
            }
        }
        if(StringUtils.isNotBlank(areaCode)){
            Area area = new Area();
            area.setCode(areaCode);
            if (!area.exist()) {
                throw new MidiException(400, "添加失败 场所不存在");
            }
        }

        if (StringUtils.isBlank(ip)) {
            throw new MidiException(400, "ip不能为null");
        }
        if (StringUtils.isNotBlank(ip)) {
            String pattern = "(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})(\\.(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})){3}";
            Pattern r = compile(pattern);
            Matcher m = r.matcher(ip);
            if (!m.matches()) {
                throw new MidiException(400, "请添加正确的ip");
            }
        }
        if(StringUtils.isNotBlank(code)){
            detect.setCode(code);
            if (!detect.exist()) {
                throw new MidiException(400, "多路侦测器不存在");
            }
        }
        detect.setName(name);
        detect.setSecretCode(secretCode);
        detect.setModel(model);
        detect.setIp(ip);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        detect.setUptUser(userCode);
        detect.setUptTime(new Date());
        detect.update(SqlOption.IGNORE_NULL);
    }
    @TX
    public void updateDetectStatus(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Detect detect = new Detect();
        String code = routingContext.pathParam("code");
        String status = bodyAsJson.getString("status");
        if(StringUtils.isBlank(code)){
            throw new MidiException(400, "唯一标识不能为空");
        }
        if(StringUtils.isNotBlank(code)){
            detect.setCode(code);
            if (!detect.exist()) {
                throw new MidiException(400, "多路侦测器不存在");
            }
        }
        if(StringUtils.isBlank(status)){
            throw new MidiException(400,"状态不能为空");
        }
        if(StringUtils.isNotBlank(status)){
            if("3".equals(status)){
                throw new MidiException(400,"多路侦测器已被废弃不可更改状态");
            }
            Pattern p = compile("^[1-3]{1}$");
            Matcher matcher = p.matcher(status);
            if(!matcher.matches()){
                throw new MidiException(400,"请传入正确的状态");
            }
        }
        detect.setStatus(status);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        detect.setUptUser(userCode);
        detect.setUptTime(new Date());
        detect.update(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteDetect(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        ctx.nExecute("delete port_location_relation FROM port_location_relation where port_code in (select code from port where detect_code = ?)", code);
        ctx.nExecute("delete port FROM port where detect_code = ? ", code);
        ctx.nExecute("delete detect FROM detect where code = ? ", code);
    }
}
