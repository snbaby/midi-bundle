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

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.drinkjava2.jdbpro.JDBPRO.*;

public class RoleService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void updateRole(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbRole role1 = new TbRole();
        String code = routingContext.pathParam("code");
        String name = bodyAsJson.getString("name");
        String description = bodyAsJson.getString("description");
        if (StringUtils.isBlank(code) ) {
            throw new MidiException(400, "角色唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 45) {
                throw new MidiException(400, "角色名称长度不能大于45");
            }
        }
        if (code.length() > 45) {
            throw new MidiException(400, "角色唯一标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 200) {
            throw new MidiException(400, "描述长度不能大于200");
        }

        role1.setCode(code);
        if(!role1.exist()){
            throw new MidiException(400,"修改角色失败，角色唯一标识不存在");
        }
        role1.setName(name);
        role1.setDescription(description);
        role1.update(SqlOption.IGNORE_NULL);
    }

    public Page findRoleResourcePageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";

        String sqlTemplate = "select %s from tb_resource re "
        		+ "left JOIN tb_role_resource_relation arr on arr.resource_code = re.code "
        		+ "left JOIN tb_role ap on arr.role_code = ap.code "
                + "where ";
        String sqlResult = "ap.code as roleCode,ap.name as roleName,re.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(sql(" arr.role_code = ? "));
        conditionList.add(param(routingContext.pathParam("roleCode")));
        conditionAttrList.add(new ConditionAttr("re.code","resourceCode"));
//        conditionList.add(notNull("and re.code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("resourceCode") : null));
//        conditionList.add(notNull("and re.code like ? ", bodyAsJson.getJsonObject("filter") != null ?
//                (bodyAsJson.getJsonObject("filter").getString("resourceCode") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("resourceCode") + "%" : null) : null));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by re." + orderBy));

        return Utils.queryPage(TbRoleResource.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page findRoleUserPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";

        String sqlTemplate = "SELECT %s FROM tb_role_user_relation trur LEFT JOIN tb_user tu ON trur.user_code = tu.code LEFT JOIN tb_person tp ON trur.user_code = tp.code WHERE 1 = 1";
        String sqlResult = " tu.code AS userCode,tu.name AS userName,tu.status AS userStatus,tp.code AS personCode,tp.name AS personName,tp.sex AS personSex,tp.security_classification AS personSecurityClassification,tp.status AS personStatus,tp.email AS email,tp.mobile AS mobile,tp.telephone AS telephone,tp.address AS address,tu.expire_time as expireTime ";
        List<SqlItem> conditionList = new ArrayList<>();
        conditionList.add(sql(" and trur.role_code = ? "));
        conditionList.add(param(routingContext.pathParam("roleCode")));

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("tu.code", "userCode"));
        conditionAttrList.add(new ConditionAttr("tu.name", "userName"));
        conditionAttrList.add(new ConditionAttr("tu.status", "userStatus"));

        conditionAttrList.add(new ConditionAttr("tp.code", "personCode"));
        conditionAttrList.add(new ConditionAttr("tp.name", "personName"));
        conditionAttrList.add(new ConditionAttr("tp.sex", "personSex"));
        conditionAttrList.add(new ConditionAttr("tp.security_classification", "personSecurityClassification"));
        conditionAttrList.add(new ConditionAttr("tp.status", "personStatus"));
        conditionAttrList.add(new ConditionAttr("tp.email", "email"));
        conditionAttrList.add(new ConditionAttr("tp.mobile", "mobile"));
        conditionAttrList.add(new ConditionAttr("tp.telephone", "telephone"));
        conditionAttrList.add(new ConditionAttr("tp.address", "address"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by tu." + orderBy));
        return Utils.queryPage(TbRoleUser.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page findNotInRoleUserPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";

        String sqlTemplate = "SELECT %s FROM tb_user tu LEFT JOIN tb_person tp ON tu.code = tp.code WHERE 1 = 1 and tu.status != 3";
//        String sqlTemplate = "SELECT %s FROM tb_user tu LEFT JOIN tb_person tp ON tu.code = tp.code WHERE 1 = 1 and tp.status = 2 ";
        String sqlResult = " tu.code AS userCode,tu.name AS userName,tu.status AS userStatus,tp.code AS personCode,tp.name AS personName,tp.sex AS personSex,tp.security_classification AS personSecurityClassification,tp.status AS personStatus,tp.email AS email,tp.mobile AS mobile,tp.telephone AS telephone,tp.address AS address,tu.expire_time as expireTime ";
        List<SqlItem> conditionList = new ArrayList<>();
        conditionList.add(sql(" and tu.code not in (select trur.user_code from tb_role_user_relation trur where trur.role_code = ?) "));
        conditionList.add(param(routingContext.pathParam("roleCode")));

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("tu.code", "userCode"));
        conditionAttrList.add(new ConditionAttr("tu.name", "userName"));
        conditionAttrList.add(new ConditionAttr("tu.status", "userStatus"));

        conditionAttrList.add(new ConditionAttr("tp.code", "personCode"));
        conditionAttrList.add(new ConditionAttr("tp.name", "personName"));
        conditionAttrList.add(new ConditionAttr("tp.sex", "personSex"));
        conditionAttrList.add(new ConditionAttr("tp.security_classification", "personSecurityClassification"));
        conditionAttrList.add(new ConditionAttr("tp.status", "personStatus"));
        conditionAttrList.add(new ConditionAttr("tp.email", "email"));
        conditionAttrList.add(new ConditionAttr("tp.mobile", "mobile"));
        conditionAttrList.add(new ConditionAttr("tp.telephone", "telephone"));
        conditionAttrList.add(new ConditionAttr("tp.address", "address"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by tu." + orderBy));
        return Utils.queryPage(TbRoleUser.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    @TX
    public void deleteRoleResource(RoutingContext routingContext, SqlBoxContext ctx) {
        String roleCode = routingContext.pathParam("roleCode");
        String resourceCode = routingContext.getBodyAsJson().getString("resourceCode");
        ctx.nExecute("delete from tb_role_resource_relation where role_code = ? and resource_code = ?", roleCode, resourceCode);
    }

    @TX
    public void deleteRoleUser(RoutingContext routingContext, SqlBoxContext ctx) {
        String roleCode = routingContext.pathParam("roleCode");
        String userCode = routingContext.getBodyAsJson().getString("userCode");
        ctx.nExecute("delete from tb_role_user_relation where role_code = ? and user_code = ?", roleCode, userCode);
    }

    @TX
    public void insertRoleResource(RoutingContext routingContext) throws MidiException {
        String roleCode = routingContext.pathParam("roleCode");
        String resourceCode = routingContext.getBodyAsJson().getString("resourceCode");
        if(StringUtils.isBlank(roleCode)){
            throw new MidiException(400, "角色唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(roleCode) && roleCode.length() > 45) {
            throw new MidiException(400, "角色唯一标识长度不能大于45");
        }
        TbRole tbRole = new TbRole();
        tbRole.setCode(roleCode);
        if(!tbRole.exist()){
            throw new MidiException(400,"添加失败，角色不存在");
        }
        TbRoleResourceRelation relation = new TbRoleResourceRelation();
        relation.setId(UUID.randomUUID().toString());
        relation.setRoleCode(roleCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        relation.setCrtCode(userCode);
        relation.setCrtTime(new Date());
        relation.setResourceCode(resourceCode);
        if(StringUtils.isBlank(resourceCode)){
            throw new MidiException(400, "资源唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(resourceCode)
                && resourceCode.length() > 45) {
            throw new MidiException(400, "资源唯一标识长度不能大于45");
        }
        TbResource tbResource = new TbResource();
        tbResource.setCode(resourceCode);
        if(!tbResource.exist()){
            throw new MidiException(400,"添加失败，资源不存在");
        }
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_role_resource_relation where role_code = ? and resource_code = ?",
                param(roleCode, resourceCode));
        if (i > 0) {
            throw new MidiException(404, "已存在角色与资源关系");
        }
        relation.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertRoleResources(RoutingContext routingContext,SqlBoxContext ctx) throws MidiException {
        String roleCode = routingContext.pathParam("roleCode");
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        TbRoleResourceRelation relation = new TbRoleResourceRelation();
        relation.setRoleCode(roleCode);

        JsonArray resourceCodes = routingContext.getBodyAsJson().getJsonArray("resourcesCode");

        if(StringUtils.isBlank(roleCode)){
            throw new MidiException(400, "角色唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(roleCode) && roleCode.length() > 45) {
            throw new MidiException(400, "角色唯一标识长度不能大于45");
        }
        ctx.nExecute("delete from tb_role_resource_relation where role_code = ?", roleCode);

        TbRole tbRole = new TbRole();
        tbRole.setCode(roleCode);
        if(!tbRole.exist()){
            throw new MidiException(400,"添加失败，角色不存在");
        }

        for (int i = 0; i <resourceCodes.size() ; i++) {
            relation.setId(UUID.randomUUID().toString());
            relation.setCrtCode(userCode);
            relation.setCrtTime(new Date());
            String resourceCode = resourceCodes.getList().get(i).toString();
            relation.setResourceCode(resourceCode);
            if(StringUtils.isBlank(resourceCode)){
                throw new MidiException(400, "资源唯一标识不能为空");
            }
            if (StringUtils.isNotBlank(resourceCode)
                    && resourceCode.length() > 45) {
                throw new MidiException(400, "资源唯一标识长度不能大于45");
            }
            TbResource tbResource = new TbResource();
            tbResource.setCode(resourceCode);
            if(!tbResource.exist()){
                throw new MidiException(400,"添加失败，资源不存在");
            }
            relation.insert(SqlOption.IGNORE_NULL);
        }

    }

    @TX
    public void insertRoleUser(RoutingContext routingContext) throws MidiException {
        String roleCode = routingContext.pathParam("roleCode");
        String userCode = routingContext.getBodyAsJson().getString("userCode");
        if(StringUtils.isBlank(roleCode)){
            throw new MidiException(400, "角色唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(roleCode) && roleCode.length() > 45) {
            throw new MidiException(400, "角色唯一标识长度不能大于45");
        }
        TbRole tbRole = new TbRole();
        tbRole.setCode(roleCode);
        if(!tbRole.exist()){
            throw new MidiException(400,"添加失败，角色不存在");
        }
        TbUser tbUser = new TbUser();
        tbUser.setCode(userCode);
        if(!tbUser.exist()){
            throw new MidiException(400,"添加失败，用户不存在");
        }
        TbRoleUserRelation relation = new TbRoleUserRelation();
        relation.setId(UUID.randomUUID().toString());
        relation.setRoleCode(roleCode);
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        relation.setCrtCode(crtUserCode);
        relation.setCrtTime(new Date());
        relation.setUserCode(userCode);
        if(StringUtils.isBlank(userCode)){
            throw new MidiException(400, "人员唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(userCode) && userCode.length() > 45) {
            throw new MidiException(400, "人员唯一标识长度不能大于45");
        }
        relation.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void insertRole(RoutingContext routingContext) throws MidiException {
        String code = routingContext.getBodyAsJson().getString("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String description = routingContext.getBodyAsJson().getString("description");
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "角色唯一标识或名称不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "角色唯一标识或名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 200) {
            throw new MidiException(400, "描述长度不能大于200");
        }
        TbRole role = new TbRole();
        role.setCode(code);
        if(role.exist()){
            throw new MidiException(400,"创建角色失败，角色唯一标识已存在");
        }
        role.setName(name);
        role.setDescription(description);
        role.setCrtTime(new Date());
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        role.setCrtCode(crtUserCode);
        role.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteRoleByCode(RoutingContext routingContext) {
        String code = routingContext.pathParam("code");
        int roleAppNum = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_role_app_relation where role_code = ?",
                param(code));
        if (roleAppNum > 0) {
            throw new MidiException(400, "删除失败,该角色已被分配给应用");
        }
        int roleResourceNum = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_role_resource_relation where role_code = ?",
                param(code));
        if (roleResourceNum > 0) {
            throw new MidiException(400, "删除失败,该角色已分配资源");
        }

        int roleUserNum = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_role_user_relation where role_code = ?",
                param(code));
        if (roleUserNum > 0) {
            throw new MidiException(400, "删除失败,该角色已分配用户");
        }
        TbRole role = new TbRole();
        role.setCode(code);
        role.deleteTry();
    }

    public Page findRolePageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";

        String sqlTemplate = "select %s from tb_role tr where 1=1 ";
        String sqlResult = "tr.*";

        List<SqlItem> conditionList = new ArrayList<>();

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("tr.code", "code"));
        conditionAttrList.add(new ConditionAttr("tr.name", "name"));
        conditionAttrList.add(new ConditionAttr("tr.description", "description"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by tr." + orderBy));

        return Utils.queryPage(TbRole.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());

    }

}
