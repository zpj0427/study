package com.self.study.helloworld.collection.base

import scala.collection.mutable.ArrayBuffer

/**
 * 可变数组
 */
object ChangeArray {

  def main(args: Array[String]): Unit = {
    // 创建数组
    val arr = new ArrayBuffer[Int]()
    var arr1 = ArrayBuffer(1, 2, 3, 4, 5)
    println(arr)
    println(arr1)

    // 添加元素
    arr.append(1, 2, 3)
    arr.+=(4, 5, 6)
    arr.insert(0, -1, -2, -3)
    println(arr)

    // 修改元素
    arr(0) = 99
    println(arr)

    // 遍历元素
    arr.foreach(x => print(x + ", "))
    println()

    // 转不可变数组
    val arr3 = arr.toArray
    println(arr3.mkString(", "))

  }

}
