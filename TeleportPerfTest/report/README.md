# 性能测试报告


#### 大压力下部分节点跟不上区块高度

例如同一时间段下，peer0和peer5对比：
peer0 日志：
```shell
----executed begin block------
1641899394
----executed begin block------
11:09AM INF minted coins from module account amount=7209748386251702209abiton from=mint module=x/bank
11:09AM INF executed block tps:  end=1641899394
11:09AM INF executed block height=549 module=state num_invalid_txs=0 num_valid_txs=0 server=node
11:09AM INF commit synced commit=436F6D6D697449447B5B313930203231312031323920342031383620313030203238203139312034352036342032313120323036203838203139392031352032313020313420323820313539203436203334203232332032323320302038372031372033203132362038382032343020313639203139385D3A3232357D
----executed commit------
1641899394
549
----executed commit------
11:09AM INF committed state app_hash=BED38104BA641CBF2D40D3CE58C70FD20E1C9F2E22DFDF005711037E58F0A9C6 height=549 module=state num_txs=0 server=node
11:09AM INF indexed block height=549 module=txindex server=node
```



peer5 日志：
```shell
11:09AM INF received complete proposal block hash=09175CF2FF60F7725160DA488DE4FB7C57921B551FAA4E9B43631FC393D5F6A7 height=303 module=consensus server=node
11:09AM INF finalizing commit of block hash=09175CF2FF60F7725160DA488DE4FB7C57921B551FAA4E9B43631FC393D5F6A7 height=303 module=consensus num_txs=0 root=1B3835C9F0BAF8E3A7EE2131BDD5563E82FFA7A073105ADF6FDF5ED756F571D7 server=node
----executed begin block------
1641899395
----executed begin block------
11:09AM INF minted coins from module account amount=7209430869427615731abiton from=mint module=x/bank
11:09AM INF executed block tps:  end=1641899395
11:09AM INF executed block height=303 module=state num_invalid_txs=0 num_valid_txs=0 server=node
11:09AM INF commit synced commit=436F6D6D697449447B5B35372035332031383520393620353820313834203231203135362036312034342035352031353720313634203136322037312034312032323120313633203130362032303320313020313732203133302032303020313431203937203136322036342032323620323131203736203233355D3A3132467D
----executed commit------
1641899395
303
----executed commit------
11:09AM INF committed state app_hash=3935B9603AB8159C3D2C379DA4A24729DDA36ACB0AAC82C88D61A240E2D34CEB height=303 module=state num_txs=0 server=node
```

mempool recheck 的过程中过于缓慢的问题。

可以做到事情，做一个cache，存放的是map[hash]tx的映射。供recheck的时候绕过数据解析。