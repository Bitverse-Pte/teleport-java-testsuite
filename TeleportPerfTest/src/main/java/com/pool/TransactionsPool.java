package com.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionsPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsPool.class);

    private volatile static TransactionsPool instance = null;

    private String[] datasArray = new String[10000];

    public static TransactionsPool getInstance() {
        return TxsPoolHolder.instance;
    }

    public static void setInstance(TransactionsPool instance) {
        TransactionsPool.instance = instance;
    }

    public String[] getDatasArr() {
        return datasArray;
    }

    public void setDatasArr(String[] datasArr) {
        this.datasArray = datasArr;
    }

    private static class TxsPoolHolder {
        private static TransactionsPool instance;

        static {
            try {
                instance = new TransactionsPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
