package org.train.utlis

import cats.data.{Xor, XorT}
import cats.std.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
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

  implicit class WrapIntoXorT[T](any: T) {
    def toFutureXorT: XorT[Future, Exception, T] =
      XorT.right[Future, Exception, T](Future(any))

    def toOptionXorT: XorT[Option, Exception, T] =
      XorT.right[Option, Exception, T](Option(any))

    def toOptionXorTNone: XorT[Option, Exception, T] =
      XorT.right[Option, Exception, T](None)
  }

}
