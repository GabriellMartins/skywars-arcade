package br.com.skywars.core.command.engine.command;

import br.com.skywars.core.command.engine.command.help.Help;
import br.com.skywars.core.command.engine.command.help.HelpNode;
import br.com.skywars.core.command.engine.command.node.CommandNode;
import br.com.skywars.core.command.engine.command.paramter.ParamProcessor;
import br.com.skywars.core.command.engine.command.paramter.Processor;
import br.com.skywars.core.command.resolver.ClassGetter;
import br.com.skywars.core.command.resolver.method.MethodResolver;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;

public final class CommandHandler {

    @Getter private static Object plugin;
    @Getter private static String pluginName;

    @SneakyThrows
    public static void setPlugin(Object plugin) {
        CommandHandler.plugin = plugin;
        CommandHandler.pluginName = (String) new MethodResolver(plugin.getClass(), "getName").resolve()
                .invoke(plugin);
    }

    /**
     * Registers commands based off a file path
     * @param path Path
     */
    @SneakyThrows
    public static void registerCommands(String path, Object plugin) {
        ClassGetter.getClassesForPackageByPlugin(plugin, path).stream()
                .forEach(clazz -> registerCommands(clazz, plugin));
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    @SneakyThrows
    public static void registerCommands(Class<?> commandClass, Object plugin) {
        CommandHandler.setPlugin(plugin);
        registerCommands(commandClass.newInstance());
    }

    /**
     * Registers the commands in the class
     * @param commandClass Class
     */
    public static void registerCommands(Object commandClass) {
        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Command command = method.getAnnotation(Command.class);
            if(command == null) return;

            new CommandNode(commandClass, method, command);
        });

        Arrays.stream(commandClass.getClass().getDeclaredMethods()).forEach(method -> {
            Help help = method.getAnnotation(Help.class);
            if(help == null) return;

            HelpNode helpNode = new HelpNode(commandClass, help.names(), help.permission(), method);
            CommandNode.getNodes().forEach(node -> node.getNames().forEach(name -> Arrays.stream(help.names())
                    .map(String::toLowerCase)
                    .filter(helpName -> name.toLowerCase().startsWith(helpName))
                    .forEach(helpName -> node.getHelpNodes().add(helpNode))));
        });
    }

    /**
     * Registers processors based off a file path
     * @param path Path
     */
    @SneakyThrows
    public static void registerProcessors(String path, Object plugin) {
        ClassGetter.getClassesForPackageByPlugin(plugin, path).stream()
                .filter(clazz -> clazz.getSuperclass().equals(Processor.class))
                .forEach(clazz -> {
                    try { ParamProcessor.createProcessor((Processor<?>) clazz.newInstance());
                    } catch(Exception exception) { exception.printStackTrace(); }
                });
    }

    public static void executeAsync(Runnable command) {
        org.bukkit.Bukkit.getScheduler()
                .runTaskAsynchronously((org.bukkit.plugin.Plugin) plugin, command);
    }
}
