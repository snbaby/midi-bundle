package com.seadun.midi.aas.service;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbDuty;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

public class DutyService {
	public void getSampleItem(SampleItem simpleItem, String sql, String param) {
		if (StringUtils.isNotBlank(param)) {
			simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
		}
	}

	@TX
	public void createDuty(RoutingContext requestHandler) throws MidiException {
		JSONObject jsb = JSONObject.parseObject(requestHandler.getBodyAsString());
		String code = jsb.getString("code");
		String name = jsb.getString("name");
		String description = jsb.getString("description");

		if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
			throw new MidiException(400, "岗位唯一标识或岗位名称不能为空");
		}

		if (code.length() > 45 || name.length() > 45) {
			throw new MidiException(400, "岗位唯一标识或岗位名称长度不能大于45");
		}

		if (StringUtils.isNotBlank(description)) {
			if (description.length() > 200) {
				throw new MidiException(400, "岗位描述长度不能大于200");
			}
		}
		String crtCode = HeaderTokenUtils.getHeadersUserCode(requestHandler);

		TbDuty tbDuty = new TbDuty();
		tbDuty.setCode(code);
		if (tbDuty.exist()) {
			throw new MidiException(409, "岗位唯一标识已存在");
		}

		tbDuty.setCrtCode(crtCode);
		tbDuty.setCrtTime(new Date());
		tbDuty.setDescription(description);
		tbDuty.setName(name);
		tbDuty.setUptCode(crtCode);
		tbDuty.setUptTime(new Date());
		tbDuty.insert(SqlOption.IGNORE_NULL);
	}

	@TX
	public void updateDuty(RoutingContext requestHandler) throws MidiException {

		JSONObject jsb = JSONObject.parseObject(requestHandler.getBodyAsString());
		String code = requestHandler.pathParam("code");
		String name = jsb.getString("name");
		String description = jsb.getString("description");

		if (StringUtils.isNotBlank(name) && name.length() > 45) {
			throw new MidiException(400, "岗位职责名称长度不能大于45");
		}

		if (StringUtils.isNotBlank(description) && description.length() > 200) {
			throw new MidiException(400, "岗位描述长度不能大于200");
		}
		String crtCode = HeaderTokenUtils.getHeadersUserCode(requestHandler);

		TbDuty tbDuty = new TbDuty();
		tbDuty.setCode(code);
		if (!tbDuty.exist()) {
			throw new MidiException(404, "岗位不存在");
		}

		tbDuty.setCrtCode(crtCode);
		tbDuty.setCrtTime(new Date());
		tbDuty.setName(name);
		tbDuty.setDescription(description);
		tbDuty.setUptCode(crtCode);
		tbDuty.setUptTime(new Date());
		tbDuty.update(SqlOption.IGNORE_NULL);
	}

	@TX
	public void deleteDuty(RoutingContext requestHandler) throws MidiException {
		String code = requestHandler.pathParam("code");

		TbDuty tbDuty = new TbDuty();
		int total = SqlBoxContext.getGlobalSqlBoxContext()
				.iQueryForIntValue("select count(*) as total from tb_station_duty_relation where duty_code = " + code);
		if (total > 0) {
			throw new MidiException(403, "当前岗位职责已被岗位绑定，请先解绑岗位的岗位职责");
		}

		tbDuty.setCode(code);
		tbDuty.deleteTry(SqlOption.IGNORE_NULL);
	}

	@TX
	public Page queryDutyPage(RoutingContext requestHandler) throws MidiException {
		JsonObject bodyAsJson = requestHandler.getBodyAsJson();
		Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
		Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
		String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
		TbDuty tbDuty = new TbDuty();
		if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
			JsonObject query = bodyAsJson.getJsonObject("query");
			tbDuty = new TbDuty(query);
		}
		SampleItem simpleItem = new SampleItem(tbDuty).sql(" where  ").notNullFields();
		if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
			JsonObject filter = bodyAsJson.getJsonObject("filter");
			getSampleItem(simpleItem, "CODE", filter.getString("code"));
			getSampleItem(simpleItem, "NAME", filter.getString("name"));
		}
		simpleItem.sql(" order by " + orderBy);
		List<TbDuty> maps = SqlBoxContext.getGlobalSqlBoxContext().eFindAll(TbDuty.class, simpleItem,
				JSQLBOX.pagin(pageNo, pageSize));
		List<TbDuty> maps2 = SqlBoxContext.getGlobalSqlBoxContext().eFindAll(TbDuty.class, simpleItem);
		Page page = new Page();
		page.setData(maps);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(maps2.size());
		return page;
	}

}
