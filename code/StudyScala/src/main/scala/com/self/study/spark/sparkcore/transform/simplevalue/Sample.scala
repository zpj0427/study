package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: 抽取数据
 */
object Sample {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8))
    // 抽取不放回
    // 第一个参数: 表示抽取不放回
    // 第二个参数: 表示数据被抽取的概率, 范围在[0, 1]之间, 0表示全不取, 1表示全取
    // 第三个参数: 随机数种子, 不填以当前时间为标准
    val rdd1: RDD[Int] = rdd.sample(false, 0.1)

    // 抽取放回
    // 第一个参数: 表示抽取并放回
    // 第二个参数: 表示每个数据期望被抽到的次数
    val rdd2: RDD[Int] = rdd.sample(true, 2)
    rdd1.collect().foreach(print)
    println()
    rdd2.collect().foreach(print)
  }

}
