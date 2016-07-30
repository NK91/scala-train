package org.train.cats.exercises

import cats.syntax.show._
import org.train.cats.exercises.PrintableDefaults._

/**
  * Created by kisilnazar on 28.07.16.
  */
object Main extends App {

  val cat = Cat("Loran", 12, "Gray")

  Print.print(cat)

  println(cat.show)
}
