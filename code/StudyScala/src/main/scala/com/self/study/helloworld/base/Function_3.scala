package com.self.study.helloworld.base

/**
 * 两个小练习
 */
object Function_3 {

  def main(args: Array[String]): Unit = {
    // test_1
    // 普通调用
    println(test_1(0, "", '0'))
    println(test_1(1, "", '0'))
    // 匿名函数调用
    val test1Func = (i: Int, s: String, c: Char) => if (i == 0 && s == "" && c == '0') false else true
    println(test_1(0, "", '0'))
    println(test_1(1, "", '0'))
    println("------------------------")

    // test_2
    println(test_2(0)("")('0'))
    println(test_2(0)("")('1'))
    println("------------------------")
    println(test_2_1(0)("")('0'))
    println(test_2_1(0)("")('1'))
    println("------------------------")
    println(test_2_2(0)("")('0'))
    println(test_2_2(0)("")('1'))
    // 匿名函数
    val test2Func = (i: Int) => (s: String) => (c: Char) => if (i == 0 && s == "" && c == '0') false else true
    println(test2Func(0)("")('0'))
    println(test2Func(0)("")('1'))
  }

  /**
   * 定义一个函数 func，它接收一个 Int 类型的参数，返回一个函数（记作 f1）。
   * 它返回的函数 f1，接收一个 String 类型的参数，同样返回一个函数（记作 f2）。函数 f2 接
   * 收一个 Char 类型的参数，返回一个 Boolean 的值
   * 要求调用函数 func(0) (“”) (‘0’)得到返回值为 false，其它情况均返回 true。
   *
   * @param i
   * @return
   */
  def test_2(i: Int): String => (Char => Boolean) = {
    def f1(s: String): Char => Boolean = {
      def f2(c: Char): Boolean = {
        if (i == 0 && s == "" && c == '0') false else true
      }

      f2
    }

    f1
  }

  // 简化写法
  def test_2_1(i: Int): String => Char => Boolean = {
    s => c => if (i == 0 && s == "" && c == '0') false else true
  }

  // 函数柯里化
  def test_2_2(i: Int)(s: String)(c: Char): Boolean = {
    if (i == 0 && s == "" && c == '0') false else true
  }

  /**
   * 定义一个匿名函数，并将它作为值赋给变量 fun。函数有三个参数，类型分别为 Int，String，Char，返回值类型为 Boolean。
   * 要求调用函数 fun(0, “”, ‘0’)得到返回值为 false，其它情况均返回 true。
   */
  def test_1(i: Int, s: String, c: Char): Boolean = {
    if (i == 0 && s == "" && c == '0') false else true
  }

}
