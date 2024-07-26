package br.com.skywars.core.server.category.relation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerRelation {

    NORMAL("Servidor padrão"),
    ARCADE("Servidor de jogos");

    private final String name;
}
