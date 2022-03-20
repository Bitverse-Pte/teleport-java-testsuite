package com.controller;

import com.google.gson.Gson;
import com.pool.AccountsPool;
import com.pool.Web3jConnPool;
import com.setting.BitostSuiteSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chainBrowser")
public class BaseChainBrowser {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseChainBrowser.class);
    private Gson gson = new Gson();

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    @RequestMapping(value = "/getBlockContentById", method = RequestMethod.GET)
    String getBlockContentById(int blockId) throws Exception {
        EthBlock ethBlock = null;
        try {
            ethBlock = Web3jConnPool.getInstance().getConnClient().getWeb3jList()
                    .get(0).ethGetBlockByNumber(
                            new DefaultBlockParameterNumber(blockId)
                            , true).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.toJson(ethBlock);
    }

    @RequestMapping(value = "/getBlockByHash", method = RequestMethod.GET)
    String getBlockContentById(String txHash) throws Exception {
        EthBlock ethBlock = null;
        try {
            ethBlock = Web3jConnPool.getInstance().getConnClient().getWeb3jList()
                    .get(0).ethGetBlockByHash(txHash,false).send();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.toJson(ethBlock);
    }


    @RequestMapping(value = "/getEthBalanceByAddr", method = RequestMethod.GET)
    BigDecimal getEthBalance(String address) throws Exception {
        EthGetBalance ethGetBalance = Web3jConnPool.getInstance().getConnClient()
                .getWeb3jList()
                .get(0).ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigInteger balance = ethGetBalance.getBalance();
        BigDecimal balanceEth = new BigDecimal(balance)
                .divide(new BigDecimal("1000000000000000000"));
        return balanceEth;
    }


    @RequestMapping(value = "/query_test_accounts_balance", method = RequestMethod.GET)
    List<String> query_test_accounts() throws Exception {
        List<String> balances = new ArrayList<String>();
        for(int i=0;i<7;i++){
            balances.add(getEthBalance(AccountsPool.getInstance().getAddressList().get(i)).toString());
        }
        return balances;
    }
}
