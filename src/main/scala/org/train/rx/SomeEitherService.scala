package org.train.rx

import scala.util.Try

/**
  * Created by kisilnazar on 22.06.16.
  */
package extention {

  import rx.lang.scala.Observable

  trait Evaluation {
    def someEvaluating(): SomeDate = SomeDate(0, "SomeData from someEvaluating")
    def someEvaluating(index: Int): SomeDate = SomeDate(index, s"SomeData from someEvaluating with index=$index")
  }

  trait SomeService[T] extends Evaluation {

    def getSomeDate(): T

    def getRemoteDate(): T
  }


  class SomeEitherService extends SomeService[Either[Exception, SomeDate]] {

    override def getSomeDate(): Either[Exception, SomeDate] = {
      Try {
        Right(someEvaluating())
      } recover {
        case e: Exception => Left(e)
      } get
    }

    override def getRemoteDate(): Either[Exception, SomeDate] = {
      Try {
        Right(SomeDate(1, "RemoteDate"))
      } recover {
        case e: Exception => Left(e)
      } get
    }
  }

  class SomeRxService extends SomeService[Observable[SomeDate]] {

    override def getSomeDate(): Observable[SomeDate] = {
      Observable(subscription => Try {
        subscription.onNext(someEvaluating())
      } recover {
        case e: Throwable => subscription.onError(e)
      })
    }

    override def getRemoteDate(): Observable[SomeDate] = {
      Observable(subscription => Try {
        subscription.onNext(SomeDate(1, "RemoteDate"))
      } recover {
        case e: Throwable => subscription.onError(e)
      })
    }
  }

  case class ServiceException(message: String = "Some exception happen") extends Exception(message)

  case class SomeDate(id: Long, data: String)

}
