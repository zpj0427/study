package com.self.study.helloworld.collection.base

/**
 * 并行集合
 * 加上par关键字后, 会有多线程的效果
 */
object ParList {

  def main(args: Array[String]): Unit = {
    val result1 = (0 to 100).map( x => Thread.currentThread().getName)
    val result2 = (0 to 100).par.map(x => Thread.currentThread().getName)
    println(result1)
    println(result2)
  }

}
