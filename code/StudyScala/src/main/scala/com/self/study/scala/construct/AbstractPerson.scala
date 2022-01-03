package com.self.study.scala.construct

abstract  class AbstractPerson {

  val name: String

}

object AbstractPerson {

  def main(args: Array[String]): Unit = {
    var person = new AbstractPerson {
      override val name: String = "zhangsan"
    }
  }

}
