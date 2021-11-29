package com.self.study.helloworld.collection.base

/**
 * 不可变Map
 */
object NotChangeMap {

  def main(args: Array[String]): Unit = {
    // 构建集合
    var map1 = Map("a" -> 1, "b" -> 2, "c" -> 3)
    var map2: Map[String, Int] = Map()
    println(map1)
    println(map2)
    println("--------------------")

    // 遍历元素
    map1.foreach(println)
    map1.foreach((kv: (String, Int)) => println(kv))
    println("--------------------")

    // 取所有key或者value
    for (key <- map1.keys) {
      println(s"$key --- ${map1.get(key).get}")
    }
    println("--------------------")

    // 取指定Key
    println("a: " + map1.get("a").get)
    println("b: " + map1.get("b"))
    println("d: " + map1.getOrElse("d", 0))
    println("--------------------")

    // 添加参数
    map1 = map1 + ("e" -> -1)
    println(map1)

    map1 = map1.+("f" -> 99)
    println(map1)
    println("--------------------")

    // 合并集合
    var map3 = Map("g" -> 100)
    map1 = map1 ++ map3
    println(map1)
    println("--------------------")

    // 修改元素
    map1 = map1.updated("a", 999)
    println(map1)
    println("--------------------")

    // 移除元素
    map1 = map1.-("a")
    println(map1)

  }

}
