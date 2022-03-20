package com.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "peers.nodes")
public class BitostSuiteSetting {
    public int upgrade_height = 0;
    private List<String> nodesUrl = new ArrayList<>();
    private List<String> systemAccount = new ArrayList<>();
    private Boolean swagger_is_enable = false;
    private String websocket_url = "";

    private String tendermint_rpc = "";

    public List<String> getNodesUrl() {
        return nodesUrl;
    }

    public void setNodesUrl(List<String> nodesUrl) {
        this.nodesUrl = nodesUrl;
    }

    public List<String> getSystemAccount() {
        return systemAccount;
    }

    public void setSystemAccount(List<String> systemAccount) {
        this.systemAccount = systemAccount;
    }

    public Boolean getSwagger_is_enable() {
        return swagger_is_enable;
    }

    public void setSwagger_is_enable(Boolean swagger_is_enable) {
        this.swagger_is_enable = swagger_is_enable;
    }

    public int getUpgrade_height() {
        return upgrade_height;
    }

    public void setUpgrade_height(int upgrade_height) {
        this.upgrade_height = upgrade_height;
    }

    public String getTendermint_rpc() {
        return tendermint_rpc;
    }

    public void setTendermint_rpc(String tendermint_rpc) {
        this.tendermint_rpc = tendermint_rpc;
    }

    public String getWebsocket_url() {
        return websocket_url;
    }

    public void setWebsocket_url(String websocket_url) {
        this.websocket_url = websocket_url;
    }
}
