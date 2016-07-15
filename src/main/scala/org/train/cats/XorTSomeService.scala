package org.train.cats

import cats.data.XorT
import cats.std.all._
import org.train.rx.extention.{SomeDate, SomeService}

import scala.util.Try

/**
  * Created by kisilnazar on 14.07.16.
  */
class XorTSomeService extends SomeService[XorT[Option, Exception, SomeDate]] {
  override def getSomeDate(): XorT[Option, Exception, SomeDate] = {
    Try {
      XorT.right[Option, Exception, SomeDate] {
        Some(someEvaluating())
      }
    } recover {
      case e: Exception => XorT.left[Option, Exception, SomeDate](Some(e))
    } get
  }

  override def getRemoteDate(): XorT[Option, Exception, SomeDate] = Try {
    XorT.right[Option, Exception, SomeDate](Some(SomeDate(0, "Remote SomeDate")))
  } recover {
    case e: Exception => XorT.left[Option, Exception, SomeDate](Some(e))
  } get
}
