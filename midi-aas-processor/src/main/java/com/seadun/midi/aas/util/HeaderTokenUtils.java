package com.seadun.midi.aas.util;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 获取header内容
 */
public class HeaderTokenUtils {

    //人员code
    public static String USERCODE = "user.id";
    //名字
    public static String USERNAME = "user.name";
    //昵称
    public static String USERNICKNAME = "user.nickname";
    //角色
    public static String USERPERMISSIONS = "user.permissions";
    //过期时间
    public static String USERPEXPIRESAT = "user.expiresAt";

    public static String getHeadersUserCode(RoutingContext routingContext) {
        JsonObject jsonObject = getEntries(routingContext);
        return jsonObject.getString(USERCODE);
    }

    public static String getHeadersUserName(RoutingContext routingContext) {
        JsonObject jsonObject = getEntries(routingContext);
        return jsonObject.getString(USERNAME);
    }

    public static String getHeadersUserNickName(RoutingContext routingContext) {
        JsonObject jsonObject = getEntries(routingContext);
        return jsonObject.getString(USERNICKNAME);
    }

    public static String getHeadersUserPermissions(RoutingContext routingContext) {
        JsonObject jsonObject = getEntries(routingContext);
        return jsonObject.getString(USERPERMISSIONS);
    }

    public static String getHeadersUserPexpiresat(RoutingContext routingContext) {
        JsonObject jsonObject = getEntries(routingContext);
        return jsonObject.getString(USERPEXPIRESAT);
    }

    public static JsonObject getEntries(RoutingContext routingContext) {
        MultiMap headers = routingContext.request().headers();
        List<Map.Entry<String, String>> entries = headers.entries();
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < entries.size(); i++) {
            String value = entries.get(i).getValue();
            String key = entries.get(i).getKey();
            if("user.nickname".equals(key)){
                try {
                   value =  getUrlEncoding(value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    //转码
     private static String getUrlEncoding(String url) throws UnsupportedEncodingException {
         return URLDecoder.decode(url, "UTF-8");
    }
}
