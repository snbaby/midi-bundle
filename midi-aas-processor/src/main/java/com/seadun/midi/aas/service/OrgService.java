package com.seadun.midi.aas.service;

import static com.github.drinkjava2.jdbpro.JDBPRO.param;
import static com.github.drinkjava2.jdbpro.JDBPRO.sql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.ConditionAttr;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbOrg;
import com.seadun.midi.aas.entity.TbOrgPersonRelation;
import com.seadun.midi.aas.entity.TbOrgStationRelation;
import com.seadun.midi.aas.entity.TbPerson;
import com.seadun.midi.aas.entity.TbStation;
import com.seadun.midi.aas.entity.TbTosrPersonRelation;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class OrgService {

    public Page getPersonOrg(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        // TODO  tb_org_user_relation 表 修改为 tb_org_person_relation
        String sqlTemplate = "SELECT %s FROM tb_org_person_relation topr left join tb_person tp on topr.person_code = tp.code where 1=1";
        String sqlResult = "tp.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(sql(" and topr.org_code = ? "));
        conditionList.add(param(routingContext.pathParam("orgCode")));

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
    public void createOrg(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbOrg org = new TbOrg(bodyAsJson);
        if (StringUtils.isBlank(org.getCode())) {
            throw new MidiException(404, "添加失败，组织编码不合規");
        }
        if (StringUtils.isBlank(org.getParentCode())) {
            org.setParentCode("-1");
        } else if (!org.getParentCode().equals("-1")) {
            TbOrg tborg = new TbOrg();
            tborg.setCode(org.getParentCode());
            if (!tborg.exist()) {
                throw new MidiException(404, "添加失败，父组织编码不存在");
            }
        }
        if (StringUtils.isBlank(org.getType())) {
            org.setType("-1");
        }
        if (org.exist()) {
            throw new MidiException(400, "添加失败，已存在组织唯一标识");
        }
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        org.setCrtCode(userCode);
        org.setUptCode(userCode);
        org.setCrtTime(new Date());
        org.setUptTime(new Date());
        org.insert(SqlOption.IGNORE_NULL);
    }

    public Page getOrgPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "master.sort desc";
        String sqlTemplate = "SELECT %s FROM tb_org master left join tb_org slave on master.parent_code = slave.code where 1=1 ";

        String sqlResult = "master.*,slave.name  as parent_name";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("master.code", "code"));
        conditionAttrList.add(new ConditionAttr("master.name", "name"));
        conditionAttrList.add(new ConditionAttr("master.type", "type"));
        conditionAttrList.add(new ConditionAttr("master.parent_code", "parentCode"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));

        return Utils.queryPage(TbOrg.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page getOrgStationPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        String sqlTemplate = "select %s from tb_org_station_relation osr "
                + "left join tb_org o on osr.org_code = o.code "
                + "left join tb_station s on osr.station_code = s.code where 1=1 ";
        String sqlResult = "osr.*,o.name as orgName,s.name as stationName";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
//        conditionList.add(notNull("and osr.org_code = ? ", bodyAsJson != null ? bodyAsJson.getString("orgCode") : null));
//        conditionList.add(notNull("and osr.station_code = ? ", bodyAsJson != null ? bodyAsJson.getString("stationCode") : null));
        conditionAttrList.add(new ConditionAttr("osr.org_code","orgCode"));
        conditionAttrList.add(new ConditionAttr("osr.station_code","stationCode"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by osr." + orderBy));

        return Utils.queryPage(TbOrgStationRelation.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    @TX
    public void updateOrg(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbOrg org = new TbOrg();
        if (bodyAsJson.getString("parentCode") != null && !bodyAsJson.getString("parentCode").equals("-1")) {
            TbOrg tborg = new TbOrg();
            tborg.setCode(bodyAsJson.getString("parentCode"));
            if (!tborg.exist()) {
                throw new MidiException(404, "修改失败，父组织编码不存在");
            }
        }
        org.setCode(routingContext.pathParam("code"));
        if (!org.exist()) {
            throw new MidiException(400, "修改失败，组织唯一标识不存在");
        }

        if(bodyAsJson.getInteger("sort") != null) {
        	org.setSort(bodyAsJson.getInteger("sort"));
        }
        org.setType(bodyAsJson.getString("type"));
        org.setName(bodyAsJson.getString("name"));
        org.setParentCode(bodyAsJson.getString("parentCode"));
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        org.setUptCode(userCode);
        org.setUptTime(new Date());
        org.update(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteOrg(RoutingContext routingContext, SqlBoxContext ctx) {
        String orgCode = routingContext.pathParam("code");
        TbOrg tbOrg = new TbOrg();
        tbOrg.setCode(orgCode);
        int i = ctx.nQueryForIntValue("select count(*) num from tb_org where parent_code = ?", orgCode);
        if (i > 0) {
            throw new MidiException(400, "删除失败，此组织下还有子组织");
        }
        Integer it = ctx.nQueryForIntValue("select count(*) from tb_org_station_relation where org_code = ?", orgCode);
        if (it > 0) {
            throw new MidiException(400, "删除失败，此组织下组织岗位关系存在");
        }
        int orgUser = ctx.nQueryForIntValue("select count(*) from tb_person tp where `code` in (select person_code from tb_org_person_relation where org_code = ? ) and tp.`status` != '0'", orgCode);
        if (orgUser > 0) {
            throw new MidiException(400, "删除失败，此组织已分配人员");
        }
        ctx.nExecute("delete from tb_org where code = ?", orgCode);
    }

    public Page getStationOrg(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";


        String sqlTemplate = "select %s from tb_station ts left join tb_org_station_relation tosr on tosr.station_code = ts.code where 1=1 ";
        String sqlResult = "ts.*,tosr.id";

        List<SqlItem> conditionList = new ArrayList<>();
        conditionList.add(sql(" and tosr.org_code = ? "));
        conditionList.add(param(routingContext.pathParam("orgCode")));

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("ts.code", "code"));
        conditionAttrList.add(new ConditionAttr("ts.name", "name"));
        conditionAttrList.add(new ConditionAttr("ts.security_classification", "securityClassification"));
        conditionAttrList.add(new ConditionAttr("ts.description", "description"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by ts." + orderBy));


        return Utils.queryPage(TbStation.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }


    public Page getNotInStationOrg(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        String sqlTemplate = "select %s from tb_station ts where 1=1 ";
        String sqlResult = "ts.*";

        List<SqlItem> conditionList = new ArrayList<>();
        conditionList.add(sql(" and ts.code not in ( select tosr.station_code from tb_org_station_relation tosr where tosr.org_code = ?) "));
        conditionList.add(param(routingContext.pathParam("orgCode")));

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("ts.code", "code"));
        conditionAttrList.add(new ConditionAttr("ts.name", "name"));
        conditionAttrList.add(new ConditionAttr("ts.type", "type"));
        conditionAttrList.add(new ConditionAttr("ts.security_classification", "securityClassification"));
        conditionAttrList.add(new ConditionAttr("ts.description", "description"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by ts." + orderBy));

        return Utils.queryPage(TbStation.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }


    @TX
    public void insertPersonOrgStation(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbOrgStationRelation orgStation = new TbOrgStationRelation();
        orgStation.setId(routingContext.pathParam("orgStaionId"));
        if (!orgStation.exist()) {
            throw new MidiException(400, "添加失败，组织岗位编码不存在");
        }
        TbPerson person = new TbPerson();
        person.setCode(bodyAsJson.getString("personCode"));
        if (!person.exist()) {
            throw new MidiException(400, "添加失败，人员唯一标识不存在");
        }
        TbTosrPersonRelation tbTosrPersonRelation = new TbTosrPersonRelation();
        tbTosrPersonRelation.setId(UUID.randomUUID().toString());
        tbTosrPersonRelation.setTosrId(routingContext.pathParam("orgStaionId"));
        tbTosrPersonRelation.setPersonCode(bodyAsJson.getString("personCode"));
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        tbTosrPersonRelation.setCrtCode(userCode);
        tbTosrPersonRelation.setCrtTime(new Date());
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_tosr_person_relation where tosr_id = ? and person_code = ?",
                param(routingContext.pathParam("orgStaionId"), bodyAsJson.getString("personCode")));
        if (i > 0) {
            throw new MidiException(400, "已存在组织岗位与人员关系");
        }
        tbTosrPersonRelation.insert(SqlOption.IGNORE_NULL);
    }


    @TX
    public void insertPersonOrg(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbOrg org = new TbOrg();
        org.setCode(routingContext.pathParam("orgCode"));
        if (!org.exist()) {
            throw new MidiException(400, "添加失败，组织唯一标识不存在");
        }
        TbPerson person = new TbPerson();
        person.setCode(bodyAsJson.getString("personCode"));
        if (!person.exist()) {
            throw new MidiException(400, "添加失败，人员唯一标识不存在");
        }
        TbOrgPersonRelation orgPerson = new TbOrgPersonRelation();
        orgPerson.setId(UUID.randomUUID().toString());
        orgPerson.setPersonCode(bodyAsJson.getString("personCode"));
        orgPerson.setOrgCode(routingContext.pathParam("orgCode"));
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        orgPerson.setCrtCode(userCode);
        orgPerson.setCrtTime(new Date());
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_org_person_relation where org_code = ? and person_code = ?",
                param(routingContext.pathParam("orgCode"), bodyAsJson.getString("personCode")));
        if (i > 0) {
            throw new MidiException(400, "已存在组织与人员关系");
        }
        orgPerson.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteOrgStation(RoutingContext routingContext, SqlBoxContext ctx) {
        String orgCode = routingContext.pathParam("orgCode");
        String stationCode = routingContext.getBodyAsJson().getString("stationCode");
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_tosr_person_relation where tosr_id in " +
                        "(select id from tb_org_station_relation where org_code = ? and station_code = ?)",
                param(orgCode, stationCode));
        if (i > 0) {
            throw new MidiException(400, "删除失败,该岗位与组织已分配用户");
        }
        ctx.nExecute("delete from tb_org_station_relation where org_code = ? and station_code = ?", orgCode, stationCode);
    }

    @TX
    public void deletePersonOrgStation(RoutingContext routingContext, SqlBoxContext ctx) {
        String orgStaionId = routingContext.pathParam("orgStaionId");
        String personCode = routingContext.getBodyAsJson().getString("personCode");
        ctx.nExecute("delete from tb_tosr_person_relation where tosr_id = ? and person_code = ?", orgStaionId, personCode);
    }

    @TX
    public void deletePersonOrg(RoutingContext routingContext, SqlBoxContext ctx) {
        String orgCode = routingContext.pathParam("orgCode");
        String personCode = routingContext.getBodyAsJson().getString("personCode");
        int total = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_tosr_person_relation where person_code = ? and tosr_id in (select id from tb_org_station_relation where org_code = ?)", param(personCode, orgCode));
        if (total > 0) {
            throw new MidiException(400, "删除失败,该人员已分配给该组织下的岗位");
        }
        ctx.nExecute("delete from tb_org_person_relation where org_code = ? and person_code = ?", orgCode, personCode);
    }

    @TX
    public void insertStationOrg(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String orgCode = routingContext.pathParam("orgCode");
        String stationCode = bodyAsJson.getString("stationCode");
        TbOrg org = new TbOrg();
        org.setCode(orgCode);
        if (!org.exist()) {
            throw new MidiException(400, "添加失败，组织唯一标识不存在");
        }
        TbStation station = new TbStation();
        station.setCode(stationCode);
        if (!station.exist()) {
            throw new MidiException(400, "添加失败，岗位唯一标识不存在");
        }
        TbOrgStationRelation orgStation = new TbOrgStationRelation();
        orgStation.setId(UUID.randomUUID().toString());
        orgStation.setStationCode(bodyAsJson.getString("stationCode"));
        orgStation.setOrgCode(routingContext.pathParam("orgCode"));
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        orgStation.setCrtCode(userCode);
        orgStation.setCrtTime(new Date());
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from tb_org_station_relation where org_code = ? and station_code = ?",
                param(orgCode, stationCode));
        if (i > 0) {
            throw new MidiException(404, "已存在组织与岗位关系");
        }
        orgStation.insert(SqlOption.IGNORE_NULL);
    }

    public Page getPersonStationOrg(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";

        String sqlTemplate = "select %s from tb_tosr_person_relation ttpr left join tb_person tp on ttpr.person_code  = tp.code where 1=1 ";
        String sqlResult = "tp.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(sql(" and ttpr.tosr_id = ? "));
        conditionList.add(param(routingContext.pathParam("orgStaionId")));

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
        conditionList.add(sql(" order by ttpr." + orderBy));
        return Utils.queryPage(TbPerson.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page getNotInPersonStationOrg(RoutingContext routingContext, SqlBoxContext ctx) {
    	JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "tp.crt_time desc";

        String sqlTemplate = "select %s from tb_org_station_relation tosr left join tb_org_person_relation topr on tosr.org_code = topr.org_code left join tb_person tp on tp.code = topr.person_code where 1=1 and tp.status = '2' ";
        String sqlResult = "tp.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
//        conditionList.add(sql(" and tp.code not in  (select ttpr.person_code from tb_tosr_person_relation ttpr where ttpr.tosr_id = ?) "));
        conditionList.add(sql(" and tosr.id = ? "));
        conditionList.add(param(routingContext.pathParam("orgStaionId")));
        conditionList.add(sql(" and not EXISTS  (select trim(ttpr.person_code) from tb_tosr_person_relation ttpr where ttpr.tosr_id = ? and trim(ttpr.person_code)=trim(tp.code)) "));
        conditionList.add(param(routingContext.pathParam("orgStaionId")));

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
        conditionList.add(sql(" order by " + orderBy));
        return Utils.queryPage(TbPerson.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

}
