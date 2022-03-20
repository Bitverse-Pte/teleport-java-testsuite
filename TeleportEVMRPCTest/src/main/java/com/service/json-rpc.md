# namespace

| Namespace                                  | Description                                                                                                                                                                                                                  | Supported | Enabled by Default |
|--------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------|--------------------|
| [`eth`](./endpoints.md#eth-methods)           | Evmos provides several extensions to the standard `eth` JSON-RPC namespace.                                                                                                                                              | ✔         | ✔                  |
| [`web3`](./endpoints.md#web3-methods)         | The `web3` API provides utility functions for the web3 client.                                                                                                                                                               | ✔         | ✔                  |
| [`net`](./endpoints.md#net-methods)           | The `net` API provides access to network information of the node                                                                                                                                                             | ✔         | ✔                  |
| `clique`                                   | The `clique` API provides access to the state of the clique consensus engine. You can use this API to manage signer votes and to check the health of a private network.                                                      | ❌         |                    |
| `debug`                                    | The `debug` API gives you access to several non-standard RPC methods, which will allow you to inspect, debug and set certain debugging flags during runtime.                                                                 | ✔         |                    |
| `les`                                      | The `les` API allows you to manage LES server settings, including client parameters and payment settings for prioritized clients. It also provides functions to query checkpoint information in both server and client mode. | ❌         |                    |
| [`miner`](./endpoints.md#miner-methods)       | The `miner` API allows you to remote control the node’s mining operation and set various mining specific settings.                                                                                                           | ✔         | ❌                  |
| [`txpool`](./endpoints.md#txpool-methods)     | The `txpool` API gives you access to several non-standard RPC methods to inspect the contents of the transaction pool containing all the currently pending transactions as well as the ones queued for future processing.    | ✔         | ❌                  |
| `admin`                                    | The `admin` API gives you access to several non-standard RPC methods, which will allow you to have a fine grained control over your nodeinstance, including but not limited to network peer and RPC endpoint management.     | ❌         |                    |
| [`personal`](./endpoints.md#personal-methods) | The `personal` API manages private keys in the key store.                                                                                                                                                                    | ✔         | ❌                  |


### not supported
- clique
- les
- admin

### supported but not enable by default
- miner
- txpool
- personal
- debug

### supported and enable by default
- eth
- web3
- net

在测试环境和生产环境上，启动该三个api即可。

### 各个模块相应功能
- web3:  查看web3_client版本号，以及对数据进行sha3计算。在测试用例中对sha3计算接口进行结果的验证。
- net：  在ethermint中，net api的实现方式是查询tendemrint节点的连接状态，ethermint不直接管理连接。查询方式同样通过ABCI。
- debug: debug类型的接口主要为开发使用，主要用途为允许查看、调试以及对某些特殊变量设定指定的值来查看运行。经过测试，debug api默认不开启。通常只供开发使用。
  - mock了接口，目前没有实际实现。包括：debug_intermediateRoots、debug_stopGoTrace（go version<1.5）、debug_stopGoTrace（go version<1.5）。
  - 实现了接口，使用的过程中正确性需要进行确认。主要包括debug_traceTransaction、debug_traceBlockByNumber、debug_traceBlockByHash、debug_blockProfile、debug_cpuProfile、debug_gcStats、debug_goTrace、debug_memStats、debug_setBlockProfileRate、debug_stacks、debug_startCPUProfile、debug_stopCPUProfile、debug_writeBlockProfile、debug_writeMemProfile、debug_mutexProfile、debug_setMutexProfileFraction、debug_writeMutexProfile、debug_freeOSMemory、debug_setGCPercent、debug_startGoTrace(go version>1.5)、debug_stopGoTrace(go verison>1.5)。
- txpool: 在ethermint中，txpool接口均为mock接口，返回均为空值。引入该接口出于某些浏览器的考量，例如BlockScout启动过程中会去访问blockscout接口。
- miner: miner接口分为两类（miner在web3j sdk中目前没有直接调用接口。）：
  - mock了接口，目前没有实际实现。包括miner_start,miner_stop,miner_setExtra,miner_getHashrate,miner_setGaslimit
  - 实现了接口，正确性需要进行确认。包括miner_setGaslimint(需要重启), miner_setEtherbase（需要代码review和实际测试）。
- personal:
  - mock了接口，目前没有实际实现。主要包括：personal_lockAccount、personal_unlockAccount、personal_unpair、personal_initializeWallet、personal_ListWallets。
  - 实现了接口，正确性需要进行确认。主要包括personal_ecRecover、personal_sign、personal_sendTransaction、personal_newAccount、personal_listAccounts、personal_importRawKey。
- eth: ETH类接口是ethermint实现的核心接口，是需要特别关注的地方。目前接口分为三类：
  - 没有mock接口的实现。包括eth_getWork、eth_submitWork。只使用于POW场景。
  - mock了接口，没有实际实现。包括eth_mining,eth_hashrate、eth_getUncleCountByBlockHash、eth_getUncleCountByBlockNumber、eth_getUncleByBlockHashAndIndex、eth_getUncleByBlockNumberAndIndex。
  - 实现接口，正确性需要持续关注。接口列表如下：
    - eth_protocolVersion: 以太坊当前协议。目前eth65。
    - eth_mining:  当前节点是否挖矿。总返回false。
    - eth_hashrate: 生成hash速率。总是为0。
    - eth_chainId: 返回chainId。BigInteger类型。默认9000。**生产环境可能需要修改**
    - eth_syncing: 返回当前节点的同步状态。false或者返回`startingBlock`和`currentBlock`，通过ABCI向tendermint发起查询。
    - eth_coinbase: 返回当前coinbase账户
    - eth_gasPrice: 返回当前的gaspirce。
    - eth_maxPriorityFeePerGas: EIP-1559引入。伦敦升级，硬分叉。
    - eth_feeHistory: EIP-1559引入。伦敦升级，硬分叉。
    - eth_call: 在evm中mock执行，并不真正在链上创建交易。
  - 新增加接口，在go-ethereum中没有查看到。
    - eth_getTransactionLogs
    - eth_getPendingTransactions
  - 在生产环境中基本不会使用的接口
    - eth_sign
    - eth_sendTransaction
  - 特殊接口eth_subscribe, eth_unsubscribe
    - 可以通过wscat连接8545端口。不常用。

    
```shell
root@iZt4nfgaeco149mesz725cZ:~# wscat -c ws://106.14.204.16:8546 -x '{"id": 10, "method": "eth_subscribe", "params": ["newHeads"]}'
{"jsonrpc":"2.0","result":"0x6abf3041710ce30d43284784e06ba891","id":10}

{"jsonrpc":"2.0","method":"eth_subscription","params":{"subscription":"0x6abf3041710ce30d43284784e06ba891","result":{"parentHash":"0x79be2c7b1dafab0558edabd88b45c4ccd404d211405e6db399851427c36e3bc5","sha3Uncles":"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347","miner":"0xe38675bee6038dee5b937a8e2dd1e187aa102664","stateRoot":"0xd332972992a0bea20b09df92ac2ade0c5806d62197ec485174b642b4e6184b6c","transactionsRoot":"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421","receiptsRoot":"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421","logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","difficulty":"0x0","number":"0x37","gasLimit":"0x0","gasUsed":"0x0","timestamp":"0x61d7ee8c","extraData":"0x","mixHash":"0x0000000000000000000000000000000000000000000000000000000000000000","nonce":"0x0000000000000000","baseFeePerGas":"0x3b9aca00","hash":"0xaab6cb0359ad484662d65cbf9e635d255f1cd2814d4f629d0311e3d3048a13e9"}}}
```


### 测试发现的问题

- batchCall 不兼容。已提issue.

问题代码：
```go

func (s *websocketsServer) readLoop(wsConn *wsConn) {
	for {
		_, mb, err := wsConn.ReadMessage()
		if err != nil {
			_ = wsConn.Close()
			return
		}

		var msg map[string]interface{}
		err = json.Unmarshal(mb, &msg)
		if err != nil {
			s.sendErrResponse(wsConn, "invalid request")
			continue
		}

```

batchRequest发送到链上为数组
```
[{"jsonrpc":"2.0","method":"eth_getBalance","params":["0x62b59d0910d4dcd905cfcb268d8f35955f2e2acb","0x0"],"id":0},{"jsonrpc":"2.0","method":"eth_getBalance","params":["0xcc0aa3021d70885084b1902e20f6aee2f1ed5d9c","0x0"],"id":1}]
```

```go
// map[string]interface{} 数据结构无法解析
// error type:  cannot unmarshal array into Go value of type map[string]interface {}
```