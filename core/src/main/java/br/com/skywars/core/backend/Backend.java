package br.com.skywars.core.backend;

public interface Backend {

    void connect() throws Exception;
    void disconnect() throws Exception;
    boolean isConnected() throws Exception;
}
