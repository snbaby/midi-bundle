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

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.drinkjava2.jdbpro.JDBPRO.param;

public class LocationService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertLocation(RoutingContext routingContext) throws MidiException {
        String name = routingContext.getBodyAsJson().getString("name");
        if (StringUtils.isBlank(name)) {
            throw new MidiException(400, "名称不能为空");
        }
        if (name.length() > 45) {
            throw new MidiException(400, "名称长度不能大于45");
        }
        String cabinetCode = routingContext.getBodyAsJson().getString("cabinetCode");
        if (StringUtils.isBlank(cabinetCode)) {
            throw new MidiException(400, "机柜编码不能为空");
        }
        if (StringUtils.isNotBlank(cabinetCode)) {
            Cabinet cabinet = new Cabinet();
            cabinet.setCode(cabinetCode);
            if (!cabinet.exist()) {
                throw new MidiException(400, "添加失败 机柜code不存在。");
            }
            cabinet.load(SqlOption.IGNORE_NULL);
            //机柜是停用状态不能添加位置
            if("2".equals(cabinet.getStatus())){
                throw new MidiException(400, "机柜是停用状态,不能添加位置。");
            }
        }
        String status = routingContext.getBodyAsJson().getString("status");
        if (StringUtils.isBlank(status)) {
            status = "1";
        }
        if (StringUtils.isNotBlank(status)) {
            Pattern psex = Pattern.compile("^[1-2]{1}$");
            Matcher msex = psex.matcher(status);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的位置状态");
            }
        }
        Location location = new Location();
        location.setCode(UUID.randomUUID().toString());
        location.setName(name);
        location.setStatus(status);
        location.setCabinetCode(cabinetCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        location.setUptUser(userCode);
        location.setUptTime(new Date());
        location.setCrtTime(new Date());
        location.setCrtUser(userCode);
        location.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertLocationPort(RoutingContext routingContext) throws MidiException {
        String locationCode = routingContext.pathParam("locationCode");
        String portCode = routingContext.getBodyAsJson().getString("portCode");
        if (StringUtils.isBlank(locationCode) || StringUtils.isBlank(portCode)) {
            throw new MidiException(400, "位置唯一标识或端口唯一标识不能为空");
        }
        Location location = new Location();
        location.setCode(locationCode);
        location.load(SqlOption.IGNORE_NULL);
        String cabinetCode = location.getCabinetCode();
        Cabinet cabinet = new Cabinet();
        cabinet.setCode(cabinetCode);
        if("2".equals(cabinet.load(SqlOption.IGNORE_NULL).getStatus())){
            throw new MidiException(400, "机柜状态为停用，不能添加端口；如需使用，先启用机柜。");
        }
        if (!location.exist()) {
            throw new MidiException(400, "添加失败 位置不存在");
        }
        Port port = new Port();
        port.setCode(portCode);
        if (!port.exist()) {
            throw new MidiException(400, "添加失败 端口不存在");
        }
        port.setStatus("1");
        port.update(SqlOption.IGNORE_NULL);
        PortLocationRelation portLocationRelation = new PortLocationRelation();
        portLocationRelation.setLocationCode(locationCode);
        portLocationRelation.setPortCode(portCode);
        portLocationRelation.setId(UUID.randomUUID().toString());
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        portLocationRelation.setCrtTime(new Date());
        portLocationRelation.setCrtUser(userCode);
        portLocationRelation.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertLocationEquipment(RoutingContext routingContext) throws MidiException {
        String locationCode = routingContext.pathParam("locationCode");
        String equipmentCode = routingContext.getBodyAsJson().getString("equipmentCode");
        if (StringUtils.isBlank(locationCode) || StringUtils.isBlank(equipmentCode)) {
            throw new MidiException(400, "位置唯一标识或端口唯一标识不能为空");
        }
        Location location = new Location();
        location.setCode(locationCode);
        location.load(SqlOption.IGNORE_NULL);
        String cabinetCode = location.getCabinetCode();
        Cabinet cabinet = new Cabinet();
        cabinet.setCode(cabinetCode);
        if("2".equals(cabinet.load(SqlOption.IGNORE_NULL).getStatus())){
            throw new MidiException(400, "机柜状态为停用，不能添加绑定设备；如需使用，先启用机柜。");
        }
        if (!location.exist()) {
            throw new MidiException(400, "添加失败 位置不存在");
        }
        Equipment port = new Equipment();
        port.setCode(equipmentCode);
        if (!port.exist()) {
            throw new MidiException(400, "添加失败 重要设备不存在");
        }
        EquipmentLocationRelation relation = new EquipmentLocationRelation();
        relation.setId(UUID.randomUUID().toString());
        relation.setLocationCode(locationCode);
        relation.setEquipmentCode(equipmentCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        relation.setCrtTime(new Date());
        relation.setCrtUser(userCode);
        relation.insert(SqlOption.IGNORE_NULL);
    }

    public Page findLocationPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        Location location = new Location();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            location = new Location(query);
        }
        SampleItem simpleItem = new SampleItem(location).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "code", filter.getString("code"));
            getSampleItem(simpleItem, "name", filter.getString("name"));
            getSampleItem(simpleItem, "status", filter.getString("status"));
            getSampleItem(simpleItem, "cabinet_code", filter.getString("cabinetCode"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<Location> maps = ctx.eFindAll(Location.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Location> maps2 = ctx.eFindAll(Location.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Page equipmentNotOnLocationPage(RoutingContext routingContext, SqlBoxContext ctx) {
        List<Map<String, Object>> maps = ctx.iQueryForMapList(
                "SELECT * from equipment WHERE equipment.code not in" +
                        "(SELECT elr.equipment_code from equipment_location_relation elr) and area_code = ? and status = '1'",
                param(routingContext.pathParam("locationCode"))
        );
        List<Map<String, Object>> maps2 = ctx.iQueryForMapList(
                "SELECT * from equipment WHERE equipment.code not in" +
                        "(SELECT elr.equipment_code from equipment_location_relation elr) and area_code = ? and status = '1'",
                param(routingContext.pathParam("locationCode"))
        );
        Page page = new Page();
        page.setData(maps);
        page.setTotal(maps2.size());
        return page;
    }

    public Page portNotOnLocation(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        List<Map<String, Object>> maps = ctx.iQueryForMapList(
                "SELECT * from port WHERE port.code not in" +
                        "(SELECT plr.port_code from port_location_relation plr) and detect_code = ? and status = ?",
                param(routingContext.getBodyAsJson().getString("detectCode")),
                param(routingContext.getBodyAsJson().getString("status")),
                JSQLBOX.pagin(pageNo, pageSize)
        );
        List<Map<String, Object>> maps2 = ctx.iQueryForMapList(
                "SELECT * from port WHERE port.code not in" +
                        "(SELECT plr.port_code from port_location_relation plr) and detect_code = ? and status = ?",
                param(routingContext.getBodyAsJson().getString("detectCode")),
                param(routingContext.getBodyAsJson().getString("status"))
        );
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Location findByLocationCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        Location location = new Location();
        location.setCode(code);
        Location load = location.load();
        return load;
    }

    public Port findLocationPort(RoutingContext routingContext, SqlBoxContext ctx) throws MidiException{
        String locationCode = routingContext.pathParam("locationCode");
        String object = ctx.iQueryForObject("select p.code from port p left join port_location_relation plr " +
                " on p.code = plr.port_code where plr.location_code = '" + locationCode + "'");
        if(StringUtils.isEmpty(object)){
            return null;
        }
        Port port1 = new Port();
        port1.setCode(object);
        Port load = port1.load();
        return load;
    }

    public Equipment findLocationEquipment(RoutingContext routingContext, SqlBoxContext ctx) throws MidiException{
        String locationCode = routingContext.pathParam("locationCode");

        String equipmentCode = ctx.iQueryForObject("select e.code from equipment e left join equipment_location_relation elr " +
                "on e.code = elr.equipment_code where elr.location_code = '" + locationCode + "'");
        if(StringUtils.isEmpty(equipmentCode)){
            return null;
        }
        Equipment equipment = new Equipment();
        equipment.setCode(equipmentCode);
        Equipment load = equipment.load();
        return load;
    }

    @TX
    public void deleteLocationByCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        ctx.nExecute("delete from location where code = ?", code);
    }

    @TX
    public void deleteLocationPort(RoutingContext routingContext, SqlBoxContext ctx) {
        String portCode = routingContext.getBodyAsJson().getString("portCode");
        String locationCode = routingContext.pathParam("locationCode");
        Port port = new Port();
        port.setCode(portCode);
        port.setStatus("2");
        port.update(SqlOption.IGNORE_NULL);
        ctx.nExecute("delete from port_location_relation where port_code = ? and location_code = ?", portCode, locationCode);
        ctx.nExecute("update alarm set status = 1 where location_code = ?",locationCode);
        LocationService locationService = new LocationService();
        Equipment locationEquipment = locationService.findLocationEquipment(routingContext, ctx);
        if(locationEquipment != null){
            locationEquipment.setStatus("1");
            locationEquipment.update(SqlOption.IGNORE_NULL);
        }
    }

    @TX
    public void deleteLocationEquipment(RoutingContext routingContext, SqlBoxContext ctx) {
        String equipmentCode = routingContext.getBodyAsJson().getString("equipmentCode");
        String locationCode = routingContext.pathParam("locationCode");
        ctx.nExecute("delete from equipment_location_relation where equipment_code = ? and location_code = ?", equipmentCode, locationCode);
        ctx.nExecute("update alarm set status = 1 where location_code = ? and equipment_code = ?",locationCode,equipmentCode);
        ctx.nExecute("update equipment set status = 1 where  code = ?",equipmentCode);
    }

    public Page findEquipmentByAreaCodePageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        List<Map<String, Object>> maps = ctx.iQueryForMapList(
                getEquipmentByAreaCodePage(routingContext),
                JSQLBOX.pagin(pageNo, pageSize));
        List<Map<String, Object>> maps2 = ctx.iQueryForMapList(
                getEquipmentByAreaCodePage(routingContext));
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    private Object[] getEquipmentByAreaCodePage(RoutingContext routingContext) {
        return new Object[]{"SELECT e.* from equipment_location_relation elr left JOIN equipment e on e.code = elr.equipment_code " +
                "left join location loc on elr.location_code " +
                " = loc.code left JOIN cabinet cab on loc.cabinet_code = cab.code left join area on area.code = cab.area_code " +
                "where area.code = ?",
                param(routingContext.pathParam("areaCode"))};
    }

    @TX
    public void updateLocationStatus(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String status = routingContext.getBodyAsJson().getString("status");
        if (StringUtils.isBlank(status)) {
            throw new MidiException(400, "状态不能为空");
        }
        if (StringUtils.isNotBlank(status)) {
            Pattern pattern = Pattern.compile("^[1-2]{1}$");
            Matcher matcher = pattern.matcher(status);
            if (!matcher.matches()) {
                throw new MidiException(400, "请传入正确的位置状态");
            }
        }
        Location location = new Location();
        //返回成功与否
        location.setCode(code);
        if (!location.exist()) {
            throw new MidiException(400, "修改失败 位置不存在");
        }
        location.setStatus(status);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        location.setUptUser(userCode);
        location.setUptTime(new Date());
        location.updateTry(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updateLocation(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String cabinetCode = routingContext.getBodyAsJson().getString("cabinetCode");
        if (StringUtils.isBlank(code)) {
            throw new MidiException(400, "唯一标识不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "唯一标识或名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(cabinetCode)) {
            Cabinet cabinet = new Cabinet();
            if (!cabinet.exist()) {
                throw new MidiException(400, "修改失败 机柜不存在");
            }
        }
        Location location = new Location();
        location.setCode(code);
        if (!location.exist()) {
            throw new MidiException(400, "修改失败 位置不存在");
        }
        location.setName(name);
        location.setCabinetCode(cabinetCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        location.setUptUser(userCode);
        location.setUptTime(new Date());
        location.update(SqlOption.IGNORE_NULL);

    }

}
