package com.initial;

import com.contract.erc20.BitosErc20test;
import com.pool.AccountsPool;
import com.pool.DeployedErc20Pool;
import com.pool.Web3jConnPool;
import org.springframework.stereotype.Service;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;

@Service
public class InitContractService {

    public String erc20Address;

    public void deployContract(int peerId, int accountId) throws Exception {
        DeployedErc20Pool.getInstance().setErc20test(BitosErc20test.
                deploy(Web3jConnPool.getInstance().getConnClient().getWeb3jList().get(peerId), AccountsPool.getInstance().getColdWalletList().get(accountId),
                ManagedTransaction.GAS_PRICE.multiply(new BigInteger("1")),
                Contract.GAS_LIMIT).send());

        this.erc20Address = DeployedErc20Pool.getInstance().getErc20test().getContractAddress();
    }

    public void loadContract(int peerId, int accountId, String contractAddrss) throws Exception{
        DeployedErc20Pool.getInstance().setErc20test(BitosErc20test.load(
                contractAddrss,Web3jConnPool.getInstance().getConnClient().getWeb3jList().get(peerId),
                AccountsPool.getInstance().getColdWalletList().get(accountId),ManagedTransaction.GAS_PRICE.multiply(new BigInteger("1")),
                Contract.GAS_LIMIT
        ));
        this.erc20Address = contractAddrss;
    }

}
