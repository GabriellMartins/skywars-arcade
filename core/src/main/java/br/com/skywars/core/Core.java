package br.com.skywars.core;

import br.com.skywars.core.backend.redis.Redis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;

public class Core {

    @Getter
    @Setter
    private static Redis redis;

    @Getter
    private static final Gson gson = new Gson();

    @Getter
    private static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    @Getter
    private static final JsonParser parser = new JsonParser();


}
