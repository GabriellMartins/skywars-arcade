package br.com.skywars.core.command.engine.command.utils;

import br.com.skywars.core.command.resolver.method.MethodResolver;

public final class CommandReflection {

    public static void sendMessage(Object sender, String msg) {
        try {
            new MethodResolver(sender.getClass(), "sendMessage", String.class).resolve()
                    .invoke(sender, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPermission(Object sender, String name) {
        try {
           return (boolean) new MethodResolver(sender.getClass(), "hasPermission", String.class).resolve()
                    .invoke(sender, name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPlayer(Object sender) {
        return sender.getClass().getSimpleName().equals("CraftPlayer") ||
                sender.getClass().getSimpleName().equals("UserConnection");
    }
}
