package org.train.cats

import cats.Foldable
import cats.data.{Xor, XorT}
import cats.Monoid
import org.train.utlis.CatsUtils._
import cats.std.all._

/**
  * Created by kisilnazar on 05.07.16.
  */
object CatsApplication extends App {

  val s = List(1, 2, 3, 5, 6).map(_.toOptionXorT).map( x => x.map(List(_)))

  println(s"before: $s")
  val r = FoldingXorTOptionList.fold(s)
  println(s"after: $r")

}
