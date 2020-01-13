package com.seadun.midi.equipment.service;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.DicEquipmentType;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class DicEquipmentTypeService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertDic(RoutingContext routingContext) throws MidiException {
        String typeCode = routingContext.getBodyAsJson().getString("typeCode");
        String typeName = routingContext.getBodyAsJson().getString("typeName");
        if (StringUtils.isBlank(typeCode) || StringUtils.isBlank(typeName)) {
            throw new MidiException(400, "唯一标识或类型名称不能为空");
        }
        if (typeCode.length() > 45 || typeName.length() > 45) {
            throw new MidiException(400, "唯一标识或类型名称长度不能大于45");
        }
        DicEquipmentType dicEquipmentType = new DicEquipmentType();
        dicEquipmentType.setTypeCode(typeCode);
        if (dicEquipmentType.exist()) {
            throw new MidiException(400, "添加失败 唯一标识已存在");
        }
        dicEquipmentType.setTypeName(typeName);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        dicEquipmentType.setUptUser(userCode);
        dicEquipmentType.setUptTime(new Date());
        dicEquipmentType.setCrtTime(new Date());
        dicEquipmentType.setCrtUser(userCode);
        dicEquipmentType.insert(SqlOption.IGNORE_NULL);
    }

    public Page findDicPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        DicEquipmentType equipment = new DicEquipmentType();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            equipment = new DicEquipmentType(query);
        }
        SampleItem simpleItem = new SampleItem(equipment).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "type_code", filter.getString("typeCode"));
            getSampleItem(simpleItem, "type_name", filter.getString("typeName"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<DicEquipmentType> maps = ctx.eFindAll(DicEquipmentType.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<DicEquipmentType> maps2 = ctx.eFindAll(DicEquipmentType.class);
        HashSet<DicEquipmentType> hashSet = new HashSet<>(maps);
        HashSet<DicEquipmentType> hashSet2 = new HashSet<>(maps2);
        List<DicEquipmentType> list = new ArrayList<DicEquipmentType>(hashSet);
        List<DicEquipmentType> list2 = new ArrayList<DicEquipmentType>(hashSet2);
        Page page = new Page();
        page.setData(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(list2.size());
        return page;
    }

    public DicEquipmentType findByDicTypeCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String typeCode = routingContext.pathParam("typeCode");
        DicEquipmentType equipment = new DicEquipmentType();
        equipment.setTypeCode(typeCode);
        return equipment.load();
    }

    @TX
    public void deleteEquipmentByCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String typeCode = routingContext.pathParam("typeCode");
        DicEquipmentType equipment = new DicEquipmentType();
        equipment.setTypeCode(typeCode);
        equipment.deleteTry(equipment);
    }


    @TX
    public void updateDic(RoutingContext routingContext) throws MidiException {
        String typeCode = routingContext.pathParam("typeCode");
        String typeName = routingContext.getBodyAsJson().getString("typeName");
        if (StringUtils.isBlank(typeCode) || StringUtils.isBlank(typeName)) {
            throw new MidiException(400, "唯一标识或类型名称不能为空");
        }
        if (typeCode.length() > 45 || typeName.length() > 45) {
            throw new MidiException(400, "唯一标识或类型名称长度不能大于45");
        }
        DicEquipmentType dicEquipmentType = new DicEquipmentType();
        dicEquipmentType.setTypeCode(typeCode);
        if (!dicEquipmentType.exist()) {
            throw new MidiException(400, "修改失败 记录不存在");
        }
        DicEquipmentType equipment = new DicEquipmentType();
        //返回成功与否
        equipment.setTypeCode(typeCode);
        equipment.setTypeName(typeName);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        equipment.setUptUser(userCode);
        equipment.setUptTime(new Date());
        equipment.update(SqlOption.IGNORE_NULL);

    }
}
