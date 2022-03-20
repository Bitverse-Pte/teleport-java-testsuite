//package com.service.erc20;
//
//public class BlockFilterService {
//
//
//    private static final int COUNT = 10;
//
//    private static Logger log = LoggerFactory.getLogger(Main.class);
//
//    private final Web3j web3j;
//
//    public Main() {
//        web3j = Web3j.build(new HttpService());  // defaults to http://172.17.0.1:8545/
//    }
//
//    private void run() throws Exception {
//        log.info("Doing simpleFilterExample");
//        simpleFilterExample();
//
//        log.info("Doing blockInfoExample");
//        blockInfoExample();
//
//        log.info("Doing countingEtherExample");
//        countingEtherExample();
//
//        log.info("Doing clientVersionExample");
//        clientVersionExample();
//
//        System.exit(0);  // we explicitly call the exit to clean up our ScheduledThreadPoolExecutor used by web3j
//    }
//
//    public static void main(String[] args) throws Exception {
//        new Main().run();
//    }
//
//    void simpleFilterExample() throws Exception {
//        Disposable subscription = web3j.blockFlowable(false).subscribe(block -> {
//            log.info("Sweet, block number " + block.getBlock().getNumber()
//                    + " has just been created");
//        }, Throwable::printStackTrace);
//
//        TimeUnit.MINUTES.sleep(2);
//        subscription.dispose();
//    }
//
//    void blockInfoExample() throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
//
//        log.info("Waiting for " + COUNT + " transactions...");
//        Disposable subscription = web3j.blockFlowable(true)
//                .take(COUNT)
//                .subscribe(ethBlock -> {
//                    EthBlock.Block block = ethBlock.getBlock();
//                    LocalDateTime timestamp = Instant.ofEpochSecond(
//                            block.getTimestamp()
//                                    .longValueExact()).atZone(ZoneId.of("UTC")).toLocalDateTime();
//                    int transactionCount = block.getTransactions().size();
//                    String hash = block.getHash();
//                    String parentHash = block.getParentHash();
//
//                    log.info(
//                            timestamp + " " +
//                                    "Tx count: " + transactionCount + ", " +
//                                    "Hash: " + hash + ", " +
//                                    "Parent hash: " + parentHash
//                    );
//                    countDownLatch.countDown();
//                }, Throwable::printStackTrace);
//
//        countDownLatch.await(10, TimeUnit.MINUTES);
//        subscription.dispose();
//    }
//
//    void countingEtherExample() throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        log.info("Waiting for " + COUNT + " transactions...");
//        Single<BigInteger> transactionValue = web3j.transactionFlowable()
//                .take(COUNT)
//                .map(Transaction::getValue)
//                .reduce(BigInteger.ZERO, BigInteger::add);
//
//        Disposable subscription = transactionValue.subscribe(total -> {
//            BigDecimal value = new BigDecimal(total);
//            log.info("Transaction value: " +
//                    Convert.fromWei(value, Convert.Unit.ETHER) + " Ether (" +  value + " Wei)");
//            countDownLatch.countDown();
//        }, Throwable::printStackTrace);
//
//        countDownLatch.await(10, TimeUnit.MINUTES);
//        subscription.dispose();
//    }
//
//    void clientVersionExample() throws Exception {
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//
//        Disposable subscription = web3j.web3ClientVersion().flowable().subscribe(x -> {
//            log.info("Client is running version: {}", x.getWeb3ClientVersion());
//            countDownLatch.countDown();
//        });
//
//        countDownLatch.await();
//        subscription.dispose();
//    }
//}
