package com.controller;


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

@Api("teleport")
//@RestController
//@RequestMapping("/SingleNodeController")
public class SingleNodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleNodeController.class);

    @Autowired
    private ChainService chainService;

    @RequestMapping(value = "/send_tx", method = RequestMethod.GET)
    BigDecimal preConstructData() throws Exception {
        String txHash = ColdWallet.sendRawTxWithoutData(AccountsPool.getInstance().getAddressList().get(0),
                10,ColdWallet.loadSingleNodeColdwallet(), false);
        if (txHash == null) {
            LOGGER.error("null txHash failed");
            System.exit(0);
        }
        //if we don't get the receipt in 30000s, we will exit the service.
        EthGetTransactionReceipt receipt = chainService.getReceiptByHash(txHash);
        return chainService.getEthBalance(0, AccountsStr.account_single);
    }


    @RequestMapping(value = "/query_value", method = RequestMethod.GET)
    BigDecimal queryValue() throws Exception {
        return chainService.getEthBalance(0, AccountsPool.getInstance().getAddressList().get(0));
    }


    @RequestMapping(value = "/query_single_val_value", method = RequestMethod.GET)
    BigDecimal query_single_val_value() throws Exception {
        return chainService.getEthBalance(0, AccountsStr.account_single);
    }

}
