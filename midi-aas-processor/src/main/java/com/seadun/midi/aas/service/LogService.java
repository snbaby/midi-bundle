package com.seadun.midi.aas.service;

import static com.github.drinkjava2.jdbpro.JDBPRO.sql;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.ConditionAttr;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbLog;
import com.seadun.midi.aas.entity.TbLogDB;
import com.seadun.midi.aas.entity.TbLogLogin;
import com.seadun.midi.aas.entity.TbLogModel;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public class LogService {

    public Page getAllLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        
        String sqlTemplate = "select %s from tb_log where 1=1 ";
        
        String sqlResult = "*";
        
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("user_ode", "userCode"));
        conditionAttrList.add(new ConditionAttr("resource_code", "resourceCode"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("model_code", "modelCode"));
        conditionAttrList.add(new ConditionAttr("model_name", "modelName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));
        
        
        return Utils.queryPage(TbLog.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }


    public Page getAdministratorLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        
        String sqlTemplate = "select %s from tb_log where USER_CODE in (SELECT USER_CODE FROM tb_role_user_relation WHERE ROLE_CODE = 'administrator') ";
        
        String sqlResult = "*";
        
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("user_ode", "userCode"));
        conditionAttrList.add(new ConditionAttr("resource_code", "resourceCode"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("model_code", "modelCode"));
        conditionAttrList.add(new ConditionAttr("model_name", "modelName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));
        
        
        return Utils.queryPage(TbLog.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page getSecurityLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        
        String sqlTemplate = "select %s from tb_log where USER_CODE in (SELECT USER_CODE FROM tb_role_user_relation WHERE ROLE_CODE = 'security') ";
        
        String sqlResult = "*";
        
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("user_ode", "userCode"));
        conditionAttrList.add(new ConditionAttr("resource_code", "resourceCode"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("model_code", "modelCode"));
        conditionAttrList.add(new ConditionAttr("model_name", "modelName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));
        
        
        return Utils.queryPage(TbLog.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page getAuditorLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        
        String sqlTemplate = "select %s from tb_log where USER_CODE in (SELECT USER_CODE FROM tb_role_user_relation WHERE ROLE_CODE = 'auditor') ";
        
        String sqlResult = "*";
        
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("user_ode", "userCode"));
        conditionAttrList.add(new ConditionAttr("resource_code", "resourceCode"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("model_code", "modelCode"));
        conditionAttrList.add(new ConditionAttr("model_name", "modelName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));
        
        
        return Utils.queryPage(TbLog.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
        
    }

    public Page getOtherLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
        
        String sqlTemplate = "select %s from tb_log where USER_CODE not in (SELECT USER_CODE FROM tb_role_user_relation WHERE ROLE_CODE in ('auditor','security','administrator')) ";
        
        String sqlResult = "*";
        
        List<SqlItem> conditionList = new ArrayList<>();

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("user_ode", "userCode"));
        conditionAttrList.add(new ConditionAttr("resource_code", "resourceCode"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("model_code", "modelCode"));
        conditionAttrList.add(new ConditionAttr("model_name", "modelName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));
        
        
        return Utils.queryPage(TbLog.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }


    public Page getLoginLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";

        String sqlTemplate = "select %s from tb_login_log where 1=1 ";

        String sqlResult = "*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("crt_code", "crtCode"));
        conditionAttrList.add(new ConditionAttr("crt_name", "crtName"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("type", "type"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));


        return Utils.queryPage(TbLogLogin.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page getDBLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";

        String sqlTemplate = "select %s from tb_db_log where 1=1 ";

        String sqlResult = "*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("crt_code", "crtCode"));
        conditionAttrList.add(new ConditionAttr("crt_name", "crtName"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("operation_type", "operationType"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));

        return Utils.queryPage(TbLogDB.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page getModelLog(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";

        String sqlTemplate = "select %s from tb_model_log where 1=1 ";

        String sqlResult = "*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("client_ip", "clientIp"));
        conditionAttrList.add(new ConditionAttr("crt_code", "crtCode"));
        conditionAttrList.add(new ConditionAttr("crt_name", "crtName"));
        conditionAttrList.add(new ConditionAttr("app_code", "appCode"));
        conditionAttrList.add(new ConditionAttr("model_code", "modelCode"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        conditionList.add(sql(" order by " + orderBy));

        return Utils.queryPage(TbLogModel.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }
}
