package com.seadun.midi.equipment.service;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.config.DataSourceCfgAas;
import com.seadun.midi.equipment.entity.*;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.drinkjava2.jdbpro.JDBPRO.notNull;
import static java.util.regex.Pattern.*;

@Log4j2
public class AreaService extends AbstractVerticle {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertArea(RoutingContext routingContext) throws MidiException {
        String name = routingContext.getBodyAsJson().getString("name");
        String dutyUserCode = routingContext.getBodyAsJson().getString("dutyUserCode");
        String dutyOrgCode = routingContext.getBodyAsJson().getString("dutyOrgCode");
        String detailedLocation = routingContext.getBodyAsJson().getString("detailedLocation");
        if (StringUtils.isBlank(name)) {
            throw new MidiException(400, "名称不能为空");
        }
        if (name.length() > 45) {
            throw new MidiException(400, "名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(dutyUserCode)) {
            TbPerson tbPerson = new TbPerson();
            tbPerson.setCode(dutyUserCode);
            if (!tbPerson.exist()) {
                throw new MidiException(400, "添加失败 职责人员不存在");
            }
        }

        if (StringUtils.isNotBlank(dutyOrgCode)) {
            TbOrg tbOrg = new TbOrg();
            tbOrg.setCode(dutyOrgCode);
            if (!tbOrg.exist()) {
                throw new MidiException(400, "添加失败 组织人员不存在");
            }
        }
        Area area = new Area();
        area.setCode(UUID.randomUUID().toString());
        area.setName(name);
        area.setDutyUserCode(dutyUserCode);
        area.setDutyOrgCode(dutyOrgCode);
        area.setStatus("1");
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        area.setUptUser(userCode);
        area.setUptTime(new Date());
        area.setCrtTime(new Date());
        area.setCrtUser(userCode);
        area.setDetailedLocation(detailedLocation);
        area.insert(SqlOption.IGNORE_NULL);
    }

    public List<Map<String, Object>> findAreaPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        int pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        int pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        List<Map<String, Object>> areas = ctx.iQueryForMapList(
                "select a.code code, a.name name,a.status status,a.detailed_location detailedLocation,a.crt_user crtUser,a.crt_time crtTime,a.upt_user uptUser," +
                        "a.upt_time uptTime,a.duty_user_code dutyUserCode,a.duty_org_code dutyOrgCode, " +
                        "tu.code as personCode,tu.name as personName,tu.mobile,tu.telephone,tu.email from area a LEFT JOIN tb_person tu on a.duty_user_code = tu.`code`" +
                        "where 1=1 ",
                notNull(" and a.code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("code") : null),
                notNull(" and a.name = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("name") : null),
                notNull(" and a.duty_user_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyUserCode") : null),
                notNull(" and a.duty_org_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyOrgCode") : null),
                notNull(" and a.code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("code") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("code") + "%" : null) : null),
                notNull(" and a.name like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("name") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("name") + "%" : null) : null),
                notNull(" and a.duty_user_code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("dutyUserCode") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("dutyUserCode") + "%" : null) : null),
                notNull(" and a.duty_org_code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("dutyOrgCode") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("dutyOrgCode") + "%" : null) : null),
                JSQLBOX.pagin(pageNo, pageSize));
        return areas;
    }

    public List<Map<String, Object>> findAreaIsEnablePage() {
        SysDatasource sysDatasource = new SysDatasource();
        SampleItem simpleItem = new SampleItem(sysDatasource);
        List<SysDatasource> datasources = new SqlBoxContext((DataSource) BeanBox.getBean(DataSourceCfgAas.class)).eFindAll(SysDatasource.class,simpleItem);
        List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
        for (SysDatasource datasource : datasources) {
            Map<String, Object> area2 = new HashMap<>();
            if ("1".equals(datasource.getStatus())) {
                area2.put("name", datasource.getName());
                area2.put("isEnable", false);
            } else {
                area2.put("name", datasource.getName());
                area2.put("isEnable", true);
            }
            areaList.add(area2);
        }
        return areaList;
    }

    public Area findByAreaCode(RoutingContext routingContext) {
        String code = routingContext.pathParam("code");
        Area area = new Area();
        area.setCode(code);
        return area.load();
    }

    @TX
    public void deleteAreaByCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        ctx.nExecute("delete from area where code = ?", code);
    }

    @TX
    public void updateAreaStatus(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String status = routingContext.getBodyAsJson().getString("status");
        Area area = new Area();
        area.setCode(code);
        Area load = area.load();
        if (StringUtils.isNotBlank(status)) {
            if ("3".equals(load.getStatus())) {
                throw new MidiException(400, "场所已被废弃不可更改状态");
            }
            Pattern psex = compile("^[1-3]{1}$");
            Matcher msex = psex.matcher(status);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的状态");
            }
        } else {
            throw new MidiException(400, "状态为空");
        }

        if (!area.exist()) {
            throw new MidiException(400, "场所不存在!");
        }
        if (StringUtils.isNotBlank(status) && "3".equals(status)) {
            SqlBoxContext sqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
            sqlBoxContext.nExecute("delete from cabinet where area_code = ?", code);
            sqlBoxContext.nExecute("delete from detect where area_code = ?", code);
            sqlBoxContext.nExecute("delete from equipment where area_code = ?", code);
        }
        if (StringUtils.isNotBlank(status) && "2".equals(status)) {
            SqlBoxContext sqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
            sqlBoxContext.nExecute("update cabinet set status = 2 where area_code = ?", code);
            sqlBoxContext.nExecute("update detect set status = 2 where area_code = ?", code);
            sqlBoxContext.nExecute("update equipment set status = 2 where area_code = ?", code);
            sqlBoxContext.nExecute("update location set status = 2 where cabinet_code = (select cabinet.code from cabinet where cabinet.area_code = ?)", code);
            sqlBoxContext.nExecute("update port set status = 2 where detect_code = (select detect.code from detect where area_code = ?)", code);
        }
        if (StringUtils.isNotBlank(status) && "1".equals(status)) {
            SqlBoxContext sqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
            sqlBoxContext.nExecute("update cabinet set status = 1 where area_code = ?", code);
            sqlBoxContext.nExecute("update detect set status = 1 where area_code = ?", code);
            sqlBoxContext.nExecute("update equipment set status = 1 where area_code = ?", code);
            sqlBoxContext.nExecute("update location set status = 1 where cabinet_code = (select cabinet.code from cabinet where cabinet.area_code = ?)", code);
            sqlBoxContext.nExecute("update port set status = 1 where detect_code = (select detect.code from detect where area_code = ?)", code);
        }

        area.setStatus(status);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        area.setUptUser(userCode);
        area.setUptTime(new Date());
        area.updateTry(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updateArea(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String dutyUserCode = routingContext.getBodyAsJson().getString("dutyUserCode");
        String dutyOrgCode = routingContext.getBodyAsJson().getString("dutyOrgCode");
        String detailedLocation = routingContext.getBodyAsJson().getString("detailedLocation");
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
        if (StringUtils.isNotBlank(dutyUserCode)) {
            TbPerson tbUser = new TbPerson();
            tbUser.setCode(dutyUserCode);
            if (!tbUser.exist()) {
                throw new MidiException(400, "添加失败 职责人员不存在");
            }
        }

        if (StringUtils.isNotBlank(dutyOrgCode)) {
            TbOrg tbOrg = new TbOrg();
            tbOrg.setCode(dutyOrgCode);
            if (!tbOrg.exist()) {
                throw new MidiException(400, "添加失败 组织人员不存在");
            }
        }
        Area area = new Area();
        //返回成功与否
        area.setCode(code);
        if (!area.exist()) {
            throw new MidiException(400, "场所不存在!");
        }
        area.setName(name);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        area.setUptUser(userCode);
        area.setUptTime(new Date());
        area.setDutyOrgCode(dutyOrgCode);
        area.setDutyUserCode(dutyUserCode);
        area.setDetailedLocation(detailedLocation);
        area.update(SqlOption.IGNORE_NULL);
    }
}
