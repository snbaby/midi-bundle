package com.seadun.midi.aas.service;

import static com.github.drinkjava2.jdbpro.JDBPRO.notNull;
import static com.github.drinkjava2.jdbpro.JDBPRO.param;
import static com.github.drinkjava2.jdbpro.JDBPRO.sql;
import static java.util.regex.Pattern.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seadun.midi.aas.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    public List findByUserCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        TbUser tbUser = new TbUser();
        tbUser.setCode(code);
        return ctx.<TbUser>eFindBySample(tbUser);
    }

    /**
     * 获取用户个人详细信息
     *
     * @param routingContext
     */
    public TbUser findSelfUser(RoutingContext routingContext, SqlBoxContext ctx) {
        // 后期从jwt中获取
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        TbUser tbUser = new TbUser();
        tbUser.setCode(crtUserCode);
        return tbUser.load();
    }

//	@TX
//	public void insertUser(RoutingContext routingContext) throws MidiException {
//		String name = routingContext.getBodyAsJson().getString("name");
//		String password = routingContext.getBodyAsJson().getString("password");
//		String code = routingContext.getBodyAsJson().getString("code");
//		if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
//			throw new MidiException(400, "用户唯一标识或名称不能为空");
//		}
//		if (code.length() > 45 || name.length() > 45) {
//			throw new MidiException(400, "用户唯一标识或名称长度不能大于45");
//		}
//		if (StringUtils.isBlank(password)) {
//			throw new MidiException(400, "密码不能为空");
//		}
//		if (StringUtils.isNotBlank(password) && password.length() > 45) {
//			throw new MidiException(400, "密码长度不能大于45");
//		}
//		if (StringUtils.isNotBlank(password)) {
//			Pattern psex = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)"
//					+ "(?![a-z\\\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()-+=]{10,45}$");
//			Matcher msex = psex.matcher(password);
//			if (!msex.matches()) {
//				throw new MidiException(400, "请传入密码长度不少于10位,不大于40位 必须由大写字母、小写字母、数字、特殊字符中的三者及以上构成的密码");
//			}
//		}
//		TbUser tbUser = new TbUser();
//		tbUser.setCode(code);
//		if (tbUser.exist()) {
//			throw new MidiException(400, "已存在用户重复添加");
//		}
//		TbPerson tbPerson = new TbPerson();
//		tbPerson.setCode(code);
//		tbPerson.setStatus("1");
//		String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
//		tbPerson.setCrtCode(crtUserCode);
//		tbPerson.setUptCode(crtUserCode);
//		tbPerson.setUptTime(new Date());
//		tbPerson.setCrtTime(new Date());
//		tbPerson.setName(name);
//		tbPerson.insert(SqlOption.IGNORE_NULL);
//
//		tbUser.setName(name);
//		tbUser.setPassword(password);
//		Date date = new Date();
//		date.setDate(date.getDate() + 7);
//		tbUser.setExpireTime(date);
//		TbUser insert = tbUser.insert(SqlOption.IGNORE_NULL);
//	}

//	@TX
//	public void updateUser(RoutingContext routingContext) throws MidiException {
//		String code = routingContext.pathParam("code");
//		String password = routingContext.getBodyAsJson().getString("password");
//		String status = routingContext.getBodyAsJson().getString("status");
//		if (StringUtils.isBlank(code)) {
//			throw new MidiException(400, "用户唯一标识不能为空");
//		}
//		if (code.length() > 45) {
//			throw new MidiException(400, "用户唯一标识长度不能大于45");
//		}
//		if (StringUtils.isBlank(password)) {
//			throw new MidiException(400, "密码不能为空");
//		}
//		if (StringUtils.isNotBlank(password) && password.length() > 45) {
//			throw new MidiException(400, "密码长度不能大于45");
//		}
//		if (StringUtils.isNotBlank(password)) {
//			Pattern psex = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)"
//					+ "(?![a-z\\\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()-+=]{10,45}$");
//			Matcher msex = psex.matcher(password);
//			if (!msex.matches()) {
//				throw new MidiException(400, "请传入密码长度不少于10位,不大于40位 必须由大写字母、小写字母、数字、特殊字符中的三者及以上构成的密码");
//			}
//		}
//		if (StringUtils.isNotBlank(status)) {
//			Pattern psex = Pattern.compile("^[0-3]{1}$");
//			Matcher msex = psex.matcher(status);
//			if (!msex.matches()) {
//				throw new MidiException(400, "请传入正确状态值");
//			}
//		}
//		TbUser tbUser = new TbUser();
//		// 返回成功与否
//		tbUser.setCode(code);
//		tbUser.setPassword(password);
//		tbUser.setStatus(status);
//		String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
//		tbUser.setUptCode(crtUserCode);
//		tbUser.setUptTime(new Date());
//		Date date = new Date();
//		date.setDate(date.getDate() + 7);
//		tbUser.setExpireTime(date);
//		int i = tbUser.updateTry(SqlOption.IGNORE_NULL);
//	}

    @TX
    public void updateStatus(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String status = routingContext.getBodyAsJson().getString("status");
        if (StringUtils.isBlank(code)) {
            throw new MidiException(400, "用户唯一标识不能为空");
        }
        if (code.length() > 45) {
            throw new MidiException(400, "用户唯一标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(status)) {
            Pattern psex = compile("^[0-3]{1}$");
            Matcher msex = psex.matcher(status);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确状态值");
            }
        }
        TbUser tbUser = new TbUser();
        // 返回成功与否
        tbUser.setCode(code);
        tbUser.setStatus(status);
        String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        tbUser.setUptCode(crtUserCode);
        tbUser.setUptTime(new Date());
        tbUser.updateTry(SqlOption.IGNORE_NULL);
    }

//	@TX
//	public void updateSelfUser(RoutingContext routingContext, SqlBoxContext ctx) throws MidiException {
//		String code = routingContext.getBodyAsJson().getString("code");
//		TbUser tbUser1 = new TbUser();
//		tbUser1.setCode(code);
//		if (!tbUser1.exist()) {
//			throw new MidiException(400, "修改失败，用户唯一标识不存在");
//		}
//		List<TbUser> userList = ctx.eFindBySample(tbUser1);
//		String password1 = userList.get(0).getPassword();
//		String oldPwd = routingContext.getBodyAsJson().getString("oldPwd");
//		String newPwd = routingContext.getBodyAsJson().getString("newPwd");
//		if (StringUtils.isBlank(oldPwd)) {
//			throw new MidiException(400, "旧密码不能为空");
//		}
//		if (StringUtils.isNotBlank(oldPwd) && oldPwd.length() > 45) {
//			throw new MidiException(400, "旧密码长度不能大于45");
//		}
//		if (StringUtils.isNotBlank(oldPwd)) {
//			Pattern psex = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)"
//					+ "(?![a-z\\\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()-+=]{10,45}$");
//			Matcher msex = psex.matcher(oldPwd);
//			if (!msex.matches()) {
//				throw new MidiException(400, "请传入旧密码长度不少于10位,不大于40位 必须由大写字母、小写字母、数字、特殊字符中的三者及以上构成的密码");
//			}
//		}
//		if (StringUtils.isBlank(newPwd)) {
//			throw new MidiException(400, "新密码不能为空");
//		}
//		if (StringUtils.isNotBlank(newPwd) && newPwd.length() > 45) {
//			throw new MidiException(400, "新密码长度不能大于45");
//		}
//		if (StringUtils.isNotBlank(newPwd)) {
//			Pattern psex = Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)"
//					+ "(?![a-z\\\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()-+=]{10,45}$");
//			Matcher msex = psex.matcher(newPwd);
//			if (!msex.matches()) {
//				throw new MidiException(400, "请传入新密码长度不少于10位,不大于40位 必须由大写字母、小写字母、数字、特殊字符中的三者及以上构成的密码");
//			}
//		}
//		TbUser tbUser = new TbUser();
//		// 后面根据什么加密解密对比具体实现
//		tbUser.setCode(code);
//		if (oldPwd.equals(password1)) {
//			tbUser.setPassword(newPwd);
//		} else {
//			respError(routingContext, 500,
//					new JsonObject().put("description", "原始密码错误!").put("code", 400).put("data", ""));
//			return;
//		}
//		Date date = new Date();
//		date.setDate(date.getDate() + 7);
//		tbUser.setExpireTime(date);
//		tbUser.setUptTime(new Date());
//		String crtUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
//		tbUser.setUptCode(crtUserCode);
//		TbUser i = tbUser.update(SqlOption.IGNORE_NULL);
//	}

    @TX
    public void deleteUserByCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        ctx.nExecute("delete from tb_user where code = ?", code);
    }

    public Page findUserPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        TbUser tbuser = new TbUser();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            tbuser = new TbUser(query);
        }
        SampleItem simpleItem = new SampleItem(tbuser).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "CODE", filter.getString("code"));
            getSampleItem(simpleItem, "NAME", filter.getString("name"));
            getSampleItem(simpleItem, "STATUS", filter.getString("status"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<TbUser> maps = ctx.eFindAll(TbUser.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<TbUser> maps2 = ctx.eFindAll(TbUser.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Page findUserResourcePageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;

        String sqlTemplate = "select %s \r\n" +
                "  from tb_resource tr\r\n" +
                "  left join tb_role_resource_relation trrr\r\n" +
                "    on tr.code = trrr.resource_code\r\n" +
                "  left join tb_role trole\r\n" +
                "    on trole.code = trrr.role_code\r\n" +
                "  left join tb_role_user_relation trur\r\n" +
                "    on trur.role_code = trole.code\r\n" +
                " where 1=1 ";
        String sqlResult = "distinct tr.code as code, tr.name as name, tr.type as type, tr.uri as uri, tr.method as method, tr.parent_code as parent_code, tr.description as description, tr.crt_code as crt_code, tr.crt_time as crt_time, tr.upt_code as upt_code, tr.upt_time as upt_time ";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(notNull(" and trur.user_code = ? ", userCode));
        conditionAttrList.add(new ConditionAttr("tr.code", "code"));
        conditionAttrList.add(new ConditionAttr("tr.name", "name"));
        conditionAttrList.add(new ConditionAttr("tr.type", "type"));
        conditionAttrList.add(new ConditionAttr("tr.parent_code", "parentCode"));
//        conditionList.add(notNull(" and tr.code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("code") : null));
//        conditionList.add(notNull(" and tr.name = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("name") : null));
//        conditionList.add(notNull(" and tr.type = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("type") : null));
//        conditionList.add(notNull(" and tr.parent_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("parentCode") : null));
//        conditionList.add(notNull(" and tr.code like ? ", bodyAsJson.getJsonObject("filter") != null ?
//                (bodyAsJson.getJsonObject("filter").getString("code") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("code") + "%" : null) : null));
//        conditionList.add(notNull(" and tr.type like ? ", bodyAsJson.getJsonObject("filter") != null ?
//                (bodyAsJson.getJsonObject("filter").getString("type") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("type") + "%" : null) : null));
//        conditionList.add(notNull(" and tr.parent_code like ? ", bodyAsJson.getJsonObject("filter") != null ?
//                (bodyAsJson.getJsonObject("filter").getString("parentCode") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("parentCode") + "%" : null) : null));
//        conditionList.add(notNull(" and tr.name like ? ", bodyAsJson.getJsonObject("filter") != null ?
//                (bodyAsJson.getJsonObject("filter").getString("name") != null ? "%" + bodyAsJson.getJsonObject("filter").getString("name") + "%" : null) : null));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        return Utils.queryPage(TbResource.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page findByUserCodeOrgPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String userCode = bodyAsJson.getString("userCode");
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        List<Map<String, Object>> maps = ctx.iQueryForMapList(
                "select tb_org.* from tb_org left join tb_org_person_relation on tb_org_person_relation.org_code = "
                        + "tb_org.code where tb_org_person_relation.person_code = ?",
                param(userCode), JSQLBOX.pagin(pageNo, pageSize));
        List<Map<String, Object>> maps2 = ctx.iQueryForMapList(
                "select tb_org.* from tb_org left join tb_org_person_relation on tb_org_person_relation.org_code = "
                        + "tb_org.code where tb_org_person_relation.person_code = ? ",
                param(userCode));

        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    public Page findUserOrgPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;



//        List<Map<String, Object>> maps = ctx.iQueryForMapList(
//                "SELECT DISTINCT o.* from tb_org o left join  tb_org_user_relation tour on o.code = tour.org_code"
//                        + " left join tb_user tb on tb.code = tour.user_code  where 1=1 and tb.code = ?",
//                param(UserCode),
//                notNull(" and o.code = ? ",
//                        bodyAsJson.getJsonObject("query") != null
//                                ? bodyAsJson.getJsonObject("query").getString("orgCode")
//                                : null),
//                notNull(" and o.name = ? ",
//                        bodyAsJson.getJsonObject("query") != null
//                                ? bodyAsJson.getJsonObject("query").getString("orgName")
//                                : null),
//                notNull(" and o.code like ? ", bodyAsJson.getJsonObject("filter") != null
//                        ? (bodyAsJson.getJsonObject("filter").getString("orgCode") != null
//                        ? "%" + bodyAsJson.getJsonObject("filter").getString("orgCode") + "%"
//                        : null)
//                        : null),
//                notNull(" and o.name like ? ",
//                        bodyAsJson.getJsonObject("filter") != null
//                                ? (bodyAsJson.getJsonObject("filter").getString("orgName") != null
//                                ? "%" + bodyAsJson.getJsonObject("filter").getString("orgName") + "%"
//                                : null)
//                                : null),
//                JSQLBOX.pagin(pageNo, pageSize));
//        List<Map<String, Object>> maps2 = ctx.iQueryForMapList(
//                "SELECT DISTINCT o.* from tb_org o left join  tb_org_user_relation tour on o.code = tour.org_code "
//                        + "left join tb_user tb on tb.code = tour.user_code  where 1=1 ",
//                notNull(" and o.code = ? ",
//                        bodyAsJson.getJsonObject("query") != null
//                                ? bodyAsJson.getJsonObject("query").getString("orgCode")
//                                : null),
//                notNull(" and o.name = ? ",
//                        bodyAsJson.getJsonObject("query") != null
//                                ? bodyAsJson.getJsonObject("query").getString("orgName")
//                                : null),
//                notNull(" and o.code like ? ", bodyAsJson.getJsonObject("filter") != null
//                        ? (bodyAsJson.getJsonObject("filter").getString("orgCode") != null
//                        ? "%" + bodyAsJson.getJsonObject("filter").getString("orgCode") + "%"
//                        : null)
//                        : null),
//                notNull(" and o.name like ? ",
//                        bodyAsJson.getJsonObject("filter") != null
//                                ? (bodyAsJson.getJsonObject("filter").getString("orgName") != null
//                                ? "%" + bodyAsJson.getJsonObject("filter").getString("orgName") + "%"
//                                : null)
//                                : null));

        String sqlTemplate = "SELECT DISTINCT o.* from tb_org o left join  tb_org_user_relation tour on o.code = tour.org_code "
                + "left join tb_user tb on tb.code = tour.user_code  where 1=1 ";

        String sqlResult = "DISTINCT o.*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(param(userCode));
        conditionAttrList.add(new ConditionAttr("o.code","orgCode"));
        conditionAttrList.add(new ConditionAttr("o.name","orgName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        return Utils.queryPage(TbRole.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page findUserRolePageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;

        String sqlTemplate = "select %s from tb_role tr left join  tb_role_user_relation trur on tr.code = trur.role_code left join"
                + " tb_user tb on tb.code = trur.user_code where 1=1 ";

        String sqlResult = "DISTINCT tr.*";

        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionList.add(sql(" and tb.code = ? "));
        conditionList.add(param(userCode));
        conditionAttrList.add(new ConditionAttr("tr.code","roleCode"));
        conditionAttrList.add(new ConditionAttr("tr.name","roleName"));
        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);
        return Utils.queryPage(TbRole.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public Page findUserOrgStationPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;

        String sqlTemplate = "select  DISTINCT ts.* from tb_station ts left join tb_org_station_relation tosr on ts.code = tosr.station_code left join"
                + " tb_org o on o.code = tosr.org_code left join tb_org_user_relation our on our.org_code = o.code where 1=1 and our.user_code = ? ";
        String sqlResult = "ts.*";

        List<SqlItem> conditionList = new ArrayList<>();
        conditionList.add(param(userCode));

        List<ConditionAttr> conditionAttrList = new ArrayList<>();
        conditionAttrList.add(new ConditionAttr("ts.code", "stationCode"));
        conditionAttrList.add(new ConditionAttr("ts.name", "stationName"));
        conditionAttrList.add(new ConditionAttr("o.code", "orgCode"));
        conditionAttrList.add(new ConditionAttr("o.name", "orgName"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

//        conditionList.add(sql(" order by ts." + orderBy));

        return Utils.queryPage(TbStation.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }
}
