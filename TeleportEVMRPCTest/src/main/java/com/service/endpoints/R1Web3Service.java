package com.service.endpoints;

import com.pool.Web3jConnPool;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;

import java.io.IOException;

@Service
public class R1Web3Service {

    public String web3_clientVersion() throws IOException {
        Web3ClientVersion resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().web3ClientVersion().send();
        return resp.getResult();
    }

    public String web3_sha3() throws IOException {
        Web3Sha3 web3Sha3 = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().web3Sha3("0x68656c6c6f20776f726c64").send();
        return web3Sha3.getResult();
    }
}
