package com.service.endpoints;

import com.pool.Web3jConnPool;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.NetListening;
import org.web3j.protocol.core.methods.response.NetPeerCount;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import java.io.IOException;

@Service
public class R2NetService {

    public String net_version() throws IOException {
        NetVersion resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().netVersion().send();
        return resp.getResult();
    }


    public String net_peerCount() throws IOException {
        NetPeerCount resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().netPeerCount().send();
        return resp.getResult();
    }


    public boolean net_listening() throws IOException {
        NetListening resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().netListening().send();
        return resp.getResult();
    }
}
