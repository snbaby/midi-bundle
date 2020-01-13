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
public class WebSocketCabinetVerticle extends AbstractVerticle {

    private Map<String, ServerWebSocket> localMap;
    static long timerIdCabinet;

    @Override
    public void start() {
        localMap = new HashMap<>();
        vertx.createHttpServer().websocketHandler(serverWebSocket -> {
            String path = serverWebSocket.path();
            if ("/ws/cabinet".equals(path)) {
                serverWebSocket.handler(area_code -> {
                            String id = serverWebSocket.textHandlerID();
                            localMap.put(id, serverWebSocket);
                            timerIdCabinet = vertx.setPeriodic(Long.valueOf("3000"), timerID -> {
                                        Cabinet cabinet = new Cabinet();
                                        SampleItem simpleItem = new SampleItem(cabinet).sql(" where ").notNullFields();
                                        simpleItem.sql(" and area_code = '" + area_code + "'");
                                        List<Cabinet> alarms = SqlBoxContext.getGlobalSqlBoxContext().eFindAll(Cabinet.class, simpleItem);
                                        String s = JSON.toJSONString(alarms);
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
                        }
                );
            } else {
                serverWebSocket.reject(); // 拒绝连接
            }

            serverWebSocket.closeHandler(c -> {
                log.info("socket 关闭了");
                //取消定期计时器
                vertx.cancelTimer(timerIdCabinet);
            });

            serverWebSocket.endHandler(end -> {
                log.info("end 操作");
                //取消定期计时器
                vertx.cancelTimer(timerIdCabinet);
            });

            serverWebSocket.exceptionHandler(ex -> {
                ex.printStackTrace();
            });
        }).listen(9998, server -> {
            if (server.succeeded()) {
                log.info("websocket启动成功");
            } else {
                server.cause().printStackTrace();
            }
        });
    }


}
