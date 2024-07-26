package br.com.skywars.core.command.engine.command.paramter.impl;

import br.com.skywars.core.command.engine.command.paramter.Processor;
import br.com.skywars.core.command.engine.command.utils.CommandReflection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BooleanProcessor extends Processor<Boolean> {

    private final Map<String, Boolean> values = new HashMap<>();

    public BooleanProcessor() {
        // Values that mean true
        values.put("true", true);
        values.put("on", true);
        values.put("yes", true);
        values.put("enable", true);

        // Values that mean false
        values.put("false", false);
        values.put("off", false);
        values.put("no", false);
        values.put("disable", false);
    }

    public Boolean process(Object sender, String supplied) {
        supplied = supplied.toLowerCase();
        if(!values.containsKey(supplied)) {
            CommandReflection.sendMessage(sender, "§cVocê digitou um valor invalido: " + supplied);
            return null;
        }

        return values.get(supplied);
    }

    public List<String> tabComplete(Object sender, String supplied) {
        return values.keySet().stream().filter(s -> s.toLowerCase().startsWith(supplied.toLowerCase())).collect(Collectors.toList());
    }
}
