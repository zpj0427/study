package com.self.study.helloworld.collection.function

/**
 * 计算函数
 */
object CalculateFunction {

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5, 6, -1, 0)
    // 和
    println(list.sum)

    // 乘积
    println(list.product)

    // 最大值
    println(list.max)

    // 最小值
    println(list.min)

    // 排序
    println(list.sorted)
    println(list.sorted.reverse)

    println(list.sortBy(x => x))
    println(list.sortBy(x => x.abs))

    println(list.sorted(Ordering[Int]))
    println(list.sorted(Ordering[Int].reverse))

    val list2 = List("a" -> 1, "b" -> 3, "c" -> 2)
    println(list2.sorted)
    println(list2.sortBy(x => x._2)(Ordering[Int].reverse))
    println(list2.sortBy(_._2))

    println(list.sortWith((a, b) => a > b))
    println(list.sortWith((a, b) => a < b))

    println(list2.sortWith((a, b) => a._2 > b._2))

  }

}
