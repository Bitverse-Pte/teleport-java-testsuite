package com.pool;

import com.utils.ColdWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.List;

public class AccountsPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsPool.class);

    private volatile static AccountsPool instance = null;

    private Credentials valColdwallet = null;
    private List<String> addressList = new ArrayList<String>();
    //default account password is "123"
    private List<Credentials> coldWalletList = new ArrayList<Credentials>();

    private String receiver = "0xb3d49259b486d04505b0b652ade74849c0b703c4";

    public List<Credentials> getColdWalletList() {
        return coldWalletList;
    }

    public void setColdWalletList(List<Credentials> coldWalletList) {
        this.coldWalletList = coldWalletList;
    }

    public Credentials getValColdwallet() {
        return valColdwallet;
    }

    public void setValColdwallet(Credentials valColdwallet) {
        this.valColdwallet = valColdwallet;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    public static AccountsPool getInstance() {
        return AccountPoolHolder.instance;
    }

    public static void setInstance(AccountsPool instance) {
        AccountsPool.instance = instance;
    }

    private static class AccountPoolHolder {
        private static AccountsPool instance;

        static {
            try {
                instance = new AccountsPool();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
