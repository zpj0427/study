package com.self.study.helloworld.myimplicit

/**
 * 隐式方法转换
 */
object ImplicitFunction {

  def main(args: Array[String]): Unit = {
    println(1.max(10))
  }

  implicit def covert(value: Int): Unit = {
    new ImplicitFunction(value)
  }

}

class ImplicitFunction(value: Int) {

  def max(two: Int): Unit = {
    if (value > two) value else two
  }

}
