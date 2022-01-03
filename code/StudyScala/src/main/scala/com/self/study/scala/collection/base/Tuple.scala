package com.self.study.scala.collection.base

/**
 * 元组
 */
object Tuple {

  def main(args: Array[String]): Unit = {
    // 声明元组的方式
    val tuple = (1, "a", ("b" -> "c", "e" -> "f"))
    println(tuple._1)
    println(tuple._3._1._2)
    println(tuple._3._2._1)

    // 按索引取值
    println(tuple.productElement(0))

    // 遍历
    for (elem <- tuple.productIterator) println(elem)

    // map 作为元祖
    val map1 = Map("a" -> 1, "b" -> 2)
    val map2 = Map(("a" -> 11, "b" -> 12), ("c" -> 12, "d" -> 14))
    map1.foreach(tuple => println(tuple._1 + "   " + tuple._2))
    map2.foreach(tuple => println(tuple._1._1 + "   " + tuple._1._2))
    println("------------------------")
    map2.foreach(tuple => {
      for (ele <- tuple.productIterator) {
        println(ele)
      }
    })
  }

}
