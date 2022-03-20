package com.controller;

import com.google.gson.Gson;
import com.pool.AccountsPool;
import com.service.ChainService;
import com.initial.InitServerService;
import com.setting.BitostSuiteSetting;
import com.pool.Web3jConnPool;
import com.utils.ColdWallet;
import com.utils.TMClient;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;

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
    private ChainService chainService;

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    @RequestMapping(value = "/getBlockNumber", method = RequestMethod.GET)
    String getBlockNumber() throws Exception {
        return chainService.getEthBlockNumber(0);
    }


    @RequestMapping(value = "/getChainID", method = RequestMethod.GET)
    String getChainID() throws Exception {
        return chainService.getChainID(0);
    }


    @RequestMapping(value = "/getVal0AddressEth", method = RequestMethod.GET)
    BigDecimal getVal0AddressEth() throws Exception {
        String valAddress =  AccountsPool.getInstance().getValColdwallet().getAddress();
        return chainService.getEthBalance(0, valAddress);
    }

    @RequestMapping(value = "/getGasPrice", method = RequestMethod.GET)
    BigInteger getGasPrice(int peerNumberId) throws Exception {
        return chainService.getGasPrice(peerNumberId);
    }

    @RequestMapping(value = "/transferTo", method = RequestMethod.GET)
    BigInteger sendTx(String address) throws Exception {
        String txHash = ColdWallet.sendRawTxWithoutData(address,
                500,AccountsPool.getInstance().getValColdwallet(), false);
        chainService.getReceiptByHash(txHash);
        return chainService.getEthBalance(0,address).toBigInteger();
    }

    @RequestMapping(value = "/getBlockContentById", method = RequestMethod.GET)
    String getBlockContentById(int blockId) throws Exception {
        return gson.toJson(chainService.getBlockContentById(0, blockId));
    }

    @RequestMapping(value = "/getBlockTime", method = RequestMethod.GET)
    BigInteger getBlockTime(int blockId) throws Exception {
        return chainService.getBlockContentById(0, blockId).getBlock().getTimestamp();
    }


    @RequestMapping(value = "/getEthBalanceByAddr", method = RequestMethod.GET)
    BigDecimal getEthBalance(String address) throws Exception {
        return chainService.getEthBalance(0, address);
    }

    @RequestMapping(value = "/getAllTestAccounts", method = RequestMethod.GET)
    List<String > getAllTestAccounts() throws Exception {
        return AccountsPool.getInstance().getAddressList();
    }

    @RequestMapping(value = "/getReceipt", method = RequestMethod.GET)
    String getReceipt(String txHash) throws Exception {
        EthGetTransactionReceipt ethReceipts = chainService.getReceiptByHash(txHash);
        return ethReceipts.getRawResponse();
    }


    @RequestMapping(value = "/getTxCountInBlock", method = RequestMethod.GET)
    String getTxCountInBlock(int blockHeight) throws Exception {
        return TMClient.getTxCountInBlock(bitostSuiteSetting.getTendermint_rpc(),blockHeight);
    }


    @RequestMapping(value = "/getAllConnectPeerHeight", method = RequestMethod.GET)
    List<String> getTxCountInBlock() throws Exception {
        List<String> heights = new ArrayList<>();

        for (int i=0;i<bitostSuiteSetting.getNodesUrl().size();i++){
            heights.add(chainService.getEthBlockNumber(i));
        }
        return heights;
    }

}
