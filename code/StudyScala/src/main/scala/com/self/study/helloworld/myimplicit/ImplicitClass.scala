package com.self.study.helloworld.myimplicit

/**
 * 隐式转换类
 * 注意隐式转换类不能作为顶级类
 */
object ImplicitClass {

  def main(args: Array[String]): Unit = {
    println(1.myMax(10))
  }

  implicit class CovertClass(value: Int) {
    // 注意方法名不要与系统方法名重复
    def myMax(two: Int): Int = {
      if (two > value) two else value
    }
  }

}
