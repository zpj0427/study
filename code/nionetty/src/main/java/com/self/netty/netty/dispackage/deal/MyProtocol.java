package com.self.netty.netty.dispackage.deal;

import lombok.Data;

/**
 * @author pj_zhang
 * @create 2019-12-28 13:44
 **/
@Data
public class MyProtocol {

    /**
     * 消息长度
     */
    private int length;

    /**
     * 消息内容
     */
    private String content;

}
