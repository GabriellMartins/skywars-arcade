package br.com.skywars.core.command.engine.command.paramter.impl;

import br.com.skywars.core.command.engine.command.paramter.Processor;
import br.com.skywars.core.command.engine.command.utils.CommandReflection;
import org.bukkit.GameMode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class GamemodeProcessor extends Processor<GameMode> {

    public GameMode process(Object sender, String supplied) {
        if(supplied.equalsIgnoreCase("creative") || supplied.equalsIgnoreCase("c") || supplied.equals("1")) {
            return GameMode.CREATIVE;
        }

        if(supplied.equalsIgnoreCase("survival") || supplied.equalsIgnoreCase("s") || supplied.equals("0")) {
            return GameMode.SURVIVAL;
        }

        if(supplied.equalsIgnoreCase("adventure") || supplied.equalsIgnoreCase("a") || supplied.equals("2")) {
            return GameMode.ADVENTURE;
        }

        if(supplied.equalsIgnoreCase("spectator") || supplied.equalsIgnoreCase("sp") || supplied.equals("3")) {
            return GameMode.SPECTATOR;
        }

        CommandReflection.sendMessage(sender, "§cO modo de jogo '" + supplied + "' não é válido.");
        return null;
    }

    public List<String> tabComplete(Object sender, String supplied) {
        return Arrays.stream(GameMode.values())
                .map(GameMode::name)
                .map(String::toLowerCase)
                .filter(name -> name.startsWith(supplied.toLowerCase()))
                .collect(Collectors.toList());
    }
}
