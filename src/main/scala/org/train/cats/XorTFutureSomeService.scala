package org.train.cats

import cats.data.XorT
import org.train.rx.extention.{SomeDate, SomeService}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.std.future._


/**
  * Created by kisilnazar on 17.07.16.
  */
class XorTFutureSomeService extends SomeService[XorT[Future, Exception, SomeDate]] {
  override def getSomeDate(): XorT[Future, Exception, SomeDate] =
    XorT.right[Future, Exception, SomeDate](Future.successful(SomeDate(101, "SomeDate Future")))

  override def getRemoteDate(): XorT[Future, Exception, SomeDate] =
    XorT.right[Future, Exception, SomeDate](Future.successful(SomeDate(1234, "RemoteDate Future")))

}
