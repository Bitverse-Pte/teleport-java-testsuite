package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R1Web3Service;
import com.setting.BitostSuiteSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R1Web3Controller")
public class R1Web3Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(R1Web3Controller.class);

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    @Autowired
    private R1Web3Service r1Web3Service;

    @RequestMapping(value = "/web3_clientVersion", method = RequestMethod.GET)
    Response web3_clientVersion()  {
        Response resp = new Response();
        try {
            resp.setResult(r1Web3Service.web3_clientVersion());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }


    @RequestMapping(value = "/web3_sha3", method = RequestMethod.GET)
    Response web3_sha3()  {
        Response resp = new Response();
        String expectedData = "0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad";
        try {
            String result = r1Web3Service.web3_sha3();
            resp.setResult(result);
            if (expectedData.equals(expectedData)){
                resp.setSuccessed(true);
            }else{
                resp.setSuccessed(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        resp.setSuccessed(true);
        return resp;
    }
}
