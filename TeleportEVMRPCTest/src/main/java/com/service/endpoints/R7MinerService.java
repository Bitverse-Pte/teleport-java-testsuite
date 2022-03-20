package com.service.endpoints;

import com.pool.Web3jConnPool;
import com.setting.BitostSuiteSetting;
import com.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TxPoolStatus;

import java.io.IOException;

@Service
public class R7MinerService {

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

  String minerSetExtraReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"miner_setExtra\",\"params\":[\"data\"],\"id\":1}";
  String minerGetHashrateReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"miner_getHashrate\",\"params\":[],\"id\":1}";

  String minerStartReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"miner_start\",\"params\":[1],\"id\":1}";
  String minerStopReq = "{\"jsonrpc\":\"2.0\",\"method\":\"miner_stop\",\"params\":[],\"id\":1}";
  String minerSetGaslimitReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"miner_setGasLimit\",\"params\":[\"0x10000\"],\"id\":1}";

  String minerSetGasPriceReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"miner_setGasPrice\",\"params\":[\"0x0\"],\"id\":1}";

  String minerSetEtherbaseReq =
      "{\"jsonrpc\":\"2.0\",\"method\":\"miner_setEtherbase\",\"params\":[\"0x62b59d0910d4dcd905cfcb268d8f35955f2e2acb\"],\"id\":1}";

    //not supported, but have the interface
    public String miner_setExtra() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerSetExtraReq);
        return result;
    }

    //not supported, but have the interface
    public String miner_getHashrate() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerGetHashrateReq);
        return result;
    }

    //not supported, but have the interface
    public String miner_start() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerStartReq);
        return result;
    }

    //not supported, but have the interface
    public String miner_stop() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerStopReq);
        return result;
    }

    //not supported, but have the interface
    public String miner_setGaslimit() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerSetGaslimitReq);
        return result;
    }

    //supported, but need to verified
    public String miner_setGasPrice() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerSetGasPriceReq);
        return result;
    }

    //supported, but need to verified
    public String miner_setEtherbase() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),minerSetEtherbaseReq);
        return result;
    }
}
