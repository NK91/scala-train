package org.train.generators

/**
  * Created by kisilnazar on 13.07.16.
  */
object GeneratorApp extends App {

  val integers = new IntegerGenerator

  val randomList = Generators.lists.generate

  println(randomList)

}
