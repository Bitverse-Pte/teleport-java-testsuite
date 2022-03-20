package com.service;

import com.db.LevelDBUtils;
import com.errors.ErrorInfo;
import com.initial.InitContractService;
import com.manager.NonceManager;
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
public class PerfTestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerfTestService.class);

    @Autowired
    private InitContractService initContractService;

    @Autowired
    private LevelDBUtils levelDBUtils;

    @Autowired
    private Erc20TxService erc20TxService;

    private int startBlockNumber;

    private int endBlockNumber;

    private int statisicTxsNumber;

    public boolean initialNonce(int peerId,  int accountId){
        Web3j web3j = Web3jConnPool.getInstance()
                .getConnClient().getWeb3jList().get(peerId);
        //转账人账户地址
        String ownAddress = AccountsPool.getInstance().getColdWalletList().get(accountId).getAddress();
        //getNonce
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3j.ethGetTransactionCount(
                    ownAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
            return false;
        } catch (ExecutionException e) {
            LOGGER.info(e.getMessage());
            return false;
        }

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        NonceManager.getInstance().setNonce(nonce);
        LOGGER.info("current nonce: "+nonce.toString());

        return true;
    }

    public int getCurrentNonce(int peerId, int accountId){
        Web3j web3j = Web3jConnPool.getInstance()
                .getConnClient().getWeb3jList().get(peerId);
        //转账人账户地址
        String ownAddress = AccountsPool.getInstance().getColdWalletList().get(accountId).getAddress();
        //getNonce
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3j.ethGetTransactionCount(
                    ownAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
            return -1;
        } catch (ExecutionException e) {
            LOGGER.info(e.getMessage());
            return -1;
        }

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        LOGGER.info("current nonce: "+nonce.toString());

        return nonce.intValue();
    }

    public void preConstruct(int peerId, int accountId, int txsNumber) throws Exception {
        BigInteger nonce = NonceManager.getInstance().getNonce();
        LOGGER.info("construct begin");

        for(int groupHeadIndex = 0; groupHeadIndex<txsNumber; ){
            if (groupHeadIndex%5000==0){
                LOGGER.info("we have created:"+ Integer.toString(groupHeadIndex));
            }

            int groupSize = 0;
            if (txsNumber> groupHeadIndex+400){
                groupSize = 400;
            }else{
                groupSize = txsNumber - groupHeadIndex;
            }

            String[] datasArray = new String[groupSize];
            for (int i=0;i<groupSize;i++){
                String paramsData = DeployedErc20Pool.getInstance().getErc20test().transfer(
                        AccountsPool.getInstance().getReceiver()
                        ,new BigInteger("1")).encodeFunctionCall();

                datasArray[i] = erc20TxService.constructTxData(initContractService.erc20Address,0,
                        AccountsPool.getInstance().getColdWalletList().get(accountId),
                        true,paramsData,nonce);

                nonce = nonce.add(new BigInteger("1"));
            }
            levelDBUtils.writeBatchData("tx_", groupHeadIndex, datasArray);
            groupHeadIndex = groupHeadIndex + groupSize;

        }
        LOGGER.info("construct end");
    }

    //we needn't to know the accountid, becase we directly get the tx from leveldb storage.
    //interval define the time interval of sending.
    //default is 0, so it will send txs continously.
    public void sendTxs(int peerId,int accountIndex, int txsNumber, int interval) throws InterruptedException, ExecutionException, IOException {
        int nonceStart = getCurrentNonce(peerId,accountIndex);
        Web3j web3j = Web3jConnPool.getInstance()
                .getConnClient().getWeb3jList().get(peerId);

        //mark the start block index
        //plus 2 to get the more accurate result.
        startBlockNumber = web3j.ethBlockNumber().send().getBlockNumber().intValue()+2;

        //发送交易
        EthSendTransaction ethSendTransaction = null;
        boolean resetFlag = false;

        for (int txSeq = 0; txSeq < txsNumber;){
            if (resetFlag){
                LOGGER.info("current tx seq: "+ String.valueOf(txSeq+nonceStart));
                resetFlag = false;
            }
            String dbValue = levelDBUtils.readTxData("tx_",txSeq);
            try {
                ethSendTransaction = web3j.ethSendRawTransaction(dbValue).send();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.info("we will ignore the timeout error to keep the test on");
            }

            String txHash = ethSendTransaction.getTransactionHash();
            if (ethSendTransaction == null || ethSendTransaction.hasError() || txHash == null) {
                if (ethSendTransaction.getError().getMessage().equals(ErrorInfo.txExisted)){
                    //if tx existed in mempool, go ahead
                    txSeq++;
                }else{
                    LOGGER.info(ethSendTransaction.getError().getMessage());
                    //if we didn't receive the txHash, we will sleep 10s and resend the tx
                    //we should also print the currentNonce
                    String ownAddress = AccountsPool.getInstance().getColdWalletList().get(accountIndex).getAddress();

                    if (ethSendTransaction.getError().getMessage().startsWith(ErrorInfo.invalidNonce)){
                        LOGGER.info("invalid Nonce, we will handle it soon");
                        String result = ethSendTransaction.getError().getMessage();

                        int startIndex = result.indexOf("expected ");
                        int endIndex = result.indexOf(":");

                        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                                ownAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
                        LOGGER.info("current waiting nonce from api is: "+ ethGetTransactionCount.getTransactionCount().toString());

                        int expectedNonce = Integer.valueOf(result.substring(startIndex+9,endIndex));
                        txSeq = expectedNonce-nonceStart;
                        resetFlag =true;
                    }else{
                        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                                ownAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
                        LOGGER.info("current pending nonce is: "+ ethGetTransactionCount.getTransactionCount().toString());

                        //RESET THE NONCE
                        txSeq = ethGetTransactionCount.getTransactionCount().intValue()-nonceStart;
                        resetFlag =true;
                    }
                }
            }else{
                txSeq++;
            }

            if (txSeq % 200 == 0){
                LOGGER.info("send nonce is:" + Integer.toString(txSeq+nonceStart));
                Thread.sleep(interval);
            }
        }

        //mark the end block index
        //minus 2 to get the more accurate result.
        endBlockNumber = web3j.ethBlockNumber().send().getBlockNumber().intValue();
        statisicTxsNumber = 0;
    }

    public String statisicResult() throws IOException {
        Web3j web3j = Web3jConnPool.getInstance().getConnClient().getWeb3jList().get(0);

        for(int i= startBlockNumber;i<=endBlockNumber;i++){
            EthBlock block = web3j.ethGetBlockByNumber(
                    new DefaultBlockParameterNumber(i),
                    false).send();
            LOGGER.info(block.getBlock().getNumber().toString());
            LOGGER.info(String.valueOf(block.getBlock().getTransactions().size()));
            statisicTxsNumber = statisicTxsNumber + block.getBlock().getTransactions().size();
        }

        long startTimeStamp = web3j.ethGetBlockByNumber(
                new DefaultBlockParameterNumber(startBlockNumber),
                false).send().getBlock().getTimestamp().longValue();
        long endTimeStamp = web3j.ethGetBlockByNumber(
                new DefaultBlockParameterNumber(endBlockNumber),
                false).send().getBlock().getTimestamp().longValue();

        LOGGER.info("start block: "+ String.valueOf(startBlockNumber));
        LOGGER.info("start timestamp: "+String.valueOf(startTimeStamp));
        LOGGER.info("end block: "+ String.valueOf(endBlockNumber));
        LOGGER.info("end timestamp: "+String.valueOf(endTimeStamp));
        LOGGER.info("total statisic txs: "+String.valueOf(statisicTxsNumber));

        LOGGER.info("interval: "+String.valueOf(endTimeStamp-startTimeStamp));
        LOGGER.info("tps is: "+String.valueOf(statisicTxsNumber/(endTimeStamp-startTimeStamp)));

        return "tps is: "+String.valueOf(statisicTxsNumber/(endTimeStamp-startTimeStamp));
    }
}
