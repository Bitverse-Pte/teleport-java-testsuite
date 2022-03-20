package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R2NetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R2NetController")
public class R2NetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(R2NetController.class);

    @Autowired
    private R2NetService r2NetService;


    @RequestMapping(value = "/net_version", method = RequestMethod.GET)
    Response web3_clientVersion()  {
        Response resp = new Response();
        try {
            resp.setResult(r2NetService.net_version());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/net_peerCount", method = RequestMethod.GET)
    Response net_peerCount()  {
        Response resp = new Response();
        try {
            resp.setResult(r2NetService.net_peerCount());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/net_listening", method = RequestMethod.GET)
    Response net_listening()  {
        Response resp = new Response();
        try {
            resp.setResult(String.valueOf(r2NetService.net_listening()));
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }
}
