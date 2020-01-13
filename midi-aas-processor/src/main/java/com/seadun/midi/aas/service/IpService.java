package com.seadun.midi.aas.service;


import com.seadun.midi.aas.annotation.TX;
import com.seadun.midi.aas.exception.MidiException;
import com.seadun.midi.aas.util.RunShellUtil;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class IpService {
    @TX
    public void updateIp(RoutingContext routingContext) throws MidiException {
        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        String ip = bodyAsJson.getString("ip");
        String name = routingContext.pathParam("name");
        String gateway = bodyAsJson.getString("gateway");
        String subnetMask = bodyAsJson.getString("subnetMask");
        log.info("sh " + System.getProperty("file.separator") + "opt" + System.getProperty("file.separator") + "seadun" + System.getProperty("file.separator") + "shell" + System.getProperty("file.separator")
                + "network.sh " + " -a " + ip + " -i " + name + " -n " + subnetMask + " -g " + gateway);
        RunShellUtil.runScript("sh " + System.getProperty("file.separator") + "opt" + System.getProperty("file.separator") + "seadun" + System.getProperty("file.separator") + "shell" + System.getProperty("file.separator")
                + "network.sh " + " -a " + ip + " -i " + name + " -n " + subnetMask + " -g " + gateway);
//        log.info("sh " + System.getProperty("file.separator") + "opt" + System.getProperty("file.separator") + "seadun" + System.getProperty("file.separator") + "shell" + System.getProperty("file.separator")
//                + "network.sh " + name + " " + ip + " " + subnetMask + " " + gateway);
//        RunShellUtil.runScript("sh " + System.getProperty("file.separator") + "opt" + System.getProperty("file.separator") + "seadun" + System.getProperty("file.separator") + "shell" + System.getProperty("file.separator")
//                + "network.sh " + name + " " + ip + " " + subnetMask + " " + gateway);
    }

//    public static void main(String[] args) {
//        List<Map<String, String>> list = new ArrayList<>();
//        try {
//            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
//                 nis != null && nis.hasMoreElements(); ) {
//                NetworkInterface iface = nis.nextElement();
//                for (InterfaceAddress ifAddr : iface.getInterfaceAddresses()) {
////                    if (ifAddr.getAddress().isSiteLocalAddress()) {
//                        String pattern = "(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})(\\.(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})){3}";
//                        Pattern r = Pattern.compile(pattern);
//                        Matcher m = r.matcher(ifAddr.getAddress().getHostAddress());
//                        Map<String, String> ipMaps = new HashMap<String, String>();
//                        if (m.matches()) {
//                            log.info("网络适配器name：" + iface.getDisplayName());
//                            log.info("getName：" + iface.getName());
//                            log.info("IP：" + ifAddr.getAddress().getHostAddress());
//                            log.info("Mask:" + getMask(ifAddr.getNetworkPrefixLength()));
//                            ipMaps.put("name", iface.getDisplayName());
//                            ipMaps.put("ip", ifAddr.getAddress().getHostAddress());
//                            ipMaps.put("subnetMask", getMask(ifAddr.getNetworkPrefixLength()));
//                            if (ifAddr.getBroadcast() != null) {
//                                log.info("broad:" + ifAddr.getBroadcast().getHostAddress());
//                                ipMaps.put("gateway", ifAddr.getBroadcast().getHostAddress());
//                            }
//                            list.add(ipMaps);
//                        }
////                    }
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @TX
    public List<Map<String, String>> getLocalIp() {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
                 nis != null && nis.hasMoreElements(); ) {
                NetworkInterface iface = nis.nextElement();
                for (InterfaceAddress ifAddr : iface.getInterfaceAddresses()) {
//                    if (ifAddr.getAddress().isSiteLocalAddress()) {
                        String pattern = "(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})(\\.(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})){3}";
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(ifAddr.getAddress().getHostAddress());
                        Map<String, String> ipMaps = new HashMap<String, String>();
                        if (m.matches()) {
                            log.info("网络适配器name：" + iface.getDisplayName());
                            log.info("getName：" + iface.getName());
                            log.info("IP：" + ifAddr.getAddress().getHostAddress());
                            log.info("Mask:" + getMask(ifAddr.getNetworkPrefixLength()));
                            ipMaps.put("name", iface.getDisplayName());
                            ipMaps.put("ip", ifAddr.getAddress().getHostAddress());
                            ipMaps.put("subnetMask", getMask(ifAddr.getNetworkPrefixLength()));
                            if (ifAddr.getBroadcast() != null) {
                                log.info("broad:" + ifAddr.getBroadcast().getHostAddress());
                                ipMaps.put("gateway", ifAddr.getBroadcast().getHostAddress());
                            }
                            if("127.0.0.1".equalsIgnoreCase(ipMaps.get("ip"))){
                                continue;
                            }
                            list.add(ipMaps);
                        }
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 返回指定长度的掩码的字符串，如，长度为8的掩码，返回255.0.0.0；长度为24的掩码，返回255.255.255.0<br/>
     * 注：只适用于IPV4
     *
     * @param maskLength 掩码长度
     * @return
     */
    private static String getMask(int maskLength) {

        StringBuffer maskStr = new StringBuffer();
        int mask = 0xFFFFFFFF << 32 - maskLength;
        for (int i = 3; i >= 0; i--) {
            maskStr.append((mask >> (8 * i)) & 0xFF);
            if (i > 0) {
                maskStr.append(".");
            }
        }
        return maskStr.toString();
    }

}
