package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: mapPartitions
 */
object MapPartitions {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    // 构建RDD
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // mapPartitions 以分区为单位进行数据处理, iter表示该分区下的所有数据, 是一个迭代器
    val rdd1: RDD[Int] = rdd.mapPartitions(iter => {
      // 对该分区数据再进行转换处理, 并返回结果
      iter.map(_ * 2)
    })

    rdd1.collect().foreach(println)
  }

}
