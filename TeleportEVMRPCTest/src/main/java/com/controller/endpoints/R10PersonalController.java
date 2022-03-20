package com.controller.endpoints;


import com.bean.Response;
import com.service.endpoints.R10PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/R10PersonalController")
public class R10PersonalController {

    @Autowired
    private R10PersonalService r10PersonalService;

    @RequestMapping(value = "/personal_listAccounts", method = RequestMethod.GET)
    Response web3_clientVersion()  {
        Response resp = new Response();
        try {
            resp.setResult(r10PersonalService.personal_listAccounts());
            resp.setSuccessed(true);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setSuccessed(false);
            return resp;
        }
        return resp;
    }
}
