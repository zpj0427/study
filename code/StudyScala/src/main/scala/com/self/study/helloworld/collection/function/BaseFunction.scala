package com.self.study.helloworld.collection.function

/**
 * 集合方法
 */
object BaseFunction {

  def main(args: Array[String]): Unit = {
    var list = List(1, 2, 3, 4, 5, 6, 7, 8)
    // 获取头
    println(list.head)

    // 获取尾, 除过头都是尾
    println(list.tail)

    // 获取最后一个
    println(list.last)

    // 集合初始化数据,(不包括最后一个)
    println(list.init)

    // 反转
    println(list.reverse)

    // 取前(后) N个元素
    println(list.take(3))
    println(list.takeRight(3))

    // 去掉前(后) N个元素
    println(list.drop(1))
    println(list.dropRight(1))

    val list2 = List(2, 3, 4, 10, 11, 12)
    // 并集
    println(list.union(list2))

    // 交集
    println(list.intersect(list2))

    // 差集
    println(list.diff(list2))
    println(list2.diff(list))

    // 拉链
    println(list.zip(list2))

    // 滑窗
    list.sliding(2).foreach(println)
  }

}
