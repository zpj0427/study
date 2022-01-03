package com.self.study.scala.base

import scala.io.StdIn

object HelloWorld {

  def main(args: Array[String]): Unit = {

  }

  def equals(): Unit = {

    val s1 = "123"
    val s2 = new String("123");
    val s3 = "123"
    println(s1 == s2)
    println(s1.eq(s2))
    println(s1 == s3)
    println(s1.eq(s3))
  }

  // 键盘输入
  def stdInput(): Unit = {
    println("输入名字: ")
    val name = StdIn.readLine();
    println("输入年龄: ")
    val age = StdIn.readInt();
    println(s"name=$name, age=$age")
  }

  // 字符串格式化
  def stripMarginTest(): Unit = {
    var age: Int = 10
    var name = "zhangsan"
    // 字符串格式化拼接
    var sql: String =
      s"""
         | select
         | name
         | where age = ${age + 2}

        | and name = $name
      """.stripMargin
    println(sql)
    // 字符串拼接
    var sql1 = s"name=$name, age=$age"
    println(sql1)
  }

  // 变量常量赋值
  def variableTest(): Unit = {
    // 变量
    var a = 10
    // 常量
    val b = 10
    println(a)
    println(b)
    // 修改变量
    a = 11
    // 无法修改常量
    // b = 11
    println(a)
  }

}
