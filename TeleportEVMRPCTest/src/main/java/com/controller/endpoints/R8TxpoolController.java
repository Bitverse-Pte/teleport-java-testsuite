package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R8TxpoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R8TxpoolController")
public class R8TxpoolController {


    @Autowired
    private R8TxpoolService r8TxpoolService;

    @RequestMapping(value = "/txpool_status", method = RequestMethod.GET)
    Response txpool_status() throws Exception {
        Response resp = new Response();
        try {
            resp.setResult(r8TxpoolService.txpool_status());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }

    @RequestMapping(value = "/txpool_content", method = RequestMethod.GET)
    Response txpool_content() throws Exception {
        Response resp = new Response();
        try {
            String result = r8TxpoolService.txpool_content();
            resp.setResult(result);
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }



    @RequestMapping(value = "/txpool_inspect", method = RequestMethod.GET)
    Response txpool_inspect() throws Exception {
        Response resp = new Response();
        try {
            String result = r8TxpoolService.txpool_inspect();
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
