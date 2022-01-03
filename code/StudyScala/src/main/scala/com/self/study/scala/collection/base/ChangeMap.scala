package com.self.study.scala.collection.base

import scala.collection.mutable

/**
 * 可变Map
 */
object ChangeMap {

  def main(args: Array[String]): Unit = {

    //（1）创建可变集合
    val map = mutable.Map("a" -> 1, "b" -> 2, "c" -> 3)

    //（3）向集合增加数据
    map.+=("d" -> 4)
    map.+=(("f", 98), ("g", 99))
    // 将数值 4 添加到集合，并把集合中原值 1 返回
    val maybeInt = map.put("a", 4)
    println(maybeInt.getOrElse(0))
    println("------------------------")

    //（4）删除数据
    map.-=("b", "c")
    map.remove("f")

    //（5）修改数据
    map.update("d", 5)
    map("d") = 5

    // 合并map
    val map7 = mutable.Map("A" -> 1, "B" -> 2)
    val map8 = map ++ map7
    map ++= map7
    println(map8)
    println("------------------------")
    println(map)
    println("------------------------")

    //（2）打印集合
    map.foreach((kv) => {
      println(kv)
    })
  }

}
