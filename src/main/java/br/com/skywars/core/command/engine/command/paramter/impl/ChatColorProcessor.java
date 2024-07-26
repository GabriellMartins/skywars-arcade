package br.com.skywars.core.command.engine.command.paramter.impl;

import br.com.skywars.core.command.engine.command.paramter.Processor;
import br.com.skywars.core.command.engine.command.utils.CommandReflection;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ChatColorProcessor extends Processor<ChatColor> {

    public ChatColor process(Object sender, String supplied) {
        try { return ChatColor.valueOf(supplied.toUpperCase()); }
        catch(Exception exception) {
            CommandReflection.sendMessage(sender, "§cA cor '" + supplied + "' não existe.");
            return null;
        }
    }

    public List<String> tabComplete(Object sender, String supplied) {
        return Arrays.stream(ChatColor.values())
                .map(ChatColor::name)
                .filter(name -> name.toLowerCase().startsWith(supplied.toLowerCase()))
                .collect(Collectors.toList());
    }
}
