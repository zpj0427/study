package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子_Map算子
 */
object Map {

  def main(args: Array[String]): Unit = {
    // 初始化 sc
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)

    // 构建RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    // 对每一个数字x 2
    val rdd1: RDD[Int] = rdd.map(x => {
      x * 2
    })
    rdd1.collect().foreach(println)

    // 简写
    val rdd2: RDD[Int] = rdd.map(_ * 2)
    rdd2.collect().foreach(println)
  }

}
