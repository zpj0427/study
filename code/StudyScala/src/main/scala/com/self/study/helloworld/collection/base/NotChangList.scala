package com.self.study.helloworld.collection.base

/**
 * 不可变List集合
 */
object NotChangList {

  def main(args: Array[String]): Unit = {
    // 构建集合
    val list1: List[Int] = List()
    val list2 = List(1, 2, 3, 4, 5)
    val list3 = 1 :: 2 :: 3 :: 4 :: 5 :: Nil
    println(list1)
    println(list2)
    println(list3)
    println("--------------------------------")

    // 集合添加元素
    val list4 = 1 :: 2 :: 3 :: list1
    val list5 = -1 :: list4.+:(0).:+(4).:+(5)
    println(list4)
    println(list5)
    println("--------------------------------")

    // 合并集合
    val list6 = List(10, 11)
    val list7 = list4 ::: list6
    println(list7)
    println("--------------------------------")

    // 取指定数据
    println(list7(0))
    println("--------------------------------")

    // 修改指定数据
    val list8 = list7.updated(0, 99)
    println(list8)
    println("--------------------------------")

    // 遍历
    list7.foreach(println)

  }

}
