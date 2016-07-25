package org.train.cats

import cats.data.Xor
import org.train.rx.extention.{SomeData, SomeService}

import scala.util.Try

/**
  * Created by kisilnazar on 06.07.16.
  */
class XorSomeService extends SomeService[Xor[Exception, SomeData]] {

  override def getSomeDate(): Xor[Exception, SomeData] = {
    Try {
      Xor.Right(someEvaluating())
    } recover {
      case e: Exception => Xor.Left(e)
    } get
  }

  override def getRemoteDate(): Xor[Exception, SomeData] = {
    Try {
      Xor.Right(SomeData(1, "RemoteDate"))
    } recover {
      case e: Exception => Xor.Left(e)
    } get
  }
}
