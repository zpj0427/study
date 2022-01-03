package com.self.study.spark.sparkcore.transform.doublevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 双Value值处理
 */
object DoubleValue {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rddA: RDD[Int] = sc.makeRDD(List(1, 1, 3, 4))
    val rddB: RDD[Int] = sc.makeRDD(List(3, 4, 5, 6))
    // 交集
    val rdd3: RDD[Int] = rddA.intersection(rddB)
    // 并集
    val rdd4: RDD[Int] = rddA.union(rddB)
    // 差集
    val rdd5: RDD[Int] = rddA.subtract(rddB)
    // 拉链
    val rdd6: RDD[(Int, Int)] = rddA.zip(rddB)
    rdd3.collect().foreach(print)
    println()
    rdd4.collect().foreach(print)
    println()
    rdd5.collect().foreach(print)
    println()
    rdd6.collect().foreach(print)
    println()
  }

}
