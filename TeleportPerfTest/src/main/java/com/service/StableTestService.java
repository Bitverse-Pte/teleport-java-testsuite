package com.service;

import com.errors.ErrorInfo;
import com.initial.InitContractService;
import com.pool.AccountsPool;
import com.pool.DeployedErc20Pool;
import com.pool.Web3jConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Service
public class StableTestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StableTestService.class);

    @Autowired
    private Erc20TxService erc20TxService;

    @Autowired
    private InitContractService initContractService;

    private int beginBlockNumber;

    private int endBlockNumber;

    private int statisicTxsNumber;

    //we needn't to know the accountid, becase we directly get the tx from leveldb storage.
    //interval define the time interval of sending.
    //default is 0, so it will send txs continously.
    public void sendTxs(int peerId,int accountIndex, int txsNumber, int interval) throws InterruptedException, IOException {
        Web3j web3j = Web3jConnPool.getInstance().getConnClient().getWeb3jList().get(peerId);

        //发送交易
        EthSendTransaction ethSendTransaction = null;
        int startTime = (int) (System.currentTimeMillis()/1000);

        beginBlockNumber = web3j.ethBlockNumber().send().getBlockNumber().intValue()+2;

        int nonce = getNonce(peerId,accountIndex);
        for (int txSeq = 0; txSeq < txsNumber;){
            String paramsData = DeployedErc20Pool.getInstance().getErc20test().transfer(
                    AccountsPool.getInstance().getReceiver()
                    ,new BigInteger("1")).encodeFunctionCall();

            String hexValue = erc20TxService.constructTxData(initContractService.erc20Address,0,
                    AccountsPool.getInstance().getColdWalletList().get(accountIndex),
                    true,paramsData, BigInteger.valueOf(nonce));
            try {
                ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.info("we will ignore the timeout error to keep the test on");
            }
            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction == null || ethSendTransaction.hasError() || txHash == null) {
                if (ethSendTransaction.getError().getMessage().equals(ErrorInfo.txExisted)){
                    //if tx existed in mempool, go ahead
                    txSeq++;
                    nonce = nonce + 1;
                }
                nonce = getNonce(peerId,accountIndex);
            } else {
                txSeq++;
                nonce = nonce + 1;
            }
            if (txSeq%10==0){
                //If set 10, the send rate is 166tx/s
                Thread.sleep(interval);
            }
            if (txSeq%200==0){
                LOGGER.info("current tx seq is"+ Integer.toString(txSeq));
            }
        }
        int endTime = (int)(System.currentTimeMillis()/1000);
        int sendRate = txsNumber/(endTime- startTime);

        LOGGER.info("send end!");
        endBlockNumber = web3j.ethBlockNumber().send().getBlockNumber().intValue();
        LOGGER.info("sendRate is: "+ Integer.toString(sendRate)+" tx/s");
    }

    public String statisicResult() throws IOException {
        Web3j web3j = Web3jConnPool.getInstance().getConnClient().getWeb3jList().get(0);

        for(int i= beginBlockNumber;i<=endBlockNumber;i++){
            EthBlock block = web3j.ethGetBlockByNumber(
                    new DefaultBlockParameterNumber(i),
                    false).send();
            LOGGER.info(block.getBlock().getNumber().toString());
            LOGGER.info(String.valueOf(block.getBlock().getTransactions().size()));
            statisicTxsNumber = statisicTxsNumber + block.getBlock().getTransactions().size();
        }

        long startTimeStamp = web3j.ethGetBlockByNumber(
                new DefaultBlockParameterNumber(beginBlockNumber),
                false).send().getBlock().getTimestamp().longValue();
        long endTimeStamp = web3j.ethGetBlockByNumber(
                new DefaultBlockParameterNumber(endBlockNumber),
                false).send().getBlock().getTimestamp().longValue();

        LOGGER.info("start block: "+ String.valueOf(beginBlockNumber));
        LOGGER.info("start timestamp: "+String.valueOf(startTimeStamp));
        LOGGER.info("end block: "+ String.valueOf(endBlockNumber));
        LOGGER.info("end timestamp: "+String.valueOf(endTimeStamp));
        LOGGER.info("total statisic txs: "+String.valueOf(statisicTxsNumber));

        LOGGER.info("interval: "+String.valueOf(endTimeStamp-startTimeStamp));
        LOGGER.info("tps is: "+String.valueOf(statisicTxsNumber/(endTimeStamp-startTimeStamp)));

        return "tps is: "+String.valueOf(statisicTxsNumber/(endTimeStamp-startTimeStamp));
    }


    public int getNonce(int peerId,int accountIndex){
        Web3j web3j = Web3jConnPool.getInstance().getConnClient().getWeb3jList().get(peerId);
        //转账人账户地址
        String ownAddress = AccountsPool.getInstance().getColdWalletList().get(accountIndex).getAddress();
        //getNonce
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3j.ethGetTransactionCount(
                    ownAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
            return -1;
        } catch (ExecutionException e) {
            LOGGER.info(e.getMessage());
            return -1;
        }
        return ethGetTransactionCount.getTransactionCount().intValue();
    }


}
