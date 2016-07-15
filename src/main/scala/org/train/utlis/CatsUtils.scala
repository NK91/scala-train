package org.train.utlis

import cats.data.Xor

import scala.util.Try

/**
  * Created by nk91 on 11.07.16.
  */
object CatsUtils {

  type Or[+V] = Xor[Exception, V]

  def LeftXor[T]: PartialFunction[Throwable, Or[T]] = {
    case e: Exception => Xor.left(e)
    case _ => Xor.left(new Exception("Uknown exception"))
  }

  implicit class TryXorExtension[+A](tryBlock: Try[Or[A]]) {
    def recoverWithDefault(): Try[Or[A]] = tryBlock recover LeftXor[A]
  }

  implicit class EitherToXor[+A, B](either: Either[A, B]) {
    def toXor = Xor.fromEither(either)
  }

}
