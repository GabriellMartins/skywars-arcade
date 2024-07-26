package br.com.skywars.core.server;

import br.com.skywars.core.Core;
import br.com.skywars.core.server.status.ServerStatus;
import br.com.skywars.core.server.type.ServerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInfo {

    private String displayName;
    private String name;

    private ServerType type;
    private ServerStatus status;

    private String address;
    private Integer port;

    private Integer players;
    private Integer maxPlayers;

    public ServerInfo(String displayName, String name, ServerType type, String address, Integer port) {
        this.displayName = displayName;
        this.name = name;
        this.port = port;
        this.address = address;
        this.type = type;

        players = 0;
        maxPlayers = 20;
    }
    public void create() {
    }
}
