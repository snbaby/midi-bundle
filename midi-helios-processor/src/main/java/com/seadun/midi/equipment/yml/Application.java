package com.seadun.midi.equipment.yml;

import java.util.List;

public class Application {

    public static Server server;

    public static List<String> shardTbs;

    public static List<String> getShardTbs() {
        return shardTbs;
    }

    public void setShardTbs(List<String> shardTbs) {
        this.shardTbs = shardTbs;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        Application.server = server;
    }


}
