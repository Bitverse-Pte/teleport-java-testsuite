package com.pool;

import com.utils.ConnClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Copyright (c) 2018 energy-blockchain.com. All Rights Reserved.
 *
 * @author: wenbin  Date: 18-4-13 Time: 上午11:53
 */
public class Web3jConnPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(Web3jConnPool.class);

    private volatile static Web3jConnPool instance = null;

    private ConnClient connClient = new ConnClient();

    public static Web3jConnPool getInstance() {
        return Web3jConnHolder.instance;
    }

    public static void setInstance(Web3jConnPool instance) {
        Web3jConnPool.instance = instance;
    }

    public static Web3j getWeb3jConnection(String url) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        return Web3j.build(new HttpService(url), 100, scheduledExecutorService);
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public ConnClient getConnClient() {
        return connClient;
    }

    public void setConnClient(ConnClient connClient) {
        this.connClient = connClient;
    }

    private static class Web3jConnHolder {
        private static Web3jConnPool instance;

        static {
            try {
                instance = new Web3jConnPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
