package org.train.cats.exercises

import cats.kernel.Eq
import cats.std.all._
import cats.syntax.eq._

/**
  * Created by kisilnazar on 30.07.16.
  */
object CatEq {

  implicit val catEq = Eq.instance[Cat] {
    (cat1, cat2) =>
      (cat1.age === cat2.age &&
        cat1.name === cat2.name &&
        cat1.color === cat2.color)
  }

  implicit val catOptinEq = Eq.instance[Option[Cat]] {
    (cat1, cat2) =>
      cat1 === cat2
  }


}
