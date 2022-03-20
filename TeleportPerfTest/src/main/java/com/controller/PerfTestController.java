package com.controller;

import com.initial.InitContractService;
import com.pool.AccountsPool;
import com.pool.AccountsStr;
import com.service.PerfTestService;
import com.utils.ColdWallet;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;

import java.math.BigDecimal;

@Api("teleport")
@RestController
@RequestMapping("/PerfTestController")
public class PerfTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerfTestController.class);

    @Autowired
    private PerfTestService perfTestService;

    @RequestMapping(value = "/pre_construct_data", method = RequestMethod.GET)
    void preConstructData(int peerId, int accountId, int txsNumber) throws Exception {
        Boolean result = perfTestService.initialNonce(peerId,accountId);
        if (!result){
            System.exit(0);
        }
        perfTestService.preConstruct(peerId,accountId, txsNumber);
    }


    @RequestMapping(value = "/send_txs", method = RequestMethod.GET)
    void send_txs(int peerId, int accountIndex, int txsNumber, int interval) throws Exception {
        perfTestService.sendTxs(peerId,accountIndex,txsNumber,interval);
    }

    @RequestMapping(value = "/statisic_result", method = RequestMethod.GET)
    String statisic_result() throws Exception {
        return perfTestService.statisicResult();
    }

}
