package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R3EthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R3EthController")
public class R3EthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(R3EthController.class);

    @Autowired
    private R3EthService r3EthService;

    @RequestMapping(value = "/01_eth_protocolVersion", method = RequestMethod.GET)
    Response web3_clientVersion()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_protocolVersion());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/02_eth_mining_mock", method = RequestMethod.GET)
    Response eth_mining()  {
        Response resp = new Response();
        try {
            resp.setResult("whether or not this node is currently mining: "+String.valueOf(r3EthService.eth_mining()));
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/03_eth_hashrate_mock", method = RequestMethod.GET)
    Response eth_hashrate()  {
        Response resp = new Response();
        try {
            resp.setResult("the current node's hashrate. Always 0: "+r3EthService.eth_hashrate());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/04_eth_chainId", method = RequestMethod.GET)
    Response eth_chainId()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_chainId());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/05_eth_syncing", method = RequestMethod.GET)
    Response eth_syncing()  {
        Response resp = new Response();
        try {
            resp.setResult("isSyncing: "+ r3EthService.eth_syncing());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/06_eth_coinbase", method = RequestMethod.GET)
    Response eth_coinbase()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_coinbase());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/07_eth_gasPrice", method = RequestMethod.GET)
    Response eth_gasPrice()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_gasPrice());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/08_eth_maxPriorityFeePerGas ", method = RequestMethod.GET)
    Response eth_maxPriorityFeePerGas()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_maxPriorityFeePerGas());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/09_eth_feeHistory ", method = RequestMethod.GET)
    Response eth_feeHistory()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_feeHistory());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/10_eth_accounts", method = RequestMethod.GET)
    Response eth_accounts()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_accounts());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/11_eth_blockNumber", method = RequestMethod.GET)
    Response eth_blockNumber()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_blockNumber());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/12_eth_getBalance", method = RequestMethod.GET)
    Response eth_getBalance()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getBalance());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/13_eth_getStorageAt", method = RequestMethod.GET)
    Response eth_getStorageAt()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getStorageAt());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/14_eth_getTransactionCount", method = RequestMethod.GET)
    Response eth_getTransactionCount()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getTransactionCount());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/15_eth_getBlockTransactionCountByHash", method = RequestMethod.GET)
    Response eth_getBlockTransactionCountByHash()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getBlockTransactionCountByHash());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/16_eth_getBlockTransactionCountByNumber", method = RequestMethod.GET)
    Response eth_getBlockTransactionCountByNumber()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getBlockTransactionCountByNumber());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/17_eth_getCode", method = RequestMethod.GET)
    Response eth_getCode()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getCode());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/18_eth_getTransactionLogs", method = RequestMethod.GET)
    Response eth_getTransactionLogs()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getTransactionLogs());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/19_eth_sendRawTransaction", method = RequestMethod.GET)
    Response eth_sendRawTransaction()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_sendRawTransaction());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/20_eth_resend", method = RequestMethod.GET)
    Response eth_resend()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_resend());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/21_eth_call", method = RequestMethod.GET)
    Response eth_call()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_call());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/22_eth_estimateGas", method = RequestMethod.GET)
    Response eth_getBlockByHash()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_estimateGas());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/23_eth_getBlockByHash", method = RequestMethod.GET)
    Response eth_estimateGas()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getBlockByHash());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/24_eth_getBlockByNumber", method = RequestMethod.GET)
    Response eth_getBlockByNumber()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getBlockByNumber());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/25_eth_getTransactionByHash", method = RequestMethod.GET)
    Response eth_getTransactionByHash()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getTransactionByHash());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/26_eth_getTransactionByBlockHashAndIndex", method = RequestMethod.GET)
    Response eth_getTransactionByBlockHashAndIndex()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getTransactionByBlockHashAndIndex());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/27_eth_getTransactionByBlockNumberAndIndex", method = RequestMethod.GET)
    Response eth_getTransactionByBlockNumberAndIndex()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getTransactionByBlockNumberAndIndex());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/28_eth_getTransactionReceipt", method = RequestMethod.GET)
    Response eth_getTransactionReceipt()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getTransactionReceipt());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/29_eth_getPendingTransactions", method = RequestMethod.GET)
    Response eth_getPendingTransactions()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getPendingTransactions());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/30_eth_getProof", method = RequestMethod.GET)
    Response eth_getProof()  {
        Response resp = new Response();
        try {
            resp.setResult(r3EthService.eth_getProof());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



}
