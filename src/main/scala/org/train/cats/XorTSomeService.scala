package org.train.cats

import cats.data.XorT
import cats.std.all._
import org.train.rx.extention.{SomeData, SomeService}

import scala.util.Try

/**
  * Created by kisilnazar on 14.07.16.
  */
class XorTSomeService extends SomeService[XorT[Option, Exception, SomeData]] {
  override def getSomeDate(): XorT[Option, Exception, SomeData] = {
    Try {
      XorT.right[Option, Exception, SomeData] {
        Some(someEvaluating())
      }
    } recover {
      case e: Exception => XorT.left[Option, Exception, SomeData](Some(e))
    } get
  }

  override def getRemoteDate(): XorT[Option, Exception, SomeData] =
    Try {
      XorT.right[Option, Exception, SomeData](Some(SomeData(0, "Remote SomeDate")))
    } recover {
      case e: Exception => XorT.left[Option, Exception, SomeData](Some(e))
    } get
}
