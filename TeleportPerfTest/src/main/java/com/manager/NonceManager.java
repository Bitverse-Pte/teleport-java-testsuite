package com.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class NonceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(NonceManager.class);

    private volatile static NonceManager instance = null;

    private BigInteger nonce;

    public static NonceManager getInstance() {
        return NonceManagerHolder.instance;
    }

    public static void setInstance(NonceManager instance) {
        NonceManager.instance = instance;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    private static class NonceManagerHolder {
        private static NonceManager instance;

        static {
            try {
                instance = new NonceManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
