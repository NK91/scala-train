package org.train.utlis

import cats.data.Xor
import org.train.rx.extention.SomeDate

/**
  * Created by nk91 on 11.07.16.
  */
object CatsUtils {

  type Or[+V] = Xor[Exception, V]

  def LeftXor[T]: PartialFunction[Throwable, Or[T]] = {
    case e: Exception => Xor.left(e)
    case _ => Xor.left(new Exception("Uknown exception"))
  }

}
