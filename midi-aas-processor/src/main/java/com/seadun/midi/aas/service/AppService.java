package com.seadun.midi.aas.service;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.*;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.github.drinkjava2.jdbpro.JDBPRO.*;

public class AppService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void updateApp(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String name = bodyAsJson.getString("name");
        String code = routingContext.pathParam("code");
        String description = bodyAsJson.getString("description");
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        if (name != null && name.length() > 45) {
            throw new MidiException(400, "应用名称长度不能大于45");
        }

        if (description != null && description.length() > 200) {
            throw new MidiException(400, "应用描述长度不能大于200");
        }

        TbApp tbApp = new TbApp();
        tbApp.setCode(code);
        if (!tbApp.exist()) {
            throw new MidiException(404, "应用不存在");
        }
        tbApp.setName(name);
        tbApp.setUptCode(userCode);
        tbApp.setCrtTime(new Date());
        tbApp.setDescription(description);
        tbApp.update(SqlOption.IGNORE_NULL);
    }

    public Page findAppRolePageList(RoutingContext routingContext) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";

        String sqlTemplate = "select re.* from tb_role re left JOIN tb_role_app_relation arr on arr.role_code = re.code " +
                "left JOIN tb_app ap on arr.app_code = ap.code where arr.app_code = ?";
        String sqlResult = "re.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(param(routingContext.pathParam("appCode")));
        conditionAttrList.add(new ConditionAttr("re.code", "roleCode"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by re." + orderBy));

        return Utils.queryPage(TbRole.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }


    public Page findAppResourcePageList(RoutingContext routingContext) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        String sqlTemplate = "SELECT %s from " +
                "tb_resource re " +
                "left JOIN tb_app_resource_relation arr on arr.resource_code = re.code " +
                "left JOIN tb_app ap on arr.app_code = ap.code where arr.app_code = ?";
        String sqlResult = "re.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(param(routingContext.pathParam("appCode")));
        conditionAttrList.add(new ConditionAttr("re.code", "resourceCode"));
        conditionAttrList.add(new ConditionAttr("re.name", "resourceName"));
        conditionAttrList.add(new ConditionAttr("re.type", "resourceType"));
        conditionAttrList.add(new ConditionAttr("re.parent_code", "parentCode"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by re." + orderBy));
        return Utils.queryPage(TbResource.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    private Object[] getAppResourcePage(RoutingContext routingContext, JsonObject bodyAsJson, String orderBy) {
        return new Object[]{"SELECT re.* from " +
                "tb_resource re " +
                "left JOIN tb_app_resource_relation arr on arr.resource_code = re.code " +
                "left JOIN tb_app ap on arr.app_code = ap.code where arr.app_code = ?",
                param(routingContext.pathParam("appCode")),

                notNull("and re.code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("resourceCode") : null),
                notNull("and re.name = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("resourceName") : null),
                notNull("and re.type = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("resourceType") : null),
                notNull("and re.parent_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("parentCode") : null),
                notNull("and re.code like ? ", bodyAsJson.getJsonObject("filter") != null ?
                        (bodyAsJson.getJsonObject("filter").getString("resourceCode") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("resourceCode") + "%" : null) : null),
                notNull("and re.name like ? ", bodyAsJson.getJsonObject("filter") != null ?
                        (bodyAsJson.getJsonObject("filter").getString("resourceName") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("resourceName") + "%" : null) : null),
                notNull("and re.type like ? ", bodyAsJson.getJsonObject("filter") != null ?
                        (bodyAsJson.getJsonObject("filter").getString("resourceType") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("resourceType") + "%" : null) : null),
                notNull("and re.parent_code like ? ", bodyAsJson.getJsonObject("filter") != null ?
                        (bodyAsJson.getJsonObject("filter").getString("parentCode") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("parentCode") + "%" : null) : null),
                sql(" order by re." + orderBy)};
    }

    @TX
    public void deleteAppRole(RoutingContext routingContext, SqlBoxContext ctx) {
        String appCode = routingContext.pathParam("appCode");
        String roleCode = routingContext.getBodyAsJson().getString("roleCode");
        ctx.nExecute("delete from tb_role_app_relation where app_code = ? and role_code = ?", appCode, roleCode);
    }

    @TX
    public void deleteAppResource(RoutingContext routingContext, SqlBoxContext ctx) {
        String appCode = routingContext.pathParam("appCode");
        String resourceCode = routingContext.getBodyAsJson().getString("resourceCode");
        ctx.nExecute("delete from tb_app_resource_relation where app_code = ? and resource_code = ?", appCode, resourceCode);
    }

    @TX
    public void insertAppRole(RoutingContext routingContext) throws MidiException {
        String appCode = routingContext.pathParam("appCode");
        String roleCode = routingContext.getBodyAsJson().getString("roleCode");
        TbRoleAppRelation relation = new TbRoleAppRelation();
        TbApp app = new TbApp();
        app.setCode(appCode);
        if (!app.exist()) {
            throw new MidiException(400, "应用code不存在,请传入正确的code");
        }
        TbRole role = new TbRole();
        role.setCode(roleCode);
        if (!role.exist()) {
            throw new MidiException(400, "角色code不存在,请传入正确的code");
        }
        relation.setId(UUID.randomUUID().toString());
        relation.setAppCode(appCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        relation.setCrtCode(userCode);
        relation.setCrtTime(new Date());
        relation.setRoleCode(roleCode);
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_role_app_relation where role_code = ? and app_code = ?",
                param(roleCode, appCode));
        if (i > 0) {
            throw new MidiException(400, "已存在应用与角色关系");
        }
        relation.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertAppResource(RoutingContext routingContext, SqlBoxContext ctx) throws MidiException {
        String appCode = routingContext.pathParam("appCode");
        TbApp app = new TbApp();
        app.setCode(appCode);
        if (!app.exist()) {
            throw new MidiException(400, "应用code不存在,请传入正确的code");
        }
        String resourceCode = routingContext.getBodyAsJson().getString("resourceCode");
        TbResource tbResource = new TbResource();
        tbResource.setCode(resourceCode);
        if (!tbResource.exist()) {
            throw new MidiException(400, "资源code不存在,请传入正确的code");
        }
        TbAppResourceRelation relation = new TbAppResourceRelation();
        relation.setId(UUID.randomUUID().toString());
        relation.setAppCode(appCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        relation.setCrtCode(userCode);
        relation.setCrtTime(new Date());
        relation.setResourceCode(resourceCode);
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_app_resource_relation where resource_code = ? and app_code = ?",
                param(resourceCode, appCode));
        if (i > 0) {
            throw new MidiException(400, "已存在应用与资源关系");
        }
        relation.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertApp(RoutingContext routingContext) throws MidiException {
        String name = routingContext.getBodyAsJson().getString("name");
        String code = routingContext.getBodyAsJson().getString("code");
        String description = routingContext.getBodyAsJson().getString("description");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "应用名称不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "应用唯一标识或应用名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(description)) {
            if (description.length() > 200) {
                throw new MidiException(400, "应用描述长度不能大于200");
            }
        }
        TbApp tbApp = new TbApp();
        tbApp.setCode(code);
        if (tbApp.exist()) {
            throw new MidiException(400, "应用唯一标识已存在");
        }
        tbApp.setName(name);
        tbApp.setDescription(description);
        tbApp.setCrtTime(new Date());
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        tbApp.setCrtCode(userCode);
        tbApp.setUptCode(userCode);
        tbApp.setUptTime(new Date());
        tbApp.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteAppByCode(RoutingContext routingContext) {
        String code = routingContext.pathParam("code");
        TbApp tbApp = new TbApp();
        tbApp.setCode(code);
        tbApp.deleteTry();
    }

    public Page findAppPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        TbApp tbApp = new TbApp();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            tbApp = new TbApp(query);
        }
        SampleItem simpleItem = new SampleItem(tbApp).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "CODE", filter.getString("code"));
            getSampleItem(simpleItem, "NAME", filter.getString("name"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<TbApp> maps = ctx.eFindAll(TbApp.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<TbApp> maps2 = ctx.eFindAll(TbApp.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }
}
