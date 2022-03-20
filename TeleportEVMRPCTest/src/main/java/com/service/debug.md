# go-ethereum debug rpc接口说明

参考文档：http://cw.hubwiz.com/card/c/geth-rpc-api/1/2/3/

- debug_backtraceAt：backtractAt方法设置日志回溯位置。当设置回溯位置后，在 该位置将触发一个日志消息，执行日志语句的go协程调用栈将 在stderr输出。
- debug_blockProfile：blockProfile方法在指定的时间段内开启区块性能检测，并将检测文件写入磁盘。 在大多数情况下，blockProfile方法使用值为1的检测速率获取尽可能准确的信息。 如果需要一个不同的检测速率，可以使用debug_writeBlockProfile方法手动设置 检测速率并写入检测文件。
- debug_cpuProfile：cpuProfile方法在指定时间段内开启CPU性能检测并将检测结果写入磁盘文件。
- debug_dumpBlock：dumpBlock方法读取指定编号区块的状态并返回一组账号（包括存储和代码）。
- debug_gcStats：gcStats方法返回垃圾回收（Garbage Collection）统计信息。
- debug_getBlockRlp：getBlockRlp方法读取并返回指定序号区块的RLP编码的数据。
- debug_goTrace：debug的goTrace方法在指定的时间段内开启Go运行时跟踪，并将跟踪 数据写入磁盘。
- debug_memStats：debug的memStats方法返回运行时内存统计信息详情。
- debug_seedHash：debug的seedHash方法提取指定序号区块的种子哈希。
- debug_setBlockProfileRate：debug的setBlockProfileRate方法设置go协程区块性能数据采集的速率， 单位：样本/秒。一个非零的值表示启动区块性能检测，零值表示停止区块 性能检测。采集的区块性能数据可以使用debug_writeBlockProfile方法写入文件。
- debug_setHead：debug的setHead方法设置本地链头区块。 注意，此操作有破坏性，可能严重损坏你的区块链，使用时请小心。
- debug_stacks：debug的stacks方法返回所有go协程的执行栈表示。注意该方法的 web3封装并不返回字符串，而是直接显示执行栈。
- debug_startCPUProfile：debug的startCPUProfile方法启用CPU性能检测并将检测结果写入指定文件。
- debug_startGoTrace：debug的startGoTrace方法将go运行时跟踪数据写入指定文件。
- debug_stopGoTrace:debug的stopGoTrace方法停止Go运行时的跟踪。
- debug_traceBlock:debug的traceBlock方法返回包含在指定区块中的所有交易中的所有 被调用操作码的栈跟踪记录。注意，必须存在该区块的父区块，否则该方法 调用将失败。
- debug_traceBlockByNumber:类似于debug_traceBlock，debug的traceBlockByNumber方法 接受一个区块序号作为参数，并重放已经在数据库中的区块。
- debug_traceBlockByHash:类似于debug_traceBlock方法，debug的traceBlockByHash方法接受一个 区块哈希作为参数，并重放已经在数据库中的区块。
- debug_traceBlockFromFile:类似于debug_traceBlock方法，debug的traceBlockFromFile方法接受一个 RLP编码的区块数据文件作为参数。
- debug_traceTransaction:debug的traceTransaction方法尝试以网络上相同的方式执行交易， 该方法将重放任何可能在此交易之前执行的交易。
- debug_verbosity:debug的debug_Verbosity方法设置日志等级。只有不低于指定等级 的日志才会输出。
- debug_vmodule:debug的vmodule方法设置日志可视模式。
- debug_writeBlockProfile:debug的writeBlockProfile方法将区块性能数据写入指定文件。
- debug_writeMemProfile:debug的writeMemProfile方法将内存性能检测结果写入指定文件。 请注意检测速率不可以利用API设置，而必须在命令行使用--memprofilerate 标志设置。
