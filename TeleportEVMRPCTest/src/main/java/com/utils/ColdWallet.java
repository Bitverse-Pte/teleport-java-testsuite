package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pool.AccountsStr;
import com.pool.Web3jConnPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class ColdWallet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColdWallet.class);

    public static Credentials loadBitosColdwallet() throws CipherException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = AccountsStr.cre_bitos_account;
        WalletFile walletFile = objectMapper.readValue(json, WalletFile.class);
        Credentials coldwallet = Credentials.create(Wallet.decrypt(AccountsStr.cre_pwd, walletFile));
        return coldwallet;
    }


    public static Credentials loadColdwalletBykeystore(String cre) throws CipherException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = cre;
        WalletFile walletFile = objectMapper.readValue(json, WalletFile.class);
        Credentials coldwallet = Credentials.create(Wallet.decrypt(AccountsStr.cre_pwd, walletFile));
        return coldwallet;
    }

    public static String sendRawTxWithoutData(String toAddress, float value,
                                              Credentials coldwallet, Boolean defaultGasPrice){
        return sendRawTxs(0,toAddress,value,coldwallet,defaultGasPrice,"");
    }


    public static String sendRawTxs(int peerID,String toAddress, float value, Credentials coldwallet,
                                    Boolean defaultGasPrice,String data) {

        Web3j web3j = Web3jConnPool.getInstance()
                .getConnClient().getWeb3jList().get(peerID);
        String hexValue = constructTxData(peerID,toAddress,value,coldwallet,defaultGasPrice,data);

        //发送交易
        EthSendTransaction ethSendTransaction = null;
        try {
            ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String transactionHash = ethSendTransaction.getTransactionHash();
        if (ethSendTransaction.hasError()) {
            LOGGER.info(ethSendTransaction.getError().getMessage());
            return "failure";
        }
        return transactionHash;
    }


    public static String constructTxData(int peerID,String toAddress, float value, Credentials coldwallet,
                                  Boolean defaultGasPrice,String data){
        //设置需要的矿工费
        BigInteger GAS_PRICE = BigInteger.valueOf(0);
        if (defaultGasPrice) {
            GAS_PRICE = BigInteger.valueOf(22000000000L);
        }
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

        Web3j web3j = Web3jConnPool.getInstance()
                .getConnClient().getWeb3jList().get(peerID);
        //转账人账户地址
        String ownAddress = coldwallet.getAddress();
        //getNonce
        EthGetTransactionCount ethGetTransactionCount = null;
        try {
            ethGetTransactionCount = web3j.ethGetTransactionCount(
                    ownAddress, DefaultBlockParameterName.PENDING).sendAsync().get();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return "failure";
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
            return "failure";
        }
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

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
