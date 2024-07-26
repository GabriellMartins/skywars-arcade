package br.com.skywars.core.backend.redis;

import br.com.skywars.core.Constants;
import br.com.skywars.core.backend.Backend;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Redis implements Backend {


    @NonNull
    private final String hostname, password;
    private final int port;

    @Getter
    private JedisPool pool;


    public Redis() {
        this(Constants.REDIS_DATABASE, Constants.REDIS_PASSWORD , Integer.parseInt(Constants.REDIS_PORT));

    }

    @Override
    public void connect() throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);
        if (!password.isEmpty()) {
            pool = new JedisPool(config, hostname, port, 0, password);
        } else {
            pool = new JedisPool(config, hostname, port, 0);
        }

    }

    @Override
    public void disconnect() throws Exception {
    if (pool != null ) {
        pool.destroy();
        }
    }


    @Override
    public boolean isConnected() throws Exception {
        return !pool.isClosed();
    }
}
