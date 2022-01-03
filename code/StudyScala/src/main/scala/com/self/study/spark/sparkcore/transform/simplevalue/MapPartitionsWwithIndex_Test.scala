package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: mapPartitionsWithIndex
 */
object MapPartitionsWwithIndex_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val rdd1: RDD[Int] = rdd.mapPartitionsWithIndex((index, iter) => {
      // 第二个分区, 索引为1, 直接返回数据
      if (index == 1) {
        iter
      } else {
        // 空迭代器
        // Nil表示空集合
        Nil.iterator
      }
    })

    rdd1.collect()
  }

}
