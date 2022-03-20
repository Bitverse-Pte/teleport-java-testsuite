package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R10PersonalService;
import com.service.endpoints.R11WebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R11WebSocketController")
public class R11WebSocketController {

    @Autowired
    private R11WebsocketService r11WebsocketService;

    @RequestMapping(value = "/01_call_eth_getBalance", method = RequestMethod.GET)
    Response call_eth_getBalance()  {
        Response resp = new Response();
        try {
            resp.setResult(r11WebsocketService.eth_getBalance());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/02_call_eth_sendRawTransaction", method = RequestMethod.GET)
    Response eth_sendRawTransaction()  {
        Response resp = new Response();
        try {
            resp.setResult(r11WebsocketService.eth_sendRawTransaction());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/03_call_eth_getTransactionReceipt", method = RequestMethod.GET)
    Response eth_getTransactionReceipt()  {
        Response resp = new Response();
        try {
            resp.setResult(r11WebsocketService.eth_getTransactionReceipt());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/batch_call", method = RequestMethod.GET)
    Response wss_batchSend()  {
        Response resp = new Response();
        resp.setResult(r11WebsocketService.batchCall());
        resp.setSuccessed(true);
        return resp;
    }
}
