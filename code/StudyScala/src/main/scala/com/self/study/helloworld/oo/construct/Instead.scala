package com.self.study.helloworld.oo.construct

/**
 * 类型替换问题
 */
object Instead {

  def main(args: Array[String]): Unit = {
//    val person: Person1 = new Man1() // 属于Person
    val person = new Person1() // 属于Person
    if (person.isInstanceOf[Man1] && person.isInstanceOf[Person1]) {
      println("属于man")
      val man: Man1 = person.asInstanceOf[Man1]
      println(man.name)
    } else if (person.isInstanceOf[Person1]) {
      println("属于person")
    } else {
      println("啥也不是")
    }
  }

}

class Man1 extends Person1 {
  val age: String = "18"
}

class Person1 {
  val name: String = "zhangsan"
}
