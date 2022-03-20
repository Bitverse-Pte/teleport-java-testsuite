package com.service.endpoints;

import com.pool.Web3jConnPool;
import com.setting.BitostSuiteSetting;
import com.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.admin.methods.response.TxPoolContent;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.TxPoolStatus;

import java.io.IOException;

@Service
public class R8TxpoolService {
    String txpoolContentReq = "{\"jsonrpc\":\"2.0\",\"method\":\"txpool_content\",\"params\":[],\"id\":1}";
    String txpoolInspectReq = "{\"jsonrpc\":\"2.0\",\"method\":\"txpool_inspect\",\"params\":[],\"id\":1}";

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    public String txpool_status() throws IOException {
        TxPoolStatus resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().txPoolStatus().send();
        return resp.getResult().toString();
    }


    public String txpool_content() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),txpoolContentReq);

        return result;
    }

    public String txpool_inspect() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),txpoolInspectReq);

        return result;
    }

}
