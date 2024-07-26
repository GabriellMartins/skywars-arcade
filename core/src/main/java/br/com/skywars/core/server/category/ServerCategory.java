package br.com.skywars.core.server.category;

import br.com.skywars.core.server.category.relation.ServerRelation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ServerCategory {

    PROXY("Proxy", ServerRelation.NORMAL),
    AUTH("Auth", ServerRelation.NORMAL),
    LOBBY("Hub", ServerRelation.ARCADE),

    /* Arcade Servers */
    DUELS("Duels", ServerRelation.ARCADE),
    PVP("PvP", ServerRelation.ARCADE),

    /* HardcoreGames Servers */
    HG("HardcoreGames", "hg", ServerRelation.NORMAL);

    private final String name, id;
    private final ServerRelation relation;

    ServerCategory(String name, ServerRelation relation) {
        this(name, name.toLowerCase(), relation);
    }

    public static ServerCategory read(String name) {
        return Arrays.stream(values()).filter(server -> server.name().equalsIgnoreCase(name) || server.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public boolean isRelation(ServerRelation relation) {
        return this.relation.equals(relation);
    }
}
