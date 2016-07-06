package org.train.cats

import cats.data.Xor

/**
  * Created by kisilnazar on 05.07.16.
  */
object Cats extends App {
  val xorR = Xor.right(5)
  println(xorR.map(_ + 1))

  val xorL: String Xor Int = Xor.left("string")
  println(xorL.map(_ + 1))

}
