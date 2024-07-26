package br.com.skywars.core.service;

import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

public interface MultiService {


    /* Task Method */
    void sync(Runnable runnable);

    void syncLater(Runnable runnable, long ticks);

    void async(Runnable runnable);

    void asyncLater(Runnable runnable, long ticks);

    /* Get Methods */
    <T> T getPlayer(UUID id, Class<T> tClass);

    <T> T getPlayer(String name, Class<T> tClass);

    /* Message Sender */
    void broadcast(boolean prefix, String message);

    void broadcast(boolean prefix, BaseComponent message);

    void send(UUID id, String... message);

    void send(UUID id, BaseComponent... message);
}
