package com.self.designmode.composite;

import lombok.Getter;
import lombok.Setter;

/**
 * 组合模式: 顶层抽象类
 * @author PJ_ZHANG
 * @create 2020-07-29 12:36
 **/
@Getter
@Setter
public abstract class OrgComponent {
    private String name;
    private String des;
    public void add(OrgComponent component) { throw new UnsupportedOperationException("不支持添加...");}
    public void delete(OrgComponent component) {throw new UnsupportedOperationException("不支持删除...");}
    abstract void print();
}
