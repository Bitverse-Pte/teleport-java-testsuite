package com.pool;

import com.contract.erc20.BitosErc20test;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

public class DBConnPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnPool.class);

    private volatile static DBConnPool instance = null;

    private static DB db;

    public static DBConnPool getInstance() {
        return DBCoonPoolHolder.instance;
    }

    public static void setInstance(DBConnPool instance) {
        DBConnPool.instance = instance;
    }

    public static DB getDb() {
        return db;
    }

    public static void setDb(DB db) {
        DBConnPool.db = db;
    }

    private static class DBCoonPoolHolder {
        private static DBConnPool instance;

        static {
            try {
                instance = new DBConnPool();
                Options options = new Options();
                options.createIfMissing(true);
                instance.db = factory.open(new File("txs-pool"), options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
