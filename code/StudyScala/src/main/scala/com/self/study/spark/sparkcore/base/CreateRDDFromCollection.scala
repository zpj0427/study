package com.self.study.spark.sparkcore.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 创建RDD
 */
object CreateRDDFromCollection {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    // parallelize
    val rdd1: RDD[Int] = sc.parallelize(List(4, 5, 6))
    rdd1.collect().foreach(println)

    // makeRdd方式
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3))
    rdd.collect().foreach(println)
  }

}
