package org.train.cats

import cats.data.XorT
import cats.std.future._
import org.train.rx.extention.{SomeData, SomeService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by kisilnazar on 17.07.16.
  */
class XorTFutureSomeService extends SomeService[XorT[Future, Exception, SomeData]] {
  override def getSomeDate(): XorT[Future, Exception, SomeData] =
    XorT.right[Future, Exception, SomeData](Future.successful(SomeData(101, "SomeDate Future")))

  override def getRemoteDate(): XorT[Future, Exception, SomeData] =
    XorT.right[Future, Exception, SomeData](Future.successful(SomeData(1234, "RemoteDate Future")))

}
