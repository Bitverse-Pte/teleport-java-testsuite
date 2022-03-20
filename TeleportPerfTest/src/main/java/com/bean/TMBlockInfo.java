package com.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TMBlockInfo {
    public String jsonrpc;

    public int id;

    public BaseResponse result = new BaseResponse();

    @Data
    public class BaseResponse {
        public String last_height;

        public List<BlockMetas> block_metas = new ArrayList<BlockMetas>();

        @Data
        public class BlockMetas{
            public String num_txs;
        }
    }

}
