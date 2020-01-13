package com.seadun.midi.equipment.util;

import com.alibaba.fastjson.JSON;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import com.seadun.midi.equipment.entity.Alarm;
import com.seadun.midi.equipment.entity.Cabinet;
import com.seadun.midi.equipment.entity.Equipment;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.ServerWebSocket;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Log4j2
public class WebSocketAlarmVerticle extends AbstractVerticle {

    private Map<String, ServerWebSocket> localMap;
    static long timerIdAlarm;
    @Override
    public void start() {
        localMap = new HashMap<>();
        vertx.createHttpServer().websocketHandler(serverWebSocket -> {
            String path = serverWebSocket.path();
            if ("/ws/alarm".equals(path)) {
//                serverWebSocket.handler(buffer -> {
                String id = serverWebSocket.textHandlerID();
                localMap.put(id, serverWebSocket);
//                    log.info("--------------------");
//                    log.info("server收到消息: " + buffer.toString());
//                    log.info("--------------------");

                timerIdAlarm = vertx.setPeriodic(Long.valueOf("3000"), timerID -> {
                            Alarm alarm = new Alarm();
                            alarm.setStatus("2");
                            SampleItem simpleItem = new SampleItem(alarm).sql(" where  ").notNullFields();
                            simpleItem.sql(" order by area_code,upt_time desc");
                            List<Alarm> alarms = SqlBoxContext.getGlobalSqlBoxContext().eFindAll(Alarm.class, simpleItem);
                            List list = new LinkedList();
                            String[] areaCode = {""};
                            alarms.forEach(alarm1 -> {
                                Map map = new HashMap();
                                List list1 = new LinkedList();
                                alarms.forEach(alarm2 -> {
                                    map.put("areaCode", alarm1.getAreaCode());
                                    if (map.get("areaCode").equals(alarm2.getAreaCode())) {
                                        list1.add(alarm2);
                                    }
                                });
                                if (!areaCode[0].equals(alarm1.getAreaCode())) {
                                    areaCode[0] = alarm1.getAreaCode();
                                    map.put("data", list1);
                                    list.add(map);
                                }

                            });

                            String s = JSON.toJSONString(list);
                            if (StringUtils.isNotEmpty(s)) {
                                try {
                                    serverWebSocket.writeTextMessage(s);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //取消定期计时器
                                    vertx.cancelTimer(timerID);
                                }
                            } else {
                                serverWebSocket.close();
                            }
                        }
                );
//                }
//                );
            }
            else {
                serverWebSocket.reject(); // 拒绝连接
            }

            serverWebSocket.closeHandler(c -> {
                log.info("socket 关闭了");
                //取消定期计时器
                vertx.cancelTimer(timerIdAlarm);
            });

            serverWebSocket.endHandler(end -> {
                log.info("end 操作");
                //取消定期计时器
                vertx.cancelTimer(timerIdAlarm);
            });

            serverWebSocket.exceptionHandler(ex -> {
                ex.printStackTrace();
            });
        }).listen(9997, server -> {
            if (server.succeeded()) {
                log.info("websocket启动成功");
            } else {
                server.cause().printStackTrace();
            }
        });
    }


}
