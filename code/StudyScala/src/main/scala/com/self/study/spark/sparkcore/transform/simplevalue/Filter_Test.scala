package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子_filter
 * 从服务器日志数据 apache.log 中获取 2015 年 5 月 17 日的请求路径
 */
object Filter_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.textFile("input/apache.log")

    // 遍历并过滤数据
    val rdd1: RDD[String] = rdd.filter(line => {
      // 取时间字段, 按时间段进行数据匹配
      val time: String = line.split(" ")(3)
      time.startsWith("17/05/2015")
    })

    rdd1.collect().foreach(println)
  }

}
