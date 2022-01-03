package com.self.study.spark.sparkcore.transform.simplevalue

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 转换算子: SortBy 排序
 */
object SortBy {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Study_Spark")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[Int] = sc.makeRDD(List(1, 5, 7, 2, 3, 9, 6), 2)
    // 单值顺序排序
    val rdd1: RDD[Int] = rdd.sortBy(x => x)
    rdd1.collect().foreach(print)
    println("-----")
    // 单值逆序排序
    val rdd2: RDD[Int] = rdd.sortBy(x => x, false)
    rdd2.collect().foreach(print)
    println("-----")
    // key-value处理
    val mapRdd: RDD[(String, Int)] = sc.makeRDD(List(("1", 1), ("2", 2), ("11", 11)))
    // 按key进行排序, key是字符串类型, 会按照字典顺序排序
    val mapRdd1: RDD[(String, Int)] = mapRdd.sortBy(tuple => tuple._1)
    mapRdd1.collect().foreach(print)
    println("-----")
    // 按key进行排序, 转为数字进行排序
    val mapRdd2: RDD[(String, Int)] = mapRdd.sortBy(tuple => tuple._1.toInt)
    mapRdd2.collect().foreach(print)
    println("-----")
    // 按value进行排序
    val mapRdd3: RDD[(String, Int)] = mapRdd.sortBy(tuple => tuple._2)
    mapRdd3.collect().foreach(print)
  }

}
