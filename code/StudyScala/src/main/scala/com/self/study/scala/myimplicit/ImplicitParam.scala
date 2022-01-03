package com.self.study.scala.myimplicit

/**
 * 隐式参数
 */
object ImplicitParam {

  implicit val value: String = "zhangsan"

  def main(args: Array[String]): Unit = {
    println(hello)
    // 隐式参数优先级高于默认参数
    println(helloWithDefault)
  }

  def hello(implicit arg: String): String = {
    arg
  }

  def helloWithDefault(implicit arg: String = "default"): String = {
    arg
  }

}
