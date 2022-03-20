package com.controller;


import com.db.LevelDBUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api("teleport")
//@RestController
public class DBTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBTestController.class);

    @Autowired
    private LevelDBUtils levelDBUtils;


    @RequestMapping(value = "/db_write", method = RequestMethod.GET)
    void dbWrite() throws Exception {
        levelDBUtils.writeData("key1","value1");
    }


    @RequestMapping(value = "/db_read", method = RequestMethod.GET)
    String dbRead() throws Exception {
        String result = levelDBUtils.readData();
        LOGGER.info(result);
        return result;
    }
}
