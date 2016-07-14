package spec2.train.cats

import cats.data.Xor
import org.specs2.mutable.Specification

/**
  * Created by nk91 on 12.07.16.
  */
class CheckingXorMonadLaws extends Specification {

  val xorA = Xor.right("A")
  val xorB = Xor.right("B")
  val xorC = Xor.right("B")

  val x = "AAAA"

  val f: (String => Xor[Nothing, String]) = x => {
    Xor.right(x + x)
  }

  val fLeft: (String => Xor[Int, String]) = x => {
    Xor.left[Int, String](x.length * 2)
  }

  val gLeft: (String => Xor[Int, String]) = x => {
    Xor.left[Int, String](x.charAt(0).toInt)
  }

  val g: (String => Xor[Nothing, String]) = x => {
    Xor.right(x.toLowerCase + 1)
  }

  "checking monad law for Xor case with only Xor.right" should {

    "Associativity law" in {
      ((xorA flatMap f) flatMap g) must_== (xorA flatMap (y => f(y) flatMap g))
    }

    "Left unit" in {
      (Xor.right(x) flatMap f) must_== f(x)
    }

    "Right unit" in {
      (xorA flatMap Xor.right) must_== xorA
    }
  }

  "checking monad law for Xor case with only Xor.left" should {

    "Associativity law" in {
      ((xorA flatMap fLeft) flatMap gLeft) must_== (xorA flatMap (y => fLeft(y) flatMap gLeft))
    }

    "Left unit" in {
      (Xor.right(x) flatMap fLeft) must_== fLeft(x)
    }

    "Right unit" in {
      (xorA flatMap Xor.right) must_== xorA
    }
  }

}
