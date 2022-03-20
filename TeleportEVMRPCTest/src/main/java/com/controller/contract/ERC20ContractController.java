package com.controller.contract;


import com.pool.AccountsPool;
import com.service.erc20.Erc20TxService;
import com.service.erc20.InitContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ERC20ContractController")
public class ERC20ContractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ERC20ContractController.class);

    @Autowired
    private InitContractService initContractService;

    @Autowired
    private Erc20TxService erc20TxService;


    @RequestMapping(value = "/deploy_contract", method = RequestMethod.GET)
    String deploy(int peerId, int accountId) throws Exception {
        initContractService.deployContract(peerId,accountId);
        LOGGER.info("Deploy Contract Success："+initContractService.erc20Address);
        return initContractService.erc20Address;
    }


    @RequestMapping(value = "/querySenderBalance", method = RequestMethod.GET)
    String queryBalance(int accountId) throws Exception {
        return erc20TxService.queryBalance(AccountsPool.
                getInstance().getAddressList().get(accountId)).toString();
    }


    @RequestMapping(value = "/queryReceiverBalance", method = RequestMethod.GET)
    String queryBalance() throws Exception {
        return erc20TxService.queryBalance(AccountsPool.getInstance().getReceiver()).toString();
    }


    @RequestMapping(value = "/transferValue", method = RequestMethod.GET)
    String transferValue(int peerId, int accountId) throws Exception {
        return erc20TxService.transferValue(peerId,accountId);
    }


    @RequestMapping(value = "/transferBatch", method = RequestMethod.GET)
    void transferBatch(int peerId, int accountId, int batchNumber) throws Exception {
        erc20TxService.transferBatch(peerId,accountId,batchNumber);
    }


    @RequestMapping(value = "/event_filter", method = RequestMethod.GET)
    String tx_simple_filter() throws Exception {
        erc20TxService.contractEventFilter();
        return "success";
    }

}
