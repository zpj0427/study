package com.self.study.helloworld.oo.construct

/**
 * 枚举类
 */
object Enums {

  def main(args: Array[String]): Unit = {
    println(Color.BLACK.id)
    println(Ball.FOOT.toString)
  }

}

object Ball extends Enumeration {
  val FOOT = Value("足球")
}

object Color extends Enumeration {
  val RED = Value(1, "红色")
  val BLACK = Value(2, "黑色")
}