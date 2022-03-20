package com.service.endpoints;

import com.controller.endpoints.R3EthController;
import com.google.gson.Gson;
import com.pool.AccountsPool;
import com.pool.WebSocketPool;
import com.utils.ColdWallet;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.BatchRequest;
import org.web3j.protocol.core.BatchResponse;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.protocol.core.Request;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class R11WebsocketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(R11WebsocketService.class);

    private Gson gson = new Gson();

    public String eth_getBalance() throws IOException {
        List<String> params = new ArrayList<String>();
        params.add("0x62b59d0910d4dcd905cfcb268d8f35955f2e2acb");
        params.add("0x0");

        EthGetBalance resp = new Request<>("eth_getBalance", params,
                WebSocketPool.getInstance().getWss(), EthGetBalance.class).send();

        LOGGER.info("websocket eth_getbalance");

        return resp.getBalance().toString();
    }


    public String eth_sendRawTransaction() throws IOException {
        String txData = ColdWallet.constructTxData(0, AccountsPool.getInstance().getAddressList().get(0),
                100,AccountsPool.getInstance().getValColdwallet(), true,"");

        List<String> params = new ArrayList<String>();
        params.add(txData);

        EthSendRawTransaction resp = new Request<>("eth_sendRawTransaction", params,
                WebSocketPool.getInstance().getWss(), EthSendRawTransaction.class).send();

        LOGGER.info("websocket eth_sendRawTransaction");

        return resp.getTransactionHash();
    }

    public String eth_getTransactionReceipt() throws IOException {
        String existedTxHash = "0x6720d544e983a337da608fe39d37a28f89d83d25270672fe8bdfc9a263ef7fde";
        List<String> params = new ArrayList<String>();
        params.add(existedTxHash);

        EthGetTransactionReceipt resp = new Request<>("eth_getTransactionReceipt", params,
                WebSocketPool.getInstance().getWss(), EthGetTransactionReceipt.class).send();

        LOGGER.info("websocket eth_getTransactionReceipt");
        return gson.toJson(resp.getTransactionReceipt().get());
    }

    public String batchCall()  {
        System.out.println("send batch request");

        List<String> params1 = new ArrayList<String>();
        params1.add("0x62b59d0910d4dcd905cfcb268d8f35955f2e2acb");
        params1.add("0x0");

        List<String> params2 = new ArrayList<String>();
        params2.add("0xcc0aa3021d70885084b1902e20f6aee2f1ed5d9c");
        params2.add("0x0");

        Request<String, EthGetBalance> req1 =
            new Request<>(
                "eth_getBalance", params1, WebSocketPool.getInstance().getWss(), EthGetBalance.class);
        Request<String, EthGetBalance> req2 =
            new Request<>(
                "eth_getBalance", params2, WebSocketPool.getInstance().getWss(), EthGetBalance.class);

        BatchRequest batchRequest = new BatchRequest(WebSocketPool.getInstance().getWss());
        batchRequest = batchRequest.add(req1);
        batchRequest = batchRequest.add(req2);

        BatchResponse batchResponse = null;
        try {
            batchResponse = WebSocketPool.getInstance().getWss().sendBatch(batchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("websocket batchcall eth_balance failure!");
            return "IOException occured";
        }

        EthGetBalance balance1 = (EthGetBalance) batchResponse.getResponses().get(0);
        EthGetBalance balance2 = (EthGetBalance) batchResponse.getResponses().get(1);
        System.out.println(balance1.getBalance().toString());
        System.out.println(balance2.getBalance().toString());

        LOGGER.info("websocket batchcall eth_balance success!");
        return "failure";
    }
}
