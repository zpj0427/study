package com.self.designmode.uml.dependency;

/**
 * 依赖关系
 * @author PJ_ZHANG
 * @create 2020-07-22 11:01
 **/
public class Dependency {

    // 成员变量可以作为类依赖关系
    private MemberParam memberParam;

    // 返回值可以作为类依赖关系
    public ReturnData getData() { return null;}

    // 传参可以作为类依赖关系
    public void param(ParamData paramData) {}

    // 局部变量可以作为类依赖关系
    public void local() { LocalData localData = new LocalData(); }

}

class ReturnData {}

class ParamData {}

class MemberParam {}

class LocalData {}
