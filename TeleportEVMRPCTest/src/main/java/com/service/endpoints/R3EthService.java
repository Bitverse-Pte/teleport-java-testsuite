package com.service.endpoints;

import com.google.gson.Gson;
import com.pool.AccountsPool;
import com.pool.Web3jConnPool;
import com.service.ReqConst;
import com.setting.BitostSuiteSetting;
import com.utils.ColdWallet;
import com.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.*;

import java.io.IOException;
import java.math.BigInteger;

@Service
public class R3EthService {

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    private Gson gson = new Gson();

    public String eth_protocolVersion() throws IOException {
        EthProtocolVersion resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethProtocolVersion().send();
        return resp.getResult();
    }


    public Boolean eth_mining() throws IOException {
        EthMining resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethMining().send();
        return resp.getResult();
    }


    public String eth_hashrate() throws IOException {
        EthHashrate resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethHashrate().send();
        return resp.getHashrate().toString();
    }


    public String eth_chainId() throws IOException {
        EthChainId resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethChainId().send();
        return resp.getChainId().toString();
    }


    public String eth_syncing() throws IOException {
        EthSyncing resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethSyncing().send();
        return String.valueOf(resp.isSyncing());
    }


    public String eth_coinbase() throws IOException {
        EthCoinbase resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethCoinbase().send();
        return resp.getAddress();
    }


    public String eth_gasPrice() throws IOException {
        EthGasPrice resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethGasPrice().send();
        System.out.println(resp.getResult());
        System.out.println(resp.getRawResponse());
        return resp.getGasPrice().toString();
    }


    public String eth_maxPriorityFeePerGas() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0), ReqConst.ethMaxPriorityFeePerGas);
        return result;
    }

    public String eth_feeHistory() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),ReqConst.ethFeeHistory);
        return result;
    }


    public String eth_accounts() throws IOException {
        EthAccounts resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethAccounts().send();
        return resp.getAccounts().toString();
    }


    public String eth_blockNumber() throws IOException {
        EthBlockNumber resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethBlockNumber().send();
        return resp.getBlockNumber().toString();
    }


    public String eth_getBalance() throws IOException {
        EthGetBalance resp = Web3jConnPool.getInstance()
                .getConnClient()
                .getAnchorNodeConn()
                .ethGetBalance(AccountsPool.getInstance().getValColdwallet().getAddress(),
                        DefaultBlockParameterName.LATEST)
                .send();
        return resp.getBalance().toString();
    }


    public String eth_getStorageAt() throws IOException {
        EthGetStorageAt resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethGetStorageAt(
                        AccountsPool.getInstance().getValColdwallet().getAddress(),new BigInteger("0"),
                        DefaultBlockParameterName.LATEST
                ).send();
        return resp.getData();
    }


    public String eth_getTransactionCount() throws IOException {
        EthGetTransactionCount resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethGetTransactionCount(
                        AccountsPool.getInstance().getValColdwallet().getAddress(),
                        DefaultBlockParameterName.LATEST
                ).send();
        return resp.getTransactionCount().toString();
    }


    public String eth_getBlockTransactionCountByHash() throws IOException {
        EthGetBlockTransactionCountByHash resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().ethGetBlockTransactionCountByHash(
                        "0xefa818d4ec1c50317eff10d4e9de46704152eab521d9d2b380a303d454867b9f").send();
        return resp.getTransactionCount().toString();
    }

    public String eth_getBlockTransactionCountByNumber() throws IOException {
        EthGetBlockTransactionCountByNumber resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn()
                .ethGetBlockTransactionCountByNumber(new DefaultBlockParameterNumber(3)).send();
        return resp.getTransactionCount().toString();
    }



    public String eth_getCode() throws IOException {
        EthGetCode resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn()
                .ethGetCode(AccountsPool.getInstance().getValColdwallet().getAddress(),
                        DefaultBlockParameterName.LATEST
                ).send();
        return resp.getCode();
    }

    public String eth_getTransactionLogs() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),ReqConst.ethTransactionLogs);
        return result;
    }


    public String eth_sendRawTransaction() throws IOException {
        String txHash = ColdWallet.sendRawTxWithoutData(AccountsPool.getInstance().getAddressList().get(0),
                100,AccountsPool.getInstance().getValColdwallet(), true);
        return txHash;
    }

    public String eth_resend() throws IOException {
        return "eth_resend to be add";
    }

    public String eth_call() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),ReqConst.ethCall);
        return result;
    }


    public String eth_estimateGas() throws IOException {
        BigInteger GAS_PRICE = BigInteger.valueOf(22000000000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

        Transaction tx = Transaction.createEtherTransaction(AccountsPool.getInstance().getValColdwallet().getAddress(),
                new BigInteger("10"),GAS_PRICE,GAS_LIMIT,
                AccountsPool.getInstance().getReceiver(), new BigInteger("10"));

        EthEstimateGas resp = Web3jConnPool.getInstance().getConnClient().getAnchorNodeConn().ethEstimateGas(tx).send();
        return resp.getAmountUsed().toString();
    }



    public String eth_getBlockByHash() throws IOException {
        EthBlock resp = Web3jConnPool.getInstance().getConnClient().getAnchorNodeConn()
                .ethGetBlockByHash("0xefa818d4ec1c50317eff10d4e9de46704152eab521d9d2b380a303d454867b9f",
                        false).send();
        return gson.toJson(resp);
    }


    public String eth_getBlockByNumber() throws IOException {
        EthBlock resp = Web3jConnPool.getInstance().getConnClient().getAnchorNodeConn()
                .ethGetBlockByNumber(new DefaultBlockParameterNumber(3),
                        false).send();
        return gson.toJson(resp);
    }


    public String eth_getTransactionByHash() throws IOException {
        EthTransaction resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().
                ethGetTransactionByHash("0x6720d544e983a337da608fe39d37a28f89d83d25270672fe8bdfc9a263ef7fde").send();
        return gson.toJson(resp.getTransaction().get());
    }


    public String eth_getTransactionByBlockHashAndIndex() throws IOException {
        EthTransaction resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().
                ethGetTransactionByBlockHashAndIndex("0xefa818d4ec1c50317eff10d4e9de46704152eab521d9d2b380a303d454867b9f",
                        new BigInteger("0")).send();
        return gson.toJson(resp.getTransaction().get());
    }

    public String eth_getTransactionByBlockNumberAndIndex() throws IOException {
        EthTransaction resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().
                ethGetTransactionByBlockNumberAndIndex(new DefaultBlockParameterNumber(3),
                        new BigInteger("0")).send();
        return gson.toJson(resp.getTransaction().get());
    }


    public String eth_getTransactionReceipt() throws IOException {
        EthGetTransactionReceipt resp = Web3jConnPool.getInstance().
                getConnClient().getAnchorNodeConn().
                ethGetTransactionReceipt("0x6720d544e983a337da608fe39d37a28f89d83d25270672fe8bdfc9a263ef7fde").send();
        return gson.toJson(resp.getTransactionReceipt().get());
    }

    public String eth_getPendingTransactions() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),ReqConst.ethPendingTransactions);
        return result;
    }



    public String eth_getProof() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0),ReqConst.ethProof);
        System.out.println(result);
        return result;
    }
}
