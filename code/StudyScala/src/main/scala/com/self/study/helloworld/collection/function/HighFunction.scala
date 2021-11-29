package com.self.study.helloworld.collection.function

/**
 * 高级函数
 */
object HighFunction {

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5, 6, 7, 8)

    // 过滤, 取偶数
    println(list.filter(x => x % 2 == 0))

    // 转换
    println(list.map(x => x + 1))

    // 扁平化
    val nestedList = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
    println(nestedList.flatten)

    // 扁平化加映射
    val wordList = List("hello world", "hello scala", "hello spark")
    val splitList: List[Array[String]] = wordList.map(x => x.split(" "))
    println(splitList)
    println(splitList.flatten)

    println(wordList.flatMap(x => x.split(" ")))

    // 分组
    println(list.groupBy(x => if (x % 2 == 0) "偶数" else "基数"))

    // 简化
    // x初始表示第一个值或者最后一个值, 后续表示计算结果值, y值为持续遍历值
    val list2 = List(1, 2, 3, 4)
    // 1 - 2 - 3 - 4 = -8
    println(list2.reduce((x, y) => x - y))
    // 1 - 2 - 3 - 4 = -8
    println(list2.reduceLeft((x, y) => x - y))
    // 1 - (2 - (3 - 4)) = -2
    println(list2.reduceRight((x, y) => x - y))

    // 折叠, 是化简的一种特殊情况, 支持基于基础数据进行处理, 使用了函数柯里化
    // x初始表示小括号中的初始值, 后续表示返回的值
    // 8 - 1 - 2 - 3 - 4 = -2
    println(list2.fold(8)((x, y) => x - y))
    // 10 - 1 - 2 - 3 - 4 = 0
    println(list2.foldLeft(10)((x, y) => x - y))
    // 1 - (2 - (3 - (4 - 10))) = 8
    println(list2.foldRight(10)((x, y) => x - y))
  }

}
