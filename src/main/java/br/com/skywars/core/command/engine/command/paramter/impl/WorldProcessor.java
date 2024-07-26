package br.com.skywars.core.command.engine.command.paramter.impl;

import br.com.skywars.core.command.engine.command.paramter.Processor;
import br.com.skywars.core.command.engine.command.utils.CommandReflection;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
import java.util.stream.Collectors;

public final class WorldProcessor extends Processor<World> {

    @Override
    public World process(Object sender, String supplied) {
        World world = Bukkit.getWorld(supplied);

        if(world == null) {
            CommandReflection.sendMessage(sender, "§cNão foi encontrado o mundo '" + supplied + "'.");
            return null;
        }

        return world;
    }

    public List<String> tabComplete(Object sender, String supplied) {
        return Bukkit.getWorlds().stream().map(World::getName)
                .filter(name -> name.toLowerCase().startsWith(supplied.toLowerCase()))
                .collect(Collectors.toList());
    }
}
