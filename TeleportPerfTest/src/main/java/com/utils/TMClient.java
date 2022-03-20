package com.utils;

import com.bean.TMBlockInfo;
import com.google.gson.Gson;
import com.setting.BitostSuiteSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

//This is a tm client , used to get the blockchain statisic
//info from 26657 port.
public class TMClient {
    static Gson gson = new Gson();

    public static String getTxCountInBlock(String tmUrl, int blockHeight) throws IOException {
        String url = tmUrl+"/blockchain?minHeight="+blockHeight+"&maxHeight="+blockHeight;


        String result = HttpUtils.get(url);
        TMBlockInfo tmBlockInfo = gson.fromJson(result, TMBlockInfo.class);

        return tmBlockInfo.getResult().getBlock_metas().get(0).getNum_txs();
    }
}
