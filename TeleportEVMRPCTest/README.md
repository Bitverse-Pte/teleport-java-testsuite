# BitOS RPC接口测试

### 相关依赖
- maven
- java 1.8

### 1.代码实现位置
在Bitos中我们使用了ethermint来实现go-ethereum兼容性。

ethermint的实现方式是将go-ethereum中的许多接口直接重写，而非继承的方式实现。

具体实现的位置位于： **ethermint/RPC/目录**下，提供了基本的rpc实现和websocket的实现方式。

当确认是否支持某些接口时，比较好的方式是直接查看ethermint的代码进行确认。


### 2.特定实现带来的后续关注点

在ethermint中，将go-ethereum更多作为工具库，使用其中的函数实现指定的功能。

在ethermint中，
- 交易的执行和存储有单独的代码实现。
- 在ethermint中并不存在真正的交易池，而是mempoolclient通过abci向mempool进行查询。

这样实现带来的后续关注点如下：
- 当以太坊主网在交易执行处理存储进行升级时，例如支持WASM智能合约，我们需要独立审计功能点，并决定是否跟进。
- 安全问题需单独关注。

### 测试工具说明

可以做RPC接口的自动化测试目前已有的工具有POSTMAN等。

考虑到这部分测试还涉及智能合约的交易监听，Websocket通信，以及对SDK的测试，从选型上选择Web3j进行接口调用和检测。


### 测试包括

- [json-rpc method](src/main/java/com/service/json-rpc.md)

### 补充文档
- [debug method接口用途说明](src/main/java/com/service/debug.md)

### 目前测试出的相应的问题

##### batchcall

无法执行batchcall，会抛出invalid request的问题。主要原因在于readLoop函数中无法解析数组.

##### block event 监听有问题

在使用web3j监听区块事件，交易事件，智能合约事件的过程中，发现智能合约事件监听没有问题，区块事件监听存在问题。

智能合约事件监听代码：
```
       WebSocketService ws = new WebSocketService("ws://172.17.0.1:8546",false);
        ws.connect();
        Web3j web3jWs = Web3j.build(ws);

        if (initContractService.erc20Address.equals("")){
            LOGGER.info("conract haven't deployed yet");
            return;
        }
        String transferEventSig = EventEncoder.encode(BitosErc20test.TRANSFER_EVENT);
        EthFilter ethFilter =
                new EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        initContractService.erc20Address);

        ethFilter.addSingleTopic(transferEventSig);
        Disposable subscription = web3jWs.ethLogFlowable(ethFilter).subscribe(log ->{
            //Return class ： org.web3j.protocol.core.methods.response.Log
            LOGGER.info(log.getData());
            LOGGER.info(log.getBlockNumber().toString());
            LOGGER.info(log.getLogIndex().toString());
            LOGGER.info(gson.toJson(log.getTopics()));
        });

        LOGGER.info("tx event filter example");
        TimeUnit.MINUTES.sleep(10);
        subscription.dispose();
```

区块事件监听代码：
```
        WebSocketService ws = new WebSocketService("ws://172.17.0.1:8546",false);
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
```

经过在bitosd中将ethermint replace成本地库，测试问题出现在`rpc/ethereum/namespace/eth/filter/api.go`中。
原始代码：

```
        header := types.EthHeaderFromTendermint(data.Header, ethtypes.Bloom{}, baseFee)
        api.filtersMu.Lock()
        if f, found := api.filters[headerSub.ID()]; found {
            f.hashes = append(f.hashes, header.Hash())
        }
```
修改为：

```
        header := types.EthHeaderFromTendermint(data.Header, ethtypes.Bloom{}, baseFee)
        api.filtersMu.Lock()
        fmt.Println(header.Hash().String())
        if f, found := api.filters[headerSub.ID()]; found {
            f.hashes = append(f.hashes, common.BytesToHash(data.Header.Hash().Bytes()))
        }
```

区块事件监听正常。