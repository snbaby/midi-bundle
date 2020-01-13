package com.seadun.midi.aas.service;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbResource;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertResource(RoutingContext routingContext) throws MidiException {
        String code = routingContext.getBodyAsJson().getString("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String type = routingContext.getBodyAsJson().getString("type");
        String parentCode = routingContext.getBodyAsJson().getString("parentCode");
        String description = routingContext.getBodyAsJson().getString("description");
        String content = routingContext.getBodyAsJson().getString("content");
        String uri = routingContext.getBodyAsJson().getString("uri");
        String method = routingContext.getBodyAsJson().getString("method");
        if (("2".equals(type) && StringUtils.isBlank(uri)) || ("2".equals(type) && StringUtils.isBlank(method))) {
            throw new MidiException(400, "type为api接口时,uri和method必须传值!");
        }
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "资源唯一标识或名称不能为空");
        }
        if (StringUtils.isBlank(type) || StringUtils.isBlank(parentCode)) {
            throw new MidiException(400, "资源类型或父类资源不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "资源唯一标识或名称长度不能大于45");
        }
        if (parentCode.length() > 45) {
            throw new MidiException(400, "父级资源标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(type)) {
            Pattern pattern = Pattern.compile("^[0-2]{1}$");
            Matcher msec = pattern.matcher(type);
            if (!msec.matches()) {
                throw new MidiException(400, "请传入正确的type值");
            }
        }
        if (StringUtils.isNotBlank(description)) {
            if (description.length() > 200) {
                throw new MidiException(400, "描述长度不能大于200");
            }
        }
        TbResource resource = new TbResource();
        resource.setCode(code);
        if (resource.exist()) {
            throw new MidiException(400, "资源已存在请勿重复添加");
        }
        resource.setName(name);
        resource.setType(type);
        resource.setContent(content);
        resource.setParentCode(parentCode);
        resource.setDescription(description);
        resource.setUri(uri);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        resource.setCrtCode(userCode);
        resource.setMethod(method);
        resource.insert(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updateResource(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String type = routingContext.getBodyAsJson().getString("type");
        String content = routingContext.getBodyAsJson().getString("content");
        String parentCode = routingContext.getBodyAsJson().getString("parentCode");
        String description = routingContext.getBodyAsJson().getString("description");
        String uri = routingContext.getBodyAsJson().getString("uri");
        String method = routingContext.getBodyAsJson().getString("method");
        if (("2".equals(type) && StringUtils.isBlank(uri)) || ("2".equals(type) && StringUtils.isBlank(method))) {
            throw new MidiException(400, "type为api接口时,uri和method必须传值!");
        }
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "资源唯一标识或名称不能为空");
        }
        if (StringUtils.isBlank(type) || StringUtils.isBlank(parentCode)) {
            throw new MidiException(400, "资源类型或父类资源不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "资源唯一标识或名称长度不能大于45");
        }
        if (parentCode.length() > 45) {
            throw new MidiException(400, "父级资源标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(type)) {
            Pattern pattern = Pattern.compile("^[0-2]{1}$");
            Matcher msec = pattern.matcher(type);
            if (!msec.matches()) {
                throw new MidiException(400, "请传入正确的type值");
            }
        }
        if (StringUtils.isNotBlank(description)) {
            if (description.length() > 200) {
                throw new MidiException(400, "描述长度不能大于200");
            }
        }
        TbResource resource = new TbResource();
        resource.setName(name);
        resource.setType(type);
        resource.setContent(content);
        resource.setParentCode(parentCode);
        resource.setDescription(description);
        resource.setUri(uri);
        resource.setCode(code);
        resource.setMethod(method);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        resource.setUptCode(userCode);
        resource.setUptTime(new Date());
        resource.updateTry(SqlOption.IGNORE_NULL);
    }

    @TX
    public void deleteResourceByCode(RoutingContext routingContext) {
        String code = routingContext.pathParam("code");
        TbResource resource = new TbResource();
        resource.setCode(code);
        resource.deleteTry();
    }

    public Page findResourcePageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        TbResource resource = new TbResource();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            resource = new TbResource(query);
        }
        SampleItem simpleItem = new SampleItem(resource).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "CODE", filter.getString("code"));
            getSampleItem(simpleItem, "NAME", filter.getString("name"));
            getSampleItem(simpleItem, "type", filter.getString("type"));
            getSampleItem(simpleItem, "parent_code", filter.getString("parentCode"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<TbResource> maps = ctx.eFindAll(TbResource.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<TbResource> maps2 = ctx.eFindAll(TbResource.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }
}
