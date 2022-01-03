package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: coalesce 缩减分区
 */
object Colease {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 此处默认表示10个分区
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4))
    val rdd1: RDD[Int] = rdd.coalesce(2)
    rdd1.saveAsTextFile("output/" + System.currentTimeMillis())

    // 带shuffle处理
    val rdd2: RDD[Int] = rdd.coalesce(2, true)
    rdd2.saveAsTextFile("output/" + System.currentTimeMillis())
  }

}
