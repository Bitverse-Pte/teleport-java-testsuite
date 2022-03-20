package com.controller.event;

import com.service.event.E1Web3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/EFilter1Web3Controller")
public class EFilter1Web3Controller {

    @Autowired
    private E1Web3Service e1Web3Service;

    @RequestMapping(value = "/01_block_hash_filter", method = RequestMethod.GET)
    String block_simple_filter() throws Exception {
        e1Web3Service.blockHashFilterExample();
        return "success";
    }


    @RequestMapping(value = "/02_block_head_notiication", method = RequestMethod.GET)
    String block_head_notiication() throws Exception {
        e1Web3Service.blockHeadNotification();
        return "success";
    }

    @RequestMapping(value = "/03_block_filter", method = RequestMethod.GET)
    String block_filter() throws Exception {
        e1Web3Service.blockFilterExample();
        return "success";
    }

    @RequestMapping(value = "/04_block_info_filter", method = RequestMethod.GET)
    String block_info_filter() throws Exception {
        e1Web3Service.blockInfoFilter();
        return "success";
    }

    @RequestMapping(value = "/05_tx_simple_filter", method = RequestMethod.GET)
    String tx_simple_filter() throws Exception {
        e1Web3Service.txSimpleFilterExample();
        return "success";
    }

}