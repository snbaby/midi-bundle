package com.seadun.midi.aas.service;

import com.github.drinkjava2.jdbpro.SqlItem;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.entity.ConditionAttr;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbPerson;
import com.seadun.midi.aas.entity.TbUser;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import com.seadun.midi.aas.util.Utils;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.drinkjava2.jdbpro.JDBPRO.param;
import static com.github.drinkjava2.jdbpro.JDBPRO.sql;
import static java.util.regex.Pattern.*;

import java.util.ArrayList;

public class PersonService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    public Page getPersonPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "crt_time desc";
//        String sqlTemplate = "SELECT %s FROM tb_person tp left join tb_org_person_relation topr on topr.person_code = tp.code where 1=1 and tp.status =2 ";
        String sqlTemplate = "SELECT %s FROM tb_person tp left join tb_org_person_relation topr on topr.person_code = tp.code where 1=1  ";
        String sqlResult = "tp.*";
        List<SqlItem> conditionList = new ArrayList<>();
        List<ConditionAttr> conditionAttrList = new ArrayList<>();

        conditionAttrList.add(new ConditionAttr("tp.code", "code"));
        conditionAttrList.add(new ConditionAttr("topr.org_code", "orgCode"));
        conditionAttrList.add(new ConditionAttr("tp.name", "name"));
        conditionAttrList.add(new ConditionAttr("tp.sex", "sex"));
        conditionAttrList.add(new ConditionAttr("tp.security_classification", "securityClassification"));
        conditionAttrList.add(new ConditionAttr("tp.status", "status"));
        conditionAttrList.add(new ConditionAttr("tp.address", "address"));
        conditionAttrList.add(new ConditionAttr("tp.mobile", "mobile"));
        conditionAttrList.add(new ConditionAttr("tp.telephone", "telephone"));
        conditionAttrList.add(new ConditionAttr("tp.email", "email"));

        Utils.constructConditions(bodyAsJson, conditionAttrList, conditionList);

        conditionList.add(sql(" order by tp." + orderBy));
        return Utils.queryPage(TbPerson.class, pageNo, pageSize, sqlTemplate, sqlResult, conditionList.toArray());
    }

    public List getPersonDetail(RoutingContext routingContext, SqlBoxContext ctx) {
        TbPerson person = new TbPerson();
        person.setCode(routingContext.pathParam("code"));
        SampleItem simpleItem = new SampleItem(person).sql(" where  ").notNullFields();
        List<TbPerson> personList = ctx.eFindAll(TbPerson.class, simpleItem);
        return personList;
    }

    public TbPerson getSelfDetail(RoutingContext routingContext, SqlBoxContext ctx) {
        TbPerson person = new TbPerson();
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        String userName = HeaderTokenUtils.getHeadersUserName(routingContext);
        person.setCode(userCode);

        if (!person.exist()) {
            person.setName(userName);
            person.setStatus("1");
            person.setCrtCode(userCode);
            person.setCrtTime(new Date());
            person.setUptTime(new Date());
            person.setUptCode(userCode);
            person.insert();
        }
        TbPerson load = person.load();
        return load;
    }

    @TX
    public void updatePersonDetail(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbPerson person = new TbPerson();
        String code = routingContext.pathParam("code");
        String name = bodyAsJson.getString("name");
        String sex = bodyAsJson.getString("sex");
        String securityClassification = bodyAsJson.getString("securityClassification");
        String email = bodyAsJson.getString("email");
        String telephone = bodyAsJson.getString("telephone");
        String mobile = bodyAsJson.getString("mobile");
        String address = bodyAsJson.getString("address");
        if (StringUtils.isNotBlank(address) && address.length() > 200) {
            throw new MidiException(400, "地址长度不能大于200");
        }
        if (StringUtils.isBlank(code)) {
            throw new MidiException(400, "人员唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 45) {
                throw new MidiException(400, "人员名称长度不能大于45");
            }
        }
        if (code.length() > 45) {
            throw new MidiException(400, "人员唯一标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(sex) && sex.length() > 2) {
            throw new MidiException(400, "性别长度不能大于2");
        }
        if (StringUtils.isNotBlank(sex)) {
            Pattern psex = compile("^[0-2]{1}$");
            Matcher msex = psex.matcher(sex);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的性别描述");
            }
        }

        if (StringUtils.isNotBlank(securityClassification) && securityClassification.length() > 2) {
            throw new MidiException(400, "涉密等级长度不能大于2");
        }
        Pattern psec = compile("^[0-3]{1}$");
        if (StringUtils.isNotBlank(securityClassification)) {
            Matcher msec = psec.matcher(securityClassification);
            if (!msec.matches()) {
                throw new MidiException(400, "请传入正确的涉密等级描述");
            }
        }
        if (StringUtils.isNotBlank(email) && email.length() > 200) {
            throw new MidiException(400, "email长度不能大于200");
        }
        if (StringUtils.isNotBlank(email)) {
            String check = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(email);
            if (!matcher.matches()) {
                throw new MidiException(400, "请填入正确的email值");
            }
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            throw new MidiException(400, "手机号码长度不能大于11");
        }
        if (StringUtils.isNotBlank(mobile)) {
            Pattern p = compile("^1[345789]\\d{9}$");
            Matcher m = p.matcher(mobile);
            if (!m.matches()) {
                throw new MidiException(400, "请填入正确的手机号码值");
            }
        }

        if (StringUtils.isNotBlank(telephone) && telephone.length() > 14) {
            throw new MidiException(400, "座机长度不能大于14");
        }
        if (StringUtils.isNotBlank(telephone)) {
            Pattern pattern = compile("^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$");
            Matcher mob = pattern.matcher(telephone);
            if (!mob.matches()) {
                throw new MidiException(400, "请填入正确的座机值");
            }
        }
        person.setCode(code);
        person.setSex(sex);
        if (!person.exist()) {
            throw new MidiException(400, "修改失败，不存在人员");
        }
        person.setName(name);
        person.setSecurityClassification(securityClassification);
        person.setEmail(email);
        person.setTelephone(telephone);
        person.setMobile(mobile);
        person.setAddress(address);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        person.setUptCode(userCode);
        person.setUptTime(new Date());
        person.update(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updateSelfDetail(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbPerson person = new TbPerson();
        String name = bodyAsJson.getString("name");
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        String sex = bodyAsJson.getString("sex");
        String securityClassification = bodyAsJson.getString("securityClassification");
        String email = bodyAsJson.getString("email");
        String telephone = bodyAsJson.getString("telephone");
        String mobile = bodyAsJson.getString("mobile");
        String address = bodyAsJson.getString("address");
        if (StringUtils.isNotBlank(address) && address.length() > 200) {
            throw new MidiException(400, "地址长度不能大于200");
        }
        if (StringUtils.isBlank(userCode)) {
            throw new MidiException(400, "人员唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 45) {
                throw new MidiException(400, "人员名称长度不能大于45");
            }
        }
        if (userCode.length() > 45) {
            throw new MidiException(400, "人员唯一标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(sex) && sex.length() > 2) {
            throw new MidiException(400, "性别长度不能大于2");
        }
        if (StringUtils.isNotBlank(sex)) {
            Pattern psex = compile("^[0-2]{1}$");
            Matcher msex = psex.matcher(sex);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的性别描述");
            }
        }

        if (StringUtils.isNotBlank(securityClassification) && securityClassification.length() > 2) {
            throw new MidiException(400, "涉密等级长度不能大于2");
        }
        Pattern psec = compile("^[0-3]{1}$");
        if (StringUtils.isNotBlank(securityClassification)) {
            Matcher msec = psec.matcher(securityClassification);
            if (!msec.matches()) {
                throw new MidiException(400, "请传入正确的涉密等级描述");
            }
        }
        if (StringUtils.isNotBlank(email) && email.length() > 200) {
            throw new MidiException(400, "email长度不能大于200");
        }
        if (StringUtils.isNotBlank(email)) {
            String check = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(email);
            if (!matcher.matches()) {
                throw new MidiException(400, "请填入正确的email值");
            }
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            throw new MidiException(400, "手机号码长度不能大于11");
        }
        if (StringUtils.isNotBlank(mobile)) {
            Pattern p = compile("^1[345789]\\d{9}$");
            Matcher m = p.matcher(mobile);
            if (!m.matches()) {
                throw new MidiException(400, "请填入正确的手机号码值");
            }
        }

        if (StringUtils.isNotBlank(telephone) && telephone.length() > 14) {
            throw new MidiException(400, "座机长度不能大于14");
        }
        if (StringUtils.isNotBlank(telephone)) {
            Pattern pattern = compile("^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$");
            Matcher mob = pattern.matcher(telephone);
            if (!mob.matches()) {
                throw new MidiException(400, "请填入正确的座机值");
            }
        }
        person.setSex(sex);
        person.setCode(userCode);
        if (!person.exist()) {
            throw new MidiException(400, "修改失败，不存在人员");
        }
        person.setName(name);
        person.setSecurityClassification(securityClassification);
        person.setEmail(email);
        person.setTelephone(telephone);
        person.setMobile(mobile);
        person.setAddress(address);
        person.setUptCode(userCode);
        person.setUptTime(new Date());
        person.update(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updatePersonStatus(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbPerson person = new TbPerson();
        if (routingContext.pathParam("code") != null && routingContext.pathParam("code").length() > 45) {
            throw new MidiException(400, "code长度不能大于45");
        }
        person.setCode(routingContext.pathParam("code"));
        if (!person.exist()) {
            throw new MidiException(404, "人员不存在");
        }
        person.setCode(routingContext.pathParam("code"));
        if (StringUtils.isNotBlank(bodyAsJson.getString("status"))) {
            Pattern psex = compile("^[0-2]{1}$");
            Matcher msex = psex.matcher(bodyAsJson.getString("status"));
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的status");
            }
        }
        person.setStatus(bodyAsJson.getString("status"));
        if (!person.exist()) {
            throw new MidiException(400, "修改失败，不存在人员");
        }
         //处理在tb_person注销时 tb_user也注销
        if("0".equals(bodyAsJson.getString("status"))){
            TbUser tbUser = new TbUser();
            tbUser.setCode(routingContext.pathParam("code"));
            tbUser.setStatus("3");
            tbUser.updateTry(SqlOption.IGNORE_NULL);
        }
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        person.setUptCode(userCode);
        person.setUptTime(new Date());
        person.setCode(routingContext.pathParam("code"));
        person.update(SqlOption.IGNORE_NULL);
    }

    public void createPerson(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        TbPerson person = new TbPerson(bodyAsJson);
        String name = bodyAsJson.getString("name");
        String code = bodyAsJson.getString("code");
        String sex = bodyAsJson.getString("sex");
        String securityClassification = bodyAsJson.getString("securityClassification");
        String email = bodyAsJson.getString("email");
        String telephone = bodyAsJson.getString("telephone");
        String mobile = bodyAsJson.getString("mobile");
        String address = bodyAsJson.getString("address");
        if (StringUtils.isNotBlank(address) && address.length() > 200) {
            throw new MidiException(400, "地址长度不能大于200");
        }
        if (StringUtils.isBlank(code) || StringUtils.isBlank(name)) {
            throw new MidiException(400, "人员唯一标识或名称不能为空");
        }
        if (code.length() > 45 || name.length() > 45) {
            throw new MidiException(400, "人员唯一标识或名称长度不能大于45");
        }
        if (StringUtils.isNotBlank(sex) && sex.length() > 2) {
            throw new MidiException(400, "性别长度不能大于2");
        }

        if (StringUtils.isNotBlank(sex)) {
            Pattern psex = compile("^[0-2]{1}$");
            Matcher msex = psex.matcher(sex);
            if (!msex.matches()) {
                throw new MidiException(400, "请传入正确的性别描述");
            }
        }

        if (StringUtils.isNotBlank(securityClassification) && securityClassification.length() > 2) {
            throw new MidiException(400, "涉密等级长度不能大于2");
        }
        Pattern psec = compile("^[0-3]{1}$");
        if (StringUtils.isNotBlank(securityClassification)) {
            Matcher msec = psec.matcher(securityClassification);
            if (!msec.matches()) {
                throw new MidiException(400, "请传入正确的涉密等级描述");
            }
        }
        if (StringUtils.isNotBlank(email) && email.length() > 200) {
            throw new MidiException(400, "email长度不能大于200");
        }
        if (StringUtils.isNotBlank(email)) {
            String check = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
            Pattern regex = compile(check);
            Matcher matcher = regex.matcher(email);
            if (!matcher.matches()) {
                throw new MidiException(400, "请填入正确的email值");
            }
        }
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 11) {
            throw new MidiException(400, "手机号码长度不能大于11");
        }
        if (StringUtils.isNotBlank(mobile)) {
            Pattern p = compile("^1[345789]\\d{9}$");
            Matcher m = p.matcher(mobile);
            if (!m.matches()) {
                throw new MidiException(400, "请填入正确的手机号码值");
            }
        }

        if (StringUtils.isNotBlank(telephone) && telephone.length() > 14) {
            throw new MidiException(400, "座机长度不能大于14");
        }
        if (StringUtils.isNotBlank(telephone)) {
            Pattern pattern = compile("^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$");
            Matcher mob = pattern.matcher(telephone);
            if (!mob.matches()) {
                throw new MidiException(400, "请填入正确的座机值");
            }
        }

        person.setCode(code);
        if (person.exist()) {
            throw new MidiException(400, "添加失败，人员唯一标识已存在");
        }
        person.setStatus("1");
        person.setName(name);
        person.setSex(sex);
        person.setTelephone(telephone);
        person.setEmail(email);
        person.setSecurityClassification(securityClassification);
        person.setMobile(mobile);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        person.setCrtCode(userCode);
        person.setUptCode(userCode);
        person.setAddress(address);
        person.setCrtTime(new Date());
        person.setUptTime(new Date());
        person.insert(SqlOption.IGNORE_NULL);
    }


    public void respSuccess(RoutingContext routingContext, JsonObject jsonObject) {
        routingContext
                .response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(200)
                .end(jsonObject.encode());
    }

    public void respError(RoutingContext routingContext, Integer errorCode, JsonObject jsonObject) {
        routingContext
                .response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(errorCode)
                .end(jsonObject.encode());
    }
}
