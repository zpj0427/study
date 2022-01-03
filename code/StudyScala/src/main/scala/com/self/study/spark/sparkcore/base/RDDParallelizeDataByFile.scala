package com.self.study.spark.sparkcore.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 文件_分区分配规则
 */
object RDDParallelizeDataByFile {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    // 多文件分区
    val rdd: RDD[String] = sc.textFile("input/data/", 2)
    rdd.saveAsTextFile("output/" + System.currentTimeMillis())
  }

}
