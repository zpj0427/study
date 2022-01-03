package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转化算子_flatMap
 * 小练习: 将 List(List(1,2),3,List(4,5))进行扁平化操作
 */
object FlatMap_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(List(1, 2), 3, List(4, 5)))

    val rdd1 = rdd.flatMap(ele => {
      // 通过模式匹配进行实现
      ele match {
        case list: List[_] => list
        case data => List(data)
      }
    })
    rdd1.collect().foreach(println)
  }

}
