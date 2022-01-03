package com.self.study.spark.sparkcore.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 集合_分区数据分配
 */
object RDDParallelizeDataByCollection {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7), 3)
    rdd.saveAsTextFile("output/" + System.currentTimeMillis())
  }
}
