package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: GroupBy
 */
object GroupBy {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))
    // 以 x % 2 获取的值作为key, 目标值作为value进行分组
    // 进行奇偶数据分组
    val rdd1: RDD[(Int, Iterable[Int])] = rdd.groupBy(x => x % 2)
    rdd1.collect().foreach(println)
  }

}
