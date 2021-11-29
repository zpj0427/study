package com.self.study.helloworld.modematch

/**
 * 模式匹配
 * 每一个case后默认带break
 * case _ 表示default
 */
object TestModeMatch {

  def main(args: Array[String]): Unit = {
    // 基本语法, 模式匹配
    println(baseSyntax('a'))
    println(baseSyntax('c'))
    println( 1 match {
      case 0 => 0
      case _ => "other"
    })
    println("-----------------------")

    // 模式守卫
    println(modeGuard(-1))
    println(modeGuard(1))
    println(modeGuard("0"))
    println("-----------------------")

    // 匹配常量
    println(matchVal(5))
    println(matchVal("hello"))
    println(matchVal("123"))
    println("-----------------------")

    // 匹配类型
    // List 只是只是泛型 不会进行强匹配
    println(matchType(1))
    println(matchType("1"))
    println(matchType(List("123", "456")))
    println(matchType(List(1, 2)))
    println(matchType(Array("23", "456")))
    println(matchType(Array(1, 2)))
    println("-----------------------")

    // 匹配数组
    println(matchArray(Array(0)))
    println(matchArray(Array(4, 5)))
    println(matchArray(Array(0, 1, 2)))
    println(matchArray(Array(1, 1, 2)))
    println("-----------------------")

    // 匹配列表
    println(matchList(List(0)))
    println(matchList(List(22, 33)))
    println(matchList(List(0, 1)))
    println(matchList(List(1, 2 ,3, 5)))
    println("-----------------------")

    // 匹配元组
    println(matchTuple((0, 1)))
    println(matchTuple((1, 0)))
    println(matchTuple(1, 2))
    println(matchTuple((1, 2, 1)))
    println("-----------------------")

    // 匹配对象
    println(matchObject(User("zhangsan", 18)))
    println(matchObject(User("zhangsan", 19)))
    println("-----------------------")

    // 匹配样例类
    println(matchCaseObject(CaseUser("zhangsan", 18)))
    println(matchCaseObject(CaseUser("zhangsan", 19)))
    println("-----------------------")

    // for循环中的遍历
    matchFor(Map("a" -> 1, "b" -> 0, "c" -> 0))
    println("-----------------------")

    // 偏函数
    val fun = new PartialFunction[List[Int], Option[Int]] {
      override def isDefinedAt(x: List[Int]): Boolean = x match {
        case x :: y :: _ => true
        case _ => false
      }

      override def apply(x: List[Int]): Option[Int] = x match {
        case x :: y :: _ => Some(y)
      }
    }
    println(fun.applyOrElse(List(1, 2, 3, 4), (_: List[Int]) => None))
    println(fun.applyOrElse(List(1), (_: List[Int]) => None))
  }

  def matchFor(map: Map[String, Int]) = {
    // 匹配所有KV
    for ((k, v) <- map) print(s"$k --- $v, ")
    println()
    // 匹配值为0的kv
    for ((k, 0) <- map) print(s"$k --- 0, ")
    println()
    // 匹配v > 0 的kv
    for ((k, v) <- map if v > 0) print(s"$k --- $v, ")
    println()
  }

  def matchCaseObject(x: CaseUser) = x match {
    case CaseUser("zhangsan", 18) => true
    case _ => false
  }

  def matchObject(x: User) = x match {
    case User("zhangsan", 18) => "zhangsan, 18"
    case _ => "error"
  }

  def matchTuple(x: Any) = x match {
    case (0, x) => "以0开始的二元组" + x
    case (y, 0) => "以0结尾的二元组" + y
    case (x, y) => s"匹配二元组, $x, $y"
    case _ => "其他非元组"
  }

  def matchList(x: List[Int]) = x match {
    case List(0) => "0"
    case List(x, y) => s"$x, $y"
    case List(0, _*) => "以0开头的列表"
    // case List(x, y, _*) => s"$x, $y, 最少有2个元素的列表"
    case one :: two :: other => s"$one, $two, $other"
    case _ => "其他列表"
  }

  def matchArray(x: Array[Int]) = x match {
    case Array(0) => "0" // 精确匹配 Array(0) 这个数组
    case Array(x, y) => s"$x, $y" // 匹配有2个元素的数组
    case Array(0, _*) => "以0开头的数组"
    case Array(x, y, _*) => s"$x, $y, 最少有2个元素的数组"
    case _ => "其他数组"
  }

  def matchType(x: Any) = x match {
    case i: Int => "int"
    case s: String => "string"
    case list: List[Int] => "list[Int]"
    case listStr: List[String] => "list[String]"
    case array: Array[Int] => "array[int]"
    case arrayStr: Array[String] => "array[String]"
    case _ => "other"
  }

  def matchVal(x: Any) = x match {
    case 5 => "five"
    case "hello" => "world"
    case true => "you are right"
    case _ => "error"
  }

  def modeGuard(x: Any) = x match {
    case i: Int if i > 0 => i
    case j: Int if j < 0 => -j
    case 0 => 0
    case _ => "type illegal"
  }

  def baseSyntax(x: Char) = x match {
    case 'a' => "a"
    case 'b' => "b"
    case _ => "other"
  }

}
