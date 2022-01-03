package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: mapPartitions
 * 小练习: 获取每个分区的最大值
 */
object MapPartitions_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 构建RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val rdd1: RDD[Int] = rdd.mapPartitions(iter => {
      // 取最大值
      val max: Int = iter.max
      // 转迭代器
      List(max).toIterator
    })

    rdd1.collect().foreach(println)
  }

}
