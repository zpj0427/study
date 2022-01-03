package com.self.study.spark.sparkcore.base

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 创建RDD
 */
object CreateRDDFromFile {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Create RDD")
    val sc: SparkContext = new SparkContext(sparkConf)

    // 如果指定到具体文件, 则会读取指定文件
    val rdd: RDD[String] = sc.textFile("input/rddfile")
    rdd.collect().foreach(println)
    println("============================")
    // 如果只是指定到了文件夹, 则会读取文件夹下所有文件
    val rdd1: RDD[String] = sc.textFile("input")
    rdd1.collect().foreach(println)
    println("============================")
    // 文件读取可以使用通配符
    val rdd2: RDD[String] = sc.textFile("input/w*.txt")
    rdd2.collect().foreach(println)
    println("============================")
    // 可以访问远程路径
//    sc.textFile("hdfs://xxxxx")
//    rdd2.collect().foreach(println)
    // 上面无论从文件夹还是通配符, 都是只拿到了文件内容, 并没有拿到具体文件, 如果需要知道具体文件,
    // 输出内容为: (文件绝对路径, 文件中的每一行数据)
    // 注意: 一个文件, 文件绝对路径只输出一次, 在第一行数据前输出, 后续依旧为每行数据
    val rdd4: RDD[(String, String)] = sc.wholeTextFiles("input")
    rdd4.collect().foreach(println)
  }

}
