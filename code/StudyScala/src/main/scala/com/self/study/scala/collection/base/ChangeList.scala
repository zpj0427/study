package com.self.study.scala.collection.base

import scala.collection.mutable.ListBuffer

/**
 * 可变集合
 */
object ChangeList {

  def main(args: Array[String]): Unit = {
    // 构建集合
    var list1 = ListBuffer(1, 2, 4, 5)
    val list2 = ListBuffer()
    println(list1)
    println(list2)
    println("--------------------------")

    // 添加数据
    list1.+=(6)
    list1.append(7)
    list1.insert(0, 0)
    println(list1)
    println("--------------------------")

    // 修改数据
    list1(0) = 99
    list1.updated(1, 98)
    println("--------------------------")

    // 删除数据
    list1 = list1.-(99)
    list1.-=(98)
    println(list1)
    println("--------------------------")

    // 遍历数据
    list1.foreach(println)

  }

}
