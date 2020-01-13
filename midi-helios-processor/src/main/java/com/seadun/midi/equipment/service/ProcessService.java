package com.seadun.midi.equipment.service;


import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jdialects.Dialect;
import com.github.drinkjava2.jsqlbox.JSQLBOX;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.annotation.TX;
import com.seadun.midi.equipment.entity.*;
import com.seadun.midi.equipment.entity.Process;
import com.seadun.midi.equipment.exception.MidiException;
import com.seadun.midi.equipment.util.HeaderTokenUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.drinkjava2.jdbpro.JDBPRO.param;

public class ProcessService {

    public void getSampleItem(SampleItem simpleItem, String sql, String param) {
        if (StringUtils.isNotBlank(param)) {
            simpleItem.sql("and " + sql + " like ? ").param("%" + param + "%");
        }
    }

    @TX
    public Page getProcessPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        Process process = new Process();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            process = new Process(query);
        }
        SampleItem simpleItem = new SampleItem(process).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "apply_code", filter.getString("applyCode"));
            getSampleItem(simpleItem, "apply_name", filter.getString("applyName"));
            getSampleItem(simpleItem, "approve_code", filter.getString("approveCode"));
            getSampleItem(simpleItem, "approve_name", filter.getString("approveName"));
            getSampleItem(simpleItem, "process_info", filter.getString("processInfo"));
            getSampleItem(simpleItem, "type", filter.getString("type"));
        }
        List<Process> maps = ctx.eFindAll(Process.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Process> maps2 = ctx.eFindAll(Process.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    @TX
    public Page getProcessApplyPage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "apply_time desc";
        Process process = new Process();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            process = new Process(query);
        }
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        process.setApplyCode(userCode);
        SampleItem simpleItem = new SampleItem(process).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "approve_code", filter.getString("approveCode"));
            getSampleItem(simpleItem, "approve_name", filter.getString("approveName"));
            getSampleItem(simpleItem, "process_info", filter.getString("processInfo"));
            getSampleItem(simpleItem, "type", filter.getString("type"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<Process> maps = ctx.eFindAll(Process.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Process> maps2 = ctx.eFindAll(Process.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    @TX
    public Page getProcessApprovePage(RoutingContext routingContext, SqlBoxContext ctx) {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        Integer pageNo = bodyAsJson.getInteger("pageNo") != null ? bodyAsJson.getInteger("pageNo") : 1;
        Integer pageSize = bodyAsJson.getInteger("pageSize") != null ? bodyAsJson.getInteger("pageSize") : 10;
        String orderBy = StringUtils.isNotBlank(bodyAsJson.getString("orderBy")) ? bodyAsJson.getString("orderBy") : "apply_time desc";
        Process process = new Process();
        if (bodyAsJson.getJsonObject("query") != null && !bodyAsJson.getJsonObject("query").isEmpty()) {
            JsonObject query = bodyAsJson.getJsonObject("query");
            process = new Process(query);
        }
        String userCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
        process.setApproveCode(userCode);
        SampleItem simpleItem = new SampleItem(process).sql(" where  ").notNullFields();
        if (bodyAsJson.getJsonObject("filter") != null && !bodyAsJson.getJsonObject("filter").isEmpty()) {
            JsonObject filter = bodyAsJson.getJsonObject("filter");
            getSampleItem(simpleItem, "apply_code", filter.getString("applyCode"));
            getSampleItem(simpleItem, "apply_name", filter.getString("applyName"));
            getSampleItem(simpleItem, "process_info", filter.getString("processInfo"));
            getSampleItem(simpleItem, "type", filter.getString("type"));
        }
        simpleItem.sql(" order by " + orderBy);
        List<Process> maps = ctx.eFindAll(Process.class, simpleItem, JSQLBOX.pagin(pageNo, pageSize));
        List<Process> maps2 = ctx.eFindAll(Process.class, simpleItem);
        Page page = new Page();
        page.setData(maps);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(maps2.size());
        return page;
    }

    @TX
    public void approveProcess(RoutingContext requestHandler) throws MidiException {
        String id = requestHandler.pathParam("id");
        JSONObject jsb = JSONObject.parseObject(requestHandler.getBodyAsString());
        String approveStatuls = jsb.getString("approveStatuls");
        String approveDes = jsb.getString("approveDes");
        Process process = new Process();
        process.setId(id);
        process.load();
        if (approveDes.length() > 200) {
            throw new MidiException(400, "审批原因字符不超过200");
        }
        if (process.getApproveStatus().equals("0") && process.getProcessStatus().equals("0")) {
            if (approveStatuls.equals("1") || approveStatuls.equals("2")) {
                Process updateProcess = new Process();
                updateProcess.setId(id);
                updateProcess.setApproveStatus(approveStatuls);
                updateProcess.setApproveDes(approveDes);
                updateProcess.setProcessStatus("1");
                updateProcess.setApproveTime(new Date());
                updateProcess.update(SqlOption.IGNORE_NULL);
                String userName = process.getApproveCode();
                String type = process.getType();
                if (approveStatuls.equals("1")) {
                    if (type.startsWith("0-")) {
                        Area area = new Area();
                        area.setCode(process.getCode());
                        if (!area.exist()) {
                            throw new MidiException(404, "场所不存在");
                        }
                        area.load();
                        area.setUptTime(new Date());
                        area.setUptUser(userName);
                        if (type.equals("0-0")) {
                            if (area.getStatus().equals("2")) {
                                area.setStatus("1");
                                area.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "场所不是停用状态 无法启用");
                            }
                        } else if (type.equals("0-1")) {
                            if (area.getStatus().equals("1")) {
                                area.setStatus("2");
                                area.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "场所不是启用状态 无法停用");
                            }
                        } else if (type.equals("0-2")) {
                            area.setStatus("3");
                            area.update(SqlOption.IGNORE_NULL);
                        } else {
                            throw new MidiException(400, "未知流程  无法审批");
                        }
                    } else if (type.startsWith("1-")) {
                        Cabinet cabinet = new Cabinet();
                        cabinet.setCode(process.getCode());
                        if (!cabinet.exist()) {
                            throw new MidiException(404, "机柜不存在");
                        }
                        cabinet.load();
                        cabinet.setUptTime(new Date());
                        cabinet.setUptUser(userName);
                        if (type.equals("1-0")) {
                            if (cabinet.getStatus().equals("2")) {
                                cabinet.setStatus("1");
                                cabinet.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "机柜不是停用状态，无法启用");
                            }
                        } else if (type.equals("1-1")) {
                            if (cabinet.getStatus().equals("1")) {
                                cabinet.setStatus("2");
                                cabinet.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "机柜不是使用状态，无法停用");
                            }
                        } else if (type.equals("1-2")) {
                            cabinet.setStatus("3");
                            cabinet.update(SqlOption.IGNORE_NULL);
                        } else {
                            throw new MidiException(400, "未知流程  无法审批");
                        }
                    } else if (type.startsWith("2-")) {
                        Equipment equipment = new Equipment();
                        equipment.setCode(process.getCode());
                        if (!equipment.exist()) {
                            throw new MidiException(404, "重要设备不存在");
                        }
                        equipment.load();
                        equipment.setUptTime(new Date());
                        equipment.setUptUser(userName);
                        if (type.equals("2-0")) {
                            if (equipment.getStatus().equals("2")) {
                                equipment.setStatus("1");
                                equipment.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "设备不是停用状态，无法启用");
                            }
                        } else if (type.equals("2-1")) {
                            if (equipment.getStatus().equals("1")) {
                                equipment.setStatus("2");
                                equipment.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "设备不是使用状态，无法停用");
                            }
                        } else if (type.equals("2-2")) {
                            if (equipment.getStatus().equals("1") || equipment.getStatus().equals("2")) {
                                equipment.setStatus("3");
                                equipment.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "设备不是使用状态或停用状态，无法离开");
                            }
                        } else if (type.equals("2-3")) {
                            equipment.setStatus("5");
                            equipment.update(SqlOption.IGNORE_NULL);
                        } else if (type.equals("2-4")) {
                            if (equipment.getStatus().equals("3")) {
                                equipment.setStatus("1");
                                equipment.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "设备不是离开状态，无法返回");
                            }
                        } else {
                            throw new MidiException(400, "未知流程  无法审批");
                        }
                    } else if (type.startsWith("3-")) {
                        Detect detect = new Detect();
                        detect.setCode(process.getCode());
                        if (!detect.exist()) {
                            throw new MidiException(404, "串口服务器不存在");
                        }
                        detect.load();
                        detect.setUptTime(new Date());
                        detect.setUptUser(userName);
                        if (type.equals("3-0")) {
                            if (detect.getStatus().equals("2")) {
                                detect.setStatus("1");
                                detect.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "串口服务器不是使用状态，无法离开");
                            }
                        } else if (type.equals("3-1")) {
                            if (detect.getStatus().equals("1")) {
                                detect.setStatus("2");
                                detect.update(SqlOption.IGNORE_NULL);
                            } else {
                                throw new MidiException(400, "串口服务器不是离开状态，无法使用");
                            }
                        } else if (type.equals("3-2")) {
                            detect.setStatus("3");
                            detect.update(SqlOption.IGNORE_NULL);
                        } else {
                            throw new MidiException(400, "未知流程  无法审批");
                        }
                    } else if (type.startsWith("4-")) {
                        Alarm alarm = new Alarm();
                        alarm.setCode(process.getCode());
                        if (!alarm.exist()) {
                            throw new MidiException(404, "告警不存在");
                        }
                        alarm.load();
                        alarm.setUptTime(new Date());
                        alarm.setUptUser(userName);
                        if (type.equals("4-0")) {
                            if (alarm.getStatus().equals("2")) {
                                alarm.setStatus("1");
                                alarm.update(SqlOption.IGNORE_NULL);
                                Equipment equipment = new Equipment();
                                equipment.setCode(alarm.getEquipmentCode());
                                equipment.load();
                                if (equipment.getStatus().equals("4")) {
                                    equipment.setUptTime(new Date());
                                    equipment.setUptUser(userName);
                                    equipment.setStatus("1");
                                    equipment.update(SqlOption.IGNORE_NULL);
                                }
                            } else {
                                throw new MidiException(400, "该报警已被解决");
                            }
                        } else {
                            throw new MidiException(400, "未知流程  无法审批");
                        }
                    } else {
                        throw new MidiException(400, "未知流程  无法审批");
                    }
                }
            } else {
                throw new MidiException(400, "审批状态不正确");
            }
        } else {
            throw new MidiException(400, "该流程不存在或已被审批");
        }
    }

    @TX
    public void insertProcess(RoutingContext requestHandler) throws MidiException {
        JSONObject jsb = JSONObject.parseObject(requestHandler.getBodyAsString());
        String type = jsb.getString("type");
        String approveCode = jsb.getString("approveCode");
        String approveName = jsb.getString("approveName");
        String code = jsb.getString("code");
        String name = jsb.getString("name");
        String applyDes = jsb.getString("applyDes");
        String userCode = HeaderTokenUtils.getHeadersUserCode(requestHandler);
        String userName = HeaderTokenUtils.getHeadersUserNickName(requestHandler);
        String applyCode = userCode;
        String applyName = userName;
        String processInfo = "";

        if (StringUtils.isBlank(type)) {
            throw new MidiException(400, "流程类型不能为空");
        }

        if (type.length() > 45) {
            throw new MidiException(400, "流程类型不能大于45");
        }

        if (applyDes.length() > 200) {
            throw new MidiException(400, "发起原因字符不超过200");
        }

        if (StringUtils.isBlank(approveCode)) {
            throw new MidiException(400, "审批人唯一编码不能为空");
        }

        if (approveCode.length() > 45) {
            throw new MidiException(400, "审批人唯一编码不能大于45");
        }

        if (StringUtils.isBlank(approveName)) {
            throw new MidiException(400, "审批人姓名不能为空");
        }

        if (approveName.length() > 45) {
            throw new MidiException(400, "审批人姓名不能大于45");
        }

        if (StringUtils.isBlank(code)) {
            throw new MidiException(400, "编码不能为空");
        }

        if (code.length() > 45) {
            throw new MidiException(400, "编码不能大于45");
        }

        if (StringUtils.isBlank(name)) {
            throw new MidiException(400, "名字不能为空");
        }

        if (name.length() > 45) {
            throw new MidiException(400, "名字不能大于45");
        }

        if (type.startsWith("0-")) {
            Area area = new Area();
            area.setCode(code);
            if (!area.exist()) {
                throw new MidiException(404, "场所不存在");
            }
            area.load();

            if (type.equals("0-0") && !area.getStatus().equals("2")) {
                throw new MidiException(400, "场所不是停用状态，无法启用");
            } else if (type.equals("0-1") && !area.getStatus().equals("1")) {
                throw new MidiException(400, "场所不是启用状态，无法停用");
            }
            processInfo = JSONObject.toJSONString(area);

        } else if (type.startsWith("1-")) {
            Cabinet cabinet = new Cabinet();
            cabinet.setCode(code);
            if (!cabinet.exist()) {
                throw new MidiException(404, "机柜不存在");
            }
            cabinet.load();

            if (type.equals("1-0") && !cabinet.getStatus().equals("2")) {
                throw new MidiException(400, "机柜不是停用状态，无法启用");
            } else if (type.equals("1-1") && !cabinet.getStatus().equals("1")) {
                throw new MidiException(400, "机柜不是启用状态，无法停用");
            } else if ("1-2".equals(type)) {
                int i = SqlBoxContext.getGlobalSqlBoxContext().nQueryForIntValue("select count(*) from location" +
                        " where cabinet_code = ? ", cabinet.getCode());
                if (i > 0) {
                    throw new MidiException(400, "机柜已绑定位置,无法废弃；如需废弃，请先删除位置。");
                }
            }

            processInfo = JSONObject.toJSONString(cabinet);

        } else if (type.startsWith("2-")) {
            Equipment equipment = new Equipment();
            equipment.setCode(code);
            if (!equipment.exist()) {
                throw new MidiException(404, "重要设备不存在");
            }
            equipment.load();

            if (type.equals("2-0") && !equipment.getStatus().equals("2")) {
                throw new MidiException(400, "重要设备不是停用状态，无法启用");
            } else if (type.equals("2-1") && !equipment.getStatus().equals("1")) {
                throw new MidiException(400, "重要设备不是启用状态，无法停用");
            } else if (type.equals("2-2") && !(equipment.getStatus().equals("1") || equipment.getStatus().equals("2"))) {
                throw new MidiException(400, "重要设备不是停用或启用状态，无法离开");
            } else if (type.equals("2-3")) {
                int i = SqlBoxContext.getGlobalSqlBoxContext().nQueryForIntValue("select count(*) from equipment_location_relation" +
                        " where equipment_code = ? ", equipment.getCode());
                if (i > 0) {
                    throw new MidiException(400, "重要设备已绑定位置,无法废弃；如需废弃，请先删除位置。");
                }
            } else if (type.equals("2-4") && !equipment.getStatus().equals("3")) {
                throw new MidiException(400, "重要设备不是离开状态，无法返回");
            }

            processInfo = JSONObject.toJSONString(equipment);

        } else if (type.startsWith("3-")) {
            Detect detect = new Detect();
            detect.setCode(code);
            if (!detect.exist()) {
                throw new MidiException(404, "串口服务器不存在");
            }
            detect.load();

            if (type.equals("3-0") && !detect.getStatus().equals("2")) {
                throw new MidiException(400, "串口服务器不是停用状态，无法启用");
            } else if (type.equals("3-1") && !detect.getStatus().equals("1")) {
                throw new MidiException(400, "串口服务器不是启用状态，无法停用");
            } else if ("3-2".equals(type)) {
                int i = SqlBoxContext.getGlobalSqlBoxContext().nQueryForIntValue("SELECT count(*) from port_location_relation plr " +
                        "LEFT JOIN `port` p ON plr.port_code = p.`code` " +
                        "LEFT JOIN detect d on d.`code` = p.detect_code where d.`code` = ?", detect.getCode());
                if (i > 0) {
                    throw new MidiException(400, "串口服务器已绑定位置,无法废弃；如需废弃，请先删除位置。");
                }
            }

            processInfo = JSONObject.toJSONString(detect);
        } else if (type.startsWith("4-")) {
            Alarm alarm = new Alarm();
            alarm.setCode(code);
            if (!alarm.exist()) {
                throw new MidiException(404, "告警不存在");
            }
            alarm.load();

            if (type.equals("4-0") && alarm.getStatus().equals("1")) {
                throw new MidiException(400, "告警状态已解决,无法发起");
            }

            processInfo = JSONObject.toJSONString(alarm);
        } else {
            throw new MidiException(400, "流程不存在");
        }

        List<Map<String, Object>> o = SqlBoxContext.getGlobalSqlBoxContext().iQueryForMapList(" select * from process where code = '" + code + "' and process_status = 0 ");
        if (o.size() > 0) {
            String processApproveCode = o.get(0).get("approve_code").toString();
            TbUser tbUser = new TbUser();
            tbUser.setCode(processApproveCode);
            tbUser.load(SqlOption.IGNORE_NULL);
            if ("3".equals(tbUser.getStatus())) {
                SqlBoxContext.getGlobalSqlBoxContext().nExecute("delete from process where id = ?", o.get(0).get("id").toString());
            }
        }
        int i = SqlBoxContext.getGlobalSqlBoxContext().iQueryForIntValue("select count(*) from process where code = ? and process_status != 1 ",
                param(code));
        if (i > 0) {
            throw new MidiException(403, "当前操作已存在流程，需处理后才能提交流程");
        }

        Process process = new Process();
        process.setId(UUID.randomUUID().toString());
        process.setApplyCode(applyCode);
        process.setApplyName(applyName);
        process.setApplyTime(new Date());
        process.setApplyDes(applyDes);
        process.setApproveCode(approveCode);
        process.setApproveName(approveName);
        process.setApproveStatus("0");// 待审批
        process.setCode(code);
        process.setName(name);
        process.setProcessInfo(processInfo);
        process.setProcessStatus("0");// 待审批
        process.setType(type);
        process.insert(SqlOption.IGNORE_NULL);

    }

    @TX
    public Page getProcessUser(RoutingContext routingContext, SqlBoxContext ctx) {
        TbProcessUser user = new TbProcessUser();
        List<TbProcessUser> maps = user.findAll();
        Page page = new Page();
        page.setData(maps);
        return page;
    }

}
