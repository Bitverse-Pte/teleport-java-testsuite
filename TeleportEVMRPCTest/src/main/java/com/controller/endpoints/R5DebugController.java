package com.controller.endpoints;

import com.bean.Response;
import com.service.endpoints.R5DebugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R5DebugController")
public class R5DebugController {

    @Autowired
    private R5DebugService r5DebugService;

    @RequestMapping(value = "/debug_memStats", method = RequestMethod.GET)
    Response web3_clientVersion()  {
        Response resp = new Response();
        try {
            resp.setResult(r5DebugService.debug_memStats());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }
}
