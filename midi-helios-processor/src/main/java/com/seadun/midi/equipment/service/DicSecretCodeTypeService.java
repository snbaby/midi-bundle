package com.seadun.midi.equipment.service;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.DicSecretCode;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DicSecretCodeTypeService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertDic(RoutingContext routingContext) throws MidiException {
        String secretCode = routingContext.getBodyAsJson().getString("secretCode");
        String secretName = routingContext.getBodyAsJson().getString("secretName");
        if (StringUtils.isBlank(secretCode) || StringUtils.isBlank(secretName)) {
            throw new MidiException(400, "唯一标识或名称不能为空");
        }
        if (secretCode.length() > 45 || secretName.length() > 45) {
            throw new MidiException(400, "唯一标识或名称长度不能大于45");
        }
        DicSecretCode dicSecretCode = new DicSecretCode();
        dicSecretCode.setSecretCode(secretCode);
        if (dicSecretCode.exist()) {
            throw new MidiException(400, "添加失败 唯一标识已存在");
        }
        dicSecretCode.setSecretCode(secretCode);
        dicSecretCode.setSecretName(secretName);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        dicSecretCode.setUptUser(userCode);
        dicSecretCode.setUptTime(new Date());
        dicSecretCode.setCrtTime(new Date());
        dicSecretCode.setCrtUser(userCode);
        dicSecretCode.insert(SqlOption.IGNORE_NULL);
    }

    public Page findDicPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        DicSecretCode dicSecretCode = new DicSecretCode();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            dicSecretCode = new DicSecretCode(query);
        }
        SampleItem simpleItem = new SampleItem(dicSecretCode).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "secret_code", filter.getString("secretCode"));
            getSampleItem(simpleItem, "secret_name", filter.getString("secretName"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<DicSecretCode> list = ctx.eFindAll(DicSecretCode.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<DicSecretCode> list1 = ctx.eFindAll(DicSecretCode.class, simpleItem);
        HashSet<DicSecretCode> hashSet = new HashSet<>(list);
        list = new ArrayList<DicSecretCode>(hashSet);
        HashSet<DicSecretCode> hashSet2 = new HashSet<>(list1);
        list1 = new ArrayList<>(hashSet2);
        Page page = new Page();
        page.setData(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(list1.size());
        return page;
    }

    public List<DicSecretCode> findByDicTypeCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String secretCode = routingContext.pathParam("secretCode");
        DicSecretCode equipment = new DicSecretCode();
        equipment.setSecretCode(secretCode);
        return ctx.eFindBySample(equipment);
    }

    @TX
    public void deleteDic(RoutingContext routingContext, SqlBoxContext ctx) {
        String secretCode = routingContext.pathParam("secretCode");
        DicSecretCode equipment = new DicSecretCode();
        equipment.setSecretCode(secretCode);
        equipment.deleteTry(equipment);
    }


    @TX
    public void updateDic(RoutingContext routingContext) throws MidiException{
        String secretCode = routingContext.pathParam("secretCode");
        String secretName = routingContext.getBodyAsJson().getString("secretName");
        if (StringUtils.isBlank(secretCode) || StringUtils.isBlank(secretName)) {
            throw new MidiException(400, "唯一标识或名称不能为空");
        }
        if (secretCode.length() > 45 || secretName.length() > 45) {
            throw new MidiException(400, "唯一标识或名称长度不能大于45");
        }
        DicSecretCode dicSecretCode = new DicSecretCode();
        dicSecretCode.setSecretCode(secretCode);
        if (!dicSecretCode.exist()) {
            throw new MidiException(400, "修改失败 唯一标识不存在");
        }
        //返回成功与否
        dicSecretCode.setSecretCode(secretCode);
        dicSecretCode.setSecretName(secretName);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        dicSecretCode.setUptUser(userCode);
        dicSecretCode.setUptTime(new Date());
        dicSecretCode.update(SqlOption.IGNORE_NULL);
    }

}
