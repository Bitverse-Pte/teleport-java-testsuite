package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R7MinerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R7MinerController")
public class R7MinerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(R7MinerController.class);

    @Autowired
    private R7MinerService r7MinerService;

    @RequestMapping(value = "/miner_setExtra", method = RequestMethod.GET)
    Response txpool_inspect() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_setExtra();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/miner_getHashrate", method = RequestMethod.GET)
    Response miner_getHashrate() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_getHashrate();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/miner_start", method = RequestMethod.GET)
    Response miner_start() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_start();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/miner_stop", method = RequestMethod.GET)
    Response miner_stop() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_stop();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/miner_setGaslimit", method = RequestMethod.GET)
    Response miner_setGaslimit() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_setGaslimit();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/miner_setGasPrice", method = RequestMethod.GET)
    Response miner_setGasPrice() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_setGasPrice();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/miner_setEtherbase", method = RequestMethod.GET)
    Response miner_setEtherbase() throws Exception {
        Response resp = new Response();
        try {
            String result = r7MinerService.miner_setEtherbase();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

}
