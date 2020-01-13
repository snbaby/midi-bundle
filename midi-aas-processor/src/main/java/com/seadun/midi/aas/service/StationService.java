package com.seadun.midi.aas.service;

import static com.github.drinkjava2.jdbpro.JDBPRO.notNull;
import static com.github.drinkjava2.jdbpro.JDBPRO.param;
import static com.github.drinkjava2.jdbpro.JDBPRO.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seadun.midi.aas.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class StationService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    public Page getPersonStation(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        // TODO  tb_org_user_relation 表 修改为 tb_org_person_relation
        String sqlTemplate = "SELECT %s from tb_org_station_relation tosr LEFT JOIN tb_tosr_person_relation ttpr on tosr.id = ttpr.tosr_id left join tb_person tp on tp.code = ttpr.person_code where 1=1";
        String sqlResult = "tp.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(sql(" and tosr.station_code = ? "));
        conditionList.add(param(routingContext.pathParam("stationCode")));

        conditionAttrList.add(new ConditionAttr("tp.code", "code"));
        conditionAttrList.add(new ConditionAttr("tp.name", "name"));
        conditionAttrList.add(new ConditionAttr("tp.sex", "sex"));
        conditionAttrList.add(new ConditionAttr("tp.security_classification", "securityClassification"));
        conditionAttrList.add(new ConditionAttr("tp.status", "status"));
        conditionAttrList.add(new ConditionAttr("tp.email", "email"));
        conditionAttrList.add(new ConditionAttr("tp.mobile", "mobile"));
        conditionAttrList.add(new ConditionAttr("tp.telephone", "telephone"));
        conditionAttrList.add(new ConditionAttr("tp.address", "address"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by tp." + orderBy));
        return Utils.queryPage(TbPerson.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }
    @TX
    public void createStation(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String name = bodyAsJson.getString("name");
        String code = bodyAsJson.getString("code");
        String description = bodyAsJson.getString("description");
        String securityClassification = bodyAsJson.getString("securityClassification");
        String type = bodyAsJson.getString("type");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "岗位唯一标识或名称不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "岗位唯一标识或名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 200) {
            throw new MidiException(400, "描述长度不能大于200");
        }
        if (StringUtils.isNotBlank(securityClassification)) {
            Pattern psex = Pattern.compile("^[0-3]{1}$");
            Matcher msex = psex.matcher(securityClassification);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的岗位等级");
            }
        }
        TbStation station = new TbStation(bodyAsJson);
        if (station.exist()) {
            throw new MidiException(400, "创建失败，岗位唯一标识已存在");
        }
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        station.setCrtCode(crtUserCode);
        station.setUptCode(crtUserCode);
        station.setCrtTime(new Date());
        station.setUptTime(new Date());
        if(StringUtils.isNoneBlank(type)) {
        	station.setType(type);
        }
        station.insert(SqlOption.IGNORE_NULL);
    }

    public Page getStationPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy")
                : "upt_time desc";

        String sqlTemplate = "select %s from tb_station ts where 1=1 ";
        String sqlResult = "ts.*";

        List<SqlItem> conditionList = new ArrayList<>();

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("ts.code", "code"));
        conditionAttrList.add(new ConditionAttr("ts.type", "type"));
        conditionAttrList.add(new ConditionAttr("ts.name", "name"));
        conditionAttrList.add(new ConditionAttr("ts.security_classification", "securityClassification"));
        conditionAttrList.add(new ConditionAttr("ts.description", "description"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by ts." + orderBy));

        return Utils.queryPage(TbStation.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    @TX
    public void updateStation(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String code = routingContext.pathParam("code");
        String name = bodyAsJson.getString("name");
        String description = bodyAsJson.getString("description");
        String securityClassification = bodyAsJson.getString("securityClassification");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "岗位唯一标识或名称不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "岗位唯一标识或名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 200) {
            throw new MidiException(400, "描述长度不能大于200");
        }
        if (StringUtils.isNotBlank(securityClassification)) {
            Pattern psex = Pattern.compile("^[0-3]{1}$");
            Matcher msex = psex.matcher(securityClassification);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的岗位等级");
            }
        }
        TbStation station = new TbStation();
        station.setCode(code);
        if (!station.exist()) {
            throw new MidiException(404, "岗位不存在");
        }
        station.setName(name);
        station.setDescription(description);
        station.setSecurityClassification(securityClassification);
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        station.setUptCode(crtUserCode);
        station.setUptTime(new Date());
        station.update(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertStationDuty(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbStation station = new TbStation();
        station.setCode(routingContext.pathParam("stationCode"));
        if (!station.exist()) {
            throw new MidiException(400, "添加失败，岗位编码不存在");
        }
        TbDuty duty = new TbDuty();
        duty.setCode(bodyAsJson.getString("dutyCode"));
        if (!duty.exist()) {
            throw new MidiException(400, "添加失败，岗位职责编码不存在");
        }
        TbStationDutyRelation stationDuty = new TbStationDutyRelation();
        stationDuty.setId(UUID.randomUUID().toString());
        stationDuty.setStationCode(routingContext.pathParam("stationCode"));
        stationDuty.setDutyCode(bodyAsJson.getString("dutyCode"));
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        stationDuty.setCrtCode(crtUserCode);
        stationDuty.setCrtTime(new Date());
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue(
                "select count(*) from tb_station_duty_relation where station_code = ? and duty_code = ?",
                param(routingContext.pathParam("stationCode"), bodyAsJson.getString("dutyCode")));
        if (i > 0) {
            throw new MidiException(400, "已存在岗位与职责关系");
        }
        stationDuty.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteStationDuty(RoutingContext routingContext, SqlBoxContext ctx) {
        String stationCode = routingContext.pathParam("stationCode");
        String dutyCode = routingContext.getBodyAsJson().getString("dutyCode");
        ctx.nExecute("delete from tb_station_duty_relation where station_code = ? and duty_code = ?",
                stationCode, dutyCode);
    }

    public Page getStationDutyPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy")
                : "crt_time desc";

        String sqlTemplate = "select %s from tb_duty left join tb_station_duty_relation "
                + "on tb_station_duty_relation.duty_code = tb_duty.code " + "where 1=1 ";

        String sqlResult = "*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
//        conditionList.add(
//                notNull(" and tb_station_duty_relation.station_code = ? ", routingContext.pathParam("stationCode")));
//        conditionList.add(notNull("and tb_duty.code = ? ",
//                bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyCode")
//                        : null));
        conditionAttrList.add(new ConditionAttr(" tb_station_duty_relation.station_code", "stationCode"));
        conditionAttrList.add(new ConditionAttr(" tb_duty.code", "dutyCode"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by tb_duty." + orderBy));

        return Utils.queryPage(TbDuty.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());

    }

    @TX
    public void deleteStation(RoutingContext routingContext, SqlBoxContext ctx) {
        String stationCode = routingContext.pathParam("code");
        int stationDuty = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue(
                "select count(*) as total from tb_station_duty_relation where station_code = ?", param(stationCode));
        if (stationDuty > 0) {
            throw new MidiException(400, "删除失败,当前岗位已绑定职责");
        }
        int orgStation = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue(
                "select count(*) as total from tb_org_station_relation where station_code = ?", param(stationCode));
        if (orgStation > 0) {
            throw new MidiException(400, "删除失败,当前岗位已绑定组织");
        }
        ctx.nExecute("delete from tb_station where code = ?", stationCode);
    }

}
