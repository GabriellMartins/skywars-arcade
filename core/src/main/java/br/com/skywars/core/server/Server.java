package br.com.skywars.core.server;


import br.com.skywars.core.Core;
import br.com.skywars.core.server.category.ServerCategory;
import br.com.skywars.core.server.category.relation.ServerRelation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class Server {

    private final int id;
    private final ServerCategory category;

    private final Map<String, Object> properties = new HashMap<>();

    private int onlinePlayers;

    private final int port, maxPlayers;
    private final long startedAt = System.currentTimeMillis();

    private long heartBeat = System.currentTimeMillis();

    public boolean isAlive() {
        return heartBeat + 3000L > System.currentTimeMillis();
    }

    public boolean isArcade() {
        return category.isRelation(ServerRelation.ARCADE);
    }



    public String getName() {
        return category.getName();
    }

    public boolean isCategory(ServerCategory category) {
        return this.category.equals(category);
    }

    public void writeProperty(String key, Object value) {
        properties.put(key.toLowerCase(), value);
    }

    public Object getProperty(String key) {
        return properties.get(key.toLowerCase());
    }

    public void removeProperty(String key) {
        properties.remove(key.toLowerCase());
    }
}
