package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转化算子_flatMap
 */
object FlatMap {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.makeRDD(List("Hello World", "Hello Java"))

    // 外层对内存返回的可迭代集合再进行扁平化处理
    val rdd1: RDD[String] = rdd.flatMap(str => {
      // 内层返回一个可迭代的集合
      str.split(" ")
    })

    rdd1.collect().foreach(println)
  }

}
