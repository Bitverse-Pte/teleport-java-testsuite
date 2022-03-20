package com.pool;

import com.contract.erc20.BitosErc20test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployedErc20Pool {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployedErc20Pool.class);

    private BitosErc20test erc20test;

    private volatile static DeployedErc20Pool instance = null;

    public static DeployedErc20Pool getInstance() {
        return Erc20Holder.instance;
    }

    public static void setInstance(DeployedErc20Pool instance) {
        DeployedErc20Pool.instance = instance;
    }

    public BitosErc20test getErc20test() {
        return erc20test;
    }

    public void setErc20test(BitosErc20test erc20test) {
        this.erc20test = erc20test;
    }

    private static class Erc20Holder {
        private static DeployedErc20Pool instance;

        static {
            try {
                instance = new DeployedErc20Pool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
