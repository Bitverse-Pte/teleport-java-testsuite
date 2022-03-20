package com.pool;

import com.setting.BitostSuiteSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;

public class WebSocketPool {

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketPool.class);

    private volatile static WebSocketPool instance = null;

    private WebSocketService wss ;

    public static WebSocketPool getInstance() {
        return WebSocketPool.WebSocketPoolHolder.instance;
    }

    public static void setInstance(WebSocketPool instance) {
        WebSocketPool.instance = instance;
    }

    public WebSocketService getWss() {
        try {
            wss.connect();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        return wss;
    }

    public void setWss(WebSocketService wss) {
        this.wss = wss;
    }

    public void initialWSS(String url) throws ConnectException {
        this.wss = new WebSocketService(url,true);
    }

    public void connect() throws ConnectException {
        this.wss.connect();
    }

    private static class WebSocketPoolHolder {
        private static WebSocketPool instance;

        static {
            try {
                instance = new WebSocketPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
