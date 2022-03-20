package com.initial;

import com.db.LevelDBUtils;
import com.pool.AccountsPool;
import com.pool.AccountsStr;
import com.setting.BitostSuiteSetting;
import com.pool.Web3jConnPool;
import com.utils.ColdWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class InitServerService {
    private String monitorAddress;

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    @PostConstruct
    public void init() throws Exception {
        initAccounts();
        initNodeConn();
    }

    private void initAccounts() throws CipherException, IOException {
        AccountsPool.getInstance().setValColdwallet(ColdWallet.loadBitosColdwallet());

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_0);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_0));

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_1);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_1));

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_2);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_2));

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_3);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_3));

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_4);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_4));

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_5);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_5));

        AccountsPool.getInstance().getAddressList().add(AccountsStr.account_6);
        AccountsPool.getInstance().getColdWalletList().add(ColdWallet.loadColdwalletBykeystore(AccountsStr.cre_accounts_6));
    }

    private void initNodeConn(){
        for (int i = 0;i<bitostSuiteSetting.getNodesUrl().size();i++){
            System.out.println(bitostSuiteSetting.getNodesUrl().get(i));
        }

        for (int i = 0; i < bitostSuiteSetting.getNodesUrl().size(); i++) {
            Web3jConnPool.getInstance()
                    .getConnClient().getWeb3jList().
                    add(Web3jConnPool.
                            getWeb3jConnection(bitostSuiteSetting
                                    .getNodesUrl().get(i)));

            Web3jConnPool.getInstance()
                    .getConnClient().getAdminList()
                    .add(Admin.
                            build(new HttpService(bitostSuiteSetting.getNodesUrl().get(i))));
        }
    }

}
