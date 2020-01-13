package com.seadun.midi.equipment.service;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.*;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.drinkjava2.jdbpro.JDBPRO.notNull;
import static java.util.regex.Pattern.compile;

public class EquipmentService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public void insertEquipment(RoutingContext routingContext) throws MidiException {
        String name = routingContext.getBodyAsJson().getString("name");
        String areaCode = routingContext.getBodyAsJson().getString("areaCode");
        Area area = new Area();
        area.setCode(areaCode);
        if(!area.exist()){
            throw new MidiException(400,"添加失败 场所不存在");
        }
        if (StringUtils.isBlank(name)) {
            throw new MidiException(400, "名称不能为空");
        }
        if (name.length() > 45) {
            throw new MidiException(400, "名称长度不能大于45");
        }
        String dutyUserCode = routingContext.getBodyAsJson().getString("dutyUserCode");
        String dutyOrgCode = routingContext.getBodyAsJson().getString("dutyOrgCode");
        if (StringUtils.isNotBlank(dutyUserCode)) {
            TbPerson tbUser = new TbPerson();
            tbUser.setCode(dutyUserCode);
            if (!tbUser.exist()) {
                throw new MidiException(400, "添加失败 职责人员不存在");
            }
        }

        if (StringUtils.isNotBlank(dutyOrgCode)) {
            TbOrg tbOrg = new TbOrg();
            tbOrg.setCode(dutyOrgCode);
            if (!tbOrg.exist()) {
                throw new MidiException(400, "添加失败 组织人员不存在");
            }
        }
        String typeCode = routingContext.getBodyAsJson().getString("typeCode");
        if (StringUtils.isNotBlank(typeCode)) {
            DicEquipmentType dicEquipmentType = new DicEquipmentType();
            dicEquipmentType.setTypeCode(typeCode);
            if (!dicEquipmentType.exist()) {
                throw new MidiException(400, "添加失败 所需要的设备类型不存在");
            }
        }
        String secretCode = routingContext.getBodyAsJson().getString("secretCode");
        if (StringUtils.isNotBlank(secretCode)) {
            DicSecretCode dicSecretCode = new DicSecretCode();
            dicSecretCode.setSecretCode(secretCode);
            if (!dicSecretCode.exist()) {
                throw new MidiException(400, "添加失败 所需要的密级编码不存在");
            }
        }
        Equipment equipment = new Equipment();
        equipment.setCode(UUID.randomUUID().toString());
        equipment.setName(name);
        equipment.setAreaCode(areaCode);
        equipment.setDutyUserCode(dutyUserCode);
        equipment.setDutyOrgCode(dutyOrgCode);
        equipment.setStatus("1");
        equipment.setTypeCode(typeCode);
        equipment.setSecretCode(secretCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        equipment.setUptUser(userCode);
        equipment.setUptTime(new Date());
        equipment.setCrtTime(new Date());
        equipment.setCrtUser(userCode);
        equipment.insert(SqlOption.IGNORE_NULL);
    }

    public Page findEquipmentPageList(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = bodyAsJson.getString("orderBy") != null ? bodyAsJson.getString("orderBy") : "upt_time desc";
        List<Map<String, Object>> equipment = ctx.iQueryForMapList(
                "select  e.name,e.code,e.area_code areaCode,e.type_code typeCode,e.status,e.duty_user_code dutyUserCode," +
                        "e.duty_org_code dutyOrgCode,e.secret_code secretCode,e.crt_user crtUser,e.crt_time crtTime,e.upt_user uptUser," +
                        "tu.code as personCode, tu.name as personName,tu.mobile,tu.telephone,tu.email,tor.code orgCode,tor.name orgName from equipment e LEFT JOIN tb_person tu on e.duty_user_code = tu.`code` " +
                        "LEFT JOIN tb_org tor on tor.`code` = e.duty_org_code where 1=1 ",
                notNull(" and e.code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("code") : null),
                notNull(" and e.area_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("areaCode") : null),
                notNull(" and e.type_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("typeCode") : null),
                notNull(" and e.secret_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("secretCode") : null),
                notNull(" and e.status = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("status") : null),
                notNull(" and e.name = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("name") : null),
                notNull(" and e.duty_user_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyUserCode") : null),
                notNull(" and e.duty_org_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyOrgCode") : null),
                notNull(" and e.code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("code") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("code") + "%" : null) : null),
                notNull(" and e.name like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("name") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("name") + "%" : null) : null),
                notNull(" and e.duty_user_code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("dutyUserCode") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("dutyUserCode") + "%" : null) : null),
                notNull(" and e.duty_org_code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("dutyOrgCode") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("dutyOrgCode") + "%" : null) : null),
                JSQLBOX.pagin(pageNo, pageSize));
        List<Map<String, Object>> equipment2 = ctx.iQueryForMapList(
                "select  e.name,e.code,e.area_code areaCode,e.type_code typeCode,e.status,e.duty_user_code dutyUserCode," +
                        "e.duty_org_code dutyOrgCode,e.secret_code secretCode,e.crt_user crtUser,e.crt_time crtTime,e.upt_user uptUser," +
                        "tu.code as personCode, tu.name as personName,tu.mobile,tu.telephone,tu.email,tor.code orgCode,tor.name orgName from equipment e LEFT JOIN tb_person tu on e.duty_user_code = tu.`code` " +
                        "LEFT JOIN tb_org tor on tor.`code` = e.duty_org_code where 1=1 ",
                notNull(" and e.code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("code") : null),
                notNull(" and e.area_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("areaCode") : null),
                notNull(" and e.type_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("typeCode") : null),
                notNull(" and e.secret_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("secretCode") : null),
                notNull(" and e.status = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("status") : null),
                notNull(" and e.name = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("name") : null),
                notNull(" and e.duty_user_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyUserCode") : null),
                notNull(" and e.duty_org_code = ? ", bodyAsJson.getJsonObject("query") != null ? bodyAsJson.getJsonObject("query").getString("dutyOrgCode") : null),
                notNull(" and e.code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("code") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("code") + "%" : null) : null),
                notNull(" and e.name like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("name") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("name") + "%" : null) : null),
                notNull(" and e.duty_user_code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("dutyUserCode") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("dutyUserCode") + "%" : null) : null),
                notNull(" and e.duty_org_code like ? ", bodyAsJson.getJsonObject("filter") != null ? (bodyAsJson.getJsonObject("filter").getString("dutyOrgCode") != null
                        ? "%" + bodyAsJson.getJsonObject("filter").getString("dutyOrgCode") + "%" : null) : null));
        Page page = new Page();
        page.setData(equipment);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(equipment2.size());
        return page;
    }

    public Equipment findByEquipmentCode(RoutingContext routingContext, SqlBoxContext ctx) {
        String code = routingContext.pathParam("code");
        Equipment equipment = new Equipment();
        equipment.setCode(code);
        equipment.load();
        return equipment;
    }

    @TX
    public void deleteEquipmentByCode(RoutingContext routingContext) {
        String code = routingContext.pathParam("code");
        Equipment equipment = new Equipment();
        equipment.setCode(code);
        equipment.deleteTry(equipment);
    }

    @TX
    public void updateEquipmentStatus(RoutingContext routingContext) throws MidiException{
        String code = routingContext.pathParam("code");
        String status = routingContext.getBodyAsJson().getString("status");
        if(StringUtils.isBlank(status)){
            throw new MidiException(400,"状态值不能为空");
        }
        if(StringUtils.isNotBlank(status)){
            if("5".equals(status)){
                throw new MidiException(400,"设备已被废弃不可更改状态");
            }
            Pattern p = compile("^[1-5]{1}$");
            Matcher matcher = p.matcher(status);
            if(!matcher.matches()){
                throw new MidiException(400,"请传入正确的状态");
            }
        }
        Equipment equipment = new Equipment();
        //返回成功与否
        equipment.setCode(code);
        equipment.setStatus(status);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        equipment.setUptUser(userCode);
        equipment.setUptTime(new Date());
        equipment.updateTry(SqlOption.IGNORE_NULL);
    }

    @TX
    public void updateEquipment(RoutingContext routingContext) throws MidiException {
        String code = routingContext.pathParam("code");
        String name = routingContext.getBodyAsJson().getString("name");
        String dutyUserCode = routingContext.getBodyAsJson().getString("dutyUserCode");
        String dutyOrgCode = routingContext.getBodyAsJson().getString("dutyOrgCode");
        String typeCode = routingContext.getBodyAsJson().getString("typeCode");
        String secretCode = routingContext.getBodyAsJson().getString("secretCode");

        if (StringUtils.isBlank(code)) {
            throw new MidiException(400, "唯一标识不能为空");
        }
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 45) {
                throw new MidiException(400, "名称长度不能大于45");
            }
        }
        if (code.length() > 45) {
            throw new MidiException(400, "唯一标识长度不能大于45");
        }
        if (StringUtils.isNotBlank(dutyUserCode)) {
            TbPerson tbUser = new TbPerson();
            tbUser.setCode(dutyUserCode);
            if (!tbUser.exist()) {
                throw new MidiException(400, "添加失败 职责人员不存在");
            }
        }

        if (StringUtils.isNotBlank(dutyOrgCode)) {
            TbOrg tbOrg = new TbOrg();
            tbOrg.setCode(dutyOrgCode);
            if (!tbOrg.exist()) {
                throw new MidiException(400, "添加失败 组织人员不存在");
            }
        }
        if (StringUtils.isNotBlank(typeCode)) {
            DicEquipmentType dicEquipmentType = new DicEquipmentType();
            dicEquipmentType.setTypeCode(typeCode);
            if (!dicEquipmentType.exist()) {
                throw new MidiException(400, "修改失败 所需要的设备类型不存在");
            }
        }
        if (StringUtils.isNotBlank(secretCode)) {
            DicSecretCode dicSecretCode = new DicSecretCode();
            dicSecretCode.setSecretCode(secretCode);
            if (!dicSecretCode.exist()) {
                throw new MidiException(400, "修改失败 所需要的密级编码不存在");
            }
        }


        Equipment equipment = new Equipment();
        //返回成功与否
        equipment.setCode(code);
        if (!equipment.exist()) {
            throw new MidiException(400, "修改失败 重要设备不存在");
        }
        List<Equipment> bySample = equipment.findBySample();
        if (!"1".equals(bySample.get(0).getStatus())) {
            throw new MidiException(400, "修改失败 设备非正常状态不可修改");
        }
        equipment.setName(name);
        equipment.setDutyOrgCode(dutyOrgCode);
        equipment.setDutyUserCode(dutyUserCode);
        equipment.setTypeCode(typeCode);
        equipment.setSecretCode(secretCode);
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        equipment.setUptUser(userCode);
        equipment.setUptTime(new Date());
        equipment.update(SqlOption.IGNORE_NULL);
    }

}
