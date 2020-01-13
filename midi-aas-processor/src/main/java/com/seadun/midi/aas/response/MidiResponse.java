package com.seadun.midi.aas.response;

import com.alibaba.fastjson.JSONObject;

import com.seadun.midi.aas.entity.Page;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;

public class MidiResponse {

	public static void success(RoutingContext requestHandler) {
		JSONObject jsb = new JSONObject();
		jsb.put("code", 200);
		jsb.put("data", "");
		jsb.put("description", "success");
		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(jsb.toJSONString());
	}

	public static void success(RoutingContext requestHandler, JSONObject jsonObject) {
		JSONObject jsb = new JSONObject();
		jsb.put("code", 200);
		jsb.put("data", jsonObject);
		jsb.put("description", "success");
		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(jsb.toJSONString());
	}
	public static void success(RoutingContext requestHandler, Object o) {
		JSONObject jsb = new JSONObject();
		jsb.put("code", 200);
		jsb.put("data", o);
		jsb.put("description", "success");
		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(jsb.toJSONString());
	}

	public static void success(RoutingContext requestHandler, List list) {
		JSONObject jsb = new JSONObject();
		jsb.put("code", 200);
		jsb.put("data", list);
		jsb.put("description", "success");
		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(jsb.toJSONString());
	}

	public static void success(RoutingContext requestHandler, Page page) {
		JsonObject jsb = new JsonObject();
		jsb.put("data", page.getData())
				.put("code", 200)
				.put("total", page.getTotal())
				.put("description", "success")
				.put("pageNo", page.getPageNo())
				.put("pageSize", page.getPageSize());
		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(jsb.encode());
	}

	public static void faild(RoutingContext requestHandler, int statusCode) {
		JSONObject jsb = new JSONObject();
		jsb.put("code", statusCode);
		jsb.put("data", "");
		jsb.put("description", "fail");

		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(statusCode)
				.end(jsb.toJSONString());
	}

	public static void faild(RoutingContext requestHandler, int statusCode, String message) {
		JSONObject jsb = new JSONObject();
		jsb.put("code", statusCode);
		jsb.put("data", "");
		jsb.put("description", message);

		requestHandler.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(statusCode)
				.end(jsb.toJSONString());
	}
}
