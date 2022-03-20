package com.service.endpoints;

import com.service.ReqConst;
import com.setting.BitostSuiteSetting;
import com.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class R10PersonalService {

    @Autowired
    private BitostSuiteSetting bitostSuiteSetting;

    public String personal_listAccounts() throws IOException {
        String result = HttpUtils.postSend(bitostSuiteSetting.getNodesUrl().get(0), ReqConst.personalListAccReq);
        return result;
    }
}
