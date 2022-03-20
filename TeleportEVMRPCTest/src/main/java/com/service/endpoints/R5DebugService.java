package com.service.endpoints;

import com.pool.Web3jConnPool;
import com.service.ReqConst;
import com.setting.BitostSuiteSetting;
import com.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.NetPeerCount;

import java.io.IOException;

@Service
public class R5DebugService {

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    public String debug_memStats() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0), ReqConst.debugMemStatsReq);
        return result;
    }

}
