package com.seadun.midi.equipment.monitoring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.equipment.entity.Alarm;
import com.seadun.midi.equipment.entity.Equipment;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 查询报警信息
 */
@Log4j2
public class MonitoringAlarm implements Runnable {
    @Override
    public void run() {
        SqlBoxContext globalSqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
        List<Map<String, Object>> list = globalSqlBoxContext.iQueryForMapList("select distinct p.port,d.ip,e.name equipmentName,c.name cabinetName,d.name detectName ,a.name areaName,a.code areaCode, a.detailed_location areaDetailedLocation, c.code cabinetCode,c.model cabinetModel,e.code equipmentCode,\n" +
                        " e.duty_org_code equipmentDutyOrgCode,e.duty_user_code equipmentDutyUserCode,e.secret_code equipmentSecretCode,\n" +
                        " dsc.secret_name equipmentSecretName,det.type_code equipmentTypeCode,det.type_name equipmentTypeName,lo.code locationCode,lo.name locationName,lo.status locationStatus\n" +
                        " from detect d,port p,equipment e,cabinet c,equipment_location_relation elr,port_location_relation plr,location lo,area a,dic_secret_code dsc,dic_equipment_type det where \n" +
                        " e.status = '1' and p.status = '1' and d.status = '1' and c.status = '1' and elr.equipment_code = e.code and elr.location_code = lo.code and d.area_code = c.area_code and plr.port_code = p.code and c.area_code = a.code and lo.cabinet_code = c.code \n" +
                        " and plr.location_code = lo.code and p.detect_code = d.code and d.area_code = a.code and dsc.secret_code = e.secret_code and det.type_code = e.type_code"
        );
        JsonObject jsb = new JsonObject();
        jsb.put("data", list);
        JSONObject jobj = JSON.parseObject(String.valueOf(jsb));
        JSONArray data = jobj.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            Object o = data.get(i);
            JSONObject jsonObject = JSON.parseObject(String.valueOf(o));
            String detectName = (String) jsonObject.get("detectName");
            String ip = (String) jsonObject.get("ip");
            Alarm alarm = JSON.toJavaObject(jsonObject, Alarm.class);
            IpParameters params = JSON.toJavaObject(jsonObject, IpParameters.class);
            params.setHost(ip);
            params.setEncapsulated(true);
            ModbusMaster master = new ModbusFactory().createTcpMaster(params, true);// TCP
            master.setTimeout(1000);
            Short num = 0;//探测标志1正常 0 表示告警
            try {
                master.init();
                BaseLocator<Number> loc = BaseLocator.holdingRegister(1, 0, DataType.TWO_BYTE_BCD);
                num = master.getValue(loc).shortValue();
                log.info("num:======="+num);
            } catch (ModbusInitException | ModbusTransportException | ErrorResponseException e) {
                e.printStackTrace();
                log.error("场所：" + alarm.getAreaName() + " 机柜：" + alarm.getCabinetName() + " 多路侦测器 ：" + detectName + " 重要设备：" + alarm.getEquipmentName() + " 的 ip：" + params.getHost()
                        + " 端口：" + params.getPort() + " 出问题了");

            }
            finally {
                master.destroy();
            }
            //只有设备为正常情况下才被监控 并更改设备状态为4 报警
            if(num == 0){
                Equipment equipment = new Equipment();
                equipment.setCode(alarm.getEquipmentCode());
                equipment.load();
                if("1".equals(equipment.getStatus())){
                    equipment.setStatus("4");
                    equipment.update(SqlOption.IGNORE_NULL);
                    alarm.setCode(UUID.randomUUID().toString());
                    alarm.setCrtTime(new Date());
                    alarm.setCrtUser("system");
                    alarm.setDetail("场所：" + alarm.getAreaName() + " 机柜：" + alarm.getCabinetName() + " 多路侦测器 ：" + detectName + " 的 ip：" + params.getHost()
                            + " 端口：" + params.getPort() + "的 " + alarm.getEquipmentName() + "设备：" + " 告警了");
                    alarm.setStatus("2");
                    alarm.insert(SqlOption.IGNORE_NULL);
                }
            }
        }
    }
//    public static void main(String[] args) {
//        IpParameters params = new IpParameters();
//        params.setHost("172.172.2.1");
//        params.setPort(7161);
//        params.setEncapsulated(true);
//
//        ModbusMaster master = new ModbusFactory().createTcpMaster(params, true);// TCP
//        master.setTimeout(1000);
//        Short num = 0;//探测标志1正常
//        try {
//            master.init();
//            BaseLocator<Number> loc = BaseLocator.holdingRegister(1, 0, DataType.TWO_BYTE_BCD);
//            num = master.getValue(loc).shortValue();
//            System.out.println("num:"+num);
//        } catch (ModbusInitException | ModbusTransportException | ErrorResponseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            System.out.println("num:"+num);
//        } finally {
//            master.destroy();
//        }
//    }
}
