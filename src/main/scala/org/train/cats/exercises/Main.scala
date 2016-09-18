package org.train.cats.exercises

import cats.syntax.eq._
import org.train.cats.exercises.CatEq._

/**
  * Created by kisilnazar on 28.07.16.
  */
object Main extends App {

  val cat1 = Cat("Loran", 12, "Gray")
  val cat2 = Cat("Garfield", 12, "Gray")
  val cat3 = Cat("Garfield", 12, "Gray")

  println(cat1 === cat2)
  println(cat3 === cat2)
  println(cat1 === cat3)
}
