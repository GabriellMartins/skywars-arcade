package br.com.skywars.core.server.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerType {

    //BUNGEE
    PROXY(0, "Proxy"),

    //HUBS
    LOGIN(1, "Auth"),
    LOBBY(2, "Hub"),

    //ARCADE
    ARCADE_SOLO(3, "Skywars_solo"),
    ARCADE_DUPLA(4, "Skywars_dupla"),
    ARCADE_TRIO(5, "Skywars_trio");


    Integer id;
    String name;

    public static ServerType valueOf(Integer id) {
        for (ServerType serverType : values()) {
            if (serverType.getId().equals(id)) {
                return serverType;
            }
        }
        return null;
    }
}
