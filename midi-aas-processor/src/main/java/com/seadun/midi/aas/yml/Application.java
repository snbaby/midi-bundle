package com.seadun.midi.aas.yml;

import java.util.List;

public class Application {
    public static Init init;

    public static Server server;

    public static List<String> shardTbs;

    public List<String> getShardTbs() {
        return shardTbs;
    }

    public void setShardTbs(List<String> shardTbs) {
        Application.shardTbs = shardTbs;
    }

    public Init getInit() {
        return init;
    }

    public void setInit(Init init) {
        Application.init = init;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        Application.server = server;
    }

}
