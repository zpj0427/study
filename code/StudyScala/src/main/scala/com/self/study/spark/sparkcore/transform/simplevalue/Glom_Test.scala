package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子_glom
 * 计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
 */
object Glom_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // 进行分区处理
    // 返回值为Array[Int], 表示把同一个分区的数据作为一个数组返回
    val rdd1: RDD[Array[Int]] = rdd.glom()
    // 取分区内最大值
    val rdd2: RDD[Int] = rdd1.map(_.max)
    // 执行并求和
    println(rdd2.collect().sum)
  }

}
