package com.service.event;

import com.contract.erc20.BitosErc20test;
import com.controller.contract.ERC20ContractController;
import com.google.gson.Gson;
import com.pool.Web3jConnPool;
import com.service.erc20.InitContractService;
import com.setting.BitostSuiteSetting;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.protocol.websocket.events.NewHead;

import java.net.ConnectException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class E1Web3Service {

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    private static final Logger LOGGER = LoggerFactory.getLogger(ERC20ContractController.class);

    private Gson gson = new Gson();

    public int COUNT = 10;


    public void blockHashFilterExample() throws Exception {
        WebSocketService ws = new WebSocketService(bitostSuiteSetting.getWebsocket_url(),false);
        ws.connect();
        Web3j web3jWs = Web3j.build(ws);

        Disposable subscription = web3jWs.ethBlockHashFlowable().subscribe(txhash ->{
            LOGGER.info(txhash);
        });

        LOGGER.info("block simple filter example");
        TimeUnit.MINUTES.sleep(2);
        subscription.dispose();
    }

    public void blockHeadNotification() throws Exception{
        WebSocketService ws = new WebSocketService(bitostSuiteSetting.getWebsocket_url(),false);
        ws.connect();
        Web3j web3jWs = Web3j.build(ws);

        Disposable subscription = web3jWs.newHeadsNotifications().subscribe(head ->{
            LOGGER.info("new head coming");
            NewHead newHead = head.getParams().getResult();
            LOGGER.info(newHead.getStateRoot());
            LOGGER.info(newHead.getNumber());
        });

        TimeUnit.MINUTES.sleep(2);
        subscription.dispose();
    }

    public void blockFilterExample() throws ConnectException, InterruptedException {
        WebSocketService ws = new WebSocketService(bitostSuiteSetting.getWebsocket_url(),false);
        ws.connect();
        Web3j web3jWs = Web3j.build(ws);

        Disposable subscription = web3jWs.blockFlowable(false).subscribe(block ->{
            LOGGER.info(block.getBlock().getHash());
            LOGGER.info(block.getBlock().getNumber().toString());
            LOGGER.info(block.getBlock().getTimestamp().toString());
        });

        LOGGER.info("block simple filter example");
        TimeUnit.MINUTES.sleep(10);
        subscription.dispose();
    }


    public void txSimpleFilterExample() throws Exception {
        WebSocketService ws = new WebSocketService(bitostSuiteSetting.getWebsocket_url(),false);
        ws.connect();
        Web3j web3jWs = Web3j.build(ws);

        Disposable subscription = web3jWs.transactionFlowable().subscribe(tx ->{
            LOGGER.info(tx.getBlockNumber().toString());
            LOGGER.info(tx.getTransactionIndex().toString());
            LOGGER.info(tx.getHash());
        });

        LOGGER.info("tx simple filter example");
        TimeUnit.MINUTES.sleep(2);
        subscription.dispose();
    }

    public void blockInfoFilter() throws Exception {
        WebSocketService ws = new WebSocketService(bitostSuiteSetting.getWebsocket_url(),false);
        ws.connect();
        Web3j web3jWs = Web3j.build(ws);

        CountDownLatch countDownLatch = new CountDownLatch(COUNT);

        LOGGER.info("Waiting for " + COUNT + " transactions...");
        Disposable subscription = web3jWs.blockFlowable(true)
                .take(COUNT)
                .subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    LocalDateTime timestamp = Instant.ofEpochSecond(
                            block.getTimestamp()
                                    .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();
                    int transactionCount = block.getTransactions().size();
                    String hash = block.getHash();
                    String parentHash = block.getParentHash();
                    String height = block.getNumber().toString();

                    LOGGER.info(
                            timestamp + " " +
                                    "Tx count: " + transactionCount + ", " +
                                    "Hash: " + hash + ", " +
                                    "Parent hash: " + parentHash +
                                    "Number: " + height
                    );
                    countDownLatch.countDown();
                }, Throwable::printStackTrace);

        countDownLatch.await(10, TimeUnit.MINUTES);
        subscription.dispose();
    }


}
