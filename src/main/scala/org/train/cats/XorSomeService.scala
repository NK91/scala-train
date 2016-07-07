package org.train.cats

import cats.data.Xor
import org.train.rx.extention.{SomeDate, SomeService}

import scala.util.Try

/**
  * Created by kisilnazar on 06.07.16.
  */
class XorSomeService extends SomeService[Xor[Exception, SomeDate]] {

  override def getSomeDate(): Xor[Exception, SomeDate] = {
    Try {
      Xor.Right(someEvaluating())
    } recover {
      case e: Exception => Xor.Left(e)
    } get
  }

  override def getRemoteDate(): Xor[Exception, SomeDate] = {
    Try {
      Xor.Right(SomeDate(1, "RemoteDate"))
    } recover {
      case e: Exception => Xor.Left(e)
    } get
  }
}
