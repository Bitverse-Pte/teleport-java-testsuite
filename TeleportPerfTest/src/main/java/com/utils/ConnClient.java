package com.utils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;

import java.util.ArrayList;
import java.util.List;

public class ConnClient {
    private List<Web3j> web3jList = new ArrayList<Web3j>();
    private List<Admin> adminList = new ArrayList<Admin>();

    private List<PersonalUnlockAccount> unlockAccountList = new ArrayList<PersonalUnlockAccount>();

    public List<Web3j> getWeb3jList() {
        return web3jList;
    }

    public void setWeb3jList(List<Web3j> web3jList) {
        this.web3jList = web3jList;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }

    public List<PersonalUnlockAccount> getUnlockAccountList() {
        return unlockAccountList;
    }

    public void setUnlockAccountList(List<PersonalUnlockAccount> unlockAccountList) {
        this.unlockAccountList = unlockAccountList;
    }

}
