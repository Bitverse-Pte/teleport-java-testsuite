package com.controller;


import com.initial.InitContractService;
import com.pool.AccountsPool;
import com.pool.AccountsStr;
import com.service.ChainService;
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
import java.util.ArrayList;
import java.util.List;

@Api("teleport")
@RestController
@RequestMapping("/InitialController")
public class InitialClusterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitialClusterController.class);

    @Autowired
    private ChainService chainService;


    @RequestMapping(value = "/simple_transfer", method = RequestMethod.GET)
    BigDecimal simpleTransfer() throws Exception {

        String txHash = ColdWallet.sendRawTxWithoutData(AccountsPool.getInstance().getAddressList().get(0),
                100,AccountsPool.getInstance().getValColdwallet(), false);
        if (txHash == null) {
            LOGGER.error("null txHash failed");
            System.exit(0);
        }

        //if we don't get the receipt in 30000s, we will exit the service.
        EthGetTransactionReceipt receipt = chainService.getReceiptByHash(txHash);

        return chainService.getEthBalance(0, AccountsStr.account_val0);
    }

    @RequestMapping(value = "/initial_test_accounts", method = RequestMethod.GET)
    BigDecimal initial_test_accounts() throws Exception {
        for(int i=0;i<7;i++){
            String txHash = ColdWallet.sendRawTxWithoutData(AccountsPool.getInstance().getAddressList().get(i),
                    200,AccountsPool.getInstance().getValColdwallet(), true);
            if (txHash == null) {
                LOGGER.error("null txHash failed");
                System.exit(0);
            }
            //if we don't get the receipt in 30000s, we will exit the service.
            EthGetTransactionReceipt receipt = chainService.getReceiptByHash(txHash);
        }
        return chainService.getEthBalance(0, AccountsStr.account_val0);
    }


    @RequestMapping(value = "/return_balances", method = RequestMethod.GET)
    BigDecimal return_balances() throws Exception {
        for(int i=0;i<7;i++){
            String txHash = ColdWallet.sendRawTxWithoutData(AccountsPool.getInstance().getValColdwallet().getAddress(),
                    199,AccountsPool.getInstance().getColdWalletList().get(i),
                    true);
            if (txHash == null) {
                LOGGER.error("null txHash failed");
                System.exit(0);
            }
            //if we don't get the receipt in 30000s, we will exit the service.
            EthGetTransactionReceipt receipt = chainService.getReceiptByHash(txHash);
        }
        return chainService.getEthBalance(0, AccountsStr.account_val0);
    }


    @RequestMapping(value = "/query_test_accounts", method = RequestMethod.GET)
    List<String> query_test_accounts() throws Exception {
        List<String> balances = new ArrayList<String>();
        for(int i=0;i<7;i++){
            balances.add(chainService.getEthBalance(0,
                    AccountsPool.getInstance().getAddressList().get(i)).toString());
        }
        return balances;
    }

}
