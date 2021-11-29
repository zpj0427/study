package com.self.study.helloworld.collection.base

/**
 * 不可变Set
 */
object NotChangeSet {

  def main(args: Array[String]): Unit = {
    // 构建集合
    var set1: Set[Int] = Set()
    var set2 = Set(1, 2, 3, 4)

    // 添加元素
    set1 = set1 + 1
    set2 = set2.+(1, 2)
    println(set1)
    println(set2)
    println("-------------------")

    // 删除元素
    val set4 = set2 - 4
    println(set4)
    println("-------------------")

    // 合并set
    val set3 = set1 ++ set2
    println(set3)
  }

}
