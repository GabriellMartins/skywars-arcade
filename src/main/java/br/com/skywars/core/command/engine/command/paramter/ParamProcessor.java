package br.com.skywars.core.command.engine.command.paramter;

import br.com.skywars.core.command.engine.command.duration.Duration;
import br.com.skywars.core.command.engine.command.node.ArgumentNode;
import br.com.skywars.core.command.engine.command.paramter.impl.*;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public final class ParamProcessor {

    @Getter private static final HashMap<Class<?>, Processor<?>> processors = new HashMap<>();
    private static boolean loaded = false;

    private final ArgumentNode node;
    private final String supplied;
    private final Object sender;

    /**
     * Processes the param into an object
     * @return Processed Object
     */
    public Object get() {
        if(!loaded) loadProcessors();

        Processor<?> processor = processors.get(node.getParameter().getType());
        if(processor == null) return supplied;

        return processor.process(sender, supplied);
    }

    /**
     * Gets the tab completions for the param processor
     * @return Tab Completions
     */
    public List<String> getTabComplete() {
        if(!loaded) loadProcessors();

        Processor<?> processor = processors.get(node.getParameter().getType());
        if(processor == null) return new ArrayList<>();

        return processor.tabComplete(sender, supplied);
    }

    /**
     * Creates a new processor
     * @param processor Processor
     */
    public static void createProcessor(Processor<?> processor) {
        processors.put(processor.getType(), processor);
    }

    /**
     * Loads the processors
     */
    public static void loadProcessors() {
        loaded = true;

        processors.put(int.class, new IntegerProcessor());
        processors.put(long.class, new LongProcessor());
        processors.put(double.class, new DoubleProcessor());
        processors.put(boolean.class, new BooleanProcessor());
        processors.put(Duration.class, new DurationProcessor());

        try {
            processors.put(org.bukkit.ChatColor.class, new ChatColorProcessor());
            processors.put(org.bukkit.entity.Player.class, new PlayerProcessor());
            processors.put(org.bukkit.OfflinePlayer.class, new OfflinePlayerProcessor());
            processors.put(org.bukkit.World.class, new WorldProcessor());
            processors.put(org.bukkit.GameMode.class, new GamemodeProcessor());
        } catch (Exception ignoredNoClassFound) {

        }
    }
}
