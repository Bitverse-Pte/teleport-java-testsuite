package com.service;

import com.google.gson.Gson;
import com.setting.BitostSuiteSetting;
import com.utils.ColdWallet;
import com.pool.Web3jConnPool;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class ChainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChainService.class);
    Gson gson = new Gson();
    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    public EthGetTransactionReceipt getReceiptByHash(String txHash) throws IOException, InterruptedException {

        EthGetTransactionReceipt ethReceipts = Web3jConnPool.getInstance().getConnClient().
                getWeb3jList().get(0).ethGetTransactionReceipt(txHash).send();

        int looptime = 0;
        while (!ethReceipts.getTransactionReceipt().isPresent()) {
            Thread.sleep(6000);
            try {
                ethReceipts = Web3jConnPool.getInstance().getConnClient().
                        getWeb3jList().get(0).ethGetTransactionReceipt(txHash).send();
            } catch (Exception e) {
                LOGGER.info("get receipt meet execption");
                LOGGER.info(e.getMessage());
            }
            looptime++;
            if (looptime > 10) {
                LOGGER.error("Send Tx/Increase Height error");
                LOGGER.error("timeout failed");
                System.exit(0);
            }
        }
        if (!ethReceipts.getResult().getStatus().equals("0x1")) {
            System.out.println("we have meet a vmerr");
            System.exit(0);
        }

        return ethReceipts;
    }

    public String getEthBlockNumber(int peerNubmerId) throws Exception {
        EthBlockNumber ethBlockNumber = Web3jConnPool.getInstance().getConnClient()
                .getWeb3jList()
                .get(peerNubmerId).ethBlockNumber().send();
        return ethBlockNumber.getBlockNumber().toString();
    }

    public String getChainID(int peerNubmerId) throws Exception {
        EthChainId ethChainId = Web3jConnPool.getInstance().getConnClient()
                .getWeb3jList()
                .get(peerNubmerId).ethChainId().send();
        return ethChainId.getChainId().toString();
    }


    public BigDecimal getEthBalance(int peerNubmerId, String address) {
        BigInteger balance = null;
        try {
            EthGetBalance ethGetBalance = Web3jConnPool.getInstance().getConnClient()
                    .getWeb3jList()
                    .get(peerNubmerId).ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            balance = ethGetBalance.getBalance();
            BigDecimal balanceEth = new BigDecimal(balance)
                    .divide(new BigDecimal("1000000000000000000"));
            return balanceEth;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger getGasPrice(int peerNumberId) throws IOException {
        EthGasPrice ethGasPrice = Web3jConnPool.getInstance().getConnClient()
                .getWeb3jList().get(0).ethGasPrice().send();
        return ethGasPrice.getGasPrice();
    }

    public EthBlock getBlockContentById(int peerNumberId, int blockId) {
        EthBlock ethBlock = null;
        try {
            ethBlock = Web3jConnPool.getInstance().getConnClient().getWeb3jList()
                    .get(peerNumberId).ethGetBlockByNumber(
                            new DefaultBlockParameterNumber(blockId)
                            , true).send();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ethBlock;
    }
}
