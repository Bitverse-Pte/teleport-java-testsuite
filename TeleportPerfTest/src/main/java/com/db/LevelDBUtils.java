package com.db;

import com.pool.DBConnPool;
import org.iq80.leveldb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;

@Service
public class LevelDBUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(LevelDBUtils.class);

  public void writeData(String key, String value) throws IOException {
    Options options = new Options();
    options.createIfMissing(true);
    DB db = factory.open(new File("txs-pool"), options);
    try {
      db.put(bytes("Tampa"), bytes("rocks"));
    } finally {
      db.close();
    }
  }

  public void writeBatchData(String prefix, int groupHeadIndex, String[] txs) throws IOException {
    WriteBatch batch = DBConnPool.getInstance().getDb().createWriteBatch();
    int length = txs.length;
    try {
      for (int i = 0; i < length; i++) {
        int seq = groupHeadIndex+i;
        batch.put(bytes(prefix + Integer.toString(seq)), bytes(txs[i]));
      }
      DBConnPool.getInstance().getDb().write(batch);
    } finally {
      batch.close();
    }
  }

  public String readData() throws IOException {
    Options options = new Options();
    options.createIfMissing(true);
    DB db = factory.open(new File("txs-pool"), options);
    String value;

    try {
      value = asString(db.get(bytes("Tampa")));
    } finally {
      db.close();
    }
    return value;
  }

  public String[] readBatchData(String prefix, int groupHeadIndex, int txsNumber) throws IOException {
    Options options = new Options();
    options.createIfMissing(true);
    DB db = factory.open(new File("txs-pool"), options);

    String txs[] = new String[txsNumber];
    for (int i = 0; i < txsNumber; i++) {
      int seq = groupHeadIndex+i;
      String hexValue = asString(db.get(bytes(prefix + Integer.toString(seq))));
      txs[i] = hexValue;
    }

    //close db when finished
    db.close();

    LOGGER.info("we have closed the db");

    return txs;
  }


  public String readTxData(String prefix, int txSeq) throws IOException {
    String hexValue = "";
    hexValue = asString(DBConnPool.getInstance().getDb()
                .get(bytes(prefix + Integer.toString(txSeq))));
    return hexValue;
  }
}
