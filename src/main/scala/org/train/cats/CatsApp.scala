package org.train.cats

import cats.data.Xor
import org.train.utlis.CatsUtils._

/**
  * Created by kisilnazar on 05.07.16.
  */
object CatsApp extends App {
  val xorR = Xor.right(5)
  println(xorR.map(_ + 1))

  val xorL: String Xor Int = Xor.left("string")
  println(xorL.map(_ + 1))

}
