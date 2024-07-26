package br.com.skywars.core.backend.redis;

import br.com.skywars.core.Core;
import br.com.skywars.core.account.Account;
import br.com.skywars.core.backend.Backend;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Redis implements Backend {

    private final String hostname;
    private final String password;
    private final int port;

    private JedisPool pool;

    @Override
    public void connect() throws Exception {
        if (!this.password.isEmpty())
            pool = new JedisPool(new JedisPoolConfig(), this.hostname, this.port, 0,
                    this.password);
        else {
            pool = new JedisPool(new JedisPoolConfig(), this.hostname, this.port, 0);
        }
    }


    @Override
    public void disconnect() throws Exception {
        if (this.pool != null) {
            this.pool.close();
        }
    }

    @Override
    public boolean isConnected() {
        return this.pool != null && !this.pool.isClosed();
    }


    public class PubSubTask implements Runnable {

        private JedisPubSub jpsh;
        private final String[] channels;

        public PubSubTask(JedisPubSub s, String... channels) {
            this.jpsh = s;
            this.channels = channels;
        }

        @Override
        public void run() {
            boolean broken = false;
            try (Jedis rsc = pool.getResource()) {
                try {
                    rsc.subscribe(jpsh, channels);
                } catch (Throwable e) {
                    e.printStackTrace();
                    try {
                        jpsh.unsubscribe();
                    } catch (Throwable e1) {}
                    broken = true;
                }
            }
            if (broken) {
                run();
            }
        }

        public void addChannel(String... channel) {
            jpsh.subscribe(channel);
        }

        public void removeChannel(String... channel) {
            jpsh.unsubscribe(channel);
        }

        public void poison() {
            jpsh.unsubscribe();
        }
    }
}
