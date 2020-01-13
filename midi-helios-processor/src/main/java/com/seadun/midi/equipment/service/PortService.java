package com.seadun.midi.equipment.service;


import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seadun.midi.equipment.entity.Detect;
import com.seadun.midi.equipment.entity.Page;
import com.seadun.midi.equipment.entity.Port;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.util.regex.Pattern.*;

public class PortService {

	public void getSampleItem(SampleItem simpleItem,String sql,String param) {
    	if(StringUtils.isNotBlank(param)) {
    		simpleItem.sql("and "+sql+" like ? ").param("%"+param+"%");
    	}
    }

    @TX
	public Page getPortPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
		Integer pageNo = bodyAsJson.getInteger("pageNo") != null ?bodyAsJson.getInteger("pageNo"):1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ?bodyAsJson.getInteger("pageSize"):10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "upt_time desc";
    	Port port = new Port();
		if(bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
        	JsonObject query = bodyAsJson.getJsonObject("query");
        	port = new Port(query);
        }
		SampleItem simpleItem = new SampleItem(port).sql(" where  ").notNullFields();
        if(bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
        	JsonObject filter = bodyAsJson.getJsonObject("filter");
        	getSampleItem(simpleItem,"code",filter.getString("code"));
        	getSampleItem(simpleItem,"detect_code",filter.getString("detectCode"));
        	getSampleItem(simpleItem,"port",filter.getString("port"));
        	getSampleItem(simpleItem,"status",filter.getString("status"));
        	getSampleItem(simpleItem,"port_name",filter.getString("portName"));

        }
        simpleItem.sql(" order by " + orderBy);
        List<Port> maps = ctx.eFindAll(Port.class,simpleItem,JSQLBOX.pagin(pageNo,pageSize));
        List<Port> maps2 = ctx.eFindAll(Port.class,simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Port getPortDetail(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        Port port = new Port();
        port.setCode(code);
        return port.load();
    }

    public void updatePortDetail(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String detectCode = routingContext.getBodyAsJson().getString("detectCode");
        String portName = routingContext.getBodyAsJson().getString("portName");
        if(StringUtils.isNotBlank(detectCode)){
            Detect detect = new Detect();
            detect.setCode(detectCode);
            if(!detect.exist()){
                throw new MidiException(400,"修改失败 多路侦测器不存在");
            }
        }
        if(StringUtils.isNotBlank(portName)){
            if(portName.length()>45){
                throw  new MidiException(400,"端口名称不能大于45");
            }
        }
        Port port = new Port();
        port.setCode(code);
        if(!port.exist()){
            throw new MidiException(400,"修改失败 端口不存在");
        }
        port.setDetectCode(detectCode);
        port.setPortName(portName);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        port.setUptUser(userCode);
        port.setUptTime(new Date());
        port.update(SqlOption.IGNORE_NULL);
    }


    public void updatePortStatus(RoutingContext routingContext) throws MidiException{
        String code = routingContext.pathParam("code");
        String status = routingContext.getBodyAsJson().getString("status");
        if(StringUtils.isBlank(status)){
            throw  new MidiException(400,"端口状态不能为空");
        }
        if(StringUtils.isNotBlank(status)){
            Pattern pattern = compile("^[1-2]{1}$");
            Matcher matcher = pattern.matcher(status);
            if(!matcher.matches()){
                throw new MidiException(400,"请传入正确的状态");
            }
        }
        Port port = new Port();
        port.setCode(code);
        if(!port.exist()){
            throw new MidiException(400,"修改失败 端口不存在");
        }
        port.setStatus(status);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        port.setUptUser(userCode);
        port.setUptTime(new Date());
        port.update(SqlOption.IGNORE_NULL);
    }

}
