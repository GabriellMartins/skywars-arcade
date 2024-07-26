package br.com.skywars.core.command.engine.command.help;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public final class HelpNode {

    private final Object parentClass;
    private final String[] names;
    private final String permission;
    private final Method method;
}
