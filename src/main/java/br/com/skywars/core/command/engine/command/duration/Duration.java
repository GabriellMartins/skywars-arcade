package br.com.skywars.core.command.engine.command.duration;

import lombok.Data;

@Data
public final class Duration {

    private final String parsed;
    private final long time;
}
