package com.self.designmode.chain;

/**
 * 责任链模式: 处理请求, 即需要处理的时间
 * @author PJ_ZHANG
 * @create 2020-12-18 11:34
 **/
public class Request {

    /**
     * 根据不同的价格, 选择不同的处理器进行处理
     */
    private Integer price;

    public Request(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
