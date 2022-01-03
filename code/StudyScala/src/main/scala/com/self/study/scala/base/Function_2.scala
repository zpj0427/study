package com.self.study.scala.base

/**
 * 匿名函数
 */
object Function_2 {

  def main(args: Array[String]): Unit = {
    manyParam()
  }

  // 多个参数调用
  def manyParam(): Unit = {
    // 普通函数调用
    var result = manyOperate(2, 4, manyExecute)
    println(result)

    // 匿名函数调用
    result = manyOperate(2, 4, (x: Int, y: Int) => {
      x * y
    })
    println(result)

    // 单行代码可以省略花括号, 类型可省略
    result = manyOperate(2, 4, (x, y) => x * y)
    println(result)

    // 参数只出现一次, 可用_代替
    result = manyOperate(2, 4, _ * _)
    println(result)
  }

  def manyOperate(one: Int, two: Int, manyExecute: (Int, Int) => Int): Int = {
    manyExecute(one, two)
  }

  def manyExecute(one: Int, two: Int) = one * two

  // 单个参数调用
  def singleParam(): Unit = {
    // 标准函数调用
    var result = operate(Array(1, 2, 3, 4, 5), execute)
    println(result.mkString("_"))

    // 匿名函数调用, 将 execute 函数匿名化
    result = operate(Array(1, 2, 3, 4, 5), (ele: Int) => {
      ele * 2
    })
    println(result.mkString("_"))

    // 类型可省略, 单个参数可省略括号, 单行方法体可省略大括号
    result = operate(Array(1, 2, 3, 4, 5), ele => ele * 2)
    println(result.mkString("_"))

    // 如果参数只出现一次, 则省略参数后可以用_代替
    result = operate(Array(1, 2, 3, 4, 5), _ * 2)
    println(result.mkString("_"))
  }

  def operate(arr: Array[Int], execute: Int => Int): Array[Int] = {
    for (ele <- arr) yield execute(ele)
  }

  def execute(ele: Int): Int = ele * 2

}
