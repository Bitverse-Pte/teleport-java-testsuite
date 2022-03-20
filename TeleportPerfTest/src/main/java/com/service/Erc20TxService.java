package com.service;

import com.initial.InitContractService;
import com.pool.AccountsPool;
import com.pool.DeployedErc20Pool;
import com.pool.Web3jConnPool;
import com.utils.ColdWallet;
import okhttp3.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Service
public class Erc20TxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Erc20TxService.class);

    @Autowired
    private InitContractService initContractService;

    @Autowired
    private ChainService chainService;

    public BigInteger queryBalance(String address) throws Exception {
        try {
            System.out.println(DeployedErc20Pool.getInstance().getErc20test().getContractAddress());
            System.out.println(address);
            return DeployedErc20Pool.getInstance().getErc20test().
                    balanceOf(address).send();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String transferValue(int peerId, int accountId) throws IOException, InterruptedException {
        String txData = DeployedErc20Pool.getInstance().getErc20test().transfer(
                AccountsPool.getInstance().getReceiver()
                ,new BigInteger("1")).encodeFunctionCall();

        String txHash = ColdWallet.sendRawTxs(peerId, initContractService.erc20Address, 0,
                AccountsPool.getInstance().getColdWalletList().get(accountId),true,txData);

        if (txHash == null) {
            LOGGER.error("null txHash failed");
            System.exit(0);
        }

        //if we don't get the receipt in 30000s, we will exit the service.
        EthGetTransactionReceipt receipt = chainService.getReceiptByHash(txHash);
        return receipt.getTransactionReceipt().toString();
    }

    public void transferBatch(int peerId, int accountId, int batchNumber)throws IOException, InterruptedException {
        for (int i=0; i < batchNumber; i++){
            String txData = DeployedErc20Pool.getInstance().getErc20test().transfer(
                    AccountsPool.getInstance().getReceiver()
                    ,new BigInteger("1")).encodeFunctionCall();

            //will meet error "tx existed in mempool"
            String txHash = ColdWallet.sendRawTxs(peerId, initContractService.erc20Address, 0,
                    AccountsPool.getInstance().getColdWalletList().get(accountId),true,txData);
        }
    }

    public BigInteger queryReceiverBalance(int accountId) throws Exception {
        try {
            return DeployedErc20Pool.getInstance().getErc20test().
                    balanceOf(AccountsPool.getInstance().getAddressList().get(accountId)).send();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public String transferByHardhat(int peerId) throws IOException, InterruptedException {
        String txData = DeployedErc20Pool.getInstance().getErc20test().transfer(
                AccountsPool.getInstance().getReceiver()
                ,new BigInteger("1")).encodeFunctionCall();

        String txHash = ColdWallet.sendRawTxs(peerId, initContractService.erc20Address,
            0, AccountsPool.getInstance().getValColdwallet(), false,
            txData);

        if (txHash == null) {
            LOGGER.error("null txHash failed");
            System.exit(0);
        }

        //if we don't get the receipt in 30000s, we will exit the service.
        EthGetTransactionReceipt receipt = chainService.getReceiptByHash(txHash);
        return receipt.getTransactionReceipt().toString();
    }


    public String constructTxData(String toAddress, float value, Credentials coldwallet,
                                  Boolean defaultGasPrice,String data,BigInteger nonce){

        BigInteger GAS_PRICE = BigInteger.valueOf(0);
        if (defaultGasPrice) {
            GAS_PRICE = BigInteger.valueOf(22000000000L);
        }
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

        //创建交易
        BigInteger ethValue = Convert.toWei(String.valueOf(value), Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, toAddress, ethValue,data);

        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, coldwallet);
        String hexValue = Numeric.toHexString(signedMessage);

        return hexValue;
    }


}
