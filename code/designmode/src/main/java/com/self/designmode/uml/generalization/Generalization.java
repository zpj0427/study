package com.self.designmode.uml.generalization;

/**
 * 泛化关系. 继承关系
 * @author PJ_ZHANG
 * @create 2020-07-22 11:09
 **/
// 存在类继承, 即为泛华关系
// 包括普通类继承和抽象类继承
public abstract class Generalization extends Parent {
}

class Parent {}

// 包括接口继承
interface CliendInterface extends ParentInterface {}

interface ParentInterface {}
