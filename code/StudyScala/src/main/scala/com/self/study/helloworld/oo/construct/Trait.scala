package com.self.study.helloworld.oo.construct

trait Parent {
  def describe: String = {
    "ball"
  }
}

trait Man extends Parent {
  override def describe: String = {
    "man-" + super.describe
  }
}

trait Woman extends Parent {
  override def describe: String = {
    "woman-" + super.describe
  }
}

class Execute extends Man with Woman {
  override def describe: String = {
    "execute- " + super.describe
  }
}

object Trait {

  def main(args: Array[String]): Unit = {
    println(new Execute().describe)
  }

}
