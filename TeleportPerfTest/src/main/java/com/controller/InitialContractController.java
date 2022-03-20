package com.controller;


import com.initial.InitContractService;
import com.pool.AccountsPool;
import com.pool.AccountsStr;
import com.service.Erc20TxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/InitialContractController")
public class InitialContractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitialContractController.class);

    @Autowired
    private InitContractService initContractService;

    @Autowired
    private Erc20TxService erc20TxService;


    @RequestMapping(value = "/deploy_contract", method = RequestMethod.GET)
    String deploy(int peerId, int accountId) throws Exception {
        initContractService.deployContract(peerId,accountId);
        LOGGER.info("Deploy Contract Success："+initContractService.erc20Address);
        if (initContractService.erc20Address.equals("")){
            return "deploy failed";
        }
        return initContractService.erc20Address;
    }


    @RequestMapping(value = "/load_contract", method = RequestMethod.GET)
    String load(int peerId, int accountId, String contractAddrss) throws Exception {
        initContractService.loadContract(peerId,accountId,contractAddrss);
        return initContractService.erc20Address;
    }



    @RequestMapping(value = "/querySenderBalance", method = RequestMethod.GET)
    String queryBalance(int accountId) throws Exception {
        return erc20TxService.queryBalance(AccountsPool.
                getInstance().getAddressList().get(accountId)).toString();
    }


    @RequestMapping(value = "/queryVal0Balance", method = RequestMethod.GET)
    String queryVal0Balance() throws Exception {
        return erc20TxService.queryBalance(AccountsStr.account_val0).toString();
    }



    @RequestMapping(value = "/queryReceiverBalance", method = RequestMethod.GET)
    String queryBalance() throws Exception {
        return erc20TxService.queryBalance(AccountsPool.getInstance().getReceiver()).toString();
    }


    @RequestMapping(value = "/transferValue", method = RequestMethod.GET)
    String transferValue(int peerId, int accountId) throws Exception {
        return erc20TxService.transferValue(peerId,accountId);
    }


    @RequestMapping(value = "/transferByHardhatAccount", method = RequestMethod.GET)
    String transferByHardhatAccount(int peerId) throws Exception {
        return erc20TxService.transferByHardhat(peerId);
    }


    @RequestMapping(value = "/transferBatch", method = RequestMethod.GET)
    void transferBatch(int peerId, int accountId, int batchNumber) throws Exception {
        erc20TxService.transferBatch(peerId,accountId,batchNumber);
    }
}
