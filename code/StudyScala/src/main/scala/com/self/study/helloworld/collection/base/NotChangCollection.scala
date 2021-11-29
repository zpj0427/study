package com.self.study.helloworld.collection.base

/**
 * 不可变数组
 */
object NotChangArray {

  def main(args: Array[String]): Unit = {
    // 创建一个可变数组
    val array = new Array[Int](10)
    val array2 = Array(1, 2, 3, 4, 5)
    println(array.mkString(", "))
    println(array2.mkString(", "))
    println("------------------------")
    // 给数组添加数据
    // 因为是不可变数组, 添加后会生成一个新的数组
    val arr2 = array2 .:+ (6)
    println(arr2.mkString(", "))

    // 简写
    val arr3 = 7 +: arr2 :+ 8
    println(arr3.mkString(", "))

    // 修改
    arr3(0) = 0
    println(arr3.mkString(", "))
    println("------------------------")

    // 遍历
    for (x <- arr3) print(x + ", ")
    println()

    for (i <- 0 until arr3.length) print(arr3(i) + ", ")
    println()

    for (i <- arr3.indices) print(arr3(i) + ", ")
    println()

    arr3.foreach(x => print(x + ", "))
    println()

    arr3.foreach(print(_))
    println()

    // 不可变数组转可变数据
    val arr4 = arr3.toBuffer
    println(arr4)
  }

}
