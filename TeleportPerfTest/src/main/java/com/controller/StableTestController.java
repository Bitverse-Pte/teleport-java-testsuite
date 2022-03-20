package com.controller;

import com.service.StableTestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("teleport")
@RestController
@RequestMapping("/StableTestController")
public class StableTestController {

    @Autowired
    private StableTestService stableTestService;

    @RequestMapping(value = "/send_txs", method = RequestMethod.GET)
    void send_txs(int peerId, int accountIndex, int txsNumber, int interval) throws Exception {
        stableTestService.sendTxs(peerId,accountIndex,txsNumber,interval);
    }


    @RequestMapping(value = "/statisic_result", method = RequestMethod.GET)
    String statisic_result() throws Exception {
        return stableTestService.statisicResult();
    }

}
