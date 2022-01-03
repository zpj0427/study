package com.self.study.scala.base

import scala.util.control.Breaks

/**
 * 循环语句
 */
object Circle {

  def main(args: Array[String]): Unit = {
    forBreak()
  }

  /**
   * 中断循环
   */
  def forBreak(): Unit = {
    Breaks.breakable(
      for (i <- 1 to 5) {
        println(i)
        if (i == 3) {
          Breaks.break()
        }
      }
    )
    println("结束循环了...")
  }

  /**
   * 倒序打印
   */
  def forReverse(): Unit = {
    for (i <- 1 to 10 reverse) {
      println(i)
    }
  }

  /**
   * 循环返回值, 开发中很少用
   * 将返回结果添加的res中, 并以 Vector 返回
   */
  def forReturn(): Unit = {
    val res = for (i <- 1 to 5) yield {
      i + 1
    }
    println(res)
  }

  /**
   * for表达式带参数
   * 表达式中间用 ; 隔开
   * 如果只有一个表达式用小括号, 多个建议用大括号
   */
  def forParam(): Unit = {
    //    for (i <- 1 to 5; j = i + 1) {
    //      println(s"$i -- $j")
    //    }
    //    for (i <- 1 to 5; j = i + 1; x = j + 1) {
    //      println(s"$i -- $j -- $x")
    //    }
    for {
      i <- 1 to 5;
      j = i + 1;
      x = j + 1
    } {
      println(s"$i -- $j -- $x")
    }

  }

  /**
   * 循环嵌套, 等价于双层for循环
   */
  def forNest(): Unit = {
    for (i <- 1 to 3; j <- 1 to 5) {
      println(s"$i -- $j")
    }
  }

  /**
   * 循环步长, 下一个数 = 当前数 + 步长
   */
  def forBy(): Unit = {
    for (i <- 1 to 10 by 3) {
      println("for by : " + i)
    }
  }

  /**
   * 循环守卫, if条件成立才执行
   */
  def forIf(): Unit = {
    for (i <- 1 to 5 if i % 2 != 0) {
      println("for if: " + i)
    }
  }

  /**
   * FOR UNTIL 语句结构
   * [1, 5)
   */
  def forUntil(): Unit = {
    for (i <- 1 until 5) {
      println("for until : " + i)
    }
  }

  /**
   * FOR TO 循环语句结构
   * [1, 5]
   */
  def forTo(): Unit = {
    for (i <- 1 to 5) {
      println("for to : " + i)
    }
  }

}
