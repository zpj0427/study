package com.self.study.scala.collection.function

import com.alibaba.fastjson.{JSONArray, JSONObject}

import java.util.UUID

object WordCount {

  def main(args: Array[String]): Unit = {
    objectCombine
  }

  def objectCombine(): Unit = {
    val list: List[Map[String, String]] = List(
      Map("row" -> "2", "column" -> "name", "value" -> "zhangsan"),
      Map("row" -> "2", "column" -> "age", "value" -> "13"),
      Map("row" -> "3", "column" -> "name", "value" -> "lisi"),
      Map("row" -> "3", "column" -> "age", "value" -> "14"))
    // 先按row进行分组
    val row2LstData = list.groupBy(x => x("row"))
    println(row2LstData)
    val array = new JSONArray()
    row2LstData.foreach(x => {
      val lstData = x._2
      println(lstData)
      // 取数据组map
      val jsonObject = new JSONObject()
      lstData.foreach(x => jsonObject.put(x.get("column").get, x.get("value").get))
      println(jsonObject)
      jsonObject.put("row", x._1)
      jsonObject.put("uuid", UUID.randomUUID().toString)
      array.add(jsonObject)
      println(jsonObject)
    })
    println(array.toString)
  }

  /**
   * wordCount_2 方案优化
   */
  def wordCount_3(): Unit = {
    val list = List(("hello world", 1), ("hello scala", 2), ("hello spark", 3), ("scala for spark", 4))
    // 上一步已经知道了单次出现数量, 进行统计
    // 这一步会转成List[Array[String, Int]] 形式
    // String表示单个单次, Int表示出现次数
    val list1 = list.map(x => {
      val array = x._1.split(" ")
      array.map(y => (y, x._2))
    })
    // List((hello,1), (world,1), (hello,2), (scala,2), (hello,3), (spark,3), (scala,4), (for,4), (spark,4))
    val list2 = list1.flatten
    // 上一步可以直接写为: val list2 = list.flatmap(..)

    // 按key进行分组
    val list3 = list2.groupBy(x => x._1)

    // 统计数量
    val list4 = list3.map(x => {
      val array = x._2
      val sum = array.map(x => x._2).sum
      (x._1, sum)
    }).toList
    // 进行排序
    val list5 = list4.sortBy(x => x._2)(Ordering[Int].reverse)

    // 取前三个
    val list6 = list5.take(3)
    println(list6)
  }

  /**
   * 已知每一个字符串出现数量, 进行计算
   */
  def wordCount_2(): Unit = {
    val list = List(("hello world", 1), ("hello scala", 2), ("hello spark", 3), ("scala for spark", 4))
    // 先转字符串
    val list1 = list.map(x => (x._1 + " ") * x._2)
    println(list1)
    // 转完之后, 直接调用第一种方式即可
    wordCount_1(list1)
  }

  /**
   * 单次计数: 将集合中出现的相同的单词，进行计数，取计数排名前三的结果
   */
  def wordCount_1(value: List[String]): Unit = {
    val list = if (value.isEmpty) List("hello world", "hello scala", "hello spark", "scala for spark") else value
    // 先拆词
    val list1 = list.map(x => x.split(" "))
    println(list1)
    // 拆完后合并
    val list2 = list1.flatten
    println(list2)
    // 合并后分组
    val map = list2.groupBy(x => x)
    println(map)
    // 分组后转换数量
    val map1 = map.map((kv) => (kv._1, kv._2.size))
    println(map1)
    // 排序, 按value倒序排列
    val list3 = map1.toList.sortBy(x => x._2)(Ordering[Int].reverse)
    println(list3)
    // 取前三个
    val list4 = list3.take(3)
    println(list4)
    // 只取名字
    val listName = list4.map(x => x._1)
    println(listName)
  }

}
