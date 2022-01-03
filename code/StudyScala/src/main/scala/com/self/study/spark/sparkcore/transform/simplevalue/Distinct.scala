package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: distinct 去重
 */
object Distinct {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4))

    // Scala: 去重通过把List转为Set实现
    // Spark: 去重通过reduceByKey()实现
    val rdd1: RDD[Int] = rdd.distinct()
    rdd1.collect().foreach(print)
  }

}
