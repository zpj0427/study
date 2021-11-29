package com.self.study.helloworld.collection.base

import scala.collection.mutable

/**
 * 可变Set
 */
object ChangeSet {

  def main(args: Array[String]): Unit = {
    // 构建集合
    var set1 = mutable.Set(1, 2, 3, 4)
    var set2: mutable.Set[Int] = mutable.Set()
    println(set1)
    println(set2)
    println("--------------------")

    // 添加元素
    var set3 = set1 + 5
    println(set1)
    set1 += 98
    println(set1)
    val flag = set1.add(99)
    println(flag)
    println(set1)
    println(set3)
    println("--------------------")

    // 删除元素
    set1 -= 99
    println(set1)
    set1.remove(98)
    println(set1)
    println("--------------------")

    // 合并2个set
    var set4: mutable.Set[Int] = set1 ++ set2
    var set5 = mutable.Set[Int](-1, -2)
    set4 ++= set5
    println(set4)
    println(set4)

  }

}
